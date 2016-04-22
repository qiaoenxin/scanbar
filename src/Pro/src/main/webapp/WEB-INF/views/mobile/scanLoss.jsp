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

	var detailNo = '';
	$(document).ready(function(){
		
		$('#scan').focus();
		$('#scan').keyup(function(e){
			var value = $(this).val();
			if(e.keyCode==13){
				detailNo = value;
				init();
			}
		});
		
		
		$('.clear').click(function(){
			$('#scan').val('');
			$('#scan').focus();
		});
		
	});
	
	function init(){
		var data = {};
		data.detailNo = detailNo;
		var url = "/interface/querySub";
		ajax(url,data,"GET",function(data){
			var code = data.result;
			auth(code);
			if(code==0){
				data = data.data;
				$.each(data,function(i,item){
					var html = getItemHtml(item.product.id,item.product.name,item.number);
					$('#list').append(html);
				});
					
				$('.submit').show();
			}else{
				mAlert(data.reason);
			}
		},function(){
			mAlert("查询失败");
		});
	}
	
	function getItemHtml(id,serialNum,number){
		var html = '<li class="list-group-item row" data-id="'+id+'">';
		html += '<div class="col-xs-8">'+serialNum+'：</div>';
		html += '<div class="col-xs-4"><input type="text" class="form-control" id="" placeholder="" value="0" max-value="'+number+'"></div>';
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
		products = products.join(';');
		
		var url = "/interface/loss";
		var data = {};
		data.detailNo = detailNo;
		data.products = products;
		
		ajax(url,data,"POST",function(data){
			var code = data.result;
			auth(code);
			if(code==0){
				var code = data.result;
				auth(code);
				if(code==0){
					mAlert("提交成功");
				 }else{
				 	mAlert(data.reason);
				 	alarm();
				 }
			}else{
				mAlert(data.reason);
				alarm();
			}
		},function(){
			mAlert("提交失败");
		});
	}
</script>
</head>
<body>
	<div class="toolbar affix-top">
		<div class="row">
			<div class="col-xs-2">
				<i class="glyphicon glyphicon-menu-left back" data-back="index"></i>
			</div>
			<div class="col-xs-8 title">报损扫描</div>
		</div>
	</div>
	
	<div class="m-content">
		<br>
		<div class="form-group">
			<div class="row">
				<div class="col-xs-9">
					<input type="text" class="form-control" id="scan" placeholder="">
				</div>
				<div class="col-xs-2">
					<button class="btn btn-default clear">清空</button>
				</div>
			</div>
		</div>
		<ul class="list-group" id="list">
			
		</ul>
		<div class="center-block submit" style="display:none">
			<button class="btn btn-default" style="width:80%;" onclick="submit()">提交</button>
		</div>
	</div>
	
	<br>
	
</body>
</html>