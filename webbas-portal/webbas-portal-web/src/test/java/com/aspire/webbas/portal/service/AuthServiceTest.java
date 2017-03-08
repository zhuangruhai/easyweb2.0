package com.aspire.webbas.portal.service;

import java.util.Map;

import net.sf.json.JSONObject;

import com.aspire.webbas.portal.common.authapi.AuthResult;
import com.aspire.webbas.portal.common.service.AuthService;
import com.aspire.webbas.test.BaseTest;

public class AuthServiceTest extends BaseTest{

	
	private AuthService service;
	
	public void test(){
		
		service = (AuthService)getBean("authService");
		
		assertNotNull("authService is null", service);
		
//		authAddressByLoginName();
		
//		authorize();
		
		authExclude();
		
//		listAddressUrlByLoginName();
		
//		toJSONStr();
	}
	
	public void authAddressByLoginName(){
		String loginName = "admin";
		String url1 = "/auth/findDepartmentWithPath.ajax?p=v";
		String url2 = "/auth/findDepartmentWithPath.ajax?param=value";
		String url3 = "/auth/findDepartmentWithPath.ajax?param=value&p=v1";
		String url4 = "/test/post-test.html";
		

		try {
			Map<String,String> urlMap = service.listAddressUrlByLoginName(loginName);
			
			if(service.authorizeSuccess(url1, urlMap)){
				System.out.println("auth[url1="+url1+"] success.");
			}else{
				System.out.println("auth[url1="+url1+"] fail.");
			}
			
			if(service.authorizeSuccess(url2, urlMap)){
				System.out.println("auth[url2="+url2+"] success.");
			}else{
				System.out.println("auth[url2="+url2+"] fail.");
			}
			
			if(service.authorizeSuccess(url3, urlMap)){
				System.out.println("auth[url3="+url3+"] success.");
			}else{
				System.out.println("auth[url3="+url3+"] fail.");
			}
			
			if(service.authorizeSuccess(url4, urlMap)){
				System.out.println("auth[url4="+url4+"] success.");
			}else{
				System.out.println("auth[url4="+url4+"] fail.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void authorize(){
		Long staffId = new Long(-999);
		String url1 = "/pages/auth/role.shtml";
		String url2 = "/auth/findDepartmentWithPath.ajax?param=value";
		String url3 = "/auth/findDepartmentWithPath.ajax?param=value&p=v1";
		String url4 = "/test/post-test.html";
		
		long start = System.currentTimeMillis();
		
		if(service.authorize(staffId, url1)){
			System.out.println("auth[url1="+url1+"] success.");
		}else{
			System.out.println("auth[url1="+url1+"] fail.");
		}
		
		if(service.authorize(staffId, url2)){
			System.out.println("auth[url2="+url2+"] success.");
		}else{
			System.out.println("auth[url2="+url2+"] fail.");
		}
		
		if(service.authorize(staffId, url3)){
			System.out.println("auth[url3="+url3+"] success.");
		}else{
			System.out.println("auth[url3="+url3+"] fail.");
		}
		
		if(service.authorize(staffId, url4)){
			System.out.println("auth[url4="+url4+"] success.");
		}else{
			System.out.println("auth[url4="+url4+"] fail.");
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("auth cost : " + (end - start) + "毫秒");
	}
	
	public void authExclude(){
		String url = "/pages/login.shtml";
		
		if(service.authExclude(url)){
			System.out.println("auth[url="+url+"] success.");
		}else{
			System.out.println("auth[url="+url+"] fail.");
		}
//		
//		Map<String,String> excludeAuthMap = AuthCache.getCache().getUnauthMap();
//		
//		for(String s : excludeAuthMap.keySet()){
//			System.out.println(s + " : " + excludeAuthMap.get(s));
//		}
	}
	
	public void listAddressUrlByLoginName(){
		String loginName = "admin";
		
		try {
			Map<String,String> urlMap = service.listAddressUrlByLoginName(loginName);
			
			for(String s : urlMap.keySet()){
				System.out.println(s + " : " + urlMap.get(s));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void toJSONStr(){
		AuthResult result = new AuthResult(AuthResult.INVALID,"不行", "url");
		
		
		System.out.println(JSONObject.fromObject(result).toString());
		
	}
}
