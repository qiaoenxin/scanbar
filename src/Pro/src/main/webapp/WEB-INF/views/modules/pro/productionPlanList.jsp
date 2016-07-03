<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>生产计划管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		function production(id,isProducing){
			
			if(isProducing == '1'){
				top.$.jBox.error("该指令已投产，无法再次投产！","提示");
				top.$(".jbox-body .jbox-icon").css("top", "55px");
				return false;
			}
			
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

	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/pro/productionPlan/import" method="post" enctype="multipart/form-data"
			style="padding-left:20px;text-align:center;" class="form-search" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/pro/productionPlan/import/template">下载模板</a>
		</form>
	</div>

	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/pro/productionPlan/">生产指令列表</a></li>
		<shiro:hasPermission name="pro:productionPlan:edit"><li><a href="${ctx}/pro/productionPlan/form">生产指令添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="production" action="${ctx}/pro/productionPlan/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>批次 ：</label><form:input path="plan.serialNum" htmlEscape="false" maxlength="50" class="input-small"/>
		<label>指令编码 ：</label><form:input path="serialNum" htmlEscape="false" maxlength="50" class="input-small"/>
		<label>产品名称：</label><form:input path="product.name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		&nbsp;<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>
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
    				<a href="javascript:production('${production.id }','${production.isProducing }');">投产</a>
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
