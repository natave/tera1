package com.its.tera.util;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.cmr.security.AccessStatus;
import org.alfresco.service.cmr.security.PermissionService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;

import com.its.tera.constants.CM;
import com.its.tera.constants.CO;

public class NodeUtil {
	
	private static Log logger = LogFactory.getLog(NodeUtil.class);
	
	
	
	public static NodeRef getMonthRef(Date date, String branch, Boolean isClient, Boolean isVIP, 
			SearchService searchService, NodeService nodeService, PermissionService permissionService) throws Exception{
		
		StoreRef storeRef = StoreRef.STORE_REF_WORKSPACE_SPACESSTORE;
		
		if(isVIP == null){
			isVIP = false;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		Integer month = c.get(Calendar.MONTH) + 1;
		String monthString = month.toString().length() > 1 ? month.toString() : "0" + month.toString();
		
		logger.debug("branch  ---- " + branch);
		
		String branchName = "Branch_" + branch + (isVIP ? "_VIP" : "") + (isClient ? "_C" : "_A");
		logger.debug("branchName  ---- " + branchName);
		String yearName = branchName + "_Y" + year;
		String monthName = yearName + "_M" + monthString;
		
		NodeRef monthRef = null;
		String queryMonth = "TYPE:\""+ ContentModel.TYPE_FOLDER +"\" AND @cm\\:name:\"" + monthName + "\"";	
		logger.debug("queryMonth = " + queryMonth);
		
		ResultSet monthResult = searchService.query(storeRef, SearchService.LANGUAGE_SOLR_ALFRESCO, queryMonth);
		if(monthResult == null || monthResult.getNodeRefs().isEmpty()){
			//logger.debug("");
			String queryYear = "TYPE:\""+ ContentModel.TYPE_FOLDER +"\" AND @cm\\:name:\"" + yearName + "\"";	
			logger.debug("queryYear = " + queryYear);
			ResultSet yearResult = searchService.query(storeRef, SearchService.LANGUAGE_SOLR_ALFRESCO, queryYear);
			if(yearResult == null || yearResult.getNodeRefs().isEmpty()){
				//logger.debug("");
				String queryBranch = "TYPE:\""+ ContentModel.TYPE_FOLDER +"\" AND @cm\\:name:\"" + branchName + "\"";	
				logger.debug("queryBranch = " + queryBranch);
				ResultSet branchResult = searchService.query(storeRef, SearchService.LANGUAGE_SOLR_ALFRESCO, queryBranch);
				if(branchResult == null || branchResult.getNodeRefs().isEmpty()){
					String queryClients = "TYPE:\"" + ContentModel.TYPE_FOLDER 
							+ "\" AND PATH:\"/app:company_home/cm:SME/cm:" + (isClient ? "Clients" : "Agreements") 
							+ (isVIP ? "/cm:VIP" : "") + "\"";	
					
					logger.debug("queryClients = " + queryClients);
					ResultSet clientsResult = searchService.query(storeRef, SearchService.LANGUAGE_SOLR_ALFRESCO, queryClients);
					if(clientsResult == null || clientsResult.getNodeRefs().isEmpty()){
						logger.debug("Clients doesn't exists!!!!!!  - " + branchName);
						throw new Exception("Clients doesn't exists!!!!!!  - " + branchName);
					}else{
						NodeRef clientsRef = clientsResult.getNodeRef(0);
						NodeRef branchRef = createFolderRef(branchName, clientsRef, branch, isVIP, nodeService, permissionService);
						NodeRef yearRef = createFolderRef(yearName, branchRef, null, isVIP, nodeService, permissionService);
						monthRef = createFolderRef(monthName, yearRef, null, isVIP, nodeService, permissionService);		
					}					
					
				}else{
					NodeRef branchRef = branchResult.getNodeRef(0);
					NodeRef yearRef = createFolderRef(yearName, branchRef, null, isVIP, nodeService, permissionService);
					monthRef = createFolderRef(monthName, yearRef, null, isVIP, nodeService, permissionService);													
				}
			}else{						
				NodeRef yearRef = yearResult.getNodeRef(0);
				monthRef = createFolderRef(monthName, yearRef, null, isVIP, nodeService, permissionService);						
			}
		}else{
			monthRef = monthResult.getNodeRef(0);
		}
		return monthRef;
	}
	
	
	
	
	
	public static NodeRef createFolderRef(String name, NodeRef parentRef, String branchId, Boolean isVIP, NodeService nodeService, PermissionService permissionService){
		Map<QName, Serializable> monthProps = new HashMap<QName, Serializable>();
		monthProps.put(ContentModel.PROP_NAME, name);
		ChildAssociationRef ref = nodeService.createNode(parentRef, ContentModel.ASSOC_CONTAINS, 
				QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, name), 
				ContentModel.TYPE_FOLDER, monthProps );
		logger.debug("  Created Folder - " + name);
		if(branchId != null && !isVIP){
			permissionService.setPermission(ref.getChildRef(), CO.GROUP_ + CO.BRANCH_ + branchId, PermissionService.CONSUMER, true);			
		}
		return ref.getChildRef();
	}
	
	
	
	
	
	
	public static void removeAspectClientEmpty(NodeRef clientRef, final String userName, NodeService nodeService, final PermissionService permissionService){
		if(nodeService.hasAspect(clientRef, CM.QNAME_ASPECT_ITEM_IS_EMPTY))
		{
			final NodeRef clientFinalRef = clientRef;
			Boolean alloved = true;
			if(permissionService.hasPermission(clientRef, PermissionService.EDITOR) != AccessStatus.ALLOWED){
				alloved = false;
				AuthenticationUtil.runAsSystem(new RunAsWork<Object>() {
					@Override
					public Object doWork() throws Exception {
						permissionService.setPermission(clientFinalRef, userName, PermissionService.EDITOR, true);
						return null;
					}
				});
			}
			
			nodeService.removeAspect(clientRef, CM.QNAME_ASPECT_ITEM_IS_EMPTY);
			
			if(!alloved){
				AuthenticationUtil.runAsSystem(new RunAsWork<Object>() {
					@Override
					public Object doWork() throws Exception {
						permissionService.setPermission(clientFinalRef, userName, PermissionService.EDITOR, false);
						return null;
					}
				});
			}
		}
	}
	
	
	
	public static void setIsContentIndexedFalse(NodeRef docRef, NodeService nodeService){
		if(nodeService.getType(docRef).equals(CM.QNAME_TYPE_CREDIT_DOC)){
			Map<QName, Serializable> aspectProperties = new HashMap<QName, Serializable>();
			aspectProperties.put(ContentModel.PROP_IS_CONTENT_INDEXED, false);
			nodeService.addAspect(docRef, ContentModel.ASPECT_INDEX_CONTROL, aspectProperties );
		}
	}
	
	
	
	public static NodeRef findNode(String query, SearchService searchService, boolean throwExeption)
	{
		NodeRef resultRef = null;
		ResultSet results = searchService.query(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE, SearchService.LANGUAGE_SOLR_ALFRESCO, query);
		if(results.length() != 0)
		{
			resultRef = results.getNodeRef(0);
			results.close();
		}
		else
		{
			if(throwExeption)
			{
				throw new WebScriptException(Status.STATUS_NOT_FOUND, "Node does not exists. '" + query + "'");
			}
			else
			{
				return null;
			}
			
		}
		return resultRef;
		
	}
	
	
	
	
	

}
