package com.database.bean;

import java.util.ArrayList;
import java.util.List;

public class NewsSource {
	
	private List<NewsSource> children = new ArrayList<NewsSource>();
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_newssourcedictionary.id
     *
     * @mbggenerated Thu May 14 10:47:39 CST 2015
     */
    private Integer id;
    
    private Integer pid=0;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_newssourcedictionary.sourcename
     *
     * @mbggenerated Thu May 14 10:47:39 CST 2015
     */
    private String sourcename;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_newssourcedictionary.state
     *
     * @mbggenerated Thu May 14 10:47:39 CST 2015
     */
    private Integer state;
    
    private String noIds;

    /**
	 * @return the noIds
	 */
	public String getNoIds() {
		return noIds;
	}

	/**
	 * @param noIds the noIds to set
	 */
	public void setNoIds(String noIds) {
		this.noIds = noIds;
	}

	private String modulename;
    /**
	 * @return the modulename
	 */
	public String getModulename() {
		return modulename;
	}

	/**
	 * @param modulename the modulename to set
	 */
	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column know_newssourcedictionary.id
     *
     * @return the value of know_newssourcedictionary.id
     *
     * @mbggenerated Thu May 14 10:47:39 CST 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column know_newssourcedictionary.id
     *
     * @param id the value for know_newssourcedictionary.id
     *
     * @mbggenerated Thu May 14 10:47:39 CST 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column know_newssourcedictionary.sourcename
     *
     * @return the value of know_newssourcedictionary.sourcename
     *
     * @mbggenerated Thu May 14 10:47:39 CST 2015
     */
    public String getSourcename() {
        return sourcename;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column know_newssourcedictionary.sourcename
     *
     * @param sourcename the value for know_newssourcedictionary.sourcename
     *
     * @mbggenerated Thu May 14 10:47:39 CST 2015
     */
    public void setSourcename(String sourcename) {
        this.sourcename = sourcename;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column know_newssourcedictionary.state
     *
     * @return the value of know_newssourcedictionary.state
     *
     * @mbggenerated Thu May 14 10:47:39 CST 2015
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column know_newssourcedictionary.state
     *
     * @param state the value for know_newssourcedictionary.state
     *
     * @mbggenerated Thu May 14 10:47:39 CST 2015
     */
    public void setState(Integer state) {
        this.state = state;
    }

	public List<NewsSource> getChildren() {
		return children;
	}

	public void setChildren(List<NewsSource> children) {
		this.children = children;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}
}