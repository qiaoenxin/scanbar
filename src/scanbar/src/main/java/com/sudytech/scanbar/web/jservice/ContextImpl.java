package com.sudytech.scanbar.web.jservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sudytech.scanbar.web.jservice.ServiceBuilder.Service;
import com.sudytech.scanbar.web.jservice.api.Context;
import com.sudytech.scanbar.web.jservice.api.Request;
import com.sudytech.scanbar.web.jservice.api.Response;

/**
 * 
 * @author mac
 */
public class ContextImpl implements Context{

	private HttpServletRequest httpRequest;
	
	private HttpServletResponse httpResponse;
	
	private Service service;
	
	
	
	public ContextImpl(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) {
		super();
		this.httpRequest = httpRequest;
		this.httpResponse = httpResponse;
	}

	private Request request;
	
	private Response response;
	
	
	public Service getService() {
		return service;
	}
	
	
	public void setService(Service service) {
		this.service = service;
	}


	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public HttpServletResponse getHttpResponse() {
		return httpResponse;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(httpRequest.getPathInfo())
		.append("?").append(httpRequest.getQueryString());
		return builder.toString();
	}
}
