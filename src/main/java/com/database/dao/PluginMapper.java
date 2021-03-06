package com.database.dao;

import com.database.bean.Plugin;
import com.database.bean.PluginExample;
import com.database.bean.PluginWithBLOBs;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface PluginMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table know_plugin
	 * @mbggenerated  Fri Apr 17 10:14:38 CST 2015
	 */
	int countByExample(PluginExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table know_plugin
	 * @mbggenerated  Fri Apr 17 10:14:38 CST 2015
	 */
	int deleteByExample(PluginExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table know_plugin
	 * @mbggenerated  Fri Apr 17 10:14:38 CST 2015
	 */
	int deleteByPrimaryKey(Integer pluginId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table know_plugin
	 * @mbggenerated  Fri Apr 17 10:14:38 CST 2015
	 */
	int insert(Plugin record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table know_plugin
	 * @mbggenerated  Fri Apr 17 10:14:38 CST 2015
	 */
	int insertSelective(Plugin record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table know_plugin
	 * @mbggenerated  Fri Apr 17 10:14:38 CST 2015
	 */
	List<Plugin> selectByExample(PluginExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table know_plugin
	 * @mbggenerated  Fri Apr 17 10:14:38 CST 2015
	 */
	Plugin selectByPrimaryKey(Integer pluginId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table know_plugin
	 * @mbggenerated  Fri Apr 17 10:14:38 CST 2015
	 */
	int updateByExampleSelective(@Param("record") Plugin record,
			@Param("example") PluginExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table know_plugin
	 * @mbggenerated  Fri Apr 17 10:14:38 CST 2015
	 */
	int updateByExample(@Param("record") Plugin record,
			@Param("example") PluginExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table know_plugin
	 * @mbggenerated  Fri Apr 17 10:14:38 CST 2015
	 */
	int updateByPrimaryKeySelective(Plugin record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table know_plugin
	 * @mbggenerated  Fri Apr 17 10:14:38 CST 2015
	 */
	int updateByPrimaryKey(Plugin record);
	
	List<Plugin> queryMyPluginByName(Plugin plugin);
}