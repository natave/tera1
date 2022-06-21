package com.its.tera.model.um;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Group {
	
//	private String id;
//	private String name;
//	
//	private String branchId;
//	private String title;
//	
//	private List<User> users;
//	private List<Group> groups;
//	
	
	
	private String id;
	private String branchId;
	private String name;
	private String fullName;
	private String title;
	private String nodeRef;
	private Integer status;
	private String reportGroup;
	private Date created;
	private String reportId;
	private List<Group> children = new ArrayList<Group>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNodeRef() {
		return nodeRef;
	}
	public void setNodeRef(String nodeRef) {
		this.nodeRef = nodeRef;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getReportGroup() {
		return reportGroup;
	}
	public void setReportGroup(String reportGroup) {
		this.reportGroup = reportGroup;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public List<Group> getChildren() {
		return children;
	}
	public void setChildren(List<Group> children) {
		this.children = children;
	}
	
	
	
	
	

}
