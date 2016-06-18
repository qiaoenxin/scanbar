package com.thinkgem.jeesite.modules.pro.init;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

public class ProductExcel {
	
	private String field1;	//车种
	private String dmName; //端末名称
	private String dmNum;//端末编号
	private String wqName;//弯曲名称
	private String wqNum;//弯曲编号
	private String proName;//产品名称
	private String proNum;//产品编号
	
	
	@ExcelField(title="车种", sort=1)
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	
	@ExcelField(title="端末加工", sort=2)
	public String getDmName() {
		return dmName;
	}
	public void setDmName(String dmName) {
		this.dmName = dmName;
	}
	
	@ExcelField(title="端末加工编号", sort=3)
	public String getDmNum() {
		return dmNum;
	}
	public void setDmNum(String dmNum) {
		this.dmNum = dmNum;
	}
	
	
	@ExcelField(title="弯管", sort=4)
	public String getWqName() {
		return wqName;
	}
	public void setWqName(String wqName) {
		this.wqName = wqName;
	}
	
	@ExcelField(title="弯管编号", sort=5)
	public String getWqNum() {
		return wqNum;
	}
	public void setWqNum(String wqNum) {
		this.wqNum = wqNum;
	}
	
	@ExcelField(title="产成品", sort=6)
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	
	@ExcelField(title="产成品编号", sort=7)
	public String getProNum() {
		return proNum;
	}
	public void setProNum(String proNum) {
		this.proNum = proNum;
	}
	
	
	
	
}
