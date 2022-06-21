package com.its.tera.ws.doc;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentService;
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
import com.its.tera.util.FillModelsUtil;
import com.its.tera.util.Formatter;
import com.its.tera.util.QueryUtil;
import com.its.tera.ws.RootWebScript;

public class DocMimeType extends RootWebScript {
	
	private static Log logger = LogFactory.getLog(DocMimeType.class);

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		SearchService searchService = serviceRegistry.getSearchService();
		ContentService contentService = serviceRegistry.getContentService();
		NodeService nodeService = serviceRegistry.getNodeService();
		
		String paramFrom = req.getParameter("from");
		String paramTo = req.getParameter("to");
		
		String change = req.getParameter("change");
		
		logger.debug("paramFrom = " + paramFrom + ", paramTo = " + paramTo);
		
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
			query = "TYPE:\"" + CM.QNAME_TYPE_CREDIT_DOC + "\" AND "+ QueryUtil.queryForDate("cm", "created", dateFrom, dateTo, 0) ;
		} catch (ParseException e) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
		}
		logger.debug( "query = " + query);
//				+"@ksb-cont\\:agreementNumber:\"" 
//				+ loan.getAGREEMENT_NO() + "\" AND @ksb-cont\\:clientCode:\"" + loan.getCLIENT_ID() + "\"";	
		
		
		ResultSet resultSet = searchService.query(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE, SearchService.LANGUAGE_SOLR_ALFRESCO, query);
		if(resultSet == null || resultSet.getNodeRefs().isEmpty()) {
			logger.debug(" - Does not exists in alfresco!");
		}
		else {
			List<NodeRef> refs = resultSet.getNodeRefs();logger.debug("refs size = " + refs.size());
			int i = 0;
			int j = 0;
			
			for (NodeRef nodeRef : refs) {

				String docName = (String) nodeService.getProperty(nodeRef, ContentModel.PROP_NAME);
				
				if(nodeService.getProperty(nodeRef, ContentModel.PROP_CONTENT) != null) {
					ContentReader reader = contentService.getReader(nodeRef, ContentModel.PROP_CONTENT);
					String mimeType = reader.getMimetype();
					if(!mimeType.equalsIgnoreCase("application/pdf")) {
						i++;
						logger.debug("name = " + docName + ", mimeType = " + mimeType 
								+ ", clientCode = " + nodeService.getProperty(nodeRef, CM.QNAME_PROP_ASPECT_CLIENT_CODE));
						
						if(change.contentEquals("yes")) {
							if(!docName.endsWith(".rar") && (mimeType.equalsIgnoreCase("application/octet-stream") || mimeType.equalsIgnoreCase("application/x-rar-compressed"))) {
								nodeService.setProperty(nodeRef, ContentModel.PROP_NAME, docName + ".rar");logger.debug("newdocname = " + docName + ".rar");
								j++;
							}else if(!docName.endsWith(".zip") && mimeType.equalsIgnoreCase("application/x-zip-compressed")) {
								nodeService.setProperty(nodeRef, ContentModel.PROP_NAME, docName + ".zip");logger.debug("newdocname = " + docName + ".zip");
								j++;
							}else if(!docName.endsWith(".msg") && mimeType.equalsIgnoreCase("application/vnd.ms-outlook")) {
								nodeService.setProperty(nodeRef, ContentModel.PROP_NAME, docName + ".msg");logger.debug("newdocname = " + docName + ".msg");
								j++;
							}
						}
					}
				}
				else 
				{
					logger.debug("empty name = " + docName  
							+ ", clientCode = " + nodeService.getProperty(nodeRef, CM.QNAME_PROP_ASPECT_CLIENT_CODE));
				}
			}
			
			logger.debug(" i = " + i + ", j = " + j);
		
		}
		
		
		
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", "ok");
		return model;
	}
	
	

}
