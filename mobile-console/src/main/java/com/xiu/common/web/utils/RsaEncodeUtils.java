package com.xiu.common.web.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

import javax.crypto.Cipher;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.xiu.mobile.core.utils.MD5SignUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RsaEncodeUtils {
	/** 指定加密算法为DESede */
	private static String ALGORITHM = "RSA";
	/** 指定key的大小 */
	private static int KEYSIZE = 1024;

	/** 指定公钥存放文件 */
	
	private static String PUBLIC_KEY_FILE = RsaEncodeUtils.class.getResource("/").getPath() + "PUBLICKEY";
	/** 指定私钥存放文件 */
	private static String PRIVATE_KEY_FILE = RsaEncodeUtils.class.getResource("/").getPath() + "PRIVATEKEY";
	
	
	
	
	
	/**
	 * 生成密钥对
	 * PUBLIC_KEY_FILE  公钥路径
	 * PRIVATE_KEY_FILE 私钥路径
	 */
	private static void generateKeyPair(String PUBLIC_KEY_FILE,
			String PRIVATE_KEY_FILE) throws Exception {
		/**
		 * 判断目标公钥和私钥是否存在,如果存在则不创建
		 */
		File picFile = new File(PUBLIC_KEY_FILE);
		if(! picFile.exists()){
			/** RSA算法要求有一个可信任的随机数源 */
			SecureRandom sr = new SecureRandom();
			/** 为RSA算法创建一个KeyPairGenerator对象 */
			KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
			/** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
			kpg.initialize(KEYSIZE, sr);
			/** 生成密匙对 */
			KeyPair kp = kpg.generateKeyPair();
			/** 得到公钥 */
			Key publicKey = kp.getPublic();
			/** 得到私钥 */
			Key privateKey = kp.getPrivate();
			/** 用对象流将生成的密钥写入文件 */
			ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(
					PUBLIC_KEY_FILE));
			ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(
					PRIVATE_KEY_FILE));
			oos1.writeObject(publicKey);
			oos2.writeObject(privateKey);
			/** 清空缓存，关闭文件输出流 */
			oos1.close();
			oos2.close();
		}
	
	}

	/**
	 * 加密方法 source： 源数据
	 */
	public static String encrypt(String source) throws Exception {
		generateKeyPair(PUBLIC_KEY_FILE, PRIVATE_KEY_FILE);
		/** 将文件中的公钥对象读出 */
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
				PUBLIC_KEY_FILE));
		Key key = (Key) ois.readObject();
		ois.close();
		/** 得到Cipher对象来实现对源数据的RSA加密 */
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] b = source.getBytes();
		/** 执行加密操作 */
		byte[] b1 = null;
		
		StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < b.length; i += 100) {  
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(b, i,  
                    i + 100));  
            sb.append(new String(doFinal));  
            b1 = ArrayUtils.addAll(b1, doFinal);  
        }  
		BASE64Encoder encoder = new BASE64Encoder();
		if(b1!=null && b1.length>0){
			return encoder.encode(b1);
		}else{
			return null;
		}
	}

	/**
	 * 解密算法 cryptograph:密文
	 */
	public static String decrypt(String cryptograph)
			throws Exception {
		/** 将文件中的私钥对象读出 */
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
				PRIVATE_KEY_FILE));
		Key key = (Key) ois.readObject();
		/** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] b1 = decoder.decodeBuffer(cryptograph);
		/** 执行解密操作 */
		byte[] b ;
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b1.length; i += 128) {
			byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(b1, i,
					i + 128));
			sb.append(new String(doFinal));
		}
		b = sb.toString().getBytes();
		
		if(b1!=null && b1.length>0){
			return new String(b);
		}else{
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		String source = "hello world";// 要加密的字符串
		String cryptograph = encrypt(source);// 生成的密文
		System.out.println("RSA加密：" + cryptograph);

		
		/*System.out.println("MD5加密后：" + MD5SignUtils.MD5Encode(cryptograph));*/
		String target = decrypt(cryptograph);// 解密密文
		System.out.println(target);
		
		/*String str="";
		System.out.println( "IOS解密:"+decrypt(str));*/

		/********文件加密****//*
		File filestr = new File("E://main.js");
		BufferedReader in = new BufferedReader(new FileReader(filestr));
		StringBuffer str=new StringBuffer();
		String st;
		while ((st = in.readLine()) != null) {
			str.append(st);
		}
		
		//MD5
		String result=MD5SignUtils.MD5Encode(str.toString());
		
		String sm=encrypt(result);
		in.close();
		System.out.println("main.js加密结果："+sm.toString());
		System.out.println(decrypt(sm));
		
		//输出到文件
		String encodePath="E://main1.js";
		FileOutputStream fos = new FileOutputStream(encodePath);
		fos.write(sm.toString().getBytes());
		fos.close();
		
		
		*//*******文件解密********//*
		File decre = new File("E://main1.js");
		BufferedReader inde = new BufferedReader(new FileReader(decre));
		StringBuffer str1=new StringBuffer();
		String st1;
		while ((st1 = inde.readLine()) != null) {
			str1.append(st1);
		}
		String sm1= decrypt(str1.toString());
		inde.close();
		System.out.println("main.js解密密结果："+sm1.toString());*/
		
//		 System.out.println(decrypt("", "jsurlpath"));
	}
}