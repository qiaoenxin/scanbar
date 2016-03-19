<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>${fns:getConfig('productName')}</title>
<%@include file="/WEB-INF/views/include/dialog.jsp"%>
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<meta name="decorator" content="default" />
<script src="${ctxStatic}/echarts/js/echarts.min.js"></script>
<script>
	var ctxStatic = '${ctxStatic}';
</script>
<link href="${ctxStatic}/calendar/css/wnl.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/calendar/js/calendar.js"></script>
<style>
.widget {
	
}

.widget .body {
	margin: 10px;
}

.widget .body .header {
	background: #ddd;
	padding: 10px;
}

.widget .body .header .action {
	float: right;
	margin-top: -20px;
}

.widget .body .content {
	padding: 10px;
	height: 260px;
	border: 1px solid #ddd;
	font-family: 微软雅黑;
}
.row2 .body .content{
	height:420px;
}


.user-info div {
	padding: 6px 0px;
}

.user-info label{
	width:90px;
}
.user-info-tip{
	font-size:13px;
	background: #F9F9F9;
    line-height: 30px;
}
.jvm div {
	padding: 6px 0px;
}

.content-total{
	font-size: 14px;
	font-weight: 400;
    font-style: normal;
	text-align:center;
	font-family: 微软雅黑;
}
.content-total .item{
	margin:30px 0px;
}
.content-total .item div{
	margin:10px 0px;
}
.content-total strong{
	color: #676767;
    font-size: 30px;
}
.icon-tip{
	color: #FF2222;
    font-size: 20px;
	margin-right: 10px;
}
</style>

<script>
	$(document).ready(function(){
		$('.iconfont').click(function(){
			var isExpand = $(this).hasClass('icon-shrink');
			if(isExpand){
				$(this).parents('.body').find('.content').hide();
				$(this).removeClass('icon-shrink');
				$(this).addClass('icon-expand');
			}else{
				$(this).parents('.body').find('.content').show();
				$(this).removeClass('icon-expand');
				$(this).addClass('icon-shrink');
			}
		});
		
		initMemery();
		initCPU();
	});	
	
	function initMemery(){
		var myChart = echarts.init(document.getElementById('memery'));
		option = {
		    tooltip : {
		        formatter: "{a} <br/>{c}MB/${memery.total}MB"
		    },
		    title : {
		        text: '系统内存使用情况'
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    series : [
		        {
		            name:'内存使用情况',
		            type:'gauge',
		            axisTick: {            // 坐标轴小标记
		                show: false
		            },
		            axisLabel: {
		                formatter:function(v){
		                    return '';
		                }
		            },
		            max:${memery.total},
		            detail : {formatter:'{value}'},
		            data:[{value: ${memery.used}, name: '总内存:${memery.total}MB'}]
		        }
		    ]
		};
 		myChart.setOption(option, true);
	}
	
	
	function initCPU(){
		var x = [
        	 <c:forEach items="${cpusX}" var="item">
        	 	'${item}',
		     </c:forEach>
		];
        	
		var y = [
        	 <c:forEach items="${cpusY}" var="item">
        	 	'${item}',
		     </c:forEach>
		];
		var myChart = echarts.init(document.getElementById('cpu'));
		option = {
		    title : {
		        text: '系统CPU使用情况'
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:[]
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            data : x
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'使用率',
		            type:'bar',
		            max:100,
		            data:y
		           
		        }
		    ]
		};
		
		
		
		myChart.setOption(option, true);
	}
	
	
</script>
<html>
<body>

	<div class="sirius-row">
	
		<div class="sirius-column sirius-column-4 widget">
			<div class="body">
				<div class="header">
					<h5>用户信息</h5>
					<div class="action">
						<a href="javascript:void(0);" class="iconfont icon-shrink"></a>
					</div>
				</div>
				<div class="content user-info">
					<c:set value="${fns:getUser()}" var="user" />
					<div><label>姓名:</label>${user.name }</div>
					<div><label>电话:</label>${user.phone }</div>
					<div><label>手机:</label>${user.mobile }</div>
					<div><label>邮箱:</label>${user.email }</div>
					<div class="user-info-tip"><span><i class="iconfont icon-tip"></i>您上次登录该系统时间是${user.loginDate }，登录IP:${user.loginIp }</span></div>
				</div>
			</div>
		</div>
		
		<div class="sirius-column sirius-column-4 widget">
			<div class="body">
				<div class="header">
					<h5>内存使用情况</h5>
					<div class="action">
						<a href="javascript:void(0);" class="iconfont icon-shrink"></a>
					</div>
				</div>
				<div class="content" id="memery"></div>
			</div>
		</div>
		
		<div class="sirius-column sirius-column-4 widget">
			<div class="body">
				<div class="header">
					<h5>CPU使用率</h5>
					<div class="action">
						<a href="javascript:void(0);" class="iconfont icon-shrink"></a>
					</div>
				</div>
				<div class="content" id="cpu"></div>
			</div>
		</div>
	
	</div>

	<div class="sirius-row row2">
		
		
		<div class="sirius-column sirius-column-2 widget">
			<div class="body">
				<div class="header">
					<h5>资源信息</h5>
					<div class="action">
						<a href="javascript:void(0);" class="iconfont icon-shrink"></a>
					</div>
				</div>
				<div class="content content-total">
					<div class="item">
						<div>附件总数</div>
						<div><strong>${contentMap.totalFileCount }</strong></div>
					</div>
					<div class="item">
						<div>广告总数</div>
						<div><strong>${contentMap.totalAdvert }</strong></div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="sirius-column sirius-column-6 widget">
			<div class="body">
				<div class="header">
					<h5>日历</h5>
					<div class="action">
						<a href="javascript:void(0);" class="iconfont icon-shrink"></a>
					</div>
				</div>
				<div class="content">
					<div id="so_top"></div>
					<ul id="m-result" class="result">
						<li id="first" class="res-list">
							<div id="mohe-rili" class="g-mohe" data-mohe-type="rili">
								<div class="mh-rili-wap mh-rili-only "
									data-mgd='{"b":"rili-body"}'>
									<div class="mh-tips">
										<div class="mh-loading">
											<i class="mh-ico-loading"></i>正在为您努力加载中...
										</div>
										<div class="mh-err-tips">
											亲，出了点问题~ 您可<a href="#reload" class="mh-js-reload">重试</a>
										</div>
									</div>
									<div class="mh-rili-widget">

										<div class="mh-doc-bd mh-calendar">
											<div class="mh-hint-bar gclearfix">
												<div class="mh-control-bar">
													<div class="mh-control-module mh-year-control mh-year-bar">
														<a href="#prev-year" action="prev" class="mh-prev"
															data-md='{"p":"prev-year"}'></a>
														<div class="mh-control">
															<i class="mh-trigger"></i>
															<div class="mh-field mh-year" val=""></div>
														</div>
														<a href="#next-year" action="next" class="mh-next"
															data-md='{"p":"next-year"}'></a>
														<ul class="mh-list year-list" style="display:none;"
															data-md='{"p":"select-year"}'></ul>
													</div>
													<div
														class="mh-control-module mh-month-control mh-mouth-bar">
														<a href="#prev-month" action="prev" class="mh-prev"
															data-md='{"p":"prev-month"}'></a>
														<div class="mh-control">
															<i class="mh-trigger"></i>
															<div class="mh-field mh-month" val=""></div>
														</div>
														<a href="#next-month" action="next" class="mh-next"
															data-md='{"p":"next-month"}'></a>
														<ul class="mh-list month-list" style="display:none;"
															data-md='{"p":"select-month"}'></ul>
													</div>
													<div
														class="mh-control-module mh-holiday-control mh-holiday-bar">
														<div class="mh-control">
															<i class="mh-trigger"></i>
															<div class="mh-field mh-holiday"></div>
														</div>
														<ul class="mh-list" style="display:none;"
															data-md='{"p":"select-holiday"}'></ul>
													</div>
													<div class="mh-btn-today" data-md='{"p":"btn-today"}'>返回今天</div>
												</div>
												<div class="mh-time-panel">
													<dl class="gclearfix">
														<dt class="mh-time-monitor-title">北京时间:</dt>
														<dd class="mh-time-monitor"></dd>
													</dl>
												</div>
											</div>
											<div class="mh-cal-main">
												<div class="mh-col-1 mh-dates">
													<ul class="mh-dates-hd gclearfix">
														<li class="mh-days-title">一</li>
														<li class="mh-days-title">二</li>
														<li class="mh-days-title">三</li>
														<li class="mh-days-title">四</li>
														<li class="mh-days-title">五</li>
														<li class="mh-days-title mh-weekend">六</li>
														<li class="mh-days-title mh-last mh-weekend">日</li>
													</ul>
													<ol class="mh-dates-bd"></ol>
												</div>
												<div class="mh-col-2 mh-almanac">
													<div class="mh-almanac-base mh-almanac-main"></div>
													<div class="mh-almanac-extra gclearfix"
														style="display:none;">
														<div class="mh-suited">
															<h3 class="mh-st-label">宜</h3>
															<ul class="mh-st-items gclearfix"></ul>
														</div>
														<div class="mh-tapu">
															<h3 class="mh-st-label">忌</h3>
															<ul class="mh-st-items gclearfix"></ul>
														</div>
													</div>


												</div>
											</div>
										</div>

										<span id="mh-date-y" style="display:none;">2015</span>

									</div>
								</div>

								<div class="mh-rili-foot"></div>
								<select class="mh-holiday-data" style="display:none;">
									<option value="0" data-desc="" data-gl="">放假安排</option>
									<option value="抗战胜利纪念日"
										data-desc="9月3日至5日放假调休，共3天。9月6日（星期日）上班。" data-gl="">抗战胜利纪念日</option>
									<option value="国庆节" data-desc="10月1日至7日放假调休，共7天。10月10日（星期六）上班。"
										data-gl="">国庆节</option>
									<option value="中秋节" data-desc="9月27日放假。" data-gl="">中秋节</option>
									<option value="端午节" data-desc="6月20日放假，6月22日（星期一）补休。"
										data-gl="">端午节</option>
									<option value="劳动节" data-desc="5月1日放假，与周末连休。" data-gl="">劳动节</option>
									<option value="清明节" data-desc="4月5日放假，4月6日（星期一）补休。" data-gl="">清明节</option>
									<option value="春节"
										data-desc="2月18日至24日放假调休，共7天。2月15日（星期日）、2月28日（星期六）上班。"
										data-gl="">春节</option>
									<option value="元旦" data-desc="1月1日至3日放假调休，共3天。1月4日（星期日）上班。"
										data-gl="">元旦</option>
								</select>
								<!--value获取当前PHP服务器时间-->
								<input type="hidden" id="mh-rili-params"
									value="action=query&year=2015&month=09&day=04" />

							</div></li>
					</ul>
				</div>
			</div>
		</div>
		
		
		<div class="sirius-column sirius-column-4 widget">
			<div class="body">
				<div class="header">
					<h5>JVM</h5>
					<div class="action">
						<a href="javascript:void(0);" class="iconfont icon-shrink"></a>
					</div>
				</div>
				<div class="content jvm">
					<div>
						<label>操作系统的名称：</label>${jvm.osName }
					</div>
					<div>
						<label>操作系统的版本：</label>${jvm.osVersion }
					</div>
					<div>
						<label>操作系统的描述：</label>${jvm.osDesc }
					</div>
					<div>
						<label>JVM可以使用的处理器个数：</label>${jvm.availableProcessors }
					</div>
					<div>
						<label>主机名：</label>${jvm.hostName }
					</div>
					<div>
						<label>计算机名：</label>${jvm.computerName }
					</div>
					<div>
						<label>ip地址：</label>${jvm.ip }
					</div>
					<div>
						<label>Java的运行环境版本：</label>${jvm.javaVersion }
					</div>
					<div>
						<label>JVM可以使用的总内存：</label>${jvm.totalMemory }
					</div>
					<div>
						<label>JVM可以使用的剩余内存：</label>${jvm.freeMemory }
					</div>
					<div>
						<label>Java的安装路径：</label>${jvm.javaHome }
					</div>
				</div>
			</div>
		</div>
		
				
	</div>
	
	
	<script>
		calendarInit();
	</script>
</body>
</html>

