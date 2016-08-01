package com.xiu.mobile.wechat.web.dto;

/**
 * 文本消息类 
 * 
 * @author wangzhenjiang 
 * 
 * 以下为文本消息格式:
 * <xml> 
 * 	<ToUserName><![CDATA[toUser]]></ToUserName>
 *  <FromUserName><![CDATA[fromUser]]></FromUserName>
 *  <CreateTime>1348831860</CreateTime>
 *  <MsgType><![CDATA[text]]></MsgType> 
 *  <Content><![CDATA[this is a test]]></Content> 
 * </xml>
 */
public class TextMessage extends BaseMessage{
	
	public TextMessage(){}
	
	public TextMessage(String content){
		this.Content = content;
	}
	
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
	
}
