package com.web.bean;

import org.apache.commons.lang3.StringUtils;

public class Condition {
	// mustTagNames：必须的标签（多个参数之间使用“_”连接）
	// mustNotTagNames：不能包含的标签（多个参数之间使用“_”连接）
	// shouldTagNames：可以出现的标签（多个参数之间使用“_”连接）
	// mustWordNames：必须的关键词（多个参数之间使用“_”连接）
	// mustNotWordNames：不能包含的关键词（多个参数之间使用“_”连接）
	// shouldWordNames：可以出现的关键词（多个参数之间使用“_”连接）
	private String mustTagNames;
	private String mustNotTagNames;
	private String shouldTagNames;
	private String mustWordNames;
	private String mustNotWordNames;
	private String shouldWordNames;
	private int flag = 0;//0:全文 1标题
	private String source;

	public boolean isEmpty() {
		if (StringUtils.isBlank(mustTagNames)
				&& StringUtils.isBlank(mustNotTagNames)
				&& StringUtils.isBlank(shouldTagNames)
				&& StringUtils.isBlank(mustWordNames)
				&& StringUtils.isBlank(mustNotWordNames)
				&& StringUtils.isBlank(shouldWordNames)
				&& StringUtils.isBlank(source)) {
			return true;
		}
		return false;
	}

	public String getMustTagNames() {
		return mustTagNames;
	}

	public void setMustTagNames(String mustTagNames) {
		this.mustTagNames = mustTagNames;
	}

	public String getMustNotTagNames() {
		return mustNotTagNames;
	}

	public void setMustNotTagNames(String mustNotTagNames) {
		this.mustNotTagNames = mustNotTagNames;
	}

	public String getShouldTagNames() {
		return shouldTagNames;
	}

	public void setShouldTagNames(String shouldTagNames) {
		this.shouldTagNames = shouldTagNames;
	}

	public String getMustWordNames() {
		return mustWordNames;
	}

	public void setMustWordNames(String mustWordNames) {
		this.mustWordNames = mustWordNames;
	}

	public String getMustNotWordNames() {
		return mustNotWordNames;
	}

	public void setMustNotWordNames(String mustNotWordNames) {
		this.mustNotWordNames = mustNotWordNames;
	}

	public String getShouldWordNames() {
		return shouldWordNames;
	}

	public void setShouldWordNames(String shouldWordNames) {
		this.shouldWordNames = shouldWordNames;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	
}
