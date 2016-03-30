package com.web.document.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.common.service.CommonController;
import com.database.bean.Article;
import com.database.bean.ContentWithBLOBs;
import com.export.Function;
import com.lucene.UnbankList;
import com.lucene.entity.doc.Crawl;
import com.util.DateTool;
import com.web.document.bean.AdvancedSearchConditionBean;
import com.web.document.bean.GrabContent;
import com.web.document.bean.SearchBean;
import com.web.document.service.DocumentService;


@Controller
@RequestMapping(value="/document/")
public class DocumentController extends CommonController {
	@Autowired
	private DocumentService documentService;
	
	@Value("${nginxPic}")
	private String nginxPic;
	
	private static final Logger LOGGER = Logger.getLogger(DocumentController.class);
	/*
	 * 搜索新闻
	 * param SearchBean 
	 * @param searchsource  搜索源 1全文 2标题
	 * @param sort          排序1时间 2权重
	 */
	@RequestMapping(value="/searchNews")
	@ResponseBody
	public void searchNew(HttpServletResponse response,HttpSession session,SearchBean searchBean,String searchType,String startTime,String endTime){
		//System.out.println("SearchType="+SearchType);
		if(searchType!=null&&"normal".equals(searchType)){//暂时没用
			//开始时间
			
			//当前时间加一天
			//searchBean.setEndTime(DateTool.addDate(new Date(), 10));
		}
		int userId=1;
		try {
			if(endTime.length()>10){
				//System.out.println("DateTool.strToDate(endTime)="+DateTool.strToDate(endTime));
				searchBean.setEndTime(DateTool.strToDate(endTime));
			}
			if(startTime.length()>10){
				//System.out.println("DateTool.strToDate(endTime)="+DateTool.strToDate(endTime));
				searchBean.setStartTime(DateTool.strToDate(startTime));
			}
			//System.out.println("searchBean.getEndTime()="+searchBean.getEndTime());
			//System.out.println("endTime="+endTime);
			Map<String, Object> maps = documentService.searchNews(userId,searchBean);
			List<String> crawlIdList = new ArrayList<String>();//搜索出来id
			List<String> esIdList = new ArrayList<String>();//风险库的esId
			UnbankList<Crawl> list = (UnbankList<Crawl>) maps.get("list");
			for(Crawl crawl : list.getList()){
				crawlIdList.add(Integer.toString(crawl.getCrawlId()));
				esIdList.add(crawl.getEsId());
			}
			LOGGER.info("urlName=searchNews,urlMag=搜索新闻,"+searchBean.toString()+",crawlIdList="+StringUtils.join(crawlIdList.toArray(),";")+",esIdList="+StringUtils.join(esIdList.toArray(),";")+",searchType="+searchType+",startTime="+startTime+",endTime="+endTime);
			responseJson(response, maps);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * 建立action
	 */
	@RequestMapping(value = "document0")
	public ModelAndView createDocument(Integer articleId) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/document/document");
		return mav;
	}
	
	@RequestMapping(value = "document1")
	public ModelAndView document1(HttpServletRequest request,Integer articleId) throws Exception {
		System.out.println("Referer="+request.getHeader("Referer"));
		System.out.println(request.getRequestURI());
		System.out.println(request.getRequestURL()); 
		System.out.println(request.getQueryString());
		String path = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+request.getServletPath()+"?"+request.getQueryString();
		System.out.println("path="+path);
		//url 获取不带 锚点
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/document/document");
		return mav;
	}
	@RequestMapping(value="home", method=RequestMethod.GET)
	public String home(HttpServletRequest request,ModelMap map){
		Article article = new Article();
		article.setArticleName("文章名字home");
		map.addAttribute("article",article);
		return "/document/document";
	}
	
	@RequestMapping(value="home1", method=RequestMethod.GET)
	public String home1(HttpServletRequest request,Model model){
		Article article = new Article();
		article.setArticleName("文章名字home1");
		model.addAttribute("article",article);
		return "/document/document";
	}
	/*
	 * 这个应该算是最简单的写法了。
	 */
	@RequestMapping(value="/document", method=RequestMethod.GET)
	public void document(HttpServletRequest request,Model model){
		Article article = new Article();
		article.setArticleName("文章名字document");
		model.addAttribute("article",article);
		//return "/document/document";
	}
	/*
	 * 返回json结构
	 * @ResponseBody
	 */
	@RequestMapping(value="/json", method=RequestMethod.GET)
	public void json(HttpServletResponse response,Model model) throws Exception{
		Article article = new Article();
		article.setArticleName("文章名字document");
		model.addAttribute("article",article);
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("article", article);
		responseJson(response, maps);
		//return "/document/document";
	}
	
	/**
	 * 根据新闻名查询新闻
	 * <p>Title: queryContentByName</p> 
	 * <p>Description: TODO</p>
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "queryContentByName")
	@ResponseBody
	public void queryContentByName(HttpServletRequest request, HttpSession session,HttpServletResponse response){
		int userId=1;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		try {
			String contentName = request.getParameter("contentName");
			String contentText = documentService.queryContentByName(contentName, userId);
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("contentText", contentText);
			LOGGER.info("urlName=queryContentByName,urlMag=搜索新闻,userId="+userId+",contentName="+contentName);
			responseJson(response,maps);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据新闻id查询新闻内容
	 * <p>Title: queryContentById</p> 
	 * <p>Description: TODO</p>
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "queryContentById")
	@ResponseBody
	public void queryContentById(HttpServletRequest request, HttpServletResponse response) {
		int newsId = Integer.valueOf(request.getParameter("newsId"));
		ContentWithBLOBs content = documentService.queryContentById(newsId);
		try {
			Map<String, Object> maps = new HashMap<String, Object>();
			if(content == null){
				maps.put("contentTitle", "");
				maps.put("contentText", "");
			}else{
				maps.put("contentTitle", content.getContentName());
				maps.put("contentText", content.getContent());
			}
			LOGGER.info("urlName=queryContentById,urlMag=获取新闻,newsId="+newsId);
			responseJson(response,maps);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据crawlId查询新闻详情
	 * <p>Title: queryContentByCrawlId</p> 
	 * <p>Description: TODO</p>
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "queryContentByCrawlId")
	@ResponseBody
	public void queryContentByCrawlId(HttpServletRequest request, HttpServletResponse response){
		int crawlId = Integer.valueOf(request.getParameter("crawlId"));
		String categoryId = request.getParameter("categoryId");
		String esId = request.getParameter("esId");
		GrabContent grabContent = documentService.queryContentByCrawlId(crawlId,categoryId,nginxPic,esId);
		try {
			Map<String, Object> maps = new HashMap<String, Object>();
			if(grabContent != null){
				grabContent.setText(Function.getHtml(grabContent.getText(),nginxPic));
				maps.put("contentTitle", grabContent.getTitle());
				maps.put("contentText", grabContent.getText());
			}
			else{
				maps.put("contentTitle", "");
				maps.put("contentText", "");
			}
			LOGGER.info("urlName=queryContentById,urlMag=新闻详情,crawlId="+crawlId+",esId="+esId);
			responseJson(response,maps);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 保存为我的内容
	 * <p>Title: addContent</p> 
	 * <p>Description: TODO</p>
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "addContent")
	@ResponseBody
	public void addContent(HttpServletRequest request,HttpSession session, HttpServletResponse response) {
		try {
			int userId=1;
			if(session.getAttribute("userId")!=null){
				userId = (Integer) session.getAttribute("userId");
			}
			String contentName = request.getParameter("contentName");
			String contentText = documentService.queryContentByName(contentName, userId);
			Map<String, Object> maps = new HashMap<String, Object>();
			//判断是否重名
			if(contentText != null && !"".equals(contentText)){
				maps.put("state", "error");
				maps.put("info", "已有重名的新闻，请更改后再保存");
				LOGGER.info("urlName=addContent,urlMag=保存到我的内容,userId="+userId+",state="+maps.get("state")+",info="+maps.get("info"));
			} else{
				int newsId = Integer.valueOf(request.getParameter("newsId"));
				String categoryId = request.getParameter("categoryId");
				String esId = request.getParameter("esId");
				
				documentService.addContent(newsId, userId, contentName,categoryId,nginxPic,esId);
				maps.put("state", "success");
				maps.put("info", "保存成功");
				LOGGER.info("urlName=addContent,urlMag=保存到我的内容,newsId="+newsId+",esId="+esId+",userId="+userId+",state="+maps.get("state")+",info="+maps.get("info"));
			}
			responseJson(response,maps);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询我的内容
	 * <p>Title: searchMyNew</p> 
	 * <p>Description: TODO</p>
	 * @param response
	 * @param session
	 * @param searchBean
	 * @param type
	 */
	@RequestMapping(value="/searchMyNews")
	@ResponseBody
	public void searchMyNew(HttpServletRequest request, HttpSession session,HttpServletResponse response){
		try {
			int userId=1;
			if(session.getAttribute("userId")!=null){
				userId = (Integer) session.getAttribute("userId");
			}
			String contentName = request.getParameter("contentName");
			int pageId = Integer.valueOf(request.getParameter("pageId"));
			int pageSize = Integer.valueOf(request.getParameter("pageSize"));
			Map<String, Object> map = documentService.searchMyNews(userId,contentName,pageId,pageSize);
			responseJson(response, map);
			LOGGER.info("urlName=searchMyNews,urlMag=查询我的新闻,contentName="+contentName+",userId="+userId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 删除我的内容
	 * @param id
	 * @param session
	 * @param response
	 */
	@RequestMapping(value="/newsDelById")
	@ResponseBody
	public void newsDelById(int id, HttpSession session,HttpServletResponse response){
		try {
			int userId=1;
			if(session.getAttribute("userId")!=null){
				userId = (Integer) session.getAttribute("userId");
			}
			Map<String, Object> map = documentService.newsDelById(userId,id);
			LOGGER.info("urlName=newsDelById,urlMag=删除我的内容,id="+id+",userId="+userId);
			responseJson(response, map);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存高级检索条件
	 * <p>Title: insertAdvCondition</p> 
	 * <p>Description: TODO</p>
	 * @param response
	 * @param ascondition json串
	 */
	@RequestMapping(value="insertAdvCondition")
	@ResponseBody
	public void insertAdvCondition(HttpServletResponse response,HttpSession session,String ascondition){
		try {
			Integer userId = 1;
			if(session.getAttribute("userId")!=null){
				userId = (Integer) session.getAttribute("userId");
			}
			int asconditionId = documentService.insertAdvCondition(ascondition, userId);
			Map<String,Object> map = new HashMap<String,Object>();
			if(asconditionId > 0){
				map.put("status", "success");
			}
			else{
				map.put("status", "error");
			}
			responseJson(response,map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 搜索高级检索条件
	 * <p>Title: searchAdvCondition</p> 
	 * <p>Description: TODO</p>
	 * @param response
	 * @param start
	 * @param limit
	 * @param type
	 */
	@RequestMapping(value="searchAdvCondition")
	@ResponseBody
	public void searchAdvCondition(HttpServletResponse response,HttpSession session,int start,int limit,String type){
		try {
			Integer userId = 1;
			if(session.getAttribute("userId")!=null){
				userId = (Integer) session.getAttribute("userId");
			}
			List<AdvancedSearchConditionBean> list = documentService.searchAdvCondition(userId, start, limit,type);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("advConditionList", list);
			LOGGER.info("urlName=searchAdvCondition,urlMag=高级搜索,start="+start+",userId="+userId);
			responseJson(response,map);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除检索条件
	 * <p>Title: deleteAdvCondition</p> 
	 * <p>Description: TODO</p>
	 * @param response
	 * @param asconditionIds
	 */
	@RequestMapping(value="deleteAdvCondition")
	@ResponseBody
	public void deleteAdvCondition(HttpSession session,HttpServletResponse response,String asconditionIds){
		try {
			documentService.deleteAdvCondition(asconditionIds);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status", "success");
			int userId=0;
			if(session.getAttribute("userId")!=null){
				userId = (Integer) session.getAttribute("userId");
			}
			LOGGER.info("urlName=deleteAdvCondition,urlMag=删除搜索条件,userId="+userId);
			responseJson(response,map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改检索条件
	 * <p>Title: updateAdvCondition</p> 
	 * <p>Description: TODO</p>
	 * @param response
	 * @param ascondition
	 */
	@RequestMapping(value="updateAdvCondition")
	@ResponseBody
	public void updateAdvCondition(HttpServletResponse response,String ascondition){
		try {
			documentService.updateAdvCondition(ascondition);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status", "success");
			responseJson(response,map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
