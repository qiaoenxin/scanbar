package com.thinkgem.jeesite.common.jservice.api;

import java.lang.ref.WeakReference;

import com.alibaba.fastjson.serializer.PropertyPreFilter;

/**
 * 接口响应
 * 
 * @author qex
 * 
 */
public class Response {
	private int result;

	private String reason = "";

	private Object data;
	
	private transient WeakReference<Context> context;
	
	private transient PropertyPreFilter[] jsonFilter;

	public Response() {
		super();
	}

	public Response(int result, String reason) {
		super();
		this.result = result;
		this.reason = reason;
	}
	
	public Response(int result, String reason, Object data) {
		super();
		this.result = result;
		this.reason = reason;
		this.data = data;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
	
	public void setResultAndReason(int result, String reason){
		this.reason = reason;
		this.result = result;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Context getContext() {
		return context == null ? null : context.get();
	}

	public void setContext(Context context) {
		this.context = new WeakReference<Context>(context);
	}

	public PropertyPreFilter[] getJsonFilter() {
		return jsonFilter;
	}

	public void setJsonFilter(PropertyPreFilter... jsonFilter) {
		this.jsonFilter = jsonFilter;
	}
}
