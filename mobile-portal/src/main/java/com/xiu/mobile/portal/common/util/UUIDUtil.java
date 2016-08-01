package com.xiu.mobile.portal.common.util;

import java.util.UUID;

public class UUIDUtil {
	
	/***
	 * 生成UUID
	 * @return
	 */
	public static String generateUUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}
	
	public static void main(String[] args) {
		System.out.println(UUIDUtil.generateUUID());
	}
}
