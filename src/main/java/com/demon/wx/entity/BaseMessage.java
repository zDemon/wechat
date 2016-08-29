package com.demon.wx.entity;

import java.util.Date;

public class BaseMessage {
	
	/** 接收方帐号（收到的OpenID） */
	protected String toUserName;
	
	/** 开发者微信号 */
	protected String fromUserName;
	
	/** 消息创建时间 （整型） */
	protected Long createTime = new Date().getTime();
	
	/** 消息类型  com.demon.wx.common.MsgType */
	protected String msgType;

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
}
