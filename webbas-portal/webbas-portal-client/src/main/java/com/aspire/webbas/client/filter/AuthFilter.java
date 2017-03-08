package com.aspire.webbas.client.filter;

import static com.aspire.webbas.client.filter.TicketUtil.SESSION_TICKET;
import static com.aspire.webbas.client.filter.TicketUtil.findTicket;
import static com.aspire.webbas.client.filter.TicketUtil.sendPrivacyCookie;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspire.webbas.client.AuthProxy;
import com.aspire.webbas.client.config.PortalClientConfig;
import com.aspire.webbas.portal.common.authapi.AuthConstant;
import com.aspire.webbas.portal.common.authapi.AuthResult;
import com.aspire.webbas.portal.common.entity.Staff;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthFilter implements Filter {

	private static final Logger LOG = LoggerFactory.getLogger(AuthFilter.class);

	/** * JSP后缀. */
	public static final String JSP_SUFFIX = "jsp";
	/** * AJAX后缀. */
	public static final String AJAX_SUFFIX = "ajax";

	public static final String ADMIN_DOMAIN = "admin-domain";

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String contextPath = httpRequest.getContextPath();
		String servletPath = httpRequest.getServletPath();

		// 如果请求路径中有双斜杠转为单斜杠
		servletPath = servletPath.replaceAll("\\//", "/");

		LOG.debug("contextPath[" + contextPath + "],  servletPath["
				+ servletPath + "]");
		String authUrl = PortalClientConfig.getInstance().getPortalAuthUrl();

		if (StringTools.isEmptyString(authUrl)) {
			LOG.error("错误：没有配置鉴权路径");
			return;
		}
		AuthProxy proxy = AuthProxy.getProxy(authUrl);
		try {
			if (proxy.authExclude(servletPath)){
				chain.doFilter(request, response);
				return;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return;
		}
		String ticket = findTicket(httpRequest);

		if (StringTools.isEmptyString(ticket)) {
			LOG.error("没有ticket, 鉴权失败");
			httpResponse.sendRedirect(PortalClientConfig.getInstance()
					.getOutDomain()
					+ AuthConstant.DEFAULT_NOGRANTAUTHORITY_PAGE);
			return;
		}

		Staff staff = null;
		HttpSession session = httpRequest.getSession(true);

		staff = (Staff) session.getAttribute(ticket);

		if (staff == null) {
			staff = AuthProxy.getProxy().auth(ticket);
			if (staff == null) {
				if (isAjaxRequest(httpRequest)) {
					sendError(httpResponse, new AuthResult(
							AuthResult.NOT_LOGIN, "用户未登录", PortalClientConfig
									.getInstance().getOutDomain()
									+ AuthConstant.DEFAULT_LOGIN_PAGE));
				} else {
					httpResponse.sendRedirect(PortalClientConfig.getInstance()
							.getOutDomain() + AuthConstant.DEFAULT_LOGIN_PAGE);
				}
				return;
			} else {
				session.setAttribute(ticket, staff);
			}
		}

		// 有ticket, 调用远程接口进行鉴权
		AuthResult result = proxy.authUrl(ticket, servletPath);

		LOG.debug("调用portal鉴权接口,返回[returnCode:" + result.getReturnCode()
				+ ", message:" + result.getMessage() + "]");

		// url鉴权失败
		if (!AuthResult.SUCCESS.equals(result.getReturnCode())) {
			result.setRedirectUrl(PortalClientConfig.getInstance()
					.getOutDomain() + result.getRedirectUrl());
			if (isAjaxRequest(httpRequest)) {
				sendError(httpResponse, result);
			} else {
				LOG.debug("sendRedirect:" + result.getRedirectUrl());
				httpResponse.sendRedirect(result.getRedirectUrl());
			}
			return;
		}

		// 鉴权成功， 把ticket写入cookie，并缓存在session中
		sendPrivacyCookie(httpRequest, httpResponse, ticket);

		Cookie privacy = new Cookie(ADMIN_DOMAIN, URLEncoder.encode(
				PortalClientConfig.getInstance().getOutDomain(), "UTF-8"));// 将webbas
																			// 的外网地址设置到cookie中
		privacy.setMaxAge(-1);
		privacy.setPath("/");
		httpResponse.addCookie(privacy);

		session.setAttribute(SESSION_TICKET, ticket);

		chain.doFilter(request, response);
	}

	public void destroy() {
	}

	private void sendError(HttpServletResponse response, AuthResult result) {
		try {
			JsonGenerator jsonGenerator = null;
			ObjectMapper objectMapper = new ObjectMapper();
			jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(
					response.getOutputStream(), JsonEncoding.UTF8);

			jsonGenerator.writeObject(result);

		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * 有些ajax请求后缀不一定用ajax
	 * 
	 * @param request
	 * @return
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
		String xRequestedWith = request.getHeader("x-requested-with");
		return null != xRequestedWith
				&& "XMLHttpRequest".equalsIgnoreCase(xRequestedWith) ? true
				: false;
	}
}