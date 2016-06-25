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


public class Scan {

	private static ProductionDetailService detailService = SpringContextHolder.getBean(ProductionDetailService.class);
	
	private static ProductTreeService treeService = SpringContextHolder.getBean(ProductTreeService.class);
	
	private static ScanStockService scanStockService = SpringContextHolder.getBean(ScanStockService.class);;
	
	public static class ScanService extends BasicService<Request, Response>{
		
		@Override
		protected void service(Request request, Response response) {
			ProductionDetail detail = detailService.findByDetailNo(request.detailNo);
			if(detail == null){
				response.setResultAndReason(ReturnCode.DB_NOT_FIND_DATA, "找不到订单号");
				return;
			}
			//判断流程是否完成
			Product product = detail.getProduction().getProduct();
			if(product.getAssy() != Product.ASSY_SIMPLE){
				product = detail.getProductTree().getProduct();
				response.setResultAndReason(ReturnCode.DB_NOT_FIND_DATA, "暂不支持组合品");
				return;
			}
			/*
			List<Flow> flows = product.getFlows();
			if(!flows.isEmpty()){
				if(!detail.getStatus().equals(flows.get(flows.size() -1).getId())){
					response.setResultAndReason(ReturnCode.SAVE_STORE_ERROR, "入库失败，加工流程未结束"); 
					return;
				}
			}*/
			
			List<ProductTree> subTrees = treeService.findSubTree(product);
			 try {
				 /*
				 Flow last = null;
				 for(int i = flows.size() -1 ; i >= 0; i--){
					 Flow flow = flows.get(i);
					 if(flow.getId().equals("2")){
						 last = flow;
						 break;
					 }
					 if(flow.getId().equals("1")){
						 last = flow;
						 break;
					 }
				 }*/
				scanStockService.saveStock(detail, subTrees);
			} catch (Exception e) {
				logger.error("扫描入库出错", e);
				response.setResultAndReason(ReturnCode.DB_ERROR, "扫描入库出错");
			}
		}
	}
	
	public static class Request extends com.thinkgem.jeesite.common.jservice.api.Request{
		
		@ParameterDef(required=true, maxLength=250)
		private String detailNo;
		
		public String getDetailNo() {
			return detailNo;
		}

		public void setDetailNo(String detailNo) {
			this.detailNo = detailNo;
		}
		
	}
	
	public static class Response extends com.thinkgem.jeesite.common.jservice.api.Response{
		
	}
}
