package com.aspire.webbas.client;

import javax.servlet.http.HttpServletRequest;


import com.aspire.webbas.client.filter.TicketUtil;
import com.aspire.webbas.portal.common.entity.Staff;

/**
 * 
 * <pre>
 * <b>Title：</b>AuthStaffUtil.java<br/>
 * <b>Description：</b>当前登录用户信息工具类<br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2014年8月12日 下午2:23:07<br/>  
 * <b>Copyright (c) 2014 ASPire Tech.</b>
 * </pre>
 */
public class AuthStaffUtil {
	/**
	 * 获取当前登录用户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Staff getLoginStaff(HttpServletRequest request){
		String ticket = TicketUtil.findTicket(request);
		Staff staff = (Staff) request.getSession().getAttribute(ticket);
		if (null == staff) {
			staff = AuthProxy.getProxy().auth(ticket);
			if (null == staff) {
				return null;
			} else {
				request.getSession().setAttribute(ticket, staff);
			}
		}
		return staff;
	}

	/**
	 * 获取当前登录用户loginName
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getLoginName(HttpServletRequest request){
		Staff staff = getLoginStaff(request);
		if (null == staff)return null;
		return staff.getLoginName();
	}

	/**
	 * 获取当前登录用户staffId
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Long getStaffId(HttpServletRequest request){
		Staff staff = getLoginStaff(request);
		if (null == staff)return null;
		return staff.getStaffId();
	}
}
