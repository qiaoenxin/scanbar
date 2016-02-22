package com.sudytech.scanbar.web.jservice.api;

/**
 * 接口响应
 * @author qex
 *
 */
public class Response {
	/**
	 * 返回码
	 */
	private int returnCode;

	/**
	 * 返回码描述
	 */
	private String description;
	
	
	public Response() {
		super();
	}
	
	public Response(int returnCode, String description) {
		super();
		this.returnCode = returnCode;
		this.description = description;
	}
	
	public int getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
