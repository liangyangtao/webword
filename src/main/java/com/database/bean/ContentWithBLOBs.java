package com.database.bean;

public class ContentWithBLOBs extends Content {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_content.content
     *
     * @mbggenerated Wed Apr 29 11:52:28 CST 2015
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_content.content_skip
     *
     * @mbggenerated Wed Apr 29 11:52:28 CST 2015
     */
    private String contentSkip;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column know_content.content
     *
     * @return the value of know_content.content
     *
     * @mbggenerated Wed Apr 29 11:52:28 CST 2015
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column know_content.content
     *
     * @param content the value for know_content.content
     *
     * @mbggenerated Wed Apr 29 11:52:28 CST 2015
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column know_content.content_skip
     *
     * @return the value of know_content.content_skip
     *
     * @mbggenerated Wed Apr 29 11:52:28 CST 2015
     */
    public String getContentSkip() {
        return contentSkip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column know_content.content_skip
     *
     * @param contentSkip the value for know_content.content_skip
     *
     * @mbggenerated Wed Apr 29 11:52:28 CST 2015
     */
    public void setContentSkip(String contentSkip) {
        this.contentSkip = contentSkip;
    }
}