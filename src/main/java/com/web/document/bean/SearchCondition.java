package com.web.document.bean;


import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class SearchCondition {
	
	private String repetitive="false";
	/**
	 * 必须被包含在正文中的词语
	 */
	private List<String> mustContentWords = new ArrayList<String>();

	public void addmustContentWords(List<String> list){
		
		this.mustContentWords.addAll(list);
	}
	
	/**
	 * 不能被包含在正文中的词语
	 */
	private List<String> mustNotContentWords = new ArrayList<String>();
	
	public void addmustNotContentWords(List<String> list){
		this.mustNotContentWords.addAll(list);
	}
	
	/**
	 * 可以出现在正文中的词语
	 */
	private List<String> shouldContentWords = new ArrayList<String>();
	public void addshouldContentWords(List<String> list){
		this.shouldContentWords.addAll(list);
	}
	
	/**
	 * 标题中必须出现的词语
	 */
	private List<String> mustTitleWords = new ArrayList<String>() ;
	
	public void addmustTitleWords(List<String> list){
		this.mustTitleWords.addAll(list);
	}
	/**
	 * 标题中不能出现的词语
	 */
	private List<String> mustNotTitleWords  = new ArrayList<String>();
	public void addmustNotTitleWords(List<String> list){
		this.mustNotTitleWords.addAll(list);
	}
	
	/**
	 * 标题中可以出现的词语
	 */
	private List<String> shouldTitleWords = new ArrayList<String>();
	public void addshouldTitleWords(List<String> list){
		this.shouldTitleWords.addAll(list);
	}
	
	/**
	 * 必须属于的标签
	 */
	private List<String> mustTagNames = new ArrayList<String>();
	public void addmustTagNames(List<String> list){
		this.mustTagNames.addAll(list);
	}
	
	
	/**
	 * 不能被包含的标签
	 */
	private List<String> mustNotTagNames = new ArrayList<String>();
	public void addmustNotTagNames(List<String> list){
		this.mustNotTagNames.addAll(list);
	}
	
	
	/**
	 * 可以出现的标签
	 */
	private List<String> shouldTagNames = new ArrayList<String>();
	public void addshouldTagNames(List<String> list){
		this.shouldTagNames.addAll(list);
	}
	
	/**
	 * 来源网址
	 */
	private List<Integer> websiteIDs = new ArrayList<Integer>();
	public void addwebsiteIDs(List<Integer> list){
		this.websiteIDs.addAll(list);
	}
	
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

	public List<Integer> getWebsiteIDs() {
		return websiteIDs;
	}

	public void setWebsiteIDs(List<Integer> websiteIDs) {
		this.websiteIDs = websiteIDs;
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

	@Override
	public String toString() {
		return "SearchCondition [mustContentWords=" + mustContentWords + ", mustNotContentWords=" + mustNotContentWords
				+ ", shouldContentWords=" + shouldContentWords + ", mustTitleWords=" + mustTitleWords
				+ ", mustNotTitleWords=" + mustNotTitleWords + ", shouldTitleWords=" + shouldTitleWords
				+ ", mustTagNames=" + mustTagNames + ", mustNotTagNames=" + mustNotTagNames + ", shouldTagNames="
				+ shouldTagNames + ", websiteIDs=" + websiteIDs + "]";
	}
	
	public String toJsonString(){
		Gson gson=new Gson();
		return gson.toJson(this);
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

	public String getRepetitive() {
		return repetitive;
	}

	public void setRepetitive(String repetitive) {
		this.repetitive = repetitive;
	}


	
}
