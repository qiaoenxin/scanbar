
$(document).ready(function(){
	$('.toolbar .back').click(function(){
		var backUrl = $(this).attr('data-back');
		back(backUrl);
	});
});

function back(backUrl){
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

function getSetting(key){
	if(!_jscallapi.call){
		mAlert("未找到call方法");
		return;
	}
	var setting = {};
	setting.key = key;
	setting = JSON.stringify(setting);
	var setting = _jscallapi.call('getPrefer',setting);
	setting = JSON.parse(setting);
	return setting;
}
function setSetting(key,value){
	if(!_jscallapi.call){
		mAlert("未找到call方法");
		return;
	}
	var setting = {};
	setting.key = key;
	setting.value = value;
	setting = JSON.stringify(setting);
	 _jscallapi.call('savePrefer',setting);
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

