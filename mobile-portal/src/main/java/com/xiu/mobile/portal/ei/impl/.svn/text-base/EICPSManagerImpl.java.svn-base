package com.xiu.mobile.portal.ei.impl;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.common.command.result.Result;
import com.xiu.cps.CookiesInfoFacade;
import com.xiu.mobile.portal.ei.EICPSManager;

/**
 * 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : cps解密  UUC
 * @AUTHOR : jack.jia@xiu.com 
 * @DATE :2014年5月28日 下午3:34:14
 * </p>
 ****************************************************************
 */
@Service("eiCPSManager")
public class EICPSManagerImpl implements EICPSManager {
	
	private static final XLogger logger = XLoggerFactory.getXLogger(EICPSManagerImpl.class);
	
	@Autowired
    private CookiesInfoFacade cookiesInfoFacade;

	@Override
	public Result queryCookiesInfo(String xiukcps, String cpscode) {
		return  cookiesInfoFacade.queryCookiesInfo(xiukcps, cpscode);
	}
}
