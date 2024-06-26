package com.its.tera.model.um;

import java.util.ArrayList;
import java.util.List;

public class User extends Group{
	
	private String userName;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String jobTitle;
	
	private String ticket;
	//private String branch;
	
	private List<String> allowed;
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
//	public String getBranch() {
//		return branch;
//	}
//	public void setBranch(String branch) {
//		this.branch = branch;
//	}
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
