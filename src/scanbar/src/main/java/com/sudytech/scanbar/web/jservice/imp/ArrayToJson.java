package com.sudytech.scanbar.web.jservice.imp;

import com.alibaba.fastjson.JSONObject;

public class ArrayToJson extends JSONObject{

	private static final long serialVersionUID = 7714069362821277382L;
	public ArrayToJson(String[] arrayKeys, Object[] arrayValues) {
		this(arrayKeys, arrayValues, 0);
	}
	public ArrayToJson(String[] arrayKeys, Object[] arrayValues, int start) {
		super();
		if(arrayKeys.length != arrayValues.length){
			
		}
		for (int i = 0, j = start; i < arrayKeys.length; i++, j++) {
			put(arrayKeys[i], arrayValues[j]);
		}
	}
}
