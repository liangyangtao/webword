package com.web.bean;

public class ContionParam {
	/*
	 * id
	 */
	private int id;
	
	/*
	 * 搜索条件
	 */
	private String conditions;
	/*
	 * 置顶
	 */
	private String topList;
	
	/*
	 * 删除
	 */
	private String delList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getTopList() {
		return topList;
	}

	public void setTopList(String topList) {
		this.topList = topList;
	}

	public String getDelList() {
		return delList;
	}

	public void setDelList(String delList) {
		this.delList = delList;
	}

}
