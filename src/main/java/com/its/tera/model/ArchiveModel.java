package com.its.tera.model;

import java.util.List;

import org.alfresco.service.cmr.repository.NodeRef;

public class ArchiveModel {
	
	private Integer sent;
	private List<String> forArchive;
	private List<String> notListedAgrsUnderGeneral;
	private List<String> agrsUnderNotListedGeneral;
	
	
	public Integer getSent() {
		return sent;
	}
	public void setSent(Integer sent) {
		this.sent = sent;
	}
	public List<String> getForArchive() {
		return forArchive;
	}
	public void setForArchive(List<String> forArchive) {
		this.forArchive = forArchive;
	}
	public List<String> getNotListedAgrsUnderGeneral() {
		return notListedAgrsUnderGeneral;
	}
	public void setNotListedAgrsUnderGeneral(List<String> notListedAgrsUnderGeneral) {
		this.notListedAgrsUnderGeneral = notListedAgrsUnderGeneral;
	}
	public List<String> getAgrsUnderNotListedGeneral() {
		return agrsUnderNotListedGeneral;
	}
	public void setAgrsUnderNotListedGeneral(List<String> agrsUnderNotListedGeneral) {
		this.agrsUnderNotListedGeneral = agrsUnderNotListedGeneral;
	}
	
	
	
	
	
	
	

}
