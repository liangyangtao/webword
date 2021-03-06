package com.database.dao;

import com.database.bean.WordLabel;
import com.database.bean.WordLabelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WordLabelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table word_label
     *
     * @mbggenerated Mon Sep 14 17:19:34 CST 2015
     */
    int countByExample(WordLabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table word_label
     *
     * @mbggenerated Mon Sep 14 17:19:34 CST 2015
     */
    int deleteByExample(WordLabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table word_label
     *
     * @mbggenerated Mon Sep 14 17:19:34 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table word_label
     *
     * @mbggenerated Mon Sep 14 17:19:34 CST 2015
     */
    int insert(WordLabel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table word_label
     *
     * @mbggenerated Mon Sep 14 17:19:34 CST 2015
     */
    int insertSelective(WordLabel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table word_label
     *
     * @mbggenerated Mon Sep 14 17:19:34 CST 2015
     */
    List<WordLabel> selectByExample(WordLabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table word_label
     *
     * @mbggenerated Mon Sep 14 17:19:34 CST 2015
     */
    WordLabel selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table word_label
     *
     * @mbggenerated Mon Sep 14 17:19:34 CST 2015
     */
    int updateByExampleSelective(@Param("record") WordLabel record, @Param("example") WordLabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table word_label
     *
     * @mbggenerated Mon Sep 14 17:19:34 CST 2015
     */
    int updateByExample(@Param("record") WordLabel record, @Param("example") WordLabelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table word_label
     *
     * @mbggenerated Mon Sep 14 17:19:34 CST 2015
     */
    int updateByPrimaryKeySelective(WordLabel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table word_label
     *
     * @mbggenerated Mon Sep 14 17:19:34 CST 2015
     */
    int updateByPrimaryKey(WordLabel record);
}