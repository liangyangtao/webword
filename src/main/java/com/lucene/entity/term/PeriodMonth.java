package com.lucene.entity.term;

public class PeriodMonth extends Period {

	private String from;

	private String to;

	private long longFrom;

	private long longTo;

	public PeriodMonth(String from, String to) {
		this.from = from;
		this.to = to;
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

	public long getLongFrom() {
		return longFrom;
	}

	public void setLongFrom(long longFrom) {
		this.longFrom = longFrom;
	}

	public long getLongTo() {
		return longTo;
	}

	public void setLongTo(long longTo) {
		this.longTo = longTo;
	}

	@Override
	protected int periodType() {
		return MONTHS;
	}

}
