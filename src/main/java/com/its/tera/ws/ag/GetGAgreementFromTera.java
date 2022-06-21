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
import com.its.tera.services3.CreditLine;
import com.its.tera.services3.ITSResultCreditLine;
/*import com.its.tera.services.GeneralContract;
import com.its.tera.services.ITSResultGeneralContract;*/
import com.its.tera.services3.ITSServiceSoapProxy;
import com.its.tera.util.AppParameters;
import com.its.tera.ws.RootWebScript;

public class GetGAgreementFromTera extends RootWebScript{
	
	ITSServiceSoapProxy iTSServiceSoapProxy;
	AppParameters appParameters;
	
	private static Log logger = LogFactory.getLog(GetGAgreementFromTera.class);

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		String agrId = req.getParameter("id");
		String clientCode = req.getParameter("code");
		logger.debug("id = " + agrId + ", code = " + clientCode);
		
		if(agrId == null || agrId.isEmpty() || clientCode == null || clientCode.isEmpty()) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Parameters are missing.");
		}
				
		SimpleDateFormat format = new SimpleDateFormat(appParameters.getAgrDateFormat());
		
//		ITSResultGeneralContract generalResult;
//		try {
//			generalResult = iTSServiceSoapProxy.getGeneralContract(agrId, appParameters.getKsbAPIServiceSoapAddress());
//		} catch (Exception e) {
//			throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
//		}
//		
//		GeneralContract[] gAgreements = generalResult.getGeneralContracts();
		
		
		ITSResultCreditLine generalResult;		
		try {
			generalResult = iTSServiceSoapProxy.getCreditLineByAgrNo(agrId, appParameters.getKsbAPIServiceSoapAddress());logger.debug("KsbAPIServiceSoapAddress = " + appParameters.getKsbAPIServiceSoapAddress());
			logger.debug("generalResult = " + getGson().toJson(generalResult));
		} catch (Exception e) {
			throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
		CreditLine[] gAgreements = generalResult.getCreditLines();
		
		
		if(gAgreements != null && gAgreements.length != 0)
		{
			//GeneralContract ga = gAgreements[0];
			CreditLine ga = gAgreements[0];
			logger.debug("agrId = " + agrId + "ga.getCLIENT_ID()" + ga.getCLIENT_ID());
			if(!ga.getCLIENT_ID().equalsIgnoreCase(clientCode)){
				throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, "This Client Dosen't Contain General Agreement with number - " + agrId + ".");
			}
			Agreement gaModel = new Agreement();
			gaModel.setGagrId(ga.getCREDIT_LINE_ID() + "");
			gaModel.setGagrNumber(ga.getAGREEMENT_NO());
			gaModel.setGagrAmount(ga.getAMOUNT().toString());
			gaModel.setGagrCurrency(ga.getCCY());	
			System.out.println("G getSTART_DATE = " + ga.getSTART_DATE());
			//gaModel.setGagrDate(format.parse(ga.getSTART_DATE()).getTime());
			gaModel.setGagrDate(ga.getSTART_DATE().getTime().getTime());
			gaModel.setBranch(ga.getBRANCH_ID());
			
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("result", getGson().toJson(gaModel));
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
