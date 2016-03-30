package com.lucene.entity.term;

public abstract class Period {

	public final static int DAYS = 1;
	public final static int MONTHS = 2;

	abstract protected int periodType();

}
