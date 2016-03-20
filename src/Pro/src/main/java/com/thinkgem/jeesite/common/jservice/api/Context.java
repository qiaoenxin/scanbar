package com.thinkgem.jeesite.common.jservice.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Context {
	
	HttpServletRequest getHttpRequest();
	
	HttpServletResponse getHttpResponse();
	
}
