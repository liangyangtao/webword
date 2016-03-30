package com.web.view.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.service.CommonController;
import com.database.bean.Article;
import com.database.bean.KnowNopassReason;
import com.database.bean.WordArticleCount;
import com.database.bean.WordArticleHtml;
import com.database.bean.WordColumnGroup;
import com.database.bean.WordJournal;
import com.database.bean.WordUserMoney;
import com.database.bean.WordUsers;
import com.util.ConstantUtils;
import com.util.DateUtil;
import com.util.Md5Util;
import com.web.bean.Document;
import com.web.document.bean.News;
import com.web.document.bean.NewsCrawls;
import com.web.msg.UnbankSms.Sms;
import com.web.msg.service.SmsService;
import com.web.view.bean.JournalInfoBean;
import com.web.view.service.HomeService;

@Controller
@RequestMapping(value = "/view/")
public class HomeController extends CommonController {
	@Autowired
	HomeService homeService;
	@Autowired
	SmsService smsService;
	@Value("${nginxPic}")
	private String nginxPic;
	@Value("${picUrl}")
	private String picUrl;
	private int movepage = 5;
	private static Logger LOGGER = Logger.getLogger(HomeController.class);
	/**
	 * 新首页
	 * 
	 * @param response
	 * @param name
	 */
	@RequestMapping(value = "/index")
	public String newIndex(HttpServletResponse response, HttpSession session,
			Model model) {
		return "/newIndex";
	}

	/**
	 * 首页
	 * 
	 * @param response
	 * @param name
	 */
	@RequestMapping(value = "/home")
	public String home(HttpServletResponse response, HttpSession session,
			Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		//取出文档和上传两个值
		Map<String, Object> map=homeService.getAllCounts((user == null ? 1
				: user.getUserId()));
		//取出首页左侧栏目，数据库中首页左侧栏目总结点在word_column_type中id为1
		List<WordColumnGroup> leftGroups=homeService.getWordColumnGroup(1,(user == null ? 1
				: user.getUserId()));
		if(leftGroups==null || leftGroups.size()==0||leftGroups.isEmpty()){
			leftGroups=homeService.getWordColumnGroup(1,1);
		}
		WordColumnGroup wordColumnLeftGroup = leftGroups.get(0);		
		/*
		 * 取出首页中间栏目，数据库中首页中间栏目总结点在word_column_type中id为2,
		如果未登录则用管理员栏目，已登录先看有没有栏目，有栏目就用自己的栏目，没有就用管理员的栏目
		 * */
		List<WordColumnGroup> midGroups=homeService.getWordColumnGroup(2,(user == null ? 1
				: user.getUserId()));
		if(midGroups==null ||  midGroups.size()==0||midGroups.isEmpty()){
			midGroups=homeService.getWordColumnGroup(1,1);
		}
		WordColumnGroup wordColumnMidGroup = midGroups.get(0);
		//最新期刊专区
		Map<String, Object> latestJournal = homeService.latestJournal();
		//下方最新新闻报告专区
		List<Document> latestDoc = homeService.latestDoc();
		//获取用户资金信息
		if (user != null){
			WordUserMoney userMoney = homeService.getUserMoneyByUserId(user.getUserId());
			model.addAttribute("userMoney", userMoney);	
		}

		model.addAttribute("articleCount",map.get("articleCount"));
		model.addAttribute("uploadCount",map.get("uploadCount"));
		model.addAttribute("user", user);
		model.addAttribute("leftGroup",wordColumnLeftGroup);
		model.addAttribute("midGroup",wordColumnMidGroup);
		model.addAttribute("latestJournal", latestJournal);
		model.addAttribute("latestDoc", latestDoc);
		NewsCrawls newsList = homeService.getNewsList();
		model.addAttribute("newsList", newsList);
		return "/home/index";
	}

	/**
	 * 新闻详情
	 * 
	 * @param response
	 * @param name
	 */
	@RequestMapping(value = "/news")
	public String news(HttpServletResponse response, HttpSession session,String newsId,
			Model model) {
		News news = homeService.getNewsById(newsId);
		String searchImgReg = "(?x)(src|SRC|background|BACKGROUND)=('|\")(static.*?/)(.*?.(jpg|JPG|png|PNG|gif|GIF|jpeg|JPEG))('|\")";
		// 修改图片链接地址
		Pattern pattern = Pattern.compile(searchImgReg);
		Matcher matcher = pattern.matcher(news.getContent());
		StringBuffer replaceStr = new StringBuffer();
		while (matcher.find()) {
			 matcher.appendReplacement(replaceStr, matcher.group(1) + "="+picUrl
					  + matcher.group(4) + "");//逐个动态替换图片链接地址
		}
		matcher.appendTail(replaceStr);//添加尾部
		news.setContent(replaceStr.toString());
		model.addAttribute("news", news);
		model.addAttribute("date",new Date(news.getNewsDate()));
		List<News> moreLikeNews = homeService.moreLikeNews(newsId);
		//排除自己
		for (News n:moreLikeNews) {
			if(newsId.equals(n.getCrawl_id())){
				moreLikeNews.remove(n);
			}
		}
		model.addAttribute("moreLike",moreLikeNews.subList(0, (moreLikeNews.size()>10?10:moreLikeNews.size())));
		return "/home/news";
	}

	/**
	 * 登录
	 */
	@RequestMapping(value = "/login")
	public void login(HttpServletResponse response, HttpSession session,
			String account, String pass) {
		Map<String, Object> map = new HashMap<String, Object>();
		WordUsers user = homeService.login(account);
		String sessionId = session.getId();
		if (user == null) {
			map.put("result", 0);
			map.put("msg", "账号不存在!");
		} else {
			String pwMd5 = Md5Util.getMD5Str(pass);// 密码加密
			if (user.getUserPassword().equals(pwMd5)) {
				if (user.getEnabled().intValue() != 1) {
					map.put("result", 0);
					map.put("msg", "账号已禁用!");
				} else {
					session.setAttribute("user", user);
					session.setAttribute("userId", user.getUserId());
					map.put("result", 1);
					map.put("userId", user.getUserId());
					//更新sessionId
					user.setSessionId(sessionId);
					homeService.updateSession(user);
				}
			} else {
				map.put("result", 0);
				map.put("msg", "密码错误!");
			}
		}
		try {
			LOGGER.info("urlName=login,urlMsg=用户登录,account="+account+",result="+map.get("result")+",userId="+map.get("userId"));
			responseJson(response, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * 注册
	 */
	@RequestMapping(value = "/regist")
	public void regist(HttpServletResponse response, HttpSession session,
			WordUsers user,String checkMsg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", "0");
		if(session.getAttribute("checkMsg")!=null&&session.getAttribute("checkMsg").equals(checkMsg)){//验证码相等
			WordUsers u = homeService.login(user.getUserAccount());
			WordUsers u1 = null;
			if (u != null) {
				map.put("result", 0);
				map.put("msg", "邮箱已注册请更换");
			}
			if (user.getUserPhone() != null && !"".equals(user.getUserPhone())) {
				u1 = homeService.login(user.getUserPhone());
				if (u1 != null) {
					map.put("result", 0);
					map.put("msg", "手机号已注册请更换");
				}
			} else {
				// user.setUserPhone(null);
			}
			if (u == null && u1 == null) {
				String pwMd5 = Md5Util.getMD5Str(user.getUserPassword());// 密码加密
				user.setUserPassword(pwMd5);
				user.setEnabled(1);
				user.setIssys(0);
				user.setDateAffect(new Date());
				user.setSessionId(session.getId());
				user.setUserName(user.getUserAccount());
				homeService.regist(user);
				session.setAttribute("user", user);
				session.setAttribute("userId", user.getUserId());
				map.put("result", 1);
				map.put("userId", user.getUserId());
			}
		}else{
			map.put("result", 0);
			map.put("msg", "手机验证码错误");
		}
		try {
			LOGGER.info("urlName=regist,urlMsg=用户注册,userAccount="+user.getUserAccount()+",userPhone="+user.getUserPhone()+",userId="+map.get("userId")+",result="+map.get("result")+",msg="+map.get("msg"));
			responseJson(response, map);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
		}
	}
	
	/**
	 * 验证手机号码是否重复
	 * @param response
	 * @param session
	 * @param phone
	 */
	@RequestMapping(value = "/validEmailorPhone")
	public void validEmailorPhone(HttpServletResponse response, HttpSession session,
			String account){
		Map<String, Object> map = new HashMap<String, Object>();
		WordUsers user = homeService.login(account);
		if (user != null) {
			map.put("result", 0);
			map.put("msg", account.indexOf("@")>=0?"邮箱已注册请更换":"手机号已注册请更换");
		}else{
			map.put("result", 1);
		}
		try {
			responseJson(response, map);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 是否登录
	 */
	@RequestMapping(value = "/islogin")
	public void islogin(HttpServletResponse response, HttpSession session) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		try {
			responseJson(response, (user != null ? 1 : 0));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 退出
	 */
	@RequestMapping(value = "/logout")
	public void logout(HttpServletResponse response, HttpSession session) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		session.removeAttribute("user");
		session.removeAttribute("userId");
		try {
			LOGGER.info("urlName=logout,urlMsg=用户退出,userId="+(user!=null?user.getUserId():""));
			responseJson(response, 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * 搜索
	 * 
	 * @param model
	 * @param keyword
	 * @param type
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/search")
	public String search(HttpSession session, Model model, String keyword,
			String searchType, String articleFormat,String docType,Integer pageNo, Integer pageSize, Integer startPage) {
		/*
		 * try { keyword = new String(keyword.getBytes("ISO-8859-1"), "UTF-8");
		 * } catch (UnsupportedEncodingException e) { // TODO Auto-generated
		 * catch block }
		 */
		int userId=0;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		if(docType==null||"".equals(docType)||docType.length()==0){
			docType="1";
		}
		if(searchType==null||"".equals(searchType)||searchType.length()==0){
			searchType="title";
		}
		if(articleFormat==null||"".equals(articleFormat)||articleFormat.length()==0){
			articleFormat="all";
		}
		Map<String, Object> map = homeService.search(keyword, searchType,articleFormat,docType, pageNo,
				pageSize);
		map.put("startPage", (startPage == null ? 1 : startPage));
		map.put("endPage", Math.min((startPage == null ? 1 : startPage)
				+ movepage, (Long) map.get("pageCount")));
		map.put("movepage", movepage);
		model.addAttribute("page", map);
		model.addAttribute("keyword", keyword);
		model.addAttribute("searchType", searchType);
		model.addAttribute("articleFormat", articleFormat);
		model.addAttribute("docType", docType);
		LOGGER.info("urlName=view/search,urlMsg=用户搜索模板和文档,keyword="+keyword+",searchType="+searchType+",userId="+userId);
		return "/home/search";
	}
	
	/**
	 * 搜索更多
	 * 
	 * @param model
	 * @param keyword
	 * @param type
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/more")
	public String more(HttpSession session, Model model, String keyword,
			String searchType, String articleFormat,String journalId,Integer pageNo, Integer pageSize, Integer startPage) {
		/*
		 * try { keyword = new String(keyword.getBytes("ISO-8859-1"), "UTF-8");
		 * } catch (UnsupportedEncodingException e) { // TODO Auto-generated
		 * catch block }
		 */
		int userId=0;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		Map<String, Object> map = homeService.more(keyword, searchType,articleFormat, journalId,pageNo,
				pageSize);
		map.put("startPage", (startPage == null ? 1 : startPage));
		map.put("endPage", Math.min((startPage == null ? 1 : startPage)
				+ movepage, (Long) map.get("pageCount")));
		map.put("movepage", movepage);
		model.addAttribute("page", map);
		model.addAttribute("keyword", keyword);
		model.addAttribute("searchType", searchType);
		model.addAttribute("articleFormat", articleFormat);
		model.addAttribute("journalId", journalId);
		LOGGER.info("urlName=view/more,urlMsg=用户搜索更多模板和文档,keyword="+keyword+",searchType="+searchType+",userId="+userId);
		return "/home/more";
	}


	/**
	 * 栏目下的文档
	 * 
	 * @param session
	 * @param model
	 * @param plateId
	 * @param pageNo
	 * @param pageSize
	 * @param startPage
	 * @param contentType 内容类型 1：期刊  2：非期刊
	 * @return
	 */
	@RequestMapping(value = "/plate")
	public String plate(HttpSession session, Model model, Integer plateId,
			Integer pageNo, Integer pageSize, Integer startPage, Integer contentType) {
		Integer userId = null;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		Map<String, Object> map = null;
		if (contentType != null && contentType == 2){
			//获取非期刊
			map = homeService.plate(plateId, pageNo, pageSize);
		}else {	
			//获取期刊
			map = homeService.journal(plateId, pageNo, pageSize, null,userId);
		}
		if (map != null) {
			map.put("startPage", (startPage == null ? 1 : startPage));
			map.put("startPage", (startPage == null ? 1 : startPage));
			map.put("endPage", Math.min((startPage == null ? 1 : startPage)
					+ movepage, (Long) map.get("pageCount")));
			map.put("movepage", movepage);
			model.addAttribute("page", map);
			model.addAttribute("navigater", homeService.getNavigater(plateId,1));
		}
		model.addAttribute("plateId", plateId);
		model.addAttribute("pid", homeService.getPlageById(plateId));
		model.addAttribute("contentType", contentType == null ? 1 : contentType);
		LOGGER.info("urlName=view/plate,urlMsg=栏目下文档,plateId="+plateId+",userId="+userId);
		return "/home/plate";
	}
	
//	/**
//	 * 预览非期刊页面
//	 * 
//	 * @param request
//	 * @param model
//	 * @param artilceId
//	 */
//	@RequestMapping(value = "/preview")
//	public String preview(HttpSession session, Model model, int articleId) {
//		WordUsers user = (WordUsers) session.getAttribute("user");
//		int userId=0;
//		if(session.getAttribute("userId")!=null){
//			userId = (Integer) session.getAttribute("userId");
//		}
//		List<WordPlate> list = homeService.getUserPlate((user == null ? 1
//				: user.getUserId()));
//		model.addAttribute("user", user);
//		model.addAttribute("list", list);
//		Article article = homeService.getArticle(articleId);
////		//获取期刊信息
////		if (article != null && article.getArticleJournalId() != null&&article.getArticleJournalId() !=0){
////			WordJournal wordJournal = homeService.getWordJournal(article.getArticleJournalId());
////			model.addAttribute("wordJournal", wordJournal);
////			//获取期刊分期信息
////			JournalInfoBean journalInfoBean = new JournalInfoBean();
////			journalInfoBean.setJournalId(article.getArticleJournalId());
////			journalInfoBean.setJournalTypeId(wordJournal.getTypeId());
////			if (article.getArticleJournalTime() != null){
////				//年份条件
////				String articleYear = DateUtil.convertDateToString(article.getArticleJournalTime(), "yyyy");
////				String articleMonth = DateUtil.convertDateToString(article.getArticleJournalTime(), "MM");
////				journalInfoBean.setArticleYear(articleYear);
////				
////				//日刊、周刊或者半月刊需要传月份作为参数
////				if (wordJournal.getTypeId() == 1 || wordJournal.getTypeId() == 2 || wordJournal.getTypeId() == 3){
////					//月份
////					journalInfoBean.setArticleMonth(articleMonth);	
////				}
////
////				model.addAttribute("articleYear", articleYear);
////				model.addAttribute("articleMonth", articleMonth);
////			}
////			//获取期刊分期信息
////			Map<String,List<Object>> journalMap = homeService.getJournalInfo(journalInfoBean);			
////			model.addAttribute("yearList", journalMap.get("articleYear"));
////			model.addAttribute("monthList", journalMap.get("articleMonth"));
////			model.addAttribute("showList", journalMap.get("articleShow"));
////		}
//		//获取文章阅读及下载次数
//		if (article != null){
//			WordArticleCount articleCount = homeService.getWordArticleCount(articleId);
//			articleCount.setViewCount(articleCount.getViewCount()+1);
//			homeService.updateWordArticleCount(articleCount);
//			model.addAttribute("articleCount", articleCount);
//		}
//
//		model.addAttribute("article", article);
//		model.addAttribute("articles", homeService.moreLikeSearch(articleId));
//		LOGGER.info("urlName=view/preview,urlMsg=预览页面,articleId="+articleId+",userId="+userId);
//		return "/home/preview";
//	}
	
//	/**
//	 * 预览期刊页面
//	 * 
//	 * @param request
//	 * @param model
//	 * @param journalId
//	 */
//	@RequestMapping(value = "/previewJournal")
//	public String previewJournal(HttpSession session, Model model, int journalId) {
//		WordUsers user = (WordUsers) session.getAttribute("user");
//		int userId=0;
//		if(session.getAttribute("userId")!=null){
//			userId = (Integer) session.getAttribute("userId");
//		}
//		List<WordPlate> list = homeService.getUserPlate((user == null ? 1
//				: user.getUserId()));
//		model.addAttribute("list", list);
//		model.addAttribute("user", user);
//		Article article = homeService.getArticleByJournalTime(journalId);
//		//获取期刊信息
//		if (article != null && article.getArticleJournalId() != null){
//			WordJournal wordJournal = homeService.getWordJournal(article.getArticleJournalId());
//			model.addAttribute("wordJournal", wordJournal);
//			//获取期刊分期信息
//			JournalInfoBean journalInfoBean = new JournalInfoBean();
//			journalInfoBean.setJournalId(article.getArticleJournalId());
//			journalInfoBean.setJournalTypeId(wordJournal.getTypeId());
//			if (article.getArticleJournalTime() != null){
//				//年份条件
//				String articleYear = DateUtil.convertDateToString(article.getArticleJournalTime(), "yyyy");
//				String articleMonth = DateUtil.convertDateToString(article.getArticleJournalTime(), "MM");
//				journalInfoBean.setArticleYear(articleYear);
//				
//				//日刊、周刊或者半月刊需要传月份作为参数
//				if (wordJournal.getTypeId() == 1 || wordJournal.getTypeId() == 2 || wordJournal.getTypeId() == 3){
//					//月份
//					journalInfoBean.setArticleMonth(articleMonth);	
//				}
//
//				model.addAttribute("articleYear", articleYear);
//				model.addAttribute("articleMonth", articleMonth);
//			}
//			//获取期刊分期信息
//			Map<String,List<Object>> journalMap = homeService.getJournalInfo(journalInfoBean);			
//			model.addAttribute("yearList", journalMap.get("articleYear"));
//			model.addAttribute("monthList", journalMap.get("articleMonth"));
//			model.addAttribute("showList", journalMap.get("articleShow"));
//		}
//		//获取文章阅读及下载次数
//		if (article != null){
//			WordArticleCount articleCount = homeService.getWordArticleCount(article.getArticleId());
//			articleCount.setViewCount(articleCount.getViewCount()+1);
//			homeService.updateWordArticleCount(articleCount);
//			model.addAttribute("articleCount", articleCount);
//			model.addAttribute("articles", homeService.moreLikeSearch(article.getArticleId()));
//		}
//		model.addAttribute("article", article);
//		LOGGER.info("urlName=view/preview,urlMsg=预览页面,journalId="+journalId+",userId="+userId);
//		if (article != null){
//			return "/home/preview";
//		}
//		return "null";
//	}
	
	/**
	 * 动态获取分期
	 * 
	 * @param articleId
	 */
	@RequestMapping(value = "/getJournalInfo")
	public void getJournalInfo(HttpServletResponse response, HttpSession session,
			Integer articleId, String articleYear, String articleMonth) {
		
		Article article = homeService.getArticle(articleId);
		//获取期刊信息
		if (article != null && article.getArticleJournalId() != null){
			WordJournal wordJournal = homeService.getWordJournal(article.getArticleJournalId());
			//获取期刊分期信息
			JournalInfoBean journalInfoBean = new JournalInfoBean();
			journalInfoBean.setJournalId(article.getArticleJournalId());
			journalInfoBean.setJournalTypeId(wordJournal.getTypeId());
			//年份条件
			journalInfoBean.setArticleYear(articleYear);
			
			//日刊、周刊或者半月刊需要传月份作为参数
			if (!StringUtil.isBlank(articleMonth) && (wordJournal.getTypeId() == 1 ||
					wordJournal.getTypeId() == 2 || wordJournal.getTypeId() == 3)){
				//月份
				journalInfoBean.setArticleMonth(articleMonth);	
			}
			//获取期刊分期信息
			Map<String,List<Object>> journalMap = homeService.getJournalInfo(journalInfoBean);		
			try {
				responseJson(response, journalMap);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 动态获取分期（新）
	 * 
	 * @param articleId
	 */
	@RequestMapping(value = "/getJournalInfoNew")
	public void getJournalInfoNew(HttpServletResponse response, HttpSession session,
			Integer selArticleId, String articleYear, String articleMonth, String articleDay) {
		Article article = homeService.getArticle(selArticleId);
		//获取期刊信息
		if (article != null && article.getArticleJournalId() != null){
			WordJournal wordJournal = homeService.getWordJournal(article.getArticleJournalId());
			//获取期刊分期信息
			JournalInfoBean journalInfoBean = new JournalInfoBean();
			journalInfoBean.setJournalId(article.getArticleJournalId());
			journalInfoBean.setJournalTypeId(wordJournal.getTypeId());
			String articleJournalTime = "";
			if (!StringUtil.isBlank(articleYear)){
				articleJournalTime += articleYear + "-";
			}
            if (!StringUtil.isBlank(articleMonth)){
				articleJournalTime += articleMonth+ "-";
			}else{
				articleJournalTime += "12-";
			}
            //如果是日刊只需拼接条件中的日即可，非期刊的则拼接月底日期
            if (wordJournal.getTypeId() == ConstantUtils.JournalType.DAY){
            	if (!StringUtil.isBlank(articleDay)){
            		articleJournalTime += articleDay+ " 23:59:59";
            	}else{
        			String day = DateUtil.convertDateToString(article.getArticleJournalTime(), "dd");
        			articleJournalTime += day+ " 23:59:59";
            	}
            }else{
            	articleJournalTime += "31 23:59:59";
            }
			Date articleJournalDate = DateUtil.convertStringToDate(articleJournalTime,"yyyy-MM-dd HH:mm:ss");
			journalInfoBean.setArticleJournalTime(articleJournalDate);
			//获取期刊分期信息
			Map<String,List<Object>> journalMap = homeService.getJournalInfoNew(journalInfoBean);	
			try {
				responseJson(response, journalMap);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 预览期刊页面(新)
	 * 
	 * @param request
	 * @param model
	 * @param journalId
	 * @param articleId 预览文档的ID
	 * @param selArticleId 查询条件的文档ID
	 */
	@RequestMapping(value = "/preview")
	public String previewJournalNew(HttpSession session, Model model, Integer journalId, Integer articleId, Integer selArticleId) {

		WordUsers user = (WordUsers) session.getAttribute("user");
		Article article = null;
		Article selArticle = null;
		if (journalId != null){
			article = homeService.getArticleByJournalTime(journalId);
		}else{
			article = homeService.getArticle(articleId);
		}
		if (selArticleId != null){
			selArticle = homeService.getArticle(selArticleId);
		}else{
			selArticle = article;
		}
		//获取期刊信息
		WordJournal wordJournal = null;
		if (selArticle != null && selArticle.getArticleJournalId() != null && selArticle.getArticleJournalId().intValue()!=0){
			wordJournal = homeService.getWordJournal(selArticle.getArticleJournalId());
			model.addAttribute("wordJournal", wordJournal);
			//获取期刊分期信息
			JournalInfoBean journalInfoBean = new JournalInfoBean();
			journalInfoBean.setJournalId(selArticle.getArticleJournalId());
			journalInfoBean.setJournalTypeId(wordJournal.getTypeId());
			journalInfoBean.setArticleJournalTime(selArticle.getArticleJournalTime());
			if (selArticle.getArticleJournalTime() != null){
				//年份条件
				String articleYear = DateUtil.convertDateToString(selArticle.getArticleJournalTime(), "yyyy");
				String articleMonth = DateUtil.convertDateToString(selArticle.getArticleJournalTime(), "MM");
				String articleDay = DateUtil.convertDateToString(selArticle.getArticleJournalTime(), "dd");
				model.addAttribute("articleYear", articleYear);
				model.addAttribute("articleMonth", articleMonth);
				model.addAttribute("articleDay", articleDay);
			}
			//获取期刊分期信息
			Map<String,List<Object>> journalMap = homeService.getJournalInfoNew(journalInfoBean);	
			model.addAttribute("yearList", journalMap.get("articleYearList"));
			model.addAttribute("monthList", journalMap.get("articleMonthList"));
			model.addAttribute("dayList", journalMap.get("articleDayList"));
			model.addAttribute("showList", journalMap.get("articleShowList"));
			model.addAttribute("selArticleId", selArticle.getArticleId());
		}
		//获取文章阅读及下载次数
		if (article != null){
			WordArticleCount articleCount = homeService.getWordArticleCount(article.getArticleId());
			articleCount.setViewCount(articleCount.getViewCount()+1);
			homeService.updateWordArticleCount(articleCount);
			model.addAttribute("articleCount", articleCount);
			model.addAttribute("articles", homeService.moreLikeSearch(article.getArticleId()));
			if (article.getHtmlPage() != null && !"".equals(article.getHtmlPage())){
				int htmlPage = Integer.valueOf(article.getHtmlPage());
				model.addAttribute("endPage", ConstantUtils.PREVIEW_PAGE > htmlPage ? htmlPage : ConstantUtils.PREVIEW_PAGE);//默认加载页数	
			}
		}
		model.addAttribute("article", article);
		Integer buyStatus = 0;//文档状态 0：未购买 1:免费  2：已购买 3：自己的
		Integer jourBuyStatus = 0;//期刊状态0：未购买 1:免费  2：已购买 3：自己的
		Integer artCartStatus = 0;//文档加入购物车状态：0：未加入  1：已加入
		Integer jourCartStatus = 0;//期刊加入购物车状态0：未加入  1：已加入
		if (user != null){
			//获取用户资金信息
			WordUserMoney userMoney = homeService.getUserMoneyByUserId(user.getUserId());
			model.addAttribute("userMoney", userMoney);
			//获取文档购买状态
			if (article != null){
				//先判断该文档属否属于当前登录人自己的
				if (user.getUserId().equals(article.getUserId())){
					buyStatus = 3;
				}else{
					//根据文档的价格判断 如果文档价格为空或为0 则表示该文档免费
					if (article.getArticlePrice() == null || article.getArticlePrice() <= 0){
						buyStatus = 1;
					}else{
						boolean status = homeService.getArticleBuyStatus(user.getUserId(), article.getArticleId(), article.getArticleJournalId(), article.getArticleJournalTime());
						if (status == true){
							buyStatus = 2;
						}	
					}
				}
				//获取文档加入购物车状态
				boolean artCart = homeService.getArticleCart(user.getUserId(), article.getArticleId());
				if (artCart == true){
					artCartStatus = 1;
				}
			}
			//获取期刊购买状态
			if (wordJournal != null){
				
				if (wordJournal.getPrice() <= 0){
					jourBuyStatus = 1;
				}else{
					boolean jourStatus = homeService.getJournalBuyStatus(user.getUserId(), wordJournal.getId());
					if (jourStatus == true){
						jourBuyStatus = 2;
					}
				}
				//获取期刊加入购物车状态
				boolean jourCart = homeService.getJournalCart(user.getUserId(), wordJournal.getId());
				if (jourCart == true){
					jourCartStatus = 1;
				}
			}
		}else{
			//根据文档的价格判断 如果文档价格为空或为0 则表示该文档免费
			if (article.getArticlePrice() == null || article.getArticlePrice() <= 0){
				buyStatus = 1;
			}
			//获取期刊购买状态
			if (wordJournal != null){		
				if (wordJournal.getPrice() <= 0){
					jourBuyStatus = 1;
				}
			}
		}
		model.addAttribute("buyStatus", buyStatus);
		model.addAttribute("jourBuyStatus", jourBuyStatus);
		model.addAttribute("artCartStatus", artCartStatus);
		model.addAttribute("jourCartStatus", jourCartStatus);
		//获取文档的类型 'journalarticle','article','journal'
		if (article != null && wordJournal != null){
			model.addAttribute("resoureType", "journalarticle");//期刊文档
		}else{
			model.addAttribute("resoureType", "article");//文档
		}
		LOGGER.info("urlName=view/preview,urlMsg=预览页面,articleId="+article.getArticleId()+",userId="+(user == null ? null :user.getUserId()));
		return "/home/preview";
	}
	
	/**
	 * 下载文档
	 * 
	 * @param request
	 * @param model
	 * @param logger
	 * @param artilceId
	 * @throws IOException 
	 */
	@RequestMapping(value = "/download")
	public String download(HttpSession session, HttpServletRequest request,
			HttpServletResponse response, Model model, int articleId) throws IOException {
		Article article = homeService.getArticle(articleId);
		String path = nginxPic+"doc"
				+ File.separator + "article" + articleId +"."+article.getArticleFormat();
		File file = new File(path);
		Integer userId = null;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		
		Integer buyStatus = 0;//文档状态 0：未购买 1:免费  2：已购买 3：自己的
		//获取文档购买状态
		if (article != null){
			//先判断该文档属否属于当前登录人自己的
			if (article.getUserId().equals(userId)){
				buyStatus = 3;
			}else{
				//根据文档的价格判断 如果文档价格为空或为0 则表示该文档免费
				if (article.getArticlePrice() == null || article.getArticlePrice() <= 0){
					buyStatus = 1;
				}else{
					boolean status = homeService.getArticleBuyStatus(userId, article.getArticleId(), article.getArticleJournalId(), article.getArticleJournalTime());
					if (status == true){
						buyStatus = 2;
					}	
				}
			}
		}
		//有权限的才可以下载
		if (buyStatus != 0){
			LOGGER.info("urlName=view/download,urlMsg=下载文档,articleId="+articleId+",userId="+userId);
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			if (article != null) {
				try {
					response.setCharacterEncoding("utf-8");
					String name = article.getArticleName().replaceAll(" ", "")+"."+
							article.getArticleFormat();
					String agent = request.getHeader("User-Agent").toUpperCase();
					//RV:11.0是 ie11
					if ( agent.indexOf("MSIE") > 0 || agent.indexOf("RV:11.0")>0) {
						name = URLEncoder.encode(name, "UTF-8");
					} else {
						name = new String(name.getBytes("UTF-8"), "ISO8859-1");
					}
					response.setHeader("Content-Disposition",
							"attachment;fileName=" + name);
					response.setContentType("application/octet-stream");
					response.setHeader("Content-Length",
							String.valueOf(file.length()));
					bis = new BufferedInputStream(new FileInputStream(file));
					bos = new BufferedOutputStream(response.getOutputStream());
					byte[] buff = new byte[2048];
					int bytesRead;
					while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
						bos.write(buff, 0, bytesRead);
					}
					WordArticleCount articleCount = homeService.getWordArticleCount(articleId);
					articleCount.setDownCount(articleCount.getDownCount()+1);
					homeService.updateWordArticleCount(articleCount);
					bos.flush();
					bis.close();
					bos.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				} finally {
					try {
						if (bis != null) {
							bis.close();
						}
						if (bos != null) {
							bos.close();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}else{
			response.sendRedirect(request.getContextPath()+"/view/preview.do?articleId="+articleId);
		}
		return null;
	}

	/**
	 * 获得原因
	 * 
	 * @param response
	 * @param articleId
	 * @return
	 */
	@RequestMapping(value = "/reason")
	public String getReason(HttpServletResponse response, int articleId,String type) {
		KnowNopassReason knowNopassReason = homeService.getReason(articleId,type);
		try {
			responseJson(response, knowNopassReason);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 发送短信验证码
	 * @param response
	 * @param session
	 * @param userPhone 手机号码
	 * @return
	 */
	@RequestMapping(value="/sendCheckMsg")
	public String sendCheckMsg(HttpServletResponse response, HttpSession session,String userPhone,int userType) {
		Map<String,Object> maps = new HashMap<String,Object>();
		long preTime = 0;
		if(session.getAttribute("checkMsgTime")!=null){
			preTime=(Long) session.getAttribute("checkMsgTime");
		}
		if(preTime==0||(System.currentTimeMillis()-preTime)>60000){
			//APPID :创享平台1 风险 0
			Sms sms = smsService.sendSms(userPhone,userType,1);
			if(sms.isSuccess()){//短信发送成功
				session.setAttribute("checkMsgTime", System.currentTimeMillis());
				session.setAttribute("checkMsg", sms.getSecurityCode());
				session.setAttribute("userPhone", userPhone);
				maps.put("status", "success");
				maps.put("info", "发送成功");
				maps.put("checkMsg", sms.getSecurityCode());
			}else{
				maps.put("status", "error");
				maps.put("info", sms.getMsg());
			}
		}else{
				maps.put("status", "error");
				maps.put("info", "短信验证码间隔大于60秒");
		}
		try {
			responseJson(response, maps);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 左侧栏目和中间栏目下的文档
	 * 
	 * @param session
	 * @param model
	 * @param columnId
	 * @param pageNo
	 * @param pageSize
	 * @param startPage
	 * @param contentType 内容类型 1：期刊  2：非期刊
	 * @return
	 */
	@RequestMapping(value = "/column")
	public String column(HttpSession session, Model model, Integer columnId,
			Integer pageNo, Integer pageSize, Integer startPage, Integer contentType) {
		Integer userId = null;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		Map<String, Object> map = null;
		if (contentType != null && contentType == 2){
			//获取非期刊
			map = homeService.column(columnId, pageNo, pageSize);
		}else {	
			//获取期刊
			map = homeService.columnJour(columnId, pageNo, pageSize,null,userId);
		}
		if (map != null) {
			map.put("startPage", (startPage == null ? 1 : startPage));
			map.put("startPage", (startPage == null ? 1 : startPage));
			map.put("endPage", Math.min((startPage == null ? 1 : startPage)
					+ movepage, (Long) map.get("pageCount")));
			map.put("movepage", movepage);
			model.addAttribute("page", map);
			model.addAttribute("navigater", homeService.getNavigater(columnId,2));
		}
		model.addAttribute("columnId", columnId);
		model.addAttribute("contentType", contentType == null ? 1 : contentType);
		LOGGER.info("urlName=view/column,urlMsg=左侧栏目和中间栏目下的文档,columnId="+columnId+",userId="+userId);
		return "/home/plate";
	}
	
	
	/**
	 * 文档预览展示页面
	 * @param articleId
	 * @return
	 */
	@RequestMapping(value="getHtml")
	public String getHtml(HttpServletRequest request,HttpSession session,Integer articleId,Model model){
		
		WordUsers user = (WordUsers) session.getAttribute("user");
		//获取样式
		List<WordArticleHtml> styles = homeService.getWordArticleStyles(Integer.valueOf(articleId));
		if (styles != null && styles.size() > 0){
			model.addAttribute("styles", styles.get(0).getConetnt());
		}
		Article article = homeService.getArticle(articleId);
		if (article != null){
			int htmlPage = article.getHtmlPage() == null ? 0 : Integer.valueOf(article.getHtmlPage());//文档总页数
			int unreadPage = 0;//未读页数
			int endPage = 3;
			boolean power = homeService.getArticlePower(user, article);
			//获取内容
			List<WordArticleHtml> contents = new ArrayList<WordArticleHtml>();
			if (power){
				unreadPage = htmlPage - ConstantUtils.PREVIEW_PAGE;
				contents = homeService.getWordArticleContents(articleId,0,ConstantUtils.PREVIEW_PAGE);
			}else{
				//不足5页的只能看1页
				if (!article.getHtmlPage().isEmpty()){
					if (htmlPage < 5){
						endPage = 1;				
					}
					unreadPage = htmlPage - endPage;
				}
				contents = homeService.getWordArticleContents(articleId,0,endPage);
			}

			StringBuffer contentsBuf = new StringBuffer();
			if (contents != null && contents.size() > 0){
				for(WordArticleHtml wah : contents){
					contentsBuf.append(wah.getConetnt());
				}
				if (power && unreadPage > 0){
					int startPage = ConstantUtils.PREVIEW_PAGE + 1;
					contentsBuf.append("<div id='more1' class='banner-wrap more-btn-banner' onclick='parent.loadArticleContent("+startPage+",null);'>");
					contentsBuf.append("<div class='banner-more-btn'>");
					contentsBuf.append("<span class='moreBtn goBtn'>");
					contentsBuf.append("<span>还剩"+unreadPage+"页未读，</span><span class='fc2e'>继续阅读</span></span>");
					contentsBuf.append("</div>");
					contentsBuf.append("<p class='down-arrow goBtn'></p>");
					contentsBuf.append("</div>");	
				}
				model.addAttribute("contents", contentsBuf.toString());	
			}
		}
		model.addAttribute("articleId", articleId);
		LOGGER.info("urlName=view/getHtml,urlMsg=文档预览初始化,articleId="+articleId);
		return "/home/pageview";
	}
	
	/**
	 * 根据文档ID和开始页数动态获取文档内容
 	* @param articleId
 	* @param startPageId
 	* @return
 	 */
	@RequestMapping(value="/getMoreArticleContents")
	public void getMoreArticleContents(Integer articleId,Integer startPage,Integer endPage,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		
		Map<String,Object> map = new HashMap<String,Object>();
		int htmlPage = 0;//文档总页数
		int unreadPage = 0;//未读页数
		Article article = homeService.getArticle(articleId);
		if (article != null){
			htmlPage = Integer.valueOf(article.getHtmlPage());
		}
		if (endPage == null){
			//每次加载规定页数
			endPage = startPage + ConstantUtils.PREVIEW_PAGE - 1;
		}else{
			endPage = (endPage/ConstantUtils.PREVIEW_PAGE + 1)*ConstantUtils.PREVIEW_PAGE;
		}
		if (endPage >= htmlPage){
			endPage = htmlPage;
		}else{
			unreadPage = htmlPage - endPage;
		}
		map.put("endPage", endPage == htmlPage ? endPage: endPage);
		int pageNo = endPage/ConstantUtils.PREVIEW_PAGE;//分页次数
		map.put("pageNo", pageNo);
		List<WordArticleHtml> list = homeService.getWordArticleContents(articleId,startPage,endPage);
		StringBuffer contentsBuf = new StringBuffer();
		if(list != null && list.size() > 0){
			for(WordArticleHtml wah : list){
				contentsBuf.append(wah.getConetnt());
			}
            if (unreadPage > 0){
            	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
            	contentsBuf.append("<div id='preLoadImg'style='text-align:center;height:100px;padding-top:80px'><img src='"+basePath+"word/img/loading.gif' width='32' height='32'></div>");
    			contentsBuf.append("<div id='more"+pageNo+"' class='banner-wrap more-btn-banner last_load' onclick='parent.loadArticleContent("+(endPage+1)+",null);'>");
    			contentsBuf.append("<div class='banner-more-btn'>");
    			contentsBuf.append("<span class='moreBtn goBtn'>");
    			contentsBuf.append("<span class='fc2e'>阅读后"+ConstantUtils.PREVIEW_PAGE+"页</span></span>");
    			contentsBuf.append("</div>");
    			contentsBuf.append("<p class='down-arrow goBtn'></p>");
    			contentsBuf.append("</div>");   			
            }
		
			map.put("contents", contentsBuf.toString());
		}
		try {
			responseJson(response,map);
			LOGGER.info("urlName=view/getMoreArticleContents,urlMag=动态获取文档内容,articleId="+articleId);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

