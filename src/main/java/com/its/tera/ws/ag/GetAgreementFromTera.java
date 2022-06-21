package com.its.tera.ws.ag;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.its.tera.model.Agreement;
import com.its.tera.model.DataModel;
import com.its.tera.services3.ITSResultLoan;
import com.its.tera.services3.ITSServiceSoapProxy;
import com.its.tera.services3.Loan;
import com.its.tera.util.AppParameters;
import com.its.tera.ws.RootWebScript;

public class GetAgreementFromTera extends RootWebScript {
	
	ITSServiceSoapProxy iTSServiceSoapProxy;
	AppParameters appParameters;

	private static Log logger = LogFactory.getLog(GetAgreementFromTera.class);
	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		String agrId = req.getParameter("id");
		String clientCode = req.getParameter("code");
		logger.debug("id = " + agrId + ", code = " + clientCode);
		
		if(agrId == null || agrId.isEmpty() || clientCode == null || clientCode.isEmpty()) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Parameters are missing.");
		}
		
		SimpleDateFormat format = new SimpleDateFormat(appParameters.getAgrDateFormat());
		
		ITSResultLoan result = null;
		try{
			result = iTSServiceSoapProxy.getLoanByAgrNo(agrId, appParameters.getKsbAPIServiceSoapAddress());logger.debug("KsbAPIServiceSoapAddress = " + appParameters.getKsbAPIServiceSoapAddress());
			logger.debug("result = " + getGson().toJson(result));
		}catch(Exception e) {
			e.printStackTrace();
			throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
		Loan[] agreements = result.getLoans();
		if(agreements != null && agreements.length != 0)
		{			
			Loan a = agreements[0];
			logger.debug("agrId = " + agrId + "a.getCLIENT_ID()" + a.getCLIENT_ID());
			if(!a.getCLIENT_ID().equalsIgnoreCase(clientCode)){
				throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, "This Client Dosen't Contain Agreement with number - " + agrId + ".");
			}
			Agreement aModel = new Agreement();
			aModel.setAgrId(a.getLOAN_ID() + "");
			aModel.setAgrNumber(a.getAGREEMENT_NO());
			aModel.setAmount(a.getAMOUNT().toString());
			aModel.setCurrency(a.getCCY());					
			System.out.println("A getSTART_DATE = " + a.getSTART_DATE() + ",  a.getBRANCH_ID() = " + a.getBRANCH_ID());
			//aModel.setDate(format.parse(a.getSTART_DATE()).getTime());
			aModel.setDate(a.getSTART_DATE().getTime().getTime());
			aModel.setProductType(a.getOP_TYPE());
			aModel.setBranch(a.getBRANCH_ID());

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("result", getGson().toJson(aModel));
			return model;
		}
		else
		{
			logger.debug("Doesn't exists.");
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("result", getGson().toJson(new DataModel("Doesn't exists.")));
			return model;
		}
		
		
	}


	public void setiTSServiceSoapProxy(ITSServiceSoapProxy iTSServiceSoapProxy) {
		this.iTSServiceSoapProxy = iTSServiceSoapProxy;
	}


	public void setAppParameters(AppParameters appParameters) {
		this.appParameters = appParameters;
	}
	
	

}
