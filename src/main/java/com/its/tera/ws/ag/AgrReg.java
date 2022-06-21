package com.its.tera.ws.ag;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.InvalidNodeRefException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.cmr.security.AccessStatus;
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
import com.its.tera.util.PropsUtil;
import com.its.tera.ws.RootWebScript;

public class AgrReg extends RootWebScript{
	
	private static Log logger = LogFactory.getLog(AgrReg.class);

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		

		NodeService nodeService = serviceRegistry.getNodeService();
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
		
		Agreement agr = null;
		try {
			agr = getGson().fromJson(payload, Agreement.class);
		} catch (JsonSyntaxException e) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
		}
		
		
		NodeRef parentRef = null;
		NodeRef clientRef = new NodeRef(agr.getClientRef());
		Boolean isUnderGeneral = agr.getGagrId() != null && !agr.getGagrId().isEmpty();
		if(isUnderGeneral)
		{
			parentRef = new NodeRef(agr.getParentRef());
		}
		else
		{
			parentRef = clientRef;
		}
		
		
		NodeRef monthRef;
		try {
			monthRef = NodeUtil.getMonthRef(new Date(), agr.getBranch(), false, 
					(boolean) nodeService.getProperty(clientRef, CM.QNAME_ASPECT_CLIENT_IS_VIP),
					searchService, nodeService, permissionService);
		} catch (InvalidNodeRefException e) {
			throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
		} catch (Exception e) {
			throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
		//Map<String, Serializable>  props = agr.getPropertiesMap();
		Map<QName, Serializable>  propsQnames = PropsUtil.getAgrModelProps(agr);
//		for(String key : props.keySet())
//		{
//			propsQnames.put(QName.createQName(CM.NAMESPACE_URI, key), props.get(key));
//			logger.debug(" -agr---- " + QName.createQName(CM.NAMESPACE_URI, key) + " - " + props.get(key));
//		}
		String name = CO.AGR_PREFIX + new Date().getTime();
		propsQnames.put(ContentModel.PROP_NAME, name);
		propsQnames.put(ContentModel.PROP_DESCRIPTION, CO.AGR_PREFIX + agr.getAgrNumber());
		propsQnames.put(ContentModel.PROP_TITLE, CO.AGR_PREFIX + agr.getAgrNumber());				
		
		ChildAssociationRef agreement = nodeService.createNode(monthRef, ContentModel.ASSOC_CONTAINS, 
				QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, name),
				CM.QNAME_TYPE_AGREEMENT_FOLDER, propsQnames);
		NodeRef agreementRef = agreement.getChildRef();
		
		if(permissionService.hasPermission(parentRef, PermissionService.EDITOR).equals(AccessStatus.ALLOWED) 
				&& permissionService.hasPermission(clientRef, PermissionService.EDITOR).equals(AccessStatus.ALLOWED)) {
			if(isUnderGeneral){
				nodeService.createAssociation(parentRef, agreementRef, CM.QNAME_ASSOC_ASPECT_GEN_CHILD_AGRS);
				String genBranch = (String) nodeService.getProperty(parentRef, CM.QNAME_PROP_ASPECT_BRANCH);
				if(!genBranch.equalsIgnoreCase(agr.getBranch())){
					nodeService.createAssociation(clientRef, agreementRef, CM.QNAME_ASSOC_ASPECT_GEN_CHILD_AGRS);
				}
			}else{
				nodeService.createAssociation(clientRef, agreementRef, CM.QNAME_ASSOC_ASPECT_AGRS);
			}
			
			NodeUtil.removeAspectClientEmpty(clientRef, AuthenticationUtil.getFullyAuthenticatedUser(), nodeService, permissionService);
		}
		//IF USER IS CONTRIBUTOR
		else 
		{
			final NodeRef parent = parentRef;
			final Agreement a = agr;
			RunAsWork<Void> addAssocs = new RunAsWork<Void>() {
				
				@Override
				public Void doWork() throws Exception {
					if(isUnderGeneral){
						nodeService.createAssociation(parent, agreementRef, CM.QNAME_ASSOC_ASPECT_GEN_CHILD_AGRS);
						String genBranch = (String) nodeService.getProperty(parent, CM.QNAME_PROP_ASPECT_BRANCH);
						if(!genBranch.equalsIgnoreCase(a.getBranch())){
							nodeService.createAssociation(clientRef, agreementRef, CM.QNAME_ASSOC_ASPECT_GEN_CHILD_AGRS);
						}
					}else{
						nodeService.createAssociation(clientRef, agreementRef, CM.QNAME_ASSOC_ASPECT_AGRS);
					}
					
					NodeUtil.removeAspectClientEmpty(clientRef, AuthenticationUtil.getFullyAuthenticatedUser(), nodeService, permissionService);
					return null;
				}
			};
			AuthenticationUtil.runAs(addAssocs, AuthenticationUtil.getAdminUserName());
		}
		
				
		
		
		Agreement agrModel = FillModelsUtil.getAgreementModel(agreementRef, clientRef, nodeService);
		agrModel.setParentRef(parentRef.toString());
		
		
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(agrModel));
		return model;
	}
	
	
	
	
	
	

}
