<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理管理</title>
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
        
        function flow(id){
        	var url = "iframe:${ctx}/pro/product/flow?id="+id;
	    	var buttons = {"添加":"add","保存":"save","关闭":false};
	    	top.$.jBox.open(url, "功序流程设置", 1000, 500,{
				buttons:buttons, submit:function(v, h, f){
					var win = h.find("iframe")[0].contentWindow;
					if(v=="add"){
						win.add();
					}else if(v=="save"){
						var result = win.submit();
						if(result.ok==true){
							top.$.jBox.tip("保存成功！","",{persistent:true,opacity:0});
							top.$.jBox.close();
							location.reload();
						}else{
							var error = result.error;
							top.$.jBox.tip(error,"error",{persistent:true,opacity:0});
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
		<li class="active"><a href="${ctx}/pro/product/">产品管理列表</a></li>
		<shiro:hasPermission name="pro:product:edit"><li><a href="${ctx}/pro/product/form">产品管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="product" action="${ctx}/pro/product/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>名称</th><th>编号</th><th>snp数量</th><th>工序</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="product">
			<tr>
				<td>${product.name}</td>
				<td>${product.serialNum}</td>
				<td>${product.snpNum}</td>
				<td>
				<c:forEach items="${fns:parseArray(product.flow) }" var="flow" varStatus="i">
					${i.index+1 } :${fns:getDictLabel(flow.id,'flow_type','') }
				</c:forEach>
				</td>
				<td>
					<shiro:hasPermission name="pro:product:edit">
						<a href="javascript:flow('${product.id}');">工序流</a>
	    				<a href="${ctx}/pro/product/form?id=${product.id}">修改</a>
						<a href="${ctx}/pro/product/delete?id=${product.id}" onclick="return confirmx('确认要删除该产品管理吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
