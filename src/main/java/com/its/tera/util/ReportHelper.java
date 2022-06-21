package com.its.tera.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.alfresco.model.ContentModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.LimitBy;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IParameterDefnBase;
import org.eclipse.birt.report.engine.api.IParameterSelectionChoice;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IScalarParameterDefn;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.its.tera.constants.CM;
import com.its.tera.model.TechModel;
import com.its.tera.model.report.BRParameterModel;





public class ReportHelper {
	
	private static Log logger = LogFactory.getLog(ReportHelper.class);
	
	
	@SuppressWarnings("rawtypes")
	public static List<BRParameterModel> getParameters(NodeRef designNodeRef, ContentService contentService, IReportEngine engine) throws WebScriptException// EngineException, ParseException
	{
		List<BRParameterModel> result = new ArrayList<BRParameterModel>();
		InputStream inputStream = null;
		IGetParameterDefinitionTask task = null;
		try 
		{
			ContentReader reader = contentService.getReader(designNodeRef, ContentModel.PROP_CONTENT);
			inputStream = reader.getContentInputStream();
			IReportRunnable design = null;
			try {
				design = engine.openReportDesign(inputStream);
			} catch (EngineException e1) {
				e1.printStackTrace();
			}
			task = engine.createGetParameterDefinitionTask(design);
			Collection params =  task.getParameterDefns(true);
			Iterator iterator = params.iterator();
			while(iterator.hasNext())
			{
				IParameterDefnBase param = (IParameterDefnBase ) iterator.next();
				if(param instanceof IScalarParameterDefn)
				{
					BRParameterModel paramModel = new BRParameterModel();
					IScalarParameterDefn scalar = (IScalarParameterDefn) param;
					paramModel.setName(scalar.getName());
					paramModel.setPromptText(scalar.getPromptText());
					paramModel.setHelpText(scalar.getHelpText());
					paramModel.setRequired(scalar.isRequired());
					//paramModel.setEchoInput(scalar.isValueConcealed());
					paramModel.setHidden(scalar.isHidden());
					paramModel.setDataType(scalar.getDataType());
					paramModel.setDisplayType(scalar.getControlType());
					try {
						paramModel.setDefaultValue(getDefaultValue(scalar));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					paramModel.setSelectionListValues(getSelectionListValues(task, scalar.getName()));
					logger.debug("name = " + paramModel.getName() + ", dataType = " + paramModel.getDataType() + ", isRequired = " + scalar.isRequired());
					result.add(paramModel);
				}
			}
		} 
		finally
		{
			if(inputStream != null)
			{
				try 
				{ 
					inputStream.close();
				} 
				catch(IOException e) 
				{
					e.printStackTrace();
				}
			}
			if(task != null)
			{
				task.close();	
			}
		}
		
		return result;
	}
	
	
	
	private static Object getDefaultValue(IScalarParameterDefn scalar) throws ParseException
	{
		String defValue = scalar.getDefaultValue();
		if(defValue == null)
		{
			return null;
		}
		
		Object result = null;
		int type = scalar.getDataType();
		
		switch (type) 
		{
			case IScalarParameterDefn.TYPE_DATE:
				SimpleDateFormat dateFormat = new SimpleDateFormat(BRParameterModel.DATE_FORMAT);
				result = dateFormat.parseObject(defValue);
				break;
				
			case IScalarParameterDefn.TYPE_DATE_TIME:
				System.out.println("defValue = " + defValue);
				dateFormat = new SimpleDateFormat(BRParameterModel.DATE_TIME_FORMAT);
				result = dateFormat.parseObject(defValue);
				break;

			case IScalarParameterDefn.TYPE_TIME:
				dateFormat = new SimpleDateFormat(BRParameterModel.TIME_FORMAT);
				result = dateFormat.parseObject(defValue);
				break;

			case IScalarParameterDefn.TYPE_BOOLEAN:
				result = new Boolean(defValue);
				break;

			case IScalarParameterDefn.TYPE_INTEGER:
				result = new Integer(defValue);
				break;

			case IScalarParameterDefn.TYPE_FLOAT:
				result = new Float(defValue);
				break;
			
			case IScalarParameterDefn.TYPE_DECIMAL:
				result = new Float(defValue);
				break;
				
			default:
				result = new String(defValue);
				break;
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	private static List<Object> getSelectionListValues(IGetParameterDefinitionTask task, String paramName) 
	{
		List<Object> result = new ArrayList<Object>();
		Collection selectionList = task.getSelectionList(paramName);
		if(selectionList != null )
		{
			for(Iterator sliter = selectionList.iterator( ); sliter.hasNext( ); )
			{
				IParameterSelectionChoice selectionItem = (IParameterSelectionChoice) sliter.next( );
				Object value = selectionItem.getValue( );
				result.add(value);
			}		        
		}
		return result;
	}
	
	
	
	
	public static ResultSet searchForReportData(String filter, String filters, Gson gson, ServiceRegistry serviceRegistry) {
		
				
		
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
			try {
				docFilters = gson.fromJson(filters, TechModel.class);
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
			docFilters.setCreated(DateFormat.addDays(new Date(), -5).getTime());
			
			query = QueryUtil.composeQuery(docFilters, QueryUtil.AND_FOR_QUERY, serviceRegistry.getDictionaryService(), serviceRegistry.getNamespaceService(), false);
			
		}
		
		logger.debug("query = " + query);	
		
		ResultSet result = null;
		if(query != null){
			
			result = serviceRegistry.getSearchService().query(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE, SearchService.LANGUAGE_SOLR_ALFRESCO, query );
			logger.debug("result.length() = " + result.length());
		}
		
		
		
		return result;
		
	}
	
	
	
	
	
	

}
