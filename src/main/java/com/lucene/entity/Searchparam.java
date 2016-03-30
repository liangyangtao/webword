package com.lucene.entity;

public class Searchparam {
	private int userId;
	private String record;
	private String bankdatarecord;
	private String articletype;
	private int pageSize;
	private int currentPage;
	
	public String getBankdatarecord() {
		return bankdatarecord;
	}
	public void setBankdatarecord(String bankdatarecord) {
		this.bankdatarecord = bankdatarecord;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	public String getArticletype() {
		return articletype;
	}
	public void setArticletype(String articletype) {
		this.articletype = articletype;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
}
