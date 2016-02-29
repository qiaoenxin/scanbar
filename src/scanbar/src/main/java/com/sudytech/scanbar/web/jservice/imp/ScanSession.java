package com.sudytech.scanbar.web.jservice.imp;

import com.sudytech.scanbar.bean.Device;
import com.sudytech.scanbar.bean.ProducingList;

public class ScanSession {

	private Device device;
	
	private ProducingList list;
	
	private String workerNO;

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public ProducingList getList() {
		return list;
	}

	public void setList(ProducingList list) {
		this.list = list;
	}

	public String getWorkerNO() {
		return workerNO;
	}

	public void setWorkerNO(String workerNO) {
		this.workerNO = workerNO;
	}
}
