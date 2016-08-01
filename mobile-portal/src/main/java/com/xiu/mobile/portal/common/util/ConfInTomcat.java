package com.xiu.mobile.portal.common.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 配置信息处理类
 * @author 贾泽伟
 * 2014-10-22 下午5:54
 */
public class ConfInTomcat {

	private static Log log = LogFactory.getLog(ConfInTomcat.class);
	private static Properties props ;
	private static void loadConfInTomcat(){
		props = new Properties();
		String configPath=System.getProperty("catalina.home") + "/conf/confInTomcat.properties";//获取tomcat安装后的根路径
		log.error("confInTomcat  is :" + configPath);
		
		InputStream in = null ;
		try {
			in = new BufferedInputStream(new FileInputStream(configPath));
			props.load(in);
			log.error("confInTomcat.properties load successfully!");
		} catch (Exception e) {
			log.error("confInTomcat.properties load error! and pash is : " + configPath);
		}finally{
			try {
				if(in != null) {
					in.close();
				}
			} catch (IOException e) {
				log.error("confInTomcat.properties load error!" ,e );
			}
		}
	}

  public static String getValue(String key){
	  if(null == props){
		  loadConfInTomcat();
	  }
	 return  (String)props.get(key);
  }
}
