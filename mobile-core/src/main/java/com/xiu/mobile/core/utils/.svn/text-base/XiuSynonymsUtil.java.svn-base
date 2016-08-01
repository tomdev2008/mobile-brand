package com.xiu.mobile.core.utils;

import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 小写转大写
 * @author rian.luo@xiu.com
 *
 * 2016-5-13
 */
public class XiuSynonymsUtil {

	private static final Properties prop = new Properties();
	private static final String path = "/synonyms.properties";
	static {
		try {
			prop.load(new InputStreamReader(XiuSynonymsUtil.class.getClassLoader().getResourceAsStream(path), "UTF-8"));
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
}
