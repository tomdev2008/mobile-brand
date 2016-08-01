package com.xiu.mobile.portal.controller;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.core.model.MobileCommonData;
import com.xiu.mobile.core.model.TopicType;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.model.ActivityGoodsBo;
import com.xiu.mobile.portal.model.GoodsVo;
import com.xiu.mobile.portal.model.QueryActivityVo;
import com.xiu.mobile.portal.model.Topic;
import com.xiu.mobile.portal.model.TopicActivityGoodsVo;
import com.xiu.mobile.portal.service.IActivityNoregularService;
import com.xiu.mobile.portal.service.IFindMenuService;
import com.xiu.mobile.portal.service.ITopicActivityGoodsService;

/**
 * <p>
 * ************************************************************** 
 * @Description: 首页控制器
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-14 下午4:46:14 
 * ***************************************************************
 * </p>
 */
@Controller
@RequestMapping("/cx")
public class TopicsController extends BaseController 
{
	private final Logger logger = Logger.getLogger(TopicsController.class);
	
	@Autowired
	private IActivityNoregularService activityNoregularService;
	@Autowired
	private ITopicActivityGoodsService topicActivityGoodsService;
	@Autowired
	private IFindMenuService findMenuService;
	/**
	 *  首页
	 */
	@ResponseBody
	@RequestMapping(value = "/getTopics", produces = "text/html;charset=UTF-8")
	public String getTopics(String jsoncallback, HttpServletRequest request) {
		int pageNum = Integer.parseInt(request.getParameter("queryTopicsVo.pageNum"));
		int dataType = Integer.parseInt(request.getParameter("queryTopicsVo.dataType"));
		// 卖场集  为兼容旧版app 不传只查普通卖场的
		int contentType = NumberUtils.toInt(request.getParameter("contentType"), 1);
		logger.info("首页查询参数：params="+request.getQueryString());
						
		// 存储返回结果值
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			int lineMin = (pageNum - 1) * XiuConstant.TOPIC_PAGE_SIZE + 1;
			int lineMax = pageNum * XiuConstant.TOPIC_PAGE_SIZE + 1;
			// 卖场类型ID 0查询全部
			int typeId = NumberUtils.toInt(request.getParameter("typeId"), 0); 

			HashMap<String, Object> valMap = new HashMap<String, Object>();
			valMap.put("lineMin", lineMin);
			valMap.put("lineMax", lineMax);
			valMap.put("dataType", dataType);
			valMap.put("typeId", typeId);
			// 旧版app如果传值为0 需要查询所有
			if (contentType != 0) {
				valMap.put("contentType", contentType);
			}
			
			List<Topic> topicList = activityNoregularService.getTopicList(valMap);
			// 为了兼容旧版app 手动将 特卖卖场(3)转换成普通卖场(1) 卖场集(4)转换成H5(2)卖场
			for (Topic topic : topicList) {
				if (topic.getContent_type() == 3) {
					topic.setContent_type(1);
				}else if (topic.getContent_type() == 4) {
					topic.setContent_type(2);
					if (StringUtils.isBlank(topic.getUrl())) {
						topic.setUrl("http://m.xiu.com/cx/actset.html?setId=".concat(topic.getActivity_id().toString()));
					}
				}
			}

			HashMap<String, Object> valMap2 = new HashMap<String, Object>();
			valMap2.put("dataType", dataType);
			valMap2.put("typeId", typeId);
			// 旧版app如果传值为0 需要查询所有
			if (contentType != 0) {
				valMap2.put("contentType", contentType);
			}
			int topicTotalAmount = activityNoregularService.getTopicTotal(valMap2);
			int pageSize = 20;
			int totalPage = (topicTotalAmount % pageSize > 0) ? (topicTotalAmount / pageSize + 1) : (topicTotalAmount / pageSize);
			resultMap.put("result", true);
			resultMap.put("totalPage", totalPage);
			resultMap.put("topicList", topicList);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取专题数据发生异常：" + e.getMessage(),e);
		}

		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	
	/**
	 *  获取所有顶级卖场列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "/getActList", produces = "text/html;charset=UTF-8")
	public String getActList(String jsoncallback, HttpServletRequest request) {
		int pageNum = NumberUtils.toInt(request.getParameter("queryTopicsVo.pageNum"), 1);
		int dataType = NumberUtils.toInt(request.getParameter("queryTopicsVo.dataType"), 4);
		String contentType = request.getParameter("contentType");
		logger.info("顶级列表查询参数：params="+request.getQueryString());				
		// 存储返回结果值
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			int lineMin = (pageNum - 1) * XiuConstant.TOPIC_PAGE_SIZE + 1;
			int lineMax = pageNum * XiuConstant.TOPIC_PAGE_SIZE + 1;
			// 卖场类型ID 0查询全部
			int typeId = NumberUtils.toInt(request.getParameter("typeId"), 0); 

			HashMap<String, Object> valMap = new HashMap<String, Object>();
			valMap.put("lineMin", lineMin);
			valMap.put("lineMax", lineMax);
			valMap.put("dataType", dataType);
			valMap.put("typeId", typeId);
			valMap.put("contentType", contentType);
			
			List<Topic> topicList = activityNoregularService.getTopicList(valMap);

			HashMap<String, Object> valMap2 = new HashMap<String, Object>();
			valMap2.put("dataType", dataType);
			valMap2.put("typeId", typeId);
			valMap2.put("contentType", contentType);
			int topicTotalAmount = activityNoregularService.getTopicTotal(valMap2);

			int pageSize = 20;
			int totalPage = (topicTotalAmount % pageSize > 0) ? (topicTotalAmount / pageSize + 1) : (topicTotalAmount / pageSize);
			resultMap.put("result", true);
			resultMap.put("totalPage", totalPage);
			resultMap.put("topicList", topicList);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取专题数据发生异常：" + e.getMessage(),e);
		}

		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 *  获取卖场集下的卖场列表
	 */
	@ResponseBody
	@RequestMapping(value = "/getActListBySetId", produces = "text/html;charset=UTF-8")
	public String getActListBySetId(String jsoncallback, HttpServletRequest request) {
		int pageNum = NumberUtils.toInt(request.getParameter("queryTopicsVo.pageNum"), 1);
						
		// 存储返回结果值
		Map<String, Object> resultMap = new HashMap<String, Object>();
		logger.info("获取卖场集下的卖场列表查询参数：params="+request.getQueryString());
		try {
			int lineMin = (pageNum - 1) * XiuConstant.TOPIC_PAGE_SIZE + 1;
			int lineMax = pageNum * XiuConstant.TOPIC_PAGE_SIZE + 1;
			// 卖场类型ID 0查询全部
			int typeId = NumberUtils.toInt(request.getParameter("typeId"), 0); 
			String setId = request.getParameter("setId");
			if(StringUtils.isBlank(setId)) {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.ParamsError.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
			}
			
			// 获取卖场集相关信息
			Topic topic = activityNoregularService.getTopicByActId(setId);

			HashMap<String, Object> valMap = new HashMap<String, Object>();
			valMap.put("lineMin", lineMin);
			valMap.put("lineMax", lineMax);
			valMap.put("typeId", typeId);
			valMap.put("set_id", setId);
			valMap.put("display_style", topic.getDisplayStyle());
			List<Topic> topicList = activityNoregularService.getTopicListBySetId(valMap);

			HashMap<String, Object> valMap2 = new HashMap<String, Object>();
			valMap2.put("typeId", typeId);
			valMap2.put("set_id", setId);
			valMap2.put("display_style", topic.getDisplayStyle());
			int topicTotalAmount = activityNoregularService.getTopicCountBySetId(valMap2);
			int pageSize = 20;
			int totalPage = (topicTotalAmount % pageSize > 0) ? (topicTotalAmount / pageSize + 1) : (topicTotalAmount / pageSize);
			
			// 封装响应参数信息
			resultMap.put("bannerPic", topic.getBannerPic());
			resultMap.put("displayBannerPic", topic.getDisplayBannerPic());
			resultMap.put("displayStyle", topic.getDisplayStyle());
			resultMap.put("result", true);
			resultMap.put("totalPage", totalPage);
			resultMap.put("topicList", topicList);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取专题数据发生异常：" + e.getMessage(),e);
		}

		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getAllRunningTopicsSimple", produces = "text/html;charset=UTF-8")
	public String getAllRunningTopicsSimple(String jsoncallback, HttpServletRequest request) {
		// 存储返回结果值
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			HashMap<String, Object> valMap = new HashMap<String, Object>();
			valMap.put("lineMin", 1);
			valMap.put("lineMax", 999);
			valMap.put("typeId", 0);
			
			List<Topic> topicList = activityNoregularService.queryTopicList(valMap);
			
			String isAll = request.getParameter("isAll");
			for (Iterator<Topic> iterator = topicList.iterator(); iterator.hasNext();) {
				Topic topic = (Topic) iterator.next();
				// 如果没有isAll参数 表明是旧版请求接口
				if (StringUtils.isBlank(isAll)) {
					// 为了兼容旧版app 手动将 特卖卖场(3)转换成普通卖场(1) 卖场集(4)转换成H5(2)卖场
					if (topic.getContent_type() == 3) {
						topic.setContent_type(1);
					}else if (topic.getContent_type() == 4) {
						topic.setContent_type(2);
						if (StringUtils.isBlank(topic.getUrl())) {
							topic.setUrl("http://m.xiu.com/cx/actset.html?setId=".concat(topic.getActivity_id().toString()));
						}
					}
				}
				topic.setEnd_time(null);
				topic.setStart_time(null);
				topic.setId(null);
				topic.setOrder_seq(null);
			}
			resultMap.put("result", true);
			resultMap.put("allRunningTopics", topicList);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取所有专题数据发生异常：" + e.getMessage(),e);
		}

		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 获取专题商品列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTopicGoodsList", produces = "text/html;charset=UTF-8")
	public String getTopicGoodsList(String jsoncallback, HttpServletRequest req) {
		logger.info("获取专题商品列表查询参数：params="+req.getQueryString());
		// 存储返回结果值
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String activityId = req.getParameter("queryAVo.activityId");
		String subType = req.getParameter("queryAVo.subType");
		if(StringUtils.isBlank(activityId) || StringUtils.isBlank(subType)) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.ParamsError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		
		try {
			subType = URLDecoder.decode(subType, "UTF-8");
			String order = req.getParameter("queryAVo.order");
			String onlyOnSale = req.getParameter("queryAVo.onlyOnSale");
			int pageNum = Integer.parseInt(req.getParameter("queryAVo.pageNum"));
			
			QueryActivityVo queryAVo = new QueryActivityVo();
			queryAVo.setActivityId(activityId);
			queryAVo.setSubType(subType);
			queryAVo.setOrder(order);
			queryAVo.setPageNum(pageNum);
			queryAVo.setOnlyOnSale(onlyOnSale);
			ActivityGoodsBo res = topicActivityGoodsService.queryTopicActivityGoodsList(
					queryAVo, getDeviceParams(req));
			
			if (res != null && XiuConstant.RETCODE_SUCCESS.equals(res.getRetCode())) {
				List<GoodsVo> goodsList = res.getGoodsList();// 商品列表
				// 返回总页数
				int pageSize = queryAVo.getPageSize();
				int pageTotal = (res.getGoodsNum() % pageSize > 0) 
						? (res.getGoodsNum() / pageSize + 1) : (res.getGoodsNum() / pageSize);

				// 查询卖场所有的分类
				Map<Object, Object> params = new HashMap<Object, Object>();
				params.put("activityId", Long.parseLong(queryAVo.getActivityId()));
				int cxCount = topicActivityGoodsService.getCxGoodsCountByActivityId(params);
				// 存储分类信息
				List<TopicActivityGoodsVo> categorys = new ArrayList<TopicActivityGoodsVo>();
				
				if (cxCount > 0) {
					// 增加热卖分类
					TopicActivityGoodsVo cxCategory = new TopicActivityGoodsVo();
					cxCategory.setCategory("热卖");
					categorys.add(cxCategory);
				}
				categorys.addAll(topicActivityGoodsService.getAllGoodsCategoryByActivityId(params));

				resultMap.put("result", true);
				resultMap.put("pageTotal", pageTotal);
				resultMap.put("pageNum", queryAVo.getPageNum());
				resultMap.put("categorys", categorys);

				resultMap.put("activityId", queryAVo.getActivityId());
				
				
				Topic topic = activityNoregularService.getTopicByActId(queryAVo.getActivityId());
				resultMap.put("mobile_pic", XiuConstant.MOBILE_IMAGE[1] + topic.getMobile_pic());
//				resultMap.put("mobile_pic", topic.getMobile_pic());
				resultMap.put("activityName", topic.getName());
				
				resultMap.put("goodsList", goodsList);
			} else {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.GetGoodsListFailed.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.GetGoodsListFailed.getErrorMsg());
			}
		}catch (EIBusinessException e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.GetGoodsListFailed.getErrorCode());
			resultMap.put("errorMsg", e.getExtMessage());
			logger.error("获取专题商品列表时发生异常：", e);
		}catch (Exception e) {
			resultMap.clear();
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取专题商品列表时发生异常：", e);
		}

		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

	@ResponseBody
	@RequestMapping(value = "/getTopicTypeList", produces = "text/html;charset=UTF-8")
	public String getTopicTypeList(HttpServletRequest request, String jsoncallback){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			Map<String,Object> paraMap = new HashMap<String,Object>(); 
			paraMap.put("status", 1);	//启用的分类
			//查询卖场分类信息
			List<TopicType> topicTypeList = activityNoregularService.getTopicTypeList(paraMap);
			
			int count = 0;
			if(topicTypeList != null && topicTypeList.size() > 0) {
				count = topicTypeList.size();
			}
			
			//查询发现频道版本号
			paraMap.put("key", GlobalConstants.XIU_MOBILE_FINDCHANNEL_VERSION);
			MobileCommonData commonData = findMenuService.getFindChannelVersion(paraMap);
			
			resultMap.put("topicTypeList", topicTypeList);
			resultMap.put("count", count);
			resultMap.put("findChannelVersion", commonData.getValueNum());
			resultMap.put("result", true);
			resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			
		} catch(Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询卖场分类时发生异常：" + e.getMessage());
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

	/**
	 *  获取所有顶级卖场列表数据 新的卖场分类接口
	 */
	@ResponseBody
	@RequestMapping(value = "/getActivityList", produces = "text/html;charset=UTF-8")
	public String getActivityList(String jsoncallback, HttpServletRequest request) {
		int pageNum = NumberUtils.toInt(request.getParameter("queryTopicsVo.pageNum"), 1);
		int dataType = NumberUtils.toInt(request.getParameter("queryTopicsVo.dataType"), 4);
		String contentType = request.getParameter("contentType");
		logger.info("顶级列表查询参数：params="+request.getQueryString());				
		// 存储返回结果值
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			int lineMin = (pageNum - 1) * XiuConstant.TOPIC_PAGE_SIZE + 1;
			int lineMax = pageNum * XiuConstant.TOPIC_PAGE_SIZE + 1;
			// 卖场类型ID 0查询全部
			int typeId = NumberUtils.toInt(request.getParameter("typeId"), 0); 
			//特卖 最新卖场、即将结束
			int topicTimeType = NumberUtils.toInt(request.getParameter("topicTimeType"), 0); 
			
			HashMap<String, Object> valMap = new HashMap<String, Object>();
			valMap.put("lineMin", lineMin);
			valMap.put("lineMax", lineMax);
			valMap.put("dataType", dataType);
			valMap.put("typeId", typeId);
			valMap.put("contentType", contentType);
			valMap.put("topicTimeType", topicTimeType);
			
			List<Topic> topicList = activityNoregularService.getTopicListNew(valMap);

			HashMap<String, Object> valMap2 = new HashMap<String, Object>();
			valMap2.put("dataType", dataType);
			valMap2.put("typeId", typeId);
			valMap2.put("contentType", contentType);
			valMap2.put("topicTimeType", topicTimeType);
			int topicTotalAmount = activityNoregularService.getTopicTotalNew(valMap2);

			int pageSize = 20;
			int totalPage = (topicTotalAmount % pageSize > 0) ? (topicTotalAmount / pageSize + 1) : (topicTotalAmount / pageSize);
			resultMap.put("result", true);
			resultMap.put("totalPage", totalPage);
			resultMap.put("topicList", topicList);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取专题数据发生异常：" + e.getMessage(),e);
		}

		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	
	/**
	 * 临时使用 支付相关
	 * @param jsoncallback
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/isSupportUnionPay", produces = "text/html;charset=UTF-8")
	public String isSupportUnionPay(String jsoncallback, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", true);
		resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
		resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
}
