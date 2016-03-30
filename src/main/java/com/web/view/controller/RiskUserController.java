package com.web.view.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.service.CommonController;
import com.database.bean.Companyandbank;
import com.database.bean.Module;
import com.database.bean.MyModule;
import com.database.bean.UserOtherInfo;
import com.database.bean.UserOtherInfoExample;
import com.database.bean.WordUsers;
import com.database.dao.UserOtherInfoMapper;
import com.google.code.kaptcha.Constants;
import com.util.Md5Util;
import com.web.bean.ParamsBean;
import com.web.msg.service.SmsService;
import com.web.view.service.RiskUserService;
import com.web.view.service.RiskViewService;
import com.web.view.utils.CookieTool;
import com.web.view.utils.ValidImageEngine;

/**
 * @author 王昌鸿 E-mail: 25433567@qq.com
 * @version 创建时间：2015年8月6日 下午6:28:44
 */

@Controller
@RequestMapping(value = "/risk/view/")
public class RiskUserController extends CommonController{

	private static final Logger LOGGER = Logger.getLogger(RiskUserController.class);
	
	@Autowired
	RiskUserService riskUserService;
	@Autowired
	private RiskViewService riskViewService;
	@Autowired
	SmsService smsService;
	
	/*
	 * 进入登录页
	 */
	@RequestMapping(value = "/tologin")
	public String tologin(HttpServletResponse response, HttpSession session,
			Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		List<Module> listModule = riskViewService.getModulesByParentId(0);//获取一级栏目
		model.addAttribute("id", 0);
		model.addAttribute("user", user);
		model.addAttribute("listModule", listModule);
		return "/riskview/login";
	}
	/*
	 * 登录
	 */
	@RequestMapping(value="login",method=RequestMethod.POST)
	public void login(HttpServletResponse response,String type,String userName,String passWord,HttpSession session,ModelMap map){
		int userId=0;
		try {
			
			//if(type!=null) LOGGER.info("type:"+type);
			//if(userName!=null) LOGGER.info("userName:"+userName);
			//if(passWord!=null) LOGGER.info("passWord:"+passWord);
			if(userName==null){
				map.addAttribute("state","error");
				map.addAttribute("info","用户名不能为空");
			}else if (passWord==null){
				map.addAttribute("state","error");
				map.addAttribute("info","密码不为空");
			}else{
				String md5Str = Md5Util.getMD5Str(passWord);//MD5加密的 密码
				List<WordUsers> listUser = riskUserService.login(userName, md5Str);
				if(listUser==null){
					map.addAttribute("state","error");
					map.addAttribute("info","用户名和密码错误");
				}else if(listUser.size()==1){
					
					//如果自动登陆
					if(type.equals("1")){
						int loginMaxAge = 30*24*60*60;   //定义账户密码的生命周期，这里是一个月。单位为秒  
						CookieTool.addCookie(response, "loginName", userName,
								loginMaxAge); // 将账号加入到cookie中
						CookieTool.addCookie(response, "loginPwd", passWord,
								loginMaxAge); // 将密码加入到cookie中
					}else{ 
						// 清除Cookie
						CookieTool.addCookie(response, "loginName", null, 0);
						CookieTool.addCookie(response, "loginPwd", null, 0);
					}
					
					map.addAttribute("state","OK");
					map.addAttribute("info","登录成功");
					map.addAttribute("user",listUser.get(0));
					session.setAttribute("user", listUser.get(0));
					session.setAttribute("userId",listUser.get(0).getUserId());
					userId=listUser.get(0).getUserId();
					String userId1 = Integer.toString(userId);
					//加入试用期到期时间，是否是试用期
					int status=riskViewService.getUserRiskThemeStatus(listUser.get(0));
					String endTime =riskViewService.getExpirationDate(listUser.get(0));
					map.put("status", String.valueOf(status));
					map.put("endTime", endTime);
				
					
					riskViewService.insertLoginLog(listUser.get(0));
				}else{
					map.addAttribute("state","error");
					map.addAttribute("info","用户名和密码错误,请检查");
				}
			}
		} catch (Exception e1) {
			map.addAttribute("state","error");
			map.addAttribute("info","用户名和密码错误,请检查");
			LOGGER.error(e1);;
		}
		
		try {
			LOGGER.info("urlName=risk/view/login,urlMsg=用户登录,userName="+userName+",userId="+userId+",state="+map.get("state")+",info="+map.get("info"));
			responseJson(response, map);
			
		} catch (IOException e) {
			LOGGER.error(e);;
		}
	}
	
	/*
	 * 自动登陆
	 */
//	@RequestMapping(value="autologin",method=RequestMethod.POST)
//	public void autologin(HttpServletResponse response,String type,String userName,String passWord,HttpSession session,ModelMap map) throws IOException{
//		if(type!=null) System.out.println("type="+type);
//		if(userName!=null) System.out.println("userName="+userName);
//		if(passWord!=null) System.out.println("passWord="+passWord);
//		if(userName==null){
//			map.addAttribute("state","error");
//			map.addAttribute("info","用户名不能为空");
//		}else if (passWord==null){
//			map.addAttribute("state","error");
//			map.addAttribute("info","密码不为空");
//		}else{
//			String md5Str = Md5Util.getMD5Str(passWord);//MD5加密的 密码
//			boolean f = riskUserService.autologin(userName, md5Str);
//			if(f){
//				map.addAttribute("state","ok");
//			}else{
//				map.addAttribute("state","error");
//			}
//		}
//		responseJson(response, map);;
//	}	
	
	
	
	
	

	/*
	 * 进入注册页
	 */
	@RequestMapping(value = "/toregist")
	public String toregist(HttpServletResponse response, HttpSession session,
			Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		List<Module> listModule = riskViewService.getModulesByParentId(0);//获取一级栏目
		model.addAttribute("id", 0);
		model.addAttribute("user", user);
		model.addAttribute("listModule", listModule);
		return "/riskview/regist";
	}
	/*
	 * 注册
	 */
	@RequestMapping(value="/regist",method=RequestMethod.POST)
	public void regist(HttpServletResponse response,HttpServletRequest request,String type,HttpSession session,
			String user_email,String userPhone,String user_password,String password,String station,
			String responsibility,String interest,String province,String city,String verifyCode,
			String userName,Integer companyType,String companyName,Integer companyId,String companyName2,String subbranch){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		//String code = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);        //获取生成的验证码  
  //      LOGGER.debug("客户输入图形验证码：" + verifyCode + ",系统图形验证码：" + code);  
        
        //String code =	(String) request.getSession().getAttribute("v"); 
        
        //图形验证码 验证
  //      if(verifyCode.equals(code)) {  
        	if(checkValidImg(verifyCode,request)){
        	LOGGER.debug("图形验证码验证通过 ");  
        	//短信验证码 验证
        	boolean s = smsService.checkSecurityCode(userPhone, password);
    		if(!s){
    			map.put("result", 0);
    			map.put("msg", "短信验证码输入不正确");
    		}else{
    			//邮箱 验证
    			WordUsers u = riskUserService.login(user_email);
        		if (u != null) {
        			map.put("result", 0);
        			map.put("msg", "邮箱已注册请更换");
        		}else{
        			//手机 验证
        			WordUsers u1 = riskUserService.login(userPhone);
        			if (u1 != null) {
        				map.put("result", 0);
        				map.put("msg", "手机已注册请更换");
        			}else{
            			String pwMd5 = Md5Util.getMD5Str(user_password);// 密码加密
            			WordUsers user = new WordUsers();
            			user.setUserAccount(user_email);
            			user.setUserName(userName);
            			user.setUserEmail(user_email);
            			user.setUserPhone(userPhone);
            			user.setUserPassword(pwMd5);
            			user.setEnabled(1);
            			user.setIssys(0);
            			user.setDateAffect(new Date());
            			user=riskUserService.regist(user);
            			
            			MyModule myModule = new MyModule();
            			myModule.setCity(city);
            			myModule.setCreatedate(new Date());
            			myModule.setEmailflag(0);
            			myModule.setInterest(interest);
            			//myModule.setKeyword(keyword);
            			myModule.setModuleinfo("");
            			myModule.setPhoneflag(1);
            			myModule.setProvince(province);
            			myModule.setResponsibility(responsibility);
            			myModule.setStation(station);
            			myModule.setUserid(user.getUserId());
            			riskUserService.regist(myModule);
            			
            			
        				//添加用户企业信息
            			UserOtherInfo userOtherInfo =new UserOtherInfo();
            			userOtherInfo.setUserId(user.getUserId());
            			userOtherInfo.setCompanyInfo(companyName);
            			if(companyId!=null){
                			userOtherInfo.setUserEdit(subbranch);
                			Companyandbank companyandbank = riskUserService.getCompanyAndBank(companyId);
                			userOtherInfo.setCompanyType(companyandbank.getBctype());
            			}else{
                			userOtherInfo.setUserEdit(companyName2);
                			userOtherInfo.setCompanyType(companyType);
            			}
            			
            			userOtherInfo.setCompanyId(companyId);
            			riskUserService.insetCompanyInfo(userOtherInfo);
            			
            			
            			session.setAttribute("user", user);
            			session.setAttribute("userId", user.getUserId());
            			map.put("result", 1);
            			map.put("msg", "注册成功");
            		}
        		}
    		}
        }else{
			map.put("result", 0);
			map.put("msg", "图形验证码输入不正确");
        }
		
		try {
			responseJson(response, map);
			LOGGER.info("urlName=risk/view/toregist,urlMsg=注册,result="+map.get("result")+",msg="+map.get("msg"));
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}
	

	/*
	 * 进入用户中心
	 */
	@RequestMapping(value = "/touser")
	public String touser(HttpServletResponse response, HttpSession session,
			Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		if(user==null){
			return "redirect:/risk/view/home.do";
		}else{
			List<MyModule> list = riskUserService.getPersonData(user);
			MyModule  myModule = new MyModule();
			if(list.size()>0){
				myModule=list.get(0);
			}
			model.addAttribute("interest", myModule.getInterest());
			model.addAttribute("responsibility", myModule.getResponsibility());
			model.addAttribute("station", myModule.getStation());
			model.addAttribute("prov",myModule.getProvince());
			model.addAttribute("city",myModule.getCity());
			UserOtherInfo userOtherInfo = riskUserService.getUserOtherInfo(user.getUserId());
			model.addAttribute("userOtherInfo",userOtherInfo);
			if(userOtherInfo==null){
				model.addAttribute("registCompanyType",1);//userOtherInfo中没有改用户记录
			}else if(userOtherInfo.getCompanyId()!=null){
				model.addAttribute("registCompanyType",2);//userOtherInfo中用户企业信息用选择框中的信息
			}else{
				model.addAttribute("registCompanyType",3);//userOtherInfo中用户企业信息用输入框中信息
			}
			//model.addAttribute("registCompanyType",3);//userOtherInfo中没有改用户记录
			return "/riskview/user";
		}
	}
	/*
	 * 完善资料
	 */
	@RequestMapping(value = "/perfectdata", method = RequestMethod.POST)
	public void perfectdata(HttpServletResponse response,
			HttpServletRequest request, String type, HttpSession session,
			String user_email, String userPhone, String user_password,
			String password, String station, String responsibility,
			String interest, String province, String city, String user_name,
			Integer companyType,Integer companyId,String userEdit,String companyInfo,String subbranch){
		
		Map<String, Object> map = new HashMap<String, Object>();
		WordUsers user = (WordUsers) session.getAttribute("user");
		try {
			user.setUserName(user_name);
			riskUserService.perfectdata(user);
			List<MyModule> list = riskUserService.getPersonData(user);
			MyModule myModule = new MyModule();
			if(list.size()>0){
				myModule=list.get(0);
			}
			myModule.setCreatedate(new Date());
			myModule.setInterest(interest);
			myModule.setResponsibility(responsibility);
			myModule.setStation(station);
			myModule.setCity(city);
			myModule.setProvince(province);
			if(list.size()>0){
				riskUserService.perfectdata(myModule);	
			}else{
				myModule.setModuleinfo("");
				myModule.setUserid(user.getUserId());
				riskUserService.regist(myModule);
			}
			
			session.setAttribute("user", user);
			map.put("result", 1);
			
			//加入用户企业信息
			//UserOtherInfo userOtherInfo=riskUserService.getUserOtherInfo(user.getUserId());
			UserOtherInfo uoi=new UserOtherInfo();
			uoi.setCompanyId(companyId);
			if(companyId!=null){
				uoi.setUserEdit(subbranch);
				Companyandbank companyandbank = riskUserService.getCompanyAndBank(companyId);
				uoi.setCompanyType(companyandbank.getBctype());
			}else{
				uoi.setUserEdit(userEdit);
				uoi.setCompanyType(companyType);
			}
			uoi.setCompanyInfo(companyInfo);
			uoi.setUserId(user.getUserId());
			riskUserService.perfectUserOtherInfo(uoi);
			
		} catch (Exception e1) {
			map.put("result", 0);
			map.put("msg", "完善资料失败");
			LOGGER.error(e1);
		}
		
		try {
			LOGGER.info("urlName=risk/view/perfectdata,urlMsg=修改资料,userId="+user.getUserId());
			responseJson(response, map);
			
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}
	/*
	 * 修改密码
	 */
	@RequestMapping(value="/editpassword",method=RequestMethod.POST)
	public void editpassword(HttpServletResponse response, HttpSession session,
			String user_password_old, String user_password, Model model) {
				
		Map<String, Object> map = new HashMap<String, Object>();
		WordUsers user = (WordUsers) session.getAttribute("user");
		try {
			String pwMd5 = Md5Util.getMD5Str(user_password_old);// 密码加密
			if (!pwMd5.equals(user.getUserPassword())) {
				map.put("result", 0);
				map.put("msg", "原密码输入错误!");
			} else {
				pwMd5 = Md5Util.getMD5Str(user_password);// 密码加密
				user.setUserPassword(pwMd5);
				try {
					riskUserService.editpassword(user);
					session.setAttribute("user", user);
					map.put("result", 1);
				} catch (Exception e1) {
					map.put("result", 0);
					map.put("msg", "修改密码失败");
				}
			}
		} catch (Exception e1) {
			map.put("result", 0);
			map.put("msg", "修改密码失败");
			LOGGER.error(e1);
		}
		
		try {
			LOGGER.info("urlName=risk/view/editpassword,urlMsg=修改密码,userId="+user.getUserId());
			responseJson(response, map);
			
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}
	
	
	/*
	 * 找回密码第1步
	 */
	@RequestMapping(value = "/tofondpassword1")
	public String tofondpassword1(HttpServletResponse response, HttpSession session,
			Model model) {
		return "/riskview/fond_password1";
	}
	@RequestMapping(value="/fondpassword1",method=RequestMethod.POST)
	public void fondpassword1(HttpServletResponse response,HttpServletRequest request,String type,HttpSession session,
			String user_email,String userPhone,String user_password,String password,String station,
			String responsibility,String interest,String province,String city,String verifyCode){
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//String code = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);        //获取生成的验证码  
			//LOGGER.debug("客户输入图形验证码：" + verifyCode + ",系统图形验证码：" + code);  
			//图形验证码 验证
			if(checkValidImg(verifyCode,request)){
		//	if(verifyCode.equals(code)) {  
				LOGGER.debug("图形验证码验证通过 ");  
				//手机 验证
				WordUsers u1 = riskUserService.login(userPhone);
				if (u1 == null) {
					map.put("result", 0);
					map.put("msg", "该手机未注册");
				}else{
					session.setAttribute("fond1userPhone", userPhone);
					map.put("result", 1);
					map.put("msg", "找回密码第1步通过");
				}
			}else{
				map.put("result", 0);
				map.put("msg", "图形验证码输入不正确");
			}
		} catch (Exception e1) {
			map.put("result", 0);
			map.put("msg", "找回密码第1步失败");
			LOGGER.error(e1);
		} 
		
		try {
			LOGGER.info("urlName=risk/view/fondpassword1,urlMsg=找回密码,result="+map.get("result")+",msg="+map.get("msg"));
			responseJson(response, map);
			
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}
	/*
	 * 找回密码第2步
	 */
	@RequestMapping(value = "/tofondpassword2")
	public String tofondpassword2(HttpServletResponse response, HttpSession session,
			Model model) {
		return "/riskview/fond_password2";
	}
	@RequestMapping(value="/fondpassword2",method=RequestMethod.POST)
	public void fondpassword2(HttpServletResponse response,HttpServletRequest request,String type,HttpSession session,
			String user_email,String userPhone,String user_password,String password,String station,
			String responsibility,String interest,String province,String city,String verifyCode){
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//手机 验证
			String fond1userPhone = (String) session.getAttribute("fond1userPhone");
			WordUsers u1 = riskUserService.login(fond1userPhone);
			if (u1 == null) {
				map.put("result", 0);
				map.put("msg", "该手机未注册");
			}else{
	        	//短信验证码 验证
	        	boolean s = smsService.checkSecurityCode(fond1userPhone, password);
	    		if(!s){
	    			map.put("result", 0);
	    			map.put("msg", "短信验证码输入不正确");
	    		}else{
					map.put("result", 1);
					map.put("msg", "找回密码第2步通过");
	    		}
			}
		} catch (Exception e1) {
			map.put("result", 0);
			map.put("msg", "找回密码第2步失败");
			LOGGER.error(e1);
		} 
		try {
			LOGGER.info("urlName=risk/view/fondpassword2,urlMsg=找回密码,result="+map.get("result")+",msg="+map.get("msg"));
			responseJson(response, map);
			
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}
	/*
	 * 找回密码第3步
	 */
	@RequestMapping(value = "/tofondpassword3")
	public String tofondpassword3(HttpServletResponse response, HttpSession session,
			Model model) {
		return "/riskview/fond_password3";
	}
	@RequestMapping(value="/fondpassword3",method=RequestMethod.POST)
	public void fondpassword3(HttpServletResponse response,HttpServletRequest request,String type,HttpSession session,
			String user_email,String userPhone,String user_password,String password,String station,
			String responsibility,String interest,String province,String city,String verifyCode){
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String pwMd5 = Md5Util.getMD5Str(user_password);// 密码加密
			userPhone = (String) session.getAttribute("fond1userPhone");
			WordUsers user = riskUserService.login(userPhone);
			user.setUserPassword(pwMd5);
			riskUserService.editpassword(user);
			map.put("result", 1);
		} catch (Exception e1) {
			map.put("result", 0);
			map.put("msg", "找回密码第3步失败");
			LOGGER.error(e1);
		} 
		
		try {
			LOGGER.info("urlName=risk/view/fondpassword3,urlMsg=找回密码,result="+map.get("result")+",msg="+map.get("msg"));
			responseJson(response, map);
			
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}
	/*
	 * 找回密码第4步
	 */
	@RequestMapping(value = "/tofondpassword4")
	public String tofondpassword4(HttpServletResponse response, HttpSession session,
			Model model) {
		session.removeAttribute("fond1userPhone");
		return "/riskview/fond_password4";
	}	
	
	
	
//	/*
//	 * 退出
//	 */
//	@RequestMapping(value="loginOut")
//	public String loginOut(HttpSession session){
//		session.removeAttribute("user");
//		return "redirect:../index.jsp";
//	}
	/**
	 * 生成校验码
	 * 
	 */
	@RequestMapping(value="/getVerificationCode",method=RequestMethod.GET)
	public void getVerificationCode(HttpSession session,ModelMap map,HttpServletRequest request,HttpServletResponse response) throws IOException{
	
		response.setHeader("Cache-Control", "no-store");  
        response.setHeader("Pragma", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setContentType("image/jpeg");  
        ServletOutputStream out = response.getOutputStream();  
        String str = null;
        
        try { 
			ValidImageEngine v = new ValidImageEngine(response);
			str = v.createRandImage();
            out.flush();  
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {  
            out.close();
        }
        request.getSession().setAttribute("v", str);
	}
	/**
	 * 校验校验码是否正确
	 * @param valid
	 * @return
	 */
	protected boolean checkValidImg(String valid,HttpServletRequest request){
		 boolean b=false;
		 try {
			b=valid.equals(request.getSession().getAttribute("v"));
		} catch (Exception e) {
			b=false;
		}
		 return b;
	}
	
	/**
	 * 按企业名称搜索
	 */
	@RequestMapping(value="/searchCompanyAndBankList")
	@ResponseBody
	public void searchCompanyAndBankList(ParamsBean pb,HttpServletResponse response,HttpSession session,String name,int pageId,int pageSize){
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("status", "success");
		//int userId = (Integer) session.getAttribute("userId");
		//pb.setUserId(userId);
		List<Companyandbank> list=riskUserService.searchCompanyAndBankList(pb, name,pageId,pageSize);
		
		maps.put("list", list);
		
		maps.put("count", riskUserService.searchCompanyAndBankListCount(pb,name));
		maps.put("pageId", pageId);
		try {
			responseJson(response, maps);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
