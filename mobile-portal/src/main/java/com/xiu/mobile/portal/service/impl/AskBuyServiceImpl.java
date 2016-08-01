package com.xiu.mobile.portal.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.commerce.hessian.model.Product;
import com.xiu.csc.facade.dto.OrderInfoDTO;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.util.ImageServiceConvertor;
import com.xiu.mobile.portal.common.util.ImageUtil;
import com.xiu.mobile.portal.dao.AskBuyDao;
import com.xiu.mobile.portal.ei.EICSCManager;
import com.xiu.mobile.portal.model.AskBuyOrderVO;
import com.xiu.mobile.portal.model.GoodsVo;
import com.xiu.mobile.portal.model.ProductTypeBrandVO;
import com.xiu.mobile.portal.service.IAskBuyService;
import com.xiu.mobile.portal.service.ITopicActivityGoodsService;
import com.xiu.sales.common.itemsettle.dataobject.ItemSettleResultDO;

@Transactional
@Service("askBuyService")
public class AskBuyServiceImpl implements IAskBuyService {

	private Logger logger = Logger.getLogger(AskBuyServiceImpl.class);

	@Autowired
	private EICSCManager eiCSCManager;
	@Autowired
	private AskBuyDao askBuyDao;
	@Autowired
	private ITopicActivityGoodsService topicActivityGoodsService;
	
	/**
	 * 添加求购信息
	 */
	public Map<String, Object> addAskBuyInfo(Map map) {
		Map<String, Object> resultMap = eiCSCManager.insertProductService(map);
		return resultMap;
	}

	/**
	 * 查询用户订单列表
	 */
	public Map getUserOrderList(Map map) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<AskBuyOrderVO> orderList = new ArrayList<AskBuyOrderVO>();
		
		String userId = (String) map.get("userId");
		int startRow = Integer.parseInt(map.get("startRow").toString());
		int endRow = Integer.parseInt(map.get("endRow").toString());
		
		//查询用户订单列表
		List<OrderInfoDTO> orderInfoList = eiCSCManager.getUserOrdersByUserId(Long.parseLong(userId), startRow, endRow);
		
		if(orderInfoList != null && orderInfoList.size() > 0) {
			for(OrderInfoDTO orderInfo : orderInfoList) {
				orderList.add(convertOrder(orderInfo, false));
			}
		}
		
		//查询用户订单数量
		int count = eiCSCManager.getUserOrderCount(Long.parseLong(userId));
		
		dataMap.put("orderList", orderList);
		dataMap.put("totalCount", count);
		
		return dataMap;
	}

	/**
	 * 根据订单ID查询订单详情
	 */
	public AskBuyOrderVO getOrderInfoByOrderId(Map map) {
		AskBuyOrderVO order = null;
		
		String orderId = (String) map.get("orderId");
		//根据订单ID查询订单详情
		OrderInfoDTO orderInfo = eiCSCManager.getUsersOrderInfoByOrderId(Long.parseLong(orderId));
		if(orderInfo != null) {
			//订单对象转换
			order = convertOrder(orderInfo, true);
		}
		
		return order;
	}
	
	/**
	 * 求购订单对象转换
	 * @param orderInfo
	 * @param goodsFlag		是否转换商品
	 * @return
	 */
	private AskBuyOrderVO convertOrder(OrderInfoDTO orderInfo, boolean goodsFlag) {
		AskBuyOrderVO order = new AskBuyOrderVO();
		
		order.setOrderId(Long.parseLong(orderInfo.getOderId()));
		order.setProductType(orderInfo.getSort());
		order.setBrandName(orderInfo.getBrand());
		order.setSex(orderInfo.getProductType());
		order.setProductNumber(orderInfo.getProductNumber());
		order.setProductDesc(orderInfo.getProductDesc());
		order.setReferenceUrl(orderInfo.getReferenceArdress());
		order.setMobile(orderInfo.getMobile());
		order.setReason(orderInfo.getComment());
		
		//提交时间
		String commitTime = orderInfo.getCommitTime();
		if(StringUtils.isNotBlank(commitTime) && commitTime.length() > 10){
			commitTime = commitTime.substring(0, 10);
		}
		order.setCreateDate(commitTime);
		
		List<String> picList = new ArrayList<String>();
		String picUrl = "";
		
		//处理图片
		if(StringUtils.isNotBlank(orderInfo.getPic1())) {
			picUrl = orderInfo.getPic1();
			picList.add(ImageUtil.getAsyBuyImageConvertUrl(picUrl));
		}
		if(StringUtils.isNotBlank(orderInfo.getPic2())) {
			picUrl = orderInfo.getPic2();
			picList.add(ImageUtil.getAsyBuyImageConvertUrl(picUrl));
		}
		if(StringUtils.isNotBlank(orderInfo.getPic3())) {
			picUrl = orderInfo.getPic3();
			picList.add(ImageUtil.getAsyBuyImageConvertUrl(picUrl));
		}
		if(StringUtils.isNotBlank(orderInfo.getPic4())) {
			picUrl = orderInfo.getPic4();
			picList.add(ImageUtil.getAsyBuyImageConvertUrl(picUrl));
		}
		if(StringUtils.isNotBlank(orderInfo.getPic5())) {
			picUrl = orderInfo.getPic5();
			picList.add(ImageUtil.getAsyBuyImageConvertUrl(picUrl));
		}
		if(StringUtils.isNotBlank(orderInfo.getPic6())) {
			picUrl = orderInfo.getPic6();
			picList.add(ImageUtil.getAsyBuyImageConvertUrl(picUrl));
		}
		order.setPicList(picList);	//设置转换后的图片
		
		
		//处理订单状态
		String orderStatus = orderInfo.getStatus();
				
		if(StringUtils.isNotBlank(orderStatus)) {
			//如果关联了走秀的商品，则查询商品信息并返回
			String goodsSn = orderInfo.getXiuId();	//走秀码
			
			if(StringUtils.isNotBlank(goodsSn)) {
				List<Product> products = topicActivityGoodsService.batchLoadProducts(goodsSn);
				if(products != null && products.size() > 0){
					List<ItemSettleResultDO> resultDOList = topicActivityGoodsService.itemListSettle(products);
					Product product = products.get(0);
					GoodsVo goods = topicActivityGoodsService.assembleGoodsItem(product, resultDOList);
					
					if (StringUtils.isNotEmpty(goods.getGoodsImg())) {
						//处理图片
						String orgi = goods.getGoodsImg() + XiuConstant.G1_IMG;
						String iurl = ImageServiceConvertor.removePort(orgi);// 去掉端口号
						iurl = ImageServiceConvertor.replaceImageDomain(iurl);

						//目前图片服务器前缀
						String goodsId =  goods.getGoodsId();
			        	int index = Integer.parseInt(goodsId.substring(goodsId.length()-1, goodsId.length()));
						String imgUrl = "http://" + XiuConstant.XIUSTATIC_NUMS[index%3] + ".xiustatic.com";
						String[] temp = goods.getGoodsImg().split("/");
						String imgName = temp[temp.length - 1];
						String url = goods.getGoodsImg().split(imgName)[0] + imgName + "/g1_315_420.jpg";
						String[] sName = url.split("upload");

						goods.setGoodsImg(imgUrl + "/upload" + sName[1]);
					}
					
					order.setGoodsInfo(goods);
				}
			}
			
			//订单状态转换
			if(orderStatus.equals("0")) {
				orderStatus = "待审核";
			} else if(orderStatus.equals("1")) {
				orderStatus = "询货中";
			} else if(orderStatus.equals("2")) {
				orderStatus = "确认购买";
			} else if(orderStatus.equals("3")) {
				orderStatus = "审核不通过";
			} else if(orderStatus.equals("4")) {
				orderStatus = "已有商品";
			} else if(orderStatus.equals("5")) {
				orderStatus = "需询货";
			} else if(orderStatus.equals("6")) {
				orderStatus = "询货成功";
			} else if(orderStatus.equals("7")) {
				orderStatus = "询货失败";
			} else if(orderStatus.equals("8")) {
				orderStatus = "已下单";
			} else if(orderStatus.equals("9")) {
				orderStatus = "已关闭";
			}
			
		}
		order.setOrderStatus(orderStatus);	//设置转换后的订单状态
		
		return order;
	}

	/**
	 * 查询最新的订单
	 */
	public List<String> getNearOrderList(Map map) {
		List<String> resultList = new ArrayList<String>();
		
		List<OrderInfoDTO> orderInfoList = eiCSCManager.getUsersNearOrders();
		
		if(orderInfoList != null && orderInfoList.size() > 0) {
			for(OrderInfoDTO order : orderInfoList) {
				//处理求购信息
				StringBuffer orderInfo = new StringBuffer();
				String mobile = order.getMobile();
				mobile = mobile.substring(0, 3) + "****" + mobile.substring(mobile.length() - 4, mobile.length());
				String brandName = order.getBrand();
				String productType = order.getSort();
				
				orderInfo.append("用户").append(mobile).append("已提交代购").append(brandName).append(productType).append("求购申请");
				
				resultList.add(orderInfo.toString());
			}
		}
		
		return resultList;
	}

	/**
	 * 删除订单
	 */
	public Map<String, Object> deleteOrder(Map map) {
		String orderId = (String) map.get("orderId");
		Map<String, Object> resultMap = eiCSCManager.deleteOrder(Long.parseLong(orderId));
		return resultMap;
	}
	
	/**
	 * 查询产品分类和品牌信息  
	 */
	public List<ProductTypeBrandVO> getProductTypeAndBrand() {
		List<ProductTypeBrandVO> productTypeList = new ArrayList<ProductTypeBrandVO>();
		
		//查询产品分类和品牌信息
		List result = askBuyDao.getProductTypeAndBrand();
		
		if(result != null && result.size() > 0) {
			for(int i = 0; i < result.size(); i++) {
				ProductTypeBrandVO obj = new ProductTypeBrandVO();
				//产品分类名称
				Map map = (Map) result.get(i);
				obj.setProductType(map.get("TYPE_NAME").toString());
				
				//品牌列表
				String brands = (String) map.get("BRAND");
				if(StringUtils.isNotBlank(brands)) {
					String[] brandArr = brands.split(",");
					List<String> brandList = Arrays.asList(brandArr);
					obj.setBrandList(brandList);
				}
				
				productTypeList.add(obj);
			}
		}
		
		return productTypeList;
	}
	
	/**
	 * 查询产品分类和品牌信息  
	 */
//	public Integer getAskBuyHadNews(Map params) {
//		
//		//查询产品分类和品牌信息
//		Integer result = askBuyDao.getAskBuyHadNews(params);
//		
//		if(result != null && result.size() > 0) {
//			for(int i = 0; i < result.size(); i++) {
//				ProductTypeBrandVO obj = new ProductTypeBrandVO();
//				//产品分类名称
//				Map map = (Map) result.get(i);
//				obj.setProductType(map.get("TYPE_NAME").toString());
//				
//				//品牌列表
//				String brands = (String) map.get("BRAND");
//				if(StringUtils.isNotBlank(brands)) {
//					String[] brandArr = brands.split(",");
//					List<String> brandList = Arrays.asList(brandArr);
//					obj.setBrandList(brandList);
//				}
//				
//				productTypeList.add(obj);
//			}
//		}
//		
//		return productTypeList;
//	}

}
