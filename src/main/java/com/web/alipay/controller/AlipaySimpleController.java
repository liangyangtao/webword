package com.web.alipay.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.database.bean.Alipayasynchronouslog;
import com.database.bean.Alipaysynchronouslog;
import com.database.bean.WordUserMoney;
import com.database.bean.WordUserMoneyLog;
import com.web.alipay.service.RechargeService;
import com.web.alipay.util.AlipayNotify;
import com.web.alipay.util.InfoTransfer;
import com.web.view.controller.RiskMyController;
import com.web.view.service.ShoppingCartService;

@Controller
@RequestMapping(value = "/alipay/")
public class AlipaySimpleController {
	private static final Logger LOGGER = Logger.getLogger(AlipaySimpleController.class);
	@Autowired
	public RechargeService rechargeService;
	@Autowired
	ShoppingCartService shoppingCartService;
	/**
	 * 生成订单并发起支付
	 * 
	 */
	
	@RequestMapping(value = "/userorder")
	public String userorder(HttpServletRequest request,HttpServletResponse response, HttpSession session,
			Model model,Double rechargecount) throws Exception{
		if(session.getAttribute("userId")!=null){
			int userId = (Integer) session.getAttribute("userId");
			if(rechargecount>0){
				try {
							Map<String,String> params = new HashMap<String,String>();
							String orderno =  InfoTransfer.getorderno();
							params.put("orderno", orderno);
							params.put("userId", userId+"");
					//用户与订单绑定
							rechargeService.userbindingorder(params);
							model.addAttribute("orderno",orderno);
							model.addAttribute("total",rechargecount);
							LOGGER.info("urlName=view/userorder,urlMsg=用户充值,rechargecount="+rechargecount+",userId="+userId);
							return "/alipay/info";
				} catch (Exception e) {
					//用户与订单绑定失败，无法完成充值到出错页面
					LOGGER.error("urlName=view/userorder,urlMsg=用户充值过程用户与订单绑定失败,rechargecount="+rechargecount+",userId="+userId);
					//throw new RuntimeException();
					response.sendRedirect(request.getContextPath()+"/view/home.do");
				}
			}else{
				//充值不能小于0
						response.sendRedirect(request.getContextPath()+"/view/home.do");
				//throw new RuntimeException();
			}
			
		}else{
			//throw new RuntimeException();
						response.sendRedirect(request.getContextPath()+"/view/home.do");
		}
		
		return "";
	}
	
	
	
	
	/**
	 * 同步步通知
	 */
	@RequestMapping(value="/alipayreturn" ,method=RequestMethod.GET)
	public void alipayasynchronouslog(HttpServletRequest request,HttpServletResponse response, HttpSession session,
			Model model) throws Exception{
		LOGGER.info("接收到同步通知");
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		//验证通知合法性
		if(AlipayNotify.verify(params)){
			//插入日志
			rechargeService.updatealipayreturn(params);
				int userId = (Integer) session.getAttribute("userId");
				LOGGER.info("同步通知session中取userId=="+userId);
				if("TRADE_SUCCESS".equals(params.get("trade_status"))){
					//WordUserMoney usermoney = rechargeService.userrecharge(params);
						model.addAttribute("count", params.get("total_fee"));
						//model.addAttribute("usertotal",usermoney.getMoney());
						//request.setAttribute("carts", shoppingCartService.getMyShoppingCart(userId));
						//request.setAttribute("cartCount", shoppingCartService.getMyShoppingCartCount(userId));
					 //	return "/user/userrechargesuccess";
						response.sendRedirect(request.getContextPath()+"/alipay/rechargeresult.do?count="+params.get("total_fee")+"");
						return;
				}
			}else{
				LOGGER.info("同步通知验证不通过");
				response.sendRedirect(request.getContextPath()+"/view/home.do");
				return;
			}
		
		LOGGER.info("urlName=view/alipayreturn,urlMsg=支付宝同步跳转,userId="+(Integer) session.getAttribute("userId"));
		//return "error";
	}
	@RequestMapping(value="/rechargeresult" ,method=RequestMethod.GET)
	public String rechargeresult(HttpServletRequest request,HttpServletResponse response, HttpSession session,
			Model model,String count) throws Exception{
		try{
		if(null!=session.getAttribute("userId")){
			int userId = (Integer) session.getAttribute("userId");
			LOGGER.info("同步通知跳转后session中取userId=="+userId);
			model.addAttribute("count", count);
			model.addAttribute("carts",shoppingCartService.getMyShoppingCart(userId));
			model.addAttribute("cartCount", shoppingCartService.getMyShoppingCartCount(userId));
			return "/user/userrechargesuccess";
		}else{
			model.addAttribute("count", count);
			return "/user/userrechargesuccess";
		}
		}catch(Exception e){
			model.addAttribute("count", count);
			return "/user/userrechargesuccess";
		}
	}
	
	/**
	 *测试
	 */
	@RequestMapping(value="/sharetest" ,method=RequestMethod.GET)
	public String sharetest(HttpServletRequest request,HttpServletResponse response, HttpSession session,
			Model model,String count) throws Exception{
			String text = "<p>南方日报讯 自去年开展农村综合改革以来，全县各镇开始土地整合工作，大量土地整合后，用于发展规模种植业，而在石角镇黄花中华里村，他们另辟蹊径，整合村内15亩土地，挖鱼塘、设围栏、搭竹棚，在村里办起了养殖场，发展规模养殖产业。</p>";
			String titile="《商业银行风险管理季度要参》（2015年四季度)";
			model.addAttribute("text", text);
			model.addAttribute("title", titile);
			return "/sharetest/document_details_web";
	}

}
