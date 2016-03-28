package com.bite.pro.util;

import android.content.Context;

public class MobileUtil {

	private static Context context;
	
	public static final String SETTING = "addressUrl";
	
	public static void init(Context ctx){
		context = ctx;
	}
	
	public static Context getContext(){
		return context;
	}
	
	
}
