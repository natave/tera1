package com.its.tera.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.its.tera.constants.CM;
import com.its.tera.model.util.DateSearch;

public class QueryUtil2 {
	
	public static final Log logger = LogFactory.getLog(QueryUtil2.class);
	public static final String AND_FOR_QUERY = " AND ";
	public static final String FIND_IN_ALL_PARAMETERS = "findInAllParameters";
	
	
	
	public static String getQueryForAgreement(Map<String, Serializable> propsMap, int isGeneral, AppParameters appParameters){
		SimpleDateFormat dateFormatForSearch = new SimpleDateFormat(DateFormat.DATE_FORMAT_FOR_SEARCH);
		
		String finalQuery = null;		
		
		logger.debug("map size = " + propsMap.size());	
		
		if(isGeneral == 1){
			finalQuery = "TYPE:\""+CM.QNAME_TYPE_GENERAL_AGREEMENT_FOLDER.toString()+"\"";
		}else if(isGeneral == 2){
			finalQuery = "TYPE:\""+CM.QNAME_TYPE_AGREEMENT_FOLDER.toString()+"\"";
		}else {
			finalQuery = "(TYPE:\"" + CM.QNAME_TYPE_GENERAL_AGREEMENT_FOLDER.toString() + "\"" 
					+ " OR " +"TYPE:\"" + CM.QNAME_TYPE_AGREEMENT_FOLDER.toString() + "\")";
		}
						
		for(String key : propsMap.keySet())
		{					
			String pefix = "";
			String name = "";
			String thisQuery = "";					
								
			if(key.contains(":"))
			{
				pefix = key.substring(0, key.indexOf(":"));
				name = key.substring(key.indexOf(":") + 1);
			}
			else
			{
				pefix = CM.NAMESPACE_URI_SHORT;
				name = key;
			}
			System.out.println(" <<<< pefix = " + pefix + ".  name = " + name);
			
			if(key.equalsIgnoreCase(CM.PROP_IS_EMPTY))
			{
				Boolean value = (Boolean) propsMap.get(key);
				if(value)
				{							
					thisQuery = "ASPECT:\""+ CM.QNAME_ASPECT_ITEM_IS_EMPTY + "\"";
				}
				else
				{
					thisQuery = "NOT ASPECT:\""+ CM.QNAME_ASPECT_ITEM_IS_EMPTY + "\"";
				}				
				finalQuery = finalQuery + AND_FOR_QUERY + thisQuery;
				continue;
			}
			else if(key.equalsIgnoreCase(CM.PROP_IS_NOT_TRULI_REGISTERED))
			{
				Boolean value = (Boolean) propsMap.get(key);
				if(value)
				{
					thisQuery = "ISNULL:\"" + CM.QNAME_PROP_CORRESPONDENCE_TYPE + "\"";
				}
				else
				{
					thisQuery = "ISNOTNULL:\"" + CM.QNAME_PROP_CORRESPONDENCE_TYPE + "\"";
				}	
				finalQuery = finalQuery + AND_FOR_QUERY + thisQuery;
				continue;
			}
			else if(key.equalsIgnoreCase(CM.PROP_IS_VIP))
			{
				Boolean value = (Boolean) propsMap.get(key);
				if(value)
				{							
					thisQuery = "ASPECT:\""+ CM.QNAME_ASPECT_CLIENT_IS_VIP + "\"";
				}
				else
				{
					thisQuery = "NOT ASPECT:\""+ CM.QNAME_ASPECT_CLIENT_IS_VIP + "\"";
				}				
				finalQuery = finalQuery + AND_FOR_QUERY + thisQuery;
				continue;
			}
			
			if(propsMap.get(key) instanceof DateSearch)
			{
				DateSearch regDateModel = (DateSearch) propsMap.get(key);
				Date from = null;
				Date to = null;
				if(regDateModel.getDateFrom() == null)
				{
					if(regDateModel.getDateTo() == null)
					{
						to = new Date();
					}
					else
					{
						to = regDateModel.getDateTo();
					}
					from = DateFormat.addDays(to, (0 - appParameters.getNumberOfDays()));
				}
				else
				{
					from = regDateModel.getDateFrom();							
					if(regDateModel.getDateTo() == null)
					{
						to = new Date();						
					}
					else
					{
						to = regDateModel.getDateTo();
					}
				}
				
				Date valueFrom = DateFormat.startOfDay(from);						
				Date valueTo = DateFormat.endOfDay(to);						
				
				String fromString = dateFormatForSearch.format(valueFrom);
				String toString = dateFormatForSearch.format(valueTo);
				thisQuery = "@"+pefix+"\\:"+name+":["+fromString+" TO "+toString+"]";
				System.out.println(" SearchModel <<<<< thisQuery = " + thisQuery);
			}
			else if(propsMap.get(key) instanceof String)
			{
				String value = (String) propsMap.get(key);
				String startQuery = "";
				if(key.equalsIgnoreCase(FIND_IN_ALL_PARAMETERS))
				{
					startQuery = "ALL:";
				}
				else
				{
					startQuery = "@" + pefix + "\\:" + name + ":";
				}
				
				if(value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"')
				{
					value = value.toLowerCase();
					thisQuery = startQuery + value;
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
	            	   String term = t.nextToken();
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
				}
			}
			else if(propsMap.get(key) instanceof Integer || propsMap.get(key) instanceof Long)
			{	
				thisQuery = "@" + pefix + "\\:" + name + ":" + propsMap.get(key);				
			}
			else if(propsMap.get(key) instanceof Boolean)
			{
				Boolean value = (Boolean) propsMap.get(key);
				thisQuery = "@" + pefix + "\\:" + name + ":" + value;
				System.out.println( " QUERY Boolean = " + thisQuery);
				
			}
			else
			{
				logger.debug("value of this key: '" + key +"' is not recognised");
				continue;
			}
			System.out.println( "  fffff q = " + thisQuery);
			if(thisQuery != null && !thisQuery.equalsIgnoreCase("") && !thisQuery.equalsIgnoreCase(" "))
			{
				finalQuery = finalQuery + AND_FOR_QUERY + thisQuery;
			}					
		}				
		
		if(propsMap.size() == 0 && !finalQuery.contains(AND_FOR_QUERY + "@" +  "cm\\:created" + ":["))
		{
			Date now = new Date();
			Date from = DateFormat.addDays(now, (0 - appParameters.getNumberOfDays()));
			finalQuery = finalQuery + AND_FOR_QUERY + "@" + "cm\\:craeted" + ":[" + dateFormatForSearch.format(DateFormat.startOfDay(from)) 
						+ " TO " + dateFormatForSearch.format(DateFormat.endOfDay(now)) + "]";					
		}	
		logger.debug("finalQuery AGR = " + finalQuery);
		return finalQuery;
	}
	

}
