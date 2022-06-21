package com.its.tera.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Client  {
	
	private String name;
	
	private  String nodeRef;
	private  String clientCode;
	private  String clientName;
	private  String clientId;
	private  Long created;
	private  String creator;
	private  Long regDate;
	private  String creatorUser;
	private  Long edited;
	private  String editor;
	private  Boolean isEmpty;
	private String reportId;
	
	private Map<String, Serializable> propertiesMap;
	private  Boolean clientIsVIP;	
	
	private Boolean nameIsDifferent;
	private  String branch;
	
	private  List<Agreement> children = new ArrayList<Agreement>();
	
	private List<String> allowed;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNodeRef() {
		return nodeRef;
	}
	public void setNodeRef(String nodeRef) {
		this.nodeRef = nodeRef;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Long getCreated() {
		return created;
	}
	public void setCreated(Long created) {
		this.created = created;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Long getRegDate() {
		return regDate;
	}
	public void setRegDate(Long regDate) {
		this.regDate = regDate;
	}
	public String getCreatorUser() {
		return creatorUser;
	}
	public void setCreatorUser(String creatorUser) {
		this.creatorUser = creatorUser;
	}
	public Long getEdited() {
		return edited;
	}
	public void setEdited(Long edited) {
		this.edited = edited;
	}
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	public Boolean getIsEmpty() {
		return isEmpty;
	}
	public void setIsEmpty(Boolean isEmpty) {
		this.isEmpty = isEmpty;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public Map<String, Serializable> getPropertiesMap() {
		return propertiesMap;
	}
	public void setPropertiesMap(Map<String, Serializable> propertiesMap) {
		this.propertiesMap = propertiesMap;
	}
	public Boolean getClientIsVIP() {
		return clientIsVIP;
	}
	public void setClientIsVIP(Boolean clientIsVIP) {
		this.clientIsVIP = clientIsVIP;
	}
	public Boolean getNameIsDifferent() {
		return nameIsDifferent;
	}
	public void setNameIsDifferent(Boolean nameIsDifferent) {
		this.nameIsDifferent = nameIsDifferent;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	
	

	public List<Agreement> getChildren() {
		return children;
	}
	public void setChildren(List<Agreement> children) {
		this.children = children;
	}
	public void addChild(Agreement child)
	{
		getChildren().add(child);
	}
	
	
	public List<String> getAllowed() {
		return allowed;
	}
	public void setAllowed(List<String> allowed) {
		this.allowed = allowed;
	}
	public void addAllowed(String key) {
		if(getAllowed() == null) {
			this.allowed = new ArrayList<String>();
		}
		getAllowed().add(key);
	}
	

}
