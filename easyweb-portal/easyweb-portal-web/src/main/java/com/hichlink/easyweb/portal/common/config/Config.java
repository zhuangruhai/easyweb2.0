package com.hichlink.easyweb.portal.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
@Service
@ConfigurationProperties(prefix = "sys")
public class Config {
	private String title;
	private String isOldPasswordSupport;
	private String isCheckCodeOn;

	
	public String getIsOldPasswordSupport() {
		return isOldPasswordSupport;
	}

	public void setIsOldPasswordSupport(String isOldPasswordSupport) {
		this.isOldPasswordSupport = isOldPasswordSupport;
	}

	public String getIsCheckCodeOn() {
		return isCheckCodeOn;
	}

	public void setIsCheckCodeOn(String isCheckCodeOn) {
		this.isCheckCodeOn = isCheckCodeOn;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 得到登陆前后首页各省定制的系统名称展示的title
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	public boolean isOldPasswordSupport() {
		String support = isOldPasswordSupport;
		return "on".equalsIgnoreCase(support);
	}

	public boolean isCheckCodeOn() {
		String support = isCheckCodeOn;
		return "true".equalsIgnoreCase(support);
	}

	public boolean isContractAgreementOn() {
		return false;
	}

	public boolean isRegisterOn() {
		return false;
	}

	public String getRegisterUrl() {
		return "";
	}

	public boolean isForgotpwdOn() {
		return false;
	}

	public String getForgotpwdUrl() {
		return "";
	}

}
