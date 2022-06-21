/**
 * ITSServiceSoapStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.its.tera.services3;

public class ITSServiceSoapStub extends org.apache.axis.client.Stub implements com.its.tera.services3.ITSServiceSoap {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[10];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetClient");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "clCode"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "clName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "clID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "ITSResultClient"));
        oper.setReturnClass(com.its.tera.services3.ITSResultClient.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "GetClientResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetCreditLineById");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "creditLineId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "ITSResultCreditLine"));
        oper.setReturnClass(com.its.tera.services3.ITSResultCreditLine.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "GetCreditLineByIdResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetCreditLineByAgrNo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "creditLineAgreementNum"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "ITSResultCreditLine"));
        oper.setReturnClass(com.its.tera.services3.ITSResultCreditLine.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "GetCreditLineByAgrNoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetCreditLinesByEndDate");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "from"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"), java.util.Calendar.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "to"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"), java.util.Calendar.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "ITSResultCreditLine"));
        oper.setReturnClass(com.its.tera.services3.ITSResultCreditLine.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "GetCreditLinesByEndDateResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetLoanById");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "loanId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "ITSResultLoan"));
        oper.setReturnClass(com.its.tera.services3.ITSResultLoan.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "GetLoanByIdResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetLoanByAgrNo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "agreementNum"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "ITSResultLoan"));
        oper.setReturnClass(com.its.tera.services3.ITSResultLoan.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "GetLoanByAgrNoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetLoansByCreditLineId");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "creditLineId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "ITSResultLoan"));
        oper.setReturnClass(com.its.tera.services3.ITSResultLoan.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "GetLoansByCreditLineIdResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetLoansByCreditLineAgrNo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "creditLineAgreementNum"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "ITSResultLoan"));
        oper.setReturnClass(com.its.tera.services3.ITSResultLoan.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "GetLoansByCreditLineAgrNoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetLoanByCloseDate");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "from"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"), java.util.Calendar.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "to"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"), java.util.Calendar.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "ITSResultLoan"));
        oper.setReturnClass(com.its.tera.services3.ITSResultLoan.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "GetLoanByCloseDateResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetDepartments");
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "ITSResultDepartment"));
        oper.setReturnClass(com.its.tera.services3.ITSResultDepartment.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "GetDepartmentsResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    public ITSServiceSoapStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public ITSServiceSoapStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public ITSServiceSoapStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "ArrayOfClient");
            cachedSerQNames.add(qName);
            cls = com.its.tera.services3.Client[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "Client");
            qName2 = new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "Client");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "ArrayOfCreditLine");
            cachedSerQNames.add(qName);
            cls = com.its.tera.services3.CreditLine[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "CreditLine");
            qName2 = new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "CreditLine");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "ArrayOfDepartment");
            cachedSerQNames.add(qName);
            cls = com.its.tera.services3.Department[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "Department");
            qName2 = new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "Department");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "ArrayOfLoan");
            cachedSerQNames.add(qName);
            cls = com.its.tera.services3.Loan[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "Loan");
            qName2 = new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "Loan");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "Client");
            cachedSerQNames.add(qName);
            cls = com.its.tera.services3.Client.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "CreditLine");
            cachedSerQNames.add(qName);
            cls = com.its.tera.services3.CreditLine.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "Department");
            cachedSerQNames.add(qName);
            cls = com.its.tera.services3.Department.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "ITSResultClient");
            cachedSerQNames.add(qName);
            cls = com.its.tera.services3.ITSResultClient.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "ITSResultCreditLine");
            cachedSerQNames.add(qName);
            cls = com.its.tera.services3.ITSResultCreditLine.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "ITSResultDepartment");
            cachedSerQNames.add(qName);
            cls = com.its.tera.services3.ITSResultDepartment.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "ITSResultLoan");
            cachedSerQNames.add(qName);
            cls = com.its.tera.services3.ITSResultLoan.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "Loan");
            cachedSerQNames.add(qName);
            cls = com.its.tera.services3.Loan.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public com.its.tera.services3.ITSResultClient getClient(java.lang.String clCode, java.lang.String clName, java.lang.String clID, String appParameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.StandardBank.ge/WebServices/GetClient");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "GetClient"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {clCode, clName, clID});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.its.tera.services3.ITSResultClient) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.its.tera.services3.ITSResultClient) org.apache.axis.utils.JavaUtils.convert(_resp, com.its.tera.services3.ITSResultClient.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.its.tera.services3.ITSResultCreditLine getCreditLineById(int creditLineId, String appParameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.StandardBank.ge/WebServices/GetCreditLineById");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "GetCreditLineById"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(creditLineId)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.its.tera.services3.ITSResultCreditLine) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.its.tera.services3.ITSResultCreditLine) org.apache.axis.utils.JavaUtils.convert(_resp, com.its.tera.services3.ITSResultCreditLine.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.its.tera.services3.ITSResultCreditLine getCreditLineByAgrNo(java.lang.String creditLineAgreementNum, String appParameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.StandardBank.ge/WebServices/GetCreditLineByAgrNo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "GetCreditLineByAgrNo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {creditLineAgreementNum});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.its.tera.services3.ITSResultCreditLine) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.its.tera.services3.ITSResultCreditLine) org.apache.axis.utils.JavaUtils.convert(_resp, com.its.tera.services3.ITSResultCreditLine.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.its.tera.services3.ITSResultCreditLine getCreditLinesByEndDate(java.util.Calendar from, java.util.Calendar to, String appParameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.StandardBank.ge/WebServices/GetCreditLinesByEndDate");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "GetCreditLinesByEndDate"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {from, to});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.its.tera.services3.ITSResultCreditLine) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.its.tera.services3.ITSResultCreditLine) org.apache.axis.utils.JavaUtils.convert(_resp, com.its.tera.services3.ITSResultCreditLine.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.its.tera.services3.ITSResultLoan getLoanById(int loanId, String appParameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.StandardBank.ge/WebServices/GetLoanById");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "GetLoanById"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(loanId)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.its.tera.services3.ITSResultLoan) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.its.tera.services3.ITSResultLoan) org.apache.axis.utils.JavaUtils.convert(_resp, com.its.tera.services3.ITSResultLoan.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.its.tera.services3.ITSResultLoan getLoanByAgrNo(java.lang.String agreementNum, String appParameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.StandardBank.ge/WebServices/GetLoanByAgrNo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "GetLoanByAgrNo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {agreementNum});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.its.tera.services3.ITSResultLoan) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.its.tera.services3.ITSResultLoan) org.apache.axis.utils.JavaUtils.convert(_resp, com.its.tera.services3.ITSResultLoan.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.its.tera.services3.ITSResultLoan getLoansByCreditLineId(int creditLineId, String appParameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.StandardBank.ge/WebServices/GetLoansByCreditLineId");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "GetLoansByCreditLineId"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(creditLineId)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.its.tera.services3.ITSResultLoan) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.its.tera.services3.ITSResultLoan) org.apache.axis.utils.JavaUtils.convert(_resp, com.its.tera.services3.ITSResultLoan.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.its.tera.services3.ITSResultLoan getLoansByCreditLineAgrNo(java.lang.String creditLineAgreementNum, String appParameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.StandardBank.ge/WebServices/GetLoansByCreditLineAgrNo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "GetLoansByCreditLineAgrNo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {creditLineAgreementNum});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.its.tera.services3.ITSResultLoan) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.its.tera.services3.ITSResultLoan) org.apache.axis.utils.JavaUtils.convert(_resp, com.its.tera.services3.ITSResultLoan.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.its.tera.services3.ITSResultLoan getLoanByCloseDate(java.util.Calendar from, java.util.Calendar to, String appParameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.StandardBank.ge/WebServices/GetLoanByCloseDate");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "GetLoanByCloseDate"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {from, to});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.its.tera.services3.ITSResultLoan) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.its.tera.services3.ITSResultLoan) org.apache.axis.utils.JavaUtils.convert(_resp, com.its.tera.services3.ITSResultLoan.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.its.tera.services3.ITSResultDepartment getDepartments(String appParameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://schemas.StandardBank.ge/WebServices/GetDepartments");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "GetDepartments"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.its.tera.services3.ITSResultDepartment) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.its.tera.services3.ITSResultDepartment) org.apache.axis.utils.JavaUtils.convert(_resp, com.its.tera.services3.ITSResultDepartment.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
