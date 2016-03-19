package com.thinkgem.jeesite.common.jservice.api;

/**
 * 返回码
 * 200以上为错误状态
 * 200-299为系统错误
 * 300以上为业务错误
 * @author qex
 *
 */
public interface ReturnCode {
	
	/**
	 * 成功
	 */
	int SUCCESS = 100;
	
	/**
	 * 未知错误
	 */
	int UNKOWN_ERROR = 200;
	
	
	/**
	 * 数据库操作
	 */
	int DB_ERROR = 201;
	
	/**
	 * 外部系统调用网络错误
	 */
	int EXTERNAL_NET_ERROR = 202;
	
	/**
	 * 没有这个接口
	 */
	int UNKOWN_SERVICE = 203;
	
	/**
	 * 参数格式错误
	 */
	int PARAMETER_ERROR = 204;
	
	/**
	 * 登录失败
	 */
	int LOGIN_ERROR = 300;
	
	/**
	 * 鉴权失败
	 */
	int AUTH_ERROR = 301;
}
