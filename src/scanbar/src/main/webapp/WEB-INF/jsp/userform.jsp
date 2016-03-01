<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="${adminPath }" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="/WEB-INF/jsp/include/header.jsp" %>
</head>
<body>
<a href="a.jsp" >hell</a>
<form:form commandName="user" action="user/save">
	<form:input path="account"/>
	<form:input path="name"/>
	<form:input path="age"/>
	<input type="submit" />
</form:form>
</body>
</html>