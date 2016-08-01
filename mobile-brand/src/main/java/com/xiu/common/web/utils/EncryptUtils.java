package com.xiu.common.web.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * 如果加密 KEY 为空或KEY的长度小于24位使用默认的 ClientConstants.ENCRYPT_KEY key
 * 
 * @author Administrator
 * 
 */

public class EncryptUtils {
    private static final String CHAR_UTF_8 = "utf-8";
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(EncryptUtils.class);
    private static final String encryptSeparator = "@mt@";
    public static final String ENCRYPT_KEY = "asdf(xiudsda4fasportal--34sfcom"; // 必须大于24位，否则报错。

    /**
     * Encrypt UTF-8 对称加密
     * 
     * @param msg
     * @param key
     * @return
     */
    private static String encrypt(String msg, String key) {
        try {
            if (key == null || key.length() < 24) {
                return java.net.URLEncoder.encode(EncryptionUtils.encrypt(ENCRYPT_KEY, msg), CHAR_UTF_8);
            } else if (key != null && key.length() > 24) {
                return java.net.URLEncoder.encode(EncryptionUtils.encrypt(key, msg), CHAR_UTF_8);
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("加密失败-不支持的字符集", e);
        } catch (Exception e) {
            LOGGER.error("加密失败", e);
        }
        return null;
    }

    /**
     * Encrypt UTF-8 对称加密
     * 
     * @param msg
     * @param key
     * @return
     */
    public static String encrypt(String msg) {
        try {
            return java.net.URLEncoder.encode(EncryptionUtils.encrypt(ENCRYPT_KEY, msg), CHAR_UTF_8);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("加密失败-不支持的字符集", e);
        } catch (Exception e) {
            LOGGER.error("加密失败", e);
        }
        return null;
    }

    /**
     * Encrypt 对称加密
     * 
     * @param msg
     * @param key
     * @return
     */
    // public static String encrypt(String msg, String key) {
    // try {
    // if(key==null||key.length()<24)
    // {
    // return EncryptionUtils.encrypt(ClientConstants.ENCRYPT_KEY, msg);
    // }else if(key!=null&&key.length()>24){
    // return EncryptionUtils.encrypt(key, msg);
    // }
    // } catch (EncryptionException e) {
    // log.error(e);
    // }
    // return null;
    // }
    /**
     * Encrypt 对称解密
     * 
     * @param msg
     * @param key
     * @return
     */
    private static String decrypt(String msg, String key) {
        try {
            if (key == null || key.length() < 24) {
                return EncryptionUtils.decrypt(ENCRYPT_KEY, msg);
            } else if (key != null && key.length() > 24) {
                return EncryptionUtils.decrypt(key, msg);
            }
        } catch (Exception e) {
            LOGGER.error("解密失败", e);
        }
        return null;
    }

    /**
     * Encrypt 对称解密
     * 
     * @param msg
     * @param key
     * @return
     */
    public static String decrypt(String msg) {
        try {
            msg = java.net.URLDecoder.decode(msg, CHAR_UTF_8);
            return EncryptionUtils.decrypt(ENCRYPT_KEY, msg);
        } catch (Exception e) {
            LOGGER.error("解密失败", e);
        }
        return null;
    }

    /**
     * Encrypt 对称解密
     * 
     * @param msg
     * @param key
     * @return byte
     */
    public static byte[] decryptForArrayByte(byte[] bytes, String key) {
        try {
            if (key == null || key.length() < 24) {
                return EncryptionUtils.decrypt(ENCRYPT_KEY, bytes);
            } else if (key != null && key.length() > 24) {
                return EncryptionUtils.decrypt(key, bytes);
            }
        } catch (Exception e) {
            LOGGER.error("加密失败", e);
        }
        return null;
    }

    /**
     * 添加时间戳有效期的加密方法 String 的格式为： msg@mt@timestamp
     * 
     * @param args
     */
    public static String encryptForTimestamp(String message, String key) {
        if (key == null || key.length() < 24) {
            message = message + encryptSeparator + java.util.Calendar.getInstance().getTimeInMillis();
            return encrypt(message, ENCRYPT_KEY);
        } else if (key != null && key.length() > 24) {
            message = message + encryptSeparator + java.util.Calendar.getInstance().getTimeInMillis();
            return encrypt(message, key);
        }
        return null;
    }

    /**
     * 添加时间戳有效期的加密方法 String 的格式为： msg@mt@timestamp
     * 
     * @param args
     */
    public static String encryptForTimestamp(String message) {
        return encryptForTimestamp(message, null);
    }

    /**
     * 添加时间戳有效期的解密方法 String 的格式为： msg@mt@timestamp
     * 
     * @return String[2] string[0]:message;String[1]:timestamp;
     * @param args
     */
    public static String[] decryptForTimestamp(String message, String key) {
        if (key == null || key.length() < 24) {
            String msg = decrypt(message, ENCRYPT_KEY);
            String[] msgArr = StringUtils.split(msg, encryptSeparator);
            return msgArr;
        } else if (key != null && key.length() > 24) {
            String msg = decrypt(message, key);
            String[] msgArr = StringUtils.split(msg, encryptSeparator);
            return msgArr;
        }
        return null;
    }

    /**
     * 添加时间戳有效期的解密方法 String 的格式为： msg@mt@timestamp
     * 
     * @return String[2] string[0]:message;String[1]:timestamp;
     * @param args
     */
    public static String[] decryptForTimestamp(String message) {
        return decryptForTimestamp(message, null);
    }

    public static void main(String[] args) throws Exception {

        String msg2 = encrypt(String.valueOf(1205494));
        // System.out.println(msg2);
        //
        // for (int i = 1205494; i < 1205594; i++) {
        // String msg = encrypt(String.valueOf(i));
        // if (msg.contains("%2B")) {
        // System.out.println(i);
        // }
        // }

        String ena = EncryptionUtils.encrypt(ENCRYPT_KEY, "abcdef");
        System.out.println("abcdef加密后的字符串:" + encrypt("abcdef"));
        // System.out.println("abcdef加密前的字符串:" + encrypt("abcdef", "asdf(ad&dsda4fsafas--34sfasf"));
        System.out
                .println("abcdef解密后的字符串:="
                        + decrypt("8czqP0htF85bsOEFMIc5nGXH%2BITNXtAzGapEAsLkbU0H10ETCt6kUUglENxwrVaeEqsx%2F9ylc1B1rPluzAH%2BEmXH%2BITNXtAzR3Xo2esBQnLgTPd9gMWeihqLmmFzRNGRhQ5YcUZfZeEwlV1Ud%2Bz1Vae12tIa%2F6%2FT"));
        System.out.println("abcdef解密后的字符串:="
                + decrypt("X90M2raCzYbYdPBgDFLYG4%2FgtpTOu%2BaYJI2dzozwV5jgMp11lCmMhw%3D%3D"));
        //
        // String szSrc = "kjt2008"; // "kjtgdsti";
        //
        // System.out.println("SH1加密前的字符串:" + szSrc);
        //
        // String abc = null;
        // // abc = SHA1(szSrc);
        // //
        // // System.out.println("SHA1加密后的字符串:" + abc);
        // //
        // abc = encrypt(szSrc, "asdf(ad&dsda4fsafas--34sfasf");
        // System.out.println("des3加密后的字符串:" + abc);
        // // // abc =
        // // //
        // //
        // "5HJ2trAMCu1/StmFDLLg+722BmxI63HREZU1snP1wGnnBqhqIlRaQZSCrL9aSazth33gUIUULTXi3d268X7mRQaank5xYBGSprpJdY1VVg+nBz5aQXxRsw==";
        // String xxx = decrypt(abc, "asdf(ad&dsda4fsafas--34sfasf");
        // System.out.println("des3解密后的字符串:" + xxx);
        //
        // System.out.println("解密后的字符串:"
        // + (new String(
        // decryptForArrayByte("b8YllJTFbt1ogRWojl1dkpBEaI70rpR8OvdzWf9+uJM=".getBytes(),
        // "asdf(ad&dsda4fsafas--34sfasf"))));
        System.out.println(String.format("aa%sbb%s==cc%s==dd", new Object[] { "1", "%s", "%d" }));
    }

}
