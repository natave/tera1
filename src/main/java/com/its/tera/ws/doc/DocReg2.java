package com.its.tera.ws.doc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.service.cmr.model.FileExistsException;
import org.alfresco.service.cmr.model.FileNotFoundException;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentIOException;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.InvalidNodeRefException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.AccessStatus;
import org.alfresco.service.cmr.security.PermissionService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.util.Content;
import org.springframework.extensions.surf.util.URLDecoder;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.servlet.WebScriptServletRuntime;

import com.google.gson.JsonSyntaxException;
import com.its.tera.constants.CM;
import com.its.tera.model.Document;
import com.its.tera.util.AppParameters;
import com.its.tera.util.FillModelsUtil;
import com.its.tera.util.NodeUtil;
import com.its.tera.util.PropsUtil;
import com.its.tera.ws.RootWebScript;

public class DocReg2 extends RootWebScript{
	
	private static Log logger = LogFactory.getLog(DocReg2.class);
	
	AppParameters appParameters;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		NodeService nodeService = serviceRegistry.getNodeService();
		PermissionService permissionService = serviceRegistry.getPermissionService();
		
		
		
		HttpServletRequest httpServletRequest = WebScriptServletRuntime.getHttpServletRequest(req);
		if (!ServletFileUpload.isMultipartContent(httpServletRequest)) {
			throw new WebScriptException("I can process only 'multipart/form-data' requests!");
		}
		
		
		FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
		
		
        List<FileItem> fileItems;
		try {
			fileItems = upload.parseRequest(httpServletRequest);
		} catch (FileUploadException e) {
			throw new WebScriptException("File upload error: " + e.getMessage());
		}

		if (fileItems.size() == 0) {
			throw new WebScriptException("There is no ietms in request!");
		}
		
		
		String payload = null;
		FileItem uploadedFile = null;
		String originalName = null;
		
		for (FileItem fileItem : fileItems) {
			if (fileItem.isFormField()) {
				String fieldName = URLDecoder.decode(fileItem.getFieldName());
				String value = URLDecoder.decode(fileItem.getString());
				if("data".equalsIgnoreCase(fieldName)){
					logger.debug("value = " + value);
					
					
					
//					byte[] decodedArray = Base64.getDecoder().decode(value);
//					try {
//						payload = new String(decodedArray, "UTF-8");
//					} catch (UnsupportedEncodingException e) {
//						//e.printStackTrace();
//						throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
//					}
					
					payload = URLDecoder.decode(value);
				}
			}
			else{
				originalName = fileItem.getName();
				uploadedFile = fileItem;
			}
			
		}
		
		
		if(originalName != null && originalName.contains("+")) {
			originalName = originalName.replaceAll("\\+", "");
		}
		
		

		if (payload == null || payload.length() == 0) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Payload is missing!");
		}
		logger.debug("payload = " + (payload.contains("docContent") ? payload.split("docContent")[0] : payload));
		
		Document doc = null;
		try {
			doc = getGson().fromJson(payload, Document.class);
		} catch (JsonSyntaxException e) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
		}		
		
		Document docModel = null;
		NodeRef docRef = null;
		NodeRef clientRef = new NodeRef((String) doc.getClientRef());
		

		Map<QName, Serializable>  propsQnames = PropsUtil.getDocModelProps(doc);

		
		String regNumber = (new Date()).getTime() + "";
		propsQnames.put(CM.QNAME_PROP_REG_NUMBER, regNumber);
		String documentName = doc.getCorrType() + "-" + regNumber + (originalName != null && originalName.lastIndexOf(".") > 0 ? originalName.substring(originalName.lastIndexOf(".")) : "");
		propsQnames.put(ContentModel.PROP_NAME, documentName);logger.debug("originalName = " + originalName + ", documentName = " + documentName);
		propsQnames.put(CM.QNAME_PROP_ASPECT_REGISTER_DATE, new Date());
		propsQnames.put(CM.QNAME_PROP_ASPECT_CREATOR_USER, AuthenticationUtil.getFullyAuthenticatedUser());
		
//		propsQnames.put(CM.QNAME_PROP_ASPECT_BRANCH, doc.getBranch());
//		propsQnames.put(CM.QNAME_PROP_IS_VIP, doc.getVIP());
		
		NodeRef parentRef = new NodeRef(doc.getParentRef()== null ? doc.getClientRef() : doc.getParentRef());
		
		if(doc.getNodeRef() == null || doc.getNodeRef().isEmpty()) 
		{
			//ORDINARY REGISTRATION (NOT FROM SCANNER)
			ChildAssociationRef document = nodeService.createNode(parentRef, ContentModel.ASSOC_CONTAINS, 
					QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, documentName), 
					QName.createQName(CM.NAMESPACE_URI, doc.getType()), propsQnames);
			docRef = document.getChildRef();
			
			NodeUtil.setIsContentIndexedFalse(docRef, nodeService);
			
			
			
			//WITH OR WITHOUT CONTENT
			if(uploadedFile != null) {
				
				String mime = uploadedFile.getContentType();
				logger.debug("mime = " + mime);
				
				ContentWriter writer = serviceRegistry.getContentService().getWriter(docRef, ContentModel.PROP_CONTENT, true);
				writer.setMimetype(mime/* MimetypeMap.MIMETYPE_PDF */);
				writer.setEncoding("UTF-8");
				try {
					writer.putContent(uploadedFile.getInputStream());
				} catch (ContentIOException | IOException e) {
					throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, "General error occurred while reading or writing content ...");
				}
			}else {
				nodeService.addAspect(docRef, CM.QNAME_ASPECT_ITEM_IS_EMPTY, null);
			}	
		}
		else
		{
			//REGISTRATION FROM SCANNER
			docRef = new NodeRef(doc.getNodeRef());		
			
			if(nodeService.exists(docRef))
			{
				String n = (String) nodeService.getProperty(docRef, ContentModel.PROP_NAME);
				n.replace("/", "-");
				
				int point = n.lastIndexOf(".");
				if(point != -1){
					documentName = n.substring(0, point) + "-" + regNumber + n.substring(point);
					propsQnames.put(ContentModel.PROP_NAME, documentName);
				}
				
				if(!nodeService.getType(docRef).equals(CM.QNAME_TYPE_CREDIT_DOC))
				{
					nodeService.setType(docRef, CM.QNAME_TYPE_CREDIT_DOC);
				}			
				nodeService.addProperties(docRef, propsQnames);
			}
			else
			{
				throw new WebScriptException("This noderef is invalid!");
			}
			
			try 
			{
				//logger.debug("userName = " + userName + " ; parentRef = " + parentRef + " ; docRef = " + docRef + " ; documentName = " + documentName);
				serviceRegistry.getFileFolderService().move(docRef, parentRef, documentName);
			} 
			catch (FileExistsException | InvalidNodeRefException
					| FileNotFoundException e) 
			{
				e.printStackTrace();
			}
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
