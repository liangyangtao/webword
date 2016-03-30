package com.unbank.model;

import com.unbank.plugin.PluginException;

public class PluginModelException extends PluginException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -760850853069522693L;

	public PluginModelException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public PluginModelException(String msg) {
		super(msg);
	}

}
