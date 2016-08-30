package com.demon.wx.entity;

import java.util.Map;

import com.demon.wx.common.MessageType;

/**
 * <p>Title: Text</p>
 * <p>Description: 文本消息</p>
 * @author dmeng
 * @date 2016年8月26日 下午6:03:34
 */
public class Text extends BaseMessage {
	
	/** 文本消息内容 */
	private String Content;

	public Text() {
		super();
		this.MsgType = MessageType.TEXT.toString().toLowerCase();
	}
	
	public Text(Map<String, String> map) {
		super(map);
		this.MsgType = MessageType.TEXT.toString().toLowerCase();
	}

	public Text(Map<String, String> map, String content) {
		super(map);
		this.Content = content;
		this.MsgType = MessageType.TEXT.toString().toLowerCase();
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

}
