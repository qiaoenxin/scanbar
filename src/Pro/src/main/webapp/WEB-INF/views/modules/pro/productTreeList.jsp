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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/pro/productTree/">产品树列表</a></li>
		<shiro:hasPermission name="pro:productTree:edit"><li><a href="${ctx}/pro/productTree/form">产品树添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="productTree" action="${ctx}/pro/productTree/" method="post" class="breadcrumb form-search">
		<label>编号 ：</label><form:input path="product.serialNum" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>名称</th><th>数量</th><shiro:hasPermission name="pro:productTree:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="productTree">
			<tr  id="${productTree.id}"
					pId="${productTree.parent.id ne '1' ? productTree.parent.id : '0'}">
				<td>${productTree.product.serialNum}</td>
				<td>${productTree.number}</td>
				<shiro:hasPermission name="pro:productTree:edit"><td>
					<a href="${ctx}/pro/productTree/delete?id=${productTree.id}" onclick="return confirmx('确认要删除该节点吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>

</body>
</html>