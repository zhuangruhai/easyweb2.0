package com.hichlink.easyweb.portal.common.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.hichlink.easyweb.portal.common.entity.Staff;
import com.hichlink.easyweb.portal.common.entity.StaffExtendProperty;

public interface LoginService {

	  /**
     * 登录用户名.
     */
    public static final String LOGIN_NAME = "LOGIN_NAME";
    
    /**
     * 登录用户名.
     */
    public static final String REAL_NAME = "REAL_NAME";
   
    /**
     * 登录用户信息.
     */
    public static final String LOGIN_STAFF = "LOGIN_STAFF";
    
	void login(String name, String password, HttpSession session) throws Exception;
	
	void logout(HttpSession session) throws Exception;
	
	boolean isReadContractAgreement(List<StaffExtendProperty> staffExtendProperties);
	
	StaffExtendProperty setReadContractAgreement(Staff staff);
}
