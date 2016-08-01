package com.xiu.common.web.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
  
public class RSAEncrypt {  
      
	/** 指定加密算法为DESede */
	private static String ALGORITHM = "RSA";
	
	/** 指定公钥存放文件 */
	
	private static String PUBLIC_KEY_FILE = RsaEncodeUtils.class.getResource("/").getPath() + "PUBLICKEY";
	/** 指定私钥存放文件 */
	private static String PRIVATE_KEY_FILE = RsaEncodeUtils.class.getResource("/").getPath() + "PRIVATEKEY";
	
	
    public static final String DEFAULT_PUBLIC_KEY=   
        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCsINQTMJ2KUgNMXWhe3ttexbHz" + "\r" +  
        "7jA5EzqMO89u7WJKcczMzNq7nnGaIlwNfKaMpmCHlvU5P0ab9KdqdUmfVqjRUeOV" + "\r" +  
        "61iAGK3uoBKiOO7TNDwNtBOJQTfSUURUREwo2KonDcHaiNHNqTlV943dWgYMDrFR" + "\r" +  
        "P4FsWgWKjn88tpAXXwIDAQAB" + "\r";  
       
    public static final String DEFAULT_PRIVATE_KEY=  
    		"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKwg1BMwnYpSA0xd"+"\r"+
			"aF7e217FsfPuMDkTOow7z27tYkpxzMzM2ruecZoiXA18poymYIeW9Tk/Rpv0p2p1"+"\r"+
			"SZ9WqNFR45XrWIAYre6gEqI47tM0PA20E4lBN9JRRFRETCjYqicNwdqI0c2pOVX3"+"\r"+
			"jd1aBgwOsVE/gWxaBYqOfzy2kBdfAgMBAAECgYBqcEp0K2Xf+OMNP92gfNLOghWO"+"\r"+
			"r8WmJZvYx8t33tcfDcwFANBpoIrDM2gU9vXOl7utXM37PoOdQOad0DVckKwG8KzA"+"\r"+
			"K7gESOivI0bwW9DtaKkE2ywpCr9fkbo/KtE+2eIbFLmTHhkDbqoMpzVBQKcedzWa"+"\r"+
			"tk13jh7yM+wWx6R0MQJBANKEvFmCGzw4UPmyBpkeq2fkKLC0dElds/YQ/4kJzl/F"+"\r"+
			"VrzD1LaMJvZuJDpUdDqZOr3GUAagWbyWb6xj38wPo+MCQQDRUNIpy+HxdqOM2AaG"+"\r"+
			"+ryKEDnJAlkVkHLRvscmUOJrCoHRIigWI+8xjXT3+Olu0F2Mxtwavwz1JdOY9Hiz"+"\r"+
			"GC9VAkEAzQdhuXsxs1DV7JoqOu7X2XMo/hCCQQH1x21+sqTOThAQDgBcJv4Q5GkY"+"\r"+
			"94ZRPNEHmNSQFCQPwdByKt+Kk1+YKQJASg1/7Q2WqCjDAAdFd+epYGcRKo5MGhb6"+"\r"+
			"qD4IkwO2twwGlYiyCfM4op1bLoe/flb1YeLQ6B2DZg+15r1HHoWF6QJBAMItibbq"+"\r"+
			"6qOB2T95V2ZlLEaT4tUa0g6AJ/qrL/nNchmiBrRcQHqbqJfoyepgUzpkdEBBxUs/"+"\r"+
			"wZhWH0JHcYENqkQ=" +"\r"; 
  
    /** 
     * 私钥 
     */  
    private RSAPrivateKey privateKey;  
  
    /** 
     * 公钥 
     */  
    private RSAPublicKey publicKey;  
      
    /** 
     * 字节数据转字符串专用集合 
     */  
    private static final char[] HEX_CHAR= {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};  
      
  
    /** 
     * 获取私钥 
     * @return 当前的私钥对象 
     */  
    public RSAPrivateKey getPrivateKey() {  
        return privateKey;  
    }  
  
    /** 
     * 获取公钥 
     * @return 当前的公钥对象 
     */  
    public RSAPublicKey getPublicKey() {  
        return publicKey;  
    }  
  
    /** 
     * 随机生成密钥对 
     */  
    public void genKeyPair(){  
        KeyPairGenerator keyPairGen= null;  
        try {  
            keyPairGen= KeyPairGenerator.getInstance("RSA");  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        keyPairGen.initialize(1024, new SecureRandom());  
        KeyPair keyPair= keyPairGen.generateKeyPair();  
        this.privateKey= (RSAPrivateKey) keyPair.getPrivate();  
        this.publicKey= (RSAPublicKey) keyPair.getPublic();  
    }  
  
    /** 
     * 从文件中输入流中加载公钥 
     * @param in 公钥输入流 
     * @throws Exception 加载公钥时产生的异常 
     */  
    public void loadPublicKey(InputStream in) throws Exception{  
        try {  
            BufferedReader br= new BufferedReader(new InputStreamReader(in));  
            String readLine= null;  
            StringBuilder sb= new StringBuilder();  
            while((readLine= br.readLine())!=null){  
                if(readLine.charAt(0)=='-'){  
                    continue;  
                }else{  
                    sb.append(readLine);  
                    sb.append('\r');  
                }  
            }  
            loadPublicKey(sb.toString());  
        } catch (IOException e) {  
            throw new Exception("公钥数据流读取错误");  
        } catch (NullPointerException e) {  
            throw new Exception("公钥输入流为空");  
        }  
    }  
  
  
    /** 
     * 从字符串中加载公钥 
     * @param publicKeyStr 公钥数据字符串 
     * @throws Exception 加载公钥时产生的异常 
     */  
    public void loadPublicKey(String publicKeyStr) throws Exception{  
        try {  
            BASE64Decoder base64Decoder= new BASE64Decoder();  
            byte[] buffer= base64Decoder.decodeBuffer(publicKeyStr);  
            KeyFactory keyFactory= KeyFactory.getInstance("RSA");  
            X509EncodedKeySpec keySpec= new X509EncodedKeySpec(buffer);  
            this.publicKey= (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("公钥非法");  
        } catch (IOException e) {  
            throw new Exception("公钥数据内容读取错误");  
        } catch (NullPointerException e) {  
            throw new Exception("公钥数据为空");  
        }  
    }  
  
    /** 
     * 从文件中加载私钥 
     * @param keyFileName 私钥文件名 
     * @return 是否成功 
     * @throws Exception  
     */  
    public void loadPrivateKey(InputStream in) throws Exception{  
        try {  
            BufferedReader br= new BufferedReader(new InputStreamReader(in));  
            String readLine= null;  
            StringBuilder sb= new StringBuilder();  
            while((readLine= br.readLine())!=null){  
                if(readLine.charAt(0)=='-'){  
                    continue;  
                }else{  
                    sb.append(readLine);  
                    sb.append('\r');  
                }  
            }  
            loadPrivateKey(sb.toString());  
        } catch (IOException e) {  
            throw new Exception("私钥数据读取错误");  
        } catch (NullPointerException e) {  
            throw new Exception("私钥输入流为空");  
        }  
    }  
  
    public void loadPrivateKey(String privateKeyStr) throws Exception{  
        try {  
            BASE64Decoder base64Decoder= new BASE64Decoder();  
            byte[] buffer= base64Decoder.decodeBuffer(privateKeyStr);  
            PKCS8EncodedKeySpec keySpec= new PKCS8EncodedKeySpec(buffer);  
            KeyFactory keyFactory= KeyFactory.getInstance("RSA");  
            this.privateKey= (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("私钥非法");  
        } catch (IOException e) {  
            throw new Exception("私钥数据内容读取错误");  
        } catch (NullPointerException e) {  
            throw new Exception("私钥数据为空");  
        }  
    }  
  
    /** 
     * 加密过程 
     * @param publicKey 公钥 
     * @param plainTextData 明文数据 
     * @return 
     * @throws Exception 加密过程中的异常信息 
     */  
    public String encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception{  
        
    	
    	Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] b = plainTextData;
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
     * 解密过程 
     * @param privateKey 私钥 
     * @param cipherData 密文数据 
     * @return 明文 
     * @throws Exception 解密过程中的异常信息 
     */  
    public String decrypt(RSAPrivateKey privateKey, String cipherData) throws Exception{  
      
    	Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] b1 = decoder.decodeBuffer(cipherData);
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
  
      
    /** 
     * 字节数据转十六进制字符串 
     * @param data 输入数据 
     * @return 十六进制内容 
     */  
    public static String byteArrayToString(byte[] data){  
        StringBuilder stringBuilder= new StringBuilder();  
        for (int i=0; i<data.length; i++){  
            //取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移  
            stringBuilder.append(HEX_CHAR[(data[i] & 0xf0)>>> 4]);  
            //取出字节的低四位 作为索引得到相应的十六进制标识符  
            stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);  
            if (i<data.length-1){  
                stringBuilder.append(' ');  
            }  
        }  
        return stringBuilder.toString();  
    }  
  
  
    public static void main(String[] args){  
        RSAEncrypt rsaEncrypt= new RSAEncrypt();  
        //rsaEncrypt.genKeyPair();  
        //加载公钥  
        try {  
            rsaEncrypt.loadPublicKey(RSAEncrypt.DEFAULT_PUBLIC_KEY);
//        	rsaEncrypt.loadPublicKey(new FileInputStream(PUBLIC_KEY_FILE));
            System.out.println("文件加载公钥成功");  
        } catch (Exception e) {  
            System.err.println(e.getMessage());  
            System.err.println("加载公钥失败");  
        }  
  
        //加载私钥  
        try {  
//        	rsaEncrypt.loadPublicKey(new FileInputStream(PRIVATE_KEY_FILE));
          rsaEncrypt.loadPrivateKey(RSAEncrypt.DEFAULT_PRIVATE_KEY);  
            System.out.println("加载私钥成功");  
        } catch (Exception e) {  
            System.err.println(e.getMessage());  
            System.err.println("加载私钥失败");  
        }  
  
        //测试字符串  
       
//        String encryptStr="hello world!";
        try {  
            //加密  
           /* String cipher = rsaEncrypt.encrypt(rsaEncrypt.getPublicKey(), encryptStr.getBytes());  
            //解密  
            String plainText = rsaEncrypt.decrypt(rsaEncrypt.getPrivateKey(), cipher);  
            System.out.println("密文长度:"+ cipher);  
            System.out.println("明文长度:"+ plainText);  */
            
            /*******正常流程*****/
            File filestr = new File("E://main.js");
            
            FileInputStream  fis = new FileInputStream(filestr);  
            FileChannel  fileChannel = fis.getChannel();  
            MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, filestr.length());
            
            MessageDigest md = MessageDigest.getInstance("MD5");  
            md.update(byteBuffer);  
            String md5 = byteArrayToString(md.digest());  
            System.out.println("MD5结果:"+md5.replaceAll(" ", ""));
            
            String encryptStr=md5.replaceAll(" ", "");  
            
            String cipher = rsaEncrypt.encrypt(rsaEncrypt.getPublicKey(), encryptStr.getBytes());  
            //解密  
            String plainText = rsaEncrypt.decrypt(rsaEncrypt.getPrivateKey(), cipher);  
            System.out.println("密文长度:"+ cipher);  
            System.out.println("明文长度:"+ plainText); 
            
            
            System.out.println("最后加密:"+rsaEncrypt.encrypt(rsaEncrypt.getPublicKey(), "d38e1c8a0bef93f95e6d668ab4b1a240".getBytes()));
         
            System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
            String ss="l4hB2u4Swluqb1DsLZjKcdbZxwAnp4H7M1gwHiVEs2KmKKcaFlZo1+uN9hv/t4GAG0TKzplAnvJC1i+wXaSZNNPvUgcsuNYSsPIP8imCwal+SxRUYHUDG9VOiohvbTIrRCLpdNZ/vd0/TAJ60XBaKSnjic3md0FQd7LkpA6pt/0=";
            System.out.println("YYY最后解密"+rsaEncrypt.decrypt(rsaEncrypt.getPrivateKey(), ss));
        } catch (Exception e) {  
            System.err.println(e.getMessage());  
        }  
    } 
    
}  

