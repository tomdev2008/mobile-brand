package com.xiu.mobile.portal.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.xiu.mobile.portal.common.constants.DeliverInfo;
import com.xiu.sales.common.balance.dataobject.ProdSettlementDO;
import com.xiu.sales.common.itemsettle.dataobject.ItemSettleActivityResultDO;


/**
 * 商品详情对象
 * @author caichangliang
 *
 */
public class GoodsDetailBo {
	/**
	 * 上架状态：0:下架,1:上架
	 */
	private String onSale;
	/**
	 * 0:普通商品,1：活动商品
	 */
	private int isActivityGoods;
	/**
	 * 活动价
	 */
	private String activityPrice;
	/**
	 * 2:名品特卖,4:奢华惠
	 */
	private int activityType;
	/**
	 * 活动库存
	 */
	private int activityStock;
	/**
	 * 可能参与的优惠活动列表,如:满一件打9折,满1000减150
	 */
	private List<String> activityList;
	/**促销活动信息*/
	private List<MktActInfoVo> activityItemList;
	/**
	 * 商品价格(市场价)
	 */
	private BigDecimal price;
	/**
	 * 走秀价
	 */
	private BigDecimal zsPrice;
	/**
	 * 商品基本信息
	 */
	private GoodsInfo goodsInfo;
	/**
	 * SKU列表
	 */
	private List<GoodsSkuItem> skuList;
	/**
	 * 弹性信息
	 */
	private List<FlexibleItem> flexibleAttr;

	/**
	 * 是否支持货到付款：0:不支持,1:支持
	 */
	private String codFlag;

	private String tranport;
	
	// 发货信息
	private DeliverInfo deliverInfo; 
	 
	//价格组成
	private  PriceComponentVo priceComponentVo;
	//行邮信息
	private ProdSettlementDO proSettlement;
	//库存
	private Integer stock;
	//是否支持退换货
	private String supportRejected;
	//供应商名称
	private String supplierName;
	
	//汽车显示类型 0 非汽车类，1汽车类无活动  2，汽车类有活动
	private int busDisplayType;
	
	private String brandName;//品牌名称
	
	//支持礼包包装
	private boolean supportPackaging; 
	
	private Integer limitSaleNum;//限购数量

	public int getBusDisplayType() {
		return busDisplayType;
	}

	public void setBusDisplayType(int busDisplayType) {
		this.busDisplayType = busDisplayType;
	}

	public String getSupportRejected() {
		return supportRejected;
	}

	public void setSupportRejected(String supportRejected) {
		this.supportRejected = supportRejected;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public ProdSettlementDO getProSettlement() {
		return proSettlement;
	}

	public void setProSettlement(ProdSettlementDO proSettlement) {
		this.proSettlement = proSettlement;
	}

	public PriceComponentVo getPriceComponentVo() {
		return priceComponentVo;
	}

	public void setPriceComponentVo(PriceComponentVo priceComponentVo) {
		this.priceComponentVo = priceComponentVo;
	}

	public List<MktActInfoVo> getActivityItemList() {
		return activityItemList;
	}

	public void setActivityItemList(
			List<MktActInfoVo> activityItemList) {
		this.activityItemList = activityItemList;
	}

	public String getCodFlag() {
		return codFlag;
	}

	public void setCodFlag(String codFlag) {
		this.codFlag = codFlag;
	}

	// TODO 剩余时间
	public String getOnSale() {
		return onSale;
	}

	public void setOnSale(String onSale) {
		this.onSale = onSale;
	}

	public int getIsActivityGoods() {
		return isActivityGoods;
	}

	public void setIsActivityGoods(int isActivityGoods) {
		this.isActivityGoods = isActivityGoods;
	}

	public String getActivityPrice() {
		return activityPrice;
	}

	public void setActivityPrice(String activityPrice) {
		this.activityPrice = activityPrice;
	}

	public int getActivityType() {
		return activityType;
	}

	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}

	public int getActivityStock() {
		return activityStock;
	}

	public void setActivityStock(int activityStock) {
		this.activityStock = activityStock;
	}

	public List<String> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<String> activityList) {
		this.activityList = activityList;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getZsPrice() {
		return zsPrice;
	}

	public void setZsPrice(BigDecimal zsPrice) {
		this.zsPrice = zsPrice;
	}

	public GoodsInfo getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(GoodsInfo goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public List<GoodsSkuItem> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<GoodsSkuItem> skuList) {
		this.skuList = skuList;
	}

	public List<FlexibleItem> getFlexibleAttr() {
		return flexibleAttr;
	}

	public void setFlexibleAttr(List<FlexibleItem> flexibleAttr) {
		this.flexibleAttr = flexibleAttr;
	}

	public String getTranport() {
		return tranport;
	}

	public void setTranport(String tranport) {
		this.tranport = tranport;
	}

	public DeliverInfo getDeliverInfo() {
		return deliverInfo;
	}

	public void setDeliverInfo(DeliverInfo deliverInfo) {
		this.deliverInfo = deliverInfo;
	}

	public boolean getSupportPackaging() {
		return supportPackaging;
	}

	public void setSupportPackaging(boolean supportPackaging) {
		this.supportPackaging = supportPackaging;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getLimitSaleNum() {
		return limitSaleNum;
	}

	public void setLimitSaleNum(Integer limitSaleNum) {
		this.limitSaleNum = limitSaleNum;
	}
	
	
	

}
