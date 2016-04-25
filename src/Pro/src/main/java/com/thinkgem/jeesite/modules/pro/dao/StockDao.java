/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.thinkgem.jeesite.common.persistence.BaseDao;
import com.thinkgem.jeesite.modules.pro.entity.Stock;

/**
 * 库存管理DAO接口
 * @author Generate Tools
 * @version 2016-03-12
 */
@Repository
public class StockDao extends BaseDao<Stock> {

	public Stock getByProductId(String productId){
		DetachedCriteria cr = createDetachedCriteria();
		cr.add(Restrictions.eq("product.id", productId));
		List<Stock> stocks = find(cr);
		return stocks.isEmpty() ? null : stocks.get(0);
	}
}
