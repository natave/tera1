package com.its.tera.ws.search;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.LimitBy;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.google.gson.JsonSyntaxException;
import com.its.tera.constants.CM;
import com.its.tera.model.Client;
import com.its.tera.model.TechModel;
import com.its.tera.model.util.ListResult;
import com.its.tera.util.AppParameters;
import com.its.tera.util.DateFormat;
import com.its.tera.util.QueryUtil;
import com.its.tera.ws.RootWebScript;

public class SearchClients extends RootWebScript{
	
	private static Log logger = LogFactory.getLog(SearchClients.class);
	
	AppParameters appParameters;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		SearchService searchService = serviceRegistry.getSearchService();	
		NodeService nodeService = serviceRegistry.getNodeService();
		
		logger.debug("FullyAuthenticatedUser = " + AuthenticationUtil.getFullyAuthenticatedUser());
		
		String filter = req.getParameter("filter");
		
		String start = req.getParameter("page");
		String limit = req.getParameter("size");
		String sort = req.getParameter("sort");
		String filters = req.getParameter("filters");
		

		if (start == null) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Requset mandatory parameter 'start' is missing! URL = '" + req.getURL() + "'");
		}
		if (limit == null) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Requset mandatory parameter 'limit' is missing! URL = '" + req.getURL() + "'");
		}
		
		
		logger.debug("start = " + start + "  limit = " + limit + "  sort = " + sort + " filter = " + filter+ " filters = " + filters);
		
		
		if(filters != null) {
			try {
				logger.debug("base 64 = " + new String(Base64.encodeBase64(filters.getBytes()), "UTF-8"));
			} catch (UnsupportedEncodingException e2) {
				e2.printStackTrace();
			}
		}
		
		
		
		TechModel clientFilters;
		String query = "";
		
		if(filters != null && !filters.isEmpty()) 
		{	
			logger.debug("1");
			try {
				clientFilters = getGson().fromJson(filters, TechModel.class);
				clientFilters.setType(CM.TYPE_CLIENT_FOLDER);if(clientFilters.getCreator() != null) {logger.debug(" creator = " + clientFilters.getCreator());}
			} catch (JsonSyntaxException e) {
				throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
			}
			query = QueryUtil.composeQuery(clientFilters, QueryUtil.AND_FOR_QUERY, serviceRegistry.getDictionaryService(), serviceRegistry.getNamespaceService(), false);
			
		}
		else if(filter != null && !filter.isEmpty()) 
		{
			logger.debug("2");
			clientFilters = new TechModel();
			clientFilters.setType(CM.TYPE_CLIENT_FOLDER);
			clientFilters.setClientId(filter);
			clientFilters.setClientCode(filter);
			
			query = QueryUtil.composeQuery1(clientFilters, QueryUtil.AND_FOR_QUERY, serviceRegistry.getDictionaryService(), serviceRegistry.getNamespaceService(), false);
		}
		else 
		{
			logger.debug("3");
			clientFilters = new TechModel(); 
			clientFilters.setType(CM.TYPE_CLIENT_FOLDER);
			clientFilters.setCreated(DateFormat.addDays(new Date(), -appParameters.getNumberOfDays()).getTime());
			
			query = QueryUtil.composeQuery(clientFilters, QueryUtil.AND_FOR_QUERY, serviceRegistry.getDictionaryService(), serviceRegistry.getNamespaceService(), false);
			
		}
		
		logger.debug("query = " + query);
		
		
		int pageSize = Integer.parseInt(limit);
		int startPageOriginal = Integer.parseInt(start);
		int startPageForSearchParameters = (startPageOriginal - 1) * pageSize;
		
		ResultSet result = null;
		if(query != null){
			SearchParameters searchParameters = new SearchParameters();
			searchParameters.setLimit(pageSize);
			searchParameters.setSkipCount(startPageForSearchParameters);
			searchParameters.setLimitBy(LimitBy.FINAL_SIZE);
			//searchParameters.addSort("@" + CM.QNAME_PROP_ASPECT_REGISTER_DATE.toString(), false);
			searchParameters.addSort("@" + ContentModel.PROP_CREATED.toString(), false);
			searchParameters.setLanguage(SearchService.LANGUAGE_SOLR_ALFRESCO);
			searchParameters.addStore(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE);
			searchParameters.setQuery(query);
			
			result = searchService.query(searchParameters );
		}
		
		int pageResultsSize = 0;
		Long totalCount = (long) 0;
		if(result != null) {
			pageResultsSize = result.length();
			totalCount = result.getNumberFound();
			logger.debug("totalCount = " + totalCount);
		}
		
		List<Client> clientstList = new ArrayList<Client>();
		
		if(pageResultsSize != 0)
		{
			List<NodeRef> refs = result.getNodeRefs();
			
			for (NodeRef clientRef : refs) {
				
				Client clientModel = new Client();
				
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
				
				clientstList.add(clientModel);
			}
			result.close();			
		}
		
				
		ListResult listResult = new ListResult();
		listResult.setPage(startPageOriginal);
		listResult.setSize(pageResultsSize);
		listResult.setTotalCount(totalCount);
		listResult.setData(clientstList);			
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(listResult));
		return model;
	}

	public void setAppParameters(AppParameters appParameters) {
		this.appParameters = appParameters;
	}
	
	

}
