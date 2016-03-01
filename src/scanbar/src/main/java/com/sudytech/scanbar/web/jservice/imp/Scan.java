package com.sudytech.scanbar.web.jservice.imp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sudytech.scanbar.bean.Materials;
import com.sudytech.scanbar.bean.Step;
import com.sudytech.scanbar.service.MaterialsService;
import com.sudytech.scanbar.service.ScanService;
import com.sudytech.scanbar.util.StringUtils;
import com.sudytech.scanbar.web.jservice.ContextImpl;
import com.sudytech.scanbar.web.jservice.api.BasicService;
import com.sudytech.scanbar.web.jservice.api.ParameterDef;
import com.sudytech.scanbar.web.jservice.api.Request;
import com.sudytech.scanbar.web.jservice.api.Response;
import com.sudytech.scanbar.web.jservice.api.ReturnCode;

public class Scan {

	@Service
	public static class ScanImpl extends BasicService<ScanRequest, ScanResponse>{
		
		@Autowired
		private MaterialsService materialsService;
		
		@Autowired
		private ScanService scanService;
		
		@Override
		protected void service(ScanRequest request, ScanResponse response) {
			
			ContextImpl context = (ContextImpl) getContext();
			ScanSession session = (ScanSession) context.getHttpRequest().getSession().getAttribute(ScanSession.class.getName());
			if(session == null){
				response.setReturnCode(ReturnCode.SCAN_SESSION_NOT_EXIST);
				response.setDescription("扫描会话不存在。");
				return;
			}
			
			
			Materials materials = materialsService.findByCode(request.getBarcode());
			materials = materials == null ? new Materials() : materials;
			int step = StringUtils.toInt(request.getStep(), 0);
			if(step != session.getList().getStep()){
				response.setReturnCode(ReturnCode.STATUS_ERROR);
				response.setDescription(String.format("发行单状态[%s]与当前操作不一致", Step.steps[session.getList().getStep()]));
				return;
			}
			if(materials.getCurrentStep() == step){
				response.setReturnCode(ReturnCode.STATUS_ERROR);
				response.setDescription("重复扫描");
				return;
			}else if(materials.getCurrentStep() != step -1 ){
				response.setReturnCode(ReturnCode.STATUS_ERROR);
				response.setDescription(String.format("无法从状态[%s]切换到[%s]错误", Step.steps[materials.getCurrentStep()], Step.steps[step]));
				return;
			}else if(step == Step.step1){
				materials.setBarCode(request.getBarcode());
				materialsService.save(materials);
			}
			
			materials.setCurrentStep(step);
			scanService.saveStep(session, materials);
		}

		public MaterialsService getMaterialsService() {
			return materialsService;
		}

		public void setMaterialsService(MaterialsService materialsService) {
			this.materialsService = materialsService;
		}

		public ScanService getScanService() {
			return scanService;
		}

		public void setScanService(ScanService scanService) {
			this.scanService = scanService;
		}
	}
	
	public static class ScanRequest extends Request {
		/**
		 * 步骤
		 */
		@ParameterDef(required=true)
		private String step;
		
		/**
		 * 条码
		 */
		@ParameterDef(required=true)
		private String barcode;
		
		@ParameterDef
		public int count;
		
		@ParameterDef(required = false)
		public String kindType;
		
		public String getStep() {
			return step;
		}
		public void setStep(String step) {
			this.step = step;
		}

		public String getBarcode() {
			return barcode;
		}
		public void setBarcode(String barcode) {
			this.barcode = barcode;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public String getKindType() {
			return kindType;
		}
		public void setKindType(String kindType) {
			this.kindType = kindType;
		}

	}
	
	public static class ScanResponse extends Response{
		
	}
}
