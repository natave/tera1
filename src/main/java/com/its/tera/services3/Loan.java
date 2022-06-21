/**
 * Loan.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.its.tera.services3;

public class Loan  implements java.io.Serializable {
    private java.lang.String AGREEMENT_NO;

    private java.math.BigDecimal AMOUNT;

    private java.lang.String CCY;

    private java.util.Calendar START_DATE;

    private java.util.Calendar CLOSE_DATE;

    private java.lang.Integer STATE;

    private java.lang.Integer CREDIT_LINE_ID;

    private java.lang.String OP_TYPE;

    private int LOAN_ID;

    private java.lang.String BRANCH_ID;

    private java.lang.String CLIENT_ID;

    public Loan() {
    }

    public Loan(
           java.lang.String AGREEMENT_NO,
           java.math.BigDecimal AMOUNT,
           java.lang.String CCY,
           java.util.Calendar START_DATE,
           java.util.Calendar CLOSE_DATE,
           java.lang.Integer STATE,
           java.lang.Integer CREDIT_LINE_ID,
           java.lang.String OP_TYPE,
           int LOAN_ID,
           java.lang.String BRANCH_ID,
           java.lang.String CLIENT_ID) {
           this.AGREEMENT_NO = AGREEMENT_NO;
           this.AMOUNT = AMOUNT;
           this.CCY = CCY;
           this.START_DATE = START_DATE;
           this.CLOSE_DATE = CLOSE_DATE;
           this.STATE = STATE;
           this.CREDIT_LINE_ID = CREDIT_LINE_ID;
           this.OP_TYPE = OP_TYPE;
           this.LOAN_ID = LOAN_ID;
           this.BRANCH_ID = BRANCH_ID;
           this.CLIENT_ID = CLIENT_ID;
    }


    /**
     * Gets the AGREEMENT_NO value for this Loan.
     * 
     * @return AGREEMENT_NO
     */
    public java.lang.String getAGREEMENT_NO() {
        return AGREEMENT_NO;
    }


    /**
     * Sets the AGREEMENT_NO value for this Loan.
     * 
     * @param AGREEMENT_NO
     */
    public void setAGREEMENT_NO(java.lang.String AGREEMENT_NO) {
        this.AGREEMENT_NO = AGREEMENT_NO;
    }


    /**
     * Gets the AMOUNT value for this Loan.
     * 
     * @return AMOUNT
     */
    public java.math.BigDecimal getAMOUNT() {
        return AMOUNT;
    }


    /**
     * Sets the AMOUNT value for this Loan.
     * 
     * @param AMOUNT
     */
    public void setAMOUNT(java.math.BigDecimal AMOUNT) {
        this.AMOUNT = AMOUNT;
    }


    /**
     * Gets the CCY value for this Loan.
     * 
     * @return CCY
     */
    public java.lang.String getCCY() {
        return CCY;
    }


    /**
     * Sets the CCY value for this Loan.
     * 
     * @param CCY
     */
    public void setCCY(java.lang.String CCY) {
        this.CCY = CCY;
    }


    /**
     * Gets the START_DATE value for this Loan.
     * 
     * @return START_DATE
     */
    public java.util.Calendar getSTART_DATE() {
        return START_DATE;
    }


    /**
     * Sets the START_DATE value for this Loan.
     * 
     * @param START_DATE
     */
    public void setSTART_DATE(java.util.Calendar START_DATE) {
        this.START_DATE = START_DATE;
    }


    /**
     * Gets the CLOSE_DATE value for this Loan.
     * 
     * @return CLOSE_DATE
     */
    public java.util.Calendar getCLOSE_DATE() {
        return CLOSE_DATE;
    }


    /**
     * Sets the CLOSE_DATE value for this Loan.
     * 
     * @param CLOSE_DATE
     */
    public void setCLOSE_DATE(java.util.Calendar CLOSE_DATE) {
        this.CLOSE_DATE = CLOSE_DATE;
    }


    /**
     * Gets the STATE value for this Loan.
     * 
     * @return STATE
     */
    public java.lang.Integer getSTATE() {
        return STATE;
    }


    /**
     * Sets the STATE value for this Loan.
     * 
     * @param STATE
     */
    public void setSTATE(java.lang.Integer STATE) {
        this.STATE = STATE;
    }


    /**
     * Gets the CREDIT_LINE_ID value for this Loan.
     * 
     * @return CREDIT_LINE_ID
     */
    public java.lang.Integer getCREDIT_LINE_ID() {
        return CREDIT_LINE_ID;
    }


    /**
     * Sets the CREDIT_LINE_ID value for this Loan.
     * 
     * @param CREDIT_LINE_ID
     */
    public void setCREDIT_LINE_ID(java.lang.Integer CREDIT_LINE_ID) {
        this.CREDIT_LINE_ID = CREDIT_LINE_ID;
    }


    /**
     * Gets the OP_TYPE value for this Loan.
     * 
     * @return OP_TYPE
     */
    public java.lang.String getOP_TYPE() {
        return OP_TYPE;
    }


    /**
     * Sets the OP_TYPE value for this Loan.
     * 
     * @param OP_TYPE
     */
    public void setOP_TYPE(java.lang.String OP_TYPE) {
        this.OP_TYPE = OP_TYPE;
    }


    /**
     * Gets the LOAN_ID value for this Loan.
     * 
     * @return LOAN_ID
     */
    public int getLOAN_ID() {
        return LOAN_ID;
    }


    /**
     * Sets the LOAN_ID value for this Loan.
     * 
     * @param LOAN_ID
     */
    public void setLOAN_ID(int LOAN_ID) {
        this.LOAN_ID = LOAN_ID;
    }


    /**
     * Gets the BRANCH_ID value for this Loan.
     * 
     * @return BRANCH_ID
     */
    public java.lang.String getBRANCH_ID() {
        return BRANCH_ID;
    }


    /**
     * Sets the BRANCH_ID value for this Loan.
     * 
     * @param BRANCH_ID
     */
    public void setBRANCH_ID(java.lang.String BRANCH_ID) {
        this.BRANCH_ID = BRANCH_ID;
    }


    /**
     * Gets the CLIENT_ID value for this Loan.
     * 
     * @return CLIENT_ID
     */
    public java.lang.String getCLIENT_ID() {
        return CLIENT_ID;
    }


    /**
     * Sets the CLIENT_ID value for this Loan.
     * 
     * @param CLIENT_ID
     */
    public void setCLIENT_ID(java.lang.String CLIENT_ID) {
        this.CLIENT_ID = CLIENT_ID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Loan)) return false;
        Loan other = (Loan) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.AGREEMENT_NO==null && other.getAGREEMENT_NO()==null) || 
             (this.AGREEMENT_NO!=null &&
              this.AGREEMENT_NO.equals(other.getAGREEMENT_NO()))) &&
            ((this.AMOUNT==null && other.getAMOUNT()==null) || 
             (this.AMOUNT!=null &&
              this.AMOUNT.equals(other.getAMOUNT()))) &&
            ((this.CCY==null && other.getCCY()==null) || 
             (this.CCY!=null &&
              this.CCY.equals(other.getCCY()))) &&
            ((this.START_DATE==null && other.getSTART_DATE()==null) || 
             (this.START_DATE!=null &&
              this.START_DATE.equals(other.getSTART_DATE()))) &&
            ((this.CLOSE_DATE==null && other.getCLOSE_DATE()==null) || 
             (this.CLOSE_DATE!=null &&
              this.CLOSE_DATE.equals(other.getCLOSE_DATE()))) &&
            ((this.STATE==null && other.getSTATE()==null) || 
             (this.STATE!=null &&
              this.STATE.equals(other.getSTATE()))) &&
            ((this.CREDIT_LINE_ID==null && other.getCREDIT_LINE_ID()==null) || 
             (this.CREDIT_LINE_ID!=null &&
              this.CREDIT_LINE_ID.equals(other.getCREDIT_LINE_ID()))) &&
            ((this.OP_TYPE==null && other.getOP_TYPE()==null) || 
             (this.OP_TYPE!=null &&
              this.OP_TYPE.equals(other.getOP_TYPE()))) &&
            this.LOAN_ID == other.getLOAN_ID() &&
            ((this.BRANCH_ID==null && other.getBRANCH_ID()==null) || 
             (this.BRANCH_ID!=null &&
              this.BRANCH_ID.equals(other.getBRANCH_ID()))) &&
            ((this.CLIENT_ID==null && other.getCLIENT_ID()==null) || 
             (this.CLIENT_ID!=null &&
              this.CLIENT_ID.equals(other.getCLIENT_ID())));
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
        if (getAGREEMENT_NO() != null) {
            _hashCode += getAGREEMENT_NO().hashCode();
        }
        if (getAMOUNT() != null) {
            _hashCode += getAMOUNT().hashCode();
        }
        if (getCCY() != null) {
            _hashCode += getCCY().hashCode();
        }
        if (getSTART_DATE() != null) {
            _hashCode += getSTART_DATE().hashCode();
        }
        if (getCLOSE_DATE() != null) {
            _hashCode += getCLOSE_DATE().hashCode();
        }
        if (getSTATE() != null) {
            _hashCode += getSTATE().hashCode();
        }
        if (getCREDIT_LINE_ID() != null) {
            _hashCode += getCREDIT_LINE_ID().hashCode();
        }
        if (getOP_TYPE() != null) {
            _hashCode += getOP_TYPE().hashCode();
        }
        _hashCode += getLOAN_ID();
        if (getBRANCH_ID() != null) {
            _hashCode += getBRANCH_ID().hashCode();
        }
        if (getCLIENT_ID() != null) {
            _hashCode += getCLIENT_ID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Loan.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "Loan"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("AGREEMENT_NO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "AGREEMENT_NO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("AMOUNT");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "AMOUNT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CCY");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "CCY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("START_DATE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "START_DATE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CLOSE_DATE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "CLOSE_DATE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STATE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "STATE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CREDIT_LINE_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "CREDIT_LINE_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OP_TYPE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "OP_TYPE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("LOAN_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "LOAN_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BRANCH_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "BRANCH_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CLIENT_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.StandardBank.ge/WebServices/", "CLIENT_ID"));
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
