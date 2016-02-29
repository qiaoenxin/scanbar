package com.sudytech.scanbar.service;

import org.springframework.stereotype.Service;

import com.sudytech.scanbar.bean.ProducingList;

@Service
public interface ProducingListService extends BaseService<ProducingList>{

	
	ProducingList findByListNO(String listNO);
	
}
