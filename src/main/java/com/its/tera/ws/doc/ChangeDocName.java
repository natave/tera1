package com.its.tera.ws.doc;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.its.tera.constants.CM;
import com.its.tera.util.Formatter;
import com.its.tera.util.QueryUtil;
import com.its.tera.ws.RootWebScript;

public class ChangeDocName extends RootWebScript{
	
	private static Log logger = LogFactory.getLog(ChangeDocName.class);

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		NodeService nodeService = serviceRegistry.getNodeService();
		SearchService searchService = serviceRegistry.getSearchService();
		
		
		
		String paramFrom = req.getParameter("from");
		String paramTo = req.getParameter("to");
		
		String change = req.getParameter("change");
		
		String namePattern = req.getParameter("namePattern");
		
		logger.debug("paramFrom = " + paramFrom + ", paramTo = " + paramTo + ", change = " + change);
		
		Date dateFrom;
		Date dateTo;
		try {
			dateFrom = Formatter.getDateFormat(paramFrom, Formatter.DATE_FORMAT);
			dateTo = Formatter.getDateFormat(paramTo, Formatter.DATE_FORMAT);
		} catch (ParseException e1) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e1.getMessage());
		}
		
		String query;
		try {
			query = "@cm\\:name:*"+ (namePattern == null ? "+" : namePattern) +"* AND TYPE:\"" + CM.QNAME_TYPE_CREDIT_DOC + "\" AND "+ QueryUtil.queryForDate("cm", "created", dateFrom, dateTo, 0) ;
		} catch (ParseException e) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
		}
		logger.debug( "name query = " + query);
		
		String size = "0";
		ResultSet resultSet = searchService.query(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE, SearchService.LANGUAGE_SOLR_ALFRESCO, query);
		if(resultSet == null || resultSet.getNodeRefs().isEmpty()) {
			logger.debug(" - Does not exists in alfresco!");
		}
		else {
			List<NodeRef> refs = resultSet.getNodeRefs();
			logger.debug("name refs size = " + refs.size());
			size = refs.size() + "";
			
			if(change.equalsIgnoreCase("yes")) {
				for (NodeRef nodeRef : refs) {
					
					String name = (String) nodeService.getProperty(nodeRef, ContentModel.PROP_NAME);
					logger.debug("name = " + name + " , NodRef = " + nodeRef.toString());
					nodeService.setProperty(nodeRef, ContentModel.PROP_NAME, name.replaceAll("\\+", ""));
				}
			}
			
		}
		
		
		
		
		
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", "ok " + size);
		return model;
	}
	
	
	

}
