package com.database.dao;

import com.database.bean.PluginUser;
import com.database.bean.PluginUserExample;
import com.web.plugin.bean.PluginUserExt;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface PluginUserMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table know_plugin_user
	 * @mbggenerated  Fri Apr 17 10:14:38 CST 2015
	 */
	int countByExample(PluginUserExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table know_plugin_user
	 * @mbggenerated  Fri Apr 17 10:14:38 CST 2015
	 */
	int deleteByExample(PluginUserExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table know_plugin_user
	 * @mbggenerated  Fri Apr 17 10:14:38 CST 2015
	 */
	int deleteByPrimaryKey(Integer pluginUserId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table know_plugin_user
	 * @mbggenerated  Fri Apr 17 10:14:38 CST 2015
	 */
	int insert(PluginUser record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table know_plugin_user
	 * @mbggenerated  Fri Apr 17 10:14:38 CST 2015
	 */
	int insertSelective(PluginUser record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table know_plugin_user
	 * @mbggenerated  Fri Apr 17 10:14:38 CST 2015
	 */
	List<PluginUser> selectByExample(PluginUserExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table know_plugin_user
	 * @mbggenerated  Fri Apr 17 10:14:38 CST 2015
	 */
	PluginUser selectByPrimaryKey(Integer pluginUserId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table know_plugin_user
	 * @mbggenerated  Fri Apr 17 10:14:38 CST 2015
	 */
	int updateByExampleSelective(@Param("record") PluginUser record,
			@Param("example") PluginUserExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table know_plugin_user
	 * @mbggenerated  Fri Apr 17 10:14:38 CST 2015
	 */
	int updateByExample(@Param("record") PluginUser record,
			@Param("example") PluginUserExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table know_plugin_user
	 * @mbggenerated  Fri Apr 17 10:14:38 CST 2015
	 */
	int updateByPrimaryKeySelective(PluginUser record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table know_plugin_user
	 * @mbggenerated  Fri Apr 17 10:14:38 CST 2015
	 */
	int updateByPrimaryKey(PluginUser record);
	
	List<PluginUserExt> selectByExampleExt(Map<String,Object> map);
}