/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.service;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.entity.Product.Flow;
import com.thinkgem.jeesite.modules.pro.entity.ProductionDetail;
import com.thinkgem.jeesite.modules.pro.entity.ProductionHistory;
import com.thinkgem.jeesite.modules.pro.entity.StockHistory;
import com.thinkgem.jeesite.modules.pro.dao.ProductionHistoryDao;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 产品流历史Service
 * @author Generate Tools
 * @version 2016-03-12
 */
@Component
@Transactional(readOnly = true)
public class ProductionHistoryService extends BaseService {

	@Autowired
	private ProductionHistoryDao productionHistoryDao;
	
	@Autowired
	private ProductionDetailService detailService;
	
	@Autowired
	private StockHistoryService stockHistoryService;
	
	@Autowired
	private ProductService productService;
	
	public ProductionHistory get(String id) {
		return productionHistoryDao.get(id);
	}
	
	public Page<ProductionHistory> find(Page<ProductionHistory> page, ProductionHistory productionHistory) {
		DetachedCriteria dc = productionHistoryDao.createDetachedCriteria();
		if(productionHistory.getProductionDetail()!=null && productionHistory.getProductionDetail().getProduction()!=null && productionHistory.getProductionDetail().getProduction().getProduct()!=null){
			String name =  productionHistory.getProductionDetail().getProduction().getProduct().getName();
			if(StringUtils.isNotBlank(name)){
				dc.createAlias("productionDetail", "de");
				dc.createAlias("de.production", "pn");
				dc.createAlias("pn.product", "pr");
				dc.createAlias("de.productTree", "tr", JoinType.LEFT_OUTER_JOIN);
				dc.createAlias("tr.product", "pr2");
				dc.add(Restrictions.or(Restrictions.like("pr.name", "%"+name+"%"), 
						Restrictions.like("pr2.name", "%"+name+"%")));
			}
		}
		if(StringUtils.isNotBlank(productionHistory.getStatus())){
			dc.add(Restrictions.eq("status", productionHistory.getStatus()));
		}
		
		dc.add(Restrictions.eq(ProductionHistory.FIELD_DEL_FLAG, ProductionHistory.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return productionHistoryDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductionHistory productionHistory) {
		productionHistoryDao.save(productionHistory);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		productionHistoryDao.deleteById(id);
	}
	
	public List<ProductionHistory> findByDetail(String detailId){
		DetachedCriteria criteria = productionHistoryDao.createDetachedCriteria();
		criteria.add(Restrictions.eq("productionDetail.id", detailId));
		return productionHistoryDao.find(criteria);
	}
	
	@Transactional
	public void saveHistory(ProductionDetail productionDetail, Flow from){
		List<ProductionHistory> detailHistories = findByDetail(productionDetail.getId());
		for(ProductionHistory history: detailHistories){
			//重复扫描
			if(productionDetail.getStatus().equals(history.getStatus())){
				return;
			}
		}
		ProductionHistory history = new ProductionHistory();
		history.setStatus(productionDetail.getStatus());
		history.setProductionDetail(productionDetail);
		detailService.save(productionDetail);
		productionHistoryDao.save(history);
		
		StockHistory stockHistory = new StockHistory();
		
		String status = productionDetail.getStatus();
		if(!(status.equals(Product.FLOW_D) || status.equals(Product.FLOW_D))){
			return;
		}
		Product producnt = null;
		if(productionDetail.getProductTree() != null){
			producnt = productionDetail.getProductTree().getProduct();
		}else{
			producnt = productionDetail.getProduction().getProduct();
		}
		if(from != null){
			producnt = productService.findByName(from.getField1());
		}
		
		stockHistory.setProduct(producnt);
		stockHistory.setNumber(productionDetail.getNumber());
		stockHistory.setReason("");
		stockHistory.setType(StockHistory.TYPE_SCAN_ADD);
		stockHistoryService.save(stockHistory);
	}
}
