package com.xiu.mobile.wechat.core.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.ConnectException;
import java.net.MalformedURLException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.remoting.RemoteConnectFailureException;
import org.springframework.remoting.RemoteLookupFailureException;
import org.springframework.remoting.RemoteProxyFailureException;
import org.springframework.remoting.support.UrlBasedRemoteAccessor;
import org.springframework.util.Assert;

import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.client.HessianRuntimeException;
import com.caucho.hessian.io.SerializerFactory;

/**
 * {@link org.aopalliance.intercept.MethodInterceptor} for accessing a Hessian service.
 * Supports authentication via username and password.
 * The service URL must be an HTTP URL exposing a Hessian service.
 *
 * <p>Hessian is a slim, binary RPC protocol.
 * For information on Hessian, see the
 * <a href="http://www.caucho.com/hessian">Hessian website</a>
 *
 * <p>Note: There is no requirement for services accessed with this proxy factory
 * to have been exported using Spring's {@link HessianServiceExporter}, as there is
 * no special handling involved. As a consequence, you can also access services that
 * have been exported using Caucho's {@link com.caucho.hessian.server.HessianServlet}.
 *
 * @author Juergen Hoeller
 * @since 29.09.2003
 * @see #setServiceInterface
 * @see #setServiceUrl
 * @see #setUsername
 * @see #setPassword
 * @see HessianServiceExporter
 * @see HessianProxyFactoryBean
 * @see com.caucho.hessian.client.HessianProxyFactory
 * @see com.caucho.hessian.server.HessianServlet
 */
public class HessianClientInterceptor extends UrlBasedRemoteAccessor implements MethodInterceptor {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(HessianClientInterceptor.class);

    private HessianProxyFactory proxyFactory = new HessianProxyFactory();

    private Object hessianProxy;


    /**
     * Set the HessianProxyFactory instance to use.
     * If not specified, a default HessianProxyFactory will be created.
     * <p>Allows to use an externally configured factory instance,
     * in particular a custom HessianProxyFactory subclass.
     */
    public void setProxyFactory(HessianProxyFactory proxyFactory) {
        this.proxyFactory = (proxyFactory != null ? proxyFactory : new HessianProxyFactory());
    }

    /**
     * Specify the Hessian SerializerFactory to use.
     * <p>This will typically be passed in as an inner bean definition
     * of type <code>com.caucho.hessian.io.SerializerFactory</code>,
     * with custom bean property values applied.
     */
    public void setSerializerFactory(SerializerFactory serializerFactory) {
        this.proxyFactory.setSerializerFactory(serializerFactory);
    }

    /**
     * Set whether to send the Java collection type for each serialized
     * collection. Default is "true".
     */
    public void setSendCollectionType(boolean sendCollectionType) {
        this.proxyFactory.getSerializerFactory().setSendCollectionType(sendCollectionType);
    }

    /**
     * Set whether overloaded methods should be enabled for remote invocations.
     * Default is "false".
     * @see com.caucho.hessian.client.HessianProxyFactory#setOverloadEnabled
     */
    public void setOverloadEnabled(boolean overloadEnabled) {
        this.proxyFactory.setOverloadEnabled(overloadEnabled);
    }

    /**
     * Set the username that this factory should use to access the remote service.
     * Default is none.
     * <p>The username will be sent by Hessian via HTTP Basic Authentication.
     * @see com.caucho.hessian.client.HessianProxyFactory#setUser
     */
    public void setUsername(String username) {
        this.proxyFactory.setUser(username);
    }

    /**
     * Set the password that this factory should use to access the remote service.
     * Default is none.
     * <p>The password will be sent by Hessian via HTTP Basic Authentication.
     * @see com.caucho.hessian.client.HessianProxyFactory#setPassword
     */
    public void setPassword(String password) {
        this.proxyFactory.setPassword(password);
    }

    /**
     * Set whether Hessian's debug mode should be enabled.
     * Default is "false".
     * @see com.caucho.hessian.client.HessianProxyFactory#setDebug
     */
    public void setDebug(boolean debug) {
        this.proxyFactory.setDebug(debug);
    }

    /**
     * Set whether to use a chunked post for sending a Hessian request.
     * @see com.caucho.hessian.client.HessianProxyFactory#setChunkedPost
     */
    public void setChunkedPost(boolean chunkedPost) {
        this.proxyFactory.setChunkedPost(chunkedPost);
    }

    /**
     * Set the timeout to use when waiting for a reply from the Hessian service.
     * @see com.caucho.hessian.client.HessianProxyFactory#setReadTimeout
     */
    public void setReadTimeout(long timeout) {
        this.proxyFactory.setReadTimeout(timeout);
    }

    /**
     * Set whether version 2 of the Hessian protocol should be used for
     * parsing requests and replies. Default is "false".
     * @see com.caucho.hessian.client.HessianProxyFactory#setHessian2Request
     */
    public void setHessian2(boolean hessian2) {
        this.proxyFactory.setHessian2Request(hessian2);
        this.proxyFactory.setHessian2Reply(hessian2);
    }

    /**
     * Set whether version 2 of the Hessian protocol should be used for
     * parsing requests. Default is "false".
     * @see com.caucho.hessian.client.HessianProxyFactory#setHessian2Request
     */
    public void setHessian2Request(boolean hessian2) {
        this.proxyFactory.setHessian2Request(hessian2);
    }

    /**
     * Set whether version 2 of the Hessian protocol should be used for
     * parsing replies. Default is "false".
     * @see com.caucho.hessian.client.HessianProxyFactory#setHessian2Reply
     */
    public void setHessian2Reply(boolean hessian2) {
        this.proxyFactory.setHessian2Reply(hessian2);
    }


    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        prepare();
    }

    /**
     * Initialize the Hessian proxy for this interceptor.
     * @throws RemoteLookupFailureException if the service URL is invalid
     */
    public void prepare() throws RemoteLookupFailureException {
        try {
            this.hessianProxy = createHessianProxy(this.proxyFactory);
        }
        catch (MalformedURLException ex) {
            throw new RemoteLookupFailureException("Service URL [" + getServiceUrl() + "] is invalid", ex);
        }
    }

    /**
     * Create the Hessian proxy that is wrapped by this interceptor.
     * @param proxyFactory the proxy factory to use
     * @return the Hessian proxy
     * @throws MalformedURLException if thrown by the proxy factory
     * @see com.caucho.hessian.client.HessianProxyFactory#create
     */
    protected Object createHessianProxy(HessianProxyFactory proxyFactory) throws MalformedURLException {
        Assert.notNull(getServiceInterface(), "'serviceInterface' is required");
        return proxyFactory.create(getServiceInterface(), getServiceUrl());
    }


    public Object invoke(MethodInvocation invocation) throws Throwable {
        long startTm =  System.currentTimeMillis();
        Object obj = null;
        try {
            obj = handleInvoke(invocation);
            if (LOGGER.isInfoEnabled()) {
                printAccessLog(startTm, System.currentTimeMillis(), invocation.getMethod().getName(), null);
            }
            return obj;
        }  catch (Throwable ex) {
            if (LOGGER.isInfoEnabled()) {
                printAccessLog(startTm, System.currentTimeMillis(), invocation.getMethod().getName(), ex);
            }
            LOGGER.error("hession interface error "+getServiceInterface().getName(),ex);
            throw ex;
        }
    }
    
    //接口类、接口方法、执行结果、执行时间、接口URL、错误信息
    private void printAccessLog(long startTm, long endTm, String methodName, Throwable ex) {
        StringBuilder builder = new StringBuilder();
        builder.append(getServiceInterface().getName()).append(",")     //接口类
               .append(methodName).append(",")                          //接口方法
               .append(endTm - startTm).append(",")                     //执行时间
               .append((ex==null)? "000" : "999").append(",")               //执行结果
               .append(getServiceUrl()).append(",")                     //接口URL
               .append((ex==null)? "NULL" : ex.getMessage());               //接口异常信息
               
        LOGGER.info(builder.toString());
    }
    
    public Object handleInvoke(MethodInvocation invocation) throws Throwable {
        if (this.hessianProxy == null) {
            throw new IllegalStateException("HessianClientInterceptor is not properly initialized - " +
                    "invoke 'prepare' before attempting any operations");
        }

        ClassLoader originalClassLoader = overrideThreadContextClassLoader();
        try {
            return invocation.getMethod().invoke(this.hessianProxy, invocation.getArguments());
        }
        catch (InvocationTargetException ex) {
            if (ex.getTargetException() instanceof HessianRuntimeException) {
                HessianRuntimeException hre = (HessianRuntimeException) ex.getTargetException();
                Throwable rootCause = (hre.getRootCause() != null ? hre.getRootCause() : hre);
                throw convertHessianAccessException(rootCause);
            }
            else if (ex.getTargetException() instanceof UndeclaredThrowableException) {
                UndeclaredThrowableException utex = (UndeclaredThrowableException) ex.getTargetException();
                throw convertHessianAccessException(utex.getUndeclaredThrowable());
            }
            throw ex.getTargetException();
        }
        catch (Throwable ex) {
            throw new RemoteProxyFailureException(
                    "Failed to invoke Hessian proxy for remote service [" + getServiceUrl() + "]", ex);
        }
        finally {
            resetThreadContextClassLoader(originalClassLoader);
        }
    }

    /**
     * Convert the given Hessian access exception to an appropriate
     * Spring RemoteAccessException.
     * @param ex the exception to convert
     * @return the RemoteAccessException to throw
     */
    protected RemoteAccessException convertHessianAccessException(Throwable ex) {
        if (ex instanceof ConnectException) {
            return new RemoteConnectFailureException(
                    "Cannot connect to Hessian remote service at [" + getServiceUrl() + "]", ex);
        }
        else {
            return new RemoteAccessException(
                "Cannot access Hessian remote service at [" + getServiceUrl() + "]", ex);
        }
    }

}