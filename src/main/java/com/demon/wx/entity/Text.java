package com.demon.wx.entity;

import com.demon.wx.common.MsgType;

/**
 * <p>Title: Text</p>
 * <p>Description: 文本消息</p>
 * @author dmeng
 * @date 2016年8月26日 下午6:03:34
 */
public class Text extends BaseMessage {
	
	/** 文本消息内容 */
	private String content;

	
	
	public Text(String content) {
		super();
		this.content = content;
		this.msgType = MsgType.TEXT.toString().toLowerCase();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
