package com.sudytech.scanbar.service;

import org.springframework.stereotype.Service;
import com.sudytech.scanbar.bean.Materials;


@Service
public interface MaterialsService extends BaseService<Materials>{

	/**
	 * 
	 * @param code
	 * @return 
	 */
	Materials findByCode(String barcode);
}
