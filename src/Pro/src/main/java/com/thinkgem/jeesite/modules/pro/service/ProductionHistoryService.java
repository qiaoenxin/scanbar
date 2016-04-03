/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.service;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.pro.entity.ProductionDetail;
import com.thinkgem.jeesite.modules.pro.entity.ProductionHistory;
import com.thinkgem.jeesite.modules.pro.dao.ProductionHistoryDao;
import com.thinkgem.jeesite.modules.sys.entity.User;

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
	
	@Autowired
	private ProductionDetailService detailService;
	
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
	
	public List<ProductionHistory> findByDetail(String detailId){
		DetachedCriteria criteria = productionHistoryDao.createDetachedCriteria();
		criteria.add(Restrictions.eq("productionDetail.id", detailId));
		return productionHistoryDao.find(criteria);
	}
	
	@Transactional
	public void saveHistory(ProductionDetail productionDetail){
		List<ProductionHistory> detailHistories = findByDetail(productionDetail.getId());
		for(ProductionHistory history: detailHistories){
			//重复扫描
			if(productionDetail.getStatus().equals(history.getStatus())){
				return;
			}
		}
		ProductionHistory history = new ProductionHistory();
		history.setStatus(productionDetail.getStatus());
		history.setProductionDetail(productionDetail);
		detailService.save(productionDetail);
		productionHistoryDao.save(history);
	}
}
