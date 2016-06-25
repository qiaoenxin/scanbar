/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.thinkgem.jeesite.common.persistence.IdEntity;

/**
 * 生产详情Entity
 * @author Generate Tools
 * @version 2016-03-12
 */
@Entity
@Table(name = "pro_production_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductionDetail extends IdEntity<ProductionDetail> {
	
	private static final long serialVersionUID = 1L;
	private Production production;//生产
	private	String serialNum;	//编号	生产指令中的产品编号 + xxxx（4位数字）
	private ProductTree productTree;	//产品
	private String status;		//状态
	private int number;			//数量
	private String data;		//数据
	
	
	
	private int remainder;
	
	public ProductionDetail() {
		super();
	}

	public ProductionDetail(String id){
		this();
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="production_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@NotNull
	public Production getProduction() {
		return production;
	}

	public void setProduction(Production production) {
		this.production = production;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="product_tree_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public ProductTree getProductTree() {
		return productTree;
	}

	public void setProductTree(ProductTree productTree) {
		this.productTree = productTree;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	
	@Transient
	public int getRemainder() {
		return remainder;
	}

	public void setRemainder(int remainder) {
		this.remainder = remainder;
	}
	
	
	
	
	
}


