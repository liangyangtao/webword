package com.web.plugin.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.database.bean.Plugin;
import com.database.bean.PluginExample;
import com.database.bean.PluginUser;
import com.database.bean.PluginUserExample;
import com.database.dao.PluginMapper;
import com.database.dao.PluginUserMapper;
import com.web.plugin.bean.PluginUserExt;

@Service
public class PluginServices {
	
	@Autowired
	private PluginUserMapper pluginUserMapper;
	
	@Autowired
	private PluginMapper pluginMapper;
	
	
	/*
	 * 搜索插件
	 * @param userId 用户的id
	 * @param name 搜索的名字
	 * @param pageId 页数
	 * @param pageSize 页大小
	 */
	public Map<String, Object> searchPlugins(String name,int pageId,int pageSize){
		Map<String, Object> maps = new HashMap<String, Object>();
		int count =0;
		int pageCount=0;
		
		PluginExample example = new PluginExample();
		com.database.bean.PluginExample.Criteria cr =null;
		com.database.bean.PluginExample.Criteria cra =null;
		String[] names = name.split(" ");
		for(int i=0;i<names.length;i++){
			cr = example.createCriteria();
			cra = example.createCriteria();
			if(!("".equals(names[i])) && names[i]!=null) {
				cr.andPluginNameLike("%"+names[i]+"%");
				cra.andPluginNoteLike("%"+names[i]+"%");//2016-01-22 增加按关键词查询
			}
			example.or(cr);
			example.or(cra);
		}
		count= pluginMapper.countByExample(example);
		
		int start = (pageId-1)*pageSize;
		example.setLimitStart(start);
		example.setLimitEnd(pageSize);
		List<Plugin> list = pluginMapper.selectByExample(example);
		//计算页数
		pageCount=count%pageSize==0?count/pageSize:count/pageSize+1;
		
		maps.put("pageCount",pageCount);
		maps.put("count", count);
		maps.put("pageId",pageId);
		maps.put("pageSize",pageSize);
		maps.put("list", list);
		return maps;
	}
	/*
	 * 搜索我的插件
	 * @param userId 用户的id
	 * @param name 搜索的名字
	 * @param pageId 页数
	 * @param pageSize 页大小
	 */
	public Map<String, Object> searchMyPlugins(int userId,String name,int pageId,int pageSize){
		Map<String, Object> maps = new HashMap<String, Object>();
		int count =0;
		int pageCount=0;
		
		PluginUserExample example = new PluginUserExample();
		com.database.bean.PluginUserExample.Criteria cr = null;
		List<String> pluginNames = new ArrayList<String>();
		if(name != null && !"".equals(name)){
			String[] names = name.split(" ");
			for(int i=0;i<names.length;i++){
				pluginNames.add(names[i]);
				cr = example.createCriteria();
				if(userId!=0) cr.andUserIdEqualTo(userId);
				if(!("".equals(names[i])) && names[i]!=null) {
					cr.andMyPluginNameLike("%"+names[i]+"%");
				}
				example.or(cr);
			}
		} else{
			cr = example.createCriteria();
			cr.andUserIdEqualTo(userId);
		}
		count= pluginUserMapper.countByExample(example);
		int start = (pageId-1)*pageSize;
		example.setLimitStart(start);
		example.setLimitEnd(pageSize);
		/*String sql = "b.plugin_id ";
		if(userId != 0){
			sql += " and a.user_id="+userId;
		}
		if(name != null && !"".equals(name)){
			sql += " and ( 1<>1 ";
			String[] names = name.split(" ");
			for(int i=0;i<names.length;i++){
				if(!("".equals(names[i])) && names[i]!=null) {
					sql += " or my_plugin_name like '"+names[i]+"' ";
				}
			}
			sql += " )";
		}
		sql += " limit "+start+","+pageSize;*/
		//List<PluginUser> list = pluginUserMapper.selectByExample(example);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("pluginNames", pluginNames);
		map.put("limitStart", start);
		map.put("limitEnd", pageSize);
		List<PluginUserExt> list = pluginUserMapper.selectByExampleExt(map);
		//计算页数
		pageCount=count%pageSize==0?count/pageSize:count/pageSize+1;
		
		maps.put("pageCount",pageCount);
		maps.put("count", count);
		maps.put("pageId",pageId);
		maps.put("pageSize",pageSize);
		maps.put("list", list);
		return maps;
	}
	/*
	 * 查询单个插件
	 * @param pluginId 插件pluginId
	 */
	public Map<String, Object> getPlugin(int pluginId){
		Map<String, Object> maps = new HashMap<String,Object>();
		Plugin plugin = pluginMapper.selectByPrimaryKey(pluginId);
		maps.put("list",plugin);
		return maps;
		
	}
	/**
	 * 删除我的插件
	 * @param id
	 * @param userId
	 * @return
	 */
	public Map<String,Object> pluginDelById(int id,int userId){
		Map<String,Object> maps = new HashMap<String,Object>();
		PluginUser pluginUser=pluginUserMapper.selectByPrimaryKey(id);
		if(pluginUser!=null){
			if(pluginUser.getUserId()==userId){
				int flag = pluginUserMapper.deleteByPrimaryKey(id);
				maps.put("state","success");
				maps.put("info", "删除成功");
				maps.put("flag",flag);
			}else{
				maps.put("state","error");
				maps.put("info", "不是此用户的插件");
			}
		}else{
			maps.put("state","error");
			maps.put("info", "没有此记录");
		}
		return maps;
	}
	
	public String queryMyPluginByName(String pluginName,int userId){
		String info = "";
		Plugin plugin = new Plugin();
		plugin.setUserId(userId);
		plugin.setPluginName(pluginName);
		List<Plugin> plugins = pluginMapper.queryMyPluginByName(plugin);
		if(plugins.size() > 0){
			info = "error";
		}
		return info ;
	}
	
	public void saveUserPlugin(int pluginId,String pluginName,int userId){
		PluginUser record = new PluginUser();
		record.setPluginId(pluginId);
		record.setMyPluginName(pluginName);
		record.setUserId(userId);
		DateFormat dd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		record.setInsertTime(Timestamp.valueOf(dd.format(new Date())));
		pluginUserMapper.insert(record);
	}
}
