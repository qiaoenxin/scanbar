package com.sudytech.scanbar.bean;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ProducingStep extends BaseBean{
	
	private String step;
	
	private String workerNO;
	
	@ManyToOne
	private Materials materials;
	
	@ManyToOne
	private ProducingList list;
	
	@ManyToOne
	private Device device;

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getWorkerNO() {
		return workerNO;
	}

	public void setWorkerNO(String workerNO) {
		this.workerNO = workerNO;
	}

	public Materials getMaterials() {
		return materials;
	}

	public void setMaterials(Materials materials) {
		this.materials = materials;
	}

	public ProducingList getList() {
		return list;
	}

	public void setList(ProducingList list) {
		this.list = list;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
}
