package com.hichlink.easyweb.portal;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(exclude = MybatisAutoConfiguration.class)
@ServletComponentScan
public class Application{

	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		long statTime = System.currentTimeMillis();
		LOG.info("开始启动Web服务~~~~~~~~~~~~~~~~");
		SpringApplication.run(Application.class, args);
		long time = System.currentTimeMillis() - statTime;
		LOG.info("Web服务启动完成,消耗时间：" + time + "毫秒");
	}
	
}
