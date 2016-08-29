package com.demon.wx.entity;

import com.demon.wx.common.MsgType;

/**
 * <p>Title: Video</p>
 * <p>Description: </p>
 * @author dmeng
 * @date 2016年8月29日 上午11:58:13
 */
public class Video extends BaseMessage {

	private String mediaId;			// 通过素材管理接口上传多媒体文件，得到的id required
	private String tile;			// 视频消息的标题
	private String description;		// 视频消息的描述
		
	public Video(String mediaId) {
		super();
		this.mediaId = mediaId;
		this.msgType = MsgType.VIDEO.toString().toLowerCase();
	}
	
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getTile() {
		return tile;
	}
	public void setTile(String tile) {
		this.tile = tile;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
