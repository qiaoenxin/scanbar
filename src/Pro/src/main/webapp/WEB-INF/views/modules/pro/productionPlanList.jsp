<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>生产计划管理</title>
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
		
		function production(id){
	    	var url = "iframe:${ctx}/pro/productionPlanTree/list?productionId="+id;
		    var buttons = {"投产":"product","关闭":false};
		    top.$.jBox.open(url, "明细预览", 800, 500,{
				buttons:buttons, submit:function(v, h, f){
					var win = h.find("iframe")[0].contentWindow;
					if(v=="product"){
				    	loading('正在提交，请稍等...');
				    	$('#productionForm').attr('action','${ctx}/pro/productionPlanTree/createPlanDetail?productionId='+id).submit();
				    	return true;
					}else{
						return true;
					}
					return false;
				}, loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        	
        }
		
		function showDetail(id){
	    	var url = "iframe:${ctx}/pro/productionPlanTree/list?productionId="+id;
		    var buttons = {"关闭":false};
		    top.$.jBox.open(url, "明细预览", 800, 500,{
				buttons:buttons, submit:function(v, h, f){
					return true;
				}, loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        	
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/pro/productionPlan/">生产指令列表</a></li>
		<shiro:hasPermission name="pro:productionPlan:edit"><li><a href="${ctx}/pro/productionPlan/form">生产指令添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="productionPlan" action="${ctx}/pro/productionPlan/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>批次</th><th>指令编号</th><th>产品</th><th>产品编号</th><th>数量</th><th>完成数</th><shiro:hasPermission name="pro:productionPlan:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="production">
			<tr>
				<td>${production.plan.serialNum}</td>
				<td>${production.serialNum}</td>
				<td>${production.product.name}</td>
				<td>${production.product.serialNum}</td>
				<td>${production.number}</td>
				<td>${production.completeNum}</td>
				<shiro:hasPermission name="pro:productionPlan:edit"><td>
    				<a href="javascript:production('${production.id }');">投产</a>
    				<a href="javascript:showDetail('${production.id }');">明细</a>
    				<a href="${ctx}/pro/productionPlan/form?id=${production.plan.id}">修改</a>
					<a href="${ctx}/pro/productionPlan/delete?id=${production.plan.id}" onclick="return confirmx('确认要删除该生产计划吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>			
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<form id="productionForm" action="" method="post" class="hide"></form>
</body>
</html>
