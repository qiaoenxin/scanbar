package com.sudytech.scanbar.service;

import org.springframework.stereotype.Service;

import com.sudytech.scanbar.bean.Materials;
import com.sudytech.scanbar.web.jservice.imp.ScanSession;

@Service
public interface ScanService {

	void saveStep(ScanSession session, Materials materials);
}
