package com.xiu.mobile.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.mobile.core.constants.ErrConstants;
import com.xiu.mobile.core.dao.AdvertisementDao;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.core.model.Advertisement;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.service.IAdvertisementService;
import com.xiu.mobile.core.utils.DateUtil;
import com.xiu.mobile.core.utils.ImageUtil;

/**
 * @author: gaoyuan
 * @description:广告管理
 * @date: 2013-11-14
 */
@Transactional
@Service("advertisementService")
public class AdvertisementServiceImpl implements IAdvertisementService {
	
	//日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(AdvertisementServiceImpl.class);
	
	 @Autowired
	 private AdvertisementDao advertisementDao;

	@Override
	public Map getAdvertisementList(Map<Object, Object> params,Page<?> page) {
		Map resultMap=new HashMap();//结果集
		Boolean resultStatus=false;
		List<Advertisement> advertisementlist=new ArrayList<Advertisement>();

			Object startDate= params.get("startDate");
			Object endDate= params.get("endDate");
			if(startDate!=null&&!startDate.equals("")){
				params.put("startDate",DateUtil.parseTime(startDate.toString()));
			}
			if(endDate!=null&&!endDate.equals("")){
				params.put("endDate",DateUtil.parseTime(endDate.toString()));
			}
			params.put("pageMin",page.getFirstRecord());
			params.put("pageSize", page.getPageSize());
			params.put("pageMax", page.getEndRecord());

		//查询列表
		 advertisementlist= advertisementDao.getAdvertisementList(params);
		 int size=advertisementlist.size();
		 Date now=new Date();
		 for (int i = 0; i < size; i++) {
			 Advertisement adv=advertisementlist.get(i);
			 if(now.after(adv.getStartTime())&&now.before(adv.getEndTime())){
				 adv.setStatus(1);//进行中
			 }else if(now.before(adv.getStartTime())){
				 adv.setStatus(0);//未开始
			 }else if(now.after(adv.getEndTime())){
				 adv.setStatus(2);//过期
			 }
			 adv.setPicPath(ImageUtil.getShowimageUrl(adv.getPicPath()));
		 }
	     page.setTotalCount(Integer.valueOf(advertisementDao.getAdvertisementTotalCount(params)));
		//成功
		resultStatus=true;
		
		resultMap.put("page", page);
		resultMap.put("status", resultStatus);
		resultMap.put("msg","");
		resultMap.put("resultInfo", advertisementlist);
		return resultMap;
	}

	@Override
	public Map save(Map params) {
		Map resultMap=new HashMap();//结果集
		boolean isSuccess =false;
		Advertisement advertisement=(Advertisement)params.get("model");
		//如果选择了广告帧，需判断广告帧的排期是否有重复
		if(advertisement.getAdvFrameId()!=null){
			Map checkMap=new HashMap();
			checkMap.put("begintime", advertisement.getStartTime());
			checkMap.put("endtime", advertisement.getEndTime());
			checkMap.put("advFrameId", advertisement.getAdvFrameId());
			//检查在同一个广告帧是否有时间重叠
			int num=advertisementDao.checkTimeByAdvPlace(checkMap);
			if(num>0){
				resultMap.put("status", isSuccess);
				resultMap.put("msg", "该广告帧下存在时间重叠的广告");
				return 	resultMap; 
			}
		}
		//增加
		 int updateNum=advertisementDao.save(advertisement);
		 if(updateNum>0){
			 isSuccess=true;
		 }
		 resultMap.put("status", isSuccess);
		 resultMap.put("msg", "");
		 return resultMap;
	}

	@Override
	public Map update(Map params) {
		Map resultMap=new HashMap();//结果集
		 boolean isSuccess =false;
		 Advertisement adv=(Advertisement)params.get("model");
		//查询绑定的链接对象是否存在
		 if(adv.getAdvFrameId()!=null){
			Map checkMap=new HashMap();
			checkMap.put("id", adv.getId());
			checkMap.put("begintime", adv.getStartTime());
			checkMap.put("endtime", adv.getEndTime());
			checkMap.put("advFrameId", adv.getAdvFrameId());
			int num=advertisementDao.checkTimeByAdvPlace(checkMap);
			if(num>0){
				resultMap.put("status", isSuccess);
				resultMap.put("msg", "该广告帧下存在时间重叠的广告");
				return 	resultMap; 
			}
	     }
		 
		 int updateNum=advertisementDao.update(adv);
		 if(updateNum>0){
			 isSuccess=true;
		 }
		 
		 resultMap.put("status", isSuccess);
		 return resultMap;
	}

	@Override
	public int delete(Advertisement advertisement) {
		// TODO Auto-generated method stub
		 int result=0;
	        try {
	            if(advertisementDao.delete(advertisement)>0){
	            	
	                result=0;
	            }else{
	                result=-1;
	            }
	        } catch (Throwable e) {
	            result=-1;
	            LOGGER.error("删除广告异常！",e);
	            throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_WAP_ERR, "删除广告异常！");
	        }
	        return result;
	}

	@Override
	public Advertisement getAdvertisementById(Long id) {
		// TODO Auto-generated method stub
		 	Advertisement advertisement=null;
	        try {
	        	advertisement=advertisementDao.getAdvertisementById(id);
	   		     Date now=new Date();
				 if(now.after(advertisement.getStartTime())&&now.before(advertisement.getEndTime())){
					 advertisement.setStatus(1);//进行中
				 }else if(now.before(advertisement.getStartTime())){
					 advertisement.setStatus(0);//未开始
				 }else if(now.after(advertisement.getEndTime())){
					 advertisement.setStatus(2);//过期
				 }
	        	advertisement.setPicPath(ImageUtil.getShowimageUrl(advertisement.getPicPath()));
	        } catch (Throwable e) {
	            LOGGER.error("根据广告ID查询广告异常！",e);
	            throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_WAP_ERR, "根据广告ID查询广告异常！");
	        }
	        return advertisement;
	}

	@Override
	public String getTimesByAdvFrameId(Map params) {
		Long advFrameId =(Long)params.get("advFrameId");
		
		List<Advertisement> advs=advertisementDao.getTimesByAdvFrameId(params);
		StringBuffer sb=new StringBuffer();
		int total=advs.size();
		for (int i=0;i<total;i++) {
			Advertisement adv=advs.get(i);
			if(i>0){
				sb.append("<br/>");
			}
			sb.append(DateUtil.formatDate(adv.getStartTime()));
			sb.append("到");
			sb.append(DateUtil.formatDate(adv.getEndTime()));
		}
		
		return sb.toString();
	}

}
