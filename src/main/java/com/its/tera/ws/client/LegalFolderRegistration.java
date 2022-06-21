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
import com.its.tera.model.Agreement;
import com.its.tera.util.FillModelsUtil;
import com.its.tera.util.NodeUtil;
import com.its.tera.ws.RootWebScript;

public class LegalFolderRegistration extends RootWebScript{
	
	private static Log logger = LogFactory.getLog(LegalFolderRegistration.class);

	NodeService nodeService;
	SearchService searchService;
	PermissionService permissionService;
	
	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		nodeService = serviceRegistry.getNodeService();
		searchService = serviceRegistry.getSearchService();
		permissionService = serviceRegistry.getPermissionService();
		
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
		
		Agreement legal = null;
		try {
			legal = getGson().fromJson(payload, Agreement.class);
		} catch (JsonSyntaxException e) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
		}
		
		NodeRef clientRef = new NodeRef(legal.getClientRef());
		NodeRef legalBranchRef = getLegalBranchRef(legal.getBranch());
		
		
		Map<QName, Serializable>  propsQnames = new HashMap<QName, Serializable>();
		String name = CO.LEGAL_PREFIX + new Date().getTime();
		propsQnames.put(ContentModel.PROP_NAME, name);
		propsQnames.put(ContentModel.PROP_DESCRIPTION, legal.getFolderDesc());
		propsQnames.put(ContentModel.PROP_TITLE, legal.getFolderDesc());
		propsQnames.put(CM.QNAME_PROP_ASPECT_CLIENT_CODE, nodeService.getProperty(clientRef, CM.QNAME_PROP_ASPECT_CLIENT_CODE));
		propsQnames.put(CM.QNAME_PROP_ASPECT_CLIENT_ID, nodeService.getProperty(clientRef, CM.QNAME_PROP_ASPECT_CLIENT_ID));
		propsQnames.put(CM.QNAME_PROP_ASPECT_CLIENT_NAME, nodeService.getProperty(clientRef, CM.QNAME_PROP_ASPECT_CLIENT_NAME));
		
		ChildAssociationRef agr = nodeService.createNode(legalBranchRef, ContentModel.ASSOC_CONTAINS, 
				QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, name),
				CM.QNAME_TYPE_LEGAL_DOC_FOLDER, propsQnames);
		NodeRef folderRef = agr.getChildRef();
		
		Agreement legalModel = FillModelsUtil.getAgreementModel(folderRef, clientRef, nodeService);
		legalModel.setParentRef(clientRef.toString());
		
		nodeService.createAssociation(clientRef, folderRef, CM.QNAME_ASSOC_ASPECT_LEGALS);
		
		
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(legalModel));
		return model;
	}
	
	
	public NodeRef getLegalBranchRef(String branch) throws WebScriptException{
		
		logger.debug("branch  ---- " + branch);
		
		String branchName = "Branch_" + branch + "_L";
		logger.debug("branchName  ---- " + branchName);
		
		NodeRef branchRef = null;
			
		
				String queryBranch = "TYPE:\""+ ContentModel.TYPE_FOLDER +"\" AND @cm\\:name:\"" + branchName + "\"";	
				logger.debug("queryBranch = " + queryBranch);
				ResultSet branchResult = searchService.query(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE, SearchService.LANGUAGE_LUCENE, queryBranch);
				if(branchResult == null || branchResult.getNodeRefs().isEmpty()){
					String queryLegal = "TYPE:\"" + ContentModel.TYPE_FOLDER 
							+ "\" AND PATH:\"/app:company_home/cm:Legal" + "\"";	
					
					logger.debug("queryLegal = " + queryLegal);
					ResultSet legalResult = searchService.query(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE, SearchService.LANGUAGE_LUCENE, queryLegal);
					if(legalResult == null || legalResult.getNodeRefs().isEmpty()){
						logger.debug("Legal doc's folder doesn't exists!!!!!!  - " + branchName);
						throw new WebScriptException("Legal doc's folder doesn't exists!!!!!!  - " + branchName);
					}else{
						NodeRef legalsRef = legalResult.getNodeRef(0);
						branchRef = NodeUtil.createFolderRef(branchName, legalsRef, null, false, nodeService, permissionService);
					}					
					
				}else{
					branchRef = branchResult.getNodeRef(0);												
				}
			
		return branchRef;
	}
	
	
	
	
	
	

}
