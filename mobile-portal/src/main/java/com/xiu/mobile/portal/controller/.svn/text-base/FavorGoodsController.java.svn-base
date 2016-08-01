package com.xiu.mobile.portal.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.commerce.hessian.model.Product;
import com.xiu.common.lang.StringUtil;
import com.xiu.mobile.core.utils.XiuUtil;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.ConfigUtil;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.model.BookmarkIitemBo;
import com.xiu.mobile.portal.model.BookmarkIitemListVo;
import com.xiu.mobile.portal.model.BookmarkIitemVo;
import com.xiu.mobile.portal.model.LoginResVo;
import com.xiu.mobile.portal.model.MktActInfoVo;
import com.xiu.mobile.portal.service.IBookmarkService;
import com.xiu.mobile.portal.service.IGoodsService;
import com.xiu.mobile.portal.service.ILoginService;
import com.xiu.mobile.portal.service.ITopicActivityGoodsService;
import com.xiu.sales.common.itemsettle.dataobject.ItemSettleActivityResultDO;
import com.xiu.sales.common.itemsettle.dataobject.ItemSettleResultDO;
 
/**
 * 
 * @author wangchengqun
 * @date 	20140523
 *
 */
@Controller
@RequestMapping("/favor") 
public class FavorGoodsController extends BaseController {

	private Logger logger = Logger.getLogger(FavorGoodsController.class);

	@Autowired
	private IBookmarkService bookmarkService;
	@Autowired
	private IGoodsService goodsManager;
	@Autowired
	private ITopicActivityGoodsService topicActivityGoodsService;
	@Autowired
	private ILoginService loginService;
	/**
	 * 
	 *添加收藏
	 *@param goodsId 商品Id
	 *@param jsoncallback
	 */
	@ResponseBody
	@RequestMapping(value = "/addFavorGoods", produces = "text/html;charset=UTF-8")
	public String addFavorGoods(HttpServletRequest request,String goodsId,String terminal,String jsoncallback) {
		// 存储返回结果值
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		
		//检测参数
		if(StringUtils.isBlank(goodsId)) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		Long ter=Long.parseLong("3");
		try {
			//查询该用户是否有收藏夹
			//从cookie中获得userId
			LoginResVo loginResVo=SessionUtil.getUser(request);
			Long userId=Long.parseLong(loginResVo.getUserId());
			if(null==terminal||"".equals(terminal)){
				ter=Long.parseLong("3");
			}else{
				ter=Long.parseLong(terminal);
			}
			BookmarkIitemBo iitemVo=new BookmarkIitemBo();
			iitemVo.setCatentryId(Long.parseLong(goodsId));
			iitemVo.setUserId(userId);
			iitemVo.setStoreentId(Integer.parseInt(ConfigUtil.getValue("goods.storeId")));
			iitemVo.setLastupdate(new Date());
			iitemVo.setField2(getGoodsPrice(Long.parseLong(goodsId)));
			iitemVo.setTerminalType(ter);
			Long itemListId=this.getItemListByUserId(userId);
			if(null!=itemListId){
				//存在就获得收藏夹Id，添加收藏的商品信息，修改收藏夹更新时间
				//是否收藏了此商品
				if(!hasExistsFavorGoodsUG(userId,Long.parseLong(goodsId))){
					iitemVo.setIitemlistId(itemListId);
					int flag = bookmarkService.addIitemVo(iitemVo);
					if (0 == flag) {
						BookmarkIitemListVo iitemList=new BookmarkIitemListVo();
						iitemList.setIitemlistId(itemListId);
						iitemList.setUserId(iitemVo.getUserId());
						iitemList.setLastupdate(new Date());
						int res=bookmarkService.updateIitemListByUserIdAndItemListId(iitemList);
						if(0==res){
//							//返回商品收藏数量
//							int favorCounts = bookmarkService.getGoodsFavorCounts(goodsId);
//							if(favorCounts < 1000) {
//								resultMap.put("favorCounts", String.valueOf(favorCounts));
//							} else {
//								double hundred = Math.floor(favorCounts/100);
//								String favorCountsStr = String.valueOf(hundred);
//								if(hundred%10 == 0) {
//									resultMap.put("favorCounts", favorCountsStr.substring(0, 1)+"k");
//								} else {
//									resultMap.put("favorCounts", favorCountsStr.substring(0, 1)+"."+favorCountsStr.substring(1, 2)+"k");
//								}
//							}
							
							resultMap.put("result", true);
							resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
							resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
						} 
					} 
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.CheckAddIitemVoFailed.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.CheckAddIitemVoFailed.getErrorMsg());
			}
			
			}else{
				//不存在就为用户新建收藏夹，获得收藏夹Id，添加收藏商品信息
				BookmarkIitemListVo iitemList=new BookmarkIitemListVo();
				iitemList.setUserId(userId);
				iitemList.setLastupdate(new Date());
				iitemList.setDescription("Favor");
				int flag = bookmarkService.addIitemListVo(iitemList);
				if (0 == flag) {
					Long newItemListId=this.getItemListByUserId(userId);
					iitemVo.setIitemlistId(newItemListId);
					int res = bookmarkService.addIitemVo(iitemVo);
					if(0==res){
//						//返回商品收藏数量
//						int favorCounts = bookmarkService.getGoodsFavorCounts(goodsId);
//						if(favorCounts < 1000) {
//							resultMap.put("favorCounts", String.valueOf(favorCounts));
//						} else {
//							double hundred = Math.floor(favorCounts/100);
//							String favorCountsStr = String.valueOf(hundred);
//							if(hundred%10 == 0) {
//								resultMap.put("favorCounts", favorCountsStr.substring(0, 1)+"k");
//							} else {
//								resultMap.put("favorCounts", favorCountsStr.substring(0, 1)+"."+favorCountsStr.substring(1, 2)+"k");
//							}
//						}
						
						resultMap.put("result", true);
						resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
						resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
					} 
				} 
			}
			
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("添加收藏商品时发生异常：" + e.getMessage());
		}

		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	 * 获得用户最新的iitemListId
	 * @param userId
	 * @return
	 */
	private Long getItemListByUserId(Long userId)throws Exception{
		List<BookmarkIitemListVo> itemListVoList=bookmarkService.getItemListByUserId(userId);
		if(null!=itemListVoList&&itemListVoList.size()>0){
			Long itemListId=itemListVoList.get(0).getIitemlistId();
			return itemListId;
		}else{
			return null;
		}
		 
	}
	/**
	 * 检查是否已收藏了商品
	 * @param userId
	 * @param goodsId
	 * @return
	 */
	private boolean hasExistsFavorGoodsUG(Long userId,Long goodsId)throws Exception{
		HashMap<String, Object> valMap=new HashMap<String, Object>();
		valMap.put("userId", userId);
		valMap.put("catentryId", goodsId);
		List<BookmarkIitemBo> itemList=bookmarkService.getItemByUserIdAndCatentryId(valMap);
		if(null!=itemList&&itemList.size()>0){
			return true;
		}else{
			return false;
		}
		
	}
	
	
	/**
	 * 检查用户是否已收藏了某商品
	 * @param userId
	 * @param goodsId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/hasExistsFavorGoods", produces = "text/html;charset=UTF-8")
	public String hasExistsFavorGoods(HttpServletRequest request, Long goodsId,String jsoncallback){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try{
			//从cookie中获得userId
			LoginResVo loginResVo=SessionUtil.getUser(request);
			Long userId=Long.parseLong(loginResVo.getUserId());
			boolean flag=this.hasExistsFavorGoodsUG(userId,goodsId);
			if(flag){
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.CheckAddIitemVoFailed.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.CheckAddIitemVoFailed.getErrorMsg());
			
				}else{
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.WithoutAddIitemVo.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.WithoutAddIitemVo.getErrorMsg());
			}
		}
		catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("检查用户已收藏了商品时发生异常:"+e.getMessage());
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}	
	
	/**
	 * 查询用户收藏的商品信息
	 * @param request
	 * @param jsoncallback
	 * @param pageNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getFavorGoodsList", produces = "text/html;charset=UTF-8")
	public String getFavorGoodsList(HttpServletRequest request, String jsoncallback, String pageNum) {
		int pageCntNum = 1;
		try {
			pageCntNum = Integer.parseInt(pageNum);
		} catch (NumberFormatException e1) {
			logger.info("查询用户收藏的商品信息时页码参数错误，故使用默认第一页：" + e1.getMessage());
		}

		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			// 从cookie中获得userId
			LoginResVo loginResVo = SessionUtil.getUser(request);
			Long userId = Long.parseLong(loginResVo.getUserId());
			int pageSize = NumberUtils.toInt(request.getParameter("pageSize"), XiuConstant.PAGE_SIZE_10);
			int lineMin = (pageCntNum - 1) * pageSize + 1;
			int lineMax = pageCntNum * pageSize + 1;
			// 分页数据
			HashMap<String, Object> valMap = new HashMap<String, Object>();
			valMap.put("lineMin", lineMin);
			valMap.put("lineMax", lineMax);
			valMap.put("userId", userId);
			List<BookmarkIitemBo> bookmarkList = bookmarkService.getBookmark(valMap);

			List<BookmarkIitemVo> bookmarkBoList = new ArrayList<BookmarkIitemVo>();
			if (null != bookmarkList && bookmarkList.size() > 0) {
				int len = bookmarkList.size();
				// 查询参数商品Id
				List<Long> catIds = new ArrayList<Long>();
				for (int i = 0; i < len; i++) {
					BookmarkIitemBo oldbo=bookmarkList.get(i);
					BookmarkIitemVo bookmarkVo = new BookmarkIitemVo();
					bookmarkVo.setGoodsId(oldbo.getCatentryId());
					bookmarkVo.setGoodsPrice(getGoodsPrice(bookmarkList.get(i).getCatentryId()));//获取最新价格
					//降价信息处理
					if(oldbo.getField2()!=null&&bookmarkVo.getGoodsPrice()!=null&&oldbo.getField2()>bookmarkVo.getGoodsPrice()){
						bookmarkVo.setCutPricesMsg("比关注时降"+(XiuUtil.getPriceDouble2Str(bookmarkList.get(i).getField2()-bookmarkVo.getGoodsPrice()))+"元");
					}
					bookmarkBoList.add(bookmarkVo);
					catIds.add(bookmarkVo.getGoodsId());
				}
				//获取产品最新信息
				List<Product> products = topicActivityGoodsService.batchLoadProducts(catIds);
				//获取产品促销信息
				List<ItemSettleResultDO> itemSettleResultDOs=topicActivityGoodsService.itemListSettle(products);
				//对收藏商品做最新信息处理
				for (BookmarkIitemVo iitem : bookmarkBoList) {
					if (null != products && products.size() > 0) {
						for (Product product : products) {
							if (iitem.getGoodsId().equals(product.getInnerID())) {
								iitem.setGoodsName(product.getPrdName());
								iitem.setGoodsSn(product.getXiuSN());
								iitem.setBrandName(product.getBrandName());
								if (StringUtils.isNotEmpty(product.getImgUrl())) {
									// 目前图片服务器前缀
									String goodsId = iitem.getGoodsSn();
									int index = Integer.parseInt(goodsId.substring(goodsId.length() - 1, goodsId.length()), 16);
									String domain = XiuConstant.XIUSTATIC_NUMS[index % 3] + ".xiustatic.com";
									String imgUrl = product.getImgUrl() + "/g1_180_240.jpg"; 
									imgUrl = imgUrl.replaceAll("images.xiu.com", domain);
									iitem.setGoodsImgUrl(imgUrl);
								}
//								if(iitem.getGoodsPrice()==null){//如果为空则获取最新的价格
//									Double price=	getGoodsPrice(iitem.getGoodsId());
//									iitem.setGoodsPrice(price);
//								}
								
								// 商品市场价
								if (product.getPrdListPrice() == null || product.getPrdListPrice().doubleValue() == 0.0) {
									iitem.setPrice(0.00);
								} else {
									iitem.setPrice(product.getPrdListPrice());
								}
								// 商品是否售罄
								int stock = Integer.parseInt(product.getOnSale());
								iitem.setStock(stock);

							}
						}
						//设置促销信息
						if(itemSettleResultDOs!=null&&itemSettleResultDOs.size()>0){
							for (ItemSettleResultDO itemSettle:itemSettleResultDOs) {
								if(itemSettle.getGoodsSn().equals(iitem.getGoodsSn())){
									List<MktActInfoVo> mktActInfoList=new ArrayList<MktActInfoVo>();
									 List<ItemSettleActivityResultDO> activityList= itemSettle.getActivityList();
									 if(activityList!=null&&activityList.size()>0){
										 for (ItemSettleActivityResultDO activity:activityList) {
											   MktActInfoVo mktActInfo=new MktActInfoVo();
												mktActInfo.setActivityId(activity.getActivityId());
												mktActInfo.setActivityName(activity.getActivityName());
												mktActInfo.setActivityType(activity.getActivityType());
												mktActInfoList.add(mktActInfo);
										}
									 }
									iitem.setActivityItemList(mktActInfoList);
								}
							}
						}
						
					} else {
						iitem.setGoodsImgUrl("");
					}

				}

				// 所有数据数量
				int count = bookmarkService.getBookmarkCount(userId);

				int totalPage = (count % pageSize > 0) ? (count / pageSize + 1) : (count / pageSize);
				resultMap.put("result", true);
				resultMap.put("totalPage", totalPage);
				resultMap.put("favorGoodsList", bookmarkBoList);
			} else {
				resultMap.put("result", true);
				resultMap.put("totalPage", 0);
				resultMap.put("favorGoodsList", new ArrayList<BookmarkIitemVo>());
			}
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询用户收藏的商品信息时发生异常：" + e.getMessage());
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 删除收藏夹中的某条收藏信息
	 * @param jsoncallback
	 */
	@ResponseBody
	@RequestMapping(value="delFavorGoods", produces = "text/html;charset=UTF-8")
	public String delFavorGoods(HttpServletRequest request,Long goodsId,String jsoncallback){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			//从cookie中获得userId
			LoginResVo loginResVo=SessionUtil.getUser(request);
			Long userId=Long.parseLong(loginResVo.getUserId());
			boolean flag = this.hasExistsFavorGoodsUG(userId, goodsId);
			if (!flag) {
				resultMap.put("result", false);
				resultMap.put("errorCode",
						ErrorCode.WithoutAddIitemVo.getErrorCode());
				resultMap.put("errorMsg",
						ErrorCode.WithoutAddIitemVo.getErrorMsg());
			} else {
			HashMap<String, Object> valMap=new HashMap<String, Object>();
			valMap.put("userId", userId);
			valMap.put("catentryId", goodsId);
			int resCnt=bookmarkService.deleteBookmark(valMap);
			if(0==resCnt){
//				//返回商品收藏数量
//				int favorCounts = bookmarkService.getGoodsFavorCounts(String.valueOf(goodsId));
//				if(favorCounts < 1000) {
//					resultMap.put("favorCounts", String.valueOf(favorCounts));
//				} else {
//					double hundred = Math.floor(favorCounts/100);
//					String favorCountsStr = String.valueOf(hundred);
//					if(hundred%10 == 0) {
//						resultMap.put("favorCounts", favorCountsStr.substring(0, 1)+"k");
//					} else {
//						resultMap.put("favorCounts", favorCountsStr.substring(0, 1)+"."+favorCountsStr.substring(1, 2)+"k");
//					}
//				}
				
				//删除商品降价信息
				bookmarkService.deleteGoodsReducedPriceInfo(String.valueOf(goodsId));
				
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}
		}
	} catch (Exception e) {
		resultMap.put("result", false);
		resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
		resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		logger.error("删除收藏夹中的某条收藏信息时发生异常：" + e.getMessage());
	}

	return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	 * 获得商品最终售价
	 * 
	 */
	private Double getGoodsPrice(Long goodsId)throws Exception {
		Double price=0.00;
		String goodsSn=bookmarkService.getGoodsSnByCatentryId(goodsId);
		if(StringUtils.isNotBlank(goodsSn)){
			price=goodsManager.getZxPrice(goodsSn) ;
			return price;
		}else{
			logger.info("获得商品最终售价时异常"+goodsId);
			return null;
		} 
	}

	
	public ITopicActivityGoodsService getITopicActivityGoodsService() {
		return topicActivityGoodsService;
	}
	public void setITopicActivityGoodsService(ITopicActivityGoodsService iTopicActivityGoodsService) {
		topicActivityGoodsService = iTopicActivityGoodsService;
	}
	
	/**
	 * 批量检测商品是否收藏
	 * @param goodsIds
	 * @param jsoncallback
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "hasExistsFavorGoodsBatch", produces = "text/html;charset=UTF-8")
	public String getGoodsDetail(String goodsIds, String jsoncallback, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if(StringUtils.isBlank(goodsIds)) {
			//如果商品ID为空
			resultMap.put("result", true);
			resultMap.put("goodsList", new HashMap());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		
		try {
			String userId = SessionUtil.getUserId(request);
			//检测是否登陆
			if(StringUtils.isBlank(userId)) {
				List resultList = new ArrayList();
				String[] goodsIdArr = goodsIds.split(",");
				for(String goodsId : goodsIdArr) {
					Map goodsMap = new HashMap();
					goodsMap.put("goodsId", goodsId);
					goodsMap.put("favorGoods", false);
					resultList.add(goodsMap);
				}
				resultMap.put("result", true);
				resultMap.put("goodsList", resultList);
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
			}
			
			Map<String,Object> paraMap = new HashMap<String,Object>();
			paraMap.put("userId", userId);
			paraMap.put("goodsIdArr", goodsIds.split(","));
			
			//批量检车商品是否收藏
			List goodsList = bookmarkService.hasExistsFavorGoodsBatch(paraMap); 
			resultMap.put("result", true);
			resultMap.put("goodsList", goodsList);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("批量检测商品是否收藏时发生异常：", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	
	
	/**
	 * 批量删除收藏夹中的收藏信息
	 * @param jsoncallback
	 */
	@ResponseBody
	@RequestMapping(value="delBatchFavorGoods", produces = "text/html;charset=UTF-8")
	public String delBatchFavorGoods(HttpServletRequest request,String goodsIds,String jsoncallback){
			Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
			try {
				//从cookie中获得userId
				LoginResVo loginResVo=SessionUtil.getUser(request);
				Long userId=Long.parseLong(loginResVo.getUserId());
				
				if(StringUtil.isBlank(goodsIds)){
					resultMap.put("result", false);
					resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
					return JsonUtils.bean2jsonP(jsoncallback, resultMap);
				}
				
				HashMap<String, Object> valMap=new HashMap<String, Object>();
				valMap.put("userId", userId);
				String[] strIds = goodsIds.split(",");
				List<Long> idsL = new ArrayList<Long>();
				if(strIds != null){
					for(String s : strIds){
						if(!StringUtil.isEmpty(s))
							idsL.add(Long.parseLong(s));
					}
				}
				valMap.put("goodsIdArr", strIds);
				int resCnt=bookmarkService.deleteBatchBookmark(valMap);
				if(0==resCnt){
					resultMap.put("result", true);
					resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
				}
			
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("批量删除收藏夹中的某条收藏信息时发生异常：" + e.getMessage());
		}
	
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	
	/**
	 * 删除收藏夹中售罄的商品
	 * @param jsoncallback
	 */
	@ResponseBody
	@RequestMapping(value="delAllSoldOutFavorGoods", produces = "text/html;charset=UTF-8")
	public String delAllSoldOutFavorGoods(HttpServletRequest request,String jsoncallback){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		int deleteNum=0;//清空售罄个数
		try {
			//从cookie中获得userId
			LoginResVo loginResVo=SessionUtil.getUser(request);
			Long userId=Long.parseLong(loginResVo.getUserId());
			
			HashMap<String, Object> valMap=new HashMap<String, Object>();
			
			// 所有数据数量
			int count = bookmarkService.getBookmarkCount(userId);
			
			int pageSize = 100;//每页100查询
			int pageTotal=count/100+1;
			int pageCntNum=1;
			for (int i = 0; i < pageTotal; i++) {
				pageCntNum=i+1;
				int lineMin = (pageCntNum - 1) * pageSize + 1;
				int lineMax = pageCntNum * pageSize + 1;
				// 分页数据
				valMap.put("lineMin", lineMin);
				valMap.put("lineMax", lineMax);
				valMap.put("userId", userId);
				List<BookmarkIitemBo> bookmarkList = bookmarkService.getBookmark(valMap);

				if (null != bookmarkList && bookmarkList.size() > 0) {
					int len = bookmarkList.size();
					// 查询参数商品Id
					List<Long> catIds = new ArrayList<Long>();
					for (int j = 0; j < len; j++) {
						catIds.add(bookmarkList.get(j).getCatentryId());
					}
					//获取产品最新信息
					List<Product> products = topicActivityGoodsService.batchLoadProducts(catIds);
					
					List<Long> saleOutCatIds = new ArrayList<Long>();
					//对收藏商品做最新信息处理
					for (Long catId : catIds) {
						if (null != products && products.size() > 0) {
							for (Product product : products) {
								if (catId.equals(product.getInnerID())) {
									// 商品是否售罄
									int stock = Integer.parseInt(product.getOnSale());
									if(stock==0){
										saleOutCatIds.add(catId);
										deleteNum++;
									}
								}
							}
						} 
					}
				    if(saleOutCatIds.size()>0){
				    	valMap.put("goodsIdArr", saleOutCatIds);
				    	int resCnt=bookmarkService.deleteBatchBookmark(valMap);
				    }
			      }
			}
			if(deleteNum>0){
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.FavorNotSoldOut.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.FavorNotSoldOut.getErrorMsg());
			}
			
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("删除收藏夹中的某条收藏信息时发生异常：" + e.getMessage());
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
}
