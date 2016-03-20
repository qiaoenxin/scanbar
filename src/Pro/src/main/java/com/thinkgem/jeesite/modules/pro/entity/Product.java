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
		return flow;
	}

	public void setFlow(String flow) {
		this.flow = flow;
		flows = null;
	}
	
	@Transient
	public List<Flow> getFlows() {
		if(flow == null){
			return Collections.emptyList();
		}
		
		if(flows != null){
			return flows;
		}
		
		JSONArray arrays = JSONArray.parseArray(flow);
		List<Flow> list = new ArrayList<Product.Flow>();
		Flow prev = null;
		for(int i =0, len = arrays.size(); i < len;i++){
			JSONObject json = arrays.getJSONObject(i);
			String id = json.getString("id");
			String value = json.getString("value");
			Flow flow = new Flow();
			flow.setId(id);
			flow.setValue(value);
			
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


