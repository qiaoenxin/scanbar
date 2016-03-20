/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.pro.entity.Stock;
import com.thinkgem.jeesite.modules.pro.dao.StockDao;

/**
 * 库存管理Service
 * @author Generate Tools
 * @version 2016-03-12
 */
@Component
@Transactional(readOnly = true)
public class StockService extends BaseService {

	@Autowired
	private StockDao stockDao;
	
	public Stock get(String id) {
		return stockDao.get(id);
	}
	
	public Stock getByProductId(String productId) {
		return stockDao.getByHql("from Stock where product.id=:p1",new Parameter(productId));
	}
	
	public Page<Stock> find(Page<Stock> page, Stock stock) {
		DetachedCriteria dc = stockDao.createDetachedCriteria();
		if(stock.getProduct()!=null && StringUtils.isNotBlank(stock.getProduct().getSerialNum())){
			dc.add(Restrictions.like("product.serialNum", "%,"+stock.getProduct().getSerialNum()+",%"));
		}
		
		dc.add(Restrictions.eq(Stock.FIELD_DEL_FLAG, Stock.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return stockDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Stock stock) {
		stockDao.save(stock);
	}
	
	@Transactional(readOnly = false)
	public void updateUseNumber(List<String> productIds,List<Integer> useNumbers) {
		int index = 0;
		for(String productId : productIds){
			Stock stock = getByProductId(productId);
			int useNumber = stock.getUseNumber() + useNumbers.get(index);
			stock.setUseNumber(useNumber);
			stockDao.save(stock);
			index += 1;
		}
	}
	
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		stockDao.deleteById(id);
	}
	
}
