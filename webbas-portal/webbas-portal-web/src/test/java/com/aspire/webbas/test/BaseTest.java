package com.aspire.webbas.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseTest extends TestCase{

	private static ApplicationContext applicationContext = null;
	
	public void test(){
		
	}

	public Object getBean(String beanName){
		if(applicationContext == null){
			 applicationContext = 
					new ClassPathXmlApplicationContext("applicationContext.xml");
		}
		
		
		return applicationContext.getBean(beanName);
	}
	
	
	public void toJson(Object obj){
		  JsonGenerator jsonGenerator = null;
		  ObjectMapper objectMapper = null;

		  Map<String, Object> map = new HashMap<String,Object>();
		  
		  map.put("data", obj);
		  map.put("success", true);
		  
		  objectMapper = new ObjectMapper();
	        try {
	            jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
	        
	            
	            System.out.println("json object ---->");
	            jsonGenerator.writeObject(map);
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
}
