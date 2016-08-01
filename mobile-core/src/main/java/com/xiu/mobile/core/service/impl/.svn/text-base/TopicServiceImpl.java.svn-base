package com.xiu.mobile.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.mobile.core.constants.ErrConstants;
import com.xiu.mobile.core.constants.GlobalConstants;
import com.xiu.mobile.core.dao.LabelDao;
import com.xiu.mobile.core.dao.TopicDao;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.core.model.LabelCentre;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.Topic;
import com.xiu.mobile.core.service.ILabelService;
import com.xiu.mobile.core.service.ISearchManagerService;
import com.xiu.mobile.core.service.ITopicService;
import com.xiu.mobile.core.utils.ObjectUtil;


/**
 * @author: 贾泽伟
 * @description:卖场活动
 * @date: 2014-03-18
 */
@Transactional
@Service("topicService")
public class TopicServiceImpl implements ITopicService {
	// 日志
	private static final XLogger LOGGER = XLoggerFactory
			.getXLogger(TopicServiceImpl.class);

	public TopicDao getTopicDao() {
		return topicDao;
	}

	public void setTopicDao(TopicDao topicDao) {
		this.topicDao = topicDao;
	}

	@Autowired
	private TopicDao topicDao;

    @Autowired
	private LabelDao labelDao;
	
	@Autowired
	private ILabelService labelService;
	
	@Autowired
	private ISearchManagerService	 searchService;
    
	@Override
	public List<Topic> getTopicList(Map<Object, Object> map, Page<?> page) {
		List<Topic> topicList = new ArrayList<Topic>();
		try {
			int lineMax = page.getPageNo() * page.getPageSize()+1;
			int lineMin = (page.getPageNo()-1)*page.getPageSize()+1;
			
			map.put("lineMax", lineMax);
			map.put("lineMin", lineMin);
			map.put("labelType",  GlobalConstants.LABEL_TYPE_TOPIC);
			topicList = topicDao.getTopicList(map);

			// get goods count
			List<BigDecimal> ids = new ArrayList<BigDecimal>();
			List<Long> topicIds = new ArrayList<Long>();
			for (int i = 0; i < topicList.size(); i++) {
				ids.add(topicList.get(i).getActivity_id());
				topicIds.add(topicList.get(i).getActivity_id().longValue());
			}

			if(ids != null && ids.size() > 0) {
				List<Map<String, Object>> resultMapList = getGoodsCountByActIds(ids);

				HashMap<BigDecimal, Long> resultMap = new HashMap<BigDecimal, Long>();
				for (int j = 0; j < resultMapList.size(); j++) {
					resultMap.put((BigDecimal)resultMapList.get(j).get("ACTIVITY_ID"),
							new Long(((BigDecimal)resultMapList.get(j).get("GOODS_COUNT")).longValue()));
				}

				for (int i = 0; i < topicList.size(); i++) {
					if (null != resultMap.get(topicList.get(i).getActivity_id())) {
						topicList.get(i).setGoods_count(
								resultMap.get(topicList.get(i).getActivity_id()));
					}
				}
//			//查询标签
				Map<String,Object> labelMap=new HashMap<String,Object>();
				labelMap.put("objectIds", topicIds);
				labelMap.put("type", GlobalConstants.LABEL_TYPE_TOPIC);//卖场
				List<LabelCentre> results=labelDao.findLabelsByObjectIds(labelMap);
				for(Topic topic:topicList){
					if(results!=null){
						for (LabelCentre lc:results) {
							if(lc.getObjectId().equals(topic.getActivity_id().longValue())){
								List<LabelCentre> topiclc=topic.getLabelCentre();
								if(topiclc==null){
									topiclc=new  ArrayList<LabelCentre>();
								}
								topiclc.add(lc);
								topic.setLabelCentre(topiclc);
							}
						}
					}
				}
			}
			
			page.setTotalCount(Integer.valueOf(topicDao.getTopicTotalCount(map)));
		} catch (Throwable e) {
			LOGGER.error("查询卖场列表异常！", e);
			throw ExceptionFactory.buildBusinessException(
					ErrConstants.BusinessErrorCode.BIZ_WAP_ERR, "查询卖场列表异常！");
		}
		return topicList;
	}

	@Override
	public Topic getTopicByActivityId(BigDecimal activityId) {
		return topicDao.getTopicByActivityId(activityId);
	}

	@Override
	public int update(Topic topic) {
		return topicDao.update(topic);
	}
	
	@Override
	public int insert(Topic topic) {
		return topicDao.insert(topic);
	}

	@Override
	public List<Map<String, Object>> getGoodsCountByActIds(List<BigDecimal> ids) {
		try {
			Map map = new HashMap();
			map.put("ids", ids);
			return topicDao.getGoodsCountByActIds(map);
		} catch (Throwable e) {
			LOGGER.error("修改卖场异常！", e);
			throw ExceptionFactory.buildBusinessException(
					ErrConstants.BusinessErrorCode.BIZ_WAP_ERR, "修改卖场异常！");
		}
	}
	
	@Override
	public void callSpSynTopicDataTask(){
		LOGGER.info("callSpSynTopicDataTask...");
		topicDao.callSpSynTopicDataTask();
	}
	@Override
	public void callSpUpdateBrandVsTopicTask(){
		LOGGER.error("SP_UPDATE_BRAND_TOPIC_RELATION...");
		topicDao.callSpUpdateBrandVsTopicTask();
	}

	//查询卖场集下的二级卖场
	public List<Topic> queryActivityBySet(Map map, Page<?> page) {
		page.setPageNo(page.getPageNo() == 0 ? 1 : page.getPageNo());
		int lineMax = page.getPageNo() * page.getPageSize()+1;
		int lineMin = (page.getPageNo()-1)*page.getPageSize()+1;
		map.put("lineMax", lineMax);
		map.put("lineMin", lineMin);
		
		//1.查询卖场集下的二级卖场
		List<Topic> topicList = topicDao.queryActivityBySet(map);	
		
		//2.查询卖场集下的二级卖场数量
		int counts = topicDao.getActivityCountsBySet(map);	
		page.setTotalCount(counts);
		
		return topicList;
	}
	
	//查询二级卖场所属卖场集
	public List<Topic> querySetByActivity(Map map, Page<?> page) {
		page.setPageNo(page.getPageNo() == 0 ? 1 : page.getPageNo());
		int lineMax = page.getPageNo() * page.getPageSize()+1;
		int lineMin = (page.getPageNo()-1)*page.getPageSize()+1;
		//卖场编辑页面暂未实现分页查询  最大查询数量加1000个
		lineMax = lineMax + 1000;
		map.put("lineMax", lineMax); 
		map.put("lineMin", lineMin);
		//1.查询二级卖场所属卖场集
		List<Topic> topicList = topicDao.querySetByActivity(map);
		
		//2.查询二级卖场所属卖场集数量
		int counts = topicDao.getSetCountsByActivity(map);
		page.setTotalCount(counts);
		return topicList;
	}
	
	//查询卖场集下的二级卖场ID
	public String queryActivityIdBySet(Integer set_id) {
		return topicDao.queryActivityIdBySet(set_id);
	}
	
	//批量查询卖场
	public List<Topic> queryActivityList(Map map) {
		return topicDao.queryActivityList(map);
	}
	
	//查询卖场
	public Topic queryActivity(Map map) {
		return topicDao.queryActivity(map);
	}

	//移入卖场集
	public int addActivitySet(Map map) {
		String act_id = map.get("act_id").toString();
		int returnValue = -1;
		if(act_id.indexOf(",") > -1) {
			//批量移入卖场集
			map.put("act_id", act_id.split(","));
			returnValue = topicDao.addActivitySetBatch(map);
		} else {
			int hasExists = topicDao.hasExistsActivitySet(map);
			if(hasExists == 0) {
				returnValue = topicDao.addActivitySet(map);
			}else {
				returnValue = 0;
			}
		}
		return returnValue;
	}

	//移出卖场集
	public int deleteActivitySet(Map map) {
		String act_id = map.get("act_id").toString();
		int returnValue = -1;
		if(act_id.indexOf(",") > -1){
			//批量移出卖场集
			map.put("act_id", act_id.split(","));
			returnValue = topicDao.deleteActivitySetBatch(map);
		} else {
			returnValue = topicDao.deleteActivitySet(map);
		}
		return returnValue;
	}

	//查询卖场集下是否已存在二级卖场
	public int hasExistsActivitySet(Map map) {
		return topicDao.hasExistsActivitySet(map);
	}

	//清空卖场集
	public int emptyActivitySet(Map map) {
		return topicDao.emptyActivitySet(map);
	}
	
	//获取卖场活动Id序列
	public Long getCMSActivitySeq() {
		return topicDao.getCMSActivitySeq();
	}

	//增加卖场所属分类
	public int insertTopicTypeRela(Map map) {
		return topicDao.insertTopicTypeRela(map);
	}

	//删除卖场所属分类
	public int deleteTopicTypeRela(Map map) {
		return topicDao.deleteTopicTypeRela(map);
	}

	//查询要预览的卖场列表
	public List<Topic> getPreviewTopicList(Map map, Page<?> page) {
		List<Topic> topicList = new ArrayList<Topic>();
		try {
			int lineMax = page.getPageNo() * page.getPageSize()+1;
			int lineMin = (page.getPageNo()-1)*page.getPageSize()+1;
			
			map.put("lineMax", lineMax);
			map.put("lineMin", lineMin);

			topicList = topicDao.getPreviewTopicList(map);
			page.setTotalCount(Integer.valueOf(topicDao.getPreviewTopicCount(map)));

			//设置日期信息
			if(topicList != null && topicList.size() > 0) {
				for(int i = 0; i < topicList.size(); i++) {
					Topic topic = topicList.get(i);
					topic.setDateInfo(topic.getDateInfo());
				}
			}
		} catch (Throwable e) {
			LOGGER.error("查询要预览的卖场列表异常！", e);
			throw ExceptionFactory.buildBusinessException(
					ErrConstants.BusinessErrorCode.BIZ_WAP_ERR, "查询要预览的卖场列表异常！");
		}
		return topicList;
	}

	@Override
	public int batchUpdateImportTopics(Map<String, Map<Object, Object>> data,
			Long createUserId) {
			int count = 0;
			int totalCount = data.size();
			long startTime = System.currentTimeMillis();
			try{
				LOGGER.debug("=======================开始导入修改卖场========去重后模板文件数据总数量：{} =================",new Object[]{totalCount});
				// 提取卖场ID
				List<String> topicActivityIdList = new ArrayList<String>();
				Iterator topicActivityId = data.entrySet().iterator();
				while(topicActivityId.hasNext()){
					Map.Entry entry = (Map.Entry) topicActivityId.next();
					String sn = (String) entry.getKey();
					topicActivityIdList.add(sn);
				}
				
				// 开始导入
				Iterator it = data.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry entry = (Map.Entry) it.next();
					String sn = (String) entry.getKey();
					Map<Object, Object> map = (Map<Object, Object>) entry.getValue();
					
					// 卖场ID
					String activityId = (String) map.get(0);
					// 卖场名称
					String name = (String) map.get(1);
					// 标题
					String title = (String) map.get(2);
					// 卖场类型
					Integer contentType = (Integer) map.get(3);
					// 分类-旧
					Long typeOld = (Long) map.get(4);
					// 分类
					List<Long> typeIds = (List<Long>) map.get(5);
					// 排序
					String order = (String) map.get(6);
					// 开始时间
					Date beginTime = (Date) map.get(7);
					// 结束时间
					Object endTimeObj =  map.get(8);
					// 广告图片地址
					String adImgUrl = (String) map.get(9);
					// 卖场顶部焦点图
					String topImg = (String) map.get(10);
					// 卖场形式
					Integer displayStyle = (Integer) map.get(11);
					// 卖场是否显示
					String isDisplay = (String) map.get(12);
					// 卖场URL
					String topicUrl = (String) map.get(13);
					//是否显示倒计时
					Integer isShowCuntDown = (Integer) map.get(14);
					// 结束后是否被搜索到
					Integer isEndBeSearch = (Integer) map.get(15);
					// 标签名称串
					Object  labelsIdsObj =  map.get(16);

					
					if(sn.startsWith("HTML5")){
						//新增HTML5卖场
						Topic topic = new Topic();
						topic.setActivity_id(new BigDecimal(getCMSActivitySeq()));
						topic.setName(name);
						topic.setTitle(title);
						topic.setContentType(contentType);
						topic.setTopicTypeId(typeOld);
						topic.setOrder_seq(ObjectUtil.getLong(order,null));
						topic.setStart_time(beginTime);
						topic.setEnd_time((Date)endTimeObj);
						topic.setMobile_pic(adImgUrl);
						topic.setBannerPic(topImg);
						topic.setDisplayStyle(displayStyle);
						topic.setDisplay(isDisplay);
						topic.setURL(topicUrl);

						int i = insert(topic);

						if(i > 0){
							Map paraMap = new HashMap();
							paraMap.put("activity_id",topic.getActivity_id());
							StringBuffer sb=new StringBuffer();
							for (Long typeId:typeIds) {
								if(!sb.toString().equals("")){
									sb.append(",");
								}
								sb.append(typeId);
							}
							paraMap.put("topicTypeRela", sb.toString());
							this.insertTopicTypeRela(paraMap);	//新增卖场所属分类
						}
					}else{
						
						// 插入数据
						// 查询卖场信息
						Topic topic =topicDao.getTopicByActivityId(BigDecimal.valueOf(Long.valueOf(activityId)));
						topic.setName(name);
						//标题
						if(title!=""&&!title.equals("")){
							topic.setTitle(title);;
						}
						//分类-旧
						topic.setTopicTypeId(typeOld);

						//排序
						Long orderSeq=ObjectUtil.getLong(order,null);

						if(orderSeq!=null && orderSeq !=0){
							topic.setOrder_seq(orderSeq);
						}
						topic.setStart_time(beginTime);
						if(endTimeObj!=null){
							topic.setEnd_time((Date)endTimeObj);
						}
						topic.setMobile_pic(adImgUrl);
						topic.setBannerPic(topImg);
						if(displayStyle!=null){
							topic.setDisplayStyle(displayStyle);
						}
						if(isDisplay!=null){
							topic.setDisplay(isDisplay);
						}
						if(topicUrl != null){
							topic.setURL(topicUrl);
						}
						//是否显示倒计时
						topic.setIsShowCountDown(isShowCuntDown);
//						// 结束后是否被搜索到
						topic.setIsEndCanBeSearch(isEndBeSearch);
//						// 标签名称串
								List<LabelCentre> labelCentres=new ArrayList<LabelCentre>();
								if(labelsIdsObj!=null){
									List<Long> labelIds=(List<Long>)labelsIdsObj;
									for (int i = 0; i < labelIds.size(); i++) {
										LabelCentre lc=new LabelCentre();
										lc.setLabelId(labelIds.get(i));
										labelCentres.add(lc);
									}
								}
								labelService.bindLableToSevice(GlobalConstants.LABEL_TYPE_TOPIC, Long.valueOf(activityId), labelCentres);
						
						int updateCount=topicDao.update(topic);
						//加入到搜索表 中
						searchService.addDataToSearch(GlobalConstants.LABEL_TYPE_TOPIC, Long.valueOf(activityId));

						if(updateCount > 0) {
							//如果更新成功，则更新卖场所属分类信息
							Map paraMap = new HashMap();
							paraMap.put("activity_id",topic.getActivity_id());
							this.deleteTopicTypeRela(paraMap);  //删除旧的卖场所属分类
							StringBuffer sb=new StringBuffer();
							for (Long typeId:typeIds) {
								if(!sb.toString().equals("")){
									sb.append(",");
								}
								sb.append(typeId);
							}
							paraMap.put("topicTypeRela", sb.toString());
							this.insertTopicTypeRela(paraMap);	//新增卖场所属分类
						}
					}

					count++;
				}
			}catch(Exception e){
				LOGGER.error("导入单品发现商品异常", e);
				throw ExceptionFactory.buildBaseException(ErrConstants.BusinessErrorCode.BIZ_FIND_GOODS_ERR, e);
			}
			long endTime = System.currentTimeMillis();
			LOGGER.debug("=============== 导入商品完成 =========== 成功导入条数:" 
			+ count + " 花费时间:" + ((endTime - startTime)/1000) + " 秒 =====================");
			return count;
		}

	@Override
	public List<String> findExistTopicActivityId(Map params) {
		return topicDao.findExistTopicActivityId(params);
	}

	public Integer getSyncToSearchCount(Map params){
		return topicDao.getSyncToSearchCount(params);
	}

	@Override
	public List<Topic> getSyncToSearchList(Map params) {
		Map result =new HashMap();
		Integer page=ObjectUtil.getInteger(params.get("pageNo"), 1);
		Integer pageSize=ObjectUtil.getInteger(params.get("pageSize"), 100);
		int lineMax =page * pageSize+1;
		int lineMin = (page-1)*pageSize+1;
		params.put("pageMax", lineMax);
		params.put("pageMin", lineMin);
		List<Topic> topics=topicDao.getSyncToSearchList(params);
		return topics;
	}


}
