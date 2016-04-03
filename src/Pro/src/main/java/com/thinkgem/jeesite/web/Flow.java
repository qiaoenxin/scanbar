package com.thinkgem.jeesite.web;


import java.util.List;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.thinkgem.jeesite.common.jservice.api.BasicService;
import com.thinkgem.jeesite.common.jservice.api.ParameterDef;
import com.thinkgem.jeesite.common.jservice.api.ReturnCode;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.entity.ProductTree;
import com.thinkgem.jeesite.modules.pro.entity.ProductionDetail;
import com.thinkgem.jeesite.modules.pro.service.ProductTreeService;
import com.thinkgem.jeesite.modules.pro.service.ProductionDetailService;
import com.thinkgem.jeesite.modules.pro.service.ScanStockService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.service.DictService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;


public class Flow {

	
	public static class FlowService extends BasicService<Request, Response>{
		
		
		@Override
		protected void service(Request request, Response response) {
			try {
				List<Dict> flows = DictUtils.getDictList("flow_type");
				response.setData(flows);
				response.setJsonFilter(new SimplePropertyPreFilter(Dict.class, "value", "label"));
			} catch (Exception e) {
				logger.error(e.toString(), e);
				response.setResultAndReason(ReturnCode.DB_ERROR, "获取流程错误");
			}
		}
	}
	
	public static class Request extends com.thinkgem.jeesite.common.jservice.api.Request{
		
	}
	
	public static class Response extends com.thinkgem.jeesite.common.jservice.api.Response{
		
	}
}
