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
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.entity.Stock;
import com.thinkgem.jeesite.modules.pro.dao.ProductDao;
import com.thinkgem.jeesite.modules.pro.dao.StockDao;

/**
 * 产品管理Service
 * @author Generate Tools
 * @version 2016-03-12
 */
@Component
@Transactional(readOnly = true)
public class ProductService extends BaseService {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private StockDao stockDao;
	
	public Product get(String id) {
		return productDao.get(id);
	}
	
	public List<Product> findAll(){
		return productDao.findAll();
	}
	
	public Page<Product> find(Page<Product> page, Product product) {
		DetachedCriteria dc = productDao.createDetachedCriteria();
		if(StringUtils.isNotBlank(product.getSerialNum())){
			dc.add(Restrictions.eq("serialNum", product.getSerialNum()));
		}
		dc.add(Restrictions.eq(Product.FIELD_DEL_FLAG, Product.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return productDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Product product) {
		boolean isNew = false;
		if(StringUtils.isBlank(product.getId())){
			isNew = true;
		}
		productDao.save(product);
		if(isNew){
			Stock stock = new Stock();
			stock.setProduct(product);
			stockDao.save(stock);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		productDao.deleteById(id);
	}
	
}
