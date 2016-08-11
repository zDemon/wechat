package com.demon.wx.common;

/**
 * <p>Title: MediaType</p>
 * <p>Description: 微信素材上传支持的媒体类型</p>
 * @author dmeng
 * @date 2016年8月11日 下午5:08:38
 */
public enum MediaType {

	IMAGE("image", "图片"), 
	VOICE("voice", "语音"), 
	VIDEO("video", "视频"),
	THUMB("thumb", "略缩图");	 
	
	private String type;
	private String desc;
	
	private MediaType(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
