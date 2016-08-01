package com.xiu.mobile.portal.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.common.lang.StringUtil;
import com.xiu.mobile.core.model.DataBrandBo;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.ImageServiceConvertor;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.model.BrandTopicVo;
import com.xiu.mobile.portal.model.BrandVo;
import com.xiu.mobile.portal.model.DataBrandVo;
import com.xiu.mobile.portal.model.FavorBrandBo;
import com.xiu.mobile.portal.model.LoginResVo;
import com.xiu.mobile.portal.service.IFavorBrandService;
import com.xiu.mobile.portal.service.ILoginService;

/**
 * <p>
 * **************************************************************
 * 
 * @Description: 收藏品牌控制层
 * @AUTHOR wangchengqun
 * @DATE 2014-5-30
 **************************************************************** 
 *       </p>
 */
@Controller
@RequestMapping("/favorBrand")
public class FavorBrandController extends BaseController {
	private Logger logger = Logger.getLogger(FavorBrandController.class);

	@Autowired
	private IFavorBrandService favorBrandService;
	@Autowired
	private ILoginService loginService;

	/**
	 * 
	 * 添加收藏
	 * 
	 * @param brandId
	 *            品牌Id
	 * @param jsoncallback
	 * @param terminal客户端
	 */
	@ResponseBody
	@RequestMapping(value = "/addFavorBrand", produces = "text/html;charset=UTF-8")
	public String addFavorBrand(HttpServletRequest request, Long brandId, String terminal, String jsoncallback) {
		// 存储返回结果值
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			// 从cookie中获得userId
			LoginResVo loginResVo = SessionUtil.getUser(request);
			Long userId = Long.parseLong(loginResVo.getUserId());
			int ter = 3;
			if (null == terminal || "".equals(terminal)) {
				ter = 3;
			} else {
				ter = Integer.parseInt(terminal);
			}
			// 是否收藏了此品牌
			if (!hasExistsFavorBrandUG(userId, brandId)) {
				FavorBrandBo favorBrand = new FavorBrandBo();
				favorBrand.setBrandId(brandId);
				favorBrand.setUserId(userId);
				favorBrand.setCreateDate(new Date());
				favorBrand.setTerminal(ter);
				int flag = favorBrandService.addFavorBrand(favorBrand);
				if (0 == flag) {
					resultMap.put("result", true);
					resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
				}
			} else {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.CheckAddBrandFailed.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.CheckAddBrandFailed.getErrorMsg());
			}

		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("添加收藏品牌时发生异常：" + e.getMessage());
		}

		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

	/**
	 * 检查用户是否已收藏了某品牌
	 * 
	 * @param userId
	 * @param brandId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/hasExistsFavorBrand", produces = "text/html;charset=UTF-8")
	public String hasExistsFavorBrand(HttpServletRequest request, Long brandId, String jsoncallback) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			// 从cookie中获得userId
			LoginResVo loginResVo = SessionUtil.getUser(request);
			Long userId = Long.parseLong(loginResVo.getUserId());
			boolean flag = this.hasExistsFavorBrandUG(userId, brandId);
			if (flag) {
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.CheckAddBrandFailed.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.CheckAddBrandFailed.getErrorMsg());
			} else {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.WithoutAddBrand.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.WithoutAddBrand.getErrorMsg());
			}
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("检查用户已收藏某品牌时发生异常:" + e.getMessage());
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

	/**
	 * 删除收藏夹中的某条收藏信息
	 * 
	 * @param brandId
	 * @param jsoncallback
	 */
	@ResponseBody
	@RequestMapping(value = "delFavorBrand", produces = "text/html;charset=UTF-8")
	public String delFavorBrand(HttpServletRequest request, Long brandId, String jsoncallback) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			// 从cookie中获得userId
			LoginResVo loginResVo = SessionUtil.getUser(request);
			Long userId = Long.parseLong(loginResVo.getUserId());
		//	Long userId=2012465815L;
			boolean flag = this.hasExistsFavorBrandUG(userId, brandId);
			if (!flag) {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.WithoutAddBrand.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.WithoutAddBrand.getErrorMsg());
			} else {
				HashMap<String, Object> valMap = new HashMap<String, Object>();
				valMap.put("userId", userId);
				valMap.put("brandId", brandId);
				int resCnt = favorBrandService.delFavorBrand(valMap);
				if (0 == resCnt) {
					resultMap.put("result", true);
					resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
				}
			}
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("删除某条收藏品牌时发生异常：" + e.getMessage());
		}

		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

	/**
	 * 查询用户收藏的品牌信息
	 * 
	 * @param request
	 * @param jsoncallback
	 * @param pageNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getFavorBrandsList", produces = "text/html;charset=UTF-8")
	public String getFavorBrandsList(HttpServletRequest request, String jsoncallback, String pageNum) {
		int pageCntNum = 1;
		try {
			pageCntNum = Integer.parseInt(pageNum);
		} catch (NumberFormatException e1) {
			logger.info("查询用户收藏的品牌信息时页码参数错误，故使用默认第一页：" + e1.getMessage());
		}

		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			// 从cookie中获得userId
			LoginResVo loginResVo = SessionUtil.getUser(request);
			Long userId = Long.parseLong(loginResVo.getUserId());
			int pageSize = NumberUtils.toInt(request.getParameter("pageSize"), XiuConstant.TOPIC_PAGE_SIZE);
			int lineMin = (pageCntNum - 1) * pageSize + 1;
			int lineMax = pageCntNum * pageSize + 1;
			List<DataBrandVo> dataBrandVoList = new ArrayList<DataBrandVo>();
			// 分页数据
			List<DataBrandBo> dataBrandList = this.getBrandList(userId, lineMin, lineMax);
			if (null != dataBrandList) {
				int dataLen = dataBrandList.size();
				for (int i = 0; i < dataLen; i++) {
					String name = dataBrandList.get(i).getBrandName() == null ? "" : dataBrandList.get(i).getBrandName();
					String img = dataBrandList.get(i).getAuthimgurl();
					DataBrandVo dataBrandVo = new DataBrandVo();
					dataBrandVo.setBrandId(dataBrandList.get(i).getBrandId());
					dataBrandVo.setBrandCode(dataBrandList.get(i).getBrandCode());
					dataBrandVo.setBrandName(name);
					if (null != img && !"".equals(img)) {
						int index = img.lastIndexOf(".");
						int lastNum = Integer.parseInt(img.substring(index - 1, index), 16);
						dataBrandVo.setBrandImg(ImageServiceConvertor.replaceImageDomain(XiuConstant.MOBILE_IMAGE[lastNum % 3] + "/UploadFiles/xiu/brand" + img));
					} else {
						dataBrandVo.setBrandImg("");
					}
					// 品牌对应的卖场数量
					String cnt = favorBrandService.queryActivityCount(dataBrandList.get(i).getBrandId());
					String[] cnts = cnt.split(",");
					if (cnts.length > 1) {
						dataBrandVo.setTopicCnt(Integer.parseInt(cnts[0]));
						dataBrandVo.setActivityId(cnts[1]);
					} else if (cnts.length == 1) {
						dataBrandVo.setTopicCnt(Integer.parseInt(cnts[0]));
					}

					dataBrandVoList.add(dataBrandVo);

				}
				// 所有数据数量
				int count = favorBrandService.getFavorBrandCount(userId);
				int totalPage = (count % pageSize > 0) ? (count / pageSize + 1) : (count / pageSize);

				resultMap.put("result", true);
				resultMap.put("totalPage", totalPage);
				resultMap.put("favorBrandList", dataBrandVoList);
			} else {
				resultMap.put("result", true);
				resultMap.put("totalPage", 0);
				resultMap.put("favorBrandList", new ArrayList<DataBrandVo>());
			}
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询用户收藏的品牌信息时发生异常：" + e.getMessage());
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 查询用户已关注的品牌信息
	 * 
	 * @param request
	 * @param jsoncallback
	 * @param pageNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAttentionBrandsList", produces = "text/html;charset=UTF-8")
	public String getAttentionBrandsList(HttpServletRequest request, String jsoncallback, String pageNum) {
		int pageCntNum = 1;
		try {
			pageCntNum = Integer.parseInt(pageNum);
		} catch (NumberFormatException e1) {
			logger.info("查询用户收藏的品牌信息时页码参数错误，故使用默认第一页：" + e1.getMessage());
		}

		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			// 从cookie中获得userId
			LoginResVo loginResVo = SessionUtil.getUser(request);
			Long userId = Long.parseLong(loginResVo.getUserId());
			int lineMin = (pageCntNum - 1) * XiuConstant.TOPIC_PAGE_SIZE + 1;
			int lineMax = 1000; // 查询写死一千条关注品牌记录
			List<BrandVo> brandVoList = new ArrayList<BrandVo>();
			// 分页数据
			List<DataBrandBo> dataBrandList = this.getBrandList(userId, lineMin, lineMax);
			if (null != dataBrandList) {
				int dataLen = dataBrandList.size();
				for (int i = 0; i < dataLen; i++) {
					String name = dataBrandList.get(i).getBrandName() == null ? "" : dataBrandList.get(i).getBrandName();
					String img = dataBrandList.get(i).getAuthimgurl();
					BrandVo dataBrandVo = new BrandVo();
					dataBrandVo.setBrandId(dataBrandList.get(i).getBrandId());
					dataBrandVo.setBrandCode(dataBrandList.get(i).getBrandCode());
					dataBrandVo.setBrandName(name);
					if (null != img && !"".equals(img)) {
						int index = img.lastIndexOf(".");
						int lastNum = Integer.parseInt(img.substring(index - 1, index), 16);
						dataBrandVo.setBrandImg(ImageServiceConvertor.replaceImageDomain(XiuConstant.MOBILE_IMAGE[lastNum % 3] + "/UploadFiles/xiu/brand" + img));
					} else {
						dataBrandVo.setBrandImg("");
					}
					brandVoList.add(dataBrandVo);
				}
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
				resultMap.put("favorBrandList", brandVoList);
			} else {
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
				resultMap.put("favorBrandList", new ArrayList<DataBrandVo>());
			}
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询用户收藏的品牌信息时发生异常：" + e.getMessage());
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

	/**
	 * 检查是否收藏了某品牌
	 * 
	 * @param userId
	 * @param brandId
	 * @return
	 * @throws Exception
	 */
	private boolean hasExistsFavorBrandUG(Long userId, Long brandId) throws Exception {
		HashMap<String, Object> valMap = new HashMap<String, Object>();
		valMap.put("userId", userId);
		valMap.put("brandId", brandId);
		List<FavorBrandBo> favorBrandList = favorBrandService.getBrandByUserIdAndBrandId(valMap);
		if (null != favorBrandList && favorBrandList.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获得品牌信息
	 * 
	 * @param brandId
	 * @return
	 */
	private List<DataBrandBo> getBrandList(Long userId, int lineMin, int lineMax) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>(10);
		params.put("lineMin", lineMin);
		params.put("lineMax", lineMax);
		params.put("userId", userId);
		List<DataBrandBo> dataBrandList = favorBrandService.getBrandList(params);
		if (null != dataBrandList && dataBrandList.size() > 0) {
			return dataBrandList;
		} else {
			return null;
		}

	}

	/**
	 * 关联品牌收藏的精选卖场
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBrandTopicList", produces = "text/html;charset=UTF-8")
	public String getBrandTopicList(String brandId, String pageNum, String jsoncallback) {
		int pageCntNum = 1;
		try {
			pageCntNum = Integer.parseInt(pageNum);
		} catch (NumberFormatException e1) {
			logger.error("关联品牌收藏的精选卖场时页码参数错误，故使用默认第一页：" + e1.getMessage());
		}
		// 存储返回结果值
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int lineMin = (pageCntNum - 1) * XiuConstant.TOPIC_PAGE_SIZE + 1;
			int lineMax = pageCntNum * XiuConstant.TOPIC_PAGE_SIZE + 1;

			HashMap<String, Object> valMap = new HashMap<String, Object>();
			valMap.put("lineMin", lineMin);
			valMap.put("lineMax", lineMax);
			valMap.put("brandId", brandId);

			List<BrandTopicVo> brandTopicVoList = favorBrandService.queryBrandTopicList(valMap);
			if (null != brandTopicVoList && brandTopicVoList.size() > 0) {

				int brandTopicCnt = favorBrandService.queryBrandTopicListCount(Long.parseLong(brandId));

				int pageSize = XiuConstant.TOPIC_PAGE_SIZE;
				int totalPage = (brandTopicCnt % pageSize > 0) ? (brandTopicCnt / pageSize + 1) : (brandTopicCnt / pageSize);
				resultMap.put("result", true);
				resultMap.put("totalPage", totalPage);
				resultMap.put("brandTopicList", brandTopicVoList);

			} else {
				resultMap.put("result", true);
				resultMap.put("totalPage", 0);
				resultMap.put("brandTopicList", new ArrayList<BrandTopicVo>());
			}
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取专题数据发生异常：" + e.getMessage());
		}

		return JsonUtils.bean2jsonP(jsoncallback, resultMap);

	}
	
	
	/**
	 * 删除收藏夹中的某条收藏信息
	 * @param request
	 * @param brandIds
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delBatchFavorBrand", produces = "text/html;charset=UTF-8")
	public String delBatchFavorBrand(HttpServletRequest request, String brandIds, String jsoncallback) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			//从cookie中获得userId
			LoginResVo loginResVo=SessionUtil.getUser(request);
			Long userId=Long.parseLong(loginResVo.getUserId());
			
			if(StringUtil.isBlank(brandIds)){
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
			}
			
			HashMap<String, Object> valMap=new HashMap<String, Object>();
			valMap.put("userId", userId);
			String[] strIds = brandIds.split(",");
			List<Long> idsL = new ArrayList<Long>();
			if(strIds != null){
				for(String s : strIds){
					if(!StringUtil.isEmpty(s))
						idsL.add(Long.parseLong(s));
				}
			}
			valMap.put("brandIdArr", strIds);
			int resCnt=favorBrandService.delBatchFavorBrand(valMap);
			if(0==resCnt){
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}
		
	} catch (Exception e) {
		resultMap.put("result", false);
		resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
		resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		logger.error("批量删除收藏夹中品牌时发生异常：" + e.getMessage());
	}

	return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

	
}
