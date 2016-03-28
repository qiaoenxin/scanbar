package com.bite.pro;

import java.lang.ref.WeakReference;

import org.json.JSONException;
import org.json.JSONObject;

import com.bite.pro.util.PreferUtil;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public class JSInterface {
	

	private WeakReference<WebView> webView;
	
	
	public JSInterface(WebView webView) {
		super();
		this.webView = new WeakReference<WebView>(webView);
	}
	
	private WebView getView(){
		return webView.get();
	}

	@JavascriptInterface
	public String call(String name, String param){
		JSONObject json = null;
		try {
			json = new JSONObject(param);
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
		if("getPrefer".equals(name)){
			String key = getValue("key", json);
			String value = PreferUtil.getWebPreferString(key, "");
			return value;
		}
		
		if("savePrefer".equals(name)){
			String key = getValue("key", json);
			String value = getValue("value", json);
			PreferUtil.putWebPreferString(key, value);
			return "1";
		}
		return "";
	}
	
	private String getValue(String name, JSONObject json){
		try {
			return json.getString(name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
