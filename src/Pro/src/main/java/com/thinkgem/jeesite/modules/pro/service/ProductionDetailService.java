/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.service;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.pro.entity.ProductionDetail;
import com.thinkgem.jeesite.modules.pro.dao.ProductionDetailDao;

/**
 * 生产详情Service
 * @author Generate Tools
 * @version 2016-03-12
 */
@Component
@Transactional(readOnly = true)
public class ProductionDetailService extends BaseService {

	@Autowired
	private ProductionDetailDao productionDetailDao;
	
	public ProductionDetail get(String id) {
		return productionDetailDao.get(id);
	}
	
	
	public List<ProductionDetail> findByProductionId(String productionId){
		DetachedCriteria dc = productionDetailDao.createDetachedCriteria();
		dc.add(Restrictions.eq("production.id", productionId));
		dc.add(Restrictions.eq(ProductionDetail.FIELD_DEL_FLAG, ProductionDetail.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return productionDetailDao.find(dc);
	}
	
	public Page<ProductionDetail> find(Page<ProductionDetail> page, ProductionDetail productionDetail) {
		DetachedCriteria dc = productionDetailDao.createDetachedCriteria();
		dc.createAlias("production", "pdt");
		dc.createAlias("pdt.product", "pd");
		dc.createAlias("productTree", "pdtree", JoinType.LEFT_OUTER_JOIN);
		dc.createAlias("pdtree.product", "treepd", JoinType.LEFT_OUTER_JOIN);
		if(StringUtils.isNotBlank(productionDetail.getSerialNum())){
			dc.add(Restrictions.eq("serialNum", productionDetail.getSerialNum()));
		}
		if(productionDetail.getProduction()!=null && StringUtils.isNotBlank(productionDetail.getProduction().getSerialNum())){
			dc.add(Restrictions.eq("pdt.serialNum", productionDetail.getProduction().getSerialNum()));
		}
		
		if(null != productionDetail.getProduction() && null != productionDetail.getProduction().getProduct() && StringUtils.isNotBlank(productionDetail.getProduction().getProduct().getName())){
			String name = productionDetail.getProduction().getProduct().getName();
			dc.add(Restrictions.disjunction().add( Restrictions.like("treepd.name", "%"+ name +"%"))
					.add(Restrictions.like("pd.name", "%"+ name +"%")));
		}
		if(StringUtils.isNotBlank(productionDetail.getStatus())){
			dc.add(Restrictions.eq("status", productionDetail.getStatus()));
		}
		
		dc.add(Restrictions.eq(ProductionDetail.FIELD_DEL_FLAG, ProductionDetail.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("serialNum"));
		return productionDetailDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductionDetail productionDetail) {
		productionDetailDao.save(productionDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(List<ProductionDetail> productionDetailList) {
		for(ProductionDetail productionDetail : productionDetailList){
			productionDetailDao.save(productionDetail);
		}
	}
	
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		productionDetailDao.deleteById(id);
	}
	
	public ProductionDetail findByDetailNo(String detailNO){
		DetachedCriteria detachedCriteria = productionDetailDao.createDetachedCriteria(Restrictions.eq("serialNum", detailNO));
		List<ProductionDetail> list = productionDetailDao.find(detachedCriteria);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
	
}
