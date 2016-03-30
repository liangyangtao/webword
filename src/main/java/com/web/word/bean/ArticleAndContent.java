/*
 * <p>Title: 知识自动化平台</p>
 * <p>Description: ArticleAndContent.java</p>
 * <p>Copyright: Copyright (c) 2015 北京银联信投资顾问有限责任公司，版权所有. </p>
 * <p>Company: 北京银联信投资顾问有限责任公司</p>
 * @author knight
 * @date 2015-5-6
 * @version 1.0
 */
package com.web.word.bean;

/**
 * <p>Title: ArticleAndContent</p>
 * <p>Description: TODO</p>
 * @author knight
 * @date 2015-5-6
 */
public class ArticleAndContent {
	private Integer id;
	private String name="";
	private String type="";
	private int user_id;
	private String user_name="";
	private String pass_type;
	private String pass_time;
	private String insert_time;
	private String update_time;
	private int pass_user=0;
	private int check_number;
	private String keyword;
	private String content;
	private String reason;
	private String article_skip="";
	private int plate;//类别
	private String skip="";//简介
	private String articleLabel;//标签
	
	
	public String getSkip() {
		return skip;
	}
	public void setSkip(String skip) {
		this.skip = skip;
	}
	public int getPlate() {
		return plate;
	}
	public void setPlate(int plate) {
		this.plate = plate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getPass_type() {
		return pass_type;
	}
	public void setPass_type(String pass_type) {
		this.pass_type = pass_type;
	}
	public String getPass_time() {
		return pass_time;
	}
	public void setPass_time(String pass_time) {
		this.pass_time = pass_time;
	}
	public String getInsert_time() {
		return insert_time;
	}
	public void setInsert_time(String insert_time) {
		this.insert_time = insert_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public int getPass_user() {
		return pass_user;
	}
	public void setPass_user(int pass_user) {
		this.pass_user = pass_user;
	}
	public int getCheck_number() {
		return check_number;
	}
	public void setCheck_number(int check_number) {
		this.check_number = check_number;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getArticle_skip() {
		return article_skip;
	}
	public void setArticle_skip(String article_skip) {
		this.article_skip = article_skip;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getArticleLabel() {
		return articleLabel;
	}
	public void setArticleLabel(String articleLabel) {
		this.articleLabel = articleLabel;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
