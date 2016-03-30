package com.web.bean;

import java.util.List;

/**
 * 期刊搜索封装类
 * 2015/11/04
 */
public class JournalSearchCondition {

	/**
	 * 必须
	 */
	private List<String> journalIdMust;

	/**
	 * 可以
	 */
	private List<String> journalIdShould;

	/**
	 * 不能
	 */
	private List<String> journalIdNot;

	/**
	 * 必须包含期刊名称（标题）
	 */
	private List<String> nameMust;

	/**
	 * 可以包含期刊名称（标题）
	 */
	private List<String> nameShould;

	/**
	 * 不能包含期刊名称（标题）
	 */
	private List<String> nameNot;

	/**
	 * 必须包含简介
	 */
	private List<String> skipMust;

	/**
	 * 可以包含简介
	 */
	private List<String> skipShould;

	/**
	 * 不能包含简介
	 */
	private List<String> skipNot;

	/**
	 * 必须包含标签
	 */
	private List<String> labelMust;

	/**
	 * 可以包含的标签
	 */
	private List<String> labelShould;

	/**
	 * 不能包含的标签
	 */
	private List<String> labelNot;

	/**
	 * 必须包含的关键字
	 */
	private List<String> keyWordMust;

	/**
	 * 可以包含的关键字
	 */
	private List<String> keyWordShould;

	/**
	 * 不能包含的关键字
	 */
	private List<String> keyWordNot;

	/**
	 * 搜索类别(title：标题，content：全文)
	 */
	private String searchType;

	/**
	 * 状态：SAVED:私有保存, SUBMITTED:审核中, PASSED:审核成功, FAILED:审核失败 默认：SUBMITTED审核中
	 */
	private List<String> passType;

	/**
	 * 创建人
	 */
	private String submitUserName;

	/**
	 * 提交起始时间
	 */
	private long submitStartTime;

	/**
	 * 提交结果时间
	 */
	private long submitEndTime;
	
	/**
	 * 默认:all,　无数据：0，　有数据：1
	 */
	private String showData="1";
	
	/**
	 * true: 不过滤删除信息，并做删除标记
	 */
	private String deleteTag;
	

	public List<String> getNameMust() {
		return nameMust;
	}

	public void setNameMust(List<String> nameMust) {
		this.nameMust = nameMust;
	}

	public List<String> getNameShould() {
		return nameShould;
	}

	public void setNameShould(List<String> nameShould) {
		this.nameShould = nameShould;
	}

	public List<String> getNameNot() {
		return nameNot;
	}

	public void setNameNot(List<String> nameNot) {
		this.nameNot = nameNot;
	}

	public List<String> getSkipMust() {
		return skipMust;
	}

	public void setSkipMust(List<String> skipMust) {
		this.skipMust = skipMust;
	}

	public List<String> getSkipShould() {
		return skipShould;
	}

	public void setSkipShould(List<String> skipShould) {
		this.skipShould = skipShould;
	}

	public List<String> getSkipNot() {
		return skipNot;
	}

	public void setSkipNot(List<String> skipNot) {
		this.skipNot = skipNot;
	}

	public List<String> getLabelMust() {
		return labelMust;
	}

	public void setLabelMust(List<String> labelMust) {
		this.labelMust = labelMust;
	}

	public List<String> getLabelShould() {
		return labelShould;
	}

	public void setLabelShould(List<String> labelShould) {
		this.labelShould = labelShould;
	}

	public List<String> getLabelNot() {
		return labelNot;
	}

	public void setLabelNot(List<String> labelNot) {
		this.labelNot = labelNot;
	}

	public List<String> getKeyWordMust() {
		return keyWordMust;
	}

	public void setKeyWordMust(List<String> keyWordMust) {
		this.keyWordMust = keyWordMust;
	}

	public List<String> getKeyWordShould() {
		return keyWordShould;
	}

	public void setKeyWordShould(List<String> keyWordShould) {
		this.keyWordShould = keyWordShould;
	}

	public List<String> getKeyWordNot() {
		return keyWordNot;
	}

	public void setKeyWordNot(List<String> keyWordNot) {
		this.keyWordNot = keyWordNot;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public List<String> getJournalIdMust() {
		return journalIdMust;
	}

	public void setJournalIdMust(List<String> journalIdMust) {
		this.journalIdMust = journalIdMust;
	}

	public List<String> getJournalIdShould() {
		return journalIdShould;
	}

	public void setJournalIdShould(List<String> journalIdShould) {
		this.journalIdShould = journalIdShould;
	}

	public List<String> getJournalIdNot() {
		return journalIdNot;
	}

	public void setJournalIdNot(List<String> journalIdNot) {
		this.journalIdNot = journalIdNot;
	}

	public String getSubmitUserName() {
		return submitUserName;
	}

	public void setSubmitUserName(String submitUserName) {
		this.submitUserName = submitUserName;
	}

	public long getSubmitStartTime() {
		return submitStartTime;
	}

	public void setSubmitStartTime(long submitStartTime) {
		this.submitStartTime = submitStartTime;
	}

	public long getSubmitEndTime() {
		return submitEndTime;
	}

	public void setSubmitEndTime(long submitEndTime) {
		this.submitEndTime = submitEndTime;
	}

	public String getShowData() {
		return showData;
	}

	public void setShowData(String showData) {
		this.showData = showData;
	}

	public String getDeleteTag() {
		return deleteTag;
	}

	public void setDeleteTag(String deleteTag) {
		this.deleteTag = deleteTag;
	}

	public List<String> getPassType() {
		return passType;
	}

	public void setPassType(List<String> passType) {
		this.passType = passType;
	}

	
}
