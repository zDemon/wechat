package com.demon.wx.entity;

import java.util.Map;

import com.demon.wx.common.MessageType;

/**
 * <p>Title: Voice</p>
 * <p>Description: </p>
 * @author dmeng
 * @date 2016年8月29日 上午11:57:59
 */
public class Voice extends BaseMessage {

	private String MediaId;

	public Voice() {
		super();
		this.MsgType = MessageType.VOICE.getType();
	}
	
	public Voice(Map<String, String> map) {
		super(map);
		this.MsgType = MessageType.VOICE.getType();
	}
	
	public Voice(Map<String, String> map, String mediaId) {
		super(map);
		this.MediaId = mediaId;
		this.MsgType = MessageType.VOICE.getType();
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

}
