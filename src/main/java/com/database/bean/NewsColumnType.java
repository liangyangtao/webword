package com.database.bean;

import java.util.Date;

public class NewsColumnType {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column news_column_type.id
     *
     * @mbggenerated Wed Feb 24 10:59:49 GMT+08:00 2016
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column news_column_type.tname
     *
     * @mbggenerated Wed Feb 24 10:59:49 GMT+08:00 2016
     */
    private String tname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column news_column_type.insert_time
     *
     * @mbggenerated Wed Feb 24 10:59:49 GMT+08:00 2016
     */
    private Date insertTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column news_column_type.id
     *
     * @return the value of news_column_type.id
     *
     * @mbggenerated Wed Feb 24 10:59:49 GMT+08:00 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column news_column_type.id
     *
     * @param id the value for news_column_type.id
     *
     * @mbggenerated Wed Feb 24 10:59:49 GMT+08:00 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column news_column_type.tname
     *
     * @return the value of news_column_type.tname
     *
     * @mbggenerated Wed Feb 24 10:59:49 GMT+08:00 2016
     */
    public String getTname() {
        return tname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column news_column_type.tname
     *
     * @param tname the value for news_column_type.tname
     *
     * @mbggenerated Wed Feb 24 10:59:49 GMT+08:00 2016
     */
    public void setTname(String tname) {
        this.tname = tname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column news_column_type.insert_time
     *
     * @return the value of news_column_type.insert_time
     *
     * @mbggenerated Wed Feb 24 10:59:49 GMT+08:00 2016
     */
    public Date getInsertTime() {
        return insertTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column news_column_type.insert_time
     *
     * @param insertTime the value for news_column_type.insert_time
     *
     * @mbggenerated Wed Feb 24 10:59:49 GMT+08:00 2016
     */
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
}