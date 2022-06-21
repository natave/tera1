package com.its.tera.ws.arch;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
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
import com.its.tera.services3.ITSResultLoan;
import com.its.tera.services3.ITSServiceSoapProxy;
import com.its.tera.services3.Loan;
import com.its.tera.util.AppParameters;
import com.its.tera.util.Formatter;
import com.its.tera.ws.RootWebScript;

public class LoansForArchAuto extends RootWebScript{
	
	private static Log logger = LogFactory.getLog(LoansForArchAuto.class);
	
	
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
		
		
		ITSResultLoan result = null;
		try{
			result = iTSServiceSoapProxy.getLoanByCloseDate(from, to, appParameters.getKsbAPIServiceSoapAddress());
		}catch(Exception e) {
			e.printStackTrace();
			throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
		Map<QName, Serializable> aspectProperties = new HashMap<QName, Serializable>();
		aspectProperties.put(CM.QNAME_PROP_ASPECT_ARCHIVE_USER, AuthenticationUtil.getFullyAuthenticatedUser()); //system
		
		Loan[] agreements = result.getLoans();
		logger.debug(" Sent Loans List length = " + agreements.length);
		
		List<String> nodeRefsList = new ArrayList<String>();
		
		for (Loan loan : agreements) {
			
			if(loan.getCREDIT_LINE_ID()!= null) {
				logger.debug("agr number = " + loan.getAGREEMENT_NO() + ", gen CREDIT_LINE_ID =" + loan.getCREDIT_LINE_ID());
				continue;
			}
			else
			{
				
				String query = "TYPE:\"" + CM.QNAME_TYPE_AGREEMENT_FOLDER + "\" AND @ksb-cont\\:agreementNumber:\"" 
						+ loan.getAGREEMENT_NO()+ "\" AND @ksb-cont\\:agreementId:\"" 
						+ loan.getLOAN_ID() + "\" AND @ksb-cont\\:clientCode:\"" + loan.getCLIENT_ID() + "\"";	
				logger.debug("query = " + query);
				
				ResultSet resultSet = searchService.query(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE, SearchService.LANGUAGE_SOLR_ALFRESCO, query);
				if(resultSet == null || resultSet.getNodeRefs().isEmpty()) {
					logger.debug(loan.getAGREEMENT_NO() + " - Does not exists in alfresco!");
				}
				else {
					NodeRef agreementRef = resultSet.getNodeRefs().get(0);
					nodeRefsList.add(agreementRef.toString());
					nodeService.addAspect(agreementRef, CM.QNAME_ASPECT_FOR_ARCHIVE, aspectProperties );
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
