package com.model.domain;

import java.io.Serializable;

public class Industry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7749928686967867079L;

	private int id;

	private String code;

	private String name;

	public Industry() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
