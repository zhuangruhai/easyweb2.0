package com.hichlink.easyweb.portal.common.service;

import java.util.List;
import java.util.Map;

public interface AuthService {

	Map<String,String> listAddressUrlByLoginName(String loginName) throws Exception;
	
	boolean authorizeSuccess(String addressUrl, Map<String,String> urlMap);
	
	boolean authorizeFail(String addressUrl, Map<String,String> urlMap);
	
	boolean authExclude(String addressUrl);
	
	boolean notNeedAuthAfterLogin(String addressUrl);
	
	boolean authorize(Long staffId, String url);
	
	List<Map<String,Boolean>> authorize(Long staffId, String[] resKeys,String[] operKeys);
}
