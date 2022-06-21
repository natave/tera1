package com.its.tera.services3;

public class ITSServiceSoapProxy implements com.its.tera.services3.ITSServiceSoap {
  private String _endpoint = null;
  private com.its.tera.services3.ITSServiceSoap iTSServiceSoap = null;
  
  public ITSServiceSoapProxy() {
    _initITSServiceSoapProxy();
  }
  
  public ITSServiceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initITSServiceSoapProxy();
  }
  
  private void _initITSServiceSoapProxy() {
    try {
      iTSServiceSoap = (new com.its.tera.services3.ITSServiceLocator()).getITSServiceSoap();
      if (iTSServiceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iTSServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iTSServiceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  
  /**
	 * its*/
	private void _initITSServiceSoapProxyIts(String appParameters) {
		try {
			iTSServiceSoap = (new com.its.tera.services3.ITSServiceLocator(appParameters)).getITSServiceSoap();
			if (iTSServiceSoap != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub)iTSServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
				else
					_endpoint = (String)((javax.xml.rpc.Stub)iTSServiceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
			}      
		}
		catch (javax.xml.rpc.ServiceException serviceException) {}
	}
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iTSServiceSoap != null)
      ((javax.xml.rpc.Stub)iTSServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.its.tera.services3.ITSServiceSoap getITSServiceSoap(String appParameters) {
    if (iTSServiceSoap == null)
    	_initITSServiceSoapProxyIts(appParameters);
    return iTSServiceSoap;
  }
  
  public com.its.tera.services3.ITSResultClient getClient(java.lang.String clCode, java.lang.String clName, java.lang.String clID, String appParameters) throws java.rmi.RemoteException{
    if (iTSServiceSoap == null)
    	_initITSServiceSoapProxyIts(appParameters);
    return iTSServiceSoap.getClient(clCode, clName, clID, appParameters);
  }
  
  public com.its.tera.services3.ITSResultCreditLine getCreditLineById(int creditLineId, String appParameters) throws java.rmi.RemoteException{
    if (iTSServiceSoap == null)
    	_initITSServiceSoapProxyIts(appParameters);
    return iTSServiceSoap.getCreditLineById(creditLineId, appParameters);
  }
  
  public com.its.tera.services3.ITSResultCreditLine getCreditLineByAgrNo(java.lang.String creditLineAgreementNum, String appParameters) throws java.rmi.RemoteException{
    if (iTSServiceSoap == null)
    	_initITSServiceSoapProxyIts(appParameters);
    return iTSServiceSoap.getCreditLineByAgrNo(creditLineAgreementNum, appParameters);
  }
  
  public com.its.tera.services3.ITSResultCreditLine getCreditLinesByEndDate(java.util.Calendar from, java.util.Calendar to, String appParameters) throws java.rmi.RemoteException{
    if (iTSServiceSoap == null)
    	_initITSServiceSoapProxyIts(appParameters);
    return iTSServiceSoap.getCreditLinesByEndDate(from, to, appParameters);
  }
  
  public com.its.tera.services3.ITSResultLoan getLoanById(int loanId, String appParameters) throws java.rmi.RemoteException{
    if (iTSServiceSoap == null)
    	_initITSServiceSoapProxyIts(appParameters);
    return iTSServiceSoap.getLoanById(loanId, appParameters);
  }
  
  public com.its.tera.services3.ITSResultLoan getLoanByAgrNo(java.lang.String agreementNum, String appParameters) throws java.rmi.RemoteException{
    if (iTSServiceSoap == null)
    	_initITSServiceSoapProxyIts(appParameters);
    return iTSServiceSoap.getLoanByAgrNo(agreementNum, appParameters);
  }
  
  public com.its.tera.services3.ITSResultLoan getLoansByCreditLineId(int creditLineId, String appParameters) throws java.rmi.RemoteException{
    if (iTSServiceSoap == null)
    	_initITSServiceSoapProxyIts(appParameters);
    return iTSServiceSoap.getLoansByCreditLineId(creditLineId, appParameters);
  }
  
  public com.its.tera.services3.ITSResultLoan getLoansByCreditLineAgrNo(java.lang.String creditLineAgreementNum, String appParameters) throws java.rmi.RemoteException{
    if (iTSServiceSoap == null)
    	_initITSServiceSoapProxyIts(appParameters);
    return iTSServiceSoap.getLoansByCreditLineAgrNo(creditLineAgreementNum, appParameters);
  }
  
  public com.its.tera.services3.ITSResultLoan getLoanByCloseDate(java.util.Calendar from, java.util.Calendar to, String appParameters) throws java.rmi.RemoteException{
    if (iTSServiceSoap == null)
    	_initITSServiceSoapProxyIts(appParameters);
    return iTSServiceSoap.getLoanByCloseDate(from, to, appParameters);
  }
  
  public com.its.tera.services3.ITSResultDepartment getDepartments(String appParameters) throws java.rmi.RemoteException{
    if (iTSServiceSoap == null)
    	_initITSServiceSoapProxyIts(appParameters);
    return iTSServiceSoap.getDepartments(appParameters);
  }
  
  
}