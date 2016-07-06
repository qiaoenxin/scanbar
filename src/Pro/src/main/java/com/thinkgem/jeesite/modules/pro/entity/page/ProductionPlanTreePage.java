package com.thinkgem.jeesite.modules.pro.entity.page;

import java.util.Date;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.pro.entity.Product;

public class ProductionPlanTreePage {

	private String treeId;
	private String id;
	private String parent;
	private String name;
	private int number;
	private Product product;
	
	private Date date;
	private String dateForShow;
	private String printNum;
	private int completeNum;
	private int unqualifiedNum;
	
	

	public ProductionPlanTreePage() {
		this(null, null, null, null, 0, null, 0, 0);
	}
	

	public Product getProduct() {
		return product;
	}

	public int getSnpNum(){
		 return number / product.getSnpNum();
	}

	public int getModNum(){
		return number % product.getSnpNum();
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}


	public ProductionPlanTreePage(String treeId, String id, String parent,
			Product product, int number, Date date,int completeNum, int unqualifiedNum) {
		super();
		this.treeId = treeId;
		this.id = id;
		this.parent = parent;
		this.name = product.getName();
		this.number = number;
		
		this.product = product;
		this.date = date;
		this.dateForShow = toCompleteDate(date);
		this.printNum = toPrintNum(number, product.getRealSnpNum());
		this.completeNum = completeNum;
		this.unqualifiedNum = unqualifiedNum;
	}
	
	/**
	 * 转换成完成时间
	 * @param date
	 * @return
	 */
	private String toCompleteDate(Date date){
		return DateUtils.formatDate(date, "MM") + "-" + DateUtils.formatDate(date, "dd");
	}
	
	/**
	 * 转换成打印数
	 * @param number
	 * @param snpNum
	 * @return
	 */
	private String toPrintNum(int number, int snpNum){

		if (snpNum == 0) {
			return "SNP不能为0";
		}
		
		int surplus = number%snpNum;
		int time = number/snpNum;
		
		StringBuilder result = new StringBuilder();
		result.append(snpNum).append("*").append(time);
		if(surplus > 0){
			result.append("+").append(surplus);
		}
		
		return result.toString();
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public String getDateForShow() {
		return dateForShow;
	}


	public void setDateForShow(String dateForShow) {
		this.dateForShow = dateForShow;
	}


	public String getPrintNum() {
		return printNum;
	}


	public void setPrintNum(String printNum) {
		this.printNum = printNum;
	}


	public int getCompleteNum() {
		return completeNum;
	}


	public void setCompleteNum(int completeNum) {
		this.completeNum = completeNum;
	}


	public int getUnqualifiedNum() {
		return unqualifiedNum;
	}


	public void setUnqualifiedNum(int unqualifiedNum) {
		this.unqualifiedNum = unqualifiedNum;
	}

	

}
