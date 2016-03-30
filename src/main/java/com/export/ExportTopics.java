package com.export;

import java.util.List;

public class ExportTopics {

	private String topicTitle;

	/**
	 * 是否显示标题
	 */
	private boolean display;

	private List<Property> list;

	public ExportTopics() {
		// TODO Auto-generated constructor stub
	}

	public String getTopicTitle() {
		return topicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}

	public List<Property> getList() {
		return list;
	}

	public void setList(List<Property> list) {
		this.list = list;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

}
