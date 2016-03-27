<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>${fns:getConfig('productName')}</title>
<%@include file="/WEB-INF/views/include/dialog.jsp"%>
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<meta name="decorator" content="default" />
<style type="text/css">
#main {
	padding: 0;
	margin: 0;
	background: #e7e8eb;
}

#main .container-fluid {
	padding: 0px;
	margin: 0px 60px;
	background: white;
}

#header {
	margin: 0 0 10px;
	position: static;
}

#header .navbar-inner {
	padding: 0px 80px;
	border-bottom: 2px solid #015b9a;
}

#header li {
	font-size: 14px;
	_font-size: 12px;
}

#header .brand {
	font-family: Helvetica, Georgia, Arial, sans-serif, 黑体;
	font-size: 26px;
	padding-left: 33px;
}

#footer {
	margin: 8px 0 0 0;
	font-size: 14px;
	text-align: center;
	border-top: 2px solid #015b9a;
	background: white;
	margin-top: 20px;
	line-height: 60px;
}

#footer,#footer a {
	color: #999;
}

#left {
	border-right: 1px solid #e7e7eb;
}

#menu{
	margin-top:32px;
}
.menu.no_extra {
	margin: 0px;
}

.menu:first-child {
	border: 0px;
}

.menu {
	padding-bottom: 0px;
}

.menu {
	padding-top: 6px;
}

.menu_title {
	margin-bottom: 0;
}

.menu_title {
	padding: 0 30px 0;
	width: auto;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
	word-wrap: normal;
	line-height: 34px;
	color: #8d8d8d;
}

dt,dd {
	font-weight: normal;
	margin-left: 0px;
}

.menu_item {
	line-height: 34px;
}

.menu_item:hover {
	background: #F5F5F5;
}

.menu_item a {
	display: block;
	padding: 0 0 0 26px;
	width: auto;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
	word-wrap: normal;
	color: #222;
	text-decoration: none;
}

.menu_item.selected {
	background-color: #ec4906;
	color: #fff;
}

.menu_item.selected a,.menu_item.selected .glyphicon {
	color: #fff;
}
.navbar-inner{
    background-image: url("${ctxStatic}/common/images/login_bg.png");
    filter:none;
}
.navbar-inner,.navbar-inner li {
	min-height: 40px;
	line-height: 40px;
}
.ztree li{
	min-height: none;
	line-height: normal;
}
.navbar .nav>li>a {
	text-shadow: none;
	color:#ddd;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#menu a.menu").click(function() {
			$("#menu li.menu").removeClass("active");
			$(this).parent().addClass("active");
			if (!$("#openClose").hasClass("close")) {
				$("#openClose").click();
			}
		});

		$('#left .menu_item').click(function() {
			$('#left .menu_item').removeClass('selected');
			$(this).addClass('selected');
		});

		initSiteTree();
		
		
		
	});

	function initSiteTree() {

		$('.dropdown-toggle').click(function() {
			$('.dropdown-menu').toggle();
			$('.dropdown-menu').css('left',0);
			$('.dropdown-menu').css('right','inherit');
		});

		var lastValue = "", nodeList = [];
		var tree, setting = {
			view : {
				selectedMulti : false
			},
			check : {
				enable : "${checked}",
				nocheckInherit : true
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			view : {
				fontCss : function(treeId, treeNode) {
					return (!!treeNode.highlight) ? {
						"font-weight" : "bold"
					} : {
						"font-weight" : "normal"
					};
				}
			},
			callback : {
				beforeClick : function(id, node) {
					if ("${checked}" == "true") {
						tree.checkNode(node, !node.checked, true, true);
						return false;
					}
				},
				onClick : function(event, treeId, treeNode) {
					window.parent.refreshSite(treeNode.id);
				}
			}
		};

		$.get("${ctx}/cms/site/treeData?t=" + new Date().getTime(), function(
				zNodes) {

			$.each(zNodes, function(i, node) {
				node.url = "${ctx}/cms/site/select?id=" + node.id;
				node.target = "_self";
			});

			// 初始化树结构
			tree = $.fn.zTree.init($("#tree"), setting, zNodes);

			// 默认展开一级节点
			var nodes = tree.getNodesByParam("level", 0);
			for ( var i = 0; i < nodes.length; i++) {
				tree.expandNode(nodes[i], true, false, false);
			}
			// 默认选择节点
			var ids = "${selectIds}".split(",");
			for ( var i = 0; i < ids.length; i++) {
				var node = tree.getNodeByParam("id", ids[i]);
				if ("${checked}" == "true") {
					try {
						tree.checkNode(node, true, true);
					} catch (e) {
					}
					tree.selectNode(node, false);
				} else {
					tree.selectNode(node, true);
				}
			}
		});
	}

	function openUserInfo() {
		var url = "iframe:${ctx}/sys/user/info";
		var buttons = {
			"关闭" : false
		};
		top.$.jBox.open(url, "个人信息", 900, 460, {
			buttons : buttons,
			submit : function(v, h, f) {
			},
			loaded : function(h) {
				$(".jbox-content", top.document).css("overflow-y", "hidden");
			}
		});
	}

	function dyniframesize(down) {
		var pTar = null;
		if (document.getElementById) {
			pTar = document.getElementById(down);
		} else {
			eval('pTar = ' + down + ';');
		}
		if (pTar && !window.opera) {
			//begin resizing iframe 
			pTar.style.display = "block"
			var height;
			if (pTar.contentDocument && pTar.contentDocument.body.offsetHeight) {
				//ns6 syntax 
				height = pTar.contentDocument.body.offsetHeight + 40;
			} else if (pTar.Document && pTar.Document.body.scrollHeight) {
				//ie5+ syntax 
				height = pTar.Document.body.scrollHeight + 40;
			}
			pTar.height = height;
			$('#left').height('auto');
			var height_ = $('#left').height();
			if (height > height_) {
				$('#left').height(height + 50);
			}
		}
	}
</script>
</head>
<body>
	<div id="main">
		<div id="header" class="navbar navbar-fixed-top">
			<div class="navbar-inner">
				<div class="brand"><a href="" style="font-size: 24; color: white;padding-top: 200px;"><!--<img src="${ctxStatic}/common/images/logo.png" />--></a></div>
				<div class="nav-collapse">
					<ul id="menu" class="nav">
						<c:set var="firstMenu" value="true" />
						<c:forEach items="${fns:getMenuList()}" var="menu"
							varStatus="idxStatus">
							<c:if test="${menu.parent.id eq '1' && menu.isShow eq '1'}">
								<li class="menu"><a
									class="menu" href="${ctx}/sys/menu/tree?parentId=${menu.id}"
									target="menuFrame">${menu.name}</a>
								</li>
								<c:if test="${firstMenu}">
									<c:set var="firstMenuId" value="${menu.id}" />
								</c:if>
								<c:set var="firstMenu" value="false" />
							</c:if>
						</c:forEach>
					</ul>

					<ul class="nav pull-right">
						<li><a href="javascript:openUserInfo();" title="个人信息">您好,
								<shiro:principal property="name" /> </a></li>
						<li><a href="${ctx}/logout" title="退出登录" title="退出"><i
								class="iconfont icon-loginout"></i> </a></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
		<div class="container-fluid">
			<div id="content" class="row-fluid">
				<div id="left">
					<iframe id="menuFrame" name="menuFrame"
						src="${ctx}/sys/menu/tree?parentId=${firstMenuId}"
						style="overflow:visible;" scrolling="yes" frameborder="no"
						width="100%" height="650"></iframe>
				</div>
				<div id="openClose" class="close">&nbsp;</div>
				<div id="right">
					<iframe id="mainFrame"
						onload="Javascript:dyniframesize('mainFrame')" name="mainFrame"
						src="" style="overflow:visible;margin-top: 30px;min-height:600px;"
						scrolling="yes" frameborder="no" width="100%"></iframe>
				</div>
			</div>
		</div>

		<div id="footer" class="row-fluid">
			Copyright &copy; 2012-${fns:getConfig('copyrightYear')}
			${fns:getConfig('productName')} - Powered By <a
				href="https://github.com/thinkgem/tongchaung" target="_blank">${fns:getConfig('PoweredBy')}</a>
			${fns:getConfig('version')}
		</div>
	</div>
	<script type="text/javascript">
		var leftWidth = "200"; // 左侧窗口大小
		function wSize() {
			var minHeight = 500, minWidth = 980;
			var strs = getWindowSize().toString().split(",");
			$("#menuFrame, #openClose").height(
					(strs[0] < minHeight ? minHeight : strs[0])
							- $("#header").height() - $("#footer").height()
							- 32);
			$("#openClose").height($("#openClose").height() - 5);
			if (strs[1] < minWidth) {
				$("#main").css("width", minWidth - 10);
			} else {
				$("#main").css("width", "auto");
			}
			$("html,body").css({
				"height" : "100%"
			});
			$("html").css({
				"overflow" : "hidden",
				"overflow-x" : "hidden",
				"overflow-y" : "scroll"
			});
			$("body").css({
				"overflow" : "scroll",
				"overflow-x" : "hidden"
			});

			$("#right").width(
					$("#content").width() - $("#left").width()
							- $("#openClose").width() - 5);
		}
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>