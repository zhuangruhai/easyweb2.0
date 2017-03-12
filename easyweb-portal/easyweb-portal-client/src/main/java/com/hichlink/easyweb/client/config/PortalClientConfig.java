package com.hichlink.easyweb.client.config;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hichlink.easyweb.configuration.config.ConfigurationHelper;

public class PortalClientConfig {

	/**
     * logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(PortalClientConfig.class);
	/**
     * 配置文件接口.
     */
    private static Configuration configuration = null;
    
    /**
     * 门户配置对象.
     */
    private static PortalClientConfig config;

    /**
     * 缺省配置文件名称.
     */
    private static final String DEFAULT_CONFIGURATION_FILENAME = "portalclient/portalclient.xml";
    
    /**
     * 配置文件名.
     */
    private static String configurationFileName = DEFAULT_CONFIGURATION_FILENAME;
    
    /**
     * 私有构造方法.
     * @param configurationFileName 配置文件相对路径
     */
    private PortalClientConfig() {
        if (configuration == null) {
            refresh();
        }
    }
    
    /*
    **
    * 获取AJAX服务端配置对象.
    * @return AJAX服务端配置对象
    */
   public static PortalClientConfig getInstance() {
       if (config == null) {
           config = new PortalClientConfig();
       }
       return config;
   }
   
   /**
    * 刷新配置文件.
    */
   private static void refresh() {
       configuration = ConfigurationHelper.getConfiguration(configurationFileName,50000);
       if (configuration == null) {
           LOG.error("读portal配置文件失败, 配置文件：" + configurationFileName);
       }
   }
   
   /**
    * 得到登陆前后首页各省定制的系统名称展示的title
    * @return
    */
   public String getDomain() { 
       return getString("portal.domain");
   }
   
   public String getOutDomain() { 
       return getString("portal.out-domain");
   }
   
   public String getPortalServiceUrl() { 
       return getString("portal.portalservice-url");
   }
   public String getPortalAuthUrl() { 
       return getDomain() + getPortalServiceUrl();
   }
   private String getString(String arg){
	   if (configuration == null ) {
           return null;
       }
	   return configuration.getString(arg);
   }
}
