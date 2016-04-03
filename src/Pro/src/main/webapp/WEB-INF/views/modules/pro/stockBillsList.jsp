<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>扎帐管理</title>
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
		<li class="active"><a href="${ctx}/pro/stockBills/">扎帐列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="stockBills" action="${ctx}/pro/stockBills/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>产品 ：</label><form:input path="product.name" htmlEscape="false" maxlength="50" class="input-small"/>
		<label>日期 ：</label><input id="createDate" name="createDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
				value="${fns:formatDate(stockBills.createDate,'yyyy-MM-dd')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>产品</th><th>库存数量</th><th>占用数量</th><th>日期</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="stock">
			<tr>
				<td>${stock.product.name}</td>
				<td>${stock.number}</td>
				<td>${stock.useNumber}</td>
				<td>${fns:formatDate(stock.createDate,'yyyy-MM-dd')}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
