package com.database.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.database.bean.NewsMyPlate;
import com.database.bean.NewsMyPlateExample;

public interface NewsMyPlateMapper {

	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	int countByExample(NewsMyPlateExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	int deleteByExample(NewsMyPlateExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	int deleteByPrimaryKey(Integer plateId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	int insert(NewsMyPlate record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	int insertSelective(NewsMyPlate record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	List<NewsMyPlate> selectByExample(NewsMyPlateExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	NewsMyPlate selectByPrimaryKey(Integer plateId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	int updateByExampleSelective(@Param("record") NewsMyPlate record,
			@Param("example") NewsMyPlateExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	int updateByExample(@Param("record") NewsMyPlate record,
			@Param("example") NewsMyPlateExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	int updateByPrimaryKeySelective(NewsMyPlate record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table news_my_plate
	 * @mbggenerated  Fri Mar 04 09:49:36 CST 2016
	 */
	int updateByPrimaryKey(NewsMyPlate record);

	/**
	 * 以下是自定义的sql
	 */
	
	/**
	 * 获取用户栏目列表
	 * @param userId
	 * @return
	 */
	List<NewsMyPlate> getNewsMyPlate(@Param("userId") int userId);
	
	/**
	 * 获取用户pid栏目列表
	 * @param userId
	 * @param pid
	 * @return
	 */
	List<NewsMyPlate> getNewsMyPlateByPid(@Param("userId") int userId,@Param("pid") int pid);
	
	/**
	 * 获取栏目数
	 * @param userId
	 * @param pid
	 * @return
	 */
	int getCountByPid(@Param("userId") int userId,@Param("pid") int pid);
	
	/**
	 * 获取最大的orde_id
	 * @param userId
	 * @param pid
	 * @return
	 */
	int getNewsPlateMaxOrder(@Param("userId") int userId,@Param("pid") int pid);
	
	/**
	 * 仅更新orderId
	 * @param record
	 */
	void updateOrder(NewsMyPlate record);
	
	/**
	 * 更新id
	 * @param userId
	 * @param pid
	 * @param orderId
	 * @param plateId
	 */
	void updateOrderAdd(@Param("userId") int userId,@Param("pid") int pid,@Param("orderId") int orderId,@Param("plateId") int plateId);
	
	void updateOrderAdd(NewsMyPlate record);
}