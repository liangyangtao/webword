package com.web.view.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.service.CommonController;
import com.database.bean.WordUsers;
import com.web.view.service.DirectBuyService;

@Controller
@RequestMapping(value = "/buy/")
public class DirectBuyController extends CommonController {

	@Autowired
	private DirectBuyService directBuyService;
	private static Logger LOGGER = Logger.getLogger(DirectBuyController.class);
	
	
	/**
	 * 购买前验证
	 * @param response
	 * @param name
	 */
	@RequestMapping(value = "/prevBuy")
	public synchronized String prevBuy(HttpServletResponse response, HttpSession session, String resoureType, 
			Integer articleId, Integer journalId) {
		
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String,Object> map = new HashMap<String,Object>(); 
		if(user != null){
			try {
				map = directBuyService.prevBuy(resoureType, articleId, journalId, user.getUserId());
				responseJson(response, map);
				LOGGER.info("urlName=buy/prevBuy,urlMsg=购买前验证,userId="+ user.getUserId()+",resoureType="+resoureType+",articleId="+articleId+",journalId="+journalId);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			} 
		}
		return null;
	}
	
	/**
	 * 立即购买
	 * @param response
	 * @param name
	 */
	@RequestMapping(value = "/directBuy")
	public synchronized String directBuy(HttpServletResponse response, HttpSession session, String resoureType, 
			Integer articleId, Integer journalId) {
		
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String,Object> map = new HashMap<String,Object>(); 
		if(user != null){
			try {
				map = directBuyService.buy(resoureType, articleId, journalId, user.getUserId());
				responseJson(response, map);
				LOGGER.info("urlName=buy/directBuy,urlMsg=立即购买,userId="+ user.getUserId()+",resoureType="+resoureType+",articleId="+articleId+",journalId="+journalId);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			} 
		}
		return null;
	}
}
