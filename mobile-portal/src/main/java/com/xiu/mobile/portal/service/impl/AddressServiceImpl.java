package com.xiu.mobile.portal.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.xiu.common.lang.StringUtil;
import com.xiu.csp.facade.dto.CspResult;
import com.xiu.csp.facade.dto.SysParamDTO;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.portal.common.constants.AddressConstant;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.model.BaseBackDataVo;
import com.xiu.mobile.portal.constants.ErrConstants;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.ei.EIAddressManager;
import com.xiu.mobile.portal.ei.EISysParamManager;
import com.xiu.mobile.portal.ei.EIReceiverIdManager;
import com.xiu.mobile.portal.model.AddressAddOutParam;
import com.xiu.mobile.portal.model.AddressDataListVo;
import com.xiu.mobile.portal.model.AddressListQueryInParam;
import com.xiu.mobile.portal.model.AddressOutParam;
import com.xiu.mobile.portal.model.AddressUpdateOutParam;
import com.xiu.mobile.portal.model.AddressVo;
import com.xiu.mobile.portal.model.ProvinceRegionCityListOutParam;
import com.xiu.mobile.portal.model.ProvinceRegionCityVo;
import com.xiu.mobile.portal.model.QueryAddressParamInParam;
import com.xiu.mobile.portal.model.QueryUserAddressDetailInParam;
import com.xiu.mobile.portal.model.QueryUserAddressListOutParam;
import com.xiu.mobile.portal.model.UserAddressOperationInParam;
import com.xiu.mobile.portal.service.IAddressService;
import com.xiu.mobile.portal.service.IMemcachedService;
import com.xiu.uuc.facade.dto.IdentityInfoDTO;
import com.xiu.uuc.facade.dto.RcvAddressDTO;
import com.xiu.uuc.facade.dto.Result;
import com.xiu.uuc.facade.dto.UserIdentityDTO;
import com.xiu.uuc.facade.util.FacadeConstant;

/**
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION :
 * @AUTHOR : mike@xiu.com
 * @DATE :2014-5-16 上午11:08:28
 *       </p>
 **************************************************************** 
 */
@Service("addressService")
public class AddressServiceImpl implements IAddressService {
    private static final Logger logger = Logger.getLogger(AddressServiceImpl.class);

    private final String QUERY_TYPE_ADD = "0";
	//private final String QUERY_TYPE_MODIFY = "1";
	private final String QUERY_TYPE_ONCHANGE = "2";
	
	@Autowired
	private IMemcachedService memcachedService;
	@Autowired
	private EIAddressManager eiAddressManager;
	@Autowired
	private EISysParamManager eiSysParamManager;
	@Autowired
	private EIReceiverIdManager eiReceiverIdManager;
	
	private RcvAddressDTO convertMapToBean(UserAddressOperationInParam addressInParam) {
        RcvAddressDTO dto = new RcvAddressDTO();
        dto.setUserId(Long.parseLong(addressInParam.getUserId()));// 用戶Id
        if(null != addressInParam.getAddressId()){
        	dto.setAddressId(Long.parseLong(addressInParam.getAddressId()));// 地址Id，新增时为空
        }
        dto.setAddressInfo(addressInParam.getAddressInfo());// 街道地址
        dto.setCreateChannelId(Integer.parseInt(GlobalConstants.CHANNEL_ID));// 渠道标识
        dto.setAreaCode(addressInParam.getAreaCode());// 区号
        dto.setBookerName(addressInParam.getBookerName());// 订购人姓名
        dto.setBookerPhone(addressInParam.getBookerPhone());// 订购人联系电话
        dto.setCityCode(addressInParam.getCityCode());// 所在县CODE
        dto.setDivCode(addressInParam.getDivCode());// 分机号
        dto.setIsMaster(addressInParam.getIsMaster());// 是否设置默认
        dto.setMobile(addressInParam.getMobile());// 手机号码
        dto.setPhone(addressInParam.getPhone());// 电话号码
        dto.setPostCode(addressInParam.getPostCode());// 邮政编码
        dto.setProvinceCode(addressInParam.getProvinceCode());// 所在省CODE
        dto.setRcverName(addressInParam.getRcverName());// 收货人姓名
        dto.setRegionCode(addressInParam.getRegionCode());// 所在市CODE
        dto.setIdentityId(addressInParam.getIdentityId());// 身份认证ID
        return dto;
    }

    @Override
    public AddressAddOutParam addAddress(UserAddressOperationInParam addressInParam) throws Exception {
    	logger.info("添加收货人地址信息：addressInParam="+addressInParam);
    	String userId = addressInParam.getUserId();
        AddressAddOutParam backData = new AddressAddOutParam();
        addressInParam.setBizType(AddressConstant.BIZ_TYPE_ADD);
        Assert.notNull(userId);
        Assert.notNull(addressInParam.getPostCode());
        RcvAddressDTO dto = this.convertMapToBean(addressInParam);
        Result result = eiAddressManager.addAddress(dto);
        // 用户中心接口定义返回1为成功
        if (result.getSuccess().equals("1")) {
        	logger.info("用户收货地址信息添加成功！");
        	RcvAddressDTO dto2 = (RcvAddressDTO) result.getData();
        	//身份证号、身份证图片，任意一个不为空则添加身份信息；正反面图片必须同时存在，或者都没有 
        	if (StringUtil.isNotBlank(addressInParam.getIdCard()) || (StringUtil.isNotBlank(addressInParam.getIdHead()) && StringUtil.isNotBlank(addressInParam.getIdTails()))) {
        		logger.info("绑定用户收货地址身份认证！");
            	// 保存成功后绑定用户收货地址身份证相关信息
            	UserIdentityDTO userIdentityDTO = new UserIdentityDTO();
            	userIdentityDTO.setAddressId(dto2.getAddressId());
            	userIdentityDTO.setCreateDate(new Date());
            	userIdentityDTO.setIdHeads(addressInParam.getIdHead());
            	userIdentityDTO.setIdName(addressInParam.getRcverName());
            	userIdentityDTO.setIdNumber(addressInParam.getIdCard());
            	userIdentityDTO.setIdTails(addressInParam.getIdTails());
            	userIdentityDTO.setIsReview(0L);
            	userIdentityDTO.setUserId(Long.parseLong(userId));
            	// 持久化到数据库
            	Result idResult = eiReceiverIdManager.insertUserIdentity(userIdentityDTO);
            	if (idResult.getSuccess().equals("1")) {
            		backData.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
                    backData.setAddressId(Long.toString(dto2.getAddressId()));
    			}else{
    				backData.setRetCode(result.getErrorCode());
    	            backData.setErrorMsg(result.getErrorMessage());
    			}
			}else{
				backData.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
                backData.setAddressId(Long.toString(dto2.getAddressId()));
			}
        } else {
            backData.setRetCode(result.getErrorCode());
            backData.setErrorMsg(result.getErrorMessage());
        }
        return backData;
    }

    @Override
    public BaseBackDataVo updateAddress(UserAddressOperationInParam addressInParam) throws Exception {
    	logger.info("修改收货人地址信息参数：addressInParam="+addressInParam);
    	String userId = addressInParam.getUserId();
    	Assert.notNull(userId,"用户userId不能为空.");
    	Assert.notNull(addressInParam.getAddressId(),"地址id不能为空.");
        AddressAddOutParam backData = new AddressAddOutParam();
        addressInParam.setBizType(AddressConstant.BIZ_TYPE_UPDATE);
        RcvAddressDTO dto = this.convertMapToBean(addressInParam);
        Result result = eiAddressManager.updateAddress(dto);
        if (result.getSuccess().equals("1")) {// 用户中心接口定义返回1为成功
        	backData.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
        }
        return backData;
    }

    @SuppressWarnings("unchecked")
	@Override
    public AddressUpdateOutParam getUpdateAddress(QueryAddressParamInParam queryAddressParam) throws Exception {

    	AddressUpdateOutParam outParam = new AddressUpdateOutParam();
		if (logger.isDebugEnabled()){
			logger.debug("开始调用收货地址省市信息接口");
		}
		// 1.先拿到当前的地址信息
		String addressId = queryAddressParam.getAddressId();
		Assert.notNull(addressId,"addressId不能为空.");
		
		// 当前地址Id详细信息
		RcvAddressDTO dto = eiAddressManager.getRcvAddressInfoById(Long.parseLong(addressId));
		// 获取收货人信息
		AddressVo addressVo = converTOAddressVo(dto);
		UserIdentityDTO userIdentityDto = new UserIdentityDTO();
		userIdentityDto.setAddressId(Long.parseLong(addressId));
		userIdentityDto.setUserId(Long.parseLong(queryAddressParam.getUserId()));
		Result userIdentityResult = eiReceiverIdManager.getUserIdentity(userIdentityDto);
		boolean flag = false;
		if (userIdentityResult!=null && FacadeConstant.SUCCESS.equals(userIdentityResult.isSuccess())) {
			userIdentityDto = (UserIdentityDTO) userIdentityResult.getData();
			addressVo.setIdCard(userIdentityDto.getIdNumber()==null? "" : userIdentityDto.getIdNumber());
			addressVo.setIdHead(userIdentityDto.getIdHeads()==null? "" : userIdentityDto.getIdHeads());
			addressVo.setIdTails(userIdentityDto.getIdTails()==null? "" : userIdentityDto.getIdTails());
			if(userIdentityDto.getIdNumber()!=null&&!userIdentityDto.getIdNumber().equals("")){
				flag=true;
			}
//			// 是否审核0:未审核 1:审核通过 2:审核不通过
//			Long isReview = userIdentityDto.getIsReview();
//			if (isReview!=null && isReview ==1) {
//				flag = true;
//			}
		}
		addressVo.setIdAuthorized(flag);
		
		// 拿到所有省信息
		CspResult cspResult = eiSysParamManager.getListByParamType(GlobalConstants.ADDRESS_PARAM_TYPE_PROVINCE);
		List<SysParamDTO> list_province = (List<SysParamDTO>)cspResult.getData();
		// 拿到当前省下的所有市信息
		cspResult = eiSysParamManager.getListByParamTypeAndParentCode(GlobalConstants.ADDRESS_PARAM_TYPE_REGION,dto.getProvinceCode());
		List<SysParamDTO> list_region = (List<SysParamDTO>)cspResult.getData();
		// 拿到当前市下的所有县信息
		cspResult = eiSysParamManager.getListByParamTypeAndParentCode(GlobalConstants.ADDRESS_PARAM_TYPE_CITY,dto.getRegionCode());
		List<SysParamDTO> list_city = (List<SysParamDTO>)cspResult.getData();
		
		List<ProvinceRegionCityVo> province = this.convertRegionCityOutList(list_province);
		List<ProvinceRegionCityVo> region = this.convertRegionCityOutList(list_region);
		List<ProvinceRegionCityVo> city = this.convertRegionCityOutList(list_city);
		
		AddressDataListVo addressDataList = new AddressDataListVo();
		addressDataList.setAddressDetail(addressVo);
		addressDataList.setList_city(city);
		addressDataList.setList_province(province);
		addressDataList.setList_region(region);
		outParam.setAddressDataList(addressDataList);
		outParam.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
		return outParam;
    }

    @Override
    public AddressVo getMasterAddress(AddressListQueryInParam addressListQuery) throws Exception {
    	//QueryUserAddressListOutParam listOut = this.listAddress(addressListQuery);
    	QueryUserAddressListOutParam listOut = this.listAddressNew(addressListQuery);
    	List<AddressVo> lstAddressVos = listOut.getAddressList();
		if (null != lstAddressVos && lstAddressVos.size() > 0) {
			for (AddressVo addressVo : lstAddressVos) {
				if (AddressConstant.IS_MASTER_YES.equals(addressVo.getIsMaster())) {
					return addressVo;
				}
			}
			return lstAddressVos.get(0);
		}
        return null;
    }

    @Override
    public BaseBackDataVo delAddress(UserAddressOperationInParam addressInParam) {
    	logger.info("删除收货人地址信息参数：addressInParam="+addressInParam);
		Assert.notNull(addressInParam.getAddressId(),"addressId不能为空.");
        AddressAddOutParam backData = new AddressAddOutParam();
        addressInParam.setBizType(AddressConstant.BIZ_TYPE_DEL);
        Result result = eiAddressManager.delAddress(Long.parseLong(addressInParam.getAddressId()));
        if (result.getSuccess().equals("1")) {// 用户中心接口定义返回1为成功
            backData.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
        }
        return backData;
    }

    @Override
    public AddressOutParam getAddress(QueryUserAddressDetailInParam addressDetail) throws Exception {
    	AddressOutParam addressOutParam = new AddressOutParam();
		String addressId = addressDetail.getAddressId();
		Assert.notNull(addressId,"addressId不能为空.");
		RcvAddressDTO dto = eiAddressManager.getRcvAddressInfoById(Long.parseLong(addressId));
		if (null != dto){
			addressOutParam = converTOAddressOutParam(dto);
			addressOutParam.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
		}
        return addressOutParam;
    }

	@Override
    public QueryUserAddressListOutParam listAddress(AddressListQueryInParam addressListQuery) throws Exception {
    	QueryUserAddressListOutParam listOutParam = new QueryUserAddressListOutParam();
    	String userId = addressListQuery.getUserId();
		Assert.notNull(userId,"userId不能为空.");
		List<RcvAddressDTO> list_rcv = eiAddressManager.getRcvAddressListByUserId(Long.parseLong(userId));
		if(null!=list_rcv && list_rcv.size()>0){
        	List<AddressVo> list_out = new ArrayList<AddressVo>();
        	//在这里做转换，剔除某些无线客户端不需要的字段
        	for(RcvAddressDTO dto:list_rcv){
 	        	AddressVo out = converTOAddressVo(dto);
 	        	UserIdentityDTO userIdentityDto = new UserIdentityDTO();
 	        	userIdentityDto.setAddressId(Long.parseLong(out.getAddressId()));
 	        	userIdentityDto.setUserId(Long.parseLong(userId));
 	        	Result userIdentityResult = eiReceiverIdManager.getUserIdentity(userIdentityDto);
 	        	boolean flag = false;
	 	   		if (userIdentityResult!=null && FacadeConstant.SUCCESS.equals(userIdentityResult.isSuccess())) {
	 	   			userIdentityDto = (UserIdentityDTO) userIdentityResult.getData();
	 				if(userIdentityDto.getIdNumber()!=null){
	 					flag=true;
	 				}
	 	   			// 是否审核0:未审核 1:审核通过 2:审核不通过
//	 	   			Long isReview = userIdentityDto.getIsReview();
//	 	   			if (isReview!=null && isReview ==1) {
//	 	   				flag = true;
//	 	   			}
	 	   		}
	 	   		out.setIdAuthorized(flag);
 	        	list_out.add(out);
        	}
        	listOutParam.setAddressList(list_out);
        }
		listOutParam.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
		return listOutParam;
    }
	
	public QueryUserAddressListOutParam listAddressNew(AddressListQueryInParam addressListQuery) throws Exception {
    	QueryUserAddressListOutParam listOutParam = new QueryUserAddressListOutParam();
    	String userId = addressListQuery.getUserId();
		Assert.notNull(userId,"userId不能为空.");
		List<RcvAddressDTO> list_rcv = eiAddressManager.getRcvAddressListByUserId(Long.parseLong(userId));
		if(null!=list_rcv && list_rcv.size()>0){
        	List<AddressVo> list_out = new ArrayList<AddressVo>();
        	//在这里做转换，剔除某些无线客户端不需要的字段
        	for(RcvAddressDTO dto:list_rcv){
 	        	AddressVo out = converTOAddressVo(dto);
 	        	Long identityId = dto.getIdentityId();
 	        	if(identityId != null && identityId.longValue() != 0) {
 	        		Result result = eiReceiverIdManager.queryIdentityInfoById(identityId);
 	        		
 	        		boolean flag = false;
 	        		if(result != null && result.getData() != null && FacadeConstant.SUCCESS.equals(result.isSuccess())) {
	 	   				IdentityInfoDTO identityInfoDTO = (IdentityInfoDTO) result.getData();
	 	   			    out.setIdCard(identityInfoDTO.getIdNumber() == null ? "" : identityInfoDTO.getIdNumber());
	 	   			    if(identityInfoDTO.getIdNumber() != null&&!identityInfoDTO.getIdNumber().equals("")){
	 	   			       flag=true;
	 	   			    }
//	 	   				// 是否审核0:未审核 1:审核通过 2:审核不通过
//	 	   				Integer isReview = identityInfoDTO.getReviewState();
//	 	   				if(isReview != null && isReview == 1) {
//	 	   					flag = true;
//	 	   				}
	 	   			}
 	        		out.setIdAuthorized(flag);
 	        		out.setIdentityId(dto.getIdentityId());	//设置身份信息ID
 	        	}
 	        	list_out.add(out);
        	}
        	listOutParam.setAddressList(list_out);
        }
		listOutParam.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
		return listOutParam;
    }

    @Override
    public BaseBackDataVo setMasterAddress(UserAddressOperationInParam addressOperation) throws Exception {
    	Assert.notNull(addressOperation.getAddressId(),"addressId不能为空.");
    	addressOperation.setIsMaster(AddressConstant.IS_MASTER_YES);
    	addressOperation.setBizType(AddressConstant.BIZ_TYPE_UPDATE);
    	return this.updateAddress(addressOperation);
    }

    @SuppressWarnings("unchecked")
	@Override
    public ProvinceRegionCityListOutParam getAddressParam(QueryAddressParamInParam queryAddressParam) throws Exception {
    	
    	ProvinceRegionCityListOutParam outParam = new ProvinceRegionCityListOutParam();
		String queryType = queryAddressParam.getQueryType();
		String paramType = queryAddressParam.getParamType();
		Assert.notNull(queryType,"queryType不能为空.");
		if (logger.isDebugEnabled()){
			logger.debug("开始调用收货地址省市信息接口:queryType:" + queryType);
		}
		if (queryType.equals(QUERY_TYPE_ONCHANGE)) {// 是页面联动取
			// 上一级Code，如查询市信息传入的参数是省code
			Assert.notNull(paramType,"paramType不能为空.");
			String parentCode = queryAddressParam.getParentCode();
			CspResult result = eiSysParamManager.getListByParamTypeAndParentCode(paramType, parentCode);
			List<SysParamDTO> list_po = (List<SysParamDTO>)result.getData();
			if (paramType.equals(GlobalConstants.ADDRESS_PARAM_TYPE_REGION)) {
				// 组装成区
				List<ProvinceRegionCityVo> region = this.convertRegionCityOutList(list_po);
				outParam.setPrcList(region);
			} if(paramType.equals(GlobalConstants.ADDRESS_PARAM_TYPE_POST_CODE)) {
				//组装邮政编码
				List<ProvinceRegionCityVo> postCode = this.convertRegionCityOutList(list_po);
				outParam.setPrcList(postCode);  //ProvinceRegionCityVo.paramDesc即为邮政编码
			} else {
				// 组装成市
				List<ProvinceRegionCityVo> city = this.convertRegionCityOutList(list_po);
				outParam.setPrcList(city);
			}
		} else if (queryType.equals(QUERY_TYPE_ADD)) { // 是新增收货地址，仅返回省一级信息
			CspResult result = eiSysParamManager.getListByParamType(GlobalConstants.ADDRESS_PARAM_TYPE_PROVINCE);
			List<SysParamDTO> list_po = (List<SysParamDTO>)result.getData();
			// 组装成省
			List<ProvinceRegionCityVo> province = this.convertRegionCityOutList(list_po);
			outParam.setPrcList(province);
		} else {
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_CSP_BIZ_ERR, GlobalConstants.RET_CODE_OTHER_MSG, "不支持的查询类型！");
		}
		outParam.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
		return outParam;
    }
    
	/**
	 * 将返回的list重新做封装，主要是剔除不需要的字段，以减少返回到客户端的流量
	 * 
	 * @param list_po
	 * @return
	 */
	private List<ProvinceRegionCityVo> convertRegionCityOutList(List<SysParamDTO> list_po) {
		List<ProvinceRegionCityVo> list_vo = new ArrayList<ProvinceRegionCityVo>();
		if (null != list_po && list_po.size() > 0) {
			for (SysParamDTO po : list_po) {
				ProvinceRegionCityVo vo = new ProvinceRegionCityVo();
				vo.setParamCode(po.getParamCode());
				vo.setParamDesc(po.getParamDesc());
				vo.setParamType(po.getParamType());
				vo.setParentCode(po.getParentCode());
				list_vo.add(vo);
			}
		}
		return list_vo;
	}
	
	/**
	 * 把RcvAddressDTO转化成AddressOutParam对象
	 * @param dto
	 * @return
	 */
	private AddressOutParam converTOAddressOutParam(RcvAddressDTO dto){
		AddressOutParam addressOutParam = new AddressOutParam();
		addressOutParam.setAddressId(dto.getAddressId().toString());
		addressOutParam.setUserId(dto.getUserId() == null ? null : dto.getUserId().toString());
		addressOutParam.setCustomerId(dto.getCustId() == null ? null : dto.getCustId().toString());
		addressOutParam.setAddressInfo(dto.getAddressInfo());
		addressOutParam.setAreaCode(dto.getAreaCode());
		addressOutParam.setBookerName(dto.getBookerName());
		addressOutParam.setBookerPhone(dto.getBookerPhone());
		addressOutParam.setCityCode(dto.getCityCode());
		addressOutParam.setCityCodeDesc(dto.getCityCodeDesc());
		addressOutParam.setCityCodeRemark(dto.getCityCodeRemark());
		addressOutParam.setDivCode(dto.getDivCode());
		addressOutParam.setIsMaster(dto.getIsMaster());
		addressOutParam.setMobile(dto.getMobile());
		addressOutParam.setPhone(dto.getPhone());
		addressOutParam.setPostCode(dto.getPostCode());
		addressOutParam.setProvinceCode(dto.getProvinceCode());
		addressOutParam.setProvinceCodeDesc(dto.getProvinceCodeDesc());
		addressOutParam.setRcverName(dto.getRcverName());
		addressOutParam.setRegionCode(dto.getRegionCode());
		addressOutParam.setRegionCodeDesc(dto.getRegionCodeDesc());
		addressOutParam.setIdentityId(dto.getIdentityId());
		return addressOutParam;
	}
	
	/**
	 * 把RcvAddressDTO转化成AddressOutParam对象
	 * @param dto
	 * @return
	 */
	private AddressVo converTOAddressVo(RcvAddressDTO dto){
		AddressVo addressOutParam = new AddressVo();
		addressOutParam.setAddressId(dto.getAddressId().toString());
		addressOutParam.setAddressInfo(dto.getAddressInfo());
		addressOutParam.setAreaCode(dto.getAreaCode());
		addressOutParam.setBookerName(dto.getBookerName());
		addressOutParam.setBookerPhone(dto.getBookerPhone());
		addressOutParam.setCityCode(dto.getCityCode());
		addressOutParam.setCityCodeDesc(dto.getCityCodeDesc());
		addressOutParam.setCityCodeRemark(dto.getCityCodeRemark());
		addressOutParam.setAddressPrefix(dto.getCityCodeRemark());
		addressOutParam.setDivCode(dto.getDivCode());
		addressOutParam.setIsMaster(dto.getIsMaster());
		addressOutParam.setMobile(dto.getMobile());
		addressOutParam.setPhone(dto.getPhone());
		addressOutParam.setPostCode(dto.getPostCode());
		addressOutParam.setProvinceCode(dto.getProvinceCode());
		addressOutParam.setProvinceCodeDesc(dto.getProvinceCodeDesc());
		addressOutParam.setRcverName(dto.getRcverName());
		addressOutParam.setRegionCode(dto.getRegionCode());
		addressOutParam.setRegionCodeDesc(dto.getRegionCodeDesc());
		addressOutParam.setIdentityId(dto.getIdentityId());
		return addressOutParam;
	}
	
	public AddressAddOutParam addAddressNew(UserAddressOperationInParam addressInParam) throws Exception {
		logger.info("添加收货人地址信息：addressInParam="+addressInParam);
    	String userId = addressInParam.getUserId();
        AddressAddOutParam backData = new AddressAddOutParam();
        addressInParam.setBizType(AddressConstant.BIZ_TYPE_ADD);
        Assert.notNull(userId);
        Assert.notNull(addressInParam.getPostCode());
        RcvAddressDTO dto = this.convertMapToBean(addressInParam);
        Result result = null;
        //如果身份证号码或者身份证图片不为空，则先保存用户身份证信息，然后把身份信息ID设置到收货地址中，再保存收货地址
        if (StringUtil.isNotBlank(addressInParam.getIdCard()) || (StringUtil.isNotBlank(addressInParam.getIdHead()) && StringUtil.isNotBlank(addressInParam.getIdTails()))) {
        	//根据用户ID和收货人名称查询用户的身份认证信息是否存在
        	Result identityResult = eiReceiverIdManager.queryIdentityInfoByUserIdAndName(Long.parseLong(userId), addressInParam.getRcverName());
        	IdentityInfoDTO identityInfoDTO = null;
        	if(identityResult != null && identityResult.getData() != null) {
        		//如果身份认证信息存在，则更新
        		identityInfoDTO = (IdentityInfoDTO) identityResult.getData();
        		if (identityInfoDTO.getIdNumber()!=null && (!identityInfoDTO.getIdNumber().equals(addressInParam.getIdCard()))) {
					identityInfoDTO.setReviewState(0);
				}
            	if (identityInfoDTO.getIdHeads()!=null && (!identityInfoDTO.getIdHeads().equals(addressInParam.getIdHead()))) {
            		identityInfoDTO.setReviewState(0);
				}
            	if (identityInfoDTO.getIdTails()!=null && (!identityInfoDTO.getIdTails().equals(addressInParam.getIdTails()))) {
            		identityInfoDTO.setReviewState(0);
				}

            	identityInfoDTO.setIdName(addressInParam.getRcverName());
				identityInfoDTO.setReceiverName(addressInParam.getRcverName());
				identityInfoDTO.setIdNumber(addressInParam.getIdCard());
				identityInfoDTO.setIdHeads(addressInParam.getIdHead());
				identityInfoDTO.setIdTails(addressInParam.getIdTails());
				identityInfoDTO.setUserId(Long.parseLong(userId));
				
				eiReceiverIdManager.updateIdentityInfo(identityInfoDTO); //更新身份认证信息
				
				dto.setIdentityId(identityInfoDTO.getIdentityId());	//设置身份信息ID
        	} else {
        		//如果身份认证新不存在，则新增
        		identityInfoDTO = new IdentityInfoDTO();
            	identityInfoDTO.setCreateTime(new Date());
            	identityInfoDTO.setReceiverName(addressInParam.getRcverName());	//收货人姓名
            	identityInfoDTO.setIdName(addressInParam.getRcverName());		//身份证姓名
            	identityInfoDTO.setIdNumber(addressInParam.getIdCard());		//身份证号码
            	identityInfoDTO.setIdHeads(addressInParam.getIdHead());			//身份证图片正面
            	identityInfoDTO.setIdTails(addressInParam.getIdTails());		//身份证图片反面
            	identityInfoDTO.setReviewState(0);
            	identityInfoDTO.setUserId(Long.parseLong(userId));
            	
            	result = eiReceiverIdManager.insertIdentityInfo(identityInfoDTO);	//新增用户身份证信息
            	
            	if (result != null && result.getSuccess().equals("1")) {
            		logger.info("用户身份证信息添加成功！");
            		Long identityId = (Long) result.getData();
                	dto.setIdentityId(identityId);	//设置身份信息ID
    			}else{
    				if(result != null) {
    					backData.setRetCode(result.getErrorCode());
        	            backData.setErrorMsg(result.getErrorMessage());
    				}
    			}
        	}
        } 
        
        result = eiAddressManager.addAddress(dto);	//添加收货地址信息
		if (result.getSuccess().equals("1")) {
			RcvAddressDTO dto2 = (RcvAddressDTO) result.getData();
			backData.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
            backData.setAddressId(Long.toString(dto2.getAddressId()));
		} else {
			 backData.setRetCode(result.getErrorCode());
	         backData.setErrorMsg(result.getErrorMessage());
		}
        
        return backData;
	}
	
	public AddressUpdateOutParam getUpdateAddressNew(QueryAddressParamInParam queryAddressParam) throws Exception {
    	AddressUpdateOutParam outParam = new AddressUpdateOutParam();
		if (logger.isDebugEnabled()){
			logger.debug("开始调用收货地址省市信息接口");
		}
		// 1.先拿到当前的地址信息
		String addressId = queryAddressParam.getAddressId();
		Assert.notNull(addressId,"addressId不能为空.");
		
		// 当前地址Id详细信息
		RcvAddressDTO dto = eiAddressManager.getRcvAddressInfoById(Long.parseLong(addressId));
		// 获取收货人信息
		AddressVo addressVo = converTOAddressVo(dto);
		
		//查询用户身份认证信息
		boolean flag = false;
		Long identityId = dto.getIdentityId();
		if(identityId != null && identityId.longValue() != 0) {
			Result result = eiReceiverIdManager.queryIdentityInfoById(identityId);	//根据身份认证ID查询用户身份证信息
			if(result != null && result.getData() != null && FacadeConstant.SUCCESS.equals(result.isSuccess())) {
				IdentityInfoDTO identityInfoDTO = (IdentityInfoDTO) result.getData();
				addressVo.setIdCard(identityInfoDTO.getIdNumber() == null ? "" : identityInfoDTO.getIdNumber());
				addressVo.setIdHead(identityInfoDTO.getIdHeads() == null ? "" : identityInfoDTO.getIdHeads());
				addressVo.setIdTails(identityInfoDTO.getIdTails() == null ? "" : identityInfoDTO.getIdTails());
				if(identityInfoDTO.getIdNumber() != null&&!identityInfoDTO.getIdNumber().equals("")){
					flag=true;
				}
//				// 是否审核0:未审核 1:审核通过 2:审核不通过
//				Integer isReview = identityInfoDTO.getReviewState();
//				if(isReview != null && isReview == 1) {
//					flag = true;
//				}
			}
		}
		addressVo.setIdAuthorized(flag);
		
		// 拿到所有省信息
		CspResult cspResult = eiSysParamManager.getListByParamType(GlobalConstants.ADDRESS_PARAM_TYPE_PROVINCE);
		List<SysParamDTO> list_province = (List<SysParamDTO>)cspResult.getData();
		// 拿到当前省下的所有市信息
		cspResult = eiSysParamManager.getListByParamTypeAndParentCode(GlobalConstants.ADDRESS_PARAM_TYPE_REGION,dto.getProvinceCode());
		List<SysParamDTO> list_region = (List<SysParamDTO>)cspResult.getData();
		// 拿到当前市下的所有县信息
		cspResult = eiSysParamManager.getListByParamTypeAndParentCode(GlobalConstants.ADDRESS_PARAM_TYPE_CITY,dto.getRegionCode());
		List<SysParamDTO> list_city = (List<SysParamDTO>)cspResult.getData();
		
		List<ProvinceRegionCityVo> province = this.convertRegionCityOutList(list_province);
		List<ProvinceRegionCityVo> region = this.convertRegionCityOutList(list_region);
		List<ProvinceRegionCityVo> city = this.convertRegionCityOutList(list_city);
		
		AddressDataListVo addressDataList = new AddressDataListVo();
		addressDataList.setAddressDetail(addressVo);
		addressDataList.setList_city(city);
		addressDataList.setList_province(province);
		addressDataList.setList_region(region);
		outParam.setAddressDataList(addressDataList);
		outParam.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
		return outParam;
    }

	/**
	 * 查询所有的地区参数信息：省、市、区、邮编
	 */
	public ProvinceRegionCityListOutParam getAllRegionParamInfo() throws Exception {
		ProvinceRegionCityListOutParam outParam = new ProvinceRegionCityListOutParam();
		
		if (logger.isDebugEnabled()){
			logger.debug("开始查询所有的地区参数信息：省、市、区、邮编接口:");
		}
		
		//从缓存获取省市区参数信息
		List<SysParamDTO> list_po = memcachedService.getAllRegionParamInfo();
		
		if(list_po == null || list_po.size() == 0) {
			//如果缓存中不存在，则查询数据库并添加至缓存
			CspResult result = eiSysParamManager.getAllRegionParamInfo();
			list_po = (List<SysParamDTO>)result.getData();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(XiuConstant.MOBILE_MEMCACHED_ALL_REGIONPARAM, list_po);
			memcachedService.addAllRegionParamCache(map);
		}
		
		// 组装数据
		List<ProvinceRegionCityVo> province = this.convertRegionCityOutList(list_po);
		outParam.setPrcList(province);
		outParam.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
		
		return outParam;
	}

	@Override
	public Map<String,Object> getRegionParamInfoByNames(String names)
			throws Exception {
		Map<String,Object> resultMap=new HashMap<String,Object>();
		ProvinceRegionCityListOutParam outParam = new ProvinceRegionCityListOutParam();
		List<SysParamDTO> listRegions=new ArrayList<SysParamDTO>();
		List<SysParamDTO> listRegionsNew=new ArrayList<SysParamDTO>();
		String postCode=null;
		//从缓存获取省市区参数信息
		List<SysParamDTO> list_po = memcachedService.getAllRegionParamInfo();
		
		if(list_po == null || list_po.size() == 0) {
			//如果缓存中不存在，则查询数据库并添加至缓存
			CspResult result = eiSysParamManager.getAllRegionParamInfo();
			list_po = (List<SysParamDTO>)result.getData();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(XiuConstant.MOBILE_MEMCACHED_ALL_REGIONPARAM, list_po);
			memcachedService.addAllRegionParamCache(map);
		}
		String namesArr[]=names.split(",");
		//对直辖市特殊处理
		if(namesArr.length>1&&namesArr[0].equals(namesArr[1])){
			namesArr[1]=namesArr[1]+"区";
		}
		for (SysParamDTO sys:list_po) {
			for (int i = 0; i < namesArr.length; i++) {
				if(sys.getParamDesc().equals(namesArr[i])){
					listRegions.add(sys);
				}
			}
			if(listRegions.size()==namesArr.length){
				break;
			}
		}
		String countyCode="";
		//按上下级排序，无上级的数据过滤
		for (int i = 1; i <=listRegions.size(); i++) {
			for (SysParamDTO r:listRegions) {
				if(r.getParamType().equals(i+"")){
					if(i==1){
						listRegionsNew.add(r);
					}else if(listRegionsNew.size()>0){
						if(listRegionsNew.get(listRegionsNew.size()-1).getParamCode().equals(r.getParentCode())){
							listRegionsNew.add(r);
							if(r.getParamType().equals("3")){
								countyCode=r.getParamCode();
							}
						}
					}
				}
			}
		}
		//如果查询到了县区，则查询邮编
		if(!countyCode.equals("")){
			CspResult result = eiSysParamManager.getListByParamTypeAndParentCode(GlobalConstants.ADDRESS_PARAM_TYPE_POST_CODE, countyCode);
			List<SysParamDTO> postlist = (List<SysParamDTO>)result.getData();
			if(postlist!=null&&postlist.size()>0){
				postCode=postlist.get(0).getParamDesc();
			}
		}
		
		// 组装数据
		List<ProvinceRegionCityVo> province = this.convertRegionCityOutList(listRegionsNew);
		outParam.setPrcList(province);
		outParam.setRetCode(GlobalConstants.RET_CODE_SUCESSS);
		resultMap.put("regions", outParam);
		resultMap.put("postCode", postCode);
		return resultMap;
	}

}
