package com.xiu.mobile.wechat.web.dto;

/**
 * 发送类型消息基类 
 * 
 * 本类包含发送消息中的基础属性
 * 
 * @author wangzhenjiang 
 * 
 * 例：以下为 文本消息 的发送格式
 * <xml> 
 * 	<ToUserName><![CDATA[toUser]]></ToUserName>
 *  <FromUserName><![CDATA[fromUser]]></FromUserName>
 *  <CreateTime>1348831860</CreateTime>
 *  <MsgType><![CDATA[text]]></MsgType> 
 *  <Content><![CDATA[this is a test]]></Content> 
 * </xml>
 */
public class BaseMessage {
	/**
	 * 接收方帐号（收到的OpenID）
	 */
	private String ToUserName;
	/**
	 * 开发者微信号
	 */
	private String FromUserName;
	/**
	 * 消息创建时间 （整型）
	 */
	private long CreateTime;
	/**
	 * 消息类型（text/music/news）
	 */
	private String MsgType;
	/**
	 * 位0x0001被标志时，星标刚收到的消息
	 */
	//比如用户如果发送一个请求，而 API 又无法处理，可以将这个节点内容设置为 1，那么用户发送的消息会被加上星标
	private int FuncFlag;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public int getFuncFlag() {
		return FuncFlag;
	}

	public void setFuncFlag(int funcFlag) {
		FuncFlag = funcFlag;
	}
}
