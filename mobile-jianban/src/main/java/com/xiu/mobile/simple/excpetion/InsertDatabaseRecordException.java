package com.xiu.mobile.simple.excpetion;

/**
 * 创建数据库记录失败异常
 * 
 * 
 */
public class InsertDatabaseRecordException extends RuntimeException {

	private static final long serialVersionUID = 7693561973676622384L;

	public InsertDatabaseRecordException() {
		super();
	}

	public InsertDatabaseRecordException(String msg) {
		super(msg);
	}

}
