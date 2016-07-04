/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.pro.entity.Loss;
import com.thinkgem.jeesite.modules.pro.dao.LossDao;

/**
 * 损失Service
 * @author Generate Tools
 * @version 2016-07-03
 */
@Component
@Transactional(readOnly = true)
public class LossService extends BaseService {

	@Autowired
	private LossDao lossDao;
	
	public Loss get(String id) {
		return lossDao.get(id);
	}
	
	public Page<Loss> find(Page<Loss> page, Loss loss) {
		DetachedCriteria dc = lossDao.createDetachedCriteria();
		dc.add(Restrictions.eq(Loss.FIELD_DEL_FLAG, Loss.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return lossDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Loss loss) {
		lossDao.save(loss);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		lossDao.deleteById(id);
	}
	
}
