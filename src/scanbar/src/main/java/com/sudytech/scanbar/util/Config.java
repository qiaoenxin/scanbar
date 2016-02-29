package com.sudytech.scanbar.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.MapPropertySource;

public class Config {

	public static String getProperty(String name){
		ApplicationContext context = SpringContextHolder.getContext();
		PropertySourcesPlaceholderConfigurer configurer =  (PropertySourcesPlaceholderConfigurer) context.getBean("propertyPlaceholderConfigurer");
		MapPropertySource mapPropertySource = (MapPropertySource) configurer.getAppliedPropertySources().get("localProperties");
		return (String) mapPropertySource.getProperty(name);
	}
}
