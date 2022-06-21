/**
 * ITSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.its.tera.services3;

public class ITSServiceLocator extends org.apache.axis.client.Service implements com.its.tera.services3.ITSService {
	
	public ITSServiceLocator() {
    }

    public ITSServiceLocator(String u) {
    	this.ITSServiceSoap_address = u;
    }


    public ITSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ITSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ITSServiceSoap
    private java.lang.String ITSServiceSoap_address; // = "http://10.210.0.76:4444/ITSService.asmx";

    public java.lang.String getITSServiceSoapAddress() {
        return ITSServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
   // private java.lang.String ITSServiceSoapWSDDServiceName = "ITSServiceSoap";
    private java.lang.String ITSServiceSoapWSDDServiceName = "ITSServiceSoap12";

    public java.lang.String getITSServiceSoapWSDDServiceName() {
        return ITSServiceSoapWSDDServiceName;
    }

    public void setITSServiceSoapWSDDServiceName(java.lang.String name) {
        ITSServiceSoapWSDDServiceName = name;
    }

    public com.its.tera.services3.ITSServiceSoap getITSServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ITSServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getITSServiceSoap(endpoint);
    }

    public com.its.tera.services3.ITSServiceSoap getITSServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.its.tera.services3.ITSServiceSoapStub _stub = new com.its.tera.services3.ITSServiceSoapStub(portAddress, this);
            _stub.setPortName(getITSServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setITSServiceSoapEndpointAddress(java.lang.String address) {
        ITSServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.its.tera.services3.ITSServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.its.tera.services3.ITSServiceSoapStub _stub = new com.its.tera.services3.ITSServiceSoapStub(new java.net.URL(ITSServiceSoap_address), this);
                _stub.setPortName(getITSServiceSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        //if ("ITSServiceSoap".equals(inputPortName)) {
        if ("ITSServiceSoap12".equals(inputPortName)) {
            return getITSServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "ITSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            //ports.add(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "ITSServiceSoap"));
            ports.add(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "ITSServiceSoap12"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
    	//if ("ITSServiceSoap".equals(portName)) {
    	if ("ITSServiceSoap12".equals(portName)) {
            setITSServiceSoapEndpointAddress(address);
        }
        else 
        { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
