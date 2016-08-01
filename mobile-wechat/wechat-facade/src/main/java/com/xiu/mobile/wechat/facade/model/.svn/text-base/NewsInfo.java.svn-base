package com.xiu.mobile.wechat.facade.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author wenxiaozhuo
 * @date   20140530
 * 图文消息
 *
 */
public class NewsInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 图文数量
	 */
	private int ArticleCount;
	/**
	 * 图文消息
	 */
	private List<ArticleInfo> articles;

	public NewsInfo() {
	}

	public NewsInfo(int ArticleCount, List<ArticleInfo> articles) {
		this.ArticleCount = ArticleCount;
		this.articles = articles;
	}

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<ArticleInfo> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticleInfo> articles) {
		this.articles = articles;
	}

	@Override
	public String toString() {
		return "NewsInfo [ArticleCount=" + ArticleCount + ", articles=" + articles + "]";
	}

}
