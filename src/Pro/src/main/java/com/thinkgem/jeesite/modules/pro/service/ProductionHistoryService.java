/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.pro.entity.ProductionHistory;
import com.thinkgem.jeesite.modules.pro.dao.ProductionHistoryDao;

/**
 * 产品流历史Service
 * @author Generate Tools
 * @version 2016-03-12
 */
@Component
@Transactional(readOnly = true)
public class ProductionHistoryService extends BaseService {

	@Autowired
	private ProductionHistoryDao productionHistoryDao;
	
	public ProductionHistory get(String id) {
		return productionHistoryDao.get(id);
	}
	
	public Page<ProductionHistory> find(Page<ProductionHistory> page, ProductionHistory productionHistory) {
		DetachedCriteria dc = productionHistoryDao.createDetachedCriteria();
		if(productionHistory.getProductionDetail()!=null && productionHistory.getProductionDetail().getProduction()!=null && productionHistory.getProductionDetail().getProduction().getPlan()!=null && productionHistory.getProductionDetail().getProduction().getPlan().getProduct()!=null){
			String serialNum =  productionHistory.getProductionDetail().getProduction().getPlan().getProduct().getSerialNum();
			if(StringUtils.isNotBlank(serialNum)){
				dc.add(Restrictions.eq("productionDetail.production.plan.product.serialNum", serialNum));
			}
		}
		if(StringUtils.isNotBlank(productionHistory.getStatus())){
			dc.add(Restrictions.eq("status", productionHistory.getStatus()));
		}
		
		dc.add(Restrictions.eq(ProductionHistory.FIELD_DEL_FLAG, ProductionHistory.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return productionHistoryDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductionHistory productionHistory) {
		productionHistoryDao.save(productionHistory);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		productionHistoryDao.deleteById(id);
	}
	
}
