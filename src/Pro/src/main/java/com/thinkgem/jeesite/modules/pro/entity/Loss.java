/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.persistence.IdEntity;
import com.thinkgem.jeesite.modules.pro.dao.ProductDao;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 损失Entity
 * @author Generate Tools
 * @version 2016-07-03
 */
@Entity
@Table(name = "pro_loss")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Loss extends IdEntity<Loss> {
	
	private static final long serialVersionUID = 1L;

	private Product product;
	
	private int number;
	
	private ProductionDetail detail;
	
	public Loss() {
		super();
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@NotNull
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "detail_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@NotNull
	public ProductionDetail getDetail() {
		return detail;
	}

	public void setDetail(ProductionDetail detail) {
		this.detail = detail;
	}
}


