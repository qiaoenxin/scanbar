package com.sudytech.scanbar.web.jservice.imp;

import com.sudytech.scanbar.web.jservice.api.Interfaces;
import com.sudytech.scanbar.web.jservice.api.Interfaces.InterfaceDef;
import com.sudytech.scanbar.web.jservice.imp.Scan.ScanImpl;
import com.sudytech.scanbar.web.jservice.imp.Scan.ScanRequest;
import com.sudytech.scanbar.web.jservice.imp.Scan.ScanResponse;

public class InterfaceConfig {
	
	public void init(){
		Interfaces interfaces = Interfaces.getInstance();
		
		interfaces.registerInterface(new InterfaceDef("scan", "/scan", ScanRequest.class, ScanResponse.class, ScanImpl.class));
		
		
	}
	
	
}
