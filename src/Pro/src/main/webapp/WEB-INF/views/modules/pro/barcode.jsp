<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>设备二维码</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/qrcode/jquery.qrcode.min.js" type="text/javascript"></script>
	<script type="text/javascript">
	$(function(){
		$("#clientUrl").qrcode({"width":150,"height":150,"text":"${clientUrl}"});;
		$("#deviceKey").qrcode({"width":150,"height":150,"text":"${device.deviceKey}"});;
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
						<label class="control-label">服务器地址:</label>
						<div class="controls">
							${clientUrl }<br/>
							<div id="clientUrl">
							</div>
						</div>
					</div>
				</td>
				<td>
					<div class="control-group">
						<label class="control-label">设备号:</label>
						<div class="controls">
							${device.deviceKey }<br/>
							<div id="deviceKey">
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
