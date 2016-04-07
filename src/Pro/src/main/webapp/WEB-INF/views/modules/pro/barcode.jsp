<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>设备二维码</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	</style>
	<script src="${ctxStatic}/qrcode/jquery.qrcode.min.js" type="text/javascript"></script>
	<script type="text/javascript">
	$(function(){
		$("#clientUrl").qrcode({"width":150,"height":150,"text":"${clientUrl}"});;
		$("#deviceKey").qrcode({"width":150,"height":150,"text":"${device.deviceKey}"});
		$(".control-group").on("mouseover",function(){
			var tar = $(this).find(".barcode");
			tar.show();
		}).on("mouseout",function(){
			var tar = $(this).find(".barcode");
			tar.hide();
		});
	});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/pro/device/">设备号列表</a></li>
		<li class="active"><a href="#">设备号</a></li>
	</ul><br/>
		<tags:message content="${message}"/>
		<table width="100%">
			<tr>
				<td width="50%">
					<div class="control-group">
						<label class="control-label">服务器地址: <font color="red">鼠标悬停显示二维码</font></label>
						<div class="controls">
							${clientUrl }<br/>
							<div id="clientUrl" class="barcode" style="display: none">
							</div>
						</div>
					</div>
				</td>
				<td>
					<div class="control-group">
						<label class="control-label">设备号: <font color="red">鼠标悬停显示二维码</font></label>
						<div class="controls">
							${device.deviceKey }<br/>
							<div id="deviceKey" class="barcode" style="display: none">
							</div>
						</div>
					</div>
				</td>
			</tr>
		</table>
		
		
		<div class="form-actions" style="text-align: center;">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
</body>
</html>
