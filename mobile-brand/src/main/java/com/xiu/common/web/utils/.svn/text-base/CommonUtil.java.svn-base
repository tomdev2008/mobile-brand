package com.xiu.common.web.utils;
/** 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 
 * @AUTHOR : kolenxiao@xiu.com 
 * @DATE :2012-8-6 下午4:19:16
 * </p>
 **************************************************************** 
 */
public class CommonUtil {
    
    /**
     * 
    * @Title: getBlurryEmail
    * @Description: 得到模糊处理的邮箱
    * @param sourceEmail 待处理的邮箱
    * @return 处理后的邮箱
     */
    public static String getBlurryEmail(String sourceEmail) {
        int index = sourceEmail.indexOf("@");
        if (index == -1)
            return sourceEmail;
        String prefix = sourceEmail.substring(0, index);
        String postfix = sourceEmail.substring(index);
        int prelen = prefix.length();

        if (prelen > 2) {
            return prefix.substring(0, 2) + "****" + prefix.substring(prelen - 2, prelen) + postfix;
        } else {
            return sourceEmail;
        }
    }
    
    /**
     * 
    * @Title: getBlurryMobile
    * @Description: 得到模糊处理的手机号
    * @param sourceMobile 待处理的手机号
    * @return 处理后的手机号
     */
    public static String getBlurryMobile(String sourceMobile) {
        return sourceMobile.substring(0, 3) + "****" + sourceMobile.substring(7, 11);
    }

}
