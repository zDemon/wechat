package com.demon.wx.entity;

import com.demon.wx.common.MsgType;

/**
 * <p>Title: Image</p>
 * <p>Description: </p>
 * @author dmeng
 * @date 2016年8月29日 上午11:57:34
 */
public class Image extends BaseMessage {

	/** 通过素材管理接口上传多媒体文件，得到的id。 */
	private String mediaId;

	public Image(String mediaId) {
		super();
		this.mediaId = mediaId;
		this.msgType = MsgType.IMAGE.toString().toLowerCase();
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	
	
}
