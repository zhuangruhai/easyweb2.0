package com.hichlink.easyweb.portal.common.cache;

import java.util.List;
import java.util.Map;

import com.hichlink.easyweb.portal.common.entity.OperationAddress;

public class AuthCache {

	private static AuthCache cache;

	/**
	 * 存放不需要鉴权地址资源map.
	 */
	private Map<String, String> unauthMap = null;

	/**
	 * 存放只需要登录鉴权地址资源map.
	 */
	private Map<String, String> loginAuthMap = null;

	/**
	 * 
	 */
	private Map<String, List<OperationAddress>> authMap = null;

	public Map<String, String> getUnauthMap() {
		return unauthMap;
	}

	public Map<String, String> getLoginAuthMap() {
		return loginAuthMap;
	}

	// public void setLoginAuthMap(Map<String, String> loginAuthMap) {
	// this.loginAuthMap = loginAuthMap;
	// }

	public Map<String, List<OperationAddress>> getAuthMap() {
		return authMap;
	}

	// public void setAuthMap(Map<String, String> authMap) {
	// this.authMap = authMap;
	// }

	private AuthCache() {

	}

	public static AuthCache getCache() {
		if (cache == null) {
			cache = new AuthCache();
		}

		return cache;
	}

	public void init(Map<String, String> unauth, Map<String, String> loginAuth,
			Map<String, List<OperationAddress>> auth) {
		unauthMap = unauth;
		loginAuthMap = loginAuth;
		authMap = auth;
	}

}
