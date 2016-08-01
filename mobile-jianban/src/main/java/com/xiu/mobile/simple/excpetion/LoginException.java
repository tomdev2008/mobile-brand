package com.xiu.mobile.simple.excpetion;

/**
 * 
 * 登录异常类
 * 
 * @author wangzhenjiang
 * 
 * @since  2014-05-07
 *
 */
public class LoginException extends Exception {

	private static final long serialVersionUID = 298910380486579878L;

	public LoginException() {
		super();
	}

	public LoginException(String message) {
		super(message);
	}

	public LoginException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoginException(Throwable cause) {
		super(cause);
	}

}
