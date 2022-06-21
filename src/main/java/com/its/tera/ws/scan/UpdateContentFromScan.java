package com.its.tera.ws.scan;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.util.TempFileProvider;
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
import com.its.tera.model.TechModel;
import com.its.tera.util.AppParameters;
import com.its.tera.util.FillModelsUtil;
import com.its.tera.ws.RootWebScript;

public class UpdateContentFromScan extends RootWebScript{
	
	private static Log logger = LogFactory.getLog(UpdateContentFromScan.class);
	
	AppParameters appParameters;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		
		ContentService contentService = serviceRegistry.getContentService();
		FileFolderService fileFolderService = serviceRegistry.getFileFolderService();
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
		logger.debug("payload = " + payload);
		
		TechModel doc = null;
		try {
			doc = getGson().fromJson(payload, TechModel.class);
		} catch (JsonSyntaxException e) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
		}		
		
		
		
		NodeRef docRef = new NodeRef(doc.getNodeRef());
		
		NodeRef scanRef = new NodeRef(doc.getScannedRef());
		
		
		
		
		String mimetype = fileFolderService.getFileInfo(scanRef).getContentData().getMimetype();
		String extension = serviceRegistry.getMimetypeService().getExtension(mimetype);
		
		File sourceFile = TempFileProvider.createTempFile("ITS_TRNS_Source_", extension);
		ContentReader reader = contentService.getReader(scanRef, ContentModel.PROP_CONTENT);
        reader.getContent(sourceFile);		        
		
		logger.debug("sourceFile - " + sourceFile.getName() + "  extension - " + extension);
		
		String docName = fileFolderService.getFileInfo(docRef).getName();	
		if(docName.contains(".") && !extension.equalsIgnoreCase(docName.substring(docName.lastIndexOf("."))))
		{
			nodeService.setProperty(docRef, ContentModel.PROP_NAME, docName.substring(0, docName.lastIndexOf(".")) + extension);
		}
		else if (!docName.contains(".")) {
			nodeService.setProperty(docRef, ContentModel.PROP_NAME, docName + extension);
		}
		
		ContentWriter writer = contentService.getWriter(docRef, ContentModel.PROP_CONTENT, true);			        
        writer.setMimetype(mimetype);					
		writer.putContent(sourceFile);
		

		if(scanRef != null && nodeService.exists(scanRef))
		{
			nodeService.deleteNode(scanRef);
		}
		
		
		if(nodeService.hasAspect(docRef, CM.QNAME_ASPECT_ITEM_IS_EMPTY))
		{
			nodeService.removeAspect(docRef, CM.QNAME_ASPECT_ITEM_IS_EMPTY);
		}
		
		
		
		Document docModel = FillModelsUtil.getDocModelFull(docRef, new NodeRef(doc.getClientRef()), serviceRegistry, req.getServiceContextPath(), appParameters);
		docModel.setParentRef(doc.getParentRef());
		
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(docModel));
		return model;
	}
	
	
	public void setAppParameters(AppParameters appParameters) {
		this.appParameters = appParameters;
	}
	
	
	

}
