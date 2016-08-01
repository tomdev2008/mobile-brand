package com.xiu.common.web.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xiu.common.web.utils.UserAuthInfoHolder;
import com.xiu.common.web.utils.UserSessionUtil;

/**
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION :
 * @AUTHOR : mike@xiu.com
 * @DATE :2014-1-21 下午3:01:34
 *       </p>
 **************************************************************** 
 */
public class SessionInterceptor extends AnnotationBasedIgnoreableInterceptor {

    @Override
    protected boolean preHandleInternal(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (handler == null || !HandlerMethod.class.isAssignableFrom(handler.getClass())) {
            return true;
        }
        String sessionId = UserSessionUtil.getSessionId(request);
        
        if (sessionId == null) {
            UUID uuid = UUID.randomUUID();
            sessionId = uuid.toString();
        }
        UserSessionUtil.setSessionId(response, sessionId);
        UserAuthInfoHolder.setSessionId(sessionId);
        return true;
    }

    @Override
    protected void postHandleInternal(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }

    @Override
    protected void afterCompletionInternal(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) throws Exception {
        UserAuthInfoHolder.clearSessionId();
    }

}
