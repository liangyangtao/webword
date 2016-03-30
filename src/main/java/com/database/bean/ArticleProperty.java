package com.database.bean;

import java.util.Date;

public class ArticleProperty {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_article_property.article_property_id
     *
     * @mbggenerated Fri Apr 10 13:43:36 CST 2015
     */
    private Integer articlePropertyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_article_property.article_id
     *
     * @mbggenerated Fri Apr 10 13:43:36 CST 2015
     */
    private Integer articleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_article_property.start_time
     *
     * @mbggenerated Fri Apr 10 13:43:36 CST 2015
     */
    private Date startTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_article_property.end_time
     *
     * @mbggenerated Fri Apr 10 13:43:36 CST 2015
     */
    private Date endTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_article_property.industry_id
     *
     * @mbggenerated Fri Apr 10 13:43:36 CST 2015
     */
    private Integer industryId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_article_property.area_id
     *
     * @mbggenerated Fri Apr 10 13:43:36 CST 2015
     */
    private Integer areaId;
    
    private String industryName;
    
    /**
	 * @return the industryName
	 */
	public String getIndustryName() {
		return industryName;
	}

	/**
	 * @param industryName the industryName to set
	 */
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param areaName the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	private String areaName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column know_article_property.article_property_id
     *
     * @return the value of know_article_property.article_property_id
     *
     * @mbggenerated Fri Apr 10 13:43:36 CST 2015
     */
    public Integer getArticlePropertyId() {
        return articlePropertyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column know_article_property.article_property_id
     *
     * @param articlePropertyId the value for know_article_property.article_property_id
     *
     * @mbggenerated Fri Apr 10 13:43:36 CST 2015
     */
    public void setArticlePropertyId(Integer articlePropertyId) {
        this.articlePropertyId = articlePropertyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column know_article_property.article_id
     *
     * @return the value of know_article_property.article_id
     *
     * @mbggenerated Fri Apr 10 13:43:36 CST 2015
     */
    public Integer getArticleId() {
        return articleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column know_article_property.article_id
     *
     * @param articleId the value for know_article_property.article_id
     *
     * @mbggenerated Fri Apr 10 13:43:36 CST 2015
     */
    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column know_article_property.start_time
     *
     * @return the value of know_article_property.start_time
     *
     * @mbggenerated Fri Apr 10 13:43:36 CST 2015
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column know_article_property.start_time
     *
     * @param startTime the value for know_article_property.start_time
     *
     * @mbggenerated Fri Apr 10 13:43:36 CST 2015
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column know_article_property.end_time
     *
     * @return the value of know_article_property.end_time
     *
     * @mbggenerated Fri Apr 10 13:43:36 CST 2015
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column know_article_property.end_time
     *
     * @param endTime the value for know_article_property.end_time
     *
     * @mbggenerated Fri Apr 10 13:43:36 CST 2015
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column know_article_property.industry_id
     *
     * @return the value of know_article_property.industry_id
     *
     * @mbggenerated Fri Apr 10 13:43:36 CST 2015
     */
    public Integer getIndustryId() {
        return industryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column know_article_property.industry_id
     *
     * @param industryId the value for know_article_property.industry_id
     *
     * @mbggenerated Fri Apr 10 13:43:36 CST 2015
     */
    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column know_article_property.area_id
     *
     * @return the value of know_article_property.area_id
     *
     * @mbggenerated Fri Apr 10 13:43:36 CST 2015
     */
    public Integer getAreaId() {
        return areaId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column know_article_property.area_id
     *
     * @param areaId the value for know_article_property.area_id
     *
     * @mbggenerated Fri Apr 10 13:43:36 CST 2015
     */
    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }
}