package com.lucene.entity.term;

public class TermDateConfig {

	private int userId;

	private int templateId;
	private int structureId;
	private int termId;

	/**
	 * 搜索时间
	 */
	private String useDate;
	private String from;
	private String to;

	/**
	 * 定制周期
	 */
	private String usePeriod;
	private int periodType;
	private String pfrom;
	private String pto;
	private int days;

	public TermDateConfig() {
		// TODO Auto-generated constructor stub
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public int getStructureId() {
		return structureId;
	}

	public void setStructureId(int structureId) {
		this.structureId = structureId;
	}

	public int getTermId() {
		return termId;
	}

	public void setTermId(int termId) {
		this.termId = termId;
	}

	public String getUseDate() {
		return useDate;
	}

	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getUsePeriod() {
		return usePeriod;
	}

	public void setUsePeriod(String usePeriod) {
		this.usePeriod = usePeriod;
	}

	public int getPeriodType() {
		return periodType;
	}

	public void setPeriodType(int periodType) {
		this.periodType = periodType;
	}

	public String getPfrom() {
		return pfrom;
	}

	public void setPfrom(String pfrom) {
		this.pfrom = pfrom;
	}

	public String getPto() {
		return pto;
	}

	public void setPto(String pto) {
		this.pto = pto;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

}
