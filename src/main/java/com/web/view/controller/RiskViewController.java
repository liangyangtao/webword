package com.web.view.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.service.CommonController;
import com.database.bean.Module;
import com.database.bean.UserSaveContent;
import com.database.bean.WordUsers;
import com.web.view.service.RiskViewService;
import com.web.view.bean.Risk;
import com.web.view.bean.RiskSearchCondition;
import com.web.view.bean.RiskWithModule;
import com.web.view.bean.SearchRiskData;
import com.web.view.bean.UserBuyThemeRiskStatus;

@Controller
@RequestMapping(value = "/risk/view/")
public class RiskViewController extends CommonController {

	@Autowired
	private RiskViewService riskViewService;
	private static final Logger LOGGER = Logger.getLogger(RiskViewController.class);

	@RequestMapping(value = "/getChildModuleAndNews")
	public String getChildModuleAndNews(int parentModuleId, int from, int pageSize, HttpServletResponse response,Model model
			) throws IOException {
		List<Module> moduleList = riskViewService.getModulesByParentId(parentModuleId);
		List<RiskWithModule> riskWithModuleList = new ArrayList<RiskWithModule>();
		for (Module module : moduleList) {
			RiskSearchCondition condition = new RiskSearchCondition();
			List<Integer> categoryIds = new ArrayList<Integer>();
			
			categoryIds.add(module.getMid());
			condition.setCategoryIds(categoryIds);
			/**
			 * 不重复的信息
			 */
			condition.setRepetitive("false");
			SearchRiskData searchRiskData = riskViewService.fetchRisk(condition, pageSize, from, null, null, null,
					false);
			RiskWithModule riskWithModule = new RiskWithModule();
			riskWithModule.setModule(module);
			riskWithModule.setRiskList(searchRiskData.getData());
			riskWithModuleList.add(riskWithModule);
		}
		model.addAttribute("riskWithModuleList", riskWithModuleList);
		return "/riskview/home_content";
	}
	
	@RequestMapping(value = "/home")
	public String home(HttpServletResponse response, HttpSession session,HttpServletRequest request, 
			Model model) {
		int id=0;//栏目id，0显示第一个栏目
		String idStr = request.getParameter("id");
		if(idStr!=null&&!("".equals(idStr))){
			id = Integer.parseInt(idStr);
		}
		//获取用户信息
		WordUsers user = (WordUsers) session.getAttribute("user");
		int userId=0;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		//获取区域栏目
		//List<Module> listModule = riskViewService.getModuleByPid(0);//获取一级栏目
		List<Module> listModule = riskViewService.getModulesByParentId(0);//获取一级栏目
		
		model.addAttribute("user", user);
		model.addAttribute("listModule", listModule);
		
		
		int currentPId=id;
		
		if(id==0){
			currentPId=listModule.get(0).getMid();
		}
		
		model.addAttribute("id", currentPId);
		
		List<Module> moduleList = riskViewService.getModulesByParentId(currentPId);
		List<RiskWithModule> riskWithModuleList = new ArrayList<RiskWithModule>();
		for (Module module : moduleList) {
			RiskSearchCondition condition = new RiskSearchCondition();
			List<Integer> categoryIds = new ArrayList<Integer>();
			
			categoryIds.add(module.getMid());
			condition.setCategoryIds(categoryIds);
			/**
			 * 不重复的信息
			 */
			condition.setRepetitive("false");
			SearchRiskData searchRiskData = riskViewService.fetchRisk(condition, 8, 0, null, null, null,
					false);
			RiskWithModule riskWithModule = new RiskWithModule();
			riskWithModule.setModule(module);
			riskWithModule.setRiskList(searchRiskData.getData());
			riskWithModuleList.add(riskWithModule);
		}
		model.addAttribute("riskWithModuleList", riskWithModuleList);
		
		//查询用户状态 1 表示已购买栏目，2 栏目  11 购买  12 购买即将到期  13 购买已经到期  21 试用阶段 22 试用即将到期 23 试用已经到期  
//		if(user!=null){
//			int status=riskViewService.getUserRiskThemeStatus(user);
//			model.addAttribute("status", status);
//			if(status==UserBuyThemeRiskStatus.JJDQ_SY){
//				model.addAttribute("endTime", riskViewService.getTimeLeft(user));
//			}
		
//		}
//		model.addAttribute("status", 23);
//		model.addAttribute("endTime", "2天3小时");
		//加入试用期到期时间，是不是今天首次登录，是否是试用期
//		int status=riskViewService.getUserRiskThemeStatus(user);
//		String endTime =riskViewService.getExpirationDate(user);
//		//String isFirstLogin =riskViewService.getIsFirstLogin(user);
//		map.put("status", String.valueOf(status));
//		map.put("endTime", endTime);
//		//map.put("isFirstLogin", isFirstLogin);
		try{
			LOGGER.info("urlName=risk/view/home,urlMsg=风险预警首页,userId="+userId);
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		return "/riskview/home";
	}
	/**
	 * 栏目下的新闻
	 * @param moduleId
	 * @param parentModuleId
	 * @param page
	 * @param pageSize
	 * @param session
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getNewsByModuleId")
	public String getNewsByModuleId(Integer moduleId,Integer parentModuleId, Integer page,Integer pageSize,HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model)
			throws IOException {
		int id=0;
		//获取用户信息
		WordUsers user = (WordUsers) session.getAttribute("user");
		int userId=0;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
//		//查看用户是否有权限查看该记录 
//		int status=riskViewService.getUserRiskThemeStatus(user);
//		if(status==UserBuyThemeRiskStatus.GQ_SY){//试用期结束并且未购买栏目，
//			return "/riskview/login";
//		}
		
		//获取区域栏目
		//List<Module> listModule = riskViewService.getModuleByPid(0);//获取一级栏目
		List<Module> listModule = riskViewService.getModulesByParentId(0);//获取一级栏目
		model.addAttribute("id", id);
		model.addAttribute("user", user);
		model.addAttribute("listModule", listModule);
		
		if(pageSize==null){
			pageSize=10;
		}
		List<Module> moduleList=new ArrayList<Module>();
		moduleList = riskViewService.getModulesByParentId(parentModuleId);
		
		if(moduleList==null||moduleList.size()==0){
			Module module= riskViewService.getModuleById(parentModuleId);
			moduleList.add(module);
		}
		
		model.addAttribute("moduleList", moduleList);
		model.addAttribute("currentModuleId", moduleId);
		RiskSearchCondition condition = new RiskSearchCondition();
		List<Integer> categoryIds = new ArrayList<Integer>();
		Integer currentModuleId;
		
		if(moduleId!=null){
			currentModuleId=moduleId;
		}else{
			currentModuleId=moduleList.get(0).getMid();
		}
		
		categoryIds.add(currentModuleId);
		model.addAttribute("currentModuleId", currentModuleId);
		List<Module> modulePathList=riskViewService.getModulePathById(currentModuleId);
		model.addAttribute("modulePathList", modulePathList);
		condition.setCategoryIds(categoryIds);
		condition.setRepetitive("false");
		
		int from=(page-1)*pageSize;
		
		SearchRiskData searchRiskData = riskViewService.fetchRisk(condition, pageSize, from, null, null, null, false);
		//map.put("searchRiskData", searchRiskData);
		model.addAttribute("searchRiskData", searchRiskData);
		model.addAttribute("parentModuleId", parentModuleId);
		
		int currentPageNum=from/pageSize+1;
		int totalPageNum=(int) (searchRiskData.getCount()/pageSize)+1;
		
		model.addAttribute("currentPageNum", currentPageNum);
		model.addAttribute("totalPageNum", totalPageNum);
		try{
			String moduleNames = "";
			String moduleIds = "";
			if (modulePathList != null && modulePathList.size() > 0){
				for (int i = 0;i < modulePathList.size();i ++){
					if (i == modulePathList.size() - 1){
						moduleNames += modulePathList.get(i).getMname();
						moduleIds += modulePathList.get(i).getMid();
					}else{
						moduleNames += modulePathList.get(i).getMname()+ ";";
						moduleIds += modulePathList.get(i).getMid()+ ";";
					}
				}
			}
			LOGGER.info("urlName=risk/view/getNewsByModuleId,urlMsg=风险栏目下的信息,parentModuleId="+parentModuleId+",userId="+userId+",moduleNames="+moduleNames+",moduleIds="+moduleIds);
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		
		return "/riskview/riskMenu";
		
	}

	/**
	 * 依据原新闻ID以及分类ID获取风险信息
	 * @param id 对应抓取的ID
	 * @param categoryId 分类ID
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="getNewsById")
	public String getNewsById(String esId,int riskId,int categoryId,ModelMap map,HttpSession session, HttpServletRequest request, HttpServletResponse response,Model model){
		int id=0;
		String idStr = request.getParameter("id");
		if(idStr!=null&&!("".equals(idStr))){
			id = Integer.parseInt(idStr);
		}
		//获取用户信息
		WordUsers user = (WordUsers) session.getAttribute("user");
		int userId=0;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		//获取区域栏目
		//List<Module> listModule = riskViewService.getModuleByPid(0);//获取一级栏目
		List<Module> listModule = riskViewService.getModulesByParentId(0);//获取一级栏目
		model.addAttribute("id", id);
		model.addAttribute("user", user);
		model.addAttribute("listModule", listModule);
		
		//Risk risk=riskViewService.fetchRiskById(riskId,categoryId);
		Risk risk = new Risk();
		List<Module> modulPathList = new ArrayList<Module>();
		UserSaveContent userSaveContent=null;
		if(user!=null){
			risk=riskViewService.fetchRiskByEsId(esId);
			userSaveContent=riskViewService.getUserSaveContent(userId,esId);//查询是否收藏该文章
			if(risk!=null){
				//获取栏目树
				modulPathList=riskViewService.getModulePath(categoryId,risk);
				if(riskViewService.checkPermissions(user)){//判断试用期
					//替换图片
					risk.setContent(riskViewService.replaceImg(risk.getContent()));
					map.put("canShare","Y");
				}else if(riskViewService.checkColumns(risk,user)){//判断正式订阅是否到期
					risk.setContent(riskViewService.replaceImg(risk.getContent()));
					map.put("canShare","Y");
				}else{
					if(riskViewService.checkUserModules(risk,user)){//判断是否有正式订阅
						risk.setTitle("提示：尊敬的用户，您的订阅已到期，请您联系客服续费。");
						risk.setContent("");
						map.put("canShare","N");
					}else{
						risk.setTitle("提示：尊敬的用户，您的试用已到期，请您联系客服续费。");
						risk.setContent("");
						map.put("canShare","N");
					}
				}
			}else{
				risk = new Risk();
				risk.setCrawl_id(riskId);
				risk.setTitle("该条新闻不存在");
				map.put("canShare","N");
			}
		}else{
			risk.setCrawl_id(riskId);
			risk.setTitle("请先登录");
			map.put("canShare","N");
		}
		List<Module> modulPathNewList = new ArrayList<Module>();
		modulPathNewList.addAll(modulPathList);
		Collections.reverse(modulPathNewList);
		map.put("modulPathNewList",modulPathNewList);
		map.put("modulePathList",modulPathList);
		map.put("risk",risk);
		map.put("categoryId",categoryId);
		map.put("isCollection",userSaveContent!=null?"Y":"N");
		
		try{
			String moduleNames = "";
			String moduleIds = "";
			if (modulPathList != null && modulPathList.size() > 0){
				for (int i = 0;i < modulPathList.size();i ++){
					if (i == modulPathList.size() - 1){
						moduleNames += modulPathList.get(i).getMname();
						moduleIds += modulPathList.get(i).getMid();
					}else{
						moduleNames += modulPathList.get(i).getMname()+ ";";
						moduleIds += modulPathList.get(i).getMid()+ ";";
					}
				}
			}
			LOGGER.info("urlName=risk/view/getNewsById,urlMsg=风险信息详情,esId="+esId+",userId="+userId+",moduleNames="+moduleNames+",moduleIds="+moduleIds);
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		return "/riskview/risk";
	}
	/**
	 * 依据原新闻ID以及分类ID获取企业风险信息
	 * @param id 对应抓取的ID
	 * @param categoryId 分类ID
	 * @param map
	 * @return
	 */
	@RequestMapping(value="getNewsByMid")
	public String getNewsByMid(String esId,int riskId,int categoryId,ModelMap map,HttpSession session, HttpServletRequest request, HttpServletResponse response,Model model){
		int id=0;
		String idStr = request.getParameter("id");
		if(idStr!=null&&!("".equals(idStr))){
			id = Integer.parseInt(idStr);
		}
		//获取用户信息
		WordUsers user = (WordUsers) session.getAttribute("user");
		int userId=0;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		//获取区域栏目
		List<Module> listModule = riskViewService.getModulesByParentId(0);//获取一级
		model.addAttribute("id", id);
		model.addAttribute("user", user);
		model.addAttribute("listModule", listModule);
		
		Risk risk = new Risk();
		List<Module> modulPathList = new ArrayList<Module>();
		UserSaveContent userSaveContent=null;
		if(user!=null){
			risk=riskViewService.fetchRiskByEsId(esId);
			userSaveContent=riskViewService.getUserSaveContent(userId,esId);//查询是否收藏该文章
			if(risk!=null){
				//获取栏目树
				modulPathList=riskViewService.getModulePath(categoryId,risk);
			    if(riskViewService.checkCompany(risk,user)){//判断企业组是否到期
					risk.setContent(riskViewService.replaceImg(risk.getContent()));
					map.put("canShare","Y");
				}else{
					risk.setTitle("提示：尊敬的用户，您的订阅已到期，请您联系客服续费。");
					risk.setContent("");
					map.put("canShare","N");
				}
			}else{
				risk = new Risk();
				risk.setCrawl_id(riskId);
				risk.setTitle("该条新闻不存在");
				map.put("canShare","N");
			}
			
		}else{
			risk.setCrawl_id(riskId);
			risk.setTitle("请先登录");
			map.put("canShare","N");
		}
		List<Module> modulPathNewList = new ArrayList<Module>();
		modulPathNewList.addAll(modulPathList);
		Collections.reverse(modulPathNewList);
		map.put("modulPathNewList",modulPathNewList);
		map.put("modulePathList",modulPathList);
		map.put("risk",risk);
		map.put("categoryId",categoryId);
		map.put("isCollection",userSaveContent!=null?"Y":"N");
		
		try{
			String moduleNames = "";
			String moduleIds = "";
			if (modulPathList != null && modulPathList.size() > 0){
				for (int i = 0;i < modulPathList.size();i ++){
					if (i == modulPathList.size() - 1){
						moduleNames += modulPathList.get(i).getMname();
						moduleIds += modulPathList.get(i).getMid();
					}else{
						moduleNames += modulPathList.get(i).getMname()+ ";";
						moduleIds += modulPathList.get(i).getMid()+ ";";
					}
				}
			}
			LOGGER.info("urlName=risk/view/getNewsByMid,urlMsg=企业风险信息详情,esId="+esId+",userId="+userId+",moduleNames="+moduleNames+",moduleIds="+moduleIds);
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		return "/riskview/risk";
	}
	
	/**
	 * 依据类别ID或者类别下的风险信息
	 * @param categoryId 类别ID
	 * @param from 分页起始
	 * @param pageSize 分页大小
	 * @param response
	 * @param map
	 * @throws IOException
	 */
	@RequestMapping(value="getNewsByModule")
	public void getNewsByModule(HttpSession session,int categoryId,int from,int pageSize,HttpServletResponse response,ModelMap map) throws IOException{
		RiskSearchCondition condition=new RiskSearchCondition();
		int userId=0;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		List<Integer> categoryIds=new ArrayList<Integer>();
		categoryIds.add(categoryId);
		condition.setCategoryIds(categoryIds);
		SearchRiskData searchRiskData = riskViewService.fetchRisk(condition, pageSize, from, null, null, null, false);
		map.put("searchRiskData", searchRiskData);
		
		try{
			LOGGER.info("urlName=risk/view/getNewsByModule,urlMsg=风险栏目信息,categoryId="+categoryId+",userId="+userId);
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		responseJson(response, map);
	}
	
	/**
	 * 基于内容的推荐
	 * @param riskId
	 * @param categoryId
	 * @param from
	 * @param pageSize
	 * @param response
	 * @param map
	 * @throws IOException
	 */
	@RequestMapping(value="getMoreLikeRisks")
	public void getMoreLikeRisks(int riskId,int categoryId,HttpServletResponse response,ModelMap map) throws IOException{
		SearchRiskData searchRiskData =riskViewService.getMoreLikeRisks(riskId, categoryId, 0, 10);
	if(null!=searchRiskData){
		map.put("riskList", searchRiskData.getData());
		responseJson(response, map);
	}
	}
	
	@RequestMapping(value="searchByKeyword")
	public void searchByKeyword(HttpSession session,int categoryId,String keyword,int from,int pageSize,HttpServletResponse response,ModelMap map) throws IOException{
		RiskSearchCondition condition=new RiskSearchCondition();
		/**
		 * keyword
		 */
		int userId=0;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		List<Integer> categoryIds=new ArrayList<Integer>();
		categoryIds.add(categoryId);
		condition.setCategoryIds(categoryIds);
		SearchRiskData searchRiskData = riskViewService.fetchRisk(condition, pageSize, from, null, null, null, false);
		map.put("searchRiskData", searchRiskData);
		
		try{
			LOGGER.info("urlName=risk/view/searchByKeyword,urlMsg=风险栏目搜索,categoryId="+categoryId+",keyword="+keyword+",userId="+userId);
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		responseJson(response, map);
	}
	/**
	 * 登录提示
	 */
	@RequestMapping(value = "/getTipsMsg")
	public void getTipsMsg(HttpServletResponse response, HttpSession session) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		if(user==null){
			return;
		}
		int status=riskViewService.getUserRiskThemeStatus(user);
		String endTime =riskViewService.getExpirationDate(user);
		//String isFirstLogin =riskViewService.getIsFirstLogin(user);
		Map<String, String> map=new HashMap<String, String>();
		map.put("status", String.valueOf(status));
		map.put("endTime", endTime);
		//map.put("isFirstLogin", isFirstLogin);
		try {
			responseJson(response,map );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
