package com.web.bean;

import java.util.List;

import com.google.gson.Gson;

public class SearchDocumentData {
	
	private Long count=0L;
	private List<Document> data;
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public List<Document> getData() {
		return data;
	}
	public void setData(List<Document> data) {
		this.data = data;
	}
	
	public String toJsonString(){
		Gson gson=new Gson();
		return gson.toJson(this);
	}
	public SearchDocumentData(Long count, List<Document> data) {
		super();
		this.count = count;
		this.data = data;
	}
	
	public SearchDocumentData(){}

}
