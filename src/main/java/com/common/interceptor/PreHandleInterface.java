package com.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理器执行前处理请求接口定义
 * 
 * @author 
 * 
 */
public interface PreHandleInterface {

	/**
	 * 处理器执行请求前方法定义,返回true执行链继续执行，返回false结束请求处理
	 * 
	 * @param request
	 * @param response
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception;
}
