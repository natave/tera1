package com.its.tera.mybatis.mappers;

import java.util.List;

import com.its.tera.model.Client;


public interface ClientsTempTableMapper 
{
	
	public List<Client> selectClientsTempDataResults(String reportId);
	public void insertClientsTempDataForReport(Client model);
	public void clearClientsTempData();
	public void deleteClientsTempDataByReportId(String reportId);

}
