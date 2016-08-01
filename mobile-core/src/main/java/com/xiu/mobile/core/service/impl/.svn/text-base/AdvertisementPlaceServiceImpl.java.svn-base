package com.xiu.mobile.core.service.impl;

import java.util.ArrayList;
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
import com.xiu.mobile.core.dao.AdvertisementFrameDao;
import com.xiu.mobile.core.dao.AdvertisementPlaceDao;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.core.model.AdvertisementPlace;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.service.IAdvertisementPlaceService;

/**
 * 
* @Description: TODO(广告组管理) 
* @author haidong.luo@xiu.com
* @date 2015年9月8日 下午3:30:19 
*
 */
@Transactional
@Service("advertisementPlaceService")
public class AdvertisementPlaceServiceImpl implements IAdvertisementPlaceService {
	

	//日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(AdvertisementPlaceServiceImpl.class);
	
	 @Autowired
	 private AdvertisementPlaceDao advertisementPlaceDao;
	 @Autowired
	 private AdvertisementFrameDao advertisementFrameDao;
	 @Autowired 
	 private AdvertisementDao advertisementDao;

	@Override
	public List<AdvertisementPlace> getAdvertisementPlaceList(Map<Object, Object> map,Page<?> page) {
		// TODO Auto-generated method stub
		List<AdvertisementPlace> advertisementPlaceList=new ArrayList<AdvertisementPlace>();
		try {
			 int pageMax=page.getPageNo()*page.getPageSize();
			 int pageMin=1;
			 if(page.getPageNo()!=1){
				 pageMin=(pageMax-(page.getPageNo()-1)*page.getPageSize())+1;
			 }
			 map.put("pageMin", pageMin-1);
			 map.put("pageSize", page.getPageSize());
			 map.put("pageMax", pageMax);
			 
			 advertisementPlaceList= advertisementPlaceDao.getAdvertisementPlaceList(map);
		     page.setTotalCount(Integer.valueOf(advertisementPlaceDao.getAdvertisementPlaceTotalCount(map)));
		    } catch (Throwable e) {
				LOGGER.error("查询广告位异常！",e);
				throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_WAP_ERR, "查询广告位异常！");
			}
		return advertisementPlaceList;
	}

	@Override
	public int save(AdvertisementPlace advertisementPlace) {
		// TODO Auto-generated method stub
		int result=0;
		advertisementPlace.setCode(advertisementPlace.getCode().trim());
		advertisementPlace.setName(advertisementPlace.getName().trim());
        try {
            if(advertisementPlaceDao.isExistAdvPlace(advertisementPlace)>0){
                result=1;
            }else{
            	if(advertisementPlaceDao.save(advertisementPlace)>0){
            		 result=0;
            	}else{
            		result=-1;
            	}
            }
        } catch (Throwable e) {
            result=-1;
            LOGGER.error("保存广告位异常！",e);
            throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_WAP_ERR, "保存广告位异常！");
        }
        return result;
	}

	@Override
	public int update(AdvertisementPlace advertisementPlace) {
		// TODO Auto-generated method stub
		 int result=0;
			advertisementPlace.setCode(advertisementPlace.getCode().trim());
			advertisementPlace.setName(advertisementPlace.getName().trim());
	        try {
	        	 if(advertisementPlaceDao.isExistAdvPlace(advertisementPlace)>0){
	                 result=1;
	             }else{
	            	 if(advertisementPlaceDao.update(advertisementPlace)>0){
	            		 result=0;
	            	}else{
	            		result=-1;
	            	}
	             }
	        } catch (Throwable e) {
	            result=-1;
	            LOGGER.error("修改广告位异常！",e);
	            throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_WAP_ERR, "修改广告位异常！");
	        }
	        return result;
	}

	@Override
	public int delete(AdvertisementPlace advertisementPlace) {
		// TODO Auto-generated method stub
		 int result=0;
	        try {
	            if(advertisementPlaceDao.delete(advertisementPlace)>0){
	            	Map params=new HashMap();
	            	params.put("advPlaceId", advertisementPlace.getId());
	            	params.put("deleteFlag", advertisementPlace.getDeleted());
	            	//删除广告
	            	advertisementDao.deleteByAdvPlaceId(params);
	            	//删除广告帧
	            	advertisementFrameDao.deleteByAdvPlaceId(params);
	                result=0;
	            }else{
	                result=-1;
	            }
	        } catch (Throwable e) {
	            result=-1;
	            LOGGER.error("删除广告位异常！",e);
	            throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_WAP_ERR, "删除广告位异常！");
	        }
	        return result;
	}

	@Override
	public AdvertisementPlace getAdvertisementPlaceById(Long id) {
		// TODO Auto-generated method stub
			AdvertisementPlace advertisementPlace=null;
	        try {
	        	advertisementPlace=advertisementPlaceDao.getAdvertisementPlaceById(id);
	        } catch (Throwable e) {
	            LOGGER.error("根据广告位ID查询广告位异常！",e);
	            throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_WAP_ERR, "根据广告位ID查询广告位异常！");
	        }
	        return advertisementPlace;
	}

	@Override
	public List<AdvertisementPlace> getAdvertisementPlaceListAll(
			Map<Object, Object> map) {
		List<AdvertisementPlace> advertisementPlaceList=new ArrayList<AdvertisementPlace>();
		try {
			 
			 advertisementPlaceList= advertisementPlaceDao.getAdvertisementPlaceListAll(map);
		    } catch (Throwable e) {
				LOGGER.error("查询广告位异常！",e);
				throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_WAP_ERR, "查询广告位异常！");
			}
		return advertisementPlaceList;
	}

}
