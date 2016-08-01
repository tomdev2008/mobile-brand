package com.xiu.mobile.portal.common.util;

import java.util.Collection;
import java.util.Map;

/**
 * 
 * @ClassName: LogicUtil
 * @Description: 逻辑判断
 * @author: Hualong
 * @date 2013-3-7 下午05:30:13
 * 
 */
public class LogicUtil {

	public static boolean isNullOrEmpty(Collection<?> collection) {
		if (null == collection || 0 == collection.size()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotNullAndEmpty(Collection<?> collection) {
		return (!isNullOrEmpty(collection));
	}

	public static boolean isNullOrEmpty(Map map) {
		if (null == map || 0 == map.size()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotNullAndEmpty(Map map) {
		return (!isNullOrEmpty(map));
	}

	public static boolean isNull(Object object) {
		if (object == null) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotNull(Object Object) {
		return (!isNull(Object));
	}

	public static boolean isNullOrEmpty(String subject) {
		if (null == subject || "".equals(subject)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotNullAndEmpty(String subject) {
		return (!isNullOrEmpty(subject));
	}
}
