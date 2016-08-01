package com.xiu.mobile.portal.facade.model;

import com.xiu.mobile.portal.model.DeviceVo;

/**
 * 
 * @ClassName: DeliverTrackInParam
 * @Description: 快递跟踪输入参数
 * @author: Hualong
 * @date 2013-5-3 04:59:28
 * 
 */
public class ExpressTrackInParam extends DeviceVo {

    private static final long serialVersionUID = 1L;

    private String tokenId;// 登录凭证
    private Integer orderId;// 订单编号

    public String getTokenId() {
        return this.tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public Integer getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

}
