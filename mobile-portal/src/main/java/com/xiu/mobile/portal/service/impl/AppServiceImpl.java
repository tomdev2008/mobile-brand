package com.xiu.mobile.portal.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.mobile.core.model.AppStartManagerModel;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.util.ImageServiceConvertor;
import com.xiu.mobile.portal.dao.AppDao;
import com.xiu.mobile.portal.model.AppVo;
import com.xiu.mobile.portal.service.IAppService;

@Transactional
@Service("appService")
public class AppServiceImpl implements IAppService {
	Logger logger = Logger.getLogger(AppServiceImpl.class);
	@Autowired
	AppDao appDao;

	@Override
	public int addApp(AppVo appVo) {
		int result = 0;
		try {
			// 把以前的数据的isLast都设置为N
			appDao.updateAppIsLastByType(appVo.getAppType());
			// 插入最新的数据
			appVo.setIsLast("Y");
			Date dat = new Date();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			appVo.setCreateTime(df.format(dat));
			appDao.insertApp(appVo);
			return result;
		} catch (Exception e) {
			result = -1;
			logger.error("添加app版本信息失败！", e);
		}
		return result;
	}

	@Override
	public boolean isNeedUpdate(String appType, String appVersion) {
		AppVo app = queryLatestAppByType(appType);
		return null != app && !appVersion.equals(app.getAppVersion());
	}

	@Override
	public AppVo queryLatestAppByType(String appType) {

		return appDao.selectLatestAppByType(appType);
	}

	public AppStartManagerModel getLatelyAppStartPage(Map map) {
		AppStartManagerModel appStartMangaer = appDao.getLatelyAppStartPage(map);
		if(appStartMangaer != null) {
			String picPath =  appStartMangaer.getUrl();
        	int index = 0;
        	int lastNum = 0;
        	if(StringUtils.isNotBlank(picPath)) {
        		index = picPath.lastIndexOf(".");
            	lastNum = Integer.parseInt(picPath.substring(index-1, index),16);
            	appStartMangaer.setUrl(ImageServiceConvertor.replaceImageDomain(XiuConstant.MOBILE_IMAGE[lastNum%3]+ picPath)); //图片地址拼串
        	}
		}
		
		return appStartMangaer;
	}

}
