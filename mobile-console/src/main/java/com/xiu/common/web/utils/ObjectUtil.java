package com.xiu.common.web.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ObjectUtil {

	static String datefomate="yyyy-MM-dd HH:mm:ss";		//时间格式
	static String dayFomate = "yyyy-MM-dd";				//日期格式
	
	public static String getString(Object obj){
		if(obj==null){
			return "";
		}else {
			return obj.toString();
		}
	}
	
	public static Long getLong(Object obj){
		if(obj==null){
			return null;
		}else {
			return (Long)obj;
		}
	}
	
	/**
	 * 日期对象转为字符串
	 * @param date
	 * @return
	 */
	public static String date2String(Date date){
		if(date==null){
			return null;
		}
		SimpleDateFormat sf=new SimpleDateFormat(datefomate);
		return sf.format(date);
	}
	
	/**
	 * 日期对象转为字符串-自定义格式
	 * @param date
	 * @return
	 */
	public static String date2String(Date date,String formateStr){
		if(date==null){
			return null;
		}
		SimpleDateFormat sf=new SimpleDateFormat(formateStr);
		return sf.format(date);
	}
	
	/**
	 * 获取Int参数 有默认值
	 * @param obj
	 * @param defaultNum
	 * @return
	 */
	public static Integer getInteger(Object obj,Integer defaultNum){
		Integer result=null;
		if(obj==null){
			return defaultNum;
		}else{
			try {
				result = Integer.parseInt(obj.toString());
			} catch (NumberFormatException e) {
				result=defaultNum;
			}
		}
		return result;
	}
	
	/**
	 * 比较两个时间的相隔天数
	 * @param first
	 * @param end
	 */
	public static Integer getDayNum(Date first,Date end){
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		 try {
			first=sdf.parse(sdf.format(first));
			end=sdf.parse(sdf.format(end));  
		} catch (ParseException e) {
			e.printStackTrace();
		}  
	        Calendar cal = Calendar.getInstance();    
	        cal.setTime(first);    
	        long time1 = cal.getTimeInMillis();                 
	        cal.setTime(end);    
	        long time2 = cal.getTimeInMillis();         
	        long between_days=(time2-time1)/(1000*3600*24);  
	       return Integer.parseInt(String.valueOf(between_days));   
	}
	
	/**
	 * 获取当前时间和下一天的时间
	 * @return
	 */
	public static Map getDayTime() {
		Map resultMap = new HashMap();
		
		SimpleDateFormat sf = new SimpleDateFormat(datefomate); 
		Date now = new Date();	//开始时间
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.DAY_OF_MONTH, 1);	// 时间间隔，1天
		
		Date nextTime = calendar.getTime();	//结束时间
		String beginTime = sf.format(now);
		String endTime = sf.format(nextTime);
		
		resultMap.put("beginTime", beginTime);
		resultMap.put("endTime", endTime);
		
		return resultMap;
	}
	
	/**
	 * 获取今天的时间
	 * @return
	 */
	public static Map getTodayTime() {
		Map<String, String> resultMap = new HashMap<String, String>();
		
		SimpleDateFormat sf = new SimpleDateFormat(dayFomate); 
		Date now = new Date();
		String day = sf.format(now);
		//开始时间
		String beginTime = day + " 00:00:00";
		//结束时间
		String endTime = day + " 23:59:59";
		
		resultMap.put("beginTime", beginTime);
		resultMap.put("endTime", endTime);
		
		return resultMap;
	}
	
	public static Map getYesterdayTime() {
		Map<String, String> resultMap = new HashMap<String, String>();
		
		SimpleDateFormat sf = new SimpleDateFormat(dayFomate); 
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.DAY_OF_MONTH, -1);	// 时间间隔，1天
		Date nextTime = calendar.getTime();	//结束时间
		String day = sf.format(nextTime);
		
		//开始时间
		String beginTime = day + " 00:00:00";
		//结束时间
		String endTime = day + " 23:59:59";
		
		resultMap.put("beginTime", beginTime);
		resultMap.put("endTime", endTime);
		
		return resultMap;
	}
	
	public static Map getLastedWeekTime() {
		Map<String, String> resultMap = new HashMap<String, String>();
		
		SimpleDateFormat sf = new SimpleDateFormat(dayFomate); 
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.DAY_OF_MONTH, -7);	// 时间间隔，7天
		Date nextTime = calendar.getTime();	//结束时间
		
		//开始时间
		String beginTime = sf.format(nextTime);
		beginTime = beginTime + " 00:00:00";
		//结束时间
		String endTime = sf.format(now);
		endTime = endTime + " 23:59:59";
		
		resultMap.put("beginTime", beginTime);
		resultMap.put("endTime", endTime);
		
		return resultMap;
	}
	
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date fisr=sf.parse("2015-06-01 23:12:12");
		Date end=sf.parse("2015-06-02 00:14:12");
//		System.out.println(getDayNum(fisr,end));
		
		Map map = getLastedWeekTime();
		String beginTime = (String) map.get("beginTime");
		String endTime = (String) map.get("endTime");
		System.out.println(beginTime);
		System.out.println(endTime);
	}
}
