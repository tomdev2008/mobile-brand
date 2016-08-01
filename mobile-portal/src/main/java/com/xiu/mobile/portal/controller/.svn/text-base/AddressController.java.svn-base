package com.xiu.mobile.portal.controller;
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

import com.xiu.common.lang.StringUtil;
import com.xiu.mobile.core.exception.BaseException;
import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.core.utils.ObjectUtil;
import com.xiu.mobile.portal.common.constants.AddressConstant;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.model.BaseBackDataVo;
import com.xiu.mobile.portal.common.util.EncryptUtils;
import com.xiu.mobile.portal.common.util.ResponseUtil;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.common.util.UnicodeUtils;
import com.xiu.mobile.portal.model.AddressAddOutParam;
import com.xiu.mobile.portal.model.AddressDataListVo;
import com.xiu.mobile.portal.model.AddressListQueryInParam;
import com.xiu.mobile.portal.model.AddressOutParam;
import com.xiu.mobile.portal.model.AddressUpdateOutParam;
import com.xiu.mobile.portal.model.AddressUpdateVo;
import com.xiu.mobile.portal.model.AddressVo;
import com.xiu.mobile.portal.model.ProvinceRegionCityListOutParam;
import com.xiu.mobile.portal.model.ProvinceRegionCityVo;
import com.xiu.mobile.portal.model.QueryAddressParamInParam;
import com.xiu.mobile.portal.model.QueryUserAddressDetailInParam;
import com.xiu.mobile.portal.model.QueryUserAddressListOutParam;
import com.xiu.mobile.portal.model.UserAddressOperationInParam;
import com.xiu.mobile.portal.service.IAddressService;
import com.xiu.mobile.portal.service.IReceiverIdService;
import com.xiu.mobile.portal.service.impl.AddressUpdateVoConvertor;
import com.xiu.mobile.portal.service.impl.AddressVoConvertor;
import com.xiu.mobile.portal.url.DomainURL;
import com.xiu.uuc.facade.dto.IdentityInfoDTO;

@Controller
@RequestMapping("/address")
public class AddressController extends BaseController {
	
	private Logger logger = Logger.getLogger(AddressController.class);
	@Autowired
	private IAddressService addressService;
	@Autowired
	private AddressVoConvertor addressVoConvertor;
	@Autowired
	private AddressUpdateVoConvertor addressUpdateVoConvertor;
	@Autowired
	private IReceiverIdService receiverIdService;
	@Autowired
	private DomainURL domainURL;
	/**
	 * 初始化新增页面数据
	 */
	@ResponseBody
	@RequestMapping(value = "/initAddAddressRemote")
	public String initAddAddress(HttpServletRequest request,HttpServletResponse response,String jsoncallback)  {
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		try {
			ProvinceRegionCityListOutParam addressParam = getPRC(request,"100086", AddressConstant.PARAM_TYPE_PROVINCE);
			if (!addressParam.isOverTime()) {
				if (addressParam.isSuccess()) {
					List<ProvinceRegionCityVo> prcVos = addressParam.getPrcList();
					model.put("result", true);
					model.put("errorCode", ErrorCode.Success.getErrorCode());
					model.put("errorMsg", ErrorCode.Success.getErrorMsg());
					model.put("provinces", prcVos);
				}
			} else {
				model.put("result", false);
				model.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
				model.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
			}
		}catch(EIBusinessException e){
			model.put("result", false);
			model.put("errorCode", ErrorCode.getPrcVosListFailed.getErrorCode());
			model.put("errorMsg", ErrorCode.getPrcVosListFailed.getErrorMsg());
			logger.error("初始化新增页面数据时发生异常:" + e.getMessageWithSupportCode(),e);
		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("初始化新增页面数据时发生异常:" + e.getMessage(),e);
		}
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}

	/**
	 * 
	 * 保存新增收货地址
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/saveAddressRemote",produces = "text/html;charset=UTF-8")
	public String saveAddress(HttpServletRequest request,HttpServletResponse response,String jsoncallback)  {
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		String rcverName = request.getParameter("addressVo.rcverName");
		String provinceCode = request.getParameter("addressVo.provinceCode");
		String regionCode = request.getParameter("addressVo.regionCode");
		String cityCode = request.getParameter("addressVo.cityCode");
		String postCode = request.getParameter("addressVo.postCode");
		String mobile = request.getParameter("addressVo.mobile");
		String addressInfo = request.getParameter("addressVo.addressInfo");
		String isMaster=request.getParameter("addressVo.isMaster");// 设为默认
		String idCard = request.getParameter("addressVo.idCard"); // 身份证号码
		String idHead = request.getParameter("addressVo.idHead"); // 身份证正面
		String idTails = request.getParameter("addressVo.idTails"); // 身份证反面信息
		Boolean idAuthorized=false;//身份证是否认证
		try {
			// 身份证正面图片、反面图片当都为空 或者都不为空的时候才能进行数据更新
			if ((StringUtils.isNotBlank(idHead)&&StringUtils.isNotBlank(idTails))||(StringUtils.isBlank(idHead)&&StringUtils.isBlank(idTails))) {
			
				AddressVo addressVo = new AddressVo();
				addressVo.setRcverName(rcverName);
				addressVo.setProvinceCode(provinceCode);
				addressVo.setRegionCode(regionCode);
				addressVo.setCityCode(cityCode);
				addressVo.setPostCode(postCode);
				addressVo.setMobile(mobile);
				addressVo.setAddressInfo(addressInfo);
				addressVo.setIsMaster(isMaster);
				addressVo.setIdCard(idCard);
				addressVo.setIdHead(idHead);
				addressVo.setIdTails(idTails);
				
				String tokenId = SessionUtil.getTokenId(request);
				UserAddressOperationInParam addressInParam = addressVoConvertor.Converter2AddressInParam(addressVo);
				addressInParam.setTokenId(tokenId);
				SessionUtil.setDeviceInfo(request, addressInParam);
				//AddressAddOutParam addAddress = addressService.addAddress(addressInParam);
				AddressAddOutParam addAddress = addressService.addAddressNew(addressInParam);	
				if (addAddress.isSuccess()) {
					// addressId加密(AES)
					String aesKey = EncryptUtils.getAESKeySelf();
					String addressId=EncryptUtils.encryptByAES(addAddress.getAddressId(), aesKey);
					addAddress.setAddressId(addressId);
					if(addressVo.getIdCard()!=null&&!addressVo.getIdCard().equals("")){//身份证有录入则为认证
						idAuthorized=true;
					}
					model.put("result", true);
					model.put("errorCode", ErrorCode.Success.getErrorCode());
					model.put("errorMsg", ErrorCode.Success.getErrorMsg());
					model.put("addAddress", addAddress);
				} else {
					if (isRepeat(addAddress)) {
						model.put("result", false);
						model.put("errorCode", ErrorCode.AddressRepeated.getErrorCode());
						model.put("errorMsg", ErrorCode.AddressRepeated.getErrorMsg());
					} else {
						model.put("result", false);
						model.put("errorCode", ErrorCode.AddAddressFailed.getErrorCode());
						model.put("errorMsg", ErrorCode.AddAddressFailed.getErrorMsg());
						logger.error("保存新增收货地址失败：[{错误代码:" + addAddress.getRetCode() + "},{错误信息:" + addAddress.getErrorMsg() + "}]");
					}
				}
			}else{
				model.put("result", false);
				model.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				model.put("errorMsg", ErrorCode.MissingParams.getErrorMsg().concat(",身份认证参数不完善"));
			}
		} catch(EIBusinessException e){
			model.put("result", false);
			model.put("errorCode", ErrorCode.AddAddressFailed.getErrorCode());
			model.put("errorMsg", ErrorCode.AddAddressFailed.getErrorMsg());
			logger.error("保存新增收货地址时发生异常:" + e.getMessageWithSupportCode(),e);
		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("保存新增收货地址时发生异常：" + e.getMessage(),e);
		}
		model.put("idAuthorized", idAuthorized);
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}
	
	private boolean isRepeat(BaseBackDataVo baseBackDataVo) {
		if (baseBackDataVo.getRetCode().equals("002")) {
			return true;
		}
		return false;
	}

	/**
	 * 更新收货地址数据接口
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAddressRemote")
	public String updateAddress(HttpServletRequest request,HttpServletResponse response,String jsoncallback)  {
        //原来从cookie里获取的东西现在都从参数里得到了
		//从url里获取uId(userId)并解密,获取tId(tokenId)
		//如果有参数fromWWW=Y,且aId(addressId需要解密) 不为空，则去接口里查询这些，从参数里只获取身份证号码，正面，反面信息进行覆盖
		
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		String addressId = request.getParameter("addressVo.addressId");
		String rcverName = request.getParameter("addressVo.rcverName");
		String provinceCode = request.getParameter("addressVo.provinceCode");
		String regionCode = request.getParameter("addressVo.regionCode");
		String cityCode = request.getParameter("addressVo.cityCode");
		String postCode = request.getParameter("addressVo.postCode");
		String mobile = request.getParameter("addressVo.mobile");
		String addressInfo = request.getParameter("addressVo.addressInfo");
		String isMaster=request.getParameter("addressVo.isMaster");
		String idCard = request.getParameter("addressVo.idCard"); // 身份证号码
		String idHead = request.getParameter("addressVo.idHead"); // 身份证正面
		String idTails = request.getParameter("addressVo.idTails"); // 身份证反面信息
		Boolean idAuthorized=false;//身份证是否认证

		try {
			// 身份证正面图片、反面图片当都为空 或者都不为空的时候才能进行数据更新
			if ((StringUtils.isNotBlank(idHead)&&StringUtils.isNotBlank(idTails)) || StringUtils.isBlank(idHead)&&StringUtils.isBlank(idTails)) {
				AddressVo addressVo = new AddressVo();
				// addressId解密(AES)
				String aseKey=EncryptUtils.getAESKeySelf();
				addressId=EncryptUtils.decryptByAES(addressId, aseKey);
				
				addressVo.setAddressId(addressId);
				addressVo.setRcverName(rcverName);
				addressVo.setProvinceCode(provinceCode);
				addressVo.setRegionCode(regionCode);
				addressVo.setCityCode(cityCode);
				addressVo.setPostCode(postCode);
				addressVo.setMobile(mobile);
				addressVo.setAddressInfo(addressInfo);
				addressVo.setIdCard(idCard);
				addressVo.setIdHead(idHead);
				addressVo.setIdTails(idTails);
				addressVo.setIsMaster(isMaster);
				String tokenId = SessionUtil.getTokenId(request);
				UserAddressOperationInParam addressInParam = addressVoConvertor.Converter2AddressInParam(addressVo);
				addressInParam.setTokenId(tokenId);
				SessionUtil.setDeviceInfo(request, addressInParam);
				String userId = SessionUtil.getUserId(request);
				logger.info("修改收货人地址信息");
				
				//如果身份证号码或者身份证图片不为空，则先保存身份证信息，把身份证信息ID设置到收货地址中，再保存收货地址
				if (StringUtil.isNotBlank(addressInParam.getIdCard()) || (StringUtil.isNotBlank(addressInParam.getIdHead()) && StringUtil.isNotBlank(addressInParam.getIdTails()))) {
					//根据用户ID和收货人名称查询用户的身份认证信息是否存在
					IdentityInfoDTO identityInfoDTO = receiverIdService.queryIdentityInfoByUserIdAndName(Long.parseLong(userId), addressVo.getRcverName());
					if(identityInfoDTO != null) {
						//如果身份认证信息存在，则更新
						IdentityInfoDTO identityInfoDTONew  = new IdentityInfoDTO();
						if (identityInfoDTO.getIdNumber()!=null && (!identityInfoDTO.getIdNumber().equals(addressInParam.getIdCard()))) {
							identityInfoDTONew.setReviewState(0);
						}
		            	if (identityInfoDTO.getIdHeads()!=null && (!identityInfoDTO.getIdHeads().equals(addressInParam.getIdHead()))) {
		            		identityInfoDTONew.setReviewState(0);
						}
		            	if (identityInfoDTO.getIdTails()!=null && (!identityInfoDTO.getIdTails().equals(addressInParam.getIdTails()))) {
		            		identityInfoDTONew.setReviewState(0);
						}
	
		            	identityInfoDTONew.setIdName(addressInParam.getRcverName());
		            	identityInfoDTONew.setReceiverName(addressInParam.getRcverName());
		            	identityInfoDTONew.setIdNumber(addressInParam.getIdCard());
		            	identityInfoDTONew.setIdHeads(addressInParam.getIdHead());
		            	identityInfoDTONew.setIdTails(addressInParam.getIdTails());
		            	identityInfoDTONew.setUserId(Long.parseLong(userId));
		            	identityInfoDTONew.setIdentityId(identityInfoDTO.getIdentityId());
		            	
						receiverIdService.updateIdentityInfo(identityInfoDTONew);	//修改身份信息
						addressInParam.setIdentityId(identityInfoDTO.getIdentityId()); //收货地址中设置身份信息ID
					} else {
						//如果身份认证信息不存在，则新增
						identityInfoDTO = new IdentityInfoDTO();
						identityInfoDTO.setIdName(addressInParam.getRcverName());
						identityInfoDTO.setReceiverName(addressInParam.getRcverName());
						identityInfoDTO.setIdNumber(addressInParam.getIdCard());
						identityInfoDTO.setIdHeads(addressInParam.getIdHead());
						identityInfoDTO.setIdTails(addressInParam.getIdTails());
						identityInfoDTO.setUserId(Long.parseLong(userId));
						identityInfoDTO.setReviewState(0);
						identityInfoDTO.setCreateTime(new Date());
						Long identityId = receiverIdService.insertIdentityInfo(identityInfoDTO);	//新增身份信息
						if(identityId != null) {
							addressInParam.setIdentityId(identityId);  //收货地址中设置身份信息ID
						}
					}
						
						
				}
				//更新收货地址信息
				BaseBackDataVo updateAddress = addressService.updateAddress(addressInParam);
				if (updateAddress.isSuccess()) {
					if(addressVo.getIdCard()!=null&&!addressVo.getIdCard().equals("")){
						idAuthorized=true;
					}
					model.put("result", true);
					model.put("errorCode", ErrorCode.Success.getErrorCode());
					model.put("errorMsg", ErrorCode.Success.getErrorMsg());
				}
			}else{
				model.put("result", false);
				model.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				model.put("errorMsg", ErrorCode.MissingParams.getErrorMsg().concat(",身份认证参数不完善"));
			}
		} catch(EIBusinessException e){
			model.put("result", false);
			model.put("errorCode", ErrorCode.UpdateAddressFailed.getErrorCode());
			model.put("errorMsg", ErrorCode.UpdateAddressFailed.getErrorMsg());
			logger.error("更新收货地址数据时发生异常:" + e.getMessageWithSupportCode(),e);
		}catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("更新收货地址数据时发生异常：" + e.getMessage(),e);
		}
		model.put("idAuthorized", idAuthorized);
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}

	/**
	 * 
	 * 显示收货地址详情
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/getAddressDetailRemote")
	public String getAddressDetail(HttpServletRequest request,HttpServletResponse response,String jsoncallback,String addressId)  {
		// 存储返回结果值
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			QueryAddressParamInParam queryAddressParam = new QueryAddressParamInParam();
			String tokenId = SessionUtil.getTokenId(request);
			// addressId解密(AES)
			String aesKey=EncryptUtils.getAESKeySelf();
			addressId=EncryptUtils.decryptByAES(addressId,aesKey);				
			
			queryAddressParam.setAddressId(addressId);
			queryAddressParam.setTokenId(tokenId);
			SessionUtil.setDeviceInfo(request, queryAddressParam);
			//AddressUpdateOutParam addressUpdateOut = addressService.getUpdateAddress(queryAddressParam);
			AddressUpdateOutParam addressUpdateOut = addressService.getUpdateAddressNew(queryAddressParam);
			if (addressUpdateOut.isSuccess()) {
				AddressUpdateVo addressUpdateVo = addressUpdateVoConvertor.converter2AddressUpdateVo(addressUpdateOut);
				AddressDataListVo addressDataList = addressUpdateVo.getAddressDataList();
				AddressVo decodeUnicode = this.decodeAddress(addressDataList.getAddressDetail());
				addressDataList.setAddressDetail(decodeUnicode);
				
				// addressId加密(AES)
				addressId=EncryptUtils.encryptByAES(addressId, aesKey);
                addressUpdateVo.getAddressDataList().getAddressDetail().setAddressId(addressId);
                
				model.put("result", true);
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg", ErrorCode.Success.getErrorMsg());
				model.put("addresses", addressUpdateVo.getAddressDataList());
			}
		} catch(EIBusinessException e){
			model.put("result", false);
			model.put("errorCode", ErrorCode.getUpdateAddressFailed.getErrorCode());
			model.put("errorMsg", ErrorCode.getUpdateAddressFailed.getErrorMsg());
			logger.error("获取收货地址详细信息发生异常:" + e.getMessageWithSupportCode(),e);
		}catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取收货地址详细信息发生异常：" + e.getMessage(),e);
		}
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}

	/**
	 * 删除某个地址
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/delAddressRemote")
	public String delAddress(HttpServletRequest request,HttpServletResponse response,String jsoncallback,String addressId)  {
		// 存储返回结果值
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			UserAddressOperationInParam addressOperation = new UserAddressOperationInParam();
			SessionUtil.setDeviceInfo(request, addressOperation);
			
			// addressId解密(AES)
			String aesKey=EncryptUtils.getAESKeySelf();
			addressId=EncryptUtils.decryptByAES(addressId, aesKey);				
			addressOperation.setAddressId(addressId);
			
			BaseBackDataVo delAddress = addressService.delAddress(addressOperation);
			if (delAddress.isSuccess()) {
				//删除地址时不再删除身份信息
//					LoginResVo user = SessionUtil.getUser(request);
//					UserIdentityDTO userIdentityDTO = receiverIdService.getUserIdentity(user.getUserId(), addressId);
//					if (userIdentityDTO!=null && userIdentityDTO.getAddressId()!=null) {
//						// 删除相关的用户身份认证信息及证件文件
//						receiverIdService.deleteByAddressId(Long.parseLong(addressId));
//						if (StringUtils.isNotBlank(userIdentityDTO.getIdHeads())) {
//							File faceFile = new File(domainURL.getIdPicUploadPath().concat("/").concat(user.getUserId()).concat("/").concat(userIdentityDTO.getIdHeads()));
//							if (faceFile.exists()) {
//								faceFile.delete();
//							}
//						}
//						
//						if (StringUtils.isNotBlank(userIdentityDTO.getIdTails())) {
//							File tailsFile = new File(domainURL.getIdPicUploadPath().concat("/").concat(user.getUserId()).concat("/").concat(userIdentityDTO.getIdTails()));
//							if (tailsFile.exists()) {
//								tailsFile.delete();
//							}
//						}
//					}
				model.put("result", true);
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}
		} catch(EIBusinessException e){
			model.put("result", false);
			model.put("errorCode", ErrorCode.DelAddressFailed.getErrorCode());
			model.put("errorMsg", ErrorCode.DelAddressFailed.getErrorMsg());
			logger.error("删除收货地址接口时发生异常:" + e.getMessageWithSupportCode(),e);
		}catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("删除收货地址接口时发生异常：" + e.getMessage(),e);
		}
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}

	/**
	 *  省市区联动接口
	 * @return
	 * @
	 */
	@ResponseBody
	@RequestMapping(value = "/getAddressParamRemote")
	public String getAddressParam(HttpServletRequest request,HttpServletResponse response,String jsoncallback,String parentCode,String paramType)  {
		
		// 存储返回值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		try {
			QueryAddressParamInParam queryAddressParam = new QueryAddressParamInParam();
			queryAddressParam.setParentCode(parentCode);
			queryAddressParam.setParamType(paramType);
			queryAddressParam.setQueryType(AddressConstant.QUERY_TYPE_LINK);
			SessionUtil.setDeviceInfo(request, queryAddressParam);
			ProvinceRegionCityListOutParam addressParam = addressService.getAddressParam(queryAddressParam);
			if (addressParam.isSuccess()) {
				List<ProvinceRegionCityVo> prcVos = addressParam.getPrcList();
				model.put("result", true);
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg", ErrorCode.Success.getErrorMsg());
				model.put("prcVos", prcVos);
			} 
		} catch(EIBusinessException e){
			model.put("result", false);
			model.put("errorCode", ErrorCode.getPrcVosListFailed.getErrorCode());
			model.put("errorMsg", ErrorCode.getPrcVosListFailed.getErrorMsg());
			logger.error("获取省市区联动接口时发生异常:" + e.getMessageWithSupportCode(),e);
		}catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取省市区联动接口时发生异常：" + e.getMessage(),e);
		}
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}

	private ProvinceRegionCityListOutParam getPRC(HttpServletRequest request,String code, String type) throws Exception {
		QueryAddressParamInParam queryAddressParam = new QueryAddressParamInParam();

		// 100086 查的是省数据
		queryAddressParam.setParentCode(code);
		queryAddressParam.setParamType(type);
		queryAddressParam.setQueryType(AddressConstant.QUERY_TYPE_LINK);

		SessionUtil.setDeviceInfo(request, queryAddressParam);
		ProvinceRegionCityListOutParam addressParam = addressService.getAddressParam(queryAddressParam);

		return addressParam;
	}
	
	/**
	 * 转换 Unicode 字符串 将收货人姓名和街道信息
	 * 
	 * @Title: decodeUnicode
	 * @param addressVo
	 * @author: yong
	 * @date: 2013-5-15上午09:47:28
	 */
	private AddressVo decodeAddress(AddressVo addressVo) {

		String rName = addressVo.getRcverName();
		rName = UnicodeUtils.decodeUnicode_try(rName);
		addressVo.setRcverName(rName);

		String addressInfo = addressVo.getAddressInfo();
		addressInfo = UnicodeUtils.decodeUnicode_try(addressInfo);
		addressVo.setAddressInfo(addressInfo);
		
		if(StringUtils.isBlank(addressVo.getMobile())) {
			//如果手机号为空，则改为空串
			addressVo.setMobile("");
		}
		
		return addressVo;
	}
	
	/**
	 * 获取收货地址列表
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/getAddressListRemote")
	public String getAddressList(HttpServletRequest request,HttpServletResponse response,String jsoncallback)  {
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		try {
			AddressListQueryInParam addressListQuery = new AddressListQueryInParam();
			SessionUtil.setDeviceInfo(request, addressListQuery);
			//QueryUserAddressListOutParam listAddress = addressService.listAddress(addressListQuery);
			QueryUserAddressListOutParam listAddress = addressService.listAddressNew(addressListQuery);
			boolean iOnlineStates = listAddress.isOverTime() ? false : true;
			if (iOnlineStates) {
				if (listAddress.isSuccess()) {
					List<AddressVo> addressVos = listAddress.getAddressList();
					if (null != addressVos && addressVos.size() > 0) {
						for (int i = 0; i < addressVos.size(); i++) {
							decodeAddress(addressVos.get(i));
							
							// addressId加密(AES)
							String aesKey = EncryptUtils.getAESKeySelf();
							String addressId=EncryptUtils.encryptByAES(addressVos.get(i).getAddressId(), aesKey);
							AddressVo bean=addressVos.get(i);
							bean.setAddressId(addressId);
						}
					}
					model.put("result", true);
					model.put("errorCode", ErrorCode.Success.getErrorCode());
					model.put("errorMsg", ErrorCode.Success.getErrorMsg());
					model.put("adressList", addressVos);
				} else {
					model.put("result", false);
					model.put("errorCode", ErrorCode.GetAddressListFailed.getErrorCode());
					model.put("errorMsg", ErrorCode.GetAddressListFailed.getErrorMsg());
					logger.error("获取收货地址列表接口时发生错误{ErrorCode：" + listAddress.getRetCode() + ", ErrorMsg:" + listAddress.getErrorMsg()
							+ "}");
				}
			}

		} catch(BaseException e){
			model.put("result", false);
			model.put("errorCode", e.getErrCode());
			model.put("errorMsg", e.getMessageWithSupportCode());
			logger.error("获取收货地址列表接口时发生异常:" + e.getMessageWithSupportCode(),e);
		}catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取收货地址列表接口时发生异常：" + e.getMessage(),e);
		}
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}
	
	/**
	 * 将收货地址设为默认
	 * 
	 * @Title: setMasterAddress
	 * @param addressId
	 * @
	 * @author: wangzhenjiang
	 * @date: 2014-03-22
	 */
	@ResponseBody
	@RequestMapping(value = "/setMasterAddressRemote")
	public String setMasterAddress(HttpServletRequest request,HttpServletResponse response,String jsoncallback,String addressId)  {
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		try {
			QueryUserAddressDetailInParam addressDetail = new QueryUserAddressDetailInParam();
			SessionUtil.setDeviceInfo(request, addressDetail);
			
			// addressId解密(AES)
			String aesKey=EncryptUtils.getAESKeySelf();
			addressId=EncryptUtils.decryptByAES(addressId, aesKey);
			
			addressDetail.setAddressId(addressId);
			AddressOutParam addressOutParam = addressService.getAddress(addressDetail);
			boolean iOnlineStates = addressOutParam.isOverTime() ? false : true;
			if (iOnlineStates) {
				if (addressOutParam.isSuccess()) {
					AddressVo addressVo = addressVoConvertor.Converter2AddressVo(addressOutParam);
					addressVo = decodeAddress(addressVo);
					// 修改默认
					UserAddressOperationInParam addressOperation = addressVoConvertor.Converter2AddressInParam(addressVo);
					SessionUtil.setDeviceInfo(request, addressOperation);
					BaseBackDataVo setMaster = addressService.setMasterAddress(addressOperation);
					iOnlineStates = setMaster.isOverTime() ? false : true;
					if (iOnlineStates) {
						if (setMaster.isSuccess()) {
							model.put("result", true);
							model.put("errorCode", ErrorCode.Success.getErrorCode());
							model.put("errorMsg", ErrorCode.Success.getErrorMsg());
						} else {
							model.put("result", false);
							model.put("errorCode", ErrorCode.SetMasterAddressFailed.getErrorCode());
							model.put("errorMsg", ErrorCode.SetMasterAddressFailed.getErrorMsg());
						}
					}
				} else {
					model.put("result", false);
					model.put("errorCode", ErrorCode.GetAddressListFailed.getErrorCode());
					model.put("errorMsg", ErrorCode.GetAddressListFailed.getErrorMsg());
					logger.error("获取收货地址时发生错误{ErrorCode：" + addressOutParam.getRetCode() + ", ErrorMsg:"
							+ addressOutParam.getErrorMsg() + "}");
				}
			}
		} catch(BaseException e){
			model.put("result", false);
			model.put("errorCode", e.getErrCode());
			model.put("errorMsg", e.getMessageWithSupportCode());
			logger.error("设置默认收货地址时发生异常:" + e.getMessageWithSupportCode(),e);
		}catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("设置默认收货地址时发生异常：" + e.getMessage(),e);
		}
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}
	
	/**
	 * 查询所有地区信息
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getRegionInfoByNames")
	public String getRegionInfoByNames(HttpServletRequest request,HttpServletResponse response,String names,String jsoncallback)  {
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		if(StringUtils.isEmpty(names)) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.ParamsError.getErrorCode());
			model.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
			ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
			return null;
		}
		try {
			Map<String,Object> result= addressService.getRegionParamInfoByNames(names);
			ProvinceRegionCityListOutParam addressParam = (ProvinceRegionCityListOutParam)result.get("regions");
			if (addressParam.isSuccess()) {
				List<ProvinceRegionCityVo> prcVos = addressParam.getPrcList();
				
				String postCode=ObjectUtil.getString(result.get("postCode"),null);
				model.put("result", true);
				model.put("postCode", postCode);
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg", ErrorCode.Success.getErrorMsg());
				model.put("regionList", prcVos);
			} 
		} catch(BaseException e){
			model.put("result", false);
			model.put("errorCode", e.getErrCode());
			model.put("errorMsg", e.getMessageWithSupportCode());
			logger.error("按名称查询地区信息时发生异常:" + e.getMessageWithSupportCode(),e);
		}catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("按名称查询地区信息时发生异常：" + e.getMessage(),e);
		}
		
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}
	
	
	/**
	 * 查询所有地区信息
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllRegionInfo")
	public String getAllRegionInfo(HttpServletRequest request,HttpServletResponse response,String jsoncallback)  {
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		
		try {
			ProvinceRegionCityListOutParam addressParam = addressService.getAllRegionParamInfo();
			if (addressParam.isSuccess()) {
				List<ProvinceRegionCityVo> prcVos = addressParam.getPrcList();
				model.put("result", true);
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg", ErrorCode.Success.getErrorMsg());
				model.put("regionList", prcVos);
			} 
		} catch(BaseException e){
			model.put("result", false);
			model.put("errorCode", e.getErrCode());
			model.put("errorMsg", e.getMessageWithSupportCode());
			logger.error("查询所有地区信息时发生异常:" + e.getMessageWithSupportCode(),e);
		}catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询所有地区信息时发生异常：" + e.getMessage(),e);
		}
		
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}
	
}
