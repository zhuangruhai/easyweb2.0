package com.hichlink.easyweb.portal.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.caucho.hessian.server.HessianServlet;
@Configuration
public class WebConfig {
	private static Logger LOG = LoggerFactory.getLogger(WebConfig.class);
	@Bean
	public ServletRegistrationBean statViewServlet() {
	    ServletRegistrationBean reg = new ServletRegistrationBean();
	    reg.setServlet(new HessianServlet());
	    reg.addInitParameter("service-class", "com.hichlink.easyweb.portal.modules.authapi.PortalClientImpl");
	    reg.addUrlMappings("/auth");
	    LOG.debug("web.xml配置成功{}",reg.toString());
	    return reg;
	}
	
}
