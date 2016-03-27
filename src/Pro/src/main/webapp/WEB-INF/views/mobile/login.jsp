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

<script>
	$(document).ready(function() {
	});

	function submit() {
		var loginName = $('#loginName').val();
		var password = $('#password').val();
		var device = $('#device').val();
		if(!loginName){
			mAlert("请输入用户名");
			$('#loginName').focus();
			return;
		}
		if(!password){
			mAlert("请输入密码");
			$('#password').focus();
			return;
		}
		if(!device){
			mAlert("请输入设备号");
			$('#device').focus();
			return;
		}
		
		var url = "${contextPath}/interface/login";
		var data = {};
		data.loginName = loginName;
		data.password = password;
		data.device = device;

		$.ajax({
			url : url,
			type : "POST",
			data : data,
			dataType : "json",
			success : function(data) {
				if (data.result == 0) {
					var user = {};
					user.loginName = loginName;
					user.device = device;
					localStorage.setItem("user",JSON.stringify(user));
					location.href='index';
				} else {
					mAlert(data.reason);
				}
			},
			error : function() {
				mAlert("登录失败");
			}
		});
	}
</script>
</head>
<body>
	<div class="toolbar affix-top">
		<div class="row">
			<div class="col-xs-12 title">登录</div>
		</div>
	</div>

	<div class="m-content" style="padding:20px;">
		<div class="form-group">
			<label for="exampleInputEmail1">用户名</label> <input type="text"
				class="form-control" id="loginName" placeholder="请输入用户名">
		</div>
		<div class="form-group">
			<label for="exampleInputPassword1">密码</label> <input type="password"
				class="form-control" id="password" placeholder="请输入密码">
		</div>
		<div class="form-group">
			<label for="exampleInputEmail1">设备号</label> <input type="text"
				class="form-control" id="device" placeholder="请输入设备号">
		</div>
		<div style="text-align:center;">
			<button class="btn btn-default" onclick="submit()">提交</button>
		</div>
	</div>
</body>
</html>