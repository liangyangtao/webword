package com.web.bean;

import java.util.List;

public class GroupParam {

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
	private List<DocumentSearchCondition> jsonData;

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getPageSize() {
		return pageSize;
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

	public List<DocumentSearchCondition> getJsonData() {
		return jsonData;
	}

	public void setJsonData(List<DocumentSearchCondition> jsonData) {
		this.jsonData = jsonData;
	}

}
