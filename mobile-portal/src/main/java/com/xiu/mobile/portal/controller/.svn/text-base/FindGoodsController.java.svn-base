package com.xiu.mobile.portal.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.commerce.hessian.model.Product;
import com.xiu.mobile.core.model.FindGoodsVo;
import com.xiu.mobile.core.model.LoveGoodsBo;
import com.xiu.mobile.core.service.IFindGoodsService;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.common.util.UUIDUtil;
import com.xiu.mobile.portal.service.IGoodsService;
import com.xiu.mobile.portal.service.ILoginService;
import com.xiu.mobile.portal.service.IProductService;
import com.xiu.mobile.portal.service.ITopicActivityGoodsService;

/**
 * <p>
 * **************************************************************
 * 
 * @Description:发现商品控制层
 * @AUTHOR wangchengqun
 * @DATE 2014-6-4
 ****************************************************************
 *</p>
 */
@Controller
@RequestMapping("/findGoods") 
public class FindGoodsController extends BaseController{
	private final Logger logger = Logger.getLogger(FindGoodsController.class);
	// 点赞用户默认设备Id
	private final String DEFAULT_DEVICEID = "default-deviceId";

	@Autowired
	private IFindGoodsService findGoodsService;
	@Autowired
	private IProductService productService;
	@Autowired
	private ITopicActivityGoodsService topicActivityGoodsService;
	@Autowired
	private IGoodsService goodsManager;
	@Autowired
	private ILoginService loginService;
	/**
	 * 分页查询发现商品列表
	 * @param request
	 * @param jsoncallback
	 * @param pageNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getFindGoodsList", produces = "text/html;charset=UTF-8")
	public String getFindGoodsList(HttpServletRequest request,String jsoncallback,String pageNum,HttpServletResponse response){
		int pageCntNum = 1;
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			pageCntNum = Integer.parseInt(pageNum);
		} catch (NumberFormatException e1) {
			logger.error("查询发现商品信息时页码参数错误，故使用默认第一页：" + e1.getMessage());
		} 
		try {
			int lineMin = (pageCntNum - 1) * XiuConstant.TOPIC_PAGE_SIZE + 1;
			int lineMax = pageCntNum * XiuConstant.TOPIC_PAGE_SIZE + 1;
			//分页数据
			HashMap<String, Object> valMap = new HashMap<String, Object>();
			valMap.put("lineMin", lineMin);
			valMap.put("lineMax", lineMax);
			List<FindGoodsVo> findGoodsList = findGoodsService.getFindGoodsList(valMap);
			String goodsSns="";
			// 获取设备相关数据信息
			String deviceId = request.getParameter("deviceId");
			// 如果设备信息为空  则取cookie中uuid的值
			if (StringUtils.isBlank(deviceId)) {
				deviceId = SessionUtil.getPraiseDeviceId(request);
			}
			// 如果cookie中uuid为空  则取登录用户Id
			if (StringUtils.isBlank(deviceId)) {
				deviceId = SessionUtil.getUserId(request);
			}
			if(null!= findGoodsList && findGoodsList.size()>0){
				
				// =======================封装产品价格、是否点赞信息=============================//
				// 商品SN相关集合信息
				List<String> goodsSNList = new ArrayList<String>();
				for (int i = 0; i < findGoodsList.size(); i++) {
					FindGoodsVo findGoodsVo = findGoodsList.get(i);
					// 如果都为空
					if (StringUtils.isBlank(deviceId)) {
						// 商品设置为未点赞
						findGoodsVo.setIsLove("N");
						// 使用UUID为设备Id并存入cookie
						deviceId = UUIDUtil.generateUUID();
						SessionUtil.addGoodPraiseCookie(response, deviceId);
					}else{
						// 检测当前商品是否已点赞
						if(hasLovedTheGoodsUG(deviceId,findGoodsVo.getGoodsSn())){
							findGoodsVo.setIsLove("Y");
						}else{
							findGoodsVo.setIsLove("N");
						}
					}
					// 商品sn码组合
					goodsSns+= findGoodsVo.getGoodsSn()+",";
					goodsSNList.add(findGoodsVo.getGoodsSn());
				}
				
				// ===========================封装产品图片相关信息==============================/
				List<Product> products = topicActivityGoodsService.batchLoadProducts(goodsSns);
				for(FindGoodsVo iitem: findGoodsList ){
					if(null!=products&&products.size()>0){
				     	for(Product product:products ){
							if(iitem.getGoodsSn().equals(product.getXiuSN())){
								if (StringUtils.isNotEmpty(product.getImgUrl())) {
									// 目前图片服务器前缀
									String goodsId =  iitem.getGoodsSn();
									int index = Integer.parseInt(goodsId.substring(goodsId.length()-1, goodsId.length()),16);
									String domain =  XiuConstant.XIUSTATIC_NUMS[index%3] + ".xiustatic.com";
									String imgUrl =  product.getImgUrl() + "/g1_162_216.jpg";
									imgUrl = imgUrl.replaceAll("images.xiu.com", domain);
									iitem.setGoodsImg(imgUrl);
									iitem.setGoodsId(product.getInnerID());
								}	
								
								// 获取商品价格
								Double price=goodsManager.getZxPrice(product);
								iitem.setGoodsPrice(price==null?0.00:price);
								//商品上下架状态
								int stock=Integer.parseInt(product.getOnSale());
								iitem.setStock(stock);
							}
						}
					}else{
						iitem.setGoodsImg("");
						iitem.setGoodsId(Long.parseLong("0"));
						iitem.setGoodsPrice(0.00);
					}
				}
				
				// =======================封装库存相关产品信息============================//
				// 查询产品sn对应的库存数量
			/*	Map<String, Integer> stockMap = productService.queryInventoryByCodeList(goodsSNList);
				for (FindGoodsVo findGoodsVo : findGoodsList) {
					String goodsSn = findGoodsVo.getGoodsSn();
					if (StringUtils.isNotBlank(goodsSn) && stockMap.containsKey(goodsSn)) {
						int stock = stockMap.get(goodsSn);
						findGoodsVo.setStock(stock);
					}
				}*/
			
				//总数
				int count=findGoodsService.getFindGoodsListCnt();
				int pageSize = XiuConstant.TOPIC_PAGE_SIZE;
				int totalPage = (count % pageSize > 0) ? (count / pageSize + 1) : (count / pageSize);
				resultMap.put("result", true);
				resultMap.put("totalPage", totalPage);
				resultMap.put("findGoodsList", findGoodsList);
			}else{
				resultMap.put("result", true);
				resultMap.put("totalPage", 0);
				resultMap.put("findGoodsList",new ArrayList<FindGoodsVo>());
			}
		}catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("分页查询发现商品列表时发生异常：" + e.getMessage());
		}

		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		
	}
	/**
	 * 查询某发现商品有多少人喜欢
	 * @param goodsSn
	 * @return
	 * @throws Exception
	 */

	private int getLovedCountofGoods(String goodsSn)throws Exception{
		return findGoodsService.getLovedCountofGoods(goodsSn);
	}
	/**
	 * 检查登陆用户是否添加了喜爱商品
	 * @param userId
	 * @param goodsSn
	 * @return
	 * @throws Exception
	 */
	private boolean hasLovedTheGoodsUG(Long userId,String goodsSn) throws Exception{
		HashMap<String, Object> valMap=new HashMap<String, Object>();
		valMap.put("userId", userId);
		valMap.put("goodsSn", goodsSn);
		List<LoveGoodsBo> itemList=findGoodsService.getLoveGoodsList(valMap);
		if(null!=itemList&&itemList.size()>0){
			return true;
			}else{
			return false;
		}
	}
	
	/**
	 * 检查登陆用户是否添加了喜爱商品
	 * @param deviceId
	 * @param goodsSn
	 * @return
	 * @throws Exception
	 */
	private boolean hasLovedTheGoodsUG(String deviceId,String goodsSn) throws Exception{
		HashMap<String, Object> valMap=new HashMap<String, Object>();
		valMap.put("deviceId", deviceId);
		valMap.put("goodsSn", goodsSn);
		List<LoveGoodsBo> itemList=findGoodsService.getLoveGoodsList(valMap);
		if(null!=itemList&&itemList.size()>0){
			return true;
			}else{
			return false;
		}
	}
	/**
	 * 检查登陆用户是否添加了喜爱商品
	 * @param request
	 * @param goodsSn
	 * @param jsoncallback
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping(value="hasLovedTheGoods", produces = "text/html;charset=UTF-8")
	public String hasLovedTheGoods(HttpServletRequest request,String goodsSn,String jsoncallback){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			// 检查登陆
			if (checkLogin(request)) {
				//从cookie中获得userId
				LoginResVo loginResVo=SessionUtil.getUser(request);
				Long userId=Long.parseLong(loginResVo.getUserId());
				if(hasLovedTheGoodsUG(userId,goodsSn)){
					resultMap.put("result", true);
					resultMap.put("errorCode", ErrorCode.WithAddLoveGoods.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.WithAddLoveGoods.getErrorMsg());
					}else{
					resultMap.put("result", false);
					resultMap.put("errorCode", ErrorCode.WithoutAddLoveGoods.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.WithoutAddLoveGoods.getErrorMsg());
				
				}
			} else {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
			}
		}catch (Exception e) {
		resultMap.put("result", false);
		resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
		resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		logger.error("检查登陆用户是否添加了喜爱商品时发生异常：" + e.getMessage());
	}
	
	return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}*/
	/**
	 * 用户取消点赞（发现商品）
	 * @param request
	 * @param goodsSn
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="delLovedTheGoods", produces = "text/html;charset=UTF-8")
	public String delLovedTheGoods(HttpServletRequest request,String goodsSn,String jsoncallback){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		// 获取设备相关数据信息
		String deviceId = request.getParameter("deviceId");
		// 如果设备信息为空  则取cookie中uuid的值
		if (StringUtils.isBlank(deviceId)) {
			deviceId = SessionUtil.getPraiseDeviceId(request);
		}
		// 如果cookie中uuid为空  则取登录用户Id
		if (StringUtils.isBlank(deviceId)) {
			deviceId = SessionUtil.getUserId(request);
		}
		// 如果三个值都为空  取默认设备值
		if (StringUtils.isBlank(deviceId)) {
			deviceId = DEFAULT_DEVICEID;
		}
		
		try {
			// 如果没有设备Id 直接报错
			if (StringUtils.isNotBlank(deviceId)) {
				if(hasLovedTheGoodsUG(deviceId,goodsSn)){
					HashMap<String, Object> valMap=new HashMap<String, Object>();
					valMap.put("deviceId", deviceId);
					valMap.put("goodsSn", goodsSn);
					int resCnt=findGoodsService.delLovedTheGoods(valMap);
					if(0==resCnt){
						resultMap.put("result", true);
						resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
						resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
					}
				}else{
					resultMap.put("result", false);
					resultMap.put("errorCode", ErrorCode.WithoutAddLoveGoods.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.WithoutAddLoveGoods.getErrorMsg());
				}
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			}
		}catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("用户取消点赞发现商品时发生异常：" + e.getMessage());
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	  * 用户点赞发现商品
	 * @param request
	 * @param goodsSn
	 * @param terminal
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="addLovedTheGoods", produces = "text/html;charset=UTF-8")
	public String addLovedTheGoods(HttpServletRequest request,String goodsSn,String terminal,String jsoncallback){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		// 获取设备相关数据信息
		String deviceId = request.getParameter("deviceId");
		// 如果设备信息为空  则取cookie中uuid的值
		if (StringUtils.isBlank(deviceId)) {
			deviceId = SessionUtil.getPraiseDeviceId(request);
		}
		// 如果cookie中uuid为空  则取登录用户Id
		if (StringUtils.isBlank(deviceId)) {
			deviceId = SessionUtil.getUserId(request);
		}
		// 如果三个值都为空  取默认设备值
		if (StringUtils.isBlank(deviceId)) {
			deviceId = DEFAULT_DEVICEID;
		}
		
		try {
			// 如果没有设备相关信息
			if (StringUtils.isNotBlank(deviceId)) {
				int ter=3;
				if(null==terminal||"".equals(terminal)){
					ter=3;
				}else{
					ter=Integer.parseInt(terminal);
				}
				if(!hasLovedTheGoodsUG(deviceId,goodsSn)){
					LoveGoodsBo loveGoods=new LoveGoodsBo();
					loveGoods.setGoodsSn(goodsSn);
					loveGoods.setUserId(null);
					loveGoods.setCreateDate(new Date());
					loveGoods.setTerminal(ter);
					loveGoods.setDeviceId(deviceId);
					int resCnt=findGoodsService.addLovedTheGoods(loveGoods);
					if(0==resCnt){
						resultMap.put("result", true);
						resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
						resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
					}
				}else{
					resultMap.put("result", false);
					resultMap.put("errorCode", ErrorCode.WithAddLoveGoods.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.WithAddLoveGoods.getErrorMsg());
				}
			}else {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			}
		}catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("用户点赞发现商品时发生异常：" + e.getMessage());
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

}
