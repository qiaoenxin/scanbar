package com.thinkgem.jeesite.web;




import java.util.ArrayList;
import java.util.List;

import com.thinkgem.jeesite.common.jservice.api.BasicService;
import com.thinkgem.jeesite.common.jservice.api.ParameterDef;
import com.thinkgem.jeesite.common.jservice.api.ReturnCode;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.entity.ProductTree;
import com.thinkgem.jeesite.modules.pro.entity.ProductionDetail;
import com.thinkgem.jeesite.modules.pro.service.ProductService;
import com.thinkgem.jeesite.modules.pro.service.ProductTreeService;
import com.thinkgem.jeesite.modules.pro.service.ProductionDetailService;


public class CheckScan {

	private static ProductionDetailService detailService = SpringContextHolder.getBean(ProductionDetailService.class);
	
	private static ProductTreeService treeService = SpringContextHolder.getBean(ProductTreeService.class);
	
	private static ProductService productService = SpringContextHolder.getBean(ProductService.class);
	
	
	public static class CheckScanService extends BasicService<Request, Response>{
		
		@Override
		protected void service(Request request, Response response) {
			ProductionDetail detail = detailService.findByDetailNo(request.detailNo);
			if(detail == null){
				response.setResultAndReason(ReturnCode.DB_NOT_FIND_DATA, "找不到订单号");
				return;
			}
			Product product = detail.getProduct();
			
			List<Product> products = productService.findByName(request.productNo);
			
			if(products.isEmpty()){
				response.setResultAndReason(ReturnCode.DB_NOT_FIND_DATA, "样品不存在");
				return;
			}
			
			Product productModal = products.get(0);
			
			if(product.getId().equals(productModal.getId())){
				return;
			}
			
			if(Product.FLOW_C.equals(request.flow)){
				List<ProductTree> children = treeService.findChildrensByProductId(productModal.getId());
				for(ProductTree tree: children){
					if(tree.getProduct().getId().equals(productModal.getId())){
						return;
					}
				}
				response.setResultAndReason(ReturnCode.DB_NOT_FIND_DATA, "组合品番错误");
				return;
			}
			
			if(!request.flow.equals(productModal.getBom().getAction())){
				response.setResultAndReason(ReturnCode.DB_NOT_FIND_DATA, "工位与样品不一致");
				return;
			}
			
			Tree tree = new Tree(detail.getProduct());
			tree.build();
			boolean contains = tree.search(productModal);
			if(contains){
				return;
			}
			response.setResultAndReason(ReturnCode.DB_NOT_FIND_DATA, "品番错误");
			return;
		}
	}
	
	public static class Tree{
		
		private Product node;
		
		List<Tree> children;
		
		
		public Tree(Product node) {
			super();
			this.node = node;
		}

		void build(){
			List<ProductTree> list = treeService.findChildrensByProductId(node.getId());
			children = new ArrayList<CheckScan.Tree>();
			Product product = null;
			for(ProductTree child : list){
				product = child.getProduct();
				 product.getBom().getAction();
				Tree tree = new Tree(product);
				tree.build();
				children.add(tree);
			}
		}
		
		/**
		 * 
		 * @param product
		 * @return
		 */
		public boolean search(Product product){
			if(node.getId().equals(product.getId())){
				return true;
			}
			if(children == null){
				return false;
			}
			for(Tree child : children ){
				if(child.search(product)){
					return true;
				}
			}
			return false;
		}
	}
	
	
	
	public static class Request extends com.thinkgem.jeesite.common.jservice.api.Request{
		
		@ParameterDef(required=true, maxLength=250)
		private String detailNo;
		
		@ParameterDef(required=true, maxLength=250)
		private String productNo;
		
		@ParameterDef(required=true, maxLength=250)
		private String flow;
		
		public String getDetailNo() {
			return detailNo;
		}

		public void setDetailNo(String detailNo) {
			this.detailNo = detailNo;
		}

		public String getProductNo() {
			return productNo;
		}

		public void setProductNo(String productNo) {
			this.productNo = productNo;
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
