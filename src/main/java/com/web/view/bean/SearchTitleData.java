package com.web.view.bean;

import java.util.List;


public class SearchTitleData {

	private Integer mid;
	private String mname;
	private List<SearchRiskData> list;
	public Integer getMid() {
		return mid;
	}
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public List<SearchRiskData> getList() {
		return list;
	}
	public void setList(List<SearchRiskData> list) {
		this.list = list;
	}
	
	
}
