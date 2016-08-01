package com.xiu.mobile.simple.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.commerce.hessian.model.Product;
import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.simple.common.constants.ErrorCode;
import com.xiu.mobile.simple.common.constants.XiuConstant;
import com.xiu.mobile.simple.common.json.JsonUtils;
import com.xiu.mobile.simple.common.util.ConfigUtil;
import com.xiu.mobile.simple.common.util.SessionUtil;
import com.xiu.mobile.simple.constants.GlobalConstants;
import com.xiu.mobile.simple.facade.utils.HttpUtil;
import com.xiu.mobile.simple.model.BookmarkIitemBo;
import com.xiu.mobile.simple.model.BookmarkIitemListVo;
import com.xiu.mobile.simple.model.BookmarkIitemVo;
import com.xiu.mobile.simple.model.LoginResVo;
import com.xiu.mobile.simple.service.IBookmarkService;
import com.xiu.mobile.simple.service.IGoodsService;
import com.xiu.mobile.simple.service.ILoginService;
import com.xiu.mobile.simple.service.ITopicActivityGoodsService;
 

/**
 * 
 * @author wangchengqun
 * @date 	20140523
 *
 */
@Controller
@RequestMapping("/favor") 
public class BookmarkController extends BaseController {

	private Logger logger = Logger.getLogger(BookmarkController.class);

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
	public String addFavorGoods(HttpServletRequest request,Long goodsId,String terminal,String jsoncallback) {
		// 存储返回结果值
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		Long ter=Long.parseLong("3");
		try {
			// 检查登陆
			if (checkLogin(request)) {
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
			iitemVo.setCatentryId(goodsId);
			iitemVo.setUserId(userId);
			iitemVo.setStoreentId(Integer.parseInt(ConfigUtil.getValue("goods.storeId")));
			iitemVo.setLastupdate(new Date());
			iitemVo.setField2(getGoodsPrice(goodsId));
			iitemVo.setTerminalType(ter);
			Long itemListId=this.getItemListByUserId(userId);
			if(null!=itemListId){
				//存在就获得收藏夹Id，添加收藏的商品信息，修改收藏夹更新时间
				//是否收藏了此商品
				if(!hasExistsFavorGoodsUG(userId,goodsId)){
					iitemVo.setIitemlistId(itemListId);
					int flag = bookmarkService.addIitemVo(iitemVo);
					if (0 == flag) {
						BookmarkIitemListVo iitemList=new BookmarkIitemListVo();
						iitemList.setIitemlistId(itemListId);
						iitemList.setUserId(iitemVo.getUserId());
						iitemList.setLastupdate(new Date());
						int res=bookmarkService.updateIitemListByUserIdAndItemListId(iitemList);
						if(0==res){
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
						resultMap.put("result", true);
						resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
						resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
					} 
				} 
			}
		} else {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
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
			// 检查登陆
			if (checkLogin(request)) {
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
		} else {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
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
	public String getFavorGoodsList(HttpServletRequest request,String jsoncallback,String pageNum){
		int pageCntNum = 1;
		try {
			pageCntNum = Integer.parseInt(pageNum);
		} catch (NumberFormatException e1) {
			logger.info("查询用户收藏的商品信息时页码参数错误，故使用默认第一页：" + e1.getMessage());
		}
		
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			// 检查登陆
			if (checkLogin(request)) {
				//从cookie中获得userId
				LoginResVo loginResVo=SessionUtil.getUser(request);
				Long userId=Long.parseLong(loginResVo.getUserId());
				int lineMin = (pageCntNum - 1) * XiuConstant.TOPIC_PAGE_SIZE + 1;
				int lineMax = pageCntNum * XiuConstant.TOPIC_PAGE_SIZE + 1;
				//分页数据
				HashMap<String, Object> valMap = new HashMap<String, Object>();
				valMap.put("lineMin", lineMin);
				valMap.put("lineMax", lineMax);
				valMap.put("userId", userId);
				List<BookmarkIitemBo> bookmarkList =bookmarkService.getBookmark(valMap);
				
				List<BookmarkIitemVo> bookmarkBoList=new ArrayList<BookmarkIitemVo>();
				if(null!=bookmarkList&&bookmarkList.size()>0){
				int len=bookmarkList.size();
				//查询参数商品Id
				List<Long>  catIds=new ArrayList<Long>();
				for(int i=0;i<len;i++){
					BookmarkIitemVo bookmarkBo=new BookmarkIitemVo();
					bookmarkBo.setGoodsId(bookmarkList.get(i).getCatentryId());
					bookmarkBo.setGoodsPrice(bookmarkList.get(i).getField2());
					bookmarkBoList.add(bookmarkBo);
					catIds.add(bookmarkBo.getGoodsId());
				}
				List<Product> products = topicActivityGoodsService.batchLoadProducts(catIds);
				
				for(BookmarkIitemVo iitem: bookmarkBoList ){
					if(null!=products&&products.size()>0){
						for(Product product:products ){
							if(iitem.getGoodsId().equals(product.getInnerID()))
							{
								iitem.setGoodsName(product.getPrdName());
								iitem.setGoodsSn(product.getXiuSN());
								if (StringUtils.isNotEmpty(product.getImgUrl())) {
									// 目前图片服务器前缀
									int[] arr = { 1, 2, 3 };
									String goodsId =  iitem.getGoodsSn();
									int index = Integer.parseInt(goodsId.substring(goodsId.length()-1, goodsId.length()),16);
									String domain =  arr[index%3] + ".xiustatic.com";
									String imgUrl =  product.getImgUrl() + "/g1_66_88.jpg";
									imgUrl = imgUrl.replaceAll("images.xiu.com", domain);
									iitem.setGoodsImgUrl(imgUrl);
								}
								
							}
						}
					}else{
						iitem.setGoodsImgUrl("");
					}
			     	
				}
				
				//所有数据数量
				int count = bookmarkService.getBookmarkCount(userId);
	
				int pageSize = XiuConstant.TOPIC_PAGE_SIZE;
				int totalPage = (count % pageSize > 0) ? 
						(count / pageSize + 1) : (count / pageSize);
				resultMap.put("result", true);
				resultMap.put("totalPage", totalPage);
				resultMap.put("favorGoodsList", bookmarkBoList);
				}else{
					resultMap.put("result", true);
					resultMap.put("totalPage", 0);
					resultMap.put("favorGoodsList",new ArrayList<BookmarkIitemVo>());
				}
			} else {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
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
			// 检查登陆
			if (checkLogin(request)) {
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
					resultMap.put("result", true);
					resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
				}
				}
		} else {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
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
	/**
	 * 检查登陆
	 * @return
	 */
	private boolean checkLogin(HttpServletRequest request)throws Exception{
		try{
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		String tokenId = SessionUtil.getTokenId(request);
		params.put("tokenId", tokenId);
		params.putAll(getDeviceParams(request));
		// 添加IP信息，登录，获取用户状态等接口需要。
		params.put(GlobalConstants.KEY_REMOTE_IP,HttpUtil.getRemoteIpAddr(request));
		// 检查登陆
		if (!StringUtils.isEmpty(tokenId) && loginService.checkOnlineStatus(params)) {
			return true;
		}else{
			return false;
		}
	}
	catch(EIBusinessException e){
		return false;
	}
	}
	
	public ITopicActivityGoodsService getITopicActivityGoodsService() {
		return topicActivityGoodsService;
	}
	public void setITopicActivityGoodsService(ITopicActivityGoodsService iTopicActivityGoodsService) {
		topicActivityGoodsService = iTopicActivityGoodsService;
	}
	

}
