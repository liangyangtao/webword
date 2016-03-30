package com.web.view.bean;

import java.util.Date;

/**
 * 期刊分期信息
 * @author rrq 
 */
public class JournalInfoBean {

	private Integer articleId;//文章ID
	private Date articleJournalTime;//文章时间
	private String articleYear;//文章年份
	private String articleMonth;//月份
	private String articleDay;//日
	private Integer journalId;//期刊ID
	private Integer journalTypeId;//期刊类型ID(1:日刊 2:周刊 3:半月刊 4:月刊 5:双月刊 6:季刊 7:半年刊 8:年刊)
	private String articleShow;//显示用(如08日、1周、2季度)
	private Date compareTime;
	
	
	
	
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public Date getArticleJournalTime() {
		return articleJournalTime;
	}
	public void setArticleJournalTime(Date articleJournalTime) {
		this.articleJournalTime = articleJournalTime;
	}
	public String getArticleYear() {
		return articleYear;
	}
	public void setArticleYear(String articleYear) {
		this.articleYear = articleYear;
	}
	public String getArticleMonth() {
		return articleMonth;
	}
	public void setArticleMonth(String articleMonth) {
		this.articleMonth = articleMonth;
	}
	public String getArticleDay() {
		return articleDay;
	}
	public void setArticleDay(String articleDay) {
		this.articleDay = articleDay;
	}
	public Integer getJournalId() {
		return journalId;
	}
	public void setJournalId(Integer journalId) {
		this.journalId = journalId;
	}
	public Integer getJournalTypeId() {
		return journalTypeId;
	}
	public void setJournalTypeId(Integer journalTypeId) {
		this.journalTypeId = journalTypeId;
	}
	public String getArticleShow() {
		return articleShow;
	}
	public void setArticleShow(String articleShow) {
		this.articleShow = articleShow;
	}
	public Date getCompareTime() {
		return compareTime;
	}
	public void setCompareTime(Date compareTime) {
		this.compareTime = compareTime;
	}


}
