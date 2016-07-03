package com.thinkgem.jeesite.modules.pro.entity;


import java.util.Date;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.persistence.IdEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;


/**
 * 生产指令Excel实体
 * 
 */
public class ProductionPlanExcel
{
	@ExcelField(title = "批次", align = 2, sort = 10)
    private String serialNum;	//批次
	
    @ExcelField(title = "日期（格式：20160705）", align = 2, sort = 20)
    private String endDate;		//完成日期,格式:20160705
    
    @ExcelField(title = "产品名称", align = 2, sort = 30)
    private String productName;	//产品名称
    
    @ExcelField(title = "生产目标数量", align = 2, sort = 40)
    private Integer number;			//生产目标数量
    
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
    
}
