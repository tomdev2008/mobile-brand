package com.xiu.mobile.portal.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.portal.model.MessageCode;

/**
 * <p>
 * ************************************************************** 
 * @Description: 验证码Dao层接口
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-14 上午11:33:37 
 * ***************************************************************
 * </p>
 */
public interface MessageCodeDao {
	
	int insertMessageCode(MessageCode messageCode);
	
	List<MessageCode> getMessageCodeByPhone(String msgPhone);
	
	int deleteMessageCode(String msgPhone);

	int updateMessageCode(MessageCode messageCode);

	//获取验证数量
	int getValidateCount(Map map);
	
	//新增一条验证记录 
	int insertValidateRecord(Map map);
	
	//更新验证记录
	int updateValidateRecord(Map map);
	
	//重置验证记录
	int resetValidateRecord(Map map);
	
	//查询邮箱验证码数量
	int getEmailVerifyCodeCount(Map map);
	
	//查询邮箱验证码
	String getEmailVerifyCode(Map map);
	
	//修改邮箱验证码
	int updateEmailVerifyCode(Map map);
	
	//新增邮箱验证码
	int addEmailVerifyCode(Map map);
	
	Map getMobileValidateInfo(Map map);
	
	/**
	 * 查询可用的短信
	 * @param map
	 * @return
	 */
	List<MessageCode> getUsefulMessageCodeByPhone(Map map);
}
