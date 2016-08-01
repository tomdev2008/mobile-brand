package com.xiu.manager.web.utils;

import org.springframework.util.Assert;

import com.xiu.manager.web.model.User;


public class AdminAuthInfoHolder {
	private static final ThreadLocal<User> threadLocal = new ThreadLocal<User>(); // NOPMD

	public static User getUserAuthInfo() {
		return threadLocal.get();
	}

	public static void setUserAuthInfo(User user) {
		Assert.notNull(user,
				"Only non-null UserAuthInfo instances are permitted");
		threadLocal.set(user);
	}

	public static void clear() {
		threadLocal.remove();
	}
}
