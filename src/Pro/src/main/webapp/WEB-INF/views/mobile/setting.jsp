<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="Cache-Control" content="no-cache">
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

	$(document).ready(function(){
		init();
	});
	
	function loginOut(){
		var url = "/interface/logout";
		ajax(url,{},"POST",function(data){
			var code = data.result;
			if(code==0){
				location.href='login';
			}else{
				mAlert(data.reason);
			}
		},function(){
			mAlert("登出失败");
		});
	}
	
	function init(){
		var addressUrl = getSetting("addressUrl");
		var deviceKey = getSetting("deviceKey");
		$('#address').val(addressUrl);
		$('#device').val(deviceKey);
	}
	function submit(){
		var address = $('#address').val();
		var device = $('#device').val();
		$.ajax({
			url: address + "/interface/test",
			data: {device: device},
			success: function(data){
				if(data.result == 0){
					submit0();
				}else{
					mAlert(data.reason);
				}
			},
			error: function(){
				mAlert("无法连接服务器，");
			}
		});
	}
	
	function submit0(){
		var address = $('#address').val();
		var device = $('#device').val();
		setSetting("addressUrl",address);
		setSetting("deviceKey",device);
		mAlert("提交成功");
	}
</script>

</head>
<body>
	<div class="toolbar affix-top">
		<div class="row">
			<div class="col-xs-2">
				<i class="glyphicon glyphicon-menu-left back" data-back="index"></i>
			</div>
			<div class="col-xs-8 title">设置</div>
			<div class="col-xs-2">
				<a href="javascript:loginOut();" style="color:#FFFFFF;">注销</a>
			</div>
		</div>
	</div>

	<div class="m-content">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">服务器地址</h3>
			</div>
			<div class="panel-body">
				<input type="text" class="form-control" id="address" placeholder="请输入服务器地址">
			</div>
		</div>
		
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">设备号</h3>
			</div>
			<div class="panel-body">
				<input type="text" class="form-control" id="device" placeholder="请输入设备号">
			</div>
		</div>
		
		<div class="panel panel-default">
			<div class="panel-heading" onclick="location.href='scanFlow';">
				<div style="display: inline-block;;width:50%;"><h3 class="panel-title">工位设置</h3></div><div style="display: inline-block;width:50%;text-align:right;"><a href="javascript:void(0);">》</a></div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading" onclick="location.href='sample';">
				<div style="display: inline-block;;width:50%;"><h3 class="panel-title">样品设置</h3></div><div style="display: inline-block;width:50%;text-align:right;"><a href="javascript:void(0);">》</a></div>
			</div>
		</div>
	</div>
	
	<div class="center-block">
		<button class="btn btn-default" style="width:80%;" onclick="submit()">提交</button>
	</div>
</body>
</html>