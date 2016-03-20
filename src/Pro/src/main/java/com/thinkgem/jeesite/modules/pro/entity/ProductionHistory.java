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
 * 生产历史Entity
 * @author Generate Tools
 * @version 2016-03-12
 */
@Entity
@Table(name = "pro_production_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductionHistory extends IdEntity<ProductionHistory> {
	
	private static final long serialVersionUID = 1L;
	private ProductionDetail productionDetail;//生产
	private String status;		//状态

	public ProductionHistory() {
		super();
	}

	public ProductionHistory(String id){
		this();
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="detail_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@NotNull
	public ProductionDetail getProductionDetail() {
		return productionDetail;
	}

	public void setProductionDetail(ProductionDetail productionDetail) {
		this.productionDetail = productionDetail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}


