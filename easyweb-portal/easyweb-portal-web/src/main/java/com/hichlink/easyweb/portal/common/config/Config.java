package com.hichlink.easyweb.portal.common.config;

import org.springframework.beans.factory.annotation.Value;

public class Config {
	@Value("${sys.title}")
	private String title;
	@Value("${sys.isOldPasswordSupport}")
	private String isOldPasswordSupport;
	@Value("${sys.isCheckCodeOn}")
	private String isCheckCodeOn;
	private static Config config = null;

	private Config() {
	}

	public static Config getInstance() {
		if (null == config) {
			config = new Config();
		}
		return config;
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
