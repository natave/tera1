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
import com.its.tera.model.db.Currency;
import com.its.tera.mybatis.service.DBServiceBean;
import com.its.tera.ws.RootWebScript;

public class GetCurrencyList extends RootWebScript {
	
	DBServiceBean dbServiceBean;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		List<Currency> currencyList = dbServiceBean.getCurrency();;
		
		Type typeOfSrc = new TypeToken<Collection<Currency>>(){}.getType();
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(currencyList, typeOfSrc));
		return model;
	}

	public void setDbServiceBean(DBServiceBean dbServiceBean) {
		this.dbServiceBean = dbServiceBean;
	}
	
	
	

}
