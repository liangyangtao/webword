package com.database.bean;

import java.util.Date;
import java.util.List;

public class WordColumnGroup {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column word_column_group.id
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column word_column_group.name
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	private String name;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column word_column_group.user_id
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	private Integer userId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column word_column_group.type_id
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	private Integer typeId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column word_column_group.inser_time
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	private Date inserTime;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column word_column_group.id
	 * @return  the value of word_column_group.id
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column word_column_group.id
	 * @param id  the value for word_column_group.id
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column word_column_group.name
	 * @return  the value of word_column_group.name
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column word_column_group.name
	 * @param name  the value for word_column_group.name
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column word_column_group.user_id
	 * @return  the value of word_column_group.user_id
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column word_column_group.user_id
	 * @param userId  the value for word_column_group.user_id
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column word_column_group.type_id
	 * @return  the value of word_column_group.type_id
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public Integer getTypeId() {
		return typeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column word_column_group.type_id
	 * @param typeId  the value for word_column_group.type_id
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column word_column_group.inser_time
	 * @return  the value of word_column_group.inser_time
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public Date getInserTime() {
		return inserTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column word_column_group.inser_time
	 * @param inserTime  the value for word_column_group.inser_time
	 * @mbggenerated  Tue Oct 27 16:19:21 GMT+08:00 2015
	 */
	public void setInserTime(Date inserTime) {
		this.inserTime = inserTime;
	}
	
	private List<WordColumn> columns;

	public List<WordColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<WordColumn> columns) {
		this.columns = columns;
	}
	
}