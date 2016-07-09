package com.thinkgem.jeesite.modules.pro.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.pro.entity.Loss;
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.entity.ProductTree;
import com.thinkgem.jeesite.modules.pro.entity.Production;
import com.thinkgem.jeesite.modules.pro.entity.ProductionDetail;
import com.thinkgem.jeesite.modules.pro.entity.ProductionHistory;
import com.thinkgem.jeesite.modules.pro.entity.StockHistory;

@Component
public class ScanStockService {

	
	@Autowired
	private StockHistoryService historyService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductionService productionService;
	
	@Autowired
	private ProductionDetailService detailService;
	
	@Autowired
	private ProductionHistoryService productionHistoryService;
	
	@Autowired
	private ProductTreeService treeService;
	
	@Autowired
	private LossService lossService;
	
	
	
	/**
	 * 扫描入库
	 * @param detail
	 * @param subTrees
	 * @param flows 
	 * @throws Exception
	 */
	@Transactional
	public void saveStock(ProductionDetail detail, List<ProductTree> subTrees) throws Exception{
		String reason = "生产扫描";
		List<StockHistory> stockList = historyService.findByDetailId(detail.getId());
		if(stockList.size() > 0){
			return;
		}
		if(detail.getProduction().getProduct().getAssy() != Product.ASSY_SIMPLE){
			StockHistory stockHistory = new StockHistory();
			stockHistory.setType(StockHistory.TYPE_SCAN_ADD);
			stockHistory.setProduct(detail.getProductTree().getProduct());
			stockHistory.setProductionDetail(detail);
			stockHistory.setReason(reason);
			int number  = detail.getNumber();
			String productId = detail.getProduction().getProduct().getId();
			if(productId.equals(detail.getProductTree().getProduct().getId())){
				Production production = detail.getProduction();
				productionService.updateFinishNum(detail.getNumber(), production.getId());
			}
			stockHistory.setNumber(detail.getNumber());
			for(ProductTree tree: subTrees){
				Product subProduct = tree.getProduct();
				StockHistory subHistory = new StockHistory();
				subHistory.setNumber(tree.getNumber() * number);
				subHistory.setProduct(subProduct);
				subHistory.setType(StockHistory.TYPE_SCAN_DESC);
				subHistory.setProductionDetail(detail);
				subHistory.setReason(reason);
				historyService.save(subHistory);
			}
			historyService.save(stockHistory);
		}else{
			Production production = detail.getProduction();
			productionService.updateFinishNum(detail.getNumber(), production.getId());
			
			Product product = detail.getProduction().getProduct();
			StockHistory stockHistory = new StockHistory();
			stockHistory.setType(StockHistory.TYPE_SCAN_ADD);
			stockHistory.setProduct(product);
			stockHistory.setNumber(detail.getNumber());
			stockHistory.setProductionDetail(detail);
			stockHistory.setReason(reason);
			historyService.save(stockHistory);
		}
	}
	
	
	/**
	 * 生产报损失
	 * @param detail
	 * @param products
	 * @param details 
	 * @throws Exception
	 */
	@Transactional
	public void saveLossStock(ProductionDetail detail, String[] products, List<ProductionDetail> details) throws Exception{
		List<String> pruductIds = new ArrayList<String>();
		int detaiValue = 0;
		for(String pruduct: products){
			String[] splits = pruduct.split(",");
			Product pro = productService.get(splits[0]);
			pruductIds.add(pro.getId());
			int value = StringUtils.toInteger(splits[1]);
			if(value == 0){
				continue;
			}
			if(detail.getProduct().getId().equals(pro.getId())){
				detaiValue = value;
				detail.setUnqualifiedNum(detail.getUnqualifiedNum() + detaiValue);
				detail.setNumber(detail.getNumber() - detaiValue);
				detailService.save(detail);
			}
			Loss loss = new Loss();
			loss.setNumber(value);
			loss.setDetail(detail);
			loss.setProduct(pro);
			lossService.save(loss);
			StockHistory subHistory = new StockHistory();
			subHistory.setNumber(value);
			subHistory.setProduct(pro);
			subHistory.setType(StockHistory.TYPE_PRODUCING_DESC);
			subHistory.setProductionDetail(detail);
			subHistory.setRemarks("loss:" + loss.getId());
			historyService.save(subHistory);
		}
		
		if(detaiValue == 0){
			return;
		}
		
		List<ProductionDetail> detailList = Lists.newArrayList();
		for(ProductionDetail temp : details){
			
			if(!StringUtils.isEmpty(temp.getStatus())){
				continue;
			}
			
			if(pruductIds.contains(temp.getId())){
				continue;
			}
			
			temp.setNumber(detail.getNumber() - detaiValue);
			
			detailList.add(temp);
		}
		
		detailService.save(detailList);
	}
	
	@Transactional
	public void composeScan(ProductionDetail detail, List<ProductTree> children){
		
		Product product = detail.getProduct();
		StockHistory stockHistory = new StockHistory();
//		int number = detail.getNumber() - detail.getUnqualifiedNum();
		int number = detail.getNumber();
		stockHistory.setNumber(number);
		stockHistory.setProduct(product);
		stockHistory.setType(StockHistory.TYPE_SCAN_ADD);
		stockHistory.setProductionDetail(detail);
		historyService.save(stockHistory);
		for(ProductTree tree : children){
			StockHistory sub = new StockHistory();
			sub.setNumber(number * tree.getNumber());
			sub.setProduct(tree.getProduct());
			sub.setType(StockHistory.TYPE_SCAN_DESC);
			sub.setProductionDetail(detail);
			historyService.save(sub);
		}
		
		detail.setCompleteNum(number);
		detailService.save(detail);
		if(detail.getProduct().getId().equals(detail.getProduction().getProduct().getId())){
			productionService.updateFinishCount(detail.getNumber(), detail.getProduction().getId());
		}
		
		ProductionHistory productionHistory = new ProductionHistory();
		productionHistory.setProductionDetail(detail);
		productionHistory.setStatus(detail.getStatus());
		productionHistoryService.save(productionHistory);
	}
	

	static class Buffer{
		
		Product product;
		int num;
	}
	
	private void findLeaf(int num, List<ProductTree> children, List<Buffer> leaf) {
		for(ProductTree tree: children){
			List<ProductTree> list = treeService.findChildrensByProductId(tree.getProduct().getId());
			if(list.isEmpty()){
				Buffer buffer = new Buffer();
				buffer.num = num * tree.getNumber();
				buffer.product = tree.getProduct();
				leaf.add(buffer);
			}else{
				findLeaf(num * tree.getNumber(), list, leaf);
			}
		}
	}
	
}
