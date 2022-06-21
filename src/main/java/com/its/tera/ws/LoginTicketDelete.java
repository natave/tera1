package com.its.tera.ws;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.repo.security.authentication.AuthenticationException;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.TicketComponent;
import org.alfresco.service.cmr.security.AuthenticationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

public class LoginTicketDelete extends DeclarativeWebScript {
	// dependencies
	private AuthenticationService authenticationService;
	private TicketComponent ticketComponent;
	
	private static Log logger = LogFactory.getLog(LoginTicketDelete.class);

	/**
	 * @param ticketComponent
	 */
	public void setTicketComponent(TicketComponent ticketComponent) {
		this.ticketComponent = ticketComponent;
	}

	/**
	 * @param authenticationService
	 */
	public void setAuthenticationService(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.alfresco.web.scripts.DeclarativeWebScript#executeImpl(org.alfresco.
	 * web.scripts.WebScriptRequest, org.alfresco.web.scripts.WebScriptResponse)
	 */
	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status) {
		// retrieve ticket from request and current ticket
		String ticket = req.getExtensionPath();
		if (ticket == null || ticket.length() == 0) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Ticket not specified");
		}
		
		logger.debug("ticket string" + new String(Base64.getDecoder().decode(ticket)));
		
		ticket = (new String(Base64.getDecoder().decode(ticket))).replace("ROLE_TICKET:", "");
		logger.debug("ticket = " + ticket);

		// construct model for ticket
		Map<String, Object> model = new HashMap<String, Object>(1, 1.0f);
		model.put("ticket", ticket);

		try {
			String ticketUser = ticketComponent.validateTicket(ticket);

			// do not go any further if tickets are different
			if (!AuthenticationUtil.getFullyAuthenticatedUser().equals(ticketUser)) {
				status.setCode(Status.STATUS_NOT_FOUND);
				status.setMessage("Ticket not found");
			} else {
				// delete the ticket
				authenticationService.invalidateTicket(ticket);
				status.setMessage("Deleted Ticket " + ticket);
			}
		} catch (AuthenticationException e) {
			status.setCode(Status.STATUS_NOT_FOUND);
			status.setMessage("Ticket not found");
		}

		status.setRedirect(true);
		return model;
	}

}
