package com.web.bean;

import java.util.List;

public class NewsSearchCondition {

	/**
	 * 必须
	 */
	private List<String> mustCrawlId;
	/**
	 * 不能
	 */
	private List<String> mustNotCrawlId;

	/**
	 * 可以
	 */
	private List<String> shouldCrawlId;

	/**
	 * 必须被包含在正文中的词语
	 */
	private List<String> mustContentWords;

	/**
	 * 不能被包含在正文中的词语
	 */
	private List<String> mustNotContentWords;

	/**
	 * 可以出现在正文中的词语
	 */
	private List<String> shouldContentWords;
	
	private List<String> termsMustContent;

	/**
	 * 标题中必须出现的词语
	 */
	private List<String> mustTitleWords;

	/**
	 * 标题中不能出现的词语
	 */
	private List<String> mustNotTitleWords;

	/**
	 * 标题中可以出现的词语
	 */
	private List<String> shouldTitleWords;
	
	private List<String> termsMustTitle;

	/**
	 * 必须属于的标签
	 */
	private List<String> mustTagNames;

	/**
	 * 不能被包含的标签
	 */
	private List<String> mustNotTagNames;

	/**
	 * 可以出现的标签
	 */
	private List<String> shouldTagNames;

	/**
	 * 必须的关键词
	 */
	private List<String> mustKeywords;

	/**
	 * 不能出现的关键词
	 */
	private List<String> mustNotKeywords;

	/**
	 * 可以出现的关键词
	 */
	private List<String> shouldKeywords;

	/**
	 * 搜索必须从这些网站来的新闻
	 */
	private List<String> mustWebNames;

	/**
	 * 搜索不能从这些网站来的新闻
	 */
	private List<String> mustNotWebNames;

	/**
	 * 搜索可以从这些网站来的新闻
	 */
	private List<String> shouldWebNames;

	/**
	 * 搜索必须从这些网站来的新闻
	 */
	private List<String> mustSectionNames;

	/**
	 * 搜索不能从这些板块来的新闻
	 */
	private List<String> mustNotSectionNames;

	/**
	 * 搜索可以从这些板块来的新闻
	 */
	private List<String> shouldSectionNames;

	/**
	 * 必须属于的扩展标签
	 */
	private List<String> mustExtraTags;

	/**
	 * 不能属于的扩展标签
	 */
	private List<String> mustNotExtraTags;

	/**
	 * 可以属于的扩展标签
	 */
	private List<String> shouldExtraTags;

	/**
	 * 来源网址
	 */
	private List<Integer> websiteIDs;

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
	private List<String> mustRegion;

	/**
	 * 不能包含的区域
	 */
	private List<String> mustNotRegion;

	/**
	 * 可以包含的区域
	 */
	private List<String> shouldRegion;

	public boolean isEmpty() {
		if (mustCrawlId == null && mustNotCrawlId == null 
				&& shouldCrawlId == null && mustContentWords == null
				&& mustNotContentWords == null && shouldContentWords == null
				&& mustTitleWords == null && mustNotTitleWords == null
				&& shouldTitleWords == null && mustTagNames == null
				&& mustNotTagNames == null && shouldTagNames == null
				&& mustKeywords == null && mustNotKeywords == null
				&& shouldKeywords == null && mustWebNames == null
				&& mustNotWebNames == null && shouldWebNames == null
				&& mustSectionNames == null && mustNotSectionNames == null
				&& shouldSectionNames == null && mustExtraTags == null
				&& mustNotExtraTags == null && shouldExtraTags == null
				&& websiteIDs == null && extraTagNull == null
				&& picUrlNull == null && mustRegion == null
				&& mustNotRegion == null && shouldRegion == null) {
			return true;
		}
		return false;
	}
	
	
	public List<String> getMustCrawlId() {
		return mustCrawlId;
	}

	public void setMustCrawlId(List<String> mustCrawlId) {
		this.mustCrawlId = mustCrawlId;
	}

	public List<String> getMustNotCrawlId() {
		return mustNotCrawlId;
	}

	public void setMustNotCrawlId(List<String> mustNotCrawlId) {
		this.mustNotCrawlId = mustNotCrawlId;
	}

	public List<String> getShouldCrawlId() {
		return shouldCrawlId;
	}

	public void setShouldCrawlId(List<String> shouldCrawlId) {
		this.shouldCrawlId = shouldCrawlId;
	}

	public List<String> getMustContentWords() {
		return mustContentWords;
	}

	public void setMustContentWords(List<String> mustContentWords) {
		this.mustContentWords = mustContentWords;
	}

	public List<String> getMustNotContentWords() {
		return mustNotContentWords;
	}

	public void setMustNotContentWords(List<String> mustNotContentWords) {
		this.mustNotContentWords = mustNotContentWords;
	}

	public List<String> getShouldContentWords() {
		return shouldContentWords;
	}

	public void setShouldContentWords(List<String> shouldContentWords) {
		this.shouldContentWords = shouldContentWords;
	}
	
	public List<String> getTermsMustContent() {
		return termsMustContent;
	}

	public void setTermsMustContent(List<String> termsMustContent) {
		this.termsMustContent = termsMustContent;
	}

	public List<String> getMustTitleWords() {
		return mustTitleWords;
	}

	public void setMustTitleWords(List<String> mustTitleWords) {
		this.mustTitleWords = mustTitleWords;
	}

	public List<String> getMustNotTitleWords() {
		return mustNotTitleWords;
	}

	public void setMustNotTitleWords(List<String> mustNotTitleWords) {
		this.mustNotTitleWords = mustNotTitleWords;
	}

	public List<String> getShouldTitleWords() {
		return shouldTitleWords;
	}

	public void setShouldTitleWords(List<String> shouldTitleWords) {
		this.shouldTitleWords = shouldTitleWords;
	}

	public List<String> getTermsMustTitle() {
		return termsMustTitle;
	}

	public void setTermsMustTitle(List<String> termsMustTitle) {
		this.termsMustTitle = termsMustTitle;
	}

	public List<String> getMustTagNames() {
		return mustTagNames;
	}

	public void setMustTagNames(List<String> mustTagNames) {
		this.mustTagNames = mustTagNames;
	}

	public List<String> getMustNotTagNames() {
		return mustNotTagNames;
	}

	public void setMustNotTagNames(List<String> mustNotTagNames) {
		this.mustNotTagNames = mustNotTagNames;
	}

	public List<String> getShouldTagNames() {
		return shouldTagNames;
	}

	public void setShouldTagNames(List<String> shouldTagNames) {
		this.shouldTagNames = shouldTagNames;
	}

	public List<String> getMustKeywords() {
		return mustKeywords;
	}

	public void setMustKeywords(List<String> mustKeywords) {
		this.mustKeywords = mustKeywords;
	}

	public List<String> getMustNotKeywords() {
		return mustNotKeywords;
	}

	public void setMustNotKeywords(List<String> mustNotKeywords) {
		this.mustNotKeywords = mustNotKeywords;
	}

	public List<String> getShouldKeywords() {
		return shouldKeywords;
	}

	public void setShouldKeywords(List<String> shouldKeywords) {
		this.shouldKeywords = shouldKeywords;
	}

	public List<String> getMustWebNames() {
		return mustWebNames;
	}

	public void setMustWebNames(List<String> mustWebNames) {
		this.mustWebNames = mustWebNames;
	}

	public List<String> getMustNotWebNames() {
		return mustNotWebNames;
	}

	public void setMustNotWebNames(List<String> mustNotWebNames) {
		this.mustNotWebNames = mustNotWebNames;
	}

	public List<String> getShouldWebNames() {
		return shouldWebNames;
	}

	public void setShouldWebNames(List<String> shouldWebNames) {
		this.shouldWebNames = shouldWebNames;
	}

	public List<String> getMustSectionNames() {
		return mustSectionNames;
	}

	public void setMustSectionNames(List<String> mustSectionNames) {
		this.mustSectionNames = mustSectionNames;
	}

	public List<String> getMustNotSectionNames() {
		return mustNotSectionNames;
	}

	public void setMustNotSectionNames(List<String> mustNotSectionNames) {
		this.mustNotSectionNames = mustNotSectionNames;
	}

	public List<String> getShouldSectionNames() {
		return shouldSectionNames;
	}

	public void setShouldSectionNames(List<String> shouldSectionNames) {
		this.shouldSectionNames = shouldSectionNames;
	}

	public List<String> getMustExtraTags() {
		return mustExtraTags;
	}

	public void setMustExtraTags(List<String> mustExtraTags) {
		this.mustExtraTags = mustExtraTags;
	}

	public List<String> getMustNotExtraTags() {
		return mustNotExtraTags;
	}

	public void setMustNotExtraTags(List<String> mustNotExtraTags) {
		this.mustNotExtraTags = mustNotExtraTags;
	}

	public List<String> getShouldExtraTags() {
		return shouldExtraTags;
	}

	public void setShouldExtraTags(List<String> shouldExtraTags) {
		this.shouldExtraTags = shouldExtraTags;
	}

	public List<Integer> getWebsiteIDs() {
		return websiteIDs;
	}

	public void setWebsiteIDs(List<Integer> websiteIDs) {
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

	public List<String> getMustRegion() {
		return mustRegion;
	}

	public void setMustRegion(List<String> mustRegion) {
		this.mustRegion = mustRegion;
	}

	public List<String> getMustNotRegion() {
		return mustNotRegion;
	}

	public void setMustNotRegion(List<String> mustNotRegion) {
		this.mustNotRegion = mustNotRegion;
	}

	public List<String> getShouldRegion() {
		return shouldRegion;
	}

	public void setShouldRegion(List<String> shouldRegion) {
		this.shouldRegion = shouldRegion;
	}

}
