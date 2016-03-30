package com.database.dao;

import com.database.bean.NewsWord;
import com.database.bean.NewsWordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NewsWordMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	int countByExample(NewsWordExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	int deleteByExample(NewsWordExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	int insert(NewsWord record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	int insertSelective(NewsWord record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	List<NewsWord> selectByExample(NewsWordExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	NewsWord selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	int updateByExampleSelective(@Param("record") NewsWord record,
			@Param("example") NewsWordExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	int updateByExample(@Param("record") NewsWord record,
			@Param("example") NewsWordExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	int updateByPrimaryKeySelective(NewsWord record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	int updateByPrimaryKey(NewsWord record);
}