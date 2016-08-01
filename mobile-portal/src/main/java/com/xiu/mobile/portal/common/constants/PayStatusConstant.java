package com.xiu.mobile.portal.common.constants;

/**
 * 
 * @ClassName: PayStatusConstant
 * @Description: 支付状态
 * @author: Hualong
 * @date 2013-5-4 下午04:39:14
 * 
 */
public class PayStatusConstant {

    /**
     * 未到账
     */
    public static int NOT_ARRIVE_ACCOUNT = 0;
    public static String NOT_ARRIVE_ACCOUNT_MSG = "未到账";
    /**
     * 已到账
     */
    public static int ARRIVED_ACCOUNT = 1;
    public static String ARRIVED_ACCOUNT_MSG = "已到账";
    /**
     * 已退款
     */
    public static int REFUNDED = -1;
    public static String REFUNDED_MSG = "已退款";
}
