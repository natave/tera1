package com.its.tera.ws.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.repository.AssociationRef;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.cmr.security.AccessStatus;
import org.alfresco.service.cmr.security.PermissionService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.its.tera.constants.CM;
import com.its.tera.constants.CO;
import com.its.tera.constants.Path;
import com.its.tera.model.Agreement;
import com.its.tera.model.Client;
import com.its.tera.model.Document;
import com.its.tera.util.AppParameters;
import com.its.tera.util.FillModelsUtil;
import com.its.tera.ws.RootWebScript;

public class GetClientInfo extends RootWebScript{
	
	private static Log logger = LogFactory.getLog(GetClientInfo.class);
	
	AppParameters appParameters;
	
	NodeService nodeService;
	PermissionService permissionService;
	FileFolderService fileFolderService;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		nodeService  = serviceRegistry.getNodeService();
		permissionService = serviceRegistry.getPermissionService();
		fileFolderService = serviceRegistry.getFileFolderService();
		
		String code = req.getServiceMatch().getTemplateVars().get("code");		
		//String ref = req.getServiceMatch().getTemplateVars().get("ref");	
		String ref = req.getParameter("nodeRef");
		
		NodeRef clientRef = null;
		if(ref != null && !ref.isEmpty())
		{logger.debug("ref not encoded = " + ref);
		
			clientRef = new NodeRef(ref);
//			try {
//				clientRef = new NodeRef( URLEncoder.encode(ref, "utf-8") );//logger.debug("ref encoded = " + clientRef.toString());
//			} catch (UnsupportedEncodingException e) {
//				
//				throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage() + "  Invalid NodeRef : " + ref);
//			}
			if(!nodeService.exists(clientRef)) {
				throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Invalid NodeRef : " + clientRef);
			}
		}
		else if(code != null && !code.isEmpty()) 
		{
			String query = "TYPE:\""+ CM.QNAME_TYPE_CLIENT_FOLDER +"\" AND @" + CM.NAMESPACE_URI_SHORT + "\\:" + CM.PROP_ASPECT_CLIENT_CODE + 
					":\"" + code + "\""; // + " AND " + Path.QUERY_SME_ALL_CHILDREN;
			logger.debug("query = " + query);
			
			ResultSet results = serviceRegistry.getSearchService().query(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE, SearchService.LANGUAGE_SOLR_ALFRESCO, query );
			if(results == null || results.getNodeRefs().size() == 0)
			{
				throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Client folder with code - " + code + " doesn't exists in system!");
			}
			else
			{
				clientRef = results.getNodeRef(0);
			}
		}else {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Variables are missing!");
		}
		
		
		Client clientModel = new Client();
		
		//List<Agreement> treeList = new ArrayList<Agreement>();
		
		List<Agreement> treeList = new ArrayList<Agreement>();
		List<NodeRef> generalsChildrenList = new ArrayList<NodeRef>();
		
		List<AssociationRef> generalAssocs = nodeService.getTargetAssocs(clientRef, CM.QNAME_ASSOC_ASPECT_GEN_AGRS);
		logger.debug("generalAssocs size = " + generalAssocs.size() + " - - " + clientRef);
		for (AssociationRef associationRef : generalAssocs) {
			NodeRef generalRef = associationRef.getTargetRef();	
			generalsChildrenList.addAll(fillTree(generalRef, null, treeList, clientRef, req));
		}
		
		List<AssociationRef> ordinalAssocs = nodeService.getTargetAssocs(clientRef, CM.QNAME_ASSOC_ASPECT_AGRS);
		logger.debug("ordinalAssocs size = " + ordinalAssocs.size());
		for (AssociationRef associationRef : ordinalAssocs) {
			NodeRef ordinalRef = associationRef.getTargetRef();
			fillTree(ordinalRef, null, treeList, clientRef, req);
		}
		
		List<AssociationRef> generalsChildInClientAssocs = nodeService.getTargetAssocs(clientRef, CM.QNAME_ASSOC_ASPECT_GEN_CHILD_AGRS);
		logger.debug("generalsChildInClientAssocs size = " + generalsChildInClientAssocs.size());
		for (AssociationRef associationRef : generalsChildInClientAssocs) {
			NodeRef generalsChildInClientRef = associationRef.getTargetRef();
			if(generalsChildrenList.isEmpty()){
				fillTree(generalsChildInClientRef, null, treeList, clientRef, req);
			}else{
				if(!generalsChildrenList.contains(generalsChildInClientRef)){
					fillTree(generalsChildInClientRef, null, treeList, clientRef, req);
				}
			}					
		}	
		
		List<AssociationRef> legalDocAssocs = nodeService.getTargetAssocs(clientRef, CM.QNAME_ASSOC_ASPECT_LEGALS);
		logger.debug("legalDocsAssocs size = " + legalDocAssocs.size());
		for (AssociationRef associationRef : legalDocAssocs) {
			NodeRef ordinalRef = associationRef.getTargetRef();
			fillTree(ordinalRef, null, treeList, clientRef, req);
		}
		
		
		
		
		
		
		Set<QName> childNodeTypeQNames = new HashSet<QName>();
		
		
		
		
		childNodeTypeQNames.add(CM.QNAME_TYPE_ORDINARY_FOLDER);
		List<ChildAssociationRef> ords = nodeService.getChildAssocs(clientRef, childNodeTypeQNames);
		for(ChildAssociationRef child : ords)
		{
			NodeRef childRef = child.getChildRef();
			fillTree(childRef, null, treeList, clientRef, req);					
		}	
		
		childNodeTypeQNames = new HashSet<QName>();
		childNodeTypeQNames.add(CM.QNAME_TYPE_LEGAL_DOC_FOLDER);
		List<ChildAssociationRef> legals = nodeService.getChildAssocs(clientRef, childNodeTypeQNames);
		for(ChildAssociationRef child : legals)
		{
			NodeRef childRef = child.getChildRef();
			fillTree(childRef, null, treeList, clientRef, req);					
		}
		
		childNodeTypeQNames = new HashSet<QName>();
		childNodeTypeQNames.add(CM.QNAME_TYPE_CREDIT_DOC);
		List<ChildAssociationRef> children = nodeService.getChildAssocs(clientRef, childNodeTypeQNames);
		for(ChildAssociationRef child : children)
		{
			NodeRef childRef = child.getChildRef();
			fillTree(childRef, null, treeList, clientRef, req);					
		}	
		clientModel.setChildren(treeList);
		logger.debug("treeList size = " + treeList.size());
		
		
		clientModel.setNodeRef(clientRef.toString());
		clientModel.setCreated(((Date) nodeService.getProperty(clientRef, ContentModel.PROP_CREATED)).getTime());
		clientModel.setCreator((String) nodeService.getProperty(clientRef, ContentModel.PROP_CREATOR));
		clientModel.setEdited(((Date) nodeService.getProperty(clientRef, ContentModel.PROP_MODIFIED)).getTime());
		clientModel.setEditor((String) nodeService.getProperty(clientRef, ContentModel.PROP_MODIFIER));
		clientModel.setClientCode((String) nodeService.getProperty(clientRef, CM.QNAME_PROP_ASPECT_CLIENT_CODE));
		clientModel.setClientId((String) nodeService.getProperty(clientRef, CM.QNAME_PROP_ASPECT_CLIENT_ID));
		clientModel.setClientName((String) nodeService.getProperty(clientRef, CM.QNAME_PROP_ASPECT_CLIENT_NAME));
		clientModel.setClientIsVIP(nodeService.hasAspect(clientRef, CM.QNAME_ASPECT_CLIENT_IS_VIP) && (Boolean)nodeService.getProperty(clientRef, CM.QNAME_ASPECT_CLIENT_IS_VIP));
		clientModel.setIsEmpty(nodeService.hasAspect(clientRef, CM.QNAME_ASPECT_ITEM_IS_EMPTY));
		clientModel.setBranch((String) nodeService.getProperty(clientRef, CM.QNAME_PROP_ASPECT_BRANCH));
		logger.debug("client code = " + clientModel.getClientCode() + " CLIENT_IS_VIP = " + clientModel.getClientIsVIP()
				+ "  userName = "/* + userName */);
		
		if(permissionService.hasPermission(clientRef, PermissionService.CONTRIBUTOR).equals(AccessStatus.ALLOWED)) {
			clientModel.addAllowed(CO.KEY_NEW_G_AGR);
			clientModel.addAllowed(CO.KEY_NEW_AGR);
			
			clientModel.addAllowed(CO.KEY_NEW_DOC);
			clientModel.addAllowed(CO.KEY_NEW_SCAN);
			clientModel.addAllowed(CO.KEY_REPORT);
		}
		if(nodeService.hasAspect(clientRef, CM.QNAME_ASPECT_HAS_ARCHIVE)) {
			clientModel.addAllowed(CO.KEY_HAS_ARCHIVE);
		}
		
		
		
		logger.debug("key list = " + clientModel.getAllowed());
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(clientModel));
		return model;
	}
	
	
	
	
	
	public void setAppParameters(AppParameters appParameters) {
		this.appParameters = appParameters;
	}





	private List<NodeRef> fillTree(final NodeRef nodeRef, final Agreement root, final List<Agreement> list, final NodeRef clientRef, WebScriptRequest req) throws WebScriptException 
	{		
		List<NodeRef> generalsChildrenList = new ArrayList<NodeRef>();
		if(permissionService.hasPermission(nodeRef, PermissionService.CONSUMER) == AccessStatus.ALLOWED){
			
			if(nodeService.getType(nodeRef).equals(CM.QNAME_TYPE_CREDIT_DOC))
			{						
				Document docTree = FillModelsUtil.getDocModelFull(nodeRef, clientRef, serviceRegistry, req.getServiceContextPath(), appParameters/*, versionService*/);
				
				setKeyForDoc(nodeRef, docTree);
				//logger.debug("key ALLOWED d = " + docTree.getAllowed());
				
				docTree.setType(CM.TYPE_CREDIT_DOC);
				if(nodeService.getProperty(nodeRef, ContentModel.PROP_CONTENT) == null)
				{
					docTree.setSize((long) 0);
				}
				else
				{
					docTree.setSize(fileFolderService.getFileInfo(nodeRef).getContentData().getSize());
				}
				
				if(root != null)
				{
					//docTree.setParentAgreement(root);
					root.addChild(docTree);						
				}
				else
				{
					list.add(docTree);
				}
			}			
			else
			{
				Agreement tree = FillModelsUtil.getAgreementModel(nodeRef, clientRef, nodeService);
				
				setKeyForFolder(nodeRef, tree);
				//logger.debug("key ALLOWED f = " + tree.getAllowed());
							
				if(root != null)
				{
					//tree.setParentAgreement(root);
					root.addChild(tree);
				}
				else
				{
					list.add( tree);
				}
				
				List<AssociationRef> targets = nodeService.getTargetAssocs(nodeRef, CM.QNAME_ASSOC_ASPECT_GEN_CHILD_AGRS);
				
				for (AssociationRef associationRef : targets) {
					fillTree(associationRef.getTargetRef(), tree, null, clientRef, req);
					generalsChildrenList.add(associationRef.getTargetRef());
				}
				
				for(ChildAssociationRef child : nodeService.getChildAssocs(nodeRef))
				{
					if(child.getChildRef() != nodeRef)
					{
						fillTree(child.getChildRef(), tree, null, clientRef, req);
					}
				}					
			}
			
			
		}else{
			logger.debug("NOT ALLOWED - " + nodeRef);
		}
		return generalsChildrenList;
	}
	
	
	
	
	
	
	private void setKeyForFolder(NodeRef nodeRef, Agreement model) {
		List<String> allowed = new ArrayList<String>();
		model.setAllowed(allowed );
		
		if(permissionService.hasPermission(nodeRef, PermissionService.CONTRIBUTOR).equals(AccessStatus.ALLOWED)) {
			model.addAllowed(CO.KEY_NEW_DOC);
			model.addAllowed(CO.KEY_NEW_SCAN);
			model.addAllowed(CO.KEY_PASTE);
			
			if(nodeService.getType(nodeRef).equals(CM.QNAME_TYPE_GENERAL_AGREEMENT_FOLDER)) {
				model.addAllowed(CO.KEY_NEW_AGR);
			}
		}
		
		if(permissionService.hasPermission(nodeRef, PermissionService.COORDINATOR).equals(AccessStatus.ALLOWED)) {
			model.addAllowed(CO.KEY_DELETE);
			
			if(nodeService.getType(nodeRef).equals(CM.QNAME_TYPE_GENERAL_AGREEMENT_FOLDER) 
					|| ((nodeService.getType(nodeRef).equals(CM.QNAME_TYPE_AGREEMENT_FOLDER) && !nodeService.hasAspect(nodeRef, CM.QNAME_ASPECT_GEN_AGR_PROPS)))) {
				if(nodeService.hasAspect(nodeRef, CM.QNAME_ASPECT_FOR_ARCHIVE)) {
					model.addAllowed(CO.KEY_NO_ARCHIVE);
				}else {
					model.addAllowed(CO.KEY_FOR_ARCHIVE);
				}
			}
		}
		
	}
	
	
	
	private void setKeyForDoc(NodeRef nodeRef, Document doc) {
		
		
		//permissionService.TAKE_OWNERSHIP;
		
		
		
		
		if(permissionService.hasPermission(nodeRef, PermissionService.EDITOR).equals(AccessStatus.ALLOWED)) {
			doc.addAllowed(CO.KEY_UPLOAD);
			doc.addAllowed(CO.KEY_SCAN_UPLOAD);
			doc.addAllowed(CO.KEY_UPDATE);
		}
		
		if(permissionService.hasPermission(nodeRef, PermissionService.CONTRIBUTOR).equals(AccessStatus.ALLOWED)) {
			doc.addAllowed(CO.KEY_CUT);
		}
		
		
		if(permissionService.hasPermission(nodeRef, PermissionService.COORDINATOR).equals(AccessStatus.ALLOWED)) {
			doc.addAllowed(CO.KEY_DELETE);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
