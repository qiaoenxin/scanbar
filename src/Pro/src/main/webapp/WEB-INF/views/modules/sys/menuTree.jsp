<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>菜单导航</title>
<meta name="decorator" content="default" />
<style>
	.accordion-heading a.active{
		background:#015b9a;
		color: #fff;
		text-shadow: 0 -1px 0 rgba(0,0,0,0.2);
	}
	.nav-list>.active>a, .nav-list>.active>a:hover, .nav-list>.active>a:focus {
	    background-color: #015b9a;
	    padding: 10px;
	}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$(".nav-list a.third").click(function() {
			/*$('.accordion-toggle i').removeClass('icon-chevron-down');
			$('.accordion-toggle i').addClass('icon-chevron-right');
			if (!$($(this).attr('href')).hasClass('in')) {
				$(this).children('i').removeClass('icon-chevron-right');
				$(this).children('i').addClass('icon-chevron-down');
			}*/
			$(".nav-list li").removeClass('active');
			$(this).parent('li').addClass('active');
			
			$(".accordion-heading a").removeClass('active');
			$(".accordion-heading a").find('i').removeClass('icon-white');
			
		});
		
		$(".accordion-heading a.second").click(function() {
			$(".accordion-heading a").removeClass('active');
			$(".accordion-heading a").find('i').removeClass('icon-white');
			$(this).addClass('active');
			$(this).find('i').addClass('icon-white');
			
			$(".nav-list li").removeClass('active');
			$(this).parent('li').addClass('active');
		});
		
		var menu = $('#menu a:first');
		if(menu.hasClass('second')){
			$(".accordion-heading a.second:first i").click();
		}else{
			$(".nav-list li a.third:first i").click();
		}
	});
</script>
</head>
<body>
	<div class="accordion" id="menu">
		<c:set var="menuList" value="${fns:getMenuList()}" />
		<c:set var="firstMenu" value="true" />
		<c:forEach items="${menuList}" var="menu" varStatus="idxStatus">
			<c:if test="${menu.parent.id eq (not empty param.parentId?param.parentId:'1')&&menu.isShow eq '1'}">
				<c:set var="menuChildList" value="${fns:getMenuChildList(menu.id)}" />
				<div class="accordion-group">
					<div class="accordion-heading">
						<c:choose>
							<c:when test="${empty menuChildList}">
								<a class="accordion-toggle second" href="${ctx}${menu.href }" target="mainFrame" >
									<i class="iconfont icon-${not empty menu.icon?menu.icon:'circle-arrow-right'}"></i>
									&nbsp;${menu.name}
								</a>
							</c:when>
							<c:otherwise>
								<a class="accordion-toggle" data-toggle="collapse" data-parent="#menu" href="#collapse${menu.id}" title="${menu.remarks}">
									<i class="icon-chevron-${firstMenu?'down':'right'}"></i>
									&nbsp;${menu.name}
								</a>
							</c:otherwise>
						</c:choose>
					</div>
					<c:if test="${not empty menuChildList}">
						<div id="collapse${menu.id}" class="accordion-body collapse ${firstMenu?'in':''}">
							<div class="accordion-inner">
								<ul class="nav nav-list">
									<c:forEach items="${menuChildList}" var="menuChild">
											<li>
												<a href="${ctx}${menuChild.href }" class="third" target="mainFrame" >
													<i class="iconfont icon-${not empty menuChild.icon?menuChild.icon:'circle-arrow-right'}"></i>
													&nbsp;${menuChild.name}
												</a>
											</li>
											<c:set var="firstMenu" value="false"/>
									</c:forEach>
								</ul>
							</div>
						</div>
					</c:if>
				</div>
			</c:if>
		</c:forEach>
	</div>
</body>
</html>
