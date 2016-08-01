package com.xiu.mobile.portal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.core.utils.ObjectUtil;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.model.GoodsVo;
import com.xiu.mobile.portal.model.Topic;
import com.xiu.mobile.portal.model.TopicCategoryVO;
import com.xiu.mobile.portal.model.TopicFilterItemVO;
import com.xiu.mobile.portal.model.TopicFilterVO;
import com.xiu.mobile.portal.service.ITopicsExtService;

/**
 * 卖场扩展Controller
 * @author coco.long
 * @time	2015-08-21
 */
@Controller
@RequestMapping("/topicsExt")
public class TopicsExtController {

	private Logger logger = Logger.getLogger(TopicsExtController.class);
	
	@Autowired
	private ITopicsExtService topicsExtService;
	
	/**
	 * 查询卖场商品列表
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTopicGoodsList", produces = "text/html;charset=UTF-8")
	public String getTopicGoodsList(HttpServletRequest request, String jsoncallback) {
		logger.info("查询卖场商品列表-进入方法");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String activityId = request.getParameter("activityId");	//卖场ID 
		String onlyOnSale = request.getParameter("onlyOnSale");	//是否在售，有货
		String categorys = request.getParameter("categorys");		//商品分类ID拼串，逗号分割
		String sizes = request.getParameter("sizes");				//商品尺码拼串，逗号分割
		String order = request.getParameter("order");			//排序类型 0.推荐 1.价格升序 2.价格降序 3.折扣升序 4.折扣降序
		String pageNum = request.getParameter("pageNum");		//页数
		String filterIds = request.getParameter("filterIds");		//过滤参数
		String minPrice = request.getParameter("minPrice");		//最小价格
		String maxPrice = request.getParameter("maxPrice");		//最大价格
		//验证参数
		if(StringUtils.isBlank(activityId) || StringUtils.isBlank(pageNum)) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		if(categorys!=null&&categorys.equals("0")){//代表全部
			categorys=null;
		}
		if(sizes!=null&&sizes.equals("全部")){//代表全部
			sizes=null;
		}
		
		int pageCntNum = 1; //页码
		try {
			pageCntNum = Integer.parseInt(pageNum);
		} catch (NumberFormatException e) {
			logger.info("查询卖场商品列表时页码参数错误，使用默认第一页：" + e.getMessage());
		}
		
		try {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("activityId", activityId);
			paraMap.put("onlyOnSale", onlyOnSale);
			paraMap.put("categorys", categorys);
			paraMap.put("sizes", sizes);
			if(StringUtils.isBlank(order)) {
				order = "0";	//默认推荐排序
			}
			paraMap.put("order", order);
			
			// 设置分页
			int startRow = (pageCntNum - 1) * XiuConstant.PAGE_SIZE_40 + 1;
			int endRow = pageCntNum * XiuConstant.PAGE_SIZE_40;
			paraMap.put("startRow", startRow);
			paraMap.put("endRow", endRow);
			paraMap.put("filterIds", filterIds);
			paraMap.put("minPrice", minPrice);
			paraMap.put("maxPrice", maxPrice);
			
			//查询卖场商品列表
			Map dataMap = topicsExtService.getTopicGoodsList(paraMap);
			
			if(dataMap != null) {
				boolean result = (Boolean) dataMap.get("result");
				
				if(result) {
					//成功，组装数据
					List<GoodsVo> goodsList = (List<GoodsVo>) dataMap.get("goodsList");
					List<TopicCategoryVO> categoryList = (List<TopicCategoryVO>) dataMap.get("categoryList");
					
					int totalCount = Integer.parseInt(dataMap.get("totalCount").toString());
					int totalPage = 0;
					if(totalCount > 0) {
						totalPage = totalCount / XiuConstant.ORDER_PAGE_SIZE;
						if(totalCount % XiuConstant.ORDER_PAGE_SIZE > 0) {
							totalPage++;
						}
					}
					
					Topic topic = (Topic) dataMap.get("topic");
					if(topic != null) {
						resultMap.put("activityId", topic.getActivity_id());
						resultMap.put("activityName", topic.getName());
						resultMap.put("mobile_pic", topic.getMobile_pic());
						resultMap.put("bannerPic", topic.getBannerPic());	 //卖场Banner图
						resultMap.put("title", topic.getTitle());			 //标题
						resultMap.put("description", topic.getDescription());//描述
						resultMap.put("isShowCountDown", topic.getIsShowCountDown());//是否显示卖场倒计时 1:是 0:否
						if(topic.getIsShowCountDown()!=null&& topic.getIsShowCountDown()==1){
							resultMap.put("endTime", topic.getEndTime());		 //结束时间
						}
					} else {
						resultMap.put("activityId", "");
						resultMap.put("activityName", "");
						resultMap.put("mobile_pic", "");
					}
					
					resultMap.put("result", true);
					resultMap.put("goodsList", goodsList);
					resultMap.put("categoryList", categoryList);
					resultMap.put("totalCount", totalCount);
					resultMap.put("totalPage", totalPage);
				} else {
					//失败
					resultMap.put("result", false);
					resultMap.put("errorCode", ErrorCode.GetGoodsListFailed.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.GetGoodsListFailed.getErrorMsg());
				}
			} else {
				//没有查询到数据
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.GetGoodsListFailed.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.GetGoodsListFailed.getErrorMsg());
			}
		} catch(Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询卖场商品列表时发生异常：" + e.getMessage());
		}
		logger.info("查询卖场商品列表-退出方法");
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	

	/**
	 * 卖场筛选数据查询
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTopicFilterList", produces = "text/html;charset=UTF-8")
	public String getTopicFilterList(HttpServletRequest request, String jsoncallback) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String activityId = request.getParameter("activityId");	//卖场ID 
		String filterIds = ObjectUtil.getString(request.getParameter("filterIds"),"");	//筛选ids
		
		//验证参数
		if(StringUtils.isBlank(activityId)) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		
		
		Map paraMap = new HashMap();
		paraMap.put("activityId", activityId);
		paraMap.put("filterIds", filterIds);
		List<TopicFilterVO> filters =topicsExtService.getTopicGoodFilter(paraMap);
		resultMap.put("filterList", filters);

		resultMap.put("result", true);
		resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
		resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	
	
	/**
	 * 移除卖场的尺码分类缓存
	 * @param request
	 * @param activityId
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/removeTopicCache", produces = "text/html;charset=UTF-8")
	public String removeTopicCache(HttpServletRequest request, String activityId, String jsoncallback) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
	
		//验证参数
		if(StringUtils.isBlank(activityId)) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		
		try {
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("activityId", activityId);
			
			//移除卖场的尺码分类缓存
			boolean result = topicsExtService.removeTopicMemcache(paraMap);
			
			if(result) {
				//成功
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			} else {
				//失败
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.RemoveTopicCacheFailed.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.RemoveTopicCacheFailed.getErrorMsg());
			}
		} catch(Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("移除卖场的尺码分类缓存时发生异常：" + e.getMessage());
		}
				
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
}
