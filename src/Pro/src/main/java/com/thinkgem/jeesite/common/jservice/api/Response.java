package com.thinkgem.jeesite.common.jservice.api;

/**
 * 接口响应
 * 
 * @author qex
 * 
 */
public class Response {
	private int result = 1;

	private String reason = "";

	private String data = "";

	public Response() {
		super();
	}

	public Response(int result, String reason, String data) {
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
