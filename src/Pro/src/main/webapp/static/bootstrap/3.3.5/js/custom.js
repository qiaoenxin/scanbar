
$(document).ready(function(){
	$('.toolbar .back').click(function(){
		back();
	});
	
	//验证用户是否登录
	if(location.href.indexOf('/login')==-1){
		var user = localStorage.getItem("user");
		if(!user){
			location.href='login';
		}
	}
	
});

function back(){
	history.go(-1);
}

function mAlert(msg){
	var jDom = $('body').find('.m-alert');
	if(jDom.length>0){
		jDom.remove();
	}
	var html = '<div class="m-alert"><span class="m-alert-content">'+msg+'</span></div>';
	$('body').append(html);
	setTimeout(function(){
		$('body').find('.m-alert').remove();
	},2000);
}