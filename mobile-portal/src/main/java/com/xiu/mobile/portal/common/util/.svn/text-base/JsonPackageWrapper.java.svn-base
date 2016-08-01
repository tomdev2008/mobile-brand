package com.xiu.mobile.portal.common.util;

/**
 * 
 * <p>
 * ************************************************************** 
 * @Description: TODO(JSON统一输出格式) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-18 上午10:00:39 
 * ***************************************************************
 * </p>
 */
public class JsonPackageWrapper {
    public final static int S_OK = 0;
    public final static int S_ERR = -1;

    private int code;
    private String msg;
    private String errorCode;

    private Object data;

    public JsonPackageWrapper() {
    	code = JsonPackageWrapper.S_OK;
    	msg = "";
        data = null;
    }

    public JsonPackageWrapper(int scode) {
        this.code = scode;
        this.msg = "";
        this.data = null;
    }

    public JsonPackageWrapper(int scode, String smessage) {
        this.code = scode;
        this.msg = smessage;
        this.data = null;
    }

    public JsonPackageWrapper(int scode, Object data) {
        this.code = scode;
        this.msg = "";
        this.data = data;
    }

    public JsonPackageWrapper(int scode, String smessage, Object data) {
        this.code = scode;
        this.msg = smessage;
        this.data = data;
    }

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
    
}
