package com.thinkgem.jeesite.web;





import com.thinkgem.jeesite.common.jservice.api.BasicService;
import com.thinkgem.jeesite.common.jservice.api.ParameterDef;
import com.thinkgem.jeesite.common.jservice.api.ReturnCode;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.pro.entity.Device;
import com.thinkgem.jeesite.modules.pro.service.DeviceService;

public class Test {
	private static DeviceService deviceService = SpringContextHolder.getBean(DeviceService.class);
	public static class TestService extends BasicService<Request, Response> {
		@Override
		protected void service(Request request, Response response) {
			Device device = deviceService.findByDeviceKey(request.getDevice());
			if(device == null){
				response.setResultAndReason(ReturnCode.AUTH_ERROR, "登陆失败，无效的设备号");
				return;
			}
			
			if(!deviceService.valid(device)){
				response.setResultAndReason(ReturnCode.AUTH_ERROR, "登陆失败，无效的设备号");
				return;
			}
		}
		
	}
	

	public static class Request extends
			com.thinkgem.jeesite.common.jservice.api.Request {
		@ParameterDef(required = true)
		private String device;


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
