package com.its.tera.constants;

import org.alfresco.service.namespace.QName;

public class CM 
{
	
	
	
	public static final String NAMESPACE_URI = "http://www.its.ge/model/content/base/ksb/1.0";
	public static final String NAMESPACE_URI_SHORT = "ksb-cont";
	
	
	
	
	
	
	
	
	//FROM PN

	
	
	
	
	public static final String TYPE_CLIENT_FOLDER = "clientFolder";	
	public static final String TYPE_GENERAL_AGREEMENT_FOLDER = "genAgreementFolder";	
	public static final String TYPE_AGREEMENT_FOLDER = "agreementFolder";
	public static final String TYPE_ORDINARY_FOLDER = "ordinaryFolder";
	public static final String TYPE_LEGAL_DOC_FOLDER = "legalDocFolder";
	public static final String TYPE_CREDIT_DOC = "creditDoc";	
	
	
	public static final String PROP_ASPECT_CLIENT_CODE = "clientCode";
	public static final String PROP_ASPECT_CLIENT_ID = "clientID";
	public static final String PROP_ASPECT_CLIENT_NAME = "clientName";
	public static final String ASPECT_CLIENT_IS_VIP = "clientIsVIP";
	
	public static final String PROP_ASPECT_REGISTER_DATE = "registerDate";
	public static final String PROP_ASPECT_CREATOR_USER = "creatorUser";
	
	public static final String ASPECT_GEN_AGR_PROPS = "genAgreementProps";
	
	public static final String PROP_ASPECT_GEN_AGR_ID = "gAgreementId";
	public static final String PROP_ASPECT_GEN_AGR_NUM = "gAgreementNumber";
	public static final String PROP_ASPECT_GEN_AGR_AMOUNT = "gAgreementAmount";
	public static final String PROP_ASPECT_GEN_AGR_CURRENCY = "gAgreementCurrency";
	public static final String PROP_ASPECT_GEN_AGR_DATE = "gAgreementDate";
	
	public static final String ASPECT_AGR_PROPS = "agreementProps";
	
	public static final String PROP_ASPECT_AGR_ID = "agreementId";	
	public static final String PROP_ASPECT_AGR_NUM = "agreementNumber";	
	public static final String PROP_ASPECT_AGR_AMOUNT = "agreementAmount";
	public static final String PROP_ASPECT_AGR_CURRENCY = "agreementCurrency";
	public static final String PROP_ASPECT_AGR_PRODUCT = "agreementProduct";
	public static final String PROP_ASPECT_AGR_DATE = "agreementDate";
	
	public static final String ASSOC_ASPECT_ASSOC_FILES = "associatedFiles";
	
	public static final String PROP_REG_NUMBER = "registerNumber";
	public static final String PROP_CORRESPONDENCE_TYPE = "correspondenceType";
	public static final String PROP_DESCRIPTION = "description";
	public static final String PROP_IS_VIP = "isVIP";
	public static final String PROP_IS_EMPTY = "isEmpty";
	public static final String PROP_IS_NOT_TRULI_REGISTERED = "isNotRegistered";
	
	public static final String ASPECT_ITEM_IS_EMPTY = "itemIsEmpty";
	
	
	
	public static final String FIND_IN_ALL_PARAMETERS = "findInAllParameters";
	
	
	
	
	public static final String PROP_ASPECT_BRANCH = "branch";
	
//	public static final String ASPECT_GEN_AGR_DOC_PROPS = "genAgrDocProps";
//	public static final String PROP_ASPECT_GEN_AGR_DOC_ID = "gAgrDocId";
//	public static final String PROP_ASPECT_GEN_AGR_DOC_NUM = "gAgrDocNumber";
//	
//	public static final String ASPECT_AGR_DOC_PROPS = "agrDocProps";
//	public static final String PROP_ASPECT_AGR_DOC_ID = "agrDocId";	
//	public static final String PROP_ASPECT_AGR_DOC_NUM = "agrDocNumber";
	
	public static final String ASPECT_LEGAL_DOCS = "legalDocs";
	
	public static final String ASSOC_ASPECT_ASSOC_GEN_AGRS = "assocGenAgrs";
	public static final String ASSOC_ASPECT_ASSOC_GEN_CHILD_AGRS = "assocGenChildAgrs";
	public static final String ASSOC_ASPECT_ASSOC_AGRS = "assocAgrs";
	public static final String ASSOC_ASPECT_ASSOC_LEGALS = "assocLegals";
	
	
	
	
	
	
	
	
	
		
	
	
	// TYPES			
	public static final QName QNAME_TYPE_CLIENT_FOLDER = QName.createQName(NAMESPACE_URI, TYPE_CLIENT_FOLDER);		
			
	public static final QName QNAME_TYPE_GENERAL_AGREEMENT_FOLDER = QName.createQName(NAMESPACE_URI, TYPE_GENERAL_AGREEMENT_FOLDER);
	public static final QName QNAME_TYPE_AGREEMENT_FOLDER = QName.createQName(NAMESPACE_URI, TYPE_AGREEMENT_FOLDER);	
	public static final QName QNAME_TYPE_ORDINARY_FOLDER = QName.createQName(NAMESPACE_URI, TYPE_ORDINARY_FOLDER);
	public static final QName QNAME_TYPE_LEGAL_DOC_FOLDER = QName.createQName(NAMESPACE_URI, TYPE_LEGAL_DOC_FOLDER);
	
	public static final QName QNAME_TYPE_CREDIT_DOC = QName.createQName(NAMESPACE_URI, TYPE_CREDIT_DOC);	
	
		
		
	// PROPERTIES
	
			//ASPECT PROPERTIES
		
	public static final QName QNAME_PROP_ASPECT_CLIENT_CODE = QName.createQName(NAMESPACE_URI, PROP_ASPECT_CLIENT_CODE);
	public static final QName QNAME_PROP_ASPECT_CLIENT_ID = QName.createQName(NAMESPACE_URI, PROP_ASPECT_CLIENT_ID);
	public static final QName QNAME_PROP_ASPECT_CLIENT_NAME = QName.createQName(NAMESPACE_URI, PROP_ASPECT_CLIENT_NAME);
	
	public static final QName QNAME_PROP_ASPECT_REGISTER_DATE = QName.createQName(NAMESPACE_URI, PROP_ASPECT_REGISTER_DATE);
	public static final QName QNAME_PROP_ASPECT_CREATOR_USER = QName.createQName(NAMESPACE_URI, PROP_ASPECT_CREATOR_USER);
	
	public static final QName QNAME_ASPECT_CLIENT_IS_VIP = QName.createQName(NAMESPACE_URI, ASPECT_CLIENT_IS_VIP);
	
	// GEN AGR PROPERTIES
	
	public static final QName QNAME_ASPECT_GEN_AGR_PROPS = QName.createQName(NAMESPACE_URI, ASPECT_GEN_AGR_PROPS); 	

	public static final QName QNAME_PROP_ASPECT_GEN_AGR_ID = QName.createQName(NAMESPACE_URI, PROP_ASPECT_GEN_AGR_ID);
	public static final QName QNAME_PROP_ASPECT_GEN_AGR_NUM = QName.createQName(NAMESPACE_URI, PROP_ASPECT_GEN_AGR_NUM);
	public static final QName QNAME_PROP_ASPECT_GEN_AGR_AMOUNT = QName.createQName(NAMESPACE_URI, PROP_ASPECT_GEN_AGR_AMOUNT);
	public static final QName QNAME_PROP_ASPECT_GEN_AGR_CURRENCY = QName.createQName(NAMESPACE_URI, PROP_ASPECT_GEN_AGR_CURRENCY);
	public static final QName QNAME_PROP_ASPECT_GEN_AGR_DATE = QName.createQName(NAMESPACE_URI, PROP_ASPECT_GEN_AGR_DATE);
	
	// AGR PROPERTIES
	
	public static final QName QNAME_ASPECT_AGR_PROPS = QName.createQName(NAMESPACE_URI, ASPECT_AGR_PROPS); 		
	
	public static final QName QNAME_PROP_ASPECT_AGR_ID = QName.createQName(NAMESPACE_URI, PROP_ASPECT_AGR_ID);
	public static final QName QNAME_PROP_ASPECT_AGR_NUM = QName.createQName(NAMESPACE_URI, PROP_ASPECT_AGR_NUM);
	public static final QName QNAME_PROP_ASPECT_AGR_AMOUNT = QName.createQName(NAMESPACE_URI, PROP_ASPECT_AGR_AMOUNT);
	public static final QName QNAME_PROP_ASPECT_AGR_CURRENCY = QName.createQName(NAMESPACE_URI, PROP_ASPECT_AGR_CURRENCY);
	public static final QName QNAME_PROP_ASPECT_AGR_PRODUCT = QName.createQName(NAMESPACE_URI, PROP_ASPECT_AGR_PRODUCT);
	public static final QName QNAME_PROP_ASPECT_AGR_DATE = QName.createQName(NAMESPACE_URI, PROP_ASPECT_AGR_DATE);
	
	
	public static final QName QNAME_ASPECT_ITEM_IS_EMPTY = QName.createQName(NAMESPACE_URI, ASPECT_ITEM_IS_EMPTY); 
	
	public static final QName QNAME_ASSOC_ASPECT_ASSOC_FILES = QName.createQName(NAMESPACE_URI, ASSOC_ASPECT_ASSOC_FILES);
	
	
	
			//DOCUMENT PROPERTIES	
	
	public static final QName QNAME_PROP_REG_NUMBER = QName.createQName(NAMESPACE_URI, PROP_REG_NUMBER);
	public static final QName QNAME_PROP_CORRESPONDENCE_TYPE = QName.createQName(NAMESPACE_URI, PROP_CORRESPONDENCE_TYPE);
	public static final QName QNAME_PROP_DESCRIPTION = QName.createQName(NAMESPACE_URI, PROP_DESCRIPTION);
	public static final QName QNAME_PROP_IS_VIP = QName.createQName(NAMESPACE_URI, PROP_IS_VIP);
	
		

	// QQQQUERY	
	
	public static final String QUERY_COMPANY_HOME = "PATH:\"/app:company_home\"";
	
	public static final String QUERY_DATA_DICTIONARY = "PATH:\"/app:company_home/app:Data_x0020_Dictionary\"";
	
	public static final String QUERY_PRESENTATION_TEMPLATES = "PATH:\"/app:company_home/app:Data_x0020_Dictionary/app:Presentation_x0020_Templates\"";
	
	public static final String QUERY_SPACE_TEMPLATE = "PATH:\"/app:company_home/app:dictionary/app:content_templates/cm:juridical_template.ftl\"";
	
	public static final String QUERY_CONTENT_TEMPLATE = "PATH:\"/app:company_home/app:dictionary/app:content_templates/cm:doc_info_77.ftl\"";
	
	public static final String QUERY_RESOLUTION_TEMPLATES = "PATH:\"/app:company_home/app:dictionary/cm:Resolution_x0020_Templates/*\"";
	
	
	
	
	public static final QName QNAME_PROP_ASPECT_BRANCH = QName.createQName(NAMESPACE_URI, PROP_ASPECT_BRANCH);
	
//	public static final QName QNAME_ASPECT_GEN_AGR_DOC_PROPS = QName.createQName(NAMESPACE_URI, ASPECT_GEN_AGR_DOC_PROPS);
//	public static final QName QNAME_PROP_ASPECT_GEN_AGR_DOC_ID = QName.createQName(NAMESPACE_URI, PROP_ASPECT_GEN_AGR_DOC_ID);
//	public static final QName QNAME_PROP_ASPECT_GEN_AGR_DOC_NUM = QName.createQName(NAMESPACE_URI, PROP_ASPECT_GEN_AGR_DOC_NUM);
//	
//	public static final QName QNAME_ASPECT_AGR_DOC_PROPS = QName.createQName(NAMESPACE_URI, ASPECT_AGR_DOC_PROPS);
//	public static final QName QNAME_PROP_ASPECT_AGR_DOC_ID = QName.createQName(NAMESPACE_URI, PROP_ASPECT_AGR_DOC_ID);
//	public static final QName QNAME_PROP_ASPECT_AGR_DOC_NUM = QName.createQName(NAMESPACE_URI, PROP_ASPECT_AGR_DOC_NUM);
	
	public static final QName QNAME_ASSOC_ASPECT_GEN_AGRS = QName.createQName(NAMESPACE_URI, ASSOC_ASPECT_ASSOC_GEN_AGRS);
	public static final QName QNAME_ASSOC_ASPECT_GEN_CHILD_AGRS = QName.createQName(NAMESPACE_URI, ASSOC_ASPECT_ASSOC_GEN_CHILD_AGRS);
	public static final QName QNAME_ASSOC_ASPECT_AGRS = QName.createQName(NAMESPACE_URI, ASSOC_ASPECT_ASSOC_AGRS);
	
	public static final QName QNAME_ASSOC_ASPECT_LEGALS = QName.createQName(NAMESPACE_URI, ASSOC_ASPECT_ASSOC_LEGALS);
	
	public static final QName QNAME_ASPECT_LEGAL_DOCS = QName.createQName(NAMESPACE_URI, ASPECT_LEGAL_DOCS);
	
	
	
	
	//ARCHIVE ASPECTS
	
	public static final String ASPECT_FOR_ARCHIVE = "forArchive";
	public static final String PROP_ASPECT_ARCHIVE_USER = "archUser";
	
	public static final String ASPECT_FOR_LIVE = "forLive";
	public static final String PROP_ASPECT_LIVE_USER = "liveUser";
	
	public static final String ASPECT_ARCHIVE_HISTORY = "archiveHistory";
	public static final String PROP_ASPECT_HISTORY_DATE = "historyDate";
	
	public static final String ASPECT_HAS_ARCHIVE = "hasArchive";
	
	public static final QName QNAME_ASPECT_FOR_ARCHIVE = QName.createQName(NAMESPACE_URI, ASPECT_FOR_ARCHIVE);
	public static final QName QNAME_PROP_ASPECT_ARCHIVE_USER = QName.createQName(NAMESPACE_URI, PROP_ASPECT_ARCHIVE_USER);
	
	public static final QName QNAME_ASPECT_FOR_LIVE = QName.createQName(NAMESPACE_URI, ASPECT_FOR_LIVE);
	public static final QName QNAME_PROP_ASPECT_LIVE_USER = QName.createQName(NAMESPACE_URI, PROP_ASPECT_LIVE_USER);
	
	public static final QName QNAME_ASPECT_ARCHIVE_HISTORY = QName.createQName(NAMESPACE_URI, ASPECT_ARCHIVE_HISTORY);
	public static final QName QNAME_PROP_ASPECT_HISTORY_DATE = QName.createQName(NAMESPACE_URI, PROP_ASPECT_HISTORY_DATE);
	
	public static final QName QNAME_ASPECT_HAS_ARCHIVE = QName.createQName(NAMESPACE_URI, ASPECT_HAS_ARCHIVE);
	
	
	
	


}
