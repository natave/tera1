package com.its.tera.ws;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.TicketComponent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

public class GetTicketForArchive extends RootWebScript {
	
	private static Log logger = LogFactory.getLog(GetTicketForArchive.class);
	
	private TicketComponent ticketComponent;
	/**
	 * @param ticketComponent
	 */
	public void setTicketComponent(TicketComponent ticketComponent) {
		this.ticketComponent = ticketComponent;
	}

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		
		
		
//		String user = req.getExtensionPath();
//		String ticket = req.getExtensionPath();
		
		String user = req.getParameter("u");
		String ticket = req.getParameter("t");
		
		byte[] bytes = Base64.getDecoder().decode(user);
		String userName = new String(bytes);
		//AuthenticationUtil.setFullyAuthenticatedUser(userName);
		//serviceRegistry.getAuthenticationService().getCurrentTicket();
		
		ticket = (new String(Base64.getDecoder().decode(ticket))).replace("ROLE_TICKET:", "");
		logger.debug("ticket = " + ticket);
		
		
		String ticketUser = ticketComponent.validateTicket(ticket);
		if(ticketUser.equals(userName)) {
			
			
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("result",  "ok");
			return model;
		}else {
			throw new WebScriptException(Status.STATUS_UNAUTHORIZED, "Ticket is invalid!");
		}
		
		
		
		
	}
	
	
	
	

}
