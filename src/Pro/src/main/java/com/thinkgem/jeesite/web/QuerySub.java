package com.thinkgem.jeesite.web;


import java.util.ArrayList;
import java.util.List;




import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
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


public class QuerySub {

	private static ProductionDetailService detailService = SpringContextHolder.getBean(ProductionDetailService.class);
	
	private static ProductTreeService treeService = SpringContextHolder.getBean(ProductTreeService.class);
	
	
	public static class QuerySubService extends BasicService<Request, Response>{
		
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
			
			Product product = detail.getProduct();
			List<ProductTree> list = new ArrayList<ProductTree>();
			ProductTree tree = new ProductTree();
			tree.setNumber(detail.getNumber());
			tree.setProduct(product);
			list.add(tree);
			List<ProductTree> subs = treeService.findSubTree(detail.getProductTree().getProduct());
			list.addAll(subs);
			response.setData(list);
			response.setJsonFilter(new SimplePropertyPreFilter(ProductTree.class, "product", "number"), new SimplePropertyPreFilter(Product.class,"id", "serialNum","name"));
		}
	}
	
	public static class Request extends com.thinkgem.jeesite.common.jservice.api.Request{
		
		@ParameterDef(required=true, maxLength=250)
		private String detailNo;
		
		@ParameterDef(required=true, maxLength=250)
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
