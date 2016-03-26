package com.thinkgem.jeesite.web;


import java.util.List;


import com.thinkgem.jeesite.common.jservice.api.BasicService;
import com.thinkgem.jeesite.common.jservice.api.ParameterDef;
import com.thinkgem.jeesite.common.jservice.api.ReturnCode;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.entity.ProductTree;
import com.thinkgem.jeesite.modules.pro.entity.ProductionDetail;
import com.thinkgem.jeesite.modules.pro.service.ProductTreeService;
import com.thinkgem.jeesite.modules.pro.service.ProductionDetailService;
import com.thinkgem.jeesite.modules.pro.service.ScanStockService;


public class Loss {

	private static ProductionDetailService detailService = SpringContextHolder.getBean(ProductionDetailService.class);
	
	
	private static ScanStockService scanStockService;
	
	public static class LossService extends BasicService<Request, Response>{
		
		
		@Override
		protected void service(Request request, Response response) {
			ProductionDetail detail = detailService.findByDetailNo(request.detailNo);
			if(detail == null){
				response.setResultAndReason(ReturnCode.DB_NOT_FIND_DATA, "找不到订单号");
				return;
			}
			try {
				scanStockService.saveStock(detail, request.products);
			} catch (Exception e) {
				logger.error(e.toString(), e);
				response.setResultAndReason(ReturnCode.DB_ERROR, "数据库操作错误");
			}
		}
	}
	
	public static class Request extends com.thinkgem.jeesite.common.jservice.api.Request{
		
		@ParameterDef(required=true, maxLength=250)
		private String detailNo;
		
		@ParameterDef(required = true)
		private String[] products;
		
		public String getDetailNo(){
			return detailNo;
		}

		public void setDetailNo(String detailNo) {
			this.detailNo = detailNo;
		}

		public String[] getProducts() {
			return products;
		}

		public void setProducts(String[] products) {
			this.products = products;
		}
	}
	
	public static class Response extends com.thinkgem.jeesite.common.jservice.api.Response{
		
	}
}
