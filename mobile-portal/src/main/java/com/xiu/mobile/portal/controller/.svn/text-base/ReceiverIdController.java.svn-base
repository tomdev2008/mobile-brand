package com.xiu.mobile.portal.controller;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.model.BaseBackDataVo;
import com.xiu.mobile.portal.common.util.EncryptUtils;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.common.util.UUIDUtil;
import com.xiu.mobile.portal.facade.utils.HttpUtil;
import com.xiu.mobile.portal.model.AddressOutParam;
import com.xiu.mobile.portal.model.AddressVo;
import com.xiu.mobile.portal.model.LoginResVo;
import com.xiu.mobile.portal.model.QueryUserAddressDetailInParam;
import com.xiu.mobile.portal.model.UserAddressOperationInParam;
import com.xiu.mobile.portal.service.IAddressService;
import com.xiu.mobile.portal.service.ILoginService;
import com.xiu.mobile.portal.service.IReceiverIdService;
import com.xiu.mobile.portal.service.impl.AddressVoConvertor;
import com.xiu.mobile.portal.url.DomainURL;
import com.xiu.uuc.facade.dto.IdentityInfoDTO;

@Controller
@RequestMapping("/receiverId")
public class ReceiverIdController extends BaseController {
	
	private Logger logger = Logger.getLogger(ReceiverIdController.class);
	
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IReceiverIdService receiverIdService;
	@Autowired
	private IAddressService addressService;
	@Autowired
	private DomainURL domainURL;
	@Autowired
	private AddressVoConvertor addressVoConvertor;

	/**
	 * 处理上传身份证
	 * 
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String uploadFile(MultipartHttpServletRequest request, HttpServletResponse response,String jsoncallback) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String userId = SessionUtil.getUserId(request);
			String uId = request.getParameter("uId");
			// 如果url没带userId参数 则需要验证登录
			if (StringUtils.isBlank(uId)) {
				if (!checkLogin(request)) {
					result.put("result", false);
					result.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
					result.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
					return JsonUtils.bean2jsonP(jsoncallback, result);
				}
			}else{
				// userId 原本记录在cookie 为了适用微信二维码扫描上传用户地址身份证 增加url获取用户信息
				userId = uId;
				String aseKey = EncryptUtils.getAESKeySelf();
				userId = EncryptUtils.decryptByAES(userId, aseKey);
			}
			
			// 转型为MultipartHttpRequest：     
	        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;     
	        // 获得文件：     
			MultipartFile patch = multipartRequest.getFile("imgFile");
			// 判断是否获取到文件
			if (patch.isEmpty() || patch.getSize() <= 0 || patch.getOriginalFilename() == null) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg().concat(",没有接收到文件信息"));
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			String fileName = patch.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf("."));
			// 限制上传文件格式为jpg,jepg,png,bmp
			if (("*.jpg;*.jepg,*.png,*.bmp").indexOf(suffix.toLowerCase()) < 0) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", "限制上传文件格式为jpg,jepg,png,bmp");
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			// 文件大小 不能超过10MB
			if (patch.getSize()>10485760) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg().concat(",文件大小不能超过10MB"));
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			String transFileName  = userId.concat("-").concat(UUIDUtil.generateUUID()).concat(".jpg");
//			String imageSortName = UploadUtil.getImageSortName(userId);	//获取图片分类名称
//			String imgURL = "/" + imageSortName + "/" + userId + "/" + transFileName;
			String imgURL = "/" + userId + "/" + transFileName;
			String fullPicFile = domainURL.getIdPicUploadPath() + imgURL;
			//如果文件不存在则创建之
			createFile(fullPicFile);
			patch.transferTo(new File(fullPicFile));// 上传图片
			
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			result.put("imgURL", transFileName);
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("图片上传错误:", e);
		}
	
		logger.info("上传身份证响应结果："+ JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/***
	 * wap用户收货身份认证信息图片上传
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @param picFile 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/uploadFileDomains", produces = "text/html;charset=UTF-8")
	public String uploadImgTwoDomains(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		Map<String, Object> result = new HashMap<String, Object>();
		String toURL = request.getParameter("toURL");
		if(null == toURL || toURL.trim().equals("")){
			toURL = "http://m.xiu.com/myxiu/upload_redirect.html";	
		}
		
		try {
			// 检查用户登录状态
			if (!checkLogin(request)) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
				result.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			// 转型为MultipartHttpRequest：     
	        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;     
	        // 获得文件：     
			MultipartFile patch = multipartRequest.getFile("imgFile");
			// 判断是否获取到文件
			if (patch.isEmpty() || patch.getSize() <= 0 || patch.getOriginalFilename() == null) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg().concat(",没有接收到文件信息"));
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			String fileName = patch.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf("."));
			// 限制上传文件格式为jpg,jepg,png,bmp
			if (("*.jpg;*.jepg,*.png,*.bmp").indexOf(suffix.toLowerCase()) < 0) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", "限制上传文件格式为jpg,jepg,png,bmp");
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			// 文件大小 不能超过10MB
			if (patch.getSize()>10485760) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg().concat(",文件大小不能超过10MB"));
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			String userId = SessionUtil.getUserId(request);
			String transFileName  = userId.concat("-").concat(UUIDUtil.generateUUID()).concat(".jpg");
//			String imageSortName = UploadUtil.getImageSortName(userId);	//获取图片分类名称
//			String imgURL = "/" + imageSortName + "/"+ userId + "/" + transFileName;
			String imgURL = "/" + userId + "/" + transFileName;
			String fullPicFile = domainURL.getIdPicUploadPath() + imgURL;
			//如果文件不存在则创建之
			createFile(fullPicFile);
			patch.transferTo(new File(fullPicFile));// 上传图片

			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			result.put("imgURL", transFileName);
			
			StringBuffer respData = new StringBuffer(); 
			respData.append("?result=true");
			respData.append("&errorCode=" +  ErrorCode.Success.getErrorCode());
			respData.append("&errorMsg=" + ErrorCode.Success.getErrorMsg());
			respData.append("&imgURL=" + transFileName);
			
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
		return null;
	}
	
	/***
	 * 图片删除
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @param picFile 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteImg", produces = "text/html;charset=UTF-8")
	public String deleteImg(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			
			String userId = SessionUtil.getUserId(request);
			String uId = request.getParameter("uId");
			// 如果url没带userId参数 则需要验证登录
			if (StringUtils.isBlank(uId)) {
				// 检查用户登陆状态
				if(!checkLogin(request)) {
					result.put("result", false);
					result.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
					result.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
					return JsonUtils.bean2jsonP(jsoncallback, result);
				}
			}else{
				// userId 原本记录在cookie 为了适用微信二维码扫描上传用户地址身份证 增加url获取用户信息
				userId = uId;
				String aseKey = EncryptUtils.getAESKeySelf();
				userId = EncryptUtils.decryptByAES(userId, aseKey);
			}
			
			
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
			if(imgURL.indexOf("/") > -1) {
				//新上传的图片路径
				imgURL = domainURL.getIdPicUploadPath() + imgURL;
			} else {
				imgURL = domainURL.getIdPicUploadPath().concat("/").concat(userId).concat("/").concat(imgURL);
			}
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
	
	@RequestMapping("/showImg")
	public void showImg(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		try {
			String userId = SessionUtil.getUserId(request);
			String uId = request.getParameter("uId");
			// 如果url没带userId参数 则需要验证登录
			if (StringUtils.isNotBlank(uId)) {
				// userId 原本记录在cookie 为了适用微信二维码扫描上传用户地址身份证 增加url获取用户信息
				userId = uId;
				String aseKey = EncryptUtils.getAESKeySelf();
				userId = EncryptUtils.decryptByAES(userId, aseKey);
			}
			
			// 获取传递参数拼装URL
			String imgURL = request.getParameter("imgURL");
			logger.info("获取用户身份证信息，获取用户userId："+userId);
			logger.info("获取身份证图片信息imgURL="+imgURL);
			// 检查用户登陆状态
			if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(imgURL)) {
				if(imgURL.indexOf("/") > -1) {
					//新上传的图片路径
					imgURL = domainURL.getIdPicUploadPath() + imgURL;
				} else {
					imgURL = domainURL.getIdPicUploadPath().concat("/").concat(userId).concat("/").concat(imgURL);
				}
				logger.info("获取身份证图片信息完整URL="+imgURL);
				File imgFile = new File(imgURL);
				if (imgFile.exists()) {
					// 在内存中创建图象
					BufferedImage image = ImageIO.read(new FileInputStream(imgFile));  
					// 输出图象到页面
					ImageIO.write(image, "JPEG", response.getOutputStream());
				}
			}else{
				logger.info("取不到用户信息");
			}
		} catch (Exception e) {
			logger.error("获取用户身份证图片失败",e);
		}
	}
	
	/**
	 * 获取收货地址数据更新数据
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/getUpdateInfo", produces = "text/html;charset=UTF-8")
	public String getUpdateInfo(HttpServletRequest request,HttpServletResponse response,String jsoncallback)  {	
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		
		try {
			if (checkLogin(request)) {
				// 收货地址
				String addressId = request.getParameter("addressId");
				if (StringUtils.isNotBlank(addressId)) {
					// addressId、userId解密(AES)
					String aesKey = EncryptUtils.getAESKeySelf();
					String url = "http://m.xiu.com/myxiu/uploadIdCardImg.html";
					String userId = SessionUtil.getUserId(request);
					userId = EncryptUtils.encryptByAES(userId, aesKey);
					
					// 拼装url
					url = url.concat("?addressId=").concat(addressId).concat("&uId=").concat(userId);
					addressId = EncryptUtils.decryptByAES(addressId, aesKey);
					QueryUserAddressDetailInParam addressDetailInParam = new QueryUserAddressDetailInParam();
					addressDetailInParam.setAddressId(addressId);
					SessionUtil.setDeviceInfo(request, addressDetailInParam);
					AddressOutParam addressOutParam = addressService.getAddress(addressDetailInParam);
					if (addressOutParam != null) {
						result.put("result", true);
						result.put("errorCode", ErrorCode.Success.getErrorCode());
						result.put("errorMsg", ErrorCode.Success.getErrorMsg());
						result.put("receiverName", addressOutParam.getRcverName());
						result.put("reward", "登记号码送30元，上传图片再送20元。");
						result.put("uId", userId);
						result.put("url", url);
					}else {
						result.put("result", false);
						result.put("errorCode", ErrorCode.GetAddressListFailed.getErrorCode());
						result.put("errorMsg", ErrorCode.GetAddressListFailed.getErrorMsg());
					}
				}else{
					result.put("result", false);
					result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
					result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				}
			}else {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
				result.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
			}
		}catch(EIBusinessException e){
			String errCode = e.getErrCode();
			// 90305   为EI获取收货地址失败所抛异常
			if (errCode.contains("90305")) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.GetAddressInfoFailed.getErrorCode());
				result.put("errorMsg", ErrorCode.GetAddressInfoFailed.getErrorMsg());
			}else{
				result.put("result", false);
				result.put("errorCode", e.getExtErrCode());
				result.put("errorMsg", e.getExtMessage());
			}
			logger.error("获取收货地址数据时发生异常:" + e.getMessageWithSupportCode(),e);
		}catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取收货地址数据更新地址发生异常：" + e.getMessage(),e);
		}

		logger.info("获取收货地址数据更新地址响应结果："+ JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/**
	 * 获取收货地址数据接口
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/getUpdateAddress", produces = "text/html;charset=UTF-8")
	public String getUpdateAddress(HttpServletRequest request,HttpServletResponse response,String jsoncallback)  {	
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		
		try {
			// 收货地址
			String addressId = request.getParameter("addressId");
			if (StringUtils.isNotBlank(addressId)) {
				// addressId解密(AES)
				String aesKey = EncryptUtils.getAESKeySelf();
				addressId = EncryptUtils.decryptByAES(addressId, aesKey);
				QueryUserAddressDetailInParam addressDetailInParam = new QueryUserAddressDetailInParam();
				addressDetailInParam.setAddressId(addressId);
				SessionUtil.setDeviceInfo(request, addressDetailInParam);
				AddressOutParam addressOutParam = addressService.getAddress(addressDetailInParam);
				
				if (addressOutParam != null) {
					result.put("result", true);
					result.put("errorCode", ErrorCode.Success.getErrorCode());
					result.put("errorMsg", ErrorCode.Success.getErrorMsg());
					result.put("receiverName", addressOutParam.getRcverName());
					result.put("reward", "登记号码送30元，上传图片再送20元。");
					Long identityId = addressOutParam.getIdentityId();
					if (identityId != null && identityId.longValue() != 0 ) {
						IdentityInfoDTO identityInfoDTO = receiverIdService.queryIdentityInfoById(identityId);
						if (identityInfoDTO != null) {
							result.put("idName", identityInfoDTO.getIdName());
							result.put("idNumber", identityInfoDTO.getIdNumber());
							result.put("idHeads", identityInfoDTO.getIdHeads());
							result.put("idTails", identityInfoDTO.getIdTails());
							// 审核状态  0 - 待审核, 1 - 审核通过, 2 - 审核不通过
							result.put("reviewState", identityInfoDTO.getReviewState());
						}
					}
				}else{
					result.put("result", false);
					result.put("errorCode", ErrorCode.GetAddressInfoFailed.getErrorCode());
					result.put("errorMsg", ErrorCode.GetAddressInfoFailed.getErrorMsg());
				}
			}else {
				result.put("result", false);
				result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			}
		} catch(EIBusinessException e){
			String errCode = e.getErrCode();
			// 90305   为EI获取收货地址失败所抛异常
			if (errCode.contains("90305")) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.GetAddressInfoFailed.getErrorCode());
				result.put("errorMsg", ErrorCode.GetAddressInfoFailed.getErrorMsg());
			}else{
				result.put("result", false);
				result.put("errorCode", e.getExtErrCode());
				result.put("errorMsg", e.getExtMessage());
			}
			logger.error("获取收货地址数据时发生异常:" + e.getMessageWithSupportCode(),e);
		}catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取收货地址数据时发生异常：" + e.getMessage(),e);
		}

		logger.info("获取收货地址数据响应结果："+ JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/**
	 * 更新收货地址数据接口
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAddress", produces = "text/html;charset=UTF-8")
	public String updateAddress(HttpServletRequest request,HttpServletResponse response,String jsoncallback)  {	
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		String addressId = request.getParameter("addressId"); // 收货地址
		String idName = request.getParameter("idName"); // 身份证姓名
		String idCard = request.getParameter("idCard"); // 身份证号码
		String idHead = request.getParameter("idHead"); // 身份证正面
		String idTails = request.getParameter("idTails"); // 身份证反面信息
		String uId = request.getParameter("uId"); // 用户userId信息
		logger.info("wap用户扫描二维码上传身份证参数信息 params:"+request.getQueryString());
		
		try {
			// 用户Id都不为空的时候才能进行数据更新，身份证号码不能为空，身份证图片要么都为空，要么都不为空
			if (StringUtils.isNotBlank(idCard) && StringUtils.isNotBlank(addressId) && StringUtils.isNotBlank(uId)
					&&((StringUtils.isNotBlank(idHead) && StringUtils.isNotBlank(idTails))||(StringUtils.isBlank(idHead) && StringUtils.isBlank(idTails)))) {
				// addressId、userId解密(AES)
				String aesKey = EncryptUtils.getAESKeySelf();
				String userId = EncryptUtils.decryptByAES(uId, aesKey);
				addressId = EncryptUtils.decryptByAES(addressId, aesKey);
				QueryUserAddressDetailInParam addressDetailInParam = new QueryUserAddressDetailInParam();
				addressDetailInParam.setAddressId(addressId);
				SessionUtil.setDeviceInfo(request, addressDetailInParam);
				AddressOutParam addressOutParam = addressService.getAddress(addressDetailInParam);
				
				// 收货地址不为空且userId一致才进行操作  因hessinal 接口无法获取userId数据 故暂屏蔽userId判断
				//if (addressOutParam != null && addressOutParam.getUserId().equals(userId)) {
				// 收货地址不为空
				if (addressOutParam != null) { 
					BaseBackDataVo resultData = null;
					Long identityId = addressOutParam.getIdentityId();
					Long identityId2 = null;	//用来更新收货地址的身份信息ID
					IdentityInfoDTO identityInfoDTO = null;
					if(identityId != null && identityId.longValue() != 0 ) {
						identityInfoDTO = receiverIdService.queryIdentityInfoById(identityId);
						if(identityInfoDTO != null) {
							//如果身份信息存在，则更新
							if(identityInfoDTO.getReviewState() != null && identityInfoDTO.getReviewState().equals(1)) {
								result.put("result", false);
								result.put("errorCode", ErrorCode.UpdateAddressFailed.getErrorCode());
								result.put("errorMsg", ErrorCode.UpdateAddressFailed.getErrorMsg().concat(",身份认证参数不完善"));
								logger.info("更新收货地址数据响应结果："+ JSON.toJSONString(result));
								return JsonUtils.bean2jsonP(jsoncallback, result);
							}
							logger.info("绑定用户收货地址身份认证！");
							IdentityInfoDTO identityInfoDTONew = new IdentityInfoDTO();
							identityInfoDTONew.setReceiverName(addressOutParam.getRcverName());	//收货人名称
							idName = StringUtils.isNotBlank(idName) ? idName : addressOutParam.getRcverName(); //身份证姓名 如果不传 则取收货人姓名
							identityInfoDTONew.setIdName(idName);			//身份证名称
							identityInfoDTONew.setIdNumber(idCard);
							identityInfoDTONew.setIdHeads(idHead);
							identityInfoDTONew.setIdTails(idTails);
							identityInfoDTONew.setReviewState(0);
							identityInfoDTONew.setUserId(Long.parseLong(userId));
							identityInfoDTONew.setIdentityId(identityInfoDTO.getIdentityId());
							
							//修改身份信息
							receiverIdService.updateIdentityInfo(identityInfoDTONew);
						} else {
							//如果身份信息不存在，则新增
							identityInfoDTO = new IdentityInfoDTO();
							identityInfoDTO.setCreateTime(new Date());
							identityInfoDTO.setReceiverName(addressOutParam.getRcverName());	//收货人名称
							idName = StringUtils.isNotBlank(idName) ? idName : addressOutParam.getRcverName(); //身份证姓名 如果不传 则取收货人姓名
							identityInfoDTO.setIdName(idName);			//身份证名称
							identityInfoDTO.setIdNumber(idCard);
							identityInfoDTO.setIdHeads(idHead);
							identityInfoDTO.setIdTails(idTails);
							identityInfoDTO.setReviewState(0);
							identityInfoDTO.setUserId(Long.parseLong(userId));
							
							 //新增身份信息	
							identityId2 = receiverIdService.insertIdentityInfo(identityInfoDTO);
						}
					} else {
						//如果身份信息不存在，则新增
						identityInfoDTO = new IdentityInfoDTO();
						identityInfoDTO.setCreateTime(new Date());
						identityInfoDTO.setReceiverName(addressOutParam.getRcverName());	//收货人名称
						idName = StringUtils.isNotBlank(idName) ? idName : addressOutParam.getRcverName(); //身份证姓名 如果不传 则取收货人姓名
						identityInfoDTO.setIdName(idName);			//身份证名称
						identityInfoDTO.setIdNumber(idCard);
						identityInfoDTO.setIdHeads(idHead);
						identityInfoDTO.setIdTails(idTails);
						identityInfoDTO.setReviewState(0);
						identityInfoDTO.setUserId(Long.parseLong(userId));
						
						 //新增身份信息	
						identityId2 = receiverIdService.insertIdentityInfo(identityInfoDTO);
					}
					
					//新增身份信息时，更新收货地址的身份信息ID
					if(identityId2 != null) {
						//修改收货地址的身份信息
						addressOutParam.setIdentityId(identityId2);
						AddressVo addressVo = addressVoConvertor.Converter2AddressVo(addressOutParam);
						UserAddressOperationInParam addressInParam = addressVoConvertor.Converter2AddressInParam(addressVo);
						addressInParam.setUserId(userId);
						resultData = addressService.updateAddress(addressInParam);
						if (resultData.isSuccess()) {
							result.put("result", true);
							result.put("errorCode", ErrorCode.Success.getErrorCode());
							result.put("errorMsg", ErrorCode.Success.getErrorMsg());
						}
					} else {
						result.put("result", true);
						result.put("errorCode", ErrorCode.Success.getErrorCode());
						result.put("errorMsg", ErrorCode.Success.getErrorMsg());
					}
					
//					UserIdentityDTO userIdentityDTO = receiverIdService.getUserIdentity(userId, addressId);
//					// 如果已存在信息则修改否则新增
//					if (userIdentityDTO != null && userIdentityDTO.getAddressId() != null) {
//						// 是否审核0:未审核 1:审核通过 2:审核不通过
//						if (userIdentityDTO.getIsReview().longValue() == 1) {
//							result.put("result", false);
//							result.put("errorCode", ErrorCode.UpdateAddressFailed.getErrorCode());
//							result.put("errorMsg", ErrorCode.UpdateAddressFailed.getErrorMsg().concat(",身份认证参数不完善"));
//							logger.info("更新收货地址数据响应结果："+ JSON.toJSONString(result));
//							return JsonUtils.bean2jsonP(jsoncallback, result);
//						}
//						logger.info("绑定用户收货地址身份认证！");
//		            	// 保存成功后绑定用户收货地址身份证相关信息
//		            	UserIdentityDTO newUserIdentityDTO = new UserIdentityDTO();
//		            	newUserIdentityDTO.setAddressId(Long.parseLong(addressId));
//		            	newUserIdentityDTO.setLastModifyDate(new Date());
//		            	newUserIdentityDTO.setIdHeads(idHead);
//		            	newUserIdentityDTO.setIdName(addressOutParam.getRcverName());
//		            	newUserIdentityDTO.setIdNumber(idCard);
//		            	newUserIdentityDTO.setIdTails(idTails);
//		            	newUserIdentityDTO.setIsReview(0L);
//		            	newUserIdentityDTO.setUserId(Long.parseLong(userId));
//		            	// 持久化到数据库
//		            	receiverIdService.modifyUserIdentity(newUserIdentityDTO);
//					}else{
//		            	// 保存成功后绑定用户收货地址身份证相关信息
//		            	userIdentityDTO = new UserIdentityDTO();
//		            	userIdentityDTO.setAddressId(Long.parseLong(addressOutParam.getAddressId()));
//		            	userIdentityDTO.setLastModifyDate(new Date());
//		            	userIdentityDTO.setIdHeads(idHead);
//		            	userIdentityDTO.setIdName(addressOutParam.getRcverName());
//		            	userIdentityDTO.setIdNumber(idCard);
//		            	userIdentityDTO.setIdTails(idTails);
//		            	userIdentityDTO.setIsReview(0L);
//		            	userIdentityDTO.setUserId(Long.parseLong(userId));
//		            	// 持久化到数据库
//		            	receiverIdService.insertUserIdentity(userIdentityDTO);
//					}	
//					result.put("result", true);
//					result.put("errorCode", ErrorCode.Success.getErrorCode());
//					result.put("errorMsg", ErrorCode.Success.getErrorMsg());
				}else{
					result.put("result", false);
					result.put("errorCode", ErrorCode.GetAddressListFailed.getErrorCode());
					result.put("errorMsg", ErrorCode.GetAddressListFailed.getErrorMsg());
				}
			}else {
				result.put("result", false);
				result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg().concat(",身份认证参数不完善"));
			}
		} catch(EIBusinessException e){
			result.put("result", false);
			result.put("errorCode", ErrorCode.UpdateAddressFailed.getErrorCode());
			result.put("errorMsg", ErrorCode.UpdateAddressFailed.getErrorMsg());
			logger.error("更新收货地址数据时发生异常:" + e.getMessageWithSupportCode(),e);
		}catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("更新收货地址数据时发生异常：" + e.getMessage(),e);
		}

		logger.info("更新收货地址数据响应结果："+ JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/***
	 * 查询地址是否审核信息
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/identityInfo", produces = "text/html;charset=UTF-8")
	public String identityInfo(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			// 检查用户登陆状态
			if(!checkLogin( request)) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
				result.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			String addressId = request.getParameter("addressId");
			// addressId解密(AES)
			String aesKey=EncryptUtils.getAESKeySelf();
			String adI=EncryptUtils.decryptByAES(addressId, aesKey);
						
			if (StringUtils.isNotBlank(adI)) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			}
			
			QueryUserAddressDetailInParam addressDetailInParam = new QueryUserAddressDetailInParam();
			addressDetailInParam.setAddressId(adI);
			SessionUtil.setDeviceInfo(request, addressDetailInParam);
			AddressOutParam addressOutParam = addressService.getAddress(addressDetailInParam); //查询收货地址信息
			
			Long identityId = addressOutParam.getIdentityId();
			if(identityId != null && identityId.longValue() != 0 ) {
				//如果地址的身份信息ID不为空
				IdentityInfoDTO identityInfoDTO = receiverIdService.queryIdentityInfoById(identityId);
				if(identityInfoDTO != null) {
					result.put("result", true);
					result.put("errorCode", ErrorCode.Success.getErrorCode());
					result.put("errorMsg", ErrorCode.Success.getErrorMsg());
					result.put("isReview", identityInfoDTO.getReviewState()); // 是否审核0:未审核 1:审核通过 2:审核不通过
				} else {
					result.put("result", false);
					result.put("errorCode", ErrorCode.SystemError.getErrorCode());
					result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
				}
			} else {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			}
			
//			LoginResVo user = SessionUtil.getUser(request);
//			UserIdentityDTO userIdentityDto = receiverIdService.getUserIdentity(user.getUserId(),adI);
//			if (userIdentityDto!=null) {
//				result.put("result", true);
//				result.put("errorCode", ErrorCode.Success.getErrorCode());
//				result.put("errorMsg", ErrorCode.Success.getErrorMsg());
//				result.put("isReview", userIdentityDto.getIsReview()); // 是否审核0:未审核 1:审核通过 2:审核不通过
//			}else{
//				result.put("result", false);
//				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
//				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
//			}
		} catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("查询地址是否审核发生异常：" + e.getMessageWithSupportCode(), e);
		}  catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询地址是否审核信息错误:", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	protected boolean checkLoginStatus(LoginResVo user, HttpServletRequest request) {
		if(user == null || StringUtils.isEmpty(user.getTokenId())
				|| StringUtils.isEmpty(user.getUserId())) {
			return false;
		}
		
		// 添加IP信息，登录，获取用户状态等接口需要。
		String remoteIp = HttpUtil.getRemoteIpAddr(request);
		return loginService.checkOnlineStatus(user.getTokenId(), user.getUserId(), remoteIp);
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
}
