package com.aspire.webbas.portal.common.util;

import com.aspire.webbas.portal.common.config.Config;
import com.aspire.webbas.portal.common.entity.Staff;

public class PasswordAdapter {

	private Staff staff;
	
	public PasswordAdapter(Staff s){
		this.staff = s;
	}
	
	public String encryptPassword(){
		
		// 如果兼容老WEBBAS的加密算法，需要用staffId作为key进行加密
		if( Config.getInstance().isOldPasswordSupport() ){
			return PasswordUtil.buildPassword(staff.getStaffId().toString(), staff.getPassword());
		}else{
		// 否则采用loginName作为key进行加密
			return PasswordUtil.buildPassword(staff.getLoginName(), staff.getPassword());
		}
	}
}
