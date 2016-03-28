package com.bite.pro;

import com.bite.pro.util.MobileUtil;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

public class WebViewActivity extends BaseActivity{
	private AppWebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_activity);
		ViewGroup root = (ViewGroup) findViewById(R.id.scrollroot);
		webView = new AppWebView(this);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		root.addView(webView,params);
		webView.initWebView();
		webView.requestFocus();
//		String setting = PreferUtil.getWebPreferString(MobileUtil.SETTING, "");
		webView.loadUrl("file:///android_asset/www/setting.html");
//		if(setting == null || setting.length() == 0){
//			
//		}else{
//			webView.loadUrl(setting + "/m/mobile/index");
//		}
	}
	BackPress backPress = new BackPress();
	
	@Override
	public void onBackPressed() {
		String url = webView.getUrl();
		if(url.indexOf("m/mobile/index")!= -1){
			backPress.back();
		}else{
			webView.goBack();
		}
	}
	
	class BackPress{
		long prevTime;
		int count;
		
		public void back(){
			if(System.currentTimeMillis() - prevTime > 2000){
				count = 0;
			}
			prevTime = System.currentTimeMillis();
			count++;
			if(count == 1){
				Toast.makeText(MobileUtil.getContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
			}
			if(count > 1){
				System.exit(0);
			}
			
		}
	}
}
