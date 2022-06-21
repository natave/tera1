package com.its.tera.ws.um;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.AuthorityType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.util.Content;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.google.gson.JsonSyntaxException;
import com.its.tera.constants.SE;
import com.its.tera.model.um.Group;
import com.its.tera.mybatis.service.DBServiceBean;
import com.its.tera.util.AppParameters;
import com.its.tera.util.ArchiveCalls;
import com.its.tera.util.NodeUtil;
import com.its.tera.ws.RootWebScript;

public class BranchBlockUnblock extends RootWebScript{
	
	private static Log logger = LogFactory.getLog(BranchBlockUnblock.class);
	
	private DBServiceBean dbServiceBean;
	private AppParameters appParameters;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		AuthorityService authorityService = serviceRegistry.getAuthorityService();
		
		String block = req.getServiceMatch().getTemplateVars().get("block");
		
		
		Content content = req.getContent();
		if (content == null) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Requset body is missing! URL = '" + req.getURL() + "'");
		}

		String payload = null;
		try {
			payload = content.getContent();
		} catch (IOException e) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
		}

		if (payload == null || payload.length() == 0) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Payload is missing!");
		}
		logger.debug("block = '" + block + "', payload = " + payload);
		
		Group branch = null;
		try {
			branch = getGson().fromJson(payload, Group.class);
		} catch (JsonSyntaxException e) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
		}		
		String branchName = branch.getName();
		
		//BLOCK
		if(block != null && block.equalsIgnoreCase("true")) {
			Set<String> ch = authorityService.getContainedAuthorities(AuthorityType.USER, branchName, true);
			RunAsWork<Void> runAsAdmin = new RunAsWork<Void>() {
				
				@Override
				public Void doWork() throws Exception {
					for (String string : ch) {
						authorityService.removeAuthority(branchName, string);
					}	
					return null;
				}
			};
			AuthenticationUtil.runAs(runAsAdmin, AuthenticationUtil.getAdminUserName());
				
			
			branch.setStatus(-1);
			branch.setChildren(new ArrayList<Group>());
			dbServiceBean.updateBranch(branch);
		}
		//UNBLOCK
		else {
			branch.setStatus(1);
			dbServiceBean.updateBranch(branch);
		}
		
		
		String responseDoc = null;
		try {			
			
			// get ticket
	        String ticket = serviceRegistry.getAuthenticationService().getCurrentTicket();
			String authData = "ROLE_TICKET:" + ticket;
			String authData64 = Base64.getEncoder().encodeToString(authData.getBytes());
			
			
			String t = "Basic " + authData64;
			
			responseDoc = ArchiveCalls.callServicePut(SE.BRANCH_BLOCK_UNBLOCK + block, payload, appParameters, t);
		} catch (IOException e) {
			e.printStackTrace();
			throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(branch));
		return model;
	}
	
	

	public void setDbServiceBean(DBServiceBean dbServiceBean) {
		this.dbServiceBean = dbServiceBean;
	}



	public void setAppParameters(AppParameters appParameters) {
		this.appParameters = appParameters;
	}

	
	
	
	
	
	
	
	
	
	

}
