package com.web.bean;

public class ParamsBean {
	
	private int curNumber=1;
	private int mid;
	private String keyword;
	private String mids;
	private int userId;
	private String station;//岗位
	private String responsibility;//职责
	private String interest;//关注的行业
	private int startPage=1;//开始的页码
	private int sorttype=0;//按栏目搜索排序方式
	
	
	
	public int getSorttype() {
		return sorttype;
	}
	public void setSorttype(int sorttype) {
		this.sorttype = sorttype;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getResponsibility() {
		return responsibility;
	}
	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getCurNumber() {
		return curNumber;
	}
	public void setCurNumber(int curNumber) {
		this.curNumber = curNumber;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getMids() {
		return mids;
	}
	public void setMids(String mids) {
		this.mids = mids;
	}
	

}
