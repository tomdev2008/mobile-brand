package com.xiu.mobile.portal.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.xiu.mobile.portal.constants.GlobalConstants;

public class ParseProperties {
	private static Properties properties = null;

	private ParseProperties() {
	};

	static {
		InputStream in = null;
		try {
			in = ParseProperties.class.getClassLoader().getResourceAsStream(GlobalConstants.PROPERTIES_FILE_NAME);
			properties = new Properties();
			properties.load(in);
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != in)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
    
	public static String getPropertiesValue(String key){
		return properties.getProperty(key);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String CHANNEL_ID = ParseProperties.getPropertiesValue("CHANNEL_ID");
		System.out.println("CHANNEL_ID:"+CHANNEL_ID);
	}

}
