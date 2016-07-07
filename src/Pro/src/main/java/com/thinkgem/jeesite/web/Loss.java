package com.thinkgem.jeesite.web;




import java.util.List;

import com.thinkgem.jeesite.common.jservice.api.BasicService;
import com.thinkgem.jeesite.common.jservice.api.ParameterDef;
import com.thinkgem.jeesite.common.jservice.api.ReturnCode;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.pro.entity.ProductionDetail;
import com.thinkgem.jeesite.modules.pro.service.ProductionDetailService;
import com.thinkgem.jeesite.modules.pro.service.ScanStockService;


public class Loss {

	private static ProductionDetailService detailService = SpringContextHolder.getBean(ProductionDetailService.class);
	
	
	private static ScanStockService scanStockService = SpringContextHolder.getBean(ScanStockService.class);
	
	
	public static class LossService extends BasicService<Request, Response>{
		
		
		@Override
		protected void service(Request request, Response response) {
			List<ProductionDetail> details = detailService.findComDetail(request.detailNo);
			ProductionDetail detail = null;
			for(ProductionDetail cur : details){
				if(cur.getProduct().getBom().getAction().equals(request.flow)){
					detail = cur;
					break;
				}
			}
			
			if(details.isEmpty()){
				response.setResultAndReason(ReturnCode.DB_NOT_FIND_DATA, "找不到订单号");
				return;
			}
			
			if(detail == null){
				response.setResultAndReason(ReturnCode.DB_NOT_FIND_DATA, "工位不匹配");
				return;
			}
			
			try {
				String[] products = request.products.split(";");
				scanStockService.saveLossStock(detail, products, details);
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
		private String products;
		
		@ParameterDef(required = true)
		private String flow;
		
		public String getDetailNo(){
			return detailNo;
		}

		public void setDetailNo(String detailNo) {
			this.detailNo = detailNo;
		}

		public String getProducts() {
			return products;
		}

		public void setProducts(String products) {
			this.products = products;
		}

		public String getFlow() {
			return flow;
		}

		public void setFlow(String flow) {
			this.flow = flow;
		}
	}
	
	public static class Response extends com.thinkgem.jeesite.common.jservice.api.Response{
		
	}
}
