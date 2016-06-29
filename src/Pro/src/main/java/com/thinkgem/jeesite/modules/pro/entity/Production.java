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
 * 指令中的产品
 * @author Generate Tools
 * @version 2016-03-12
 */
@Entity
@Table(name = "pro_production")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Production extends IdEntity<Production> {
	
	private static final long serialVersionUID = 1L;
	private ProductionPlan plan;
	private Product product;
	private int number;		//生产目标数量
	
	private int priority = 3;	//优先级	1:高;2:中;3:低
	
	private	String serialNum;	//生产指令编号+产品编号
	
	private int isProducing;    //0:未投产，1:投产
	
	private int completeNum;
	
	public static final int PRODUCTION_NO = 0; //未投产
	
	public static final int PRODUCTION_YES = 1;	//投产
	
	public Production() {
		super();
	}

	public Production(String id){
		this();
		this.id = id;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@NotNull
	public ProductionPlan getPlan() {
		return plan;
	}

	public void setPlan(ProductionPlan plan) {
		this.plan = plan;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	
	public int getCompleteNum() {
		return completeNum;
	}

	public void setCompleteNum(int completeNum) {
		this.completeNum = completeNum;
	}
	
	@Transient
	public void addCompleteNum(int completeNum){
		this.completeNum += completeNum;
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

	public int getIsProducing() {
		return isProducing;
	}

	public void setIsProducing(int isProducing) {
		this.isProducing = isProducing;
	}
	
}


