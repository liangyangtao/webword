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
import com.database.bean.NewsColumn;
import com.web.view.service.NewsHotColumnViewService;

@Controller
@RequestMapping(value = "/news/view/")
public class NewsHotColumnViewController extends CommonController {
	@Autowired
	NewsHotColumnViewService newsHotColumnViewService;
	private static Logger LOGGER = Logger
			.getLogger(NewsHotColumnViewController.class);

	/**
	 * 获取新闻热门栏目
	 * 
	 * url
	 * http://xxxx:8080/webword/news/view/getHotColumns.do
	 * 
	 * 
	 * @param response
	 * @param session
	 * @return json格式数据
	 */
	@RequestMapping(value = "getHotColumns")
	public String getHotColumns(HttpServletResponse response,
			HttpSession session) {
		int userId = 0;
		if (session.getAttribute("userId") != null) {
			userId = (Integer) session.getAttribute("userId");
		}
		try {
			List<NewsColumn> newsColumns = newsHotColumnViewService
					.getHotColumns();
			responseJson(response, newsColumns);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(
					"urlName=/news/view/getHotColumns,urlMsg=调用热门栏目出错,userId="
							+ userId, e);
		} finally {
			LOGGER.info("urlName=/news/view/getHotColumns,urlMsg=调用热门栏目,userId="
					+ userId);
		}
		return null;
	}
	
	

}
