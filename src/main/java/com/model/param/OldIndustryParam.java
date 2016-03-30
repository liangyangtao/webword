package com.model.param;

public class OldIndustryParam implements UnbankParam {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8460278809112592591L;

	private int industryId;

	private String name;

	private String code;

	public OldIndustryParam() {
		// TODO Auto-generated constructor stub
	}

	public int getIndustryId() {
		return industryId;
	}

	public void setIndustryId(int industryId) {
		this.industryId = industryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
