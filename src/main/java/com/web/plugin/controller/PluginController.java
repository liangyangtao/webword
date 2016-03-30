package com.web.plugin.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.service.CommonController;
import com.unbank.UnbankException;
import com.unbank.model.UnbankModel;
import com.unbank.model.param.AreaParam;
import com.unbank.model.param.BankParam;
import com.unbank.model.param.DatafieldParam;
import com.unbank.model.param.DateParam;
import com.unbank.model.param.IndustryParam;
import com.unbank.model.param.Params;
import com.unbank.model.param.PropertyParam;
import com.unbank.model.param.PublishParam;
import com.unbank.plugin.service.PluginProxyService;
import com.web.plugin.service.PluginServices;


@Controller
@RequestMapping(value="/plugin/")
public class PluginController extends CommonController {
	
	@Autowired
	private PluginServices pluginServices;
	
	@Autowired
	private PluginProxyService pluginProxyService;//插件接口
	
	
	private static final Logger LOGGER = Logger.getLogger(PluginController.class);
	
	/*
	 * 插件搜索
	 * @param userId 用户Id
	 * @param name 插件名字
	 * @param searchType 搜索类型
	 * @param pageId
	 * @param pageSize
	 */
	@RequestMapping(value="/searchPlugins")
	@ResponseBody
	public void searchPlugins(HttpServletResponse response,HttpSession session,String name,int pageId,int pageSize){
		//System.out.println("SearchType="+SearchType);
		try {
			Integer userId=1;
			if(session.getAttribute("userId")!=null){
				userId = (Integer) session.getAttribute("userId");
			}
			Map<String, Object> maps = pluginServices.searchPlugins(name,pageId,pageSize);
			if(name==null){
				name="";
			}
			LOGGER.info("urlName=searchPlugins,urlMsg=搜索插件,userId="+userId+",name="+name);
			responseJson(response, maps);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * 我的插件搜索
	 * @param userId 用户Id
	 * @param name 插件名字
	 * @param searchType 搜索类型
	 * @param pageId
	 * @param pageSize
	 */
	@RequestMapping(value="/searchMyPlugins")
	@ResponseBody
	public void searchMyPlugins(HttpServletResponse response,String name,int userId,int pageId,int pageSize){
		//System.out.println("SearchType="+SearchType);
		try {
			Map<String, Object> maps = pluginServices.searchMyPlugins(userId,name,pageId,pageSize);
			
			LOGGER.info("urlName=searchMyPlugins,urlMsg=搜索我的插件,userId="+userId+",name="+name);
			responseJson(response, maps);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * 获取插件信息
	 * @param pluginId 
	 */
	@RequestMapping(value="/getPlugin")
	@ResponseBody
	public void getPlugin(HttpServletResponse response,int pluginId){
		//System.out.println("SearchType="+SearchType);
		try {
			Map<String, Object> maps = pluginServices.getPlugin(pluginId);
			LOGGER.info("urlName=getPlugin,urlMsg=获取插件,pluginId="+pluginId);
			responseJson(response, maps);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(value="/pluginDelById")
	@ResponseBody
	public void pluginDelById(int id, HttpSession session,HttpServletResponse response){
		int userId=0;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		Map<String,Object> map =pluginServices.pluginDelById(id, userId);
		try {
			LOGGER.info("urlName=pluginDelById,urlMsg=删除插件,pluginId="+id+",userId="+userId);
			responseJson(response, map);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * 获取地区
	 */
	
	/*
	 * 获取行业
	 */
	
	/**
	 * 
	 * 方法名称    :queryGrabContentText
	 * 功能描述    ：根据抓取内容id查询抓取内容
	 * 逻辑描述    :
	 * @param   :无
	 * @return  :void
	 * @throws  :无
	 * @since   :Ver 1.00
	 */
	@RequestMapping(value="/getPlugContent")
	@ResponseBody
	public void getPlugContent(HttpSession session,HttpServletRequest request,HttpServletResponse response){
		
		//plugin
		Map<String,Object> map = new HashMap<String,Object>();
		try {
		String strplugId = request.getParameter("plugId");
		String strindustryId = request.getParameter("industryId");
		String strareaId = request.getParameter("areaId");
		int plugId=0, industryId=0,areaId=0;
		
		if(!strplugId.equals("")){
			plugId=Integer.valueOf(strplugId);
		}
		if(!strindustryId.equals("")){
			industryId=Integer.valueOf(strindustryId);
		}
		if(!strareaId.equals("")){
			areaId=Integer.valueOf(strareaId);
		}
		String industryName = request.getParameter("industryName");
		String areaName = request.getParameter("areaName");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		//2015-11-26
		String publisherId = request.getParameter("publisherId");
		String publisherName = request.getParameter("publisherName");
		String bankTypeId = request.getParameter("bankTypeId");
		String bankTypeName = request.getParameter("bankTypeName");
		String propertyId = request.getParameter("propertyId");
		String propertyName = request.getParameter("propertyName");
		//2015-12-30
		String datafieldId = request.getParameter("datafieldId");//走势图
		
		Params params = new Params();
		// date
		DateParam dp = new DateParam();
		dp.setFrom(startTime);
		dp.setTo(endTime);
		params.put(DateParam.class, dp);
		// industry
		IndustryParam ip = new IndustryParam();
		ip.setIndustryId(industryId);
		ip.setName(industryName);
		params.put(IndustryParam.class, ip);
		// area
		AreaParam ap = new AreaParam();
		ap.setAreaId(areaId);
		ap.setName(areaName);
		params.put(AreaParam.class, ap);		

		//Publish
		if (!publisherId.isEmpty()){
			PublishParam pp = new PublishParam();
			pp.setPublisherId(Integer.valueOf(publisherId));
			pp.setPublisherName(publisherName);	
			params.put(PublishParam.class, pp);
		}

		//Bank
		if (!bankTypeId.isEmpty()){
			BankParam bp = new BankParam();
			bp.setTypeId(Integer.valueOf(bankTypeId));
			bp.setTypeName(bankTypeName);
			params.put(BankParam.class, bp);
		}

		//Property
		if (!propertyId.isEmpty()){
			PropertyParam pm = new PropertyParam();
			pm.setPropertyId(Integer.valueOf(propertyId));
			pm.setPropertyName(propertyName);
			params.put(PropertyParam.class, pm);
		}

		//Datafield 
		if (!datafieldId.isEmpty()){
			DatafieldParam dfp = new DatafieldParam();
			dfp.setBulkId(Integer.valueOf(datafieldId));
			params.put(DatafieldParam.class, dfp);
		}

		UnbankModel model=pluginProxyService.template(plugId, params);
//		map.put("data", model.getData());
		map.put("state", "success");
		map.put("template", model.getTemplate());
		//throw new UnbankException("时间错误!");
		LOGGER.info("urlName=getPlugContent,urlMsg=执行插件,plugId="+plugId+",params="+params);
		}catch(UnbankException e){
			map.put("state", "error");
			map.put("error", e.getMessage());
			//logger.error("ERROR!---1="+e.getMessage());
		}catch (Exception e) {
			map.put("state", "error");
			map.put("error", "插件未能正常执行，请您核对参数!");
			//logger.error("ERROR!---2=", e);
		}
		try {
			responseJson(response,map);
			LOGGER.info("urlName=getPlugContent,urlMsg=执行插件,state="+map.get("state")+",error="+map.get("error"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
	}

	/**
	 * 将插件另存为用户插件
	 * <p>Title: saveUserPlugin</p> 
	 * <p>Description: TODO</p>
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/saveUserPlugin")
	@ResponseBody
	public void saveUserPlugin(HttpServletRequest request,HttpSession session,HttpServletResponse response){
		int pluginId = Integer.valueOf(request.getParameter("pluginId"));
		String pluginName = request.getParameter("pluginName");
		int userId=1;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		Map<String, Object> maps = new HashMap<String, Object>();
		try {
			String info = pluginServices.queryMyPluginByName(pluginName,userId);
			if(info != null && !"".equals(info)){
				maps.put("state", "error");
				maps.put("info", "已有重名的插件，请更改后再保存");
			} else{
				pluginServices.saveUserPlugin(pluginId,pluginName,userId);
				maps.put("state", "success");
				maps.put("info", "保存成功");
			}
			LOGGER.info("urlName=saveUserPlugin,urlMsg=保存我的插件,pluginId="+pluginId+",userId="+userId+",state="+maps.get("state")+",info="+maps.get("info"));
			responseJson(response,maps);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
