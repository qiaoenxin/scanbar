package com.sudytech.scanbar.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;



/**
 * service 基础接口
 * @author mac
 *
 * @param <T>
 */
public interface BaseService<T> {

	/**
	 * 保存
	 * @param bean
	 */
	void save(T bean);
	
	/**
	 * 更新
	 * @param bean
	 */
	void update(T bean);
	
	/**
	 * 删除
	 * @param bean
	 */
	void delete(T bean);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	T findById(Serializable id);
	
	
	/**
	 * 查询所有
	 * @param begin
	 * @param length
	 * @return
	 */
	List<T> findAll(int begin, int length);
	

	/**
	 * 创建条件
	 * @param criterions
	 * @return
	 */
	Criteria createCriteria(Criterion... criterions);
}
