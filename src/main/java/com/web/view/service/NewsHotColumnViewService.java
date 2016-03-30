package com.web.view.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.database.bean.NewsColumn;
import com.database.bean.NewsColumnExample;
import com.database.bean.NewsMyPlate;
import com.database.bean.NewsMyPlateExample;
import com.database.bean.WordUsers;
import com.database.dao.NewsColumnMapper;
import com.database.dao.NewsMyPlateMapper;

@Service
public class NewsHotColumnViewService {

	@Autowired
	NewsColumnMapper newsColumnMapper;

	@Autowired
	NewsMyPlateMapper newsMyPlateMapper;

	/**
	 * 
	 * 
	 * 
	 * 获取热门定制栏目 根据订阅次数 倒叙排列
	 * 
	 * @return 6个栏目列表
	 */
	public List<NewsColumn> getHotColumns() {
		NewsColumnExample newsColumnExample = new NewsColumnExample();
		newsColumnExample.or().andIdNotEqualTo(0).andPidNotEqualTo(0);
		newsColumnExample.setOrderByClause("count desc limit 6");
		return newsColumnMapper.selectByExample(newsColumnExample);
	}

	/**
	 * 获取自己的栏目
	 * 
	 * @param userId
	 * @return
	 */
	public List<NewsMyPlate> getMyPLates(Integer userId) {
		NewsMyPlateExample newsMyPlateExample = new NewsMyPlateExample();
		newsMyPlateExample.or().andUserIdEqualTo(userId).andPidEqualTo(0);
		newsMyPlateExample.setOrderByClause("order_id asc");
		return newsMyPlateMapper.selectByExample(newsMyPlateExample);
	}

	/**
	 * 用户是否订阅了栏目
	 * 
	 * @param userId
	 * @return
	 */
	public boolean isHasColumn(int userId, int columnId) {

		NewsMyPlateExample newsMyPlateExample = new NewsMyPlateExample();
		newsMyPlateExample.or().andColumnIdEqualTo(columnId)
				.andUserIdEqualTo(userId);
		List<NewsMyPlate> newsMyPlates = newsMyPlateMapper
				.selectByExample(newsMyPlateExample);
		if (newsMyPlates.size() > 0) {
			return true;
		} else {
			return false;
		}

	}

	public void addMyPlates(WordUsers user,List<NewsMyPlate> nyList) {
		for(NewsMyPlate p:nyList){
			p.setUserId(user.getUserId());
			p.setPlateId(null);
			newsMyPlateMapper.insertSelective(p);
		}
	}

}
