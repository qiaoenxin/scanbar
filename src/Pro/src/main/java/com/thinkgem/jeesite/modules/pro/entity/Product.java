/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.thinkgem.jeesite.common.persistence.IdEntity;

/**
 * 产品管理Entity
 * @author Generate Tools
 * @version 2016-03-12
 */
@Entity
@Table(name = "pro_product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product extends IdEntity<Product> {
	
	private static final long serialVersionUID = 1L;

	private String serialNum;//编号
	private int snpNum;		 //snp数量
	private String flow;	//功序流
	
	public Product() {
		super();
	}

	public Product(String id){
		this();
		this.id = id;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public int getSnpNum() {
		return snpNum;
	}

	public void setSnpNum(int snpNum) {
		this.snpNum = snpNum;
	}

	public String getFlow() {
		return flow;
	}

	public void setFlow(String flow) {
		this.flow = flow;
	}

	
}


