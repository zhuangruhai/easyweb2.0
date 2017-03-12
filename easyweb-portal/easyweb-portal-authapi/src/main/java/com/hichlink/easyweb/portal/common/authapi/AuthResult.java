package com.hichlink.easyweb.portal.common.authapi;

import java.io.Serializable;

public class AuthResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4078036728685021925L;
	public static String SUCCESS   = "0";
	public static String FAIL      = "1";
	public static String INVALID   = "2";
	public static String NOT_LOGIN = "3";
	
	private String returnCode;
	
	private String message;
	
	private String redirectUrl;

	public AuthResult(){
		
	}
	
	public AuthResult(String code, String message, String url){
		this.returnCode = code;
		this.message = message;
		this.redirectUrl = url;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
