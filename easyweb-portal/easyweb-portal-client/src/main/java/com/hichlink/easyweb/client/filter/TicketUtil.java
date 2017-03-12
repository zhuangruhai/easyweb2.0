package com.hichlink.easyweb.client.filter;

import javax.servlet.ServletException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * 
 * <pre>
 * <b>Title：</b>TicketUtil.java<br/>
 * <b>Description：</b>Ticket操作工具类<br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2014年8月12日 下午2:14:08<br/>  
 * <b>Copyright (c) 2014 ASPire Tech.</b>   
 *  </pre>
 */
public class TicketUtil {
	public static final String AUTH_TICKET = "AUTH_TICKET";

	public static final String SESSION_TICKET = "SESSION_TICKET";
	public static final String TICKET_FLAG =  "ticket";
	public static String findTicket(HttpServletRequest request) {
		String ticket = request.getParameter(TICKET_FLAG);

		if (StringTools.isEmptyString(ticket)) {
			ticket = findTicketFromCookie(request);
		}

		if (StringTools.isEmptyString(ticket)) {
			ticket = findTicketFromSession(request);
		}
		if (StringTools.isEmptyString(ticket)) {
			ticket = findTicketFromReferer(request);
		}
		return ticket;
	}
	public static String findTicketFromReferer(HttpServletRequest request) {
		String referer = request.getHeader("referer");
		if (StringTools.isEmptyString(referer)){
			return "";
		}
		String[] urls = referer.split("\\?");
		if (urls.length < 2){
			return "";
		}
		String[] params = urls[1].split("&");
		for (String param : params){
			String[] p = param.split("=");
			if (p.length == 2 && TICKET_FLAG.equals(p[0])){
				return p[1];
			}
		}
		return "";
	}
	public static String findTicketFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession(true);

		return (String) session.getAttribute(SESSION_TICKET);
	}

	public static String findTicketFromCookie(HttpServletRequest req) {
		Cookie[] cookies = req.getCookies();

		if (cookies == null) {
			return null;
		}

		for (Cookie c : cookies) {
			if (AUTH_TICKET.equalsIgnoreCase(c.getName())) {
				return c.getValue();
			}
		}
		return null;
	}
	public static void sendPrivacyCookie(HttpServletRequest request, HttpServletResponse response, String ticket)
		      throws ServletException {
				
			Cookie privacy = new Cookie(AUTH_TICKET, ticket);
//			privacy.setSecure(true);
			privacy.setMaxAge(-1);
			privacy.setPath("/");
			response.addCookie(privacy);
				
	}
}
