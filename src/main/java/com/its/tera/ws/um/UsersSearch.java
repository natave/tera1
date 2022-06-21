package com.its.tera.ws.um;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.AuthorityType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.google.gson.reflect.TypeToken;
import com.its.tera.model.um.User;
import com.its.tera.ws.RootWebScript;

public class UsersSearch extends RootWebScript {
	
	private static Log logger = LogFactory.getLog(UsersSearch.class);

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		NodeService nodeService = serviceRegistry.getNodeService();
		

		String text /* = req.getParameter("root") */;
		try {
			text = URLEncoder.encode(req.getParameter("name"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
		}
		
		List<User> usersList = new ArrayList<User>();
		Set<String> result = serviceRegistry.getAuthorityService().findAuthorities(AuthorityType.USER, null, false, "*" + text + "*", null);
		for(String user : result)
		{
			User u = new User();
			u.setUserName(user);
			u.setName(user);
			NodeRef personRef = serviceRegistry.getPersonService().getPerson(user, false);
			//logger.debug("user = " + user + ",  personRef = " + personRef);
			if(personRef != null)
			{
				//logger.debug("first = " + nodeService.getProperty(personRef, ContentModel.PROP_FIRSTNAME));
				u.setFirstName((String) nodeService.getProperty(personRef, ContentModel.PROP_FIRSTNAME));
				u.setLastName((String) nodeService.getProperty(personRef, ContentModel.PROP_LASTNAME));
				u.setEmail((String) nodeService.getProperty(personRef, ContentModel.PROP_EMAIL));
			}
			usersList.add(u);
		}
		
		
		Type typeOfSrc = new TypeToken<Collection<User>>(){}.getType();
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(usersList, typeOfSrc));
		return model;
	}
	
	

}
