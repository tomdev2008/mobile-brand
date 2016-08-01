package com.xiu.mobile.wechat.web.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xiu.mobile.wechat.web.constants.EventType;
import com.xiu.mobile.wechat.web.constants.MessageType;
import com.xiu.mobile.wechat.web.constants.MessageXmlField;
import com.xiu.mobile.wechat.web.dto.Article;
import com.xiu.mobile.wechat.web.dto.NewsMessage;
import com.xiu.mobile.wechat.web.service.IWechatService;
import com.xiu.mobile.wechat.web.utils.MessageUtil;

/**
 * 
 * 微信信息处理服务类
 * 
 * 处理微信接收的信息，并进行响应
 * 
 * @author wangzhenjiang
 * 
 */
@Service("wechatService")
public class WechatServiceImpl implements IWechatService {
	private static final Logger logger = LoggerFactory.getLogger(WechatServiceImpl.class);

	// @Resource
	// private IMessageCfgService messageCfgService;
	//
	// @Resource
	// private IArticleCfgService articleCfgService;
	//
	// @Resource
	// private IMaterialCfgService materialCfgService;

	/**
	 * 
	 * 处理请求信息，并进行响应
	 * 
	 * @param request
	 *            .getInstream();
	 * @return String
	 * @throws IOException
	 */
	public String processReq(InputStream in) {
		Map<String, String> reqMsg = new HashMap<String,String>();
		try {
			reqMsg = MessageUtil.parseXml(in);
		} catch (DocumentException e) {
			logger.error("解析接收消息发生异常： " + e.getMessage());
		} catch (IOException e) {
			logger.error("解析接收消息发生异常： " + e.getMessage());
		}
		logger.info("--------------解析从客户端获取的消息：-------------- \n");
		for (String key : reqMsg.keySet()) {
			logger.info(key + " -- " + reqMsg.get(key));
		}
		logger.info("--------------解析结束--------------");
		return getMessage(reqMsg);
	}

	/**
	 * 
	 * 组装需要返回的信息
	 * 
	 * @param reqMsg
	 * @return String
	 */
	public String getMessage(Map<String, String> reqMsg) {
		String reqMsgType = reqMsg.get(MessageXmlField.MSG_TYPE.getValue());
		String returnMsg = "";
		// 判断接收到的消息类型
		if (MessageType.EVENT.getValue().equals(reqMsgType)) {
			String eventType = reqMsg.get(MessageXmlField.EVENT.getValue());
			// Event事件中的订阅事件
			if (eventType.equals(EventType.SUBSCRIBE.getValue())) {
				returnMsg = getMenu(reqMsg);
			}
			// 自定义菜单点击事件
			else if (eventType.equals(EventType.CLICK.getValue())) {
				// 事件KEY值，与创建自定义菜单时指定的KEY值对应
				String eventKey = reqMsg.get("EventKey");
				logger.info("获取自定义菜单点击事件[eventKey:" + eventKey + "]");
				returnMsg = getReturnMsg(reqMsg, eventKey);
			}
		} else if (MessageType.TEXT.getValue().equals(reqMsgType)) {// 接收消息类型为文本类型
			String text = reqMsg.get(MessageXmlField.CONTENT.getValue());
			logger.info("获取文本请求：[" + text + "]");
			returnMsg = getReturnMsg(reqMsg, text);
		}
		logger.info("获取返回信息：" + returnMsg);
		return returnMsg;
	}

	private String getReturnMsg(Map<String, String> reqMsgMap, String strReqMsg) {
		String returnMsg = "";
		/*		List<MessageCfgVo> lstMessageCfg = messageCfgService.getMessageCfgList(strReqMsg);
				if (lstMessageCfg != null && lstMessageCfg.size() > 0) {
					MessageCfgVo objMessageCfg = lstMessageCfg.get(0);
					if (objMessageCfg != null) {
						String respMsgType = objMessageCfg.getMsgType();
						// 回复文本消息
						if (MessageType.TEXT.getValue().equals(respMsgType)) {
							returnMsg = this.getTextMessage(reqMsgMap, objMessageCfg.getContent());
						}
						// 回复图文消息
						else if (MessageType.NEWS.getValue().equals(respMsgType)) {
							Long materialId = objMessageCfg.getMaterialId();
							MaterialCfgVo objMaterialCfg = materialCfgService.getMaterialCfg(materialId);
							List<ArticleCfgVo> lstArticleCfg = new ArrayList<ArticleCfgVo>(10);
							// 第一张图为大图
							Long firstArticleId = objMaterialCfg.getFirstArticleId();
							ArticleCfgVo objArticleCfg = articleCfgService.getArticleCfg(firstArticleId);
							lstArticleCfg.add(objArticleCfg);
							// 多图文消息
							if ("simple" == objMaterialCfg.getType()) {
								String otherArticleId = objMaterialCfg.getOtherArticleId();
								Map<String, Object> params = new HashMap<String, Object>(10);
								params.put("ids", otherArticleId.split(","));
								lstArticleCfg.addAll(articleCfgService.getArticleCfgList(params));
							}
							List<Article> lstArticles = onvertToArticle(lstArticleCfg);
							returnMsg = this.getNewsMessage(reqMsgMap, lstArticles);
						}
					}
				}*/
		return returnMsg;
	}

	/*	private List<Article> onvertToArticle(List<ArticleCfgVo> lstArticleCfg) {
			if (lstArticleCfg == null || lstArticleCfg.size() == 0) {
				logger.info("没有获取到图文配置信息： lstArticleCfg is null !");
				return null;
			}
			List<Article> lstArticle = new ArrayList<Article>();
			Article article = null;
			for (ArticleCfgVo objArticleCfg : lstArticleCfg) {
				article = new Article();
				article.setTitle(objArticleCfg.getTitle());
				article.setDescription(objArticleCfg.getDescription());
				article.setPicUrl(objArticleCfg.getPicUrl());
				article.setUrl(objArticleCfg.getUrl());

				lstArticle.add(article);
			}
			return lstArticle;
		}*/

	/**
	 * 返回菜单
	 * 
	 * @param reqMsg
	 * @return
	 */
	private String getMenu(Map<String, String> reqMsg) {

		NewsMessage out = new NewsMessage();
		// 回应消息时 需要较色转换
		out.setFromUserName(reqMsg.get(MessageXmlField.TO_USER_NAME.getValue()));
		out.setToUserName(reqMsg.get(MessageXmlField.FROM_USER_NAME.getValue()));
		out.setCreateTime(new Date().getTime());
		out.setMsgType(MessageType.NEWS.getValue());
		out.setFuncFlag(0);

		List<Article> lstArticle = new ArrayList<Article>();
		Article article = new Article();
		article.setTitle("欢迎关注走秀网");

		Article article2 = new Article();
		StringBuilder sb = new StringBuilder();
		sb.append("【1】每日促销\n");
		sb.append("【2】走秀主页\n");
		sb.append("【3】女士促销专场\n");
		sb.append("【4】男士促销专场\n");
		sb.append("【5】名品特卖\n");
		sb.append("【6】奢侈品专柜\n");
		sb.append("【7】eBay秀\n");
		sb.append("【8】走秀团购\n");
		sb.append("【0】显示本菜单");
		article2.setTitle(sb.toString());

		Article article3 = new Article();
		article3.setTitle("回复对应数字查看使用方法 ");

		lstArticle.add(article);
		lstArticle.add(article2);
		lstArticle.add(article3);
		out.setArticles(lstArticle);
		out.setArticleCount(lstArticle.size());
		return MessageUtil.messageToXml(out);
	}

	/**
	 * 
	 * 返回文本类型的消息
	 * 
	 * @param reqMsg
	 * @return String
	 */
	/*private String getTextMessage(Map<String, String> reqMsg, String content) {
		TextMessage out = new TextMessage();
		out.setContent(content);
		// 回应消息时 需要较色转换
		out.setFromUserName(reqMsg.get(MessageXmlField.TO_USER_NAME.getValue()));
		out.setToUserName(reqMsg.get(MessageXmlField.FROM_USER_NAME.getValue()));
		out.setCreateTime(new Date().getTime());
		out.setMsgType(MessageType.TEXT.getValue());
		return MessageUtil.messageToXml(out);
	}*/

	/**
	 * 
	 * 返回图文类型的消息
	 * 
	 * @param reqMsg
	 * @return String
	 */
	/*private String getNewsMessage(Map<String, String> reqMsg, List<Article> lstArticle) {
		NewsMessage out = new NewsMessage();
		// 回应消息时 需要较色转换
		out.setFromUserName(reqMsg.get(MessageXmlField.TO_USER_NAME.getValue()));
		out.setToUserName(reqMsg.get(MessageXmlField.FROM_USER_NAME.getValue()));
		out.setCreateTime(new Date().getTime());
		out.setMsgType(MessageType.NEWS.getValue());
		out.setFuncFlag(0);

		out.setArticles(lstArticle);
		out.setArticleCount(lstArticle.size());
		return MessageUtil.messageToXml(out);
	}*/

}
