package com.bite.pro;

import com.bite.pro.util.MobileUtil;

import android.app.Application;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

public class App extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("App", "--------init app------");
		MobileUtil.init(this);
		
		//清除cookie
		CookieSyncManager.createInstance(this);
		CookieSyncManager.getInstance().startSync(); 
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeSessionCookie();
		cookieManager.removeExpiredCookie();
		
		//清除缓存
		AppWebView webView = new AppWebView(this);
		webView.clearCache(true);
	}
}
