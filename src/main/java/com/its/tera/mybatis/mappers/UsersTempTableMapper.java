package com.its.tera.mybatis.mappers;

import java.util.List;

import com.its.tera.model.um.User;




public interface UsersTempTableMapper 
{
	
	public List<User> selectUsersTempDataResults(String reportId);
	public void insertUsersTempDataForReport(User model);
	public void clearUsersTempData();
	public void deleteUsersTempDataByReportId(String reportId);

}
