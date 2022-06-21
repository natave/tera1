package com.its.tera.ws;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.repository.AssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.its.tera.constants.CM;
import com.its.tera.model.DataModel;

public class DeleteNode extends RootWebScript{
	
	private static Log logger = LogFactory.getLog(DeleteNode.class);

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		NodeService nodeService = serviceRegistry.getNodeService();
		
		//String ref = req.getServiceMatch().getTemplateVars().get("ref");
		String ref = req.getParameter("nodeRef");
		String cref = req.getParameter("clientRef");
		NodeRef nodeRef = new NodeRef(ref);
		
		logger.debug("nodeRef = " + ref + ", clientRef = " + cref);
		
		
		if(nodeRef == null || !nodeService.exists(nodeRef))		
		{
			throw new WebScriptException("Node doesn't exists!");
		}
		
		//Boolean isFolder = serviceRegistry.getFileFolderService().getFileInfo(nodeRef).isFolder();
		if(serviceRegistry.getFileFolderService().getFileInfo(nodeRef).isFolder()) {
			if(cref != null && nodeService.exists(new NodeRef(cref))) {
				NodeRef clientRef = new NodeRef(cref);
				QName type = nodeService.getType(nodeRef);
				QName assocType;
				if(type.equals(CM.QNAME_TYPE_GENERAL_AGREEMENT_FOLDER)) {
					assocType = CM.QNAME_ASSOC_ASPECT_GEN_AGRS;
				}else if(type.equals(CM.QNAME_TYPE_AGREEMENT_FOLDER)) {
					assocType = CM.QNAME_ASSOC_ASPECT_AGRS;
				}else {
					assocType = CM.QNAME_ASSOC_ASPECT_LEGALS;
				}
				logger.debug("assocType = " + assocType);
				List<AssociationRef> assocs = nodeService.getSourceAssocs(nodeRef, assocType);
				if(assocs != null && ! assocs.isEmpty()) {
					AssociationRef assoc = assocs.get(0);
					if(assoc != null && assoc.getSourceRef().equals(clientRef)) {
						nodeService.deleteNode(nodeRef);//nodeService.asso
						List<AssociationRef> generals = nodeService.getTargetAssocs(clientRef, CM.QNAME_ASSOC_ASPECT_GEN_AGRS);
						List<AssociationRef> ordinalr = nodeService.getTargetAssocs(clientRef, CM.QNAME_ASSOC_ASPECT_AGRS);
						List<AssociationRef> legals = nodeService.getTargetAssocs(clientRef, CM.QNAME_ASSOC_ASPECT_LEGALS);
						if(nodeService.getChildAssocs(clientRef).isEmpty() && !nodeService.hasAspect(clientRef, CM.QNAME_ASPECT_ITEM_IS_EMPTY)
								&& (generals == null || generals.size() == 0) && (ordinalr == null || ordinalr.size() == 0)
								&& (legals == null || legals.size() == 0))
						{
							nodeService.addAspect(clientRef, CM.QNAME_ASPECT_ITEM_IS_EMPTY, null);
						}
					}else {
						throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Client folder is not valid!");
					}	
				}
				else {
					List<AssociationRef> assocsForGenChild = nodeService.getSourceAssocs(nodeRef, CM.QNAME_ASSOC_ASPECT_GEN_CHILD_AGRS);
					if(assocsForGenChild != null && ! assocsForGenChild.isEmpty()) {
						AssociationRef assoc = assocsForGenChild.get(0);
						NodeRef parentGeneralRef = assoc.getSourceRef();
						if(nodeService.getType(parentGeneralRef).equals(CM.QNAME_TYPE_GENERAL_AGREEMENT_FOLDER) 
								|| nodeService.getType(parentGeneralRef).equals(CM.QNAME_TYPE_CLIENT_FOLDER)) {
							nodeService.deleteNode(nodeRef);
						}else {
							throw new WebScriptException(Status.STATUS_BAD_REQUEST, "NodeRef's type is not valid!");
						}
					}
				}
							
			}
			else
			{
				throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Client folder is not valid (or missing)!");
			}
			//QName type = nodeService.getType(nodeRef);
			
		}else {
			nodeService.deleteNode(nodeRef);
		}
		
		
		
		
		
		
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(new DataModel("Node is Deleted.")));
		return model;
	}
	
	
	

}
