package com.its.tera.ws.reports;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.content.MimetypeMap;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.MimetypeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.cmr.security.AuthenticationService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.HTMLServerImageHandler;
import org.eclipse.birt.report.engine.api.IPDFRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.IScalarParameterDefn;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.springframework.extensions.surf.util.Content;
import org.springframework.extensions.surf.util.I18NUtil;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.google.gson.JsonSyntaxException;
import com.its.tera.constants.CO;
import com.its.tera.constants.Path;
import com.its.tera.model.Document;
import com.its.tera.model.TechModel;
import com.its.tera.model.report.BRDesignModel;
import com.its.tera.model.report.BRParameterModel;
import com.its.tera.model.report.RunReportModel;
import com.its.tera.mybatis.service.DBServiceBean;
import com.its.tera.util.AppParameters;
import com.its.tera.util.FillModelsUtil;
import com.its.tera.util.Formatter;
import com.its.tera.util.NodeUtil;
import com.its.tera.util.ReportHelper;
import com.its.tera.ws.RootWebScript;

public class ReportRun extends RootWebScript{
	
	private static Log logger = LogFactory.getLog(ReportRun.class);
	
	NodeService nodeService;
	SearchService searchService;
	FileFolderService fileFolderService;
	ContentService contentService;
	AuthenticationService authenticationService;
	MimetypeService mimetypeService;
	
	BirtReportEngine birtReportingEngine;
	IReportEngine engine;
	AppParameters appParameters;
	DBServiceBean dbServiceBean;

	
	@SuppressWarnings("unchecked")
	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		nodeService = serviceRegistry.getNodeService();
		searchService = serviceRegistry.getSearchService();
		fileFolderService = serviceRegistry.getFileFolderService();
		contentService = serviceRegistry.getContentService();
		authenticationService = serviceRegistry.getAuthenticationService();
		mimetypeService = serviceRegistry.getMimetypeService();
		
		engine = birtReportingEngine.getEngine();
		
		String filter = req.getParameter("filter");
		String filters = req.getParameter("filters");
		
		logger.debug(" filter = " + filter+ " filters = " + filters);
		
		//Date currentDate = new Date();
		Long reportId = new Date().getTime();
		
		ResultSet resultSet = ReportHelper.searchForReportData(filter, filters, getGson(), serviceRegistry);
		
		List<NodeRef> docRefs = resultSet.getNodeRefs();
		List<Document> documentsList = new ArrayList<Document>();
		
		for (NodeRef nodeRef : docRefs) {
			TechModel m = FillModelsUtil.getDocModelFull(nodeRef, null, serviceRegistry, req.getServiceContextPath(), appParameters);
			m.setReportId(reportId.toString());
			m.setBrowsURL(m.getBrowseURL());
			
			m.setCreatedR(new Date(m.getCreated()));
			m.setEditedR(new Date(m.getEdited()));
			m.setRegDateR(new Date(m.getRegDate()));
			if(m.getDate() != null) {
				m.setDateR(new Date(m.getDate()));
			}
			if(m.getGagrDate() != null) {
				m.setGagrDateR(new Date(m.getGagrDate()));
			}
			
			documentsList.add(m);
		}
		logger.debug("end filling tempTable List");
		logger.debug("tempTable size = " + docRefs.size());
		dbServiceBean.insertTempDataListForReport(documentsList);
		logger.debug("end filling tempTable");
		
		
		
		//String nodeRef = reportModel.getNodeRef();
		NodeRef designNodeRef = NodeUtil.findNode(Path.QUERY_PATH_RPT_BIRT_REPORT_WITH_ALL_PARAMETERS, searchService, true); //new NodeRef(nodeRef);
		Integer resultType = BRDesignModel.RESULT_TYPE_XLSX; //reportModel.getResultType();
		Boolean paginate = true; //reportModel.getPaginate();
		
		
		List<BRParameterModel> p = getParameterModels(null/*reportModel.getParams()*/, designNodeRef);//reportModel.getParameters();
		Map<String, Serializable> params = new HashMap<String, Serializable>();
		if(p != null && !p.isEmpty()){
			for (BRParameterModel brParameterModel : p) {
				try {
					setParameterValue(brParameterModel, params);
				} catch (ParseException e) {
					throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
				}
			}logger.debug("report parameters = " + params);
		}
		
		
		String reportTitle = null;				
		if(params.containsKey(CO.BIRT_PARAMETER_REPORT_TITLE))
		{
			reportTitle = (String) params.get(CO.BIRT_PARAMETER_REPORT_TITLE);
			
			if(reportTitle == null || reportTitle == "")
			{
				reportTitle = (String) nodeService.getProperty(designNodeRef, ContentModel.PROP_DESCRIPTION);
				params.put(CO.BIRT_PARAMETER_REPORT_TITLE, reportTitle);
			}
		}
		else if(nodeService.getProperty(designNodeRef, ContentModel.PROP_DESCRIPTION) != null)
		{
			reportTitle = (String) nodeService.getProperty(designNodeRef, ContentModel.PROP_DESCRIPTION);
		}	
		System.out.println(" REPORT TITLE = " + reportTitle);
		
		if(params.keySet().contains(CO.BIRT_PARAMETER_REPORT_ID))
		{
			params.put(CO.BIRT_PARAMETER_REPORT_ID, reportId.toString());
		}				
		
		if(params.keySet().contains(CO.BIRT_PARAMETER_HOSTPAGE_URL))
		{
			params.put(CO.BIRT_PARAMETER_HOSTPAGE_URL, "http://localhost/alfresco/");
			System.out.println("hostPageBaseURL = " + params.get(CO.BIRT_PARAMETER_HOSTPAGE_URL));
		}
		
		NodeRef birtDesignResultsNodeRef = NodeUtil.findNode(Path.QUERY_PATH_RPT_BIRT_RESULTS_TEMP_SPACE, searchService, true);
		
		//String imageBaseUrl = "/report/images";
		//String imageDirectory = sc.getServletContext().getRealPath(imageBaseUrl);
		
		Document result = new Document();
		
		IRunAndRenderTask task = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try 
		{
			ContentReader reader = contentService.getReader(designNodeRef, ContentModel.PROP_CONTENT);
			inputStream = reader.getContentInputStream();
			
			IReportRunnable design = engine.openReportDesign(inputStream); 
			task = engine.createRunAndRenderTask(design); 
			task.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, ReportRun.class.getClassLoader()); 

			if(params != null)
			{
				task.setParameterValues(params);
				if(!task.validateParameters())
				{
					throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Parameter validation did not pass!");
				}
			}
			
			String fileName = reportId + ".";
			if(resultType == BRDesignModel.RESULT_TYPE_HTML)
			{
				fileName = fileName + "html";
			}
			else if(resultType == BRDesignModel.RESULT_TYPE_PDF)
			{
				fileName = fileName + "pdf";
			}
			else if(resultType == BRDesignModel.RESULT_TYPE_XLSX)
			{
				fileName = fileName + "xlsx";
			}
			
			logger.debug("fileName = " + fileName);
			/*QName contentQName = QName.createQName("{http://www.alfresco.org/model/content/1.0}content");
			NodeRef resultNodeRef = fileFolderService.create(birtDesignResultsNodeRef, fileName, contentQName).getNodeRef();*/
			Map<QName, Serializable> props = new HashMap<QName, Serializable>();
			props.put(ContentModel.PROP_NAME, fileName);
			NodeRef resultNodeRef = nodeService.createNode(birtDesignResultsNodeRef, ContentModel.ASSOC_CONTAINS, 
					QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, fileName), ContentModel.TYPE_CONTENT, props).getChildRef();
			
			logger.debug("resultNodeRef = " + resultNodeRef);
			
			ContentWriter writer = contentService.getWriter(resultNodeRef, ContentModel.PROP_CONTENT, true);
			writer.setEncoding("UTF-8");						
			logger.debug("writer");
			RenderOption renderOption = null;
			if(resultType == BRDesignModel.RESULT_TYPE_HTML)
			{
				writer.setMimetype(MimetypeMap.MIMETYPE_HTML);
				renderOption = new HTMLRenderOption();
				renderOption.setOutputFormat(HTMLRenderOption.OUTPUT_FORMAT_HTML);
				((HTMLRenderOption) renderOption).setHtmlPagination(paginate);
				((HTMLRenderOption) renderOption).setEmbeddable(false);
				//((HTMLRenderOption) renderOption).setImageDirectory(imageDirectory);
				//((HTMLRenderOption) renderOption).setBaseImageURL(/*req.getContextPath()*/getThreadLocalRequest().getContextPath() + imageBaseUrl);
				((HTMLRenderOption) renderOption).setImageHandler(new HTMLServerImageHandler());

			}
			else if(resultType == BRDesignModel.RESULT_TYPE_PDF)
			{					
				writer.setMimetype(MimetypeMap.MIMETYPE_PDF);
				renderOption = new PDFRenderOption();
				renderOption.setOutputFormat(HTMLRenderOption.OUTPUT_FORMAT_PDF);
				if(paginate)
				{
					((PDFRenderOption) renderOption).setOption(IPDFRenderOption.PAGE_OVERFLOW, IPDFRenderOption.OUTPUT_TO_MULTIPLE_PAGES);							
				}
			}
			else if(resultType == BRDesignModel.RESULT_TYPE_XLSX)
			{logger.debug("writer RESULT_TYPE_XLSX");
				writer.setMimetype(MimetypeMap.MIMETYPE_EXCEL);
//				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
//				writer.setMimetype("application/msexcel");
				renderOption = new EXCELRenderOption();
				renderOption.setOutputFormat("xlsx");logger.debug("renderOption OutputFormat");
				((EXCELRenderOption) renderOption).setEnableMultipleSheet(false/* paginate */);
			}
			logger.debug("writer setMimetype");
			outputStream = writer.getContentOutputStream();		
			renderOption.setOutputStream(outputStream);logger.debug("renderOption.setOutputStream");
			task.setRenderOption(renderOption);logger.debug("task.setRenderOption");
			task.run();logger.debug("task.run");
			
			//ModelData result = new BaseModelData();
			//Document result = new Document();
			String browseUrl = FillModelsUtil.getBrowseUrlWithName(req.getServiceContextPath(), appParameters.getHostPortInfo(), resultNodeRef, fileName, authenticationService.getCurrentTicket());
			logger.debug("browseUrl = " + browseUrl);
			if(!browseUrl.endsWith("xlsx")) {
				logger.debug("browseUrl + \"x\"");
				browseUrl = browseUrl + "x";
			}
			result.setBrowseURL(browseUrl);
			//result.setDownloadUrl(ModelFill.getDownloadUrl(fileName, browseUrl));
			result.setDocumentName(fileName);
			/*result.set(PN.BROWSE_URL, DownloadContentServlet.generateBrowserURL(resultNodeRef, fileName));
			result.set(PN.DOWNLOAD_URL, DownloadContentServlet.generateDownloadURL(resultNodeRef, fileName));
			result.set(PN.RESULT_NAME, fileName);*/
			//return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
		} 
		finally 
		{
			if(task != null)
			{
				task.close();	logger.debug("task.close()");
			}
			
			if(inputStream != null)
			{
				try {
					inputStream.close(); logger.debug("inputStream.close()");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(outputStream != null)
			{
				try {
					outputStream.close(); logger.debug("outputStream.close()");
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
			logger.debug("start delete TempData By ReportId");
			dbServiceBean.deleteTempDataByReportId(reportId.toString());
			logger.debug("end delete TempData By ReportId");
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(result, Document.class));
		return model;
	}
	
	
	
	private void setParameterValue(BRParameterModel brParameterModel, Map<String, Serializable> params) throws ParseException
	{
		String key = brParameterModel.getName();
		int type = brParameterModel.getDataType();		
		Object value = brParameterModel.getValue();
		
		switch (type) 
		{
			case IScalarParameterDefn.TYPE_DATE:
				SimpleDateFormat dateFormat = new SimpleDateFormat(Formatter.DATE_FORMAT3);
				if(key.equalsIgnoreCase(CO.REPORT_DATE_TO)){
					dateFormat = new SimpleDateFormat(Formatter.DATE_TIME_FORMAT3);
					params.put(key, dateFormat.parse((String) value + " 23:59"));
				}else{
					dateFormat = new SimpleDateFormat(Formatter.DATE_FORMAT3);
					params.put(key, dateFormat.parse((String) value));
				}				
				break;
				
			case IScalarParameterDefn.TYPE_DATE_TIME:
				if(key.equalsIgnoreCase(CO.REPORT_DATE_TO)){
					dateFormat = new SimpleDateFormat(Formatter.DATE_TIME_FORMAT3);
					params.put(key, dateFormat.parse((String) value + " 23:59"));
				}else{
					dateFormat = new SimpleDateFormat(Formatter.DATE_FORMAT3);
					params.put(key, dateFormat.parse((String) value));
				}
				break;

			case IScalarParameterDefn.TYPE_TIME:
				
				/*dateFormat = new SimpleDateFormat(BRParameterModel.TIME_FORMAT);
				result = dateFormat.parseObject(defValue);*/
				break;

			case IScalarParameterDefn.TYPE_BOOLEAN:
				//params.put(key, (Boolean) value);
				params.put(key, true);
				break;

			case IScalarParameterDefn.TYPE_INTEGER:
				params.put(key, (Integer) value);
				break;

			case IScalarParameterDefn.TYPE_FLOAT:
				params.put(key, (Float) value);
				break;
			
			case IScalarParameterDefn.TYPE_DECIMAL:
				params.put(key, (Float) value);
				break;
				
			default:
				params.put(key, (String) value);
				break;
		}
	}
	
	
	
	
	private List<BRParameterModel> getParameterModels(Map<String, Object> p, NodeRef designNodeRef){
		//List<BRParameterModel> paramsForRun = new ArrayList<BRParameterModel>();						
		List<BRParameterModel> params = ReportHelper.getParameters(designNodeRef, contentService, engine);
		/*Set<String> keySet = p.keySet();*/
//		for (BRParameterModel brParameterModel : params) {
//			brParameterModel.setValue(p.get(brParameterModel.getName()));			
//		}		
		return params;
	}
	
	
	

	public void setAppParameters(AppParameters appParameters) {
		this.appParameters = appParameters;
	}



	public void setDbServiceBean(DBServiceBean dbServiceBean) {
		this.dbServiceBean = dbServiceBean;
	}



	public void setBirtReportingEngine(BirtReportEngine birtReportingEngine) {
		this.birtReportingEngine = birtReportingEngine;
	}
	

}
