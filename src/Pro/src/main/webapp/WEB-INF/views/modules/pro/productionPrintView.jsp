<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>生产明细预览</title>
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
        
        function print(){
        	var params = {};
        	params.productionId = '${production.id}';
        	
        	var productTreeIds = new Array();
        	var numbers = new Array();
        	$("table tbody tr").each(function(){
        		var id = $(this).attr("tree-id");
        		productTreeIds.push(id);
        		var num = $(this).find('input').val();
        		numbers.push(num);
        	});
        	
        	params.productTreeIds = productTreeIds.join(',');
        	params.numbers = numbers.join(',');
        	
        	var ok = false; 
        	var url = "${ctx}/pro/production/print";
        	$.ajax({
				url:   url,
			 	data: params,
			 	async :false,
			 	success: function(data){
			 		ok = true
			 	}
			}); 
			
			return ok;
        	
        }
	</script>
</head>
<body>
	
	<br>
	<tags:message content="${message}"/>
	
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>名称</th><th>数量</th></tr></thead>
		<tbody>
		<c:forEach items="${list}" var="productTree">
			<tr id="${productTree.id}" tree-id="${productTree.treeId }"
					pId="${productTree.parent ne '1' ? productTree.parent : '0'}">
				<td>${productTree.name}</td>
				<td>
					<c:choose>
						<c:when test="${isProduction }">
							<input type="hidden" value="${productTree.number}" class="input-small"/>
							${productTree.number}
						</c:when>
						<c:otherwise>
							<input type="text" value="${productTree.number}" class="input-small"/>
						</c:otherwise>
					</c:choose>
				
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
</body>
</html>
