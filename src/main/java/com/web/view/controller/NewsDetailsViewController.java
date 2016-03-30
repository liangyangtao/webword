package com.web.view.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.service.CommonController;
import com.database.bean.WordUsers;
import com.util.CommonUtils;
import com.web.bean.NewsSearchCondition;
import com.web.document.bean.News;
import com.web.view.service.NewsDetailsViewService;
import com.web.view.service.NewsUserFavoriteService;

@Controller
@RequestMapping(value = "/news/view/")
public class NewsDetailsViewController extends CommonController {
	@Autowired
	NewsDetailsViewService newsDetailsViewService;
	@Autowired
	NewsUserFavoriteService newsFavoriteService;

	@Value("${picUrl}")
	private String picUrl;

	/**
	 * 新闻详情
	 * 
	 * @param response
	 * @param session
	 * @param newsId
	 * @param model
	 * @param sectionName
	 * @return news.jsp
	 */
	@RequestMapping(value = "showNewsDetails")
	public String showNewsDetails(HttpServletResponse response,
			HttpSession session, String newsId, Model model,
			String sectionName, Integer plateId,Integer columnId) {
		News news = newsDetailsViewService.getNewsById(newsId);
		WordUsers user = (WordUsers) session.getAttribute("user");
		int userId = 0;
		if (user != null) {
			userId = user.getUserId();
		}
		if (newsFavoriteService.issaved(userId, Integer.parseInt(newsId))) {
			model.addAttribute("canShare", "Y");
		} else {
			model.addAttribute("canShare", "N");
		}
		if (news.getContent() == null) {
			news =null;
		}else{
			String searchImgReg = "(?x)(src|SRC|background|BACKGROUND)=('|\")(static.*?/)(.*?.(jpg|JPG|png|PNG|gif|GIF|jpeg|JPEG))('|\")";
			// 修改图片链接地址
			Pattern pattern = Pattern.compile(searchImgReg);
			Matcher matcher = pattern.matcher(news.getContent());
			StringBuffer replaceStr = new StringBuffer();
			while (matcher.find()) {
				matcher.appendReplacement(replaceStr, matcher.group(1) + "="
						+ picUrl + matcher.group(4) + "");// 逐个动态替换图片链接地址
			}
			if (sectionName == null || sectionName.isEmpty()) {
				sectionName = "";
			}
			matcher.appendTail(replaceStr);// 添加尾部
			String content = replaceStr.toString();
			content = content.replace("class=\"p_center\"",
					"class=\"p_center\" align=\"center\"");
			news.setContent(content);
		}
		model.addAttribute("news", news);
		model.addAttribute("sectionName", sectionName); // 面包屑
		model.addAttribute("plateId", plateId);
		model.addAttribute("columnId", columnId);
		model.addAttribute("date",news ==null?new Date(): new Date(news.getNewsDate()));

		// 请求ES
		/*
		 * List<News> newsList = null; newsList =
		 * newsDetailsViewService.moreLikeNews(newsId);
		 * if(CommonUtils.isEmpty(newsList)){ // 标题分词 String title =
		 * news.getTitle(); List<String> searchList =
		 * CommonUtils.analyzer(title);
		 * 
		 * // 请求参数封装 NewsSearchCondition searchInfo = new NewsSearchCondition();
		 * searchInfo.setShouldTitleWords(searchList); // 排除自身 List<String>
		 * mustNotCrawlId = new ArrayList<String>(); mustNotCrawlId.add(newsId);
		 * searchInfo.setMustNotCrawlId(mustNotCrawlId); // 请求ES newsList =
		 * newsDetailsViewService.getNewsList(0, 10, searchInfo); }
		 * 
		 * // 排除自己 for (News n : newsList) { if (newsId.equals(n.getCrawl_id()))
		 * { newsList.remove(n); } } model.addAttribute("moreLike",
		 * newsList.subList(0, (newsList.size() > 10 ? 10 : newsList.size())));
		 */
		return "/news/news";
	}

	@RequestMapping(value = "getLikeNewsById")
	public void getLikeNewsById(HttpServletResponse response,
			HttpSession session, String newsId) {
		try {
			News news = newsDetailsViewService.getNewsById(newsId);
			List<News> newsList = null;
			newsList = newsDetailsViewService.moreLikeNews(newsId);
			if (CommonUtils.isEmpty(newsList)) {
				// 标题分词
				String title = news.getTitle();
				List<String> searchList = CommonUtils.analyzer(title);

				// 请求参数封装
				NewsSearchCondition searchInfo = new NewsSearchCondition();
				searchInfo.setShouldTitleWords(searchList);
				// 排除自身
				List<String> mustNotCrawlId = new ArrayList<String>();
				mustNotCrawlId.add(newsId);
				searchInfo.setMustNotCrawlId(mustNotCrawlId);
				// 请求ES
				newsList = newsDetailsViewService
						.getNewsList(0, 10, searchInfo);
			}
			// 排除自己
			for (News n : newsList) {
				if (newsId.equals(n.getCrawl_id())) {
					newsList.remove(n);
				}
			}
			responseJson(
					response,
					newsList.subList(0,
							(newsList.size() > 10 ? 10 : newsList.size())));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}

}
