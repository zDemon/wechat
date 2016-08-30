package com.demon.wx.entity;

import java.util.Map;

import com.demon.wx.common.MessageType;

/**
 * <p>Title: Video</p>
 * <p>Description: </p>
 * @author dmeng
 * @date 2016年8月29日 上午11:58:13
 */
public class Video extends BaseMessage {

	private String MediaId;			// 通过素材管理接口上传多媒体文件，得到的id required
	private String Tile;			// 视频消息的标题
	private String Description;		// 视频消息的描述
		
	public Video() {
		super();
		this.MsgType = MessageType.VIDEO.toString().toLowerCase();
	}
	
	public Video(Map<String, String> map) {
		super(map);
		this.MsgType = MessageType.VIDEO.toString().toLowerCase();
	}
	
	public Video(Map<String, String> map, String mediaId) {
		super(map);
		this.MediaId = mediaId;
		this.MsgType = MessageType.VIDEO.toString().toLowerCase();
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getTile() {
		return Tile;
	}

	public void setTile(String tile) {
		Tile = tile;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
	
}
