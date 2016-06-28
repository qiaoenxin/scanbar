/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.thinkgem.jeesite.common.persistence.IdEntity;

/**
 * 生产指令
 * 
 * @author Generate Tools
 * @version 2016-03-20
 */
@Entity
@Table(name = "pro_production_plan")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductionPlan extends IdEntity<ProductionPlan> {

	private static final long serialVersionUID = 1L;

	private Date endDate; // 计划完成时间
	private String serialNum;//指令编号
	

	public ProductionPlan() {
		super();
	}

	public ProductionPlan(String id) {
		this();
		this.id = id;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}


}
