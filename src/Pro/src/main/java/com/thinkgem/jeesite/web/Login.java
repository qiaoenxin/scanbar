package com.thinkgem.jeesite.web;

import javax.servlet.http.HttpSession;

import com.thinkgem.jeesite.common.jservice.api.BasicService;
import com.thinkgem.jeesite.common.jservice.api.ParameterDef;
import com.thinkgem.jeesite.common.jservice.api.ReturnCode;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

public class Login {
	private static SystemService systemService = SpringContextHolder.getBean(SystemService.class);

	public static class LoginService extends BasicService<Request, Response> {

		@Override
		protected void service(Request request, Response response) {
			User user = systemService.getUserByLoginName(request.getLoginName());
			if(user == null){
				response.setResultAndReason(ReturnCode.AUTH_ERROR, "登陆失败，用户名或密码错误");
				return;
			}
			boolean result = SystemService.validatePassword(request.getPassword(), user.getPassword());
			if(!result){
				response.setResultAndReason(ReturnCode.AUTH_ERROR, "登陆失败，用户名或密码错误");
				return;
			}
			HttpSession session = request.getContext().getHttpRequest().getSession();
			session.setAttribute(UserUtils.USER_SESSION, user);
		}
	}

	public static class Request extends
			com.thinkgem.jeesite.common.jservice.api.Request {
		@ParameterDef(required = true)
		private String loginName;
		@ParameterDef(required = true)
		private String password;
		@ParameterDef(required = true)
		private String device;

		public String getLoginName() {
			return loginName;
		}

		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getDevice() {
			return device;
		}

		public void setDevice(String device) {
			this.device = device;
		}
	}

	public static class Response extends
			com.thinkgem.jeesite.common.jservice.api.Response {

	}
}