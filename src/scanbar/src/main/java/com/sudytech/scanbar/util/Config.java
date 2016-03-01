package com.sudytech.scanbar.util;

import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.MapPropertySource;
import org.springframework.web.context.WebApplicationContext;

public class Config {
	
	
	private static Map<String, Object> properties;
	
	private static final String PATH = "/admin/";
	
	public static void init(ApplicationContext context){
		
		PropertySourcesPlaceholderConfigurer configurer =  (PropertySourcesPlaceholderConfigurer) context.getBean("propertyPlaceholderConfigurer");
		MapPropertySource mapPropertySource = (MapPropertySource) configurer.getAppliedPropertySources().get("localProperties");
		properties = mapPropertySource.getSource();
		
		ServletContext webContext = ((WebApplicationContext) context).getServletContext();
		String contextPath = webContext.getContextPath() + PATH;
		webContext.setAttribute("adminPath", contextPath);;
	}
	
	public static String getProperty(String name){
		return (String) properties.get(name);
	}
	
	public static String getPath(){
		return PATH;
	}
		
}
