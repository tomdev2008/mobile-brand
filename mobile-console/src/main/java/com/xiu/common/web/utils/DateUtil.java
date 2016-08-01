package com.xiu.common.web.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    
    /**
     * 获取今天时间
     * @return
     */
    public static String getNowTime(){
    	return formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 获取今天日期
     * @return
     */
    public static String getNowDate(){
    	return formatDate(new Date(),"yyyy-MM-dd");
    }
    
    /**
     * 获取指定时间的，相隔天数的日期
     * @param first 指定的时间
     * @param num 相隔天数 负数则为日期前的时间
     * @return
     */
    public static String getTimeByDays(Date first,Integer num){
    	Calendar theCa = Calendar.getInstance();
    	theCa.setTime(first);
    	theCa.add(theCa.DATE, num);
    	Date date = theCa.getTime();
    	return formatDate(date,"yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 获取指定时间的，相隔天数的日期
     * @param first 指定的时间
     * @param num 相隔天数 负数则为日期前的时间
     * @return
     */
    public static Date getTimeDateByDays(Date first,Integer num){
    	Calendar theCa = Calendar.getInstance();
    	theCa.setTime(first);
    	theCa.add(theCa.DATE, num);
    	Date date = theCa.getTime();
    	return date;
    }
    /**
     * 获取指定时间的，相隔天数的日期
     * @param first 指定的时间
     * @param num 相隔天数 负数则为日期前的时间
     * @return
     */
    public static String getDateByDays(Date first,Integer num){
    	Calendar theCa = Calendar.getInstance();
    	 theCa.setTime(first);
    	 theCa.add(theCa.DATE, num);
    	 Date date = theCa.getTime();
    	 return formatDate(date,"yyyy-MM-dd");
    }
    /**
     * 获取指定时间的，相隔天数的日期
     * @param first 指定的时间
     * @param num 相隔天数 负数则为日期前的时间
     * @return
     */
    public static String getDateByDay(Date first,Integer num){
    	Calendar theCa = Calendar.getInstance();
    	 theCa.setTime(first);
    	 theCa.add(theCa.DATE, num);
    	 Date date = theCa.getTime();
    	 return formatDate(date,"yyyy-MM-dd HH:mm:ss");
    }
    public static String formatDate(Date time){
    	return formatDate(time,timeFormat);
    }
    
    public static Date parseTime(String timeStr){
    	return parseDate(timeStr,timeFormat);
    }
    
    public static void main(String[] args) {
		System.out.println(DateUtil.getDateByDay(new Date(), 365));//半年前);
		
	}
    
}
