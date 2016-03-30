package com.unbank;

import org.springframework.core.NestedRuntimeException;

/**
 * 
 * @author zile
 *
 */
public class UnbankException extends NestedRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7033343322280026119L;

	public UnbankException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public UnbankException(String msg) {
		super(msg);
	}

}
