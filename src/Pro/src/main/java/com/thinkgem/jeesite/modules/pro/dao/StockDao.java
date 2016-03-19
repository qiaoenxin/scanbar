/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.dao;

import org.springframework.stereotype.Repository;

import com.thinkgem.jeesite.common.persistence.BaseDao;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.modules.pro.entity.Stock;

/**
 * 库存管理DAO接口
 * @author Generate Tools
 * @version 2016-03-12
 */
@Repository
public class StockDao extends BaseDao<Stock> {

	public Stock getByProductId(String productId){
		return this.getByHql("from Stock where product.id=:p1",new Parameter(productId));
	}
}
