package com.web.view.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.service.CommonController;
import com.web.view.service.NewsHotSearchWordViewService;

@Controller
@RequestMapping(value = "/news/view/")
public class NewsHotSearchWordViewController extends CommonController {
	@Autowired
	NewsHotSearchWordViewService newsHotSearchWordViewService;
	private static Logger LOGGER = Logger
			.getLogger(NewsHotSearchWordViewController.class);

	/**
	 * 获取新闻热搜词
	 * 
	 * url
	 * http://xxxx:8080/webword/news/view/getHotSearchWord.do?from=0&pageSize=10
	 * 
	 * 返回结果： [{"id":1,"sword":"银行","doId":1,"wordType":0,"wordSource":"news",
	 * "searchCount"
	 * :0,"insertTime":"Feb 18, 2016 9:13:55 AM"},{"id":2,"sword":"宏观"
	 * ,"doId":1,"wordType":0,"wordSource":"news","searchCount":0,"insertTime":
	 * "Feb 18, 2016 9:14:09 AM"}]
	 * 
	 * @param response
	 * @param session
	 * @return json格式数据
	 */
	@RequestMapping(value = "getHotSearchWord")
	public String getHotSearchWord(HttpServletResponse response,
			HttpSession session, int from, int pageSize) {
		int userId = 0;
		if (session.getAttribute("userId") != null) {
			userId = (Integer) session.getAttribute("userId");
		}
		try {
			List<String> newsWords = newsHotSearchWordViewService.readHotWords(
					from, pageSize);
			responseJson(response, newsWords);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(
					"urlName=/news/view/getHotSearchWord,urlMsg=调用搜索热词出错,userId="
							+ userId, e);
		} finally {
			LOGGER.info("urlName=/news/view/getHotSearchWord,urlMsg=调用搜索热词,userId="
					+ userId);
		}
		return null;
	}

	/***
	 * 记录用户搜索日志
	 * 
	 * url http://XXXX:8080/webword/news/view/saveSearchWord.do?searchWord=%
	 * E5%93%88%E5%93%88%E5%93%88%E5%93%88
	 * 
	 * @param response
	 * @param session
	 * @param searchWord
	 */
	@RequestMapping(value = "saveSearchWord")
	public String saveSearchWord(HttpServletResponse response,
			HttpSession session, String searchWord) {
		int userId = 0;
		if (session.getAttribute("userId") != null) {
			userId = (Integer) session.getAttribute("userId");
		}
		try {
			int isSave = newsHotSearchWordViewService.saveSearchWord(
					searchWord, userId);
			String result = "";
			if (isSave > 0) {
				result = "success";
			} else {
				result = "error";
			}
			responseJson(response, result);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(
					"urlName=/news/view/saveSearchWord,urlMsg=用户搜索关键词失败,userId="
							+ userId, e);
		} finally {
			LOGGER.info("urlName=/news/view/saveSearchWord,urlMsg=用户搜索关键词,searchWord="
					+ searchWord + ",userId=" + userId);
		}
		return null;

	}

}
