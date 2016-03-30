package com.lucene.entity.term;

public class PeriodDay extends Period {

	private int days;

	public PeriodDay(int days) {
		this.days = days;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	@Override
	protected int periodType() {
		return DAYS;
	}

}
