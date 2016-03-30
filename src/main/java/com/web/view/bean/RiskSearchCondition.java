package com.web.view.bean;

import java.util.ArrayList;
import java.util.List;



import com.google.gson.Gson;

public class RiskSearchCondition {
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
	private List<String> shouldKeywords ;
	
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
	 * 简介中必须包含相关搜索词
	 */
	private List<String> mustBriefWords;
	
	/**
	 * 简介中不能包含相关词
	 */
	private List<String> mustNotBriefWords;
	
	/**
	 * 简介中可以包含相关词
	 */
	private List<String> shouldBriefWords;
	
	/**
	 * 新词中必须包含制定词
	 */
	private List<String> mustNewWords;
	
	/**
	 * 新词中不能包含指定词
	 */
	private List<String> mustNotNewWords;
	
	/**
	 * 新词中可以包含的词
	 */
	private List<String> shouldNewWords;
	
	/**
	 * 必须属于的栏目
	 */
	private List<String> mustCategories;
	
	/**
	 * 不能属于的栏目
	 */
	private List<String> mustNotCategories;
	
	/**
	 * 可以属于的栏目
	 */
	private List<String> shouldCategories;
	
	/**
	 * 审查人
	 */
	private String examiner;
	
	/**
	 * 存储时间（开始时间）
	 */
	private Long startStoreTime;
	
	/**
	 * 存储时间（结束时间）
	 */
	private Long endStoreTime;
	
	/**
	 * 是否重复
	 */
	private String repetitive="false";
	
	/**
	 * 栏目ID
	 */
	private List<Integer> categoryIds;
	
	/**
	 * 来源网址
	 */
	private List<Integer> websiteIDs;
	



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
	

	@Override
	public String toString() {
		return "RiskSearchCondition [mustContentWords=" + mustContentWords + ", mustNotContentWords="
				+ mustNotContentWords + ", shouldContentWords=" + shouldContentWords + ", mustTitleWords="
				+ mustTitleWords + ", mustNotTitleWords=" + mustNotTitleWords + ", shouldTitleWords="
				+ shouldTitleWords + ", mustTagNames=" + mustTagNames + ", mustNotTagNames=" + mustNotTagNames
				+ ", shouldTagNames=" + shouldTagNames + ", mustKeywords=" + mustKeywords + ", mustNotKeywords="
				+ mustNotKeywords + ", shouldKeywords=" + shouldKeywords + ", mustWebNames=" + mustWebNames
				+ ", mustNotWebNames=" + mustNotWebNames + ", shouldWebNames=" + shouldWebNames + ", mustSectionNames="
				+ mustSectionNames + ", mustNotSectionNames=" + mustNotSectionNames + ", shouldSectionNames="
				+ shouldSectionNames + ", mustBriefWords=" + mustBriefWords + ", mustNotBriefWords="
				+ mustNotBriefWords + ", shouldBriefWords=" + shouldBriefWords + ", mustNewWords=" + mustNewWords
				+ ", mustNotNewWords=" + mustNotNewWords + ", shouldNewWords=" + shouldNewWords + ", mustCategories="
				+ mustCategories + ", mustNotCategories=" + mustNotCategories + ", shouldCategories="
				+ shouldCategories + ", examiner=" + examiner + ", startStoreTime=" + startStoreTime
				+ ", endStoreTime=" + endStoreTime + ", repetitive=" + repetitive + ", categoryIds=" + categoryIds
				+ ", websiteIDs=" + websiteIDs + "]";
	}


	public List<String> getMustBriefWords() {
		return mustBriefWords;
	}


	public void setMustBriefWords(List<String> mustBriefWords) {
		this.mustBriefWords = mustBriefWords;
	}


	public List<String> getMustNotBriefWords() {
		return mustNotBriefWords;
	}


	public void setMustNotBriefWords(List<String> mustNotBriefWords) {
		this.mustNotBriefWords = mustNotBriefWords;
	}


	public List<String> getShouldBriefWords() {
		return shouldBriefWords;
	}


	public void setShouldBriefWords(List<String> shouldBriefWords) {
		this.shouldBriefWords = shouldBriefWords;
	}


	public List<String> getMustNewWords() {
		return mustNewWords;
	}


	public void setMustNewWords(List<String> mustNewWords) {
		this.mustNewWords = mustNewWords;
	}


	public List<String> getMustNotNewWords() {
		return mustNotNewWords;
	}


	public void setMustNotNewWords(List<String> mustNotNewWords) {
		this.mustNotNewWords = mustNotNewWords;
	}


	public List<String> getShouldNewWords() {
		return shouldNewWords;
	}


	public void setShouldNewWords(List<String> shouldNewWords) {
		this.shouldNewWords = shouldNewWords;
	}


	public List<String> getMustCategories() {
		return mustCategories;
	}


	public void setMustCategories(List<String> mustCategories) {
		this.mustCategories = mustCategories;
	}


	public List<String> getMustNotCategories() {
		return mustNotCategories;
	}


	public void setMustNotCategories(List<String> mustNotCategories) {
		this.mustNotCategories = mustNotCategories;
	}


	public List<String> getShouldCategories() {
		return shouldCategories;
	}


	public void setShouldCategories(List<String> shouldCategories) {
		this.shouldCategories = shouldCategories;
	}


	public String getExaminer() {
		return examiner;
	}


	public void setExaminer(String examiner) {
		this.examiner = examiner;
	}


	public Long getStartStoreTime() {
		return startStoreTime;
	}


	public void setStartStoreTime(Long startStoreTime) {
		this.startStoreTime = startStoreTime;
	}


	public Long getEndStoreTime() {
		return endStoreTime;
	}


	public void setEndStoreTime(Long endStoreTime) {
		this.endStoreTime = endStoreTime;
	}


	public String getRepetitive() {
		return repetitive;
	}


	public void setRepetitive(String repetitive) {
		this.repetitive = repetitive;
	}


	public List<Integer> getCategoryIds() {
		return categoryIds;
	}


	public void setCategoryIds(List<Integer> categoryIds) {
		this.categoryIds = categoryIds;
	}


	public List<Integer> getWebsiteIDs() {
		return websiteIDs;
	}


	public void setWebsiteIDs(List<Integer> websiteIDs) {
		this.websiteIDs = websiteIDs;
	}

	

	
	public String toJsonString(){
		Gson gson=new Gson();
		return gson.toJson(this);
	}
}
