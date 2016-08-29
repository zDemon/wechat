package com.demon.wx.entity;

import com.demon.wx.common.MsgType;

/**
 * <p>Title: Voice</p>
 * <p>Description: </p>
 * @author dmeng
 * @date 2016年8月29日 上午11:57:59
 */
public class Voice extends BaseMessage {

	private String mediaId;

	public Voice(String mediaId) {
		super();
		this.mediaId = mediaId;
		this.msgType = MsgType.VOICE.getType();
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	
	
}
