package com.web.Article.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.service.CommonController;
import com.database.bean.Article;
import com.database.bean.ContentPlugin;
import com.lucene.UnbankList;
import com.lucene.entity.doc.Crawl;
import com.util.DateTool;
import com.web.Article.service.ArticleService;
import com.web.word.service.WordService;

@Controller
@RequestMapping(value="/article/")
public class ArticleController extends CommonController{
	
	@Autowired
	ArticleService articleService;
	
	@Autowired
	private WordService wordService;
	
	private static final Logger LOGGER = Logger.getLogger(ArticleController.class);
	/*
	 * 搜索新闻,测试
	 * param SearchBean
	 *  
	 */
	@RequestMapping(value="/searchArticle")
	@ResponseBody
	public void searchNew(HttpServletResponse response,String name,int pageId,int pageSize){
		//System.out.println("SearchType="+SearchType);
		try {
			Map<String, Object> maps = new HashMap<String,Object>();
			maps.put("pageId", pageId);
			responseJson(response, maps);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * 设置内容插件
	 */
	@RequestMapping(value="/setContentPlugin")
	@ResponseBody
	public void setContentPlugin(HttpServletResponse response,HttpSession session,HttpServletRequest request,ContentPlugin contentPlugin){
		int userId=0;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		Map<String, Object> maps = new HashMap<String, Object>();
		ContentPlugin  bean = null;
		int newId = 0;
		String path=request.getSession().getServletContext().getRealPath("");
		Article articel = wordService.selectById(contentPlugin.getContentpluginArticleid());
		List<String> crawlIdList = new ArrayList<String>();//搜索出来id
		List<String> esIdList = new ArrayList<String>();//风险库的esId
		if(articel!=null){
			Date newDate=DateTool.getDate(new Date());
			contentPlugin.setContentpluginInsertdate(newDate);
			contentPlugin.setContentpluginUpdatetime(newDate);
			contentPlugin.setContentpluginInserttime(new Date());
			contentPlugin.setContentpluginUserid(userId);
			
			if(contentPlugin.getContentpluginId()!=null&&contentPlugin.getContentpluginId()!=0){//存在内容插件的Id
				bean = articleService.getContentPlugin(contentPlugin.getContentpluginId());
			}
			if(bean!=null){//存在更新
				newId = bean.getContentpluginId();
				articleService.updateContentPlugin(contentPlugin);
				maps.put("info", "更新");
			}else{//不存在 ,插入
				newId = articleService.setContentPlugin(contentPlugin);
				maps.put("info", "插入");
			}
			maps.put("newId", newId);
			maps.put("status", "success");
			UnbankList<Crawl> list=null;
			list=articleService.search(userId,contentPlugin,path,articel.getArticleId());
			maps.put("listSize",list.getList().size());
			maps.put("nodes",list);
			for(Crawl crawl : list.getList()){
				crawlIdList.add(Integer.toString(crawl.getCrawlId()));
				esIdList.add(crawl.getEsId());
			}
		}else{
			maps.put("status", "error");
			maps.put("info", "操作无效,该篇文档不存在");
		}

		try {
			//LOGGER.info(objectToString(contentPlugin));
			LOGGER.info("urlName=setContentPlugin,urlMsg=设置内容插件,userId="+userId+",articleId="+contentPlugin.getContentpluginArticleid()+","+contentPlugin.toString()+",crawlIdList="+StringUtils.join(crawlIdList.toArray(), ";")+",esIdList="+StringUtils.join(esIdList.toArray(),";"));
			responseJson(response, maps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 *  获取内容插件
	 */
	@RequestMapping(value="/getContentPlugin")
	@ResponseBody
	public void getContentPlugin(HttpServletResponse response,int contentpluginId) throws IOException{
		Map<String, Object> maps = new HashMap<String, Object>();
		ContentPlugin contentPlugin = articleService.getContentPlugin(contentpluginId);
		maps.put("status", "success");
		if(contentPlugin==null){
			maps.put("status", "error");
			maps.put("info", "内容插件为空");
		}else{
			maps.put("info", "获取内容插件");
		}
		maps.put("contentPlugin", contentPlugin);
		try{
			LOGGER.info("urlName=getContentPlugin,urlMsg=获取内容插件,contentpluginId="+contentpluginId);
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		responseJson(response, maps);
	}
	/*
	 *  删除内容插件
	 */
	@RequestMapping(value="/delContentPlugin")
	@ResponseBody
	public void delContentPlugin(HttpServletResponse response,int contentpluginId) throws IOException{
		Map<String, Object> maps = new HashMap<String, Object>();
		articleService.delConetntPlugin(contentpluginId);
		maps.put("status", "success");
		maps.put("info", "删除内容");
		try{
			LOGGER.info("urlName=delContentPlugin,urlMsg=删除内容插件,contentpluginId="+contentpluginId);
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		responseJson(response, maps);
	}

}
