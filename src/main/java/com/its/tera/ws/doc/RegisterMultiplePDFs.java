package com.its.tera.ws.doc;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.model.FileExistsException;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.model.FileNotFoundException;
import org.alfresco.service.cmr.repository.InvalidNodeRefException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.PermissionService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.util.Content;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.its.tera.constants.CM;
import com.its.tera.model.Agreement;
import com.its.tera.model.Document;
import com.its.tera.util.AppParameters;
import com.its.tera.util.FillModelsUtil;
import com.its.tera.util.NodeUtil;
import com.its.tera.util.PropsUtil;
import com.its.tera.ws.RootWebScript;

public class RegisterMultiplePDFs extends RootWebScript {
	
	private static Log logger = LogFactory.getLog(RegisterMultiplePDFs.class);
	AppParameters appParameters;
	

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		NodeService nodeService = serviceRegistry.getNodeService();
		FileFolderService fileFolderService = serviceRegistry.getFileFolderService();
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
		
		Document doc = null;
		try {
			doc = getGson().fromJson(payload, Document.class);
		} catch (JsonSyntaxException e) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
		}		
		
		List<Document> regDocs = new ArrayList<Document>();
		
		
		NodeRef clientRef = new NodeRef(doc.getClientRef());
		
		NodeRef parentRef = new NodeRef(doc.getParentRef());
		
		
//		Map<String, Serializable>  props = doc.getPropertiesMap();
//		Map<QName, Serializable>  propsQnames = new HashMap<QName, Serializable>();
//		for(String key : props.keySet())
//		{
//			propsQnames.put(QName.createQName(CM.NAMESPACE_URI, key), props.get(key));
//		}
		
		Map<QName, Serializable>  propsQnames = PropsUtil.getDocModelProps(doc);
		
		propsQnames.put(CM.QNAME_PROP_ASPECT_REGISTER_DATE, new Date());
		propsQnames.put(CM.QNAME_PROP_ASPECT_CREATOR_USER, AuthenticationUtil.getFullyAuthenticatedUser());
		propsQnames.put(CM.QNAME_PROP_ASPECT_BRANCH, doc.getBranch());
		propsQnames.put(CM.QNAME_PROP_IS_VIP, doc.getClientIsVIP());
		
		List<Agreement> children = doc.getChildren();
		for (Agreement child : children) {
			NodeRef docRef = new NodeRef(child.getNodeRef());
			
			
			
			if(nodeService.exists(docRef))
			{
				if(!nodeService.getType(docRef).equals(CM.QNAME_TYPE_CREDIT_DOC))
				{
					nodeService.setType(docRef, CM.QNAME_TYPE_CREDIT_DOC);
				}			
				nodeService.addProperties(docRef, propsQnames);
				NodeUtil.setIsContentIndexedFalse(docRef, nodeService);
										
				String regNumber = (new Date()).getTime() + "";
				nodeService.setProperty(docRef, CM.QNAME_PROP_REG_NUMBER, regNumber);
				
				String docName = (String) nodeService.getProperty(docRef, ContentModel.PROP_NAME);
				String newName = regNumber + "-" + docName;
				int point = docName.lastIndexOf(".");
				if(point > 0){
					newName = docName.substring(0, point) + "-" + regNumber + docName.substring(point);
				}
				
				try 
				{
					fileFolderService.move(docRef, parentRef, newName);
				} 
				catch (FileExistsException | InvalidNodeRefException
						| FileNotFoundException e) 
				{
					throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
				}
				Document docModel = FillModelsUtil.getDocModelFull(docRef, clientRef, serviceRegistry, req.getServiceContextPath(), appParameters/*, versionService*/);
				docModel.setParentRef(parentRef.toString());
				if(nodeService.getProperty(docRef, ContentModel.PROP_CONTENT) == null)
				{
					docModel.setSize((long) 0);
				}
				else
				{
					docModel.setSize(fileFolderService.getFileInfo(docRef).getContentData().getSize());
				}
				if(nodeService.getType(parentRef).equals(CM.QNAME_TYPE_LEGAL_DOC_FOLDER)){
					nodeService.addAspect(docRef, CM.QNAME_ASPECT_LEGAL_DOCS, null);
				}
				regDocs.add(docModel);	
				//increaseVersionLabel(docRef);
			}
			else
			{
				throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, "This noderef is invalid!");
			}			
			
		}
		NodeUtil.removeAspectClientEmpty(clientRef, AuthenticationUtil.getFullyAuthenticatedUser(), nodeService, permissionService);
						
		Type typeOfSrc = new TypeToken<Collection<Document>>(){}.getType();
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(regDocs, typeOfSrc));
		return model;
	}


	public void setAppParameters(AppParameters appParameters) {
		this.appParameters = appParameters;
	}
	
	

}
