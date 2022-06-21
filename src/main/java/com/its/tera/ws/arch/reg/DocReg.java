package com.its.tera.ws.arch.reg;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.service.cmr.model.FileExistsException;
import org.alfresco.service.cmr.model.FileNotFoundException;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.InvalidNodeRefException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
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
import com.its.tera.model.Document;
import com.its.tera.util.AppParameters;
import com.its.tera.util.FillModelsUtil;
import com.its.tera.util.NodeUtil;
import com.its.tera.util.PropsUtil;
import com.its.tera.ws.RootWebScript;

public class DocReg extends RootWebScript{
	
	private static Log logger = LogFactory.getLog(DocReg.class);
	
	AppParameters appParameters;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		NodeService nodeService = serviceRegistry.getNodeService();
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
		
		
		Document doc = null;
		try {
			doc = getGson().fromJson(payload, Document.class);
		} catch (JsonSyntaxException e) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
		}		
		
		logger.debug("payload = " + (payload.contains("docContent") ? payload.split("docContent")[0] : payload));
		
		Document docModel = null;
		NodeRef docRef = null;
		NodeRef clientRef = new NodeRef((String) doc.getClientRef());
		

		Map<QName, Serializable>  propsQnames = PropsUtil.getDocModelProps(doc);
		
		
		NodeRef parentRef = new NodeRef(doc.getParentRef()== null ? doc.getClientRef() : doc.getParentRef());
		
		
		//ORDINARY REGISTRATION (NOT FROM SCANNER)
		ChildAssociationRef document = nodeService.createNode(parentRef, ContentModel.ASSOC_CONTAINS, 
				QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, doc.getDocumentName()), 
				CM.QNAME_TYPE_CREDIT_DOC, propsQnames);
		docRef = document.getChildRef();
		
		NodeUtil.setIsContentIndexedFalse(docRef, nodeService);
		
		//WITH OR WITHOUT CONTENT
		String docContent = doc.getDocContent();
		if(docContent != null && !docContent.isEmpty()) {
			byte[] decodedArray = Base64.getDecoder().decode(docContent);
			ByteArrayInputStream str = new ByteArrayInputStream(decodedArray);	
			
			String mime = serviceRegistry.getMimetypeService().guessMimetype("filename", (InputStream)str);
			logger.debug("mime = " + mime);
			
			ContentWriter writer = serviceRegistry.getContentService().getWriter(docRef, ContentModel.PROP_CONTENT, true);
			writer.setMimetype(mime/* MimetypeMap.MIMETYPE_PDF */);
			writer.setEncoding("UTF-8");
			writer.putContent((InputStream)str);
		}else {
			nodeService.addAspect(docRef, CM.QNAME_ASPECT_ITEM_IS_EMPTY, null);
		}	
							
				
		
		if(nodeService.getType(parentRef).equals(CM.QNAME_TYPE_LEGAL_DOC_FOLDER)){
			nodeService.addAspect(docRef, CM.QNAME_ASPECT_LEGAL_DOCS, null);
		}
		
		
		docModel = FillModelsUtil.getDocModelFull(docRef, clientRef, serviceRegistry, req.getServiceContextPath(), appParameters);
		docModel.setParentRef(parentRef.toString());
		
		if(permissionService.hasPermission(clientRef, PermissionService.EDITOR).equals(AccessStatus.ALLOWED)) {
			NodeUtil.removeAspectClientEmpty(clientRef, AuthenticationUtil.getFullyAuthenticatedUser(), nodeService, permissionService);
		}
		else
		{
			RunAsWork<Void> addAssocs = new RunAsWork<Void>() {
				
				@Override
				public Void doWork() throws Exception {
					NodeUtil.removeAspectClientEmpty(clientRef, AuthenticationUtil.getFullyAuthenticatedUser(), nodeService, permissionService);
					return null;
				}
			};
			AuthenticationUtil.runAs(addAssocs, AuthenticationUtil.getAdminUserName());
		}
				
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(docModel));
		return model;
	}

	public void setAppParameters(AppParameters appParameters) {
		this.appParameters = appParameters;
	}
	
	
	

}
