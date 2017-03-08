/**
 * @(#) DefaultInitListener.java Created on 2012-11-22
 *
 * Copyright (c) 2012 Aspire. All Rights Reserved
 */
package com.aspire.webbas.portal.common.listener;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;

import com.aspire.webbas.configuration.config.ConfigurationHelper;

/**
 * 配置上下文信息初始监听及初始化日志上下文设置 <code>DefaultInitListener</code>
 *
 * @version 1.0
 * @author lixd / lixuandong@aspirecn.com
*/
public class DefaultInitListener implements ServletContextListener  {
	/**
	 * logger
	 */
	private final static Logger logger = LoggerFactory.getLogger(DefaultInitListener.class);
    /**
     * application root key
     */
    private static final String APP_ROOT_KEY = "APP_ROOT";
    /**
     * LOG root key
     */
    private static final String LOG_ROOT = "logbase.dir";
    /**
     * config root key
     */
    private static final String CONFIG_ROOT = "configPath";
    /**
     * application root
     */
    private String appRootPath;
    
    public void contextInitialized(ServletContextEvent event) {
        ServletContext sc = event.getServletContext();
        logger.info("Initializing Web Application Context【" + sc.getRealPath("/") + "】.");
        System.setProperty(APP_ROOT_KEY, appRootPath = sc.getRealPath("/"));
        if(System.getProperty(APP_ROOT_KEY)==null){
            System.setProperty(APP_ROOT_KEY, sc.getRealPath("/"));
            logger.info("Initializing Web Application Context[" + System.getProperty(APP_ROOT_KEY) + "].");
        }
        initRootConfigPath(sc);
        intiLogBack(sc);
    }

    /**
     * 初始化配置文件根目录.
     */
    private void initRootConfigPath(ServletContext sc) {
        logger.info("设置系统配置文件根目录....");
        // 初始化配置文件根目录
        /**********************************
         *  常用服务器缺省存放配置文件根目录：
         *  TOMCAT：$TOMCAT_HOME/bin/config
         *  OC4J:   $OC4J_HOME/j2ee/home/sims/config
         **********************************/
        //首先从系统属性中读取
        String configPath = System.getProperty(CONFIG_ROOT);
        if(StringUtils.isEmpty(configPath)){
            configPath = getUserDir() + "/config";
        }
        System.setProperty(CONFIG_ROOT,configPath);
        ConfigurationHelper.setBasePath(configPath);
        
        logger.info("done! 配置文件根目录:" + configPath);
    }   

    /**
     * 得到user.dir
     * @return user.dir
     */
    private String getUserDir() {
        return System.getProperty("user.dir");
    }
    
    /**
     * 初始化日志输出文件根目录
     * @param sc
     */
    private void intiLogBack(ServletContext sc){
        //首先从系统属性中读取
        String logRoot = System.getProperty(LOG_ROOT);
        //再从web 上下文中读取
        if(StringUtils.isEmpty(logRoot)){
            logRoot = sc.getInitParameter(LOG_ROOT);
        }
        //默认
        if(StringUtils.isEmpty(logRoot)){
            logRoot = getUserDir();
        }
        System.setProperty(LOG_ROOT, logRoot);
        logger.info("Set the system Log file root directory is successful, the Log directory is:【{}】",logRoot);
        
        String logbackFile = "";
        try {
            logbackFile = ConfigurationHelper.getBasePath() + "/logback/logback.xml";
            if (logbackFile != null && !logbackFile.isEmpty()) {
                LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory();
                lc.reset();
                JoranConfigurator configurator = new JoranConfigurator();
                configurator.setContext(lc);
                configurator.doConfigure(logbackFile);
                logger.info("Loaded logback configure file from {}.", logbackFile);
            } else {
                logger.info("No logback configuration location specified, keepping default.");
            }
        } catch (Exception e) {
            logger.error("Fail to loading logback configuration from " + logbackFile + ", keepping default.", e);  
        }
    }
    

    public void contextDestroyed(ServletContextEvent event) {
        logger.info("Web Application Context【" + appRootPath + "】 Destroyed.");
    } 
}
