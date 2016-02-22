package com.sudytech.scanbar.web.jservice.api;

import com.sudytech.scanbar.web.jservice.api.entities.Auth;

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
}
