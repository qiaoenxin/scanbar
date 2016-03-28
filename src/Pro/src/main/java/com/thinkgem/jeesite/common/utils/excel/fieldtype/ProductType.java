/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.thinkgem.jeesite.common.utils.excel.fieldtype;

import com.thinkgem.jeesite.modules.pro.entity.Product;

/**
 * 字段类型转换
 * @author ThinkGem
 * @version 2013-03-10
 */
public class ProductType {

	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		return null;
	}

	/**
	 * 获取对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((Product)val).getSerialNum() != null){
			return ((Product)val).getSerialNum();
		}
		return "";
	}
}
