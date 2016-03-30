package com.web.view.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.database.bean.NewsUserFavorite;
import com.database.bean.NewsUserFavoriteExample;
import com.database.dao.ContentMapper;
import com.database.dao.NewsUserFavoriteMapper;

@Service
public class NewsUserFavoriteService {

	@Autowired
	NewsUserFavoriteMapper newsUserFavoriteMapper;

	@Autowired
	ContentMapper contentMapper;

	/**
	 * 分页查询我的收藏
	 * 
	 * @param pageSize
	 * @param pageNo
	 * @param status
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getAllNews(Integer pageSize, Integer pageNo,
			Integer userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (pageSize == null || pageSize < 1) {
			pageSize = 10;
		}
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		NewsUserFavoriteExample example = new NewsUserFavoriteExample();
		example.setOrderByClause("fav_time desc");
		example.setLimitStart((pageNo - 1) * pageSize);
		example.setLimitEnd(pageSize);
		example.or().andUserIdEqualTo(userId).andIsdelEqualTo(0);
		map.put("list", newsUserFavoriteMapper.selectByExample(example));
		int count = newsUserFavoriteMapper.countByExample(example);
		map.put("count", count);
		map.put("pageSize", pageSize);
		map.put("pageNo", pageNo);
		map.put("pageCount", ((count % pageSize == 0) ? (count / pageSize)
				: ((count / pageSize) + 1)));
		return map;
	}

	/**
	 * 删除我的收藏
	 * 
	 * @param contentIds
	 * @param userId
	 * 
	 */
	public void delAllContent(String contentIds, Integer userId) {
		for (String str : contentIds.split("_")) {
			NewsUserFavoriteExample example = new NewsUserFavoriteExample();
			example.or().andDocIdEqualTo(Integer.parseInt(str));
			NewsUserFavorite f = new NewsUserFavorite();
			f.setDocId(Integer.parseInt(str));
			f.setUserId(userId);
			f.setIsdel(1);
			newsUserFavoriteMapper.updateByExampleSelective(f, example);
		}

	}

	/**
	 * 根据用户id和新闻id查询当前用户是否已收藏此新闻
	 */

	public boolean issaved(Integer userId, Integer crawl_id) {

		NewsUserFavoriteExample example = new NewsUserFavoriteExample();
		example.or().andDocIdEqualTo(crawl_id).andUserIdEqualTo(userId)	.andIsdelEqualTo(0);
		return newsUserFavoriteMapper.selectByExample(example).isEmpty() ? false
				: true;
	}

	/***
	 * 添加到我的收藏
	 * 
	 * @param title
	 * @param docId
	 * @param userId
	 */
	public void insertNewsToMyFavorite(String title, Integer docId,
			Integer userId) {
		NewsUserFavoriteExample example = new NewsUserFavoriteExample();
		example.or().andDocIdEqualTo(docId).andUserIdEqualTo(userId);
		List<NewsUserFavorite> flist = newsUserFavoriteMapper
				.selectByExample(example);
		Date date = new Date();
		NewsUserFavorite newsUserFavorite = new NewsUserFavorite();
		newsUserFavorite.setDoType("news");
		newsUserFavorite.setIsdel(0);
		newsUserFavorite.setFavTime(date);
		newsUserFavorite.setNewsTitle(title);
		newsUserFavorite.setDocId(docId);
		newsUserFavorite.setUserId(userId);
		if (flist.size() == 0) {
			newsUserFavoriteMapper.insertSelective(newsUserFavorite);
		} else {
			newsUserFavorite.setId(flist.get(0).getId());
			newsUserFavoriteMapper.updateByPrimaryKey(newsUserFavorite);
		}

	}

	/***
	 * 删除到我的收藏
	 * 
	 * @param title
	 * @param docId
	 * @param userId
	 */
	public void deleteNewsToMyFavorite(Integer docId, Integer userId) {
		NewsUserFavoriteExample example = new NewsUserFavoriteExample();
		example.or().andDocIdEqualTo(docId).andUserIdEqualTo(userId);
		List<NewsUserFavorite> flist = newsUserFavoriteMapper
				.selectByExample(example);
		if (flist.size() == 0) {

		} else {
			NewsUserFavorite newsUserFavorite = new NewsUserFavorite();
			newsUserFavorite.setDocId(docId);
			newsUserFavorite.setUserId(userId);
			newsUserFavorite.setIsdel(1);
			newsUserFavorite.setId(flist.get(0).getId());
			newsUserFavoriteMapper.updateByPrimaryKeySelective(newsUserFavorite);
		}

	}

}
