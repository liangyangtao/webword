package com.model;

public class NoSuchModelException extends PluginModelException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7533354066950883254L;

	public NoSuchModelException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NoSuchModelException(String msg) {
		super(msg);
	}

}
