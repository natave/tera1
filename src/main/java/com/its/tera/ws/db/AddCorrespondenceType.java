package com.its.tera.ws.db;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.extensions.surf.util.Content;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.google.gson.JsonSyntaxException;
import com.its.tera.model.db.CorrType;
import com.its.tera.mybatis.service.DBServiceBean;
import com.its.tera.ws.RootWebScript;

public class AddCorrespondenceType extends RootWebScript {
	
	DBServiceBean dbServiceBean;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
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
		
		CorrType corrType = null;
		try {
			corrType = getGson().fromJson(payload, CorrType.class);
		} catch (JsonSyntaxException e) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
		}	
		
		if(corrType.getName() != null)
		{			
			try {
				dbServiceBean.insertCorrType(corrType);
			} catch (DataIntegrityViolationException e) {
				throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Corespondence type with name '" + corrType.getName() + "' already exists!");
			}catch (Exception e) {
				throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
			}
			//dictionaryServiceBean.getCorrTypeByName(corrType.getName());
		}
		else
		{
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Correspondence type name is null!");
		}
		
		
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(dbServiceBean.getCorrTypeByName(corrType.getName())));
		return model;
	}

	public void setDbServiceBean(DBServiceBean dbServiceBean) {
		this.dbServiceBean = dbServiceBean;
	}
	
	
	

}
