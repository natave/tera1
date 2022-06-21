/**
 * Client.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.its.tera.services3;

public class Client  implements java.io.Serializable {
    private java.lang.String CLIENT_NO;

    private java.lang.String DESCRIP;

    private java.lang.String PERSONAL_ID;

    private java.lang.String TAX_INSP_CODE;

    private java.lang.String DEPT_NO;

    private java.lang.String IS_VIP;

    public Client() {
    }

    public Client(
           java.lang.String CLIENT_NO,
           java.lang.String DESCRIP,
           java.lang.String PERSONAL_ID,
           java.lang.String TAX_INSP_CODE,
           java.lang.String DEPT_NO,
           java.lang.String IS_VIP) {
           this.CLIENT_NO = CLIENT_NO;
           this.DESCRIP = DESCRIP;
           this.PERSONAL_ID = PERSONAL_ID;
           this.TAX_INSP_CODE = TAX_INSP_CODE;
           this.DEPT_NO = DEPT_NO;
           this.IS_VIP = IS_VIP;
    }


    /**
     * Gets the CLIENT_NO value for this Client.
     * 
     * @return CLIENT_NO
     */
    public java.lang.String getCLIENT_NO() {
        return CLIENT_NO;
    }


    /**
     * Sets the CLIENT_NO value for this Client.
     * 
     * @param CLIENT_NO
     */
    public void setCLIENT_NO(java.lang.String CLIENT_NO) {
        this.CLIENT_NO = CLIENT_NO;
    }


    /**
     * Gets the DESCRIP value for this Client.
     * 
     * @return DESCRIP
     */
    public java.lang.String getDESCRIP() {
        return DESCRIP;
    }


    /**
     * Sets the DESCRIP value for this Client.
     * 
     * @param DESCRIP
     */
    public void setDESCRIP(java.lang.String DESCRIP) {
        this.DESCRIP = DESCRIP;
    }


    /**
     * Gets the PERSONAL_ID value for this Client.
     * 
     * @return PERSONAL_ID
     */
    public java.lang.String getPERSONAL_ID() {
        return PERSONAL_ID;
    }


    /**
     * Sets the PERSONAL_ID value for this Client.
     * 
     * @param PERSONAL_ID
     */
    public void setPERSONAL_ID(java.lang.String PERSONAL_ID) {
        this.PERSONAL_ID = PERSONAL_ID;
    }


    /**
     * Gets the TAX_INSP_CODE value for this Client.
     * 
     * @return TAX_INSP_CODE
     */
    public java.lang.String getTAX_INSP_CODE() {
        return TAX_INSP_CODE;
    }


    /**
     * Sets the TAX_INSP_CODE value for this Client.
     * 
     * @param TAX_INSP_CODE
     */
    public void setTAX_INSP_CODE(java.lang.String TAX_INSP_CODE) {
        this.TAX_INSP_CODE = TAX_INSP_CODE;
    }


    /**
     * Gets the DEPT_NO value for this Client.
     * 
     * @return DEPT_NO
     */
    public java.lang.String getDEPT_NO() {
        return DEPT_NO;
    }


    /**
     * Sets the DEPT_NO value for this Client.
     * 
     * @param DEPT_NO
     */
    public void setDEPT_NO(java.lang.String DEPT_NO) {
        this.DEPT_NO = DEPT_NO;
    }


    /**
     * Gets the IS_VIP value for this Client.
     * 
     * @return IS_VIP
     */
    public java.lang.String getIS_VIP() {
        return IS_VIP;
    }


    /**
     * Sets the IS_VIP value for this Client.
     * 
     * @param IS_VIP
     */
    public void setIS_VIP(java.lang.String IS_VIP) {
        this.IS_VIP = IS_VIP;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Client)) return false;
        Client other = (Client) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.CLIENT_NO==null && other.getCLIENT_NO()==null) || 
             (this.CLIENT_NO!=null &&
              this.CLIENT_NO.equals(other.getCLIENT_NO()))) &&
            ((this.DESCRIP==null && other.getDESCRIP()==null) || 
             (this.DESCRIP!=null &&
              this.DESCRIP.equals(other.getDESCRIP()))) &&
            ((this.PERSONAL_ID==null && other.getPERSONAL_ID()==null) || 
             (this.PERSONAL_ID!=null &&
              this.PERSONAL_ID.equals(other.getPERSONAL_ID()))) &&
            ((this.TAX_INSP_CODE==null && other.getTAX_INSP_CODE()==null) || 
             (this.TAX_INSP_CODE!=null &&
              this.TAX_INSP_CODE.equals(other.getTAX_INSP_CODE()))) &&
            ((this.DEPT_NO==null && other.getDEPT_NO()==null) || 
             (this.DEPT_NO!=null &&
              this.DEPT_NO.equals(other.getDEPT_NO()))) &&
            ((this.IS_VIP==null && other.getIS_VIP()==null) || 
             (this.IS_VIP!=null &&
              this.IS_VIP.equals(other.getIS_VIP())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCLIENT_NO() != null) {
            _hashCode += getCLIENT_NO().hashCode();
        }
        if (getDESCRIP() != null) {
            _hashCode += getDESCRIP().hashCode();
        }
        if (getPERSONAL_ID() != null) {
            _hashCode += getPERSONAL_ID().hashCode();
        }
        if (getTAX_INSP_CODE() != null) {
            _hashCode += getTAX_INSP_CODE().hashCode();
        }
        if (getDEPT_NO() != null) {
            _hashCode += getDEPT_NO().hashCode();
        }
        if (getIS_VIP() != null) {
            _hashCode += getIS_VIP().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Client.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "Client"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CLIENT_NO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "CLIENT_NO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DESCRIP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "DESCRIP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PERSONAL_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "PERSONAL_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TAX_INSP_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "TAX_INSP_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DEPT_NO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "DEPT_NO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IS_VIP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "IS_VIP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
