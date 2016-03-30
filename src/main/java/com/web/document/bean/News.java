package com.web.document.bean;

public class News {
	private int crawl_id;
	private String url;
	private String title;
	private String content;
	private long newsDate;
	private long crawlDate;
	private String webName;
	private String webSectionName;
	private String p_url;
	private String tagName;
	private String keyWords;
	private String categoryId;// 栏目的Id
	private String esId = "0";// 栏目的Id
	/**
	 * lyt add 2016年2月25日10:36:07
	 */
	private String picUrl;// 新闻内容中的图片 ，多个用英文逗号隔开
	private String region; // 新闻所属地区， 多个用空格隔开

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * lyt add end
	 * 
	 */

	public int getCrawl_id() {
		return crawl_id;
	}

	public void setCrawl_id(int crawl_id) {
		this.crawl_id = crawl_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(long newsDate) {
		this.newsDate = newsDate;
	}

	public long getCrawlDate() {
		return crawlDate;
	}

	public void setCrawlDate(long crawlDate) {
		this.crawlDate = crawlDate;
	}

	public String getWebName() {
		return webName;
	}

	public void setWebName(String webName) {
		this.webName = webName;
	}

	public String getWebSectionName() {
		return webSectionName;
	}

	public void setWebSectionName(String webSectionName) {
		this.webSectionName = webSectionName;
	}

	public String getP_url() {
		return p_url;
	}

	public void setP_url(String p_url) {
		this.p_url = p_url;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getEsId() {
		return esId;
	}

	public void setEsId(String esId) {
		this.esId = esId;
	}

}
