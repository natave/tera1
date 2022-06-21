/**
 * Department.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.its.tera.services3;

public class Department  implements java.io.Serializable {
    private java.lang.String DEPT_NO;

    private java.lang.String DESCRIP;

    public Department() {
    }

    public Department(
           java.lang.String DEPT_NO,
           java.lang.String DESCRIP) {
           this.DEPT_NO = DEPT_NO;
           this.DESCRIP = DESCRIP;
    }


    /**
     * Gets the DEPT_NO value for this Department.
     * 
     * @return DEPT_NO
     */
    public java.lang.String getDEPT_NO() {
        return DEPT_NO;
    }


    /**
     * Sets the DEPT_NO value for this Department.
     * 
     * @param DEPT_NO
     */
    public void setDEPT_NO(java.lang.String DEPT_NO) {
        this.DEPT_NO = DEPT_NO;
    }


    /**
     * Gets the DESCRIP value for this Department.
     * 
     * @return DESCRIP
     */
    public java.lang.String getDESCRIP() {
        return DESCRIP;
    }


    /**
     * Sets the DESCRIP value for this Department.
     * 
     * @param DESCRIP
     */
    public void setDESCRIP(java.lang.String DESCRIP) {
        this.DESCRIP = DESCRIP;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Department)) return false;
        Department other = (Department) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.DEPT_NO==null && other.getDEPT_NO()==null) || 
             (this.DEPT_NO!=null &&
              this.DEPT_NO.equals(other.getDEPT_NO()))) &&
            ((this.DESCRIP==null && other.getDESCRIP()==null) || 
             (this.DESCRIP!=null &&
              this.DESCRIP.equals(other.getDESCRIP())));
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
        if (getDEPT_NO() != null) {
            _hashCode += getDEPT_NO().hashCode();
        }
        if (getDESCRIP() != null) {
            _hashCode += getDESCRIP().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Department.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "Department"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DEPT_NO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "DEPT_NO"));
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
