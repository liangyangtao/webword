/*
 * <p>Title: 知识自动化平台</p>
 * <p>Description: GrabContent.java</p>
 * <p>Copyright: Copyright (c) 2015 北京银联信投资顾问有限责任公司，版权所有. </p>
 * <p>Company: 北京银联信投资顾问有限责任公司</p>
 * @author knight
 * @date 2015-4-30
 * @version 1.0
 */
package com.web.document.bean;

/**
 * <p>Title: GrabContent</p>
 * <p>Description: TODO</p>
 * @author knight
 * @date 2015-4-30
 */
public class GrabContent {

	private int crawl_id;//编号
	private String text;//内容
	private String title;//标题
	
	public int getCrawl_id() {
		return crawl_id;
	}
	public void setCrawl_id(int crawl_id) {
		this.crawl_id = crawl_id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
