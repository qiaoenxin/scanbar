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
				{id:"${flow.id}",label:"${flow.label}",value:"${flow.value}",number:"${flow.number}"},
				</c:forEach>
			];
			
			$.each(flows,function(i,flow){
				add(flow);
			});
		}
		
		var flows = [
			<c:forEach items="${fns:getDictList('flow_type')}" var="flow">
			{id:"${flow.value}",label:"${flow.label}"},
			</c:forEach>
		];
		
		
		function add(flow){
			var id = '',value='',number='';
			
			if(flow){
				id = flow.id;
				value = flow.value;
				number = flow.number;
			}
			
			var len = $('.flows .flow').length;
			
			var html = "<div class='flow'>";
			html += "步骤<span>"+(len+1)+"</span>:";
			html += "<select>";
			$.each(flows,function(i,flow){
				var selected = "";
				if(id == flow.id){
					selected = " selected='true' ";
				}
				html += "<option value='"+flow.id+"' "+selected+">"+flow.label+"</option>";
			})
			html += "</select>";
			html += "描述：<input type='text' class='value' value='"+value+"' style='width:100px'/>";
			html += "编号：<input type='text' class='number' value='"+number+"' style='width:100px'/>";
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
			var flows = new Array();
			var _temp = {};
			$('.flows .flow').each(function(){
				var id = $(this).find("select").val();
				var value = $(this).find("input[class='value']").val();
				var number = $(this).find("input[class='number']").val();
				
				if(_temp[id]){
					result.error = "不能选择相同的工序！";
					return;
				}
				_temp[id] = id;
				flows.push({id:id,value:value,number:number});
			});
			
			if(result.error){
				return result;
			}
			
			var url = "${ctx}/pro/product/saveFlow";
			var params = {};
			params.id = "${product.id}";
			params.flows = JSON.stringify(flows);
			
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
