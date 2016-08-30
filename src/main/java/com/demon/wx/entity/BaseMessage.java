package com.demon.wx.entity;

import java.util.Date;
import java.util.Map;

import com.demon.wx.utils.MessageHandler;

public class BaseMessage {
	
	/** 接收方帐号（收到的OpenID） */
	protected String ToUserName;
	
	/** 开发者微信号 */
	protected String FromUserName;
	
	/** 消息创建时间 （整型） */
	protected Long CreateTime = new Date().getTime();
	
	/** 消息类型  com.demon.wx.common.MessageType */
	protected String MsgType;
	
	public BaseMessage() {
		super();
	}

	public BaseMessage(Map<String, String> map) {
		super();
		this.FromUserName = map.get("ToUserName");
		this.ToUserName = map.get("FromUserName");
	}

	public String toXML() {
		return MessageHandler.msgToXML(this);
	}

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

	public Long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	
}
