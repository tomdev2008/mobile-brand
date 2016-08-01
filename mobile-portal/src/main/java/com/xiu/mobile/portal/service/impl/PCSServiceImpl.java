package com.xiu.mobile.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.core.utils.XiuUtil;
import com.xiu.mobile.portal.common.util.Arithmetic4Double;
import com.xiu.mobile.portal.ei.EIPCSManager;
import com.xiu.mobile.portal.ei.EIPromotionManager;
import com.xiu.mobile.portal.model.PriceCompareVo;
import com.xiu.mobile.portal.service.IPCSService;
import com.xiu.pcs.facade.dto.OverseasProductHessianSingleDTO;
import com.xiu.settlement.common.model.ProdSimpleSettleDO;

@Service("pcsService")
public class PCSServiceImpl implements IPCSService{

	@Autowired
	private EIPCSManager pcsManager;
	@Autowired
	private EIPromotionManager promotionManager;
	
	@Override
	public OverseasProductHessianSingleDTO queryPortalOverseasProduct(String goodsSn) {
		return pcsManager.queryPortalOverseasProduct(goodsSn);
	}

	@Override
	public Map<String, OverseasProductHessianSingleDTO> queryPortalOverseasProducts(List<String> goodsSns) {
		return pcsManager.queryPortalOverseasProducts(goodsSns);
	}

	@Override
	public PriceCompareVo getPriceCompareBySn(String goodsSn) {
		// 查询比价系统的数据信息
		OverseasProductHessianSingleDTO dto = pcsManager.queryPortalOverseasProduct(goodsSn);
		if (dto != null) {
			PriceCompareVo priceCompareVo = new PriceCompareVo();
			priceCompareVo.setGoodsChannel(dto.getGoodsChannel());
			priceCompareVo.setGoodsPrice(dto.getCurrencySymbol().concat(XiuUtil.getPriceDouble2Str(Arithmetic4Double.div(dto.getGoodsPrice().doubleValue(), 100))));
			priceCompareVo.setGoodsRMBPrice(Arithmetic4Double.div(dto.getGoodsRMBPrice().doubleValue(), 100));
			priceCompareVo.setGoodsSn(dto.getXiuSn());
			priceCompareVo.setGoodsUrl(dto.getGoodsUrl());
			priceCompareVo.setRate(dto.getRate());
			priceCompareVo.setShowStatus(true);
			// 获取商品报关、物流信息
			ProdSimpleSettleDO prodSimpleSettleDO = promotionManager.prodSimpleSettle(goodsSn, dto.getGoodsRMBPrice());
			if (prodSimpleSettleDO != null) {
				priceCompareVo.setCustomsTax(Arithmetic4Double.div(prodSimpleSettleDO.getPrice().getRealCustomsTax(), 100));
				priceCompareVo.setTransportCost(Arithmetic4Double.div(prodSimpleSettleDO.getPrice().getTransportCost(), 100));
			}
			return priceCompareVo;
		}
		return null;
	}

}
