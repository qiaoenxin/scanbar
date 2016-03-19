package com.thinkgem.jeesite.web;

import com.thinkgem.jeesite.common.jservice.api.Interfaces;
import com.thinkgem.jeesite.common.jservice.api.Interfaces.InterfaceDef;

public class InterfaceConfig {
	
	public void init(){
		Interfaces interfaces = Interfaces.getInstance();
		
		//用户初始化接口
		//interfaces.registerInterface(new InterfaceDef("userInit", "/userInit", UserInitRequest.class, UserInitResponse.class, UserInitService.class));
		
	}
	
	
}
