<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<script src="${ctxStatic}/lodop/LodopFuncs.js" type="text/javascript"></script>
	<script src="${ctxStatic}/handlebars/handlebars.min.js" type="text/javascript"></script>
	
	<script src="${ctxStatic}/qrcode/jquery.qrcode.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/qrcode/canvas2Image.js" type="text/javascript"></script>	
	<script type="text/javascript">
		$(document).ready(function() {
			
			
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出产品数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/pro/product/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
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
        
        function printt(id){
        	var params = {};
        	params.id = id;
        	var ok = ''; 
        	var url = "${ctx}/pro/product/detail";
        	$.ajax({
				url: url,
			 	data: params,
			 	async :false,
			 	dataType: "json",
			 	success: function(data){
					if(!data){
						top.$.jBox.error("操作失败！","提示");
						top.$(".jbox-body .jbox-icon").css("top", "55px");
						return false;
					}
					//打印
					var myTemplate = Handlebars.compile($('#print-templ').html());
					var dataItem = data;
					var showBom = "";
					if(dataItem.bom && dataItem.bom.properties){
						var text ={"guiGe":"规格","yinZi":"印字","PCO":"PCO","hongHuTao":"烘护套","biaoShi":"标识","duanMo":"端末","HPC":"HPC","ISO":"ISO"};
						var prop = dataItem.bom.properties || {};
						for(key in prop){
							showBom += (text[key] || key) +" : "+(prop[key] || "-");
							showBom +="<br>";
						}
						dataItem.showBom = showBom;
					}
					
					var QRImg = Canvas2Image.qrcode(dataItem.name,150,150);
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
			}); 
        }        
	</script>
</head>
<body>

	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/pro/product/import" method="post" enctype="multipart/form-data"
			style="padding-left:20px;text-align:center;" class="form-search" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/pro/product/import/template">下载模板</a>
		</form>
	</div>
	
	
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/pro/product/">产品管理列表</a></li>
		<shiro:hasPermission name="pro:product:edit"><li><a href="${ctx}/pro/product/form">产品管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="product" action="${ctx}/pro/product/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		<label>车种 ：</label><form:input path="machine" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		&nbsp;<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
		&nbsp;<input id="btnImport" class="btn btn-primary" type="button" value="导入"/
	</form:form>
	<br>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>名称</th><th>编号</th><th>车种</th><th>snp数量</th><th>assy</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="product">
			<tr>
				<td>${product.name}</td>
				<td>${product.serialNum}</td>
				<td>${product.machine}</td>
				<td>${product.snpNum}</td>
				<td>
					<c:if test="${product.assy == 0}">单品</c:if>
					<c:if test="${product.assy == 1}">组装产品</c:if>
				</td>
				<td>
					<shiro:hasPermission name="pro:product:edit">
	    				<a href="${ctx}/pro/product/form?id=${product.id}">修改</a>
						<a href="${ctx}/pro/product/delete?id=${product.id}" onclick="return confirmx('确认要删除该产品管理吗？', this.href)">删除</a>
						<a href="${ctx}/pro/product/productTreeForm?id=${product.id}">BOM编辑</a>
						<c:if test="${product.bom.print && not empty product.bom.print}">
						<a href="javascript:printt('${product.id}')">样品打印</a>
						</c:if>
					</shiro:hasPermission>
				</td>
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

<table style="width:98%;" class="gridtable" id="c" cellspacing="0" cellpadding="0">
<tr style="height:20px;">
	<td colspan="2" style="text-align:center;font-weight:bold;font-size:14px">制程管理样品卡</td>
</tr>
<tr style="height:30px;">
	<td colspan="2" style="font-weight:bold;font-size:14px">品番:{{name}}</td>
</tr>
<tr style="height:30px;">
	<td style="width:50%;font-weight:bold;font-size:14px">细节</td>
	<td style="width:50%;font-weight:bold;font-size:14px">二维码</td>
</tr>
<tr style="height:210px;vertical-align: top;font-size:14px">
	<td>
	{{#if showBom}}{{{showBom}}}{{else}}{{/if}}
	</td>
	<td style="position:relative;">
		<div style="width:100%;height:210px;padding:30px 0;text-align:center;">{{{qrImg}}}</div>
	</td>
</tr>
<tr style="height:20px;">
	<td>日期:</td>
	<td>三樱武汉汽车部件有限公司</td>
</tr>
</table>
	</script>	
</body>
</html>
