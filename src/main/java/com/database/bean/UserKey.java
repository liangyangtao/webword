package com.database.bean;

public class UserKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pub_users.user_account
     *
     * @mbggenerated Tue Apr 07 14:40:31 CST 2015
     */
    private String userAccount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pub_users.user_id
     *
     * @mbggenerated Tue Apr 07 14:40:31 CST 2015
     */
    private Integer userId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pub_users.user_account
     *
     * @return the value of pub_users.user_account
     *
     * @mbggenerated Tue Apr 07 14:40:31 CST 2015
     */
    public String getUserAccount() {
        return userAccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pub_users.user_account
     *
     * @param userAccount the value for pub_users.user_account
     *
     * @mbggenerated Tue Apr 07 14:40:31 CST 2015
     */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pub_users.user_id
     *
     * @return the value of pub_users.user_id
     *
     * @mbggenerated Tue Apr 07 14:40:31 CST 2015
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pub_users.user_id
     *
     * @param userId the value for pub_users.user_id
     *
     * @mbggenerated Tue Apr 07 14:40:31 CST 2015
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}