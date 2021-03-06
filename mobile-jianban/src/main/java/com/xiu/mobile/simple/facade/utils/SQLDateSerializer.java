/*
 * 文 件 名:  SQLDateSerializer.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  JKF54424
 * 修改时间:  2011-11-10
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.xiu.mobile.simple.facade.utils;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * JsonUtils转换工具类辅助类 <功能详细描述>
 * 
 * @author jiangtianming
 * @version [版本号, 2011-11-10]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SQLDateSerializer implements JsonSerializer<java.sql.Date> {

	public JsonElement serialize(java.sql.Date src, Type typeOfSrc,
			JsonSerializationContext context) {
		return new JsonPrimitive(src.getTime());
	}
}
