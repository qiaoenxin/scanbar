<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>出入库记录管理</title>
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
		<li class="active"><a href="${ctx}/pro/stockHistory/">出入库记录列表</a></li>
		<shiro:hasPermission name="pro:stockHistory:edit"><li><a href="${ctx}/pro/stockHistory/form">出入库记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="stockHistory" action="${ctx}/pro/stockHistory/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		&nbsp;<label>产品名称 ：</label><form:input path="product.name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<label>类型：</label><form:select path="type" items="${types}" itemLabel="label" itemValue="value"></form:select>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>产品</th><th>产品编号</th><th>类型</th><th>数量</th><th>原因</th><th>操作人</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="stockHistory">
			<tr>
				<td>${stockHistory.product.name}</td>
				<td>${stockHistory.product.serialNum}</td>
				<td>${fns:getDictLabel(stockHistory.type,'stock_type','')}</td>
				<td>${stockHistory.number}</td>
				<td>${stockHistory.reason}</td>
				<td>${stockHistory.createBy.loginName}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
