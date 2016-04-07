<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>生产管理管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/lodop/LodopFuncs.js" type="text/javascript"></script>
	<script src="${ctxStatic}/handlebars/handlebars.min.js" type="text/javascript"></script>
	
	<script src="${ctxStatic}/qrcode/jquery.qrcode.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/qrcode/canvas2Image.js" type="text/javascript"></script>
	
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
						var str = win.print();
						if(!str){
							alert('保存失败！');
							return false;
						}
						var data = JSON.parse(str);
						//top.$.jBox.tip("保存成功！","",{persistent:true,opacity:0});
						//top.$.jBox.close();
						for(var i = 0; i< data.length; i++){
							//打印
							var myTemplate = Handlebars.compile($('#print-templ').html());
							var dataItem = data[i];
							
							var flows = dataItem.productTree.product.flow;
							flows = JSON.parse(flows);
							dataItem.flow1 = flows[0];
							dataItem.flow2 = flows[1];
							
							var QRImg = Canvas2Image.qrcode(dataItem.serialNum,130,130);
							var QRHtml = QRImg.outerHTML;
							dataItem.qrImg = QRHtml;
							var html = myTemplate(dataItem);
							try{ 
						    	var LODOP=getLodop();
						    	LODOP.PRINT_INIT("");
								LODOP.SET_PRINT_PAGESIZE(1,1200,800,"CreateCustomPage");
								LODOP.ADD_PRINT_HTM(16,6,"100%","100%",html);
								LODOP.PRINT();
							 }catch(err){ 
							 	alert(err);
					 		 } 
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
		<label>计划编号 ：</label><form:input path="plan.serialNum" htmlEscape="false" maxlength="50" class="input-small"/>
		<label>生产编号 ：</label><form:input path="serialNum" htmlEscape="false" maxlength="50" class="input-small"/>
		<label>产品：</label><form:input path="product.name" htmlEscape="false" maxlength="50" class="input-small"/>
		<label>优先级 ：</label><form:radiobuttons path="priority" items="${fns:getDictList('production_priority') }" itemLabel="label" itemValue="value"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>计划名称</th><th>生产编号</th><th>产品</th><th>产品编号</th><th>生产目标</th><th>完成数</th><th>优先级</th><shiro:hasPermission name="pro:production:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="production">
			<tr>
				<td>${production.plan.name}</td>
				<td>${production.serialNum}</td>
				<td>${production.product.name}</td>
				<td>${production.product.serialNum}</td>
				<td>${production.number}</td>
				<td>${production.completeNum}</td>
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
				<table style="font-size:14px;" id="c"> 

<style>
table{
	font-size:10px;
}
div{
	font-size:10px;
}
table.gridtable {
	color:#333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
}
table.gridtable td {
	border-width: 1px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
}
</style>

<tr>
	<td>
<table style="height:260px;width:324px;" class="gridtable">
<tr>
	<td colspan="6">制程管理卡 {{productTree.product.field1}}  </td>
</tr>
<tr>
	<td colspan="4">品   番  {{productTree.product.serialNum}}</td>
	<td colspan="2">SNP {{number}}</td>
</tr>
<tr>
	<td>HPC&nbsp;{{flow1.field2}} </td>
	<td colspan="2">端末&nbsp;{{flow1.field3}}</td>
	<td>烘护套&nbsp;{{flow1.field5}}</td>
	<td>印字&nbsp;{{flow1.field7}}</td>
	<td>标示（{{flow1.field8}}）</td>
</tr>
<tr>
	<td style="width:13%;">*</td>
	<td style="width:18%;">*</td>
	<td style="width:18%;">*</td>
	<td style="width:22%;">*</td>
	<td style="width:15%;">*</td>
	<td>*</td>
</tr>
<tr>
	<td>PCO&nbsp;{{flow1.field6}}</td>
	<td colspan="3">弯曲&nbsp;{{flow2.field2}}</td>
	<td colspan="2" rowspan="2">仓库</td>
</tr>
<tr>
	<td>*</td>
	<td colspan="3">*</td>
</tr>
<tr>
	<td colspan="2">检查</td>
	<td colspan="2">管材 LOT . NO</td>
	<td colspan="2">出货日期年月日</td>
</tr>
<tr>
	<td colspan="2">*</td>
	<td colspan="2">*</td>
	<td colspan="2">*</td>
</tr>
<tr>
	<td colspan="3">日期/名字</td>
	<td colspan="3">三楼（武汉）汽车部件有限公司</td>
</tr>
</table>

</td>
	<td style="padding-left:6px;">
		<div>
			<div>优先级：{{production.priority}}</div>
		</div>
		<div style="padding:16px 0px;">
			<div>计划日期</div>
			<div>{{production.plan.beginDate}}</div>
		</div>
		<div style="padding-bottom:16px;">
			<div>批次流水号</div>
			<div>{{serialNum}}</div>
		</div>
		<div>
			<div>{{{qrImg}}}</div>
		</div>
	</td>
</tr>

</table>
	</script>
</body>
</html>
