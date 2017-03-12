package com.hichlink.easyweb.portal.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hichlink.easyweb.portal.common.config.Config;
import com.hichlink.easyweb.portal.common.entity.Staff;
@Service
public class PasswordAdapter {
	@Autowired
	private Config config;

	public String encryptPassword(Staff staff) {

		// 如果兼容老WEBBAS的加密算法，需要用staffId作为key进行加密
		if (config.isOldPasswordSupport()) {
			return PasswordUtil.buildPassword(staff.getStaffId().toString(), staff.getPassword());
		} else {
			// 否则采用loginName作为key进行加密
			return PasswordUtil.buildPassword(staff.getLoginName(), staff.getPassword());
		}
	}
}
