package com.xiu.mobile.core.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class XiuUtil {
	/**
	 * 根据doulbe*100倍获取long 
	 * @param d
	 * @return
	 */
	public static Long getPriceLongFormat100(Double d){
		if(d==null){
			return 0l;
		}else{
			BigDecimal b = new BigDecimal(d);
			BigDecimal one = new BigDecimal("1");
			double nv= b.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
			return (long) (nv*100l);
		}
	}
	/**
	 * 四舍五入获取格式化Double类型数值
	 * @param d Double值
	 * @return
	 */
	public static Double getPriceDoubleFormat(String d){
		if(d==null){
			return 0.00d;
		}else{
			BigDecimal b = new BigDecimal(d);
			BigDecimal one = new BigDecimal("1");
			double nv= b.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
			return nv;
		}
	}
	/**
	 * 四舍五入获取格式化Double类型数值
	 * @param d Double值
	 * @return
	 */
	public static Double getPriceDoubleFormat(Double d){
		if(d==null){
			return 0.00d;
		}else{
			BigDecimal b = new BigDecimal(d);
			BigDecimal one = new BigDecimal("1");
			double nv= b.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
			return nv;
		}
	}
	
	/**
	 * 四舍五入获取格式化Double类型数值
	 * @param d Double值
	 * @param scale 保留小数点
	 * @return
	 */
	public static Double getPriceDoubleFormat(Double d,Integer scale){
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
	public static String getPriceDouble2Str(Double d){
		Integer scale=2;
		if(d==null){
			return "0";
		}else{
			BigDecimal b = new BigDecimal(Double.toString(d));
			BigDecimal one = new BigDecimal("1");
			double nv= b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
			NumberFormat nf = NumberFormat.getInstance();
			nf.setGroupingUsed(false); //大数值，不显示成科学计数法
			String dStr=nf.format(nv);
			String reg="[0-9]+\\.0+";//无小数点处理
			if(dStr.matches(reg)){
				dStr=dStr.substring(0,dStr.indexOf(".0"));
				return dStr;
			}
			 reg="[0-9]+\\.[1-9]0";//第二位小数位0处理
			if(dStr.matches(reg)){
				dStr=dStr.substring(0,dStr.indexOf(".0"));
			}
			return dStr;
		}
	}
	
	/**
	 * 根据doulbe*100倍获取long 
	 * @param d
	 * @return
	 */
	public static Long getPriceLongFormat100(String d){
		if(d==null){
			return 0l;
		}else{
			BigDecimal a = new BigDecimal(d);
			BigDecimal b = new BigDecimal(100);
			return a.multiply(b).longValue();
		}
	}
	
	public static void main(String[] args) {
//		System.out.println(getPriceDouble2Str(112314.0d));
//		System.out.println(getPriceDouble2Str(112314.20d));
//		System.out.println(getPriceDouble2Str(112314.238d));
//		System.out.println(getPriceDouble2Str(112314.232d));
//		System.out.println(getPriceDoubleFormat(112314.232d));
//		System.out.println(getPriceDoubleFormat(112314.232d));
//		String xiuWord="http://item.xiu.com/product/41943002.shtml";
//		String goodsId=xiuWord.substring(xiuWord.indexOf("product/")+8,xiuWord.lastIndexOf("."));
//		System.out.println(goodsId);
//		String userName="0xiu_23r";
//		
//		System.out.println(userName.indexOf("xiu_")==0);
		
		System.out.println(getPriceLongFormat100("17243.01"));
		
	}
	
}
