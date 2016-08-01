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
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.core.model.AdvertisementFrame;
import com.xiu.mobile.core.service.IAdvertisementFrameService;

/**
 * 
* @Description: TODO(广告帧) 
* @author haidong.luo@xiu.com
* @date 2015年9月8日 下午3:31:03 
*
 */
@Transactional
@Service("advertisementFrameService")
public class AdvertisementFrameServiceImpl implements IAdvertisementFrameService {
	

	//日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(AdvertisementFrameServiceImpl.class);
	
	 @Autowired
	 private AdvertisementFrameDao advertisementFrameDao;
	 @Autowired
	 private AdvertisementDao advertisementDao;
	 

	@Override
	public List<AdvertisementFrame> getAdvertisementFrameList(Map<Object, Object> map) {
		// TODO Auto-generated method stub
		List<AdvertisementFrame> advertisementFrameList=new ArrayList<AdvertisementFrame>();
		try {
			 
			 advertisementFrameList= advertisementFrameDao.getAdvertisementFrameList(map);
		    } catch (Throwable e) {
				LOGGER.error("查询广告位异常！",e);
				throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_WAP_ERR, "查询广告位异常！");
			}
		return advertisementFrameList;
	}

	
	public List<AdvertisementFrame> getAdvertisementFrameListAll() {
		List<AdvertisementFrame> advertisementFrameList=new ArrayList<AdvertisementFrame>();
		try {
			 
			 advertisementFrameList= advertisementFrameDao.getAdvertisementFrameListAll();
		    } catch (Throwable e) {
				LOGGER.error("查询广告位异常！",e);
				throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_WAP_ERR, "查询广告位异常！");
			}
		return advertisementFrameList;
	}

	
	@Override
	public int save(AdvertisementFrame advertisementFrame) {
		// TODO Auto-generated method stub
		int result=0;
        try {
            if(advertisementFrameDao.isExistAdvFrame(advertisementFrame)>0){
                result=1;
            }else{
            	//查询该广告位下有几个广告帧
            	Map params=new HashMap();
            	params.put("advPlaceId", advertisementFrame.getAdvPlaceId());
            	int count=advertisementFrameDao.getAdvertisementFrameTotalCount(params);
            	advertisementFrame.setOrderSeq(count+1);
            	if(advertisementFrameDao.save(advertisementFrame)>0){
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
	public int update(AdvertisementFrame advertisementFrame) {
		// TODO Auto-generated method stub
		 int result=0;
	        try {
	        	 if(advertisementFrameDao.isExistAdvFrame(advertisementFrame)>0){
	                 result=1;
	             }else{
	            	 if(advertisementFrameDao.update(advertisementFrame)>0){
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
	public int delete(AdvertisementFrame advertisementFrame) {
		// TODO Auto-generated method stub
		List<AdvertisementFrame> advertisementFrameList=new ArrayList<AdvertisementFrame>();
		Map deleteParams=new HashMap();
		deleteParams.put("deleteFlag",advertisementFrame.getDeleteFlag());
		 int result=0;
	        try {
	        	advertisementFrame=advertisementFrameDao.getAdvertisementFrameById(advertisementFrame.getId());
	            if(advertisementFrameDao.delete(advertisementFrame)>0){
	            	//删除对应广告
	            	deleteParams.put("advFrameId", advertisementFrame.getId());
	            	advertisementDao.deleteByAdvFrameId(deleteParams);
	            	
	            	//修改同一广告位下的广告帧的顺序
	            	Map params=new HashMap();
	    			params.put("advPlaceId", advertisementFrame.getAdvPlaceId());
	    			 advertisementFrameList= advertisementFrameDao.getAdvertisementFrameList(params);
	    			 int size=advertisementFrameList.size();
	    			 for (int i = 0; i < size; i++) {
	    				 AdvertisementFrame frame= advertisementFrameList.get(i);
	    				 frame.setOrderSeq(i+1);
	    				 update(frame);
	    			}
	            	
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
	public AdvertisementFrame getAdvertisementFrameById(Long id) {
		// TODO Auto-generated method stub
			AdvertisementFrame advertisementFrame=null;
	        try {
	        	advertisementFrame=advertisementFrameDao.getAdvertisementFrameById(id);
	        } catch (Throwable e) {
	            LOGGER.error("根据广告位ID查询广告位异常！",e);
	            throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_WAP_ERR, "根据广告位ID查询广告位异常！");
	        }
	        return advertisementFrame;
	}
	
//	/**
//	 * 更新顺序
//	 * @param id
//	 */
//	public void updateOrder(Long id){
//		List<AdvertisementFrame> advertisementFrameList=new ArrayList<AdvertisementFrame>();
//		try {
//			Map params=new HashMap();
//			params.put("advPlaceId", id);
//			 advertisementFrameList= advertisementFrameDao.getAdvertisementFrameList(params);
//			 int size=advertisementFrameList.size();
//			 for (int i = 0; i < size; i++) {
//				 AdvertisementFrame frame= advertisementFrameList.get(i);
//				 frame.setOrderSeq(i+1);
//				 update(frame);
//			}
//		    } catch (Throwable e) {
//				LOGGER.error("查询广告位异常！",e);
//				throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_WAP_ERR, "查询广告位异常！");
//			}
//	}

	/**修改顺序
	 * ID 转移的id
	 * beId 被转移的id
	 * type 0上移 1下移
	 * @param map
	 * @return
	 */
	public int updateOrder(Map<Object, Object> map) {
	 Long id=(Long)map.get("id");
	 Long beId=(Long)map.get("beId");
	 Integer type=(Integer)map.get("type");
	 int result=0;
	 try {
		AdvertisementFrame advFrame= advertisementFrameDao.getAdvertisementFrameById(id);
		AdvertisementFrame beAdvFrame= advertisementFrameDao.getAdvertisementFrameById(beId);
		if(advFrame!=null&&beAdvFrame!=null){
			int order=advFrame.getOrderSeq();
			beAdvFrame.setOrderSeq(order);
			if(type==0&&order>1){//上移
				//修改选择的节点上移
				advFrame.setOrderSeq(advFrame.getOrderSeq()-1);
			}else if(type==1){//下移
	        	Map params=new HashMap();
	        	params.put("advPlaceId", advFrame.getAdvPlaceId());
				int count=advertisementFrameDao.getAdvertisementFrameTotalCount(params);
				if(order<count){//如果不是最后一个位置则可以修改
					//修改选择的节点下移
					advFrame.setOrderSeq(advFrame.getOrderSeq()+1);
				}
			}
			advertisementFrameDao.update(beAdvFrame);
			advertisementFrameDao.update(advFrame);
		}
	 }catch(Throwable e){
		 result=-1;
		  LOGGER.error("广告帧移动异常！",e);
          throw ExceptionFactory.buildBusinessException(ErrConstants.BusinessErrorCode.BIZ_WAP_ERR, "广告帧移动异常！");
	 }
		return result;
	}


}
