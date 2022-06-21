package com.its.tera.job;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.its.tera.services3.CreditLine;
import com.its.tera.services3.ITSResultCreditLine;
import com.its.tera.services3.ITSResultLoan;
//import com.its.tera.services3.ITSResultGeneralContract;
import com.its.tera.services3.ITSServiceSoapProxy;
import com.its.tera.services3.Loan;
import com.its.tera.util.AppParameters;

public class AgreementsArchiveMove {
	
	private static Log logger = LogFactory.getLog(AgreementsArchiveMove.class);
	private ServiceRegistry services;
	private ITSServiceSoapProxy iTSServiceSoapProxy;
	private AppParameters appParameters;
	
	
	
	




	protected void execute() {
		
		NodeService nodeService = services.getNodeService();
		ITSResultCreditLine closedgenerals = null;
		Integer year = 0;
		if(appParameters.getYearArchMove() != null) {
			year = appParameters.getYearArchMove(); logger.debug("year = " + year);
		}
		
		Date date = new Date();
		logger.debug("date = " + date);
		
		Calendar from = Calendar.getInstance();
		from.setTime(date);
		from.roll(Calendar.YEAR, -year);
		from.roll(Calendar.MONTH, -1);
		Calendar to = Calendar.getInstance();
		to.setTime(date);
		to.roll(Calendar.YEAR, -year);
		
		logger.debug("from = " + from.getTime());
		logger.debug("to = " + to.getTime());
		try {
			closedgenerals = iTSServiceSoapProxy.getCreditLinesByEndDate(from, to, appParameters.getKsbAPIServiceSoapAddress());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		CreditLine[] generals = closedgenerals.getCreditLines();
		logger.debug("test AgreementsArchiveMove GENERALS length = " + generals.length);
		
		int x = generals.length > 10 ? 10 : generals.length;
		for (int i = 0; i < x/*generals.length*/; i++) {
			logger.debug("GEN = " + generals[i].getAGREEMENT_NO() + " - " + generals[i].getCREDIT_LINE_ID());
		}
								
		
		
		
		
		
		ITSResultLoan loansR = null;
		try {
			loansR = iTSServiceSoapProxy.getLoanByCloseDate(from, to, appParameters.getKsbAPIServiceSoapAddress());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Loan[] loans = loansR.getLoans();
		logger.debug("test AgreementsArchiveMove LOANS length = " + loans.length);
		
		int y = loans.length > 10 ? 10 : loans.length;
		for (int i = 0; i < y/*generals.length*/; i++) {
			logger.debug("AGR = " + loans[i].getAGREEMENT_NO() + " - " + loans[i].getLOAN_ID());
		}
								
		
		logger.debug("test AgreementsArchiveMove LOANS length = " + loans.length);
		
		
		
		
	}
	
	
	
	
	
	
	
	

	public void setAppParameters(AppParameters appParameters) {
		this.appParameters = appParameters;
	}




	public void setiTSServiceSoapProxy(ITSServiceSoapProxy iTSServiceSoapProxy) {
		this.iTSServiceSoapProxy = iTSServiceSoapProxy;
	}




	public void setServices(ServiceRegistry services) {
		this.services = services;
	}
	
	

}
