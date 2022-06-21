package com.its.tera.ws.arch;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.repo.security.authentication.AuthenticationException;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.TicketComponent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.its.tera.ws.RootWebScript;

public class GetUTInfo extends RootWebScript {
	
	private static Log logger = LogFactory.getLog(GetUTInfo.class);
	
	private TicketComponent ticketComponent;
	/**
	 * @param ticketComponent
	 */
	public void setTicketComponent(TicketComponent ticketComponent) {
		this.ticketComponent = ticketComponent;
	}

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		String header = req.getHeader("Authorization");
		
		String ticket = header.replace("Basic ", "");

		
//		String user = req.getParameter("u");
//		String ticket = req.getParameter("t");
		
//		byte[] bytes = Base64.getDecoder().decode(user);
//		String userName = new String(bytes);
		
		
		ticket = (new String(Base64.getDecoder().decode(ticket))).replace("ROLE_TICKET:", "");
		logger.debug("ticket = " + ticket);
		
		try {
			String ticketUser = ticketComponent.validateTicket(ticket);
			logger.debug("ticketUser = " + ticketUser);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("result",  ticketUser);
			return model;
		} catch (AuthenticationException e) {
			logger.debug("AuthenticationException Message = " + e.getMessage());
			throw new WebScriptException(Status.STATUS_UNAUTHORIZED, "Ticket is invalid!");
		}
		
		
		
		
		
		
		
		
	}
	
	
	
	

}
