/**  
 * @Project: xiu
 * @Title: ActivityGoodsResVo.java
 * @Package org.lazicats.xiu.model.topicActivity.vo
 * @Description: TODO
 * @author: chengyuanhuan
 * @date 2013-5-3 下午2:17:54
 * @Copyright: BroadenGate Software Services Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.xiu.mobile.simple.model;

import java.io.Serializable;
import java.util.List;

public class ActivityGoodsBo implements Serializable{

	/** 
	 * @Fields serialVersionUID : TODO
	 */ 
	
	private static final long serialVersionUID = 1L;
	
	private String retCode;//返回编码
	private int goodsNum; //商品数目
	private List<GoodsVo> goodsList;//商品集合
	
	private String errorMsg;//错误信息
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public int getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(int goodsNum) {
		this.goodsNum = goodsNum;
	}
	public List<GoodsVo> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<GoodsVo> goodsList) {
		this.goodsList = goodsList;
	}
	
	
	

}
