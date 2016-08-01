package com.xiu.mobile.core.exception;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiu.mobile.core.constants.ErrConstants;

/** 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 
 * @AUTHOR : xiu@xiu.com 
 * @DATE :2012-4-18 下午3:23:27
 * </p>
 **************************************************************** 
 */
public final class ExceptionFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionFactory.class);
    
    private static Properties props = new Properties();
    
    static {
        InputStream is = ExceptionFactory.class.getResourceAsStream("/errorMessages_zh.properties");
        if(is != null){ 
            try {
                props.load(is);
            } catch (IOException e) {
                LOGGER.error("load error messages faild");
            }
        }
    }
    
    public static EIRuntimeException buildEIRuntimeException(String code, Throwable cause, String... params) {
        String message = formatMessage(code, params);
        if (message == null) {
            message = ErrConstants.GENERAL_UNKNOWN_ERR_MSG;
        }
        
        return new EIRuntimeException(message, code, cause);
    }
   
    public static EIRuntimeException buildEIRuntimeException(String code, String extCode, String extMsg, String... params) {
        String message = formatMessage(code, params);
        if (message == null) {
            message = extMsg;
        }
        return new EIRuntimeException(message, code, extCode, extMsg);
    }
    
    public static EIBusinessException buildEIBusinessException(String code, String extCode, String extMsg, String... params) {
        String message = formatMessage(code, params);
        if (message == null) {
            message = extMsg;
        }
        return new EIBusinessException(message, code, extCode, extMsg);   
    }
    
    public static BusinessException buildBusinessException(String code, String... params) {
        String message = formatMessage(code, params);
        if (message == null) {
            message = ErrConstants.GENERAL_UNKNOWN_ERR_MSG;
        }
        return new BusinessException(message, code);   
    }
    
    public static BaseException buildBaseException(String code, Throwable cause, String... params) {
        String message = formatMessage(code, params);
        if (message == null) {
            message = ErrConstants.GENERAL_UNKNOWN_ERR_MSG;
        }
        
        return new BaseException(message, code, cause);
    } 
    
    private static String formatMessage(String code, String... params) {
        String msg = props.getProperty(code);
        String message = null;
        
        if (StringUtils.isNotEmpty(msg)) {
            int count = StringUtils.countMatches(msg, "%s");
            Object[] values = new Object[count];
            for(int i=0; i<count; i++){
                if(i<params.length){
                    values[i] = params[i];
                }else{
                    values[i] = "";
                }
            }
            message = String.format(msg, values);
        } 
        
        return message;
    }
    
}
