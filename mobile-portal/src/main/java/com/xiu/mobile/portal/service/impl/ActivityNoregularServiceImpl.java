/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-14 下午3:19:22 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.core.model.TopicType;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.util.ImageServiceConvertor;
import com.xiu.mobile.portal.dao.ActivityNoregularDao;
import com.xiu.mobile.portal.model.ActivityVo;
import com.xiu.mobile.portal.model.Topic;
import com.xiu.mobile.portal.service.IActivityNoregularService;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-14 下午3:19:22 
 * ***************************************************************
 * </p>
 */
@Service
public class ActivityNoregularServiceImpl implements IActivityNoregularService {
	private static final Logger logger = LoggerFactory.getLogger(ActivityNoregularServiceImpl.class);
	
	@Autowired
	private ActivityNoregularDao activityNoregularDao;

	@Override
	public List<ActivityVo> queryActiveList(Map<String, Object> valMap) {
		return null;
	}

	@Override
	public List<ActivityVo> queryOutletsList(String saleType) {
		return null;
	}

	@Override
	public List<Topic> queryTopicList(Map<String, Object> valMap) {
		List<Topic> list= activityNoregularDao.queryTopicList(valMap);
		
        for(Topic vo : list) {
        	String picPath =  vo.getMobile_pic();
        	int index = 0;
        	int lastNum = 0;
        	if(StringUtils.isNotBlank(picPath)) {
        		index = picPath.lastIndexOf(".");
            	lastNum = Integer.parseInt(picPath.substring(index-1, index),16);
                vo.setMobile_pic(ImageServiceConvertor.replaceImageDomain(XiuConstant.MOBILE_IMAGE[lastNum%3]+ picPath));
        	}
        	
            String bannerPic  = vo.getBannerPic();
            if (StringUtils.isNotBlank(bannerPic)) {
            	 index = bannerPic.lastIndexOf(".");
             	lastNum = Integer.parseInt(bannerPic.substring(index-1, index),16);
             	vo.setBannerPic(ImageServiceConvertor.replaceImageDomain(XiuConstant.MOBILE_IMAGE[lastNum%3]+ bannerPic));
			}
        }
        
        return list;
	}

	@Override
	public Topic getTopicByActId(String actId) {
		Topic topic = activityNoregularDao.getTopicByActId(actId);
		if(null == topic){
			logger.error(" getTopicByActId() activity is null, and ActId is :" + actId);
			return topic;
		}
		
    	String picPath =  topic.getMobile_pic();
    	int index = 0;
    	int lastNum = 0;
    	if(StringUtils.isNotBlank(picPath)) {
    		index = picPath.lastIndexOf(".");
        	lastNum = Integer.parseInt(picPath.substring(index-1, index),16);
        	topic.setMobile_pic(ImageServiceConvertor.replaceImageDomain(XiuConstant.MOBILE_IMAGE[lastNum%3]+ picPath));
    	}
    	
        String bannerPic  = topic.getBannerPic();
        if (StringUtils.isNotBlank(bannerPic)) {
       	 	index = bannerPic.lastIndexOf(".");
        	lastNum = Integer.parseInt(bannerPic.substring(index-1, index),16);
        	topic.setBannerPic(ImageServiceConvertor.replaceImageDomain(XiuConstant.MOBILE_IMAGE[lastNum%3]+ bannerPic));
		}
		return topic;
	}

	@Override
	public int getTopicTotalAmount(Map<String, Object> valMap) {
		return activityNoregularDao.getTopicTotalAmount(valMap);
	}

	@Override
	public List<Topic> getTopicList(Map<String, Object> valMap) {
		List<Topic> list = activityNoregularDao.getTopicList(valMap);
		for(Topic vo : list) {
        	String picPath =  vo.getMobile_pic();
        	int index = 0;
        	int lastNum = 0;
        	if(StringUtils.isNotBlank(picPath)) {
        		index = picPath.lastIndexOf(".");
            	lastNum = Integer.parseInt(picPath.substring(index-1, index),16);
                vo.setMobile_pic(ImageServiceConvertor.replaceImageDomain(XiuConstant.MOBILE_IMAGE[lastNum%3]+ picPath));
        	}
            String bannerPic  = vo.getBannerPic();
            if (StringUtils.isNotBlank(bannerPic)) {
              	index = bannerPic.lastIndexOf(".");
               	lastNum = Integer.parseInt(bannerPic.substring(index-1, index),16);
               	vo.setBannerPic(ImageServiceConvertor.replaceImageDomain(XiuConstant.MOBILE_IMAGE[lastNum%3]+ bannerPic));
       		}
        }
		return list;
	}

	@Override
	public int getTopicTotal(Map<String, Object> valMap) {
		return activityNoregularDao.getTopicTotal(valMap);
	}

	@Override
	public List<Topic> getTopicListBySetId(Map<String, Object> valMap) {
		List<Topic> list = activityNoregularDao.getTopicListBySetId(valMap);
		for(Topic vo : list) {
        	String picPath =  vo.getMobile_pic();
        	int index = 0;
        	int lastNum = 0;
        	if(StringUtils.isNotBlank(picPath)) {
        		index = picPath.lastIndexOf(".");
            	lastNum = Integer.parseInt(picPath.substring(index-1, index),16);
                vo.setMobile_pic(ImageServiceConvertor.replaceImageDomain(XiuConstant.MOBILE_IMAGE[lastNum%3]+ picPath));
        	}
            String bannerPic  = vo.getBannerPic();
            if (StringUtils.isNotBlank(bannerPic)) {
              	index = bannerPic.lastIndexOf(".");
               	lastNum = Integer.parseInt(bannerPic.substring(index-1, index),16);
               	vo.setBannerPic(ImageServiceConvertor.replaceImageDomain(XiuConstant.MOBILE_IMAGE[lastNum%3]+ bannerPic));
       		}
        }
		return list;
	}

	@Override
	public int getTopicCountBySetId(Map<String, Object> valMap) {
		return activityNoregularDao.getTopicCountBySetId(valMap);
	}

	public List<TopicType> getTopicTypeList(Map map) {
		return activityNoregularDao.getTopicTypeList(map);
	}

	public List<Topic> getTopicListNew(Map<String, Object> valMap) {
		List<Topic> list = activityNoregularDao.getTopicListNew(valMap);
		for(Topic vo : list) {
        	String picPath =  vo.getMobile_pic();
        	int index = 0;
        	int lastNum = 0;
        	if(StringUtils.isNotBlank(picPath)) {
        		index = picPath.lastIndexOf(".");
            	lastNum = Integer.parseInt(picPath.substring(index-1, index),16);
                vo.setMobile_pic(ImageServiceConvertor.replaceImageDomain(XiuConstant.MOBILE_IMAGE[lastNum%3]+ picPath));
        	}
            String bannerPic  = vo.getBannerPic();
            if (StringUtils.isNotBlank(bannerPic)) {
              	index = bannerPic.lastIndexOf(".");
               	lastNum = Integer.parseInt(bannerPic.substring(index-1, index),16);
               	vo.setBannerPic(ImageServiceConvertor.replaceImageDomain(XiuConstant.MOBILE_IMAGE[lastNum%3]+ bannerPic));
       		}
        }
		return list;
	}

	public int getTopicTotalNew(Map<String, Object> valMap) {
		return activityNoregularDao.getTopicTotalNew(valMap);
	}

}
