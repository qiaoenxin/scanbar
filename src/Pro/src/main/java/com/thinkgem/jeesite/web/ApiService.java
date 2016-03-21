package com.thinkgem.jeesite.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.thinkgem.jeesite.common.jservice.ContextImpl;
import com.thinkgem.jeesite.common.jservice.ServiceBuilder;
import com.thinkgem.jeesite.common.jservice.ServiceBuilder.FieldResovler;
import com.thinkgem.jeesite.common.jservice.ServiceBuilder.Service;
import com.thinkgem.jeesite.common.jservice.api.BasicService;
import com.thinkgem.jeesite.common.jservice.api.Interfaces;
import com.thinkgem.jeesite.common.jservice.api.ParameterDef;
import com.thinkgem.jeesite.common.jservice.api.Request;
import com.thinkgem.jeesite.common.jservice.api.Response;
import com.thinkgem.jeesite.common.jservice.api.ReturnCode;
import com.thinkgem.jeesite.common.jservice.check.Comparator;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

public class ApiService extends HttpServlet{
	
	private static final String UTF8_CHARSET = "UTF-8";
	private static final String JSONP_PARAM = "jsoncallback";
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiService.class);
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2441041291183970441L;
	private ServiceBuilder builder;
	private Comparator comparator;

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		User user = (User) request.getSession().getAttribute(UserUtils.USER_SESSION);
		try {
			UserUtils.setThreadUser(user);
			service0(request, response);
		} finally {
			UserUtils.setThreadUser(null);
		}
	}
	protected void service0(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ContextImpl context = new ContextImpl(request,response);
		//业务方法
		doService(context);
		//构造响应
		String text = buildResponse(context);
		//写响应
		writeResponse(text, response);
	}
	/**
	 * 写响应
	 * @param text
	 * @param response
	 * @throws IOException
	 */
	private void writeResponse(String text, HttpServletResponse response) throws IOException {
		if(text == null){
			return;
		}
		byte[] bytes = new byte[0];
		try {
			bytes = text.getBytes(UTF8_CHARSET);
		} catch (UnsupportedEncodingException e) {
		}
		response.setCharacterEncoding(UTF8_CHARSET);
		response.setContentLength(bytes.length);
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			out.write(bytes);
			out.flush();
		} finally {
			if(out != null){
				try {
					out.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 接口
	 * @param context
	 */
	private void doService(ContextImpl context) {
		try {
			Service service = builder.getService(context.getHttpRequest().getPathInfo());
			context.setService(service);
			if(service == null){
				Response resp = new Response(ReturnCode.UNKOWN_SERVICE, "no such service.");
				context.setResponse(resp);
				return;
			}
			Request req = service.newResquest();
			req.setContext(context);
			context.setRequest(req);
			Response resp = service.newResponse();
			resp.setContext(context);
			context.setResponse(resp);
			
			//解析参数
			parseRequest(context);
			
			//登录鉴权
			if(!checkLogin(context)){
				Response errorResp = new Response(ReturnCode.AUTH_ERROR, "check auth error");
				context.setResponse(errorResp);
				return;
			}
			
			//校验参数
			if(!checkParameter(context)){
				return;
			}
			
			BasicService<Request, Response> impl = (BasicService<Request, Response>) service.getImpl();
			long begin = System.currentTimeMillis();
			impl.doService(context.getRequest(), resp);
			LOGGER.info("interface [{}] spent: {} ms", context, System.currentTimeMillis() - begin);
			return;
		} catch (Exception e) {
			LOGGER.warn("", e);
			Response resp = new Response(ReturnCode.UNKOWN_ERROR, e.toString());
			context.setResponse(resp);
		}
	}

	/**
	 * 登录鉴权
	 * @param context
	 * @return
	 */
	private boolean checkLogin(ContextImpl context) {
		Service service = context.getService();
		//不需要校验
		if(!service.getInterfaceDef().isNeedAuth()){
			return true;
		}
		//登陆用户校验
		User user = UserUtils.getUser();
		return user.getLoginName() != null;
	}
	
	/**
	 * 参数校验
	 * @param context
	 * @return
	 */
	private boolean checkParameter(ContextImpl context) {
		
		Request request = context.getRequest();
		for (Iterator<FieldResovler> iter = context.getService().getResovlers().iterator(); iter.hasNext();) {
			FieldResovler resolver = iter.next();
			ParameterDef def = resolver.getParameterDef();
			Object paramValue = resolver.getValue(request);
			if ((paramValue instanceof String || paramValue == null) && !resolver.isArray()) {
				String value = (String) paramValue;
				if((value == null || value.length() == 0) && def != null){
					value = def.defaultValue();
					resolver.setValue(request, value);
				}

				if(def != null && !comparator.matchRule(def, value)){
					Response resp = new Response(ReturnCode.PARAMETER_ERROR,"parameter error: " + resolver.getPath());
					context.setResponse(resp);
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 解析参数
	 * @param context
	 */
	private void parseRequest(ContextImpl context) {
		HttpServletRequest request = context.getHttpRequest();
		Service service = context.getService();
		Request req = context.getRequest();
		for(Enumeration<String> names = context.getHttpRequest().getParameterNames(); 
				names.hasMoreElements();){
			String name = names.nextElement();
			FieldResovler resovler = service.getFieldResovler(name);
			if(resovler == null){
				continue;
			}
			ParameterDef def = resovler.getParameterDef();
			if(resovler.isArray()){
				resovler.setValue(req, request.getParameterValues(name));
			}else{
				String value = request.getParameter(name);
				resovler.setValue(req, value);
			}
		}
	}

	/**
	 * 构造响应
	 * @param context
	 * @return
	 */
	private String buildResponse(ContextImpl context) {
		
		Response response = context.getResponse();
		
		//流响应
		if(response.getResult() == ReturnCode.Stream){
			return null;
		}
		
		String type = null;
		if(context.getRequest()!=null){
			type = context.getRequest().getCallType();
		}
		if(Request.XML_CALL_TYPE.equals(type)){
			context.getHttpResponse().setContentType("application/xml;charset=" + UTF8_CHARSET);
			return "<xml>not support yet.</xml>";
		}
		context.getHttpResponse().setContentType("application/json;charset=" + UTF8_CHARSET);
		String text;
		if(response.getJsonFilter() != null){
			text = JSON.toJSONString(response, response.getJsonFilter(), SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue);
		}else{
			text = JSON.toJSONString(response,SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue);
		}
		
//		String text = JSON.toJSONString(context.getResponse(), SerializerFeature.WriteMapNullValue);
		if(Request.JSONP_CALL_TYPE.equals(type)){
			StringBuilder builder = new StringBuilder();
			builder.append(context.getHttpRequest().getParameter(JSONP_PARAM));
			builder.append("(");
			builder.append(text);
			builder.append(")");
			text = builder.toString();
		}
		return text;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		InterfaceConfig interfaceConfig = new InterfaceConfig();
		interfaceConfig.init();
		comparator = new Comparator();
		builder = new ServiceBuilder();
		builder.build();
	}
	@Override
	public void destroy() {
		super.destroy();
		Interfaces.getInstance().clear();
	}
	
	
}
