package com.xiu.mobile.portal.facade.model;

import com.xiu.mobile.portal.model.DeviceVo;

/**
 * 
 * @ClassName: OrderListInParam
 * @Description: 订单列表查询入参
 * @author: Hualong
 * @date 2013-5-3 10:45:27
 * 
 */
public class OrderListInParam extends DeviceVo {

    private static final long serialVersionUID = 1L;

    private String tokenId;// 认证凭据
    private String queryType;// 查询类型
    private int page;// 查询页码
    private int pageSize;// 每次查询的记录数

    public String getTokenId() {
        return this.tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getQueryType() {
        return this.queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
