package com.xiu.mobile.portal.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.xiu.commerce.hessian.model.Product;
import com.xiu.common.lang.PagingList;
import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.EncryptUtils;
import com.xiu.mobile.portal.common.util.ResponseUtil;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.facade.utils.HttpUtil;
import com.xiu.mobile.portal.model.CommoSummaryOutParam;
import com.xiu.mobile.portal.model.GoodsVo;
import com.xiu.mobile.portal.model.LoginResVo;
import com.xiu.mobile.portal.model.OrderListInParam;
import com.xiu.mobile.portal.model.OrderSummaryVo;
import com.xiu.mobile.portal.model.RefundApplyVo;
import com.xiu.mobile.portal.model.RefundGoodsInfoVo;
import com.xiu.mobile.portal.model.RefundLogisticsVo;
import com.xiu.mobile.portal.model.RefundOrderInfoVo;
import com.xiu.mobile.portal.service.IGoodsRefundService;
import com.xiu.mobile.portal.service.ILoginService;
import com.xiu.mobile.portal.service.IOrderListService;
import com.xiu.mobile.portal.service.ITopicActivityGoodsService;
import com.xiu.mobile.portal.service.impl.RefundOrderInfoVoConvertor;
import com.xiu.mobile.portal.url.DomainURL;
import com.xiu.show.core.common.util.ObjectUtil;
import com.xiu.tc.common.orders.domain.BizOrderDO;
import com.xiu.tc.common.orders.domain.RefundItemFlowCompanyDO;
import com.xiu.tc.common.orders.domain.RefundItemFlowDO;

@Controller
@RequestMapping("/goodsRefund")
public class RefundController extends BaseController{
	
	private static final Logger logger = Logger.getLogger(CouponController.class);
	
	@Autowired
	private DomainURL domainURL;
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IGoodsRefundService goodsRefundService;
	@Autowired
	private IOrderListService orderListService;
	@Autowired
	private ITopicActivityGoodsService topicActivityGoodsService;
	
	/***
	 * 获取用户退换货列表
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getOrderList", produces = "text/html;charset=UTF-8")
	public String getOrderList(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		int pageNo = NumberUtils.toInt(request.getParameter("pageNo"), 1);
		int pageSize = NumberUtils.toInt(request.getParameter("pageSize"),12);
		LoginResVo user = SessionUtil.getUser(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			// 封装查询参数
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", user.getUserId());
			params.put("status", request.getParameter("status"));
			params.put("pageNo", pageNo);
			params.put("pageSize", pageSize);
			// 查询用户订单商品退换货列表信息
			PagingList<RefundItemFlowDO> pagingList = goodsRefundService.getUserRefundList(params);
			List<RefundOrderInfoVo> orderInfoList = RefundOrderInfoVoConvertor.getRefundOrderInfoVoList(pagingList);
			StringBuffer goodsb=new StringBuffer();
			for (RefundOrderInfoVo refundOrderInfoVo : orderInfoList) {
				// 查询用户订单商品退换货物流信息
				List<RefundItemFlowCompanyDO> refundItemFlowCompanyDOList = goodsRefundService.getRefundLogisticsByRefundFlowId(refundOrderInfoVo.getId());
				// 设置用户订单物流登记状态  1为已登记  0为未登记
				if (refundItemFlowCompanyDOList!=null && refundItemFlowCompanyDOList.size()>0) {
					refundOrderInfoVo.setLogisticsStatus("1");
				}else{
					refundOrderInfoVo.setLogisticsStatus("0");
				}
				// orderId不为空
				if(StringUtils.isNotEmpty(refundOrderInfoVo.getOrderId())){
					// orderId加密(AES)
					String aesKey=EncryptUtils.getAESKeySelf();
					String orderId=EncryptUtils.encryptOrderIdByAES(refundOrderInfoVo.getOrderId(), aesKey);
					refundOrderInfoVo.setOrderId(orderId);
				}
				RefundGoodsInfoVo iteminfo=refundOrderInfoVo.getGoodsInfoVo();
				if(iteminfo!=null){
					if(!goodsb.toString().equals("")){
						goodsb.append(",");
					}
					goodsb.append(ObjectUtil.getLong(iteminfo.getGoodsId(),0l));
				}
			}
			
//			//查询商品品牌
			String goodids=goodsb.toString();
			if(!goodids.equals("")){
				List<GoodsVo> goods = topicActivityGoodsService.getBrandNameByGoodIds(goodids.split(","));
				for(RefundOrderInfoVo refundOrderInfoVo :orderInfoList){
					RefundGoodsInfoVo iteminfo=refundOrderInfoVo.getGoodsInfoVo();
					String brandName=iteminfo.getBrandName();
					if(iteminfo!=null){
						for (GoodsVo p:goods) {
							if(iteminfo.getGoodsId().equals(p.getGoodsId())){
								if(p.getBrandEnName()!=null&&!p.getBrandEnName().equals("")){
									brandName=p.getBrandEnName();
								}else if(p.getBrandCNName()!=null&&!p.getBrandCNName().equals("")){
									brandName=p.getBrandCNName();
								}
							}
						}
					}
					iteminfo.setBrandName(brandName);
				}
			}
			
			
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			result.put("pageNo", pagingList.getCurrentPage());
			result.put("totalPage", pagingList.getTotalPage());
			result.put("total", pagingList.getTotalNum());
			result.put("orderInfoList", orderInfoList);
		} catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("获取用户退换货列表异常:", e);
		}catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取用户退换货列表异常:", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/***
	 * 获取能被申请售后的订单列表
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getCanBeApplyOrderList", produces = "text/html;charset=UTF-8")
	public String getCanbeApplyOrderList(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		// 存储返回结果值
				int page = NumberUtils.toInt(request.getParameter("pageNo"), 1);
				int pageSize = NumberUtils.toInt(request.getParameter("pageSize"),12);
				Map<String, Object> model = new LinkedHashMap<String, Object>();
				String queryType="6";
				try{
					// 检查登录
					String tokenId = SessionUtil.getTokenId(request);
					PagingList<BizOrderDO> pagingList =new PagingList<BizOrderDO>();
					pagingList.setPage(page);
					pagingList.setPageSize(pageSize);
					// 组装查询参数
					OrderListInParam orderListInParam = getOrderListInParam(request,tokenId, queryType, page, pageSize);
					
					List<OrderSummaryVo> waitCommentOrderList =this.orderListService.getCanBeRefundOrderList(orderListInParam,pagingList);
					// 查询参数商品Id
					List<Long> itemIds = new ArrayList<Long>();
					// orderId加密(AES)
					for(OrderSummaryVo order:waitCommentOrderList){
						String aesKey=EncryptUtils.getAESKeySelf();
						String orderId=EncryptUtils.encryptOrderIdByAES(order.getOrderId(), aesKey);
						order.setOrderId(orderId);
						
						//
						List<CommoSummaryOutParam> items=  order.getGoodsList();
						if(items!=null){
							for (CommoSummaryOutParam item:items) {
								itemIds.add(ObjectUtil.getLong(item.getGoodsId(),0l));
							}
						}
					}
					
					//获取产品品牌名
					List<Product> products = topicActivityGoodsService.batchLoadProducts(itemIds);
					for(OrderSummaryVo order:waitCommentOrderList){
						List<CommoSummaryOutParam> items=  order.getGoodsList();
						if(items!=null){
							for (CommoSummaryOutParam item:items) {
								for (Product p:products) {
									if(item.getGoodsId().equals(p.getInnerID()+"")){
										item.setBrandName(p.getBrandName());
									}
								}
							}
						}
					}
					
					model.put("result", true);
					model.put("errorCode", ErrorCode.Success.getErrorCode());
					model.put("errorMsg", ErrorCode.Success.getErrorMsg());
					model.put("pageNo", pagingList.getCurrentPage());
					model.put("totalPage", pagingList.getTotalPage());
					model.put("total", pagingList.getTotalNum());
					model.put("listData", waitCommentOrderList);
						 
				}catch(EIBusinessException e){
					model.put("result", false);
					model.put("errorCode", ErrorCode.GetOrderDataFailed.getErrorCode());
					model.put("errorMsg", ErrorCode.GetOrderDataFailed.getErrorMsg());
					logger.error("获取订单列表发生异常:" + e.getMessageWithSupportCode(),e);
				}catch(Exception ex){
					model.put("result", false);
					model.put("errorCode", ErrorCode.SystemError.getErrorCode());
					model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
					logger.error("获取订单列表发生异常：",ex);
				}
				ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
				return null;
	}
	
	

	// 供移动端接口使用
	private OrderListInParam getOrderListInParam(HttpServletRequest request,String tokenId, String queryType, int page, int pageSize) {
		OrderListInParam orderListInParam = new OrderListInParam();

		orderListInParam.setTokenId(tokenId);
		// 转换为查询全部订单（在全部订单中过滤各类订单）
		orderListInParam.setQueryType(queryType);
		orderListInParam.setPage(page);
		orderListInParam.setPageSize(pageSize);
		SessionUtil.setDeviceInfo(request, orderListInParam);

		return orderListInParam;
	}
	
	
	/***
	 * 获取用户退换货订单详情
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getOrderInfo", produces = "text/html;charset=UTF-8")
	public String getOrderInfo(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		LoginResVo user = SessionUtil.getUser(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		String itemCode = request.getParameter("itemCode");
		try {
			// 缺少参数
			if (StringUtils.isBlank(itemCode)) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			// 查询用户订单商品退换货列表信息
			RefundItemFlowDO refundItemFlowDO = goodsRefundService.getUserRefundInfoByCode(itemCode, Long.parseLong(user.getUserId()));
			RefundOrderInfoVo refundOrderInfoVo = RefundOrderInfoVoConvertor.getRefundOrderInfoVo(refundItemFlowDO);
			// 用户退换图片信息加上域名
			String proofs = refundOrderInfoVo.getProofImgURL();
			if (StringUtils.isNotBlank(proofs)) {
				String[] proofArr = proofs.split(";");
				StringBuilder imgURLBuilder = new StringBuilder();
				for (int i = 0; i < proofArr.length; i++) {
					String imgURL = domainURL.getShowImgURL().concat(proofArr[i]);
					imgURLBuilder.append(imgURL).append(";");
				}
				String proofImgURL = imgURLBuilder.toString();
				// 去掉最后一个;号
				if (proofImgURL.endsWith(";")) {
					proofImgURL = proofImgURL.substring(0,proofImgURL.length()-1);
				}
				refundOrderInfoVo.setProofImgURL(proofImgURL);
			}
			
			// 查询用户订单商品退换货物流信息
			List<RefundItemFlowCompanyDO> refundItemFlowCompanyDOList = goodsRefundService.getRefundLogisticsByRefundFlowId(refundItemFlowDO.getId());
			// 设置用户订单物流登记状态  1为已登记  0为未登记
			if (refundItemFlowCompanyDOList!=null && refundItemFlowCompanyDOList.size()>0) {
				// 退换货物流信息
				RefundLogisticsVo logisticsVo = RefundOrderInfoVoConvertor.getRefundLogisticsVo(refundItemFlowCompanyDOList.get(0));
				refundOrderInfoVo.setLogisticsVo(logisticsVo);
				refundOrderInfoVo.setLogisticsStatus("1");
			}else{
				refundOrderInfoVo.setLogisticsStatus("0");
			}
			
            RefundGoodsInfoVo info= refundOrderInfoVo.getGoodsInfoVo();
            if(info!=null){
            	List<Long> items=new ArrayList<Long>();
	            items.add(ObjectUtil.getLong(info.getGoodsId(),0l));
				//查询商品品牌
				List<Product> products = topicActivityGoodsService.batchLoadProducts(items);
				for (Product p:products) {
					if(info.getGoodsId().equals(p.getInnerID()+"")){
						info.setBrandName(p.getBrandName());
					}
				}
            }
			
			
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			// orderId加密(AES)
			String aesKey=EncryptUtils.getAESKeySelf();
			String orderId=EncryptUtils.encryptOrderIdByAES(refundOrderInfoVo.getOrderId(), aesKey);
			refundOrderInfoVo.setOrderId(orderId);
			
			result.put("orderInfo", refundOrderInfoVo);
		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("获取用户退换货订单详情异常:", e);
		}catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取用户退换货订单详情异常:", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	
	/***
	 * 退换货申请
	 * @param request
	 * @param response
	 * @param refundApplyVo 退换货申请信息封装
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/refundApply", produces = "text/html;charset=UTF-8")
	public String refundApply(HttpServletRequest request,HttpServletResponse response,RefundApplyVo refundApplyVo,String jsoncallback){
		LoginResVo user = SessionUtil.getUser(request);
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			
			// 封装递交表单用户信息
			refundApplyVo.setUserId(Long.parseLong(user.getUserId()));
			refundApplyVo.setUsername(user.getLogonName());
			String remoteIp = HttpUtil.getRemoteIpAddr(request);
			refundApplyVo.setOperatorIp(remoteIp);
			// 获取上传图片的URL 
			String imgURL = refundApplyVo.getProofImgURL();
			if (StringUtils.isNotBlank(imgURL)) {
				// 图片存储地址不能带域名 此处需要替换
				imgURL = imgURL.replaceAll(domainURL.getShowImgURL(), "");
				refundApplyVo.setProofImgURL(imgURL);
			}
			
			// orderId解密(AES)
			String aesKey=EncryptUtils.getAESKeySelf();
			String orderId=EncryptUtils.decryptOrderIdByAES(refundApplyVo.getOrderId(), aesKey);
			refundApplyVo.setOrderId(orderId);
			
			// 退换货申请
			String applyCode = goodsRefundService.saveRefundItemFlow(refundApplyVo);
			if (StringUtils.isNotBlank(applyCode)) {
				result.put("result", true);
				result.put("errorCode", ErrorCode.Success.getErrorCode());
				result.put("errorMsg", ErrorCode.Success.getErrorMsg());
				result.put("applyCode", applyCode);
			}else{
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			}
		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("用户退换货申请异常:", e);
		}catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("用户退换货申请异常:", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/***
	 * 退换货申请信息更新
	 * @param request
	 * @param response
	 * @param refundApplyVo 退换货申请信息封装
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/refundReplace", produces = "text/html;charset=UTF-8")
	public String refundReplace(HttpServletRequest request,HttpServletResponse response,RefundApplyVo refundApplyVo,String jsoncallback){
		LoginResVo user = SessionUtil.getUser(request);
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			
			// 封装递交表单用户信息
			refundApplyVo.setUserId(Long.parseLong(user.getUserId()));
			refundApplyVo.setUsername(user.getLogonName());
			String remoteIp = HttpUtil.getRemoteIpAddr(request);
			refundApplyVo.setOperatorIp(remoteIp);
			if(null == refundApplyVo.getOrderId()|| refundApplyVo.getOrderId().trim().equals("")){
				refundApplyVo.setOrderId("0");
			}else{
				refundApplyVo.setOrderId(EncryptUtils.decryptOrderIdByAES(refundApplyVo.getOrderId(), EncryptUtils.getAESKeySelf()));
			}
			
			// 获取上传图片的URL 
			String imgURL = refundApplyVo.getProofImgURL();
			if (StringUtils.isNotBlank(imgURL)) {
				// 图片存储地址不能带域名 此处需要替换
				imgURL = imgURL.replaceAll(domainURL.getShowImgURL(), "");
				refundApplyVo.setProofImgURL(imgURL);
			}
			// 退换货申请
			boolean flag = goodsRefundService.updateRefundItemFlow(refundApplyVo);
			if (flag) {
				result.put("result", true);
				result.put("errorCode", ErrorCode.Success.getErrorCode());
				result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			}
		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("用户退换货更改异常:", e);
		}catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("用户退换货更改异常:", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/***
	 * 退换货申请订单取消
	 * @param request
	 * @param response
	 * @param refundApplyVo 退换货申请信息封装
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/refundCancel", produces = "text/html;charset=UTF-8")
	public String refundCancel(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		LoginResVo user = SessionUtil.getUser(request);
		Map<String, Object> result = new HashMap<String, Object>();
		String id = request.getParameter("id");
		String code = request.getParameter("code");

		try {
			
			// 缺少参数
			if (StringUtils.isBlank(id) || StringUtils.isBlank(code)) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("userId", user.getUserId());
			params.put("username", user.getLogonName());
			params.put("id", request.getParameter("id"));
			params.put("code", request.getParameter("code"));
			String remoteIp = HttpUtil.getRemoteIpAddr(request);
			params.put("ip", remoteIp);

			// 退换货申请取消
			boolean flag = goodsRefundService.cancelRefundItemFlow(params);
			if (flag) {
				result.put("result", true);
				result.put("errorCode", ErrorCode.Success.getErrorCode());
				result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			}
		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("用户退换货取消异常:", e);
		}catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("用户退换货取消异常:", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/***
	 * 记录用户退换货物流信息
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/recordLogistics", produces = "text/html;charset=UTF-8")
	public String recordLogistics(HttpServletRequest request,HttpServletResponse response,RefundLogisticsVo refundLogisticsVo,String jsoncallback){
		LoginResVo user = SessionUtil.getUser(request);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 物流运费默认值
			double postFee = NumberUtils.toDouble(request.getParameter("postFee"), 0);
			refundLogisticsVo.setPostFee(postFee);
			// orderId解密(AES)
			String aesKey=EncryptUtils.getAESKeySelf();
			String orderId=EncryptUtils.decryptOrderIdByAES(refundLogisticsVo.getOrderId(), aesKey);
			refundLogisticsVo.setOrderId(orderId);
			
			// 记录用户退换货物流公司信息
			int number = goodsRefundService.saveRefundItemFlowCompany(refundLogisticsVo);
			if (number>0) {
				result.put("result", true);
				result.put("errorCode", ErrorCode.Success.getErrorCode());
				result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			}
		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("获取用户退换货订单商品详情异常:", e);
		}catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取用户退换货订单商品详情异常:", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/***
	 * 获取用户退换货订单物流详情
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getOrderLogistics", produces = "text/html;charset=UTF-8")
	public String getOrderLogistics(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		LoginResVo user = SessionUtil.getUser(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		int refundFlowId = NumberUtils.toInt(request.getParameter("refundFlowId"), 0);
		try {
			// 退换货物流信息
			RefundLogisticsVo logisticsVo = null;
			// 查询用户订单商品退换货物流信息
			List<RefundItemFlowCompanyDO> refundItemFlowCompanyDOList = goodsRefundService.getRefundLogisticsByRefundFlowId(refundFlowId);
			if (refundItemFlowCompanyDOList!=null && refundItemFlowCompanyDOList.size()>0) {
				logisticsVo = RefundOrderInfoVoConvertor.getRefundLogisticsVo(refundItemFlowCompanyDOList.get(0));
			}
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			// orderId加密(AES)
			String aesKey=EncryptUtils.getAESKeySelf();
			if(logisticsVo != null) {
				String orderId=EncryptUtils.encryptOrderIdByAES(logisticsVo.getOrderId(), aesKey);
				logisticsVo.setOrderId(orderId);
			}
			result.put("logisticsVo", logisticsVo);
		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("获取用户退换货订单物流详情异常:", e);
		}catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取用户退换货订单物流详情异常:", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/***
	 * 获取用户退换货寄回地址
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getReceiveAddress", produces = "text/html;charset=UTF-8")
	public String getReceiveAddress(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", true);
		result.put("errorCode", ErrorCode.Success.getErrorCode());
		result.put("errorMsg", ErrorCode.Success.getErrorMsg());
		
		// 相关邮寄地址信息  暂时固定
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("address", "广东省深圳市宝安区福永镇怀德翠岗工业园六区西部物流信息中心四楼");
		data.put("receiver", "走秀网退货小组");
		data.put("postalCode", "518103");
		data.put("phone", "4008884499");
		result.put("data", data);
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	
	/***
	 * app获取用户退换货订单图片上传
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @param picFile 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/uploadImg", produces = "text/html;charset=UTF-8")
	public String uploadImg(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		LoginResVo user = SessionUtil.getUser(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			// 转型为MultipartHttpRequest：     
	        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;     
	        // 获得文件：     
			MultipartFile patch = multipartRequest.getFile("imgFile");
			// 判断是否获取到文件
			if (patch == null || patch.getSize() <= 0) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg().concat(",没有接收到文件信息"));
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			// 文件大小 不能超过10MB
			if (patch.getSize()>10485760) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg().concat(",文件大小不能超过10MB"));
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			String fileName = patch.getOriginalFilename();
			if (null == fileName || "".equals(fileName)) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			// 文件命名 时分秒毫秒定义成文件名
			fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + fileName.substring(fileName.lastIndexOf("."));
			String imgURL = "/" + user.getUserId()+ "/" + fileName;
			String fullPicFile = domainURL.getUploadURL() + imgURL;
			//如果文件不存在则创建之
			createFile(fullPicFile);
			patch.transferTo(new File(fullPicFile));// 上传图片

			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			// 图片响应路径增加域名路径
			result.put("imgURL", domainURL.getShowImgURL().concat(imgURL));
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("图片上传错误:", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/***
	 * wap获取用户退换货订单图片上传
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @param picFile 
	 * @return
	 */
	//@ResponseBody
	@RequestMapping(value="/uploadImgTwoDomains", produces = "text/html;charset=UTF-8")
	public String uploadImgTwoDomains(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		LoginResVo user = SessionUtil.getUser(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		String toURL = request.getParameter("toURL");
		if(null == toURL || toURL.trim().equals("")){
			toURL = "http://m.xiu.com/myxiu/upload_redirect.html";	
		}
		
		try {
			// 转型为MultipartHttpRequest：     
	        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;     
	        // 获得文件：     
			MultipartFile patch = multipartRequest.getFile("imgFile");
			// 判断是否获取到文件
			if (patch == null || patch.getSize() <= 0) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg().concat(",没有接收到文件信息"));
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			// 文件大小 不能超过10MB
			if (patch.getSize()>10485760) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg().concat(",文件大小不能超过10MB"));
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			String fileName = patch.getOriginalFilename();
			if (null == fileName || "".equals(fileName)) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			// 文件命名 时分秒毫秒定义成文件名
			fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + fileName.substring(fileName.lastIndexOf("."));
			String imgURL = "/" + user.getUserId()+ "/" + fileName;
			String fullPicFile = domainURL.getUploadURL() + imgURL;
			//如果文件不存在则创建之
			createFile(fullPicFile);
			patch.transferTo(new File(fullPicFile));// 上传图片

			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			// 图片响应路径增加域名路径
			result.put("imgURL", domainURL.getShowImgURL().concat(imgURL));
			
			
			StringBuffer respData = new StringBuffer(); 
			respData.append("?result=true");
			respData.append("&errorCode=" +  ErrorCode.Success.getErrorCode());
			respData.append("&errorMsg=" + ErrorCode.Success.getErrorMsg());
			respData.append("&imgURL=" + domainURL.getShowImgURL().concat(imgURL));
			
			try {
				response.sendRedirect(toURL + respData.toString() );
			} catch (IOException e) {
				logger.error("跨域上传重定向失败", e);
			}
			
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("图片上传错误:", e);
		}
		//return JsonUtils.bean2jsonP(jsoncallback, result);
		return null;
	}
	
	/***
	 * 获取用户退换货订单图片删除
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @param picFile 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteImg", produces = "text/html;charset=UTF-8")
	public String deleteImg(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		LoginResVo user = SessionUtil.getUser(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			// 获取图片上传路径
			String imgURL = request.getParameter("imgURL");
			// 删除路径判断
			if (StringUtils.isBlank(imgURL)) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			// 替换图片上传显示路径 换成真实路径
			imgURL = imgURL.replace(domainURL.getShowImgURL(), "");
			imgURL = domainURL.getUploadURL().concat(imgURL);
			// 定义文件流
			File file = new File(imgURL);
			if (file.exists()) {
				file.delete();
			}
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("图片删除错误:", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	
	/**
	 * 如果文件不存在就创建
	 * @param fullFileName
	 */
	public static void createFile(String fullFileName) {
		File picFile = new File(fullFileName);
		String picParentPath = picFile.getParent();
		File picParentFile = new File(picParentPath);
		if (!picParentFile.exists()) {
			picParentFile.mkdirs();
		}
	}
	
	/***
	 * 查询订单商品退换货订单列表
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getOrderRefundList", produces = "text/html;charset=UTF-8")
	public String getOrderRefundList(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		Map<String, Object> result = new HashMap<String, Object>();
		String orderDetailId = request.getParameter("orderDetailId");
		
		try {
			// 缺少参数
			if (StringUtils.isBlank(orderDetailId)) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			// 查询订单商品退换货订单列表
			List<RefundItemFlowDO> list = goodsRefundService.getOrderRefundList(Long.parseLong(orderDetailId));
			List<RefundOrderInfoVo> orderInfoList = RefundOrderInfoVoConvertor.getRefundOrderInfoList(list);
			StringBuffer goodsb=new StringBuffer();
			for (RefundOrderInfoVo refundOrderInfoVo : orderInfoList) {
				// 查询用户订单商品退换货物流信息
				List<RefundItemFlowCompanyDO> refundItemFlowCompanyDOList = goodsRefundService.getRefundLogisticsByRefundFlowId(refundOrderInfoVo.getId());
				// 设置用户订单物流登记状态  1为已登记  0为未登记
				if (refundItemFlowCompanyDOList!=null && refundItemFlowCompanyDOList.size()>0) {
					refundOrderInfoVo.setLogisticsStatus("1");
				}else{
					refundOrderInfoVo.setLogisticsStatus("0");
				}
				// orderId不为空
				if(StringUtils.isNotEmpty(refundOrderInfoVo.getOrderId())){
					// orderId加密(AES)
					String aesKey=EncryptUtils.getAESKeySelf();
					String orderId=EncryptUtils.encryptOrderIdByAES(refundOrderInfoVo.getOrderId(), aesKey);
					refundOrderInfoVo.setOrderId(orderId);
				}
				RefundGoodsInfoVo iteminfo=refundOrderInfoVo.getGoodsInfoVo();
				if(iteminfo!=null){
					if(!goodsb.toString().equals("")){
						goodsb.append(",");
					}
					goodsb.append(ObjectUtil.getLong(iteminfo.getGoodsId(),0l));
				}
			}
			
			//查询商品品牌
			String goodids=goodsb.toString();
			if(!goodids.equals("")){
				List<GoodsVo> goods = topicActivityGoodsService.getBrandNameByGoodIds(goodids.split(","));
				for(RefundOrderInfoVo refundOrderInfoVo :orderInfoList){
					RefundGoodsInfoVo iteminfo=refundOrderInfoVo.getGoodsInfoVo();
					String brandName=iteminfo.getBrandName();
					if(iteminfo!=null){
						for (GoodsVo p:goods) {
							if(iteminfo.getGoodsId().equals(p.getGoodsId())){
								if(p.getBrandEnName()!=null&&!p.getBrandEnName().equals("")){
									brandName=p.getBrandEnName();
								}else if(p.getBrandCNName()!=null&&!p.getBrandCNName().equals("")){
									brandName=p.getBrandCNName();
								}
							}
						}
					}
					iteminfo.setBrandName(brandName);
				}
			}
			
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			result.put("orderInfoList", orderInfoList);
		} catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("获取订单商品退换货列表异常:", e);
		}catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取订单商品退换货列表异常:", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
}
