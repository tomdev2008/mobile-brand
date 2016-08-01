package com.xiu.show.robot.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import com.xiu.show.robot.common.contants.ShowRobotConstants;
import com.xiu.show.robot.common.thread.HttpThread;
import com.xiu.show.robot.common.thread.HttpThreadTool;
public class HttpUtil {
	// 日志
	private static final XLogger LOGGER = XLoggerFactory
			.getXLogger(HttpUtil.class);
	static String tokenId="";
	static Long loginTime=0l;
	
	public static void main(String[] args) {
//		String pwdstr="123456";
//		String key=EncryptUtils.getAESKeyShare();
//		try {
//			String ss=EncryptUtils.encryptPassByAES(pwdstr, key);
//			System.out.println(ss);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		for (int i = 0; i < 10; i++) {
		int num=ObjectUtil.getRandom(3*1)+2*1;
		System.out.println(num);
		}
		
		
//		String result="2323errorCode\":4001,asdfasdf";
//		System.out.println(result.indexOf("errorCode\":4001")>0);
	}

	/**
	 * 用户登录
	 */
	public static void executeLogin( ) {  
		Long nowtime=System.currentTimeMillis();
		int loginTimeMinter=(int) ((nowtime-loginTime)/(1000*60));
//		LOGGER.info(tokenId+"登录了"+loginTimeMinter+"分钟");
		if(tokenId.equals("")||loginTimeMinter>1440){//每天登录一次，防止token失效
			loginTime=nowtime;
			String username=ShowRobotConstants.SHOW_BRUSH_DATA_LOGIN_USER_NAME;
			String pwd=ShowRobotConstants.getRobotUserPwd();
			try {
				pwd=URLEncoder.encode(pwd,"UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			String url="http://mportal.xiu.com/loginReg/requestSubmitLogin.shtml?memberVo.logonName="+username+"&memberVo.password="+pwd+"&memberVo.regType=02&memberVo.isRemember=1&loginChannel=mobile-wap&encryptFlag=Y&jsoncallback=jsonp3";
			HttpClient httpClient = new HttpClient();  //打开窗口
			GetMethod getMethod = new GetMethod(url); //输入网址
			getMethod.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			getMethod.setRequestHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
			getMethod.setRequestHeader("Connection","keep-alive");
			getMethod.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:36.0) Gecko/20100101 Firefox/36.0");
			getMethod.setRequestHeader("Host","show.xiu.com");
			try {
				int statusCode = httpClient.executeMethod(getMethod);  //按下回车运行，得到返回码
				if (statusCode != HttpStatus.SC_OK) {
					LOGGER.error("Method failed: " + getMethod.getStatusLine());
				}
				//读取内容 
				byte[] responseBody = getMethod.getResponseBody();  //得到返回的内容
				//处理内容
				LOGGER.info(Thread.currentThread().getName()+" "+new String(responseBody));   
				
				  Cookie[] cookies = httpClient.getState().getCookies();
		        if (cookies.length==0) {    
		            System.out.println("None");    
		        } else {    
		            for (int i = 0; i < cookies.length; i++) {  
		            	String cookiestr=cookies[i].toString();
		            	if(cookiestr.indexOf("tokenId")>0){
		            		tokenId=  cookiestr.substring(cookiestr.indexOf("=")+1);
		            		break;
		            	}
		            }
		        }  
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				getMethod.releaseConnection();
			}
		}
	}  
	
	/**
	 * url 访问的url地址
	 * userId 登录用户id
	 */
    public static void executeGet(String url,String userId) {  
//    	LOGGER.info("executeGet-begin");  
    	 executeLogin();//登录获取token
    	 int sleepnum=ObjectUtil.getRandom(3);
			 try {
				Thread.sleep(sleepnum*1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
    	 HttpClient httpClient = new HttpClient();  //打开窗口
    	  GetMethod getMethod = new GetMethod(url); //输入网址
    	  getMethod.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    	     getMethod.setRequestHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
    	     getMethod.setRequestHeader("Connection","keep-alive");
    	      getMethod.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:36.0) Gecko/20100101 Firefox/36.0");
    	      getMethod.setRequestHeader("Host","show.xiu.com");
    	      getMethod.setRequestHeader("Cookie","xiu.login.tokenId="+tokenId+"; xiu.login.userId="+userId);
    	  try {
    	int statusCode = httpClient.executeMethod(getMethod);  //按下回车运行，得到返回码
    	if (statusCode != HttpStatus.SC_OK) {
    	   System.err.println("Method failed: " + getMethod.getStatusLine());
    	  }
    	
    	  //读取内容 
//    	  byte[] responseBody = getMethod.getResponseBody();  //得到返回的内容
//    	  String result=new String(responseBody);
    	  
	      	BufferedReader reader = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream()));  
	      	StringBuffer stringBuffer = new StringBuffer();  
	      	String str = "";  
	      	while((str = reader.readLine())!=null){  
	      	    stringBuffer.append(str);  
	      	}  
	      	String result = stringBuffer.toString();  
	      	System.out.println(result);
    	  
    	  
    	  if(result.indexOf("errorCode\":0")>0){//需重新登录
    		  LOGGER.info("点赞成功");
    	  }
    	  else if(result.indexOf("errorCode\":4001")>0){//需重新登录
    		    tokenId="";
    		    loginTime=0l;
    		  executeLogin();//登录获取token
    	  }else{
    		  LOGGER.info("点赞失败:"+result);
    	  }
//      	LOGGER.info("executeGet-end");  
    	  //处理内容
//    		LOGGER.info(new String(responseBody));    
    	} catch (Exception e) {
    	e.printStackTrace();
    	}finally{
    	getMethod.releaseConnection();
    	}
    }  
    
    /**
     * 线程执行访问接口业务
     * @param url
     * @param userId
     */
    public static void executeGetByThread(String url,List<String> showIds,List<String> userIds){
    	HttpThread t=new HttpThread(url,showIds,userIds);
    	Thread t1=new Thread(t);
    	HttpThreadTool.getInstance().execute(t1);
    }
    
   
    
  
    /**
     * POST
     * url 访问的url地址
     * userId 登录用户id
     */
    public static void executePost(String url,String userId,Map<String,String> param) {  
    	HttpClient httpClient = new HttpClient();  //打开窗口
    	PostMethod getMethod = new PostMethod(url); //输入网址
    	getMethod.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    	getMethod.setRequestHeader("Accept-Encoding","gzip, deflate");
    	getMethod.setRequestHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
    	getMethod.setRequestHeader("Connection","keep-alive");
    	getMethod.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:36.0) Gecko/20100101 Firefox/36.0");
    	getMethod.setRequestHeader("Host","show.xiu.com");
    	getMethod.setRequestHeader("Cookie","xiu.login.tokenId="+tokenId+"; xiu.login.userId="+userId);
    	for(Map.Entry<String, String> entry: param.entrySet())   {  
    		getMethod.addParameter(entry.getKey() , entry.getValue());
    	}
    	try {
    		int statusCode = httpClient.executeMethod(getMethod);  //按下回车运行，得到返回码
    		System.out.println(statusCode);
    		if (statusCode != HttpStatus.SC_OK) {
    			System.err.println("Method failed: " + getMethod.getStatusLine());
    		}
    		//读取内容 
    		byte[] responseBody = getMethod.getResponseBody();  //得到返回的内容
    		//处理内容
    		System.out.println(new String(responseBody));   
    	} catch (Exception e) {
    		e.printStackTrace();
    	}finally{
    		getMethod.releaseConnection();
    	}
    }  
  
	
}
