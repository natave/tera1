package com.its.tera.ws.ag;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.its.tera.model.Agreement;
import com.its.tera.model.DataModel;
import com.its.tera.util.AppParameters;
import com.its.tera.ws.RootWebScript;

public class GetGAgreementFromTera2 extends RootWebScript{
	
	
	AppParameters appParameters;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		String agrId = req.getParameter("id");
		String clientCode = req.getParameter("code");
		
		if(agrId == null || agrId.isEmpty() || clientCode == null || clientCode.isEmpty()) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Parameters are missing.");
		}
		
		
		if(agrId != null && agrId.length() > 5)
		{
			if(agrId.equalsIgnoreCase("999999999")){
				throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, "This Client Dosen't Contain General Agreement with number - " + agrId + ".");
			}
			Agreement gaModel = new Agreement();
			gaModel.setGagrId(agrId);
			gaModel.setGagrNumber((new Date()).getTime() + "");
			gaModel.setGagrAmount(agrId.startsWith("0") ? "50000" : agrId.substring(0, 2) +"000");
			gaModel.setGagrCurrency("GEL");	
			gaModel.setGagrDate(new Date().getTime());
			gaModel.setBranch(agrId.startsWith("7") ? "7" : "0");

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("result", getGson().toJson(gaModel));
			return model;
		}
		else
		{
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("result", getGson().toJson(new DataModel("Doesn't exists.")));
			return model;
		}
		
		
		
	}

	

	public void setAppParameters(AppParameters appParameters) {
		this.appParameters = appParameters;
	}
	
	
	

}
