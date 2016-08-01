package com.xiu.mobile.portal.model;

import java.io.Serializable;

/**
 * 
 * 
 * @author
 * 
 * */
public class PayInfoVO implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    private String payInfo;
    
    private String retCode;
    
    private String errorMsg;
    
    public String getPayInfo()
    {
        return payInfo;
    }
    
    public void setPayInfo(String payInfo)
    {
        this.payInfo = payInfo;
    }
    
    public String getRetCode()
    {
        return retCode;
    }
    
    public void setRetCode(String retCode)
    {
        this.retCode = retCode;
    }

	public String getErrorMsg()
	{
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg)
	{
		this.errorMsg = errorMsg;
	}
    
    
}
