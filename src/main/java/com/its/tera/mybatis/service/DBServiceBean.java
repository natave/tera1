package com.its.tera.mybatis.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;


import com.its.tera.model.Client;
import com.its.tera.model.Document;
import com.its.tera.model.db.ClientCodesModel;
import com.its.tera.model.db.CorrType;
import com.its.tera.model.db.Currency;
import com.its.tera.model.um.Group;
import com.its.tera.model.um.User;
import com.its.tera.mybatis.mappers.BranchMapper;
import com.its.tera.mybatis.mappers.ClientCodesMapper;
import com.its.tera.mybatis.mappers.ClientsTempTableMapper;
import com.its.tera.mybatis.mappers.CorrTypeMapper;
import com.its.tera.mybatis.mappers.CurrencyMapper;
import com.its.tera.mybatis.mappers.TempTableMapper;
import com.its.tera.mybatis.mappers.UsersTempTableMapper;

public class DBServiceBean 
{
	public static final String NAME = "dbServiceBean";
	
	

	/**
	 * Correspondence types
	 */
	@Transactional
	public List<CorrType> getCorrTypes() 
	{
		List<CorrType> result = new ArrayList<CorrType>();
		result.addAll(corrTypeMapper.selectCorrTypes());		
		return result;
	}
	
	public CorrType getCorrTypeByName(String name)
	{
		return corrTypeMapper.selectCorrTypeByName(name);
	}
	
	public void insertCorrType(CorrType corrType)
	{
		corrTypeMapper.insertCorrType(corrType);
	}
	
	public void updateCorrType(CorrType corrType)
	{
		corrTypeMapper.updateCorrType(corrType);
	}
	
	public void deleteCorrType(CorrType corrType)
	{
		corrTypeMapper.deleteCorrType(corrType);
	}
	
	/**
	 * Currency
	 */
	@Transactional
	public List<Currency> getCurrency() 
	{
		List<Currency> result = new ArrayList<Currency>();
		result.addAll(currencyMapper.selectCurrency());		
		return result;
	}
	
	public CorrType getCurrencyByName(String name)
	{
		return corrTypeMapper.selectCorrTypeByName(name);
	}
	
	public void insertCurrency(Currency currency)
	{
		currencyMapper.insertCurrency(currency);
	}
	
	public void updateCurrency(Currency currency)
	{
		currencyMapper.updateCurrency(currency);
	}
	
	public void deleteCurrency(Currency currency)
	{
		currencyMapper.deleteCurrency(currency);
	}
	
	
	/**
	 * Get Temporary Data For Report
	 */	
	public List<Document> selectTempDataResults(String reportId)
	{		
		return tempTableMapper.selectTempDataResults(reportId);
	}
	
	/**
	 * Insert Temporary Data For Report
	 */	
	public void insertTempDataForReport(Document tempModel)
	{
		tempTableMapper.insertTempDataForReport(tempModel);
	}
	
	/**
	 * Insert Temporary Data List For Report
	 */	
	public void insertTempDataListForReport(List<Document> tempModelList)
	{
		for(Document tempModel : tempModelList)
		{
			tempTableMapper.insertTempDataForReport(tempModel);
		}		
	}
	
	/**
	 * Clear All Temporary Data From Table
	 */	
	public void clearTempData()
	{
		tempTableMapper.clearTempData();
	}
	
	/**
	 * Clear Temporary Data By Report Id
	 */	
	public void deleteTempDataByReportId(String reportId)
	{
		tempTableMapper.deleteTempDataByReportId(reportId);
	}
	
	
	
	
	
	/**
	 * Get Clients Temporary Data For Report
	 */	
	public List<Client> selectClientsTempDataResults(String reportId)
	{		
		return clientsTempTableMapper.selectClientsTempDataResults(reportId);
	}
	
	/**
	 * Insert Clients Temporary Data For Report
	 */	
	public void insertClientTempDataForReport(Client tempModel)
	{
		clientsTempTableMapper.insertClientsTempDataForReport(tempModel);
	}
	
	/**
	 * Insert Clients Temporary Data List For Report
	 */	
	public void insertClientsTempDataListForReport(List<Client> tempModelList)
	{
		for(Client tempModel : tempModelList)
		{
			clientsTempTableMapper.insertClientsTempDataForReport(tempModel);
		}		
	}
	
	/**
	 * Clear All Clients Temporary Data From Table
	 */	
	public void clearClientsTempData()
	{
		clientsTempTableMapper.clearClientsTempData();
	}
	
	/**
	 * Clear Clients Temporary Data By Report Id
	 */	
	public void deleteClientsTempDataByReportId(String reportId)
	{
		clientsTempTableMapper.deleteClientsTempDataByReportId(reportId);
	}
	
	
	
	
	
	
	
	/**
	 * Get Users Temporary Data For Report
	 */	
	public List<User> selectUsersTempDataResults(String reportId)
	{		
		return usersTempTableMapper.selectUsersTempDataResults(reportId);
	}
	
	/**
	 * Insert Users Temporary Data For Report
	 */	
	public void insertUsersTempDataForReport(User tempModel)
	{
		usersTempTableMapper.insertUsersTempDataForReport(tempModel);
	}
	
	/**
	 * Insert Users Temporary Data List For Report
	 */	
	public void insertUsersTempDataListForReport(List<User> tempModelList)
	{
		for(User tempModel : tempModelList)
		{
			usersTempTableMapper.insertUsersTempDataForReport(tempModel);
		}		
	}
	
	/**
	 * Clear All Users Temporary Data From Table
	 */	
	public void clearUsersTempData()
	{
		usersTempTableMapper.clearUsersTempData();
	}
	
	/**
	 * Clear Users Temporary Data By Report Id
	 */	
	public void deleteUsersTempDataByReportId(String reportId)
	{
		usersTempTableMapper.deleteUsersTempDataByReportId(reportId);
	}
	

	
	
	

	/*private String getStringFromList(String key, ModelData model)
	{
		List<String> list = model.get(key);
		String text = "";
		String comma = "";
		if(list != null)
		{
			for(String value : list)
			{
				text = text + comma + value;
				comma = " ,";
			}
		}			
		return text;
	}
	
	
	*/
	
	/**
	 * Select All Active Branches
	 */	
	public List<Group> selectBranches()
	{
		return branchMapper.selectBranches();
	}
	
	/**
	 * Select Branch by Branch Id
	 */	
	public Group selectBranchesByBranchId(String branchId)
	{
		return branchMapper.selectBranchesByBranchId(branchId);
	}
	
	/**
	 * Insert Branch
	 */	
	public void insertBranch(Group branch)
	{
		branchMapper.insertBranch(branch);
	}
	
	/**
	 * Delete Branch
	 */	
	public void deleteBranch(Group branch)
	{
		branchMapper.deleteBranch(branch);
	}
	
	/**
	 * Update Branch
	 */	
	public void updateBranch(Group branch)
	{
		branchMapper.updateBranch(branch);
	}
	
	
	
	/**
	 *  Client Codes
	 */
	
	public List<ClientCodesModel> selectClientCodes()
	{
		return clientCodesMapper.selectClientCodes();
	}
	
	public List<ClientCodesModel> selectActivTopClientCodes(Integer status)
	{
		return clientCodesMapper.selectActivTopClientCodes(status);
	}
	
	public ClientCodesModel selectClientCodesById(Integer id)
	{
		return clientCodesMapper.selectClientCodesById(id);
	}
	
	public List<ClientCodesModel> selectClientCodesByStatus(Integer status)
	{
		return clientCodesMapper.selectClientCodesByStatus(status);
	}
	
	public ClientCodesModel selectClientCodesByCode(String code)
	{
		return clientCodesMapper.selectClientCodesByCode(code);
	}
	
	public void insertClientCodes(ClientCodesModel codes)
	{
		clientCodesMapper.insertClientCodes(codes);
	}
	
	public void deleteClientCodeByStatus(ClientCodesModel codes)
	{
		clientCodesMapper.deleteClientCodeByStatus(codes);
	}
	
	public void updateClientCodeById(ClientCodesModel codes)
	{
		clientCodesMapper.updateClientCodeById(codes);
	}
	
	public Integer selectClientCodesCount(Integer status)
	{
		return clientCodesMapper.selectCountClientCodes(status);
	}
	
	
		
	
	/**
	 *  Mappers
	 */
	
	CorrTypeMapper corrTypeMapper;	
	CurrencyMapper currencyMapper;
	TempTableMapper tempTableMapper;
	BranchMapper branchMapper;
	UsersTempTableMapper usersTempTableMapper;
	ClientsTempTableMapper clientsTempTableMapper;
	ClientCodesMapper clientCodesMapper;


	public void setCorrTypeMapper(CorrTypeMapper corrTypeMapper) 
	{
		this.corrTypeMapper = corrTypeMapper;
	}
	public void setCurrencyMapper(CurrencyMapper currencyMapper) {
		this.currencyMapper = currencyMapper;
	}

	public void setTempTableMapper(TempTableMapper tempTableMapper) 
	{
		this.tempTableMapper = tempTableMapper;
	}	
	public void setBranchMapper(BranchMapper branchMapper)
	{
		this.branchMapper = branchMapper;
	}
	public void setUsersTempTableMapper(UsersTempTableMapper usersTempTableMapper) 
	{
		this.usersTempTableMapper = usersTempTableMapper;
	}

	public void setClientsTempTableMapper(
			ClientsTempTableMapper clientsTempTableMapper) {
		this.clientsTempTableMapper = clientsTempTableMapper;
	}

	public void setClientCodesMapper(ClientCodesMapper clientCodesMapper) {
		this.clientCodesMapper = clientCodesMapper;
	}

	
}