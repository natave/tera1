package com.its.tera.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Formatter {
	
	private static final Log logger = LogFactory.getLog(Formatter.class);
	
	public static final String DATE_FORMAT = "dd-MM-yyyy";
	public static final String DATE_FORMAT2 = "yyyy-MM-dd";
	public static final String DATE_FORMAT3 = "MM-dd-yyyy";
	public static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";
	public static final String DATE_TIME_FORMAT2 = "yyyy-MM-dd HH:mm";
	public static final String DATE_TIME_FORMAT3 = "MM-dd-yyyy HH:mm";
	public static final String DATE_FORMAT_FOR_DOC_NAME = "ddMMyy";
	public static final String DATE_FORMAT_FOR_SEARCH = "yyyy-MM-dd'T'HH:mm:ss.SSS";
	
	
	public static final String DATE_FORMAT_DOC = "dd.MM.yy";
	//public static final String DATE_FORMAT_FOR_SEARCH_PARSE = "EEE MMM dd HH:mm:ss Z yyyy";
	
	
	
	public static Date getDateFormat(String value, String format) throws ParseException
	{
		SimpleDateFormat f = new SimpleDateFormat(format);		
		return f.parse(value);
	}
	
	public static String getDateFormat(Date date, String format) 
	{
		if(date != null) {
			SimpleDateFormat f = new SimpleDateFormat(format);		
			return f.format(date);
		}else {
			return null;
		}
		
	}
	
	
	public static String getSHA256String(String value) throws UnsupportedEncodingException
	{
		String generatedPassword = null;
		try 
		{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			if(md == null){
				logger.debug("md == null :(((((((");
			}
			if(value == null){
				logger.debug("value == null :(((((((");
			}
			
			if(value.getBytes("UTF-8") == null){
				logger.debug("value.getBytes(\"UTF-8\") == null :(((((((");
			}
			
			byte[] bytes = md.digest(value.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++){
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} 
		catch (NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		return generatedPassword;
	}
	
	
	public static Date startOfDay(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		
		return calendar.getTime();
	}

	public static Date endOfDay(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MILLISECOND, 999);
		calendar.set(Calendar.SECOND, 58);
		calendar.set(Calendar.MINUTE, 59);
		
		return calendar.getTime();
	}
	
	public static Long getDayDiffs(Date d1, Date d2)
	{
		return (startOfDay(d1).getTime() - startOfDay(d2).getTime()) / (1000 * 60 * 60 * 24);
	}
	
	public static Long getMinuteDiffs(Date d1, Date d2)
	{
		return (startOfDay(d1).getTime() - startOfDay(d2).getTime()) / (1000 * 60);
	}

}
