package com.xiu.mobile.core.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;


/** 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 
 * @AUTHOR : mike@xiu.com 
 * @DATE :2013-5-22 下午3:28:19
 * </p>
 **************************************************************** 
 */
public class DateUtil {
    private static Logger logger = Logger.getLogger(DateUtil.class);

    private static String timeFormat="yyyy-MM-dd HH:mm:ss";
    
    public static String formatDate(Date date, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        String strDate = df.format(date);
        return strDate;
    }

    public static Date parseDate(String strDate, String pattern) {
        Date date = null;
        try {
            DateFormat df = new SimpleDateFormat(pattern);
            date = df.parse(strDate);           
        } catch (java.text.ParseException e) {
            logger.error(e.getMessage());
        }
        return date;
    }
    
    public static String formatDate(Date time){
    	return formatDate(time,timeFormat);
    }
    
    public static Date parseTime(String timeStr){
    	return parseDate(timeStr,timeFormat);
    }
    
}
