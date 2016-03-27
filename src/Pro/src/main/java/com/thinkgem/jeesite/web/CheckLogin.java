package com.thinkgem.jeesite.web;


import com.thinkgem.jeesite.common.jservice.api.BasicService;
import com.thinkgem.jeesite.common.jservice.api.ReturnCode;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

public class CheckLogin {

	public static class CheckLoginService extends BasicService<Request, Response> {

		@Override
		protected void service(Request request, Response response) {
			User user = UserUtils.getUser();
			if(user == null || StringUtils.isEmpty(user.getId())){
				response.setResultAndReason(ReturnCode.AUTH_ERROR, "没有登录");
			}
		}
	}

	public static class Request extends
			com.thinkgem.jeesite.common.jservice.api.Request {
	}

	public static class Response extends
			com.thinkgem.jeesite.common.jservice.api.Response {
	}
}
