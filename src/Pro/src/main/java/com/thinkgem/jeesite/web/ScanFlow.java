package com.thinkgem.jeesite.web;


import java.util.List;


import com.thinkgem.jeesite.common.jservice.api.BasicService;
import com.thinkgem.jeesite.common.jservice.api.ParameterDef;
import com.thinkgem.jeesite.common.jservice.api.ReturnCode;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.entity.Product.Bom;
import com.thinkgem.jeesite.modules.pro.entity.ProductTree;
import com.thinkgem.jeesite.modules.pro.entity.ProductionDetail;
import com.thinkgem.jeesite.modules.pro.service.ProductTreeService;
import com.thinkgem.jeesite.modules.pro.service.ProductionDetailService;
import com.thinkgem.jeesite.modules.pro.service.ProductionHistoryService;
import com.thinkgem.jeesite.modules.pro.service.ScanStockService;


public class ScanFlow {

	private static ProductionDetailService detailService = SpringContextHolder.getBean(ProductionDetailService.class);
	
	private static ScanStockService scanStockService = SpringContextHolder.getBean(ScanStockService.class);
	
	private static ProductionHistoryService historyService = SpringContextHolder.getBean(ProductionHistoryService.class);;
	
	private static ProductTreeService treeService = SpringContextHolder.getBean(ProductTreeService.class);
	
	public static class ScanFlowService extends BasicService<Request, Response>{
		
		@Override
		protected void service(Request request, Response response) {
			
			ProductionDetail detail = detailService.findByDetailNo(request.detailNo);
			if(detail == null){
				response.setResultAndReason(ReturnCode.DB_NOT_FIND_DATA, "找不到订单号");
				return;
			}
			if(request.flow.equals(detail.getStatus())){
				return;
			}
			String flow = request.getFlow();
			Product product = detail.getProduct();
			Bom bom = product.getBom();
			if(Bom.PRINTCARD_ZU_ZHUANG.equals(bom.getPrintCard())){
				List<ProductTree> children = treeService.findChildrensByProductId(product.getId());
				detail.setStatus(flow);
				scanStockService.composeScan(detail, children);
			}else if(Bom.PRINTCARD_ZHI_CHENG.equals(bom.getPrintCard())){
				detail.setStatus(flow);
				scanStockService.flowScan(detail);
			}else{
				response.setResultAndReason(ReturnCode.DB_NOT_FIND_DATA, "未知的扫描卡");
				return;
			}
		}
	}
	
	
	
	public static class Request extends com.thinkgem.jeesite.common.jservice.api.Request{
		
		@ParameterDef(required=true, maxLength=250)
		private String detailNo;
		
		@ParameterDef(required=true)
		private String flow;
		
		public String getDetailNo() {
			return detailNo;
		}

		public void setDetailNo(String detailNo) {
			this.detailNo = detailNo;
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
