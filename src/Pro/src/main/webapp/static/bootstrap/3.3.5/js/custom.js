
$(document).ready(function(){
	$('.toolbar .back').click(function(){
		var backUrl = $(this).attr('data-back');
		back(backUrl);
	});
});

function back(backUrl){
	//alert('');
	history.go(-1);
	//location.href = backUrl;
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

function getSetting(key){
	try{
		var setting = {};
		setting.key = key;
		setting = JSON.stringify(setting);
		return  _jscallapi.call('getPrefer',setting);
	}catch(e){
		mAlert("未找到_jscallapi方法");
		return localStorage.getItem(key);
	}
	
}
function setSetting(key,value){
	try{
		var setting = {};
		setting.key = key;
		setting.value = value;
		setting = JSON.stringify(setting);
		 _jscallapi.call('savePrefer',setting);
	}catch(e){
		mAlert("未找到_jscallapi方法");
		return localStorage.setItem(key,value);
	}
}

function ajax(url,data,method,success,error){
	var addressUrl = getSetting("addressUrl");
	url = addressUrl + url;
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

var audio;
function alarm(){
	if(!audio){
		var addressUrl = getSetting("addressUrl");
		var src = addressUrl + "/static/sound/ALARM.WAV";
		audio = new Audio();
		audio.src = src;
		audio.id = 'alarm';
		audio.autoplay = true;
	}
	audio.currentTime=0;
	//audio.loop = true;
	audio.play();
}

