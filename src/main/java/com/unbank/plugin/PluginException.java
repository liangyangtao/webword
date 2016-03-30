package com.unbank.plugin;

import com.unbank.UnbankException;

/**
 * 
 * @author zile
 *
 */
public class PluginException extends UnbankException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6246105467022218862L;

	public PluginException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public PluginException(String msg) {
		super(msg);
	}

}
