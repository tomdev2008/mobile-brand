package com.xiu.mobile.core.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ObjectUtil {

	static String datefomate="yyyy-MM-dd HH:mm:ss";
	
	public static String getString(Object obj){
		if(obj==null){
			return "";
		}else {
			return obj.toString();
		}
	}
	
	
	public static String getString(Object obj,String defaultStr){
		if(obj==null||obj.equals("")){
			return defaultStr;
		}else {
			return obj.toString();
		}
	}
	
	public static Long getLong(Object obj){
		if(obj==null){
			return null;
		}else {
			return Long.valueOf(obj.toString());
		}
	}
	/**
	 * 获取Long参数 有默认值
	 * @param obj
	 * @param defaultNum
	 * @return
	 */
	public static Long getLong(Object obj,Long defaultNum){
		Long result=null;
		if(obj==null){
			return defaultNum;
		}else{
			try {
				result = Long.parseLong(obj.toString());
			} catch (NumberFormatException e) {
				result=defaultNum;
			}
		}
		return result;
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
	 * 字符串转换为日期对象
	 * @param date
	 * @return
	 */
	public static Date string2Date(String dateStr){
		if(dateStr==null){
			return null;
		}
		SimpleDateFormat sf=new SimpleDateFormat(datefomate);
		Date date=null;
		try {
			date= sf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
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
	 * 获取当前时间
	 * @return
	 */
	public static String getCurrentTime() {
		SimpleDateFormat sf = new SimpleDateFormat(datefomate); 
		Date now = new Date();
		String currentTime = sf.format(now); //当前时间
		return currentTime;
	}
	
	
	/**
	 * 四舍五入获取格式化Double类型数值
	 * @param d Double值
	 * @param scale 保留小数点
	 * @return
	 */
	public static Double getDoubleFormat(Double d,Integer scale){
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		if(d==null){
			return 0.00d;
		}else{
			BigDecimal b = new BigDecimal(Double.toString(d));
			BigDecimal one = new BigDecimal("1");
			double nv= b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
			return nv;
		}
	}
	
	/**
	 * 四舍五入获取格式化Double类型数值转化为字符串
	 * @param d Double值
	 * @param scale 保留小数点
	 * @return
	 */
	public static String getDouble2Str(Double d,Integer scale){
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		if(d==null){
			return "0";
		}else{
			BigDecimal b = new BigDecimal(Double.toString(d));
			BigDecimal one = new BigDecimal("1");
			double nv= b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
			String dStr=Double.toString(nv);
			String reg="[0-9]+\\.0+";
			if(dStr.matches(reg)){
				dStr=dStr.substring(0,dStr.indexOf(".0"));
			}
			return dStr;
		}
	}
	
	public static void invokeSetMethod(Object obj, String methodName, Object value){
		try {
			Method method = obj.getClass().getMethod("set"+methodName, value.getClass());
			method.invoke(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Object invokeGetMethod(Object obj, String methodName){
		try {
			Method method = obj.getClass().getMethod("get"+methodName);
			return method.invoke(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String[] args) throws ParseException {
//		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date fisr=sf.parse("2015-06-01 23:12:12");
//		Date end=sf.parse("2015-06-02 00:14:12");
//		System.out.println(getDayNum(fisr,end));
		System.out.println(getDouble2Str(124124.10d,2));
		
		
	}
}
