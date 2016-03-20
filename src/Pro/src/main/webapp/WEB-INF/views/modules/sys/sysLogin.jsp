<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>${fns:getConfig('productName')} 登录</title>
<meta name="decorator" content="default" />
<style type="text/css">
html,body {
	height: 100%;
	color: #ffffff;
}

legend {
	border-bottom: 1px solid rgba(229, 229, 229, 0.24);
}

body {
	background-image: url("${ctxStatic}/common/images/login_bg.png");
}

.login-wraper {
	background-image: url("${ctxStatic}/common/images/login.png");
	text-align: center;
	height: 400px;
}

.login-wraper .body {
	border: 1px solid rgba(241, 241, 241, 0.27);
	width: 300px;
	margin: auto;
	padding: 30px;
	border-radius: 2px 2px;
	text-align: left;
}

.body label {
	width: 60px;
}

.login-btn {
	padding: 5px 20px;
	border-radius: 2px 2px;
}

.control-group {
	border-bottom: 0px;
}

#messageBox {
	width: 200px;
	position: absolute;
	text-align: center;
	top: 60px;
	left: 40%;
	background: rgba(23, 22, 22, 0.27);
    text-shadow: none;
    border: 1px solid rgba(0, 0, 0, 0.08);
}
</style>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#loginForm")
								.validate(
										{
											rules : {
												validateCode : {
													remote : "${pageContext.request.contextPath}/servlet/validateCodeServlet"
												}
											},
											messages : {
												username : {
													required : "请填写用户名."
												},
												password : {
													required : "请填写密码."
												},
												validateCode : {
													remote : "验证码不正确.",
													required : "请填写验证码."
												}
											},
											errorLabelContainer : "#messageBox",
											errorPlacement : function(error,
													element) {
												error.appendTo($("#loginError")
														.parent());
											}
										});

						var wh = $(window).height();
						var h = $('.login-wraper').height();
						h = (wh - h) / 2;
						$('.login-wraper').css('margin-top', h);
					});
					
					// 如果在框架中，则跳转刷新上级页面
			if(self.frameElement && self.frameElement.tagName=="IFRAME"){
				parent.location.reload();
			}
</script>
</head>
<body id="login-body">
	<div class="container">
		<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
		<%
			String error = (String) request
					.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		%>
		<div id="messageBox"
			class="alert alert-error <%=error == null ? "hide" : ""%>">
			<button data-dismiss="alert" class="close">×</button>
			<label id="loginError" class="error"><%=error == null ? ""
					: "com.sujie.sirius.manage.modules.sys.security.CaptchaException"
							.equals(error) ? "验证码错误, 请重试." : "用户或密码错误, 请重试."%></label>
		</div>
		<div class="login-wraper">
			<form id="loginForm" class="form login-form" action="${ctx}/login"
				method="post">
				<legend>
					<div style="color:#FFFFFF;">${fns:getConfig('productName')}</div>
				</legend>
				<div class="body">
					<div class="control-group">
						<div class="controls">
							<label>账&nbsp;&nbsp;&nbsp;号：</label><input type="text"
								id="username" name="username" class="required"
								style="margin-left:3px;" value="${username}" placeholder="登录名">
						</div>
					</div>

					<div class="control-group">
						<div class="controls">
							<label>密&nbsp;&nbsp;&nbsp;码：</label><input type="password"
								id="password" name="password" class="required"
								style="margin-left:3px;" placeholder="密码" />
						</div>
					</div>

					<div class="control-group">
						<div class="controls">
							<label>验证码：</label>
							<tags:validateCode name="validateCode"
								inputCssStyle="margin-bottom:0;" />
						</div>
					</div>

					<div class="control-group" style="text-align:center;">
						<label class="checkbox inline"> <input type="checkbox"
							id="rememberMe" name="rememberMe" style="margin-top: 5px;">
							<span style="color:#FFFFFF;">记住我</span> </label> <input
							class="btn login-btn" type="submit" value="登 录" />
					</div>
				</div>
			</form>
		</div>
	</div>

</body>
</html>