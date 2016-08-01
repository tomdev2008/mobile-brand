package com.xiu.mobile.portal.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xiu.common.lang.StringUtil;
import com.xiu.mobile.core.exception.BusinessException;
import com.xiu.mobile.core.model.CrumbsVo;
import com.xiu.mobile.core.service.ICrumbsService;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.EncryptUtils;
import com.xiu.mobile.portal.common.util.HttpUtils;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.model.AddressListQueryInParam;
import com.xiu.mobile.portal.model.AddressVo;
import com.xiu.mobile.portal.model.FlexibleItem;
import com.xiu.mobile.portal.model.GoodsDetailVo;
import com.xiu.mobile.portal.model.PriceCompareVo;
import com.xiu.mobile.portal.model.ResultDO;
import com.xiu.mobile.portal.service.IAddressService;
import com.xiu.mobile.portal.service.IGoodsService;
import com.xiu.mobile.portal.service.IPCSService;
import com.xiu.mobile.portal.service.IProductService;
import com.xiu.mobile.portal.service.IReceiverIdService;
import com.xiu.uuc.facade.dto.IdentityInfoDTO;

/**
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-13 下午6:12:41 
 * ***************************************************************
 * </p>
 */
@Controller
@RequestMapping("/goods")
public class GoodsController extends BaseController {	
	private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);
	
	@Autowired
	private IGoodsService goodsManager;
	@Autowired
	private IAddressService addressService;
	@Autowired
	private IReceiverIdService receiverIdService;
	@Autowired
	private ICrumbsService crumbsService;
	@Autowired
	private IPCSService pcsService;
	@Autowired
	private IProductService productServiceImpl;

	/**
	 * 根据商品ID查询商品详情，此处先根据goodsId查询goodsSn，然后根据调用loadGoodsDetail()
	 */
	@ResponseBody
	@RequestMapping(value = "loadGoodsDetailByGoodsId", produces = "text/html;charset=UTF-8")
	public String loadGoodsDetailByGoodsId(
			String goodsId, String jsoncallback, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		logger.info("查询商品详情参数params="+request.getQueryString());
		try {
			if (!StringUtils.isEmpty(goodsId)) {
				String goodsSn = goodsManager.getGoodsSnByGoodsId(goodsId);
				
				if (!StringUtils.isEmpty(goodsSn)) {
					return loadGoodsDetail(goodsSn, jsoncallback, request);
				}
			}
			
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.GoodsNotFound.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.GoodsNotFound.getErrorMsg());
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("加载商品详情发生异常（ByGoodsId）：", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

	/**
	 * 商品详情
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "loadGoodsDetail", produces = "text/html;charset=UTF-8")
	public String loadGoodsDetail(
			String goodsSn, String jsoncallback, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		logger.info("查询商品详情参数params="+request.getQueryString());
		if(StringUtils.isEmpty(goodsSn)) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.GoodsNotFound.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.GoodsNotFound.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		
		try {
			GoodsDetailVo goodsDetailVo = goodsManager.viewGoodsDetail(goodsSn, getDeviceParams(request));
			Long brandId = goodsManager.getBrandIdByGoodsSn(goodsSn);
			goodsDetailVo.setBrandId(brandId);
			
			//返回结果加分类ID(最后一个)
			List<CrumbsVo> list=crumbsService.getCrumbsByGoodsSn(goodsSn);
			if(list!=null && list.size()>0)	{
				Integer categoryId =list.get(list.size()-1).getCatalogId();
				goodsDetailVo.setCatalogId(categoryId);
		    }
			
			try {
				// 查询商品的评论数量
				String prodId = goodsDetailVo.getGoodsInnerId();
				String url = XiuConstant.GET_COMMENTNUM_BY_PROD + "?prodId="+prodId+"&format=jsonp";
				String httpResult = HttpUtils.getMethod(url);
				if(!StringUtils.isEmpty(httpResult)) {
					ResultDO commentResult = JsonUtils.json2bean(httpResult, ResultDO.class);
					String data = commentResult.getData();
					goodsDetailVo.setCommentNum(Integer.parseInt(data));
				}
			} catch (Exception e) {
				goodsDetailVo.setCommentNum(0);
			}
			
			resultMap.put("result", true);
			resultMap.put("goodsDetailVo", dealImgUrl(goodsDetailVo));
			// 查询商品比价信息
			PriceCompareVo priceCompareVo = pcsService.getPriceCompareBySn(goodsSn);
			if (priceCompareVo != null) {
				// 非整型数，运算由于精度问题，可能会有误差，使用BigDecimal类型
				BigDecimal zsPrice = new BigDecimal(goodsDetailVo.getZsPrice());
				BigDecimal comparePrice = new BigDecimal(priceCompareVo.getGoodsRMBPrice());
				int result = zsPrice.compareTo(comparePrice);
				// 如果走秀价大于其他官网价 则不展示
				if (result >= 0) {
					// 清除掉比价数据
					priceCompareVo = new PriceCompareVo();
					priceCompareVo.setShowStatus(false);
				}else{
					priceCompareVo.setShowStatus(true);
				}
			} else {
				// 清除掉比价数据
				priceCompareVo = new PriceCompareVo();
				priceCompareVo.setShowStatus(false);
			}
			resultMap.put("priceCompareVo", priceCompareVo);
		}
		catch (BusinessException e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.GoodsNotFound.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.GoodsNotFound.getErrorMsg());
			logger.error("加载商品详情发生异常：", e );
		}
		catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("加载商品详情发生异常");
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

	/**
	 * 加载商品、收货地址 相关信息
	 * @throws Exception 
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "loadGoodsAndAddressInfo", produces = "text/html;charset=UTF-8")
	public String loadGoodsAndAddressInfo(String goodsSn, String jsoncallback,
			HttpServletRequest request) throws Exception {
		// 存储返回结果值
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if(StringUtils.isEmpty(goodsSn)) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.ParamsError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}

		try {
			GoodsDetailVo objGoodsDetailVo = goodsManager.viewGoodsDetail(goodsSn, getDeviceParams(request));

			if (null != objGoodsDetailVo) {
				// 获取默认收货地址
				AddressListQueryInParam addressListQuery = new AddressListQueryInParam();
				SessionUtil.setDeviceInfo(request, addressListQuery);
				AddressVo addressVo = addressService.getMasterAddress(addressListQuery);
				boolean uploadIdCardStatus = true;
				// 加载身份认证信息
				if (addressVo!=null) {
					int uploadIdCard = goodsManager.getGoodsUploadIdCardByGoodsSn(goodsSn); //查询商品上传身份证状态
					
					boolean flag = false;
					Long identityId = addressVo.getIdentityId();
					if(identityId != null && identityId.longValue() != 0 ){
						//如果地址的身份信息ID不为空
						IdentityInfoDTO identityInfoDTO = receiverIdService.queryIdentityInfoById(identityId);
						if(identityInfoDTO != null) {
							if(identityInfoDTO.getIdNumber()!=null&&!identityInfoDTO.getIdNumber().equals("")){
								flag=true;
							}
//							if(identityInfoDTO.getReviewState() != null && identityInfoDTO.getReviewState().equals(1)) {
//								flag = true;
//							}
							addressVo.setIdCard(identityInfoDTO.getIdNumber());
							
							if (uploadIdCard == 0 || uploadIdCard == 1) {
								// 审核状态 reviewState{0 - 待审核, 1 - 审核通过, 2 - 审核不通过}
								Integer reviewState = identityInfoDTO.getReviewState();
								//  审核通过和待审核状态的身份证，则显示支付成功页面  否则提示需要上传身份证
								if(reviewState == 2) {
									uploadIdCardStatus = false;
								} else {
									uploadIdCardStatus = true;
								}
							}
						} else {
							if (uploadIdCard == 0 || uploadIdCard == 1) {
								uploadIdCardStatus = false;
							}
						}
					} else {
						if (uploadIdCard == 0 || uploadIdCard == 1) {
							uploadIdCardStatus = false;
						}
					}
					
//					boolean flag = false;
//					LoginResVo user = SessionUtil.getUser(request);
//					UserIdentityDTO userIdentityDTO = receiverIdService.getUserIdentity(user.getUserId(), addressVo.getAddressId());
//					if (userIdentityDTO!=null) {
//						// 是否审核0:未审核 1:审核通过 2:审核不通过
//						Long isReview = userIdentityDTO.getIsReview();
//						if (isReview!=null && (isReview ==1)) {
//							flag = true;
//						}
//						
//					}
					// addressId加密(AES)
					addressVo.setAddressId(EncryptUtils.encryptByAES(addressVo.getAddressId(), EncryptUtils.getAESKeySelf()));
					addressVo.setIdAuthorized(flag);
					
					if(StringUtils.isEmpty(addressVo.getMobile())) {
						//如果手机号为空，则改为空串
						addressVo.setMobile("");
					}
				}

				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());

				resultMap.put("uploadIdCardStatus", uploadIdCardStatus);
				// 设置默认收货地址
				resultMap.put("addressVo", addressVo);
				// 设置商品信息
				resultMap.put("goodsSn", objGoodsDetailVo.getGoodsSn());
				resultMap.put("goodsName", objGoodsDetailVo.getGoodsName());
				Long brandId = goodsManager.getBrandIdByGoodsSn(goodsSn);
				resultMap.put("brandId", brandId);
				
				//是否支持礼品包装
				boolean supportWrapStatus = goodsManager.isProductSupportWrap(goodsSn);
				resultMap.put("supportPackaging", supportWrapStatus);
				if(supportWrapStatus) {
					String packagingPrice = goodsManager.getProductPackagingPrice();
					resultMap.put("packagingPrice", packagingPrice);
				}
				
				//限购数量
				int limitSaleNum = goodsManager.getGoodsLimitSaleNum(goodsSn);
				resultMap.put("limitSaleNum", limitSaleNum);
				
				Integer categoryId = 0; 
				//返回结果加分类ID(最后一个)
				List<CrumbsVo> list=crumbsService.getCrumbsByGoodsSn(goodsSn);
				if(list!=null && list.size()>0)	{
					categoryId =list.get(list.size()-1).getCatalogId();
			    }
				resultMap.put("catalogId", categoryId);
				String price = objGoodsDetailVo.getZsPrice();
				String[] tempPrice = price.split("\\.");

				if (tempPrice.length > 0 && tempPrice[1].equals("00")) {
					resultMap.put("goodsPrice", tempPrice[0]);
				} else {
					resultMap.put("goodsPrice", objGoodsDetailVo.getZsPrice());
				}

				resultMap.put("goodsImgUrlList", dealLoadGoodsImgUrl(objGoodsDetailVo.getImgList()));
			} else {
				logger.error("该商品已下架：{goodsSn：" + goodsSn + "}");
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.GoodsNotOnSale.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.GoodsNotOnSale.getErrorMsg());
			}
		} 
		catch (BusinessException e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.GoodsNotOnSale.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.GoodsNotOnSale.getErrorMsg());
			logger.error("加载商品详情发生异常：{goodsSn：" + goodsSn + "}", e);
		}
		catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("加载商品详情发生异常：", e);
		}

		logger.info("立即购买返回结果：" + JSON.toJSONString(resultMap));
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

	/**
	 * 返回SKU的信息
	 */
/*	@ResponseBody
	@RequestMapping("getGoodsSku")
	public String getGoodsSku(HttpServletRequest request, String goodsSn, String goodsSku) {
		String json = null;
		
		try {
			json = goodsManager.getGoodsSkuJson(goodsSn, goodsSku, getDeviceParams(request));
		} catch (Exception e) {
			logger.error("返回SKU的JSON字符信息时发生异常：" + e.getMessage(),e);
		}
		
		logger.info("返回SKU的JSON字符信息：" + json);
		return json;
	}*/
	
	/**
	 * 处理商品详情图片地址
	 * @param goods
	 * @return
	 */
	protected GoodsDetailVo dealImgUrl(GoodsDetailVo goods) {
		List<FlexibleItem> fm = goods.getImgList();
		List<FlexibleItem> getFm = new ArrayList<FlexibleItem>();
		List<FlexibleItem> getFmBig = new ArrayList<FlexibleItem>();
		String imgServerUrl = getImgServerUrlPre();
		
		for (FlexibleItem f : fm) {
			if (f.getValue() != null && !f.getValue().equals("[]")) {
				FlexibleItem fTemp1 = new FlexibleItem();// 小图实体
				FlexibleItem fTemp2 = new FlexibleItem();// 大图实体
				
				if (f.getValue() != null) {
					String[] temp = f.getValue().split(",");
					StringBuffer newValue1 = new StringBuffer("[");
					StringBuffer newValue2 = new StringBuffer("[");
					
					for (int i = 0; i < temp.length; i++) {
						String[] temp1 = temp[i].split("com/")[1].split("_");
						newValue1.append("\"" + imgServerUrl + temp1[0] + "_"
								+ "402_536.jpg");
						newValue2.append("\"" + imgServerUrl + temp1[0] + "_"
								+ "720_960.jpg");
						
						if (i != temp.length - 1) {
							newValue1.append("\",");
							newValue2.append("\",");
						}
					}
					
					newValue1.append("\"]");
					newValue2.append("\"]");
					fTemp1.setValue(newValue1.toString());
					fTemp2.setValue(newValue2.toString());
				}
				
				fTemp1.setKey(f.getKey());
				fTemp2.setKey(f.getKey());
				getFm.add(fTemp1);
				getFmBig.add(fTemp2);
			}
		}
		
		goods.setImgList(getFm);
		goods.setImgListMax(getFmBig);
		return goods;
	}
	
	/**
	 * 获取图片服务器URL访问前缀
	 * @return url
	 */
	private String getImgServerUrlPre() {
		// 目前图片服务器前缀
		int index = new Random().nextInt(XiuConstant.XIUSTATIC_NUMS.length);
		return "http://" + XiuConstant.XIUSTATIC_NUMS[index] + ".xiustatic.com/";
	}
	
	/**
	 * 处理商品详情图片地址
	 * @param fm
	 * @return
	 */
	protected List<FlexibleItem> dealLoadGoodsImgUrl(List<FlexibleItem> fm) {
		List<FlexibleItem> getFm = new ArrayList<FlexibleItem>();
		String imgServerUrl = getImgServerUrlPre();
		
		for (FlexibleItem f : fm) {
			String value = f.getValue();
			
			if (!StringUtils.isEmpty(f.getKey())
					&& !StringUtils.isEmpty(value)
					&& !StringUtils.isEmpty(value.subSequence(
							value.indexOf("[") + 1, value.indexOf("]")))) {
				FlexibleItem fTemp = new FlexibleItem();// 小图实体
				String[] temp = value.split(",");
				StringBuffer newValue = new StringBuffer("[");
				
				for (int i = 0; i < temp.length; i++) {
					String[] temp1 = temp[i].split("com/")[1].split("_");
					newValue.append("\"" + imgServerUrl + temp1[0] + "_"
							+ "66_88.jpg");
					
					if (i != temp.length - 1) {
						newValue.append("\",");
					}
				}
				
				newValue.append("\"]");
				fTemp.setValue(newValue.toString());
				fTemp.setKey(f.getKey());

				getFm.add(fTemp);
			}
		}
		
		return getFm;
	}
	
	/**
	 * 查询商品详情
	 * @param goodsId	商品ID
	 * @param goodsSn	商品走秀码
	 * @param deviceType	设备类型
	 * @param jsoncallback
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getGoodsDetail", produces = "text/html;charset=UTF-8")
	public String getGoodsDetail(String goodsId, String goodsSn, String deviceType, String jsoncallback, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		// 验证参数
		if(StringUtils.isEmpty(goodsId) && StringUtils.isEmpty(goodsSn)) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		
		try {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			if(!StringUtils.isEmpty(goodsId)) {
				//如果商品ID不为空
				String goodsSnResult = goodsManager.getGoodsSnByGoodsId(goodsId);	//查询商品goodsSn
				if(StringUtils.isEmpty(goodsSnResult)) {
					resultMap.put("result", false);
					resultMap.put("errorCode", ErrorCode.GoodsNotFound.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.GoodsNotFound.getErrorMsg());
					return JsonUtils.bean2jsonP(jsoncallback, resultMap);
				}
				paraMap.put("goodsSn", goodsSnResult);
			} else {
				paraMap.put("goodsSn", goodsSn);
			}
			
			if(StringUtils.isEmpty(deviceType)) {
				deviceType = "";
			}
			paraMap.put("deviceType", deviceType);
			String userId = SessionUtil.getUserId(request);
			paraMap.put("userId", userId); //用户ID
			//查询商品详情
			Map<String, Object> dataMap = goodsManager.getGoodsDetail(paraMap);
			
			boolean result = (Boolean) dataMap.get("result");
			if(result) {
				//成功
				resultMap = dataMap;
			} else {
				resultMap.put("result", false);
				Integer errorCode = (Integer) dataMap.get("errorCode");
				resultMap.put("errorCode", errorCode);
				resultMap.put("errorMsg", ErrorCode.getErrorMsgByCode(errorCode));
			}
			
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询商品详情时发生异常：", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 查询商品详情 V3.7版本
	 * @param goodsId	商品ID
	 * @param goodsSn	商品走秀码
	 * @param deviceType	设备类型
	 * @param jsoncallback
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getGoodsDetailV7", produces = "text/html;charset=UTF-8")
	public String getGoodsDetailV7(String goodsId, String goodsSn, String deviceType, String jsoncallback, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		// 验证参数
		if(StringUtils.isEmpty(goodsId) && StringUtils.isEmpty(goodsSn)) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		try {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			if(!StringUtils.isEmpty(goodsId)) {
				//如果商品ID不为空
				String goodsSnResult = goodsManager.getGoodsSnByGoodsId(goodsId);	//查询商品goodsSn
				if(StringUtils.isEmpty(goodsSnResult)) {
					resultMap.put("result", false);
					resultMap.put("errorCode", ErrorCode.GoodsNotFound.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.GoodsNotFound.getErrorMsg());
					return JsonUtils.bean2jsonP(jsoncallback, resultMap);
				}
				paraMap.put("goodsSn", goodsSnResult);
			} else {
				paraMap.put("goodsSn", goodsSn);
			}
			
			if(StringUtils.isEmpty(deviceType)) {
				deviceType = "";
			}
			paraMap.put("deviceType", deviceType);
			String userId = SessionUtil.getUserId(request);
			paraMap.put("userId", userId); //用户ID
			paraMap.put("appVersion", "3.7"); //添加版本号
			//查询商品详情
			Map<String, Object> dataMap = goodsManager.getGoodsDetail(paraMap);
			
			boolean result = (Boolean) dataMap.get("result");
			if(result) {
				//成功
				resultMap = dataMap;
				resultMap.put("errorCode", "0");
				resultMap.put("errorMsg", "成功");
			} else {
				resultMap.put("result", false);
				Integer errorCode = (Integer) dataMap.get("errorCode");
				resultMap.put("errorCode", errorCode);
				resultMap.put("errorMsg", ErrorCode.getErrorMsgByCode(errorCode));
			}
			
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询商品详情时发生异常：", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 查询秀客商品详情
	 * @param goodsSn	商品走秀码
	 * @param jsoncallback
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getShowGoodsDetail", produces = "text/html;charset=UTF-8")
	public String getShowGoodsDetail(String goodsId, String deviceType, String jsoncallback, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		// 验证参数
		if(StringUtils.isEmpty(goodsId)) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		
		try {
			String goodsSnResult = goodsManager.getGoodsSnByGoodsId(goodsId);	//查询商品goodsSn
			if(StringUtils.isEmpty(goodsSnResult)) {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.GoodsNotFound.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.GoodsNotFound.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
			}
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("goodsSn", goodsSnResult);
			String userId = SessionUtil.getUserId(request);
			paraMap.put("userId", userId); //用户ID
			paraMap.put("appVersion", "4.0"); //添加版本号
			if(StringUtils.isEmpty(deviceType)) {
				deviceType = "";
			}
			paraMap.put("deviceType", deviceType);
			//查询商品详情
			Map<String, Object> dataMap = goodsManager.getGoodsDetail(paraMap);
			
			boolean result = (Boolean) dataMap.get("result");
			if(result) {
				//成功
				resultMap = dataMap;
			} else {
				resultMap.put("result", false);
				Integer errorCode = (Integer) dataMap.get("errorCode");
				resultMap.put("errorCode", errorCode);
				resultMap.put("errorMsg", ErrorCode.getErrorMsgByCode(errorCode));
			}
			
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询商品详情时发生异常：", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 添加求购商品信息
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addAskBuyInfo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String addAskBuyInfo(HttpServletRequest request, String jsoncallback) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String goodsId = request.getParameter("goodsId");	//商品ID
		String goodsSn = request.getParameter("goodsSn");	//商品走秀码
		String minPrice = request.getParameter("minPrice");	//最小价格
		String maxPrice = request.getParameter("maxPrice"); //最大价格
		String color = request.getParameter("color");		//颜色
		String size = request.getParameter("size");			//尺码
		String mobile = request.getParameter("mobile");		//电话
		String terminal = request.getParameter("terminal");	//终端来源
		
		//检查参数
		if(StringUtil.isBlank(mobile) || StringUtil.isBlank(terminal) || StringUtil.isBlank(goodsId) || StringUtil.isBlank(goodsSn)
				|| StringUtil.isBlank(minPrice) || StringUtil.isBlank(maxPrice) || StringUtil.isBlank(color) || StringUtil.isBlank(size)) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		
		try {
			//拼串参数
			String referenceUrl = "http://m.xiu.com/product/"+goodsId+".html"; //商品详情页地址
			String productDesc = "颜色："+color+"，尺码："+size+"，期望价格："+minPrice+" - "+maxPrice;
			String userId = SessionUtil.getUserId(request);
			
			//组装参数
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("userId", userId);
			paraMap.put("mobile", mobile);
			paraMap.put("terminal", terminal);
			paraMap.put("goodsSn", goodsSn);
			paraMap.put("goodsId", goodsId);
			paraMap.put("referenceUrl", referenceUrl);
			paraMap.put("productDesc", productDesc);
			
			//添加商品求购信息
			Map<String, Object> map = goodsManager.addAskBuyInfo(paraMap);
					
			boolean result = (Boolean) map.get("result");
			
			if(result) {
				//成功
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			} else { 
				//失败
				resultMap.put("result", false);
				
				if(map.containsKey("errorCode")) {
					resultMap.put("errorCode", map.get("errorCode"));
					resultMap.put("errorMsg", map.get("errorMsg"));
				} else {
					resultMap.put("errorCode", ErrorCode.AddAskBuyInfoFailed.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.AddAskBuyInfoFailed.getErrorMsg());
				}
			}
			
		} catch(Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("添加求购商品信息时发生异常：" + e.getMessage());
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 一品多商：加购物车或立即购买时，校验是否有库存
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkStock", produces = "text/html;charset=UTF-8")
	public String checkStock(String goodsSn, String goodsSku, String jsoncallback, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		// 验证参数
		if(StringUtils.isEmpty(goodsSn) || StringUtils.isEmpty(goodsSku)) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		try {
			Map<String, Object> dataMap = goodsManager.checkStock(goodsSn, goodsSku);
			
			boolean result = (Boolean) dataMap.get("result");
			if(result) {
				//成功
				resultMap = dataMap;
				resultMap.put("errorCode", "0");
				resultMap.put("errorMsg", "成功");
			} else {
				resultMap.put("result", false);
				Integer errorCode = (Integer) dataMap.get("errorCode");
				resultMap.put("errorCode", errorCode);
				resultMap.put("errorMsg", ErrorCode.getErrorMsgByCode(errorCode));
			}
			
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("加购物车或立即购买时，校验是否有库存异常，goodsSku: " + goodsSku, e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
}
