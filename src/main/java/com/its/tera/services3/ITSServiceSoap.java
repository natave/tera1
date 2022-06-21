/**
 * ITSServiceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.its.tera.services3;

public interface ITSServiceSoap extends java.rmi.Remote {
    public com.its.tera.services3.ITSResultClient getClient(java.lang.String clCode, java.lang.String clName, java.lang.String clID, String appParameters) throws java.rmi.RemoteException;
    public com.its.tera.services3.ITSResultCreditLine getCreditLineById(int creditLineId, String appParameters) throws java.rmi.RemoteException;
    public com.its.tera.services3.ITSResultCreditLine getCreditLineByAgrNo(java.lang.String creditLineAgreementNum, String appParameters) throws java.rmi.RemoteException;
    public com.its.tera.services3.ITSResultCreditLine getCreditLinesByEndDate(java.util.Calendar from, java.util.Calendar to, String appParameters) throws java.rmi.RemoteException;
    public com.its.tera.services3.ITSResultLoan getLoanById(int loanId, String appParameters) throws java.rmi.RemoteException;
    public com.its.tera.services3.ITSResultLoan getLoanByAgrNo(java.lang.String agreementNum, String appParameters) throws java.rmi.RemoteException;
    public com.its.tera.services3.ITSResultLoan getLoansByCreditLineId(int creditLineId, String appParameters) throws java.rmi.RemoteException;
    public com.its.tera.services3.ITSResultLoan getLoansByCreditLineAgrNo(java.lang.String creditLineAgreementNum, String appParameters) throws java.rmi.RemoteException;
    public com.its.tera.services3.ITSResultLoan getLoanByCloseDate(java.util.Calendar from, java.util.Calendar to, String appParameters) throws java.rmi.RemoteException;
    public com.its.tera.services3.ITSResultDepartment getDepartments(String appParameters) throws java.rmi.RemoteException;
}
