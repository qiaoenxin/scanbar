package com.thinkgem.jeesite.modules.pro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.entity.ProductTree;
import com.thinkgem.jeesite.modules.pro.entity.ProductionDetail;
import com.thinkgem.jeesite.modules.pro.entity.StockHistory;

@Component
public class ScanStockService {

	
	@Autowired
	private StockHistoryService historyService;
	
	@Autowired
	private ProductService productService;
	
	
	
	/**
	 * 扫描入库
	 * @param detail
	 * @param subTrees
	 * @throws Exception
	 */
	@Transactional
	public void saveStock(ProductionDetail detail, List<ProductTree> subTrees) throws Exception{
		StockHistory stockHistory = new StockHistory();
		stockHistory.setType(StockHistory.TYPE_SCAN_ADD);
		stockHistory.setProduct(detail.getProductTree().getProduct());
		stockHistory.setProductionDetail(detail);
		int number  = detail.getNumber();
		stockHistory.setNumber(detail.getNumber());
		for(ProductTree tree: subTrees){
			Product subProduct = tree.getProduct();
			StockHistory subHistory = new StockHistory();
			subHistory.setNumber(tree.getNumber() * number);
			subHistory.setProduct(subProduct);
			subHistory.setType(StockHistory.TYPE_SCAN_DESC);
			subHistory.setProductionDetail(detail);
			historyService.save(subHistory);
		}
		historyService.save(stockHistory);
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
			StockHistory subHistory = new StockHistory();
			subHistory.setNumber(value);
			subHistory.setProduct(pro);
			subHistory.setType(StockHistory.TYPE_PRODUCING_DESC);
			subHistory.setProductionDetail(detail);
			historyService.save(subHistory);
		}
	}

}