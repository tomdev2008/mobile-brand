package com.xiu.mobile.portal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.portal.dao.QuestionPlayerDao;
import com.xiu.mobile.portal.model.QuestionPlayer;
import com.xiu.mobile.portal.service.IQuestionPlayerService;

/***
 * 游戏玩家数据统计接口(业务实现类)
 * @author hejianxiong
 * @date 2014-12-15 14:25:45
 */
@Service("questionPlayer")
public class QuestionPlayerServiceImpl implements IQuestionPlayerService{

	@Autowired
	private QuestionPlayerDao questionPlayerDao;
	
	@Override
	public List<QuestionPlayer> getList() {
		return questionPlayerDao.getList();
	}

	@Override
	public void updateQuestionPlayer(QuestionPlayer questionPlayer) {
		questionPlayerDao.updateQuestionPlayer(questionPlayer);
	}

	@Override
	public QuestionPlayer getById(Long id) {
		return questionPlayerDao.getById(id);
	}

}
