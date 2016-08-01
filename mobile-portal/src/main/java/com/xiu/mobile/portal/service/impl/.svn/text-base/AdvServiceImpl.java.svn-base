package com.xiu.mobile.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.mobile.portal.common.util.ImageUtil;
import com.xiu.mobile.portal.common.util.Tools;
import com.xiu.mobile.portal.dao.AdvDao;
import com.xiu.mobile.portal.dao.AdvViewLogDao;
import com.xiu.mobile.portal.model.AdvertisementVo;
import com.xiu.mobile.portal.service.IAdvService;

@Transactional
@Service("advService")
public class AdvServiceImpl implements IAdvService {
	Logger logger = Logger.getLogger(AdvServiceImpl.class);
	@Autowired
	AdvDao advertisementDao;
	@Autowired
	AdvViewLogDao advViewLogDao;
	
	/**
	 * 按类型获取广告
	 * @param map
	 * @return
	 */
	public List<AdvertisementVo> getAdvListByType(Map map) {
		
		List<AdvertisementVo> advs=advertisementDao.getAdvListByType(map);
		int size=advs.size();
		for (int i=0;i<size;i++) {
			AdvertisementVo adv=advs.get(i);
			adv.setPicPath(ImageUtil.getAdvImageUrl(adv.getPicPath()));
			adv.setOrderSeq(i+1);
			if(adv.getLinkType()==1){//url类型
				adv.setHttpUrl(adv.getLinkObject());
				adv.setLinkObject(null);
			}
			else if(adv.getLinkType()==7){
				adv.setHttpUrl(null);
			}else{
				adv.setXiuAppUrl(Tools.getXiuAppUrlByType(adv.getLinkType())+adv.getLinkObject());
//				adv.setLinkType(1);
//				adv.setLinkObject(null);
			}
		}
		return advs;
	}

	public Integer addAdvLog(Map map) {
		return advViewLogDao.addAdvViewLog(map);
	}

	/**
	 * 按类型查询广告
	 */
	public AdvertisementVo getAdvByType(Map map) {
		AdvertisementVo adv = advertisementDao.getAdvByType(map);
		if(adv != null){
			adv.setPicPath(ImageUtil.getAdvImageUrl(adv.getPicPath()));
			if(adv.getLinkType()==1){//url类型
				adv.setHttpUrl(adv.getLinkObject());
				adv.setLinkObject(null);
			} else if(adv.getLinkType()==7){
				adv.setHttpUrl(null);
			} else{
				adv.setXiuAppUrl(Tools.getXiuAppUrlByType(adv.getLinkType())+adv.getLinkObject());
//				adv.setLinkType(1);
//				adv.setLinkObject(null);
			}
		}
		return adv;
	}

	public List<AdvertisementVo> getAdvListByTypes(Map map) {
		List<AdvertisementVo> advs=advertisementDao.getAdvListByTypes(map);
		int size=advs.size();
		for (int i=0;i<size;i++) {
			AdvertisementVo adv=advs.get(i);
			adv.setPicPath(ImageUtil.getAdvImageUrl(adv.getPicPath()));
			adv.setOrderSeq(i+1);
			if(adv.getLinkType()==1){//url类型
				adv.setHttpUrl(adv.getLinkObject());
				adv.setLinkObject(null);
			}
			else if(adv.getLinkType()==7){
				adv.setHttpUrl(null);
			}else{
				adv.setXiuAppUrl(Tools.getXiuAppUrlByType(adv.getLinkType())+adv.getLinkObject());
//				adv.setLinkType(1);
//				adv.setLinkObject(null);
			}
		}
		return advs;
	}


}
