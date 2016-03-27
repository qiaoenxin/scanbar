package com.thinkgem.jeesite.web;

import com.thinkgem.jeesite.common.jservice.api.Interfaces;
import com.thinkgem.jeesite.common.jservice.api.Interfaces.InterfaceDef;
import com.thinkgem.jeesite.web.Flow.FlowService;
import com.thinkgem.jeesite.web.Login.LoginService;
import com.thinkgem.jeesite.web.Logout.LogoutService;
import com.thinkgem.jeesite.web.Loss.LossService;
import com.thinkgem.jeesite.web.QuerySub.QuerySubService;
import com.thinkgem.jeesite.web.Scan.ScanService;
import com.thinkgem.jeesite.web.ScanFlow.ScanFlowService;

public class InterfaceConfig {
	
	public void init(){
		Interfaces interfaces = Interfaces.getInstance();
		
		//扫描入库接口
		interfaces.registerInterface(new InterfaceDef("scanSave", "/scanSave", ScanService.class));
		//工艺流扫描接口
		interfaces.registerInterface(new InterfaceDef("scanFlow", "/scanFlow", ScanFlowService.class));
		//登陆接口
		interfaces.registerInterface(new InterfaceDef("login", "/login", LoginService.class, false));
		//查询子树
		interfaces.registerInterface(new InterfaceDef("querySub", "/querySub", QuerySubService.class, false));
		//损失
		interfaces.registerInterface(new InterfaceDef("loss", "/loss", LossService.class, false));
		//登出
		interfaces.registerInterface(new InterfaceDef("logout", "/logout", LogoutService.class, false));
		//工序流
		interfaces.registerInterface(new InterfaceDef("flow", "/flow", FlowService.class, false));
	}
	
}
