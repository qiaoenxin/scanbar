/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.persistence.IdEntity;

/**
 * 产品管理Entity
 * @author Generate Tools
 * @version 2016-03-12
 */
@Entity
@Table(name = "pro_product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product extends IdEntity<Product> {
	
	private static final long serialVersionUID = 1L;

	private String serialNum;//编号
	private int snpNum;		 //snp数量
	private String flow;	//功序流
	private String field1;
	private String field2;
	private String field3;
	private String field4;
	private String field5;
	private String field6;
	
	
	private transient List<Flow> flows;
	
	public Product() {
		super();
	}

	public Product(String id){
		this();
		this.id = id;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public int getSnpNum() {
		return snpNum;
	}

	public void setSnpNum(int snpNum) {
		this.snpNum = snpNum;
	}

	public String getFlow() {
		return StringEscapeUtils.unescapeHtml4(flow);
	}

	public void setFlow(String flow) {
		this.flow = flow;
		flows = null;
	}
	
	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public String getField3() {
		return field3;
	}

	public void setField3(String field3) {
		this.field3 = field3;
	}

	public String getField4() {
		return field4;
	}

	public void setField4(String field4) {
		this.field4 = field4;
	}

	public String getField5() {
		return field5;
	}

	public void setField5(String field5) {
		this.field5 = field5;
	}

	public String getField6() {
		return field6;
	}

	public void setField6(String field6) {
		this.field6 = field6;
	}

	@Transient
	public List<Flow> getFlows() {
		if(StringUtils.isBlank(this.getFlow())){
			return Collections.emptyList();
		}
		
		if(flows != null){
			return flows;
		}
		//StringEscapeUtils.unescapeHtml4
		JSONArray arrays = JSONArray.parseArray(this.getFlow());
		List<Flow> list = new ArrayList<Product.Flow>();
		Flow prev = null;
		for(int i =0, len = arrays.size(); i < len;i++){
			JSONObject json = arrays.getJSONObject(i);
			String id = json.getString("id");
			String value = json.getString("value");
			String number = json.getString("number");
			Flow flow = new Flow();
			flow.setId(id);
			flow.setValue(value);
			flow.setNumber(number);
			
			flow.prev = prev;
			if(prev != null){
				prev.next = flow;
			}
			list.add(flow);
			prev = flow;
		}
		flows = Collections.unmodifiableList(list);
		return flows;
	}

	public class Flow{
		
		private String id;
		
		private String value;
		
		private String number;
		
		private Flow prev;
		
		private Flow next;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
		public String getNumber() {
			return number;
		}
		public void setNumber(String number) {
			this.number = number;
		}
		public Flow getNext(){
			return next;
		}
		
		public boolean isFirst(){
			return prev == null;
		}
		
		public boolean isLast(){
			return next == null;
		}
	}
}


