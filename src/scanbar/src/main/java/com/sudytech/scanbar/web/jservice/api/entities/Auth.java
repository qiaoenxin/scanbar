package com.sudytech.scanbar.web.jservice.api.entities;

import java.util.HashMap;
import java.util.Map;


/**
 * 鉴权
 * @author qex
 *
 */
public class Auth {
	
	public static Map<String, Token> TOKENS = new HashMap<String, Token>();
	
	private String token;
	
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public static class Token{
		private String token;
		private String userId;
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
	}
}
