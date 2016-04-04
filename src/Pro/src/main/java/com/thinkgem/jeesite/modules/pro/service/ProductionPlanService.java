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
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.entity.ProductionPlan;
import com.thinkgem.jeesite.modules.pro.dao.ProductionPlanDao;

/**
 * 生产计划Service
 * @author Generate Tools
 * @version 2016-03-20
 */
@Component
@Transactional(readOnly = true)
public class ProductionPlanService extends BaseService {

	@Autowired
	private ProductionPlanDao productionPlanDao;
	
	public ProductionPlan get(String id) {
		return productionPlanDao.get(id);
	}
	
	public List<ProductionPlan> findAll(){
		String hql = "from ProductionPlan where delFlag=:p1";
		return productionPlanDao.find(hql, new Parameter(Product.DEL_FLAG_NORMAL));
	}
	
	public Page<ProductionPlan> find(Page<ProductionPlan> page, ProductionPlan productionPlan) {
		DetachedCriteria dc = productionPlanDao.createDetachedCriteria();
		if(StringUtils.isNotBlank(productionPlan.getName())){
			dc.add(Restrictions.like("name", "%"+productionPlan.getName()+"%"));
		}
		if(StringUtils.isNotBlank(productionPlan.getField1())){
			dc.add(Restrictions.like("field1", "%"+productionPlan.getField1()+"%"));
		}
		dc.add(Restrictions.eq(ProductionPlan.FIELD_DEL_FLAG, ProductionPlan.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("serialNum"));
		return productionPlanDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductionPlan productionPlan) {
		productionPlanDao.save(productionPlan);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		productionPlanDao.deleteById(id);
	}
	
}
