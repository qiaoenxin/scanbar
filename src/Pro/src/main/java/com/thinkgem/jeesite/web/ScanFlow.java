package com.thinkgem.jeesite.web;


import java.util.List;


import com.thinkgem.jeesite.common.jservice.api.BasicService;
import com.thinkgem.jeesite.common.jservice.api.ParameterDef;
import com.thinkgem.jeesite.common.jservice.api.ReturnCode;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.entity.Product.Flow;
import com.thinkgem.jeesite.modules.pro.entity.ProductionDetail;
import com.thinkgem.jeesite.modules.pro.service.ProductionDetailService;
import com.thinkgem.jeesite.modules.pro.service.ProductionHistoryService;


public class ScanFlow {

	private static ProductionDetailService detailService = SpringContextHolder.getBean(ProductionDetailService.class);
	
	
	private static ProductionHistoryService historyService = SpringContextHolder.getBean(ProductionHistoryService.class);;
	
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
			
			Product product = detail.getProductTree().getProduct();
			List<Flow> flows = product.getFlows();
			Flow statusFlow = null;
			for(Flow flow : flows){
				if(flow.getId().equals(detail.getStatus())){
					statusFlow = flow;
					break;
				}
			}
			Flow next = null;
			if(statusFlow != null){
				next = statusFlow.getNext();
			}else{
				if(!flows.isEmpty()){
					next = flows.get(0);
				}
			}
			if(next == null){
				response.setResultAndReason(ReturnCode.FLOW_ERROR, "最后一个环节");
				return;
			}
			if(next.getId().equals(request.getFlow())){
				detail.setStatus(request.getFlow());
				historyService.saveHistory(detail);
			}else{
				response.setResultAndReason(ReturnCode.FLOW_ERROR, "流程不匹配");
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