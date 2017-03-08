package com.aspire.webbas.portal.common.auth.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

public class SessionContext {
	
	public static String LOGIN_STAFF = "LOGIN_STAFF";
	private static Map<String,HttpSession> sessionMaps = new ConcurrentHashMap<String,HttpSession>();
	
	private static SessionContext context;
	
	private SessionContext(){
		
	}
	
	public synchronized static SessionContext getContext(){
		if(context == null){
			context = new SessionContext();
		}
		return context;
	}
	
	public void addSession(HttpSession session){
		sessionMaps.put(session.getId(), session);
	}
	
	public void deleteSession(String sessionId){
		sessionMaps.remove(sessionId);
	}
	
	public HttpSession getSession(String sessionId){
		return sessionMaps.get(sessionId);
	}
	
//	public Staff getLoginStaff(String sessionId){
//		
//		if(!sessionMaps.containsKey(sessionId)){
//			return null;
//		}
//		
//		return (Staff)sessionMaps.get(sessionId).getAttribute(LOGIN_STAFF);
//	}
}