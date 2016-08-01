package com.xiu.mobile.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.mobile.core.model.FindMenuMgt;
import com.xiu.mobile.core.model.MobileCommonData;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.util.ImageServiceConvertor;
import com.xiu.mobile.portal.dao.FindMenuDao;
import com.xiu.mobile.portal.service.IFindMenuService;

@Transactional
@Service("findMenuService")
public class FindMenuServiceImpl implements IFindMenuService {

	private Logger logger = Logger.getLogger(FindMenuServiceImpl.class);
	
	@Autowired
	private FindMenuDao findMenuDao;
	
	public List<FindMenuMgt> getFindMenuList(Map map) {
		List<FindMenuMgt> resultList = findMenuDao.getFindMenuList(map); 
		if(resultList != null && resultList.size() > 0) {
			for(FindMenuMgt vo : resultList) {
				String picPath =  vo.getIconUrl();
	        	int index = 0;
	        	int lastNum = 0;
	        	if(StringUtils.isNotBlank(picPath)) {
	        		index = picPath.lastIndexOf(".");
	            	lastNum = Integer.parseInt(picPath.substring(index-1, index),16);
	                vo.setIconUrl(ImageServiceConvertor.replaceImageDomain(XiuConstant.MOBILE_IMAGE[lastNum%3]+ picPath)); //图片地址拼串
	        	}
			}
		}
		return resultList;
	}

	public int getFindMenuCount(Map map) {
		return findMenuDao.getFindMenuCount(map);
	}

	public FindMenuMgt getFindMenuById(Map map) {
		FindMenuMgt vo = findMenuDao.getFindMenuById(map); 
		if(vo != null) {
			String picPath =  vo.getIconUrl();
        	int index = 0;
        	int lastNum = 0;
        	if(StringUtils.isNotBlank(picPath)) {
        		index = picPath.lastIndexOf(".");
            	lastNum = Integer.parseInt(picPath.substring(index-1, index),16);
                vo.setIconUrl(ImageServiceConvertor.replaceImageDomain(XiuConstant.MOBILE_IMAGE[lastNum%3]+ picPath)); //图片地址拼串
        	}
		}
		return vo;
	}

	public MobileCommonData getFindChannelVersion(Map map) {
		return findMenuDao.getFindChannelVersion(map);
	}
	
	
	public List<FindMenuMgt> getFindMenuListByBlock(Long block) {
		List<FindMenuMgt> resultList = findMenuDao.getFindMenuListByBlock(block); 
		if(resultList != null && resultList.size() > 0) {
			for(FindMenuMgt vo : resultList) {
				String iconClickUrl =  vo.getIconClickUrl();
				String picPath =  vo.getIconUrl();
	        	int index = 0;
	        	int lastNum = 0;
	        	if(StringUtils.isNotBlank(picPath)) {
	        		index = picPath.lastIndexOf(".");
	            	lastNum = Integer.parseInt(picPath.substring(index-1, index),16);
	                vo.setIconUrl(ImageServiceConvertor.replaceImageDomain(XiuConstant.MOBILE_IMAGE[lastNum%3]+ picPath)); //图片地址拼串
	        	}
	        	if(StringUtils.isNotBlank(iconClickUrl)) {
	        		index = iconClickUrl.lastIndexOf(".");
	        		lastNum = Integer.parseInt(iconClickUrl.substring(index-1, index),16);
	        		vo.setIconClickUrl(ImageServiceConvertor.replaceImageDomain(XiuConstant.MOBILE_IMAGE[lastNum%3]+ iconClickUrl)); //图片地址拼串
	        	}
			}
		}
		return resultList;
	}
	
	


}
