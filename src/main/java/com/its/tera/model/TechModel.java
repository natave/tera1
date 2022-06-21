package com.its.tera.model;

import java.util.Date;

public class TechModel extends Document
{
	
	
	private  Long created_to;
	private  Long regDate_to;
	private  Long edited_to;
	
	private Long gagrDate_to;
	private Long date_to;
	
	private Long historyDate_to;
	
	private String ScannedRef;
	
	private Boolean notProperlyRegistered;
	private Boolean isLegal;
	
	private Boolean forArchive;
	private Boolean forLive;
	private Boolean hasArchiveHistory;
	
	
	//FOR REPORT
	
	private  Date createdR;
	private  Date regDateR;
	private  Date editedR;
	
	private Date gagrDateR;
	private Date dateR;
	
	
	
	public Long getCreated_to() {
		return created_to;
	}
	public void setCreated_to(Long created_to) {
		this.created_to = created_to;
	}
	public Long getRegDate_to() {
		return regDate_to;
	}
	public void setRegDate_to(Long regDate_to) {
		this.regDate_to = regDate_to;
	}
	public Long getEdited_to() {
		return edited_to;
	}
	public void setEdited_to(Long edited_to) {
		this.edited_to = edited_to;
	}
	public Long getGagrDate_to() {
		return gagrDate_to;
	}
	public void setGagrDate_to(Long gagrDate_to) {
		this.gagrDate_to = gagrDate_to;
	}
	public Long getDate_to() {
		return date_to;
	}
	public void setDate_to(Long date_to) {
		this.date_to = date_to;
	}
	public Long getHistoryDate_to() {
		return historyDate_to;
	}
	public void setHistoryDate_to(Long historyDate_to) {
		this.historyDate_to = historyDate_to;
	}
	public String getScannedRef() {
		return ScannedRef;
	}
	public void setScannedRef(String scannedRef) {
		ScannedRef = scannedRef;
	}
	public Boolean getNotProperlyRegistered() {
		return notProperlyRegistered;
	}
	public void setNotProperlyRegistered(Boolean notProperlyRegistered) {
		this.notProperlyRegistered = notProperlyRegistered;
	}
	public Boolean getIsLegal() {
		return isLegal;
	}
	public void setIsLegal(Boolean isLegal) {
		this.isLegal = isLegal;
	}
	public Boolean getForArchive() {
		return forArchive;
	}
	public void setForArchive(Boolean forArchive) {
		this.forArchive = forArchive;
	}
	public Boolean getForLive() {
		return forLive;
	}
	public void setForLive(Boolean forLive) {
		this.forLive = forLive;
	}
	public Boolean getHasArchiveHistory() {
		return hasArchiveHistory;
	}
	public void setHasArchiveHistory(Boolean hasArchiveHistory) {
		this.hasArchiveHistory = hasArchiveHistory;
	}
	public Date getCreatedR() {
		return createdR;
	}
	public void setCreatedR(Date createdR) {
		this.createdR = createdR;
	}
	public Date getRegDateR() {
		return regDateR;
	}
	public void setRegDateR(Date regDateR) {
		this.regDateR = regDateR;
	}
	public Date getEditedR() {
		return editedR;
	}
	public void setEditedR(Date editedR) {
		this.editedR = editedR;
	}
	public Date getGagrDateR() {
		return gagrDateR;
	}
	public void setGagrDateR(Date gagrDateR) {
		this.gagrDateR = gagrDateR;
	}
	public Date getDateR() {
		return dateR;
	}
	public void setDateR(Date dateR) {
		this.dateR = dateR;
	}
	
	
	
	
	
	
	
	

}
