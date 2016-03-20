<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工艺流</title>
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
		<li class="active"><a href="${ctx}/pro/productionHistory">工艺流列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="productionHistory" action="${ctx}/pro/productionHistory" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>生产编号 ：</label><form:input path="productionDetail.production.serialNum" htmlEscape="false" maxlength="50" class="input-small"/>
		<label>产品编号 ：</label><form:input path="productionDetail.production.plan.product.serialNum" htmlEscape="false" maxlength="50" class="input-small"/>
		<label>状态 ：</label><form:select path="status" items="${fns:getDictList('flow_type') }" itemLabel="label" itemValue="value"></form:select>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>生产编号</th><th>产品</th><th>状态</th><th>流水时间</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="productionHistory">
			<tr>
				<td>${productionHistory.productionDetail.production.serialNum}</td>
				<td>${productionHistory.productionDetail.production.plan.product.serialNum}</td>
				<td>${fns:getDictLabel(productionHistory.status,'flow_type','')}</td>
				<td>${fns:formatDate(productionHistory.createDate,'yyyy-MM-dd HH:mm')}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
