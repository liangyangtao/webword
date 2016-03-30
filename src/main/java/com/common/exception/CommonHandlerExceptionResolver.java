package com.common.exception;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 自定义异常解析
 * 
 * @author
 * 
 */
public class CommonHandlerExceptionResolver implements HandlerExceptionResolver {

	/**
	 * 自定义异常解析
	 * 
	 */
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception exception) {
		HandlerMethod hm = (HandlerMethod) object;
		Method method = hm.getMethod();
		String returnType = method.getReturnType().getName();
		if (returnType.equals("void")) {
			responseJson(response, exception);
		} else {
			System.out.println("出现错误1");
			return jsp(exception);
		}
		return null;
	}
	
	private ModelAndView jsp(Exception exception) {
		//ModelAndView mav = new ModelAndView("/exception/error");
		System.out.println("出现错误");
		String url = "redirect:/jumpErrorJsp.action";
		ModelAndView mav = new ModelAndView(url);
		return mav;
	}

	private void responseJson(HttpServletResponse response, Exception exception) {
		
	}
}
