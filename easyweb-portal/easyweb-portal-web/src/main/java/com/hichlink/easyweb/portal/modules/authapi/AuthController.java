package com.hichlink.easyweb.portal.modules.authapi;

import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hichlink.easyweb.core.web.BaseController;
import com.hichlink.easyweb.portal.common.entity.Staff;
import com.hichlink.easyweb.portal.common.service.AuthService;
import com.hichlink.easyweb.portal.common.util.CookieUtil;
import com.hichlink.easyweb.portal.common.util.StaffUtil;

/**
 * 
 * <b>Title：</b>AuthController.java<br/>
 * <b>Description：</b> 页面组件签权<br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2014年8月23日 下午10:25:33<br/>
 * <b>Copyright (c) 2014 ASPire Tech.</b>
 * 
 */
@Controller
@RequestMapping("/auth")
public class AuthController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	@Autowired
	@Qualifier("authService")
	private AuthService authService;

	@RequestMapping(value = "/pageAuth.ajax")
	@ResponseBody
	public void pageAuth(HttpServletRequest request,
			HttpServletResponse response, String resKeys, String operKeys)
			throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		try {
			Staff staff = StaffUtil.getLoginStaff();
			if (staff == null) {
				throw new RuntimeException("用户未登录,无法得到登录用户信息");
			}
			checkArgumentValid(resKeys, operKeys);
			modelMap.put(
					"data",
					authService.authorize(staff.getStaffId(),
							resKeys.split(","), operKeys.split(",")));
			modelMap.put("success", true);
		} catch (Exception e) {
			logger.error("签权出错", e);
			modelMap.put("success", false);
			modelMap.put("data", e.getMessage());
		}
		super.outputMatch(request, response, JSON.toJSONString(modelMap));
	}


	@RequestMapping(value = "/ifmStyle.ajax")
	public void ifmStyle(HttpServletRequest request,
			HttpServletResponse response,IfmStyle ifmStyle,String rtnName,String ticket) throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		try {
			IfmStyle style = null;
			if (isNotEmpty(rtnName)){
				String ifmStyleStr = CookieUtil.getCookie(request,"admin-ifm-style"); 
				if (isNotEmpty(ifmStyleStr)){
					ifmStyleStr = URLDecoder.decode(ifmStyleStr, "UTF-8");
					String[] ifmStyleStrs = ifmStyleStr.split("&");
					style = new IfmStyle();
					for (String param : ifmStyleStrs){
						String[] params = param.split("=");
						if (params.length == 2){
							Field field = style.getClass().getDeclaredField(params[0]);
							if (null != field){
								field.setAccessible(true);
								field.set(field, params[1]);
							}
						}
					}
				}
			}
			modelMap.put("data", style);
			modelMap.put("success", true);
		} catch (Exception e) {
			logger.error("获取页面admin-ifm-style出错", e);
			modelMap.put("success", false);
			modelMap.put("data", null);
		}
		super.outputMatch(request, response, JSON.toJSONString(modelMap));
	}

	private void checkArgumentValid(String resKeys, String operKeys) {
		if (isEmpty(resKeys) || isEmpty(operKeys)) {
			throw new IllegalArgumentException("参数resKeys,operKeys不能为空");
		}
		String[] resKeysArray = resKeys.split(",");
		String[] operKeysArray = operKeys.split(",");
		if (resKeysArray.length == 0
				|| resKeysArray.length != operKeysArray.length) {
			throw new IllegalArgumentException("resKey,operKey不能为空并且必须成对出现");
		}
	}
}
