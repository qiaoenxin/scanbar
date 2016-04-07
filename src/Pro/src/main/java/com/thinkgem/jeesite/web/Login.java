package com.thinkgem.jeesite.web;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;


import com.thinkgem.jeesite.common.jservice.api.BasicService;
import com.thinkgem.jeesite.common.jservice.api.ParameterDef;
import com.thinkgem.jeesite.common.jservice.api.ReturnCode;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.pro.entity.Device;
import com.thinkgem.jeesite.modules.pro.service.DeviceService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

public class Login {
	private static SystemService systemService = SpringContextHolder.getBean(SystemService.class);
	private static DeviceService deviceService = SpringContextHolder.getBean(DeviceService.class);
	public static class LoginService extends BasicService<Request, Response> {
		private List<LoginInfo> loginInfos = new ArrayList<Login.LoginInfo>();
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
			Device device = deviceService.findByDeviceKey(request.getDevice());
			if(device == null){
				response.setResultAndReason(ReturnCode.AUTH_ERROR, "登陆失败，无效的设备号");
				return;
			}
			
			if(!deviceService.valid(device)){
				response.setResultAndReason(ReturnCode.AUTH_ERROR, "登陆失败，无效的设备号");
				return;
			}
			
			//一个账号和设备，仅允许登录一次
			loginOnce(user.getLoginName(), device.getDeviceKey());
			
			HttpSession session = request.getContext().getHttpRequest().getSession();
			session.setAttribute(UserUtils.USER_SESSION, user);
			LoginInfo info = new LoginInfo();
			info.session = session;
			info.loginName = user.getLoginName();
			info.device = device.getDeviceKey();
			loginInfos.add(info);
		}
		
		private synchronized void loginOnce(String loginName, String device) {
			for(Iterator<LoginInfo> iter = loginInfos.iterator(); iter.hasNext();){
				LoginInfo info = iter.next();
				if(loginName.equals(info.loginName) || device.equals(info.device)){
					info.session.invalidate();
					iter.remove();
				}
			}
		}
	}
	
	public static class LoginInfo {
		private HttpSession session;
		private String loginName;
		private String device;
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
