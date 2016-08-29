package com.demon.wx.utils;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.demon.wx.common.EventType;
import com.demon.wx.common.MsgType;
import com.demon.wx.entity.Music;
import com.demon.wx.entity.News;
import com.demon.wx.entity.Video;

/**
 * <p>Title: MessageHandler</p>
 * <p>Description: 微信消息处理</p>
 * @author dmeng
 * @date 2016年7月20日 下午5:33:40
 */
public class MessageHandler {

	/**
	 * <p>Description: 解析微信发来的xml消息</p>
	 * @author dmeng
	 * @date 2016年7月20日 下午5:39:19 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> parseXML(HttpServletRequest request) throws Exception {
		// 解析为map
		Map<String, String> map = new HashMap<String, String>();
		//获取输入流
		InputStream is = request.getInputStream();
		SAXReader reader = new SAXReader();
		// 读取xml
		Document doc = reader.read(is);
		Element root = doc.getRootElement();
		@SuppressWarnings("unchecked")
		List<Element> elements = root.elements();
		for(Element e : elements) {
			System.out.println(e.getName() + ">" + e.getText());
			map.put(e.getName(), e.getText());
		}
		is.close();
		is = null;
		return map;
	}
	
	/**
	 * <p>Description: 构建xml返回信息</p>
	 * @author dmeng
	 * @date 2016年7月21日 上午9:40:44
	 * @param map
	 * @return
	 */
	public static String getResponse(Map<String, String> map) {
		String response = "success";
		MsgType msgType = MsgType.valueOf(MsgType.class, map.get("MsgType").toUpperCase());
		switch(msgType) {
		case TEXT :
			response = handleTextMessage(map);
			break;
		case IMAGE :
			response = handleImageMessage(map);
			break;
		case EVENT :
			response = handleEventMessage(map);
			break;
		default :
			break;
		}
		return response;
	}
	
	/**
	 * <p>Description: 处理文本输入信息</p>
	 * <p>Company: caimei365</p> 
	 * @author dmeng
	 * @date 2016年8月12日 下午3:01:38
	 * @param map
	 * @return
	 */
	private static String handleTextMessage(Map<String, String> map) {
		String responseMsg = "success";
		String content = map.get("Content");
		switch(content) {
		case "文本" :
			int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			String text = "\n(这是来自遥远的Demon回复的消息)";
			if(hourOfDay < 20) {
				text = "这么晚了你该睡觉了(,,•́.•̀,,)" + text;
			} else if(hourOfDay < 6) {
				text = "上午好(•̀ω•́)✧" + text;
			} else if(hourOfDay < 14) {
				text = "中午好(•̀ω•́)✧" + text;
			} else if(hourOfDay < 18) {
				text = "下午好(•̀ω•́)✧" + text;
			} else {
				text = "晚上好(•̀ω•́)✧" + text;
			}
			responseMsg = buildTextMessage(map, text);
			break;
		case "图片" :
			String mediaId = "_2vZB3Em2BqVrUFgs9OEgjrxon_nD6jUO6AnTyWHhyziGGZihPBx9gBDbQI02tKr";
			responseMsg = buildImageMessage(map, mediaId);
			break;
		case "音乐" : 
			Music music = new Music();
			music.setTitle("泠鸢yousa - 黑猫.mp3");
			music.setDescription("泠鸢yousa填词 黑猫");
			music.setMusicUrl("http://demon.vip.natapp.cn/material/yousa_heimao.mp3");
			music.setHqMusicUrl("http://demon.vip.natapp.cn/material/yousa_heimao.mp3");
			responseMsg = buildMusicMessage(map, music);
			break;
		case "语音" :
			String voiceMediaId = "tRtEUlibZWIqzjjxavKPGSKO8pEkXMqS8yrqY1wMzrMr74briEESbhyfDAGWYpKq"; // 薛之谦 - 演员.mp3
			responseMsg = buildVoiceMessage(map, voiceMediaId);
			break;
		case "视频" :
			String videoMediaId = "HslvNqmBkHkNTYyxDnDNe5PH7DMQPcsqsH4-l9e6L5DdyhASZUMAsgyorjpKP_qR"; // RememberTheName.mp4
			Video video = new Video(videoMediaId);
			video.setTile("短裙全明星Remember the name");
			video.setDescription("B站鬼畜全明星av5337457→_→");
			responseMsg = buildVideoMessage(map, video);
			break;
		case "图文" :
			List<News> newsList = new ArrayList<News>();
			News news0 = new News();
			news0.setTitle("【王司徒X傅园慧】王司徒传授给傅爷的最后一课！【逗比神功续集】");
			news0.setDescription("up主 理科生天依酱");
			news0.setPicUrl("http://demon.vip.natapp.cn/material/situ_fuye.jpg");
			news0.setUrl("http://www.bilibili.com/video/av5746638/");
			newsList.add(news0);
			News news1 = new News();
			news1.setTitle("【王司徒单曲】入戏太深");
			news1.setDescription("up主 飞机君");
			news1.setPicUrl("http://demon.vip.natapp.cn/material/ruxitaishen.jpg");
			news1.setUrl("http://www.bilibili.com/video/av3078651/");
			newsList.add(news1);
			News news2 = new News();
			news2.setTitle("短裙全明星Nobody4 Plus");
			news2.setDescription("up主 女孩为何穿短裙");
			news2.setPicUrl("http://demon.vip.natapp.cn/material/Nobody4Plus.jpg");
			news2.setUrl("http://www.bilibili.com/video/av4995866/");
			newsList.add(news2);
			responseMsg = buildNewsMessage(map, newsList);
			break;
		default :
			responseMsg = buildTextMessage(map, "你这消息我没法接(๑•́₃•̀๑)");
			break;
		}
		return responseMsg;
	}
	
	/**
	 * <p>Description: </p>
	 * <p>Company: caimei365</p> 
	 * @author dmeng
	 * @date 2016年8月12日 下午3:02:24
	 * @param map
	 * @return
	 */
	private static String handleImageMessage(Map<String, String> map) {
		String fromUserName = map.get("FromUserName");
		String toUserName = map.get("ToUserName");
		return String.format(
				"<xml>" +
					"<ToUserName><![CDATA[%s]]></ToUserName>" +
					"<FromUserName><![CDATA[%s]]></FromUserName>" +
					"<CreateTime>%s</CreateTime>" +
					"<MsgType><![CDATA[image]]></MsgType>" +
					"<Image>" +
						"<MediaId><![CDATA[%s]]></MediaId>" +
					"</Image>" +
				"</xml>", 
					fromUserName, toUserName, getUtcTime(), "srHo_d1OiKBMl_F0kmsrcI7gpBXwFO5qSVyMS1pV2SbJDe3OxlKtM-ZuGupH6sEN"); 
	}
	
	private static String handleEventMessage(Map<String, String> map) {
		String responseMsg = "";
		EventType event = EventType.valueOf(EventType.class, map.get("Event").toUpperCase());
		switch(event) {
		case SUBSCRIBE : 
			String content = "welcaome to Demon's official account(ฅ>ω<*ฅ)\n请回复如下关键词获取对应信息：\n文本\n图片\n语音\n视频\n音乐\n图文";
			responseMsg = buildTextMessage(map, content);
			break;
		case UNSUBSCRIBE :
			System.out.println("用户" + map.get("FromUserName") + "取消了关注(；′⌒`)");
			break;
		case LOCATION :
			break;
		default :
			break;
		}
		return responseMsg;
	}
	
	
	
	/**
	 * <p>Description: </p>
	 * @author dmeng
	 * @date 2016年7月21日 上午10:17:09
	 * @param map
	 * @param content
	 * @return
	 * 文本消息格式：
	 *  <xml>
			<ToUserName><![CDATA[toUser]]></ToUserName>
			<FromUserName><![CDATA[fromUser]]></FromUserName>
			<CreateTime>12345678</CreateTime>
			<MsgType><![CDATA[text]]></MsgType>
			<Content><![CDATA[你好]]></Content>
		</xml>
	 * 
	 * 
	 * 
	 */
	private static String buildTextMessage(Map<String, String> map, String content) {
		String fromUserName = map.get("FromUserName");
		String toUserName = map.get("ToUserName");
		return String.format(
				"<xml>" + 
						"<ToUserName><![CDATA[%s]]></ToUserName>" +
						"<FromUserName><![CDATA[%s]]></FromUserName>" + 
						"<CreateTime>%s</CreateTime>" +
						"<MsgType><![CDATA[text]]></MsgType>" +
						"<Content><![CDATA[%s]]></Content>" +
				"</xml>", 
				fromUserName, toUserName, getUtcTime(), content);
	}
	
	/**
	 * <p>Description: </p>
	 * @author dmeng
	 * @date 2016年8月8日 下午3:43:43
	 * @param map
	 * @param mediaId
	 *  <xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>12345678</CreateTime>
		<MsgType><![CDATA[image]]></MsgType>
		<Image>
		<MediaId><![CDATA[media_id]]></MediaId>
		</Image>
		</xml>
	 * @return
	 */
	private static String buildImageMessage(Map<String, String> map, String mediaId) {
		String fromUserName = map.get("FromUserName");
		String toUserName = map.get("ToUserName");	
		return String.format(
				"<xml>" + 
						"<ToUserName><![CDATA[%s]]></ToUserName>" +
						"<FromUserName><![CDATA[%s]]></FromUserName>" + 
						"<CreateTime>%s</CreateTime>" +
						"<MsgType><![CDATA[image]]></MsgType>" +
						"<Image>" +
						"<MediaId><![CDATA[%s]]></MediaId>" + 
						"</Image>" +
				"</xml>",  
				fromUserName, toUserName, getUtcTime(), mediaId);
	}
	
	private static String buildMusicMessage(Map<String, String> map, Music music) {
		String fromUserName = map.get("FromUserName");
		String toUserName = map.get("ToUserName");	
		return String.format(
				"<xml>" + 
						"<ToUserName><![CDATA[%s]]></ToUserName>" +
						"<FromUserName><![CDATA[%s]]></FromUserName>" + 
						"<CreateTime>%s</CreateTime>" +
						"<MsgType><![CDATA[%s]]></MsgType>" +
						"<Music>" +
						"<Title><![CDATA[%s]]></Title>" + 
						"<Description><![CDATA[%s]]></Description>" + 
						"<MusicUrl><![CDATA[%s]]></MusicUrl>" + 
						"<HQMusicUrl><![CDATA[%s]]></HQMusicUrl>" + 
						"</Music>" +
				"</xml>",  
				fromUserName, toUserName, getUtcTime(), music.getMsgType(), music.getTitle(), music.getDescription(), music.getMusicUrl(), music.getHqMusicUrl());
	}
	
	private static String buildVoiceMessage(Map<String, String> map, String mediaId) {
		String fromUserName = map.get("FromUserName");
		String toUserName = map.get("ToUserName");	
		return String.format(
				"<xml>" + 
						"<ToUserName><![CDATA[%s]]></ToUserName>" +
						"<FromUserName><![CDATA[%s]]></FromUserName>" + 
						"<CreateTime>%s</CreateTime>" +
						"<MsgType><![CDATA[voice]]></MsgType>" +
						"<Voice>" +
						"<MediaId><![CDATA[%s]]></MediaId>" + 
						"</Voice>" +
				"</xml>",  
				fromUserName, toUserName, getUtcTime(), mediaId);
	}
	
	private static String buildVideoMessage(Map<String, String> map, Video video) {
		String fromUserName = map.get("FromUserName");
		String toUserName = map.get("ToUserName");	
		return String.format(
				"<xml>" + 
					"<ToUserName><![CDATA[%s]]></ToUserName>" +
					"<FromUserName><![CDATA[%s]]></FromUserName>" + 
					"<CreateTime>%s</CreateTime>" +
					"<MsgType><![CDATA[%s]]></MsgType>" +
					"<Video>" +
					"<MediaId><![CDATA[%s]]></MediaId>" + 
					"<Title><![CDATA[%s]]></Title>" + 
					"<Description><![CDATA[%s]]></Description>" + 
					"</Voice>" +
				"</xml>",  
				fromUserName, toUserName, getUtcTime(), video.getMsgType(), video.getMediaId(), video.getTile(), video.getDescription());
	}
	
	private static String buildNewsMessage(Map<String, String> map, List<News> newsList) {
		String fromUserName = map.get("FromUserName");
		String toUserName = map.get("ToUserName");	
		String items = "";
		for(News news : newsList) {
			items += buildSingleItem(news);
		}
		return String.format(
				"<xml>" + 
					"<ToUserName><![CDATA[%s]]></ToUserName>" +
					"<FromUserName><![CDATA[%s]]></FromUserName>" + 
					"<CreateTime>%s</CreateTime>" +
					"<MsgType><![CDATA[%s]]></MsgType>" +
					"<ArticleCount>%s</ArticleCount>" +
					"<Articles>" +
					"%s" +
					"</Articles>" +
				"</xml>",
				fromUserName, toUserName, getUtcTime(), newsList.get(0).getMsgType(), newsList.size(), items);
	}
	
	private static String buildSingleItem(News news) {
		return String.format(
				"<item>" +
				"<Title><![CDATA[%s]]></Title>" +
				"<Description><![CDATA[%s]]></Description>" +
				"<PicUrl><![CDATA[%s]]></PicUrl>" +
				"<Url><![CDATA[%s]]></Url>" +
				"</item>", 
				news.getTitle(), news.getDescription(), news.getPicUrl(), news.getUrl());
	}
	
	private static String getUtcTime() {
		Date dt = new Date();// 如果不需要格式,可直接用dt,dt就是当前系统时间
	 	DateFormat df = new SimpleDateFormat("yyyyMMddhhmm");// 设置显示格式
		String nowTime = df.format(dt);
		long dd = (long) 0;
		try {
		    dd = df.parse(nowTime).getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(dd);
	}
	
}
