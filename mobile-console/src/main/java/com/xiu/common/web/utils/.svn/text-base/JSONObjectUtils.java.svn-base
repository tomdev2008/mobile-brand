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

	public static String toString(Object object) {
		JSONObject json = JSONObject.fromObject(object);
		String str = json.toString();
		return str;
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
