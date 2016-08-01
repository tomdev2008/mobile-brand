/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-14 上午11:30:13 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.portal.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xiu.common.lang.StringUtil;
import com.xiu.mobile.portal.common.constants.LoginRegConstant;
import com.xiu.mobile.portal.common.constants.MobileCommonConstant;
import com.xiu.mobile.portal.common.util.Tools;
import com.xiu.mobile.portal.dao.MessageCodeDao;
import com.xiu.mobile.portal.facade.utils.DateUtil;
import com.xiu.mobile.portal.model.MessageCode;
import com.xiu.mobile.portal.service.IMessageCodeService;

/** 
 * <p>
 * ************************************************************** 
 * @Description: 验证码业务逻辑实现类 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-14 上午11:30:13 
 * ***************************************************************
 * </p>
 */
@Service 
public class MessageCodeServiceImpl implements IMessageCodeService {
	private Logger logger = Logger.getLogger(MessageCodeServiceImpl.class);
	
	@Autowired
    private MessageCodeDao messageCodeDao;

	@Override
	public int saveMessageCode(MessageCode messageCode) {
		int result = 0;
		try {
			// 插入数据库之前先查询有没有该记录，若有则删除掉
			List<MessageCode> messageCodes = messageCodeDao.getMessageCodeByPhone(
					messageCode.getMsgPhone());
			
			if (messageCodes != null && messageCodes.size() > 0) {
				messageCodeDao.deleteMessageCode(messageCode.getMsgPhone());
			}
			// 插入到数据库中
			messageCodeDao.insertMessageCode(messageCode);
			return result;
		} catch (Exception e) {
			result = -1;
			logger.error("添加验证码失败！", e);
		}
		return result;
	}

	@Override
	public MessageCode findMessageCodeByPhone(String msgPhone) {
		MessageCode messageCode = null;
		
		try {
			List<MessageCode> mesList = messageCodeDao.getMessageCodeByPhone(msgPhone);

			if (mesList != null && mesList.size() > 0) {
				messageCode = mesList.get(0);
			}
		} catch (Exception e) {
			logger.error("查询验证码失败！", e);
		}
		
		return messageCode;
	}

	@Override
	public int removeMessageCodeByPhone(String msgPhone) {
		int result = 0;
		
		try{
			messageCodeDao.deleteMessageCode(msgPhone);
			return result;
		} catch(Exception e) {
			result = -1;
			logger.error("验证码删除失败！",e);
		}
		
		return result;
	}

	@Override
	public int updateMessageCode(MessageCode messageCode) {
		int result = 0;
		
		try {
			messageCodeDao.updateMessageCode(messageCode);
			return result;
		} catch(Exception e) {
			result = -1;
			logger.error("验证码更新失败！",e);
		}
		
		return result;
	}

	/**
	 * 获取短信验证码限制状态
	 * a.1小时内同一台设备同一种业务只能发送五次验证码
	 * b.一个手机号所有业务只能发送10次验证码
	 */
	public boolean getSMSLimitStatus(Map map) {
		boolean flag = false;
		//1.查询是否已有验证记录
		int result = messageCodeDao.getValidateCount(map);
		if(result > 0) {
			//2.如果存在，判断当前时间是否在限制时间内
			map.put("time", "1");
			result = messageCodeDao.getValidateCount(map);
			if(result > 0) {
				//3.如果在限制时间内，判断已验证次数是否超过限制次数
				map.put("times", "1");
				result = messageCodeDao.getValidateCount(map);
				if(result > 0) {
					//4.超过限制，返回true
					flag = true;
				} else {
					//验证手机号
					flag = getMobileLimitStatus(map);
					if(flag) {
						return flag;
					}
					//没有则更新限制记录次数+1
					messageCodeDao.updateValidateRecord(map);
				}
			} else {
				//验证手机号
				flag = getMobileLimitStatus(map);
				if(flag) {
					return flag;
				}
				//没有则重置限制记录
				messageCodeDao.resetValidateRecord(map);
			}
		} else {
			//验证手机号
			flag = getMobileLimitStatus(map);
			if(flag) {
				return flag;
			}
			//没有则新增一条
			messageCodeDao.insertValidateRecord(map);
		}
		
		return flag;
	}
	
	/**
	 * 验证手机号，一个小时最多发送10次验证码
	 * @param map
	 * @return
	 */
	private boolean getMobileLimitStatus(Map map) {
		boolean flag = false;
		
		String mobile = (String) map.get("mobile");
		if(StringUtil.isNotBlank(mobile)) {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("type", MobileCommonConstant.SMS_VALIDATE_TIMES_MOBILE);
			paraMap.put("deviceId", MobileCommonConstant.MOBILE_LIMIT_CONSTANT + mobile);
			//查询手机号记录是否存在
			Map resultMap = messageCodeDao.getMobileValidateInfo(paraMap);
			if(resultMap != null) {
				String limitTimes = resultMap.get("LIMIT_TIMES").toString();	//限制次数
				String times = resultMap.get("TIMES").toString();	//当前次数
				Date startTime = (Date) resultMap.get("START_TIME"); //限制开始时间
				Date stopTime = (Date) resultMap.get("STOP_TIME"); //限制结束时间
				if(startTime != null && stopTime != null) {
					//是否在限制时间内
					Date now = new Date();
					if(now.after(startTime) && now.before(stopTime)) {
						//限制时间内则比较限制次数
						if(StringUtil.isNotBlank(limitTimes) && StringUtil.isNotBlank(times)) {
							//比较限制次数
							Integer counts = Integer.parseInt(times);
							Integer limitCounts = Integer.parseInt(limitTimes);
							if(counts.intValue() < limitCounts.intValue()) {
								//没有超过限制，则更新限制信息
								messageCodeDao.updateValidateRecord(paraMap);
							} else {
								//超过限制
								return true;
							}
						}
					} else {
						//重置限制信息
						messageCodeDao.resetValidateRecord(paraMap);
					}
				} else {
					//重置限制信息
					messageCodeDao.resetValidateRecord(paraMap);
				}
			} else {
				//如果手机号验证信息不存在，则新增一条
				paraMap.put("limitTimes", MobileCommonConstant.MOBILE_LIMIT_TIMES);
				messageCodeDao.insertValidateRecord(paraMap);
			}
		}
		
		return flag;
	}

	//检测邮箱验证码是否存在
	public boolean isEmailVerifyCodeExists(Map map) {
		int counts = messageCodeDao.getEmailVerifyCodeCount(map);
		if(counts > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	//获取邮箱验证码，如果邮箱验证码在有效期内，则返回，没有则生成
	public String getEmailVerifyCode(Map map) {
		String result = "";
		//查询邮箱是否存在记录
		boolean existsStatus = isEmailVerifyCodeExists(map);
		
		if(existsStatus) {
			//存在
			result = messageCodeDao.getEmailVerifyCode(map);
			if(StringUtil.isBlank(result)) {
				//如果验证码存在，但是失效
				result = Tools.getRandomPassword(); //生成验证码 6位数字
				map.put("code", result);
				updateEmailVerifyCode(map);
			}
		} else {
			//新增邮箱验证码
			result = Tools.getRandomPassword(); //生成验证码 6位数字
			map.put("code", result);
			map.put("limitTimes", MobileCommonConstant.SMS_LIMIT_TIMES);
			addEmailVerifyCode(map);
		}
		
		return result;
	}

	//修改邮箱验证码
	public boolean updateEmailVerifyCode(Map map) {
		int counts = messageCodeDao.updateEmailVerifyCode(map);
		if(counts > 0) {
			return true;
		} else {
			return false;
		}
	}

	//新增邮箱验证码
	public boolean addEmailVerifyCode(Map map) {
		int counts = messageCodeDao.addEmailVerifyCode(map);
		if(counts > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * 获取短信验证码（如果限定时间内有未过时的则获取旧的，无则生成）
	 * @param msgPhone
	 * @return
	 */
	public String getMessageCodeByPhone(String msgPhone) {
		MessageCode oldmessageCode = this.findMessageCodeByPhone(msgPhone);
		//默认生成新的短信验证码
		String msgCode=String.valueOf((int) (Math.random() * 9000 + 1000));
		  //如果存在合法的验证码则不重新生成新的验证码
		   if (oldmessageCode != null) {
			String oldSmsCode = oldmessageCode.getMsgCode();
			String sendCodeTime = oldmessageCode.getTimestamp();
			String timeFlag = oldmessageCode.getTimeFlag();
			String contentFlag = oldmessageCode.getContentFlag();
			// 不同的手机验证码不同
			if (!StringUtils.isEmpty(oldSmsCode) && !StringUtils.isEmpty(sendCodeTime) && timeFlag.equals("N") && contentFlag.equals("N")) {
				long dif = DateUtil.diffDateTime(new Date(), new Date(Long.valueOf(sendCodeTime)));
				// 时间差大于规定重复发送时间则依旧获取老的验证码
				if (dif < LoginRegConstant.SMS_REPEAT_DIS_SECOND) {
					msgCode=oldSmsCode;
				}
			}
		   }
		return msgCode;
	}

	
}
