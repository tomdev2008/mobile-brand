package com.xiu.mobile.portal.model;

import java.util.List;

public class CalculateOrderBo
{
    private String tokenId;
    //用户最后要付的钱
    private String amount;
    //优惠券兑换的钱
    private String promoAmount;
    //运费
    private String freight;
    
    private List<OrderGoodsVO> goodsList;
    //商品活动列表
    private List<String> activityList;
    
    //商品金额总计
    private String goodsAmount;
    
    //虚拟账户可用余额
    private String vtotalAmount;
    //虚拟账户支付金额
    private String vpayAmount;
    //用户最后还需支付
    private String  totalAmount;
    private String leftAmount;//还需支付用这个字段
    private boolean supportPackaging; //是否支持礼品包装
    private String packagingAmount; //礼品包装金额
    private String packagingPrice; //礼品包装价格
    //订单参与促销活动信息
    private List<MktActInfoVo> mktActInfoList;

	/**促销赠品详细信息*/
	private List<GoodsDetailVo> mktGiftList;
	
	
	public List<GoodsDetailVo> getMktGiftList() {
		return mktGiftList;
	}

	public void setMktGiftList(List<GoodsDetailVo> mktGiftList) {
		this.mktGiftList = mktGiftList;
	}
    
	public List<MktActInfoVo> getMktActInfoList() {
		return mktActInfoList;
	}

	public void setMktActInfoList(List<MktActInfoVo> mktActInfoList) {
		this.mktActInfoList = mktActInfoList;
	}

	public String getLeftAmount() {
		return leftAmount;
	}

	public void setLeftAmount(String leftAmount) {
		this.leftAmount = leftAmount;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getVtotalAmount() {
		return vtotalAmount;
	}

	public void setVtotalAmount(String vtotalAmount) {
		this.vtotalAmount = vtotalAmount;
	}

	public String getVpayAmount() {
		return vpayAmount;
	}

	public void setVpayAmount(String vpayAmount) {
		this.vpayAmount = vpayAmount;
	}

	public String getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(String goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public List<String> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<String> activityList) {
		this.activityList = activityList;
	}

	public String getTokenId()
    {
        return tokenId;
    }
    
    public void setTokenId(String tokenId)
    {
        this.tokenId = tokenId;
    }
    
    public String getAmount()
    {
        return amount;
    }
    
    public void setAmount(String amount)
    {
        this.amount = amount;
    }
    
    public String getPromoAmount()
    {
        return promoAmount;
    }
    
    public void setPromoAmount(String promoAmount)
    {
        this.promoAmount = promoAmount;
    }
    
    public String getFreight()
    {
        return freight;
    }
    
    public void setFreight(String freight)
    {
        this.freight = freight;
    }
    
    public List<OrderGoodsVO> getGoodsList()
    {
        return goodsList;
    }
    
    public void setGoodsList(List<OrderGoodsVO> goodsList)
    {
        this.goodsList = goodsList;
    }

	public boolean getSupportPackaging() {
		return supportPackaging;
	}

	public void setSupportPackaging(boolean supportPackaging) {
		this.supportPackaging = supportPackaging;
	}

	public String getPackagingAmount() {
		return packagingAmount;
	}

	public void setPackagingAmount(String packagingAmount) {
		this.packagingAmount = packagingAmount;
	}

	public String getPackagingPrice() {
		return packagingPrice;
	}

	public void setPackagingPrice(String packagingPrice) {
		this.packagingPrice = packagingPrice;
	}
    
   /* public List<String> getActivityList()
    {
        return activityList;
    }
    
    public void setActivityList(List<String> activityList)
    {
        this.activityList = activityList;
    }*/
    
}
