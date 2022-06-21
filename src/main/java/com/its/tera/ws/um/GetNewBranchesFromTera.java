package com.its.tera.ws.um;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.security.AuthorityService;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.google.gson.reflect.TypeToken;
import com.its.tera.constants.CO;
import com.its.tera.model.um.Group;
import com.its.tera.services3.Department;
import com.its.tera.services3.ITSResultDepartment;
import com.its.tera.services3.ITSServiceSoapProxy;
import com.its.tera.util.AppParameters;
import com.its.tera.ws.RootWebScript;

public class GetNewBranchesFromTera extends RootWebScript{
	
	ITSServiceSoapProxy iTSServiceSoapProxy;
	AppParameters appParameters;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		AuthorityService authorityService = serviceRegistry.getAuthorityService();
		
		List<Group> branchesNotExists = new ArrayList<Group>();
		ITSResultDepartment itsResultDeps = null;
		try{
			itsResultDeps = iTSServiceSoapProxy.getDepartments(appParameters.getKsbAPIServiceSoapAddress());
		}catch(Exception e) {
			e.printStackTrace();
			throw new WebScriptException(e.getMessage());
		}
		
		Department[] allBranchesFromKSB = itsResultDeps.getDepartments();
		for(int i = 0; i < allBranchesFromKSB.length; i++)
		{
			if(!authorityService.authorityExists(CO.GROUP_ + CO.BRANCH_ + allBranchesFromKSB[i].getDEPT_NO()))
			{
				Group branch = new Group();
				branch.setBranchId(allBranchesFromKSB[i].getDEPT_NO());
				branch.setTitle(allBranchesFromKSB[i].getDESCRIP());
				branch.setName(CO.GROUP_ + CO.BRANCH_ + allBranchesFromKSB[i].getDEPT_NO());
				branchesNotExists.add(branch);
			}					
		}
		
		Type typeOfSrc = new TypeToken<Collection<Group>>(){}.getType();
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(branchesNotExists, typeOfSrc));
		return model;
	}

	public void setiTSServiceSoapProxy(ITSServiceSoapProxy iTSServiceSoapProxy) {
		this.iTSServiceSoapProxy = iTSServiceSoapProxy;
	}

	public void setAppParameters(AppParameters appParameters) {
		this.appParameters = appParameters;
	}
	
	
	

}
