package com.web.view.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.service.CommonController;
import com.database.bean.WordShoppingCart;
import com.database.bean.WordUsers;
import com.web.view.service.HomeService;
import com.web.view.service.ShoppingCartService;

@Controller
@RequestMapping(value = "/cart/")
public class ShoppingCartController extends CommonController {
	@Autowired
	HomeService homeService;
	@Autowired
	ShoppingCartService shoppingCartService;
	private static Logger LOGGER = Logger.getLogger(HomeController.class);
	/**
	 * 购物车页面
	 * @param response
	 */
	@RequestMapping(value = "/index")
	public String index(HttpServletResponse response, HttpSession session,
			Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		model.addAttribute("carts", shoppingCartService.getMyShoppingCart(user.getUserId()));
		try {
			LOGGER.info("urlName=cart/index,urlMsg=我的购物车,userId="+ user.getUserId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return "/cart/index";
	}
	
	/**
	 *加入购物车
	 * @param response
	 * @param wordShoppingCart
	 */
	@RequestMapping(value = "/add")
	public String add(HttpServletResponse response, HttpSession session,
			WordShoppingCart wordShoppingCart) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String,Object> map = new HashMap<String,Object>();
		if(user!=null){
			List<WordShoppingCart> carts = shoppingCartService.getMyShoppingCart(user.getUserId());
			if(carts!=null && carts.size()>=50){
				map.put("result","0");
				map.put("msg","购物车已满，不能超过50件！");
			}else{
				wordShoppingCart.setUserId(user.getUserId());
				wordShoppingCart.setCreateTime(new Date());
				wordShoppingCart.setPayFlag(0);
				map =  shoppingCartService.add(wordShoppingCart);
				int count = shoppingCartService.getMyShoppingCartCount(user.getUserId());
				map.put("count",count);
			}
			try {
				LOGGER.info("urlName=cart/add,urlMsg=加入购物车,userId="+ user.getUserId()+",resoure_type="+wordShoppingCart.getResoureType()+",journal_id="+wordShoppingCart.getJournalId()+",article_id="+wordShoppingCart.getArticleId());
				responseJson(response, map);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}
		return null;
	}
	
	/**
	 *删除购物车商品
	 * @param response
	 * @param cartIds
	 */
	@RequestMapping(value = "/del")
	public String del(HttpServletResponse response, HttpSession session,
			String cartIds) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String,Object> map = new HashMap<String,Object>();
		if(user!=null && cartIds!=null){
			shoppingCartService.del(cartIds,user.getUserId());
			map.put("result", "1");
			try {
				LOGGER.info("urlName=cart/add,urlMsg=删除购物车,userId="+ user.getUserId()+",cartIds="+cartIds);
				responseJson(response, map);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}
		return null;
	}
	
	/**
	 *预结算
	 * @param response
	 * @param cartIds
	 */
	@RequestMapping(value = "/prevPay")
	public String prevPay(HttpServletResponse response, HttpSession session,
			String cartIds) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		if(user!=null && cartIds!=null){
			//判断钱是否够支付
			Map<String,Object> map = shoppingCartService.prevPay(cartIds,user.getUserId());
			try {
				LOGGER.info("urlName=cart/prevPay,urlMsg=预结算,userId="+ user.getUserId()+",cartIds="+cartIds);
				responseJson(response, map);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}
		return null;
	}
	
	/**
	 *结算
	 * @param response
	 * @param name
	 */
	@RequestMapping(value = "/pay")
	public synchronized String pay(HttpServletResponse response, HttpSession session,
			String cartIds,Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		if(user!=null && cartIds!=null){
			Map<String,Object> map = shoppingCartService.pay(cartIds.replaceAll(",", "_"),user.getUserId());
			model.addAttribute("map",map);
			//成功
			if(map.get("result")!=null && ((Integer)map.get("result")).intValue()==1){
				model.addAttribute("carts",shoppingCartService.getMyShoppingCart(user.getUserId()));
				model.addAttribute("cartCount", shoppingCartService.getMyShoppingCartCount(user.getUserId()));
				LOGGER.info("urlName=cart/pay,urlMsg=结算成功,userId="+ user.getUserId()+",cartIds="+cartIds);
				return "cart/success";
			}else{
				return "cart/index";
			}
		}
		return "cart/index";
	}
}
