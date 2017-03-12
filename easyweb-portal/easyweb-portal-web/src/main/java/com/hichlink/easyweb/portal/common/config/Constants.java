package com.hichlink.easyweb.portal.common.config;

public class Constants {
	private Constants() {

	}

	public static final String TICKET_COOKIE_NAME = "AUTH_TICKET";
	public static final String COOKIE_VALID_USERNAME = "username";
	public static final String COOKIE_VALID_PASSWD = "token";
	public static final int COOKIE_VALID_MAXAGE = 3 * 24 * 60 * 60;
}
