package com.xiu.show.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.show.core.common.constants.ShowConstant;
import com.xiu.show.core.dao.FindFriendManagerDao;
import com.xiu.show.core.dao.MessageManagerDao;
import com.xiu.show.core.dao.ShowStatisticsManagerDao;
import com.xiu.show.core.model.UserFriendModel;
import com.xiu.show.core.service.IShowManagerService;
import com.xiu.show.ei.EIUUCManager;
import com.xiu.show.web.service.IUserRecommendManagerService;
import com.xiu.uuc.facade.dto.UserDetailDTO;

/**
 * 秀统计Service
 * @author coco.long
 * @time	2015-06-30
 */
@Transactional
@Service("userRecommendManagerService")
public class IUserRecommendManagerServiceImpl implements IUserRecommendManagerService {

	private static final XLogger LOGGER = XLoggerFactory.getXLogger(IUserRecommendManagerServiceImpl.class);

	@Autowired
	private ShowStatisticsManagerDao showStatisticsManagerDao;
	@Autowired
	private MessageManagerDao messageManagerDao;
	@Autowired
	private FindFriendManagerDao findFriendDao;
	@Autowired
	private EIUUCManager eiUUCManager; 
	
	@Autowired
    private IShowManagerService showManagerService;
	
	/**
	 * 24小时内用户被赞数统计定时任务
	 */
	public void callUserPraisedStatistics() {
		showStatisticsManagerDao.callUserPraisedStatistics();
	}

	/**
	 * 根据关注数据进行用户推荐
	 */
	public void callUserRecommendConcernMore() {
		showStatisticsManagerDao.callUserRecommendConcernMore();
	}
	
	public void callUserRecommendConcern() {
		showStatisticsManagerDao.callUserRecommendConcern();
	}

	/**
	 * 24小时内被点赞最多的用户推荐
	 */
	public void callUserRecommendLastedPraised() {
		showStatisticsManagerDao.callUserRecommendLastedPraised();
	}

	/**
	 * 被点赞次数最多的用户推荐
	 */
	public void callUserRecommendMostPraised() {
		showStatisticsManagerDao.callUserRecommendMostPraised();
	}

	/**
	 * 根据关注品牌数据进行用户推荐
	 */
	public void callUserRecommendConcernBrand() {
		showStatisticsManagerDao.callUserRecommendConcernBrand();
	}

	/**
	 *  根据根据好友数据进行用户推荐
	 */
	public void callUserRecommendFriend() {
		//1.先同步以及注册的好友信息
		        Integer pageSize=500;
		       // 1.1分页获取未注册秀客的好友信息
		        Integer total =findFriendDao.findNotXiuUserFriendTotal(null);
		        Integer pageNum=total/pageSize+1;
		        for (int i = 0; i < pageNum; i++) {
					Map notXiuUserFriendParams=new HashMap();
					notXiuUserFriendParams.put("startRow", i*pageSize);
					notXiuUserFriendParams.put("endRow", (i+1)*pageSize);
					List<UserFriendModel> notXiuUserFrineds =findFriendDao.getNotXiuUserFriendList(notXiuUserFriendParams);
					List<String> mobileFriend=new ArrayList<String>();
					List<String> weiboFriend=new ArrayList<String>();
					Boolean mobileIsUpload=false;//待完善！！
					Boolean weiboIsUpload=false;
					Map<String,UserFriendModel> userFriendMap=new HashMap<String,UserFriendModel>();
					for (UserFriendModel friend:notXiuUserFrineds) {
						if(friend.getType()==1){
							mobileFriend.add(friend.getValue());
							mobileIsUpload=true;
						}else if(friend.getType()==2){
							weiboFriend.add(friend.getValue());
							weiboIsUpload=true;
						}
						userFriendMap.put(friend.getValue(), friend);
					}
					//手机情况
					if(mobileFriend.size()>0){
						//查询uuc好友是否已经注册走秀
						Map findFriendIsRegister=new HashMap();
						findFriendIsRegister.put("type", "1");
						findFriendIsRegister.put("values", mobileFriend);
						Map<String,UserDetailDTO> friendInfo=null;
						try{
							friendInfo=eiUUCManager.getFriendInfoDTOList(findFriendIsRegister);
							 Iterator it = friendInfo.entrySet().iterator();
							   while (it.hasNext()) {
								    Map.Entry entry = (Map.Entry) it.next();
								    String key = (String)entry.getKey();
								    UserDetailDTO value =(UserDetailDTO) entry.getValue();
								    UserFriendModel friend= userFriendMap.get(key);
								    UserFriendModel updateFriend= new UserFriendModel();
								    updateFriend.setFriendId(friend.getFriendId());
								    updateFriend.setShowerUserId(value.getUserId());
								    updateFriend.setStatus(1);
								    findFriendDao.update(updateFriend);
							   }
							   
						}catch(Exception e){
							LOGGER.error(e.getMessage());
	//						isSuccess=false;
						}
					}
					//微博情况
					if(weiboFriend.size()>0){
						//查询uuc好友是否已经注册走秀
						Map findFriendIsRegister=new HashMap();
						findFriendIsRegister.put("type", "2");
						findFriendIsRegister.put("values", weiboFriend);
						Map<String,UserDetailDTO> friendInfo=null;
						try{
							friendInfo=eiUUCManager.getFriendInfoDTOList(findFriendIsRegister);
							 Iterator it = friendInfo.entrySet().iterator();
							   while (it.hasNext()) {
								    Map.Entry entry = (Map.Entry) it.next();
								    String key = (String)entry.getKey();
								    UserDetailDTO value =(UserDetailDTO) entry.getValue();
								    UserFriendModel friend= userFriendMap.get(key);
								    UserFriendModel updateFriend= new UserFriendModel();
								    updateFriend.setUserId(friend.getUserId());
								    updateFriend.setFriendId(friend.getFriendId());
								    updateFriend.setShowerUserId(value.getUserId());
								    updateFriend.setStatus(1);
								    findFriendDao.update(updateFriend);
							   }
							   
						}catch(Exception e){
							LOGGER.error(e.getMessage());
	//						isSuccess=false;
						}	
					}
			      showStatisticsManagerDao.callUserRecommendFriend();
		 }
	}

	/**
	 * 定时发布秀审核处理
	 */
	public void callCheckShowFuture() {
		//查询需要处理的秀
		List showList = showStatisticsManagerDao.getShowFutureCheck();
		if(showList != null && showList.size() > 0) {
			int size = showList.size();
			for(int i = 0; i < size; i++) {
				Map showMap = (Map) showList.get(i);
				String showId = showMap.get("SHOW_ID").toString();
				String userId = showMap.get("USER_ID").toString();
				String userName = showMap.get("PET_NAME").toString();
				
				Map<String, Object> paraMap = new HashMap<String, Object>();
				paraMap.put("showIds", showId);
				paraMap.put("status", "1");
				paraMap.put(ShowConstant.USER_ID, userId);
				paraMap.put(ShowConstant.OPERATE_USER_NAME, userName);
				paraMap.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_COMMON);
				
				boolean result = showManagerService.updateShowCheckStatusBatch(paraMap);
				if(result) {
					//更新定时秀状态
					showStatisticsManagerDao.updateShowFuture(showId);
				}
			}
		}
	}
	
}
