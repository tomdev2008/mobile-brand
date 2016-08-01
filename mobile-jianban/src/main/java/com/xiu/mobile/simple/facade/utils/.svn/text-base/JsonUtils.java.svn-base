package com.xiu.mobile.simple.facade.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JSON转换类
 * 
 * @author 江天明
 */
public class JsonUtils {

	/**
	 * 将BEAN转换成JSON字符串
	 * 
	 * @param bean
	 *            BEAN
	 * @return JSON字符串
	 */
	public static String bean2json(Object bean) {
		Gson gson = new GsonBuilder().registerTypeAdapter(java.sql.Date.class, new SQLDateSerializer())
				.registerTypeAdapter(java.util.Date.class, new UtilDateSerializer()).setDateFormat(DateFormat.LONG).setPrettyPrinting()
				.create();
		// 需要去除换行，走秀解析不支持换行
		return gson.toJson(bean).replace("\n", "");
	}

	/**
	 * 将JSON字符串转换成BEAN，如果JSON字符串中包含有Bean里不存在的字段，则不存在的字段会被忽略
	 * 
	 * @param json
	 *            JSON字符串
	 * @param type
	 *            BEAN的类型
	 * @return BEAN对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T json2bean(String json, Type type) {
		Gson gson = new GsonBuilder().registerTypeAdapter(java.sql.Date.class, new SQLDateDeserializer())
				.registerTypeAdapter(java.util.Date.class, new UtilDateDeserializer()).setDateFormat(DateFormat.LONG).create();
		return (T) gson.fromJson(json, type);
	}

	/**
	 * 从jsonObject中加载属性值
	 * @param <T>
	 * @param jsonObject
	 * @param classType
	 * @return
	 */
	public static <T> T loadPropertiesByName(JSONObject jsonObject, Class<T> classType) {
		T object = null;
		try {
			object = classType.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		Field[] fs = classType.getDeclaredFields();
		for (int i = 0; i < fs.length; i++) {
			Field cf = fs[i];
			Class<?> fty = cf.getType();
			// 简单对象
			if (fty.isPrimitive() || fty.isInstance(new String()) || fty.isInstance(new BigDecimal(1))) {
				cf.setAccessible(true);
				try {
					Object val = jsonObject.get(cf.getName());
					if (val != null && !val.equals("null")) {
						cf.set(object, val);
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return object;
	}

	/**
	 * 返回列表属性的列表
	 * @param <T>
	 * @param jsonObject
	 * @param propName
	 * @param classType
	 * @return
	 */
	public static <T> List<T> loadListProperty(JSONObject jsonObject, String propName, Class<T> classType) {
		List<T> list = new ArrayList<T>();
		JSONArray jList = jsonObject.getJSONArray(propName);
		for (int i = 0; i < jList.size(); i++) {
			list.add(JsonUtils.loadPropertiesByName(jList.getJSONObject(i), classType));
		}
		return list;
	}
}
