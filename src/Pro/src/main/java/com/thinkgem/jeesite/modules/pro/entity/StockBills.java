/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.persistence.IdEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.common.utils.excel.fieldtype.ProductType;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 扎帐Entity
 * @author Generate Tools
 * @version 2016-04-03
 */
@Entity
@Table(name = "pro_stock_bills")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StockBills extends IdEntity<StockBills>{
	
	private static final long serialVersionUID = 1L;

	private Product product;//产品
	private int number;	//库存数量
	private String subName;
	private String subCode;
	private int useNumber;//占用
	private Date fromDate;//上一次轧账日期
	private int prevNumber;//上一次轧账数量
	
	public StockBills() {
		super();
	}

	public StockBills(String id){
		this();
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="product_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@NotNull
	@ExcelField(title="产品", align=2, sort=20,fieldType=ProductType.class)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@ExcelField(title="数量", align=2, sort=25)
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@ExcelField(title="占用数量", align=2, sort=30)
	public int getUseNumber() {
		return useNumber;
	}

	public void setUseNumber(int useNumber) {
		this.useNumber = useNumber;
	}


	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public int getPrevNumber() {
		return prevNumber;
	}

	public void setPrevNumber(int prevNumber) {
		this.prevNumber = prevNumber;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
}


