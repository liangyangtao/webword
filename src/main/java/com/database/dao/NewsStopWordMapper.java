package com.database.dao;

import com.database.bean.NewsStopWord;
import com.database.bean.NewsStopWordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NewsStopWordMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_stop_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	int countByExample(NewsStopWordExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_stop_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	int deleteByExample(NewsStopWordExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_stop_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_stop_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	int insert(NewsStopWord record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_stop_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	int insertSelective(NewsStopWord record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_stop_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	List<NewsStopWord> selectByExample(NewsStopWordExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_stop_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	NewsStopWord selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_stop_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	int updateByExampleSelective(@Param("record") NewsStopWord record,
			@Param("example") NewsStopWordExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_stop_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	int updateByExample(@Param("record") NewsStopWord record,
			@Param("example") NewsStopWordExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_stop_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	int updateByPrimaryKeySelective(NewsStopWord record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_stop_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	int updateByPrimaryKey(NewsStopWord record);
}