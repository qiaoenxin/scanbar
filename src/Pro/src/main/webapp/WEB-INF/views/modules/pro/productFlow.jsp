<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理管理</title>
	<meta name="decorator" content="default"/>
	<style>
		.flows{
			padding:20px;
		}
		
		.flows .btn{
		    margin-top: -10px;
    		margin-left: 10px;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			init();
		});
		
		
		function init(){
			var flows = [
				<c:forEach items="${flowList}" var="flow">
				{label:"${flow.label}",value:"${flow.value}"},
				</c:forEach>
			];
			
			$.each(flows,function(i,flow){
				var val = flow.value;
				add(val);
			});
		}
		
		var flows = [
			<c:forEach items="${fns:getDictList('flow_type')}" var="flow">
			{label:"${flow.label}",value:"${flow.value}"},
			</c:forEach>
		];
		
		
		function add(id){
			var len = $('.flows .flow').length;
			
			var html = "<div class='flow'>";
			html += "步骤<span>"+(len+1)+"</span>:";
			html += "<select>";
			$.each(flows,function(i,flow){
				var selected = "";
				if(id == flow.value){
					selected = " selected='true' ";
				}
				html += "<option value='"+flow.value+"' "+selected+">"+flow.label+"</option>";
			})
			html += "</select>";
			html += "<input type='button' class='btn' value='删除' onclick='_delete(this);'/>";
			html += "</div>";
			
			if(len==0){
				$('.flows').html(html);
			}else{
				$('.flows').append(html);
			}
		}
		
		function _delete(dom){
			$(dom).parent(".flow").remove();
			 $('.flows .flow').each(function(i,v){
				$(this).find("span").html(i+1);			 	
			 });
		}
		
		function submit(){
			
			var result = {};
			result.ok = false;
			var ids = new Array();
			var _temp = {};
			$('.flows .flow').each(function(){
				var val = $(this).find("select").val();
				if(_temp[val]){
					result.error = "不能选择相同的工序！";
					return;
				}
				_temp[val] = val;
				ids.push(val);
			});
			
			if(result.error){
				return result;
			}
			
			var url = "${ctx}/pro/product/saveFlow";
			var params = {};
			params.id = "${product.id}";
			params.flows = ids.join(",");
			
        	$.ajax({
				url:   url,
			 	data: params,
			 	async :false,
			 	success: function(data){
			 		result=data;
			 	}
			}); 
			return result;
		}
	</script>
</head>
<body>
	
	<div class="flows">
		<div class="empty-message">请点击创库下方的添加按钮进行添加工序流</div>
	</div>
	
</body>
</html>
