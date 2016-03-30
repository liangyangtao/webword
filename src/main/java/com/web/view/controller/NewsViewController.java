package com.web.view.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chart.util.GsonUtil;
import com.common.service.CommonController;
import com.database.bean.NewsColumn;
import com.database.bean.NewsMyPlate;
import com.database.bean.NewsUserSearchLog;
import com.database.bean.WordUsers;
import com.util.CommonUtils;
import com.web.bean.Document;
import com.web.bean.NewsSearchCondition;
import com.web.document.bean.News;
import com.web.document.bean.NewsCrawls;
import com.web.view.service.NewsHotColumnViewService;
import com.web.view.service.NewsHotSearchWordViewService;
import com.web.view.service.NewsViewService;

@Controller
@RequestMapping(value = "/news/view/")
public class NewsViewController extends CommonController {
	@Autowired
	NewsViewService newsViewService;
	@Autowired
	NewsHotSearchWordViewService newsHotSearchWordViewService;
	@Autowired
	NewsHotColumnViewService newsHotColumnViewService;

	@Value("${newsMaxSize}")
	private int newsMaxSize;
	@Value("${newsSearchNum}")
	private int newsSearchNum;

	@Value("${picUrl}")
	private String picUrl;
	@Value("${oldPicUrl}")
	private String oldPicUrl;

	private int movepage = 4;
	private static Logger LOGGER = Logger.getLogger(NewsViewController.class);

	/**
	 * 首页
	 * 
	 * @param response
	 * @param name
	 */
	@RequestMapping(value = "/index")
	public String newIndex(HttpServletResponse response, HttpSession session,
			Model model) {
		// 获取用户
		WordUsers user = (WordUsers) session.getAttribute("user");
		// 用户栏目
		// 随机去一个栏目的条件
		String searchConditions = fillUserPlates(model, user);
		// List<String> newsWords = new ArrayList<String>();
		// 热搜词
		List<String> hotWords = fillHotSearchWord(model);
		if (hotWords.size() == 0) {
			hotWords.add("银行");
			hotWords.add("风险");
			hotWords.add("理财");
			hotWords.add("财经");
		}

		// 用户搜索记录
		List<String> newsWords = getUserWord(user);

		NewsSearchCondition newsSearchCondition = new NewsSearchCondition();
		// 新闻列表
		List<String> tempList = new ArrayList<String>();
		NewsCrawls newsResult = null;
		// 记录关键词
		String searchWords = "";
		// 如果用户的搜索词为空
		if (newsWords.size() == 0 && searchConditions != null) {

			// 随机去一个栏目的条件显示
			newsResult = newsViewService.searchNewsCrawls(1, newsSearchNum,
					searchConditions);
			searchWords = searchConditions;
		} else {
			if (newsWords.size() < 5) {
				newsWords.addAll(hotWords);
			}
			// 第一个词
			int index = (int) (Math.random() * newsWords.size());
			// 调用分词器
			tempList.addAll(CommonUtils.analyzer(newsWords.get(index)));

			// 第二个ci ----------------
			int index2 = (int) (Math.random() * newsWords.size());
			while (true) {
				if (index2 == index) {
					index2 = (int) (Math.random() * newsWords.size());
				} else {
					break;
				}
			}
			// 调用分词器
			tempList.addAll(CommonUtils.analyzer(newsWords.get(index2)));
			// 第三个词 -----------------
			int index3 = (int) (Math.random() * newsWords.size());
			while (true) {
				if (index3 == index || index3 == index2) {
					index3 = (int) (Math.random() * newsWords.size());
				} else {
					break;
				}
			}
			// 调用分词器
			tempList.addAll(CommonUtils.analyzer(newsWords.get(index3)));
			// -------------------------
			for (String str : tempList) {
				searchWords += CommonUtils.isEmpty(searchWords) ? str : "_"
						+ str;
			}
			// 搜索标题和内容
			newsSearchCondition.setShouldContentWords(tempList);
			newsSearchCondition.setShouldTitleWords(tempList);
			// 新闻详情
			newsResult = newsViewService.getNewsList(0, newsSearchNum,
					newsSearchCondition);

		}

		if (CommonUtils.isNotEmpty(newsResult)) {
			model.addAttribute("newsList", newsResult);
			int count = newsResult.getData().size();
			if (newsSearchNum > count) {
				// more隐藏标记
				model.addAttribute("hideFlag", "true");
			}
			model.addAttribute("count", count);
			model.addAttribute("crawlIds", newsResult.getCrawlIds());
		}
		// 已获取的新闻ID
		Object crawlIds = session.getAttribute("crawlIds");
		if (CommonUtils.isNotEmpty(crawlIds)) {
			String tempStr = crawlIds.toString();
			String[] ids = tempStr.split("_");
			List<String> idsList = Arrays.asList(ids);
			newsSearchCondition.setMustNotCrawlId(idsList);
		}
		// 请求次数
		Object page = session.getAttribute("pageIndex");
		if (CommonUtils.isNotEmpty(page)) {
			int pageIndex = Integer.parseInt(String.valueOf(page));
			model.addAttribute("pageIndex", pageIndex);
		} else {
			model.addAttribute("pageIndex", 1);
		}
		model.addAttribute("user", user);
		model.addAttribute("searchWords", searchWords);
		model.addAttribute("newsMaxSize", newsMaxSize);
		model.addAttribute("picUrl", picUrl);
		model.addAttribute("oldPicUrl", oldPicUrl);
		// 热门栏目
		List<NewsColumn> newsColumns = newsHotColumnViewService.getHotColumns();
		fillHotColumn(model, user, newsColumns);
		return "/news/index";
	}

	private List<String> getUserWord(WordUsers user) {
		List<String> userSearchWords = new ArrayList<String>();
		Set<String> c = new HashSet<String>();
		if (user != null) {
			List<NewsUserSearchLog> userSearchLogs = newsHotSearchWordViewService
					.getUserSearchWord(user.getUserId());
			for (NewsUserSearchLog newsUserSearchLog : userSearchLogs) {
				c.add(newsUserSearchLog.getSearchWord());
				if (c.size() > 20) {
					break;
				}
			}
		}
		userSearchWords.addAll(c);
		return userSearchWords;
	}

	/**
	 * 热搜栏目
	 * 
	 * @param model
	 */
	private void fillHotColumn(Model model, WordUsers user,
			List<NewsColumn> newsColumns) {
		// List<NewsColumn> newsColumns =
		// newsHotColumnViewService.getHotColumns();
		int userId = 0;
		if (user != null) {
			userId = user.getUserId();
		} else {
			userId = 1;
		}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (NewsColumn newsColumn : newsColumns) {
			newsColumn.getId();
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("data", newsColumn);

			boolean isHas = newsHotColumnViewService.isHasColumn(userId,
					newsColumn.getId());
			if (isHas) {
				temp.put("isAdd", "Y");
			} else {
				temp.put("isAdd", "N");
			}
			result.add(temp);
		}
		model.addAttribute("newsColumns", result);
	}

	/**
	 * 热搜词
	 * 
	 * @param model
	 * @return
	 */
	private List<String> fillHotSearchWord(Model model) {
		// 热搜关键词
		List<String> newsWords = newsHotSearchWordViewService.readHotWords(0,
				10);
		model.addAttribute("newsWords", newsWords);
		return newsWords;
	}

	/**
	 * 用户导航栏目
	 * 
	 * @param model
	 * @param user
	 */
	private String fillUserPlates(Model model, WordUsers user) {
		List<NewsMyPlate> newsMyPlates = null;
		// 用户栏目
		if (user != null) {
			newsMyPlates = newsHotColumnViewService.getMyPLates(user
					.getUserId());
		} else {
			newsMyPlates = newsHotColumnViewService.getMyPLates(1);
		}
		model.addAttribute("newsMyPlates", newsMyPlates);

		int index = (int) (Math.random() * newsMyPlates.size());
		if (CommonUtils.isNotEmpty(newsMyPlates)) {
			return newsMyPlates.get(index).getConditions();
		}
		return null;
	}

	/**
	 * 推荐 更多
	 * 
	 * @param response
	 * @param request
	 * @param param
	 * @throws IOException
	 */
	@RequestMapping(value = "/searchMore")
	@ResponseBody
	public void searchMore(HttpServletResponse response,
			HttpServletRequest request, ParamBean param, HttpSession session)
			throws IOException {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String, Object> maps = new HashMap<String, Object>();
		List<String> newsWords = getUserWord(user);
		NewsCrawls newsResult = null;
		int size = 0;
		int pageIndex = param.getPageIndex();
		String searchWords = param.getSearchWords();

		if (newsWords.size() == 0
				&& (searchWords.contains("must") || searchWords
						.contains("should"))) {

			// 请求次数
			// 起始数
			int startNum = pageIndex * newsSearchNum;
			// 查询量
			size = newsMaxSize - param.getCount() > newsSearchNum ? newsSearchNum
					: newsMaxSize - param.getCount();
			newsResult = newsViewService.searchNewsCrawls(startNum, size,
					searchWords);
		} else {
			// 新闻列表
			NewsSearchCondition newsSearchCondition = new NewsSearchCondition();
			// 热搜词
			// String searchWords = param.getSearchWords();
			if (CommonUtils.isNotEmpty(searchWords)) {
				String[] words = searchWords.split("_");
				List<String> tempList = Arrays.asList(words);

				newsSearchCondition.setShouldContentWords(tempList);
				newsSearchCondition.setShouldTitleWords(tempList);
			}
			// 已获取的新闻ID
			String crawlIds = param.getCrawlIds();
			if (CommonUtils.isNotEmpty(crawlIds)) {
				String[] ids = crawlIds.split("_");
				List<String> idsList = Arrays.asList(ids);
				newsSearchCondition.setMustNotCrawlId(idsList);
			}

			// 请求次数
			pageIndex = param.getPageIndex();

			// 起始数
			int startNum = pageIndex * newsSearchNum;
			// 查询量
			size = newsMaxSize - param.getCount() > newsSearchNum ? newsSearchNum
					: newsMaxSize - param.getCount();
			// 新闻详情
			newsResult = newsViewService.getNewsList(startNum, size,
					newsSearchCondition);
		}

		if (CommonUtils.isNotEmpty(newsResult)) {
			maps.put("newsList", newsResult);
			int count = newsResult.getData().size();
			// more隐藏标记
			if (size > count || newsMaxSize == param.getCount() + count) {
				maps.put("hideFlag", true);
			}

			maps.put("count", count);
			maps.put("crawlIds", newsResult.getCrawlIds());

		} else {
			// more隐藏标记
			maps.put("hideFlag", true);
		}

		maps.put("pageIndex", ++pageIndex);

		String json = GsonUtil.getJsonStringFromObject(maps);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("application/json; charset=utf-8");
		response.getWriter().print(json);
		response.getWriter().close();
	}

	/**
	 * 搜索
	 * 
	 * @param model
	 * @param keyword
	 * @param type
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/search")
	public String search(HttpSession session, Model model, String keyword,
			String searchType, String articleFormat, Integer pageNo,
			Integer pageSize, Integer startPage) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		int userId = 0;
		if (user != null) {
			userId = user.getUserId();
		}
		if (searchType != null && "TITLE".equals(searchType)) {
			searchType = "TITLE";
		} else {
			searchType = "ALL";
		}

		if (articleFormat != null && "TIME".equals(articleFormat)) {
			articleFormat = "TIME";
		} else {
			articleFormat = "SCORE";
		}
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = 20;
		}
		// 分词
		List<String> analyList = new ArrayList<String>();
		if (CommonUtils.isNotEmpty(keyword)) {
			analyList = CommonUtils.analyzer(keyword);
		}
		if (CommonUtils.isEmpty(analyList)) {
			analyList.add(keyword);
		}
		
		NewsCrawls newsList = null;

		if(articleFormat.equals("SCORE")){
			newsList = newsViewService.searchByScore(keyword, searchType,
					articleFormat, pageNo, pageSize);
		}else{
			newsList = newsViewService.search(analyList, searchType,
					articleFormat, pageNo, pageSize);
		}

		List<News> result = null;
		if (newsList.getCount() > 0) {
			// 过滤重复新闻
			LinkedHashMap<String, News> newsMap = new LinkedHashMap<String, News>();
			result = newsList.getData();

			for (News news : result) {
				if (!newsMap.containsKey(news.getTitle())) {
					newsMap.put(news.getTitle(), news);
				}
			}

			if (CommonUtils.isNotEmpty(newsMap)) {
				result = new ArrayList<News>();
				for (News v : newsMap.values()) {
					result.add(v);
				}
			}

			newsList.setData(result);
		}

		Integer count = newsList.getCount();
		Integer pageCount = ((count % pageSize == 0) ? (count / pageSize)
				: ((count / pageSize) + 1));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startPage", (startPage == null ? 1 : startPage));
		map.put("endPage", Math.min((startPage == null ? 1 : startPage)
				+ movepage, pageCount));
		map.put("movepage", movepage);
		map.put("count", count);
		map.put("pageSize", pageSize);
		map.put("pageNo", pageNo);
		map.put("pageCount", pageCount);
		model.addAttribute("page", map);
		// ？？？
		model.addAttribute("movepage", movepage);
		// 新闻列表
		model.addAttribute("newsList", newsList);
		// 搜索词
		model.addAttribute("keyword", keyword);
		// 搜标题还全文
		model.addAttribute("searchType", searchType);
		// 按照时间排序还是焦点
		model.addAttribute("articleFormat", articleFormat);
		model.addAttribute("analyList",
				GsonUtil.getJsonStringFromObject(analyList));
		// 热搜词
		fillHotSearchWord(model);
		// 相关文档
		List<Document> likeDocuments = newsViewService
				.getLikeDocument(analyList);
		model.addAttribute("picUrl", picUrl);
		model.addAttribute("oldPicUrl", oldPicUrl);
		model.addAttribute("likeDocuments", likeDocuments);
		// 日志
		newsHotSearchWordViewService.saveSearchWord(keyword, userId);
		LOGGER.info("urlName=view/search,urlMsg=用户搜索模板和文档,keyword=" + keyword
				+ ",searchType=" + searchType + ",userId=" + userId);
		return "/news/search";
	}

	/**
	 * 搜索
	 * 
	 * @param model
	 * @param keyword
	 * @param type
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/plate")
	public String plate(HttpSession session, Model model, Integer plateId,
			Integer columnId, Integer pageNo, Integer pageSize,
			Integer startPage) {
		// &columnId=${newsMyPlate.columnId}
		WordUsers user = (WordUsers) session.getAttribute("user");
		int userId = 0;
		if (user != null) {
			userId = user.getUserId();
		}
		// 用户
		model.addAttribute("user", user);
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = 20;
		}
		if (plateId == null) {
			plateId = 0;
			// 表明是热门栏目
		}
		if (columnId == null) {
			columnId = 0;
		}
		NewsMyPlate newsMyPlate = newsViewService.getNewsMyPlate(plateId);
		List<NewsColumn> newsColumns = newsHotColumnViewService.getHotColumns();
		NewsCrawls newsList = null;
		if (newsMyPlate == null) {
			if (columnId != 0) {
				// 说明是系统栏目
				NewsColumn newsColumn = newsViewService
						.getNewsColumnById(columnId);
				// 说明没有订阅
				if (newsHotColumnViewService.isHasColumn(userId, columnId)) {
					model.addAttribute("isOrder", "Y");

				} else {
					model.addAttribute("isOrder", "N");
				}
				newsList = newsViewService.search(newsColumn, pageNo, pageSize);
				newsMyPlate = new NewsMyPlate();
				newsMyPlate.setPlateName(newsColumn.getColumnName());
				newsMyPlate.setColumnId(newsColumn.getId());
			} else {
				// 自定义栏目 删除后不可查看
			}

		} else {
			if (newsHotColumnViewService.isHasColumn(userId, columnId)) {
				model.addAttribute("isOrder", "Y");
			} else {
				model.addAttribute("isOrder", "N");
			}
			newsList = newsViewService.search(newsMyPlate, pageNo, pageSize);
		}
		if (userId == 0) {
			userId = 1;

			for (NewsColumn newsColumn : newsColumns) {
				if (newsMyPlate.getColumnId() == newsColumn.getId()) {
					boolean isHas = newsHotColumnViewService.isHasColumn(
							userId, newsColumn.getId());
					if (isHas) {
						model.addAttribute("isOrder", "Y");
					} else {
						model.addAttribute("isOrder", "N");
					}
					break;
				} else {
					model.addAttribute("isOrder", "N");
				}
			}
			List<NewsMyPlate> newsMyPlates = newsHotColumnViewService
					.getMyPLates(1);
			for (NewsMyPlate newsMyPlate2 : newsMyPlates) {
				if (newsMyPlate.getColumnId() == newsMyPlate2.getColumnId()) {
					model.addAttribute("isOrder", "Y");
					break;
				} else {
					model.addAttribute("isOrder", "N");
				}

			}
		}

		Integer count = newsList.getCount();
		Integer pageCount = ((count % pageSize == 0) ? (count / pageSize)
				: ((count / pageSize) + 1));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startPage", (startPage == null ? 1 : startPage));
		map.put("endPage", Math.min((startPage == null ? 1 : startPage)
				+ movepage, pageCount));
		map.put("movepage", movepage);
		map.put("count", count);
		map.put("pageSize", pageSize);
		map.put("pageNo", pageNo);
		map.put("pageCount", pageCount);
		model.addAttribute("page", map);

		// 当前栏目信息
		model.addAttribute("nowPlate", newsMyPlate);
		// 新闻列表
		model.addAttribute("newsList", newsList);
		// 用户导航栏
		fillUserPlates(model, user);
		// 热搜词
		fillHotSearchWord(model);
		// 热门栏目

		fillHotColumn(model, user, newsColumns);
		// 日志
		LOGGER.info("urlName=view/plate,urlMsg=用户点击了栏目,plateId=" + plateId
				+ ",userId=" + userId);
		return "/news/plate";
	}

}
