package com.thinkgem.jeesite.common.jservice.api;

import java.lang.ref.WeakReference;

import com.thinkgem.jeesite.common.jservice.api.entities.Auth;

/**
 * 接口请求
 * @author qex
 *
 */
public class Request {
	
	public static final String JSON_CALL_TYPE = "json";
	
	public static final String JSONP_CALL_TYPE = "jsonp";
	
	public static final String XML_CALL_TYPE = "xml";
	/**
	 * 鉴权信息
	 */
	private Auth auth = new Auth();
	
	/**
	 * api版本
	 */
	@ParameterDef(defaultValue="1.0")
	private String version;
	
	/**
	 * 调用方式
	 */
	@ParameterDef(regex="(json)|(jsonp)|(xml)", defaultValue="json")
	private String callType;
	
	
	@ParameterDef(ignore = true)
	private transient WeakReference<Context> context;
	
	
	public Auth getAuth() {
		return auth;
	}

	public void setAuth(Auth auth) {
		this.auth = auth;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public Context getContext() {
		return context == null ? null : context.get();
	}

	public void setContext(Context context) {
		this.context = new WeakReference<Context>(context);
	}
}
