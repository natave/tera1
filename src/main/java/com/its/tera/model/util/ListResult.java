package com.its.tera.model.util;

import java.io.Serializable;

public class ListResult  implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private Integer page;
	private Integer size;
	private Long totalCount;
	private Object data;
	
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	

}
