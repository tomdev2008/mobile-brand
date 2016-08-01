package com.xiu.mobile.core.model;

import java.util.Date;
import java.util.List;
/** 
 * @ClassName:FindGoodsBo
 * @Description: TODO xiu_wap.find_goods
 * @author: wangchengqun
 * @date 2014-6-4
 *  
 */

public class FindGoods {
	/**
	 * 发现Id
	 */
	private Long id;
	/**
	 * 走秀码
	 */
	private String goodsSn;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 推荐语
	 */
	private String content;
	/**
	 * 开始时间
	 */
	private Date startDate;
	
	/**
	 * 开始时间 --- form表单使用
	 */
	private String sDate;
	/**
	 * 结束时间
	 */
	private Date endDate;
	
	/**
	 * 结束时间 -- form表单使用
	 */
	private String eDate;
	
	/**
	 * 排序
	 */
	private Long orderSequence;
	/**
	 *添加时间
	 */
	private Date createDate;
	/**
	 * 添加人
	 */
	private Long createBy;
	/**
	 * 是否删除
	 */
	private String deleted;
	/**
	 * 是否替换
	 */
	private String replaced;
	
	/**
	 * 商品库存
	 */
	private Long stock;
	
	/**
	 * 商品中心的商品名称
	 */
	private String goodsName;
	
	/**
	 * 商品图片
	 */
	private String goodsImage;
	
	// 分页用到的参数
	private int pageMax;
	private int pageMin;
	
	
	/**
	 * 点赞手动添加数量，默认值0
	 */
	private int hotIncNum;
	
	/**
	  * 
	  * 多少用户点赞，默认值0
	  */
	private int loveGoodsCnt;
	
	/**
	 * 状态显示  -1没有选择状态 |0未开始|1进行中|2已结束
	 */
	private int status = -1;
	
	private List<LabelCentre> labelCentre;//标签中间表
	//修改标签ID
	private String labelId;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGoodsSn() {
		return goodsSn;
	}
	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Long getOrderSequence() {
		return orderSequence;
	}
	public void setOrderSequence(Long orderSequence) {
		this.orderSequence = orderSequence;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getReplaced() {
		return replaced;
	}
	public void setReplaced(String replaced) {
		this.replaced = replaced;
	}
	public String getsDate() {
		return sDate;
	}
	public void setsDate(String sDate) {
		this.sDate = sDate;
	}
	public String geteDate() {
		return eDate;
	}
	public void seteDate(String eDate) {
		this.eDate = eDate;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsImage() {
		return goodsImage;
	}
	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}
	public int getPageMax() {
		return pageMax;
	}
	public void setPageMax(int pageMax) {
		this.pageMax = pageMax;
	}
	public int getPageMin() {
		return pageMin;
	}
	public void setPageMin(int pageMin) {
		this.pageMin = pageMin;
	}
	public int getLoveGoodsCnt() {
		return loveGoodsCnt;
	}
	public void setLoveGoodsCnt(int loveGoodsCnt) {
		this.loveGoodsCnt = loveGoodsCnt;
	}
	/**
	 * @return the stock
	 */
	public Long getStock() {
		return stock;
	}
	/**
	 * @param stock the stock to set
	 */
	public void setStock(Long stock) {
		this.stock = stock;
	}
	/**
	 * @return the hotIncNum
	 */
	public int getHotIncNum() {
		return hotIncNum;
	}
	/**
	 * @param hotIncNum the hotIncNum to set
	 */
	public void setHotIncNum(int hotIncNum) {
		this.hotIncNum = hotIncNum;
	}
	public List<LabelCentre> getLabelCentre() {
		return labelCentre;
	}
	public void setLabelCentre(List<LabelCentre> labelCentre) {
		this.labelCentre = labelCentre;
	}
	public String getLabelId() {
		return labelId;
	}
	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}
}
