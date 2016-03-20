<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>生产管理管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/lodop/LodopFuncs.js" type="text/javascript"></script>
	<script src="${ctxStatic}/handlebars/handlebars.min.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        
        function printView(id){
        	var url = "iframe:${ctx}/pro/production/printView?id="+id;
	    	var buttons = {"打印":"print","关闭":false};
	    	top.$.jBox.open(url, "明细预览", 800, 500,{
				buttons:buttons, submit:function(v, h, f){
					var win = h.find("iframe")[0].contentWindow;
					if(v=="print"){
						var ok = win.print();
						if(ok == true){
							//top.$.jBox.tip("保存成功！","",{persistent:true,opacity:0});
							top.$.jBox.close();
							//打印
							var myTemplate = Handlebars.compile($('#print-templ').html());
							var html = myTemplate({});
							
							try{ 
						    	var LODOP=getLodop(); 
								LODOP.PRINT_INIT("");		            
								LODOP.ADD_PRINT_HTM(10,10,"100%","100%",html);	       
								LODOP.PRINT();
							 }catch(err){ 
					 		 } 
 		 
						}else{
							top.$.jBox.tip("保存失败","error",{persistent:true,opacity:0});
						}
					}else{
						return true;
					}
					return false;
				}, loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        	
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/pro/production/">生产列表</a></li>
		<shiro:hasPermission name="pro:production:edit"><li><a href="${ctx}/pro/production/form">生产添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="production" action="${ctx}/pro/production/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>计划编号 ：</label><form:input path="plan.id" htmlEscape="false" maxlength="50" class="input-small"/>
		<label>生产编号 ：</label><form:input path="serialNum" htmlEscape="false" maxlength="50" class="input-small"/>
		<label>优先级 ：</label><form:radiobuttons path="priority" items="${fns:getDictList('production_priority') }" itemLabel="label" itemValue="value"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>计划名称</th><th>生产编号</th><th>生产目标</th><th>优先级</th><shiro:hasPermission name="pro:production:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="production">
			<tr>
				<td>${production.plan.name}</td>
				<td>${production.serialNum}</td>
				<td>${production.number}</td>
				<td>${fns:getDictLabel(production.priority,'production_priority','') }</td>
				<shiro:hasPermission name="pro:production:edit"><td>
    				<a href="${ctx}/pro/production/form?id=${production.id}">修改</a>
					<a href="${ctx}/pro/production/delete?id=${production.id}" onclick="return confirmx('确认要删除该生产管理吗？', this.href)">删除</a>
					<a href="javascript:printView('${production.id }');">投产</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<script id="print-templ" type="text/x-handlebars-template">
		{{#each items}}
			<li class="am-g am-list-item-dated"><a href="javascript:itemClick({{id}});"
			class="am-list-item-hd ">
				<div class="am-g">
					<div class="am-u-sm-11">
						<div class="title">{{name}}</div>
						<div class="time">部门：{{department}}&nbsp;&nbsp;&nbsp;&nbsp;时间：{{createDate}}</div>
					</div>
					<div class="am-u-sm-1">
						<span class="am-list-date icon iconfont icon-right"></span>
					</div>
				</div> </a></li>
		{{/each}}
	</script>
</body>
</html>
