<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="Cache-Control" content="no-cache">
<title></title>

<link rel="stylesheet" href="${ctxStatic }/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctxStatic }/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="${ctxStatic }/bootstrap/3.3.5/css/custom.css">
<script type="text/javascript"src="${ctxStatic }/bootstrap/3.3.5/js/jquery-1.9.1.min.js"></script>
<script src="${ctxStatic }/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="${ctxStatic }/bootstrap/3.3.5/js/custom.js"></script>

<script>
					
	$(document).ready(function(){
		$('#productName').html(getSetting("sampleProduct"));
		$('#scan').focus();
		$('#scan').keyup(function(e){
			var value = $(this).val();
			if(e.keyCode==13){
				var data = {};
				data.productName = value;
				var url = "/interface/queryProduct";
				ajax(url,data,"POST",function(data){
					var code = data.result;
				 	auth(code);
				 	if(code==0){
				 		var product = data.data;
				 		setSetting("sampleProduct",product.name);
				 		$('#productName').html(product.name);
				 		mAlert("设置成功");
				 	}else{
				 		mAlert(data.reason);
				 		alarm();
				 	}
				},function(){
					mAlert("设置失败");
					alarm();
				});
			}
		});
		
		$('.clear').click(function(){
			$('#scan').val('');
			$('#scan').focus();
		});
	});
</script>

</head>
<body>
	<div class="toolbar affix-top">
		<div class="row">
			<div class="col-xs-2">
				<i class="glyphicon glyphicon-menu-left back" data-back="index"></i>
			</div>
			<div class="col-xs-8 title">样品设置</div>
		</div>
	</div>
	
	<div class="m-content" >
		<br>
		<div class="form-group">
			<div class="row">
				<div class="col-xs-9">
					<div id="productName"></div>
					<input type="text" class="form-control" id="scan" placeholder="">
				</div>
				<div class="col-xs-2">
					<button class="btn btn-default clear">清空</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
</body>
</html>