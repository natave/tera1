package com.its.tera.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.dictionary.DataTypeDefinition;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.namespace.NamespacePrefixResolver;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;

import com.its.tera.constants.CM;
import com.its.tera.model.Document;
import com.its.tera.model.TechModel;



public class QueryUtil {
	
	public static final Log logger = LogFactory.getLog(QueryUtil.class);
	//public static final String AND_FOR_QUERY = " AND ";
	public static final String FIND_IN_ALL_PARAMETERS = "findInAllParameters";
	
	
	public static final String AND_FOR_QUERY = " AND ";
	public static final String OR_FOR_QUERY = " OR ";
	public static final String DOC_TYPE_KEY = "type";
	public static final String IS_ARCHIVED_KEY = "isArchived";
	
	public static final Integer STRING = 0;
	public static final Integer DATE = 1;
	public static final Integer LONG = 2;
	public static final Integer BOOLEAN = 3;
	public static final Integer DATETIME = 4;
	public static final Integer FLOAT = 5;
	
	
	
	
	
	
	
	public  static  Map<String, Serializable> getQueryProps(Object searchParams, /*Map<String, String> props,*/ NamespacePrefixResolver namespacePrefixResolver){
		
		Map<String, Serializable> props = new HashMap<String, Serializable>();
		
		TechModel model = (TechModel) searchParams;
		
		//TYPE
		setQueryProp(props, "type", model.getType());
		
		setQueryProp(props, ContentModel.PROP_CREATED.toPrefixString(namespacePrefixResolver), model.getCreated());
		setQueryProp(props, ContentModel.PROP_CREATED.getLocalName(), model.getCreated_to());
		
		setQueryProp(props, ContentModel.PROP_CREATOR.toPrefixString(namespacePrefixResolver), model.getCreator());
		
		setQueryProp(props, ContentModel.PROP_MODIFIED.toPrefixString(namespacePrefixResolver), model.getEdited());
		setQueryProp(props, ContentModel.PROP_MODIFIED.getLocalName(), model.getEdited_to());
		
		setQueryProp(props, ContentModel.PROP_MODIFIER.toPrefixString(namespacePrefixResolver), model.getEditor());
		//setQueryProp(props, ContentModel.PROP_MODIFIED.getPrefixedQName(namespacePrefixResolver).getPrefixString(), model.getModified());
		//setQueryProp(props, ContentModel.PROP_MODIFIER.getPrefixedQName(namespacePrefixResolver).getPrefixString(), model.getModifier());
		
		//setQueryProp(props, ContentModel.PROP_NAME.getPrefixedQName(namespacePrefixResolver).getPrefixString(), model.getName());
		//setQueryProp(props, ContentModel.PROP_DESCRIPTION.getPrefixedQName(namespacePrefixResolver).getPrefixString(), model.getDescription());
		
		
		//CLIENT
		setQueryProp(props, CM.QNAME_PROP_ASPECT_CLIENT_CODE.toPrefixString(namespacePrefixResolver), model.getClientCode());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_CLIENT_ID.toPrefixString(namespacePrefixResolver), model.getClientId());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_CLIENT_NAME.toPrefixString(namespacePrefixResolver), model.getClientName());
		
		
		//GENERAL AGREEMENT
		setQueryProp(props, CM.QNAME_PROP_ASPECT_GEN_AGR_NUM.toPrefixString(namespacePrefixResolver), model.getGagrNumber());
		
		//					agreements jurnal
		setQueryProp(props, CM.QNAME_PROP_ASPECT_GEN_AGR_ID.toPrefixString(namespacePrefixResolver), model.getGagrId());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_GEN_AGR_AMOUNT.toPrefixString(namespacePrefixResolver), model.getGagrAmount());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_GEN_AGR_CURRENCY.toPrefixString(namespacePrefixResolver), model.getGagrCurrency());
		
		setQueryProp(props, CM.QNAME_PROP_ASPECT_GEN_AGR_DATE.toPrefixString(namespacePrefixResolver), model.getGagrDate());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_GEN_AGR_DATE.getLocalName(), model.getGagrDate_to());
		
		//AGREEMENT
		setQueryProp(props, CM.QNAME_PROP_ASPECT_AGR_NUM.toPrefixString(namespacePrefixResolver), model.getAgrNumber());
		
		//					agreements jurnal
		setQueryProp(props, CM.QNAME_PROP_ASPECT_AGR_ID.toPrefixString(namespacePrefixResolver), model.getAgrId());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_AGR_PRODUCT.toPrefixString(namespacePrefixResolver), model.getProductType());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_AGR_AMOUNT.toPrefixString(namespacePrefixResolver), model.getAmount());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_AGR_CURRENCY.toPrefixString(namespacePrefixResolver), model.getCurrency());
		
		setQueryProp(props, CM.QNAME_PROP_ASPECT_AGR_DATE.toPrefixString(namespacePrefixResolver), model.getDate());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_AGR_DATE.getLocalName(), model.getDate_to());
		
		
		//DOCUMENT
		setQueryProp(props, CM.QNAME_PROP_REG_NUMBER.toPrefixString(namespacePrefixResolver), model.getRegNumber());
		setQueryProp(props, CM.QNAME_PROP_CORRESPONDENCE_TYPE.toPrefixString(namespacePrefixResolver), model.getCorrType());
		
		setQueryProp(props, CM.QNAME_PROP_ASPECT_REGISTER_DATE.toPrefixString(namespacePrefixResolver), model.getRegDate());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_REGISTER_DATE.getLocalName(), model.getRegDate_to());
		
		
		setQueryProp(props, CM.QNAME_PROP_DESCRIPTION.toPrefixString(namespacePrefixResolver), model.getDescription());
		
		if(model.getIsEmpty() != null) {
			setQueryProp(props, CM.PROP_IS_EMPTY, model.getIsEmpty());
		}
		
		if(model.getNotProperlyRegistered() != null) {
			setQueryProp(props, CM.PROP_IS_NOT_TRULI_REGISTERED, model.getNotProperlyRegistered());
		}
		
		if(model.getVIP() != null) {
			setQueryProp(props, CM.PROP_IS_VIP, model.getVIP());
		}
		
		if(model.getIsLegal() != null) {
			setQueryProp(props, CM.ASPECT_LEGAL_DOCS, model.getIsLegal());
		}
		
		if(model.getForArchive() != null) {
			setQueryProp(props, CM.ASPECT_FOR_ARCHIVE, model.getForArchive());
		}
		
		if(model.getForLive() != null) {
			setQueryProp(props, CM.ASPECT_FOR_LIVE, model.getForLive());
		}
		
		if(model.getHasArchiveHistory() != null) {
			setQueryProp(props, CM.ASPECT_ARCHIVE_HISTORY, model.getHasArchiveHistory());
		}
		
		setQueryProp(props, CM.QNAME_PROP_ASPECT_ARCHIVE_USER.toPrefixString(namespacePrefixResolver), model.getArchUser());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_LIVE_USER.toPrefixString(namespacePrefixResolver), model.getLiveUser());
		
		setQueryProp(props, CM.QNAME_PROP_ASPECT_HISTORY_DATE.toPrefixString(namespacePrefixResolver), model.getHistoryDate());
		setQueryProp(props, CM.QNAME_PROP_ASPECT_HISTORY_DATE.getLocalName(), model.getHistoryDate_to());
		
		
//		
//		setQueryProp(props, CM.Q_ASPECT_STATUS.getPrefixedQName(namespacePrefixResolver).getPrefixString(), 
//				model.getStatus() == null ? null : model.getStatus().toString());
//	//		
//		setQueryProp(props, CM.Q_ASPECT_PRIORITY.getPrefixedQName(namespacePrefixResolver).getPrefixString(), 
//				model.getPriority() == null ? null : model.getPriority().toString());
//		
//		setQueryProp(props, CM.Q_PROP_SENT_DATE.getPrefixedQName(namespacePrefixResolver).getPrefixString(), model.getSentDate());
//		setQueryProp(props, CM.Q_PROP_DOC_NUMBER.getPrefixedQName(namespacePrefixResolver).getPrefixString(), model.getDocNumber());
//		
//		setQueryProp(props, CM.ASPECT_IN_CONTROL, 
//				model.getInControl() == null ? null : model.getInControl().toString());
//		
//		setQueryProp(props, CM.Q_ASPECT_BLANK_NUMBER.getPrefixedQName(namespacePrefixResolver).getPrefixString(), model.getBlankNumber());
//		setQueryProp(props, CM.Q_PROP_SIGNATORY.getPrefixedQName(namespacePrefixResolver).getPrefixString(), model.getSignatory());
//		setQueryProp(props, CM.Q_PROP_PERFORMER.getPrefixedQName(namespacePrefixResolver).getPrefixString(), model.getPerformer());
//		
//		
//		setQueryProp(props, CM.ASPECT_IS_ARCHIVE, 
//				model.getIsArchive() == null ? null : model.getIsArchive().toString());
//		setQueryProp(props, CM.Q_PROP_ARCHIVE_DATE.getPrefixedQName(namespacePrefixResolver).getPrefixString(), model.getArchiveDate());
//		setQueryProp(props, CM.Q_PROP_ARCHIVE_USER.getPrefixedQName(namespacePrefixResolver).getPrefixString(), model.getArchiveUser());
//		setQueryProp(props, CM.Q_PROP_ARCHIVE_REASON.getPrefixedQName(namespacePrefixResolver).getPrefixString(), model.getArchiveReson());
		
		return props;
		
	}
	
//	private static void setQueryProp(Map<String, Serializable> props, String prefixString, Serializable value){
//		if(value != null && (value instanceof String && !((String) value).isEmpty() )){
//			props.put(prefixString, value);
//		}
//	}
	
	
	private static void setQueryProp(Map<String, Serializable> props, String prefixString, Serializable value){
		if(value != null ){
			props.put(prefixString, value);
		}
	}
	
	
	
	
	
	
	
	public static String composeQuery(/*Map<String, Serializable> props*/Document doc, String QUERY_JOIN, DictionaryService dictionaryService, 
			NamespacePrefixResolver namespacePrefixResolver, boolean withAutoArchive)
	{
		Map<String, Serializable> props = getQueryProps(doc, namespacePrefixResolver);		
		 
		String query = null;			 
		
		if(props == null){
			return null;
		}
		if(props.get(ContentModel.PROP_CREATOR.toPrefixString(namespacePrefixResolver)) != null) {
			logger.debug("q creator = " + props.get(ContentModel.PROP_CREATOR.toPrefixString(namespacePrefixResolver)));
		}
		
		if(props.containsKey("type")) {
			if(((String) props.get("type")).equalsIgnoreCase(CM.TYPE_CREDIT_DOC)) {
				query = "TYPE:\"" + CM.QNAME_TYPE_CREDIT_DOC.toString() + "\"";
			} 
			else if(((String) props.get("type")).equalsIgnoreCase(CM.TYPE_AGREEMENT_FOLDER)) {
				query = "(TYPE:\"" + CM.QNAME_TYPE_GENERAL_AGREEMENT_FOLDER.toString() + "\"" 
						+ " OR " +"TYPE:\"" + CM.QNAME_TYPE_AGREEMENT_FOLDER.toString() + "\")";
			} 
			else if(((String)props.get("type")).equalsIgnoreCase(CM.TYPE_CLIENT_FOLDER)) {
				query = "TYPE:\"" + CM.QNAME_TYPE_CLIENT_FOLDER.toString() + "\"";
			} 
//				else if(props.get("type").equalsIgnoreCase(CM.TYPE_HR_ORDER)) {
//				query = "TYPE:\"" + CM.Q_TYPE_HR_ORDER.toString() + "\"";
//			} else {
//				query = "TYPE:\"" + CM.Q_TYPE_ROOT_DOC.toString() + "\"";
//			}
		} else {
//			query = "TYPE:\"" + CM.Q_TYPE_ROOT_DOC.toString() + "\"";
		}
		
		//if(props.containsKey(CM.ASPECT_IN_CONTROL)){
//			if(props.get(CM.ASPECT_IN_CONTROL).equalsIgnoreCase("false")){
//				query = query + AND_FOR_QUERY + "(NOT ASPECT:\""+ CM.Q_ASPECT_IN_CONTROL + "\" OR " 
//						+ queryForLongAndBoolean(CM.CH_MODEL_URI_SHORT, CM.ASPECT_IN_CONTROL, "false"/*props.get(CM.ASPECT_SIGNED_DOC_BLOCK)*/) + ")";
//			}
			//if(props.get(CM.ASPECT_IN_CONTROL).equalsIgnoreCase("false")){
				//query = query + AND_FOR_QUERY + "(ISNULL:\""+ CM.Q_ASPECT_IN_CONTROL + "\" OR " 
						//+ queryForLongAndBoolean(CM.CH_MODEL_URI_SHORT, CM.ASPECT_IN_CONTROL, "false"/*props.get(CM.ASPECT_SIGNED_DOC_BLOCK)*/) + ")";
			//}else if(props.get(CM.ASPECT_IN_CONTROL).equalsIgnoreCase("true")){
				//query = query + AND_FOR_QUERY + "ASPECT:\""+ CM.Q_ASPECT_IN_CONTROL + "\"" + AND_FOR_QUERY 
						//+ queryForLongAndBoolean(CM.CH_MODEL_URI_SHORT, CM.ASPECT_IN_CONTROL, "true"/*props.get(CM.ASPECT_SIGNED_DOC_BLOCK)*/);
			//}
		//}
		
		//SEARCH BY ARCHIVE
//		if(props.containsKey(CM.ASPECT_IS_ARCHIVE)) {
//			
//			if(props.get(CM.ASPECT_IS_ARCHIVE).equalsIgnoreCase("true")) {
//				query = query + AND_FOR_QUERY + "ASPECT:\""+ CM.Q_ASPECT_IS_ARCHIVE + "\"";
//			} else if(props.get(CM.ASPECT_IS_ARCHIVE).equalsIgnoreCase("false")) {
//				query = query + AND_FOR_QUERY + "-ASPECT:\""+ CM.Q_ASPECT_IS_ARCHIVE + "\"";
//			}
//			
//		}else if(withAutoArchive) {
//			query = query + AND_FOR_QUERY + "-ASPECT:\""+ CM.Q_ASPECT_IS_ARCHIVE + "\"";
//		}
		
		
		System.out.println();
		for(String key : props.keySet())
		{		
			if(key.equalsIgnoreCase(CM.PROP_IS_EMPTY))
			{
				Boolean value = (Boolean) props.get(key); //Boolean.getBoolean(props.get(key));
				if(value)
				{							
					query = query + QUERY_JOIN + "ASPECT:\""+ CM.QNAME_ASPECT_ITEM_IS_EMPTY + "\"";
				}
				else
				{
					query = query + QUERY_JOIN +  "NOT ASPECT:\""+ CM.QNAME_ASPECT_ITEM_IS_EMPTY + "\"";
				}				
				//finalQuery = finalQuery + AND_FOR_QUERY + thisQuery;
				continue;
			}
			else if(key.equalsIgnoreCase(CM.PROP_IS_NOT_TRULI_REGISTERED))
			{
				Boolean value = (Boolean) props.get(key); //Boolean.getBoolean(props.get(key));
				if(value)
				{
					query = query + QUERY_JOIN + "-EXISTS:\""/* "ISNULL:\"" */ + CM.QNAME_PROP_CORRESPONDENCE_TYPE + "\"";
				}
				else
				{
					query = query + QUERY_JOIN +  "ISNOTNULL:\"" + CM.QNAME_PROP_CORRESPONDENCE_TYPE + "\"";
				}	
				//finalQuery = finalQuery + AND_FOR_QUERY + thisQuery;
				continue;
			}
			else if(key.equalsIgnoreCase(CM.PROP_IS_VIP))
			{
				Boolean value = (Boolean) props.get(key); //Boolean.getBoolean(props.get(key));
				if(value)
				{							
					query = query + QUERY_JOIN +  "ASPECT:\""+ CM.QNAME_ASPECT_CLIENT_IS_VIP + "\"";
				}
				else
				{
					query = query + QUERY_JOIN +  "NOT ASPECT:\""+ CM.QNAME_ASPECT_CLIENT_IS_VIP + "\"";
				}				
				//finalQuery = finalQuery + AND_FOR_QUERY + thisQuery;
				continue;
			}
			else if(key.equalsIgnoreCase(CM.ASPECT_LEGAL_DOCS))
			{
				Boolean value = (Boolean) props.get(key); //Boolean.getBoolean(props.get(key));
				if(value)
				{							
					query = query + QUERY_JOIN +  "ASPECT:\""+ CM.QNAME_ASPECT_LEGAL_DOCS + "\"";
				}
				else
				{
					query = query + QUERY_JOIN +  "NOT ASPECT:\""+ CM.QNAME_ASPECT_LEGAL_DOCS + "\"";
				}				
				//finalQuery = finalQuery + AND_FOR_QUERY + thisQuery;
				continue;
			}
			else if(key.equalsIgnoreCase(CM.ASPECT_FOR_ARCHIVE))
			{
				Boolean value = (Boolean) props.get(key); //Boolean.getBoolean(props.get(key));
				if(value)
				{							
					query = query + QUERY_JOIN +  "ASPECT:\""+ CM.QNAME_ASPECT_FOR_ARCHIVE + "\"";
				}
				else
				{
					query = query + QUERY_JOIN +  "NOT ASPECT:\""+ CM.QNAME_ASPECT_FOR_ARCHIVE + "\"";
				}				
				//finalQuery = finalQuery + AND_FOR_QUERY + thisQuery;
				continue;
			}
			else if(key.equalsIgnoreCase(CM.ASPECT_FOR_LIVE))
			{
				Boolean value = (Boolean) props.get(key); 
				if(value)
				{							
					query = query + QUERY_JOIN +  "ASPECT:\""+ CM.QNAME_ASPECT_FOR_LIVE + "\"";
				}
				else
				{
					query = query + QUERY_JOIN +  "NOT ASPECT:\""+ CM.QNAME_ASPECT_FOR_LIVE + "\"";
				}
				continue;
			}
			else if(key.equalsIgnoreCase(CM.ASPECT_ARCHIVE_HISTORY))
			{
				Boolean value = (Boolean) props.get(key); 
				if(value)
				{							
					query = query + QUERY_JOIN +  "ASPECT:\""+ CM.QNAME_ASPECT_ARCHIVE_HISTORY + "\"";
				}
				else
				{
					query = query + QUERY_JOIN +  "NOT ASPECT:\""+ CM.QNAME_ASPECT_ARCHIVE_HISTORY + "\"";
				}
				continue;
			}
			else if(key.contains(":"))
			{
				String prefix = key.substring(0, key.indexOf(":"));
				String name = key.substring(key.indexOf(":") + 1);	
				
				Integer valType = getValueType(key, dictionaryService, namespacePrefixResolver);
				
				if(valType == STRING)
				{
					query = query == null ? queryForString(prefix, name, (String) props.get(key)) : query + QUERY_JOIN + queryForString(prefix, name, (String) props.get(key));
				}
				else if(valType == LONG || valType == BOOLEAN)
				{
					query = query == null ? queryForLongAndBoolean(prefix, name, (boolean) props.get(key)) : query + QUERY_JOIN + queryForLongAndBoolean(prefix, name, (boolean) props.get(key));
				}
				else if(valType == FLOAT)
				{
					query = query == null ? queryForFloatAndDouble(prefix, name, (String) props.get(key)) : query + QUERY_JOIN + queryForFloatAndDouble(prefix, name, (String) props.get(key));
				}
				else if(valType == DATE || valType == DATETIME)
				{
					try {logger.debug("key = " + key + ",  prefix = " + prefix + ",  name = " + name + ", nameDate = " + props.get(name));
						Date date_to = props.get(name) != null ? new Date((Long)props.get(name)) : null;
						query = query == null ? 
								queryForDate(prefix, name, new Date((Long)props.get(key)), date_to, valType) : 
										query + QUERY_JOIN + queryForDate(prefix, name, new Date((Long)props.get(key)), date_to, valType);
					} catch (ParseException e) {
						e.printStackTrace();
						throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
					}
				}
			}
		}
		
		
		return query;
	}
	
	
	
	
	
	
	
	public static String composeQuery1(/*Map<String, Serializable> props*/Document doc, String QUERY_JOIN, DictionaryService dictionaryService, 
			NamespacePrefixResolver namespacePrefixResolver, boolean withAutoArchive)
	{
		Map<String, Serializable> props = getQueryProps(doc, namespacePrefixResolver);
		
		 
		String query = null;			 
		
		if(props == null){
			return null;
		}
		
		if(props.containsKey("type")) {
			if(((String) props.get("type")).equalsIgnoreCase(CM.TYPE_CREDIT_DOC)) {
				query = "TYPE:\"" + CM.QNAME_TYPE_CREDIT_DOC.toString() + "\"";
			} 
			else if(((String) props.get("type")).equalsIgnoreCase(CM.TYPE_AGREEMENT_FOLDER)) {
				query = "(TYPE:\"" + CM.QNAME_TYPE_GENERAL_AGREEMENT_FOLDER.toString() + "\"" 
						+ " OR " +"TYPE:\"" + CM.QNAME_TYPE_AGREEMENT_FOLDER.toString() + "\")";
			} 
			else if(((String)props.get("type")).equalsIgnoreCase(CM.TYPE_CLIENT_FOLDER)) {
				query = "TYPE:\"" + CM.QNAME_TYPE_CLIENT_FOLDER.toString() + "\"";
			} 
//				else if(props.get("type").equalsIgnoreCase(CM.TYPE_HR_ORDER)) {
//				query = "TYPE:\"" + CM.Q_TYPE_HR_ORDER.toString() + "\"";
//			} else {
//				query = "TYPE:\"" + CM.Q_TYPE_ROOT_DOC.toString() + "\"";
//			}
		} else {
//			query = "TYPE:\"" + CM.Q_TYPE_ROOT_DOC.toString() + "\"";
		}
		
		
		QUERY_JOIN = AND_FOR_QUERY + " (";
		
		System.out.println();
		for(String key : props.keySet())
		{		
			if(key.contains(":"))
			{
				String prefix = key.substring(0, key.indexOf(":"));
				String name = key.substring(key.indexOf(":") + 1);	
				
				Integer valType = getValueType(key, dictionaryService, namespacePrefixResolver);
				
				if(valType == STRING)
				{
					query = query == null ? queryForString(prefix, name, (String) props.get(key)) : query + QUERY_JOIN + queryForString(prefix, name, (String) props.get(key));
				}
				else if(valType == LONG || valType == BOOLEAN)
				{
					query = query == null ? queryForLongAndBoolean(prefix, name, (boolean) props.get(key)) : query + QUERY_JOIN + queryForLongAndBoolean(prefix, name, (boolean) props.get(key));
				}
				else if(valType == FLOAT)
				{
					query = query == null ? queryForFloatAndDouble(prefix, name, (String) props.get(key)) : query + QUERY_JOIN + queryForFloatAndDouble(prefix, name, (String) props.get(key));
				}
				else if(valType == DATE || valType == DATETIME)
				{
					try {logger.debug("key = " + key + ",  prefix = " + prefix + ",  name = " + name + ", " + props.get(key + "_to"));
						Date date_to = props.get(key + "_to") != null ? new Date((Long)props.get(key)) : null;
						query = query == null ? 
								queryForDate(prefix, name, new Date((Long)props.get(key)), date_to, valType) : 
										query + QUERY_JOIN + queryForDate(prefix, name, new Date((Long)props.get(key)), date_to, valType);
					} catch (ParseException e) {
						e.printStackTrace();
						throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
					}
				}
				QUERY_JOIN = OR_FOR_QUERY;
			}
		}
		
		query = query + " )";
		return query;
	}
	
	
	
	
	
	
	public static String queryForDate(String prefix, String name, Date fromDate, Date toDate, Integer valFormat) throws ParseException
	{		//Date d = new Date(); long l = d.getTime();
//		logger.debug("date   = " + Formatter.getDateFormat(d, Formatter.DATE_FORMAT_FOR_SEARCH) );
//		logger.debug("date l = " + l );
//		logger.debug("date ld= " + Formatter.getDateFormat(new Date(l), Formatter.DATE_FORMAT_FOR_SEARCH) );
		
		
		if(fromDate == null ){
			return "";
		}		
		
		if(toDate == null )
		{
			toDate = new Date();
		}
		
		//logger.debug("fromDate = " + fromDate + ", toDate = " + toDate);
		
		String from = Formatter.getDateFormat(Formatter.startOfDay(fromDate), Formatter.DATE_FORMAT_FOR_SEARCH);
		String to = Formatter.getDateFormat(Formatter.endOfDay(toDate), Formatter.DATE_FORMAT_FOR_SEARCH);
		//logger.debug("from = " + from + ", to = " + to);
		
		return "@" + prefix + "\\:" + name + ":[" + from + " TO " + to + "]";
	}
	
	
	private static String queryForDate_old(String prefix, String name, String value, Integer valFormat) throws ParseException
	{		
		String f = value.substring(0, value.indexOf("$"));
		String t = value.substring(value.indexOf("$") + 1);
		
		if(f == null || f.isEmpty()){
			return "";
		}
		
		Date fromDate = Formatter.getDateFormat(f, Formatter.DATE_FORMAT);
		//logger.debug("fromDate = " + fromDate);
		Date toDate = null;
		if(t == null || t.equalsIgnoreCase("") || t.equalsIgnoreCase(" "))
		{
			toDate = new Date();
		}
		else
		{
			String format = Formatter.DATE_FORMAT;
			/*if(valFormat == DATETIME)
			{
				format = Formatter.DATE_TIME_FORMAT;
			}*/
			toDate = Formatter.getDateFormat(t, format);
		}
		//logger.debug("toDate = " + toDate);
		
		String from = Formatter.getDateFormat(Formatter.startOfDay(fromDate), Formatter.DATE_FORMAT_FOR_SEARCH);
		String to = Formatter.getDateFormat(Formatter.endOfDay(toDate), Formatter.DATE_FORMAT_FOR_SEARCH);
		
		return "@" + prefix + "\\:" + name + ":[" + from + " TO " + to + "]";
	}
	
	
	public static String queryForLongAndBoolean(String prefix, String name, boolean value)
	{		
		return "@" + prefix + "\\:" + name + ":" + value;
	}
	
	
	private static String queryForFloatAndDouble(String prefix, String name, String value)
	{		
		return "@" + prefix + "\\:" + name + ":\"" + value  + "\"";
	}	
	
	
	public static String queryForString(String prefix, String name, String value)
	{				logger.debug("S " + prefix + " : " + name + " = " + value);
		String startQuery = "@" + prefix + "\\:" + name + ":";
		
		if(value.charAt(0) == ' ') 
		{
			value = value.substring(1);
		}
		
		String thisQuery = "";
		if(value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"')
		{
			//value = value.toLowerCase();
			thisQuery  = startQuery + value;
		}
		else
		{
			String q = startQuery + "(";
			String andno = "";	
			String n = startQuery + "("; 
			String nq = "";
			String nqEnd = "";
			
			String[] t = value.split(" ");
			//StringTokenizer t = new StringTokenizer(value, " ");
			int tokenCount = t.length;//countTokens();
			boolean nBool = false;
			boolean qBool = false;
			
			for (int i=0; i < tokenCount; i++)
			{//logger.debug(" " + i + " = " + t[i]);
				String term = t[i]/* .nextToken() */.replace("\"", "");
        	   if(term.charAt(0) == '-' || term.charAt(0) == '+')
        	   {
        		   char a = term.charAt(0);
					nq = nq + nqEnd + " " + a + n + "\"" + term.substring(1)/* .toLowerCase() */ + "\"";	
        		   nBool = true;
        		   nqEnd = " )";
        	   }
        	   else
        	   {
					q = q + " " + "\"" + term/* .toLowerCase() */ + "\"";	
        		   qBool = true;
        	   }			            	   
			}		                  
			if(qBool)
			{
				thisQuery = q + " )";
			}
			if(nBool && qBool)
			{
				thisQuery = thisQuery + andno + nq+ " )";
			}
			else if(nBool)
			{
				thisQuery = nq+ " )";
			}
		}		logger.debug("thisQuery = " + thisQuery);
		return thisQuery;
	}
	
	public static String queryForString2(String prefix, String name, String value)
	{				logger.debug("S " + prefix + " : " + name + " = " + value);
		String startQuery = "@" + prefix + "\\:" + name + ":";
		
		String thisQuery = "";
		if(value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"')
		{
			//value = value.toLowerCase();
			thisQuery  = startQuery + value;
		}
		else
		{
			String q = startQuery + "(";
			String andno = "";	
			String n = startQuery + "("; 
			String nq = "";
			String nqEnd = "";
			StringTokenizer t = new StringTokenizer(value, " ");
			int tokenCount = t.countTokens();
			boolean nBool = false;
			boolean qBool = false;
			
			for (int i=0; i < tokenCount; i++)
			{
        	   String term = t.nextToken().replace("\"", "");
        	   if(term.charAt(0) == '-' || term.charAt(0) == '+')
        	   {
        		   char a = term.charAt(0);
        		   nq = nq + nqEnd +" " + a + n + "\"" + term.substring(1).toLowerCase() + "\"";	
        		   nBool = true;
        		   nqEnd = " )";
        	   }
        	   else
        	   {
        		   q = q + " " + "\"" + term.toLowerCase() + "\"";	
        		   qBool = true;
        	   }			            	   
			}		                  
			if(qBool)
			{
				thisQuery = q + " )";
			}
			if(nBool && qBool)
			{
				thisQuery = thisQuery + andno + nq+ " )";
			}
			else if(nBool)
			{
				thisQuery = nq+ " )";
			}
		}		logger.debug("thisQuery = " + thisQuery);
		return thisQuery;
	}
	
	
	private static Integer getValueType(String shortQname, DictionaryService dictionaryService, NamespacePrefixResolver namespacePrefixResolver) {	
		QName qname = QName.createQName(shortQname, namespacePrefixResolver);
		try {
			if (DataTypeDefinition.DATE.equals(dictionaryService.getProperty(qname).getDataType().getName())) {
				return DATE;
			} else if (DataTypeDefinition.DATETIME.equals(dictionaryService.getProperty(qname).getDataType().getName())) {
				return DATETIME;
			} else if (DataTypeDefinition.LONG.equals(dictionaryService.getProperty(qname).getDataType().getName())) {
				return LONG;
			} else if (DataTypeDefinition.DOUBLE.equals(dictionaryService.getProperty(qname).getDataType().getName())) {
				return FLOAT;
			} else if (DataTypeDefinition.BOOLEAN.equals(dictionaryService.getProperty(qname).getDataType().getName())) {
				return BOOLEAN;
			} else if (DataTypeDefinition.FLOAT.equals(dictionaryService.getProperty(qname).getDataType().getName())) {
				return FLOAT;
			} else if (DataTypeDefinition.INT.equals(dictionaryService.getProperty(qname).getDataType().getName())) {
				return LONG;
			} else {
				return STRING;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	
	
	

}
