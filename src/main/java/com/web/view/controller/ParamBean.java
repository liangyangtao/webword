package com.web.view.controller;

public class ParamBean {

	private String crawlIds;

	private int pageIndex = 1;

	private String searchWords;

	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getSearchWords() {
		return searchWords;
	}

	public void setSearchWords(String searchWords) {
		this.searchWords = searchWords;
	}

	public String getCrawlIds() {
		return crawlIds;
	}

	public void setCrawlIds(String crawlIds) {
		this.crawlIds = crawlIds;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

}
