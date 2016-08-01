package com.xiu.show.robot.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class ObjectUtil {

	
	public static void main(String[] args) {
		Date now=new Date();
		for (int i = 0; i < 10; i++) {
			System.out.println(getTimeStrByMin(now,-i));
		}
//		System.out.println(getRandomCharAndNumr(10));
	}
	
	/** 
	 * 获取随机字母数字组合 
	 * @param length 
	 * @return 
	 */  
	public static String getRandomCharAndNumr(Integer length) {  
	    String str = "";  
	    Random random = new Random();  
	    for (int i = 0; i < length; i++) {  
	        boolean b = random.nextBoolean();  
	        if (b) { // 字符串  
	            // int choice = random.nextBoolean() ? 65 : 97; 取得65大写字母还是97小写字母  
	            str += (char) (65 + random.nextInt(26));// 取得大写字母  
	        } else { // 数字  
	            str += String.valueOf(random.nextInt(10));  
	        }  
	    }  
	    return str;  
	}  
	
	
	/**
	 * 获取指定范围的随机数
	 * @param max
	 * @return
	 */
	public static Integer getRandom(Integer max){
		Random r = new Random();  
		return 	r.nextInt(max);
	}
	
	private static String dateFormat = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 获取当前时间字符串格式
	 * @return
	 */
	public static String getNowTimeString(){
		Date now=new Date();
		SimpleDateFormat sf=new SimpleDateFormat(dateFormat);
		return sf.format(now);
	}
	
	/**
	 * 获取时间字符串
	 * @param date
	 * @return
	 */
	public static String getTimeString(Date date){
		SimpleDateFormat sf=new SimpleDateFormat(dateFormat);
		return sf.format(date);
	}
	
	/**
	 * 获取指定分钟前的时间
	 * @param minuteNum
	 * @return
	 */
	public static Date getTimeByMin(Date date,Integer minuteNum){
		long minutes=minuteNum*1000*60l;
		long datanum=date.getTime()+minutes;
		return new Date(datanum);
	}
	
	/**
	 * 获取指定分钟前的时间
	 * @param minuteNum
	 * @return
	 */
	public static String getTimeStrByMin(Date date,Integer minuteNum){
		long minutes=minuteNum*1000*60l;
		long datanum=date.getTime()+minutes;
		SimpleDateFormat sf=new SimpleDateFormat(dateFormat);
		return sf.format(new Date(datanum));
	}
	
	/**
	 * 获取指定分钟前的时间
	 * @param minuteNum
	 * @return
	 */
	public static String getBeforTimeByMin(Integer minuteNum){
		Date dNow = new Date(); //当前时间
		return getTimeStrByMin(dNow,-minuteNum);
	}
	/**
	 * 获取指定分钟后的时间
	 * @param minuteNum
	 * @return
	 */
	public static String getAfterTimeByMin(Integer minuteNum){
		Date dNow = new Date(); //当前时间
		return getTimeStrByMin(dNow,minuteNum);
	}
	
	
}
