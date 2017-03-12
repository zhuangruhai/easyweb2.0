package com.hichlink.easyweb.portal.common.servlet;

import static com.hichlink.easyweb.portal.common.config.Constants.COOKIE_VALID_MAXAGE;
import static com.hichlink.easyweb.portal.common.config.Constants.COOKIE_VALID_PASSWD;
import static com.hichlink.easyweb.portal.common.config.Constants.COOKIE_VALID_USERNAME;
import static com.hichlink.easyweb.portal.common.config.Constants.TICKET_COOKIE_NAME;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hichlink.easyweb.core.servlet.BaseServlet;
import com.hichlink.easyweb.core.util.SpringContextHolder;
import com.hichlink.easyweb.core.util.StringTools;
import com.hichlink.easyweb.portal.common.auth.session.SessionContext;
import com.hichlink.easyweb.portal.common.config.Config;
import com.hichlink.easyweb.portal.common.entity.City;
import com.hichlink.easyweb.portal.common.entity.Department;
import com.hichlink.easyweb.portal.common.entity.Staff;
import com.hichlink.easyweb.portal.common.entity.StaffExtendProperty;
import com.hichlink.easyweb.portal.common.service.CityService;
import com.hichlink.easyweb.portal.common.service.DepartmentService;
import com.hichlink.easyweb.portal.common.service.LoginService;
import com.hichlink.easyweb.portal.common.service.StaffService;
import com.hichlink.easyweb.portal.common.service.impl.DepartmentServiceImpl;
import com.hichlink.easyweb.portal.common.service.impl.LoginServiceImpl;
import com.hichlink.easyweb.portal.common.service.impl.StaffServiceImpl;
import com.hichlink.easyweb.portal.common.util.CookieUtil;
import com.hichlink.easyweb.portal.common.util.RSAUtil;

/**
 * 
 * <b>Title：</b>FacadeServlet.java<br/>
 * <b>Description：</b> 提供页面请求的一些js数据<br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2014年6月27日 上午10:30:57<br/>
 * <b>Copyright (c) 2014 ASPire Tech.</b>
 *
 */
@WebServlet(urlPatterns="/servlet/facade.script",description="提供页面请求的一些js数据")
public class FacadeServlet extends BaseServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2416289388042304446L;
	private static final Logger LOG = LoggerFactory.getLogger(FacadeServlet.class);
	private LoginService loginService;
	private StaffService staffService;
	private DepartmentService departmentService;
	@Autowired
	private Config config;
	private boolean autoLogin(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = SessionContext.getContext().getSession(request.getSession(true).getId());
		if (null != session && null != session.getAttribute("LOGIN_STAFF")) {
			return false;
		}
		String username = CookieUtil.getCookie(request, COOKIE_VALID_USERNAME);
		String password = CookieUtil.getCookie(request, COOKIE_VALID_PASSWD);
		String passwd = RSAUtil.decryptString(password);
		if (StringTools.isNotEmptyString(username) && StringTools.isNotEmptyString(passwd)) {
			loginService = SpringContextHolder.getBean(LoginServiceImpl.class);
			try {
				if (config.isContractAgreementOn()) {
					staffService = SpringContextHolder.getBean(StaffServiceImpl.class);
					Staff staff = staffService.findStaffByLoginName(username);
					Department department = null;
					if (null != staff.getDepartmentId()) {
						departmentService = SpringContextHolder.getBean(DepartmentServiceImpl.class);
						department = departmentService.findDepartment(staff.getDepartmentId());
					}
					List<StaffExtendProperty> list = staffService.listStaffExtendProperties(staff.getStaffId());
					if ((null != department && "SP".equals(department.getDomain()))
							&& !loginService.isReadContractAgreement(list)) {
						throw new RuntimeException("由于用户未同意协议，自动登录无效，此异常可忽略。");
					}
				}
				loginService.login(username, passwd, request.getSession());
				CookieUtil.setCookie(request, response, TICKET_COOKIE_NAME, request.getSession().getId());
				CookieUtil.setCookie(request, response, COOKIE_VALID_USERNAME, username, COOKIE_VALID_MAXAGE);
				CookieUtil.setCookie(request, response, COOKIE_VALID_PASSWD, password, COOKIE_VALID_MAXAGE);

				return true;
			} catch (Exception e) {
				LOG.error("在Filter中自动登录失败，错误可忽略", e);
				try {
					CookieUtil.delCookie(request, response, COOKIE_VALID_USERNAME);
					CookieUtil.delCookie(request, response, COOKIE_VALID_PASSWD);
				} catch (ServletException e1) {
					LOG.error("在Filter中删除自动登录cookie出错，错误可忽略", e1);
				}

			}
		}
		return false;
	}

	public void getLoginInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String title = config.getTitle();
		String modulus = RSAUtil.bigIntToHexStr(RSAUtil.getDefaultPublicKey().getModulus());
		String exponent = RSAUtil.bigIntToHexStr(RSAUtil.getDefaultPublicKey().getPublicExponent());
		Staff loginStaff = null;
		autoLogin(request, response);
		HttpSession session = SessionContext.getContext().getSession(request.getSession(true).getId());
		if (null != session) {
			loginStaff = (Staff) session.getAttribute("LOGIN_STAFF");
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("modulus", modulus);
		jsonObject.put("exponent", exponent);
		jsonObject.put("isLogin", (null != loginStaff ? true : false));
		jsonObject.put("isCheckcodeOn", config.isCheckCodeOn());
		jsonObject.put("isRegisterOn", config.isRegisterOn());
		jsonObject.put("registerUrl", config.getRegisterUrl());
		jsonObject.put("isForgotpwdOn", config.isForgotpwdOn());
		jsonObject.put("forgotpwdUrl", config.getForgotpwdUrl());
		jsonObject.put("isContractAgreementOn", config.isContractAgreementOn());

		super.outputJS(request, response, "var config = " + jsonObject.toString());
	}

	public void getStaffInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String title = config.getTitle();
		autoLogin(request, response);
		HttpSession session = SessionContext.getContext().getSession(request.getSession().getId());
		Staff loginStaff = (Staff) session.getAttribute("LOGIN_STAFF");
		String realName = "";
		if (null != loginStaff) {
			realName = loginStaff.getRealName();
		}
		StringBuilder sb = new StringBuilder();
		sb.append("window.title='" + title + "';");
		sb.append("var realName='" + realName + "';");
		super.outputJS(request, response, sb.toString());
	}

	public void listCity(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CityService cityService = SpringContextHolder.getBean(CityService.class);
		List<City> list = cityService.list(null);
		StringBuilder sb = new StringBuilder();
		sb.append("var citys=" + JSON.toJSONString(list) + ";");
		super.outputJS(request, response, sb.toString());
	}
}
