package com.thinkgem.jeesite.common.jservice.check;

import com.thinkgem.jeesite.common.jservice.api.ParameterDef;

public class Comparator {
	
	 public boolean matchRule(ParameterDef rule, String value){
		 
		 //必选参数
		 if(rule.required()){
			 if(value.trim().length() == 0){
				 return false;
			 }
		 }
		 
		 //参数长度
		 if(rule.maxLength() > 0 ){
			 if(value.length() > rule.maxLength())
			 {
				 return false;
			 }
		 }
		 
		 //正则表达式
		 if(rule.regex().length() > 0){
			  if(!value.matches(rule.regex())){
				  return false;
			  }
		 }
		 
		 return true;
	 }
}
