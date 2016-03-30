package com.unbank.model.domain;

import java.io.Serializable;

public class Area implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4922941165889701961L;

	private int id;

	private String code;

	private String name;

	public Area() {
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
