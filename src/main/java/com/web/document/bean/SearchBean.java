package com.web.document.bean;

import java.util.Date;

public class SearchBean {
	private int type=1;//搜索的类型 1搜索 2插件
	private String must;//必须包含
	private String arbitrarily;//任意字符
	private String not;//不包含字符
	private Integer searchSource=1;//1:全文 2:标题
	private Integer sort=1;//排序方式 1:时间 2:权重
	private String fromSource;//信息来源 新闻网
	private Date startTime;//开始的时间
	private Date endTime;//结束时间
	private int pageSize=10;//返回的记录数 
	private int pageId=1;//返回页数
	private int userId=0;//用户的id
	private boolean isHigh=false;//是否高亮
	
	private boolean fullContent;//是否显示正文 true:显示这正文
	private String strType="";//搜索新闻的来源 默认新闻库 risk:审核通过
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getMust() {
		return must;
	}
	public void setMust(String must) {
		this.must = must;
	}
	public String getArbitrarily() {
		return arbitrarily;
	}
	public void setArbitrarily(String arbitrarily) {
		this.arbitrarily = arbitrarily;
	}
	public String getNot() {
		return not;
	}
	public void setNot(String not) {
		this.not = not;
	}
	public Integer getSearchSource() {
		return searchSource;
	}
	public void setSearchSource(Integer searchSource) {
		this.searchSource = searchSource;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getFromSource() {
		return fromSource;
	}
	public void setFromSource(String fromSource) {
		this.fromSource = fromSource;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageId() {
		return pageId;
	}
	public void setPageId(int pageId) {
		this.pageId = pageId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public boolean isHigh() {
		return isHigh;
	}
	public void setHigh(boolean isHigh) {
		this.isHigh = isHigh;
	}
	public boolean isFullContent() {
		return fullContent;
	}
	public void setFullContent(boolean fullContent) {
		this.fullContent = fullContent;
	}
	public String getStrType() {
		return strType;
	}
	public void setStrType(String strType) {
		this.strType = strType;
	}
	/**
	 * 返回字符串
	 */
	public String toString(){
		return "must="+must+
				",arbitrarily="+arbitrarily+
				",not="+not+
				",searchSource="+searchSource+
				",strType="+strType+
				",userId="+userId+
				",sort="+sort+
				",fromSource="+fromSource+
				",pageId="+pageId+
				",pageSize="+pageSize;
	}
}
