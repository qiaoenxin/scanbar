/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.service;

import java.nio.charset.Charset;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.security.Digests;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.CryptoUtils.Coder;
import com.thinkgem.jeesite.common.utils.CryptoUtils.Hex;
import com.thinkgem.jeesite.common.utils.CryptoUtils.RSATool;
import com.thinkgem.jeesite.modules.pro.entity.Device;
import com.thinkgem.jeesite.modules.pro.dao.DeviceDao;

/**
 * 生产计划Service
 * @author Generate Tools
 * @version 2016-04-03
 */
@Component
@Transactional(readOnly = true)
public class DeviceService extends BaseService {
	private static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAt5gh8t2t75oleaQqE9SGrz3dzvswtUJzTGCHFSCOqLj+NHsKFYHTYKT+HanQfE3aSntGhFg4sbvGf5kTYVoJJFmoRBIDaEXeADHlSrEIhioN71yn+I2hZsqxR4jJPSS/hPXkkZAcwcsRysSQYyqJvB5s4CLjWse46rKKnuovKj3pKRSUggIQTHiOspgK5tyRjiLqqVec/Uo9zcMAPBveoLKfQO2BGRW6rhEJS5MWBahgb5zZ/ecc6FOZay6Zh7roayRSj4829xVaDbLvvZgNIIiH+uE9Px0TO4t0G7zz6RjZB8PjDdDpEFbMUZ1Ra6PWLoWpurnqSvjRIyiRKWohKQIDAQAB";
	
	@Autowired
	private DeviceDao deviceDao;
	
	public Device get(String id) {
		return deviceDao.get(id);
	}
	
	public Page<Device> find(Page<Device> page, Device device) {
		DetachedCriteria dc = deviceDao.createDetachedCriteria();
		dc.add(Restrictions.eq(Device.FIELD_DEL_FLAG, Device.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return deviceDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Device device) {
		deviceDao.save(device);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		deviceDao.deleteById(id);
	}
	
	public Device findByDeviceKey(String deviceKey){
		DetachedCriteria dc = deviceDao.createDetachedCriteria();
		dc.add(Restrictions.eq("deviceKey", deviceKey));
		List<Device> list = deviceDao.find(dc);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
	
	public String getDevice(String serial){
		byte[] sha1 = Digests.sha1(serial.getBytes());
		return Hex.toHexString(sha1);
	}
	
	/**
	 * 设备号校验
	 * @param device
	 * @return
	 * @throws Exception 
	 */
	public boolean valid(Device device) {
		try {
			Charset charset = Charset.forName("UTF-8");
			byte[] sha1 = Digests.sha1(device.getSerial().getBytes(charset));
			if(!device.getDeviceKey().equals(Hex.toHexString(sha1))){
				return false;
			}
			String serial = device.getSerial();
			int index = serial.indexOf("|");
			String header = serial.substring(0, index);
			String code = serial.substring(index + 1);
			byte[] checkCode = RSATool.decryptByPublicKey(Coder.decryptBASE64(code), publicKey);
			if(header.equals(new String(checkCode))){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
