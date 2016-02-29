package com.sudytech.scanbar.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * 生产单据
 * @author mac
 */
@Entity
public class ProducingList extends BaseBean{

	@Column(nullable=false, unique= true)
	private String listNO;
	
	private int count;
	
	private int producingCount;
	
	private int step = 1;
	
	@ManyToOne
	private ProductPlan plan;

	public String getListNO() {
		return listNO;
	}

	public void setListNO(String listNO) {
		this.listNO = listNO;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getProducingCount() {
		return producingCount;
	}

	public void setProducingCount(int producingCount) {
		this.producingCount = producingCount;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public ProductPlan getPlan() {
		return plan;
	}

	public void setPlan(ProductPlan plan) {
		this.plan = plan;
	}
}
