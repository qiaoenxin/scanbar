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
							//alert('保存失败！');
							return false;
						}
						var data = JSON.parse(str);
						//top.$.jBox.tip("保存成功！","",{persistent:true,opacity:0});
						//top.$.jBox.close();
						for(var i = 0; i< data.length; i++){
							//打印
							var myTemplate = Handlebars.compile($('#print-templ').html());
							var dataItem = data[i];
							
							var flows = dataItem.productTree? dataItem.productTree.product.flow : dataItem.production.product.flow;
							if(flows){
								flows = JSON.parse(flows);
								dataItem.flow1 = flows[0];
								dataItem.flow2 = flows[1];
							}
							if(!dataItem.productTree || dataItem.productTree.product.serialNum == dataItem.production.product.serialNum){
								dataItem.next = "仓库";
							}else{
								dataItem.next = "组立";
							}
							
							var QRImg = Canvas2Image.qrcode(dataItem.serialNum,130,130);
							var QRHtml = QRImg.outerHTML;
							dataItem.qrImg = QRHtml;
							var html = myTemplate(dataItem);
							try{ 
						    	var LODOP=getLodop();
						    	LODOP.PRINT_INIT("");
								LODOP.SET_PRINT_PAGESIZE(2,1000,1500,"CreateCustomPage");
								LODOP.ADD_PRINT_HTM(10,0,"100%","100%",html);
								//LODOP.PRINT();
								LODOP.PREVIEW();
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

<style>
table{
	font-size:12px;
}
div{
	font-size:12px;
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

<table style="height:350px;width:98%;" class="gridtable" id="c" cellspacing="0" cellpadding="0">
<tr>
	<td colspan="7" style="text-align:center;font-weight:bold;font-size:14px">制程管理卡 </td>
</tr>
<tr>
	<td colspan="3" style="font-weight:bold;font-size:14px">管理NO:{{serialNum}}</td>
	<td colspan="2" style="font-weight:bold;font-size:14px">品番</td>
	<td>SNP</td>
	<td>尾数</td>
</tr>
<tr style="height:80px;">
	<td colspan="3" style="font-weight:bold;font-size:18px;text-align:center">{{production.product.field1}}</td>
	<td colspan="2" style="font-weight:bold;font-size:18px;text-align:center">{{#if productTree}}{{productTree.product.name}}{{else}}{{production.product.name}}{{/if}}</td>
	<td style="font-weight:bold;font-size:18px">{{number}}</td>
	<td style="font-weight:bold;font-size:18px">{{remainder}}</td>
</tr>
<tr>
	<td>HPC&nbsp;/&nbsp;{{flow1.field2}} </td>
	<td colspan="2">端末&nbsp;{{flow1.field3}}&nbsp;{{flow1.field4}} </td>
	<td>烘护套&nbsp;{{flow1.field5}}</td>
	<td>印字&nbsp;{{flow1.field7}}</td>
	<td colspan="2">标示（{{flow1.field8}}）</td>
</tr>
<tr>
	<td rowspan=2 style="width:13%;">{{#if flow1.field2}}*{{else}}无{{/if}}</td>
	<td rowspan=2 style="width:18%;">{{#if flow1}}*{{else}}无{{/if}}</td>
	<td rowspan=2 style="width:18%;">{{#if flow1}}*{{else}}无{{/if}}</td>
	<td rowspan=2 style="width:22%;">{{#if flow1.field5}}*{{else}}无{{/if}}</td>
	<td style="width:15%;">{{#if flow1.field7}}*{{else}}无{{/if}}</td>
	<td colspan="2" rowspan=2>{{#if flow1.field8}}*{{else}}无{{/if}}</td>
</tr>
<tr style="height:20px;">
	<td rowspan="3">{{{qrImg}}}</td>
	<td style="width:1px;"></td>
</tr>
<tr >
	<td>PCO&nbsp;{{flow1.field6}}</td>
	<td colspan="3">弯曲&nbsp;{{flow2.field2}}</td>
	<td colspan="2" rowspan="2" style="font-weight:bold;font-size:16px;text-align:center">{{next}}</td>
</tr>
<tr style="height:40px;">
	<td>{{#if flow1.field6}}*{{else}}无{{/if}}</td>
	<td colspan="3">{{#if flow2}}*{{else}}无{{/if}}</td>
</tr>
<tr>
	<td colspan="2">检查</td>
	<td colspan="2">管材 LOT . NO</td>
	<td colspan="3">出货日期年月日</td>
</tr>
<tr>
	<td colspan="2">*</td>
	<td colspan="2">*</td>
	<td colspan="3">*</td>
</tr>
<tr>
	<td colspan="3">日期/名字</td>
	<td colspan="4">三樱武汉汽车部件有限公司</td>
</tr>
</table>
	</script>
</body>
</html>
