package com.thinkgem.jeesite.common.jservice.api;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;




/**
 * 接口列表
 * @author qex
 *
 */
public class Interfaces {
	
	private static final Interfaces instance = new Interfaces();
	
	/**
	 * 接口列表
	 */
	private Map<String, InterfaceDef> inters = new HashMap<String, InterfaceDef>();
	
	
	private Interfaces() {
		super();
	}

	public static Interfaces getInstance(){
		return instance;
	}
	
	public InterfaceDef getInterface(String uri){
		return inters.get(uri);
	}
	
	public Set<String> getUris(){
		return inters.keySet();
	}
	/**
	 * 清除接口注册
	 */
	public void clear(){
		inters.clear();
	}
	/**
	 * 注册接口
	 * @param inter 接口
	 */
	public void registerInterface(InterfaceDef inter){
		inters.put(inter.getUri(), inter);
	}
	
	
	public static class InterfaceDef{
		
		/**
		 * 请求url
		 */
		private String uri;
		
		/**
		 * 请求参数
		 */
		private Class<? extends Request> request;
		
		
		/**
		 * 响应数据
		 */
		private Class<? extends Response> response;
		
		
		/**
		 * 实现类
		 */
		private Class<?>  impl;
		
		/**
		 * 接口名称
		 */
		private String name;
		
		private boolean needAuth;
		
		public <T extends Request, S extends Response>InterfaceDef(String name,String uri, Class<T> request,
				Class<S> response, Class<? extends BasicService<T, S>> impl) {
			this(name, uri, request, response, impl, true);
		}
		
		public <T extends Request, S extends Response>InterfaceDef(String name,String uri, Class<T> request,
				Class<S> response, Class<? extends BasicService<T, S>> impl, boolean needAuth) {
			super();
			this.uri = uri;
			this.request = request;
			this.response = response;
			this.impl = impl;
			this.name = name;
			this.needAuth = needAuth;
		}

		public String getUri(){
			return uri;
		}

		public Class<? extends Request> getRequest() {
			return request;
		}

		public Class<? extends Response> getResponse() {
			return response;
		}

		public Class<?> getImpl() {
			return impl;
		}


		public String getName() {
			return name;
		}

		public boolean isNeedAuth() {
			return needAuth;
		}
	}
}
