package com.thinkgem.jeesite.common.jservice.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 接口服务
 * @author qex
 *
 * @param <Req> 接口参数
 * @param <Resp> 接口响应
 */
public abstract class BasicService<Req extends Request,Resp extends Response > {
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	public void doService(Req request, Resp response){
		before(request, response);
		service(request, response);
		after(request, response);
	}
	
	protected void before(Req request, Resp response){
		
	}
	protected abstract void service(Req request, Resp response);
	
	protected void after(Req request, Resp response){
		
	}
}
