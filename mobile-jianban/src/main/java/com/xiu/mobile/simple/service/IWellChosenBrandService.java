package com.xiu.mobile.simple.service;

import java.util.List;

import com.xiu.mobile.core.model.DataBrandBo;
import com.xiu.mobile.simple.model.WellChosenBrandVo;

public interface IWellChosenBrandService {

	List<DataBrandBo> getWellChosenBrandList()throws Exception;

	WellChosenBrandVo getSortList(List<DataBrandBo> wellChosenBrandBoList)throws Exception;

	List<Object> getResultList(WellChosenBrandVo wcb) throws Exception;
 

}
