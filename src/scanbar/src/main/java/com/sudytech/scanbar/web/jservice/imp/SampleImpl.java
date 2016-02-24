package com.sudytech.scanbar.web.jservice.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sudytech.scanbar.bean.User;
import com.sudytech.scanbar.service.UserService;
import com.sudytech.scanbar.web.jservice.api.BasicService;
import com.sudytech.scanbar.web.jservice.api.Request;
import com.sudytech.scanbar.web.jservice.api.Response;

public class SampleImpl{
	
	@Service
	public static class SampleService extends BasicService<SampleRequest, SampleResponse>{

		@Autowired
		private UserService userService;
		@Override
		protected void service(SampleRequest request, SampleResponse response) {
//			UserService service = SpringContextHolder.getContext().getBean(UserService.class);
			User user = new User();
			user.setAccount("12346");
			userService.save(user);
			System.out.println("243234");
		}
		
		public UserService getUserService() {
			return userService;
		}

		public void setUserService(UserService userService) {
			this.userService = userService;
		}
	}
	
	

	
	

	public static class SampleRequest extends Request{
		
	}
	
	public static class SampleResponse extends Response{
		private String tables;
		
		/**
		 * bu fan hui
		 */
		private transient String nojson;
		
		
		public String getTables() {
			return tables;
		}

		public String getNojson() {
			return nojson;
		}

		public void setNojson(String nojson) {
			this.nojson = nojson;
		}

		public void setTables(String tables) {
			this.tables = tables;
		}
	}
}
