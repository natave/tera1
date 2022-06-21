package com.its.tera.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormat 
{
	
	public static final String DATE_FORMAT = "dd-MM-yyyy";
	public static final String DATE_FORMAT_FOR_DOC_NAME = "ddMMyy";
	public static final String DATE_FORMAT_FOR_SEARCH = "yyyy-MM-dd'T'HH:mm:ss.SSS";
	
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
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MINUTE, 59);
		
		return calendar.getTime();
	}
	
	public static Date addDays(Date date, int days)
	{
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		
		return calendar.getTime();
	}
	
	public static Long getDayDiffs(Date d1, Date d2)
	{
		return (startOfDay(d1).getTime() - startOfDay(d2).getTime()) / (1000 * 60 * 60 * 24);
	}
	
	public static String getDate(Date date)
	{
		SimpleDateFormat f = new SimpleDateFormat(DATE_FORMAT_FOR_DOC_NAME);
		return f.format(date);
	}
	
	public static String getDateFor(Date date, String format)
	{
		SimpleDateFormat f = new SimpleDateFormat(format);
		return f.format(date);
	}
	
	public static Date getDateFromString(String dateString, String format) throws ParseException{
		SimpleDateFormat f = new SimpleDateFormat(format);
		return f.parse(dateString);
	}
	
	

}
