package com.lucene.entity.doc;

import java.util.ArrayList;
import java.util.List;

public class Crawl {

	/**
	 * ID
	 */
	private int crawlId;

	/**
	 * 网址ID
	 */
	private int websiteId;

	/**
	 * 抓取标题
	 */
	private String crawlTitle;

	/**
	 * 简介
	 */
	private String crawlBrief;

	/**
	 * 文本内容
	 */
	private String text;

	/**
	 * 原始文本内容
	 */
//	private String originalText;

	/**
	 * 浏览量
	 */
	private int crawlViews;

	/**
	 * 网址名称
	 */
	private String webname;

	/**
	 * 网址URL
	 */
	private String url;

	/**
	 * 是否文件索引
	 */
	private boolean fileIndex;

	/**
	 * 是否抓取任务队列中
	 */
	private boolean task;

	/**
	 * 新闻时间
	 */
	private long newsTime;

	/**
	 * 抓取时间
	 */
	private long crawlTime;
	/**
	 * 数据来源  1:抓取数据
	 */
	private int datafrom;
	/**
	 * 数据分类  
	 */
	private int dataclass;
	/**
	 * 关键词
	 */
	private List<Keyword> keywords = new ArrayList<Keyword>();
	//栏目的ID
	private int categoryId=0;
	
	//栏目的Id
	private String categoryIds;
	
	private String esId="0";
	
	public Crawl() {
	}

	/**
	 * 
	 * @param keyword
	 * @return
	 */
	public boolean addKeyword(Keyword keyword) {
		return keywords.add(keyword);
	}

	public int getDataclass() {
		return dataclass;
	}

	public void setDataclass(int dataclass) {
		this.dataclass = dataclass;
	}

	public int getDatafrom() {
		return datafrom;
	}

	public void setDatafrom(int datafrom) {
		this.datafrom = datafrom;
	}

	public int getCrawlId() {
		return crawlId;
	}

	public void setCrawlId(int crawlId) {
		this.crawlId = crawlId;
	}

	public int getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(int websiteId) {
		this.websiteId = websiteId;
	}

	public String getCrawlTitle() {
		return crawlTitle;
	}

	public void setCrawlTitle(String crawlTitle) {
		this.crawlTitle = crawlTitle;
	}

	public String getCrawlBrief() {
		return crawlBrief;
	}

	public void setCrawlBrief(String crawlBrief) {
		this.crawlBrief = crawlBrief;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

//	public String getOriginalText() {
//		return originalText;
//	}
//
//	public void setOriginalText(String originalText) {
//		this.originalText = originalText;
//	}

	public int getCrawlViews() {
		return crawlViews;
	}

	public void setCrawlViews(int crawlViews) {
		this.crawlViews = crawlViews;
	}

	public String getWebname() {
		return webname;
	}

	public void setWebname(String webname) {
		this.webname = webname;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isFileIndex() {
		return fileIndex;
	}

	public void setFileIndex(boolean fileIndex) {
		this.fileIndex = fileIndex;
	}

	public boolean isTask() {
		return task;
	}

	public void setTask(boolean task) {
		this.task = task;
	}

	public long getNewsTime() {
		return newsTime;
	}

	public void setNewsTime(long newsTime) {
		this.newsTime = newsTime;
	}

	public long getCrawlTime() {
		return crawlTime;
	}

	public void setCrawlTime(long crawlTime) {
		this.crawlTime = crawlTime;
	}

	public List<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String getEsId() {
		return esId;
	}

	public void setEsId(String esId) {
		this.esId = esId;
	}

}
