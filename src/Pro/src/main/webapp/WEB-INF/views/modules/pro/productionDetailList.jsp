<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>生产详情管理</title>
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
		<li class="active"><a href="${ctx}/pro/productionDetail/">生产详情</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="productionDetail" action="${ctx}/pro/productionDetail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>明细编号 ：</label><form:input path="serialNum" htmlEscape="false" maxlength="50" class="input-small"/>
		<label>生产编号 ：</label><form:input path="production.serialNum" htmlEscape="false" maxlength="50" class="input-small"/>
		<label>产品 ：</label><form:input path="production.product.name" htmlEscape="false" maxlength="50" class="input-small"/>
		<label>状态 ：</label><form:select path="status" items="${status }" itemLabel="label" itemValue="value" class="input-small"></form:select>
		
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>明细编号</th><th>产品</th><th>数量</th><th>状态</th><th>打印</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="productionDetail">
			<tr>
				<td>${productionDetail.serialNum}</td>
				<td>${productionDetail.production.product.name}</td>
				<td>${productionDetail.number}</td>
				<td>${fns:getDictLabel(productionDetail.status,'flow_type','')}</td>
				<td><a href="javascript:void(0);" >打印</a> </td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
