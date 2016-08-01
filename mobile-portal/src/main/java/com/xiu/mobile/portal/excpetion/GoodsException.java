package com.xiu.mobile.portal.excpetion;

public class GoodsException extends Exception {
	private static final long serialVersionUID = 3130785664637562612L;

	public GoodsException() {
		super();
	}

	public GoodsException(String message, Throwable cause) {
		super(message, cause);
	}

	public GoodsException(String message) {
		super(message);
	}

	public GoodsException(Throwable cause) {
		super(cause);
	}

}
