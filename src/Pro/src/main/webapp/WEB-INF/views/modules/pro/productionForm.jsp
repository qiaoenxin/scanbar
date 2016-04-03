<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>生产管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/pro/production/">生产列表</a></li>
		<li class="active"><a href="${ctx}/pro/production/form?id=${production.id}">生产<shiro:hasPermission name="pro:production:edit">${not empty production.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="pro:production:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="production" action="${ctx}/pro/production/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		
		<div class="control-group">
			<label class="control-label">计划:</label>
			<div class="controls">
				<form:select path="plan.id" items="${productionPlanList }" itemLabel="name" itemValue="id"></form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">产品:</label>
			<div class="controls">
				<form:select path="product.id" items="${productList }" itemLabel="name" itemValue="id"></form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">生产目标数量:</label>
			<div class="controls">
				<form:input path="number" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">优先级:</label>
			<div class="controls">
				<form:radiobuttons path="priority" items="${fns:getDictList('production_priority') }" itemLabel="label" itemValue="value"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="pro:production:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
