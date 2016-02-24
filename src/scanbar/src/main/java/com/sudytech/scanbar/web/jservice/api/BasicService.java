package com.sudytech.scanbar.web.jservice.api;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;



/**
 * 接口服务
 * @author qex
 *
 * @param <Req> 接口参数
 * @param <Resp> 接口响应
 */
public abstract class BasicService<Req extends Request,Resp extends Response > {
	
	private static ThreadLocal<Context> context = new ThreadLocal<Context>();
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void doService(Req request, Resp response){
//		SessionFactory  sessionFactory = (SessionFactory) SpringContextHolder.getContext().getBean("sessionFactory");
		try {
			Session session = sessionFactory.openSession();
			SessionHolder sessionHolder = new SessionHolder(session);
			TransactionSynchronizationManager.bindResource(sessionFactory, sessionHolder);
			before(request, response);
			service(request, response);
			after(request, response);
		} finally {
			context.remove();
			SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
			SessionFactoryUtils.closeSession(sessionHolder.getSession());
		}
	}
	
	protected void before(Req request, Resp response){
		
	}
	protected abstract void service(Req request, Resp response);
	
	protected void after(Req request, Resp response){
		
	}
	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Context getContext() {
		return context.get();
	}

	public void setContext(Context ctx) {
		context.set(ctx);
	}
}
