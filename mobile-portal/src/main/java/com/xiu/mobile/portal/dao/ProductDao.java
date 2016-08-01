package com.xiu.mobile.portal.dao;

import java.util.Map;

import com.xiu.mobile.portal.common.constants.DeliverInfo;

/**
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 商品中心商品信息
 * @AUTHOR : coco.long
 * @DATE :2015-07-30
 * </p>
 ****************************************************************
 */
public interface ProductDao {
	
	DeliverInfo getDeliverInfoByProduct(Map map) throws Exception;
	
	//查询商品上传身份证状态：0.必须 1.需要 2.不需要
	Integer getGoodsUploadIdCardByGoodsId(String goodsId) throws Exception;
	
	//查询商品上传身份证状态：0.必须 1.需要 2.不需要
	Integer getGoodsUploadIdCardByGoodsSn(String goodsSn) throws Exception;
	
	//根据goodsId查询goodsSn
	public String gindGoodsSn(String goodsId);
}
