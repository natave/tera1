package com.its.tera.mybatis.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.its.tera.model.db.Currency;



public interface CurrencyMapper 
{
	public List<Currency> selectCurrency();
	public Currency selectCurrencyByName(@Param("name")String name);
	public void insertCurrency(Currency corrType);
	public void deleteCurrency(Currency corrType);
	public void updateCurrency(Currency corrType);
}