package com.xiu.mobile.simple.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.core.exception.BusinessException;
import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.simple.common.constants.ErrorCode;
import com.xiu.mobile.simple.common.constants.XiuConstant;
import com.xiu.mobile.simple.common.json.JsonUtils;
import com.xiu.mobile.simple.common.util.HttpClientUtil;
import com.xiu.mobile.simple.common.util.SessionUtil;
import com.xiu.mobile.simple.constants.GlobalConstants;
import com.xiu.mobile.simple.facade.utils.HttpUtil;
import com.xiu.mobile.simple.model.ActivityGoodsBo;
import com.xiu.mobile.simple.model.BookmarkIitemBo;
import com.xiu.mobile.simple.model.CommentInfo;
import com.xiu.mobile.simple.model.FlexibleItem;
import com.xiu.mobile.simple.model.GoodsDetailVo;
import com.xiu.mobile.simple.model.GoodsVo;
import com.xiu.mobile.simple.model.LoginResVo;
import com.xiu.mobile.simple.model.QueryActivityVo;
import com.xiu.mobile.simple.service.IBookmarkService;
import com.xiu.mobile.simple.service.IGoodsService;
import com.xiu.mobile.simple.service.ILoginService;
import com.xiu.mobile.simple.service.ITopicActivityGoodsService;
import com.xiu.mobile.simple.url.DomainURL;

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
@RequestMapping("/product")
public class ProductController extends BaseController {	
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private IGoodsService goodsManager;
	@Autowired
	private ITopicActivityGoodsService topicActivityGoodsService;
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IBookmarkService bookmarkService;
	@Autowired
	private DomainURL domainURL;
	
	/**
	 * 获取专题商品列表
	 * @return
	 */
	@RequestMapping(value = "/good-list")
	public String getTopicGoodsList(Model model,HttpServletRequest request,HttpServletResponse response) {
		// 获取相关查询参数
		String activityId = request.getParameter("activityId");
		String subType = request.getParameter("subType");
		// subType 当前选择分类 热卖或全部  默认全部
		subType = StringUtils.isEmpty(subType)?"全部":subType;
		String order = request.getParameter("order");
		// order 1：价格升序、2.价格降序、3：折扣升序、4.折扣降序   默认价格降序
		order = StringUtils.isEmpty(order)?"2":order;
		// 当前页数  默认为1
		int pageNo = NumberUtils.toInt(request.getParameter("pageNo"),1);
		// 封装相关查询参数
		QueryActivityVo queryAVo = new QueryActivityVo();
		queryAVo.setActivityId(activityId);
		queryAVo.setSubType(subType);
		queryAVo.setOrder(order);
		queryAVo.setPageNum(pageNo);
		
		try {			
			ActivityGoodsBo res = topicActivityGoodsService.queryTopicActivityGoodsList(queryAVo, getDeviceParams(request));
			if (res != null && XiuConstant.RETCODE_SUCCESS.equals(res.getRetCode())) {
				List<GoodsVo> goodsList = res.getGoodsList();// 商品列表
				// 每页记录数
				int pageSize = queryAVo.getPageSize();
				// 返回总页数
				int totalPage = (res.getGoodsNum() % pageSize > 0) ? (res.getGoodsNum() / pageSize + 1) : (res.getGoodsNum() / pageSize);

				model.addAttribute("pageNo", pageNo);
				model.addAttribute("totalPage", totalPage);
				model.addAttribute("activityId", queryAVo.getActivityId());
				model.addAttribute("goodsList", goodsList);
			} 
		} 
		catch (Exception e) {
			logger.error("获取专题商品列表时发生异常：", e);
		}

		return "/goods/goodsList";
	}

	/**
	 * 根据商品ID查询商品详情，此处先根据goodsId查询goodsSn，然后根据调用loadGoodsDetail()
	 */
	@RequestMapping(value = "/{goodsId}")
	public String loadGoodsDetailByGoodsId(@PathVariable("goodsId") String goodsId, Model model,HttpServletRequest request,HttpServletResponse response) {
		String activityId = request.getParameter("activityId");
		try {
			if (!StringUtils.isEmpty(goodsId)) {
				String goodsSn = goodsManager.getGoodsSnByGoodsId(goodsId);
				if (!StringUtils.isEmpty(goodsSn)) {
					
					// ======================= 加载商品基本信息   =========================
					GoodsDetailVo goodsDetailVo = goodsManager.viewGoodsDetail(goodsSn, getDeviceParams(request));
					model.addAttribute("goodsDetailVo", dealImgUrl(goodsDetailVo));
					model.addAttribute("activityId", activityId);
				}
				

				// ====================== 加载商品是否已收藏信息  ==============================
				boolean favorStatus = false; // 商品收藏属性  默认为不收藏
				// 检查登陆
				if (checkLogin(request)) {
					//从cookie中获得userId
					LoginResVo loginResVo = SessionUtil.getUser(request);
					Long userId = Long.parseLong(loginResVo.getUserId());
					favorStatus = hasExistsFavorGoodsUG(userId,Long.parseLong(goodsId));
				}
				
				model.addAttribute("favorStatus", favorStatus);
					
				// 加载商品评论信息
				String url = domainURL.getCommDomainURL() + "/mobile/list?searchType=0&pageType=0&catgCode=0&pageNo=1&pageSize=10&prodSn="+goodsSn;
				String result = HttpClientUtil.sendHttpClientMsg(url);
				List<CommentInfo> commentInfoList = new ArrayList<CommentInfo>();
				JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(result);
				// 评论响应应答信息
				if (jsonObject.getBoolean("result")) {
					// 获取评论列表信息
					JSONArray jsonArray = jsonObject.getJSONArray("comBO");
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject comBoJsonObject = jsonArray.getJSONObject(i);
						JSONObject comment = comBoJsonObject.getJSONObject("comment");
						JSONObject commentProd = comBoJsonObject.getJSONObject("commentProd");
						JSONObject commentUser = comBoJsonObject.getJSONObject("commentUser");
						CommentInfo commentInfo = new CommentInfo();
						// 封装评论的相关信息
						commentInfo.setCommentContent(comment.getString("commentContent"));
						commentInfo.setCommentDate(new Date(comment.getLong("commentDate")));
						if (comBoJsonObject.containsKey("percent")) {
							commentInfo.setCommentScore(comBoJsonObject.getInt("percent"));
						}
						if (comment.containsKey("lastReplyContent")) {
							commentInfo.setLastReplyContent(comment.getString("lastReplyContent"));
						}
						if (comment.containsKey("lastReplyDate")) {
							commentInfo.setLastReplyDate(new Date(comment.getLong("lastReplyDate")));
						}
						if (comment.containsKey("lastReplyUpdater")) {
							commentInfo.setLastReplyUpdater(comment.getString("lastReplyUpdater"));
						}
						
						// 封装产品相关信息
						commentInfo.setBrandCode(commentProd.getString("brandCode"));
						commentInfo.setBrandId(commentProd.getLong("brandId"));
						commentInfo.setProdCode(commentProd.getString("prodCode"));
						commentInfo.setProdColor(commentProd.getString("prodColor"));
						commentInfo.setProdSize(commentProd.getString("prodSize"));
						commentInfo.setProdId(commentProd.getLong("prodId"));
						commentInfo.setProdImgUrl(commentProd.getString("prodImgUrl"));
						commentInfo.setProdName(commentProd.getString("prodName"));
						commentInfo.setProdSku(commentProd.getString("prodName"));
						
						// 封装用户相关信息
						commentInfo.setHasBought(commentUser.getInt("hasBought"));
						if (commentUser.containsKey("mobile")) {
							commentInfo.setMobile(commentUser.getString("mobile"));
						}
						commentInfo.setUserId(commentUser.getLong("userId"));
						if (commentUser.containsKey("userName")) {
							commentInfo.setUserName(commentUser.getString("userName"));
						}
						commentInfo.setUserNick(commentUser.getString("userNick"));
						
						if (comBoJsonObject.containsKey("commentProdAttr")) {
							// 商品额外其他属性信息
							JSONArray commentProdAttr = comBoJsonObject.getJSONArray("commentProdAttr");
							List<FlexibleItem> flexibleItems = new ArrayList<FlexibleItem>();
							for (int j = 0; j < commentProdAttr.size(); j++) {
								JSONObject commentProJsonObject = commentProdAttr.getJSONObject(j);
								FlexibleItem flexibleItem = new FlexibleItem();
								flexibleItem.setKey(commentProJsonObject.getString("attrName"));
								flexibleItem.setValue(commentProJsonObject.getString("attrDesc"));
								flexibleItems.add(flexibleItem);
							}
							commentInfo.setCommentProdAttr(flexibleItems);
						}
						
						// 添加到用户列表
						commentInfoList.add(commentInfo);
					}
				}
				
				model.addAttribute("commentInfoList", commentInfoList);
			}
		} catch (Exception e) {
			logger.error("加载商品详情发生异常（ByGoodsId）：", e);
		}
		
		return "/goods/goodsInfo";
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
			resultMap.put("result", true);
			resultMap.put("goodsDetailVo", dealImgUrl(goodsDetailVo));
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
			logger.error("加载商品详情发生异常：", e );
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

	/**
	 * 返回SKU的信息
	 */
	@ResponseBody
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
	}
	
	/**
	 * 处理商品详情图片地址
	 * @param goods
	 * @return
	 */
	protected GoodsDetailVo dealImgUrl(GoodsDetailVo goods) {
		List<FlexibleItem> imgList = goods.getImgList();
		List<FlexibleItem> smallImgList = new ArrayList<FlexibleItem>();
		List<FlexibleItem> bigImgList = new ArrayList<FlexibleItem>();
		String imgServerUrl = getImgServerUrlPre();
		
		for (FlexibleItem item : imgList) {
			if (item.getValue() != null && !item.getValue().equals("[]")) {
				
				String[] imgArr = item.getValue().split(",");
				for (int i = 0; i < imgArr.length; i++) {
					
					FlexibleItem smallItem = new FlexibleItem();// 小图实体
					FlexibleItem bigItem = new FlexibleItem();// 大图实体

					String[] newImgArr = imgArr[i].split("com/")[1].split("_");
					String smallImgUrl = imgServerUrl + newImgArr[0] + "_" + "402_536.jpg";
					String bigImgUrl = imgServerUrl + newImgArr[0] + "_" + "720_960.jpg";
					
					smallItem.setKey(item.getKey());
					smallItem.setValue(smallImgUrl);
					smallImgList.add(smallItem);
					
					bigItem.setKey(item.getKey());
					bigItem.setValue(bigImgUrl);
					bigImgList.add(bigItem);
					
				}
				
			}
		}
		
		goods.setImgList(smallImgList);
		goods.setImgListMax(bigImgList);
		return goods;
	}
	
	/**
	 * 获取图片服务器URL访问前缀
	 * @return url
	 */
	private String getImgServerUrlPre() {
		// 目前图片服务器前缀
		int[] arr = { 1, 2,3 };
		int index = new Random().nextInt(arr.length);
		return "http://" + arr[index] + ".xiustatic.com/";
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
}
