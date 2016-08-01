package com.xiu.mobile.portal.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.portal.model.PriceCompareVo;
import com.xiu.pcs.facade.dto.OverseasProductHessianSingleDTO;

public interface IPCSService {
	/**
	 * 通过走秀码查询商品海外供应商信息 提供前台价最高者供应商
	 * 
	 * @param goodsSn
	 *            走秀码
	 * @return Result defaultMode OverseasProductHessianSingleDTO
	 */
	OverseasProductHessianSingleDTO queryPortalOverseasProduct(String goodsSn);

	/**
	 * 通过走秀码批量查询商品海外供应商信息 提供前台价最高者供应商
	 * 
	 * @param goodsSns
	 *            走秀码
	 * @return Result defaultMode OverseasProductHessianMultipleDTO
	 */
	Map<String, OverseasProductHessianSingleDTO> queryPortalOverseasProducts(List<String> goodsSns);
	
	/***
	 * 通过走秀码获取比价信息
	 * @param goodsSn
	 * @return
	 */
	PriceCompareVo getPriceCompareBySn(String goodsSn);
}
