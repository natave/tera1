package com.its.tera.mybatis.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.its.tera.model.db.CorrType;

public interface CorrTypeMapper 
{
	public List<CorrType> selectCorrTypes();
	public CorrType selectCorrTypeByName(@Param("name")String name);
	public void insertCorrType(CorrType corrType);
	public void deleteCorrType(CorrType corrType);
	public void updateCorrType(CorrType corrType);
}