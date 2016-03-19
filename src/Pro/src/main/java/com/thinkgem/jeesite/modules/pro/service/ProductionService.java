/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.service;

import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.pro.entity.Production;
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
	
	public Page<Production> find(Page<Production> page, Production production) {
		DetachedCriteria dc = productionDao.createDetachedCriteria();
		if(StringUtils.isNotBlank(production.getSerialNum())){
			dc.add(Restrictions.eq("serialNum", production.getSerialNum()));
		}
		if(production.getProduct()!=null && StringUtils.isNotBlank(production.getProduct().getSerialNum())){
			dc.add(Restrictions.eq("product.serialNum", production.getProduct().getSerialNum()));
		}
		if(production.getPriority()!=0){
			dc.add(Restrictions.eq("priority", production.getPriority()));
		}
		
		dc.add(Restrictions.eq(Production.FIELD_DEL_FLAG, Production.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return productionDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Production production) {
		if(StringUtils.isBlank(production.getSerialNum())){
			String serialNum = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss")+RandomUtils.nextInt(10000);
			production.setSerialNum(serialNum);
		}
		productionDao.clear();
		productionDao.save(production);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		productionDao.deleteById(id);
	}
	
}
