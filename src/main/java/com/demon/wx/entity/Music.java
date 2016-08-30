package com.demon.wx.entity;

import java.util.Map;

import com.demon.wx.common.MessageType;

/**
 * <p>Title: Music</p>
 * <p>Description: </p>
 * @author dmeng
 * @date 2016年8月29日 上午11:58:29
 */
public class Music extends BaseMessage {

	private String Title;			// 音乐标题
	private String Description;		// 音乐描述
	private String MusicUrl;		// 音乐链接
	private String HQMusicUrl;		// 高质量音乐链接，WIFI环境优先使用该链接播放音乐
	private String ThumbMediaId;	// 缩略图的媒体id，通过素材管理接口上传多媒体文件，得到的id
	
	public Music() {
		super();
		this.MsgType = MessageType.MUSIC.toString().toLowerCase();
	}
	
	public Music(Map<String, String> map) {
		super(map);
		this.MsgType = MessageType.MUSIC.toString().toLowerCase();
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getMusicUrl() {
		return MusicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		MusicUrl = musicUrl;
	}

	public String getHQMusicUrl() {
		return HQMusicUrl;
	}

	public void setHQMusicUrl(String hQMusicUrl) {
		HQMusicUrl = hQMusicUrl;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
	
}
