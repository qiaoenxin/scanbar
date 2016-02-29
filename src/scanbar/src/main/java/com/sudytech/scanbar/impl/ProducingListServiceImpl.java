package com.sudytech.scanbar.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.sudytech.scanbar.bean.ProducingList;
import com.sudytech.scanbar.service.ProducingListService;

@Service
public class ProducingListServiceImpl extends BaseServiceImpl<ProducingList> implements ProducingListService{

	@Override
	public ProducingList findByListNO(String listNO) {
		Criteria criteria = createCriteria(Restrictions.eq("listNO", listNO));
		return (ProducingList) criteria.uniqueResult();
	}

}
