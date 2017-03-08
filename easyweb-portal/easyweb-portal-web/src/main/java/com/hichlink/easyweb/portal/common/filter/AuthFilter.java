package com.aspire.webbas.portal.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspire.webbas.core.util.SpringContextHolder;
import com.aspire.webbas.portal.common.auth.session.SessionContext;
import com.aspire.webbas.portal.common.authapi.AuthConstant;
import com.aspire.webbas.portal.common.authapi.AuthResult;
import com.aspire.webbas.portal.common.entity.Staff;
import com.aspire.webbas.portal.common.service.AuthService;
import com.aspire.webbas.portal.common.service.impl.AuthServiceImpl;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
public class AuthFilter implements Filter {

	/** * JSP后缀. */
	public static final String JSP_SUFFIX = "jsp";
	/** * AJAX后缀. */
	public static final String AJAX_SUFFIX = "ajax";
	
	private static final Logger LOG = LoggerFactory.getLogger(AuthFilter.class);
	
	private AuthService authService;
	
	public AuthService getAuthService() {
		return authService;
	}

	public void setAuthService(AuthService authService) {
		this.authService = authService;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	public void doFilter(ServletRequest request, 
			             ServletResponse response,
			             FilterChain chain) 
		throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String contextPath = httpRequest.getContextPath();
		String servletPath = httpRequest.getServletPath();
		
		//如果请求路径中有双斜杠转为单斜杠
		servletPath = servletPath.replaceAll("\\//", "/");
		
		System.out.println("contextPath["+contextPath+"],  servletPath[" + servletPath + "]");

	
		authService = SpringContextHolder.getBean(AuthServiceImpl.class);
		// 判断是否鉴权例外的url， 无需登录 , 第二个判断是规避元数据没导入或者导入出错之后访问首页死循环的问题
		if(authService.authExclude(servletPath) || servletPath.equals(AuthConstant.DEFAULT_LOGIN_PAGE)){
			System.out.println("url["+servletPath+"]属于例外鉴权。");
			chain.doFilter(request, response);
			return;
		}
		// 检查用户是否已经登录
		HttpSession session = SessionContext.getContext().getSession(httpRequest.getSession(true).getId());
		
		Staff staff = session == null ? null : (Staff)session.getAttribute("LOGIN_STAFF");
		// 对已经登录的用户，访问根“/”, 登录页面都统一重定向到首页
		if (("/".equals(servletPath)
						|| "".equals(servletPath)
						|| servletPath.equals(AuthConstant.DEFAULT_LOGIN_PAGE))) {
			if (null != staff){
				httpResponse.sendRedirect(contextPath + AuthConstant.DEFAULT_MAIN_PAGE);
				return;
			}
		}
		if(staff == null){
			forceToLogin(httpRequest,httpResponse, contextPath, servletPath);
			return;
		}
		
//		System.out.println("******获取登录用户["+staff.getStatusName()+"]*******");
		
		
		
		System.out.println("是否从session获得用户：" + (staff != null));
		
		
		// 判断登录后无需用户鉴权的url
		if(authService.notNeedAuthAfterLogin(servletPath)){
			System.out.println("url["+servletPath+"]属于登录后不鉴权。");
			chain.doFilter(request, response);
			return;
		}
		
		System.out.println("***********用户["+staff.getStaffId()+"]已登录*************");
		
		// 用户url鉴权, 鉴权失败按类型返回
		// ajax类型返回鉴权失败的json， 页面类型则直接重定向到没有权限的页面
		if( !authService.authorize(staff.getStaffId(), servletPath) ){
			System.out.println("***用户["+staff.getStaffId()+"]鉴权失败*****");
			
			if(isAjaxRequest(httpRequest)){
				sendError(httpResponse, 
						new AuthResult(AuthResult.FAIL,"用户没有权限",contextPath + AuthConstant.DEFAULT_NOGRANTAUTHORITY_PAGE));
			}else{
				httpResponse.sendRedirect(contextPath + AuthConstant.DEFAULT_NOGRANTAUTHORITY_PAGE);
			}

			return;
		}
		
		chain.doFilter(request, response);
	}
	
	public void destroy() {
	}
	private void forceToLogin(HttpServletRequest request, HttpServletResponse response, String contextPath, String servletPath) 
			throws IOException{
		System.out.println("forceToLogin....");
		if(isAjaxRequest(request)){
			sendError(response, 
					new AuthResult(AuthResult.NOT_LOGIN,"用户未登录",contextPath + AuthConstant.DEFAULT_LOGIN_PAGE));
		}else{
			System.out.println("************重定向到登录页面*****************");
			response.sendRedirect(contextPath + AuthConstant.DEFAULT_LOGIN_PAGE);
		}
	}
    
    private void sendError(HttpServletResponse response, AuthResult result){
    	try {

			JsonGenerator jsonGenerator = null;
			ObjectMapper objectMapper = new ObjectMapper();
			jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(
					response.getOutputStream(), JsonEncoding.UTF8);

			jsonGenerator.writeObject(result);
    		  
		} catch (IOException e) {
			LOG.error(e.getMessage(),e);
		}
    }
    
    public boolean isAjaxRequest(HttpServletRequest request) {
        String xRequestedWith = request.getHeader("x-requested-with");
        return null != xRequestedWith && "XMLHttpRequest".equalsIgnoreCase(xRequestedWith) ? true : false;
    }
}
