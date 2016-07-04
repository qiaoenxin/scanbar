package com.thinkgem.jeesite.web;





import java.util.List;

import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.thinkgem.jeesite.common.jservice.api.BasicService;
import com.thinkgem.jeesite.common.jservice.api.ParameterDef;
import com.thinkgem.jeesite.common.jservice.api.ReturnCode;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.pro.entity.Device;
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.entity.ProductTree;
import com.thinkgem.jeesite.modules.pro.service.DeviceService;
import com.thinkgem.jeesite.modules.pro.service.ProductService;

public class QueryProduct {
	private static ProductService productService = SpringContextHolder.getBean(ProductService.class);
	public static class QueryProductService extends BasicService<Request, Response> {
		@Override
		protected void service(Request request, Response response) {
			List<Product> list = productService.findByName(request.productName);
			if(list.isEmpty()){
				response.setResultAndReason(ReturnCode.DB_ERROR, "查无此产品");
				return;
			}
			Product product = list.get(0);
			response.setData(product);
			response.setJsonFilter(new SimplePropertyPreFilter(Product.class,"id", "serialNum","name"));
			return;
		}
		
	}
	

	public static class Request extends
			com.thinkgem.jeesite.common.jservice.api.Request {
		@ParameterDef(required = true)
		private String productName;

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}
	}

	public static class Response extends
			com.thinkgem.jeesite.common.jservice.api.Response {

	}
}
