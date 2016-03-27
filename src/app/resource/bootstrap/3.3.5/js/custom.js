



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


function getSetting(key){
	if(!_jscallapi){
		mAlert("未找到_jscallapi方法");
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
	if(!_jscallapi){
		mAlert("未找到_jscallapi方法");
		return;
	}
	var setting = {};
	setting.key = key;
	setting.value = value;
	setting = JSON.stringify(setting);
	 _jscallapi.call('savePrefer',setting);
}

