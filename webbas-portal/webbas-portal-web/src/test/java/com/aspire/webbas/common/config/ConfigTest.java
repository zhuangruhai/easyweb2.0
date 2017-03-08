package com.aspire.webbas.common.config;

import org.apache.commons.configuration.Configuration;

import com.aspire.webbas.configuration.config.ConfigurationHelper;
import com.aspire.webbas.portal.common.config.Config;
import com.aspire.webbas.test.BaseTest;

public class ConfigTest extends BaseTest{

	public void test(){
		String fileName = "C:\\hyb\\dev\\apache-tomcat-7.0.33\\bin\\config\\webbas.xml";
		
//		 XMLConfiguration xmlConfiguration = new XMLConfiguration();
//		 
//		
//		 try {
//			xmlConfiguration.load(fileName);
//			
//			System.out.println("ok");
//		} catch (ConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
//		System.out.println(System.getProperty("user.dir"));
		
//		readConfig(fileName);
		
		
//		readConfig();
		
	}
	
	public void readConfig(){
		ConfigurationHelper.setBasePath("C:\\hyb\\dev\\apache-tomcat-7.0.33\\bin\\config");
		
		System.out.println(Config.getInstance().getTitle());
		
		System.out.println(Config.getInstance().isOldPasswordSupport());
	}
	
	public void readConfig(String fileName){
		Configuration config = ConfigurationHelper.getConfiguration(fileName,50000);
		
		System.out.println(config.getString("checkCode"));
		
		System.out.println(config.getString("smsVarifyLength"));
		
		System.out.println(config.getString("portal.title"));
	}
}
