package com.sudytech.scanbar.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 原材料
 * @author mac
 *
 */
@Entity
public class Materials extends BaseBean{

	@Column(unique=true,nullable=false)
	private String barCode;
	
	private String name;
	
	private int status;

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
