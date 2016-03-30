package com.lucene.entity.type;

public enum LogStatus {

	ERROR("失败"), SUCCESS("成功");

	private final String name;

	private LogStatus(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
