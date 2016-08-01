package com.xiu.show.robot.common.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ShowConfigUtil {

	private static Properties prop = new Properties();

	// 获取配置
	static {
		InputStream in = null;
		try {
			String filePath = ShowConfigUtil.class.getResource("/").getPath() + "show/conf/application.properties";
			in = new BufferedInputStream(new FileInputStream(filePath));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据key获取properties的value
	 * 
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		return prop.getProperty(key).trim();
	}

	public static void main(String[] args) {
		System.out.println(getValue("show.brush.data.time"));
	}
}
