package com.database.bean;

import java.util.Date;

public class LoginLog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column login_log.id
     *
     * @mbggenerated Wed Sep 30 08:54:10 CST 2015
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column login_log.userid
     *
     * @mbggenerated Wed Sep 30 08:54:10 CST 2015
     */
    private Integer userid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column login_log.username
     *
     * @mbggenerated Wed Sep 30 08:54:10 CST 2015
     */
    private String username;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column login_log.logintime
     *
     * @mbggenerated Wed Sep 30 08:54:10 CST 2015
     */
    private Date logintime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column login_log.id
     *
     * @return the value of login_log.id
     *
     * @mbggenerated Wed Sep 30 08:54:10 CST 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column login_log.id
     *
     * @param id the value for login_log.id
     *
     * @mbggenerated Wed Sep 30 08:54:10 CST 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column login_log.userid
     *
     * @return the value of login_log.userid
     *
     * @mbggenerated Wed Sep 30 08:54:10 CST 2015
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column login_log.userid
     *
     * @param userid the value for login_log.userid
     *
     * @mbggenerated Wed Sep 30 08:54:10 CST 2015
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column login_log.username
     *
     * @return the value of login_log.username
     *
     * @mbggenerated Wed Sep 30 08:54:10 CST 2015
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column login_log.username
     *
     * @param username the value for login_log.username
     *
     * @mbggenerated Wed Sep 30 08:54:10 CST 2015
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column login_log.logintime
     *
     * @return the value of login_log.logintime
     *
     * @mbggenerated Wed Sep 30 08:54:10 CST 2015
     */
    public Date getLogintime() {
        return logintime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column login_log.logintime
     *
     * @param logintime the value for login_log.logintime
     *
     * @mbggenerated Wed Sep 30 08:54:10 CST 2015
     */
    public void setLogintime(Date logintime) {
        this.logintime = logintime;
    }
}