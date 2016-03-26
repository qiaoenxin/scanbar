/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.entity.ProductTree;
import com.thinkgem.jeesite.modules.pro.dao.ProductTreeDao;

/**
 * 产品流管理Service
 * @author Generate Tools
 * @version 2016-03-12
 */
@Component
@Transactional(readOnly = true)
public class ProductTreeService extends BaseService {

	@Autowired
	private ProductTreeDao productTreeDao;
	
	public ProductTree get(String id) {
		return productTreeDao.get(id);
	}
	
	public List<ProductTree> findAll(){
		return productTreeDao.findAll();
	}
	
	public List<ProductTree> findRoots(){
		DetachedCriteria dc = productTreeDao.createDetachedCriteria();
		dc.add(Restrictions.isNull("parent.id"));
		//dc.add(Restrictions.not(Restrictions.eq("id", ProductTree.SYS_ID)));
		dc.add(Restrictions.eq(ProductTree.FIELD_DEL_FLAG, ProductTree.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("createDate"));
		return productTreeDao.find(dc);
	}
	
	public List<ProductTree> findParentsByProductId(String productId){
		DetachedCriteria dc = productTreeDao.createDetachedCriteria();
		dc.add(Restrictions.eq("product.id", productId));
		//dc.add(Restrictions.not(Restrictions.eq("id", ProductTree.SYS_ID)));
		dc.add(Restrictions.eq(ProductTree.FIELD_DEL_FLAG, ProductTree.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("createDate"));
		return productTreeDao.find(dc);
	}
	
	public List<ProductTree> findChildrensByProductId(String productId){
		DetachedCriteria dc = productTreeDao.createDetachedCriteria();
		dc.add(Restrictions.eq("parent.id", productId));
		//dc.add(Restrictions.not(Restrictions.eq("id", ProductTree.SYS_ID)));
		dc.add(Restrictions.eq(ProductTree.FIELD_DEL_FLAG, ProductTree.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("createDate"));
		return productTreeDao.find(dc);
	}
	
	public List<String> hasChildren(){
		List<String> ids = productTreeDao.findBySql("SELECT DISTINCT t.`parent_id` FROM pro_product_tree t where t.`del_flag` = 0");
		List<String> list = new ArrayList<String>(ids);
		ids = productTreeDao.findBySql("SELECT DISTINCT t.`product_id` FROM pro_product_tree t WHERE t.`parent_id` IS NULL and t.`del_flag` = 0");
		list.addAll(ids);
		return list;
	}
	
	
	public Page<ProductTree> find(Page<ProductTree> page, ProductTree productTree) {
		DetachedCriteria dc = productTreeDao.createDetachedCriteria();
		if(productTree.getProduct()!=null && StringUtils.isNotBlank(productTree.getProduct().getSerialNum())){
			dc.add(Restrictions.eq("product.serialNum", productTree.getProduct().getSerialNum()));
		}
		dc.add(Restrictions.not(Restrictions.eq("id", ProductTree.SYS_ID)));
		dc.add(Restrictions.eq(ProductTree.FIELD_DEL_FLAG, ProductTree.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("createDate"));
		return productTreeDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductTree productTree) {
		if(productTree.getParent()==null || StringUtils.isBlank(productTree.getParent().getId())){
			productTree.setParent(null);
		}
		
		productTreeDao.save(productTree);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		productTreeDao.deleteById(id);
	}
	
	
	/**
	 * 根据父查询所有的子
	 * @param product
	 * @return
	 */
	public List<ProductTree> findSubTree(Product product){
		DetachedCriteria criteria = productTreeDao.createDetachedCriteria(Restrictions.eq("parent.id", product.getId()));
		List<ProductTree> list = productTreeDao.find(criteria);
		return list;
	}
}
