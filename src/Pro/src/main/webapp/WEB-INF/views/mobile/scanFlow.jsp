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
<link rel="stylesheet" href="${ctxStatic }/bootstrap/3.3.5/css/custom.css">
<script type="text/javascript"src="${ctxStatic }/bootstrap/3.3.5/js/jquery-1.9.1.min.js"></script>
<script src="${ctxStatic }/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="${ctxStatic }/bootstrap/3.3.5/js/custom.js"></script>

<style>
	.btn-group-vertical button{
		padding:20px;
	}
</style>
<script>
	$(document).ready(function() {
		$('#scan').hide();
		init();
	});
	
	
	function init(){
		var url = "/interface/flow";
		ajax(url,{},"GET",function(data){
			var code = data.result;
			auth(code);
			if(code==0){
				var jDom = $('#flows');
				var flows = data.data;
				$.each(flows,function(i,flow){
					var html = '<button class="btn btn-default" data-flow="'+flow.value+'">'+flow.label+'</button><br>';
					jDom.append(html);
				});
					
				bindEvent();
			}else{
				mAlert(data.reason);
			}
		},function(){
			mAlert("查询失败");
		});
	}
	
	function bindEvent(){
		var flowId = '';
		$('.btn-group-vertical').find('.btn').click(function(){
			flowId = $(this).attr('data-flow');
			location.href = 'scanFlowInput?flowId='+flowId;
		});
	}
</script>
<style>
.btn-group-vertical {
	width: 80%;
	margin-left: 10%;
	margin-top: 30px;
}

</style>
</head>
<body>
	<div class="toolbar affix-top">
		<div class="row">
			<div class="col-xs-2">
				<i class="glyphicon glyphicon-menu-left back" data-back="index"></i>
			</div>
			<div class="col-xs-8 title">流程扫描</div>
		</div>
	</div>
	
	<div class="m-content" >
		<div class="btn-group-vertical" role="group" id="flows">
		</div>
	</div>
</body>
</html>