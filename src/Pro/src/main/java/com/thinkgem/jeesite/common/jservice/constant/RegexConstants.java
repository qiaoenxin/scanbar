package com.thinkgem.jeesite.common.jservice.constant;

public interface RegexConstants {
	/**
	 * 1—99的数字
	 */
	String SHORT_NUM99 = "[1-9]\\d{0,1}";
	
	/**
	 * 非零数字
	 */
	String NUMBER = "[1-9]\\d*";
	
	/**
	 * 布尔类型
	 */
	String BOOLEAN = "(true)|(false)";
	
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	String SIMPLE_DATE="\\d{1,4}-[0-1]\\d-[0-3]\\d [0-2]\\d:[0-5]\\d:[0-5]\\d";
}
