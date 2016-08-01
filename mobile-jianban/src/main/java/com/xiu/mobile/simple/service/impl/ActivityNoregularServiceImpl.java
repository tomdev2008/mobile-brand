/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-14 下午3:19:22 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.simple.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.simple.common.constants.XiuConstant;
import com.xiu.mobile.simple.common.util.ImageServiceConvertor;
import com.xiu.mobile.simple.dao.ActivityNoregularDao;
import com.xiu.mobile.simple.model.ActivityVo;
import com.xiu.mobile.simple.model.Topic;
import com.xiu.mobile.simple.service.IActivityNoregularService;

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
        	int index = picPath.lastIndexOf(".");
        	int lastNum = Integer.parseInt(picPath.substring(index-1, index),16);
            vo.setMobile_pic(ImageServiceConvertor.replaceImageDomain(
            		XiuConstant.MOBILE_IMAGE[lastNum%3]+ picPath));
        }
        
        return list;
	}

	@Override
	public String queryTopicNameByActivityId(String activityId) {
		return activityNoregularDao.queryTopicNameByActivityId(activityId);
	}

	@Override
	public int getTopicTotalAmount(Map<String, Object> valMap) {
		return activityNoregularDao.getTopicTotalAmount(valMap);
	}

}
