package com.its.tera.ws.doc;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.service.cmr.repository.InvalidNodeRefException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.its.tera.constants.CM;
import com.its.tera.model.Document;
import com.its.tera.util.AppParameters;
import com.its.tera.util.FillModelsUtil;
import com.its.tera.ws.RootWebScript;

public class MoveDocument extends RootWebScript{
	
	private static Log logger = LogFactory.getLog(MoveDocument.class);
	AppParameters appParameters;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		NodeService nodeService = serviceRegistry.getNodeService();
		
//		String doc = req.getParameter("doc");
//		String newParent = req.getParameter("parent");
//		String client = req.getParameter("client");
		
		NodeRef docRef;
		NodeRef newParentRef;
		NodeRef clientRef;
		try {
			docRef = new NodeRef(req.getParameter("doc"));
			newParentRef = new NodeRef(req.getParameter("parent"));
			clientRef = new NodeRef(req.getParameter("client"));
			logger.debug("docRef = " + docRef + ", newParentRef = " + newParentRef + ", clientRef = " + clientRef);
		} catch (Exception e1) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e1.getMessage());
		}
		
		
		
		
		try 
		{
			AuthenticationUtil.runAsSystem(new RunAsWork<Object>() {
				@Override
				public Object doWork() throws Exception {
					nodeService.moveNode(docRef, newParentRef, ContentModel.ASSOC_CONTAINS, 
							QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, (String) nodeService.getProperty(docRef, ContentModel.PROP_NAME)));
					return null;
				}
			});
			//logger.debug(" old REF = " + sourceNodeRef.toString());
		}
		//catch (FileExistsException | FileNotFoundException e) 
		catch(InvalidNodeRefException e)
		{
			throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, "This document is not possible to move!");
		}
		
		
		
		
		
		
		if(nodeService.getType(newParentRef).equals(CM.QNAME_TYPE_CLIENT_FOLDER) || nodeService.getType(newParentRef).equals(CM.QNAME_TYPE_ORDINARY_FOLDER) 
				|| nodeService.getType(newParentRef).equals(CM.QNAME_TYPE_LEGAL_DOC_FOLDER))
		{
			logger.debug(" CLIENT OR ORDINARY                                <<<<<<");
			if(nodeService.hasAspect(docRef, CM.QNAME_ASPECT_GEN_AGR_PROPS))
				nodeService.removeAspect(docRef, CM.QNAME_ASPECT_GEN_AGR_PROPS);
			if(nodeService.hasAspect(docRef, CM.QNAME_ASPECT_AGR_PROPS))
				nodeService.removeAspect(docRef, CM.QNAME_ASPECT_AGR_PROPS);
		}
		else if(nodeService.getType(newParentRef).equals(CM.QNAME_TYPE_GENERAL_AGREEMENT_FOLDER))
		{
			if(nodeService.hasAspect(docRef, CM.QNAME_ASPECT_AGR_PROPS))
				nodeService.removeAspect(docRef, CM.QNAME_ASPECT_AGR_PROPS);
			
			Map<QName, Serializable> aspectProperties = new HashMap<QName, Serializable>();
			aspectProperties.put(CM.QNAME_PROP_ASPECT_GEN_AGR_ID, nodeService.getProperty(newParentRef, CM.QNAME_PROP_ASPECT_GEN_AGR_ID));
			aspectProperties.put(CM.QNAME_PROP_ASPECT_GEN_AGR_NUM, nodeService.getProperty(newParentRef, CM.QNAME_PROP_ASPECT_GEN_AGR_NUM));
			aspectProperties.put(CM.QNAME_PROP_ASPECT_GEN_AGR_AMOUNT, nodeService.getProperty(newParentRef, CM.QNAME_PROP_ASPECT_GEN_AGR_AMOUNT));
			aspectProperties.put(CM.QNAME_PROP_ASPECT_GEN_AGR_CURRENCY, nodeService.getProperty(newParentRef, CM.QNAME_PROP_ASPECT_GEN_AGR_CURRENCY));
			aspectProperties.put(CM.QNAME_PROP_ASPECT_GEN_AGR_DATE, nodeService.getProperty(newParentRef, CM.QNAME_PROP_ASPECT_GEN_AGR_DATE));
			if(!nodeService.hasAspect(docRef, CM.QNAME_ASPECT_GEN_AGR_PROPS))
			{				
				nodeService.addAspect(docRef, CM.QNAME_ASPECT_GEN_AGR_PROPS, null );
			}			
			nodeService.addProperties(docRef, aspectProperties);			
			
		}
		else if(nodeService.getType(newParentRef).equals(CM.QNAME_TYPE_AGREEMENT_FOLDER))
		{
			Map<QName, Serializable> aspectProperties = new HashMap<QName, Serializable>();
			aspectProperties.put(CM.QNAME_PROP_ASPECT_AGR_ID, nodeService.getProperty(newParentRef, CM.QNAME_PROP_ASPECT_AGR_ID));
			aspectProperties.put(CM.QNAME_PROP_ASPECT_AGR_NUM, nodeService.getProperty(newParentRef, CM.QNAME_PROP_ASPECT_AGR_NUM));
			aspectProperties.put(CM.QNAME_PROP_ASPECT_AGR_AMOUNT, nodeService.getProperty(newParentRef, CM.QNAME_PROP_ASPECT_AGR_AMOUNT));
			aspectProperties.put(CM.QNAME_PROP_ASPECT_AGR_CURRENCY, nodeService.getProperty(newParentRef, CM.QNAME_PROP_ASPECT_AGR_CURRENCY));
			aspectProperties.put(CM.QNAME_PROP_ASPECT_AGR_PRODUCT, nodeService.getProperty(newParentRef, CM.QNAME_PROP_ASPECT_AGR_PRODUCT));
			aspectProperties.put(CM.QNAME_PROP_ASPECT_AGR_DATE, nodeService.getProperty(newParentRef, CM.QNAME_PROP_ASPECT_AGR_DATE));
			if(!nodeService.hasAspect(docRef, CM.QNAME_ASPECT_AGR_PROPS))
			{				
				nodeService.addAspect(docRef, CM.QNAME_ASPECT_AGR_PROPS, null);
			}			
			nodeService.addProperties(docRef, aspectProperties);
			
			if(nodeService.getProperty(newParentRef, CM.QNAME_PROP_ASPECT_GEN_AGR_NUM) == null && nodeService.hasAspect(docRef, CM.QNAME_ASPECT_GEN_AGR_PROPS))
			{
				nodeService.removeAspect(docRef, CM.QNAME_ASPECT_GEN_AGR_PROPS);
			}
			else if(nodeService.getProperty(newParentRef, CM.QNAME_PROP_ASPECT_GEN_AGR_NUM) != null)
			{
				Map<QName, Serializable> genAspectProperties = new HashMap<QName, Serializable>();
				genAspectProperties.put(CM.QNAME_PROP_ASPECT_GEN_AGR_ID, nodeService.getProperty(newParentRef, CM.QNAME_PROP_ASPECT_GEN_AGR_ID));
				genAspectProperties.put(CM.QNAME_PROP_ASPECT_GEN_AGR_NUM, nodeService.getProperty(newParentRef, CM.QNAME_PROP_ASPECT_GEN_AGR_NUM));
				genAspectProperties.put(CM.QNAME_PROP_ASPECT_GEN_AGR_AMOUNT, nodeService.getProperty(newParentRef, CM.QNAME_PROP_ASPECT_GEN_AGR_AMOUNT));
				genAspectProperties.put(CM.QNAME_PROP_ASPECT_GEN_AGR_CURRENCY, nodeService.getProperty(newParentRef, CM.QNAME_PROP_ASPECT_GEN_AGR_CURRENCY));
				genAspectProperties.put(CM.QNAME_PROP_ASPECT_GEN_AGR_DATE, nodeService.getProperty(newParentRef, CM.QNAME_PROP_ASPECT_GEN_AGR_DATE));
						
				if(!nodeService.hasAspect(docRef, CM.QNAME_ASPECT_GEN_AGR_PROPS))
				{				
					nodeService.addAspect(docRef, CM.QNAME_ASPECT_GEN_AGR_PROPS, null );
				}	
				nodeService.addProperties(docRef, genAspectProperties);
			}
		}
		nodeService.setProperty(docRef, CM.QNAME_PROP_ASPECT_BRANCH, nodeService.getProperty(newParentRef, CM.QNAME_PROP_ASPECT_BRANCH));
		
		Document movedDoc = FillModelsUtil.getDocModelFull(docRef, clientRef, serviceRegistry, req.getServiceContextPath(), appParameters);
		
		
		
		
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(movedDoc));
		return model;
	}

	public void setAppParameters(AppParameters appParameters) {
		this.appParameters = appParameters;
	}
	
	
	
	
	

}
