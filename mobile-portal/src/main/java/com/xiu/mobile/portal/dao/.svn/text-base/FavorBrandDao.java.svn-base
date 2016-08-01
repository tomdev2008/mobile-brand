package com.xiu.mobile.portal.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiu.mobile.portal.model.BrandTopicVo;
import com.xiu.mobile.portal.model.FavorBrandBo;
 
/**
 * <p>
 * ************************************************************** 
 * @Description: 收藏品牌数据层
 * @AUTHOR wangchengqun
 * @DATE 2014-5-30
 * ***************************************************************
 * </p>
 */
public interface FavorBrandDao 
{

	int insertFavorBrand(FavorBrandBo favorBrand)throws Exception;

	int deleteBrand(HashMap<String, Object> valMap)throws Exception;

	List<FavorBrandBo> getBrandByPage(HashMap<String, Object> valMap)throws Exception;

	int getBrandCount(Long userId)throws Exception;

	List<FavorBrandBo> getBrandByUserIdAndBrandId(
			HashMap<String, Object> valMap)throws Exception;
	List<BrandTopicVo> queryBrandTopicList(HashMap<String, Object> valMap)throws Exception;

	int queryBrandTopicListCount(Long brandId)throws Exception;
	
	List<BrandTopicVo> queryActivityCount(Long brandId)throws Exception;
	
	int deleteBatchBrand(HashMap<String, Object> valMap)throws Exception;
	//在关注表添加标签秀
	int addNewShowByLabel(FavorBrandBo favorBrand);
	//删除关注
	int deleteLabelConcern(Map<String,Object> params);
	//批量删除关注表中的秀
	int deleteBatchLabelConcern(Map<String,Object> params);
}