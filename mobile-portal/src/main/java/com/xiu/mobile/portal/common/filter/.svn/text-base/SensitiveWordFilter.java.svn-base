package com.xiu.mobile.portal.common.filter;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/***
 * 敏感词过滤
 * @author haijun.chen@xiu.com
 * @date 2013-06-13 11:09:30
 */
@Component
public class SensitiveWordFilter {
	
	//日志
	private static final Logger LOGGER = LoggerFactory.getLogger(SensitiveWordFilter.class);
	
	private static Pattern pattern =null;
	
	/**
	 * 从sensitivewords.properties初始化正则表达式字符串
	 */
	@SuppressWarnings("rawtypes")
	@PostConstruct
	private static void initPattern() {
		StringBuffer patternBuf = new StringBuffer("");
		try {
			InputStream in = SensitiveWordFilter.class.getClassLoader().getResourceAsStream("sensitivewords.properties");
			if(in!=null){
				Properties pro = new Properties();
				pro.load(in);
				Enumeration enu = pro.propertyNames();
				patternBuf.append("(");
				while (enu.hasMoreElements()) {
					patternBuf.append((String) enu.nextElement() + "|");
				}
				patternBuf.deleteCharAt(patternBuf.length() - 1);
				patternBuf.append(")");

				// unix换成UTF-8
				 pattern = Pattern.compile(patternBuf.toString());
				// win下换成gb2312
				//pattern = Pattern.compile(new String(patternBuf.toString()
				//		.getBytes("ISO-8859-1"), "gb2312"));
			}else{
				LOGGER.info("敏感词 job : sensitivewords.properties 文件没有找到！");
			}
		} catch (IOException ioEx) {
			ioEx.printStackTrace();
			LOGGER.error("sensitivewords.properties：敏感词初始化异常...",ioEx);
		}
	}

	/**
	 * 返回不敏感的词
	 * @param str
	 * @return
	 */
	private static String doCharacter(String str) {
		if(pattern!=null){
			Matcher m = pattern.matcher(str);
			str = m.replaceAll("");
		}
		return str;
	}
	
	/**
	 *  返回敏感的词,以字符串的形式返回(特殊符号进行隔开)
	 * @param str
	 * @param reg
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String doUnCharacter(String str,String reg){
		String sbstr="";
		if(pattern!=null){
			Matcher m = pattern.matcher(str);
			int start = 0;
		    int i = 0;
			while (m.find(start)) {
				  if(!"".equals(sbstr)){
					  sbstr+=reg+str.substring(m.start(),m.end());
				  }else{
					  sbstr=str.substring(m.start(),m.end());
				  }
			      start = m.end();
			      i++;
			}
		}
		return sbstr;
	}
	
	/**
	 * 返回敏感的词,以集合的形式返回
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unused")
	private static List<Object> doUncharachterSet(String str){
		List<Object> strlist=new ArrayList<Object>();
		if(pattern!=null){
			Matcher m = pattern.matcher(str);
			int start = 0;
		    int i = 0;
			while (m.find(start)) {
				  strlist.add(str.substring(m.start(),m.end()));
			      start = m.end();
			      i++;
			}
		}
		return strlist;
	}
	
	/**
	 * 是否存在敏感字符(存在:true ,不存在 false)
	 * @param str
	 * @return
	 */
	public static boolean existsCharachter(String str){
		if(pattern!=null){
			Matcher m = pattern.matcher(str);
			return m.find();
		}
		return false;
	}
	
	
	/**
	 * 用特殊符号，替代敏感字符
	 * @param str
	 * @param reg
	 * @return
	 */
	public static String doReplaceCharachter(String str,String reg){
		if(pattern!=null){
			Matcher m = pattern.matcher(str);
			str = m.replaceAll(reg);
		}
		return str;
	}
	

	/**
	 * 测试方法
	 * @param str
	 */
	public static void wordTest(String str){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("输入的内容是=========:" + str);
		System.out.println("字符的长度是=========:" + str.length());
		Date d1 = new Date();
		System.out.println("开始匹配时间是=======:" + formatter.format(d1));
		System.out.println("合法的字符是=========:'"+ SensitiveWordFilter.doCharacter(str) + " '");
		System.out.println("不合法的字符是=======:'"+ SensitiveWordFilter.doUnCharacter(str,",") + " '");
		System.out.println("不合法的字符是=======:"+SensitiveWordFilter.doUncharachterSet(str));
		System.out.println("用特殊符号替代敏感字符:"+SensitiveWordFilter.doReplaceCharachter(str,"***"));
		System.out.println("是否存在不合法的字符==:"+SensitiveWordFilter.existsCharachter(str));
		Date d2 = new Date();
		System.out.println("结束匹配时间是=======:" + formatter.format(d2));
		Calendar c = Calendar.getInstance();  
        c.setTimeInMillis(d2.getTime() - d1.getTime());  
        System.out.println("检验敏感字符总共耗时:========= " + c.get(Calendar.MINUTE) + " 分 "+ c.get(Calendar.SECOND) + " 秒 " + c.get(Calendar.MILLISECOND) + " 毫秒");  
	}
	
	/**
	 * 测试入口
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "你妹!";
		SensitiveWordFilter.wordTest(str);
	}
}
