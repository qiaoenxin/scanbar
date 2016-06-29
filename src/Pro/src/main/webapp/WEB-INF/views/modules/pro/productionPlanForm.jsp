<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>生产计划管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	
		jQuery.validator.addMethod("productUnique",function(value, element, param) {
			return $("#contentTable select option[value="+value+"]:selected").length < 2;
		},"产品不得重复");

		jQuery.validator.addMethod("checkUnique", function(value, element) {
			var id = $("#id").val();
		    var deferred = $.Deferred();//创建一个延迟对象
		    $.ajax({
		        url:"${ctx}/pro/productionPlan/checkSerial?id="+id+"&serialNum="+value,
		        async:false,//要指定不能异步,必须等待后台服务校验完成再执行后续代码
		        dataType:"json",
		        success:function(data) {
		            if (data) {
		                deferred.resolve();    
		            } else {
		                deferred.reject();
		            }               
		        }
		    });
		    return deferred.state() == "resolved" ? true : false;
		}, "批次已存在");		
		
		var stock = ${fns:toJson(stock)};
		$(document).ready(function() {
			$("#serialNum").focus();
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
		});
		
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
				$(this).change(function(e){
					var productId = $(this).val();
					($(list+idx+"_stock").val(stock[productId] || 0));
				});		
				$(this).change();
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
		function preview(prefix){
			var id = $(prefix+" select").val();
			var number = $(prefix+"_number").val();
			var endDate = $("#endDate").val();
			
			var url = "iframe:${ctx}/pro/productionPlanTree/previewList?productId="+id+"&number="+number+"&date="+endDate;
		    var buttons = {"关闭":false};
		    top.$.jBox.open(url, "明细预览", 800, 500,{
				buttons:buttons, submit:function(v, h, f){
					return true;
				}, loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
			
		}		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/pro/productionPlan/">生产指令列表</a></li>
		<li class="active"><a href="${ctx}/pro/productionPlan/form?id=${productionPlan.id}">生产指令<shiro:hasPermission name="pro:productionPlan:edit">${not empty productionPlan.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="pro:productionPlan:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="productionPlan" action="${ctx}/pro/productionPlan/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		
		<div class="control-group">
			<label class="control-label">批次:</label>
			<div class="controls">
				<form:input path="serialNum" htmlEscape="false" maxlength="200" checkUnique="true" class="required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">日期:</label>
			<div class="controls">
				<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate required"
				value="${fns:formatDate(productionPlan.endDate,'yyyy-MM-dd')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		
			<div class="control-group">
				<label class="control-label">产品：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>产品</th>
								<th>当前库存</th>
								<th>数量</th>
								<shiro:hasPermission name="pro:productionPlan:edit"><th>操作</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="productionList">
						</tbody>
						<shiro:hasPermission name="pro:productionPlan:edit"><tfoot>
							<tr><td colspan="5"><a href="javascript:" onclick="addRow('#productionList', productionRowIdx, productionTpl);productionRowIdx = productionRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="productionTpl">//<!--
							<tr id="productionList{{idx}}">
								<td class="hide">
									<input id="productionList{{idx}}_id" name="productionList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
									<input id="productionList{{idx}}_delFlag" name="productionList[{{idx}}].delFlag" type="hidden" value="0"/>
								</td>
								<td style="width: 40%;">
									<select id="productionList{{idx}}_id" name="productionList[{{idx}}].product.id" data-value="{{row.product.id}}" class="input-small " style="width: 80%;" productUnique="true" required>
										<c:forEach items="${productList}" var="product">
											<option value="${product.id}">${product.name}</option>
										</c:forEach>
									</select>
								</td>
								<td style="width: 20%;">
									<input id="productionList{{idx}}_stock" type="text" readonly value="0" />
								</td>
								<td style="width: 25%;">
									<input id="productionList{{idx}}_number" name="productionList[{{idx}}].number" type="text" value="{{row.numberRenderer}}" min="1" style="width: 80%;">
								</td>
								<shiro:hasPermission name="pro:productionPlan:edit"><td class="text-center" style="width: 15%;line-height: 30px;">
    								<a href="javascript:preview('#productionList{{idx}}');">预览</a>
									<a href="javascript:delRow(this, '#productionList{{idx}}');" >删除</a>
								</td></shiro:hasPermission>
							</tr>//-->
						</script>
					<script type="text/javascript">
						var productionRowIdx = 0, productionTpl = $("#productionTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(productionList)};
							for (var i=0; i<data.length; i++){
								addRow('#productionList', productionRowIdx, productionTpl, data[i]);
								productionRowIdx = productionRowIdx + 1;
							}
							if(data.length == 0){
								addRow('#productionList', productionRowIdx, productionTpl);
								productionRowIdx = productionRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>

		<div class="form-actions">
			<shiro:hasPermission name="pro:productionPlan:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
