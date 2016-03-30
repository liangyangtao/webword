package com.model.param;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 
 * @author zile
 * 
 */
public class DateParam implements UnbankParam {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3151891005223946455L;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private String from;

	private String to;

	public DateParam() {
	}

	public DateParam(String from, String to) {
		this.from = from;
		this.to = to;
	}

	public Calendar from() {
		return toDate(from);

	}

	public Calendar to() {
		return toDate(to);
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

	private Calendar toDate(String date) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(date));
			return cal;
		} catch (ParseException e) {
			throw new RuntimeException("toDate error!", e);
		}

	}

}
