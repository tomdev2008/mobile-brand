package com.xiu.mobile.wechat.facade.model;

import java.io.Serializable;

/**
* 消息通知结果 
*
* @author wangzhenjiang
*
* @since  2014年5月20日
*/
public class NotifyResult implements Serializable {
	private static final long serialVersionUID = 1L;

	/**是否成功 ：成功：true,失败：false*/
	private boolean isSuccess = false;
	/**返回码*/
	private String errorCode = "-1";
	/**说明*/
	private String errorMsg = "请求失败";

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		return "NotifyResult [isSuccess=" + isSuccess + ", errorCode=" + errorCode + ", errorMsg=" + errorMsg + "]";
	}

}
