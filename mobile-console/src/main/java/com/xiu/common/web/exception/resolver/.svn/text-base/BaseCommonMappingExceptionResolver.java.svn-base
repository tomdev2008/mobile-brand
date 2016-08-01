package com.xiu.common.web.exception.resolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.xiu.mobile.core.constants.ErrConstants;
import com.xiu.mobile.core.exception.BaseException;

/**
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION :
 * @AUTHOR : xiu@xiu.com
 * @DATE :2012-5-10 下午12:01:01
 *       </p>
 **************************************************************** 
 */
public class BaseCommonMappingExceptionResolver extends SimpleMappingExceptionResolver {
	
	
	
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {

        // Expose ModelAndView for chosen error view.  
    	ex.printStackTrace();
        String viewName = determineViewName(ex, request);  
        if (viewName != null) {//JSP格式返回  
            if(!(request.getHeader("accept").indexOf("application/json")>-1 )){//如果不是异步请求  
                // Apply HTTP status code for error views, if specified.  
                // Only apply it if we're processing a top-level request.  
                Integer statusCode = determineStatusCode(request, viewName);  
                if (statusCode != null) {  
                    applyStatusCodeIfPossible(request, response, statusCode);  
                    return getModelAndView(viewName, ex, request);  
                }  
            }else{//JSON格式返回  
                Map<String, Object> model=new HashMap<String, Object>();  
                if(this.logger.isDebugEnabled()){  
                    model.put("debug", true);   
                }//exception  
                model.put("msg", ex.getMessage());  
                model.put("failure", true);  
                try {  
                    response.getWriter().write("有异常啦!");  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
                return new ModelAndView();  
            }  
            return null;  
        }  
        else {  
            return null;  
        }  
    }

    private String generateErrorMessage(HttpServletRequest request, HttpServletResponse response, Exception ex) {
    	if(BaseException.class.isAssignableFrom(ex.getClass())){
    		return ((BaseException) ex).getMessageWithSupportCode();
        } else {
            return ErrConstants.GENERAL_COMM_ERR_MSG + "["+ErrConstants.HttpErrorCode.HTTP_INTERNAL_SERVER_ERROR + "]";
        }
    }
}
