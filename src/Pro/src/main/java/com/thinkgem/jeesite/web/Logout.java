package com.thinkgem.jeesite.web;

import javax.servlet.http.HttpSession;

import com.thinkgem.jeesite.common.jservice.api.BasicService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

public class Logout {

	public static class LogoutService extends BasicService<Request, Response> {

		@Override
		protected void service(Request request, Response response) {
			HttpSession session = request.getContext().getHttpRequest().getSession();
			session.removeAttribute(UserUtils.USER_SESSION);
		}
	}

	public static class Request extends
			com.thinkgem.jeesite.common.jservice.api.Request {
	}

	public static class Response extends
			com.thinkgem.jeesite.common.jservice.api.Response {
	}
}
