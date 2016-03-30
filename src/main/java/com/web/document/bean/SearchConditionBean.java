/*
 * <p>Title: 知识自动化平台</p>
 * <p>Description: AdvancedSearchConditionBean.java</p>
 * <p>Copyright: Copyright (c) 2015 北京银联信投资顾问有限责任公司，版权所有. </p>
 * <p>Company: 北京银联信投资顾问有限责任公司</p>
 * @author knight
 * @date 2015-5-8
 * @version 1.0
 */
package com.web.document.bean;

import java.util.Date;

import com.sun.jmx.snmp.Timestamp;

/**
 * <p>Title: AdvancedSearchConditionBean</p>
 * <p>Description: TODO</p>
 * @author knight
 * @date 2015-5-8
 */
public class SearchConditionBean {

	private int ascondition_id;//id
	private String ascondition_all;//所有
	private String ascondition_any;//任意
	private String ascondition_nocontains;//不包含
	private Timestamp ascondition_stime;//搜索开始时间
	private Timestamp ascondition_itime;//插入时间
	private int user_id;//用户id
	private String type;//搜索类型
	private int hitnumber;//搜索结果数量
	private Timestamp ascondition_etime;//搜索结束时间
	private int ascondition_part;//搜索范围
	private int ascondition_sort;//排序方式
	private String ascondition_source;//搜索网址
	
	private String advancedSearchStartTime;
	private String advancedSearchEndTime;
	
	private boolean isHigh=false;//是否高亮
	
	private boolean fullContent;//是否显示正文 true:显示这正文
	
	
	//2015-01-28杨振兴增加文章id
	private int articleId;
	
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	public int getAscondition_id() {
		return ascondition_id;
	}
	public void setAscondition_id(int ascondition_id) {
		this.ascondition_id = ascondition_id;
	}
	public String getAscondition_all() {
		return ascondition_all;
	}
	public void setAscondition_all(String ascondition_all) {
		this.ascondition_all = ascondition_all;
	}
	public String getAscondition_any() {
		return ascondition_any;
	}
	public void setAscondition_any(String ascondition_any) {
		this.ascondition_any = ascondition_any;
	}
	public String getAscondition_nocontains() {
		return ascondition_nocontains;
	}
	public void setAscondition_nocontains(String ascondition_nocontains) {
		this.ascondition_nocontains = ascondition_nocontains;
	}
	public Timestamp getAscondition_stime() {
		return ascondition_stime;
	}
	public void setAscondition_stime(Timestamp ascondition_stime) {
		this.ascondition_stime = ascondition_stime;
	}
	public Timestamp getAscondition_itime() {
		return ascondition_itime;
	}
	public void setAscondition_itime(Timestamp ascondition_itime) {
		this.ascondition_itime = ascondition_itime;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getHitnumber() {
		return hitnumber;
	}
	public void setHitnumber(int hitnumber) {
		this.hitnumber = hitnumber;
	}
	public Timestamp getAscondition_etime() {
		return ascondition_etime;
	}
	public void setAscondition_etime(Timestamp ascondition_etime) {
		this.ascondition_etime = ascondition_etime;
	}
	public int getAscondition_part() {
		return ascondition_part;
	}
	public void setAscondition_part(int ascondition_part) {
		this.ascondition_part = ascondition_part;
	}
	public int getAscondition_sort() {
		return ascondition_sort;
	}
	public void setAscondition_sort(int ascondition_sort) {
		this.ascondition_sort = ascondition_sort;
	}
	public String getAscondition_source() {
		return ascondition_source;
	}
	public void setAscondition_source(String ascondition_source) {
		this.ascondition_source = ascondition_source;
	}
	public String getAdvancedSearchStartTime() {
		return advancedSearchStartTime;
	}
	public void setAdvancedSearchStartTime(String advancedSearchStartTime) {
		this.advancedSearchStartTime = advancedSearchStartTime;
	}
	public String getAdvancedSearchEndTime() {
		return advancedSearchEndTime;
	}
	public void setAdvancedSearchEndTime(String advancedSearchEndTime) {
		this.advancedSearchEndTime = advancedSearchEndTime;
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
}
