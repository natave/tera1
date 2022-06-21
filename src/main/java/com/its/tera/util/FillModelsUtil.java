package com.its.tera.util;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.alfresco.model.ContentModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.util.URLEncoder;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.its.tera.constants.CM;
import com.its.tera.constants.CO;
import com.its.tera.model.Agreement;
import com.its.tera.model.ArchiveHistory;
import com.its.tera.model.Document;
import com.its.tera.model.TechModel;

public class FillModelsUtil {
	
	private static final Log logger = LogFactory.getLog(FillModelsUtil.class);
	
	
	public static Agreement getAgreementModel(NodeRef agrRef, NodeRef clientRef, NodeService nodeService)
	{
		Agreement model = new Agreement();
		
		if(clientRef != null)
		{
			model.setClientRef(clientRef.toString());
		}
		model.setNodeRef(agrRef.toString());				
		
		model.setClientCode((String) nodeService.getProperty(agrRef, CM.QNAME_PROP_ASPECT_CLIENT_CODE));
		model.setClientName((String) nodeService.getProperty(agrRef, CM.QNAME_PROP_ASPECT_CLIENT_NAME));
		model.setClientId((String) nodeService.getProperty(agrRef, CM.QNAME_PROP_ASPECT_CLIENT_ID));
		
		model.setCreated(((Date) nodeService.getProperty(agrRef, ContentModel.PROP_CREATED)).getTime());
		model.setCreator((String) nodeService.getProperty(agrRef, ContentModel.PROP_CREATOR));	
		
		model.setEdited(((Date) nodeService.getProperty(agrRef, ContentModel.PROP_MODIFIED)).getTime());
		model.setEditor((String) nodeService.getProperty(agrRef, ContentModel.PROP_MODIFIER));	
		
		//ORDINARY FOLDER (OLD VERSION)
		
		if(nodeService.getType(agrRef).equals(CM.QNAME_TYPE_ORDINARY_FOLDER))
		{
			model.setType(CM.TYPE_ORDINARY_FOLDER);
			model.setAgrType(CM.TYPE_ORDINARY_FOLDER);
			model.setSpaceName((String) nodeService.getProperty(agrRef, ContentModel.PROP_NAME));
			model.setFolderDesc((String) nodeService.getProperty(agrRef, ContentModel.PROP_DESCRIPTION));
		}
		
		//LEGAL DOCS FOLDER
		else if(nodeService.getType(agrRef).equals(CM.QNAME_TYPE_LEGAL_DOC_FOLDER))
		{
			model.setType(CM.TYPE_LEGAL_DOC_FOLDER);
			model.setAgrType(CM.TYPE_LEGAL_DOC_FOLDER);
			model.setSpaceName((String) nodeService.getProperty(agrRef, ContentModel.PROP_NAME));
			model.setFolderDesc((String) nodeService.getProperty(agrRef, ContentModel.PROP_DESCRIPTION));
		}
		else
		{
			if(nodeService.getProperty(agrRef, CM.QNAME_PROP_ASPECT_GEN_AGR_NUM) != null)
			{
				model.setGagrId((String) nodeService.getProperty(agrRef, CM.QNAME_PROP_ASPECT_GEN_AGR_ID));
				model.setGagrNumber((String) nodeService.getProperty(agrRef, CM.QNAME_PROP_ASPECT_GEN_AGR_NUM));
				model.setGagrAmount((String) nodeService.getProperty(agrRef, CM.QNAME_PROP_ASPECT_GEN_AGR_AMOUNT));
				model.setGagrDate(((Date) nodeService.getProperty(agrRef, CM.QNAME_PROP_ASPECT_GEN_AGR_DATE)).getTime());
				model.setGagrCurrency((String) nodeService.getProperty(agrRef, CM.QNAME_PROP_ASPECT_GEN_AGR_CURRENCY));
			}
			
			//AGREEMENT FOLDER
			if(nodeService.getType(agrRef).equals(CM.QNAME_TYPE_AGREEMENT_FOLDER))
			{
				model.setType(CM.TYPE_AGREEMENT_FOLDER);
				model.setAgrType(CM.TYPE_AGREEMENT_FOLDER);
				model.setAgrId((String) nodeService.getProperty(agrRef, CM.QNAME_PROP_ASPECT_AGR_ID));
				model.setAgrNumber((String) nodeService.getProperty(agrRef, CM.QNAME_PROP_ASPECT_AGR_NUM));
				model.setAmount((String) nodeService.getProperty(agrRef, CM.QNAME_PROP_ASPECT_AGR_AMOUNT));
				model.setDate(((Date) nodeService.getProperty(agrRef, CM.QNAME_PROP_ASPECT_AGR_DATE)).getTime());
				model.setCurrency((String) nodeService.getProperty(agrRef, CM.QNAME_PROP_ASPECT_AGR_CURRENCY));
				model.setProductType((String) nodeService.getProperty(agrRef, CM.QNAME_PROP_ASPECT_AGR_PRODUCT));
			}
			
			//GENERAL AGREEMENT FOLDER
			else
			{
				model.setType(CM.TYPE_GENERAL_AGREEMENT_FOLDER);
				model.setAgrType(CM.TYPE_GENERAL_AGREEMENT_FOLDER);
			}
			
			if(nodeService.hasAspect(agrRef, CM.QNAME_ASPECT_FOR_ARCHIVE)) {
				model.setArchUser((String) nodeService.getProperty(agrRef, CM.QNAME_PROP_ASPECT_ARCHIVE_USER));
			}
			
			if(nodeService.hasAspect(agrRef, CM.QNAME_ASPECT_FOR_LIVE)) {
				model.setLiveUser((String) nodeService.getProperty(agrRef, CM.QNAME_PROP_ASPECT_LIVE_USER));
			}
			
			if(nodeService.hasAspect(agrRef, CM.QNAME_ASPECT_FOR_ARCHIVE)) {
				model.addAllowed(CO.KEY_FOR_ARCHIVE);
			}
			
			if(nodeService.hasAspect(agrRef, CM.QNAME_ASPECT_ARCHIVE_HISTORY)) {
				if(nodeService.getProperty(agrRef, CM.QNAME_ASPECT_ARCHIVE_HISTORY) != null) {
					List<ArchiveHistory> archHistory = new ArrayList<ArchiveHistory>();
					
					List<String> archHistoryList = (List<String>) nodeService.getProperty(agrRef, CM.QNAME_ASPECT_ARCHIVE_HISTORY);
					for (String string : archHistoryList) {
						String[] s = string.split("#");
						ArchiveHistory historyModel = new ArchiveHistory();
						historyModel.setStatus(s[0]);
						historyModel.setUser(s[1]);
						historyModel.setDate(Long.parseLong(s[2]));
						//historyModel.setComment(s[3]);
						archHistory.add(historyModel);
					}	
					model.setArchHistory(archHistory);
					model.setArchHistoryString(archHistoryList);
					if(!nodeService.hasAspect(agrRef, CM.QNAME_ASPECT_FOR_ARCHIVE)) {
						model.addAllowed(CO.KEY_WAS_ARCHIVE);
					}
				}
				
				model.setHistoryDate(((Date) nodeService.getProperty(agrRef, CM.QNAME_PROP_ASPECT_HISTORY_DATE)).getTime());
			}
		}
		
		model.setBranch((String) nodeService.getProperty(agrRef, CM.QNAME_PROP_ASPECT_BRANCH));
		model.setClientIsVIP((Boolean) nodeService.getProperty(agrRef, CM.QNAME_ASPECT_CLIENT_IS_VIP));
		
		if(nodeService.getProperty(agrRef, CM.QNAME_PROP_ASPECT_REGISTER_DATE) != null) {
			model.setRegDate(((Date) nodeService.getProperty(agrRef, CM.QNAME_PROP_ASPECT_REGISTER_DATE)).getTime());
		}
		
		model.setCreatorUser((String) nodeService.getProperty(agrRef, CM.QNAME_PROP_ASPECT_CREATOR_USER));
		model.setName((String) nodeService.getProperty(agrRef, ContentModel.PROP_NAME));
		
		if(nodeService.getType(agrRef).equals(CM.QNAME_TYPE_ORDINARY_FOLDER) || nodeService.getType(agrRef).equals(CM.QNAME_TYPE_LEGAL_DOC_FOLDER)) {
			model.setRegDate(((Date) nodeService.getProperty(agrRef, ContentModel.PROP_CREATED)).getTime());
			model.setCreatorUser((String) nodeService.getProperty(agrRef, ContentModel.PROP_CREATOR));
		}
		
		
		
		return model;
	}
	
	
	
	
	public static void setArchiveHistory(NodeRef nodeRef, String status, String user, Date date, String comment, NodeService nodeService) {
		
		if(status != null) {
			String parameterValue = status + "#" + user + "#" + date.getTime() + "#" + comment;
			List<String> archHistoryList = new ArrayList<String>();
			if(nodeService.getProperty(nodeRef, CM.QNAME_ASPECT_ARCHIVE_HISTORY) != null){
				archHistoryList.addAll((List<String>) nodeService.getProperty(nodeRef, CM.QNAME_ASPECT_ARCHIVE_HISTORY));
			}
			
			archHistoryList.add(parameterValue);
			nodeService.setProperty(nodeRef, CM.QNAME_ASPECT_ARCHIVE_HISTORY, (Serializable) archHistoryList);
		}		
		nodeService.setProperty(nodeRef, CM.QNAME_PROP_ASPECT_HISTORY_DATE, date);
		
	}
	
	
	
	
	public static TechModel getDocModelFull(NodeRef nodeRef, NodeRef clientRef,
			/* Boolean isMulti, */ ServiceRegistry sr, String serviceContextPath, AppParameters appParameters/*, VersionService versionService*/)
	{
		
		NodeService nodeService = sr.getNodeService();
		
		TechModel docModel = new TechModel();
		
		docModel.setNodeRef(nodeRef.toString());
		if(clientRef != null)
		{
			docModel.setClientRef(clientRef.toString());
		}		
		String docName = (String) nodeService.getProperty(nodeRef, ContentModel.PROP_NAME);
		docModel.setDocumentName(docName);
		docModel.setBrowseURL(getBrowseUrlWithName(serviceContextPath, appParameters.getHostPortInfo(), nodeRef, 
				docName, sr.getAuthenticationService().getCurrentTicket()));
		//docModel.setDownloadURL(DownloadContentServlet.generateDownloadURL(nodeRef, docName));
		
		//docModel.set(PN.ICON_PATH, FileTypeImageUtils.getFileTypeImage(sc, docName, true));
		//fileFolderService.getFileInfo(nodeRef).getContentData().getSize();
		
		docModel.setRegNumber((String) nodeService.getProperty(nodeRef, CM.QNAME_PROP_REG_NUMBER));
		docModel.setClientCode((String) nodeService.getProperty(nodeRef, CM.QNAME_PROP_ASPECT_CLIENT_CODE));
		docModel.setClientName((String) nodeService.getProperty(nodeRef, CM.QNAME_PROP_ASPECT_CLIENT_NAME));
		docModel.setClientId((String) nodeService.getProperty(nodeRef, CM.QNAME_PROP_ASPECT_CLIENT_ID));
		
		docModel.setIsEmpty(nodeService.hasAspect(nodeRef, CM.QNAME_ASPECT_ITEM_IS_EMPTY));		
		
		docModel.setCreated(((Date) nodeService.getProperty(nodeRef, ContentModel.PROP_CREATED)).getTime());
		docModel.setCreator((String) nodeService.getProperty(nodeRef, ContentModel.PROP_CREATOR));
		
		
		docModel.setRegDate(((Date) nodeService.getProperty(nodeRef, CM.QNAME_PROP_ASPECT_REGISTER_DATE)).getTime());
		docModel.setCreatorUser((String) nodeService.getProperty(nodeRef, CM.QNAME_PROP_ASPECT_CREATOR_USER));
		docModel.setEdited(((Date) nodeService.getProperty(nodeRef, ContentModel.PROP_MODIFIED)).getTime());
		docModel.setEditor((String) nodeService.getProperty(nodeRef, ContentModel.PROP_MODIFIER));
		
		
				
		if(nodeService.getProperty(nodeRef, CM.QNAME_PROP_CORRESPONDENCE_TYPE) != null)
		{
			docModel.setCorrType((String) nodeService.getProperty(nodeRef, CM.QNAME_PROP_CORRESPONDENCE_TYPE));
			docModel.setDescription((String) nodeService.getProperty(nodeRef, CM.QNAME_PROP_DESCRIPTION));
			//docModel.setVIP((Boolean) nodeService.getProperty(nodeRef, CM.QNAME_PROP_IS_VIP));
		}
						
		
		if(nodeService.getProperty(nodeRef, CM.QNAME_PROP_ASPECT_GEN_AGR_NUM) != null)
		{
			docModel.setGagrId((String) nodeService.getProperty(nodeRef, CM.QNAME_PROP_ASPECT_GEN_AGR_ID));
			docModel.setGagrNumber((String) nodeService.getProperty(nodeRef, CM.QNAME_PROP_ASPECT_GEN_AGR_NUM));
			docModel.setGagrAmount((String) nodeService.getProperty(nodeRef, CM.QNAME_PROP_ASPECT_GEN_AGR_AMOUNT));
			docModel.setGagrCurrency((String) nodeService.getProperty(nodeRef, CM.QNAME_PROP_ASPECT_GEN_AGR_CURRENCY));
			docModel.setGagrDate(((Date) nodeService.getProperty(nodeRef, CM.QNAME_PROP_ASPECT_GEN_AGR_DATE)).getTime());
		}
		
		if(nodeService.getProperty(nodeRef, CM.QNAME_PROP_ASPECT_AGR_NUM) != null)
		{
			docModel.setAgrId((String) nodeService.getProperty(nodeRef, CM.QNAME_PROP_ASPECT_AGR_ID));
			docModel.setAgrNumber((String) nodeService.getProperty(nodeRef, CM.QNAME_PROP_ASPECT_AGR_NUM));
			docModel.setAmount((String) nodeService.getProperty(nodeRef, CM.QNAME_PROP_ASPECT_AGR_AMOUNT));
			docModel.setCurrency((String) nodeService.getProperty(nodeRef, CM.QNAME_PROP_ASPECT_AGR_CURRENCY));
			docModel.setDate(((Date) nodeService.getProperty(nodeRef, CM.QNAME_PROP_ASPECT_AGR_DATE)).getTime());
			docModel.setProductType((String) nodeService.getProperty(nodeRef, CM.QNAME_PROP_ASPECT_AGR_PRODUCT));			
		}	
		docModel.setBranch((String) nodeService.getProperty(nodeRef, CM.QNAME_PROP_ASPECT_BRANCH));
		docModel.setVIP((Boolean) nodeService.getProperty(nodeRef, CM.QNAME_PROP_IS_VIP));
		
		if(nodeService.getProperty(nodeRef, ContentModel.PROP_CONTENT) == null)
		{
			docModel.setSize((long) 0);
			docModel.setSizeString(readableFileSize(0));
		}
		else
		{
			long size = sr.getFileFolderService().getFileInfo(nodeRef).getContentData().getSize();
			docModel.setSize(size);
			docModel.setSizeString(readableFileSize(size));
		}
		
		
		
		
		
		// WHAT IS MEANING OF AgreementType ????????????????????????
		ChildAssociationRef pp = nodeService.getPrimaryParent(nodeRef);
		//NodeRef ppRef = pp.getChildRef();
		NodeRef pparRef = pp.getParentRef();
		
		/*if(ppRef != null && nodeService.getType(ppRef).equals(CM.QNAME_TYPE_LEGAL_DOC_FOLDER)){
			logger.debug("parent node ref = " + ppRef);
			docModel.setAgreementType(Document.DOC);
		}*/
		
		if(pparRef != null && nodeService.getType(pparRef).equals(CM.QNAME_TYPE_LEGAL_DOC_FOLDER)){
			logger.debug("parent node pparRef = " + pparRef);
			docModel.setAgreementType(Document.DOC);
		}
		
		
		return docModel;
	}
	
	
	
	
	
	
	
	
	
	public static String getBrowseUrlWithName(String serviceContextPath, String hostPortInfo, NodeRef nodeRef, String docName, String ticket){
		String dName = URLEncoder.encode(docName);
		String browseUrl = MessageFormat.format(CO.URL_PATTERN,
				new Object[] { nodeRef.getStoreRef().getProtocol(), nodeRef.getStoreRef().getIdentifier(), nodeRef.getId() });
		browseUrl = /*req.getServerPath()*/hostPortInfo + serviceContextPath + browseUrl;
		browseUrl = browseUrl + "/" + dName + "?alf_ticket=" + ticket;
		return browseUrl;
	}
	
	
	public static Document fillScannedDocModel(NodeRef docRef, NodeService nodeService, ServiceRegistry sr, WebScriptRequest req, String hostPortInfo)
	{
		Document d = new Document();
		//d.setType(CM.TYPE_SCANNED_DOC);
		d.setNodeRef(docRef.toString());
		d.setCreated(((Date) nodeService.getProperty(docRef, ContentModel.PROP_CREATED)).getTime());
		d.setCreator((String) nodeService.getProperty(docRef, ContentModel.PROP_CREATOR));
		String docName = (String) nodeService.getProperty(docRef, ContentModel.PROP_NAME);
		d.setDocumentName(docName);
		logger.debug("doc ref = " +  docRef);
		d.setBrowseURL(getBrowseUrlWithName(req.getServiceContextPath(), hostPortInfo, docRef, docName, sr.getAuthenticationService().getCurrentTicket()));
		//d.setDownloadURL(DownloadContentServlet.generateDownloadURL(docRef, docName));
		if(nodeService.getProperty(docRef, ContentModel.PROP_CONTENT) == null)
		{
        	d.setSize((long) 0);
        	d.setSizeString(readableFileSize(0));
		}
		else
		{
			long size = sr.getFileFolderService().getFileInfo(docRef).getContentData().getSize();
			d.setSize(size);
			d.setSizeString(readableFileSize(size));
		}			
		return d;
	}
	
	
	public static Agreement fillScannedFolder(NodeRef nodeRef, NodeService nodeService)
	{
		Agreement a = new Agreement();
		//a.setType(PN.TYPE_SCANNED_SPACE);
		a.setNodeRef(nodeRef.toString()); logger.debug("nodeRef = " +  nodeRef);
		a.setCreated(((Date) nodeService.getProperty(nodeRef, ContentModel.PROP_CREATED)).getTime());
		a.setCreator((String) nodeService.getProperty(nodeRef, ContentModel.PROP_CREATOR));
		String spaceName = (String) nodeService.getProperty(nodeRef, ContentModel.PROP_NAME);
		a.setSpaceName(spaceName);
		
		return a;
	}
	
	
	
	
	
	
	public static String readableFileSize(long size) 
	{
	    if(size <= 0) return "0";
	    final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
	    int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
	    return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}
	
	
	
	

}
