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
        
        function print(id,type){
        	var params = {};
        	params.id = id;
        	params.type = type;
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
							var tplId = "#" + type + "-templ";
							var myTemplate = Handlebars.compile($(tplId).html());
							var dataItem = data[i];
							
							dataItem.bom = JSON.parse(dataItem.product.bomString).properties;
							if(dataItem.reminder != 0){
								dataItem.product.realSnpNum = "";
							}
							
							var imgSize = 100;
							if(type="package"){
								imgSize = 150;
							}
							var QRImg = Canvas2Image.qrcode(dataItem.serialNum,imgSize,imgSize);
							var QRHtml = QRImg.outerHTML;
							dataItem.qrImg = QRHtml;
							var html = myTemplate(dataItem);
							try{ 
						    	var LODOP=getLodop();
						    	LODOP.PRINT_INIT("");
								LODOP.SET_PRINT_PAGESIZE(2,1000,1500,"CreateCustomPage");
								LODOP.ADD_PRINT_HTM(0,5,"100%","100%",html);
								//LODOP.PRINT();
								LODOP.PREVIEW();
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
				<td>${productionDetail.product.name}</td>
				<td>${productionDetail.product.serialNum}</td>
				<td>${productionDetail.number}</td>
				<td>${fns:getDictLabel(productionDetail.status,'flow_type','')}</td>
				<td>
				<c:if test="${productionDetail.product.bom.printCard ==  '组装卡'}">
				<a href="javascript:print('${productionDetail.id}','package');" >组装卡</a>
				</c:if>	
				<c:if test="${productionDetail.product.bom.printCard ==  '制程卡'}">
				<a href="javascript:print('${productionDetail.id}','detail');" >制程卡</a>
				</c:if>	
				</td>					
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	
	
	
	<script id="detail-templ" type="text/x-handlebars-template">
				

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
	<td colspan="3" style="font-weight:bold;font-size:18px;text-align:center">{{product.machine}}</td>
	<td colspan="2" style="font-weight:bold;font-size:18px;text-align:center">{{product.name}}</td>
	<td style="font-weight:bold;font-size:18px">{{product.realSnpNum}}</td>
	<td style="font-weight:bold;font-size:18px">{{remainder}}</td>
</tr>
<tr>
	<td>HPC&nbsp;/&nbsp;{{bom.HPC}} </td>
	<td colspan="2">端末&nbsp;{{bom.duanMo}}&nbsp;{{bom.ISO}} </td>
	<td>烘护套&nbsp;{{bom.hongHuTao}}</td>
	<td>印字&nbsp;{{bom.yinZi}}</td>
	<td colspan="2">标识（{{bom.biaoShi}}）</td>
</tr>
<tr>
	<td rowspan=2 style="width:13%;">{{#if bom.HPC}}*{{else}}无{{/if}}</td>
	<td rowspan=2 style="width:18%;">{{#if bom.duanMo}}*{{else}}无{{/if}}</td>
	<td rowspan=2 style="width:18%;">{{#if bom.ISO}}*{{else}}无{{/if}}</td>
	<td rowspan=2 style="width:22%;">{{#if bom.hongHuTao}}*{{else}}无{{/if}}</td>
	<td style="width:15%;">{{#if bom.yinZi}}*{{else}}无{{/if}}</td>
	<td colspan="2" rowspan=2>{{#if bom.biaoShi}}*{{else}}无{{/if}}</td>
</tr>
<tr style="height:20px;">
	<td rowspan="3">{{{qrImg}}}</td>
	<td style="width:1px;"></td>
</tr>
<tr >
	<td>PCO&nbsp;{{bom.PCO}}</td>
	<td colspan="3">弯曲&nbsp;{{bom.guiGe}}</td>
	<td colspan="2" rowspan="2" style="font-weight:bold;font-size:16px;text-align:center">{{nextPart}}</td>
</tr>
<tr style="height:40px;">
	<td>{{#if bom.PCO}}*{{else}}无{{/if}}</td>
	<td colspan="3">{{#if bom.guiGe}}*{{else}}无{{/if}}</td>
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
	
	<script id="package-templ" type="text/x-handlebars-template">
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

<table style="width:98%;" class="gridtable" id="c" cellspacing="0" cellpadding="0">
<tr style="height:20px;">
	<td colspan="2" style="text-align:center;font-weight:bold;font-size:14px">制程管理组装卡</td>
</tr>
<tr style="height:30px;">
	<td colspan="2" style="font-weight:bold;font-size:14px">品番:{{product.name}}</td>
</tr>
<tr style="height:30px;">
	<td style="width:50%;font-weight:bold;font-size:14px">SNP：{{product.realSnpNum}} 尾数：{{remainder}}</td>
	<td style="width:50%;font-weight:bold;font-size:14px">编号:</td>
</tr>
<tr style="height:210px;vertical-align: top;font-size:14px">
	<td>
	{{#if data}}{{{data}}}{{else}}{{/if}}
	</td>
	<td style="">
		<div style="width:100%;height:210px;padding:30px 0;text-align:center;">{{{qrImg}}}</div>
	</td>
</tr>
<tr style="height:30px;">
	<td>组立:</td>
	<td>检查:</td>
</tr>
<tr style="height:20px;">
	<td>日期:</td>
	<td>三樱武汉汽车部件有限公司</td>
</tr>
</table>
	</script>	
</body>
</html>
