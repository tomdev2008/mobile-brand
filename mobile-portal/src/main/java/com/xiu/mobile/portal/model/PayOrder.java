package com.xiu.mobile.portal.model;

import java.io.Serializable;
import java.util.List;

public class PayOrder implements Serializable{
    private static final long serialVersionUID = -5139230361270204781L;
    private Long tradeId;
    private String orderId;
    private String orderCode;//订单编码
    private String tradeUserId;//下单人ID
    private String tradeUserName;
    private String merchantPrivateDomain;
    private String merchantId;//10001
    private String tradeType;// :0
    private List<PayOrderItem> tradeList;
    
    public Long getTradeId() {
        return tradeId;
    }
    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getOrderCode() {
        return orderCode;
    }
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    public String getTradeUserId() {
        return tradeUserId;
    }
    public void setTradeUserId(String tradeUserId) {
        this.tradeUserId = tradeUserId;
    }
    public String getTradeUserName() {
        return tradeUserName;
    }
    public void setTradeUserName(String tradeUserName) {
        this.tradeUserName = tradeUserName;
    }
    public String getMerchantPrivateDomain() {
        return merchantPrivateDomain;
    }
    public void setMerchantPrivateDomain(String merchantPrivateDomain) {
        this.merchantPrivateDomain = merchantPrivateDomain;
    }
    public String getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
    
    public String getTradeType() {
        return tradeType;
    }
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
    public List<PayOrderItem> getTradeList() {
        return tradeList;
    }
    public void setTradeList(List<PayOrderItem> tradeList) {
        this.tradeList = tradeList;
    }
}
