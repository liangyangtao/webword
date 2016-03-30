package com.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * 定义拦截器，用来处理用户请求前，请求后，整个处理请求处理结束自定义处理方法
 * 
 * @author 
 * 
 */
public class CommonHandlerInterceptor implements HandlerInterceptor {

	/** 请求前 */
	private PreHandleInterface preHandleInterface;
	/** 请求后 */
	private AfterCompletionInterface afterCompletionInterface;
	/** 请求处理结束 */
	private PostHandleInterface postHandleInterface;

	/**
	 * 执行后调用
	 * 
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception exception) throws Exception {
		if (afterCompletionInterface != null) {
			afterCompletionInterface.afterCompletion(request, response, object, exception);
		}
	}

	/**
	 * 请求处理完成后调用
	 * 
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
			ModelAndView modelAndiew) throws Exception {
		if (postHandleInterface != null) {
			postHandleInterface.postHandle(request, response, object, modelAndiew);
		}
	}

	/**
	 * 执行前调用
	 * 
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		if (preHandleInterface != null) {
			return preHandleInterface.preHandle(request, response, object);
		}
		return true;
	}

	public void setPreHandleInterface(PreHandleInterface preHandleInterface) {
		this.preHandleInterface = preHandleInterface;
	}

	public void setAfterCompletionInterface(AfterCompletionInterface afterCompletionInterface) {
		this.afterCompletionInterface = afterCompletionInterface;
	}

	public void setPostHandleInterface(PostHandleInterface postHandleInterface) {
		this.postHandleInterface = postHandleInterface;
	}
}
