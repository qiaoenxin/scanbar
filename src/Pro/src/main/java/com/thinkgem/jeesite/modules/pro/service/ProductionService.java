/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.service;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.pro.entity.Production;
import com.thinkgem.jeesite.modules.pro.entity.ProductionPlan;
import com.thinkgem.jeesite.modules.pro.dao.ProductionDao;

/**
 * 生产管理Service
 * @author Generate Tools
 * @version 2016-03-12
 */
@Component
@Transactional(readOnly = true)
public class ProductionService extends BaseService {

	@Autowired
	private ProductionDao productionDao;
	
	public Production get(String id) {
		return productionDao.get(id);
	}
	
	
	public List<Production> findByPlanId(String planId) {
		DetachedCriteria dc = productionDao.createDetachedCriteria();
		dc.add(Restrictions.eq("plan.id", planId));
		dc.add(Restrictions.eq(Production.FIELD_DEL_FLAG, Production.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return productionDao.find(dc);
	}
	
	public Page<Production> find(Page<Production> page, Production production) {
		DetachedCriteria dc = productionDao.createDetachedCriteria();
		dc.createAlias("plan", "pl");
		if(StringUtils.isNotBlank(production.getSerialNum())){
			dc.add(Restrictions.eq("serialNum", production.getSerialNum()));
		}
		if(production.getPlan()!=null && StringUtils.isNotBlank(production.getPlan().getSerialNum())){
			dc.add(Restrictions.eq("pl.serialNum", production.getPlan().getSerialNum()));
		}
		if(production.getProduct()!=null && StringUtils.isNotBlank(production.getProduct().getName())){
			dc.createAlias("product", "pr");
			dc.add(Restrictions.like("pr.name", "%"+production.getProduct().getName()+"%"));
		}
		if(production.getPriority()!=0){
			dc.add(Restrictions.eq("priority", production.getPriority()));
		}
		
		dc.add(Restrictions.eq(Production.FIELD_DEL_FLAG, Production.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("pl.serialNum"));
		dc.addOrder(Order.asc("serialNum"));
		return productionDao.find(page, dc);
	}
	
	@Transactional
	public void updateFinishCount(int number, String id){
		String hql = "update Production p set p.completeNum = p.completeNum + :num where p.id = :id";
		productionDao.update(hql, new Parameter(new Object[][]{{"num", number}, {"id", id}}));
	}
	
	@Transactional(readOnly = false)
	public void save(Production production) {
		productionDao.clear();
		productionDao.save(production);
	}
	
	@Transactional(readOnly = false)
	public void save(List<Production> productions) {
		productionDao.clear();
		productionDao.save(productions);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		productionDao.deleteById(id);
	}

	@Transactional(readOnly = false)
	public void deleteByFlag() {
		String hql = "delete Production p where p.delFlag="+Production.DEL_FLAG_DELETE;
		productionDao.update(hql);
	}
	
	@Transactional(readOnly = false)
	public void deleteByPlan(String planId) {
		String hql = "delete Production p where p.plan.id=:p1";
		productionDao.update(hql,new Parameter(planId));
	}
	
	@Transactional
	public synchronized void updateFinishNum(int num, String id){
		Production production = productionDao.get(id);
		production.setCompleteNum(production.getCompleteNum() + num);
		productionDao.save(production);
	}
	
}
