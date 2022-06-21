package com.its.tera.ws.um;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.AuthorityType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.its.tera.constants.SE;
import com.its.tera.constants.UM;
import com.its.tera.model.DataModel;
import com.its.tera.model.um.Group;
import com.its.tera.model.um.User;
import com.its.tera.util.AppParameters;
import com.its.tera.util.ArchiveCalls;
import com.its.tera.ws.RootWebScript;

public class SyncGroupUsers extends RootWebScript{
	
	private static Log logger = LogFactory.getLog(SyncGroupUsers.class);
	
	AppParameters appParameters;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		
		AuthorityService authorityService = serviceRegistry.getAuthorityService();
		
		// get ticket
        String ticket = serviceRegistry.getAuthenticationService().getCurrentTicket();
		String authData = "ROLE_TICKET:" + ticket;
		String authData64 = Base64.getEncoder().encodeToString(authData.getBytes());
		
		
		String t = "Basic " + authData64;
		
		Set<String> branchesList = authorityService.getContainedAuthorities(AuthorityType.GROUP, UM.GROUP_BRANCHES, true);
		for (String string : branchesList) {
			
			Group b = new Group();
			b.setBranchId(string.replace(UM.GROUP_ + UM.BRANCH_START, ""));
			b.setName(string.replace(UM.GROUP_ , ""));			
			
			String response = null;
			try {			
				
				response = ArchiveCalls.callServicePost(SE.BRANCH_CREATE, getGson().toJson(b).toString(), appParameters, t);
				logger.debug("response = " + response);
			} catch (IOException e) {
				//e.printStackTrace();
				throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
			}
			
			
			
			Set<String> users = authorityService.getContainedAuthorities(AuthorityType.USER, string, true);
			for (String user : users) {
				
				User u = new User();
				u.setName(string);
				u.setUserName(user);
				
				
				String responseDoc = null;
				try {	
					
					responseDoc = ArchiveCalls.callServicePut(SE.GROUP_ADD_REMOVE + "add", getGson().toJson(u).toString(), appParameters, t);
				} catch (IOException e) {
					//e.printStackTrace();
					throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
				}
			}
		}
		
		
		
		
		
//		Set<String> rightsGroups = authorityService.getContainedAuthorities(AuthorityType.GROUP, UM.GROUP_RIGHTS, true);
//		for (String string : rightsGroups) {			
//			
//			Set<String> users = authorityService.getContainedAuthorities(AuthorityType.USER, string, true);
//			for (String user : users) {
//				
//				User u = new User();
//				u.setName(string);
//				u.setUserName(user);
//				
//				
//				String responseDoc = null;
//				try {	
//					
//					responseDoc = ArchiveCalls.callServicePut(SE.GROUP_ADD_REMOVE + "add", getGson().toJson(u).toString(), appParameters, t);
//				} catch (IOException e) {
//					e.printStackTrace();
//					throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
//				}								
//			}
//		}
//		
//		
//		
//		
//		
//		Set<String> vipRightsGroups = authorityService.getContainedAuthorities(AuthorityType.GROUP, UM.GROUP_VIP_RIGHTS, true);
//		for (String string : vipRightsGroups) {			
//			
//			Set<String> users = authorityService.getContainedAuthorities(AuthorityType.USER, string, true);
//			for (String user : users) {
//				
//				User u = new User();
//				u.setName(string);
//				u.setUserName(user);
//				
//				
//				String responseDoc = null;
//				try {	
//					
//					responseDoc = ArchiveCalls.callServicePut(SE.GROUP_ADD_REMOVE + "add", getGson().toJson(u).toString(), appParameters, t);
//				} catch (IOException e) {
//					e.printStackTrace();
//					throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
//				}
//			}
//		}
		
		
		
		Set<String> legalRightsGroups = authorityService.getContainedAuthorities(AuthorityType.GROUP, UM.GROUP_LEGAL_RIGHTS, true);
		for (String string : legalRightsGroups) {			
			
			Set<String> users = authorityService.getContainedAuthorities(AuthorityType.USER, string, true);
			for (String user : users) {
				
				User u = new User();
				u.setName(string);
				u.setUserName(user);
				
				
				String responseDoc = null;
				try {	
					
					responseDoc = ArchiveCalls.callServicePut(SE.GROUP_ADD_REMOVE + "add", getGson().toJson(u).toString(), appParameters, t);
				} catch (IOException e) {
					//e.printStackTrace();
					throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
				}				
			}
		}
		
		
		
		
		
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(new DataModel("ok")));
		return model;
	}

	public void setAppParameters(AppParameters appParameters) {
		this.appParameters = appParameters;
	}
	
	
	

}
