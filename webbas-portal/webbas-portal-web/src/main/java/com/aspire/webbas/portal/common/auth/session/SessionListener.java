package com.aspire.webbas.portal.common.auth.session;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {


    public void sessionCreated(HttpSessionEvent event) {
    	System.out.println("SessionListener:保存session【"+event.getSession().getId()+"】到context中。");
    	
    	SessionContext.getContext().addSession(event.getSession());
    }

    public void sessionDestroyed(HttpSessionEvent event) {
    	System.out.println("SessionListener:从context中删除session【"+event.getSession().getId()+"】。");

    	SessionContext.getContext().deleteSession(event.getSession().getId());
    }
}