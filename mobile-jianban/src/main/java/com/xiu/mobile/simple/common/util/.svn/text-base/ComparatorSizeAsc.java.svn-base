package com.xiu.mobile.simple.common.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 对尺码字母进行排序
 * 
 * @author wenxiaozhuo
 * @date 20140423
 * @param <T>
 */
public class ComparatorSizeAsc<T> implements java.util.Comparator<T> {
	Map<String, Integer> map = new LinkedHashMap<String, Integer>();

	@Override
	public int compare(T arg0, T arg1) {
		map.put("XXS", 1);
		map.put("XS", 2);
		map.put("S", 3);
		map.put("M", 4);
		map.put("L", 5);
		map.put("XL", 6);
		map.put("XXL", 7);
		map.put("XXXL", 8);
		String size1 = (String) arg0;
		String size2 = (String) arg1;
		int siz1 = 0;
		int siz2 = 0;
		Iterator<?> iterator = map.keySet().iterator();
		
		while (iterator.hasNext()) {
			String size = (String) iterator.next();
			if (size1.equalsIgnoreCase(size)) {
				siz1 = map.get(size);
			}
			if (size2.equalsIgnoreCase(size)) {
				siz2 = map.get(size);
			}
		}
		if (siz1 > siz2) {
			return 1;
		} else if (siz1 < siz2) {
			return -1;
		} else {
			return 0;
		}
	}
}
