package com.xiu.mobile.portal.model;

import com.xiu.mobile.portal.model.DeviceVo;

/**
 * 
 * @ClassName: OrderDetailInParam
 * @Description:
 * @author: Hualong
 * @date 2013-5-3 10:46:02
 * 
 */
public class OrderDetailInParam extends DeviceVo {

    private static final long serialVersionUID = 1L;

    private String tokenId;// 认证凭据
    private int orderId;// 订单ID

    public String getTokenId() {
        return this.tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public int getOrderId() {
        return this.orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

}
