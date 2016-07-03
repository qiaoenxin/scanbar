<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品流管理管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp"%>
	
	<style>
		.table tbody tr td {
		    text-align: left;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 3});
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
		<li class="active"><a href="${ctx}/pro/productTree/">产品树列表</a></li>
		<%-- <shiro:hasPermission name="pro:productTree:edit"><li><a href="${ctx}/pro/productTree/form">产品树添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="product" action="${ctx}/pro/productTree/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="200px">名称</th>
				<th width="50px">数量</th>
				<th width="150px">操作</th>
				<th width="100px">打印生产卡</th>
				<th width="50px">样品打印</th>
				<th>属性</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="productTree">
				<tr id="${productTree.id}"	pId="${productTree.parent ne '1' ? productTree.parent : '0'}">
					<td>${productTree.name}</td>
					<td>${productTree.number}</td>
					<td>
						<c:if test="${productTree.product.bom.action eq '0'}">
							自动
						</c:if>
						<c:if test="${productTree.product.bom.action eq '1'}">
							【端末加工】
						</c:if>
						<c:if test="${productTree.product.bom.action eq '2'}">
							【弯曲】
						</c:if>
						<c:if test="${productTree.product.bom.action eq '3'}">
							【检查】
						</c:if>
						<c:if test="${productTree.product.bom.action eq '4'}">
							【组装】
						</c:if>
						<c:if test="${productTree.product.bom.action eq '5'}">
							PCO
						</c:if>
						<c:if test="${productTree.product.bom.action eq '100'}">
							成品
						</c:if>
					</td>
					<td>${productTree.product.bom.printCard}</td>
					<td>
						<c:if test="${productTree.product.bom.print && not empty productTree.product.bom.print}">
							是
						</c:if>
						<c:if test="${!productTree.product.bom.print && not empty productTree.product.bom.print}">
							否
						</c:if>
					</td>
					<td>${productTree.product.showBom}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
