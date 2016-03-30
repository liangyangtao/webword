package com.web.document.bean;

import java.util.ArrayList;
import java.util.List;

public class NewsCrawls {

	private int count;

	private List<News> data = new ArrayList<News>();

	private String crawlIds;

	private int countResult;

	public String getCrawlIds() {
		return crawlIds;
	}

	public void setCrawlIds(String crawlIds) {
		this.crawlIds = crawlIds;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<News> getData() {
		return data;
	}

	public void setData(List<News> data) {
		this.data = data;
	}

	public int getCountResult() {
		return countResult;
	}

	public void setCountResult(int countResult) {
		this.countResult = countResult;
	}

}
