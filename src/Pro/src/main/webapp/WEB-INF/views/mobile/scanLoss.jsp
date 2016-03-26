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
		init();
	});
	
	var detailNo = "201603202324131073206";
	
	function init(){
		var url = "${contextPath}/interface/querySub";
		var data = {};
		data.detailNo = detailNo;
		$.ajax({
			url:url,
			type:"GET",
			data: data,
			dataType:"json",
			success: function(data){
				if(data.result==0){
					data = data.data;
					$.each(data,function(i,item){
						var html = getItemHtml(item.product.id,item.product.serialNum,item.number);
						$('#list').append(html);
					});
				 }else{
				 	mAlert(data.reason);
				 }
			},
			error:function(){
				mAlert("查询失败");
			}
		});
	}
	
	function getItemHtml(id,serialNum,number){
		var html = '<li class="list-group-item row" data-id="'+id+'">';
		html += '<div class="col-xs-8">'+serialNum+'：</div>';
		html += '<div class="col-xs-4"><input type="text" class="form-control" id="" placeholder="" value="'+number+'"></div>';
		html += '</li>';
		return html;
	}
	
	function submit(){
		var products = new Array();
		$('#list').find('li').each(function(i,dom){
			var jDom = $(dom);
			var id = jDom.attr('data-id');
			var number = jDom.find('input').val();
			var item = id + ',' + number;
			products.push(item);
		});
		
		var url = "${contextPath}/interface/loss";
		var data = {};
		data.detailNo = detailNo;
		data.products = products;
		
		$.ajax({
			url:url,
			type:"POST",
			data: data,
			dataType:"json",
			success: function(data){
				if(data.result==0){
					mAlert("提交成功");
				 }else{
				 	mAlert(data.reason);
				 }
			},
			error:function(){
				mAlert("提交失败");
			}
		});
	}
</script>
</head>
<body>
	<div class="toolbar affix-top">
		<div class="row">
			<div class="col-xs-2">
				<i class="glyphicon glyphicon-menu-left back"></i>
			</div>
			<div class="col-xs-8 title">报损扫描</div>
		</div>
	</div>
	
	<ul class="list-group m-content" id="list">
		
	</ul>
	<br>
	<div class="center-block affix-bottom">
		<button class="btn btn-default" style="width:80%;" onclick="submit()">提交</button>
	</div>
</body>
</html>