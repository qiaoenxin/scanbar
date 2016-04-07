package com.bite.pro;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AppWebView extends WebView{


	public AppWebView(Context context) {
		super(context);
	}
	
	@SuppressWarnings("deprecation")
	@SuppressLint("SetJavaScriptEnabled")
	public void initWebView(){
		setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        WebSettings setting = getSettings();
        setting.setJavaScriptEnabled(true); 
//        setting.setSupportZoom(true);
//        setting.setBuiltInZoomControls(true);
        setting.setAllowFileAccess(true);
        setting.setJavaScriptCanOpenWindowsAutomatically(true); 
        Context context = getContext();
        
        setting.setDomStorageEnabled(true);
        setting.setCacheMode(WebSettings.LOAD_DEFAULT);
        setting.setDatabaseEnabled(true);
        File databasePath = context.getDir("web_dbpath", 0);
        if(!databasePath.exists()){
        	databasePath.mkdirs();
        }
		setting.setDatabasePath(databasePath.getAbsolutePath());
        File path = context.getDir("web_path", 0);
        if(!path.exists()){
        	path.mkdirs();
        }
        setting.setAppCachePath(path.getAbsolutePath()); 
        setting.setAppCacheEnabled(true); 
        setting.setLoadsImagesAutomatically(true); 
        setting.setSavePassword(false); 
//        setting.setLightTouchEnabled(true);
        setWebViewClient(new Client());
        setWebChromeClient(new Chrome());
        addJavascriptInterface(new JSInterface(this), "_jscallapi");
	}
	
	static class Chrome extends WebChromeClient{
		
	}
	
	static class Client extends WebViewClient{
		
		
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			
			view.loadUrl(url);
			return true;
		}
		
	}
}
