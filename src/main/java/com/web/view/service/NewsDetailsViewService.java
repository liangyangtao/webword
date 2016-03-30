package com.web.view.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.util.CommonUtils;
import com.web.bean.NewsSearchCondition;
import com.web.document.bean.News;
import com.web.document.bean.NewsCrawls;
import com.web.utils.Fetcher;
import com.web.utils.HttpClientBuilder;

@Service
public class NewsDetailsViewService {

	private static Logger LOGGER = Logger
			.getLogger(NewsDetailsViewService.class);
	@Value("${newsByNewsId}")
	private String newsByNewsId;
	@Value("${searchMoreLikeByNewsId}")
	private String searchMoreLikeByNewsId;
	@Value("${newsByJson}")
	private String newsByJson;
	/**
	 * 新闻详情
	 * 
	 * @param newsId
	 * @return
	 */
	public News getNewsById(String newsId) {
		// TODO Auto-generated method stub
		News news = new News();
		Map<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		map.put("crawl_id", newsId + "");
		String json = getHtml(map, newsByNewsId);
		if (json != null && !json.equals("")) {
			news = gson.fromJson(json, News.class);
		}
		return news;
	}
	
	/**
	 * 获取新闻列表
	 * 
	 * @param from
	 * @param pageSize
	 * @param searchcondition
	 * @return
	 */
	public List<News> getNewsList(int from, int pageSize, NewsSearchCondition searchcondition) {
		long start = System.currentTimeMillis();
		
		List<News> newsList = new ArrayList<News>();
		Map<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		map.put("from", String.valueOf(from));
		map.put("pageSize", String.valueOf(pageSize));
		map.put("jsonStr", gson.toJson(searchcondition));
		String json = getHtml(map, newsByJson);
		
		LOGGER.info("获取相关新闻：" + (System.currentTimeMillis() - start));
		
		if (CommonUtils.isNotEmpty(json)) {
			NewsCrawls result = gson.fromJson(json, NewsCrawls.class);
			newsList = result.getData();
		}
		return newsList;
	}

	/**
	 * 相关新闻
	 * 
	 * @param newsId
	 * @return
	 */
	public List<News> moreLikeNews(String newsId) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("from", "0");
		map.put("pageSize", "20");
		map.put("crawl_id", newsId + "");
		String json = getHtml(map, searchMoreLikeByNewsId);
		List<News> docs = new ArrayList<News>();
		Gson gson = new Gson();
		if (json != null && !json.equals("")) {
			docs = gson.fromJson(json, new TypeToken<List<News>>() {
			}.getType());
			Map<String, News> resultMap = new HashMap<String, News>();
			for (News news : docs) {
				resultMap.put(news.getTitle(), news);
			}
			List<News> resultList = new ArrayList<News>();
			int i = 0;
			for (News doc : resultMap.values()) {
				if(i == 10){
					break;
				}
				resultList.add(doc);
				i++;
			}
			return resultList;
			
		}
		return docs;
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
			LOGGER.warn("与ES交互参数" + params);
			LOGGER.warn("与ES交互URL" + url);
			html = fetcher1.post(url, params, null, "utf-8");
		} catch (Exception e) {
			LOGGER.error("与ES交互异常" + e);
		}
		return html;
	}

}
