package com.thinkgem.jeesite.modules.pro.init;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

public class ProductExcel {
	
	private String machine;	//车种
	private String union_name; //产成品
	
	private String materialName;//原料名称
	private String materialSerialNum;//原料编号
	private String materialNum;//原料数量
	
	private String terminalName;//端末名称
	private String terminalSerialNum;//端末编号
	private String terminalNum;//端末数量
	
	private String nylonName;//尼龙管组装名称
	private String nylonSerialNum;//尼龙管组装编号
	private String nylonNum;//尼龙管组装数量	
	
	private String bendName;//弯曲名称
	private String bendSerialNum;//弯曲编号
	private String bendNum;//弯曲数量	
	
	private String assembleName;//组装名称
	private String assembleSerialNum;//组装编号
	private String assembleNum;//组装数量	
	
	private String productName;//产成品名称
	private String productSerialNum;//产成品编号
	private String productNum;//产成品数量
	
	
	@ExcelField(title="车种", sort=1)
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}
	
	@ExcelField(title="产成品", sort=2)
	public String getUnion_name() {
		return union_name;
	}
	public void setUnion_name(String union_name) {
		this.union_name = union_name;
	}
	
	@ExcelField(title="原料名称", sort=3)
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	
	@ExcelField(title="原料编号", sort=4)
	public String getMaterialSerialNum() {
		return materialSerialNum;
	}
	public void setMaterialSerialNum(String materialSerialNum) {
		this.materialSerialNum = materialSerialNum;
	}
	
	@ExcelField(title="原料数量", sort=5)
	public String getMaterialNum() {
		return materialNum;
	}
	public void setMaterialNum(String materialNum) {
		this.materialNum = materialNum;
	}
	
	@ExcelField(title="端末名称", sort=6)
	public String getTerminalName() {
		return terminalName;
	}
	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}
	
	@ExcelField(title="端末编号", sort=7)
	public String getTerminalSerialNum() {
		return terminalSerialNum;
	}
	public void setTerminalSerialNum(String terminalSerialNum) {
		this.terminalSerialNum = terminalSerialNum;
	}
	
	@ExcelField(title="端末数量", sort=8)
	public String getTerminalNum() {
		return terminalNum;
	}
	public void setTerminalNum(String terminalNum) {
		this.terminalNum = terminalNum;
	}
	
	@ExcelField(title="尼龙组装名称", sort=9)
	public String getNylonName() {
		return nylonName;
	}
	public void setNylonName(String nylonName) {
		this.nylonName = nylonName;
	}
	
	@ExcelField(title="尼龙组装编号", sort=10)
	public String getNylonSerialNum() {
		return nylonSerialNum;
	}
	public void setNylonSerialNum(String nylonSerialNum) {
		this.nylonSerialNum = nylonSerialNum;
	}
	
	@ExcelField(title="尼龙组装数量", sort=11)
	public String getNylonNum() {
		return nylonNum;
	}
	public void setNylonNum(String nylonNum) {
		this.nylonNum = nylonNum;
	}
	
	@ExcelField(title="弯曲名称", sort=12)
	public String getBendName() {
		return bendName;
	}
	public void setBendName(String bendName) {
		this.bendName = bendName;
	}
	
	@ExcelField(title="弯曲编号", sort=13)
	public String getBendSerialNum() {
		return bendSerialNum;
	}
	public void setBendSerialNum(String bendSerialNum) {
		this.bendSerialNum = bendSerialNum;
	}
	
	@ExcelField(title="弯曲数量", sort=14)
	public String getBendNum() {
		return bendNum;
	}
	public void setBendNum(String bendNum) {
		this.bendNum = bendNum;
	}
	
	@ExcelField(title="组装名称", sort=15)
	public String getAssembleName() {
		return assembleName;
	}
	public void setAssembleName(String assembleName) {
		this.assembleName = assembleName;
	}
	
	@ExcelField(title="组装编号", sort=16)
	public String getAssembleSerialNum() {
		return assembleSerialNum;
	}
	public void setAssembleSerialNum(String assembleSerialNum) {
		this.assembleSerialNum = assembleSerialNum;
	}
	
	@ExcelField(title="组装数量", sort=17)
	public String getAssembleNum() {
		return assembleNum;
	}
	public void setAssembleNum(String assembleNum) {
		this.assembleNum = assembleNum;
	}
	
	@ExcelField(title="产成品名称", sort=18)
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@ExcelField(title="产成品编号", sort=19)
	public String getProductSerialNum() {
		return productSerialNum;
	}
	public void setProductSerialNum(String productSerialNum) {
		this.productSerialNum = productSerialNum;
	}
	
	@ExcelField(title="产成品数量", sort=20)
	public String getProductNum() {
		return productNum;
	}
	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}
	
}
