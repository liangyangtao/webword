package com.web.word.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.common.service.CommonController;
import com.database.bean.Article;
import com.database.bean.ArticleProperty;
import com.database.bean.ContentWhole;
import com.database.bean.Homepage;
import com.database.bean.KnowPlate;
import com.database.bean.WordJournal;
import com.database.bean.WordUsers;
import com.util.DateTool;
import com.web.homePage.service.HomePageService;
import com.web.homePage.util.HomePageUtil;
import com.web.word.bean.NodeBean;
import com.web.word.service.WordService;


@Controller
@RequestMapping(value="")
public class WordController extends CommonController {
		
	private static final Logger LOGGER = Logger.getLogger(WordController.class);
	
	@Autowired
	private WordService wordService;
	
	@Autowired
	private HomePageService homePageService ;
	
	//@Value("${jdbc.username}")
	@Value("${version}")
	private String version;
	
	/*
	 * 新建文档
	 */
	@RequestMapping( value = "/wordNew")
	public String wordNew(HttpSession session){
		Integer useId=0;
		if(session.getAttribute("userId")!=null){
			useId = (Integer) session.getAttribute("userId");
			Integer articleId = wordService.wordNew(useId); 
			LOGGER.info("urlName=wordNew,urlMsg=新建文档,useId="+useId+",articleId="+articleId);
			return "redirect:/word.do?articleId="+articleId;
		}
		return "redirect:/view/home.do";
		//User user = (User) session.getAttribute("user");

	}
	/*
	 * 获取文档内容
	 * @param article 文章的Id
	 */
	@RequestMapping(value = "/word")
	public String createDocument(HttpServletRequest request,HttpSession session,Integer articleId,Model model) throws Exception {
		//System.out.println(request.getHeader( "USER-AGENT" ).toLowerCase());
		//System.out.println("searchByNewsId="+version);
		int userId = 0;
		WordUsers user = (WordUsers) session.getAttribute("user");
		if(user!=null){
			userId = user.getUserId();
		}
		String path=request.getSession().getServletContext().getRealPath("");
		
		Article article = wordService.selectById(articleId);
		if(article==null){
			return "redirect:/view/home.do";
		}
		if("upload".equals(article.getArticleSave())){//是上传的
			return "redirect:/view/preview.do?articleId="+article.getArticleId();
		}
		if(wordService.getPassType(article)){//不是审核通过,和审核中
			if("template".equals(article.getArticleType())){
				
			}else if("write".equals(article.getArticleSave())){//不是模板自动更新插件
				LOGGER.info("urlName=doContentPlugin,urlMsg=自动执行内容插件,articleId="+articleId+",userId="+userId);
				wordService.doContentPlugin(userId, articleId, path);//自动执行内容插件
			}
		}
		model.addAttribute("article",article);
		ContentWhole content = wordService.getArticleContentById(articleId);//获取文档内容
		List<com.web.word.bean.NodeBean> list =wordService.getNodeList(articleId);//获取文档节点
		if(list.size()==0&&content.getNodeContent()==null){
			com.web.word.bean.NodeBean node = new NodeBean();
			node.setId(articleId);
			node.setIdStr(Integer.toString(articleId));
			node.setOrder(1);
			node.setNodeNameStatic("标题1");
			list.add(node);
		}
		model.addAttribute("list",objectToJson(list));
		if(content.getNodeContent()==null){
			//content.setNodeContent("<h1 id=\""+articleId+"\">标题1</h1><p>输入内容</p>");
		}
		model.addAttribute("content",content);
		
		model.addAttribute("userId",userId);
		//mav.addObject("strTime",new Date().getTime());
		model.addAttribute("strTime",version);
		model.addAttribute("user",user);
		Homepage tempBean = homePageService.getHomePageSetting(articleId);
		String picpath="11";
		if(tempBean != null && tempBean.getSavepicpath() != null && !"".equals(tempBean.getSavepicpath())){
			int index = tempBean.getSavepicpath().indexOf("upload");
			picpath = tempBean.getSavepicpath().substring(index);
		}
		model.addAttribute("picpath",picpath);
		LOGGER.info("urlName=word,urlMsg=进入文档,userId="+userId+",articleId="+articleId);
		return "/word/word";
	}
	/*
	 * 获取文档内容
	 * @param article 文章的Id
	 */
	@RequestMapping(value = "/getNodeContent")
	public void getNodeContent(HttpServletResponse response,Integer articleId,String hId,String hName) throws Exception {
		
		NodeBean node = wordService.getNodeContent(articleId, hId,hName);
		
		Map<String, Object> maps = new HashMap<String, Object>();
		
		if(node != null){
			maps.put("status", "success");
			maps.put("info", "另存成功");
		}else{
			maps.put("status", "error");
			maps.put("info", "空");
		}
		maps.put("nodeContent", node.getContent());
		responseJson(response, maps);
		LOGGER.info("urlName=getNodeContent,urlMsg=获取文档内容,articleId="+articleId);
	}
	/*
	 * 获取文章内容
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView getArticleContent(Integer articleId) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/word/editor");
		ContentWhole content = wordService.getArticleContentById(articleId);
		mav.addObject("content",content);
		return mav;
	}
	/*
	 * 设置文档内容 
	 */
	
	@RequestMapping(value = "/setArticleContent")
	@ResponseBody
	public void setArticleContent(HttpSession session,HttpServletResponse response,Integer articleId,String nodeContent) throws Exception {
		Map<String, Object>  maps = wordService.setArticleContentById(articleId, nodeContent);
		Integer useId=0;
		if(session.getAttribute("userId")!=null){
			useId=(Integer) session.getAttribute("userId");
		}
		LOGGER.info("urlName=setArticleContent,urlMsg=保存文档内容,articleId="+articleId+",useId="+useId);
		responseJson(response, maps);
	}
	/*
	 * 文档搜索 
	 * @param searchType 搜索类型 normal,user,默认是正常
	 */
	/**
	 * 文档搜索
	 * @param session
	 * @param response
	 * @param name
	 * @param searchType  normal:全部文档，空，是用户文档
	 * @param articleType :空:全部文档，template：模板，document：文档
	 * @param passType :SAVED 审核中:SUBMITTED PASSED（审核通过）FAILED 空格隔开
	 * @param pageId
	 * @param pageSize
	 * @throws IOException
	 */
	@RequestMapping(value = "/word/searchArticle")
	@ResponseBody
	public void searchArticle(HttpSession session,HttpServletResponse response,String name,String searchType,String passType,String articleType,int pageId,int pageSize) throws IOException{
		int userId=0;
		if(session.getAttribute("userId")!=null){//我的文档搜索
			userId = (Integer) session.getAttribute("userId");
		}
		Map<String,Object> maps = new HashMap<String, Object>();
		if(searchType!=null&&searchType.equals("normal")){//正常搜索,搜索全部
			maps=wordService.searchArticleByResource(userId,articleType,passType,name, pageId, pageSize);
		}else{//个人文档
			maps=wordService.searchArticle(userId,articleType,passType,name, pageId, pageSize);
		}
		try{
			String urlMsg = "全部文档搜索";
			if ("document".equals(articleType)){
				urlMsg = "文档搜索";
			}else if ("template".equals(articleType)){
				urlMsg = "模板搜索";
			}
			LOGGER.info("urlName=word/searchArticle,urlMsg="+urlMsg+",userId="+userId+",articleType="+articleType+",passType="+passType+",name="+name);
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		responseJson(response, maps);
	}
	
	@RequestMapping(value = "/word/articleDelById")
	@ResponseBody
	public void articleDelById(int id,HttpSession session,HttpServletResponse response){
		int userId =0;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		Map<String,Object> maps = wordService.articleDelById(userId, id);
		try {
			LOGGER.info("urlName=word/articleDelById,urlMsg=删除文档,articleId="+id+"userId="+userId);
			responseJson(response,maps);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*
	 * 获取文章内容
	 */
	@RequestMapping(value = "word/getContent")
	@ResponseBody
	public void getContent(HttpServletResponse response,Integer articleId) throws Exception {
		ContentWhole content = wordService.getArticleContentById(articleId);
		Homepage tempBean = homePageService.getHomePageSetting(articleId);
		Map<String, Object> maps = new HashMap<String, Object>();
		if(content != null && !"".equals(content)) {
			maps.put("wholeId", content.getWholeId());
			maps.put("nodeId", content.getNodeId());
			maps.put("articleId", content.getArticleId());
			maps.put("nodeContent", content.getNodeContent());
		} else {
			maps.put("wholeId", "");
			maps.put("nodeId", "");
			maps.put("articleId", "");
			maps.put("nodeContent", "");
		}
		if(tempBean == null){
			maps.put("savepicpath", "");
		} else {
			String picpath = "";
			//System.out.println("index=----");
			int index = -1;
			if(tempBean.getSavepicpath()!=null){
				index = tempBean.getSavepicpath().indexOf("upload");
			}
			if(index>-1){
				picpath = tempBean.getSavepicpath().substring(index);
			}
			maps.put("savepicpath", picpath);
		}
		
		try{
			LOGGER.info("urlName=word/getContent,urlMsg=文档搜索,articleId="+articleId);
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		responseJson(response, maps);
	}
	/**
	 * 获取大纲节点
	 * 
	 * @param response
	 * @param outlineCode
	 * @throws Exception 
	 */
	@RequestMapping(value = "getNodeList")
	@ResponseBody
	public void getNodeList(HttpServletResponse response,Integer articleId) throws Exception {
		List<com.web.word.bean.NodeBean> list =wordService.getNodeList(articleId);
		
		try{
			LOGGER.info("urlName=getNodeList,urlMsg=获取大纲,articleId="+articleId);
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		responseJson(response, list);
	}
	
	
	/**
	 * 检查文章的标题是否重复，不重名，后续的操作直接进行
	 * <p>Title: checkArticleName</p> 
	 * <p>Description: TODO</p>
	 * @param response
	 * @param articleId
	 * @param articleName
	 * @param flag 1、指定名字保存 2、覆盖保存
	 * @param documentType 1、模板 2、文档
	 * @throws Exception
	 */
	@RequestMapping(value = "word/checkArticleName")
	@ResponseBody
	public void checkArticleName(HttpServletResponse response,HttpSession session, Integer articleId, String  articleName, 
			Integer flag, Integer documentType) throws Exception{
		
		Integer userId = 1;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		Article articel = wordService.selectById(articleId);
		Map<String, Object> maps = new HashMap<String, Object>();
		if(articel!=null){
			if(articleName==null||"".equals(articleName)){
				articleName="新建文档_"+System.currentTimeMillis()/100;
			}
			Article bean = wordService.checkArticleName(userId, articleId, articleName,flag,documentType);
			if(bean != null){
				maps.put("status", "success");
				maps.put("articleId", bean.getArticleId());
				maps.put("info", "另存成功");
			}else{
				maps.put("status", "error");
				maps.put("articleId", "");
				maps.put("info", "已经有重名文章");
			}
		}else{
			maps.put("status", "error");
			maps.put("articleId", "");
			maps.put("info", "操作无效,该篇文档不存在");
		}

		try{
			LOGGER.info("urlName=checkArticleName,urlMsg=文档另存为,articleId="+articleId);
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		responseJson(response, maps);
	}
	
	/**
	 * 获取全局属性
	 * <p>Title: getGlobalSetting</p> 
	 * <p>Description: TODO</p>
	 * @param response
	 * @param articleId
	 * @throws Exception
	 */
	@RequestMapping(value = "word/getGlobalByArticleId")
	@ResponseBody
	public void getGlobalSetting(HttpServletResponse response, Integer articleId) throws Exception{
		Map<String, Object> maps = new HashMap<String, Object>();
		ArticleProperty prop = wordService.getGlobalSetting(articleId);
		if(prop != null){
			maps.put("status", "success");
			maps.put("bean", prop);
		}else{
			maps.put("status", "error");
			maps.put("bean", null);
		}
		responseJson(response, maps);
	}
	
	/**
	 * 获取大纲属性
	 * <p>Title: getOutlineSetting</p> 
	 * <p>Description: TODO</p>
	 * @param response
	 * @param articleId
	 * @throws Exception
	 */
	@RequestMapping(value = "word/getOutlineByArticleId")
	@ResponseBody
	public void getOutlineSetting(HttpServletResponse response, Integer articleId) throws Exception{
		Map<String, Object> maps = new HashMap<String, Object>();
		ArticleProperty prop = wordService.getOutlineSetting(articleId);
		if(prop != null){
			maps.put("status", "success");
			maps.put("bean", prop);
		}else{
			maps.put("status", "error");
			maps.put("bean", null);
		}
		responseJson(response, maps);
	}
	
	/**
	 * 设置全局属性
	 * <p>Title: saveGlobalSetting</p> 
	 * <p>Description: TODO</p>
	 * @param response
	 * @param startDate
	 * @param endDate
	 * @param province
	 * @param industry
	 * @param articleId
	 * @throws Exception
	 */
	@RequestMapping(value = "word/saveGlobalSetting")
	@ResponseBody
	public void saveGlobalSetting(HttpServletResponse response, Date endDate, 
			Integer province, Integer industry, Integer articleId) throws Exception{
		
		ArticleProperty prop = wordService.getGlobalByArticleId(articleId);
		if(prop != null && prop.getArticleId() != null){
			//如果里面有值,在输入特殊字符时候，不处理
			if(!(prop.getAreaId() != null && province !=null && province==999999)){
				prop.setAreaId(province);
			}
			if(!(prop.getIndustryId() != null && industry != null && industry==999999)){
				prop.setIndustryId(industry);
			}
			if(!(prop.getEndTime()!=null&&endDate!=null&&endDate.getTime()<0)){
				prop.setEndTime(endDate);
			}
			wordService.updateGlobalProperty(prop);
		} else{
			prop = new ArticleProperty();
			prop.setEndTime(endDate);
			prop.setAreaId(province);
			prop.setIndustryId(industry);
			prop.setArticleId(articleId);
			wordService.saveGlobalSetting(prop);
		}
		Map<String, Object> maps = new HashMap<String, Object>();
		if(prop.getArticleId() != null && prop.getArticleId() > 0){
			maps.put("status", "success");
			maps.put("info", "设置成功");
		}else{
			maps.put("status", "error");
			maps.put("info", "设置失败");
		}
		responseJson(response, maps);
	}
	
	/**
	 * 设置大纲属性
	 * <p>Title: saveOutlineSetting</p> 
	 * <p>Description: TODO</p>
	 * @param response
	 * @param startDate
	 * @param endDate
	 * @param province
	 * @param industry
	 * @param articleId
	 * @throws Exception
	 */
	@RequestMapping(value = "word/saveOutlineSetting")
	@ResponseBody
	public void saveOutlineSetting(HttpServletResponse response, Date startDate, Date endDate, 
			Integer province, Integer industry, Integer articleId) throws Exception{
		
		ArticleProperty prop = wordService.getOutlineByArticleId(articleId);
		if(prop != null && prop.getArticleId() != null){
			//如果里面有值,在输入特殊字符时候，不处理
			prop.setAreaId(province);
			prop.setIndustryId(industry);
			prop.setStartTime(startDate);
			prop.setEndTime(endDate);
			wordService.updateOutlineProperty(prop);
		} else{
			prop = new ArticleProperty();
			prop.setStartTime(startDate);
			prop.setEndTime(endDate);
			prop.setAreaId(province);
			prop.setIndustryId(industry);
			prop.setArticleId(articleId);
			wordService.saveOutlineSetting(prop);
		}
		Map<String, Object> maps = new HashMap<String, Object>();
		if(prop.getArticleId() != null && prop.getArticleId() > 0){
			maps.put("status", "success");
			maps.put("info", "设置成功");
		}else{
			maps.put("status", "error");
			maps.put("info", "设置失败");
		}
		responseJson(response, maps);
	}
	
	/**
	 * 获取文章分类
	 * <p>Title: getArticlePlate</p> 
	 * <p>Description: TODO</p>
	 * @param sesssion
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="word/getArticlePlate")
	@ResponseBody
	public void getArticlePlate(HttpSession sesssion, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		String article_id=request.getParameter("articleId");
		String type=request.getParameter("type");
		KnowPlate knowPlate = new KnowPlate();
		knowPlate = wordService.getArticlePlate(Integer.parseInt(article_id), type);
		responseJson(response, knowPlate);
	}
	
	/**
	 * 提交审核重名验证
	 * <p>Title: submitCheckName</p> 
	 * <p>Description: TODO</p>
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="word/submitCheckName")
	@ResponseBody
	public void submitCheckName(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws IOException{
		Map<String,Object> maps = new HashMap<String,Object>();
		String type=request.getParameter("type");
		String name=request.getParameter("articleName");
		String articleId=request.getParameter("articleId");
		int userId = 0;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		Article bean=wordService.submitCheckName(userId,type, name,articleId);
		maps.put("article", bean);
		
		try{
			LOGGER.info("urlName=submitCheckName,urlMsg=提交审核,articleId="+articleId+"userId="+userId);
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		responseJson(response, maps);
	}
	
	/**
	 * 提交审核-在用
	 * <p>Title: editSubmitApprove</p> 
	 * @param articleName :文档名字
	 * @param articleId:文档Id
	 * @param radioValue:保存类型 1模板 2文档
	 * @param keyword :关键词
	 * @param brief:简介
	 * @param articleLabel:标签
	 * @param articleJournalId :期刊Id
	 * @param articleJournalName:期刊刊次
	 * @param articleJournalTime:期刊刊次时间
	 * @throws IOException
	 */
	@RequestMapping(value="word/editSubmitApprove")
	@ResponseBody
	public void editSubmitApprove(HttpSession session, HttpServletRequest request, 
			HttpServletResponse response) throws IOException{
		Map<String,Object> maps = new HashMap<String,Object>();
		Map<String,String> logMap = new HashMap<String,String>();
		logMap.put("keyword","");
		logMap.put("brief","");
		logMap.put("articleLabel","");
		
		//重名验证
		String vidioValue=request.getParameter("radioValue");//保存类型 1模板 2文档
		String articleName=request.getParameter("articleName");
		String article_id=request.getParameter("articleId");
		logMap.put("articleName",articleName);
		int userId=0;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		Integer articleId=Integer.parseInt(request.getParameter("articleId"));//文章id
		Article article = wordService.selectById(articleId);
		if(article==null){
			maps.put("state", "error");
			maps.put("info", "操作无效,该篇文档不存在");
		}else{
			//Article tempbean=wordService.submitCheckName(userId,vidioValue, articleName,article_id);
			boolean ArticleFlag =wordService.checkWordName(articleId,userId,articleName,article.getArticleType());
			if(ArticleFlag){
				maps.put("state", "error");
				maps.put("info", "文档名称已有重复");
			} else {//没有重名
				String articleJournalId=request.getParameter("articleJournalId");//期刊Id
				if("".equals(articleJournalId)){
					articleJournalId="0";
				}
				String keyword=request.getParameter("keyword");//关键字
				String brief= request.getParameter("brief");//简介
				String articleLabel = request.getParameter("articleLabel");//标签
				String articlePriceStr =request.getParameter("articlePrice");
				if(articlePriceStr==null||"".equals(articlePriceStr)){
					articlePriceStr="0";
				}
				Double articlePrice = (double) Integer.parseInt(articlePriceStr);
				
				keyword = HomePageUtil.transfer(keyword);
				brief = HomePageUtil.transfer(brief);
				articleName = HomePageUtil.transfer(articleName);
				articleLabel = HomePageUtil.transfer(articleLabel);
				
				Article newArticle = new Article();
				newArticle.setArticleId(articleId);//文档Id
				newArticle.setArticleName(articleName);
				newArticle.setArticleLabel(articleLabel);//文档标签
				newArticle.setArticleKeyWord(keyword);//文档的关键词
				newArticle.setArticleSkip(brief);
				newArticle.setSavestatus(1);
				newArticle.setPassTime(new Date());
				newArticle.setSubmitTime(new Date());
				newArticle.setPassType("SUBMITTED");
				newArticle.setArticlePrice(articlePrice);
				
				newArticle.setArticleJournalId(Integer.parseInt(articleJournalId));
				Double journalPrice = (double) 0;
				if(!("0".equals(articleJournalId))){//期刊文档
					String articleJournalName=request.getParameter("articleJournalName");//关键字
					String articleJournalTime= request.getParameter("articleJournalTime");//简介
					newArticle.setArticleJournalName(articleJournalName);
					newArticle.setArticleJournalTime(DateTool.strToDate(articleJournalTime+" 00:00:10"));
					WordJournal journal =  wordService.selectByPrimaryKey(Integer.parseInt(articleJournalId));
					if(journal!=null){
						journalPrice=journal.getPrice();
					}
					if(journalPrice==(double) 0){
						newArticle.setArticlePrice((double) 0);
					}
				}
				logMap.put("keyword",keyword);
				logMap.put("brief",brief);
				logMap.put("articleLabel",articleLabel);
				if(articlePrice<0||articlePrice>9999999){
					maps.put("state", "error");
					maps.put("info", "文档价格:请输入1-7位的整数");
				}
				else if(!("0".equals(articleJournalId))&&articlePrice>journalPrice){
					maps.put("state", "error");
					maps.put("info", "文档价格不能大于期刊价格");
				}
				else if(!("0".equals(articleJournalId))&&wordService.checkArticleJournalName(newArticle)){//有重复的
					maps.put("state", "error");
					maps.put("info", "期刊刊次重复");
				}else{
					if("write".equals(article.getArticleSave())){//自己编写的
						ContentWhole contentWhole=wordService.getArticleContentById(Integer.parseInt(article_id));
						//System.out.println("contentWhole.getWholeId()="+contentWhole.getWholeId());
						if((contentWhole!=null&&contentWhole.getWholeId()!=null&&contentWhole.getNodeContent()!=null&&contentWhole.getNodeContent().length()>0)){
							
							newArticle.setArticleType("1".equals(vidioValue)?"template":"document");//文档的类型
							String path=request.getSession().getServletContext().getRealPath("");
							boolean flag=homePageService.articleToWord(article,path,articleId,"article");
							if(flag){
								wordService.updateByPrimaryKeySelectiveArticle(newArticle);
								maps.put("newArticle", newArticle);
								maps.put("state", "success");
								maps.put("info", "审核成功");
							}else{
								maps.put("state", "error");
								maps.put("info", "提示失败,生成word失败");
							}
						}else{
							maps.put("state", "error");
							maps.put("info", "文件没有内容");
						}
						//自己编写完
					}else{//上传的
						wordService.updateByPrimaryKeySelectiveArticle(newArticle);
						maps.put("newArticle", newArticle);
						maps.put("state", "success");
						maps.put("info", "审核成功");
					}
				}
			}
				}


		try{
			LOGGER.info("urlName=word/editSubmitApprove,urlMsg=提交审核,articleId="+articleId+",userId="+userId+",state="+maps.get("state")+",info="+maps.get("info")+",articleName="+logMap.get("articleName")+",keyword="+logMap.get("keyword")+",brief="+logMap.get("brief")+",articleLabel="+logMap.get("articleLabel"));
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		responseJson(response, maps);
	}
	
	/**
	 * 重命名文章
	 * <p>Title: replaceName</p> 
	 * <p>Description: TODO</p>
	 * @param response
	 * @param articleId
	 * @param articleName
	 * @throws Exception
	 */
	@RequestMapping(value = "word/replaceName")
	@ResponseBody
	public void replaceName(HttpServletResponse response,HttpSession session, Integer articleId, String  articleName 
			) throws Exception{
		
		Integer userId = 0;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		Map<String,Object> maps = wordService.replaceName(userId, articleId, articleName);
		
		try{
			LOGGER.info("urlName=word/replaceName,urlMsg=重命名,articleId="+articleId+"userId="+userId+",articleName="+articleName);
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		responseJson(response, maps);
	}
	
	@RequestMapping(value ="word/docToHtml")
	@ResponseBody
	public void docToHtml(HttpServletResponse response,Integer articleId) throws IOException{
		boolean flag = wordService.docToHtml(articleId);
		
		Map<String, Object> maps = new HashMap<String, Object>();
		if(flag){
			maps.put("status", "success");
			maps.put("info", "生成成功");
		}else{
			maps.put("status", "error");
			maps.put("info", "生成失败");
		}
		responseJson(response, maps);
		
	}
}
