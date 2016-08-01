package com.xiu.mobile.portal.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.commerce.hessian.model.Product;
import com.xiu.mobile.core.utils.DateUtil;
import com.xiu.mobile.portal.service.IBrowseGoodsService;
import com.xiu.mobile.portal.service.IGoodsService;
import com.xiu.mobile.portal.service.ITopicActivityGoodsService;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.model.BrowseGoodsModel;
import com.xiu.mobile.portal.model.LoginResVo;

/**
 * 
 * @author longchaoqun
 * @date 20141219
 * 
 */
@Controller
@RequestMapping("/browseGoods")
public class BrowseGoodsController extends BaseController {
	private Logger logger = Logger.getLogger(BrowseGoodsController.class);
	
	@Autowired
	private IBrowseGoodsService browseGoodsService;
	@Autowired
	private IGoodsService goodsManager;
	@Autowired
	private ITopicActivityGoodsService topicActivityGoodsService;
	
	@ResponseBody
	@RequestMapping(value = "/addBrowseGoods", produces = "text/html;charset=UTF-8")
	public String addBrowseGoods(HttpServletRequest request, String goodsSns, String browseTime, String terminal, String jsoncallback) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			if(StringUtils.isNotBlank(goodsSns)) {
				//检查参数
				LoginResVo loginResVo = SessionUtil.getUser(request); //从cookie中获得userId
				Long userId = Long.parseLong(loginResVo.getUserId());
				Map paraMap = new LinkedHashMap<String, Object>();
				paraMap.put("userId", userId);
				paraMap.put("goodsSns", goodsSns);
				paraMap.put("browseTime", browseTime);
				paraMap.put("terminal", Long.parseLong(terminal));
				int result = browseGoodsService.addBrowseGoods(paraMap);
				if(result >= 0) {
					//成功
					resultMap.put("result", true);
					resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
				} else {
					//失败
					resultMap.put("result", false);
					resultMap.put("errorCode", ErrorCode.AddBrowseGoodsFailed.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.AddBrowseGoodsFailed.getErrorMsg());
				}
			} else {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.ParamsError.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
			}
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("添加商品浏览记录时发生异常：" + e.getMessage());
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteBrowseGoods", produces = "text/html;charset=UTF-8")
	public String deleteBrowseGoods(HttpServletRequest request, String id, String jsoncallback) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			//从cookie中获得userId
			LoginResVo loginResVo=SessionUtil.getUser(request);
			Long userId = Long.parseLong(loginResVo.getUserId());
			Map paraMap = new LinkedHashMap<String, Object>();
			paraMap.put("userId", userId);
			paraMap.put("id", id);
			int result = browseGoodsService.deleteBrowseGoods(paraMap);
			if(result >= 0) {
				//成功
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			} else {
				//失败
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.DeleteBrowseGoodsFailed.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.DeleteBrowseGoodsFailed.getErrorMsg());
			}
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("删除商品浏览记录时发生异常：" + e.getMessage());
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteAllBrowseGoods", produces = "text/html;charset=UTF-8")
	public String deleteAllBrowseGoods(HttpServletRequest request, String jsoncallback) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			//从cookie中获得userId
			LoginResVo loginResVo=SessionUtil.getUser(request);
			Long userId = Long.parseLong(loginResVo.getUserId());
			Map paraMap = new LinkedHashMap<String, Object>();
			paraMap.put("userId", userId);
			int result = browseGoodsService.deleteAllBrowseGoods(paraMap);
			if(result >= 0) {
				//成功
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			} else {
				//失败
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.DeleteAllBrowseGoodsFailed.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.DeleteAllBrowseGoodsFailed.getErrorMsg());
			}
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("清空商品浏览记录时发生异常：" + e.getMessage());
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getBrowseGoodsList", produces = "text/html;charset=UTF-8")
	public String getBrowseGoodsList(HttpServletRequest request, String pageNum, String jsoncallback) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		int pageCntNum = 1;
		try {
			pageCntNum = Integer.parseInt(pageNum);
		} catch (NumberFormatException e1) {
			logger.info("查询用户收藏的商品信息时页码参数错误，故使用默认第一页：" + e1.getMessage());
		}
		try {
			//从cookie中获得userId
			LoginResVo loginResVo = SessionUtil.getUser(request);
			Long userId = Long.parseLong(loginResVo.getUserId());
			int startRow = (pageCntNum - 1) * XiuConstant.TOPIC_PAGE_SIZE + 1;
			int endRow = pageCntNum * XiuConstant.TOPIC_PAGE_SIZE + 1;
			Map paraMap = new LinkedHashMap<String, Object>();
			paraMap.put("userId", userId);
			paraMap.put("startRow", startRow);
			paraMap.put("endRow", endRow);
			
			//查询
			List<BrowseGoodsModel> resultList = browseGoodsService.getBrowseGoodsListByUserId(paraMap);
			if(resultList != null && resultList.size() > 0) {
				String goodsSns = getGoodsSns(resultList); //获取商品走秀码拼串，批量查询商品信息
				List<Product> productList = topicActivityGoodsService.batchLoadProducts(goodsSns);
				// ===========================封装商品名称、价格、图片等相关信息==============================/
				if(productList != null && productList.size() > 0) { 
					for(BrowseGoodsModel model : resultList) {
						String createDay=DateUtil.formatDate(model.getCreateDate(), "yyyy-MM-dd");
						model.setCreateDay(createDay);
						boolean hasProduct = false;
						for(Product product : productList) {
							
							
							if(model.getGoodsSn().equals(product.getXiuSN())) {
								hasProduct = true;
								//设置商品名称
								model.setGoodsName(product.getPrdName());
								//设置品牌名称
								model.setBrandName(product.getBrandName());
								//设置商品走秀价
								Double zsPrice = goodsManager.getZxPrice(product);
								model.setZsPrice(zsPrice == null ? 0.00 : zsPrice);	
								//设置商品图片
								String goodsSn = model.getGoodsSn();
								int index = Integer.parseInt(goodsSn.substring(goodsSn.length()-1, goodsSn.length()),16);
								String domain =  XiuConstant.XIUSTATIC_NUMS[index%3] + ".xiustatic.com";
								String imgUrl =  product.getImgUrl() + "/g1_162_216.jpg";
								imgUrl = imgUrl.replaceAll("images.xiu.com", domain);
								model.setGoodsImgUrl(imgUrl);
								//设置商品是否售罄
								int stock = Integer.parseInt(product.getOnSale());
								model.setStock(stock);
								//设置商品ID
								model.setGoodsId(String.format("%07d", product.getInnerID()));
							}
						}
						if(!hasProduct) {
							//如果没有查询到产品信息，则设置空值
							model.setGoodsName("");
							model.setZsPrice(0.00);
							model.setGoodsImgUrl("");
							model.setStock(0);
							model.setGoodsId("");
						}
					}
				} else {
					//如果没有查询到产品信息，则设置空值
					for(BrowseGoodsModel model : resultList) {
						model.setGoodsName("");
						model.setZsPrice(0.00);
						model.setGoodsImgUrl("");
						model.setStock(0);
						model.setGoodsId("");
					}
				}
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
				resultMap.put("browseGoodsList", resultList);
			} else {
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
				resultMap.put("browseGoodsList", new ArrayList<BrowseGoodsModel>());
			}
			//查询数量
			int count = browseGoodsService.getBrowseGoodsTotalByUserId(paraMap);
			if(count > 0) {
				int pageSize = XiuConstant.TOPIC_PAGE_SIZE;
				int totalPage = (count % pageSize > 0) ? (count / pageSize + 1) : (count / pageSize);
				resultMap.put("totalPage", totalPage);
			} else {
				resultMap.put("totalPage", 0);
			}
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("删除商品浏览记录时发生异常：" + e.getMessage());
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getBrowseGoods", produces = "text/html;charset=UTF-8")
	public String getBrowseGoods(HttpServletRequest request, String id, String jsoncallback) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			//从cookie中获得userId
			LoginResVo loginResVo = SessionUtil.getUser(request);
			Long userId = Long.parseLong(loginResVo.getUserId());
			Map paraMap = new LinkedHashMap<String, Object>();
			paraMap.put("userId", userId);
			paraMap.put("id", id);
			BrowseGoodsModel model = browseGoodsService.getBrowseGoodsById(paraMap);
			if(model != null) {
				//成功
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
				resultMap.put("browseGoods", model);
			} else {
				//失败
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.getBrowseGoodsFailed.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.getBrowseGoodsFailed.getErrorMsg());
			}
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("删除商品浏览记录时发生异常：" + e.getMessage());
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getBrowseGoodsTotal", produces = "text/html;charset=UTF-8")
	public String getBrowseGoodsTotal(HttpServletRequest request, String jsoncallback) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			//从cookie中获得userId
			LoginResVo loginResVo = SessionUtil.getUser(request);
			Long userId = Long.parseLong(loginResVo.getUserId());
			Map paraMap = new LinkedHashMap<String, Object>();
			paraMap.put("userId", userId);
			int count = browseGoodsService.getBrowseGoodsTotalByUserId(paraMap);
			resultMap.put("result", true);
			resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			resultMap.put("browseGoodsTotal", count);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询商品浏览记录数量时发生异常：" + e.getMessage());
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getBrowseGoodsCount", produces = "text/html;charset=UTF-8")
	public String getBrowseGoodsCount(HttpServletRequest request, String goodsSn, String jsoncallback) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			//从cookie中获得userId
			LoginResVo loginResVo = SessionUtil.getUser(request);
			Long userId = Long.parseLong(loginResVo.getUserId());
			Map paraMap = new LinkedHashMap<String, Object>();
			paraMap.put("userId", userId);
			paraMap.put("goodsSn", goodsSn);
			int count = browseGoodsService.getBrowseGoodsCount(paraMap);
			resultMap.put("result", true);
			resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			resultMap.put("browseGoodsCount", count);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询商品浏览次数时发生异常：" + e.getMessage());
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	//获取商品走秀码拼串
	public String getGoodsSns(List<BrowseGoodsModel> list) {
		String goodsSns = "";
		if(list != null && list.size() > 0) {
			for(BrowseGoodsModel model : list) {
				goodsSns += model.getGoodsSn() + ",";
			}
		}
		if(!"".equals(goodsSns) && goodsSns.indexOf(",", goodsSns.length() - 1) > -1) {
			//截除最后一个逗号
			goodsSns = goodsSns.substring(0, goodsSns.length() - 1);
		}
		return goodsSns;
	}
}
