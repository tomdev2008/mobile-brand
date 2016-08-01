package com.xiu.mobile.portal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.portal.constants.ErrConstants;
import com.xiu.mobile.portal.dao.SysParamsDao;
import com.xiu.mobile.portal.ei.EISysParamManager;
import com.xiu.mobile.portal.model.SysParamsVo;
import com.xiu.mobile.portal.service.ISysParamService;

@Service("sysParamService")
public class SysParamServiceImpl implements ISysParamService {

	@Autowired
	private EISysParamManager eiSysParamManager;
	@Autowired
	private SysParamsDao sysParamsDao;
	
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

	@Override
	public List<SysParamsVo> getAppSysParamsList(Map map) {
		return sysParamsDao.getSysParamsList(map);
	}

}
