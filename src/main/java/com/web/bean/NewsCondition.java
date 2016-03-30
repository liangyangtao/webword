package com.web.bean;

import java.util.List;

public class NewsCondition {

	/**
	 * 必须
	 */
	private String mustCrawlId;
	/**
	 * 不能
	 */
	private String mustNotCrawlId;

	/**
	 * 可以
	 */
	private String shouldCrawlId;

	/**
	 * 必须被包含在正文中的词语
	 */
	private String mustContentWords;

	/**
	 * 不能被包含在正文中的词语
	 */
	private String mustNotContentWords;

	/**
	 * 可以出现在正文中的词语
	 */
	private String shouldContentWords;

	/**
	 * 标题中必须出现的词语
	 */
	private String mustTitleWords;

	/**
	 * 标题中不能出现的词语
	 */
	private String mustNotTitleWords;

	/**
	 * 标题中可以出现的词语
	 */
	private String shouldTitleWords;

	/**
	 * 必须属于的标签
	 */
	private String mustTagNames;

	/**
	 * 不能被包含的标签
	 */
	private String mustNotTagNames;

	/**
	 * 可以出现的标签
	 */
	private String shouldTagNames;

	/**
	 * 必须的关键词
	 */
	private String mustKeywords;

	/**
	 * 不能出现的关键词
	 */
	private String mustNotKeywords;

	/**
	 * 可以出现的关键词
	 */
	private String shouldKeywords;

	/**
	 * 搜索必须从这些网站来的新闻
	 */
	private String mustWebNames;

	/**
	 * 搜索不能从这些网站来的新闻
	 */
	private String mustNotWebNames;

	/**
	 * 搜索可以从这些网站来的新闻
	 */
	private String shouldWebNames;

	/**
	 * 搜索必须从这些网站来的新闻
	 */
	private String mustSectionNames;

	/**
	 * 搜索不能从这些板块来的新闻
	 */
	private String mustNotSectionNames;

	/**
	 * 搜索可以从这些板块来的新闻
	 */
	private String shouldSectionNames;

	/**
	 * 必须属于的扩展标签
	 */
	private String mustExtraTags;

	/**
	 * 不能属于的扩展标签
	 */
	private String mustNotExtraTags;

	/**
	 * 可以属于的扩展标签
	 */
	private String shouldExtraTags;

	/**
	 * 来源网址
	 */
	private String websiteIDs;

	/**
	 * 是否允许extraTag为空
	 */
	private String extraTagNull;

	/**
	 * 图片是否为空
	 */
	private String picUrlNull;

	/**
	 * 必须包含的区域
	 */
	private String mustRegion;

	/**
	 * 不能包含的区域
	 */
	private String mustNotRegion;

	/**
	 * 可以包含的区域
	 */
	private String shouldRegion;

	public String getMustCrawlId() {
		return mustCrawlId;
	}

	public void setMustCrawlId(String mustCrawlId) {
		this.mustCrawlId = mustCrawlId;
	}

	public String getMustNotCrawlId() {
		return mustNotCrawlId;
	}

	public void setMustNotCrawlId(String mustNotCrawlId) {
		this.mustNotCrawlId = mustNotCrawlId;
	}

	public String getShouldCrawlId() {
		return shouldCrawlId;
	}

	public void setShouldCrawlId(String shouldCrawlId) {
		this.shouldCrawlId = shouldCrawlId;
	}

	public String getMustContentWords() {
		return mustContentWords;
	}

	public void setMustContentWords(String mustContentWords) {
		this.mustContentWords = mustContentWords;
	}

	public String getMustNotContentWords() {
		return mustNotContentWords;
	}

	public void setMustNotContentWords(String mustNotContentWords) {
		this.mustNotContentWords = mustNotContentWords;
	}

	public String getShouldContentWords() {
		return shouldContentWords;
	}

	public void setShouldContentWords(String shouldContentWords) {
		this.shouldContentWords = shouldContentWords;
	}

	public String getMustTitleWords() {
		return mustTitleWords;
	}

	public void setMustTitleWords(String mustTitleWords) {
		this.mustTitleWords = mustTitleWords;
	}

	public String getMustNotTitleWords() {
		return mustNotTitleWords;
	}

	public void setMustNotTitleWords(String mustNotTitleWords) {
		this.mustNotTitleWords = mustNotTitleWords;
	}

	public String getShouldTitleWords() {
		return shouldTitleWords;
	}

	public void setShouldTitleWords(String shouldTitleWords) {
		this.shouldTitleWords = shouldTitleWords;
	}

	public String getMustTagNames() {
		return mustTagNames;
	}

	public void setMustTagNames(String mustTagNames) {
		this.mustTagNames = mustTagNames;
	}

	public String getMustNotTagNames() {
		return mustNotTagNames;
	}

	public void setMustNotTagNames(String mustNotTagNames) {
		this.mustNotTagNames = mustNotTagNames;
	}

	public String getShouldTagNames() {
		return shouldTagNames;
	}

	public void setShouldTagNames(String shouldTagNames) {
		this.shouldTagNames = shouldTagNames;
	}

	public String getMustKeywords() {
		return mustKeywords;
	}

	public void setMustKeywords(String mustKeywords) {
		this.mustKeywords = mustKeywords;
	}

	public String getMustNotKeywords() {
		return mustNotKeywords;
	}

	public void setMustNotKeywords(String mustNotKeywords) {
		this.mustNotKeywords = mustNotKeywords;
	}

	public String getShouldKeywords() {
		return shouldKeywords;
	}

	public void setShouldKeywords(String shouldKeywords) {
		this.shouldKeywords = shouldKeywords;
	}

	public String getMustWebNames() {
		return mustWebNames;
	}

	public void setMustWebNames(String mustWebNames) {
		this.mustWebNames = mustWebNames;
	}

	public String getMustNotWebNames() {
		return mustNotWebNames;
	}

	public void setMustNotWebNames(String mustNotWebNames) {
		this.mustNotWebNames = mustNotWebNames;
	}

	public String getShouldWebNames() {
		return shouldWebNames;
	}

	public void setShouldWebNames(String shouldWebNames) {
		this.shouldWebNames = shouldWebNames;
	}

	public String getMustSectionNames() {
		return mustSectionNames;
	}

	public void setMustSectionNames(String mustSectionNames) {
		this.mustSectionNames = mustSectionNames;
	}

	public String getMustNotSectionNames() {
		return mustNotSectionNames;
	}

	public void setMustNotSectionNames(String mustNotSectionNames) {
		this.mustNotSectionNames = mustNotSectionNames;
	}

	public String getShouldSectionNames() {
		return shouldSectionNames;
	}

	public void setShouldSectionNames(String shouldSectionNames) {
		this.shouldSectionNames = shouldSectionNames;
	}

	public String getMustExtraTags() {
		return mustExtraTags;
	}

	public void setMustExtraTags(String mustExtraTags) {
		this.mustExtraTags = mustExtraTags;
	}

	public String getMustNotExtraTags() {
		return mustNotExtraTags;
	}

	public void setMustNotExtraTags(String mustNotExtraTags) {
		this.mustNotExtraTags = mustNotExtraTags;
	}

	public String getShouldExtraTags() {
		return shouldExtraTags;
	}

	public void setShouldExtraTags(String shouldExtraTags) {
		this.shouldExtraTags = shouldExtraTags;
	}


	public String getWebsiteIDs() {
		return websiteIDs;
	}

	public void setWebsiteIDs(String websiteIDs) {
		this.websiteIDs = websiteIDs;
	}

	public String getExtraTagNull() {
		return extraTagNull;
	}

	public void setExtraTagNull(String extraTagNull) {
		this.extraTagNull = extraTagNull;
	}

	public String getPicUrlNull() {
		return picUrlNull;
	}

	public void setPicUrlNull(String picUrlNull) {
		this.picUrlNull = picUrlNull;
	}

	public String getMustRegion() {
		return mustRegion;
	}

	public void setMustRegion(String mustRegion) {
		this.mustRegion = mustRegion;
	}

	public String getMustNotRegion() {
		return mustNotRegion;
	}

	public void setMustNotRegion(String mustNotRegion) {
		this.mustNotRegion = mustNotRegion;
	}

	public String getShouldRegion() {
		return shouldRegion;
	}

	public void setShouldRegion(String shouldRegion) {
		this.shouldRegion = shouldRegion;
	}

}
