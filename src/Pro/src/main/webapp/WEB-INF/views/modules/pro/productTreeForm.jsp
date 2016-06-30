<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>产品流管理管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	jQuery.validator.addMethod("productUnique",function(value, element, param) {
		return $("#contentTable select option[value="+value+"]:selected").length < 2;
	},"产品不得重复!");

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
	
	
	function addRow(list, idx, tpl, row){
		row = row || {};
		row.numberRenderer = function(){  
	        return this.row.number || 1;
	    };
	    
		$(list).append(Mustache.render(tpl, {
			idx: idx, delBtn: true, row: row
		}));
		$(list+idx).find("select").each(function(){
			$(this).val($(this).attr("data-value"));
		});
		$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
			var ss = $(this).attr("data-value").split(',');
			for (var i=0; i<ss.length; i++){
				if($(this).val() == ss[i]){
					$(this).attr("checked","checked");
				}
			}
		});
	}
	function delRow(obj, prefix){
		var id = $(prefix+"_id");
		var delFlag = $(prefix+"_delFlag");
		if (id.val() == ""){
			$(obj).parent().parent().remove();
		}else if(delFlag.val() == "0"){
			delFlag.val("1");
			$(obj).html("撤销删除").attr("title", "撤销删除");
			$(obj).parent().parent().addClass("error");
		}else if(delFlag.val() == "1"){
			delFlag.val("0");
			$(obj).html("删除").attr("title", "删除");
			$(obj).parent().parent().removeClass("error");
		}
	}	
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/pro/product/">产品管理列表</a></li>
		<li><a href="${ctx}/pro/product/form?id=${product.id}">产品管理<shiro:hasPermission name="pro:product:edit">${not empty product.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="pro:product:edit">查看</shiro:lacksPermission></a></li>
		<li class="active"><a
			href="${ctx}/pro/productTree/form?id=${productFlow.id}">产品流管理<shiro:hasPermission
					name="pro:productTree:edit">${not empty productFlow.id?'修改':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="pro:productTree:edit">查看</shiro:lacksPermission>
		</a>
		</li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="product" action="${ctx}/pro/product/productTreeSave" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<tags:message content="${message}" />
		<div class="control-group">
			<label class="control-label">父节点:</label>
			<label class="control-label">${product.name}</label>
		</div>

		<div class="control-group">
				<label class="control-label">子节点：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>子节点</th>
								<th>数量</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="productTreeList">
						</tbody>
						<tfoot>
							<tr><td colspan="5"><a href="javascript:" onclick="addRow('#productTreeList', productTreeRowIdx, productTreeTpl);productTreeRowIdx = productTreeRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot>
					</table>
					<script type="text/template" id="productTreeTpl">//<!--
							<tr id="productTreeList{{idx}}">
								<td class="hide">
									<input id="productTreeList{{idx}}_id" name="productTreeList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
									<input id="productTreeList{{idx}}_delFlag" name="productTreeList[{{idx}}].delFlag" type="hidden" value="0"/>
								</td>
								<td style="width: 40%;">
									<select id="productTreeList{{idx}}_id" name="productTreeList[{{idx}}].product.id" data-value="{{row.product.id}}" class="input-small " productUnique="true" style="width: 80%;" required>
										<c:forEach items="${productList}" var="product">
											<option value="${product.id}">${product.name}</option>
										</c:forEach>
									</select>
								</td>
								<td style="width: 25%;">
									<input id="productTreeList{{idx}}_number" name="productTreeList[{{idx}}].number" type="text" value="{{row.numberRenderer}}" min="1" style="width: 80%;">
								</td>
								<td class="text-center" style="width: 15%;line-height: 30px;">
									<a href="javascript:void(0);" onclick="delRow(this, '#productTreeList{{idx}}');">删除</a>
								</td>
							</tr>//-->
						</script>
					<script type="text/javascript">
						var productTreeRowIdx = 0, productTreeTpl = $("#productTreeTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(productTrees)};
							for (var i=0; i<data.length; i++){
								addRow('#productTreeList', productTreeRowIdx, productTreeTpl, data[i]);
								productTreeRowIdx = productTreeRowIdx + 1;
							}
						});
					</script>
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
