package com.its.tera.ws.um;

import java.io.IOException;
import java.io.Serializable;
import java.util.Base64;
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
import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.AuthorityType;
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
import com.its.tera.constants.Path;
import com.its.tera.constants.SE;
import com.its.tera.constants.UM;
import com.its.tera.model.DataModel;
import com.its.tera.model.Document;
import com.its.tera.model.um.Group;
import com.its.tera.mybatis.service.DBServiceBean;
import com.its.tera.util.AppParameters;
import com.its.tera.util.ArchiveCalls;
import com.its.tera.util.NodeUtil;
import com.its.tera.ws.RootWebScript;

public class CreateBranch extends RootWebScript {
	
	private static Log logger = LogFactory.getLog(CreateBranch.class);
	
	private DBServiceBean dbServiceBean;
	private AppParameters appParameters;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {		
		
		AuthorityService authorityService = serviceRegistry.getAuthorityService();
		SearchService searchService = serviceRegistry.getSearchService();
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
		logger.debug("payload = " + payload);
		
		Group branch = null;
		try {
			branch = getGson().fromJson(payload, Group.class);
		} catch (JsonSyntaxException e) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
		}
		
		String branchId = branch.getBranchId();
		String branchNameShort = CO.BRANCH_ + branchId;
		String branchNameFull = branch.getName();
		if(authorityService.authorityExists(branchNameFull))
		{
			throw new WebScriptException("Group with name : '" + branchNameShort + "' already exists!");
		}
		
//		ResultSet results = searchService.query(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE, SearchService.LANGUAGE_SOLR_ALFRESCO, Path.QUERY_BRANCH_ADD_NAME + branchName + "\"");
//		if(results != null && results.length() != 0)
//		{
//			throw new WebScriptException("Folder with name : '" + branchName + "' already exists!");
//		}
		
		if(dbServiceBean.selectBranchesByBranchId(branchId) != null)
		{
			throw new WebScriptException("Row with branchId : '" + branchId + "' already exists!");
		}
		
		
		
		/**
		 * Create Scans Folders For this Branch - 'scansBranch-{code}'*/
		ResultSet scanResult = searchService.query(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE, SearchService.LANGUAGE_LUCENE, Path.QUERY_SCANS_FOLDER);	
		if(scanResult != null)
		{
			NodeRef scansRef = scanResult.getNodeRef(0);
			
			Map<QName, Serializable> scanPropsMap = new HashMap<QName, Serializable>();
			scanPropsMap.put(ContentModel.PROP_NAME, CO.SCANS_PREFIX + branchNameShort);
			scanPropsMap.put(ContentModel.PROP_DESCRIPTION, branch.getTitle());
			
			ChildAssociationRef scansChildRef = nodeService.createNode(scansRef, ContentModel.ASSOC_CONTAINS, 
					QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, CO.SCANS_PREFIX + branchNameShort), 
					ContentModel.TYPE_FOLDER, scanPropsMap);
			NodeRef scansForBranchRef = scansChildRef.getChildRef();
			
			RunAsWork<Void> addAssocs = new RunAsWork<Void>() {
				
				@Override
				public Void doWork() throws Exception {
					String group = authorityService.createAuthority(AuthorityType.GROUP, branchNameShort);
					authorityService.addAuthority(UM.GROUP_BRANCHES, branchNameFull);
					return null;
				}
			};
			AuthenticationUtil.runAs(addAssocs, AuthenticationUtil.getAdminUserName());
			
			
			permissionService.setInheritParentPermissions(scansForBranchRef, true);
			permissionService.setPermission(scansForBranchRef, branchNameFull, PermissionService.COORDINATOR, true);
			
			/**
			 * Create Scans PDF Folder For this Branch - 'ScansPDF'*/
			Map<QName, Serializable> scansPDFPropsMap = new HashMap<QName, Serializable>();
			scansPDFPropsMap.put(ContentModel.PROP_NAME, CO.SCANS_PDF);
			scansPDFPropsMap.put(ContentModel.PROP_DESCRIPTION, branchNameShort + " - " +branch.getTitle());
			
			nodeService.createNode(scansForBranchRef, ContentModel.ASSOC_CONTAINS, 
					QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, CO.SCANS_PDF), 
					ContentModel.TYPE_FOLDER, scansPDFPropsMap);			
		}  
		else
		{
			throw new WebScriptException("Folder with name : '" + CO.SCANS + "' doesn't exists!");
		}
		
		
		dbServiceBean.insertBranch(branch);
		
		logger.debug("Branch Group with name - '" + branchNameShort + "' is created.");
		
		String response = null;
		try {
			
			// get ticket
	        String ticket = serviceRegistry.getAuthenticationService().getCurrentTicket();
			String authData = "ROLE_TICKET:" + ticket;
			String authData64 = Base64.getEncoder().encodeToString(authData.getBytes());
			
			
			String t = "Basic " + authData64;
			
			response = ArchiveCalls.callServicePost(SE.BRANCH_CREATE, payload, appParameters, t);
			logger.debug("response = " + response);
		} catch (IOException e) {
			//e.printStackTrace();
			throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(new DataModel("ok")));
		return model;
	}

	public void setDbServiceBean(DBServiceBean dbServiceBean) {
		this.dbServiceBean = dbServiceBean;
	}

	public void setAppParameters(AppParameters appParameters) {
		this.appParameters = appParameters;
	}
	
	
	
	

}
