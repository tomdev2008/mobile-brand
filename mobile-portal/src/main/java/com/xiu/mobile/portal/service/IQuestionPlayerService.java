package com.xiu.mobile.portal.service;

import java.util.List;

import com.xiu.mobile.portal.model.QuestionPlayer;

/***
 * game玩家信息统计接口
 * @author hejianxiong
 * @date 2014-12-15 14:20:15
 */
public interface IQuestionPlayerService {
	
	/***
	 * 获取游戏玩家列表信息
	 * @return
	 */
	public List<QuestionPlayer> getList();
	
	/***
	 * 通过Id获取游戏玩家信息
	 * @return
	 */
	public QuestionPlayer getById(Long id);
	
	/***
	 * 更新玩家信息统计数据
	 * @param questionPlayer
	 */
	public void updateQuestionPlayer(QuestionPlayer questionPlayer);
}
