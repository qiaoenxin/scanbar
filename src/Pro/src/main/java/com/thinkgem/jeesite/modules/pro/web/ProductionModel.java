package com.thinkgem.jeesite.modules.pro.web;

import java.util.List;

import com.thinkgem.jeesite.modules.pro.entity.Production;

public class ProductionModel {
	
	private List<Production> productionList;

	public ProductionModel() {
		super();
	}


	public ProductionModel(List<Production> productionList) {
		super();
		this.productionList = productionList;
	}

	public List<Production> getProductionList() {
		return productionList;
	}

	public void setProductionList(List<Production> productionList) {
		this.productionList = productionList;
	}

}
