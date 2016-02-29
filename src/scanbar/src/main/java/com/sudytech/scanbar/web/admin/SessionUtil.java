package com.sudytech.scanbar.web.admin;

import javax.servlet.http.HttpServletRequest;

import com.sudytech.scanbar.bean.User;

public class SessionUtil {
	public static final String loginUser = "__loginUser";

	public static User loginUser(HttpServletRequest request){
		return (User) request.getSession().getAttribute(loginUser);
	}
}
