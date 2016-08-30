package com.demon.wx.entity;

import java.util.Map;

import com.demon.wx.common.MessageType;

/**
 * <p>Title: Image</p>
 * <p>Description: </p>
 * @author dmeng
 * @date 2016年8月29日 上午11:57:34
 */
public class Image extends BaseMessage {

	/** 通过素材管理接口上传多媒体文件，得到的id。 */
	private String MediaId;

	public Image() {
		super();
		this.MsgType = MessageType.IMAGE.toString().toLowerCase();
	}
	
	public Image(Map<String, String> map) {
		super(map);
		this.MsgType = MessageType.IMAGE.toString().toLowerCase();
	}
	
	public Image(Map<String, String> map, String mediaId) {
		super(map);
		this.MediaId = mediaId;
		this.MsgType = MessageType.IMAGE.toString().toLowerCase();
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

}
