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
	<tags:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>名称</th><th>数量</th><th>日期</th><th>打印</th><th>完成数量</th></tr></thead>
		<tbody>
		<c:forEach items="${list}" var="productTree">
			<tr  id="${productTree.id}"
					pId="${productTree.parent ne '1' ? productTree.parent : '0'}">
				<td>${productTree.name}</td>
				<td>${productTree.number}</td>
				<td>${productTree.dateForShow}</td>
				<td>${productTree.printNum}</td>
				<td>${productTree.completeNum}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
