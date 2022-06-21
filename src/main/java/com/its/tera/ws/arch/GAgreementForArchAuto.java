package com.its.tera.ws.arch;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.repository.AssociationRef;
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

import com.its.tera.constants.CM;
import com.its.tera.services3.CreditLine;
import com.its.tera.services3.ITSResultCreditLine;
//import com.its.tera.services.GeneralContract;
//import com.its.tera.services.ITSResultGeneralContract;
//import com.its.tera.services.ITSResultLoan;
import com.its.tera.services3.ITSServiceSoapProxy;
//import com.its.tera.services.Loan;
import com.its.tera.util.AppParameters;
import com.its.tera.util.Formatter;
import com.its.tera.ws.RootWebScript;

public class GAgreementForArchAuto extends RootWebScript{
	
	private static Log logger = LogFactory.getLog(GAgreementForArchAuto.class);
	
	
	ITSServiceSoapProxy iTSServiceSoapProxy;
	AppParameters appParameters;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		SearchService searchService = serviceRegistry.getSearchService();
		NodeService nodeService = serviceRegistry.getNodeService();
		
		String paramFrom = req.getParameter("from");
		String paramTo = req.getParameter("to");
		
		logger.debug("paramFrom = " + paramFrom + ", paramTo = " + paramTo);
		
		Date dateFrom;
		Date dateTo;
		try {
			dateFrom = Formatter.getDateFormat(paramFrom, Formatter.DATE_FORMAT);
			dateTo = Formatter.getDateFormat(paramTo, Formatter.DATE_FORMAT);
		} catch (ParseException e1) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e1.getMessage());
		}
		
				
		Calendar from = Calendar.getInstance();
		from.setTime(Formatter.startOfDay(dateFrom));
		Calendar to = Calendar.getInstance();
		to.setTime(Formatter.endOfDay(dateTo));
		
		
		
		ITSResultCreditLine result = null;
		try{
			result = iTSServiceSoapProxy.getCreditLinesByEndDate(from, to, appParameters.getKsbAPIServiceSoapAddress());
		}catch(Exception e) {
			e.printStackTrace();
			throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
		Map<QName, Serializable> aspectProperties = new HashMap<QName, Serializable>();
		aspectProperties.put(CM.QNAME_PROP_ASPECT_ARCHIVE_USER, AuthenticationUtil.getFullyAuthenticatedUser()); //system
		
		CreditLine[] gAgreements = result.getCreditLines();
		logger.debug(" Sent general agreement List length = " + gAgreements.length);
		
		List<String> nodeRefsList = new ArrayList<String>();
		
		for (CreditLine gAgr : gAgreements) {			
			
			String query = "TYPE:\"" + CM.QNAME_TYPE_GENERAL_AGREEMENT_FOLDER + "\" AND @ksb-cont\\:gAgreementNumber:\"" 
					+ gAgr.getAGREEMENT_NO() + "\" AND @ksb-cont\\:gAgreementId:\"" 
					+ gAgr.getCREDIT_LINE_ID() + "\" AND @ksb-cont\\:clientCode:\"" + gAgr.getCLIENT_ID() + "\"";	
			logger.debug("query = " + query);
			
			ResultSet resultSet = searchService.query(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE, SearchService.LANGUAGE_SOLR_ALFRESCO, query);
			if(resultSet == null || resultSet.getNodeRefs().isEmpty()) {
				logger.debug(gAgr.getAGREEMENT_NO() + " - Does not exists in alfresco!");
			}
			else {
				NodeRef gAgreementRef = resultSet.getNodeRefs().get(0);
				nodeRefsList.add(gAgreementRef.toString());
				
				nodeService.addAspect(gAgreementRef, CM.QNAME_ASPECT_FOR_ARCHIVE, aspectProperties );
				
				//GENERAL'S CHILDREN
				List<AssociationRef> args = nodeService.getTargetAssocs(gAgreementRef, CM.QNAME_ASSOC_ASPECT_GEN_CHILD_AGRS);
				if(args != null) {
					for (AssociationRef agr : args) {
						NodeRef agrRef = agr.getTargetRef();
						if(!nodeService.hasAspect(agrRef, CM.QNAME_ASPECT_FOR_ARCHIVE)) {
							nodeService.addAspect(agrRef, CM.QNAME_ASPECT_FOR_ARCHIVE, aspectProperties );
							
						}
					}
				}
			}
			
			
		}
		
		logger.debug("agr ref size = " + nodeRefsList.size());
		logger.debug("agr refs = " + nodeRefsList);
		
		
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", "ok" + ", agr ref size = " + nodeRefsList.size() + ", agr refs = " + nodeRefsList);
		return model;
	}

	public void setiTSServiceSoapProxy(ITSServiceSoapProxy iTSServiceSoapProxy) {
		this.iTSServiceSoapProxy = iTSServiceSoapProxy;
	}

	public void setAppParameters(AppParameters appParameters) {
		this.appParameters = appParameters;
	}
	
	

}
