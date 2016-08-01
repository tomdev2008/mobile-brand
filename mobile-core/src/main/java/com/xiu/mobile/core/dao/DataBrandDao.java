package com.xiu.mobile.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xiu.mobile.core.model.DataBrandBo;


 
/**
 * <p>
 * ************************************************************** 
 * @Description: 品牌数据层
 * @AUTHOR wangchengqun
 * @DATE 2014-5-30
 * ***************************************************************
 * </p>
 */
public interface DataBrandDao 
{

	 List<DataBrandBo> getDataBrandByBrandId(Map<String, Object> params)throws Exception;

	List<DataBrandBo> getWellChosenBrandList()throws Exception;

	List<DataBrandBo> getBrandList(String brandName)throws Exception;

	List<String> checkBrandCode(@Param(value="brandCode")List<String> brandCode);

	DataBrandBo getBrandByCode(String code);
	
	List<DataBrandBo> findAllBrands();

}
