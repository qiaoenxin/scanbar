package com.thinkgem.jeesite.web;


import java.util.List;




import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.thinkgem.jeesite.common.jservice.api.BasicService;
import com.thinkgem.jeesite.common.jservice.api.ParameterDef;
import com.thinkgem.jeesite.common.jservice.api.ReturnCode;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.entity.ProductTree;
import com.thinkgem.jeesite.modules.pro.entity.ProductionDetail;
import com.thinkgem.jeesite.modules.pro.service.ProductTreeService;
import com.thinkgem.jeesite.modules.pro.service.ProductionDetailService;


public class QuerySub {

	private static ProductionDetailService detailService = SpringContextHolder.getBean(ProductionDetailService.class);
	
	private static ProductTreeService treeService = SpringContextHolder.getBean(ProductTreeService.class);
	
	
	public static class QuerySubService extends BasicService<Request, Response>{
		
		@Override
		protected void service(Request request, Response response) {
			ProductionDetail detail = detailService.findByDetailNo(request.detailNo);
			if(detail == null){
				response.setResultAndReason(ReturnCode.DB_NOT_FIND_DATA, "找不到订单号");
				return;
			}
			List<ProductTree> subs = treeService.findSubTree(detail.getProductTree().getProduct());
			response.setData(subs);
			response.setJsonFilter(new SimplePropertyPreFilter(ProductTree.class, "product", "number"), new SimplePropertyPreFilter(Product.class,"id", "serialNum"));
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
