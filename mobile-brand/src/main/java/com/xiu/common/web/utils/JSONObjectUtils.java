package com.xiu.common.web.utils;

import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONObjectUtils {
	
	public static String toString(Collection c) {
		JSONArray array = JSONArray.fromObject(c);
		String str = array.toString();
		return str;
	}

	/**
	 * 将BEAN转换成JSON字符串
	 * @return
	 */
	public static String toString(Object object) {
		JSONObject json = JSONObject.fromObject(object);
		String str = json.toString();
		return str;
	}
	
	/**
	 * 将BEAN转换成JSONP字符串
	 * @return
	 */
	public static String bean2jsonP(String callback, Object bean) {
		StringBuffer sb = new StringBuffer(toString(bean));
		
		if(callback != null) {
			sb.insert(0, callback + "(");
			sb.append(")");
		}
		
		return sb.toString();
	}

	public static Collection getCollection(String jsonStr, Class c) {
		JSONArray ja = JSONArray.fromObject(jsonStr);
		Collection col = JSONArray.toCollection(ja, c);
		return col;

	}

	public static Object getObject(String userString, Class c) {
		JSONObject ja = JSONObject.fromObject(userString);
		return JSONObject.toBean(ja, c);
	}

}
