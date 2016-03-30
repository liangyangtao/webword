package com.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理器执行后处理请求接口定义
 * 
 * @author 
 * 
 */
public interface AfterCompletionInterface {

	/**
	 * 处理器执行请求后方法定义
	 * 
	 * @param request
	 * @param response
	 * @param object
	 * @param exception
	 * @throws Exception
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception exception) throws Exception;
}
