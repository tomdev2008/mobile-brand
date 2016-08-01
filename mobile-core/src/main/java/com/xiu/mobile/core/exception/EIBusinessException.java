package com.xiu.mobile.core.exception;

import org.apache.commons.lang.StringUtils;

/** 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 
 * @AUTHOR : mike.gao@xiu.com 
 * @DATE :2012-4-18 下午4:12:49
 * </p>
 **************************************************************** 
 */
public class EIBusinessException extends BusinessException {

    private static final long serialVersionUID = 3665325278700219286L;
    private String extErrCode;
    private String extMessage;
    
    public EIBusinessException(String message, String code, String extCode, String extMessage) {
        super(message, code);
        this.extErrCode = extCode;
        this.extMessage = extMessage;
    }
    
    public String getErrCode() {
        if (null != extErrCode) {
            StringBuilder builder = new StringBuilder();
            builder.append(super.getErrCode())
                   .append("#")
                   .append(extErrCode);
            
            return builder.toString();
        }
        
        return super.getErrCode();
    }
    
    @Override
    public String getReadableMessage() {
        return StringUtils.isNotEmpty(this.extMessage)? this.extMessage : super.getMessage();

    }
    
    public String getExtMessage() {
        return this.extMessage;
    }
    
    public String getExtErrCode() {
        return this.extErrCode;
    }
}
