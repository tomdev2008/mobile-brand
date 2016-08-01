package com.xiu.mobile.core.exception;
/** 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 
 * @AUTHOR : xiu@xiu.com 
 * @DATE :2012-4-18 下午3:41:33
 * </p>
 **************************************************************** 
 */
public class EIRuntimeException extends BaseException {
    
    private static final long serialVersionUID = -7900639249596835684L;

    private String extErrCode;
    private String extMessage;
    
    public EIRuntimeException(String message, String code) {
        super(message, code);
    }
    
    public EIRuntimeException(String message, String code, Throwable cause) {
        super(message, code, cause);
    }    
    
    public EIRuntimeException(String message, String code, String extCode, String extMessage) {
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
//        return StringUtils.isNotEmpty(this.extMessage)? this.extMessage : super.getMessage();
        return super.getMessage();
    }
    
    public String getExtMessage() {
        return this.extMessage;
    }
    
    public String getExtErrCode() {
        return this.extErrCode;
    }
}
