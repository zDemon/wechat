package com.demon.wx.utils;

import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.demon.wx.common.EventType;
import com.demon.wx.common.MessageType;
import com.demon.wx.entity.Article;
import com.demon.wx.entity.BaseMessage;
import com.demon.wx.entity.Image;
import com.demon.wx.entity.Music;
import com.demon.wx.entity.News;
import com.demon.wx.entity.Text;
import com.demon.wx.entity.Video;
import com.demon.wx.entity.Voice;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

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
		MessageType msgType = MessageType.valueOf(MessageType.class, map.get("MsgType").toUpperCase());
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
		String toUserName = map.get("FromUserName");
		String fromUserName = map.get("ToUserName");
		switch(content) {
		case "文本" :
			int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			String textContent = "\n(这是来自遥远的Demon回复的消息)";
			if(hourOfDay < 20) {
				textContent = "这么晚了你该睡觉了(,,•́.•̀,,)" + textContent;
			} else if(hourOfDay < 6) {
				textContent = "上午好(•̀ω•́)✧" + textContent;
			} else if(hourOfDay < 14) {
				textContent = "中午好(•̀ω•́)✧" + textContent;
			} else if(hourOfDay < 18) {
				textContent = "下午好(•̀ω•́)✧" + textContent;
			} else {
				textContent = "晚上好(•̀ω•́)✧" + textContent;
			}
			Text text = new Text(map, textContent);
			responseMsg = text.toXML();
			break;
		case "图片" :
			String mediaId = "_2vZB3Em2BqVrUFgs9OEgjrxon_nD6jUO6AnTyWHhyziGGZihPBx9gBDbQI02tKr";
			Image image = new Image(map, mediaId);
			responseMsg = image.toXML();
			break;
		case "音乐" : 
			Music music = new Music(map);
			music.setTitle("泠鸢yousa - 黑猫.mp3");
			music.setDescription("泠鸢yousa填词 黑猫");
			music.setMusicUrl("http://demon.vip.natapp.cn/material/yousa_heimao.mp3");
			music.setHQMusicUrl("http://demon.vip.natapp.cn/material/yousa_heimao.mp3");
			responseMsg = music.toXML();
			break;
		case "语音" :
			String voiceMediaId = "tRtEUlibZWIqzjjxavKPGSKO8pEkXMqS8yrqY1wMzrMr74briEESbhyfDAGWYpKq"; // 薛之谦 - 演员.mp3
			Voice voice = new Voice(map, voiceMediaId);
			responseMsg = voice.toXML();
			break;
		case "视频" :
			String videoMediaId = "HslvNqmBkHkNTYyxDnDNe5PH7DMQPcsqsH4-l9e6L5DdyhASZUMAsgyorjpKP_qR"; // RememberTheName.mp4
			Video video = new Video(map, videoMediaId);
			video.setTile("短裙全明星Remember the name");
			video.setDescription("B站鬼畜全明星av5337457→_→");
			responseMsg = video.toXML();
			break;
		case "图文" :
			News news = new News(map);
			List<Article> articles = new ArrayList<Article>();
			Article article0 = new Article();
			article0.setTitle("【王司徒X傅园慧】王司徒传授给傅爷的最后一课！【逗比神功续集】");
			article0.setDescription("up主 理科生天依酱");
			article0.setPicUrl("http://demon.vip.natapp.cn/material/situ_fuye.jpg");
			article0.setUrl("http://www.bilibili.com/video/av5746638/");
			articles.add(article0);
			
			Article article1 = new Article();
			article1.setTitle("【王司徒单曲】入戏太深");
			article1.setDescription("up主 飞机君");
			article1.setPicUrl("http://demon.vip.natapp.cn/material/ruxitaishen.jpg");
			article1.setUrl("http://www.bilibili.com/video/av3078651/");
			articles.add(article1);
			
			Article article2 = new Article();
			article2.setTitle("短裙全明星Nobody4 Plus");
			article2.setDescription("up主 女孩为何穿短裙");
			article2.setPicUrl("http://demon.vip.natapp.cn/material/Nobody4Plus.jpg");
			article2.setUrl("http://www.bilibili.com/video/av4995866/");
			articles.add(article2);
			
			news.setArticleCount(articles.size());
			news.setArticles(articles);
			
			responseMsg = news.toXML();
			break;
		default :
			Text textMsg = new Text(map, "你这消息我没法接(๑•́₃•̀๑)");
			responseMsg = textMsg.toXML();
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
		Image image = new Image(map, "srHo_d1OiKBMl_F0kmsrcI7gpBXwFO5qSVyMS1pV2SbJDe3OxlKtM-ZuGupH6sEN");
		return image.toXML();
	}
	
	private static String handleEventMessage(Map<String, String> map) {
		String responseMsg = "";
		EventType event = EventType.valueOf(EventType.class, map.get("Event").toUpperCase());
		switch(event) {
		case SUBSCRIBE : 
			String content = "welcaome to Demon's official account(ฅ>ω<*ฅ)\n请回复如下关键词获取对应信息：\n文本\n图片\n语音\n视频\n音乐\n图文";
			Text text = new Text(map, content);
			responseMsg = text.toXML();
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
	
	
	public static String msgToXML(BaseMessage msg) {
		xstream.alias("xml", msg.getClass());
		if(msg instanceof News) {
			xstream.alias("item", new Article().getClass());
		}
		return xstream.toXML(msg);
	}
	
	/**
	 * 扩展xstream，使其支持CDATA块
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				boolean cdata = true;
				
				@SuppressWarnings("rawtypes")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}
				
				protected void writeText(QuickWriter writer, String text) {
					if(cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					}
				}
				
			};
		}
	});
	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("FromUserName", "from");
		map.put("ToUserName", "to");
		News news = new News(map);
		List<Article> articles = new ArrayList<Article>();
		Article article0 = new Article();
		article0.setTitle("【王司徒X傅园慧】王司徒传授给傅爷的最后一课！【逗比神功续集】");
		article0.setDescription("up主 理科生天依酱");
		article0.setPicUrl("http://demon.vip.natapp.cn/material/situ_fuye.jpg");
		article0.setUrl("http://www.bilibili.com/video/av5746638/");
		articles.add(article0);
		
		Article article1 = new Article();
		article1.setTitle("【王司徒单曲】入戏太深");
		article1.setDescription("up主 飞机君");
		article1.setPicUrl("http://demon.vip.natapp.cn/material/ruxitaishen.jpg");
		article1.setUrl("http://www.bilibili.com/video/av3078651/");
		articles.add(article1);
		
		news.setArticleCount(articles.size());
		news.setArticles(articles);
		
		String xml = msgToXML(news);
		System.out.println(xml);
	}
	
}
