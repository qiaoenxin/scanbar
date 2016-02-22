package com.sudytech.scanbar.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


@Service
@Lazy(value=false)
public class SpringContextHolder implements ApplicationContextAware{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringContextHolder.class);
	
	private static ApplicationContext context;
	

	public static ApplicationContext getContext(){
		if(context == null){
			LOGGER.error("Spring context 没有注入。");
		}
		return context;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
	}

}
