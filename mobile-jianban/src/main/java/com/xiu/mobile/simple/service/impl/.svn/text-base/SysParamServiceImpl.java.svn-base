package com.xiu.mobile.simple.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.simple.constants.ErrConstants;
import com.xiu.mobile.simple.ei.EISysParamManager;
import com.xiu.mobile.simple.service.ISysParamService;

@Service("sysParamService")
public class SysParamServiceImpl implements ISysParamService {

	@Autowired
	private EISysParamManager eiSysParamManager;
	
	@Override
	public String getAddressDescByCode(String code) {
		try{
			return eiSysParamManager.getAddressDescByCode(code);
		}catch(Exception e){
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_ADDRESS_BIZ_ERR, 
					ErrConstants.EIErrorCode.EI_UUC_ADDRESS_BIZ_ERR,
					"获取AddressDesc失败");
		}
	}

}
