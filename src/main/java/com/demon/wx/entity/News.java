package com.demon.wx.entity;

import java.util.List;
import java.util.Map;

import com.demon.wx.common.MessageType;

public class News extends BaseMessage {

	/** 图文消息个数，限制为10条以内 */
	private Integer ArticleCount;
	
	/** 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应 */ 
	private List<Article> Articles;

	
	public News() {
		super();
		this.MsgType = MessageType.NEWS.getType();
	}
	
	public News(Map<String, String> map) {
		super(map);
		this.MsgType = MessageType.NEWS.getType();
	}
	
	public News(Map<String, String> map, Integer acticleCount, List<Article> articles) {
		super(map);
		this.ArticleCount = acticleCount;
		this.Articles = articles;
		this.MsgType = MessageType.NEWS.getType();
	}

	public Integer getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(Integer articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}
	
	
}
