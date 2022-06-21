package com.its.tera.mybatis.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.its.tera.model.db.ClientCodesModel;

public interface ClientCodesMapper 
{
	
	public List<ClientCodesModel> selectClientCodes();
	public List<ClientCodesModel> selectActivTopClientCodes(@Param("howMuch")int howMuch);
	public ClientCodesModel selectClientCodesById(@Param("id")int id);
	public List<ClientCodesModel> selectClientCodesByStatus(@Param("status")int status);
	public ClientCodesModel selectClientCodesByCode(@Param("code")String code);
	public void insertClientCodes(ClientCodesModel codes);
	public void deleteClientCodeByStatus(ClientCodesModel codes);
	public void updateClientCodeById(ClientCodesModel codes);
	public Integer selectCountClientCodes(@Param("status")int status);
}
