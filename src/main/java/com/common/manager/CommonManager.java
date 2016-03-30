package com.common.manager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 预处理类
 * 
 * @author
 * 
 */
public class CommonManager {

	public static final String SESSION_NAME = "COMMON_MANAGER";
	private static ThreadLocal<CommonManager> local = new ThreadLocal<CommonManager>();
	private HttpServletRequest request;
	private HttpServletResponse response;

	public CommonManager() {
		super();
	}

	public CommonManager(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public static CommonManager getCurrentManager() {
		return (CommonManager) local.get();
	}

	public static void setCurrentManager(CommonManager manager) {
		local.set(manager);
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
}
