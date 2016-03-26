<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title></title>

<link rel="stylesheet"
	href="${ctxStatic }/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${ctxStatic }/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<link rel="stylesheet"
	href="${ctxStatic }/bootstrap/3.3.5/css/custom.css">
<script type="text/javascript"
	src="${ctxStatic }/bootstrap/3.3.5/js/jquery-1.9.1.min.js"></script>
<script src="${ctxStatic }/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="${ctxStatic }/bootstrap/3.3.5/js/custom.js"></script>

</head>
<body>
	<div class="toolbar affix-top">
		<div class="row">
			<div class="col-xs-2">
				<i class="glyphicon glyphicon-menu-left back"></i>
			</div>
			<div class="col-xs-8 title">设置</div>
		</div>
	</div>

	<div class="m-content">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">服务器地址</h3>
			</div>
			<div class="panel-body">
				<input type="text" class="form-control" id="" placeholder="请输入服务器地址">
			</div>
		</div>
		
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">设备号</h3>
			</div>
			<div class="panel-body">
				<input type="text" class="form-control" id="" placeholder="请输入设备号">
			</div>
		</div>
	</div>
	
	<div class="center-block affix-bottom">
		<button class="btn btn-default" style="width:80%;" type="submit">提交</button>
	</div>
</body>
</html>