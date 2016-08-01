package com.xiu.mobile.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.commerce.hessian.model.Product;
import com.xiu.commerce.hessian.model.Sku;
import com.xiu.mobile.core.model.FindGoodsVo;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.utils.ObjectUtil;
import com.xiu.mobile.portal.common.constants.GoodsConstant;
import com.xiu.mobile.portal.common.util.ImageServiceConvertor;
import com.xiu.mobile.portal.common.util.Tools;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.dao.FindGoodsApiDao;
import com.xiu.mobile.portal.ei.EIChannelInventoryManager;
import com.xiu.mobile.portal.service.IFindGoodsApiService;
import com.xiu.mobile.portal.service.IGoodsService;
import com.xiu.mobile.portal.service.ITopicActivityGoodsService;

@Service("findGoodsApiService")
public class FindGoodsApiServiceImpl implements IFindGoodsApiService{

	
	@Autowired
	private FindGoodsApiDao findGoodsApiDao;
	
	@Autowired
	private ITopicActivityGoodsService topicActivityGoodsService;
	
	@Autowired
	private IGoodsService goodsManager;
	
	@Autowired
	private EIChannelInventoryManager eiChannelInventoryManager;
	
	
	public Map<String, Object> getRecommendGoodsList(Map<String, Object> params) {
		Map<String, Object> resultMap=new HashMap<String, Object>();
		Boolean isSuccess=false;
		//1.获取单品数据
		String pageNumStr=(String)params.get("pageNum");
		int pageNum=1;
		if(pageNumStr!=null&&!pageNumStr.equals("")){
			pageNum=ObjectUtil.getInteger(pageNumStr, 1);
		}
		Map<String, Object> goodsRecommendParams=new HashMap<String, Object>();
		Page page=new Page();
		page.setPageSize(10);//精选秀分页大小
		page.setPageNo(pageNum);

		goodsRecommendParams.put("tagId", params.get("tagId"));
		
		int total =findGoodsApiDao.getFindGoodsListCount(goodsRecommendParams);//获取总数
		page.setTotalCount(total);
		goodsRecommendParams.put("lineMin", page.getFirstRecord());
		goodsRecommendParams.put("lineMax", page.getEndRecord());
		List<FindGoodsVo> findGoods=findGoodsApiDao.getFindGoodsList(goodsRecommendParams);
		int goodsSize=findGoods.size();
		StringBuffer goodsSnsSb=new StringBuffer();
		for (int i = 0; i < goodsSize; i++) {
			goodsSnsSb.append(findGoods.get(i).getGoodsSn());
			goodsSnsSb.append(",");
		}
		// 2 封装产品相关信息
		List<Product> products = topicActivityGoodsService.batchLoadProducts(goodsSnsSb.toString());
		for(FindGoodsVo iitem: findGoods ){
			if(null!=products&&products.size()>0){
		     	for(Product product:products ){
					if(iitem.getGoodsSn().equals(product.getXiuSN())){
						// 商品图片
						String imgUrl = product.getImgUrl();
						// 为了适应镜像环境的端口号
						if (Tools.isEnableImageReplace()) { 
							if (null != imgUrl) {
								imgUrl = imgUrl.replace("pic.xiu.com", "pic.xiu.com:6080");
								imgUrl = imgUrl.replace("images.xiu.com", "images.xiu.com:6080");
							}
						} 
						imgUrl = ImageServiceConvertor.getGoodsDetail(imgUrl);
						iitem.setGoodsImg(imgUrl+"/g1_"+GoodsConstant.SLIDE_IMG_MAX);						
						// 获取商品价格
						Double price=goodsManager.getZxPrice(product);
						iitem.setGoodsPrice(price==null?0.00:price);
						//商品上下架
						int stateOnsale=Integer.parseInt(product.getOnSale());
						iitem.setGoodsStateOnsale(stateOnsale);
						//商品库存
						Integer stock=getProductStock(product.getSkus());
						iitem.setGoodsStock(stock);
					}
				}
			}else{
				iitem.setGoodsImg("");
				iitem.setGoodsId(Long.parseLong("0"));
				iitem.setGoodsPrice(0.00);
			}
		}
		
		isSuccess=true;
		resultMap.put("status", isSuccess);
		resultMap.put("goodsRecommendList", findGoods);
		resultMap.put("totalCount", total);
		resultMap.put("totalPage",page.getTotalPages());
		return resultMap;
	}

	// 处理sku信息
	private Integer getProductStock(Sku[] skus) {
		Integer stock=0;
		if (skus != null && skus.length > 0) {
			List<String> skuCodeList = new ArrayList<String>();
			for (Sku sku : skus) {
				skuCodeList.add(sku.getSkuSN());
			}
			// 批量获取SKU的库存信息
			Map<String, Integer> skuCodeMap = queryInventoryBySku(skuCodeList);
			for (Sku sku : skus) {
				if (skuCodeMap.containsKey(sku.getSkuSN()) && skuCodeMap.get(sku.getSkuSN()) != null) {
					// 添加sku的库存信息
					stock=stock+skuCodeMap.get(sku.getSkuSN());
				}
			}
		}

		return stock;
	}

	/**
	 * 查询库存信息
	 * @param sku
	 * @return
	 */
	public Map<String, Integer> queryInventoryBySku(List<String> skuCodeList) {
		try {
			return eiChannelInventoryManager.inventoryQueryBySkuCodeList(GlobalConstants.CHANNEL_ID, skuCodeList);
		} catch (Exception e) {
			return new HashMap<String, Integer>();
		}
	}
}
