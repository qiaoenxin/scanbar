package com.sudytech.scanbar.web.jservice.imp;

import com.sudytech.scanbar.web.jservice.api.Interfaces;
import com.sudytech.scanbar.web.jservice.api.Interfaces.InterfaceDef;
import com.sudytech.scanbar.web.jservice.imp.SampleImpl.SampleRequest;
import com.sudytech.scanbar.web.jservice.imp.SampleImpl.SampleResponse;
import com.sudytech.scanbar.web.jservice.imp.SampleImpl.SampleService;

public class InterfaceConfig {
	
	public void init(){
		Interfaces interfaces = Interfaces.getInstance();
		
		interfaces.registerInterface(new InterfaceDef("sample", "/sample/helloworld", SampleRequest.class, SampleResponse.class, SampleService.class));
		
		
	}
	
	
}
