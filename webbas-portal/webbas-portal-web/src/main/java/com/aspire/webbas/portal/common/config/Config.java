package com.aspire.webbas.portal.common.config;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspire.webbas.configuration.config.ConfigurationHelper;


public class Config {

	/**
     * logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(Config.class);
	/**
     * 配置文件接口.
     */
    private static Configuration configuration = null;
    
    /**
     * 门户配置对象.
     */
    private static Config config;

    /**
     * 缺省配置文件名称.
     */
    private static final String DEFAULT_CONFIGURATION_FILENAME = "webbas.xml";
    
    /**
     * 配置文件名.
     */
    private static String configurationFileName = DEFAULT_CONFIGURATION_FILENAME;
    
    /**
     * 私有构造方法.
     * @param configurationFileName 配置文件相对路径
     */
    private Config() {
        if (configuration == null) {
            refresh();
        }
    }
    
    /*
    **
    * 获取AJAX服务端配置对象.
    * @return AJAX服务端配置对象
    */
   public static Config getInstance() {
       if (config == null) {
           config = new Config();
       }
       return config;
   }
   
   /**
    * 刷新配置文件.
    */
   private static void refresh() {
       configuration = ConfigurationHelper.getConfiguration(configurationFileName,50000);
       if (configuration == null) {
           logger.error("读portal配置文件失败, 配置文件：" + configurationFileName);
       }
   }
   
   /**
    * 得到登陆前后首页各省定制的系统名称展示的title
    * @return
    */
   public String getTitle() { 
       return getString("portal.title");
   }
   
   public boolean isOldPasswordSupport(){
	   String support = getString("portal.old-password-crypt-support", "off");
	   
	   return "on".equalsIgnoreCase(support);
   }
   public boolean isCheckCodeOn(){
	   String support = getString("portal.checkCode", "false");
	   return "true".equalsIgnoreCase(support);
   }
   public boolean isContractAgreementOn(){
	   String support = getString("portal.contractAgreement", "false");
	   return "true".equalsIgnoreCase(support);
   }
   public boolean isRegisterOn(){
	   String support = getString("portal.is-register-on", "false");
	   return "true".equalsIgnoreCase(support);
   }
   public String getRegisterUrl(){
	   return getString("portal.register-url");
   }
   public boolean isForgotpwdOn(){
	   String support = getString("portal.is-forgotpwd-on", "false");
	   return "true".equalsIgnoreCase(support);
   }
   public String getForgotpwdUrl(){
	   return getString("portal.forgotpwd-url");
   }
   private String getString(String arg){
	   if (configuration == null ) {
           return null;
       }
	   
	   return configuration.getString(arg);
   }
   
   private String getString(String arg, String def){
	   if (configuration == null ) {
           return def;
       }
	   
	   return configuration.getString(arg, def);
   }
}
