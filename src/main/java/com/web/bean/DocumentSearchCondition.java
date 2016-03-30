package com.web.bean;

import java.util.List;

public class DocumentSearchCondition {

	/**
	 * 文档来源的项目（注意：多个来源之间进行或运算取并集）
	 */
	private List<String> articleProjects;

	/**
	 * 文档操作类型（upload or write）
	 */
	private String articleSave;

	/**
	 * 文档名称中必须包含的词
	 */
	private List<String> mustWordsOfArticleName;

	/**
	 * 文档名称中可以包含的词
	 */
	private List<String> shouldWordsOfArticleName;

	/**
	 * 文档标题中不能包含的词
	 */
	private List<String> mustNotWordsOfArticleName;

	/**
	 * 文档类型
	 */
	private String articleType;

	/**
	 * 创建文档的用户名称（注意：多个名称之间进行或运算取并集）
	 * 
	 */
	private List<String> articleUsers;

	/**
	 * 创建文档的用户的ID（注意：多个ID之间进行或运算取并集）
	 */
	private List<Integer> userIds;

	/**
	 * 文档简介中必须包含的词
	 */
	private List<String> mustWordsOfArticleBrief;

	/**
	 * 文档简介中可以包含的词
	 */
	private List<String> shouldWordsOfArticleBrief;

	/**
	 * 文档简介中不能包含的词
	 */
	private List<String> mustNotWordsOfArticleBrief;

	/**
	 * 文档内容中必须包含的词语
	 */
	private List<String> mustWordsOfArticleContent;

	/**
	 * 文档内容中可以包含的词
	 */
	private List<String> shouldWordsOfArticleContent;

	/**
	 * 文档内容中不能包含的词
	 */
	private List<String> mustNotWordsOfArticleContent;

	/**
	 * 文档关键词中必须包含的词
	 */
	private List<String> mustArticleLabels;

	/**
	 * 文档关键词中可以包含的词
	 */
	private List<String> shouldArticleLabels;

	/**
	 * 文档关键词中不能包含的词
	 */
	private List<String> mustNotArticleLabels;

	/**
	 * 文档审核通过的开始时间范围
	 */
	private Long startPassTime;

	/**
	 * 文档审核通过的结束时间范围
	 */
	private Long endPassTime;

	/**
	 * 审核人（注意：多个审核人之间进行或运算取并集）
	 */
	private List<String> passUserNames;

	/**
	 * 审核人的ID（注意：多个审核人ID之间进行或运算取并集）
	 */
	private List<Integer> passUsers;

	/**
	 * 建立文档的开始时间范围
	 */
	private Long startInsertTime;

	/**
	 * 建立文档的结束时间范围
	 */
	private Long endInsertTime;

	/**
	 * 更新时间的开始时间范围
	 */
	private Long startUpdateTime;

	/**
	 * 更新时间的结束时间范围
	 */
	private Long endUpdateTime;

	/**
	 * 生成Word时间的开始时间范围
	 */
	private Long startDownTime;

	/**
	 * 生成Word时间的结束时间范围
	 */
	private Long endDownTime;

	/**
	 * 文档价格的下限范围
	 */
	private float articlePriceFrom;

	/**
	 * 文档价格的上线范围
	 */
	private float articlePriceTo;

	/**
	 * 文档简介中必须包含的词
	 */
	private List<String> mustWordsOfArticleSkip;

	/**
	 * 文档简介中不能包含的词
	 */
	private List<String> mustNotWordsOfArticleSkip;

	/**
	 * 文档简介中可以包含的词
	 */
	private List<String> shouldWordsOfArticleSkip;

	/**
	 * 默认 all,不用传 doc，pdf,ppt,docx，pptx
	 */
	private List<String> articleFormat;

	/**
	 * 1：全部 2：期刊文档 3：非期刊文档
	 */
	private int fileType = 1;
	/*
	 * 期刊ID 多个ID之间进行或运算取并集
	 */
	private List<Integer> journalIds;

	/**
	 * 默认全部 审核类型状态：SAVED:私有保存, SUBMITTED:审核中, PASSED:审核成功, FAILED:审核失败
	 * 默认：SUBMITTED审核中
	 */
	private String passType;

	private List<String> mustArticleId;

	private List<String> mustNotArticleId;

	private List<String> shouldArticleId;

	private List<String> mustJournalId;

	private List<String> mustNotJournalId;

	private List<String> shouldJournalid;

	public List<String> getMustJournalId() {
		return mustJournalId;
	}

	public void setMustJournalId(List<String> mustJournalId) {
		this.mustJournalId = mustJournalId;
	}

	public List<String> getMustNotJournalId() {
		return mustNotJournalId;
	}

	public void setMustNotJournalId(List<String> mustNotJournalId) {
		this.mustNotJournalId = mustNotJournalId;
	}

	public List<String> getShouldJournalid() {
		return shouldJournalid;
	}

	public void setShouldJournalid(List<String> shouldJournalid) {
		this.shouldJournalid = shouldJournalid;
	}

	public List<String> getMustArticleId() {
		return mustArticleId;
	}

	public void setMustArticleId(List<String> mustArticleId) {
		this.mustArticleId = mustArticleId;
	}

	public List<String> getMustNotArticleId() {
		return mustNotArticleId;
	}

	public void setMustNotArticleId(List<String> mustNotArticleId) {
		this.mustNotArticleId = mustNotArticleId;
	}

	public List<String> getShouldArticleId() {
		return shouldArticleId;
	}

	public void setShouldArticleId(List<String> shouldArticleId) {
		this.shouldArticleId = shouldArticleId;
	}

	public List<String> getArticleProjects() {
		return articleProjects;
	}

	public void setArticleProjects(List<String> articleProjects) {
		this.articleProjects = articleProjects;
	}

	public String getArticleSave() {
		return articleSave;
	}

	public void setArticleSave(String articleSave) {
		this.articleSave = articleSave;
	}

	public List<String> getMustWordsOfArticleName() {
		return mustWordsOfArticleName;
	}

	public void setMustWordsOfArticleName(List<String> mustWordsOfArticleName) {
		this.mustWordsOfArticleName = mustWordsOfArticleName;
	}

	public List<String> getShouldWordsOfArticleName() {
		return shouldWordsOfArticleName;
	}

	public void setShouldWordsOfArticleName(
			List<String> shouldWordsOfArticleName) {
		this.shouldWordsOfArticleName = shouldWordsOfArticleName;
	}

	public List<String> getMustNotWordsOfArticleName() {
		return mustNotWordsOfArticleName;
	}

	public void setMustNotWordsOfArticleName(
			List<String> mustNotWordsOfArticleName) {
		this.mustNotWordsOfArticleName = mustNotWordsOfArticleName;
	}

	public String getArticleType() {
		return articleType;
	}

	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}

	public List<String> getArticleUsers() {
		return articleUsers;
	}

	public void setArticleUsers(List<String> articleUsers) {
		this.articleUsers = articleUsers;
	}

	public List<Integer> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}

	public List<String> getMustWordsOfArticleBrief() {
		return mustWordsOfArticleBrief;
	}

	public void setMustWordsOfArticleBrief(List<String> mustWordsOfArticleBrief) {
		this.mustWordsOfArticleBrief = mustWordsOfArticleBrief;
	}

	public List<String> getShouldWordsOfArticleBrief() {
		return shouldWordsOfArticleBrief;
	}

	public void setShouldWordsOfArticleBrief(
			List<String> shouldWordsOfArticleBrief) {
		this.shouldWordsOfArticleBrief = shouldWordsOfArticleBrief;
	}

	public List<String> getMustNotWordsOfArticleBrief() {
		return mustNotWordsOfArticleBrief;
	}

	public void setMustNotWordsOfArticleBrief(
			List<String> mustNotWordsOfArticleBrief) {
		this.mustNotWordsOfArticleBrief = mustNotWordsOfArticleBrief;
	}

	public List<String> getMustWordsOfArticleContent() {
		return mustWordsOfArticleContent;
	}

	public void setMustWordsOfArticleContent(
			List<String> mustWordsOfArticleContent) {
		this.mustWordsOfArticleContent = mustWordsOfArticleContent;
	}

	public List<String> getShouldWordsOfArticleContent() {
		return shouldWordsOfArticleContent;
	}

	public void setShouldWordsOfArticleContent(
			List<String> shouldWordsOfArticleContent) {
		this.shouldWordsOfArticleContent = shouldWordsOfArticleContent;
	}

	public List<String> getMustNotWordsOfArticleContent() {
		return mustNotWordsOfArticleContent;
	}

	public void setMustNotWordsOfArticleContent(
			List<String> mustNotWordsOfArticleContent) {
		this.mustNotWordsOfArticleContent = mustNotWordsOfArticleContent;
	}

	public List<String> getMustArticleLabels() {
		return mustArticleLabels;
	}

	public void setMustArticleLabels(List<String> mustArticleLabels) {
		this.mustArticleLabels = mustArticleLabels;
	}

	public List<String> getShouldArticleLabels() {
		return shouldArticleLabels;
	}

	public void setShouldArticleLabels(List<String> shouldArticleLabels) {
		this.shouldArticleLabels = shouldArticleLabels;
	}

	public List<String> getMustNotArticleLabels() {
		return mustNotArticleLabels;
	}

	public void setMustNotArticleLabels(List<String> mustNotArticleLabels) {
		this.mustNotArticleLabels = mustNotArticleLabels;
	}

	public Long getStartPassTime() {
		return startPassTime;
	}

	public void setStartPassTime(Long startPassTime) {
		this.startPassTime = startPassTime;
	}

	public Long getEndPassTime() {
		return endPassTime;
	}

	public void setEndPassTime(Long endPassTime) {
		this.endPassTime = endPassTime;
	}

	public List<String> getPassUserNames() {
		return passUserNames;
	}

	public void setPassUserNames(List<String> passUserNames) {
		this.passUserNames = passUserNames;
	}

	public List<Integer> getPassUsers() {
		return passUsers;
	}

	public void setPassUsers(List<Integer> passUsers) {
		this.passUsers = passUsers;
	}

	public Long getStartInsertTime() {
		return startInsertTime;
	}

	public void setStartInsertTime(Long startInsertTime) {
		this.startInsertTime = startInsertTime;
	}

	public Long getEndInsertTime() {
		return endInsertTime;
	}

	public void setEndInsertTime(Long endInsertTime) {
		this.endInsertTime = endInsertTime;
	}

	public Long getStartUpdateTime() {
		return startUpdateTime;
	}

	public void setStartUpdateTime(Long startUpdateTime) {
		this.startUpdateTime = startUpdateTime;
	}

	public Long getEndUpdateTime() {
		return endUpdateTime;
	}

	public void setEndUpdateTime(Long endUpdateTime) {
		this.endUpdateTime = endUpdateTime;
	}

	public Long getStartDownTime() {
		return startDownTime;
	}

	public void setStartDownTime(Long startDownTime) {
		this.startDownTime = startDownTime;
	}

	public Long getEndDownTime() {
		return endDownTime;
	}

	public void setEndDownTime(Long endDownTime) {
		this.endDownTime = endDownTime;
	}

	public float getArticlePriceFrom() {
		return articlePriceFrom;
	}

	public void setArticlePriceFrom(float articlePriceFrom) {
		this.articlePriceFrom = articlePriceFrom;
	}

	public float getArticlePriceTo() {
		return articlePriceTo;
	}

	public void setArticlePriceTo(float articlePriceTo) {
		this.articlePriceTo = articlePriceTo;
	}

	public List<String> getMustWordsOfArticleSkip() {
		return mustWordsOfArticleSkip;
	}

	public void setMustWordsOfArticleSkip(List<String> mustWordsOfArticleSkip) {
		this.mustWordsOfArticleSkip = mustWordsOfArticleSkip;
	}

	public List<String> getMustNotWordsOfArticleSkip() {
		return mustNotWordsOfArticleSkip;
	}

	public void setMustNotWordsOfArticleSkip(
			List<String> mustNotWordsOfArticleSkip) {
		this.mustNotWordsOfArticleSkip = mustNotWordsOfArticleSkip;
	}

	public List<String> getShouldWordsOfArticleSkip() {
		return shouldWordsOfArticleSkip;
	}

	public void setShouldWordsOfArticleSkip(
			List<String> shouldWordsOfArticleSkip) {
		this.shouldWordsOfArticleSkip = shouldWordsOfArticleSkip;
	}

	public List<String> getArticleFormat() {
		return articleFormat;
	}

	public void setArticleFormat(List<String> articleFormat) {
		this.articleFormat = articleFormat;
	}

	public int getFileType() {
		return fileType;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	public String getPassType() {
		return passType;
	}

	public void setPassType(String passType) {
		this.passType = passType;
	}

	public List<Integer> getJournalIds() {
		return journalIds;
	}

	public void setJournalIds(List<Integer> journalIds) {
		this.journalIds = journalIds;
	}

}
