package com.sudytech.scanbar.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sudytech.scanbar.bean.Device;
import com.sudytech.scanbar.bean.Materials;
import com.sudytech.scanbar.bean.ProducingList;
import com.sudytech.scanbar.bean.ProducingStep;
import com.sudytech.scanbar.service.MaterialsService;
import com.sudytech.scanbar.service.ProducingListService;
import com.sudytech.scanbar.service.ProducingStepService;
import com.sudytech.scanbar.service.ScanService;
import com.sudytech.scanbar.web.jservice.imp.ScanSession;

@Service
public class ScanServiceImpl implements ScanService {

	private ProducingStepService stepService;
	
	private MaterialsService materialsService;
	
	private ProducingListService producingListService;

	@Transactional
	@Override
	public void saveStep(ScanSession session, Materials materials) {
		Device device = session.getDevice();
		ProducingList list = session.getList();
		
		ProducingStep producingStep = new ProducingStep();
		producingStep.setDevice(device);
		producingStep.setMaterials(materials);
		producingStep.setList(list);
		producingStep.setStep(materials.getCurrentStep());
		producingStep.setWorkerNO(session.getWorkerNO());
		stepService.save(producingStep);
		materialsService.update(materials);
		list.setProducingCount(list.getProducingCount() + 1);
		//切换到下一步骤
		if(list.getProducingCount() == list.getCount()){
			list.setStep(list.getStep() + 1);
			list.setProducingCount(0);
		}
		producingListService.update(list);
	}

}
