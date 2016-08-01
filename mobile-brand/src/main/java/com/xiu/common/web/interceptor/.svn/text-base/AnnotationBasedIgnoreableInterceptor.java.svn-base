package com.xiu.common.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xiu.common.web.annotation.IgnoredInterceptors;

/**
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION :
 * @AUTHOR : xiu@xiu.com
 * @DATE :2012-4-3 上午9:56:15
 *       </p>
 **************************************************************** 
 */
public abstract class AnnotationBasedIgnoreableInterceptor implements HandlerInterceptor {

    abstract protected boolean preHandleInternal(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception;

    abstract protected void postHandleInternal(HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView) throws Exception;

    abstract protected void afterCompletionInternal(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex) throws Exception;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isIgnoreable(handler)) {
            return true;
        }
        return preHandleInternal(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        if (isIgnoreable(handler)) {
            return;
        }
        postHandleInternal(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        if (isIgnoreable(handler)) {
            return;
        }
        afterCompletionInternal(request, response, handler, ex);
    }

    private boolean isIgnoreable(Object handler) {
        IgnoredInterceptors ignored = handler.getClass().getAnnotation(IgnoredInterceptors.class);

        if (ignored == null) {
            return false;
        }

        Class<? extends HandlerInterceptor>[] interceptors = ignored.value();
        return ArrayUtils.contains(interceptors, this.getClass());
    }
}
