package com.sudytech.scanbar.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.sudytech.scanbar.bean.Materials;
import com.sudytech.scanbar.service.MaterialsService;

@Service
public class MaterialsServiceImpl extends BaseServiceImpl<Materials> implements MaterialsService{

	@Override
	public Materials findByCode(String barCode) {
		Criteria criteria = createCriteria(Restrictions.eq("barCode", barCode));
		return (Materials) criteria.uniqueResult();
	}

}
