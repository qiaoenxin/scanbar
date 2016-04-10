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
		.flows .label{
    		margin-left: 30px;
		}
		.flow{
			border: 1px solid #dddddd;
    		padding: 10px;
		}
		.flow .content{
			text-align: center;
			background: #dddddd;
		}
		.row{
		}
		.row .col-2{
			width:20%;
			float:left;
		}
		
		
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			init();
		});
		
		
		var flows = '${flowList}';
		function init(){
			var flows = '${flowList}';
			flows = JSON.parse(flows);
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
			var id = '';
			
			if(flow){
				id = flow.id;
			}
			
			var len = $('.flows .flow').length;
			
			var sId = (Math.random()+"").substring(2);
			var html = "<div class='flow'>";
			html += "步骤<span>"+(len+1)+"</span>:";
			html += "<select id='"+sId+"' onchange='flowChange(this.value,\""+sId+"\")'>";
			var selectedFlowId = '';
			$.each(flows,function(i,flow){
				var selected = "";
				if(id == flow.id){
					selected = " selected='true' ";
					selectedFlowId = id;
				}
				html += "<option value='"+flow.id+"' "+selected+">"+flow.label+"</option>";
			})
			html += "</select>";
			html += "<input type='button' class='btn' value='删除' onclick='_delete(this);'/>";
			html += "<div class='content'></div>";
			html += "</div>";
			
			if(len==0){
				$('.flows').html(html);
			}else{
				$('.flows').append(html);
			}
			if(selectedFlowId){
				flowChange(selectedFlowId,sId);
			}else{
				flowChange('1',sId);
			}
			
			
			if(flow){
				var fields = flow.fields;
				var fieldContentJDom = $('#'+sId).parents('.flow').find('.content');
				$.each(fields,function(i,field){
					var fieldName = field.field;
					var fieldValue = field.value;
					fieldContentJDom.find('input[name="'+fieldName+'"]').val(fieldValue);
				});
			}
		}
		
		function _delete(dom){
			$(dom).parent(".flow").remove();
			 $('.flows .flow').each(function(i,v){
				$(this).find("span").html(i+1);			 	
			 });
		}
		
		function flowChange(val,sId){
			
			var html = '';
			//端末
			if(val == 1){
				html += '<div class="row">'
				html += '<div class="col-2">编号：<input type="text" style="width:90px" name="field1"/></div>';
				html += '<div class="col-2">HPC：<input type="text" style="width:90px" name="field2"/></div>';
				html += '<div class="col-2">端末：<input type="text" style="width:90px" name="field3"/></div>';
				html += '<div class="col-2">ISO/ISO：<input type="text" style="width:90px" name="field4"/></div>';
				html += '<div class="col-2">烘护套：<input type="text" style="width:90px" name="field5"/></div>';
				html += '</div>'
				html += '<div class="row">'
				html += '<div class="col-2">印字：<input type="text" style="width:90px" name="field7"/></div>';
				html += '<div class="col-2">标识：<input type="text" style="width:90px" name="field8"/></div>';
				html += '<div class="col-2">PCO：<input type="text" style="width:90px" name="field6"/></div>';
				html += '</div>'
			}
			//弯曲
			else if(val == 2){
				html += '<div class="row">'
				html += '<div class="col-2">编号：<input type="text" style="width:90px" name="field1"/></div>';
				html += '<div class="col-2">规格：<input type="text" style="width:90px" name="field2"/></div>';
				html += '</div>'
			}
			
			$('#'+sId).parents('.flow').find('.content').html(html);
		}
		
		function submit(){
			
			var result = {};
			result.ok = false;
			var flows = new Array();
			var _temp = {};
			$('.flows .flow').each(function(){
				var id = $(this).find("select").val();
				
				var fields = new Array();
				$.each($(this).find("input[type=\"text\"]"),function(i,dom){
					var field = 'field'+(i+1);
					var value = $(this).val();
					fields.push({field:field,value:value});
				});
				
				if(_temp[id]){
					result.error = "不能选择相同的工序！";
					return;
				}
				_temp[id] = id;
				flows.push({id:id,fields:fields});
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
	<form action="">
	<div class="flows">
	<!-- 
		<div class="flow">
		
			端末工程<br/>
			<a class="label">编号：</a><input type='text' class='number' value='20031211' style='width:100px'/><br/>
			<a class="label">HPC：</a><input type='text' class='number' value='12' style='width:100px'/><br/>
			<a class="label">端末：</a><input type='text' class='number' value='L:723' style='width:100px'/><br/>
			<a class="label">ISO/ISO：</a><input type='text' class='number' value='' style='width:100px'/><br/>
			<a class="label">烘护套：</a><input type='text' class='number' value='' style='width:100px'/><br/>
			<a class="label">PCO：</a><input type='text' class='number' value='' style='width:100px'/><br/>
			<a class="label">印字：</a><input type='text' class='number' value='' style='width:100px'/><br/>
			<a class="label">标识：</a><input type='text' class='number' value='' style='width:100px'/><br/>
		</div>
		<div class="flow">
			弯曲工程<br/>
			<input type='hidden' class='number' value='' value/>
			<a class="label">编号：</a><input type='text' class='number' value='20031211' style='width:100px'/><br/>
			<a class="label">规格：</a><input type='text' class='number' value='TK#&LR#&FD#' style='width:100px'/><br/>
		</div>
		 -->
	</div>
	
	</form>
	
	
</body>
</html>
