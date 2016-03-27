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

<script>
					
	$(document).ready(function(){
		$('#scan').focus();
		$('#scan').keyup(function(){
			var value = $(this).val();
			if(value.indexOf('/n')!=-1 || value.indexOf('<br>')!=-1 || value.indexOf('<br/>')!=-1){
				var url = "${contextPath}/interface/scanSave";
				var data = {};
				data.detailNo = value;
				$.ajax({
					url:url,
					type:"POST",
				 	data: data,
				 	dataType:"json",
				 	success: function(data){
				 		if(data.result==0){
				 			mAlert("入库成功");
				 		}else{
				 			mAlert(data.reason);
				 		}
				 	},
				 	error:function(){
				 		mAlert("入库失败");
				 	}
				});
			}
		});
	});
</script>

</head>
<body>
	<div class="toolbar affix-top">
		<div class="row">
			<div class="col-xs-2">
				<i class="glyphicon glyphicon-menu-left back"></i>
			</div>
			<div class="col-xs-8 title">扫描入库</div>
		</div>
	</div>
	
	<div class="m-content" >
		<br>
		<div class="form-group">
			<input type="text" class="form-control" id="scan" placeholder="">
		</div>
	</div>
	
	
	
</body>
</html>