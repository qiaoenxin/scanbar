package com.sudytech.scanbar.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sudytech.scanbar.service.BaseService;

public class BaseServiceImpl<T> implements BaseService<T> {

	@Autowired
	private SessionFactory factory;

	private Class<T> cls;

	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
		ParameterizedType mCls = (ParameterizedType) getClass()
				.getGenericSuperclass();
		Type[] types = mCls.getActualTypeArguments();
		cls = (Class<T>) types[0];

	}

	@Transactional
	@Override
	public void save(T bean) {
		Session session = getSession();
		session.save(bean);
	}

	@Transactional
	@Override
	public void update(T bean) {
		Session session = getSession();
		session.update(bean);
	}

	@Transactional
	@Override
	public void delete(T bean) {
		Session session = getSession();
		session.delete(bean);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findById(Serializable id) {
		Session session = getSession();
		return (T) session.get(cls, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(int begin, int length) {
		Session session = getSession();
		Query query = session.createQuery("from " + cls.getName());
		if (length <= 0) {
			return query.list();
		} else {
			query.setFirstResult(begin);
			query.setMaxResults(length);
			return query.list();
		}
	}

	public Session getSession() {
		return factory.getCurrentSession();
	}

	@Override
	public Criteria createCriteria(Criterion... criterions) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(cls);
		if(criterions != null){
			for (Criterion criterion : criterions) {
				criteria.add(criterion);
			}
		}
		return criteria;
	}

}
