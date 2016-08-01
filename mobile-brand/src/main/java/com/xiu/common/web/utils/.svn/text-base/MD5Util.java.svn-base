package com.xiu.common.web.utils;

import java.security.MessageDigest;

/** 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 
 * @AUTHOR : mike@xiu.com 
 * @DATE :2013-5-22 下午3:33:16
 * </p>
 **************************************************************** 
 */
public class MD5Util {

    private MD5Util() {
    }

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    /**
     * Computes the MD5 fingerprint of a string.
     * 
     * @return the MD5 digest of the input <code>String</code>
     */
    public static String compute(String inStr){
        try {
            
            char[] charArray =inStr.toCharArray();
         
            byte[] byteArray = new byte[charArray.length];
         
            for (int i=0; i<charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
            MessageDigest md5=MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(byteArray);

            StringBuffer hexValue = new StringBuffer();

            for (int i=0; i<md5Bytes.length; i++)
            {
               int val = ((int) md5Bytes[i] ) & 0xff; 
               if (val < 16) hexValue.append("0");
               hexValue.append(Integer.toHexString(val));
            }

            return hexValue.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");

            resultString = byteArrayToHexString(md.digest(resultString
                    .getBytes()));

        } catch (Exception exception) {
        }
        return resultString;
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    
    private static final String hexDigits2[] = { "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
}
