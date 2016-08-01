package com.xiu.common.web.interceptor.validator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.xiu.common.web.contants.AuthLevel;
import com.xiu.common.web.interceptor.validator.AuthorizationValidator;
import com.xiu.manager.web.biz.UserService;
import com.xiu.manager.web.model.User;
import com.xiu.manager.web.utils.AdminSessionUtil;

@Component("adminCookie")
public class AdminCookieValidator implements AuthorizationValidator {
	@Autowired
	private UserService userService;

	@Override
	public boolean validate(HttpServletRequest request, AuthLevel level) {
		// TODO Auto-generated method stub
		Assert.notNull(level);
		if (AuthLevel.NONE == level) {
			return true;
		}
		User user = AdminSessionUtil.getUser(request);
		if (user == null) {
			return false;
		}
//		User user2 = userService.getUser(user.getId());
//		if (!user.getVersion().equals(user2.getVersion())) {
//			return false;
//		}
		return true;
	}

}
