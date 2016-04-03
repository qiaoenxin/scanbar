<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>生产计划管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/pro/productionPlan/">生产指令列表</a></li>
		<shiro:hasPermission name="pro:productionPlan:edit"><li><a href="${ctx}/pro/productionPlan/form">生产指令添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="productionPlan" action="${ctx}/pro/productionPlan/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>指令名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		<label>机台：</label><form:input path="field1" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>名称</th><th>机台</th><th>计划开始时间</th><th>计划完成时间</th><shiro:hasPermission name="pro:productionPlan:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="productionPlan">
			<tr>
				<td>${productionPlan.name}</td>
				<td>${productionPlan.field1}</td>
				<td>${fns:formatDate(productionPlan.beginDate,'yyyy-MM-dd')}</td>
				<td>${fns:formatDate(productionPlan.endDate,'yyyy-MM-dd')}</td>
				<shiro:hasPermission name="pro:productionPlan:edit"><td>
    				<a href="${ctx}/pro/productionPlan/form?id=${productionPlan.id}">修改</a>
					<a href="${ctx}/pro/productionPlan/delete?id=${productionPlan.id}" onclick="return confirmx('确认要删除该生产计划吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
