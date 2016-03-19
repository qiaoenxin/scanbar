/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import com.thinkgem.jeesite.common.persistence.IdEntity;

/**
 * 出入库记录Entity
 * @author Generate Tools
 * @version 2016-03-12
 */
@Entity
@Table(name = "pro_stock_History")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StockHistory extends IdEntity<StockHistory> {
	
	
	private static final long serialVersionUID = 1L;
	private Product product;
	private String type;		//-1：出库；1：入库
	private int number;		//数量
	private String reason;	//原因
	private	ProductionDetail productionDetail;
	
	public StockHistory() {
		super();
	}

	public StockHistory(String id){
		this();
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="product_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@NotNull
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="production_detail_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public ProductionDetail getProductionDetail() {
		return productionDetail;
	}

	public void setProductionDetail(ProductionDetail productionDetail) {
		this.productionDetail = productionDetail;
	}
	
}


