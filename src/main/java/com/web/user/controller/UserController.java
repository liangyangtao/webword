package com.web.user.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.common.service.CommonController;
import com.database.bean.User;
import com.util.Md5Util;
import com.web.user.service.UserService;

@Controller
@RequestMapping(value="/user/")
public class UserController extends CommonController{
	
	private static final Logger LOGGER = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * 登录页面
	 * @author zm
	 * @date 
	 * @param userName 用户名 
	 * @param passWord 密码
	 */
	@RequestMapping(value="login",method=RequestMethod.GET)
	public String home(String type,String userName,String passWord,HttpSession session,ModelMap map){
		if(type!=null) System.out.println("type="+type);
		if(userName!=null) System.out.println("userName="+userName);
		if(passWord!=null) System.out.println("passWord="+passWord);
		if(userName==null){
			map.addAttribute("state","error");
			map.addAttribute("info","用户名不能为空");
		}else if (passWord==null){
			map.addAttribute("state","error");
			map.addAttribute("info","密码不为空");
		}else{
			String md5Str = Md5Util.getMD5Str(passWord);//MD5加密的 密码
			List<User> listUser = userService.login(userName, md5Str);
			if(listUser.size()==0){
				map.addAttribute("state","error");
				map.addAttribute("info","用户名和密码错误");
			}else if(listUser.size()==1){
				map.addAttribute("state","OK");
				map.addAttribute("info","登录成功");
				session.setAttribute("user", listUser.get(0));
				session.setAttribute("userId",listUser.get(0).getUserId());
				session.getAttribute("user");
				//return "/user/"
				return "redirect:/user/loginView.action";
			}else{
				map.addAttribute("state","error");
				map.addAttribute("info","用户名和密码错误,请检查");
			}
		}
		return "redirect:../index.jsp";
	}
	/**
	 * 登录页面
	 * @author zm
	 * @date 
	 * @param userName 用户名 
	 * @param passWord 密码
	 * @throws IOException 
	 */
	@RequestMapping(value="login",method=RequestMethod.POST)
	public void login(HttpServletResponse response,String type,String userName,String passWord,HttpSession session,ModelMap map) throws IOException{
		int userId=0;
		if(type!=null) System.out.println("type="+type);
		if(userName!=null) System.out.println("userName="+userName);
		if(passWord!=null) System.out.println("passWord="+passWord);
		if(userName==null){
			map.addAttribute("state","error");
			map.addAttribute("info","用户名不能为空");
		}else if (passWord==null){
			map.addAttribute("state","error");
			map.addAttribute("info","密码不为空");
		}else{
			String md5Str = Md5Util.getMD5Str(passWord);//MD5加密的 密码
			List<User> listUser = userService.login(userName, md5Str);
			if(listUser.size()==0){
				map.addAttribute("state","error");
				map.addAttribute("info","用户名和密码错误");
			}else if(listUser.size()==1){
				map.addAttribute("state","OK");
				map.addAttribute("info","登录成功");
				map.addAttribute("user",listUser.get(0));
				session.setAttribute("user", listUser.get(0));
				session.setAttribute("userId",listUser.get(0).getUserId());
				userId=listUser.get(0).getUserId();
				//session.getAttribute("user");
			}else{
				map.addAttribute("state","error");
				map.addAttribute("info","用户名和密码错误,请检查");
			}
		}
		
		try{
			LOGGER.info("urlName=user/login,urlMsg=用户登录,userName="+userName+",userId="+userId+",state="+map.get("state")+",info="+map.get("info"));
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		responseJson(response, map);
	}
	/*
	 * 登录结果
	 */
	@RequestMapping(value="loginView")
	public String loginView(String userName,String passWord,ModelMap map,HttpSession session){
		User user = (User) session.getAttribute("user");//用户信息
		int userId = (Integer) session.getAttribute("userId");
		map.addAttribute("user",user);
		map.addAttribute("userId",userId);
		return "/user/loginView";
	}
	/*
	 * 用户退出
	 */
	@RequestMapping(value="loginOut")
	public String loginOut(HttpSession session){
		try{
			int userId = (Integer) session.getAttribute("userId");
			LOGGER.info("urlName=user/loginOut,urlMsg=用户登出,userName="+userId);
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		session.removeAttribute("user");
		return "redirect:../index.jsp";
	}

}
