package com.thinkgem.jeesite.modules.pro.entity.page;

public class ProductTreePage {

	private String treeId;
	private String id;
	private String parent;
	private String name;
	private int number;

	public ProductTreePage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductTreePage(String treeId, String id, String parent,
			String name, int number) {
		super();
		this.treeId = treeId;
		this.id = id;
		this.parent = parent;
		this.name = name;
		this.number = number;
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

}
