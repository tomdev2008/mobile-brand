package com.xiu.common.web.utils;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION :
 * @AUTHOR : xiu@xiu.com
 * @DATE :2012-4-3 下午1:52:11
 *       </p>
 **************************************************************** 
 */
public final class IPUtil {
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(IPUtil.class);
    
    public static String getRequestIP(HttpServletRequest request) {
        int count = 0;
        String[] ipArray = new String[20];

        // 查直接连接的IP（可能是反向代理服务器的IP地址＄1�7
        if (request.getRemoteAddr() != null) {
            ipArray[count++] = request.getRemoteAddr();
        }

        // 查负载均衡服务器转发的IP
        String ip = request.getHeader("X-Forwarded-For");
        // String ip = request.getHeader("X-Real-IP");

        // //查正向代理（squid）服务器转发的IP
        // if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        // ip = request.getHeader("X-Cluster-Client-Ip");
        // }

        if (ip != null) {
            if (ip.indexOf(',') < 0) {
                // 直接IP地址
                ipArray[count++] = ip;
            } else {
                // 如果使用代理(或是多级)从代理中取出真实IP地址
                String[] ips = ip.split(",");
                for (String ipEntry : ips) {
                    if (ipEntry == null) {
                        continue;
                    }

                    ipEntry = ipEntry.trim();
                    if (ipEntry.isEmpty()) {
                        continue;
                    }

                    ipArray[count++] = ipEntry;

                    if (count > (ipArray.length - 1)) {
                        break;
                    }
                }
            }
        }

        // 得到最终的IP地址
        String finalIp = null;
        while (count >= 0) {
            ip = ipArray[count--];
            if (ip == null) {
                continue;
            }

            ip = ip.trim();
            if (ip.isEmpty()) {
                continue;
            }

            if (finalIp == null) {
                finalIp = ip;
            }

            if (isPrivateAddress(ip)) {
                continue;
            } else {
                finalIp = ip;
            }
        }

        if (finalIp != null) {
            return finalIp;
        } else {
            return "0.0.0.0";
        }
    }

    /**
     * 判断是否是内部IP地址
     * 
     * @param ip
     * @return
     */
    static public boolean isPrivateAddress(String ip) {
        if (ip.indexOf("127.0.0.1") >= 0) {
            return true;
        }
        if (ip.startsWith("10.")) {
            return true;
        }
        if (ip.startsWith("192.168.")) {
            return true;
        }
        if (ip.startsWith("172.")) {
            return isFrom16To31(ip);
        }

        return false;
    }

    /**
     * 172.16.x.x至172.31.x.x 是内网ip
     * 
     * @param ipString
     * @return
     */
    private static boolean isFrom16To31(String ipString) {

        boolean result = false;

        try {
            String[] ipArr = ipString.split("\\.");

            int sendPart = Integer.parseInt(ipArr[1]);

            if (sendPart >= 16 && sendPart <= 31) {
                result = true;
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to parse IP String: {}", ipString, e);
        }

        return result;
    }

}
