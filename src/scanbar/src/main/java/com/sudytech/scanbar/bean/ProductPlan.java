package com.sudytech.scanbar.bean;

import javax.persistence.Entity;

@Entity
public class ProductPlan extends BaseBean{

	private String kindType;
	
	private int number;

	public String getKindType() {
		return kindType;
	}

	public void setKindType(String kindType) {
		this.kindType = kindType;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
