package com.its.tera.mybatis.mappers;

import java.util.List;

import com.its.tera.model.Document;


public interface TempTableMapper 
{
	
	public List<Document> selectTempDataResults(String reportId);
	public void insertTempDataForReport(Document model);
	public void clearTempData();
	public void deleteTempDataByReportId(String reportId);

}
