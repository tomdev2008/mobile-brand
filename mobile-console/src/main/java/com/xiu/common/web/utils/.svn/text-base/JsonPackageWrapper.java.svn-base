package com.xiu.common.web.utils;

/**
 * @ClassName: JsonpPackageWrapper
 * @Description: JSON统一输出格式.
 * @author toney.li
 * @date 2012-3-22 下午3:47:42
 * 
 */
public class JsonPackageWrapper {
    final static public String S_OK = "0";
    final static public String S_ERR = "-1";

    private String scode;
    private String smsg;
    private String errorCode;

    private Object data;

    public JsonPackageWrapper() {
        scode = JsonPackageWrapper.S_OK;
        smsg = "";
        data = null;
    }

    public JsonPackageWrapper(String scode) {
        this.scode = scode;
        this.smsg = "";
        this.data = null;
    }

    public JsonPackageWrapper(String scode, String smessage) {
        this.scode = scode;
        this.smsg = smessage;
        this.data = null;
    }

    public JsonPackageWrapper(String scode, Object data) {
        this.scode = scode;
        this.smsg = "";
        this.data = data;
    }

    public JsonPackageWrapper(String scode, String smessage, Object data) {
        this.scode = scode;
        this.smsg = smessage;
        this.data = data;
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public String getSmsg() {
        return smsg;
    }

    public void setSmsg(String smessage) {
        this.smsg = smessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
