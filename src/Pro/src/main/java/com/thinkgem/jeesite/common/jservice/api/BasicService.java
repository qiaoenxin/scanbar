package com.thinkgem.jeesite.common.jservice.api;


/**
 * 接口服务
 * @author qex
 *
 * @param <Req> 接口参数
 * @param <Resp> 接口响应
 */
public abstract class BasicService<Req extends Request,Resp extends Response > {
	
	protected Context context;
	
	
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

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
}
