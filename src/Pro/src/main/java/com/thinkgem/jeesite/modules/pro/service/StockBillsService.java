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
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.pro.entity.StockBills;
import com.thinkgem.jeesite.modules.pro.dao.StockBillsDao;

/**
 * 扎帐Service
 * @author Generate Tools
 * @version 2016-04-03
 */
@Component
@Transactional(readOnly = true)
public class StockBillsService extends BaseService {

	@Autowired
	private StockBillsDao stockBillsDao;
	
	public StockBills get(String id) {
		return stockBillsDao.get(id);
	}
	
	public Page<StockBills> find(Page<StockBills> page, StockBills stockBills) {
		DetachedCriteria dc = stockBillsDao.createDetachedCriteria();
		if (stockBills.getProduct()!=null && StringUtils.isNotEmpty(stockBills.getProduct().getName())){
			dc.add(Restrictions.like("name", "%"+stockBills.getProduct().getName()+"%"));
		}
		if (stockBills.getCreateDate()!=null){
			Date b =  DateUtils.getDateStart(stockBills.getCreateDate());
			Date e = DateUtils.getDateEnd(stockBills.getCreateDate());
			dc.add(Restrictions.between("createDate", b, e));
		}
		
		dc.add(Restrictions.eq(StockBills.FIELD_DEL_FLAG, StockBills.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return stockBillsDao.find(page, dc);
	}
	
	
	@Transactional(readOnly = false)
	public void save(StockBills stockBills) {
		stockBillsDao.save(stockBills);
	}
	
	@Transactional(readOnly = false)
	public void save(List<StockBills> stockBillsList) {
		stockBillsDao.save(stockBillsList);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		stockBillsDao.deleteById(id);
	}
	
}
