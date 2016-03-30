package com.web.global.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.service.CommonController;
import com.database.bean.Area;
import com.database.bean.Industry;
import com.database.bean.Tags;
import com.database.bean.WordColumn;
import com.database.bean.WordColumnGroup;
import com.database.bean.WordLabel;
import com.database.bean.WordPlate;
import com.database.bean.WordUsers;
import com.database.dao.WordJournalTypeMapper;
import com.util.ConstantUtils;
import com.web.global.service.GlobalService;
import com.web.view.service.HomeService;

@Controller
@RequestMapping(value="/global/")
public class GlobalController extends CommonController{
	@Autowired
	private GlobalService globalService;
	@Autowired
	private HomeService homeService;
	
	@Autowired
	WordJournalTypeMapper wordJournalTypeMapper;
	
	private int movepage = 5;
	private static final Logger LOGGER = Logger.getLogger(GlobalController.class);
	
	/*
	 * 获取所有的区域
	 * param SearchBean 
	 */
	@RequestMapping(value="/getAllArea")
	@ResponseBody
	public void getAllArea(HttpServletResponse response,Integer mid){
		try {
			//Map<String, Object> maps = new HashMap<String, Object>();
			List<Area> list = globalService.getAllArea(mid);
			LOGGER.info("urlName=global/getAllArea,urlMsg=获得区域,mid="+mid);
			responseJson(response, list);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * 获取所有的行业
	 * 无参数
	 */
	@RequestMapping(value="/getAllIndustry")
	@ResponseBody
	public void getAllIndustry(HttpServletResponse response){
		try {
			//Map<String, Object> maps = new HashMap<String, Object>();
			List<Industry> list = globalService.getAllIndustry();
			LOGGER.info("urlName=global/getAllIndustry,urlMsg=获得行业");
			responseJson(response, list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * 获取所有的行业
	 * 无参数
	 */
	@RequestMapping(value="/getIndustryByPid")
	@ResponseBody
	public void getIndustryByPid(HttpServletResponse response,int pid){
		try {
			//Map<String, Object> maps = new HashMap<String, Object>();
			List<Industry> list = globalService.getIndustryByPid(pid);
			responseJson(response, list);
			LOGGER.info("urlName=global/getIndustryByPid,urlMsg=pid获得行业,pid="+pid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 添加栏目 -不用
	 * @param response
	 * @param plate
	 */
	@RequestMapping(value="/plateGet")
	@ResponseBody
	public void plateGet(HttpServletResponse response,int plateId){
		WordPlate newPlate =  globalService.plateGet(plateId);
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
	 * 获取栏目数量
	 * @param response
	 * @param pid
	 */
	@RequestMapping(value="/getPlateCount")
	@ResponseBody
	public void getPlateCount(HttpServletResponse response,Integer pid,HttpSession session){
		Map<String, Object> maps = new HashMap<String, Object>();
		Integer useId=0;//获取默认的栏目
		if(session.getAttribute("userId")!=null){
			useId = (Integer) session.getAttribute("userId");
		}
		if(useId==0){
			maps.put("status", "error");
			maps.put("info", "获取用户信息失败！");
		}else{
			int count = globalService.getCountByPid(useId,pid);
			if(count>=15){
				maps.put("status", "error");
				maps.put("info", "自定义栏目最多能添加15个！");
			}else{
				maps.put("status", "success");
			}
		}
		try {
			LOGGER.info("urlName=global/getPlateCount,urlMsg=获取栏目数量,useId="+useId+",status="+maps.get("status")+",info="+maps.get("info"));
			responseJson(response, maps);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	public void plateAdd(HttpServletResponse response,WordPlate plate,HttpSession session){
		Map<String, Object> maps = new HashMap<String, Object>();
		Integer useId=0;//获取默认的栏目
		if(session.getAttribute("userId")!=null){
			useId = (Integer) session.getAttribute("userId");
		}
		if(useId==0){
			maps.put("status", "error");
			maps.put("info", "自定义栏目最多能添加15个！");
		}else{
			plate.setUserId(useId);
			int count = globalService.getCountByPid(useId,plate.getPid());
			
			WordPlate newPlate = null;
			if(count<15){
				 newPlate =  globalService.plateAdd(plate);
					maps.put("status", "success");
					maps.put("info", "成功");
			}else{
				maps.put("status", "error");
				maps.put("info", "自定义栏目最多能添加15个！");
			}
			maps.put("wordPlate", newPlate);
		}
		try {
			LOGGER.info("urlName=global/plateAdd,urlMsg=添加栏目,useId="+useId+",status="+maps.get("status")+",info="+maps.get("info"));
			responseJson(response, maps);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	public void plateAddHot(HttpServletResponse response,WordPlate plate,HttpSession session){
		
		Map<String, Object> maps = new HashMap<String, Object>();
		Integer useId = 0;//获取默认的栏目
		if (session.getAttribute("userId") != null){
			useId = (Integer) session.getAttribute("userId");
		}
		if (useId == 0){
			maps.put("status", "error");
			maps.put("info", "自定义栏目最多能添加15个！");
		}else{
			plate.setUserId(useId);
			int count = globalService.getCountByPid(useId,plate.getPid());
			
			WordPlate newPlate = null;
			if (count < 15){
				newPlate =  globalService.plateAddHot(plate);
				maps.put("status", "success");
				maps.put("info", "成功");
			}else{
				maps.put("status", "error");
				maps.put("info", "自定义栏目最多能添加15个！");
			}
			maps.put("wordPlate", newPlate);
		}
		try {
			LOGGER.info("urlName=global/plateAddHot,urlMsg=添加推荐的栏目,useId="+useId+",status="+maps.get("status")+",info="+maps.get("info"));
			responseJson(response, maps);
			
		} catch (IOException e) {
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
	public void plateEdit(HttpSession session,HttpServletResponse response,WordPlate plate){
		boolean flag =  globalService.plateEdit(plate);
		Map<String, Object> maps = new HashMap<String, Object>();
		int userId=0;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		if(flag){
			maps.put("status", "success");
			maps.put("info", "成功");
		}else{
			maps.put("status", "error");
			maps.put("info", "失败");
		}
		maps.put("wordPlate", plate);
		try {
			LOGGER.info("urlName=global/plateEdit,urlMsg=修改栏目,userId="+userId+",status="+maps.get("status")+",info="+maps.get("info"));
			responseJson(response, maps);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
		boolean flag =  globalService.plateDel(plateId);
		Map<String, Object> maps = new HashMap<String, Object>();
		int userId=0;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		if(flag){
			maps.put("status", "success");
			maps.put("info", "删除成功");
		}else{
			maps.put("status", "error");
			maps.put("info", "失败");
		}
		maps.put("plateId", plateId);
		try {
			LOGGER.info("urlName=global/plateDel,urlMsg=删除栏目,userId="+userId+",status="+maps.get("status")+",info="+maps.get("info"));
			responseJson(response, maps);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
		int userId=0;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		Map<String, Object> maps = new HashMap<String, Object>();
		List<WordPlate> list = globalService.getPlatList(userId, columnId);
		boolean flag = false;
		if (list != null && list.size() > 0){
			WordPlate plate = list.get(0);
			flag =  globalService.plateDel(plate.getPlateId());
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
			LOGGER.info("urlName=global/plateDel,urlMsg=删除栏目,userId="+userId+",status="+maps.get("status")+",info="+maps.get("info"));
			responseJson(response, maps);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得个人的栏目,返回树形结构
	 * @param response
	 */
	@RequestMapping(value="/getUserWordPlate")
	@ResponseBody
	public void getUserWordPlate(HttpServletResponse response,HttpSession session){
		Integer useId=0;//获取默认的栏目
		if(session.getAttribute("userId")!=null){
			useId = (Integer) session.getAttribute("userId");
		}
		//返回树形结构
		List<WordPlate> plateList = globalService.getUserWordPlate(useId);
		Map<String, Object> maps = new HashMap<String, Object>();
		if(useId==0){
			maps.put("status", "error");
			maps.put("info", "用户不存在");
		}else{
			maps.put("status", "success");
			maps.put("info", "删除成功");
		}

		maps.put("list", plateList);
		try {
			LOGGER.info("urlName=global/getUserWordPlate,urlMsg=获取栏目,useId="+useId);
			responseJson(response, maps);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
		boolean flag = globalService.updateOrder(firstId,location,loactionId);
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("status", "success");
		maps.put("info", "拖动成功");
		try {
			
			int userId=0;
			if(session.getAttribute("userId")!=null){
				userId = (Integer) session.getAttribute("userId");
			}
			LOGGER.info("urlName=global/updateOrder,urlMsg=拖动顺序,firstId="+firstId+",location="+location+",loactionId="+loactionId+",userId="+userId);
			responseJson(response, maps);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 栏目编辑页面
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/myPlateEdit")
	public String myPlateEdit(HttpServletResponse response, HttpSession session,Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		//取出热点栏目
		List<WordPlate> allList = globalService.getWordPlate(user.getUserId());
		//List<WordColumnGroup> hotGroups = homeService.getWordColumnGroup(ConstantUtils.WordColumnType.HOT);
		List<WordColumnGroup> hotGroups = homeService.getRecWordColumn(ConstantUtils.WordColumnType.HOT, allList);
		model.addAttribute("hotGroups", hotGroups);
		return "/global/myPlateEdit";
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
		List<WordPlate> list = new ArrayList<WordPlate>();
		if(user!=null){
			list = globalService.getWordPlate((user.getUserId()));
		}
		//取出热点栏目
		List<WordColumnGroup> hotGroups = homeService.getRecWordColumn(ConstantUtils.WordColumnType.HOT, list);
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("hotGroups", hotGroups);
		try {
			responseJson(response, maps);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取期刊类型
	 * @param response
	 */
	@RequestMapping(value="/getJournalType")
	@ResponseBody
	public void getJournalType(HttpServletResponse response){
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("status", "success");
		maps.put("typeList", wordJournalTypeMapper.selectByExample(null));
		try {
			responseJson(response, maps);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 按照名称搜索期刊
	 * @param response
	 */
	@RequestMapping(value="/searchJournal")
	@ResponseBody
	public void searchJournal(HttpServletResponse response,String name,int pageId,int pageSize){
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("status", "success");
		maps.put("list", globalService.searchJournal(name,pageId,pageSize));
		maps.put("count", globalService.countJournal(name));
		maps.put("pageId", pageId);
		try {
			responseJson(response, maps);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 获取自己栏目下期刊和非期刊信息
	 * @param plateId
	 * @param contentType
	 */
	@RequestMapping(value = "/getArticleAndJournal")
	public void getArticleAndJournal(HttpServletResponse response, HttpSession session,
			Integer plateId,Integer pageNo, Integer pageSize, Integer startPage, Integer contentType) {
	
		Integer userId = null;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}

		Map<String, Object> map = null;

		if (contentType != null && contentType == 2){
			if (pageSize == null || pageSize < 1) {
				pageSize = 3;
			}	
			//获取非期刊
			map = homeService.plate(plateId, pageNo, pageSize);
		}else {	
			if (pageSize == null || pageSize < 1) {
				pageSize = 18;
			}
			//获取期刊
			map = homeService.journal(plateId, pageNo, pageSize,1,userId);
		}

		if (map != null) {
			WordPlate plate = globalService.plateGet(plateId);
			map.put("topList", plate.getTopList());
			map.put("delList", plate.getDelList());
			map.put("startPage", (startPage == null ? 1 : startPage));
			map.put("endPage", Math.min((startPage == null ? 1 : startPage)
					+ movepage, (Long) map.get("pageCount")));
			map.put("movepage", movepage);
			map.put("contentType", contentType == null ? 1 : contentType);
			map.put("source", 1);
			try {
				responseJson(response, map);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
	
	/**
	 * 获取推荐栏目下期刊和非期刊信息
	 * @param columnId
	 * @param contentType
	 */
	@RequestMapping(value = "/getArtAndJourForColumn")
	public void getArtAndJourForColumn(HttpServletResponse response, HttpSession session,
			Integer columnId,Integer pageNo, Integer pageSize, Integer startPage, Integer contentType) {
	
		Integer userId = null;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		Map<String, Object> map = null;

		if (contentType != null && contentType == 2){
			if (pageSize == null || pageSize < 1) {
				pageSize = 3;
			}	
			//获取非期刊
			map = homeService.column(columnId, pageNo, pageSize);
		}else {	
			if (pageSize == null || pageSize < 1) {
				pageSize = 18;
			}	
			//获取期刊
			map = homeService.columnJour(columnId, pageNo, pageSize, 1,userId);
		}

		if (map != null) {
			WordColumn column = globalService.getWordColumn(columnId);
			map.put("topList", column.getTopList());
			map.put("delList", column.getDelList());
			map.put("startPage", (startPage == null ? 1 : startPage));
			map.put("endPage", Math.min((startPage == null ? 1 : startPage)
					+ movepage, (Long) map.get("pageCount")));
			map.put("movepage", movepage);
			map.put("contentType", contentType == null ? 1 : contentType);
			map.put("source", 2);
			try {
				responseJson(response, map);
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		
		WordColumn wordColumn =  globalService.getWordColumn(columnId);
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
	 * 刷新自己栏目下期刊和非期刊信息
	 * @param plateId
	 * @param contentType
	 */
	@RequestMapping(value = "/refreshArtAndJour")
	public void refreshArtAndJour(HttpServletResponse response, HttpSession session,
			WordPlate plate,Integer pageNo, Integer pageSize, Integer startPage, Integer contentType) {
		
		Integer userId = null;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		
		Map<String, Object> map = null;
		if (contentType != null && contentType == 2){
			if (pageSize == null || pageSize < 1) {
				pageSize = 3;
			}	
			//获取非期刊
			map = homeService.plateForRefresh(plate, pageNo, pageSize);
		}else {	
			if (pageSize == null || pageSize < 1) {
				pageSize = 18;
			}
			//获取期刊
			map = homeService.journalForRefresh(plate, pageNo, pageSize,userId);
		}

		if (map != null) {
			map.put("startPage", (startPage == null ? 1 : startPage));
			map.put("endPage", Math.min((startPage == null ? 1 : startPage)
					+ movepage, (Long) map.get("pageCount")));
			map.put("movepage", movepage);
			map.put("contentType", contentType == null ? 1 : contentType);
			map.put("source", 1);
			try {
				responseJson(response, map);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
	
	/**
	 * 刷新推荐栏目下期刊和非期刊信息
	 * @param columnId
	 * @param contentType
	 */
	@RequestMapping(value = "/refreshArtAndJourForColumn")
	public void refreshArtAndJourForColumn(HttpServletResponse response, HttpSession session,
			WordColumn column,Integer pageNo, Integer pageSize, Integer startPage, Integer contentType) {
	
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		
		Map<String, Object> map = null;
		if (contentType != null && contentType == 2){
			if (pageSize == null || pageSize < 1) {
				pageSize = 3;
			}	
			//获取非期刊
			map = homeService.columnForRefresh(column.getId(), pageNo, pageSize);
		}else {	
			if (pageSize == null || pageSize < 1) {
				pageSize = 18;
			}	
			//获取期刊
			map = homeService.columnJourForRefresh(column.getId(), pageNo, pageSize,column.getTopList(),column.getDelList());
		}

		if (map != null) {		
			map.put("startPage", (startPage == null ? 1 : startPage));
			map.put("endPage", Math.min((startPage == null ? 1 : startPage)
					+ movepage, (Long) map.get("pageCount")));
			map.put("movepage", movepage);
			map.put("contentType", contentType == null ? 1 : contentType);
			map.put("source", 2);
			try {
				responseJson(response, map);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
	
	
	/**
	 * 按照名称搜索标签
	 * 默认显示最热搜的5个
	 * @param response
	 */
	@RequestMapping(value="/searchTags")
	@ResponseBody
	public void searchTags(HttpServletResponse response,String name,int pageId,int pageSize){
		Map<String, Object> maps = new HashMap<String, Object>();
		List<Tags> list = globalService.searchTags(name, pageId, pageSize);
		//int count = globalService.countTags(name);
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
	
	/**
	 * 按照名称搜索关键词
	 * 默认显示最热搜的5个
	 * @param response
	 */
	@RequestMapping(value="/searchLabel")
	@ResponseBody
	public void searchLabel(HttpServletResponse response,String name,int pageId,int pageSize){
		Map<String, Object> maps = new HashMap<String, Object>();
		List<WordLabel> list = globalService.searchLabel(name, pageId, pageSize);
		//int count = globalService.countLabel(name);
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
