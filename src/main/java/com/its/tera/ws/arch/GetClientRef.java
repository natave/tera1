package com.its.tera.ws.arch;

import java.util.HashMap;
import java.util.Map;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.its.tera.constants.CM;
import com.its.tera.ws.RootWebScript;

public class GetClientRef extends RootWebScript {
	
	private static Log logger = LogFactory.getLog(GetClientRef.class);

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		//AuthenticationUtil.setAdminUserAsFullyAuthenticatedUser();
		String clientCode = req.getExtensionPath();
		
		
		String query = "TYPE:\""+ CM.QNAME_TYPE_CLIENT_FOLDER +"\" AND @" + CM.NAMESPACE_URI_SHORT + "\\:"
				+ CM.PROP_ASPECT_CLIENT_CODE + ":\"" + clientCode + "\"";	
		
		logger.debug("query = " + query);
		
		ResultSet results = serviceRegistry.getSearchService().query(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE, SearchService.LANGUAGE_SOLR_ALFRESCO, query );
		if(results == null || results.getNodeRefs().isEmpty())
		{
			throw new WebScriptException(Status.STATUS_NOT_ACCEPTABLE, "Client folder with code - " + clientCode + " doesn't exists in the system!");
		}	
		
		NodeRef clientRef = results.getNodeRef(0);
		
		logger.debug("clientRef = " + clientRef.toString());
		
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result",  clientRef.toString());
		return model;
	}
	
	
	
	

}
