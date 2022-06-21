package com.its.tera.util;

public class AppParameters 
{
	public static final String NAME = "appParameters";
	
	private String hostPortInfo;
	private String hostPortInfoArchive;
	
	// Number of days to calculate date from. DocService - Journal
	int numberOfDays;
	String passwordValidateRule;
	String searchPath;
	String ksbAPIServiceSoapAddress;
	String branchesParentGroup;
	String rootBranch;
	String agrDateFormat;
	String nameValidateRule;
	
	Integer yearArchMove;
	Integer yearArchPrepare;
	Integer yearArchSend;
	
	public AppParameters() 
	{
	}

	public String getHostPortInfo() {
		return hostPortInfo;
	}

	public void setHostPortInfo(String hostPortInfo) {
		this.hostPortInfo = hostPortInfo;
	}

	public String getHostPortInfoArchive() {
		return hostPortInfoArchive;
	}

	public void setHostPortInfoArchive(String hostPortInfoArchive) {
		this.hostPortInfoArchive = hostPortInfoArchive;
	}

	public int getNumberOfDays() 
	{
		return numberOfDays;
	}

	public void setNumberOfDays(int numberOfDays) 
	{
		this.numberOfDays = numberOfDays;
	}
	
	public String getPasswordValidateRule() 
	{
		return passwordValidateRule;
	}

	public void setPasswordValidateRule(String passwordValidateRule) 
	{
		this.passwordValidateRule = passwordValidateRule;
	}

	public String getSearchPath()
	{
		return searchPath;
	}
	
	public void setSearchPath(String searchPath)
	{
		this.searchPath = searchPath;
	}

	public String getKsbAPIServiceSoapAddress() {
		return ksbAPIServiceSoapAddress;
	}

	public void setKsbAPIServiceSoapAddress(String ksbAPIServiceSoapAddress) {
		this.ksbAPIServiceSoapAddress = ksbAPIServiceSoapAddress;
	}

	public String getBranchesParentGroup() {
		return branchesParentGroup;
	}

	public void setBranchesParentGroup(String branchesParentGroup) {
		this.branchesParentGroup = branchesParentGroup;
	}

	public String getRootBranch() {
		return rootBranch;
	}

	public void setRootBranch(String rootBranch) {
		this.rootBranch = rootBranch;
	}

	public String getAgrDateFormat() {
		return agrDateFormat;
	}

	public void setAgrDateFormat(String agrDateFormat) {
		this.agrDateFormat = agrDateFormat;
	}

	public String getNameValidateRule() {
		return nameValidateRule;
	}

	public void setNameValidateRule(String nameValidateRule) {
		this.nameValidateRule = nameValidateRule;
	}

	public Integer getYearArchMove() {
		return yearArchMove;
	}

	public void setYearArchMove(Integer yearArchMove) {
		this.yearArchMove = yearArchMove;
	}

	public Integer getYearArchPrepare() {
		return yearArchPrepare;
	}

	public void setYearArchPrepare(Integer yearArchPrepare) {
		this.yearArchPrepare = yearArchPrepare;
	}

	public Integer getYearArchSend() {
		return yearArchSend;
	}

	public void setYearArchSend(Integer yearArchSend) {
		this.yearArchSend = yearArchSend;
	}

	
}
