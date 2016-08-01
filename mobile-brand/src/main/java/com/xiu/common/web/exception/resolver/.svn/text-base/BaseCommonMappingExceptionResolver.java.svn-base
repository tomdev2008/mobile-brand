package com.xiu.common.web.exception.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.xiu.common.web.contants.Constants;
import com.xiu.common.web.utils.JsonPackageWrapper;
import com.xiu.common.web.utils.RequestUtil;
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
        ModelAndView model = super.doResolveException(request, response, handler, ex);

        if ((model != null) && (ex != null)) {
            if (RequestUtil.isJsonRequest(request) && BaseException.class.isAssignableFrom(ex.getClass())) { 
                model.addObject(Constants.JSON_MODEL__DATA, new JsonPackageWrapper(JsonPackageWrapper.S_ERR,
                        ((BaseException) ex).getMessageWithSupportCode()));
            }
            model.addObject("errorMsg", generateErrorMessage(request, response, ex));
        }

        return model;
    }

    private String generateErrorMessage(HttpServletRequest request, HttpServletResponse response, Exception ex) {
    	if(BaseException.class.isAssignableFrom(ex.getClass())){
    		return ((BaseException) ex).getMessageWithSupportCode();
        } else {
            return ErrConstants.GENERAL_COMM_ERR_MSG + "["+ErrConstants.HttpErrorCode.HTTP_INTERNAL_SERVER_ERROR + "]";
        }
    }
}
