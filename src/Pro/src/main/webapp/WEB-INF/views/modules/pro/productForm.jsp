<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		jQuery.validator.addMethod("productUnique",function(value, element, param) {
			var assy = $("#assy").val();
			var flag = true;
			if (assy == 0 && value == '组装卡'){
				flag = false;
			}
		return flag;},"当前为单品，不能选择组装卡！");
		
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			initActionSelect($('#action').val());
		});
		
		function initActionSelect(val){
			var html = '';

			//端末
			if(val == 1){
				var data = ${fns:toJson(product.bom.properties)};
				html += '<div class="control-group">';
				html += '<label class="control-label">属性：</label>';
				html += '<div class="controls">';
				html += 'HPC：&nbsp;<input type="text" style="width:90px" name="properties[\'HPC\']" value="'+ (data.HPC||"") +'" />&nbsp;端末：<input type="text" style="width:90px" name="properties[\'duanMo\']" value="'+ (data.duanMo||"") +'"/>';
				html += '&nbsp;ISO/ISO：<input type="text" style="width:90px" name="properties[\'ISO\']" value="'+ (data.ISO||"") +'"/>&nbsp;烘护套：<input type="text" style="width:90px" name="properties[\'hongHuTao\']" value="'+ (data.hongHuTao||"") +'"/><br/><br/>';
				html += '&nbsp;印字：<input type="text" style="width:90px" name="properties[\'yinZi\']" value="'+ (data.yinZi||"" ) +'"/>&nbsp;标识：&nbsp;<input type="text" style="width:90px" name="properties[\'biaoShi\']" value="'+ (data.biaoShi||"") +'"/>';
				html += '&nbsp;PCO：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" style="width:90px" name="properties[\'PCO\']" value="'+ (data.PCO||"") +'"/>';
				html += '</div></div>';
			}
			
			//弯曲
			else if(val == 2){
				var data = ${fns:toJson(product.bom.properties)};
				html += '<div class="control-group">';
				html += '<label class="control-label">属性：</label>';
				html += '<div class="controls">';
				html += '规格：<input type="text" style="width:90px" name="properties[\'guiGe\']" value="'+ (data.guiGe||"") +'"/>';
				html += '</div></div>';
			}
			$("#shuxing").html(html);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/pro/product/">产品管理列表</a></li>
		<li class="active"><a href="${ctx}/pro/product/form?id=${product.id}">产品管理<shiro:hasPermission name="pro:product:edit">${not empty product.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="pro:product:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="product" action="${ctx}/pro/product/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">编号:</label>
			<div class="controls">
				<form:input path="serialNum" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">SNP数量:</label>
			<div class="controls">
				<form:input path="snpNum" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">车种:</label>
			<div class="controls">
				<form:input path="machine" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">ASY:</label>
			<div class="controls">
				<form:input path="assy" htmlEscape="false" maxlength="1" class="required" />0表示单品，1表示assy品
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">种类:</label>
			<div class="controls">
				<select id="printCard" name="product.type" value="${product.type}">
					<option value=" " <c:if test="${product.type eq 0}">selected</c:if>>原料</option>
					<option value="组装卡" <c:if test="${product.type eq 1}">selected</c:if>>半成品</option>
					<option value="制程卡" <c:if test="${product.type eq 2}">selected</c:if>>产成品</option>
				</select>
			</div>
		</div>
		<div>
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<br/>
		<ul class="nav nav-tabs" style="border-bottom: 1px solid #0C0C0C;">
			<li><span style="font-size: 16px;font-family: inherit;">生产BOM属性</span></li>
		</ul><br/>
		
		<div class="control-group">
			<label class="control-label">操作:</label>
			<div class="controls">
				<select id="action" name="bom.action" value="${product.bom.action}" onchange="initActionSelect(this.value)">
					<c:forEach items="${dicts}" var="dict">
						<option value="${dict.value}" <c:if test="${dict.value eq product.bom.action}">selected</c:if>>${dict.label}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div id="shuxing">
			
		</div>
									
	    <div class="control-group">
			<label class="control-label">打印生产卡:</label>
			<div class="controls">
				<select id="printCard" name="bom.printCard" value="${product.bom.printCard}" class="input-small " productUnique="true">
					<option value=" " <c:if test="${product.bom.printCard eq ''}">selected</c:if>></option>
					<option value="组装卡" <c:if test="${product.bom.printCard eq '组装卡'}">selected</c:if>>组装卡</option>
					<option value="制程卡" <c:if test="${product.bom.printCard eq '制程卡'}">selected</c:if>>制程卡</option>
				</select>
				&nbsp;打印SNP：<form:input path="bom.printSnpNum" htmlEscape="false" maxlength="200" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">样品打印:</label>
			<div class="controls">
				<select id="isPrint" name="bom.print" value="${product.bom.print}">
					<option value="true" <c:if test="${product.bom.print}">selected</c:if>>是</option>
					<option value="false"  <c:if test="${!product.bom.print}">selected</c:if>>否</option>
				</select>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="pro:product:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
