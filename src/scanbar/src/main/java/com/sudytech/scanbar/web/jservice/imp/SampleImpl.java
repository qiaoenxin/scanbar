package com.sudytech.scanbar.web.jservice.imp;

import com.sudytech.scanbar.web.jservice.api.BasicService;
import com.sudytech.scanbar.web.jservice.api.PageRequest;
import com.sudytech.scanbar.web.jservice.api.ParameterDef;
import com.sudytech.scanbar.web.jservice.api.Response;
import com.sudytech.scanbar.web.jservice.api.ReturnCode;

public class SampleImpl{
	
	public static class SampleService extends BasicService<SampleRequest, SampleResponse>{

		@Override
		protected void service(SampleRequest request, SampleResponse response) {
			String keyword = request.getKeyWords();
			System.out.println(request);
			response.setReturnCode(ReturnCode.SUCCESS);
			response.setDescription("操蛋");
		}
		
	}

	
	public static class SampleRequest extends PageRequest{
		@ParameterDef(required=true, defaultValue="", regex="")
		private String keyWords;

		public String getKeyWords() {
			return keyWords;
		}

		public void setKeyWords(String keyWords) {
			this.keyWords = keyWords;
		}
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
