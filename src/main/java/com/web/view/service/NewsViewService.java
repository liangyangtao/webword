package com.web.view.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chart.util.GsonUtil;
import com.database.bean.NewsColumn;
import com.database.bean.NewsMyPlate;
import com.database.dao.NewsColumnMapper;
import com.database.dao.NewsMyPlateMapper;
import com.google.gson.Gson;
import com.util.CommonUtils;
import com.web.bean.Condition;
import com.web.bean.Document;
import com.web.bean.DocumentSearchCondition;
import com.web.bean.NewsCondition;
import com.web.bean.NewsSearchCondition;
import com.web.bean.SearchDocumentData;
import com.web.document.bean.News;
import com.web.document.bean.NewsCrawls;
import com.web.utils.Fetcher;
import com.web.utils.HttpClientBuilder;

@Service
public class NewsViewService {
	private static Logger logger = Logger.getLogger(NewsViewService.class);
	@Value("${newsByJson}")
	private String newsByJson;
	@Value("${searchByJsonStr}")
	private String searchByJsonStr;
	@Value("${newsSearchByMultiJsonStr}")
	private String newsSearchByMultiJsonStr;
	
//	@Value("${searchNews}")
//	private String searchNews;
	private final int SUM = 1000;
	private final int BASE_NUM = 200;
	
	private final int AGS [] = new int []{1,3,7,30,90,180, 365 , 365*2, 365*3};

	@Autowired
	private NewsMyPlateMapper newsMyPlateMapper;

	@Autowired
	private NewsColumnMapper newsColumnMapper;

	/**
	 * 获取新闻列表
	 * 
	 * @param from
	 * @param pageSize
	 * @param searchcondition
	 * @return
	 */
	public NewsCrawls getNewsList(int from, int pageSize,
			NewsSearchCondition searchcondition) {
		NewsCrawls newsList = new NewsCrawls();
		Map<String, String> map = new HashMap<String, String>();
		if (searchcondition == null) {
			searchcondition = new NewsSearchCondition();
		}
		if (from == 0) {
			from = 0;
		}
		if (pageSize == 0) {
			pageSize = 8;
		}
		// NewsSearchCondition
		Gson gson = new Gson();
		map.put("from", from + "");
		map.put("pageSize", pageSize + "");
		map.put("jsonStr", gson.toJson(searchcondition));
		String json = getHtml(map, newsByJson);
		if (json != null && !json.equals("")) {
			newsList = gson.fromJson(json, NewsCrawls.class);
		}
		return newsList;
	}

	private String getHtml(Map<String, String> params, String url) {
		PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
		BasicCookieStore cookieStore = new BasicCookieStore();
		HttpClientBuilder httpClientBuilder = new HttpClientBuilder(false,
				poolingHttpClientConnectionManager, cookieStore);
		CloseableHttpClient httpClient = httpClientBuilder.getHttpClient();
		Fetcher fetcher1 = new Fetcher(cookieStore, httpClient);
		String html = null;
		try {
			logger.warn("与ES交互参数" + params);
			logger.warn("与ES交互URL" + url);
			html = fetcher1.post(url, params, null, "utf-8");
		} catch (Exception e) {
			logger.error("与ES交互异常" + e);
		}
		return html;
	}

	public NewsCrawls search(List<String> analyList, String searchType,
			String articleFormat, Integer pageNo, Integer pageSize) {
		NewsCrawls newsList = new NewsCrawls();

		String from = null;
		if (pageNo == 1) {
			from = "0";
		} else {
			from = (pageNo - 1) * pageSize + "";
		}

		Map<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();

		NewsSearchCondition searchcondition = new NewsSearchCondition();

		if (searchType.equals("ALL")) {
			if (analyList.size() > 1) {
				searchcondition.setShouldTitleWords(analyList);
				searchcondition.setShouldContentWords(analyList);
			} else {
				searchcondition.setMustTitleWords(analyList);
				searchcondition.setMustContentWords(analyList);
			}
		} else {
			if (analyList.size() > 1) {
				searchcondition.setShouldTitleWords(analyList);
			} else {
				searchcondition.setMustTitleWords(analyList);
			}

		}

		map.put("from", from + "");
		map.put("pageSize", pageSize + "");
		map.put("jsonStr", gson.toJson(searchcondition));

		Calendar c = Calendar.getInstance();
		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + 10);
		long end = c.getTime().getTime();
		c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);
		long start = c.getTime().getTime();

		map.put("startTime", String.valueOf(start));
		map.put("endTime", String.valueOf(end));
		map.put("orderByColumn", articleFormat);
		map.put("highlight", "true");
		String json = getHtml(map, newsByJson);
		if (CommonUtils.isNotEmpty(json)) {
			newsList = gson.fromJson(json, NewsCrawls.class);
			newsList.setCountResult(newsList.getCount());
		}
		return newsList;
	}

	public List<Document> getLikeDocument(List<String> analyList) {
		Map<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		DocumentSearchCondition sc = new DocumentSearchCondition();
		sc.setArticleType("document");
		sc.setShouldWordsOfArticleName(analyList);
		// sc.setShouldArticleLabels(analyList);
		Calendar c = Calendar.getInstance();
		long end = c.getTime().getTime();
		sc.setEndPassTime(end);
		c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);
		long start = c.getTime().getTime();
		sc.setStartPassTime(start);

		map.put("jsonStr", gson.toJson(sc));
		map.put("pageSize", "30");
		map.put("fullContent", "false");
		map.put("orderByField", "passTime");
		map.put("order", "DESC");

		String json = getHtml(map, searchByJsonStr);
		if (CommonUtils.isNotEmpty(json)) {
			SearchDocumentData data = gson.fromJson(json, SearchDocumentData.class);
			if(data.getCount() == 0){
				map.put("orderByField", "SCORE");
				sc.setStartPassTime(0L);
				sc.setEndPassTime(0L);
				map.put("jsonStr", gson.toJson(sc));
				json = getHtml(map, searchByJsonStr);
				data = gson.fromJson(json, SearchDocumentData.class);
			}
			List<Document> result = data.getData();
			Map<String, Document> resultMap = new HashMap<String, Document>();
			for (Document document : result) {
				String tempJournalId = document.getArticleJournalId();
				int articleId = document.getArticleId();
				if (CommonUtils.isNotEmpty(tempJournalId) && Integer.parseInt(tempJournalId) != 0) {
					resultMap.put("journalId_" + tempJournalId, document);
				} else if (articleId != 0) {
					resultMap.put("articleId_" + document.getArticleId(), document);
				}

			}
			int i = 0;
			List<Document> resultList = new ArrayList<Document>();
			for (Document doc : resultMap.values()) {
				if (i == 8) {
					break;
				}
				resultList.add(doc);
				i++;
			}
			return resultList;
		}
		return null;
	}

	public NewsColumn getNewsColumnById(Integer id) {

		return newsColumnMapper.selectByPrimaryKey(id);
	}

	public NewsCrawls search(NewsColumn newsColumn, Integer pageNo,
			Integer pageSize) {
		String searchConditions = newsColumn.getConditions();
		NewsCrawls newsList = searchNewsCrawls(pageNo, pageSize,
				searchConditions);
		return newsList;
	}

	public NewsCrawls search(NewsMyPlate newsMyPlate, Integer pageNo,
			Integer pageSize) {
		String searchConditions = newsMyPlate.getConditions();
		NewsCrawls newsList = searchNewsCrawls(pageNo, pageSize,
				searchConditions);
		return newsList;
	}

	public NewsCrawls searchNewsCrawls(Integer pageNo, Integer pageSize,
			String searchConditions) {
		// 得到查询条件
		Gson gson = new Gson();
		if (searchConditions == null || searchConditions.isEmpty()) {
			searchConditions = "{\"mustTagNames\":\"\",\"mustNotTagNames\":\"\",\"shouldTagNames\":\"\",\"mustWordNames\":\"\",\"mustNotWordNames\":\"\",\"shouldWordNames\":\"银行\",\"flag\":1,\"source\":\"\"}";
		}
		Condition condition = gson.fromJson(searchConditions, Condition.class);
		NewsCrawls newsList = new NewsCrawls();
		String from = null;
		if (pageNo == 1) {
			from = "0";
			// groupParam.setFrom(0);
		} else {
			from = (pageNo - 1) * pageSize + "";
		}
		List<NewsSearchCondition> searchCondition = new ArrayList<NewsSearchCondition>();
		Map<String, String> map = new HashMap<String, String>();
		NewsSearchCondition searchcondition = new NewsSearchCondition();
		NewsSearchCondition scc = new NewsSearchCondition();

		if (StringUtils.isNotBlank(condition.getMustTagNames())) {
			List<String> l = new ArrayList<String>();
			for (String s : condition.getMustTagNames().split("_| |	")) {
				l.add(s);
			}
			searchcondition.setMustTagNames(l);
			scc.setMustTagNames(l);
		}
		if (StringUtils.isNotBlank(condition.getShouldTagNames())) {
			List<String> l = new ArrayList<String>();
			for (String s : condition.getShouldTagNames().split("_| |	")) {
				l.add(s);
			}
			searchcondition.setShouldTagNames(l);
			scc.setShouldTagNames(l);
		}
		if (StringUtils.isNotBlank(condition.getMustNotTagNames())) {
			List<String> l = new ArrayList<String>();
			for (String s : condition.getMustNotTagNames().split("_| |	")) {
				l.add(s);
			}
			searchcondition.setMustNotTagNames(l);
			scc.setMustNotTagNames(l);
		}
		// 关键词
		boolean flag = condition.getFlag() == 1 ? true : false;
		// 标题
		// and关键词
		if (StringUtils.isNotBlank(condition.getMustWordNames())) {
			String[] temp = condition.getMustWordNames().split("_| |	");
			List<String> l = new ArrayList<String>();
			for (String str : temp) {
				l.add(str);
			}
			if (flag) {
				searchcondition.setMustTitleWords(l);
			} else {
				scc.setMustContentWords(l);
			}
			/*
			 * searchcondition.setMustTitleWords(l); scc.setMustTitleWords(l);
			 * if (flag) {// 文档名字 } else {
			 * searchcondition.setMustContentWords(l); }
			 */
		}
		// OR关键词
		if (StringUtils.isNotBlank(condition.getShouldWordNames())) {
			String[] temp = condition.getShouldWordNames().split("_| |	");
			List<String> l = new ArrayList<String>();
			for (String str : temp) {
				l.add(str);
			}
			if (flag) {
				searchcondition.setShouldTitleWords(l);
			} else {
				scc.setShouldContentWords(l);
			}
			/*
			 * searchcondition.setShouldTitleWords(l); if (flag) { } else {
			 * searchcondition.setShouldContentWords(l); }
			 */
		}
		// not关键词
		if (StringUtils.isNotBlank(condition.getMustNotWordNames())) {
			String[] temp = condition.getMustNotWordNames().split("_| |	");
			List<String> l = new ArrayList<String>();
			for (String str : temp) {
				l.add(str);
			}
			if (flag) {
				searchcondition.setMustNotTitleWords(l);
			} else {
				scc.setMustNotContentWords(l);
			}
			/*
			 * searchcondition.setMustNotTitleWords(l); if (flag) { } else {
			 * searchcondition.setMustNotContentWords(l); }
			 */
		}
		// 新闻来源
		if (StringUtils.isNotBlank(condition.getSource())) {
			String[] temp = condition.getSource().split("_| |	");
			List<String> l = new ArrayList<String>();
			for (String str : temp) {
				l.add(str);
			}
			searchcondition.setShouldWebNames(l);
			scc.setShouldWebNames(l);
		}
		if (!searchcondition.isEmpty()) {
			searchCondition.add(searchcondition);
		}
		if (!flag) {// 新闻正文
			if (!scc.isEmpty()) {
				searchCondition.add(scc);
			}
		}
		map.put("from", from + "");
		map.put("pageSize", pageSize + "");
		map.put("jsonStr", gson.toJson(searchCondition));
		map.put("searchFlag", "true");

		String json = getHtml(map, newsSearchByMultiJsonStr);
		if (json != null && !json.equals("")) {
			newsList = gson.fromJson(json, NewsCrawls.class);
			if (newsList.getCount() == 0) {
				if (StringUtils.isNotBlank(condition.getMustTagNames())) {
					List<String> l = new ArrayList<String>();
					for (String s : condition.getMustTagNames().split("_| |	")) {
						l.add(s);
					}
					searchcondition.setShouldKeywords(l);
					searchcondition.setShouldTitleWords(l);
					searchcondition.setMustTagNames(null);
				}
				map.put("jsonStr", gson.toJson(searchcondition));
				json = getHtml(map, newsByJson);
				newsList = gson.fromJson(json, NewsCrawls.class);
			}
		}
		return newsList;
	}

	public NewsCrawls search1(NewsMyPlate newsMyPlate, Integer pageNo,
			Integer pageSize) {
		// 得到查询条件
		Gson gson = new Gson();
		NewsCondition newsCondition = gson.fromJson(
				newsMyPlate.getConditions(), NewsCondition.class);
		NewsCrawls newsList = new NewsCrawls();
		String from = null;
		if (pageNo == 1) {
			from = "0";
			// groupParam.setFrom(0);
		} else {
			from = (pageNo - 1) * pageSize + "";
		}
		Map<String, String> map = new HashMap<String, String>();

		NewsSearchCondition searchcondition = new NewsSearchCondition();

		/**
		 * 必须
		 */
		// private List<String> mustCrawlId;
		// 必须有的新闻编号？
		if (StringUtils.isNotBlank(newsCondition.getMustCrawlId())) {
			List<String> mustCrawlId = new ArrayList<String>();
			for (String s : newsCondition.getMustCrawlId().split("_")) {
				mustCrawlId.add(s);
			}
			searchcondition.setMustCrawlId(mustCrawlId);
		}
		/**
		 * 不能
		 */
		// private List<String> mustNotCrawlId;
		if (StringUtils.isNotBlank(newsCondition.getMustNotCrawlId())) {
			List<String> mustCrawlId = new ArrayList<String>();
			for (String s : newsCondition.getMustNotCrawlId().split("_")) {
				mustCrawlId.add(s);
			}
			searchcondition.setMustNotCrawlId(mustCrawlId);
		}

		/**
		 * 可以
		 */
		// private List<String> shouldCrawlId;
		if (StringUtils.isNotBlank(newsCondition.getShouldCrawlId())) {
			List<String> mustCrawlId = new ArrayList<String>();
			for (String s : newsCondition.getShouldCrawlId().split("_")) {
				mustCrawlId.add(s);
			}
			searchcondition.setShouldCrawlId(mustCrawlId);
		}

		/**
		 * 必须被包含在正文中的词语
		 */
		// private List<String> mustContentWords;
		// 内容中必须包含 的关键词
		if (StringUtils.isNotBlank(newsCondition.getMustContentWords())) {
			List<String> mustContentWords = new ArrayList<String>();
			for (String s : newsCondition.getMustContentWords().split("_")) {
				mustContentWords.add(s);
			}
			searchcondition.setMustContentWords(mustContentWords);
		}

		/**
		 * 不能被包含在正文中的词语
		 */
		// private List<String> mustNotContentWords;
		if (StringUtils.isNotBlank(newsCondition.getMustNotContentWords())) {
			List<String> mustCrawlId = new ArrayList<String>();
			for (String s : newsCondition.getMustNotContentWords().split("_")) {
				mustCrawlId.add(s);
			}
			searchcondition.setMustNotContentWords(mustCrawlId);
		}
		/**
		 * 可以出现在正文中的词语
		 */
		// private List<String> shouldContentWords;
		if (StringUtils.isNotBlank(newsCondition.getShouldContentWords())) {
			List<String> mustCrawlId = new ArrayList<String>();
			for (String s : newsCondition.getShouldContentWords().split("_")) {
				mustCrawlId.add(s);
			}
			searchcondition.setShouldContentWords(mustCrawlId);
		}
		/**
		 * 标题中必须出现的词语
		 */
		// private List<String> mustTitleWords;
		if (StringUtils.isNotBlank(newsCondition.getMustTitleWords())) {
			List<String> mustCrawlId = new ArrayList<String>();
			for (String s : newsCondition.getMustTitleWords().split("_")) {
				mustCrawlId.add(s);
			}
			searchcondition.setMustTitleWords(mustCrawlId);
		}
		/**
		 * 标题中不能出现的词语
		 */
		// private List<String> mustNotTitleWords;
		if (StringUtils.isNotBlank(newsCondition.getMustNotTitleWords())) {
			List<String> mustCrawlId = new ArrayList<String>();
			for (String s : newsCondition.getMustNotTitleWords().split("_")) {
				mustCrawlId.add(s);
			}
			searchcondition.setMustNotTitleWords(mustCrawlId);
		}
		/**
		 * 标题中可以出现的词语
		 */
		// private List<String> shouldTitleWords;
		if (StringUtils.isNotBlank(newsCondition.getShouldTitleWords())) {
			List<String> mustCrawlId = new ArrayList<String>();
			for (String s : newsCondition.getShouldTitleWords().split("_")) {
				mustCrawlId.add(s);
			}
			searchcondition.setShouldTitleWords(mustCrawlId);
		}
		/**
		 * 必须属于的标签
		 */
		// private List<String> mustTagNames;
		if (StringUtils.isNotBlank(newsCondition.getMustTagNames())) {
			List<String> mustTagNames = new ArrayList<String>();
			for (String s : newsCondition.getMustTagNames().split("_")) {
				mustTagNames.add(s);
			}
			searchcondition.setMustTagNames(mustTagNames);
		}
		/**
		 * 不能被包含的标签
		 */
		// private List<String> mustNotTagNames;
		// 不要的标签
		if (StringUtils.isNotBlank(newsCondition.getMustNotTagNames())) {
			List<String> mustNotTagNames = new ArrayList<String>();
			for (String s : newsCondition.getMustNotTagNames().split("_")) {
				mustNotTagNames.add(s);
			}
			searchcondition.setMustNotTagNames(mustNotTagNames);
		}
		/**
		 * 可以出现的标签
		 */
		// private List<String> shouldTagNames;
		// 可要的标签
		if (StringUtils.isNotBlank(newsCondition.getShouldTagNames())) {
			List<String> shouldTagNames = new ArrayList<String>();
			for (String s : newsCondition.getShouldTagNames().split("_")) {
				shouldTagNames.add(s);
			}
			searchcondition.setShouldTagNames(shouldTagNames);
		}
		/**
		 * 必须的关键词
		 */
		// private List<String> mustKeywords;
		if (StringUtils.isNotBlank(newsCondition.getMustKeywords())) {
			List<String> shouldTagNames = new ArrayList<String>();
			for (String s : newsCondition.getMustKeywords().split("_")) {
				shouldTagNames.add(s);
			}
			searchcondition.setMustKeywords(shouldTagNames);
		}
		/**
		 * 不能出现的关键词
		 */
		// private List<String> mustNotKeywords;
		if (StringUtils.isNotBlank(newsCondition.getMustNotKeywords())) {
			List<String> shouldTagNames = new ArrayList<String>();
			for (String s : newsCondition.getMustNotKeywords().split("_")) {
				shouldTagNames.add(s);
			}
			searchcondition.setMustNotKeywords(shouldTagNames);
		}

		/**
		 * 可以出现的关键词
		 */
		// private List<String> shouldKeywords;
		if (StringUtils.isNotBlank(newsCondition.getShouldKeywords())) {
			List<String> shouldTagNames = new ArrayList<String>();
			for (String s : newsCondition.getShouldKeywords().split("_")) {
				shouldTagNames.add(s);
			}
			searchcondition.setShouldKeywords(shouldTagNames);
		}

		/**
		 * 搜索必须从这些网站来的新闻
		 */
		// private List<String> mustWebNames;
		if (StringUtils.isNotBlank(newsCondition.getMustWebNames())) {
			List<String> shouldTagNames = new ArrayList<String>();
			for (String s : newsCondition.getMustWebNames().split("_")) {
				shouldTagNames.add(s);
			}
			searchcondition.setMustWebNames(shouldTagNames);
		}

		/**
		 * 搜索不能从这些网站来的新闻
		 */
		// private List<String> mustNotWebNames;
		if (StringUtils.isNotBlank(newsCondition.getMustNotWebNames())) {
			List<String> shouldTagNames = new ArrayList<String>();
			for (String s : newsCondition.getMustNotWebNames().split("_")) {
				shouldTagNames.add(s);
			}
			searchcondition.setMustNotWebNames(shouldTagNames);
		}

		/**
		 * 搜索可以从这些网站来的新闻
		 */
		// private List<String> shouldWebNames;
		if (StringUtils.isNotBlank(newsCondition.getShouldWebNames())) {
			List<String> shouldTagNames = new ArrayList<String>();
			for (String s : newsCondition.getShouldWebNames().split("_")) {
				shouldTagNames.add(s);
			}
			searchcondition.setShouldWebNames(shouldTagNames);
		}
		/**
		 * 搜索必须从这些网站来的新闻
		 */
		// private List<String> mustSectionNames;
		if (StringUtils.isNotBlank(newsCondition.getMustSectionNames())) {
			List<String> shouldTagNames = new ArrayList<String>();
			for (String s : newsCondition.getMustSectionNames().split("_")) {
				shouldTagNames.add(s);
			}
			searchcondition.setMustSectionNames(shouldTagNames);
		}
		/**
		 * 搜索不能从这些板块来的新闻
		 */
		// private List<String> mustNotSectionNames;
		if (StringUtils.isNotBlank(newsCondition.getMustNotSectionNames())) {
			List<String> shouldTagNames = new ArrayList<String>();
			for (String s : newsCondition.getMustNotSectionNames().split("_")) {
				shouldTagNames.add(s);
			}
			searchcondition.setMustNotSectionNames(shouldTagNames);
		}
		/**
		 * 搜索可以从这些板块来的新闻
		 */
		// private List<String> shouldSectionNames;
		if (StringUtils.isNotBlank(newsCondition.getShouldSectionNames())) {
			List<String> shouldTagNames = new ArrayList<String>();
			for (String s : newsCondition.getShouldSectionNames().split("_")) {
				shouldTagNames.add(s);
			}
			searchcondition.setShouldSectionNames(shouldTagNames);
		}
		/**
		 * 必须属于的扩展标签
		 */
		// private List<String> mustExtraTags;
		if (StringUtils.isNotBlank(newsCondition.getMustExtraTags())) {
			List<String> shouldTagNames = new ArrayList<String>();
			for (String s : newsCondition.getMustExtraTags().split("_")) {
				shouldTagNames.add(s);
			}
			searchcondition.setMustExtraTags(shouldTagNames);
		}
		/**
		 * 不能属于的扩展标签
		 */
		// private List<String> mustNotExtraTags;
		// 不允许为空的扩展标签
		if (StringUtils.isNotBlank(newsCondition.getMustNotExtraTags())) {
			List<String> mustNotExtraTags = new ArrayList<String>();
			for (String s : newsCondition.getMustNotExtraTags().split("_")) {
				mustNotExtraTags.add(s);
			}
			searchcondition.setMustNotExtraTags(mustNotExtraTags);
		}
		/**
		 * 可以属于的扩展标签
		 */
		// private List<String> shouldExtraTags;
		if (StringUtils.isNotBlank(newsCondition.getShouldExtraTags())) {
			List<String> mustNotExtraTags = new ArrayList<String>();
			for (String s : newsCondition.getShouldExtraTags().split("_")) {
				mustNotExtraTags.add(s);
			}
			searchcondition.setShouldExtraTags(mustNotExtraTags);
		}
		/**
		 * 来源网址
		 */
		// private List<Integer> websiteIDs;
		if (StringUtils.isNotBlank(newsCondition.getWebsiteIDs())) {
			List<Integer> mustNotExtraTags = new ArrayList<Integer>();
			for (String s : newsCondition.getWebsiteIDs().split("_")) {
				mustNotExtraTags.add(Integer.parseInt(s));
			}
			searchcondition.setWebsiteIDs(mustNotExtraTags);
		}

		/**
		 * 是否允许extraTag为空
		 */
		// private String extraTagNull;
		// 是否允许extraTag为空
		if (StringUtils.isNotBlank(newsCondition.getExtraTagNull())) {
			searchcondition.setExtraTagNull(newsCondition.getExtraTagNull());
		}

		/**
		 * 图片是否为空
		 */
		// private String picUrlNull;
		// 是否允许extraTag为空
		if (StringUtils.isNotBlank(newsCondition.getPicUrlNull())) {
			searchcondition.setPicUrlNull(newsCondition.getPicUrlNull());
		}

		/**
		 * 必须包含的区域
		 */
		// private List<String> mustRegion;
		if (StringUtils.isNotBlank(newsCondition.getMustRegion())) {
			List<String> mustNotExtraTags = new ArrayList<String>();
			for (String s : newsCondition.getMustRegion().split("_")) {
				mustNotExtraTags.add(s);
			}
			searchcondition.setMustRegion(mustNotExtraTags);
		}

		/**
		 * 不能包含的区域
		 */
		// private List<String> mustNotRegion;
		if (StringUtils.isNotBlank(newsCondition.getMustNotRegion())) {
			List<String> mustNotExtraTags = new ArrayList<String>();
			for (String s : newsCondition.getMustNotRegion().split("_")) {
				mustNotExtraTags.add(s);
			}
			searchcondition.setMustNotRegion(mustNotExtraTags);
		}
		/**
		 * 可以包含的区域
		 */
		// private List<String> shouldRegion;
		if (StringUtils.isNotBlank(newsCondition.getShouldRegion())) {
			List<String> mustNotExtraTags = new ArrayList<String>();
			for (String s : newsCondition.getShouldRegion().split("_")) {
				mustNotExtraTags.add(s);
			}
			searchcondition.setShouldRegion(mustNotExtraTags);
		}

		map.put("from", from + "");
		map.put("pageSize", pageSize + "");
		map.put("jsonStr", gson.toJson(searchcondition));
		String json = getHtml(map, newsByJson);
		if (json != null && !json.equals("")) {
			newsList = gson.fromJson(json, NewsCrawls.class);
		}
		return newsList;

	}

	public NewsMyPlate getNewsMyPlate(Integer plateId) {
		return newsMyPlateMapper.selectByPrimaryKey(plateId);
	}
	
	public NewsCrawls searchByScore(String keyword, String searchType,
			String articleFormat, Integer pageNo, Integer pageSize) {
		NewsCrawls result = new NewsCrawls();
		int from = 0;
		if (pageNo == 1) {
			from = 0;
		} else {
			from = (pageNo - 1) * pageSize;
		}
		NewsSearchCondition searchcondition = null;
		Map<String, String> map = new HashMap<String, String>();
		// 
		List<String> temp = new ArrayList<String>();
		temp.add(keyword);
		List<News> newsList = new ArrayList<News>();
		// 全文搜索
		if (searchType.equals("ALL")) {
			// 1.首先不分词搜索
			searchcondition = new NewsSearchCondition();
			searchcondition.setMustTitleWords(temp);
			searchcondition.setMustContentWords(temp);
			// 调用搜索实现
			getNewsSearchInfo(articleFormat, pageSize, result, from, searchcondition, map, newsList, AGS);
			
			// 2.如果分词数据不足
			int count = result.getCount();
			if(count < BASE_NUM){
				List<String> analyList = CommonUtils.analyzer(keyword);
				if (!analyList.contains(keyword)) {
					analyList.add(keyword);
				}
				
				searchcondition = new NewsSearchCondition();
				searchcondition.setTermsMustTitle(analyList);
				searchcondition.setTermsMustContent(analyList);
				
				// 重置参数
				result = new NewsCrawls();
				map.put("returnTime", String.valueOf(System.currentTimeMillis()));
				newsList = new ArrayList<News>();
				// 调用搜索实现
				getNewsSearchInfo(articleFormat, pageSize, result, from, searchcondition, map, newsList, AGS);
			}
		} else {
			// 搜索标题
			// 1.首先不分词搜索
			searchcondition = new NewsSearchCondition();
			searchcondition.setMustTitleWords(temp);
			// 调用搜索实现
			getNewsSearchInfo(articleFormat, pageSize, result, from, searchcondition, map, newsList, AGS);
			
			// 2.如果数据不足--走分词搜索
			int count = result.getCount();
			if(count < BASE_NUM){
				List<String> analyList = CommonUtils.analyzer(keyword);
				if (!analyList.contains(keyword)) {
					analyList.add(keyword);
				}
				searchcondition = new NewsSearchCondition();
				searchcondition.setTermsMustTitle(analyList);
				
				// 重置参数
				result = new NewsCrawls();
				map.put("returnTime", String.valueOf(System.currentTimeMillis()));
				newsList = new ArrayList<News>();
				// 调用搜索实现
				getNewsSearchInfo(articleFormat, pageSize, result, from, searchcondition, map, newsList, AGS);
			}
		}
		
		
		
		
		return result;
	}

	/**
	 * @param articleFormat
	 * @param pageSize
	 * @param result
	 * @param from
	 * @param searchcondition
	 * @param map
	 * @param newsList
	 * @param ags
	 */
	public void getNewsSearchInfo(String articleFormat, Integer pageSize,
			NewsCrawls result, int from, NewsSearchCondition searchcondition,
			Map<String, String> map, List<News> newsList, int[] ags) {
		int index = 0;
		int resultCount = 0;
		for (int i = 0; i < ags.length; i++) {
			// 天数
			setTimes(map, ags[i]);
			NewsCrawls tempResult = getSources(articleFormat, from, pageSize, searchcondition, map);
			int count = tempResult.getCount();
			// 累加数量
			resultCount += tempResult.getCount();
			if(count < from){
				from -= count;
			}
			// 如果取够一页数据，不做以下操作
			if(index == pageSize){
				continue;
			}
//				if(from != 0 && from < count) {
//					tempResult = getSources(articleFormat, from, pageSize, searchcondition, map);
//				}
			if(tempResult.getCount() > 0){
				List<News> tempList = tempResult.getData();
				for (News news : tempList) {
					// 判断是否够一页数据
					if(index == pageSize){
						break;
					}
					newsList.add(news);
					index++;
				}
			}
		}
		if (resultCount >= SUM) {
			result.setCountResult(resultCount);
			result.setCount(SUM);
		} else {
			result.setCountResult(resultCount);
			result.setCount(resultCount);
		}
		if (CommonUtils.isNotEmpty(newsList)) {
			result.setData(newsList);
		}
	}
	
	public NewsCrawls getSources(String articleFormat, int from, int pageSize,
			NewsSearchCondition searchcondition, Map<String, String> map) {
		NewsCrawls result = new NewsCrawls();
		map.put("from", String.valueOf(from));
		map.put("pageSize", String.valueOf(pageSize));
		map.put("jsonStr", GsonUtil.getJsonStringFromObject(searchcondition));
		map.put("orderByColumn", articleFormat);
		map.put("highlight", "true");
		String json = getHtml(map, newsByJson);
		if (CommonUtils.isNotEmpty(json)) {
			result = GsonUtil.fromJson(json, NewsCrawls.class);
		}
		return result;
	}
	
	/**
	 * @param map
	 */
	public void setTimes(Map<String, String> map, int day) {
		Calendar c = Calendar.getInstance();
		
		String date = map.get("returnTime");
		if(CommonUtils.isNotEmpty(date)){
			c.setTimeInMillis(Long.parseLong(date));
		}
//		System.out.println("结束时间 ：" + CommonUtils.formatDate(c.getTime(), null));
		
		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
		long end = c.getTime().getTime();
		c.set(Calendar.DATE, c.get(Calendar.DATE) - day);
		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
		long start = c.getTime().getTime();
//		System.out.println("开始时间：" + CommonUtils.formatDate(c.getTime(), null));
		
		map.put("startTime", String.valueOf(start));
		map.put("endTime", String.valueOf(end));
		map.put("returnTime", String.valueOf(start));
	}
}
