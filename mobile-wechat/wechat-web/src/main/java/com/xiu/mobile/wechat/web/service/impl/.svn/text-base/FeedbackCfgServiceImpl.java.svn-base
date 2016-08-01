package com.xiu.mobile.wechat.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.xiu.common.command.result.Result;
import com.xiu.feedback.bean.Feedback;
import com.xiu.feedback.hessianService.IFeedbackHessianService;
import com.xiu.mobile.wechat.core.constants.Page;
import com.xiu.mobile.wechat.web.dao.FeedbackCfgDao;
import com.xiu.mobile.wechat.web.model.FeedbackCfgVo;
import com.xiu.mobile.wechat.web.service.IFeedbackCfgService;
import com.xiu.tc.common.orders.domain.PayOrderDO;
import com.xiu.tc.orders.dointerface.BizOrderQueryServive;
import com.xiu.uuc.facade.UserManageFacade;
import com.xiu.uuc.facade.dto.UserBaseDTO;
import com.xiu.uuc.facade.util.TypeEnum;

/**
 * 
 * 配置 服务接口实现类
 * 
 * @author wangzhenjiang
 * 
 */
@Transactional
@Service("feedbackCfgService")
public class FeedbackCfgServiceImpl implements IFeedbackCfgService {
	private static final Logger logger = LoggerFactory.getLogger(FeedbackCfgServiceImpl.class);

	@Resource
	private FeedbackCfgDao feedbackCfgDao;

	@Resource(name = "feedbackHessianService")
	private IFeedbackHessianService feedbackHessianService;

	@Resource(name = "bizOrderQueryServive")
	private BizOrderQueryServive bizOrderQueryServive;

	@Resource(name = "userManageFacade")
	private UserManageFacade userManageFacade;

	@Override
	public List<FeedbackCfgVo> getFeedbackCfgList(Map<String, Object> params, Page<?> page) {
		Assert.notNull(params, "Params must be not null!");
		List<FeedbackCfgVo> lstFeedbackCfg = new ArrayList<FeedbackCfgVo>();
		try {
			int pageMax = page.getPageNo() * page.getPageSize();
			int pageMin = 1;
			if (page.getPageNo() != 1) {
				pageMin = (pageMax - (page.getPageNo() - 1) * page.getPageSize()) + 1;
			}
			params.put("pageMin", pageMin - 1);
			params.put("pageSize", page.getPageSize());
			params.put("pageMax", pageMax);

			lstFeedbackCfg = feedbackCfgDao.getFeedbackCfgListForPage(params);
			page.setTotalCount(lstFeedbackCfg.size());
		} catch (Throwable e) {
			logger.error("查询配置列表失败：", e);
		}
		return lstFeedbackCfg;
	}

	@Override
	public List<FeedbackCfgVo> getFeedbackCfgList(Map<String, Object> params) {
		Assert.notNull(params, "Params must be not null!");
		try {
			return feedbackCfgDao.getFeedbackCfgList(params);
		} catch (Exception e) {
			logger.error("查询配置列表失败：", e);
		}
		return null;
	}

	@Override
	public int saveFeedbackCfg(FeedbackCfgVo cfg) {
		try {
			return feedbackCfgDao.saveFeedbackCfg(cfg);
		} catch (Throwable e) {
			logger.error("保存配置异常:", e);
		}
		return 0;
	}

	@Override
	public int deleteFeedbackCfg(Long id) {
		try {
			return feedbackCfgDao.deleteFeedbackCfg(id);
		} catch (Throwable e) {
			logger.error("删除配置异常:", e);
		}
		return 0;
	}

	@Override
	public int deleteFeedbackCfgList(String[] ids) {
		int iCount = 0;
		for (String id : ids) {
			this.deleteFeedbackCfg(Long.valueOf(id));
			iCount++;
		}
		return iCount;
	}

	@Override
	public int updateFeedbackCfg(FeedbackCfgVo cfg) {
		try {
			return feedbackCfgDao.updateFeedbackCfg(cfg);
		} catch (Throwable e) {
			logger.error("更新配置异常:", e);
			return 0;
		}
	}

	@Override
	public FeedbackCfgVo getFeedbackCfg(Long id) {
		FeedbackCfgVo objFeedbackCfg = null;
		try {
			objFeedbackCfg = feedbackCfgDao.getFeedbackCfgById(id);
		} catch (Throwable e) {
			logger.error("根据ID查询配置异常:", e);
		}
		return objFeedbackCfg;
	}

	@Override
	public boolean sendToCustomSys(FeedbackCfgVo objFeedbackCfg) throws Exception {
		Feedback feedback = new Feedback();

		// 设置渠道ID(11-官网，12-无线)
		feedback.setChannelId(12);
		feedback.setCreate_time(new Date());
		// 类型：1-投诉；2-建议；3-咨询;4-微信维权
		feedback.setFeedback_type(new Integer(4));
		feedback.setFeedback_title("微信维权");
		feedback.setFeedback_content("用户投诉原因: " + objFeedbackCfg.getReason() + ",\n用户希望解决方案: " + objFeedbackCfg.getSolution());
		feedback.setWechatOpenId(objFeedbackCfg.getOpenId());
		feedback.setWechatFeedbackId(objFeedbackCfg.getFeedbackId());
		// 从投诉建议的扩展信息中获取电话号码(扩展信息格式：备注信息+' '+手机号码)
		if (StringUtils.isNotBlank(objFeedbackCfg.getExtinfo())) {
			String extInfo = objFeedbackCfg.getExtinfo();
			String phone = extInfo.substring(extInfo.lastIndexOf(" "));
			feedback.setCust_phone(StringUtils.isNotBlank(phone) ? phone.trim() : "");
			feedback.setFeedback_content(feedback.getFeedback_content() + ",\n备注信息： " + extInfo);
		}

		PayOrderDO payOrderDO = queryPayOrderInfo(objFeedbackCfg.getOpenId(), objFeedbackCfg.getTransId());
		if (null != payOrderDO) {
			UserBaseDTO userBaseDTO = queryUserBasicInfo(payOrderDO.getUserId());
			if (null != userBaseDTO) {
				feedback.setCust_id(payOrderDO.getUserId());
				feedback.setCustLoginName(this.getCustLoginName(userBaseDTO));
				feedback.setCust_name(userBaseDTO.getCustName());
				feedback.setCust_mail(userBaseDTO.getEmail());
				if (StringUtils.isBlank(feedback.getCust_phone())) {
					feedback.setCust_phone(userBaseDTO.getMobile());
				}
			}
		} else {
			feedback.setCustLoginName("微信用户");
			feedback.setCust_name(objFeedbackCfg.getOpenId());
		}
		// 发送到客服系统
		boolean bResult = feedbackHessianService.sendFeedback(feedback);

		int iResult = 0;
		if (bResult) {
			iResult = saveFeedbackCfg(objFeedbackCfg);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("打印发送到客服系统的维权信息:----------BEGIN-------------");
			logger.debug("维权信息:" + feedback);
			logger.debug("发送结果(bResult):" + bResult);
			logger.debug("保存结果(iResult):" + iResult);
			logger.debug("打印发送到客服系统的维权信息:-----------END--------------");
		}
		return bResult;
	}

	/**
	 * 根据UserId查询用户基本信息
	 * 
	 * @param userId
	 * @return UserBaseDTO
	 */
	private UserBaseDTO queryUserBasicInfo(long userId) {
		try {
			com.xiu.uuc.facade.dto.Result result = userManageFacade.getUserBasicInfoByUserId(userId);

			if (logger.isDebugEnabled()) {
				logger.debug("查询用户基本信息-------BEGIN-------");
				logger.debug("UserBaseDTO:{}", (UserBaseDTO) result.getData());
				logger.debug("查询用户基本信息-------END-------");
			}
			return (null != result && null != result.getData()) ? (UserBaseDTO) result.getData() : null;
		} catch (Exception e) {
			logger.error("调用UUC，根据userId查询用户基本信息时发生异常。", e);
		}
		return null;
	}

	/**
	 * 到支付中心查询支付信息
	 * 
	 * @param openId
	 * @param transId
	 * @return PayOrderDO
	 */
	private PayOrderDO queryPayOrderInfo(String openId, String transId) {
		PayOrderDO payOrderDO = null;
		try {
			Result oscResult = bizOrderQueryServive.queryPayOrderInfo(openId, transId);
			payOrderDO = (PayOrderDO) oscResult.getDefaultModel();

			if (logger.isDebugEnabled()) {
				logger.debug("查询订单支付信息-------BEGIN-------");
				logger.debug("PayOrderDO:{}", payOrderDO);
				logger.debug("查询订单支付信息-------END-------");

			}
		} catch (Exception e) {
			logger.error("调用支付中心，查询支付信息时发生异常。", e);
		}
		return payOrderDO;
	}

	/**
	 * 组装用户名
	 * 
	 * @param userDetailDTO
	 * @return String
	 */
	private String getCustLoginName(UserBaseDTO userBaseDTO) {
		String custLogName = "";

		// 注册类型 Email
		if (TypeEnum.RegisterType.EMAIL.getKey().equals(userBaseDTO.getRegisterType())) {
			custLogName = userBaseDTO.getEmail();
		}
		// 注册类型 Mobile
		if (TypeEnum.RegisterType.MOBILE.getKey().equals(userBaseDTO.getRegisterType())) {
			custLogName = userBaseDTO.getMobile();
		}
		// 注册类型 Union
		if (TypeEnum.RegisterType.PETNAME.getKey().equals(userBaseDTO.getRegisterType())) {
			custLogName = userBaseDTO.getPetName();
		}

		return custLogName;
	}

	@Override
	public int updateFeedbackCfgList(List<FeedbackCfgVo> lstVos) {
		int iCount = 0;
		for (FeedbackCfgVo feedbackCfgVo : lstVos) {
			updateFeedbackCfg(feedbackCfgVo);
			iCount++;
		}
		return iCount;
	}

}
