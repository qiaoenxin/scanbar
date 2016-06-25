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
		String hql = "from Product where delFlag=:p1";
		return productDao.find(hql, new Parameter(Product.DEL_FLAG_NORMAL));
	}
	
	public Page<Product> find(Page<Product> page, Product product) {
		DetachedCriteria dc = productDao.createDetachedCriteria();
		if(StringUtils.isNotBlank(product.getName())){
			dc.add(Restrictions.like("name", "%"+product.getName()+"%"));
		}
		if(StringUtils.isNotBlank(product.getField1())){
			dc.add(Restrictions.eq("field1", product.getField1()));
		}
		dc.add(Restrictions.eq(Product.FIELD_DEL_FLAG, Product.DEL_FLAG_NORMAL));
		return productDao.find(page, dc);
	}
	
	public List<Product> findByName(String name) {
		DetachedCriteria dc = productDao.createDetachedCriteria();
		dc.add(Restrictions.eq("name", name));
		return productDao.find(dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Product product) {
		boolean isNew = false;
		if(StringUtils.isBlank(product.getId())){
			isNew = true;
		}
		productDao.save(product);
		if(isNew){
			productDao.save(product);
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
