<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>生产详情管理</title>
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
        
        function print(id){
        	var params = {};
        	params.id = id;
        	params.type = 'detail';
        	var ok = ''; 
        	var url = "${ctx}/pro/production/print";
        	$.ajax({
				url:   url,
			 	data: params,
			 	async :false,
			 	success: function(data){
					if(!data){
						alert('操作失败！');
						return false;
					}
					data = JSON.parse(data);
					for(var i = 0; i< data.length; i++){
							//打印
							var myTemplate = Handlebars.compile($('#print-templ').html());
							var dataItem = data[i];
							
							var flows = dataItem.productTree.product.flow;
							flows = JSON.parse(flows);
							dataItem.flow1 = flows[0];
							dataItem.flow2 = flows[1];
							
							var QRImg = Canvas2Image.qrcode(dataItem.serialNum,100,100);
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
			 	}
			}); 
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/pro/productionDetail/">生产详情</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="productionDetail" action="${ctx}/pro/productionDetail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>明细编号 ：</label><form:input path="serialNum" htmlEscape="false" maxlength="50" class="input-small"/>
		<label>生产编号 ：</label><form:input path="production.serialNum" htmlEscape="false" maxlength="50" class="input-small"/>
		<label>产品 ：</label><form:input path="production.product.name" htmlEscape="false" maxlength="50" class="input-small"/>
		<label>状态 ：</label><form:select path="status" items="${status }" itemLabel="label" itemValue="value" class="input-small"></form:select>
		
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>明细编号</th><th>产品</th><th>产品编号</th><th>数量</th><th>状态</th><th>打印</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="productionDetail">
			<tr>
				<td>${productionDetail.serialNum}</td>
				<c:if test="${productionDetail.production.type == 1}">
				<td>${productionDetail.productTree.product.name}</td>
				<td>${productionDetail.productTree.product.serialNum}</td>
				</c:if>
				<c:if test="${productionDetail.production.type == 0}">
				<td>${productionDetail.production.product.name}</td>
				<td>${productionDetail.production.product.serialNum}</td>
				</c:if>
				<td>${productionDetail.number}</td>
				<td>${fns:getDictLabel(productionDetail.status,'flow_type','')}</td>
				<td><a href="javascript:print('${productionDetail.id}');" >打印</a> </td>
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
