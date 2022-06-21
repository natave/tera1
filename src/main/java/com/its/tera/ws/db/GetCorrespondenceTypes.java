package com.its.tera.ws.db;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.google.gson.reflect.TypeToken;
import com.its.tera.model.db.CorrType;
import com.its.tera.mybatis.service.DBServiceBean;
import com.its.tera.ws.RootWebScript;

public class GetCorrespondenceTypes extends RootWebScript {
	
	DBServiceBean dbServiceBean;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		
		List<CorrType> corrTypes = dbServiceBean.getCorrTypes();
		
		
		Type typeOfSrc = new TypeToken<Collection<CorrType>>(){}.getType();
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(corrTypes, typeOfSrc));
		return model;
	}

	public void setDbServiceBean(DBServiceBean dbServiceBean) {
		this.dbServiceBean = dbServiceBean;
	}
	
	
	

}
