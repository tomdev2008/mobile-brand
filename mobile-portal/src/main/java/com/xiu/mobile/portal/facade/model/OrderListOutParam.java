package com.xiu.mobile.portal.facade.model;

import java.util.List;

import com.xiu.mobile.portal.common.model.BaseBackDataVo;

/**
 * 
 * @ClassName: OrderListOutParam
 * @Description: 订单列表查询出参
 * @author: Hualong
 * @date 2013-5-4 上午11:50:07
 * 
 */
public class OrderListOutParam extends BaseBackDataVo {

    private static final long serialVersionUID = 1L;

    private String tokenId;// 认证凭据
    private String totalPage;// 总页码数
    private String currentPage;// 当前页
    private String totalItem;// 总记录数

    List<OrderSummaryOutParam> orderList;// 订单列表

    public String getTokenId() {
        return this.tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public String getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getTotalItem() {
        return this.totalItem;
    }

    public void setTotalItem(String totalItem) {
        this.totalItem = totalItem;
    }

    public List<OrderSummaryOutParam> getOrderList() {
        return this.orderList;
    }

    public void setOrderList(List<OrderSummaryOutParam> orderList) {
        this.orderList = orderList;
    }

}
