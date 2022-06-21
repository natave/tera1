package com.its.tera.ws.scan;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.util.Content;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.google.gson.JsonSyntaxException;
import com.its.tera.model.DataModel;
import com.its.tera.model.util.ListResult;
import com.its.tera.ws.RootWebScript;

public class DeleteScans extends RootWebScript{
	
	private static Log logger = LogFactory.getLog(DeleteScans.class);

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
		
		
//		String refs = req.getParameter("");
		logger.debug("refs = " + nodeRefs);
		
		
		
//		try {
//			JSONArray arr = new JSONArray(refs);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		String[] nodeRefs = refs.split(",");
		
		for (String string : nodeRefs) {
			NodeRef nodeRef = new NodeRef(string);
			
			if(nodeService.exists(nodeRef)) {
				nodeService.deleteNode(nodeRef);
			}
			else {
				logger.debug("Doesn't exists - " + string);
			}
		}
		
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(new DataModel("ok")));
		return model;
	}
	
	
	
	

}
