package com.web.view.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.database.bean.Article;
import com.database.bean.ArticleExample;
import com.database.bean.ArticlePropertyExample;
import com.database.bean.ContentExample;
import com.database.bean.ContentWhole;
import com.database.bean.ContentWholeExample;
import com.database.bean.ContentWithBLOBs;
import com.database.bean.HomepageExample;
import com.database.bean.NewsUserFavoriteExample;
import com.database.bean.PluginUser;
import com.database.bean.PluginUserExample;
import com.database.bean.WordJournal;
import com.database.bean.WordJournalExample;
import com.database.bean.WordResource;
import com.database.bean.WordResourceExample;
import com.database.bean.WordUserMoney;
import com.database.bean.WordUserMoneyExample;
import com.database.bean.WordUserMoneyLog;
import com.database.bean.WordUserMoneyLogExample;
import com.database.bean.WordUsers;
import com.database.bean.WordUsersExample;
import com.database.dao.ArticleMapper;
import com.database.dao.ArticlePropertyMapper;
import com.database.dao.ContentMapper;
import com.database.dao.ContentWholeMapper;
import com.database.dao.HomepageMapper;
import com.database.dao.NewsUserFavoriteMapper;
import com.database.dao.PluginUserMapper;
import com.database.dao.WordJournalMapper;
import com.database.dao.WordResourceMapper;
import com.database.dao.WordUserMoneyLogMapper;
import com.database.dao.WordUserMoneyMapper;
import com.database.dao.WordUsersMapper;
import com.google.gson.Gson;
import com.util.CommonUtils;
import com.util.DateTool;
import com.util.DateUtil;
import com.web.bean.Document;
import com.web.bean.DocumentSearchCondition;
import com.web.bean.ResponseParam;
import com.web.bean.SearchDocumentData;
import com.web.homePage.util.GetWholeContent;
import com.web.utils.Fetcher;
import com.web.utils.HttpClientBuilder;

@Service
public class UserCenterService {
	@Autowired
	NewsUserFavoriteMapper newsUserFavoriteMapper;
	@Autowired
	WordJournalMapper  wordJournalMapper;
	@Autowired
	ArticleMapper articleMapper;
	@Autowired
	ContentMapper contentMapper;
	@Autowired
	PluginUserMapper pluginUserMapper;
	@Autowired
	ContentWholeMapper contentWholeMapper;
	@Autowired
	HomepageMapper homepageMapper;
	@Autowired
	ArticlePropertyMapper articlePropertyMapper;
	@Autowired
	WordUsersMapper wordUsersMapper;
	@Autowired
	WordResourceMapper wordResourceMapper;
	@Autowired
	WordUserMoneyMapper wordUserMoneyMapper;
	@Autowired
	WordUserMoneyLogMapper wordUserMoneyLogMapper;
	@Value("${searchByJsonStr}")
	private String searchByJsonStr;
	@Value("${recommendLabel}")
	private String recommendLabel;
	@Value("${nginxPic}")
	private String nginxPic;
	private static Logger logger = Logger.getLogger(UserCenterService.class);
	
	//我的资源库页面单页显示多少条数据
	private static int RESOURCE_PAGESIZE = 10;
	//购买记录页面单页显示多少条数据
	private static int PURCHASE_PAGESIZE = 10;
	//资金明细页面单页显示多少条数据
	private static int CAPITAL_PAGESIZE = 10;

	
	/**
	 * 分页查询我的期刊
	 * @param pageSize
	 * @param pageNo
	 * @param status
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getMyJournal(Integer pageSize, Integer pageNo,
			String status, Integer userId) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		if (pageSize == null || pageSize < 1) {
			pageSize = 10;
		}
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		WordJournalExample example = new WordJournalExample();
		example.setOrderByClause("create_time desc");
		example.setLimitStart((pageNo - 1) * pageSize);
		example.setLimitEnd(pageSize);
		if (status == null || status.equals("")) {
			example.or().andUserIdEqualTo(userId);
		} else {
			example.or().andUserIdEqualTo(userId).andPassTypeEqualTo(status);
		}
		map.put("list", wordJournalMapper.selectByExample(example));
		int count = wordJournalMapper.countByExample(example);
		map.put("count", count);
		map.put("pageSize", pageSize);
		map.put("pageNo", pageNo);
		map.put("status", status);
		map.put("pageCount", ((count % pageSize == 0) ? (count / pageSize)
				: ((count / pageSize) + 1)));
		return map;
	}
	/**
	 * 分页查询我的文档
	 * 
	 * @param pageSize
	 * @param pageNo
	 * @param status
	 * @param userId
	 * @param type
	 * @return
	 */
	public Map<String, Object> getMyArticle(Integer pageSize, Integer pageNo,
			String status, Integer userId, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (pageSize == null || pageSize < 1) {
			pageSize = 10;
		}
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		ArticleExample example = new ArticleExample();
		example.setOrderByClause("update_time desc");
		example.setLimitStart((pageNo - 1) * pageSize);
		example.setLimitEnd(pageSize);
		if (status == null || status.equals("")) {
			example.or().andUserIdEqualTo(userId).andArticleTypeEqualTo(type)
					.andPassTypeNotEqualTo("").andPassTypeIsNotNull().andArticleSaveEqualTo("write");
		} else {
			example.or().andUserIdEqualTo(userId).andPassTypeEqualTo(status)
					.andArticleTypeEqualTo(type).andArticleSaveEqualTo("write");
		}
		map.put("list", articleMapper.selectByExample(example));
		int count = articleMapper.countByExample(example);
		map.put("count", count);
		map.put("pageSize", pageSize);
		map.put("pageNo", pageNo);
		map.put("status", status);
		map.put("pageCount", ((count % pageSize == 0) ? (count / pageSize)
				: ((count / pageSize) + 1)));
		return map;
	}

	/**
	 * 文档，模板，新闻，插件总数
	 * 
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getAllCounts(Integer userId) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		//期刊
		WordJournalExample example = new WordJournalExample();
		example.or().andUserIdEqualTo(userId);
		map.put("journalCount", wordJournalMapper.countByExample(example));
		// 文档
		ArticleExample exampleArticle = new ArticleExample();
		exampleArticle.or().andUserIdEqualTo(userId)
				.andArticleTypeEqualTo("document").andPassTypeNotEqualTo("")
				.andPassTypeIsNotNull().andArticleSaveEqualTo("write");
		map.put("articleCount", articleMapper.countByExample(exampleArticle));
		// 模板
		ArticleExample exampleTemplace = new ArticleExample();
		exampleTemplace.or().andUserIdEqualTo(userId)
				.andArticleTypeEqualTo("template").andPassTypeNotEqualTo("")
				.andPassTypeIsNotNull().andArticleSaveEqualTo("write");
		map.put("templateCount", articleMapper.countByExample(exampleTemplace));
		// 新闻
		ContentExample exampleContent = new ContentExample();
		exampleContent.or().andUserIdEqualTo(userId);
		map.put("contentCount", contentMapper.countByExample(exampleContent));
		// 插件
		PluginUserExample pluginContent = new PluginUserExample();
		pluginContent.or().andUserIdEqualTo(userId);
		map.put("pluginCount", pluginUserMapper.countByExample(pluginContent));
		//推荐
		Map<String, Object> m = this.getRecommend(10,1);
		map.put("recommendCount", m!=null?m.get("count"):0);
		//上传 
		ArticleExample exampleUpload = new ArticleExample();
		exampleUpload.or().andUserIdEqualTo(userId)
				.andArticleTypeEqualTo("document").andPassTypeNotEqualTo("")
				.andPassTypeIsNotNull().andArticleSaveEqualTo("upload");
		map.put("uploadCount", articleMapper.countByExample(exampleUpload));
		//资源库
		WordResourceExample wordResourceExample=new WordResourceExample();
		wordResourceExample.or().andUserIdEqualTo(userId);
		map.put("resourceCount", wordResourceMapper.countByExample(wordResourceExample));
		
		//收藏
		NewsUserFavoriteExample newsUserFavoriteExample = new NewsUserFavoriteExample();
		newsUserFavoriteExample.or().andUserIdEqualTo(userId).andIsdelEqualTo(0);
		map.put("myfavoritecount",
				newsUserFavoriteMapper.countByExample(newsUserFavoriteExample));
		return map;
	}

	/**
	 * 删除文档
	 * 
	 * @param articleIds
	 * @param userId
	 */
	@Transactional
	public void delArticle(String articleIds, Integer userId) {
		// TODO Auto-generated method stub
		if (articleIds != null && !articleIds.equals("")) {
			String[] ids = articleIds.split("_");
			for (int i = 0; i < ids.length; i++) {
				Integer articleId = Integer.valueOf(ids[i]);
				Article article = articleMapper.selectByPrimaryKey(articleId);
				// 通过的转移所有人到系统管理员
				 if (article.getUserId().intValue() == userId.intValue()
							&& "PASSED".equals(article.getPassType())) {
						article.setUserId(1);
						articleMapper.updateByPrimaryKey(article);
				}else if (article.getUserId().intValue() == userId.intValue()) {
					// 只能删自己的 审核通过的不能删除
					// 删除内容
					ContentWholeExample cwe = new ContentWholeExample();
					cwe.or().andArticleIdEqualTo(articleId);
					contentWholeMapper.deleteByExample(cwe);
					// 删除封面
					HomepageExample he = new HomepageExample();
					he.or().andArticleIdEqualTo(articleId);
					homepageMapper.deleteByExample(he);
					// 删除属性
					ArticlePropertyExample ape = new ArticlePropertyExample();
					ape.or().andArticleIdEqualTo(articleId);
					articlePropertyMapper.deleteByExample(ape);
					// 删除文档
					articleMapper.deleteByPrimaryKey(article.getArticleId());
				} 
			}
		}
	}

	/**
	 * 我的新闻
	 * 
	 * @param pageSize
	 * @param pageNo
	 * @param status
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getMyContent(Integer pageSize, Integer pageNo,
			String status, Integer userId) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		if (pageSize == null || pageSize < 1) {
			pageSize = 10;
		}
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		ContentExample example = new ContentExample();
		example.setOrderByClause("update_time desc");
		example.setLimitStart((pageNo - 1) * pageSize);
		example.setLimitEnd(pageSize);
		if (status != null && !status.equals("")) {
			example.or().andPassTypeEqualTo(status).andUserIdEqualTo(userId);
		} else {
			example.or().andUserIdEqualTo(userId);
		}
		map.put("list", contentMapper.selectByExample(example));
		int count = contentMapper.countByExample(example);
		map.put("count", count);
		map.put("pageSize", pageSize);
		map.put("pageNo", pageNo);
		map.put("status", status);
		map.put("pageCount", ((count % pageSize == 0) ? (count / pageSize)
				: ((count / pageSize) + 1)));
		return map;
	}
	
	/**
	 * 新闻详情
	 * @param newsid
	 */
	public ContentWithBLOBs getContent(String newsid) {
		// TODO Auto-generated method stub
		return contentMapper.selectByPrimaryKey(Integer.valueOf(newsid));
	}

	/**
	 * 删除新闻
	 * 
	 * @param contentIds
	 * @param userId
	 */
	public void delContent(String contentIds, Integer userId) {
		// TODO Auto-generated method stub
		if (contentIds != null && !contentIds.equals("")) {
			String[] ids = contentIds.split("_");
			for (int i = 0; i < ids.length; i++) {
				Integer contentId = Integer.valueOf(ids[i]);
				ContentWithBLOBs content = contentMapper
						.selectByPrimaryKey(contentId);
				if (content.getUserId().intValue() == userId.intValue()) {
					contentMapper.deleteByPrimaryKey(contentId);
				}
			}
		}
	}

	/**
	 * 分页查询我的插件
	 * 
	 * @param pageSize
	 * @param pageNo
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getMyPlugin(Integer pageSize, Integer pageNo,
			Integer userId) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		if (pageSize == null || pageSize < 1) {
			pageSize = 10;
		}
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		PluginUserExample example = new PluginUserExample();
		example.setOrderByClause("insert_time desc");
		example.setLimitStart((pageNo - 1) * pageSize);
		example.setLimitEnd(pageSize);
		example.or().andUserIdEqualTo(userId);
		map.put("list", pluginUserMapper.selectByExample(example));
		int count = pluginUserMapper.countByExample(example);
		map.put("count", count);
		map.put("pageSize", pageSize);
		map.put("pageNo", pageNo);
		map.put("pageCount", ((count % pageSize == 0) ? (count / pageSize)
				: ((count / pageSize) + 1)));
		return map;
	}

	/**
	 * 删除插件
	 * 
	 * @param pluginUserIds
	 * @param userId
	 */
	public void delPlugin(String pluginUserIds, Integer userId) {
		// TODO Auto-generated method stub
		if (pluginUserIds != null && !pluginUserIds.equals("")) {
			String[] ids = pluginUserIds.split("_");
			for (int i = 0; i < ids.length; i++) {
				Integer pluginUserId = Integer.valueOf(ids[i]);
				PluginUser pluginUser = pluginUserMapper
						.selectByPrimaryKey(pluginUserId);
				if (pluginUser.getUserId().intValue() == userId.intValue()) {
					pluginUserMapper.deleteByPrimaryKey(pluginUserId);
				}
			}
		}
	}

	/**
	 * 修改资料
	 * 
	 * @param user
	 */
	public String updateWordUsersInfo(WordUsers user) {
		// TODO Auto-generated method stub
		if (user.getUserPhone() != null && !user.getUserPhone().equals("")) {
			WordUsersExample example = new WordUsersExample();
			example.or().andUserIdNotEqualTo(user.getUserId())
					.andUserPhoneEqualTo(user.getUserPhone());
			List<WordUsers> list = wordUsersMapper.selectByExample(example);
			if (!list.isEmpty()) {
				return "手机号码重复";
			}
		}
		WordUsersExample example1 = new WordUsersExample();
		example1.or().andUserIdNotEqualTo(user.getUserId())
				.andUserNameEqualTo(user.getUserName());
		List<WordUsers> list1 = wordUsersMapper.selectByExample(example1);
		if (!list1.isEmpty()) {
			return "昵称重复";
		}
		return String.valueOf(wordUsersMapper.updateByPrimaryKey(user));
	}

	/**
	 * 修改密码
	 * 
	 * @param user
	 */
	public void updateWordUsersPass(WordUsers user) {
		// TODO Auto-generated method stub
		wordUsersMapper.updateByPrimaryKey(user);
	}

	public Map<String, Object> getRecommend(Integer pageSize, Integer pageNo) {
		// TODO Auto-generated method stub
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = 10;
		}
		Map<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		if (pageNo == 1) {
			map.put("from", 0 + "");
		} else {
			map.put("from", ((pageNo - 1) * pageSize) + "");
		}
		DocumentSearchCondition searchCondition = new DocumentSearchCondition();
		List<String> strs = new ArrayList<String>();
		strs.add(recommendLabel);
		searchCondition.setShouldArticleLabels(strs);
		searchCondition.setArticleType("template");
		map.put("jsonStr", gson.toJson(searchCondition));
		map.put("pageSize", pageSize + "");
		map.put("fullContent", "false");
		map.put("orderByField", "passTime");
		map.put("order", "DESC");
		long start = System.currentTimeMillis();
		logger.warn("推荐模板开始" + System.currentTimeMillis());
		String json = getHtml(map, searchByJsonStr);
		long end = System.currentTimeMillis();
		logger.warn("推荐模板结束" + end + " 共耗时:" + (end - start) + "ms！");
		Map<String, Object> page = new HashMap<String, Object>();
		List<Document> list = new ArrayList<Document>();
		long count = 0;
		if(json!=null && !json.equals("")){
			SearchDocumentData sd = gson.fromJson(json, SearchDocumentData.class);
			count = sd.getCount();
			list = sd.getData();
		}
		page.put("list",list );
		page.put("count", count);
		page.put("pageSize", pageSize);
		page.put("pageNo", pageNo);
		page.put("pageCount", ((count % pageSize == 0) ? (count / pageSize)
				: ((count / pageSize) + 1)));
		return page;
	}

	private String getHtml(Map<String, String> params, String url) {

		PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
		BasicCookieStore cookieStore = new BasicCookieStore();
		HttpClientBuilder httpClientBuilder = new HttpClientBuilder(false,
				poolingHttpClientConnectionManager, cookieStore);
		CloseableHttpClient httpClient = httpClientBuilder.getHttpClient();
		Fetcher fetcher1 = new Fetcher(cookieStore, httpClient);
		String html = "";
		try {
			logger.warn("与ES交互参数" + params);
			logger.warn("与ES交互URL" + url);
			html = fetcher1.post(url, params, null, "utf-8");
		} catch (Exception e) {
			logger.error("与ES交互异常" + e);
		}
		return html;
	}

	//我的上传
	public Map<String, Object> getUpload(Integer pageSize, Integer pageNo,Integer userId,String status) {
		// TODO Auto-generated method stub
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = 10;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		ArticleExample example = new ArticleExample();
		example.setOrderByClause("insert_time desc");
		example.setLimitStart((pageNo - 1) * pageSize);
		example.setLimitEnd(pageSize);
		if (status == null || status.equals("")) {
			example.or().andUserIdEqualTo(userId).andArticleSaveEqualTo("upload");
		} else {
			example.or().andUserIdEqualTo(userId).andArticleSaveEqualTo("upload").andPassTypeEqualTo(status);
		}
		int count = articleMapper.countByExample(example);
		map.put("list", articleMapper.selectByExample(example));
		map.put("count", count);
		map.put("status",status);
		map.put("pageSize", pageSize);
		map.put("pageNo", pageNo);
		map.put("pageCount", ((count % pageSize == 0) ? (count / pageSize)
				: ((count / pageSize) + 1)));
		return map;
	}
	/**
	 * 新建期刊
	 * @param wordJournal
	 */
	public void insertOrUpdateJournal(WordJournal wordJournal) {
		// TODO Auto-generated method stub
		Date now = new Date();
		wordJournal.setPassType("SUBMITTED");
		//如果是新增
		if(wordJournal.getId()==null || wordJournal.getId().intValue()==0){
			wordJournal.setCreateTime(now);
		}
		wordJournal.setSubmitTime(now);
		if(wordJournal.getId()==null || wordJournal.getId().intValue()==0){
			wordJournalMapper.insertSelective(wordJournal);
		}else{
			WordJournal j = wordJournalMapper.selectByPrimaryKey(wordJournal.getId());
			//没有期刊 或者别人的期刊不让修改
			if(!(j==null || j.getUserId().intValue()!=wordJournal.getUserId().intValue())){
				if(wordJournal.getCover().equals("")){
					wordJournal.setCover(null);
				}
				wordJournalMapper.updateByPrimaryKeySelective(wordJournal);
			}
		}
	}
	
	/**
	 * 删除期刊
	 * @param journalIds
	 * @param userId
	 */
	@Transactional
	public void delJournal(String journalIds, Integer userId) {
		// TODO Auto-generated method stub
		if (journalIds != null && !journalIds.equals("")) {
			String[] ids = journalIds.split("_");
			for (int i = 0; i < ids.length; i++) {
				Integer journalId = Integer.valueOf(ids[i]);
				WordJournal wordJournal = wordJournalMapper.selectByPrimaryKey(journalId);
				// 通过的转移所有人到系统管理员
				 if (wordJournal.getUserId().intValue() == userId.intValue()
							&& "PASSED".equals(wordJournal.getPassType())) {
					 	wordJournal.setUserId(1);
					 	wordJournalMapper.updateByPrimaryKey(wordJournal);
				}else if (wordJournal.getUserId().intValue() == userId.intValue()) {
					// 只能删自己的 审核通过的不能删除
					// 删除文档
					wordJournalMapper.deleteByPrimaryKey(journalId);
				} 
			}
		}
	}
	/**
	 * 期刊详情
	 * @param journalId
	 */
	public WordJournal getJournalDetail(String journalId) {
		// TODO Auto-generated method stub
		return wordJournalMapper.selectByPrimaryKey(Integer.valueOf(journalId));
	}
	/**
	 * 修改期刊
	 * @param wordJournal
	 */
	public void editJournal(WordJournal wordJournal) {
		// TODO Auto-generated method stub
		wordJournalMapper.updateByPrimaryKeySelective(wordJournal);
	}
	
	/**
	 * 搜索期刊下文档
	 * @param wordJournal
	 */
	public Map<String,Object> searchByJournal(String keyword,String journalId,Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
			if (pageNo == null || pageNo < 1) {
				pageNo = 1;
			}
			if (pageSize == null || pageSize < 1) {
				pageSize = 10;
			}
			DocumentSearchCondition contion = new DocumentSearchCondition();
			List<Integer>journalIds = new ArrayList<Integer>();
			journalIds.add(Integer.parseInt(journalId));
			contion.setJournalIds(journalIds);
			if(keyword!=null && !keyword.trim().equals("")){
				List<String>keywords = new ArrayList<String>();
				keywords.add(keyword);
				contion.setMustWordsOfArticleName(keywords);
			}
			Map<String, String> map = new HashMap<String, String>();
			Gson gson = new Gson();
			contion.setArticleType("document");
			map.put("jsonStr", gson.toJson(contion));
			map.put("from", Integer.toString((pageNo - 1) * pageSize));
			map.put("pageSize", Integer.toString(pageSize));
			long start = System.currentTimeMillis();
			logger.warn("搜索期刊下文档" + System.currentTimeMillis());
			String json = getHtml(map, searchByJsonStr);
			long end = System.currentTimeMillis();
			logger.warn("搜索期刊下文档" + end + " 共耗时:" + (end - start) + "ms！");
			Map<String, Object> page = new HashMap<String, Object>();
			List<Document> rs=new ArrayList<Document>();  
			long count = 0;
			if(json!=null && !json.equals("")){
				ResponseParam rp = gson.fromJson(json, ResponseParam.class);
				count = rp.getCount();
		        rs = rp.getData();
		        if(rs!=null && !rs.isEmpty()){
		        	for(Document d:rs){
		        		WordUsersExample example = new WordUsersExample();
		        		example.or().andUserIdEqualTo(d.getUserId());
		        		List<WordUsers>  users = wordUsersMapper.selectByExample(example);
		        		if(users!=null && !users.isEmpty()){
		        			d.setArticleUser(users.get(0).getUserName());
		        		}
		        	}
		        }
			}
			page.put("list",rs );
			page.put("count", count);
			page.put("pageSize", pageSize);
			page.put("pageNo", pageNo);
			page.put("pageCount", ((count % pageSize == 0) ? (count / pageSize)
					: ((count / pageSize) + 1)));
			return page;
	}
	
	/**
	 * 上传报告
	 * @param article
	 */
	@Transactional
	public int  importWord(Article article,String wordPath) {
		// TODO Auto-generated method stub
		ArticleExample articleExmaple = new ArticleExample();
		com.database.bean.ArticleExample.Criteria articleCr = articleExmaple.createCriteria();
		articleCr.andArticleNameEqualTo(article.getArticleName()).andUserIdEqualTo(article.getUserId()).andArticleTypeEqualTo("document");
		List<Article> articlelist = articleMapper.selectByExample(articleExmaple);
		if(articlelist.size()>0){//文件大于0
			article.setArticleName(article.getArticleName()+"_"+System.currentTimeMillis());
		}
		articleMapper.insertSelective(article);
		 
		if(article.getArticleFormat().equals("doc") || article.getArticleFormat().equals("docx")){
			//设置内容
			GetWholeContent getWholeContent = new GetWholeContent(wordPath,nginxPic+"upload");
			long  start = System.currentTimeMillis();
			String nodeContent = getWholeContent.getContent();
			long  end = System.currentTimeMillis();
			System.out.println("处理文档耗时"+(end-start)+"ms");
			ContentWhole whole = new ContentWhole();
			whole.setArticleId(article.getArticleId());
			whole.setNodeContent(nodeContent);
			whole.setNodeId(0);
			contentWholeMapper.saveContent(whole);
		}
		 
		return article.getArticleId();
	}
	
	/**
	 * 校验期刊重名
	 * @param name
	 * @return
	 */
	public List<WordJournal> validJournalName(String name) {
		// TODO Auto-generated method stub
		WordJournalExample example = new WordJournalExample();
		example.or().andNameEqualTo(name).andPassTypeEqualTo("PASSED");
		example.or().andNameEqualTo(name).andPassTypeEqualTo("SUBMITTED");
		return wordJournalMapper.selectByExample(example);
	}
	
	/**
	 * 分页检索查询我的资源库 shiruojiang 2015/12/22
	 * @param pageSize
	 * @param pageNo
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getMyResource(Integer pageSize, Integer pageNo,
			String keyword,Integer userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		//期刊list
	//	List<WordJournal> list=new ArrayList<WordJournal>();
		List<WordResource> list=null;
		//我的资源数量
		int count = 0;
		 
//		List<WordResource> wrlist=null;
		if (pageSize == null || pageSize < 1) {
			pageSize = RESOURCE_PAGESIZE;
		}
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		WordResourceExample example=new WordResourceExample();
		example.setOrderByClause("create_time desc");
		example.setLimitStart((pageNo - 1) * pageSize);
		example.setLimitEnd(pageSize);
		example.or().andUserIdEqualTo(userId);
	//	example.or().andUserIdEqualTo(userId).andResourceTypeEqualTo("journal");
		
		
		list=wordResourceMapper.selectByExample(example);
		
		
		
		
		count=wordResourceMapper.countByExample(example);
		//根据资源库的期刊id查询期刊
		/*	WordJournalExample jExample=new WordJournalExample();
		//期刊id的list
		List<Integer> ids=new ArrayList<Integer>(); 
		if(wrlist.size()>0){
			for(WordResource wr:wrlist){
				int journalId=wr.getJournalId();
				ids.add(journalId);
			}
			jExample.or().andIdIn(ids);
			list=wordJournalMapper.selectByExample(jExample);	
		}	*/
		if(keyword!=null&&!"".equals(keyword)){
			mateWordResource(list, keyword);
		}
		
		for(WordResource wr:list){	
			getJourAndArtName(wr);
			wr.setEndTime(DateTool.getOneDay(wr.getCreateTime(), 365));
			if("journal".equals(wr.getResourceType())){
				wr.setResourceType("期刊");
			}else if("journalarticle".equals(wr.getResourceType())){
				wr.setResourceType("期刊文档");
			}else if("article".equals(wr.getResourceType())){
				wr.setResourceType("非期刊文档");
			}
		}
		
		
		
		map.put("list", list);
		map.put("count", count);
		map.put("pageSize", pageSize);
		map.put("pageNo", pageNo);
		map.put("pageCount", ((count % pageSize == 0) ? (count / pageSize)
				: ((count / pageSize) + 1)));
		return map;
	}
	/**
	 * 分页查询购买记录 shiruojiang 2015/12/22
	 * @param enddate 
	 * @param startdate 
	 * @param pageSize
	 * @param pageNo
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getPurchase(String startdate, String enddate, Integer pageSize, Integer pageNo,
			Integer userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		//我的资源数量
		//如果不設置結束時間，那么结束时间就是当前时间
		Date start=null;
		Date end=new Date();
		if(null!=startdate&&!"".equals(startdate)){
			start=DateUtil.convertStringToDateTime(startdate+" 00:00:00");
		}
		if(null!=enddate&&!"".equals(enddate)){
			end=DateUtil.convertStringToDateTime(enddate+" 23:59:59");
		}
		
		int count = 0;
		List<WordResource> list=null;
		if (pageSize == null || pageSize < 1) {
			pageSize = PURCHASE_PAGESIZE;
		}
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		WordResourceExample example=new WordResourceExample();
		example.setOrderByClause("create_time desc");
		example.setLimitStart((pageNo - 1) * pageSize);
		example.setLimitEnd(pageSize);
		//起始时间不为空，就执行between查询方法
		if(start!=null){
			example.or().andUserIdEqualTo(userId).andCreateTimeBetween(start, end);
		}else{//起始时间为空，则查询结束时间之前的时间
			example.or().andUserIdEqualTo(userId).andCreateTimeLessThanOrEqualTo(end);
		}
		
		list=wordResourceMapper.selectByExample(example);
		count=wordResourceMapper.countByExample(example);
		//查询期刊名，期刊封面，文档名
	/*	
		WordJournal j=new WordJournal();
		Article a=new Article();*/
		
		//前台格式转换	
		for(WordResource wr:list){	
			getJourAndArtName(wr);
			wr.setPriceVo(CommonUtils.toIntegerString(wr.getPrice()));
			if("after".equals(wr.getBuyType())){
				wr.setBuyType("线   下");
			}else if("before".equals(wr.getBuyType())){
				wr.setBuyType("线   上");
			}
			if("journal".equals(wr.getResourceType())){
				wr.setResourceType("期刊");
			}else if("journalarticle".equals(wr.getResourceType())){
				wr.setResourceType("期刊文档");
			}else if("article".equals(wr.getResourceType())){
				wr.setResourceType("非期刊文档");
			}
		}	
		map.put("list", list);
		map.put("count", count);
		map.put("pageSize", pageSize);
		map.put("pageNo", pageNo);
		map.put("pageCount", ((count % pageSize == 0) ? (count / pageSize)
				: ((count / pageSize) + 1)));
		return map;
	
	}
	/**
	 * 分页查询资金明细 shiruojiang 2015/12/22
	 * @param enddate 起始时间
	 * @param startdate 结束时间
	 * @param pageSize
	 * @param pageNo
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getCapital(String startdate, String enddate, Integer pageSize, Integer pageNo,
			Integer userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		Double money=(double) 0;
		//如果不設置結束時間，那么结束时间就是当前时间
		//先转换时间格式，String到Date
		Date start=null;
		Date end=new Date();
		if(null!=startdate&&!"".equals(startdate)){
			start=DateUtil.convertStringToDateTime(startdate+" 00:00:00");
		}
		if(null!=enddate&&!"".equals(enddate)){
			end=DateUtil.convertStringToDateTime(enddate+" 23:59:59");
		}
		
		List<WordUserMoneyLog> list = null;
		if (pageSize == null || pageSize < 1) {
			pageSize = CAPITAL_PAGESIZE;
		}
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		//查询资金记录表
		WordUserMoneyLogExample example = new WordUserMoneyLogExample();
		example.setOrderByClause("create_time desc");
		example.setLimitStart((pageNo - 1) * pageSize);
		example.setLimitEnd(pageSize);
		//起始时间不为空，就执行between查询方法
		if(start!=null){
			example.or().andUserIdEqualTo(userId).andCreateTimeBetween(start, end);
		}else{//起始时间为空，则查询结束时间之前的时间
			example.or().andUserIdEqualTo(userId).andCreateTimeLessThanOrEqualTo(end);
		}	
		list = wordUserMoneyLogMapper.selectByExample(example);
		//根据资源id查询资源名
		
		WordResource wr=new WordResource();
		//转换前台格式
		for(WordUserMoneyLog wuml:list){
			wr=wordResourceMapper.selectByPrimaryKey(wuml.getResourceId());	
			getJourAndArtName(wr);
			//后台取来的double类型转换成前台所需要的String类型
			wuml.setAddMoneyVo(CommonUtils.toIntegerString(wuml.getAddMoney()));
			wuml.setMoneyVo(CommonUtils.toIntegerString(wuml.getMoney()));
			
			if("journal".equals(wuml.getResourceType())){
				wuml.setResourceName(wr.getJournalName());
				wuml.setResourceType("期刊");
			}
			if("journalarticle".equals(wuml.getResourceType())){
				wuml.setResourceName(wr.getArticleName());
				wuml.setResourceType("期刊文档");
			}
			if("article".equals(wuml.getResourceType())){
				wuml.setResourceName(wr.getArticleName());
				wuml.setResourceType("非期刊文档");
			}
			
			
			if("add".equals(wuml.getType())){
				wuml.setType("入账");
			}
			if("del".equals(wuml.getType())){
				wuml.setType("支付");
			}
			if("after".equals(wuml.getBuyType())){
				wuml.setBuyType("线上");
			}else if("before".equals(wuml.getBuyType())){
				wuml.setBuyType("线下");
			}
			if("journal".equals(wuml.getResourceType())){
				wuml.setResourceType("期刊");
			}else if("journalarticle".equals(wuml.getResourceType())){
				wuml.setResourceType("期刊文档");
			}else if("article".equals(wuml.getResourceType())){
				wuml.setResourceType("非期刊文档");
			}
		}
		
		
		
		count= wordUserMoneyLogMapper.countByExample(example);
		
		//查询资金表,查出剩余金额
		WordUserMoneyExample moneyexample = new WordUserMoneyExample();
		moneyexample.or().andUserIdEqualTo(userId);
		List<WordUserMoney> moneylist = wordUserMoneyMapper.selectByExample(moneyexample);
		if(moneylist.size()>0){
			WordUserMoney wum=moneylist.get(0);
			money = wum.getMoney();
		}
		map.put("list", list);
		map.put("count", count);
		map.put("pageSize", pageSize);
		map.put("pageNo", pageNo);
		map.put("pageCount", ((count % pageSize == 0) ? (count / pageSize)
				: ((count / pageSize) + 1)));
		map.put("money", money);
		
		return map;
	}
	
	//根据获取期刊名称和文档名称
	private void getJourAndArtName(WordResource wr){
		WordJournal j=new WordJournal();
		Article a=new Article();		
		if(wr!=null&&!"".equals(wr)){
			if(wr.getJournalId()!=null){
				j=wordJournalMapper.selectByPrimaryKey(wr.getJournalId());
				if(j!=null){
					wr.setJournalName(j.getName());
					wr.setCover(j.getCover());
					wr.setType(j.getType());
					wr.setJournalType(j.getType());		
					wr.setUpdateTime(j.getUpdateTime());
				}
			}
			if(wr.getArticleId()!=null){
				a=articleMapper.selectByPrimaryKey(wr.getArticleId());
				if(a!=null){
					wr.setArticleName(a.getArticleName());
					wr.setArticleFormat(a.getArticleFormat());
					wr.setUpdateTime(a.getUpdateTime());
				}
			}
		}
	}
	
	//筛选期刊名称或者文档名称包含keyword关键字的资源list
	private void mateWordResource(List<WordResource> list,String keyword){
		WordJournal j=new WordJournal();
		Article a=new Article();
		boolean jour=false;
		boolean art=false;
		Iterator<WordResource> iterator=list.iterator();
		while(iterator.hasNext()){
			WordResource wr=iterator.next();
			if(wr!=null&&!"".equals(wr)){
				if(wr.getJournalId()!=null){
					j=wordJournalMapper.selectByPrimaryKey(wr.getJournalId());	
					if(j!=null){
						jour=CommonUtils.mate(j.getName(), keyword);
					}
				}
				if(wr.getArticleId()!=null){
					a=articleMapper.selectByPrimaryKey(wr.getArticleId());
					if(a!=null){					
						art=CommonUtils.mate(a.getArticleName(), keyword);
					}
				}				
				if(jour==false&&art==false){
					iterator.remove();
				}
			}
		}
		
	}
	
	
	
	
	/**
	 * 根据userId获取用户帐号剩余创享币
	 * @param userId
	 * @return WordUserMoney
	 */
	public String getUserMoneyByUserId(int userId){
		
		String money = "0";
		WordUserMoneyExample example = new WordUserMoneyExample();
		example.or().andUserIdEqualTo(userId);
		List<WordUserMoney> list = wordUserMoneyMapper.selectByExample(example);
		if (list != null && list.size() == 1){
			WordUserMoney wordUserMoney = list.get(0);
			//money = CommonUtils.toIntegerString(wordUserMoney.getMoney());
			money = wordUserMoney.getMoney().toString();
		}
		return money;
	}
}
