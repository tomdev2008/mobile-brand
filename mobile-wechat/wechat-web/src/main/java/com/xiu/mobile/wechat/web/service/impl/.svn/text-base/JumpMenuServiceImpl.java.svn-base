package com.xiu.mobile.wechat.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xiu.mobile.wechat.web.dao.JumpMenuDao;
import com.xiu.mobile.wechat.web.model.JumpMenuVo;
import com.xiu.mobile.wechat.web.service.IJumpMenuService;

@Service
public class JumpMenuServiceImpl implements IJumpMenuService{
	private static final Logger logger = LoggerFactory.getLogger(JumpMenuServiceImpl.class);
	@Resource
	JumpMenuDao jumpMenuDao;
	
	@Override
	public int addJumpMenu(JumpMenuVo jumpMenuVo) {
		int result=0;
		try{
			jumpMenuDao.insertJumpMenu(jumpMenuVo);
		}catch(Exception e){
			result=-1;
			logger.error("添加微信跳转菜单失败:",e);
		}
		return result;
	}

	@Override
	public JumpMenuVo getJumpMenuByKey(String key) {
		
		return null;
	}

	@Override
	public int updateJumpMenu(JumpMenuVo jumpMenuVo) {
	
		return 0;
	}

	@Override
	public List<JumpMenuVo> getAllJumpMenus() {
		
		return jumpMenuDao.getAllJumpMenus()!=null&&jumpMenuDao.getAllJumpMenus().size()>0?jumpMenuDao.getAllJumpMenus():null;
	}

}
