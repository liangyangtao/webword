package com.database.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.database.bean.WordShoppingCart;
import com.database.bean.WordShoppingCartExample;

public interface WordShoppingCartMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_shopping_cart
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	int countByExample(WordShoppingCartExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_shopping_cart
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	int deleteByExample(WordShoppingCartExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_shopping_cart
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	int deleteByPrimaryKey(Integer cartId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_shopping_cart
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	int insert(WordShoppingCart record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_shopping_cart
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	int insertSelective(WordShoppingCart record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_shopping_cart
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	List<WordShoppingCart> selectByExample(WordShoppingCartExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_shopping_cart
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	WordShoppingCart selectByPrimaryKey(Integer cartId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_shopping_cart
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	int updateByExampleSelective(@Param("record") WordShoppingCart record,
			@Param("example") WordShoppingCartExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_shopping_cart
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	int updateByExample(@Param("record") WordShoppingCart record,
			@Param("example") WordShoppingCartExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_shopping_cart
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	int updateByPrimaryKeySelective(WordShoppingCart record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table word_shopping_cart
	 * @mbggenerated  Fri Dec 18 15:37:48 GMT+08:00 2015
	 */
	int updateByPrimaryKey(WordShoppingCart record);
	
	List<WordShoppingCart> selectMyShoppingCart(Map<String,Object> map);
}