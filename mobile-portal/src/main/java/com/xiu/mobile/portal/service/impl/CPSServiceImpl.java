package com.xiu.mobile.portal.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.common.command.result.Result;
import com.xiu.mobile.portal.ei.EICPSManager;
import com.xiu.mobile.portal.service.ICPSService;

/**
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION :
 * @AUTHOR : jack.jia@xiu.com
 * @DATE :2015-6-11 上午11:08:28
 *       </p>
 **************************************************************** 
 */
@Service("cpsService")
public class CPSServiceImpl implements ICPSService {
	private static final Logger logger = Logger.getLogger(CPSServiceImpl.class);

	@Autowired
	private EICPSManager eiCPSManager;

	@Override
	public String queryCookiesInfo(String xiuKcps, String cpsCode) {
		try {
			Result result = eiCPSManager.queryCookiesInfo(xiuKcps, cpsCode);
			if (result.isSuccess()) {
				return (String) result.getDefaultModel();
			}
		} catch (Exception e) {
			logger.error("调用CPS cookie解密接口异常,xiukcps: " + xiuKcps + ",cpscode:"
					+ cpsCode, e);
		}
		return "";
	}
}
