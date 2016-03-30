package com.unbank.model.param;

public class IndustryParam implements UnbankParam {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8056331529898307326L;

	private int industryId;

	private String name;

	private String code;

	public IndustryParam() {
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
