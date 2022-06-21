package com.its.tera.ws.um;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.service.cmr.security.AuthorityService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.util.Content;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.google.gson.JsonSyntaxException;
import com.its.tera.constants.SE;
import com.its.tera.model.DataModel;
import com.its.tera.model.Document;
import com.its.tera.model.um.User;
import com.its.tera.util.AppParameters;
import com.its.tera.util.ArchiveCalls;
import com.its.tera.util.NodeUtil;
import com.its.tera.ws.RootWebScript;

public class AddRemoveFromGroup extends RootWebScript{
	
	private static Log logger = LogFactory.getLog(AddRemoveFromGroup.class);
	
	private AppParameters appParameters;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		AuthorityService authorityService = serviceRegistry.getAuthorityService();
		
		//String groupName = req.getServiceMatch().getTemplateVars().get("group");		
		String add = req.getServiceMatch().getTemplateVars().get("add");
		
		
//		String groupName /* = req.getParameter("root") */;
//		String userName;
//		try {
//			groupName = URLEncoder.encode(req.getParameter("group"), "utf-8");
//			userName = URLEncoder.encode(req.getParameter("user"), "utf-8");
//		} catch (UnsupportedEncodingException e) {
//			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
//		}
		
		
		
		
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
		logger.debug("action = '" + add + "', payload = " + payload);
		
		User user = null;
		try {
			user = getGson().fromJson(payload, User.class);
		} catch (JsonSyntaxException e) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
		}
		
		String groupName = user.getName();
		String userName = user.getUserName();
		
		if((groupName == null || groupName.isEmpty()) || (userName == null || userName.isEmpty())) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Group or user is missing!");
		}
		

		RunAsWork<Void> runAsAdmin = new RunAsWork<Void>() {
			
			@Override
			public Void doWork() throws Exception {
				if(add != null && add.equalsIgnoreCase("add")) {
					authorityService.addAuthority(groupName, userName);
				}else {
					authorityService.removeAuthority(groupName, userName);
				}
				return null;
			}
		};
		AuthenticationUtil.runAs(runAsAdmin, AuthenticationUtil.getAdminUserName());
		
		
		
		String responseDoc = null;
		try {			
			
			// get ticket
	        String ticket = serviceRegistry.getAuthenticationService().getCurrentTicket();
			String authData = "ROLE_TICKET:" + ticket;
			String authData64 = Base64.getEncoder().encodeToString(authData.getBytes());
			
			
			String t = "Basic " + authData64;
			
			responseDoc = ArchiveCalls.callServicePut(SE.GROUP_ADD_REMOVE + add, payload, appParameters, t);
		} catch (IOException e) {
			e.printStackTrace();
			throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(new DataModel("ok")));
		return model;
	}

	public void setAppParameters(AppParameters appParameters) {
		this.appParameters = appParameters;
	}
	
	
	

}
