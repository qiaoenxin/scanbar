package com.thinkgem.jeesite.modules.pro.init;

import java.io.File;
import java.util.List;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.drew.lang.StringUtil;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.service.ProductService;

public class InitProduct {

	private static ProductService productService = SpringContextHolder.getBean(ProductService.class);
	
	private static ResourceLoader resourceLoader = new DefaultResourceLoader();
	
	public void init(){
		try {
			
			Resource resource = resourceLoader.getResource("data.xlsx");
			File file = resource.getFile();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProductExcel> list = ei.getDataList(ProductExcel.class);
			System.out.println(list.size());
			for(ProductExcel pro : list){
				try {
					String proName = pro.getProName();
					String proNum = pro.getProNum();
					String filed1 = pro.getField1();
					String dmName = pro.getDmName();
					String dmNum = pro.getDmNum();
					String wqName = pro.getWqName();
					String wqNum = pro.getWqNum();
					
					
					if(StringUtils.isBlank(proName)){
						continue;
					}
					
					
					Product product = new Product();
					product.setName(proName);
					product.setSerialNum(proNum);
					product.setSnpNum(10);//默认10
					product.setField1(filed1);//车种
					
					
					/**
					 * 工序流
					 * 端末：{"id":"1","fields":[{"field":"field1","value":"555600"},{"field":"field9","value":"pco"}]},
					 * 弯曲：{"id":"2","fields":[{"field":"field1","value":"555601"},{"field":"field9","value":"wf#qe#w"}]}
					 */
					JSONArray flows = new JSONArray();
					
					//端末  flowId=1
					JSONObject flow = new JSONObject();
					JSONArray fields = new JSONArray();
					JSONObject field = null;
					if(StringUtils.isNotBlank(dmNum) && StringUtils.isNotBlank(dmName)){
						field = new JSONObject();
						field.put("field", "field1");//编号
						field.put("value", dmNum);
						fields.add(field);
						
						field = new JSONObject();
						field.put("field", "field9");//名称
						field.put("value", dmName);
						fields.add(field);
						
						flow.put("id", "1");
						flow.put("fields", fields);
						flows.add(flow);
					}
					
					
					
					//弯曲  flowId=2
					flow = new JSONObject();
					fields = new JSONArray();
					if(StringUtils.isNotBlank(wqNum) && StringUtils.isNotBlank(wqName)){
						field = new JSONObject();
						field.put("field", "field1");//编号
						field.put("value", wqNum);
						fields.add(field);
						
						field = new JSONObject();
						field.put("field", "field9");//名称
						field.put("value", wqName);
						fields.add(field);
						
						flow.put("id", "2");
						flow.put("fields", fields);
						flows.add(flow);
					}
					
//					product.setFlow(flows.toJSONString());
					
					productService.save(product);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		new InitProduct().init();
	}
	
}
