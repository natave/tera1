package com.its.tera.mybatis.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.its.tera.model.um.Group;

public interface BranchMapper 
{
	
	public List<Group> selectBranches();
	public Group selectBranchesByBranchId(@Param("branchId")String branchId);
	public void insertBranch(Group branch);
	public void deleteBranch(Group branch);
	public void updateBranch(Group branch);
}
