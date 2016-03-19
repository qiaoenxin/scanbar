/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.service;

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
	
	public List<ProductTree> find(String productId){
		ProductTree productTree = productTreeDao.getByHql("from ProductTree where product.id=:p1 and delFlag=:p2",new Parameter(productId,ProductTree.DEL_FLAG_NORMAL));
		if(productTree==null){
			return Lists.newArrayList();
		}
		DetachedCriteria dc = productTreeDao.createDetachedCriteria();
		dc.add(Restrictions.or(Restrictions.like("parentIds", "%,"+productTree.getId()+",%"),Restrictions.eq("id",productTree.getId())));
		dc.add(Restrictions.not(Restrictions.eq("id", ProductTree.SYS_ID)));
		dc.add(Restrictions.eq(ProductTree.FIELD_DEL_FLAG, ProductTree.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("createDate"));
		return productTreeDao.find(dc);
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
		if(productTree.getParent()!=null){
			productTree.setParentIds((ProductTree.SYS_ID.equals(productTree.getParent().getId())?"0,":productTree.getParentIds()) + productTree.getParent().getId() + ",");
		}
		
		productTreeDao.save(productTree);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		productTreeDao.deleteById(id);
	}
	
}
