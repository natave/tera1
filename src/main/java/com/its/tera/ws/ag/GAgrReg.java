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

public class GAgrReg extends RootWebScript{
	
	private static Log logger = LogFactory.getLog(GAgrReg.class);

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
		
		if(agr.getBranch() == null){
			throw new WebScriptException("Some Agreement's parameter doesn't exists!");
		}
		
		NodeRef clientRef = new NodeRef(agr.getClientRef());	
		NodeRef monthRef;
		try {
			monthRef = NodeUtil.getMonthRef(new Date(), agr.getBranch(), false, 
					(boolean) nodeService.getProperty(clientRef, CM.QNAME_ASPECT_CLIENT_IS_VIP), 
					searchService, nodeService, permissionService);
		} catch (Exception e) {
			throw new WebScriptException(Status.STATUS_NOT_FOUND, e.getMessage());
		}
		
//		Map<String, Serializable>  props = agr.getPropertiesMap();
		Map<QName, Serializable>  propsQnames = PropsUtil.getAgrModelProps(agr);
//		for(String key : props.keySet())
//		{
//			propsQnames.put(QName.createQName(CM.NAMESPACE_URI, key), props.get(key));
//			logger.debug(" -genAgr---- " + QName.createQName(CM.NAMESPACE_URI, key) + " - " + props.get(key));
//		}
		String name = CO.GAGR_PREFIX + new Date().getTime();
		propsQnames.put(ContentModel.PROP_NAME, name);
		propsQnames.put(ContentModel.PROP_DESCRIPTION, CO.GAGR_PREFIX + agr.getGagrNumber());
		propsQnames.put(ContentModel.PROP_TITLE, CO.GAGR_PREFIX + agr.getGagrNumber());
		
		ChildAssociationRef agreement = nodeService.createNode(monthRef, ContentModel.ASSOC_CONTAINS, 
				QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, name),
				CM.QNAME_TYPE_GENERAL_AGREEMENT_FOLDER, propsQnames);
		NodeRef agreementRef = agreement.getChildRef();
		
		if(permissionService.hasPermission(clientRef, PermissionService.EDITOR).equals(AccessStatus.ALLOWED)) {
			NodeUtil.removeAspectClientEmpty(clientRef, AuthenticationUtil.getFullyAuthenticatedUser(), nodeService, permissionService);
			
			nodeService.createAssociation(clientRef, agreementRef, CM.QNAME_ASSOC_ASPECT_GEN_AGRS);
		}
		else
		{
			RunAsWork<Void> addAssocs = new RunAsWork<Void>() {
				
				@Override
				public Void doWork() throws Exception {
					NodeUtil.removeAspectClientEmpty(clientRef, AuthenticationUtil.getFullyAuthenticatedUser(), nodeService, permissionService);
					
					nodeService.createAssociation(clientRef, agreementRef, CM.QNAME_ASSOC_ASPECT_GEN_AGRS);
					return null;
				}
			};
			AuthenticationUtil.runAs(addAssocs, AuthenticationUtil.getAdminUserName());
		}
		
						
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(FillModelsUtil.getAgreementModel(agreementRef, clientRef, nodeService)));
		return model;
	}
	
	
	

}
