package com.thinkgem.jeesite.common.jservice.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数定义
 * @author qex
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParameterDef {
	/**
	 * 正则表达
	 * @return
	 */
	String regex() default "";
	
	/**
	 * 是否必选
	 * @return
	 */
	boolean required() default false;
	
	/**
	 * 默认参数值
	 * @return
	 */
	String defaultValue() default "";
	
	
	/**
	 * 长度
	 * @return
	 */
	int maxLength() default 0;
	
		
	/**
	 * 参数名称
	 * @return
	 */
	String name() default "";
	
	
	/**
	 * 是否忽略
	 * @return
	 */
	boolean ignore() default false;
}
