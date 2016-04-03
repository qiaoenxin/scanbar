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
	<tags:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>名称</th><th>数量</th><shiro:hasPermission name="pro:productTree:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${list}" var="productTree">
			<tr  id="${productTree.id}"
					pId="${productTree.parent ne '1' ? productTree.parent : '0'}">
				<td>${productTree.name}</td>
				<td>${productTree.number != 1 ? '1 x ' : ''} ${productTree.number}</td>
				<shiro:hasPermission name="pro:productTree:edit"><td>
					<a href="${ctx}/pro/productTree/delete?id=${productTree.treeId}" onclick="return confirmx('确认要删除该节点吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>

</body>
</html>
