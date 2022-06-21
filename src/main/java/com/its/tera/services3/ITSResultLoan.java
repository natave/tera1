/**
 * ITSResultLoan.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.its.tera.services3;

public class ITSResultLoan  implements java.io.Serializable {
    private int errorCode;

    private java.lang.String errorMessage;

    private com.its.tera.services3.Loan[] loans;

    public ITSResultLoan() {
    }

    public ITSResultLoan(
           int errorCode,
           java.lang.String errorMessage,
           com.its.tera.services3.Loan[] loans) {
           this.errorCode = errorCode;
           this.errorMessage = errorMessage;
           this.loans = loans;
    }


    /**
     * Gets the errorCode value for this ITSResultLoan.
     * 
     * @return errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }


    /**
     * Sets the errorCode value for this ITSResultLoan.
     * 
     * @param errorCode
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }


    /**
     * Gets the errorMessage value for this ITSResultLoan.
     * 
     * @return errorMessage
     */
    public java.lang.String getErrorMessage() {
        return errorMessage;
    }


    /**
     * Sets the errorMessage value for this ITSResultLoan.
     * 
     * @param errorMessage
     */
    public void setErrorMessage(java.lang.String errorMessage) {
        this.errorMessage = errorMessage;
    }


    /**
     * Gets the loans value for this ITSResultLoan.
     * 
     * @return loans
     */
    public com.its.tera.services3.Loan[] getLoans() {
        return loans;
    }


    /**
     * Sets the loans value for this ITSResultLoan.
     * 
     * @param loans
     */
    public void setLoans(com.its.tera.services3.Loan[] loans) {
        this.loans = loans;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ITSResultLoan)) return false;
        ITSResultLoan other = (ITSResultLoan) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.errorCode == other.getErrorCode() &&
            ((this.errorMessage==null && other.getErrorMessage()==null) || 
             (this.errorMessage!=null &&
              this.errorMessage.equals(other.getErrorMessage()))) &&
            ((this.loans==null && other.getLoans()==null) || 
             (this.loans!=null &&
              java.util.Arrays.equals(this.loans, other.getLoans())));
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
        _hashCode += getErrorCode();
        if (getErrorMessage() != null) {
            _hashCode += getErrorMessage().hashCode();
        }
        if (getLoans() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLoans());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLoans(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ITSResultLoan.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "ITSResultLoan"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "ErrorCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "ErrorMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loans");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "Loans"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "Loan"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "Loan"));
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
