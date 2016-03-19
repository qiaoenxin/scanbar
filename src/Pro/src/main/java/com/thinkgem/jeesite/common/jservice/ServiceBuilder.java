package com.thinkgem.jeesite.common.jservice;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.jservice.api.BasicService;
import com.thinkgem.jeesite.common.jservice.api.Interfaces;
import com.thinkgem.jeesite.common.jservice.api.ParameterDef;
import com.thinkgem.jeesite.common.jservice.api.Interfaces.InterfaceDef;
import com.thinkgem.jeesite.common.jservice.api.Request;
import com.thinkgem.jeesite.common.jservice.api.Response;

import fastreflect.ConstructorBean;
import fastreflect.MethodBean;
import fastreflect.MethodBean.MethodHandler;

public class ServiceBuilder {
	public static final String ROOT_PATH = "";
	private Map<String, Service> services = new HashMap<String, ServiceBuilder.Service>();
	
	
	public void build(){
		Interfaces interfaces = Interfaces.getInstance();
		for(Iterator<String> iter = interfaces.getUris().iterator(); iter.hasNext();){
			String uri = iter.next();
			InterfaceDef inter = interfaces.getInterface(uri);
			services.put(ROOT_PATH + uri, new Service(inter));
		}
	}
	
	
	
	public Service getService(String uri){
		return services.get(uri);
	}
	
	public static class FieldResovler{
		
		private ParameterDef parameterDef;
		
		private MethodHandler setterMethod;
		
		private MethodHandler getterMethod;
		
		private String path;
		
		private boolean isArray;
		

		public FieldResovler(ParameterDef parameterDef,
				MethodHandler setterMethod, MethodHandler getterMethod,
				String path, boolean isArray) {
			super();
			this.parameterDef = parameterDef;
			this.setterMethod = setterMethod;
			this.getterMethod = getterMethod;
			this.path = path;
			this.isArray = isArray;
		}

		public boolean isArray() {
			return isArray;
		}

		public String getPath() {
			return path;
		}
		
		public void setValue(Object obj, Object value){
			if(setterMethod == null){
				return;
			}
			try {
				setterMethod.invoke(obj, value);
			} catch (Throwable e) {
				throw new RuntimeException(setterMethod.getMethod().toString() + "cause:" + e.toString(), e);
			}
		}
		
		public Object getValue(Object obj){
			if(getterMethod == null){
				return null;
			}
			
			try {
				return getterMethod.invoke(obj, (Object[])null);
			} catch (Throwable e) {
				throw new RuntimeException(getterMethod.getMethod().toString() + "cause:" + e.toString(), e);

			}
		}

		public ParameterDef getParameterDef() {
			return parameterDef;
		}
	}
	
	public static class Service{
		private InterfaceDef interfaceDef;
		
		private BasicService<? extends Request, ? extends Response> impl;
		
		private ConstructorBean requestBean;
		
		private ConstructorBean responseBean;
		
		private Map<String, FieldResovler> resovlers = new HashMap<String, FieldResovler>();
		
		public Service(InterfaceDef interfaceDef) {
			super();
			this.interfaceDef = interfaceDef;
			init();
		}
		
		private void init(){
			impl = (BasicService) newInstance(interfaceDef.getImpl());
			Class<?> cls = interfaceDef.getRequest();
			requestBean = ConstructorBean.getConstructorBean(interfaceDef.getRequest());
			responseBean = ConstructorBean.getConstructorBean(interfaceDef.getResponse());
			MethodBean methodBean = MethodBean.getMethodBean(cls);
			Field[] fields = getAllFields(cls);
			for(Field field : fields){
				
				ParameterDef def = field.getAnnotation(ParameterDef.class);
				String name = field.getName();
				MethodHandler setter = findBeanMethod(methodBean, "set" + name.substring(0,1).toUpperCase() + name.substring(1));
				MethodHandler getter = findBeanMethod(methodBean, "get" + name.substring(0,1).toUpperCase() + name.substring(1));
				String path = name;
				if(def != null && def.name().length() > 0){
					path = def.name();
				}
				FieldResovler resovler = new FieldResovler(def, setter, getter, path, field.getType().isArray());
				resovlers.put(path, resovler);
			}
		}
		
		private MethodHandler findBeanMethod(MethodBean methodBean, String name){
			MethodHandler[] methods = methodBean.getMethodByName(name);
			if(methods.length > 1){
				throw new RuntimeException(name + " is not uneque.");
			}
			if(methods.length == 0){
				return null;
			}
			return methods[0];
		}
		
		private Field[] getAllFields(Class<?> cls){
			List<Field> fields = new ArrayList<Field>();
			while(true){
				if(Object.class.equals(cls)){
					break;
				}
				for(Field field : cls.getDeclaredFields()){
					if(Modifier.isStatic(field.getModifiers())){
						continue;
					}
					fields.add(field);
				}
				cls = cls.getSuperclass();
			}
			
			return fields.toArray(new Field[fields.size()]);
		}
		
		
		
		public Collection<FieldResovler> getResovlers() {
			return resovlers.values();
		}

		public BasicService<? extends Request, ? extends Response> getImpl() {
			return impl;
		}


		public Request newResquest(){
//			return newInstance(interfaceDef.getRequest());
			return (Request) requestBean.newInstance();
		}
		
		public Response newResponse(){
//			return newInstance(interfaceDef.getResponse());
			return (Response) responseBean.newInstance();
		}
		
		
		public InterfaceDef getInterfaceDef() {
			return interfaceDef;
		}

		public FieldResovler getFieldResovler(String path){
			return resovlers.get(path);
		}

		private <K> K newInstance(Class<K> cls){
			try {
				return cls.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
