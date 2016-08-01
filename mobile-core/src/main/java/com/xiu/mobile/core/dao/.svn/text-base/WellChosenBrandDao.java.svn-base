package com.xiu.mobile.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xiu.mobile.core.model.WellChosenBrandBo;
import com.xiu.mobile.core.model.WellChosenBrandVo;


/**
 * <p>
 * ************************************************************** 
 * @Description: 精选品牌数据层
 * @AUTHOR wangchengqun
 * @DATE 2014-6-9
 * ***************************************************************
 * </p>
 */
public interface WellChosenBrandDao {

	WellChosenBrandBo getWellChosenBrandByBrandId(Long brandId)throws Exception;

	int addWellChosenBrand(WellChosenBrandBo wellChosenBrand)throws Exception;

	int updateWellChosenBrandByBrandId(Map<String,Object> map)throws Exception;

	int deleteWellChosenBrandByBrandId(Long brandId)throws Exception;

	List<WellChosenBrandVo> getWellChosenBrandList(Map<Object, Object> map)throws Exception;

	int getWellChosenBrandListCount(Map<Object, Object> map)throws Exception;

	List<WellChosenBrandVo> getWellChosenBrandVoList(Long brandId)throws Exception;

	void updateReplace(@Param(value="brandCodeList")List<String> brandCodeList)throws Exception;
	int deleteWellChosenBrandByIds(@Param(value="ids")List<Long> ids)throws Exception;
}
