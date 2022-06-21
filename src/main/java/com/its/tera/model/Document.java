package com.its.tera.model;

import java.util.List;

public class Document extends Agreement{
	
	
	public static final Integer GEN_AGR = 1;
	public static final Integer AGR = 2;
	public static final Integer DOC = 3;
	
	private Integer agreementType;	
	private String regNumber;
	
	
	private String corrType;					
	private String description;
	private Integer status;
	private Boolean VIP;
	
	//String documentPath; 
	private String documentName;
	
	private String browseURL;
	String browsURL;
	//String downloadURL;
	private Long size;
	private String sizeString;
	//String iconPath;
	//String versionLabel;
	private String docContent;
	
	
	
	
	public Integer getAgreementType() {
		return agreementType;
	}
	public void setAgreementType(Integer agreementType) {
		this.agreementType = agreementType;
	}
	public String getRegNumber() {
		return regNumber;
	}
	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}
	public String getCorrType() {
		return corrType;
	}
	public void setCorrType(String corrType) {
		this.corrType = corrType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Boolean getVIP() {
		return VIP;
	}
	public void setVIP(Boolean vIP) {
		VIP = vIP;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getBrowseURL() {
		return browseURL;
	}
	public void setBrowseURL(String browseURL) {
		this.browseURL = browseURL;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getSizeString() {
		return sizeString;
	}
	public void setSizeString(String sizeString) {
		this.sizeString = sizeString;
	}
	public String getDocContent() {
		return docContent;
	}
	public void setDocContent(String docContent) {
		this.docContent = docContent;
	}
	public String getBrowsURL() {
		return browsURL;
	}
	public void setBrowsURL(String browsURL) {
		this.browsURL = browsURL;
	}
	
	
	
	

}
