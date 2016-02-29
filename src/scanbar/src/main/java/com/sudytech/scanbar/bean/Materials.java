package com.sudytech.scanbar.bean;

import javax.persistence.Column;
import javax.persistence.Entity;

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
	
	private int status = -1;
	
	private int currentStep;

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

	public int getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(int currentStep) {
		this.currentStep = currentStep;
	}
}
