package com.sudytech.scanbar.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
public class Device extends BaseBean{

	private String deviceKey;
	
	private int status;
	
	private Date prevLoginTime;

	public String getDeviceKey() {
		return deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getPrevLoginTime() {
		return prevLoginTime;
	}

	public void setPrevLoginTime(Date prevLoginTime) {
		this.prevLoginTime = prevLoginTime;
	}
}
