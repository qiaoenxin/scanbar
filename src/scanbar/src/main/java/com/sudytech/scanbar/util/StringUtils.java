package com.sudytech.scanbar.util;


public class StringUtils  {

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}
	
	public static int toInt(String value, int def){
		if(isEmpty(value)){
			return def;
		}
		try {
			return Integer.valueOf(value);
		} catch (NumberFormatException e) {
		}
		return def;
	}
}
