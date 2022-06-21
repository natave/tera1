package com.its.tera.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.namespace.QName;

import com.its.tera.constants.CM;
import com.its.tera.model.Agreement;
import com.its.tera.model.Document;

public class PropsUtil {	
	
	
	
	
	public  static  Map<QName, Serializable> getDocModelProps(Object object){
		
		Map<QName, Serializable> props = new HashMap<QName, Serializable>();
		
		Document model = (Document) object;
		
		//TYPE
		//setQueryProp(props, "type", model.getType());
		setQueryProp(props, ContentModel.PROP_NAME, model.getDocumentName());
		
//		setQueryProp(props, ContentModel.PROP_CREATED, model.getCreated());
//		setQueryProp(props, ContentModel.PROP_CREATOR, model.getCreator());
//		setQueryProp(props, ContentModel.PROP_MODIFIED, model.getEdited());
//		setQueryProp(props, ContentModel.PROP_MODIFIER, model.getEditor());
		
//		setQueryProp(props, ContentModel.PROP_NAME, model.getName());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_BRANCH, model.getBranch());
		
		
		//CLIENT PROPS
		setQueryProp(props, CM.QNAME_PROP_ASPECT_CLIENT_CODE, model.getClientCode());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_CLIENT_ID, model.getClientId());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_CLIENT_NAME, model.getClientName());
		
		
		// G AGR PROPS
		setQueryProp(props, CM.QNAME_PROP_ASPECT_GEN_AGR_NUM, model.getGagrNumber());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_GEN_AGR_ID, model.getGagrId());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_GEN_AGR_DATE, model.getGagrDate());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_GEN_AGR_AMOUNT, model.getGagrAmount());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_GEN_AGR_CURRENCY, model.getGagrCurrency());
		
		
		//  AGR PROPS
		setQueryProp(props, CM.QNAME_PROP_ASPECT_AGR_NUM, model.getAgrNumber());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_AGR_ID, model.getAgrId());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_AGR_DATE, model.getDate());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_AGR_AMOUNT, model.getAmount());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_AGR_CURRENCY, model.getCurrency());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_AGR_PRODUCT, model.getProductType());
				
		
//		DOC PROPS
		
		setQueryProp(props, CM.QNAME_PROP_IS_VIP, model.getVIP());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_CREATOR_USER, model.getCreatorUser());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_REGISTER_DATE, model.getRegDate());
				
		setQueryProp(props, CM.QNAME_PROP_REG_NUMBER, model.getRegNumber());
		setQueryProp(props, CM.QNAME_PROP_CORRESPONDENCE_TYPE, model.getCorrType());		
		setQueryProp(props, CM.QNAME_PROP_DESCRIPTION, model.getDescription());
		
//		
		
		return props;
		
	}
	
	
public  static  Map<QName, Serializable> getAgrModelProps(Object object){
		
		Map<QName, Serializable> props = new HashMap<QName, Serializable>();
		
		Agreement model = (Agreement) object;
		
		//TYPE
		//setQueryProp(props, "type", model.getType());
		setQueryProp(props, ContentModel.PROP_NAME, model.getName());
		
//		setQueryProp(props, ContentModel.PROP_CREATED, model.getCreated());
//		setQueryProp(props, ContentModel.PROP_CREATOR, model.getCreator());
//		setQueryProp(props, ContentModel.PROP_MODIFIED, model.getEdited());
//		setQueryProp(props, ContentModel.PROP_MODIFIER, model.getEditor());
		
//		setQueryProp(props, ContentModel.PROP_NAME, model.getName());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_BRANCH, model.getBranch());
		
		
		//CLIENT PROPS
		setQueryProp(props, CM.QNAME_PROP_ASPECT_CLIENT_CODE, model.getClientCode());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_CLIENT_ID, model.getClientId());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_CLIENT_NAME, model.getClientName());
		
		
		// G AGR PROPS
		setQueryProp(props, CM.QNAME_PROP_ASPECT_GEN_AGR_NUM, model.getGagrNumber());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_GEN_AGR_ID, model.getGagrId());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_GEN_AGR_DATE, model.getGagrDate());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_GEN_AGR_AMOUNT, model.getGagrAmount());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_GEN_AGR_CURRENCY, model.getGagrCurrency());
		
		
		//  AGR PROPS
		setQueryProp(props, CM.QNAME_PROP_ASPECT_AGR_NUM, model.getAgrNumber());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_AGR_ID, model.getAgrId());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_AGR_DATE, model.getDate());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_AGR_AMOUNT, model.getAmount());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_AGR_CURRENCY, model.getCurrency());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_AGR_PRODUCT, model.getProductType());
				
		
//		DOC PROPS
		
		//setQueryProp(props, CM.QNAME_PROP_IS_VIP, model.getVIP());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_CREATOR_USER, model.getCreatorUser());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_REGISTER_DATE, model.getRegDate());
				
		setQueryProp(props, CM.QNAME_ASPECT_CLIENT_IS_VIP, model.getClientIsVIP());
//		setQueryProp(props, CM.QNAME_PROP_CORRESPONDENCE_TYPE, model.getCorrType());		
//		setQueryProp(props, CM.QNAME_PROP_DESCRIPTION, model.getDescription());
		
//		
		
		setQueryProp(props, CM.QNAME_PROP_ASPECT_ARCHIVE_USER, model.getArchUser());
		setQueryProp(props, CM.QNAME_ASPECT_ARCHIVE_HISTORY, (Serializable) model.getArchHistoryString());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_HISTORY_DATE, model.getHistoryDate());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_LIVE_USER, model.getLiveUser());
		
		return props;
		
	}
	
	
	
	
	
	
	private static void setQueryProp(Map<QName, Serializable> props, QName propQName, Serializable value){
//		if(value == null || (value instanceof String && ((String) value).isEmpty() ))
		if(value != null )
		{
			props.put(propQName, value);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
