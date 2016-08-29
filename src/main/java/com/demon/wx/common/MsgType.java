package com.demon.wx.common;

/**
 * <p>Title: MsgType</p>
 * <p>Description: 微信消息类型</p>
 * @author dmeng
 * @date 2016年7月21日 上午10:47:39
 */
public enum MsgType {

	TEXT,
	IMAGE,
	VOICE,
	VIDEO,
	SHORTVIDEO,
	LOCALTION,
	LINK,
	EVENT,
	MUSIC,
	NEWS;
	
	/**
	 * <p>Description: 获取小写的类型名称</p>
	 * @author dmeng
	 * @date 2016年8月29日 上午11:48:15
	 * @return
	 */
	public String getType() {
		return this.toString().toLowerCase();
	}
	
}
