package com.xiu.mobile.wechat.web.dao;

import java.util.List;

import com.xiu.mobile.wechat.web.model.JumpMenuVo;

public interface JumpMenuDao {

	int insertJumpMenu(JumpMenuVo jumpMenuVo);
	
	JumpMenuVo getJumpMenuByKey(String key);
	
	List<JumpMenuVo> getAllJumpMenus();
	
	int updateJumpMenu(JumpMenuVo jumpMenuVo);
	
}
