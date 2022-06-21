package com.its.tera.ws.scan;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.google.gson.reflect.TypeToken;
import com.its.tera.constants.Path;
import com.its.tera.model.Agreement;
import com.its.tera.model.Document;
import com.its.tera.model.util.ListResult;
import com.its.tera.util.AppParameters;
import com.its.tera.util.FillModelsUtil;
import com.its.tera.ws.RootWebScript;

public class GetScannedPDFs extends RootWebScript{
	
	private static Log logger = LogFactory.getLog(GetScannedPDFs.class);
	
	private AppParameters appParameters;
	private NodeService nodeService;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		nodeService = serviceRegistry.getNodeService();
		
		String branch = req.getServiceMatch().getTemplateVars().get("branch");
		
		String query = MessageFormat.format(Path.QUERY_SCANS_BY_BRANCH, branch);
		List<Agreement> scannedsList = new ArrayList<Agreement>();
		
		logger.debug("query = " + query);
		
		
		ResultSet result = serviceRegistry.getSearchService().query(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE, SearchService.LANGUAGE_SOLR_ALFRESCO, query);	
		if(result != null && result.getNodeRefs().size() != 0)
		{				
			NodeRef folderRef = result.getNodeRefs().get(0);
			Set<QName> childNodeTypeQNames = new HashSet<QName>();
			childNodeTypeQNames.add(ContentModel.TYPE_CONTENT);
			for(ChildAssociationRef docChildRef : nodeService.getChildAssocs(folderRef))
			{		
				NodeRef nodeRef = docChildRef.getChildRef();
				fillScanenedsTree(nodeRef, null, scannedsList, req, appParameters.getHostPortInfo());					
			}					
		}
		else
		{
			
			if(serviceRegistry.getAuthorityService().isAdminAuthority(AuthenticationUtil.getFullyAuthenticatedUser()))
			{
				logger.debug("ScansPDF folder doesn't exists for - " + branch);
			}
			else
			{
				throw new WebScriptException("ScansPDF folder doesn't exists for - " + branch);
			}
		}
		
		for (Agreement resultSetRow : scannedsList) {
			
			if(resultSetRow.getChildren() != null && !resultSetRow.getChildren().isEmpty()) {
				List<Agreement> children = resultSetRow.getChildren();
				for (Agreement resultSetRow2 : children) {
					if(resultSetRow2 instanceof Document) {
						Document d = (Document) resultSetRow2;
						logger.debug("TEST doc name = " + d.getDocumentName() + ", size = " + d.getSizeString()+ ", browseURL = " + d.getBrowseURL());
					}else {
						logger.debug("TEST nodeRef = " + resultSetRow2.getNodeRef());
					}
				}
			}
			
		}
		
		
		ListResult listResult = new ListResult();
//		listResult.setPage(startPageOriginal);
//		listResult.setSize(pageResultsSize);
//		listResult.setTotalCount(totalCount);
		listResult.setData(scannedsList);	
		
		
//		Type typeOfSrc = new TypeToken<Collection<Agreement>>(){}.getType();
//		
//		Map<String, Object> model = new HashMap<String, Object>();
//		model.put("result", getGson().toJson(scannedsList, typeOfSrc));
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(listResult));
		logger.debug("json = " + getGson().toJson(listResult));
		return model;
	}
	
	
	
	
	
	private void fillScanenedsTree(final NodeRef nodeRef, final Agreement root, final List<Agreement> list, WebScriptRequest req, String hostPortInfo) throws WebScriptException 
	{					
		if(nodeService.getType(nodeRef).equals(ContentModel.TYPE_CONTENT))
		{						
			logger.debug("doc name = " + nodeService.getProperty(nodeRef, ContentModel.PROP_NAME));
			Document docTree = FillModelsUtil.fillScannedDocModel(nodeRef, nodeService, serviceRegistry, req, hostPortInfo);						
			if(root != null)
			{
				//docTree.setParentAgreement(root);
				logger.debug("doc name = " + docTree.getDocumentName() + ", size = " + docTree.getSizeString()+ ", browseURL = " + docTree.getBrowseURL());
				root.addChild(docTree);						
			}
			else
			{
				list.add(docTree);
			}
		}			
		else if(nodeService.getType(nodeRef).equals(ContentModel.TYPE_FOLDER))
		{
			logger.debug("f name = " + nodeService.getProperty(nodeRef, ContentModel.PROP_NAME));
			Agreement tree = FillModelsUtil.fillScannedFolder(nodeRef, nodeService);						
			if(root != null)
			{
				//tree.setParentAgreement(root);
//				root.addChild(tree);
			}
			else
			{
//				list.add(tree);
			}
			
			for(ChildAssociationRef child : nodeService.getChildAssocs(nodeRef))
			{
				if(child.getChildRef() != nodeRef)
				{
					logger.debug("child name = " + nodeService.getProperty(child.getChildRef(), ContentModel.PROP_NAME));
					fillScanenedsTree(child.getChildRef(), null/*tree*/,list /*null*/, req, hostPortInfo);
				}
			}					
		}
	}





	public void setAppParameters(AppParameters appParameters) {
		this.appParameters = appParameters;
	}
	
	
	
	
	
	
	
	

}
