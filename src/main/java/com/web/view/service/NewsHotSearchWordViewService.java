package com.web.view.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.database.bean.NewsStopWord;
import com.database.bean.NewsStopWordExample;
import com.database.bean.NewsUserSearchLog;
import com.database.bean.NewsUserSearchLogExample;
import com.database.bean.NewsWord;
import com.database.bean.NewsWordExample;
import com.database.dao.NewsStopWordMapper;
import com.database.dao.NewsUserSearchLogMapper;
import com.database.dao.NewsWordMapper;

@Service
public class NewsHotSearchWordViewService {

	@Autowired
	NewsWordMapper newsWordMapper;
	@Autowired
	NewsStopWordMapper newsStopWordMapper;
	@Autowired
	NewsUserSearchLogMapper newsUserSearchLogMapper;

	/**
	 * 
	 * 获取新闻热搜词，过滤掉停用词
	 * 
	 * @param from
	 *            页数
	 * @param pageSize
	 *            每页显示条数
	 * @return
	 */
	public List<String> readHotWords(int from, int pageSize) {
		if (pageSize == 0) {
			pageSize = 10;
		}
		List<String> hotWords = new ArrayList<String>();
		List<NewsWord> newsWords = readNewsWords(from, pageSize);
		List<NewsStopWord> newsStopWords = readStopWord();
		Set<String> stopwords = new HashSet<String>();
		for (NewsStopWord newsStopWord : newsStopWords) {
			stopwords.add(newsStopWord.getStopWord());
		}
		for (NewsWord newsWord : newsWords) {
			String words = newsWord.getSword();
			if (stopwords.contains(words)) {
				continue;
			}
			hotWords.add(words);
			if (hotWords.size() >= pageSize) {
				break;
			}
		}
		return hotWords;

	}

	/**
	 * 获取
	 * 
	 * @param from
	 * @param pageSize
	 * @return
	 */
	public List<NewsWord> readNewsWords(int from, int pageSize) {

		NewsWordExample newsWordExample = new NewsWordExample();
		int count = newsWordMapper.countByExample(newsWordExample);
		int startNum = 0;
		int endNum = 0;
		if (count > pageSize) {
			int index2 = (int) (Math.random() * (count - pageSize));
			startNum = index2;
			endNum = index2 + pageSize;
		} else {
			endNum = count;
		}
		List<NewsWord> newsWords = new ArrayList<NewsWord>();
		if (startNum > count) {
			startNum = 0;
		}
		if (startNum + pageSize > count) {
			endNum = count;
		}
		newsWordExample.setOrderByClause("id desc limit " + startNum + " , "
				+ endNum);
		newsWords = newsWordMapper.selectByExample(newsWordExample);
		return newsWords;
	}

	public List<NewsStopWord> readStopWord() {
		NewsStopWordExample newsStopWordExample = new NewsStopWordExample();
		List<NewsStopWord> newsStopWords = newsStopWordMapper
				.selectByExample(newsStopWordExample);
		return newsStopWords;
	}

	public int saveSearchWord(String searchWord, int userId) {
		NewsUserSearchLog newsUserSearchLog = new NewsUserSearchLog();
		newsUserSearchLog.setDoType("news");
		newsUserSearchLog.setSearchTime(new Date());
		newsUserSearchLog.setSearchWord(searchWord);
		newsUserSearchLog.setUserId(userId);
		return newsUserSearchLogMapper.insertSelective(newsUserSearchLog);
	}

	public List<NewsUserSearchLog> getUserSearchWord(int userId) {
		NewsUserSearchLogExample example = new NewsUserSearchLogExample();
		example.or().andUserIdEqualTo(userId);
		example.setOrderByClause("id desc");
		return newsUserSearchLogMapper.selectByExample(example);
	}

}
