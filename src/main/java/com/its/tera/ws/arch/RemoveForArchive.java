package com.its.tera.ws.arch;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.repository.AssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.util.Content;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.google.gson.JsonSyntaxException;
import com.its.tera.constants.CM;
import com.its.tera.model.ArchiveModel;
import com.its.tera.model.util.ListResult;
import com.its.tera.ws.RootWebScript;

public class RemoveForArchive extends RootWebScript{
	
	private static Log logger = LogFactory.getLog(RemoveForArchive.class);

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		NodeService nodeService = serviceRegistry.getNodeService();
		
		Content content = req.getContent();
		if (content == null) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Requset body is missing! URL = '" + req.getURL() + "'");
		}

		String payload = null;
		try {
			payload = content.getContent();
		} catch (IOException e) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
		}

		if (payload == null || payload.length() == 0) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Payload is missing!");
		}
		logger.debug("payload = " + payload);
		
		ListResult dataModel = null;
		try {
			dataModel = getGson().fromJson(payload, ListResult.class);
		} catch (JsonSyntaxException e) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
		}
		
		if (dataModel.getData() == null) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Data about NodeRefs is missing!");
		}
		
		@SuppressWarnings("unchecked")
		List<String> nodeRefs = (List<String>)dataModel.getData();
		
		
		
		List<String> forArchive = new ArrayList<String>();
		List<String> agrsUnderGeneral = new ArrayList<String>();
		List<String> agrsUnderNotListedGeneral = new ArrayList<String>();
		
		logger.debug("refs = " + nodeRefs);
		
		for (String string : nodeRefs) {
			NodeRef nodeRef = new NodeRef(string);
			if(nodeService.getType(nodeRef).equals(CM.QNAME_TYPE_AGREEMENT_FOLDER) && nodeService.hasAspect(nodeRef, CM.QNAME_ASPECT_FOR_ARCHIVE)) {
				
				if(nodeService.hasAspect(nodeRef, CM.QNAME_ASPECT_GEN_AGR_PROPS)){
					List<AssociationRef> aagr = nodeService.getSourceAssocs(nodeRef, CM.QNAME_ASSOC_ASPECT_GEN_CHILD_AGRS);
					if(aagr != null) {
						NodeRef gAgrRef =  aagr.get(0).getSourceRef();
						if(nodeRefs.contains(gAgrRef.toString())) {							
							nodeService.removeAspect(nodeRef, CM.QNAME_ASPECT_FOR_ARCHIVE);
							forArchive.add(nodeRef.toString());														
						}else {
							agrsUnderNotListedGeneral.add(nodeRef.toString());
						}
					}					
					
				}else {
					nodeService.removeAspect(nodeRef, CM.QNAME_ASPECT_FOR_ARCHIVE);
					forArchive.add(nodeRef.toString());
				}
			}
			else if(nodeService.getType(nodeRef).equals(CM.QNAME_TYPE_GENERAL_AGREEMENT_FOLDER) && nodeService.hasAspect(nodeRef, CM.QNAME_ASPECT_FOR_ARCHIVE))
			{
				nodeService.removeAspect(nodeRef, CM.QNAME_ASPECT_FOR_ARCHIVE);
				forArchive.add(nodeRef.toString());
				
				List<AssociationRef> args = nodeService.getTargetAssocs(nodeRef, CM.QNAME_ASSOC_ASPECT_GEN_CHILD_AGRS);
				if(args != null) {
					for (AssociationRef agr : args) {
						NodeRef agrRef = agr.getTargetRef();
						if(nodeService.hasAspect(agrRef, CM.QNAME_ASPECT_FOR_ARCHIVE)) {
							nodeService.removeAspect(agrRef, CM.QNAME_ASPECT_FOR_ARCHIVE);
							forArchive.add(agrRef.toString());
							if(!nodeRefs.contains(agrRef.toString())) {
								agrsUnderGeneral.add(agrRef.toString());
							}
						}
					}
				}
			}
			else {
				logger.debug(" is not agreement or without archive aspect = " + nodeRef);
			}			
		}
		
		ArchiveModel arch = new ArchiveModel();
		arch.setSent(nodeRefs.size());
		arch.setForArchive(forArchive);
		arch.setNotListedAgrsUnderGeneral(agrsUnderGeneral);
		arch.setAgrsUnderNotListedGeneral(agrsUnderNotListedGeneral);
		logger.debug("forArchive size = " + forArchive.size());
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(arch));
		return model;
	}
	
	

}
