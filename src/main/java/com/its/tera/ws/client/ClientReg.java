package com.its.tera.ws.client;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.cmr.security.PermissionService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.util.Content;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.google.gson.JsonSyntaxException;
import com.its.tera.constants.CM;
import com.its.tera.constants.CO;
import com.its.tera.model.Client;
import com.its.tera.util.NodeUtil;
import com.its.tera.ws.RootWebScript;

public class ClientReg extends RootWebScript{
	
	private static Log logger = LogFactory.getLog(ClientReg.class);
	
	NodeService nodeService;
	

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		nodeService = serviceRegistry.getNodeService();
		SearchService searchService = serviceRegistry.getSearchService();
		PermissionService permissionService = serviceRegistry.getPermissionService();
		
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
		logger.debug("payload = " + payload);
		
		Client client = null;
		try {
			client = getGson().fromJson(payload, Client.class);
		} catch (JsonSyntaxException e) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
		}
		
		
		if(client.getClientCode() == null || client.getClientId() == null 
				|| client.getClientName() == null ||  client.getBranch() == null){
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Some Client's parameter doesn't exists!");
		}
		
		String query = "TYPE:\""+ CM.QNAME_TYPE_CLIENT_FOLDER +"\" AND @" + CM.NAMESPACE_URI_SHORT + "\\:"
				+ CM.PROP_ASPECT_CLIENT_CODE + ":\"" + client.getClientCode() + "\"";	
		
		ResultSet results = searchService.query(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE, SearchService.LANGUAGE_SOLR_ALFRESCO, query );
		if(results != null && results.getNodeRefs().size() != 0)
		{
			throw new WebScriptException(Status.STATUS_NOT_ACCEPTABLE, "Client folder with code - " + client.getClientCode() + " already exists in system!");
		}		
		
		Date date = new Date();
		NodeRef monthRef;
		try {
			monthRef = NodeUtil.getMonthRef(date, client.getBranch(), true, client.getClientIsVIP(),
					searchService, nodeService, permissionService);
		} catch (Exception e) {
			throw new WebScriptException(Status.STATUS_NOT_FOUND, e.getMessage());
		}
		
		logger.debug(" client code to create - " + client.getClientCode());					
		Map<QName, Serializable> propertiesMap = new HashMap<QName, Serializable>();
		propertiesMap.put(CM.QNAME_PROP_ASPECT_CLIENT_CODE, (String) client.getClientCode());
		propertiesMap.put(CM.QNAME_PROP_ASPECT_CLIENT_ID, (String) client.getClientId());
		propertiesMap.put(CM.QNAME_PROP_ASPECT_CLIENT_NAME, (String) client.getClientName());
		propertiesMap.put(CM.QNAME_PROP_ASPECT_BRANCH, client.getBranch());
		propertiesMap.put(CM.QNAME_ASPECT_CLIENT_IS_VIP, client.getClientIsVIP());
		propertiesMap.put(ContentModel.PROP_NAME, CO.CLIENT_PREFIX + (String) client.getClientCode());					
		
		ChildAssociationRef clientFolder = nodeService.createNode(monthRef, ContentModel.ASSOC_CONTAINS, 
				QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, CO.CLIENT_PREFIX + (String) client.getClientCode()), 
				CM.QNAME_TYPE_CLIENT_FOLDER, propertiesMap);
		NodeRef clientRef = clientFolder.getChildRef();
		
		
		nodeService.addAspect(clientRef, CM.QNAME_ASPECT_ITEM_IS_EMPTY, null);
		
		client.setNodeRef(clientRef.toString());
		client.setIsEmpty(true);
		client.setCreated(((Date) nodeService.getProperty(clientRef, ContentModel.PROP_CREATED)).getTime());
		client.setCreator((String) nodeService.getProperty(clientRef, ContentModel.PROP_CREATOR));
		//client.setClientIsVIP(model.getClientIsVIP());
		
				
		
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(client));
		return model;
	}
	
	
	
	
	
	

}
