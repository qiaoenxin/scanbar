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
	
	<form:form id="searchForm" modelAttribute="stockHistory" action="${ctx}/pro/stockBills/detail?stockBillsId=${stockBillsId }" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
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
