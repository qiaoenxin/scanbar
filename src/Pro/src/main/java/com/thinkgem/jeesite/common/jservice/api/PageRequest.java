package com.thinkgem.jeesite.common.jservice.api;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.jservice.constant.RegexConstants;


public class PageRequest extends Request{
	
	/**
	 * 页数
	 */
	@ParameterDef(required=true, regex=RegexConstants.NUMBER, maxLength=20)
	private String page;
	
	/**
	 * 分页大小，1-99的数字
	 */
	@ParameterDef(defaultValue="10", regex="[1-9]\\d{0,1}")
	private String pageSize;

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getPageInteger(){
		return Integer.parseInt(page);
	}
	public int getPageSizeInteger(){
		return Integer.parseInt(pageSize);
	}
}
