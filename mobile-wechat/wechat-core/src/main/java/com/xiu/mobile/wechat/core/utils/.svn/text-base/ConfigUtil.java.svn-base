package com.xiu.mobile.wechat.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 加载配置文件
 * 
 * @author wangzhenjiang
 * @since 20140916
 *
 */
public enum ConfigUtil {
	INSTANCE;

	public String getString(String key) {
		return props.getProperty(key);
	}

	private Properties props;

	private ConfigUtil() {
		init();
	}

	private void init() {
		props = new Properties();
		InputStream is = null;
		try {
			is = this.getClass().getClassLoader().getResourceAsStream("application.properties");
			props.load(is);
		} catch (Exception e) {
			System.err.println("加载配置文件发生异常(application.properties)：" + e.getMessage());
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
			}
		}
	}
}
