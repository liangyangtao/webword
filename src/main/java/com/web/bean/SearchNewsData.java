package com.web.bean;

import java.util.ArrayList;
import java.util.List;

import com.web.view.bean.RiskCut;

public class SearchNewsData {
	
	private Long count;
	private List<RiskCut> data = new ArrayList<RiskCut>();
	private int curNumber;
	
	public int getCurNumber() {
		return curNumber;
	}
	public void setCurNumber(int curNumber) {
		this.curNumber = curNumber;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public List<RiskCut> getData() {
		return data;
	}
	public void setData(List<RiskCut> data) {
		this.data = data;
	}
	
	

}
