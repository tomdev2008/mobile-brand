package com.xiu.common.web.utils;

import org.springframework.util.Assert;

import com.xiu.mobile.core.model.UserAuthInfo;

/**
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION :
 * @AUTHOR : xiu@xiu.com
 * @DATE :2012-4-7 下午9:36:13
 *       </p>
 **************************************************************** 
 */
public class UserAuthInfoHolder {
    private static final ThreadLocal<UserAuthInfo> threadLocal = new ThreadLocal<UserAuthInfo>(); // NOPMD

    public static UserAuthInfo getUserAuthInfo() {
        return threadLocal.get();
    }

    public static void setUserAuthInfo(UserAuthInfo user) {
        Assert.notNull(user, "Only non-null UserAuthInfo instances are permitted");
        threadLocal.set(user);
    }

    public static void clear() {
        threadLocal.remove();
    }
}
