package com.xiu.mobile.core.common.logger;

import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION :
 * @AUTHOR : mike@xiu.com
 * @DATE :2013-7-16 下午3:36:25
 *       </p>
 **************************************************************** 
 */

public class PerfInterceptor implements MethodInterceptor {

    private static final XLogger LOGGER = XLoggerFactory.getXLogger(PerfInterceptor.class);
    private static ConcurrentHashMap<String, MethodStats> methodStats = new ConcurrentHashMap<String, MethodStats>();
    private static long statLogFrequency = 10;

    private static long methodWarningThreshold = 1000;

    public Object invoke(MethodInvocation method) throws Throwable {
        logBegin(method);
        long start = System.currentTimeMillis();
        try {
            return method.proceed();
        } finally {
            //updateStats(method.getMethod().getName(), (System.currentTimeMillis() - start));
            logEnd(method,(System.currentTimeMillis() - start));
        }

    }

    private void logBegin(MethodInvocation method){
        LOGGER.debug(method.getMethod().getDeclaringClass().getCanonicalName()+"."+method.getMethod().getName()+";BEGIN;");
    }
    private void logEnd(MethodInvocation method,long elapsedTime){
        
        LOGGER.debug(method.getMethod().getDeclaringClass().getCanonicalName()+"."+method.getMethod().getName()+";timeOut:"+elapsedTime+";END;");
    }
    
    public void updateStats(String methodName, long elapsedTime) {
        MethodStats stats = methodStats.get(methodName);
        if (stats == null) {
            stats = new MethodStats(methodName);
            synchronized (methodStats) {
            	methodStats.put(methodName, stats);
			}
        }
        stats.count++;

        stats.totalTime += elapsedTime;

        if (elapsedTime > stats.maxTime) {

            stats.maxTime = elapsedTime;

        }

        if (elapsedTime > methodWarningThreshold) {
            LOGGER.warn("method warning: " + methodName + "(), cnt = " + stats.count + ", lastTime = " + elapsedTime
                    + ", maxTime = " + stats.maxTime);
        }

        if (stats.count % statLogFrequency == 0) {
            long avgTime = stats.totalTime / stats.count;
            long runningAvg = (stats.totalTime - stats.lastTotalTime) / statLogFrequency;
            LOGGER.debug("method: " + methodName + "(), cnt = " + stats.count + ", lastTime = " + elapsedTime
                    + ", avgTime = " + avgTime + ", runningAvg = " + runningAvg + ", maxTime = " + stats.maxTime);
            // reset the last total time
            stats.lastTotalTime = stats.totalTime;

        }

    }

    class MethodStats {
        public String methodName;
        public long count;
        public long totalTime;
        public long lastTotalTime;
        public long maxTime;
        public MethodStats(String methodName) {
            this.methodName = methodName;
        }
    }

}
