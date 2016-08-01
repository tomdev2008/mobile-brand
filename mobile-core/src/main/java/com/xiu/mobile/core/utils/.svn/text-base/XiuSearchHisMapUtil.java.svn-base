package com.xiu.mobile.core.utils;

import java.io.InputStreamReader;
import java.util.Properties;

public class XiuSearchHisMapUtil {

	private static final Properties prop = new Properties();
	private static final String path = "/searchhistory.properties";
	static {
		try {
			prop.load(new InputStreamReader(XiuSearchHisMapUtil.class.getClassLoader().getResourceAsStream(path), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取Properties
	 * 
	 * @return - Properties
	 */
	public static Properties getProperties() {
		return prop;
	}
	
	/**
	 * 获取属性的值.
	 * 
	 * @param key
	 * @return - String
	 */
	public static String getValue(String key) {
		String value = prop.getProperty(key);
		if(null==value||"".equals(value))
			value = key;
		return value;
	}
	
	/*
	 * 根据搜索历史对应关系，将displayname中的能匹配的值转换为映射的词
	 */
	public static String transValue(String displayName){
		for(String key :prop.stringPropertyNames()){
			int ind = displayName.toLowerCase().indexOf(key) ;
			if(ind ==0 )  //不仅是需要匹配，还需要从第一个字母开始匹配
				return displayName.substring(0,ind)+getValue(key)+displayName.substring(ind+key.length());
		}
		return displayName;
	}
}
