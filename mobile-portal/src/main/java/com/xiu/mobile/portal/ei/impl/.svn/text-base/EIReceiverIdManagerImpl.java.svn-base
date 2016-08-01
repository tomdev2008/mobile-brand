package com.xiu.mobile.portal.ei.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.portal.ei.EIReceiverIdManager;
import com.xiu.uuc.facade.IdentityInfoFacade;
import com.xiu.uuc.facade.UserIdentityFacade;
import com.xiu.uuc.facade.dto.IdentityInfoDTO;
import com.xiu.uuc.facade.dto.IdentityInfoQueryDTO;
import com.xiu.uuc.facade.dto.Result;
import com.xiu.uuc.facade.dto.UserIdentityDTO;
import com.xiu.uuc.facade.dto.UserQueryIdentityDTO;
import com.xiu.uuc.facade.util.FacadeConstant;

@Service("eiReceiverIdManager")
public class EIReceiverIdManagerImpl implements EIReceiverIdManager{
	
	private static final Logger logger = Logger.getLogger(EIReceiverIdManagerImpl.class);
	
	@Autowired
	private UserIdentityFacade receiverIdentityFacade;
	
	@Autowired
	private IdentityInfoFacade identityInfoFacade;

	@Override
	public Result insertUserIdentity(UserIdentityDTO userIdentityDto) {
		Result result = null;
		try {
			if(userIdentityDto.getIdNumber()!=null&&!userIdentityDto.getIdNumber().equals("")){//默认为自动伞审核
				userIdentityDto.setIsReview(1l);
			}
			result = receiverIdentityFacade.insertUserIdentity(userIdentityDto);
		} catch (Exception e) {
			logger.error("新增收货人身份认证信息异常:",e);
			throw ExceptionFactory.buildEIRuntimeException("add.user.identity.error", e);
		}
		if(!FacadeConstant.SUCCESS.equals(result.isSuccess())){
			logger.error("新增收货人身份认证接口异常：errorMessage="+result.getErrorMessage());
			throw ExceptionFactory.buildEIBusinessException("add.user.identity.error",result.getErrorCode(),result.getErrorMessage());
		}
		return result;
	}

	@Override
	public Result deleteByAddressId(Long addressId) {
		Result result = null;
		try {
			result = receiverIdentityFacade.deleteByAddressId(addressId);
		} catch (Exception e) {
			logger.error("删除收货人身份认证信息异常:",e);
			throw ExceptionFactory.buildEIRuntimeException("delete.user.identity.error", e);
		}
		if(!FacadeConstant.SUCCESS.equals(result.isSuccess())){
			logger.error("删除收货人身份认证接口异常：errorMessage="+result.getErrorMessage());
			throw ExceptionFactory.buildEIBusinessException("delete.user.identity.error",result.getErrorCode(),result.getErrorMessage());
		}
		return result;
	}

	@Override
	public Result getUserIdentityListByObject(UserQueryIdentityDTO userQueryIdentityDTO) {
		Result result = null;
		try {
			result = receiverIdentityFacade.getUserIdentityListByObject(userQueryIdentityDTO);
		} catch (Exception e) {
			logger.error("查询收货人身份认证列表信息异常:",e);
			throw ExceptionFactory.buildEIRuntimeException("query.user.identity.list.error", e);
		}
		if(!FacadeConstant.SUCCESS.equals(result.isSuccess())){
			logger.error("查询收货人身份认证列表接口异常：errorMessage="+result.getErrorMessage());
			throw ExceptionFactory.buildEIBusinessException("query.user.identity.list.error",result.getErrorCode(),result.getErrorMessage());
		}
		return result;
	}

	@Override
	public Result getUserIdentity(UserIdentityDTO userIdentityDto) {
		Result result = null;
		try {
			result = receiverIdentityFacade.getUserIdentity(userIdentityDto);
		} catch (Exception e) {
			logger.error("查询收货人身份认证信息异常:",e);
			throw ExceptionFactory.buildEIRuntimeException("query.user.identity.error", e);
		}
		if(!FacadeConstant.SUCCESS.equals(result.isSuccess())){
			logger.error("查询收货人身份认证接口异常：errorMessage="+result.getErrorMessage());
			throw ExceptionFactory.buildEIBusinessException("query.user.identity.error",result.getErrorCode(),result.getErrorMessage());
		}
		return result;
	}

	@Override
	public Result modifyUserIdentity(UserIdentityDTO userIdentityDto) {
		Result result = null;
		try {
			if(userIdentityDto.getIdNumber()!=null&&!userIdentityDto.getIdNumber().equals("")){//默认为自动伞审核
				userIdentityDto.setIsReview(1l);
			}
			result = receiverIdentityFacade.modifyUserIdentity(userIdentityDto);
		} catch (Exception e) {
			logger.error("修改收货人身份认证信息异常:",e);
			throw ExceptionFactory.buildEIRuntimeException("update.user.identity.error", e);
		}
		if(!FacadeConstant.SUCCESS.equals(result.isSuccess())){
			logger.error("修改收货人身份认证接口异常：errorMessage="+result.getErrorMessage());
			throw ExceptionFactory.buildEIBusinessException("update.user.identity.error",result.getErrorCode(),result.getErrorMessage());
		}
		return result;
	}

	public Result queryIdentityInfoById(Long identityId) {
		Result result = null;
		try {
			result = identityInfoFacade.queryIdentityInfoById(identityId);
		} catch (Exception e) {
			logger.error("根据身份ID查询身份信息记录异常:",e);
			throw ExceptionFactory.buildEIRuntimeException("add.user.identity.error", e);
		}
		if(!FacadeConstant.SUCCESS.equals(result.isSuccess())){
			logger.error("根据身份ID查询身份信息记录异常：errorMessage="+result.getErrorMessage());
			throw ExceptionFactory.buildEIBusinessException("add.user.identity.error",result.getErrorCode(),result.getErrorMessage());
		}
		return result;
	}

	public Result queryIdentityInfoByUserIdAndName(Long userId, String rNameOrIdName) {
		Result result = null;
		try {
			result = identityInfoFacade.queryIdentityInfoByUserIdAndName(userId, rNameOrIdName);
		} catch (Exception e) {
			logger.error("根据用户ID和（收货人名称或者身份证姓名）查询身份信息记录异常:",e);
			throw ExceptionFactory.buildEIRuntimeException("add.user.identity.error", e);
		}
		if(!FacadeConstant.SUCCESS.equals(result.isSuccess())){
			logger.error("根据用户ID和（收货人名称或者身份证姓名）查询身份信息记录异常：errorMessage="+result.getErrorMessage());
			throw ExceptionFactory.buildEIBusinessException("add.user.identity.error",result.getErrorCode(),result.getErrorMessage());
		}
		return result;
	}

	public Result queryIdentityInfoList(IdentityInfoQueryDTO identityInfoQueryDTO) {
		Result result = null;
		try {
			result = identityInfoFacade.queryIdentityInfoList(identityInfoQueryDTO);
		} catch (Exception e) {
			logger.error("根据条件查询身份信息记录异常:",e);
			throw ExceptionFactory.buildEIRuntimeException("add.user.identity.error", e);
		}
		if(!FacadeConstant.SUCCESS.equals(result.isSuccess())){
			logger.error("根据条件查询身份信息记录异常：errorMessage="+result.getErrorMessage());
			throw ExceptionFactory.buildEIBusinessException("add.user.identity.error",result.getErrorCode(),result.getErrorMessage());
		}
		return result;
	}

	public Result updateIdentityInfo(IdentityInfoDTO identityInfoDTO) {
		Result result = null;
		try {
			if(identityInfoDTO.getIdNumber()!=null&&!identityInfoDTO.getIdNumber().equals("")){//默认为自动伞审核
				identityInfoDTO.setReviewState(1);
			}
			result = identityInfoFacade.updateIdentityInfo(identityInfoDTO);
		} catch (Exception e) {
			logger.error("更新身份信息记录异常:",e);
			throw ExceptionFactory.buildEIRuntimeException("add.user.identity.error", e);
		}
		if(!FacadeConstant.SUCCESS.equals(result.isSuccess())){
			logger.error("更新身份信息记录异常：errorMessage="+result.getErrorMessage());
			throw ExceptionFactory.buildEIBusinessException("add.user.identity.error",result.getErrorCode(),result.getErrorMessage());
		}
		return result;
	}

	public Result deleteIdentityInfoListByIds(List<Long> indetityIds) {
		Result result = null;
		try {
			result = identityInfoFacade.deleteIdentityInfoListByIds(indetityIds);
		} catch (Exception e) {
			logger.error("批量删除多条身份信息记录异常:",e);
			throw ExceptionFactory.buildEIRuntimeException("add.user.identity.error", e);
		}
		if(!FacadeConstant.SUCCESS.equals(result.isSuccess())){
			logger.error("批量删除多条身份信息记录接口异常：errorMessage="+result.getErrorMessage());
			throw ExceptionFactory.buildEIBusinessException("add.user.identity.error",result.getErrorCode(),result.getErrorMessage());
		}
		return result;
	}

	public Result deleteIdentityInfoById(Long indetityId) {
		Result result = null;
		try {
			result = identityInfoFacade.deleteIdentityInfoById(indetityId);
		} catch (Exception e) {
			logger.error("删除身份信息记录异常:",e);
			throw ExceptionFactory.buildEIRuntimeException("add.user.identity.error", e);
		}
		if(!FacadeConstant.SUCCESS.equals(result.isSuccess())){
			logger.error("删除身份信息记录接口异常：errorMessage="+result.getErrorMessage());
			throw ExceptionFactory.buildEIBusinessException("add.user.identity.error",result.getErrorCode(),result.getErrorMessage());
		}
		return result;
	}

	public Result insertIdentityInfo(IdentityInfoDTO identityInfoDTO) {
		Result result = null;
		try {
			if(identityInfoDTO.getIdNumber()!=null&&!identityInfoDTO.getIdNumber().equals("")){//默认为自动伞审核
				identityInfoDTO.setReviewState(1);
			}
			result = identityInfoFacade.insertIdentityInfo(identityInfoDTO);
		} catch (Exception e) {
			logger.error("新增收货人身份认证信息异常:",e);
			throw ExceptionFactory.buildEIRuntimeException("add.user.identity.error", e);
		}
		if(!FacadeConstant.SUCCESS.equals(result.isSuccess())){
			logger.error("新增收货人身份认证接口异常：errorMessage="+result.getErrorMessage());
			throw ExceptionFactory.buildEIBusinessException("add.user.identity.error",result.getErrorCode(),result.getErrorMessage());
		}
		return result;
	}

}
