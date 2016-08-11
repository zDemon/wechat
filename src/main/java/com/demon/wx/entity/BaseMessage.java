package com.demon.wx.entity;

public class BaseMessage {

	protected String msgType;	// 消息类型 required

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
}
