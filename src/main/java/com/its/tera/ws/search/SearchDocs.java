package com.its.tera.ws.search;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.repository.NodeRef;
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
import com.google.gson.reflect.TypeToken;
import com.its.tera.constants.CM;
import com.its.tera.model.Document;
import com.its.tera.model.TechModel;
import com.its.tera.model.util.ListResult;
import com.its.tera.util.AppParameters;
import com.its.tera.util.DateFormat;
import com.its.tera.util.FillModelsUtil;
import com.its.tera.util.QueryUtil;
import com.its.tera.ws.RootWebScript;

public class SearchDocs extends RootWebScript {
	
	private static Log logger = LogFactory.getLog(SearchDocs.class);
	
	AppParameters appParameters;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		SearchService searchService = serviceRegistry.getSearchService();
		
		logger.debug("FullyAuthenticatedUser = " + AuthenticationUtil.getFullyAuthenticatedUser());
		
		String filter = req.getParameter("filter");
		
		String start = req.getParameter("page");
		String limit = req.getParameter("size");
		String sort = req.getParameter("sort");
		String filters = req.getParameter("filters");

		
//		if (filter == null) {
//			throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Requset mandatory parameter 'type' is missing! URL = '" + req.getURL() + "'");
//		}
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
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		
		
		TechModel docFilters;
		String query = "";
		
		if(filters != null && !filters.isEmpty()) 
		{	
			logger.debug("1");
//			String filterJson = null;
//			byte[] filterBytes = Base64.decodeBase64(filters);
//			
//			try {
//				filterJson = new String(filterBytes, "UTF-8");
//				
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//				throw new WebScriptException(Status.STATUS_UNSUPPORTED_MEDIA_TYPE, e.getMessage());
//			}
			
			
			
//			String filterJson;
//			try {
//				filterJson = URLEncoder.encode(filters, "utf-8");
//			} catch (UnsupportedEncodingException e1) {				
//				throw new WebScriptException(Status.STATUS_UNSUPPORTED_MEDIA_TYPE, e1.getMessage());
//			}
			
			
			
//			Type typeOfSrc = new TypeToken<Collection<Document>>(){}.getType();
//			List<Document> filters = null;
			try {
				docFilters = getGson().fromJson(filters, TechModel.class);
				docFilters.setType(CM.TYPE_CREDIT_DOC);if(docFilters.getCreator() != null) {logger.debug(" creator = " + docFilters.getCreator());}
			} catch (JsonSyntaxException e) {
				throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
			}
			query = QueryUtil.composeQuery(docFilters, QueryUtil.AND_FOR_QUERY, serviceRegistry.getDictionaryService(), serviceRegistry.getNamespaceService(), false);
			
		}
		else if(filter != null && !filter.isEmpty()) 
		{
			logger.debug("2");
			docFilters = new TechModel();
			docFilters.setType(CM.TYPE_CREDIT_DOC);
			docFilters.setRegNumber(filter);
			docFilters.setGagrNumber(filter);
			docFilters.setAgrNumber(filter);
			docFilters.setClientCode(filter);
			
			query = QueryUtil.composeQuery1(docFilters, QueryUtil.AND_FOR_QUERY, serviceRegistry.getDictionaryService(), serviceRegistry.getNamespaceService(), false);
		}
		else 
		{
			logger.debug("3");
			docFilters = new TechModel(); 
			docFilters.setType(CM.TYPE_CREDIT_DOC);
			docFilters.setCreated(DateFormat.addDays(new Date(), -appParameters.getNumberOfDays()).getTime());
			
			query = QueryUtil.composeQuery(docFilters, QueryUtil.AND_FOR_QUERY, serviceRegistry.getDictionaryService(), serviceRegistry.getNamespaceService(), false);
			
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
			searchParameters.addSort("@" + CM.QNAME_PROP_ASPECT_REGISTER_DATE.toString(), false);
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
		
		List<Document> documentsList = new ArrayList<Document>();
		
		if(pageResultsSize != 0)
		{
			List<NodeRef> refs = result.getNodeRefs();
			
			for (NodeRef nodeRef : refs) {
				documentsList.add(FillModelsUtil.getDocModelFull(nodeRef, null, serviceRegistry, req.getServiceContextPath(), appParameters));
			}
			result.close();
			
		}
		
		
		
		
		ListResult listResult = new ListResult();
		listResult.setPage(startPageOriginal);
		listResult.setSize(pageResultsSize);
		listResult.setTotalCount(totalCount);
		listResult.setData(documentsList);			
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(listResult));
		return model;
	}

	public void setAppParameters(AppParameters appParameters) {
		this.appParameters = appParameters;
	}
	
	
	

}
