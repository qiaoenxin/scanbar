package com.sudytech.scanbar.web.jservice.imp;

import com.sudytech.scanbar.web.jservice.api.BasicService;
import com.sudytech.scanbar.web.jservice.api.Request;
import com.sudytech.scanbar.web.jservice.api.Response;

public class PrepareScan {
	
	public class PrepareScanImpl extends BasicService<PrepareScanRequest, PrepareScanResponse>{

		@Override
		protected void service(PrepareScanRequest request,
				PrepareScanResponse response) {
			
		}
	}

	public static class PrepareScanRequest extends Request {
		private String workNO;
		private String listNO;
		private String device;
		public String getWorkNO() {
			return workNO;
		}
		public void setWorkNO(String workNO) {
			this.workNO = workNO;
		}
		public String getListNO() {
			return listNO;
		}
		public void setListNO(String listNO) {
			this.listNO = listNO;
		}
		public String getDevice() {
			return device;
		}
		public void setDevice(String device) {
			this.device = device;
		}
	}
	
	public static class PrepareScanResponse extends Response{
		
	}
}
