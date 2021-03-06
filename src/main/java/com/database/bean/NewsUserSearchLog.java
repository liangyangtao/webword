package com.database.bean;

import java.util.Date;

public class NewsUserSearchLog {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column news_user_search_log.id
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column news_user_search_log.search_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	private String searchWord;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column news_user_search_log.user_id
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	private Integer userId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column news_user_search_log.do_type
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	private String doType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column news_user_search_log.search_time
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	private Date searchTime;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column news_user_search_log.id
	 * @return  the value of news_user_search_log.id
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column news_user_search_log.id
	 * @param id  the value for news_user_search_log.id
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column news_user_search_log.search_word
	 * @return  the value of news_user_search_log.search_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	public String getSearchWord() {
		return searchWord;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column news_user_search_log.search_word
	 * @param searchWord  the value for news_user_search_log.search_word
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column news_user_search_log.user_id
	 * @return  the value of news_user_search_log.user_id
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column news_user_search_log.user_id
	 * @param userId  the value for news_user_search_log.user_id
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column news_user_search_log.do_type
	 * @return  the value of news_user_search_log.do_type
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	public String getDoType() {
		return doType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column news_user_search_log.do_type
	 * @param doType  the value for news_user_search_log.do_type
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	public void setDoType(String doType) {
		this.doType = doType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column news_user_search_log.search_time
	 * @return  the value of news_user_search_log.search_time
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	public Date getSearchTime() {
		return searchTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column news_user_search_log.search_time
	 * @param searchTime  the value for news_user_search_log.search_time
	 * @mbggenerated  Thu Feb 18 09:07:19 GMT+08:00 2016
	 */
	public void setSearchTime(Date searchTime) {
		this.searchTime = searchTime;
	}
}