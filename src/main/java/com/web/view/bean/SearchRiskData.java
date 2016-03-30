package com.web.view.bean;

import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.google.gson.Gson;

public class SearchRiskData {
	private Long count;
	private List<Risk> data;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<Risk> getData() {
		return data;
	}

	public SearchRiskData(Long count, List<Risk> data) {
		super();
		this.count = count;
		this.data = data;
	}

	public void setData(List<Risk> data) {
		this.data = data;
	}

	public String toJsonString(){
		Gson gson=new Gson();
		return gson.toJson(this);
	}
	
	public SearchRiskData(){}
	
}
