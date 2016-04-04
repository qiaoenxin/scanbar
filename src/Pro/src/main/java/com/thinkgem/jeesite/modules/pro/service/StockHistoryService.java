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
import com.thinkgem.jeesite.common.schedule.StockBillsSchedule;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.entity.Stock;
import com.thinkgem.jeesite.modules.pro.entity.StockHistory;
import com.thinkgem.jeesite.modules.pro.dao.StockDao;
import com.thinkgem.jeesite.modules.pro.dao.StockHistoryDao;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 出入库记录Service
 * @author Generate Tools
 * @version 2016-03-12
 */
@Component
@Transactional(readOnly = true)
public class StockHistoryService extends BaseService {

	@Autowired
	private StockHistoryDao stockHistoryDao;
	
	@Autowired
	private StockDao stockDao;
	
	public StockHistory get(String id) {
		return stockHistoryDao.get(id);
	}
	
	public Page<StockHistory> find(Page<StockHistory> page, StockHistory stockHistory) {
		DetachedCriteria dc = stockHistoryDao.createDetachedCriteria();
		if(StringUtils.isNotBlank(stockHistory.getType())){
			dc.add(Restrictions.eq("type", stockHistory.getType()));
		}
		if(StringUtils.isNotBlank(stockHistory.getReason())){
			dc.add(Restrictions.like("reason", "%,"+stockHistory.getReason()+",%"));
		}
		if(stockHistory.getProduct()!=null && StringUtils.isNotBlank(stockHistory.getProduct().getSerialNum())){
			dc.add(Restrictions.like("product.serialNum", "%,"+stockHistory.getProduct().getSerialNum()+",%"));
		}
		
		dc.add(Restrictions.eq(StockHistory.FIELD_DEL_FLAG, StockHistory.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return stockHistoryDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public synchronized void save(StockHistory stockHistory) throws Exception {
		
		//判断是否是正在扎帐
		if(StockBillsSchedule.isRunning){
			throw new Exception("扎帐中...，不允许入库操作");
		}
		
		//修改库存
		Product product = stockHistory.getProduct();
		Stock stock = stockDao.getByProductId(product.getId());
		if(stock==null){
			stock = new Stock();
			stock.setProduct(product);
		}
		int number = stock.getNumber();
		Dict dict = DictUtils.getDict(stockHistory.getType(), "stock_type", "");
		if(dict==null){
			throw new Exception();
		}
		if(StringUtils.toInteger(dict.getDescription())>0){//入库
//			stockHistory.getNumber();
		}else{//出库
			stockHistory.setNumber(-stockHistory.getNumber());
		}
		number += stockHistory.getNumber();
		stock.setNumber(number);
		stockDao.save(stock);
		
		stockHistoryDao.save(stockHistory);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		stockHistoryDao.deleteById(id);
	}
	
	public List<StockHistory> findByDetailId(String id){
		DetachedCriteria criteria = stockHistoryDao.createDetachedCriteria();
		criteria.add(Restrictions.eq("productionDetail.id", id));
		return stockHistoryDao.find(criteria);
	}
}
