package com.its.tera.model;

import java.util.List;

public class Agreement extends Client{
	
	
	private String agrType;
	private String type;
	
	private String gagrId;
	private String gagrNumber;
	private Long gagrDate;
	private String gagrAmount;
	private String gagrCurrency;
	
	private String agrId;
	private String agrNumber;
	private Long date;
	private String amount;
	private String currency;
	private String productType;
	
	private String clientRef;
	private String parentRef;
	private String spaceName;
	//Agreement parentAgreement;
	private String folderDesc;
	
	private String archUser;
	private String liveUser;
	
	private List<ArchiveHistory> archHistory;
	private List<String> archHistoryString;
	private Long historyDate;
	
	
	public String getAgrType() {
		return agrType;
	}
	public void setAgrType(String agrType) {
		this.agrType = agrType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGagrId() {
		return gagrId;
	}
	public void setGagrId(String gagrId) {
		this.gagrId = gagrId;
	}
	public String getGagrNumber() {
		return gagrNumber;
	}
	public void setGagrNumber(String gagrNumber) {
		this.gagrNumber = gagrNumber;
	}
	public Long getGagrDate() {
		return gagrDate;
	}
	public void setGagrDate(Long gagrDate) {
		this.gagrDate = gagrDate;
	}
	public String getGagrAmount() {
		return gagrAmount;
	}
	public void setGagrAmount(String gagrAmount) {
		this.gagrAmount = gagrAmount;
	}
	public String getGagrCurrency() {
		return gagrCurrency;
	}
	public void setGagrCurrency(String gagrCurrency) {
		this.gagrCurrency = gagrCurrency;
	}
	public String getAgrId() {
		return agrId;
	}
	public void setAgrId(String agrId) {
		this.agrId = agrId;
	}
	public String getAgrNumber() {
		return agrNumber;
	}
	public void setAgrNumber(String agrNumber) {
		this.agrNumber = agrNumber;
	}
	public Long getDate() {
		return date;
	}
	public void setDate(Long date) {
		this.date = date;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getClientRef() {
		return clientRef;
	}
	public void setClientRef(String clientRef) {
		this.clientRef = clientRef;
	}
	public String getParentRef() {
		return parentRef;
	}
	public void setParentRef(String parentRef) {
		this.parentRef = parentRef;
	}
	public String getSpaceName() {
		return spaceName;
	}
	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}
	public String getFolderDesc() {
		return folderDesc;
	}
	public void setFolderDesc(String folderDesc) {
		this.folderDesc = folderDesc;
	}
	
	public String getArchUser() {
		return archUser;
	}
	public void setArchUser(String archUser) {
		this.archUser = archUser;
	}
	public String getLiveUser() {
		return liveUser;
	}
	public void setLiveUser(String liveUser) {
		this.liveUser = liveUser;
	}
	public List<ArchiveHistory> getArchHistory() {
		return archHistory;
	}
	public void setArchHistory(List<ArchiveHistory> archHistory) {
		this.archHistory = archHistory;
	}
	public List<String> getArchHistoryString() {
		return archHistoryString;
	}
	public void setArchHistoryString(List<String> archHistoryString) {
		this.archHistoryString = archHistoryString;
	}
	public Long getHistoryDate() {
		return historyDate;
	}
	public void setHistoryDate(Long historyDate) {
		this.historyDate = historyDate;
	}
	
	
	
	
	

}
