package com.thinkgem.jeesite.modules.pro.entity;

import com.thinkgem.jeesite.common.persistence.IdEntity;

public class Device extends IdEntity<Device>{
	private static final long serialVersionUID = -4460260433320333570L;

	private String deviceKey;
	
	private String serial;

	public String getDeviceKey() {
		return deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}
}
