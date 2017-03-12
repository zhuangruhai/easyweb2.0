package com.hichlink.easyweb.portal.modules.login.web;

import static com.hichlink.easyweb.portal.common.config.Constants.COOKIE_VALID_MAXAGE;
import static com.hichlink.easyweb.portal.common.config.Constants.COOKIE_VALID_PASSWD;
import static com.hichlink.easyweb.portal.common.config.Constants.COOKIE_VALID_USERNAME;
import static com.hichlink.easyweb.portal.common.config.Constants.TICKET_COOKIE_NAME;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hichlink.easyweb.core.web.BaseController;
import com.hichlink.easyweb.portal.common.config.Config;
import com.hichlink.easyweb.portal.common.entity.Department;
import com.hichlink.easyweb.portal.common.entity.Staff;
import com.hichlink.easyweb.portal.common.entity.StaffExtendProperty;
import com.hichlink.easyweb.portal.common.service.DepartmentService;
import com.hichlink.easyweb.portal.common.service.LoginService;
import com.hichlink.easyweb.portal.common.service.StaffService;
import com.hichlink.easyweb.portal.common.util.CheckCodeUtil;
import com.hichlink.easyweb.portal.common.util.CookieUtil;
import com.hichlink.easyweb.portal.common.util.RSAUtil;
import com.hichlink.easyweb.portal.common.util.StaffUtil;

@Controller
@RequestMapping("/portal")
public class LoginController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

	private static final String LOGIN_STAFF_TMP = "LOGIN_STAFF_TMP";
	@Autowired
	@Qualifier("loginService")
	private LoginService loginService;
	@Autowired
	@Qualifier("staffService")
	private StaffService staffService;

	@Autowired
	@Qualifier("departmentService")
	private DepartmentService departmentService;

	@RequestMapping(value = "/login.ajax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ? extends Object> login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String loginName, @RequestParam String password,
			@RequestParam(value = "checkCode", required = false) String checkCode,
			@RequestParam(value = "isAuto", required = false) String isAuto) {

		try {

			if (isEmpty(loginName) || isEmpty(password)) {
				throw new Exception("用户/密码为空");
			}

			if (Config.getInstance().isCheckCodeOn() && isEmpty(checkCode)) {
				throw new Exception("验证码为空");
			}

			if (Config.getInstance().isCheckCodeOn() && !CheckCodeUtil.check(getSession(), checkCode)) {
				throw new Exception("验证码不正确");
			}

			// 密码解密
			String pwd = RSAUtil.decryptString(password);

			loginService.login(loginName, pwd, getSession());
			CookieUtil.setCookie(request, response, TICKET_COOKIE_NAME, getSession().getId());
			if (isNotEmpty(isAuto) && "true".equalsIgnoreCase(isAuto)) {
				CookieUtil.setCookie(request, response, COOKIE_VALID_USERNAME, loginName, COOKIE_VALID_MAXAGE);
				CookieUtil.setCookie(request, response, COOKIE_VALID_PASSWD, password, COOKIE_VALID_MAXAGE);
			}
			Map<String, Object> result = new HashMap<String, Object>();
			if (Config.getInstance().isContractAgreementOn()) {
				Staff staff = (Staff) getSession().getAttribute(LoginService.LOGIN_STAFF);
				Department department = null;
				if (null != staff.getDepartmentId()) {
					department = departmentService.findDepartment(staff.getDepartmentId());
					result.put("department", department);
				}
				List<StaffExtendProperty> list = staffService.listStaffExtendProperties(staff.getStaffId());
				result.put("staffExtendProperty", list);
				if ((null != department && "SP".equals(department.getDomain()))
						&& !loginService.isReadContractAgreement(list)) {
					getSession().setAttribute(LOGIN_STAFF_TMP, staff);
					getSession().removeAttribute(LoginService.LOGIN_STAFF);
				}
			}
			return success(result);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return fail(e.getMessage());
		}
	}

	@RequestMapping(value = "/logout.ajax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ? extends Object> logout(HttpServletRequest request, HttpServletResponse response) {

		try {

			loginService.logout(getSession());
			CookieUtil.delCookie(request, response, TICKET_COOKIE_NAME);
			CookieUtil.delCookie(request, response, COOKIE_VALID_USERNAME);
			CookieUtil.delCookie(request, response, COOKIE_VALID_PASSWD);
			return success("用户退出成功");
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return fail(e.getMessage());
		}
	}

	@RequestMapping(value = "/readContractAgreement.ajax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ? extends Object> readContractAgreement() {
		try {
			Staff staff = (Staff) getSession().getAttribute(LOGIN_STAFF_TMP);

			if (staff == null) {
				throw new Exception("用户没有登录");
			}
			StaffExtendProperty staffExtendProperty = loginService.setReadContractAgreement(staff);
			LOG.debug("用户[" + staff.toString() + "]同意了合作协议");
			getSession().setAttribute(LoginService.LOGIN_STAFF, staff);
			getSession().removeAttribute(LOGIN_STAFF_TMP);
			return success(staffExtendProperty);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return fail(e.getMessage());
		}
	}

	@RequestMapping(value = "/logintest.ajax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ? extends Object> logintest() {

		try {

			Staff staff = StaffUtil.getLoginStaff();

			if (staff == null) {
				throw new Exception("用户没有登录");
			}

			return success("用户[" + staff.getLoginName() + "]已经登录");
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return fail(e.getMessage());
		}
	}

}
