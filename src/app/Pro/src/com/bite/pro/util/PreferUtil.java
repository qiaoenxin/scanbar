package com.bite.pro.util;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;

public class PreferUtil {
	private static final String PREFER_NAME = PreferUtil.class.getName();
	
	
	public static final String ProtoVersion = "ProtoVersion";
	/**
	 * µ±Ç°²å¼þ
	 */
	public static final String CurrentVersion = "CurrentVersion";
	
	private static Context context;
	
	
	
	
	@SuppressLint("InlinedApi")
	private static SharedPreferences getPrefer(Context context, String name){
		if(name == null){
			name = PREFER_NAME;
		}
		if(Build.VERSION.SDK_INT > 10){
			return context.getSharedPreferences(name, Context.MODE_MULTI_PROCESS);
		}else{
			return context.getSharedPreferences(name, Context.MODE_PRIVATE);
		}
	}
	
	public static String getWebPreferString(String key, String def){
		SharedPreferences prefer = getPrefer(MobileUtil.getContext(), "myWeb");
		return prefer.getString(key, def);
	}
	
	public static void putWebPreferString(String key, String value){
		SharedPreferences prefer = getPrefer(MobileUtil.getContext(), "myWeb");
		Editor editor = prefer.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
}
