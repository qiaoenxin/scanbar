package com.sudytech.scanbar.bean;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * 生产单据
 * @author mac
 */
@Entity
public class ProducingList extends BaseBean{

	private String listNO;
	
	private int count;
	
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
}
