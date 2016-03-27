<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title></title>
<link rel="stylesheet" href="${ctxStatic }/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctxStatic }/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<link rel="stylesheet"
	href="${ctxStatic }/bootstrap/3.3.5/css/custom.css">
<script type="text/javascript"src="${ctxStatic }/bootstrap/3.3.5/js/jquery-1.9.1.min.js"></script>
<script src="${ctxStatic }/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="${ctxStatic }/bootstrap/3.3.5/js/custom.js"></script>

<style>
	.btn-group-vertical{
		width:80%;
		margin-left:10%;
	}
	.btn-group-vertical button{
		padding:20px;
	}
</style>
<script>
	$(document).ready(function(){
	
		$('button').click(function(){
			
			var href = $(this).attr('data-href');
			
			var url = "/interface/checkLogin";
			ajax(url,{},"POST",function(data){
				var code = data.result;
				auth(code);
				if (code == 0) {
					location.href = href;
				} else {
					mAlert(data.reason);
				}
			},function(){
				mAlert("检查身份失败");
			});
			
		});
		
	});
</script>
</head>
<body>
	<div class="btn-group-vertical" role="group">
		<br>
		<button class="btn btn-default" data-href="scanSave">扫描入库</button>
		<br>
		<button class="btn btn-default" data-href="scanFlow">流程扫描</button>
		<br>
		<button class="btn btn-default" data-href="scanLoss">报损扫描</button>
		<br>
		<button class="btn btn-default" data-href="setting">设置</button>
	</div>
</body>
</html>