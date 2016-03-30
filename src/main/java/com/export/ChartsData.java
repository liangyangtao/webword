/*
 * <p>Title: 知识自动化平台</p>
 * <p>Description: ChartsData.java</p>
 * <p>Copyright: Copyright (c) 2015 北京银联信投资顾问有限责任公司，版权所有. </p>
 * <p>Company: 北京银联信投资顾问有限责任公司</p>
 * @author knight
 * @date 2015-5-8
 * @version 1.0
 */
package com.export;

import java.util.List;

/**
 * <p>Title: ChartsData</p>
 * <p>Description: TODO</p>
 * @author knight
 * @date 2015-5-8
 */
public class ChartsData {
	private String name;    //数据下标
	private List<Integer> data;   //数据集合
	
	private List<String> categories;   //横坐标数据集合
	private String zhibiao;
	private String danwei;//单位
	private String type;
	private String Title;
	private int yAxis;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Integer> getData() {
		return data;
	}
	public void setData(List<Integer> data) {
		this.data = data;
	}
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public String getZhibiao() {
		return zhibiao;
	}
	public void setZhibiao(String zhibiao) {
		this.zhibiao = zhibiao;
	}
	public String getDanwei() {
		return danwei;
	}
	public void setDanwei(String danwei) {
		this.danwei = danwei;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public int getyAxis() {
		return yAxis;
	}
	public void setyAxis(int yAxis) {
		this.yAxis = yAxis;
	}
}
