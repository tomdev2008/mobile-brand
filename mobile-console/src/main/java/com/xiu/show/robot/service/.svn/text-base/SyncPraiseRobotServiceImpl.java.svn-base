package com.xiu.show.robot.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiu.show.core.dao.UserManagerDao;
import com.xiu.show.core.dao.XiuRobotUserManagerDao;
import com.xiu.show.robot.common.contants.ShowRobotConstants;
import com.xiu.show.robot.common.utils.HttpUtil;
import com.xiu.show.robot.common.utils.ObjectUtil;


/**
 * 
* @Description: TODO(同步刷点赞数据) 
* @author haidong.luo@xiu.com
* @date 2015年7月16日 上午10:49:39 
*
 */
public class SyncPraiseRobotServiceImpl {
	
	
	private static final XLogger LOGGER = XLoggerFactory
			.getXLogger(SyncPraiseRobotServiceImpl.class);
	static String addPraiseUrl="http://show.xiu.com/praise/addPraise.shtml?";
	static Integer addPraisePlanPageSign=45;
	static Integer visitApiPageSign=30;
	@Autowired
	XiuRobotUserManagerDao robotDao;
	@Autowired
	UserManagerDao userManagerDao;
	
	/**
	 * 同步刷点赞数据
	 */
	public void  syncBrushPraiseService(){
		Map param=new HashMap();
		param.put("type", ShowRobotConstants.SHOW_BRUSH_OPEN_STATUS_TYPE_SIGN);
		param.put("subType", ShowRobotConstants.SHOW_BRUSH_OPEN_STATUS_SUB_TYPE_SIGN);
		try{
			//查询是否需要刷数据
			int findOpenSign=robotDao.getRobotBushStatus(param);
			if(findOpenSign==1){
				Date now=new Date();
				//插入点赞计划表
				addRobotPraisePlan(now);
				//刷已经到预期的点赞数据
				addPraiseByShowIdBatch(now);
				//删除已经过期的点赞数据
				deleteRobotPraisePlan(now);
			}
		}catch(Exception e){
			LOGGER.error("刷点赞失败："+e.getMessage());
		}
	}
	public void addRobotPraisePlan(Date now){
		//1.真实用户点赞刷点赞
		//按时间获取真实用户点赞的秀id集合
		Map<String,List<String>> realUserPraiseShowIds=findRealUserPraiseShowIdsByTime(now);
		//生成点赞计划
		addRobotPraisePlan(realUserPraiseShowIds,2,5,
				ShowRobotConstants.SHOW_BRUSH_DATA_AFTER_MINUTE_NUM_MIN,
				ShowRobotConstants.SHOW_BRUSH_DATA_AFTER_MINUTE_NUM_MAX);
		//2.推荐秀刷点赞
		//按时间获取被推荐的秀ID集合
		Map<String,List<String>> findRecommendShowIds=findRecommendShowIdsByTime(now);
		//生成点赞计划
		addRobotPraisePlan(findRecommendShowIds,20,30,
				ShowRobotConstants.SHOW_BRUSH_DATA_AFTER_MINUTE_NUM_MIN,
				ShowRobotConstants.SHOW_BRUSH_DATA_AFTER_RECOMMEND_MINUTE_NUM_MAX);
		//3.精选秀刷点赞
		//按时间获取被推荐的秀ID集合
		Map<String,List<String>> findTopicShowIds=findTopicShowIdsByTime(now);
		//生成点赞计划
		addRobotPraisePlan(findTopicShowIds,20,30,
				ShowRobotConstants.SHOW_BRUSH_DATA_AFTER_MINUTE_NUM_MIN,
				ShowRobotConstants.SHOW_BRUSH_DATA_AFTER_RECOMMEND_MINUTE_NUM_MAX);
		
	}
	/** 
	 * 访问接口进行点赞
	 * @param showid 秀id
	 * @param trueCilckNum 秀id被实际点赞的次数
	 */
	public  void addPraiseByShowIdBatch(Date now) {  
		   String userId="USER_ID";
		   String showIdStr="SHOW_ID";
		   String realUserId="REAL_USER_ID";
		   String nowTimeStr=ObjectUtil.getTimeString(now);
			String fristTimeStr=ObjectUtil.getTimeStrByMin(now, -ShowRobotConstants.SHOW_BRUSH_DATA_MINUTE_NUM);
			String findPraisePlancommonsql=" from sq_xiu_robot_praise_plan "
					+ "where praise_date>=to_date('"+fristTimeStr+"','yyyy-MM-dd hh24:mi:ss')"
							+ " and praise_date< to_date('"+nowTimeStr+"','yyyy-MM-dd hh24:mi:ss') ";
			String findCountSql="select count(1) "+findPraisePlancommonsql;
			String findPraisePlansql="select SHOW_ID,USER_ID,REAL_USER_ID "+findPraisePlancommonsql;
			//查询计划到期该刷点赞的数据总数
	    	Map countParam=new HashMap();
	    	countParam.put("sql",findCountSql);
	    	int count=robotDao.getCountBySql(countParam);
	    	
	    	int pageSize=visitApiPageSign;
	    	if(count>0){
		    	int page =count/visitApiPageSign+1;
		    	//分页进行点赞
		    	for (int i = 0; i <page; i++) {
		    		Map params=new HashMap();
		    		params.put("startRow", i*pageSize+1);
					int end=(i+1)*pageSize;
					if(i==page-1){
						end=count;
					}
			    	params.put("endRow", end);
			    	params.put("sql", findPraisePlansql);
			    	List<Map> fanseMap=robotDao.getDataListBySql(params);
			    	List<String> userIdsList=new ArrayList<String>();
			    	List<String> showIdsList=new ArrayList<String>();
			    	List<String> realUserIdsList=new ArrayList<String>();
			    	for (Map user:fanseMap) {
			    		userIdsList.add(user.get(userId).toString());
			    		showIdsList.add(user.get(showIdStr).toString());
//			    		realUserIdsList.add(user.get(realUserId).toString());
					}
			    	//进行点赞
		    		String url=addPraiseUrl+"showId=";
		    		HttpUtil.executeGetByThread(url,showIdsList,userIdsList);
		    	}
	    	}
	}
	
	/**
	 * 删除过期的点赞计划（每天删除一次）
	 */
	public void deleteRobotPraisePlan(Date now){
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(now);//把当前时间赋给日历
		int minute= calendar.get(Calendar.MINUTE);
		int hour= calendar.get(Calendar.HOUR);
		if(minute==30&&hour==1){//1点30分删除
			Map param=new HashMap();
			param.put("praiseDate", ObjectUtil.getTimeByMin(now,-(ShowRobotConstants.SHOW_BRUSH_DATA_MINUTE_NUM+60*24)));
			robotDao.deleteBatchRobotPraisePlan(param);
		}
	}
	

	
	/**
	 * 按时间查询真实用户点赞的秀id集合
	 * String 秀id
	 * Integer出现次数
	 */
	public Map<String,List<String>> findRealUserPraiseShowIdsByTime(Date now){
		//select user_id  from sq_xiu_user where user_id not in( select  uv.user_id from sq_user_praise up,sq_xiu_user uv where up.user_id=uv.user_id and up.object_id=63736)  and user_id not in(select user_id from sq_xiu_robot_praise_plan where show_id =63736)
		Map<String,Integer> showIdNumMap=new HashMap<String,Integer>(); //记录showId出现次数
		Map<String,List<String>> showId2UserIdsMap=new HashMap<String,List<String>>(); //记录showId出现次数
				String fristTimeStr=ObjectUtil.getTimeStrByMin(now,-ShowRobotConstants.SHOW_BRUSH_DATA_MINUTE_NUM);//获取定时分钟前的时间
				String findShowIdsCommonsql=" from sq_user_praise "
						+ "where create_date>= to_date('"+fristTimeStr+"','yyyy-MM-dd hh24:mi:ss') "
						+ "and USER_ID not in (select USER_ID from  SQ_XIU_USER where type=1 and status=1)";
				String findShowIdsCountsql=" select count(1) "+findShowIdsCommonsql;
				String findShowIdsDatasql=" select OBJECT_ID,CREATE_DATE,USER_ID "+findShowIdsCommonsql;
				Map countMap=new HashMap();
				countMap.put("sql", findShowIdsCountsql);
				Integer praiseNum=robotDao.getCountBySql(countMap);
				if(praiseNum>0){
					int pageSize=addPraisePlanPageSign;
					int page=praiseNum/pageSize+1;
					Map params=new HashMap();
					for (int i = 0; i < page; i++) {
						params.put("sql", findShowIdsDatasql);
						params.put("startRow", i*pageSize+1);
						int end=(i+1)*pageSize;
						if(i==page-1){
							end=praiseNum;
						}
				    	params.put("endRow", end);
						List<Map> praises=robotDao.getDataListBySql(params);
//						System.out.println("startRow"+params.get("startRow")+"end"+params.get("endRow")+"  praises"+praises.size());
						if(praises!=null&&praises.size()>0){
							for (int j = 0; j < praises.size(); j++) {//循环记录showId出现次数
								String showId=praises.get(j).get("OBJECT_ID").toString();
								String userId=praises.get(j).get("USER_ID").toString();
								if(showIdNumMap.get(showId)==null){
									showIdNumMap.put(showId, 1);
									List userList=new ArrayList();
									userList.add(userId);
									showId2UserIdsMap.put(showId, userList);
								}else{
									showIdNumMap.put(showId, showIdNumMap.get(showId)+1);
									List userList=showId2UserIdsMap.get(showId);
									userList.add(userId);
									showId2UserIdsMap.put(showId,userList);
								}
							}
						}
					}
				}
		return showId2UserIdsMap;
	}
	
	/**
	 * 按时间查询被推荐的秀id集合
	 *、String 秀id
	 * Integer出现次数
	 */
	public Map<String,List<String>> findRecommendShowIdsByTime(Date now){
		Map<String,Integer> showIdNumMap=new HashMap<String,Integer>(); //记录showId出现次数
		Map<String,List<String>> showId2UserIdsMap=new HashMap<String,List<String>>(); //记录showId出现次数
						String fristTimeStr=ObjectUtil.getTimeStrByMin(now,-ShowRobotConstants.SHOW_BRUSH_DATA_MINUTE_NUM);//获取定时分钟前的时间
						String findShowIdsCommonsql=" from sq_show_recommend "
								+ "where recommend_flag=1 and delete_flag=0 and create_date>= to_date('"+fristTimeStr+"','yyyy-MM-dd hh24:mi:ss') ";
						String findShowIdsCountsql=" select count(1) "+findShowIdsCommonsql;
						String findShowIdsDatasql=" select SHOW_ID,CREATE_DATE "+findShowIdsCommonsql;
						Map countMap=new HashMap();
						countMap.put("sql", findShowIdsCountsql);
						Integer praiseNum=robotDao.getCountBySql(countMap);
						if(praiseNum>0){
							int pageSize=addPraisePlanPageSign;
							int page=praiseNum/pageSize+1;
							Map params=new HashMap();
							for (int i = 0; i < page; i++) {
								params.put("sql", findShowIdsDatasql);
								params.put("startRow", i*pageSize+1);
								int end=(i+1)*pageSize;
								if(i==page-1){
									end=praiseNum;
								}
						    	params.put("endRow", end);
								List<Map> praises=robotDao.getDataListBySql(params);
//								System.out.println("startRow"+params.get("startRow")+"end"+params.get("endRow")+"  praises"+praises.size());
								if(praises!=null&&praises.size()>0){
									for (int j = 0; j < praises.size(); j++) {//循环记录showId出现次数
										String showId=praises.get(j).get("SHOW_ID").toString();
//										String userId=praises.get(j).get("USER_ID").toString();
										if(showIdNumMap.get(showId)==null){
											showIdNumMap.put(showId, 1);
											List userList=new ArrayList();
											userList.add("");
											showId2UserIdsMap.put(showId, userList);
										}else{
											showIdNumMap.put(showId, showIdNumMap.get(showId)+1);
											List userList=showId2UserIdsMap.get(showId);
											userList.add("");
											showId2UserIdsMap.put(showId,userList);
										}
									}
								}
							}
						}
				return showId2UserIdsMap;
	}
	
	/**
	 * 按时间查询被精选的秀id集合
	 *、String 秀id
	 * Integer出现次数
	 */
	public Map<String,List<String>> findTopicShowIdsByTime(Date now){
		Map<String,Integer> showIdNumMap=new HashMap<String,Integer>(); //记录showId出现次数
		Map<String,List<String>> showId2UserIdsMap=new HashMap<String,List<String>>(); //记录showId出现次数
		String fristTimeStr=ObjectUtil.getTimeStrByMin(now,-ShowRobotConstants.SHOW_BRUSH_DATA_MINUTE_NUM);//获取定时分钟前的时间
		String findShowIdsCommonsql=" from sq_show_topic "
				+ "where selection_flag=1 and delete_flag=0 and update_date>= to_date('"+fristTimeStr+"','yyyy-MM-dd hh24:mi:ss') ";
		String findShowIdsCountsql=" select count(1) "+findShowIdsCommonsql;
		String findShowIdsDatasql=" select SHOW_ID,CREATE_DATE "+findShowIdsCommonsql;
		Map countMap=new HashMap();
		countMap.put("sql", findShowIdsCountsql);
		Integer praiseNum=robotDao.getCountBySql(countMap);
		if(praiseNum>0){
			int pageSize=addPraisePlanPageSign;
			int page=praiseNum/pageSize+1;
			Map params=new HashMap();
			for (int i = 0; i < page; i++) {
				params.put("sql", findShowIdsDatasql);
				params.put("startRow", i*pageSize+1);
				int end=(i+1)*pageSize;
				if(i==page-1){
					end=praiseNum;
				}
				params.put("endRow", end);
				List<Map> praises=robotDao.getDataListBySql(params);
//								System.out.println("startRow"+params.get("startRow")+"end"+params.get("endRow")+"  praises"+praises.size());
				if(praises!=null&&praises.size()>0){
					for (int j = 0; j < praises.size(); j++) {//循环记录showId出现次数
						String showId=praises.get(j).get("SHOW_ID").toString();
//						String userId=praises.get(j).get("USER_ID").toString();
						if(showIdNumMap.get(showId)==null){
							showIdNumMap.put(showId, 1);
							List userList=new ArrayList();
							userList.add("");
							showId2UserIdsMap.put(showId, userList);
						}else{
							showIdNumMap.put(showId, showIdNumMap.get(showId)+1);
							List userList=showId2UserIdsMap.get(showId);
							userList.add("");
							showId2UserIdsMap.put(showId,userList);
						}
					}
				}
				
			}
		}
		return showId2UserIdsMap;
	}
	

	
	/**
	 * 
	 * @param showIdNumMap 需要点赞的秀集合
	 * @param minPraiseNum  最少点赞数
	 * @param maxPraiseNum  最多点赞数
	 * @param minAfterMinute  最少滞后点赞分钟数
	 * @param maxAfterMinute  最多滞后点赞分钟数
	 */
	public void addRobotPraisePlan(Map<String,List<String>> showIdNumMap,Integer minPraiseNum,Integer maxPraiseNum,
			Integer minAfterMinute,Integer maxAfterMinute){
		
					StringBuffer showIds=new StringBuffer();
					StringBuffer userIds=new StringBuffer();
					StringBuffer realUserIdSb=new StringBuffer();
					StringBuffer praiseTimes=new StringBuffer();
					boolean isfirst=true;
					int addNum=0;
					//循环对showid进行匹配机器人以及插入机器人点赞计划数据
					for(String showid : showIdNumMap.keySet())    
					{     
						Map params=new HashMap();
						List<String> realUserIds=showIdNumMap.get(showid);
						Integer trueCilckNum=realUserIds.size();//真实点赞的次数
						  String userId="USER_ID";
							String commensql=" from sq_xiu_user where type=1 and status=1 and user_id not in( select  uv.user_id from sq_user_praise up,sq_xiu_user uv"
					    			+ " where   up.user_id=uv.user_id and up.object_id="+showid+" and uv.type=1 and uv.status=1 ) "
					    					+ " and user_id not in(select user_id from sq_xiu_robot_praise_plan where show_id ="+showid+")"
					    							+ " order by user_id ";
							//查询用户总数
							String fingCountSql="select count(1) "+commensql;
						 	//分页查询用户
					    	String fingUserSql="select user_id "+commensql;
					    	List<String> keys=new ArrayList<String>();
					    	keys.add(userId);
					    	Map countParam=new HashMap();
					    	countParam.put("sql",fingCountSql);
					    	int count=robotDao.getCountBySql(countParam);
					    	int num=ObjectUtil.getRandom((maxPraiseNum-minPraiseNum)*trueCilckNum)+minPraiseNum*trueCilckNum;
					    	int maxfirst=1;
					    	if(count-num>0){
					    		maxfirst=count-num;
					    	}
					    	int userfrist=ObjectUtil.getRandom(maxfirst);//分页查询开始位置
					    	int userend=userfrist+num;//分页查询结束位置
					    	params=new HashMap();
					    	params.put("sql", fingUserSql);
					    	params.put("startRow", userfrist);
					    	params.put("endRow", userend);
					    	List<Map> fanseMap=robotDao.getDataListBySql(params);
					    	int userNumber=0;
					    	for (Map user:fanseMap) {
					    		if(!isfirst){
					    			showIds.append(",");
					    			userIds.append(",");
					    			praiseTimes.append(",");
					    			realUserIdSb.append(",");
					    		}
					    		showIds.append(showid);
					    		userIds.append(user.get(userId).toString());
					    		Integer afterMinNum=ObjectUtil.getRandom(maxAfterMinute)+minAfterMinute;
					    		String praisePlanTimeStr=ObjectUtil.getAfterTimeByMin(afterMinNum);
					    		praiseTimes.append(praisePlanTimeStr);
					    		realUserIdSb.append(realUserIds.get(userNumber));
					    		userNumber++;
					    		if(userNumber>realUserIds.size()-1){
					    			userNumber=0;
					    		}
					    		isfirst=false;
					    		addNum++;
							}
					} 
					if(!showIds.toString().equals("")){
						Map addRobotPraiseMap=new HashMap();
						addRobotPraiseMap.put("showIds", showIds.toString());
						addRobotPraiseMap.put("userIds", userIds.toString());
						addRobotPraiseMap.put("praiseDates", praiseTimes.toString());
						addRobotPraiseMap.put("realUserIds", realUserIdSb.toString());
						addRobotPraiseMap.put("num", addNum);
						LOGGER.info("--加入点赞计划"+addNum+"个--");
						robotDao.addBatchRobotPraisePlan(addRobotPraiseMap);
					}
	}
	
	
}
