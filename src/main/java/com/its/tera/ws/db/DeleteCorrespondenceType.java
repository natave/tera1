package com.its.tera.ws.db;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.extensions.surf.util.Content;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.google.gson.JsonSyntaxException;
import com.its.tera.model.DataModel;
import com.its.tera.model.db.CorrType;
import com.its.tera.mybatis.service.DBServiceBean;
import com.its.tera.ws.RootWebScript;

public class DeleteCorrespondenceType extends RootWebScript {

	DBServiceBean dbServiceBean;
	
	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		String idString = req.getServiceMatch().getTemplateVars().get("id");
	
//		Content content = req.getContent();
//		if (content == null) {
//			throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Requset body is missing! URL = '" + req.getURL() + "'");
//		}
//
//		String payload = null;
//		try {
//			payload = content.getContent();
//		} catch (IOException e) {
//			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
//		}
//
		if (idString == null || idString.length() == 0) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Id is missing!");
		}
		
		Integer id = Integer.parseInt(idString);
		
		CorrType corrType = new CorrType();
		corrType.setId(id);
//		try {
//			corrType = getGson().fromJson(payload, CorrType.class);
//		} catch (JsonSyntaxException e) {
//			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
//		}
		
		dbServiceBean.deleteCorrType(corrType);
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(new DataModel("ok")));
		return model;
	}

	public void setDbServiceBean(DBServiceBean dbServiceBean) {
		this.dbServiceBean = dbServiceBean;
	}
	
	

}
