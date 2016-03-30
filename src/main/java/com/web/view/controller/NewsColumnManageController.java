package com.web.view.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.service.CommonController;
import com.database.bean.NewsColumn;
import com.database.bean.NewsColumnGroup;
import com.database.bean.NewsLabel;
import com.database.bean.NewsMyPlate;
import com.database.bean.NewsSource;
import com.database.bean.WordUsers;
import com.util.ConstantUtils;
import com.web.view.service.NewsColumnManageService;
import com.web.view.service.NewsHotColumnViewService;

@Controller
@RequestMapping(value = "/news/column/")
public class NewsColumnManageController extends CommonController {

	@Autowired
	NewsColumnManageService newsColumnManageService;
	@Autowired
	NewsHotColumnViewService newsHotColumnViewService;
	private int movepage = 5;
	private static final Logger LOGGER = Logger.getLogger(NewsColumnManageController.class);
	
	
	/**
	 * 栏目管理页面
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/myPlateEdit")
	public String myPlateEdit(HttpServletResponse response, HttpSession session,Model model) {
		
		WordUsers user = (WordUsers) session.getAttribute("user");
		//取出热点栏目
		List<NewsMyPlate> allList = newsColumnManageService.getNewsMyPlate(user.getUserId());
		List<NewsColumnGroup> hotGroups = newsColumnManageService.getRecNewsColumn(ConstantUtils.WordColumnType.HOT, allList);
		model.addAttribute("hotGroups", hotGroups);
		fillUserPlates(model, user);
		model.addAttribute("showR", "N");
		return "/news/myPlateEdit";
	}
	/**
	 * 用户导航栏目
	 * 
	 * @param model
	 * @param user
	 */
	private void fillUserPlates(Model model, WordUsers user) {
		List<NewsMyPlate> newsMyPlates = null;
		// 用户栏目
		if (user != null) {
			newsMyPlates = newsHotColumnViewService.getMyPLates(user
					.getUserId());
		} else {
			newsMyPlates = newsHotColumnViewService.getMyPLates(1);
		}
		model.addAttribute("newsMyPlates", newsMyPlates);
	}
	/**
	 * 获得个人的栏目,返回树形结构
	 * @param response
	 */
	@RequestMapping(value="/getUserNewsMyPlate")
	@ResponseBody
	public void getUserNewsMyPlate(HttpServletResponse response,HttpSession session){
		
		Integer useId = 0;//获取默认的栏目
		if(session.getAttribute("userId")!=null){
			useId = (Integer) session.getAttribute("userId");
		}
		//返回树形结构
		List<NewsMyPlate> plateList = newsColumnManageService.getUserNewsMyPlate(useId);
		Map<String, Object> maps = new HashMap<String, Object>();
		if (useId==0){
			maps.put("status", "error");
			maps.put("info", "用户不存在");
		}else{
			maps.put("status", "success");
			maps.put("info", "成功");
		}

		maps.put("list", plateList);
		try {
			LOGGER.info("urlName=news/column/getUserWordPlate,urlMsg=新闻板块获取栏目,useId="+useId);
			responseJson(response, maps);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 栏目编辑页面动态获取推荐栏目的状态
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getHotColumnStatus")
	public void getHotColumnStatus(HttpServletResponse response, HttpSession session) {
		
		WordUsers user = (WordUsers) session.getAttribute("user");
		List<NewsMyPlate> list = new ArrayList<NewsMyPlate>();
		if(user!=null){
			list = newsColumnManageService.getNewsMyPlate((user.getUserId()));
		}
		//取出热点栏目
		List<NewsColumnGroup> hotGroups = newsColumnManageService.getRecNewsColumn(ConstantUtils.WordColumnType.HOT, list);
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("hotGroups", hotGroups);
		try {
			responseJson(response, maps);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加栏目
	 * @param response
	 * @param plate
	 */
	@RequestMapping(value="/plateAdd")
	@ResponseBody
	public void plateAdd(HttpServletResponse response,NewsMyPlate plate,HttpSession session){
		
		Map<String, Object> maps = new HashMap<String, Object>();
		Integer useId = 0;//获取默认的栏目
		if(session.getAttribute("userId")!=null){
			useId = (Integer) session.getAttribute("userId");
		}
		if(useId == 0){
			maps.put("status", "error");
			maps.put("info", "请重新登录！");
		}else{
			plate.setUserId(useId);
			NewsMyPlate newPlate = newsColumnManageService.plateAdd(plate);
			maps.put("status", "success");
			maps.put("info", "成功");
			maps.put("wordPlate", newPlate);
		}
		try {
			LOGGER.info("urlName=news/column/plateAdd,urlMsg=新闻板块添加栏目,useId="+useId+",status="+maps.get("status")+",info="+maps.get("info"));
			responseJson(response, maps);		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
		
	/**
	 * 添加推荐的栏目
	 * @param response
	 * @param plate
	 */
	@RequestMapping(value="/plateAddHot")
	@ResponseBody
	public void plateAddHot(HttpServletResponse response,NewsMyPlate plate,HttpSession session){
		
		Map<String, Object> maps = new HashMap<String, Object>();
		Integer useId = 0;//获取默认的栏目
		if (session.getAttribute("userId") != null){
			useId = (Integer) session.getAttribute("userId");
		}
		if (useId == 0){
			maps.put("status", "error");
			maps.put("info", "请重新登录！");
		}else{
			plate.setUserId(useId);
			NewsMyPlate newPlate = newsColumnManageService.plateAddHot(plate);
			maps.put("status", "success");
			maps.put("info", "成功");
			maps.put("wordPlate", newPlate);
		}
		try {
			LOGGER.info("urlName=news/column/plateAddHot,urlMsg=新闻板块添加推荐的栏目,useId="+useId+",status="+maps.get("status")+",info="+maps.get("info"));
			responseJson(response, maps);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * 获取栏目
	 * @param response
	 * @param plate
	 */
	@RequestMapping(value="/plateGet")
	@ResponseBody
	public void plateGet(HttpServletResponse response,int plateId){
		
		NewsMyPlate newPlate = newsColumnManageService.plateGet(plateId);
		Map<String, Object> maps = new HashMap<String, Object>();
		
		if(newPlate != null){
			maps.put("status", "success");
			maps.put("info", "成功");
		}else{
			maps.put("status", "error");
			maps.put("info", "失败");
		}
		maps.put("wordPlate", newPlate);
		try {
			responseJson(response, maps);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加修改
	 * @param response
	 * @param plate
	 */
	@RequestMapping(value="/plateEdit")
	@ResponseBody
	public void plateEdit(HttpSession session,HttpServletResponse response,NewsMyPlate plate){
		
		boolean flag = newsColumnManageService.plateEdit(plate);
		Map<String, Object> maps = new HashMap<String, Object>();
		int userId = 0;
		if (session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		if (flag){
			maps.put("status", "success");
			maps.put("info", "成功");
		}else{
			maps.put("status", "error");
			maps.put("info", "失败");
		}
		maps.put("wordPlate", plate);
		try {
			LOGGER.info("urlName=news/column/plateEdit,urlMsg=新闻板块修改栏目,userId="+userId+",status="+maps.get("status")+",info="+maps.get("info"));
			responseJson(response, maps);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除栏目
	 * @param response
	 * @param plateid
	 */
	@RequestMapping(value="/plateDel")
	@ResponseBody
	public void plateDel(HttpSession session,HttpServletResponse response,int plateId){
		
		boolean flag = newsColumnManageService.plateDel(plateId);
		Map<String, Object> maps = new HashMap<String, Object>();
		int userId = 0;
		if (session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		if (flag){
			maps.put("status", "success");
			maps.put("info", "删除成功");
		}else{
			maps.put("status", "error");
			maps.put("info", "失败");
		}
		maps.put("plateId", plateId);
		try {
			LOGGER.info("urlName=news/column/plateDel,urlMsg=新闻板块删除栏目,userId="+userId+",status="+maps.get("status")+",info="+maps.get("info"));
			responseJson(response, maps);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取推荐栏目的详情 
	 * @param response
	 * @param columnId
	 */
	@RequestMapping(value="/getColumn")
	@ResponseBody
	public void getColumn(HttpServletResponse response,int columnId){
		
		NewsColumn wordColumn = newsColumnManageService.getNewsColumn(columnId);
		Map<String, Object> maps = new HashMap<String, Object>();
		
		if(wordColumn != null){
			maps.put("status", "success");
			maps.put("info", "成功");
		}else{
			maps.put("status", "error");
			maps.put("info", "失败");
		}
		maps.put("wordColumn", wordColumn);
		try {
			responseJson(response, maps);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取消订阅
	 * @param response
	 * @param plateid
	 */
	@RequestMapping(value="/plateDelHot")
	@ResponseBody
	public void plateDelHot(HttpSession session,HttpServletResponse response,int columnId){
		
		int userId = 0;
		if (session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		Map<String, Object> maps = new HashMap<String, Object>();
		List<NewsMyPlate> list = newsColumnManageService.getPlatList(userId, columnId);
		boolean flag = false;
		if (list != null && list.size() > 0){
			NewsMyPlate plate = list.get(0);
			flag = newsColumnManageService.plateDel(plate.getPlateId());
			maps.put("plateId", plate.getPlateId());
		}

		if(flag){
			maps.put("status", "success");
			maps.put("info", "删除成功");
		}else{
			maps.put("status", "error");
			maps.put("info", "失败");
		}
		
		try {
			LOGGER.info("urlName=news/column/plateDelHot,urlMsg=新闻板块取消订阅,userId="+userId+",status="+maps.get("status")+",info="+maps.get("info"));
			responseJson(response, maps);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 拖动更新顺序
	 * @param response
	 * @param firstId
	 * @param location
	 * @param loactionId
	 */
	@RequestMapping(value="/updateOrder")
	@ResponseBody
	public void updateOrder(HttpSession session,HttpServletResponse response,int firstId,String location,int loactionId){
		
		boolean flag = newsColumnManageService.updateOrder(firstId,location,loactionId);
		Map<String, Object> maps = new HashMap<String, Object>();
		if(flag){
			maps.put("status", "success");
			maps.put("info", "拖动成功");
		}else{
			maps.put("status", "error");
			maps.put("info", "拖动失败");
		}
		try {	
			int userId = 0;
			if(session.getAttribute("userId")!=null){
				userId = (Integer) session.getAttribute("userId");
			}
			LOGGER.info("urlName=news/column/updateOrder,urlMsg=新闻板块栏目拖动顺序,firstId="+firstId+",location="+location+",loactionId="+loactionId+",userId="+userId);
			responseJson(response, maps);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 搜索新闻源
	 * <p>Title: searchNewsSource</p> 
	 * <p>Description: TODO</p>
	 * @param req
	 * @param redq
	 * @param newsName
	 * @param moduleName
	 * @throws IOException
	 */
	@RequestMapping(value = "/searchNewsSource")
	@ResponseBody
	public void searchNewsSource(HttpServletRequest req,HttpServletResponse redq, String newsName,String moduleName) throws IOException{
		
		NewsSource newsBean = new NewsSource();
		if(newsName != null && !"".equals(newsName)){
			newsBean.setSourcename(newsName);
		}
		if(moduleName != null && !"".equals(moduleName)){
			newsBean.setModulename(moduleName);
		}
		String newsSourcelist = newsColumnManageService.searchNewsSource(newsBean);
		responseJson(redq,newsSourcelist);	
	}
	
	/**
	 * 预览自己栏目下新闻
	 * @param plateId
	 * @param contentType
	 */
	@RequestMapping(value = "/previewNews")
	public void previewNews(HttpServletResponse response, HttpSession session,
			NewsMyPlate plate,Integer pageNo, Integer pageSize, Integer startPage) {
		
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		
		Map<String, Object> map = null;
		if (pageSize == null || pageSize < 1) {
			pageSize = 5;
		}	
		//获取新闻
		map = newsColumnManageService.plateForNews(plate, pageNo, pageSize);

		if (map != null) {
			map.put("startPage", (startPage == null ? 1 : startPage));
			map.put("endPage", Math.min((startPage == null ? 1 : startPage)
					+ movepage, (Long) map.get("pageCount")));
			map.put("movepage", movepage);
			map.put("source", 1);
			try {
				responseJson(response, map);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
	
	/**
	 * 预览推荐栏目下新闻
	 * @param columnId
	 * @param contentType
	 */
	@RequestMapping(value = "/previewNewsForColumn")
	public void previewNewsForColumn(HttpServletResponse response, HttpSession session,
			NewsColumn column,Integer pageNo, Integer pageSize, Integer startPage) {
	
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		
		Map<String, Object> map = null;
		if (pageSize == null || pageSize < 1) {
			pageSize = 5;
		}	
		//获取新闻
		map = newsColumnManageService.columnForNews(column.getId(), pageNo, pageSize);
		
		if (map != null) {		
			map.put("startPage", (startPage == null ? 1 : startPage));
			map.put("endPage", Math.min((startPage == null ? 1 : startPage)
					+ movepage, (Long) map.get("pageCount")));
			map.put("movepage", movepage);
			map.put("source", 2);
			try {
				responseJson(response, map);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
	
	
	/**
	 * 按照名称搜索新闻标签
	 * 默认显示最热搜的5个
	 * @param response
	 */
	@RequestMapping(value="/searchNewsLabels")
	@ResponseBody
	public void searchNewsLabels(HttpServletResponse response,String name,int pageId,int pageSize){
		
		Map<String, Object> maps = new HashMap<String, Object>();
		List<NewsLabel> list = newsColumnManageService.searchNewsLabels(name, pageId, pageSize);
		maps.put("status", "success");
		maps.put("list", list);
		maps.put("count", 5);
		maps.put("pageId", pageId);
		try {
			responseJson(response, maps);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
