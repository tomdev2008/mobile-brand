package com.xiu.mobile.portal.service;

import com.xiu.mobile.core.model.AppPatchVO;
import com.xiu.mobile.portal.model.AppVo;

public interface IAppPatchService {
	
	public AppPatchVO getAppPatchVOByCondition(AppVo appVo );
}
