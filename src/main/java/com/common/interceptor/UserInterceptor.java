package com.common.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.database.bean.WordPlate;
import com.database.bean.WordUsers;
import com.web.view.service.HomeService;
import com.web.view.service.ShoppingCartService;

/**
 * 请求拦截器
 * 
 * @ClassName: UserInterceptor
 * @Description: TODO
 * @author zyh
 * @date 2014-12-18 下午5:27:23
 * 
 */
public class UserInterceptor implements HandlerInterceptor {
	
	@Autowired
	private HomeService homeService;
	@Autowired
	private ShoppingCartService shoppingCartService;
	/**
	 * 当前请求完成操作 完全处理完请求后被调用,可用于清理资源等 Title: afterCompletion Description:()
	 * 
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @throws Exception
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object,
	 *      java.lang.Exception)
	 */
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	/**
	 * 请求之前结果返回true时请求 在业务处理器处理请求执行完成后,生成视图之前执行 Title: postHandle Description:()
	 * 
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @throws Exception
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object,
	 *      org.springframework.web.servlet.ModelAndView)
	 */
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}

	/**
	 * 请求之前操作 在业务处理器处理请求之前被调用 Title: preHandle Description:()
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		WordUsers user = (WordUsers) session.getAttribute("user");
		// 实现灵活配置处理业务请求跳转过滤;请求路径规范统一，方便处理:以下是业务不需要校验
		int dl = request.getRequestURI().indexOf("/view/");
		//判断是否是ajax
		boolean isAjax = false;
		if(request.getHeader("x-requested-with") != null&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
			isAjax = true;
		}
		if(!isAjax){
			List<WordPlate> list = homeService.getUserPlate(user==null?1:user.getUserId());
			request.setAttribute("list", list);
		}
		//需要登录的
		if (user == null && (dl <0 || request.getRequestURI().indexOf("download")>=0 || request.getRequestURI().indexOf("view/news.do")>=0)) {
			if(isAjax){
				response.setHeader("sessionstatus", "timeout");
			}else{
				response.sendRedirect(request.getContextPath()+"/view/index.do");
			}
			return false;
		}else if (user != null){
			//已登录验证sessionId
			String sessionId = session.getId();
			WordUsers usr = homeService.login(user.getUserAccount());
			//正常用户
			if (usr != null && usr.getEnabled()!=null && usr.getEnabled().intValue()==1){
				//sessionId验证未通过
				if (!sessionId.equals(usr.getSessionId())){
					session.removeAttribute("user");
					if(isAjax){
						response.setHeader("sessionstatus", "reload");
					}else{
						response.sendRedirect(request.getContextPath()+"/jsp/repeatLogin.jsp");
					}
					return false;
				}else{
					request.setAttribute("user", user);
					request.setAttribute("cartCount", shoppingCartService.getMyShoppingCartCount(user.getUserId()));
				}
			}else{
				//禁用账户
				session.removeAttribute("user");
				if(isAjax){
					response.setHeader("sessionstatus", "timeout");
				}else{
					response.sendRedirect(request.getContextPath()+"/view/index.do");
				}
				return false;
			}
		}
		return true;
	}
}
