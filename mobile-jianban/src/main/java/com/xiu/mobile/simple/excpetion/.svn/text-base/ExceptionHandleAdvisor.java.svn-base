package com.xiu.mobile.simple.excpetion;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.aop.ThrowsAdvice;

import com.xiu.mobile.core.exception.BaseException;
import com.xiu.mobile.core.exception.BusinessException;
import com.xiu.mobile.core.exception.EIRuntimeException;
import com.xiu.sales.common.tools.DateUtils;

/***
 * 统一日志进行拦截
 * @author Administrator
 *
 */
public class ExceptionHandleAdvisor implements ThrowsAdvice{
	private static final Logger logger = Logger.getLogger(ExceptionHandleAdvisor.class);

	public void afterThrowing(Method method, Object[] args, Object target, Throwable ex) throws Throwable {
		
		//==如果为根异常不再处理
		if (ex instanceof BaseException) throw ex;
		
		//==日志
		logger.error("===========================["+DateUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss")+"]===========================");   
		logger.error("Class : " + target);
		logger.error("Method: " + method.getName());
		logger.error("Params: "+args);
		logger.error("Detail: ",ex);
		logger.error("===========================================================================\r\n");
		
		//==处理EIRun异常
        if(ex instanceof EIRuntimeException){
        	EIRuntimeException eiRuntimeEx = (EIRuntimeException)ex;
        	String message = eiRuntimeEx.getMessage();
            throw new BaseException(message);
        }
		
		//==处理业务异常
        if(ex instanceof BusinessException){
        	BusinessException businessEx = (BusinessException)ex;
        	String message = businessEx.getMessage();
            throw new BaseException(message);
        }

        //==如果没有相关异常匹配 直接抛出异常
        throw new BaseException(ex.getMessage());
	}
}
