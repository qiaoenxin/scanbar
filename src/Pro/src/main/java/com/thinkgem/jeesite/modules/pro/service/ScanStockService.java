package com.thinkgem.jeesite.modules.pro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.entity.Product.Flow;
import com.thinkgem.jeesite.modules.pro.entity.ProductTree;
import com.thinkgem.jeesite.modules.pro.entity.Production;
import com.thinkgem.jeesite.modules.pro.entity.ProductionDetail;
import com.thinkgem.jeesite.modules.pro.entity.StockHistory;

@Component
public class ScanStockService {

	
	@Autowired
	private StockHistoryService historyService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductionService productionService;
	
	
	
	/**
	 * 扫描入库
	 * @param detail
	 * @param subTrees
	 * @throws Exception
	 */
	@Transactional
	public void saveStock(ProductionDetail detail, String subName, String subCode, List<ProductTree> subTrees) throws Exception{
		String reason = "生产扫描";
		
		//判断是否重复扫描
		List<StockHistory> detailScaned = historyService.findByDetailId(detail.getId());
		if(detailScaned.size() > 0){
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
	 * @throws Exception
	 */
	@Transactional
	public void saveStock(ProductionDetail detail, String[] products) throws Exception{
		for(String pruduct: products){
			String[] splits = pruduct.split(",");
			Product pro = productService.get(splits[0]);
			int value = StringUtils.toInteger(splits[1]);
			if(value == 0){
				continue;
			}
			StockHistory subHistory = new StockHistory();
			subHistory.setNumber(value);
			subHistory.setProduct(pro);
			subHistory.setType(StockHistory.TYPE_PRODUCING_DESC);
			subHistory.setProductionDetail(detail);
			historyService.save(subHistory);
		}
	}

}
