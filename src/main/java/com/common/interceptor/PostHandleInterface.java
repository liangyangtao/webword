package com.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

/**
 * 处理器处理完整个请求后接口定义
 * 
 * @author 
 *
 */
public interface PostHandleInterface {

	/**
	 * 处理器处理完整个请求定义方法
	 * 
	 * @param request
	 * @param response
	 * @param object
	 * @param modelAndiew
	 * @throws Exception
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
			ModelAndView modelAndiew) throws Exception;
}
