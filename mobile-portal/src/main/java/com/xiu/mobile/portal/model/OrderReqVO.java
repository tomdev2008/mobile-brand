package com.xiu.mobile.portal.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 订单请求信息
 * 
 * @author 江天明
 * 
 */
public class OrderReqVO extends DeviceVo implements Serializable
{
    
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    
    /*
     * 认证凭据 varchar(100)
     */
    private String tokenId;
    
    /*
     * 支付方式
     */
    private String paymentMethod;
    
    /*
     * 送货时间
     */
    private String deliverTime;
    
    /*
     * 收货信息ID
     */
    private String addressId;
    
    /*
     * 用户留言
     */
    private String message;
    
    private List<OrderGoodsVO> goodsList;
    
    /*
     * 优惠券号码
     */
    private String couponNumber;
    /**是否使用虚拟账户中可用余额 */
    private String isVirtualPay;
    /**虚拟支付的可用总金额*/
    private String vtotalAmount;
    /**用户还需支付 */
    private String totalAmount;
    private String leftAmount;//还需支付用这个字段
    /**用户id */
    private String userId;
    /**设备信息 */
    private Map<String, String> deviceParams;
    
    private String useProductPackaging; //是否使用礼品包装 1.使用 0.未使用
    private String goodsSn; //商品走秀码
    
    private String isMutilPay;//是否多次支付
    private String reqAmount; //多笔支付，本次支付用户输入金额
    
    public Map<String, String> getDeviceParams() {
		return deviceParams;
	}

	public void setDeviceParams(Map<String, String> deviceParams) {
		this.deviceParams = deviceParams;
	}

	public String getLeftAmount() {
		return leftAmount;
	}

	public void setLeftAmount(String leftAmount) {
		this.leftAmount = leftAmount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
     * 发票选择
     */
    private String invoice;
    
    /**
     * 发票抬头
     */
    private String invoiceHeading;
    
    /**
     * 发票类型
     */
    private String invoiceType;
    
    /**
	 * 订单来源
	 * PC：11； 移支WAP：20；Android应用端：21; IPHONE应端: 22；IPAD应用端：23；
	 */
    private String orderSource;
    
    /**
     * 暂时是订单只有一个商品，订单商品来源：推荐，发现，精选品牌，。。。
     */
    private String goodsFrom;

	public String getGoodsFrom() {
		return goodsFrom;
	}

	public void setGoodsFrom(String goodsFrom) {
		this.goodsFrom = goodsFrom;
	}

	private String orderMsg;

	public String getOrderMsg() {
		return orderMsg;
	}

	public void setOrderMsg(String orderMsg) {
		this.orderMsg = orderMsg;
	}

	/**
     * 媒体media属性
     */
    private String mediaId;
	private String adPosId;
	private String mediaName;
	private int cpsType;
    
    /**
     * cps属性
     */
    private String cpsId;
	private String a_id;
	private String u_id;
	private String w_id;
	private String bid;
	private String uid;
	private String abid;
	private String cid;
    

	public String getIsVirtualPay() {
		return isVirtualPay;
	}

	public void setIsVirtualPay(String isVirtualPay) {
		this.isVirtualPay = isVirtualPay;
	}

	public String getTokenId()
    {
        return tokenId;
    }
    
    public void setTokenId(String tokenId)
    {
        this.tokenId = tokenId;
    }
    
    public String getPaymentMethod()
    {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }
    
    public String getDeliverTime()
    {
        return deliverTime;
    }
    
    public void setDeliverTime(String deliverTime)
    {
        this.deliverTime = deliverTime;
    }
    
    public String getAddressId()
    {
        return addressId;
    }
    
    public void setAddressId(String addressId)
    {
        this.addressId = addressId;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public List<OrderGoodsVO> getGoodsList()
    {
        return goodsList;
    }
    
    public void setGoodsList(List<OrderGoodsVO> goodsList)
    {
        this.goodsList = goodsList;
    }
    
    public String getCouponNumber()
    {
        return couponNumber;
    }
    
    public void setCouponNumber(String couponNumber)
    {
        this.couponNumber = couponNumber;
    }
    
    public String getInvoice()
    {
        return invoice;
    }
    
    public void setInvoice(String invoice)
    {
        this.invoice = invoice;
    }
    
    public String getInvoiceHeading()
    {
        return invoiceHeading;
    }
    
    public void setInvoiceHeading(String invoiceHeading)
    {
        this.invoiceHeading = invoiceHeading;
    }
    
    public String getInvoiceType()
    {
        return invoiceType;
    }
    
    public void setInvoiceType(String invoiceType)
    {
        this.invoiceType = invoiceType;
    }
    
    public String getInvoiceContent()
    {
        return invoiceContent;
    }
    
    public void setInvoiceContent(String invoiceContent)
    {
        this.invoiceContent = invoiceContent;
    }
    
    /**
     * 发票内容
     */
    private String invoiceContent;



	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getAdPosId() {
		return adPosId;
	}

	public void setAdPosId(String adPosId) {
		this.adPosId = adPosId;
	}

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public int getCpsType() {
		return cpsType;
	}

	public void setCpsType(int cpsType) {
		this.cpsType = cpsType;
	}

	public String getCpsId() {
		return cpsId;
	}

	public void setCpsId(String cpsId) {
		this.cpsId = cpsId;
	}

	public String getA_id() {
		return a_id;
	}

	public void setA_id(String a_id) {
		this.a_id = a_id;
	}

	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public String getW_id() {
		return w_id;
	}

	public void setW_id(String w_id) {
		this.w_id = w_id;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAbid() {
		return abid;
	}

	public void setAbid(String abid) {
		this.abid = abid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	

	public String getVtotalAmount() {
		return vtotalAmount;
	}

	public void setVtotalAmount(String vtotalAmount) {
		this.vtotalAmount = vtotalAmount;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public String getUseProductPackaging() {
		return useProductPackaging;
	}

	public void setUseProductPackaging(String useProductPackaging) {
		this.useProductPackaging = useProductPackaging;
	}
	
	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}


	public String getIsMutilPay() {
		return isMutilPay;
	}

	public void setIsMutilPay(String isMutilPay) {
		this.isMutilPay = isMutilPay;
	}

	public String getReqAmount() {
		return reqAmount;
	}

	public void setReqAmount(String reqAmount) {
		this.reqAmount = reqAmount;
	}

	@Override
	public String toString() {
		return "OrderReqVO [tokenId=" + tokenId + ", paymentMethod="
				+ paymentMethod + ", deliverTime=" + deliverTime
				+ ", addressId=" + addressId + ", message=" + message
				+ ", goodsList=" + goodsList + ", couponNumber=" + couponNumber
				+ ", isVirtualPay=" + isVirtualPay + ", vtotalAmount="
				+ vtotalAmount + ", totalAmount=" + totalAmount
				+ ", leftAmount=" + leftAmount + ", userId=" + userId
				+ ", deviceParams=" + deviceParams + ", useProductPackaging="
				+ useProductPackaging + ", goodsSn=" + goodsSn
				+ ", isMutilPay=" + isMutilPay + ", reqAmount=" + reqAmount
				+ ", invoice=" + invoice + ", invoiceHeading=" + invoiceHeading
				+ ", invoiceType=" + invoiceType + ", orderSource="
				+ orderSource + ", goodsFrom=" + goodsFrom + ", orderMsg="
				+ orderMsg + ", mediaId=" + mediaId + ", adPosId=" + adPosId
				+ ", mediaName=" + mediaName + ", cpsType=" + cpsType
				+ ", cpsId=" + cpsId + ", a_id=" + a_id + ", u_id=" + u_id
				+ ", w_id=" + w_id + ", bid=" + bid + ", uid=" + uid
				+ ", abid=" + abid + ", cid=" + cid + ", invoiceContent="
				+ invoiceContent + "]";
	}
	
    /*
     * giftList 赠品列表 array giftGoodsSn 赠品商品编号 string giftGoodsSku 赠品商品SKU string giftActivityId 赠品所在的活动ID srting
     * giftPrice 赠品价格 number
     */
    
    

}
