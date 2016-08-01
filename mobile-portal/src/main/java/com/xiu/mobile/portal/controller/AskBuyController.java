package com.xiu.mobile.portal.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.ParseProperties;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.model.AskBuyOrderVO;
import com.xiu.mobile.portal.model.ProductTypeBrandVO;
import com.xiu.mobile.portal.service.IAskBuyService;

/**
 * 求购Controller
 * @author coco.long
 * @time	2015-08-21
 */
@Controller
@RequestMapping("/askBuy")
public class AskBuyController {

	private Logger logger = Logger.getLogger(AskBuyController.class);
	
	@Autowired
	private IAskBuyService askBuyService;
	
	/**
	 * 是否显示添加按钮
	 */
	private Boolean  isShowAddBut = new Boolean(ParseProperties.getPropertiesValue("askBuy.is.show.add.button"));

	
	/**
	 * 添加求购信息
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addAskBuyInfo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String addAskBuyInfo(HttpServletRequest request, String jsoncallback) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String productType = request.getParameter("productType");
		String brandName = request.getParameter("brandName");
		String sex = request.getParameter("sex");	//男0 女1
		String productNumber = request.getParameter("productNumber");
		String referenceUrl = request.getParameter("referenceUrl");
		String productDesc = request.getParameter("productDesc");
		String mobile = request.getParameter("mobile");
		String terminal = request.getParameter("terminal");
		
		//检查参数
		if(StringUtils.isBlank(productType) || StringUtils.isBlank(brandName) || StringUtils.isBlank(productDesc) || StringUtils.isBlank(mobile)) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		
		//参数校验
		boolean checkParamsResult = checkParams(productNumber,referenceUrl,mobile,productDesc);
		if(!checkParamsResult) {
			//参数校验失败
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.ParamsError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		
		try {
			String userId = SessionUtil.getUserId(request);
			
			//上传图片
			Map picMap = uploadPictures(request, userId);
			List<String> picList = new ArrayList<String>();
			boolean picUploadResult = (Boolean) picMap.get("result");
			if(picUploadResult) {
				picList = (List<String>) picMap.get("picUrlList");
			}
			
			//组装参数
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("userId", userId);
			paraMap.put("productType", productType);
			paraMap.put("brandName", brandName);
			paraMap.put("sex", sex);
			paraMap.put("productNumber", productNumber);
			paraMap.put("referenceUrl", referenceUrl);
			paraMap.put("productDesc", productDesc);
			paraMap.put("mobile", mobile);
			paraMap.put("terminal", terminal);
			paraMap.put("picList", picList);
			
			//添加求购信息
			Map<String, Object> map = askBuyService.addAskBuyInfo(paraMap);
			
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
					resultMap.put("errorMsg", map.get("errorMessage"));
				} else {
					resultMap.put("errorCode", ErrorCode.AddAskBuyInfoFailed.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.AddAskBuyInfoFailed.getErrorMsg());
				}
			}
			
		} catch(Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("添加求购信息时发生异常：" + e.getMessage());
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 查询用户求购订单列表
	 * @param request
	 * @param pageNum
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAskBuyOrderList", produces = "text/html;charset=UTF-8")
	public String getAskBuyOrderList(HttpServletRequest request, String pageNum, String jsoncallback) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//检查参数
		if(StringUtils.isBlank(pageNum)) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		
		int pageCntNum = 1;	//页码
		try {
			pageCntNum = Integer.parseInt(pageNum);
		} catch (NumberFormatException e) {
			logger.info("查询用户求购订单列表时页码参数错误，使用默认第一页：" + e.getMessage());
		}
		
		try {
			String userId = SessionUtil.getUserId(request);
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("userId", userId);
			
			// 设置分页
			int startRow = (pageCntNum - 1) * XiuConstant.ORDER_PAGE_SIZE + 1;
			int endRow = pageCntNum * XiuConstant.ORDER_PAGE_SIZE;
			paraMap.put("startRow", startRow);
			paraMap.put("endRow", endRow);
			
			//查询用户求购订单列表
			Map orderMap = askBuyService.getUserOrderList(paraMap);
			
			if(orderMap != null) {
				List<AskBuyOrderVO> orderList = (List<AskBuyOrderVO>) orderMap.get("orderList");
				
				int totalCount = Integer.parseInt(orderMap.get("totalCount").toString());
				int totalPage = 0;
				if(totalCount > 0) {
					totalPage = totalCount / XiuConstant.ORDER_PAGE_SIZE;
					if(totalCount % XiuConstant.ORDER_PAGE_SIZE > 0) {
						totalPage++;
					}
				}
				
				resultMap.put("orderList", orderList);
				resultMap.put("totalCount", totalCount);
				resultMap.put("totalPage", totalPage);
			}
			
			resultMap.put("isShowAddButton", isShowAddBut);
			resultMap.put("result", true);
			resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
		} catch(Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询用户求购订单列表时发生异常：" + e.getMessage());
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 查询求购订单详情
	 * @param request
	 * @param orderId
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAskBuyOrderDetail", produces = "text/html;charset=UTF-8")
	public String getAskBuyOrderDetail(HttpServletRequest request, String orderId, String jsoncallback) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//检查参数
		if(StringUtils.isBlank(orderId)) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		
		try {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("orderId", orderId);
			
			//查询求购订单详情
			AskBuyOrderVO orderDetail = askBuyService.getOrderInfoByOrderId(paraMap);
			
			if(orderDetail == null) {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.AskBuyOrderNotExists.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.AskBuyOrderNotExists.getErrorMsg());
			} else {
				resultMap.put("orderDetail", orderDetail);
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}
		} catch(Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询求购订单详情时发生异常：" + e.getMessage());
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 查询最新的求购订单
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getNearAskBuyOrderList", produces = "text/html;charset=UTF-8")
	public String getNearAskBuyOrderList(HttpServletRequest request, String jsoncallback) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			
			//查询最新的求购订单
			List<String> orderList = askBuyService.getNearOrderList(paraMap);
			
			resultMap.put("orderList", orderList);
			resultMap.put("result", true);
			resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
		} catch(Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询最新的求购订单时发生异常：" + e.getMessage());
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 删除求购订单
	 * @param request
	 * @param orderId
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteOrder", produces = "text/html;charset=UTF-8")
	public String deleteOrder(HttpServletRequest request, String orderId, String jsoncallback) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//检查参数
		if(StringUtils.isBlank(orderId)) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		
		try {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("orderId", orderId);
			
			//删除求购订单
			Map<String, Object> map = askBuyService.deleteOrder(paraMap);
			
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
					resultMap.put("errorMsg", map.get("errorMessage"));
				} else {
					resultMap.put("errorCode", ErrorCode.DeleteAskBuyOrder.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.DeleteAskBuyOrder.getErrorMsg());
				}
			}
			
		} catch(Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("删除求购订单时发生异常：" + e.getMessage());
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 查询产品分类和品牌信息
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getProductTypeAndBrand", produces = "text/html;charset=UTF-8")
	public String getProductTypeAndBrand(HttpServletRequest request, String jsoncallback) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
	
		try {
			//查询产品分类和品牌信息
			List<ProductTypeBrandVO> list = askBuyService.getProductTypeAndBrand();
			
			resultMap.put("list", list);
			resultMap.put("result", true);
			resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
		} catch(Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询产品分类和品牌信息时发生异常：" + e.getMessage());
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/getAskBuyHadNews", produces = "text/html;charset=UTF-8")
	public String getAskBuyHadNews(HttpServletRequest request,String lastTime, String jsoncallback) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
	
		try {
			//查询产品分类和品牌信息
			List<ProductTypeBrandVO> list = askBuyService.getProductTypeAndBrand();
			
			resultMap.put("list", list);
			resultMap.put("result", true);
			resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
		} catch(Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询产品分类和品牌信息时发生异常：" + e.getMessage());
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	
	/**
	 * 上传图片
	 * @param request
	 * @param userId
	 * @return
	 */
	private Map<String, Object> uploadPictures(HttpServletRequest request, String userId) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		//检测是否包含图片
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if(!isMultipart) {
			result.put("result", false);
			return result;
		}
		
		String imageSortName = getImageSortName(userId);	//获取图片分类名称
		String picture_uploadpath = XiuConstant.ASKBUY_PICTURE_UPLOADPATH;	//图片上传路径 
				
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;     
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	    
	    List<MultipartFile> picFileList = multipartRequest.getFiles("picFileList");	//原图文件列表
	    List<String> picUrlList = new ArrayList<String>(); //生成图片地址列表
	    
	    try {
	    	if(picFileList != null && picFileList.size() > 0 ) {
		    	 for(MultipartFile origPatch : picFileList) {
		    		 if (origPatch.isEmpty() || origPatch.getSize() <= 0 || origPatch.getOriginalFilename() == null) {
		    			 //如果图片为空
		    			 break;
		    		 }
		    		 
		    		 String fileName = origPatch.getOriginalFilename();
					 String suffix = fileName.substring(fileName.lastIndexOf("."));
					 // 限制上传文件格式为jpg,jepg,png,bmp
					 if (("*.jpg;*.jepg,*.png,*.bmp").indexOf(suffix.toLowerCase()) < 0) {
						 result.put("result", false);
						 result.put("errorCode", ErrorCode.PictureFormatLimit.getErrorCode());
						 result.put("errorMsg", ErrorCode.PictureFormatLimit.getErrorMsg());
						 return result;
					 }
						
					 // 文件大小 不能超过10MB
					 if (origPatch.getSize()>10485760) {
						 result.put("result", false);
						 result.put("errorCode", ErrorCode.PictureMoreThanSize.getErrorCode());
						 result.put("errorMsg", ErrorCode.PictureMoreThanSize.getErrorMsg());
						 return result;
					 }
						
					 String generateFileName = UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
					 String imgURL = "m/askbuy/" + imageSortName + "/" + userId + "/" + dateFormat.format(new Date()) + "/" + generateFileName;
					 String fullPicFileName = picture_uploadpath + "/" + imgURL;
					 // 创建上级文件夹
					 createFolder(fullPicFileName);
					 origPatch.transferTo(new File(fullPicFileName));// 上传图片
					 
					 picUrlList.add(imgURL); //添加原图生成地址
		    	 }
		    }
	    	
	    	result.put("result", true);
	    	result.put("picUrlList", picUrlList);
	    } catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("上传求购图片错误:", e);
		}
	    
		return result;
	}
	
	/**
	 * 获取图片分类名称：根据用户ID对5000取余
	 * @param userId
	 * @return
	 */
	private String getImageSortName(String userId) {
		long result = 0;
		if(StringUtils.isNotBlank(userId)) {
			Long id = Long.parseLong(userId);
			result = id%5000;
		}
		return String.valueOf(result);
	}
	
	/**
	 * 如果文件不存在就创建
	 * @param fullFileName
	 */
	public static void createFolder(String fullFileName) {
		File picFile = new File(fullFileName);
		String picParentPath = picFile.getParent();
		File picParentFile = new File(picParentPath);
		if (!picParentFile.exists()) {
			picParentFile.mkdirs();
		}
	}
	
	/**
	 * 校验求购申请参数
	 * @param mobile
	 * @return
	 */
	private boolean checkParams(String productNumber, String referenceUrl, String mobile, String productDesc) {
		boolean result = true;
		
		if(StringUtils.isNotBlank(productNumber)) {
			//商品货号不能超过50个字符
			if(productNumber.length() > 50) {
				result = false;
			}
		}
		if(StringUtils.isNotBlank(referenceUrl)) {
			//参考网址不能超300个字符
			if(referenceUrl.length() > 300) {
				result = false;
			}
		}
		if(StringUtils.isNotBlank(mobile)) {
			//手机号不能超过11位
			if(mobile.length() > 11) {
				result = false;
			}
		}
		if(StringUtils.isNotBlank(productDesc)) {
			//产品描述不能超过600个字符
			if(productDesc.length() > 600) {
				result = false;
			}
		}
		
		return result;
	}
}
