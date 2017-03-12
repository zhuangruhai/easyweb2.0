package com.hichlink.easyweb.portal.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hichlink.easyweb.portal.common.auth.session.SessionContext;
import com.hichlink.easyweb.portal.common.entity.Staff;
import com.hichlink.easyweb.portal.common.service.LoginService;

/**
 * 
 * <b>Title：</b>StaffUtil.java<br/>
 * <b>Description：</b> 登录用户信息工具类<br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2014年3月11日 下午4:32:13<br/>
 * <b>Copyright (c) 2014 ASPire Tech.</b>
 * 
 */
public class StaffUtil {

    private static HttpServletRequest getRequest() {
        if (null != RequestContextHolder.getRequestAttributes()) {
            return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        } else {
            return null;
        }
    }

    private static HttpSession getSession() {
        return getRequest().getSession(true);
    }

    /**
     * 获得登录的帐号
     * 
     * @return
     * @throws Exception 
     */
    public static String getLoginUserName() throws Exception {
        Staff staff = getLoginStaff();
        if (null != staff) {
            return staff.getLoginName();
        } else {
            return "";
        }
    }

    public static Staff getLoginStaff() throws Exception {
    	
    	HttpSession session = SessionContext.getContext().getSession(getSession().getId());
    	
    	if(session == null){
    		throw new Exception("用户没有登录");
    	}
    	
        Staff staff = (Staff)session.getAttribute(LoginService.LOGIN_STAFF);
        
        if (null == staff){
        	throw new Exception("用户没有登录");
        }
        return staff;
    }
    
    public static void addLoginSatff(HttpSession session, Staff newStaff){
    	HttpSession se = SessionContext.getContext().getSession(getSession().getId());
    	
    	if(se == null){
    		session.setAttribute(LoginService.LOGIN_STAFF, newStaff);
    		SessionContext.getContext().addSession(session);
    	}else{
    		se.setAttribute(LoginService.LOGIN_STAFF, newStaff);
    	}
    }
    
    public static void updateLoginStaff(Staff newStaff){
    	HttpSession session = SessionContext.getContext().getSession(getSession().getId());
    	
    	if(session == null)
    		return;
    	
    	Staff staff = (Staff)session.getAttribute(LoginService.LOGIN_STAFF);
    	
    	if(staff != null && newStaff != null){
    		if(staff.getStaffId().equals(newStaff.getStaffId())){
    			getSession().setAttribute(LoginService.LOGIN_STAFF, newStaff);
    		}
    	}
    }
}
