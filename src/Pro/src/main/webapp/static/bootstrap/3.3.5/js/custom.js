
$(document).ready(function(){
	$('.toolbar .back').click(function(){
		var backUrl = $(this).attr('data-back');
		back(backUrl);
	});
	
	//验证用户是否登录
	var href = location.href; 
	if(href.indexOf('/login')==-1 && href.indexOf('/index')==-1){
		var user = localStorage.getItem("user");
		if(!user){
			location.href='login';
		}
	}
	
});

function back(backUrl){
	alert(backUrl);
	location.href = backUrl;
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

function auth(code){
	if(code==301){
		location.href='login';
	}
}

function getSetting(){
	var setting = localStorage.getItem("setting");
	setting = JSON.parse(setting);
	return setting;
}
function setSetting(setting){
	setting = JSON.stringify(setting);
	localStorage.setItem("setting",setting);
}

function ajax(url,data,method,success,error){
	var setting = getSetting();
	var address = setting.address;
	url = address + url;
	$.ajax({
		url:url,
		type:method,
		dataType:"json",
		data:data,
		success: function(data){
			success(data);
		},
		error:function(){
			error();
		}
	});
}

function alarm(){
	var setting = getSetting();
	var address = setting.address;
	var src = address + "/static/sound/ALARM.WAV"
	var audio = new Audio();
	audio.src = src;
	//audio.loop = true;
	audio.id = 'alarm';
	audio.autoplay = true;
	audio.play();
}

