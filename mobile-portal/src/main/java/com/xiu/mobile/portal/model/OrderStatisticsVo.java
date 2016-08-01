package com.xiu.mobile.portal.model;

/**
 * 
 * @ClassName: OrderStatisticsVo
 * @Description: 各类订单数量统计
 * @author: Hualong
 * @date 2013-5-3 10:44:20
 * 
 */
public class OrderStatisticsVo {

    private String allCount = "0";// 全部数量
    private String delayPayCount = "0";// 待支付订单
    private String delayDeliveCount = "0";// 待发货订单
    private String delivedCount = "0";// 已发货订单

    public String getAllCount() {
        return this.allCount;
    }

    public void setAllCount(String allCount) {
        this.allCount = allCount;
    }

    public String getDelayPayCount() {
        return this.delayPayCount;
    }

    public void setDelayPayCount(String delayPayCount) {
        this.delayPayCount = delayPayCount;
    }

    public String getDelayDeliveCount() {
        return this.delayDeliveCount;
    }

    public void setDelayDeliveCount(String delayDeliveCount) {
        this.delayDeliveCount = delayDeliveCount;
    }

    public String getDelivedCount() {
        return this.delivedCount;
    }

    public void setDelivedCount(String delivedCount) {
        this.delivedCount = delivedCount;
    }

}
