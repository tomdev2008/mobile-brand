package com.xiu.show.web.service;

import java.util.List;
import java.util.Map;

import com.xiu.show.core.model.Page;
import com.xiu.show.core.model.ShowStatisticsModel;

/**
 * 秀统计Service
 * @author coco.long
 * @time	2015-06-30
 */
public interface IUserRecommendManagerService {
	
	/**
	 * 24小时内用户被赞数统计定时任务
	 * @param map
	 */
	public void callUserPraisedStatistics();
	
	/**
	 * 根据关注数据进行用户推荐
	 */
	public void callUserRecommendConcernMore();
	
	public void callUserRecommendConcern();
	
	/**
	 * 24小时内被点赞最多的用户推荐
	 */
	public void callUserRecommendLastedPraised();
	
	/**
	 * 被点赞次数最多的用户推荐
	 */
	public void callUserRecommendMostPraised();
	
	/**
	 * 根据关注品牌数据进行用户推荐
	 */
	public void callUserRecommendConcernBrand();
	
	/**
	 * 根据根据好友数据进行用户推荐
	 */
	public void callUserRecommendFriend();
	
	/**
	 * 定时发布秀审核处理
	 */
	public void callCheckShowFuture();
}
