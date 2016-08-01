package com.xiu.mobile.portal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.portal.ei.EIReceiverIdManager;
import com.xiu.mobile.portal.service.IReceiverIdService;
import com.xiu.uuc.facade.dto.IdentityInfoDTO;
import com.xiu.uuc.facade.dto.IdentityInfoQueryDTO;
import com.xiu.uuc.facade.dto.Result;
import com.xiu.uuc.facade.dto.UserIdentityDTO;
import com.xiu.uuc.facade.dto.UserQueryIdentityDTO;
import com.xiu.uuc.facade.util.FacadeConstant;

@Service
public class ReceiverIdServiceImpl implements IReceiverIdService{
	
	@Autowired
	private EIReceiverIdManager eiReceiverIdManager;

	@Override
	public Long insertUserIdentity(UserIdentityDTO userIdentityDto) {
		Long addressId = 0L;
		Result result = eiReceiverIdManager.insertUserIdentity(userIdentityDto);
		if (result!=null && FacadeConstant.SUCCESS.equals(result.isSuccess())) {
			addressId = (Long) result.getData();
		}
		return addressId;
	}

	@Override
	public boolean deleteByAddressId(Long addressId) {
		boolean flag = false;
		Result result = eiReceiverIdManager.deleteByAddressId(addressId);
		if (result!=null && "1".equals(result.getSuccess())) {
			flag = true;
		}
		return flag;
	}

	@Override
	public Page<UserQueryIdentityDTO> getUserIdentityListByObject(UserQueryIdentityDTO userQueryIdentityDTO) {
		Page<UserQueryIdentityDTO> dataPage = new Page<UserQueryIdentityDTO>();
		Result result = eiReceiverIdManager.getUserIdentityListByObject(userQueryIdentityDTO);
		if (result!=null && FacadeConstant.SUCCESS.equals(result.isSuccess())) {
			// 获取分页和列表数据信息
			com.xiu.uuc.facade.util.Page page = result.getPage();
			List<UserQueryIdentityDTO> userQueryIdentityDTOList = (List<UserQueryIdentityDTO>) result.getData();
			dataPage.setPageNo(page.getCurrentPage());
			dataPage.setPageSize(page.getPageSize());
			dataPage.setResult(userQueryIdentityDTOList);
			dataPage.setTotalCount(page.getTotalRecord());
		}
		return dataPage;
	}

	@Override
	public UserIdentityDTO getUserIdentity(String userId,String addressId) {
		UserIdentityDTO userIdentityDTO = new UserIdentityDTO();
		userIdentityDTO.setAddressId(Long.parseLong(addressId));
		userIdentityDTO.setUserId(Long.parseLong(userId));
		Result result = eiReceiverIdManager.getUserIdentity(userIdentityDTO);
		if (result!=null && FacadeConstant.SUCCESS.equals(result.isSuccess())) {
			userIdentityDTO = (UserIdentityDTO) result.getData();
		}
		return userIdentityDTO;
	}

	@Override
	public boolean modifyUserIdentity(UserIdentityDTO userIdentityDto) {
		boolean flag = false;
		Result result = eiReceiverIdManager.modifyUserIdentity(userIdentityDto);
		if (result!=null && "1".equals(result.getSuccess())) {
			flag = true;
		}
		return flag;
	}
	
	public IdentityInfoDTO queryIdentityInfoById(Long identityId) {
		IdentityInfoDTO identityInfoDTO = null;
		Result result = eiReceiverIdManager.queryIdentityInfoById(identityId);
		if(result != null && "1".equals(result.getSuccess())) {
			identityInfoDTO = (IdentityInfoDTO) result.getData();
		}
		return identityInfoDTO;
	}

	public Long insertIdentityInfo(IdentityInfoDTO identityInfoDTO) {
		Long identityId = null;
		Result result = eiReceiverIdManager.insertIdentityInfo(identityInfoDTO);
		if (result!=null && FacadeConstant.SUCCESS.equals(result.isSuccess())) {
			identityId = (Long) result.getData();
		}
		return identityId;
	}

	public Integer updateIdentityInfo(IdentityInfoDTO identityInfoDTO) {
		Integer count = null;
		Result result = eiReceiverIdManager.updateIdentityInfo(identityInfoDTO);
		if (result!=null && "1".equals(result.getSuccess())) {
			count = (Integer) result.getData();
		}
		return count;
	}

	public IdentityInfoDTO queryIdentityInfoByUserIdAndName(Long userId, String rNameOrIdName) {
		IdentityInfoDTO identityInfoDTO = null;
		Result result = eiReceiverIdManager.queryIdentityInfoByUserIdAndName(userId, rNameOrIdName);
		if(result != null && "1".equals(result.getSuccess())) {
			identityInfoDTO = (IdentityInfoDTO) result.getData();
		}
		return identityInfoDTO;
	}

	public Long deleteIdentityInfoById(Long indetityId) {
		Long count = null;
		Result result = eiReceiverIdManager.deleteIdentityInfoById(indetityId);
		if (result!=null && "1".equals(result.getSuccess())) {
			count = (Long) result.getData();
		}
		return count;
	}

}
