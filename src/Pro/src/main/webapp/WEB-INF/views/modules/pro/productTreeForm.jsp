<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>产品流管理管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(
		function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler : function(form) {
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer : "#messageBox",
				errorPlacement : function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")|| element.is(":radio")|| element.parent().is(".input-append")) {
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
		});
		
		initProductSelect($('#parent').val());
	});
	
	function initProductSelect(value){
		var url = "${ctx}/pro/productTree/getProductList";
		var params = {};
		params.id = value;
        $.ajax({
			url:   url,
			data: params,
			async :false,
			success: function(data){
				var control = $("#products");
				control.empty();//清空下拉框
				$.each(data,function(i,item){
					control.append("<option value='"+item.id+"'>"+item.name+"</option>");
				});
				control.select2('val', control.find('option:eq(0)').val());
			},
			error:function(){
				top.$.jBox.tip("加载产品数据失败！","error",{persistent:true,opacity:0});
			}
		}); 
			
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/pro/productTree/">产品流管理列表</a>
		</li>
		<li class="active"><a
			href="${ctx}/pro/productTree/form?id=${productFlow.id}">产品流管理<shiro:hasPermission
					name="pro:productTree:edit">${not empty productFlow.id?'修改':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="pro:productTree:edit">查看</shiro:lacksPermission>
		</a>
		</li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="productTree"
		action="${ctx}/pro/productTree/save" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<tags:message content="${message}" />
		<div class="control-group">
			<label class="control-label">父节点:</label>
			<div class="controls">
				<form:select path="parent.id" items="${productList }" id="parent"
					itemLabel="name" itemValue="id" onchange="initProductSelect(this.value)">
				</form:select>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">产品:</label>
			<div class="controls">
				<select id="products" name="product.id">
				
				</select>
			</div>
		</div>


		<div class="control-group">
			<label class="control-label">数量:</label>
			<div class="controls">
				<form:input path="number"/>
			</div>
		</div>
		
		
		<div class="form-actions">
			<shiro:hasPermission name="pro:productTree:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="保 存" />&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回"
				onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>
