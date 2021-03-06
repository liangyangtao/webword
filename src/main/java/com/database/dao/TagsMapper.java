package com.database.dao;

import com.database.bean.Tags;
import com.database.bean.TagsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TagsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tags
     *
     * @mbggenerated Tue Jul 14 14:21:17 GMT+08:00 2015
     */
    int countByExample(TagsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tags
     *
     * @mbggenerated Tue Jul 14 14:21:17 GMT+08:00 2015
     */
    int deleteByExample(TagsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tags
     *
     * @mbggenerated Tue Jul 14 14:21:17 GMT+08:00 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tags
     *
     * @mbggenerated Tue Jul 14 14:21:17 GMT+08:00 2015
     */
    int insert(Tags record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tags
     *
     * @mbggenerated Tue Jul 14 14:21:17 GMT+08:00 2015
     */
    int insertSelective(Tags record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tags
     *
     * @mbggenerated Tue Jul 14 14:21:17 GMT+08:00 2015
     */
    List<Tags> selectByExample(TagsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tags
     *
     * @mbggenerated Tue Jul 14 14:21:17 GMT+08:00 2015
     */
    Tags selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tags
     *
     * @mbggenerated Tue Jul 14 14:21:17 GMT+08:00 2015
     */
    int updateByExampleSelective(@Param("record") Tags record, @Param("example") TagsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tags
     *
     * @mbggenerated Tue Jul 14 14:21:17 GMT+08:00 2015
     */
    int updateByExample(@Param("record") Tags record, @Param("example") TagsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tags
     *
     * @mbggenerated Tue Jul 14 14:21:17 GMT+08:00 2015
     */
    int updateByPrimaryKeySelective(Tags record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tags
     *
     * @mbggenerated Tue Jul 14 14:21:17 GMT+08:00 2015
     */
    int updateByPrimaryKey(Tags record);
}