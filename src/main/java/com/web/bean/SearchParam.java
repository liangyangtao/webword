package com.web.bean;


import java.util.List;
import java.util.Map;

/**
 * 参数类 Created by LiuLei on 2015/10/27.
 */
public class SearchParam {

	/**
	 * 起始数 默认0
	 */
	private Integer from;

	/**
	 * 查询数量 默认20
	 */
	private Integer pageSize;

	/**
	 * 排序字段 默认passTime
	 */
	private String orderByField;

	/**
	 * 排序方式 默认DESC
	 */
	private String order;

	/**
	 * 是否显示全文 默认false
	 */
	private boolean fullContent;

	/**
	 * 搜索条件封装
	 */
	private List<JournalSearchCondition> jsonData;


	/**
	 * 置顶信息
	 */
	private List<String> topList;


	public void setFrom(Integer from) {
		this.from = from;
	}


	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderByField() {
		return orderByField;
	}

	public void setOrderByField(String orderByField) {
		this.orderByField = orderByField;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public boolean isFullContent() {
		return fullContent;
	}

	public void setFullContent(boolean fullContent) {
		this.fullContent = fullContent;
	}

	public List<String> getTopList() {
		return topList;
	}

	public void setTopList(List<String> topList) {
		this.topList = topList;
	}

	public List<JournalSearchCondition> getJsonData() {
		return jsonData;
	}


	public void setJsonData(List<JournalSearchCondition> jsonData) {
		this.jsonData = jsonData;
	}


	public Integer getFrom() {
		return from;
	}


	public Integer getPageSize() {
		return pageSize;
	}

}
