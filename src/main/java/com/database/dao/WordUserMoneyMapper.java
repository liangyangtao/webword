package com.database.dao;

import com.database.bean.WordUserMoney;
import com.database.bean.WordUserMoneyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WordUserMoneyMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	int countByExample(WordUserMoneyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	int deleteByExample(WordUserMoneyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	int insert(WordUserMoney record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	int insertSelective(WordUserMoney record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	List<WordUserMoney> selectByExample(WordUserMoneyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	WordUserMoney selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	int updateByExampleSelective(@Param("record") WordUserMoney record,
			@Param("example") WordUserMoneyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	int updateByExample(@Param("record") WordUserMoney record,
			@Param("example") WordUserMoneyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	int updateByPrimaryKeySelective(WordUserMoney record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_user_money
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	int updateByPrimaryKey(WordUserMoney record);
}