package com.its.tera.ws.doc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.content.MimetypeMap;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
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
import com.its.tera.ws.RootWebScript;

public class UpdateContent extends RootWebScript{
	
	private static Log logger = LogFactory.getLog(UpdateContent.class);
	
	AppParameters appParameters;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		NodeService nodeService = serviceRegistry.getNodeService();
		
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
		//logger.debug("payload = " + payload);
		
		Document doc = null;
		try {
			doc = getGson().fromJson(payload, Document.class);
		} catch (JsonSyntaxException e) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
		}		
		
		NodeRef docRef = new NodeRef(doc.getNodeRef());
		
		logger.debug("docRef = " + docRef);
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
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Content is null!");
		}	
		
		if(nodeService.hasAspect(docRef, CM.QNAME_ASPECT_ITEM_IS_EMPTY))
		{
			nodeService.removeAspect(docRef, CM.QNAME_ASPECT_ITEM_IS_EMPTY);
		}	
		
		//Document docModel = FillModelsUtil.getDocModelFull(docRef, new NodeRef(doc.getClientRef()), false, serviceRegistry, req, appParameters);
		doc.setBrowseURL(FillModelsUtil.getBrowseUrlWithName(req.getServiceContextPath(), appParameters.getHostPortInfo(), docRef, nodeService.getProperty(docRef, ContentModel.PROP_NAME).toString(), serviceRegistry.getAuthenticationService().getCurrentTicket()));
		
		if(nodeService.getProperty(docRef, ContentModel.PROP_CONTENT) == null)
		{
			doc.setSize((long) 0);
			doc.setSizeString(FillModelsUtil.readableFileSize(0));
		}
		else
		{
			long size = serviceRegistry.getFileFolderService().getFileInfo(docRef).getContentData().getSize();
			doc.setSize(size);
			doc.setSizeString(FillModelsUtil.readableFileSize(size));
		}
		
		doc = FillModelsUtil.getDocModelFull(docRef, new NodeRef(doc.getClientRef()), serviceRegistry, req.getServiceContextPath(), appParameters);
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(doc));
		return model;
	}

	public void setAppParameters(AppParameters appParameters) {
		this.appParameters = appParameters;
	}
	
	

}
