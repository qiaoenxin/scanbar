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
<script type="text/javascript"src="${ctxStatic }/bootstrap/3.3.5/js/jquery-1.9.1.min.js"></script>
<script src="${ctxStatic }/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="${ctxStatic }/bootstrap/3.3.5/js/custom.js"></script>

<style>
	.btn-group-vertical{
		width:80%;
		margin-left:10%;
	}
	.btn-group-vertical a{
		padding:20px;
	}
</style>
</head>
<body>
	<div class="btn-group-vertical" role="group">
		<br>
		<a class="btn btn-default" href="scanSave">扫描入库</a>
		<br>
		<a class="btn btn-default" href="scanFlow">流程扫描</a>
		<br>
		<a class="btn btn-default" href="scanLoss">报损扫描</a>
		<br>
		<a class="btn btn-default" href="setting">设置</a>
	</div>
</body>
</html>