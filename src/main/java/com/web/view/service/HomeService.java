package com.web.view.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.Logger;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.database.bean.Article;
import com.database.bean.ArticleExample;
import com.database.bean.KnowNopassReason;
import com.database.bean.KnowNopassReasonExample;
import com.database.bean.NewsMyPlate;
import com.database.bean.NewsMyPlateExample;
import com.database.bean.WordArticleCount;
import com.database.bean.WordArticleCountExample;
import com.database.bean.WordArticleHtml;
import com.database.bean.WordArticleHtmlExample;
import com.database.bean.WordColumn;
import com.database.bean.WordColumnContact;
import com.database.bean.WordColumnContactExample;
import com.database.bean.WordColumnExample;
import com.database.bean.WordColumnGroup;
import com.database.bean.WordColumnGroupExample;
import com.database.bean.WordJournal;
import com.database.bean.WordPlate;
import com.database.bean.WordPlateExample;
import com.database.bean.WordResource;
import com.database.bean.WordResourceExample;
import com.database.bean.WordShoppingCart;
import com.database.bean.WordShoppingCartExample;
import com.database.bean.WordUserMoney;
import com.database.bean.WordUserMoneyExample;
import com.database.bean.WordUsers;
import com.database.bean.WordUsersExample;
import com.database.bean.WordUsersRoles;
import com.database.dao.ArticleMapper;
import com.database.dao.KnowNopassReasonMapper;
import com.database.dao.NewsColumnMapper;
import com.database.dao.NewsMyPlateMapper;
import com.database.dao.WordArticleCountMapper;
import com.database.dao.WordArticleHtmlMapper;
import com.database.dao.WordColumnContactMapper;
import com.database.dao.WordColumnGroupMapper;
import com.database.dao.WordColumnMapper;
import com.database.dao.WordJournalMapper;
import com.database.dao.WordPlateMapper;
import com.database.dao.WordResourceMapper;
import com.database.dao.WordShoppingCartMapper;
import com.database.dao.WordUserMoneyMapper;
import com.database.dao.WordUsersMapper;
import com.database.dao.WordUsersRolesMapper;
import com.export.Function;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.util.CommonUtils;
import com.util.DateTool;
import com.util.DateUtil;
import com.web.bean.Condition;
import com.web.bean.ContionParam;
import com.web.bean.Document;
import com.web.bean.DocumentSearchCondition;
import com.web.bean.EsJournal;
import com.web.bean.GroupParam;
import com.web.bean.Journal;
import com.web.bean.JournalSearchCondition;
import com.web.bean.NewsSearchCondition;
import com.web.bean.ResponseParam;
import com.web.bean.SearchDocumentData;
import com.web.bean.SearchParam;
import com.web.document.bean.News;
import com.web.document.bean.NewsCrawls;
import com.web.utils.Fetcher;
import com.web.utils.HttpClientBuilder;
import com.web.view.bean.JournalInfoBean;

@Service
public class HomeService {
	@Autowired
	WordUsersMapper wordUsersMapper;
	@Autowired
	WordPlateMapper wordPlateMapper;
	@Autowired
	WordUsersRolesMapper wordUsersRolesMapper;
	@Autowired
	ArticleMapper articleMapper;
	@Autowired
	KnowNopassReasonMapper knowNopassReasonMapper;
	@Autowired
	WordColumnMapper wordColumnMapper;
	@Autowired
	WordColumnGroupMapper wordColumnGroupMapper;
	@Autowired
	WordColumnContactMapper wordColumnContactMapper;
	@Autowired
	WordArticleCountMapper wordArticleCountMapper;
	@Autowired
	WordJournalMapper wordJournalMapper;
	@Autowired
	WordUserMoneyMapper wordUserMoneyMapper;
	@Autowired
	WordResourceMapper wordResourceMapper;
	@Autowired
	WordShoppingCartMapper wordShoppingCartMapper;
	@Autowired
	WordArticleHtmlMapper wordArticleHtmlMapper;
	@Autowired
	NewsColumnMapper newsColumnMapper;
	@Autowired
	NewsMyPlateMapper newsMyPlateMapper;
	
	
	@Value("${searchJournalByQueryString}")
	private String searchJournalByQueryString;
	@Value("${searchByGroup}")
	private String searchByGroup;
	@Value("${searchMoreByJournal}")
	private String searchMoreByJournal;
	@Value("${searchByMultiJsonStr}")
	private String searchByMultiJsonStr;
	@Value("${moreLikeSearch}")
	private String moreLikeSearch;
	@Value("${newsByNewsId}")
	private String newsByNewsId;
	@Value("${newsByJson}")
	private String newsByJson;
	@Value("${searchMoreLikeByNewsId}")
	private String searchMoreLikeByNewsId;
	@Value("${searchJournalByColumn}")
	private String searchJournalByColumn;
	@Value("${searchJournalByUserId}")
	private String searchJournalByUserId;

	
	private static Logger logger = Logger.getLogger(HomeService.class);
	public static final int day = 1;//日刊
	public static final int week = 2;//周刊
	public static final int halfMonths = 3;//半月刊
	public static final int months = 4;//月刊
	public static final int twoMonths = 5;//双月刊
	public static final int season = 6;//季刊
	public static final int halfYear = 7;//半年刊
	public static final int year = 8;//年刊
	
	/**
	 * 根据账号或手机号查询用户
	 * 
	 * @param account
	 * @return
	 */
	public WordUsers login(String account) {
		WordUsersExample example = new WordUsersExample();
		example.or().andUserAccountEqualTo(account);
		example.or().andUserPhoneEqualTo(account);
		List<WordUsers> list = wordUsersMapper.selectByExample(example);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 更新用户sessionId
	 * @param WordUsers
	 */
	public void updateSession(WordUsers user) {
		wordUsersMapper.updateByPrimaryKeySelective(user);
	}
	
	/**
	 * 注册
	 * 
	 * @param user
	 */
	@Transactional
	public void regist(WordUsers user) {
		// TODO Auto-generated method stub
		wordUsersMapper.insert(user);
		WordUsersRoles wur = new WordUsersRoles();
		wur.setRoleId(3);
		wur.setUserId(user.getUserId());
		wordUsersRolesMapper.insert(wur);
		
		//复制管理员栏目给新注册用户
		//文档栏目
		WordPlateExample example = new WordPlateExample();
		example.setLimitStart(0);
		example.setLimitEnd(8);
		example.setOrderByClause("order_id");
		example.or().andPidEqualTo(0).andUserIdEqualTo(1);
		List<WordPlate> list = wordPlateMapper.selectByExample(example);
		for(WordPlate p:list){
			int pid = p.getPlateId();
			p.setUserId(user.getUserId());
			p.setPlateId(null);
			wordPlateMapper.insertSelective(p);
			WordPlateExample e = new WordPlateExample();
			e.setLimitStart(0);
			e.setLimitEnd(8);
			e.setOrderByClause("order_id");
			e.or().andPidEqualTo(pid).andUserIdEqualTo(1);
			List<WordPlate> l = wordPlateMapper.selectByExample(e);
			for(WordPlate wp:l){
				wp.setUserId(user.getUserId());
				wp.setPlateId(null);
				wp.setPid(p.getPlateId());
				wordPlateMapper.insertSelective(wp);
			}
		}
		//新闻栏目
		NewsMyPlateExample nme = new NewsMyPlateExample();
		nme.or().andPidEqualTo(0).andUserIdEqualTo(1);
		List<NewsMyPlate> nyList = newsMyPlateMapper.selectByExample(nme);
		for(NewsMyPlate p:nyList){
			p.setUserId(user.getUserId());
			p.setPlateId(null);
			newsMyPlateMapper.insertSelective(p);
		}
	}

	/**
	 * 根据用户查询栏目
	 * 
	 * @param userId
	 * @return
	 */
	public List<WordPlate> getUserPlate(int userId) {
		// TODO Auto-generated method stub
		WordPlateExample example = new WordPlateExample();
		example.setLimitStart(0);
		example.setLimitEnd(8);
		example.setOrderByClause("order_id");
		example.or().andPidEqualTo(0).andUserIdEqualTo(userId);
		List<WordPlate> list = wordPlateMapper.selectByExample(example);
		setSubs(list, userId);
		return list;
	}

	public void setSubs(List<WordPlate> list, int userId) {
		if (!list.isEmpty()) {
			for (WordPlate plate : list) {
				WordPlateExample example = new WordPlateExample();
				example.setOrderByClause("order_id");
				example.setLimitStart(0);
				example.setLimitEnd(8);
				example.or().andPidEqualTo(plate.getPlateId())
						.andUserIdEqualTo(userId);
				List<WordPlate> subs = wordPlateMapper.selectByExample(example);
				setSubs(subs, userId);
				plate.setSubs(subs);
			}
		}
	}

	/**
	 * 搜索带分页
	 * 
	 * @param keyword
	 * @param type
	 * @param pageNo
	 * @param pageSize
	 * @param searchType title：标题，content：全文
	 * @return
	 */
	public Map<String, Object> search(String keyword, String searchType,
			String articleFormat,String docType,Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		GroupParam groupParam = new GroupParam();
		
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = 20;
		}
		Map<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		
		DocumentSearchCondition documentContion = new DocumentSearchCondition();
		DocumentSearchCondition documentContion1 = new DocumentSearchCondition();
		
		List<String> formatList = new ArrayList<String>();
		if(articleFormat==null||"all".equals(articleFormat)){
		}else if("doc".equals(articleFormat)){
			formatList.add(articleFormat);
			formatList.add("docx");			
		}else if("ppt".equals(articleFormat)){
			formatList.add(articleFormat);
			formatList.add("pptx");			
		}else{
			formatList.add(articleFormat);
		}
		documentContion.setArticleFormat(formatList);
		documentContion.setFileType(Integer.parseInt(docType));
		
		List<String> mustList = new ArrayList<String>();
		String[] musts = Function.returnArray(keyword);
		for(String m:musts){
			mustList.add(m);
		}
		documentContion.setShouldWordsOfArticleName(mustList);
		
		List<DocumentSearchCondition> dlist=new ArrayList<DocumentSearchCondition>();
		dlist.add(documentContion);
		
		if("content".equals(searchType)){//全文
			documentContion1.setShouldWordsOfArticleContent(mustList);
			documentContion1.setArticleFormat(formatList);
			documentContion1.setFileType(Integer.parseInt(docType));
			dlist.add(documentContion1);		
		}

		// fileType
		// from pageSize
		if (pageNo == 1) {
			groupParam.setFrom(0);
		} else {
			groupParam.setFrom((pageNo - 1) * pageSize);
		}
		groupParam.setJsonData(dlist);
		groupParam.setPageSize(pageSize);
		
		map.put("searchParamJson", gson.toJson(groupParam));
		long start = System.currentTimeMillis();
		logger.warn("搜索文档开始" + System.currentTimeMillis());
		String json = getHtml(map, searchByGroup);
		long end = System.currentTimeMillis();
		logger.warn("搜索文档结束" + end + " 共耗时:" + (end - start) + "ms！");
		ResponseParam rp=new ResponseParam();
		//返回值长度
		long count=0;
		Map<String, Object> page = new HashMap<String, Object>();
		//期刊
		List<EsJournal> jsonData= new ArrayList<EsJournal>();  	
		//非期刊
//		List<Document> others=new ArrayList<Document>();  	
		
		if(json!=null && !json.equals("")){
			rp = gson.fromJson(json, ResponseParam.class);
			count = rp.getCount();
			String resultJsonData = rp.getJsonData();
			jsonData = gson.fromJson(resultJsonData, new TypeToken<List<EsJournal>>() {
			}.getType());
			//期刊名称高亮
			setJourHighLight(jsonData, keyword);
			 if(jsonData!=null&&!jsonData.isEmpty()){
				 for (EsJournal tempInfo : jsonData) {
	//				 List<Document> docList = gson.fromJson(tempInfo.getDocs(), new TypeToken<List<Document>>() {
	//					}.getType());
					 List<Document> docList=tempInfo.getDocs();
					 getDownAndViewCount(docList);
					 setDocTitle(docList);
//					 sethighlight(docList, keyword);
				 }
			 }
		
		}			
//		others = gson.fromJson(rp.getOthers(), new TypeToken<ArrayList<Document>>() {}.getType());		
//		getDownAndViewCount(others);
//		sethighlight(others, keyword);
		page.put("jsonData", jsonData);
//		page.put("others", others);
		page.put("count", count);
		page.put("pageSize", pageSize);
		page.put("pageNo", pageNo);
		page.put("pageCount", ((count % pageSize == 0) ? (count / pageSize)
				: ((count / pageSize) + 1)));
		return page;
	}

	/**
	 * 更多
	 * 查询出符合搜索条件，本期刊下的所有文档信息
	 * */
	public Map<String, Object> more(String keyword, String searchType,
			String articleFormat, String journalId, Integer pageNo,
			Integer pageSize) {
		GroupParam groupParam = new GroupParam();
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = 20;
		}
		Map<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		
		/*if (pageNo == 1) {
			map.put("from", 0 + "");
		} else {
			map.put("from", ((pageNo - 1) * pageSize) + "");
		}*/
		//searchParam.setArticleFormat(articleFormat==null?"all":articleFormat);
		
		List<DocumentSearchCondition> searchList = new ArrayList<DocumentSearchCondition>();
		DocumentSearchCondition contion = new DocumentSearchCondition();
		DocumentSearchCondition contion1 = new DocumentSearchCondition();
		List<String> mustList = new ArrayList<String>();
		String[] musts = Function.returnArray(keyword);
		for(String m:musts){
			mustList.add(m);
		}
		contion.setShouldWordsOfArticleName(mustList);
		contion1.setShouldWordsOfArticleContent(mustList);
		
		List<Integer> journalIds = new ArrayList<Integer>();
		journalIds.add(Integer.parseInt(journalId));
		
		contion.setJournalIds(journalIds);
		contion1.setJournalIds(journalIds);
		
		List<String> formatList = new ArrayList<String>();
		if(articleFormat==null||"all".equals(articleFormat)){
		}else if("doc".equals(articleFormat)){
			formatList.add(articleFormat);
			formatList.add("docx");			
		}else if("ppt".equals(articleFormat)){
			formatList.add(articleFormat);
			formatList.add("pptx");			
		}else{
			formatList.add(articleFormat);
		}
		contion.setArticleFormat(formatList);

		searchList.add(contion);
		/*if(searchType==null||"title".equals(searchType)){
			
		}else{//全文搜索
			searchList.add(contion1);
		}*/
		List<DocumentSearchCondition> dlist=new ArrayList<DocumentSearchCondition>();
		dlist.add(contion);
		
		if("content".equals(searchType)){//全文
			contion1.setShouldWordsOfArticleContent(mustList);
			contion1.setArticleFormat(formatList);
			dlist.add(contion1);		
		}
		if (pageNo == 1) {
			groupParam.setFrom(0);
		} else {
			groupParam.setFrom((pageNo - 1) * pageSize);
		}
		groupParam.setJsonData(dlist);
		groupParam.setPageSize(pageSize);
		/*map.put("jsonStr", gson.toJson(searchList));*/
		map.put("searchParamJson", gson.toJson(groupParam));
		//map.put("orderByField","passTime");
		//map.put("order", "desc");
		long start = System.currentTimeMillis();
		logger.warn("搜索文档/模板开始" + System.currentTimeMillis());
		String json = getHtml(map, searchMoreByJournal);
		long end = System.currentTimeMillis();
		logger.warn("搜索文档/模板结束" + end + " 共耗时:" + (end - start) + "ms！");
		ResponseParam rp=new ResponseParam();
		long count=0;
		String journalName="";
		String journalTitle="";
		Map<String, Object> page = new HashMap<String, Object>();
		//期刊
		List<Document> jsonData=new ArrayList<Document>();
		if(json!=null && !json.equals("")){
			rp = gson.fromJson(json, ResponseParam.class);
			count = rp.getCount();
			journalTitle=rp.getOthers();
			//期刊名高亮
			journalName=setMoreHighLight(journalTitle, keyword);
			
			jsonData = gson.fromJson(rp.getJsonData(), new TypeToken<List<Document>>() {}.getType());
			getDownAndViewCount(jsonData);
//			sethighlight(jsonData, keyword);
			setDocTitle(jsonData);
			
		}		
		page.put("jsonData", jsonData);	
		page.put("count", count);
		page.put("pageSize", pageSize);
		page.put("pageNo", pageNo);
		page.put("pageCount", ((count % pageSize == 0) ? (count / pageSize)
				: ((count / pageSize) + 1)));
		page.put("journalName", journalName);	
		page.put("journalTitle", journalTitle);	
		return page;
	}
	
	
	/**
	 * 栏目下的文档
	 * 
	 * @param plateId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> plate(Integer plateId, Integer pageNo,
			Integer pageSize) {
		// TODO Auto-generated method stub
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = 12;
		}
		Map<String, String> map = new HashMap<String, String>();
		List<WordPlate> templist = new ArrayList<WordPlate>();
		Gson gson = new Gson();
		List<DocumentSearchCondition> searchCondition = new ArrayList<DocumentSearchCondition>();
		WordPlate plate = wordPlateMapper.selectByPrimaryKey(plateId);
		if (plate == null) {
			return null;
		}
		//如果为一级栏目 取其子栏目的条件
		if (plate.getPid().intValue() == 0) {
			WordPlateExample example = new WordPlateExample();
			example.or().andPidEqualTo(plate.getPlateId());
			templist = wordPlateMapper.selectByExample(example);
		} else {
			templist.add(plate);
		}
		if (templist == null || templist.size() == 0){
			templist.add(plate);
		}
		if (pageNo == 1) {
			map.put("from", 0 + "");
		} else {
			map.put("from", ((pageNo - 1) * pageSize) + "");
		}
		for (WordPlate wp : templist) {
			Condition condition = gson.fromJson(wp.getConditions(),
					Condition.class);
			if (condition == null) {
				continue;
			}
			DocumentSearchCondition sc = new DocumentSearchCondition();
			DocumentSearchCondition scc = new DocumentSearchCondition();
			sc.setFileType(3);
			scc.setFileType(3);
			if (StringUtils.isNotBlank(condition.getMustTagNames())) {
				List<String> l = new ArrayList<String>();
				for (String s : condition.getMustTagNames().split("_")) {
					l.add(s);
				}
				sc.setMustArticleLabels(l);
				scc.setMustArticleLabels(l);
			}
			if (StringUtils.isNotBlank(condition.getShouldTagNames())) {
				List<String> l = new ArrayList<String>();
				for (String s : condition.getShouldTagNames().split("_")) {
					l.add(s);
				}
				sc.setShouldArticleLabels(l);
				scc.setShouldArticleLabels(l);
			}
			if (StringUtils.isNotBlank(condition.getMustNotTagNames())) {
				List<String> l = new ArrayList<String>();
				for (String s : condition.getMustNotTagNames().split("_")) {
					l.add(s);
				}
				sc.setMustNotArticleLabels(l);
				scc.setMustNotArticleLabels(l);
			}
			// 关键词
			boolean flag = condition.getFlag() == 1 ? true : false;
			// 标题
			// and关键词
			if (StringUtils.isNotBlank(condition.getMustWordNames())) {
				String[] temp = condition.getMustWordNames().split("_");
				List<String> l = new ArrayList<String>();
				for (String str : temp) {
					l.add(str);
				}
				sc.setMustWordsOfArticleName(l);
				if (flag) {//文档名字
				} else {
					scc.setMustWordsOfArticleContent(l);
				}
			}
			// OR关键词
			if (StringUtils.isNotBlank(condition.getShouldWordNames())) {
				String[] temp = condition.getShouldWordNames().split("_");
				List<String> l = new ArrayList<String>();
				for (String str : temp) {
					l.add(str);
				}
				sc.setShouldWordsOfArticleName(l);
				if (flag) {
				} else {
					scc.setShouldWordsOfArticleContent(l);
				}
			}
			// not关键词
			if (StringUtils.isNotBlank(condition.getMustNotWordNames())) {
				String[] temp = condition.getMustNotWordNames().split("_");
				List<String> l = new ArrayList<String>();
				for (String str : temp) {
					l.add(str);
				}
				sc.setMustNotWordsOfArticleName(l);
				if (flag) {
				} else {
					scc.setMustNotWordsOfArticleContent(l);
				}
			}
			//sc.setArticleType("document");
			searchCondition.add(sc);
			if (!flag){//文档内容
				searchCondition.add(scc);
			}
		}
		map.put("jsonStr", gson.toJson(searchCondition));
		map.put("pageSize", pageSize + "");
		map.put("fullContent", "false");
		map.put("orderByField", "passTime");
		map.put("order", "DESC");
		long start = System.currentTimeMillis();
		logger.warn("栏目获取文章开始" + System.currentTimeMillis());
		String json = getHtml(map, searchByMultiJsonStr);
		long end = System.currentTimeMillis();
		logger.warn("栏目取文章结束" + end + " 共耗时:" + (end - start) + "ms！");
		SearchDocumentData sd=new SearchDocumentData();
		long count = 0;
		if(json!=null && !json.equals("")){
			sd = gson.fromJson(json, SearchDocumentData.class);
			count=sd.getCount();
			getDownAndViewCount(sd.getData());
		}
		if (sd.getData() != null && sd.getData().size() > 0){
			for(Document dc : sd.getData()){
				//将更新时间由long转换成Date类型
				if (dc.getUpdateTime() != null){
					dc.setUpdateDate(DateTool.stampToDate(dc.getUpdateTime()));	
				}
			}
		}

		Map<String, Object> page = new HashMap<String, Object>();
		page.put("list", sd.getData());
		page.put("count", count);
		page.put("pageSize", pageSize);
		page.put("pageNo", pageNo);
		page.put("pageCount", ((count % pageSize == 0) ? (count / pageSize)
				: ((count / pageSize) + 1)));
		return page;
	}

	/**
	 * 栏目下的期刊
	 * 
	 * @param plateId
	 * @param pageNo
	 * @param pageSize
	 * @param deleteTag 值为1时查询所有，非1则过滤删除的
	 * @return
	 */
	public Map<String, Object> journal(Integer plateId, Integer pageNo,
			Integer pageSize, Integer deleteTag, Integer userId) {
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = 12;
		}
		Map<String, String> map = new HashMap<String, String>();
		
		List<Journal> result = new ArrayList<Journal>();
		List<ContionParam> templist = new ArrayList<ContionParam>();
		List<WordPlate> plateList = new ArrayList<WordPlate>();
		Gson gson = new Gson();
		WordPlate plate = wordPlateMapper.selectByPrimaryKey(plateId);
		if (plate == null) {
			return null;
		}
		//如果为一级栏目 取其子栏目的条件
		if (plate.getPid().intValue() == 0) {
			WordPlateExample example = new WordPlateExample();
			example.or().andPidEqualTo(plate.getPlateId());
			plateList = wordPlateMapper.selectByExample(example);		
		} else {
			plateList.add(plate);
	    }
		if (plateList == null || plateList.size() == 0){
			plateList.add(plate);
		}
		for (WordPlate pt : plateList){
			ContionParam param = new ContionParam();
			param.setConditions(pt.getConditions());
			param.setDelList(pt.getDelList());
			param.setTopList(pt.getTopList());
			templist.add(param);
		}
		if (deleteTag == null){
			deleteTag = 0;
		}

	    SearchParam searchParam = getJournalParam(templist,deleteTag); 
		searchParam.setOrderByField("updateTime");
		searchParam.setOrder("DESC");	

		if (pageNo == 1) {
			searchParam.setFrom(0);
		} else {
			searchParam.setFrom((pageNo - 1) * pageSize);
		}
		
		searchParam.setPageSize(pageSize);
		
		map.put("searchParamJson", gson.toJson(searchParam));
		if (userId != null){
			map.put("userId", userId.toString());
		}
		long start = System.currentTimeMillis();
		logger.warn("栏目获取期刊开始" + System.currentTimeMillis());
		String resultJosn = getHtml(map, searchJournalByUserId);
		long end = System.currentTimeMillis();
		logger.warn("栏目获取期刊结束" + end + " 共耗时:" + (end - start) + "ms！");
		ResponseParam rp=new ResponseParam();
		long count = 0;
		if(resultJosn!=null && !resultJosn.equals("")){
			rp = gson.fromJson(resultJosn, ResponseParam.class);
			result= gson.fromJson(rp.getJsonData(), new TypeToken<List<Journal>>() {}.getType());
			count = rp.getCount();
		}
        if (CommonUtils.isNotEmpty(result)) {
            for (Journal journal : result) {
            	journal.setUpdateDate(DateTool.stampToDate(journal.getUpdateTime()));
            }
        }
		Map<String, Object> page = new HashMap<String, Object>();
		page.put("list", result);
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
		String html = null;
		try {
			logger.warn("与ES交互参数" + params);
			logger.warn("与ES交互URL" + url);
			html = fetcher1.post(url, params, null, "utf-8");
		} catch (Exception e) {
			logger.error("与ES交互异常" + e);
		}
		return html;
	}

	/**
	 * 获取导航
	 * 
	 * @param plateId
	 * @param status 1用户栏目 2系统栏目
	 * @return
	 */
	public String getNavigater(Integer plateId,int status) {
		// TODO Auto-generated method stub
		int pid = 0;
		int id = 0;
		String name = "";
		if(status==2){
			WordColumn column = wordColumnMapper.selectByPrimaryKey(plateId);
			id = column.getId();
			pid = column.getPid();
			name = column.getColumnName();
			return getBeforNavigater(pid,
					"<a href=\"view/column.do?columnId=" + id + "\">"
							+ name + "</a>",status);
		}else{
			WordPlate plate = wordPlateMapper.selectByPrimaryKey(plateId);
			id = plate.getPlateId();
			pid = plate.getPid();
			name = plate.getPlateName();
			return getBeforNavigater(pid,
					"<a href=\"view/plate.do?plateId=" + id + "\">"
							+ name + "</a>",status);
		}
		
	}

	/**
	 * 递归父节点导航
	 * 
	 * @param plateId
	 * @param navigater
	 * @return
	 */
	public String getBeforNavigater(Integer plateId, String navigater,int status) {
		int pid = 0;
		int id = 0;
		String name = "";
		if(status==2){
			WordColumn column = wordColumnMapper.selectByPrimaryKey(plateId);
			if(column!=null){
				id = column.getId();
				pid = column.getPid();
				name = column.getColumnName();
			}
		}else{
			WordPlate plate = wordPlateMapper.selectByPrimaryKey(plateId);
			if(plate!=null){
				id = plate.getPlateId();
				pid = plate.getPid();
				name = plate.getPlateName();
			}
		}
		if (id == 0) {
			navigater = "<a href=\"view/home.do\">知识创享网</a>&nbsp;&gt;&nbsp;"
					+ navigater;
		} else {
			if(status==2){
				navigater = getBeforNavigater(pid,
						"<a href=\"view/column.do?columnId=" + id
								+ "\">" + name
								+ " </a>&nbsp;&gt;&nbsp;" + navigater,status);
			}else{
				navigater = getBeforNavigater(pid,
						"<a href=\"view/plate.do?plateId=" + id
								+ "\">" + name
								+ " </a>&nbsp;&gt;&nbsp;" + navigater,status);
			}
		}
		return navigater;
	}

	/**
	 * 文章预览页面详情
	 * 
	 * @param articleId
	 * @return
	 */
	public Article getArticle(int articleId) {
		// TODO Auto-generated method stub
		return articleMapper.selectByPrimaryKey(articleId);
	}

	/**
	 * 文章预览期刊详情
	 * 
	 * @param articleId
	 * @return
	 */
	public WordJournal getWordJournal(int wordJournalId) {
		return wordJournalMapper.selectByPrimaryKey(wordJournalId);
	}
	
	/**
	 * 获取文章阅读及下载次数
	 *  @param articleId
	 */
	public WordArticleCount getWordArticleCount(int articleId){
		WordArticleCount wordArticleCount = null;
		WordArticleCountExample example = new WordArticleCountExample();
		example.or().andArticleIdEqualTo(articleId);
		List<WordArticleCount> list = wordArticleCountMapper.selectByExample(example);
		if (list.isEmpty()){
			wordArticleCount = new WordArticleCount();
			wordArticleCount.setViewCount(0);
			wordArticleCount.setDownCount(0);
			wordArticleCount.setArticleId(articleId);
			wordArticleCountMapper.insert(wordArticleCount);
		}else{
			wordArticleCount = list.get(0);
		}
		return wordArticleCount;
	}
	
	/**
	 * 根据期刊ID、年、月获取期刊所有分期信息
	 * @param JournalInfoBean
	 */
	public Map<String,List<Object>> getJournalInfo(JournalInfoBean journalInfoBean){
		
		Map<String,List<Object>> map = new HashMap<String, List<Object>>();
		List<Object> articleYearList = new ArrayList<Object>();//年份
		List<Object> articleMonthList = new ArrayList<Object>();//月份
		List<Object> articleShowList = new ArrayList<Object>();//具体分期显示
	    Calendar calendar = Calendar.getInstance();  
		//根据条件获取该期刊所有分期信息
		List<JournalInfoBean> list = articleMapper.getJournalInfo(journalInfoBean);
		if (list != null && list.size() > 0){
			for (JournalInfoBean bean : list){
				//年
				if (!articleYearList.contains(bean.getArticleYear())){
					articleYearList.add(bean.getArticleYear());	
				}
				if (!StringUtil.isBlank(bean.getArticleYear()) && !StringUtil.isBlank(journalInfoBean.getArticleYear()) 
						&& bean.getArticleYear().equals(journalInfoBean.getArticleYear())){
					
					JournalInfoBean jourBean = null;
					//月
					if (!articleMonthList.contains(bean.getArticleMonth())){
						articleMonthList.add(bean.getArticleMonth());
					}
					//日刊、周刊、半月刊
					if (journalInfoBean.getJournalTypeId() == day || journalInfoBean.getJournalTypeId() == week
							|| journalInfoBean.getJournalTypeId() == halfMonths){
						if (!StringUtil.isBlank(bean.getArticleMonth()) && !StringUtil.isBlank(journalInfoBean.getArticleMonth()) 
								&& bean.getArticleMonth().equals(journalInfoBean.getArticleMonth())){
							jourBean = new JournalInfoBean();
							//日刊
							if (journalInfoBean.getJournalTypeId() == day){
								jourBean.setArticleShow(bean.getArticleDay()+"日");
						    //周刊	
							}else if (journalInfoBean.getJournalTypeId() == week){
							     calendar.setTime(bean.getArticleJournalTime());  
							     //第几周  
							     int whichWeek = calendar.get(Calendar.WEEK_OF_MONTH); 
							     jourBean.setArticleShow(whichWeek+"周");
							//半月报     
							}else if (journalInfoBean.getJournalTypeId() == halfMonths){
								if (Integer.valueOf(bean.getArticleDay()) <= 15){
									jourBean.setArticleShow(bean.getArticleMonth()+"月上");
								}else{
									jourBean.setArticleShow(bean.getArticleMonth()+"月下");
								}
							}                          
						}
					//月刊、双月刊	
					}else if (journalInfoBean.getJournalTypeId() == months || journalInfoBean.getJournalTypeId() == twoMonths){
						jourBean = new JournalInfoBean();
						jourBean.setArticleShow(bean.getArticleMonth()+"月");
					//季刊
					}else if (journalInfoBean.getJournalTypeId() == season){
						int whichSeanson = ((Integer.valueOf(bean.getArticleMonth()) - 1)/3) + 1;
						jourBean = new JournalInfoBean();
						jourBean.setArticleShow(whichSeanson+"季度");
				    //半年刊
					}else if (journalInfoBean.getJournalTypeId() == halfYear){
						jourBean = new JournalInfoBean();
						if (Integer.valueOf(bean.getArticleMonth()) <= 6){
							jourBean.setArticleShow(bean.getArticleYear()+"年上");
						}else{
							jourBean.setArticleShow(bean.getArticleYear()+"年下");
						}
					//年刊
					}else if (journalInfoBean.getJournalTypeId() == year){
						jourBean = new JournalInfoBean();
						jourBean.setArticleShow(bean.getArticleYear()+"年");
					}
					if (jourBean != null){
						jourBean.setArticleId(bean.getArticleId());
						articleShowList.add(jourBean);
					}
				}
			}
			map.put("articleYear", articleYearList);
			map.put("articleMonth", articleMonthList);
			map.put("articleShow", articleShowList);
		}
		return map;
	}
	
	/**
	 * 根据期刊ID、年、月、日获取期刊所有分期信息（新）
	 * 2015-11-12新版本
	 * @param JournalInfoBean
	 */
	public Map<String,List<Object>> getJournalInfoNew(JournalInfoBean journalInfoBean){
		
		Map<String,List<Object>> map = new HashMap<String, List<Object>>();
		List<Object> articleYearList = new ArrayList<Object>();//年份
		List<Object> articleMonthList = new ArrayList<Object>();//月份
		List<Object> articleDayList = new ArrayList<Object>();//日
		List<Object> articleShowList = new ArrayList<Object>();//具体分期显示
		String selyear = null;
		String selMonth = null;
	    Calendar calendar = Calendar.getInstance();  
		String articleYear = DateUtil.convertDateToString(journalInfoBean.getArticleJournalTime(), "yyyy");
		String articleMonth = DateUtil.convertDateToString(journalInfoBean.getArticleJournalTime(), "MM"); 		
		//根据条件获取该期刊所有分期信息
	    JournalInfoBean newInfoBean = new JournalInfoBean();
	    newInfoBean.setJournalId(journalInfoBean.getJournalId());
		List<JournalInfoBean> list = articleMapper.getJournalInfo(newInfoBean);

		//获取选择期刊之前的9篇文档
		List<JournalInfoBean> limitList = articleMapper.getJournalInfo(journalInfoBean);
		List<JournalInfoBean> limitListEarly = new ArrayList<JournalInfoBean>(); 
		if (limitList != null && limitList.size() > 0){
			limitListEarly = limitList;
			JournalInfoBean bean = limitList.get(0);
			if (journalInfoBean.getJournalTypeId() == day && !bean.getArticleMonth().equals(articleMonth) && bean.getArticleYear().equals(articleYear)){
				limitList = null;
			}else{
				if (!bean.getArticleYear().equals(articleYear)){
					limitList = null;
				}
			}			
		}
		//如果向下没数据，向上取9篇,再根据月份条件获取当月的，并且按时间倒序排列
		if (limitList == null || limitList.size() == 0){
			journalInfoBean.setCompareTime(journalInfoBean.getArticleJournalTime());
			List<JournalInfoBean> limitListNew = articleMapper.getJournalInfo(journalInfoBean);	
			List<JournalInfoBean> limitListLate = new ArrayList<JournalInfoBean>(); 
			if (limitListNew != null && limitListNew.size() > 0){
				limitList = new ArrayList<JournalInfoBean>();
				for (int i = limitListNew.size() - 1;i >= 0;i --){
					if (journalInfoBean.getJournalTypeId() == day){
						if (articleYear.equals(limitListNew.get(i).getArticleYear()) && articleMonth.equals(limitListNew.get(i).getArticleMonth())){
							limitList.add(limitListNew.get(i));
						}
						if (articleYear.equals(limitListNew.get(i).getArticleYear())){
							limitListLate.add(limitListNew.get(i));
						}
					}else{
						if (articleYear.equals(limitListNew.get(i).getArticleYear())){
							limitList.add(limitListNew.get(i));
						}
					}

				}
			}
			if (limitList == null || limitList.size() == 0){
				limitList = new ArrayList<JournalInfoBean>();
				if (limitListEarly != null && limitListEarly.size() > 0){
					for (int i = 0;i < limitListEarly.size();i ++){
						limitList.add(limitListEarly.get(i));
					}	
				}else if (limitListLate != null && limitListLate.size() > 0){
					for (int i = limitListLate.size() - 1;i >= 0;i --){
						limitList.add(limitListLate.get(i));
					}	
				}

		    }			

		}
		if(limitList != null && limitList.size() > 0){
			selyear = limitList.get(0).getArticleYear();
			selMonth = limitList.get(0).getArticleMonth();
			JournalInfoBean jourBean = null;
			for (JournalInfoBean bean : limitList){
		
				//日刊、周刊、半月刊
				if (journalInfoBean.getJournalTypeId() == day || journalInfoBean.getJournalTypeId() == week
						|| journalInfoBean.getJournalTypeId() == halfMonths){
				
					jourBean = new JournalInfoBean();
					//日刊
					if (journalInfoBean.getJournalTypeId() == day){
						 jourBean.setArticleShow(bean.getArticleDay()+"日");							
				    //周刊	
					}else if (journalInfoBean.getJournalTypeId() == week){
					     calendar.setTime(bean.getArticleJournalTime());  
					     //第几周  
					     int whichWeek = calendar.get(Calendar.WEEK_OF_MONTH); 
						 //获取当月的1号
						 calendar.set(Calendar.DAY_OF_MONTH,1);
					     int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;//1号是否周日，是周日时周数不变，否则周数减一
					     if (w != 0){
					    	 whichWeek -= 1; 
					     }
						 if(whichWeek == 0){
							 whichWeek += 1;
						 }
					     jourBean.setArticleShow(whichWeek+"周");
					//半月报     
					}else if (journalInfoBean.getJournalTypeId() == halfMonths){
						if (Integer.valueOf(bean.getArticleDay()) <= 15){
							jourBean.setArticleShow(bean.getArticleMonth()+"上");
						}else{
							jourBean.setArticleShow(bean.getArticleMonth()+"下");
						}
					}                          
				//月刊、双月刊	
				}else if (journalInfoBean.getJournalTypeId() == months || journalInfoBean.getJournalTypeId() == twoMonths){
					jourBean = new JournalInfoBean();
					jourBean.setArticleShow(bean.getArticleMonth()+"月");
				//季刊
				}else if (journalInfoBean.getJournalTypeId() == season){
					int whichSeanson = ((Integer.valueOf(bean.getArticleMonth()) - 1)/3) + 1;
					jourBean = new JournalInfoBean();
					jourBean.setArticleShow(whichSeanson+"季度");
			    //半年刊
				}else if (journalInfoBean.getJournalTypeId() == halfYear){
					jourBean = new JournalInfoBean();
					if (Integer.valueOf(bean.getArticleMonth()) <= 6){
						jourBean.setArticleShow(bean.getArticleYear()+"上半年");
					}else{
						jourBean.setArticleShow(bean.getArticleYear()+"下半年");
					}
				//年刊
				}else if (journalInfoBean.getJournalTypeId() == year){
					jourBean = new JournalInfoBean();
					jourBean.setArticleShow(bean.getArticleYear()+"年");
				}
				if (jourBean != null){
					jourBean.setArticleId(bean.getArticleId());
					jourBean.setArticleYear(bean.getArticleYear());
					jourBean.setArticleMonth(bean.getArticleMonth());
					jourBean.setArticleDay(bean.getArticleDay());
					articleShowList.add(jourBean);
				}
			}
		}

		if (list != null && list.size() > 0){
			JournalInfoBean dayBean = null;
			for (int j = 0;j < list.size();j ++){
				//存放年
				if (!articleYearList.contains(list.get(j).getArticleYear())){
					articleYearList.add(list.get(j).getArticleYear());	
				}
				//存放月
				if (!StringUtil.isBlank(list.get(j).getArticleYear()) && !StringUtil.isBlank(selyear) 
						&& list.get(j).getArticleYear().equals(selyear)){
					if (!articleMonthList.contains(list.get(j).getArticleMonth())){
						articleMonthList.add(list.get(j).getArticleMonth());
					}
					
					//存放日
					if (!StringUtil.isBlank(list.get(j).getArticleMonth()) && list.get(j).getArticleMonth().equals(selMonth)){
						dayBean = new JournalInfoBean();
						dayBean.setArticleDay(list.get(j).getArticleDay());
						dayBean.setArticleId(list.get(j).getArticleId());
						articleDayList.add(dayBean);
					}
				}

			}
			map.put("articleYearList", articleYearList);
			map.put("articleMonthList", articleMonthList);
			map.put("articleDayList", articleDayList);
			map.put("articleShowList", articleShowList);
		}
		return map;
	}
	
	
	/**
	 * 最新文档
	 */
	public List<Document> moreLikeSearch(int articleId) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		map.put("articleId", articleId + "");
		map.put("articeProject", "webword");
		map.put("from", "0");
		map.put("pageSize", "6");
		map.put("fullContent", "false");
		long start = System.currentTimeMillis();
		logger.warn("相似文档开始" + System.currentTimeMillis());
		String json = getHtml(map, moreLikeSearch);
		long end = System.currentTimeMillis();
		logger.warn("相似文档结束" + end + " 共耗时:" + (end - start) + "ms！");
		SearchDocumentData sdd = new SearchDocumentData();
		if (json != null && !json.equals("")){
			sdd = gson.fromJson(json, SearchDocumentData.class);
			if(sdd!=null){
				getDownAndViewCount(sdd.getData());
				return sdd.getData();
			}
		}
		return null;
	}

	/**
	 * 查看审核拒绝
	 * 
	 * @param articleId
	 * @return
	 */
	public KnowNopassReason getReason(int articleId, String type) {
		// TODO Auto-generated method stub
		KnowNopassReasonExample example = new KnowNopassReasonExample();
		example.or().andArticleIdEqualTo(articleId).andArticleTypeEqualTo(type);
		List<KnowNopassReason> list = knowNopassReasonMapper
				.selectByExample(example);
		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * plate 递归获取最后一级栏目
	 * 
	 * @param plate
	 * @param templist
	 */
	public void getEndwordPlate(WordPlate plate, List<WordPlate> templist) {
		WordPlateExample example = new WordPlateExample();
		example.or().andPidEqualTo(plate.getPlateId());
		List<WordPlate> newList = wordPlateMapper.selectByExample(example);
		if (newList.size() == 0) {
			templist.add(plate);
		} else {// 存在子元素
			for (WordPlate wordPlate : newList) {
				getEndwordPlate(wordPlate, templist);
			}
		}
	}

	/**
	 * 新闻详情
	 * @param newsId
	 * @return
	 */
	public News getNewsById(String newsId) {
		// TODO Auto-generated method stub
		News news = new News();
		Map<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		map.put("crawl_id", newsId + "");
		String json = getHtml(map, newsByNewsId);
		if(json!=null && !json.equals("")){
			news = gson.fromJson(json, News.class);
		}
		return news;
	}

	/**
	 * 新闻头条
	 * @return
	 */
	public NewsCrawls getNewsList() {
		// TODO Auto-generated method stub
		NewsCrawls newsList = new NewsCrawls();
		Map<String, String> map = new HashMap<String, String>();
		NewsSearchCondition searchcondition = new NewsSearchCondition();
		Gson gson = new Gson();
		map.put("from", "0");
		map.put("pageSize", "8");
		map.put("jsonStr", gson.toJson(searchcondition));
		String json = getHtml(map, newsByJson);
		if(json!=null && !json.equals("")){
			newsList = gson.fromJson(json, NewsCrawls.class);
		}
		return newsList;
	}

	/**
	 * 相关新闻
	 * @param newsId
	 * @return
	 */
	public List<News> moreLikeNews(String newsId) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("from", "0");
		map.put("pageSize", "11");
		map.put("crawl_id", newsId + "");
		String json = getHtml(map, searchMoreLikeByNewsId);
		List<News> docs = new ArrayList<News>();
		Gson gson = new Gson();
		if(json!=null && !json.equals("")){
			docs = gson.fromJson(json, new TypeToken<List<News>>() {}.getType());
		}
		return docs;
	}
	
	/**
	 * 文档，上传总数
	 * 
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getAllCounts(Integer userId) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		// 文档
		ArticleExample exampleArticle = new ArticleExample();
		exampleArticle.or().andUserIdEqualTo(userId)
				.andArticleTypeEqualTo("document").andPassTypeNotEqualTo("")
				.andPassTypeIsNotNull().andArticleSaveEqualTo("write");
		map.put("articleCount", articleMapper.countByExample(exampleArticle));
		//上传 
		ArticleExample exampleUpload = new ArticleExample();
		exampleUpload.or().andUserIdEqualTo(userId)
				.andArticleTypeEqualTo("document").andPassTypeNotEqualTo("")
				.andPassTypeIsNotNull().andArticleSaveEqualTo("upload");
		map.put("uploadCount", articleMapper.countByExample(exampleUpload));
		return map;
	}
	
	//检索首页左侧和中间的菜单栏
	public List<WordColumnGroup> getWordColumnGroup(Integer typeId,Integer userId){
		WordColumnGroupExample example = new WordColumnGroupExample();
		if(userId!=null){
			example.or().andUserIdEqualTo(userId).andTypeIdEqualTo(typeId);
		}else{
			example.or().andTypeIdEqualTo(typeId);
		}
		//取出栏目组
		List<WordColumnGroup> list = wordColumnGroupMapper.selectByExample(example);
		for(WordColumnGroup wg:list){
			WordColumnContactExample wcce = new WordColumnContactExample();
			wcce.setOrderByClause("order_id");
			wcce.or().andGroupIdEqualTo(wg.getId());
			//查询中间表，取出typeid
			List<WordColumnContact> liswcc = wordColumnContactMapper.selectByExample(wcce);
			if(!liswcc.isEmpty()){
				List<Integer> values = new ArrayList<Integer>();
				for(WordColumnContact wcc:liswcc){
					values.add(wcc.getColumnId());
				}
				WordColumnExample e = new WordColumnExample();
				e.or().andIdIn(values).andPidEqualTo(0);
				List<WordColumn> wcs = wordColumnMapper.selectByExample(e);
				List<WordColumn> newWcs = new ArrayList<WordColumn>();
				Map<Integer, WordColumn> colMap = new HashMap<Integer, WordColumn>();
				for (WordColumn wc : wcs){
					colMap.put(wc.getId(), wc);
				}
				for (WordColumnContact wcc:liswcc){
					WordColumn co = colMap.get(wcc.getColumnId());
					newWcs.add(co);
				}
				diguiColumn(newWcs);
				wg.setColumns(newWcs);
			}
		}
		return list;
	}

	/**
	 * 递归后台栏目
	 * @param wcs
	 */
	private void diguiColumn(List<WordColumn> wcs) {
		// TODO Auto-generated method stub
		if(!wcs.isEmpty()){
			for(WordColumn wc:wcs){
				WordColumnExample e = new WordColumnExample();
				if (wc != null){
					e.or().andPidEqualTo(wc.getId());
					List<WordColumn> subs = wordColumnMapper.selectByExample(e);
					wc.setColumns(subs);
					diguiColumn(subs);
				}
			}
		}
	}
	/**
	 * 左侧和中间栏目下的文档
	 * 
	 * @param columnId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> column(Integer columnId,Integer pageNo,
			Integer pageSize) {
		// TODO Auto-generated method stub
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = 12;
		}
		Map<String, String> map = new HashMap<String, String>();
		List<WordColumn> templist = new ArrayList<WordColumn>();
		Gson gson = new Gson();
		List<DocumentSearchCondition> searchCondition = new ArrayList<DocumentSearchCondition>();
		WordColumn column = wordColumnMapper.selectByPrimaryKey(columnId);
		if (column == null) {
			return null;
		}
		// 一级
		if (column.getPid().intValue() == 0) {
			WordColumnExample example = new WordColumnExample();
			example.or().andPidEqualTo(column.getId());
			templist = wordColumnMapper.selectByExample(example);		
		} else {
			templist.add(column);
		}
		if (templist == null || templist.size() == 0){
			templist.add(column);
		}
		if (pageNo == 1) {
			map.put("from", 0 + "");
		} else {
			map.put("from", ((pageNo - 1) * pageSize) + "");
		}
		for (WordColumn wc : templist) {
			Condition condition = gson.fromJson(wc.getConditions(),
					Condition.class);
			if (condition == null) {
				continue;
			}
			DocumentSearchCondition sc = new DocumentSearchCondition();
			DocumentSearchCondition scc = new DocumentSearchCondition();
			sc.setFileType(3);
			scc.setFileType(3);
			if (StringUtils.isNotBlank(condition.getMustTagNames())) {
				List<String> l = new ArrayList<String>();
				for (String s : condition.getMustTagNames().split("_")) {
					l.add(s);
				}
				sc.setMustArticleLabels(l);
				scc.setMustArticleLabels(l);
			}
			if (StringUtils.isNotBlank(condition.getShouldTagNames())) {
				List<String> l = new ArrayList<String>();
				for (String s : condition.getShouldTagNames().split("_")) {
					l.add(s);
				}
				sc.setShouldArticleLabels(l);
				scc.setShouldArticleLabels(l);
			}
			if (StringUtils.isNotBlank(condition.getMustNotTagNames())) {
				List<String> l = new ArrayList<String>();
				for (String s : condition.getMustNotTagNames().split("_")) {
					l.add(s);
				}
				sc.setMustNotArticleLabels(l);
				scc.setMustNotArticleLabels(l);
			}
			// 关键词
			boolean flag = condition.getFlag() == 1 ? true : false;
			// 标题
			// and关键词
			if (StringUtils.isNotBlank(condition.getMustWordNames())) {
				String[] temp = condition.getMustWordNames().split("_");
				List<String> l = new ArrayList<String>();
				for (String str : temp) {
					l.add(str);
				}
				if (flag) {
					sc.setMustWordsOfArticleName(l);
				} else {
					scc.setMustWordsOfArticleContent(l);
				}
			}
			// OR关键词
			if (StringUtils.isNotBlank(condition.getShouldWordNames())) {
				String[] temp = condition.getShouldWordNames().split("_");
				List<String> l = new ArrayList<String>();
				for (String str : temp) {
					l.add(str);
				}
				if (flag) {
					sc.setShouldWordsOfArticleName(l);
				} else {
					scc.setShouldWordsOfArticleContent(l);
				}
			}
			// not关键词
			if (StringUtils.isNotBlank(condition.getMustNotWordNames())) {
				String[] temp = condition.getMustNotWordNames().split("_");
				List<String> l = new ArrayList<String>();
				for (String str : temp) {
					l.add(str);
				}
				if (flag) {
					sc.setMustNotWordsOfArticleName(l);
				} else {
					scc.setMustNotWordsOfArticleContent(l);
				}
			}
			sc.setArticleType("document");
			searchCondition.add(sc);
			if (!flag){//文档内容
				searchCondition.add(scc);
			}
		}
		map.put("jsonStr", gson.toJson(searchCondition));
		map.put("pageSize", pageSize + "");
		map.put("fullContent", "false");
		map.put("orderByField", "passTime");
		map.put("order", "DESC");
		long start = System.currentTimeMillis();
		logger.warn("栏目获取文档开始" + System.currentTimeMillis());
		String json = getHtml(map, searchByMultiJsonStr);
		long end = System.currentTimeMillis();
		logger.warn("栏目取文档结束" + end + " 共耗时:" + (end - start) + "ms！");
		SearchDocumentData sd = new SearchDocumentData();
		long count = 0;
		if(json!=null && !json.equals("")){
			sd = gson.fromJson(json, SearchDocumentData.class);
			count = sd.getCount();
			getDownAndViewCount(sd.getData());
		}
		if (sd.getData() != null && sd.getData().size() > 0){
			for(Document dc : sd.getData()){
				//将更新时间由long转换成Date类型
				if (dc.getUpdateTime() != null){
					dc.setUpdateDate(DateTool.stampToDate(dc.getUpdateTime()));	
				}
			}
		}

		Map<String, Object> page = new HashMap<String, Object>();
		page.put("list", sd.getData());
		page.put("count", count);
		page.put("pageSize", pageSize);
		page.put("pageNo", pageNo);
		page.put("pageCount", ((count % pageSize == 0) ? (count / pageSize)
				: ((count / pageSize) + 1)));
		return page;
	}
	

	public Map<String, Object> columnJour(Integer columnId, Integer pageNo,
			Integer pageSize,Integer deleteTag,Integer userId) {
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = 12;
		}
		
		Map<String, String> map = new HashMap<String, String>();	
		List<WordColumn> columnList = new ArrayList<WordColumn>();
		List<ContionParam> templist = new ArrayList<ContionParam>();
		Gson gson = new Gson();
		WordColumn column = wordColumnMapper.selectByPrimaryKey(columnId);
		if (column == null) {
			return null;
		}
		// 一级
		if (column.getPid().intValue() == 0) {
			WordColumnExample example = new WordColumnExample();
			example.or().andPidEqualTo(column.getId());
			columnList = wordColumnMapper.selectByExample(example);
		} else {
			columnList.add(column);
		}
		if (columnList == null || columnList.size() == 0){
			columnList.add(column);
		}
		for (WordColumn col : columnList){
			ContionParam param = new ContionParam();
			param.setConditions(col.getConditions());
			param.setDelList(col.getDelList());
			param.setTopList(col.getTopList());
			templist.add(param);
		}

		if(deleteTag == null){
			deleteTag = 0;
		}
		SearchParam searchParam = getJournalParam(templist,deleteTag);
		if (pageNo == 1) {
			searchParam.setFrom(0);
		} else {
			searchParam.setFrom((pageNo - 1) * pageSize);
		}
		searchParam.setPageSize(pageSize);
		searchParam.setOrderByField("updateTime");
		searchParam.setOrder("DESC");
		map.put("searchParamJson", gson.toJson(searchParam));
		if (userId != null){
			map.put("userId", userId.toString());
		}
		long start = System.currentTimeMillis();
		logger.warn("栏目获取期刊开始" + System.currentTimeMillis());
		String resultJosn = getHtml(map, searchJournalByUserId);
		long end = System.currentTimeMillis();
		logger.warn("栏目获取期刊结束" + end + " 共耗时:" + (end - start) + "ms！");
		ResponseParam sd = new ResponseParam();
		List<Journal> result = new ArrayList<Journal>();
		long count = 0;
		if(resultJosn!=null && !resultJosn.equals("")){
		    sd = gson.fromJson(resultJosn, ResponseParam.class);
	        result = gson.fromJson(sd.getJsonData(), new TypeToken<List<Journal>>() {}.getType());
	        count = sd.getCount();
		}
        if (CommonUtils.isNotEmpty(result)) {
            for (Journal journal : result) {
            	journal.setUpdateDate(DateTool.stampToDate(journal.getUpdateTime()));
            }
        }
		Map<String, Object> page = new HashMap<String, Object>();
		page.put("list", result);
		page.put("count", count);
		page.put("pageSize", pageSize);
		page.put("pageNo", pageNo);
		page.put("pageCount", ((count % pageSize == 0) ? (count / pageSize)
				: ((count / pageSize) + 1)));
		return page;
	}

	/**
	 * 根据期刊ID获取该期刊最新一期文档
	 * @param journalId
	 */
	public Article getArticleByJournalTime(Integer journalId){
		ArticleExample example = new ArticleExample();
		example.or().andArticleJournalIdEqualTo(journalId).andPassTypeEqualTo("PASSED");
		example.setOrderByClause("article_journal_time desc");
		List<Article> list = articleMapper.selectByExample(example);		
		Article article = null;
		if (list != null && list.size() > 0){
			article = list.get(0);
		}
		return article;
	}
	/**
	 * 首页最新期刊
	 * @return
	 */
	public Map<String, Object> latestJournal() {
		Map<String, String> map = new HashMap<String, String>();
		SearchParam searchParam = new SearchParam();
		JournalSearchCondition journalSearchCondition=new JournalSearchCondition();
		//journalSearchCondition.setPassType("PASSED");
		journalSearchCondition.setShowData("1");
		List<JournalSearchCondition> contionList= new ArrayList<JournalSearchCondition>();
		contionList.add(journalSearchCondition);
		searchParam.setJsonData(contionList);
		Gson gson = new Gson();
		searchParam.setFrom(0);
		searchParam.setPageSize(12);
		searchParam.setOrderByField("updateTime");
		searchParam.setOrder("DESC");
		
		map.put("searchParamJson", gson.toJson(searchParam));
		long start = System.currentTimeMillis();
		logger.warn("获取首页最新期刊开始" + System.currentTimeMillis());
		String resultJosn = getHtml(map, searchJournalByQueryString);
		long end = System.currentTimeMillis();
		logger.warn("获取首页最新期刊结束" + end + " 共耗时:" + (end - start) + "ms！");
		ResponseParam sd = new ResponseParam();
		List<Journal> result  = new ArrayList<Journal>();
		long count = 0;
		if(resultJosn!=null && !resultJosn.equals("")){
			sd = gson.fromJson(resultJosn, ResponseParam.class);
	        result = gson.fromJson(sd.getJsonData(), new TypeToken<List<Journal>>() {}.getType());
	        count = sd.getCount();
		}
        if (CommonUtils.isNotEmpty(result)) {
            for (Journal journal : result) {
            	journal.setUpdateDate(DateTool.stampToDate(journal.getUpdateTime()));
            }
        }
		Map<String, Object> page = new HashMap<String, Object>();
		page.put("list", result);
		page.put("count", count);
		return page;
	}
	
	/**
	 * 搜索最新报告
	 * @return
	 * */
	public List<Document> latestDoc(){
		Map<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		List<DocumentSearchCondition> searchCondition = new ArrayList<DocumentSearchCondition>();
		DocumentSearchCondition sc = new DocumentSearchCondition();
		sc.setArticleType("document");
		searchCondition.add(sc);
		sc.setFileType(3);//非期刊文档
		map.put("jsonStr", gson.toJson(searchCondition));
		map.put("pageSize", "10");
		map.put("fullContent", "false");
		map.put("orderByField", "passTime");
		map.put("order", "DESC");
		long start = System.currentTimeMillis();
		logger.warn("栏目获取文章开始" + System.currentTimeMillis());
		String json = getHtml(map, searchByMultiJsonStr);
		long end = System.currentTimeMillis();
		logger.warn("栏目取文章结束" + end + " 共耗时:" + (end - start) + "ms！");
		SearchDocumentData sdd = new SearchDocumentData();
		if(json!=null && !json.equals("")){
			sdd = gson.fromJson(json, SearchDocumentData.class);
		}
		return sdd.getData();
	}

	/**
	 * 更新阅读次数下载次数
	 * @param articleId
	 */
	public void updateWordArticleCount(WordArticleCount wordArticleCount) {
		// TODO Auto-generated method stub
		wordArticleCountMapper.updateByPrimaryKey(wordArticleCount);
	}
	
	/**
	 * 获取推荐栏目列表
	 * @param typeId
	 * @return
	 */
	public List<WordColumnGroup> getRecWordColumn(Integer typeId,List<WordPlate> plateList){
		List<WordColumnGroup> list = getWordColumnGroup(typeId,null);
		if (plateList != null && plateList.size() > 0){
			for (WordColumnGroup group : list){
				List<WordColumn> columnList1 = group.getColumns();
				if (columnList1 != null && columnList1.size() > 0){
					for (WordColumn column1 : columnList1){
						List<WordColumn> columnList2 = column1.getColumns();
						if (columnList2 != null && columnList2.size() > 0){
							for (WordColumn column2 : columnList2){
								for (WordPlate plate : plateList){
									if (plate.getColumnId() == column2.getId()){
										column2.setAddFlag(1);
										continue;
									}
								}
								if (column2.getAddFlag() == null){
									column2.setAddFlag(0);
								}
							}
						}
					}
				}
			}
		}
		return list;
	}

	/**
	 * 将ES取出的文档列表通过查询数据库获得下载数和阅读数
	 * @param List<Document>
	 * @return List<Document>
	 */
	public List<Document> getDownAndViewCount(List<Document> docList){
		if(docList!=null && !docList.isEmpty()){
			List<Integer> esIdList=new ArrayList<Integer>();
			List<WordArticleCount> wordArticleCounts = null;
			for(Document d:docList){
				esIdList.add(d.getArticleId());
			}
			WordArticleCountExample example=new WordArticleCountExample();
			example.or().andArticleIdIn(esIdList);
			wordArticleCounts=wordArticleCountMapper.selectByExample(example);
			for(Document d:docList){
				d.setDownCount(0);
				d.setViewCount(0);
				for(WordArticleCount wac:wordArticleCounts){
					if(d.getArticleId()==wac.getArticleId().intValue()){
						d.setDownCount(wac.getDownCount());
						d.setViewCount(wac.getViewCount());
					}
				}
			}
		}
		return docList;	
	}
	
	/**
	 * 我的栏目下的期刊预览（页面刷新用）
	 * @param plateId
	 * @param pageNo
	 * @param pageSize
	 * @param topListStr 置顶信息
	 * @param delListStr 删除信息
	 * @return
	 */
	public Map<String, Object> journalForRefresh(WordPlate plate, Integer pageNo,
			Integer pageSize, Integer userId) {
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = 12;
		}
			
		Map<String, String> map = new HashMap<String, String>();
		
		List<Journal> result = new ArrayList<Journal>();
		Gson gson = new Gson();
		
		List<ContionParam> templist = new ArrayList<ContionParam>();
		ContionParam param = new ContionParam();
		param.setConditions(plate.getConditions());
		param.setDelList(plate.getDelList());
		param.setTopList(plate.getTopList());
		templist.add(param);
		SearchParam searchParam = getJournalParam(templist,1);
		if (pageNo == 1) {
			searchParam.setFrom(0);
		} else {
			searchParam.setFrom((pageNo - 1) * pageSize);
		}
		
		searchParam.setPageSize(pageSize);
		searchParam.setOrderByField("updateTime");
		searchParam.setOrder("DESC");	
		if (userId != null){
			map.put("userId", userId.toString());
		}
		//searchParam.setDeleteTag("true");
		map.put("searchParamJson", gson.toJson(searchParam));
		long start = System.currentTimeMillis();
		logger.warn("栏目获取期刊开始" + System.currentTimeMillis());
		String resultJosn = getHtml(map, searchJournalByUserId);
		long end = System.currentTimeMillis();
		logger.warn("栏目获取期刊结束" + end + " 共耗时:" + (end - start) + "ms！");
		ResponseParam rp=new ResponseParam();
		long count = 0;
		if(resultJosn!=null && !resultJosn.equals("")){
			rp = gson.fromJson(resultJosn, ResponseParam.class);
			result= gson.fromJson(rp.getJsonData(), new TypeToken<List<Journal>>() {}.getType());
			count = rp.getCount();
		}
        if (CommonUtils.isNotEmpty(result)) {
            for (Journal journal : result) {
            	journal.setUpdateDate(DateTool.stampToDate(journal.getUpdateTime()));
            }
        }
		Map<String, Object> page = new HashMap<String, Object>();
		page.put("list", result);
		page.put("count", count);
		page.put("pageSize", pageSize);
		page.put("pageNo", pageNo);
		page.put("pageCount", ((count % pageSize == 0) ? (count / pageSize)
				: ((count / pageSize) + 1)));
		return page;
	}
	
	/**
	 * 我的栏目下的文档预览（页面刷新用）
	 * @param plateId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> plateForRefresh(WordPlate plate, Integer pageNo,
			Integer pageSize) {

		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}

		Map<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		List<DocumentSearchCondition> searchCondition = new ArrayList<DocumentSearchCondition>();
		if (pageNo == 1) {
			map.put("from", 0 + "");
		} else {
			map.put("from", ((pageNo - 1) * pageSize) + "");
		}

		Condition condition = gson.fromJson(plate.getConditions(),
				Condition.class);
		DocumentSearchCondition sc = new DocumentSearchCondition();
		DocumentSearchCondition scc = new DocumentSearchCondition();
		sc.setFileType(3);
		scc.setFileType(3);
		if (StringUtils.isNotBlank(condition.getMustTagNames())) {
			List<String> l = new ArrayList<String>();
			for (String s : condition.getMustTagNames().split("_")) {
				l.add(s);
			}
			sc.setMustArticleLabels(l);
			scc.setMustArticleLabels(l);
		}
		if (StringUtils.isNotBlank(condition.getShouldTagNames())) {
			List<String> l = new ArrayList<String>();
			for (String s : condition.getShouldTagNames().split("_")) {
				l.add(s);
			}
			sc.setShouldArticleLabels(l);
			scc.setShouldArticleLabels(l);
		}
		if (StringUtils.isNotBlank(condition.getMustNotTagNames())) {
			List<String> l = new ArrayList<String>();
			for (String s : condition.getMustNotTagNames().split("_")) {
				l.add(s);
			}
			sc.setMustNotArticleLabels(l);
			scc.setMustNotArticleLabels(l);
		}
		// 关键词
		boolean flag = condition.getFlag() == 1 ? true : false;
		// 标题
		// and关键词
		if (StringUtils.isNotBlank(condition.getMustWordNames())) {
			String[] temp = condition.getMustWordNames().split("_");
			List<String> l = new ArrayList<String>();
			for (String str : temp) {
				l.add(str);
			}
			if (flag) {
				sc.setMustWordsOfArticleName(l);
			} else {
				scc.setMustWordsOfArticleContent(l);
			}
		}
		// OR关键词
		if (StringUtils.isNotBlank(condition.getShouldWordNames())) {
			String[] temp = condition.getShouldWordNames().split("_");
			List<String> l = new ArrayList<String>();
			for (String str : temp) {
				l.add(str);
			}
			if (flag) {
				sc.setShouldWordsOfArticleName(l);
			} else {
				scc.setShouldWordsOfArticleContent(l);
			}
		}
		// not关键词
		if (StringUtils.isNotBlank(condition.getMustNotWordNames())) {
			String[] temp = condition.getMustNotWordNames().split("_");
			List<String> l = new ArrayList<String>();
			for (String str : temp) {
				l.add(str);
			}
			if (flag) {
				sc.setMustNotWordsOfArticleName(l);
			} else {
				scc.setMustNotWordsOfArticleContent(l);
			}
		}
		//sc.setArticleType("document");
		searchCondition.add(sc);
		if (!flag){//文档内容
			searchCondition.add(scc);
		}
		map.put("jsonStr", gson.toJson(searchCondition));
		map.put("pageSize", pageSize + "");
		map.put("fullContent", "false");
		map.put("orderByField", "passTime");
		map.put("order", "DESC");
		long start = System.currentTimeMillis();
		logger.warn("栏目获取文章开始" + System.currentTimeMillis());
		String json = getHtml(map, searchByMultiJsonStr);
		long end = System.currentTimeMillis();
		logger.warn("栏目取文章结束" + end + " 共耗时:" + (end - start) + "ms！");
		SearchDocumentData sd=new SearchDocumentData();
		long count = 0;
		if(json!=null && !json.equals("")){
			sd = gson.fromJson(json, SearchDocumentData.class);
			count=sd.getCount();
			getDownAndViewCount(sd.getData());
		}
		if(sd.getData()!=null && !sd.getData().isEmpty()){
			for(Document dc : sd.getData()){
				//将更新时间由long转换成Date类型
				if (dc.getUpdateTime() != null){
					dc.setUpdateDate(DateTool.stampToDate(dc.getUpdateTime()));	
				}
			}
		}
		Map<String, Object> page = new HashMap<String, Object>();
		page.put("list", sd.getData());
		page.put("count", count);
		page.put("pageSize", pageSize);
		page.put("pageNo", pageNo);
		page.put("pageCount", ((count % pageSize == 0) ? (count / pageSize)
				: ((count / pageSize) + 1)));
		return page;
	}

	/**
	 *  推荐栏目下的非期刊预览（页面刷新用）
	 * @param columnId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> columnForRefresh(Integer columnId, Integer pageNo,
			Integer pageSize) {
		// TODO Auto-generated method stub
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = 12;
		}
		Map<String, String> map = new HashMap<String, String>();
		List<WordColumn> templist = new ArrayList<WordColumn>();
		Gson gson = new Gson();
		List<DocumentSearchCondition> searchCondition = new ArrayList<DocumentSearchCondition>();
		WordColumn column = wordColumnMapper.selectByPrimaryKey(columnId);
		if (column == null) {
			return null;
		}

		if (templist == null || templist.size() == 0){
			templist.add(column);
		}
		if (pageNo == 1) {
			map.put("from", 0 + "");
		} else {
			map.put("from", ((pageNo - 1) * pageSize) + "");
		}
		for (WordColumn wc : templist) {
			Condition condition = gson.fromJson(wc.getConditions(),
					Condition.class);
			if (condition == null) {
				continue;
			}
			DocumentSearchCondition sc = new DocumentSearchCondition();
			DocumentSearchCondition scc = new DocumentSearchCondition();
			if (StringUtils.isNotBlank(condition.getMustTagNames())) {
				List<String> l = new ArrayList<String>();
				for (String s : condition.getMustTagNames().split("_")) {
					l.add(s);
				}
				sc.setMustArticleLabels(l);
				scc.setMustArticleLabels(l);
			}
			if (StringUtils.isNotBlank(condition.getShouldTagNames())) {
				List<String> l = new ArrayList<String>();
				for (String s : condition.getShouldTagNames().split("_")) {
					l.add(s);
				}
				sc.setShouldArticleLabels(l);
				scc.setShouldArticleLabels(l);
			}
			if (StringUtils.isNotBlank(condition.getMustNotTagNames())) {
				List<String> l = new ArrayList<String>();
				for (String s : condition.getMustNotTagNames().split("_")) {
					l.add(s);
				}
				sc.setMustNotArticleLabels(l);
				scc.setMustNotArticleLabels(l);
			}
			// 关键词
			boolean flag = condition.getFlag() == 1 ? true : false;
			// 标题
			// and关键词
			if (StringUtils.isNotBlank(condition.getMustWordNames())) {
				String[] temp = condition.getMustWordNames().split("_");
				List<String> l = new ArrayList<String>();
				for (String str : temp) {
					l.add(str);
				}
				if (flag) {
					sc.setMustWordsOfArticleName(l);
				} else {
					scc.setMustWordsOfArticleContent(l);
				}
			}
			// OR关键词
			if (StringUtils.isNotBlank(condition.getShouldWordNames())) {
				String[] temp = condition.getShouldWordNames().split("_");
				List<String> l = new ArrayList<String>();
				for (String str : temp) {
					l.add(str);
				}
				if (flag) {
					sc.setShouldWordsOfArticleName(l);
				} else {
					scc.setShouldWordsOfArticleContent(l);
				}
			}
			// not关键词
			if (StringUtils.isNotBlank(condition.getMustNotWordNames())) {
				String[] temp = condition.getMustNotWordNames().split("_");
				List<String> l = new ArrayList<String>();
				for (String str : temp) {
					l.add(str);
				}
				if (flag) {
					sc.setMustNotWordsOfArticleName(l);
				} else {
					scc.setMustNotWordsOfArticleContent(l);
				}
			}
			sc.setFileType(3);
			scc.setFileType(3);
			searchCondition.add(sc);
			if (!flag){//文档内容
				searchCondition.add(scc);
			}
		}
		map.put("jsonStr", gson.toJson(searchCondition));
		map.put("pageSize", pageSize + "");
		map.put("fullContent", "false");
		map.put("orderByField", "passTime");
		map.put("order", "DESC");
		long start = System.currentTimeMillis();
		logger.warn("栏目获取文档开始" + System.currentTimeMillis());
		String json = getHtml(map, searchByMultiJsonStr);
		long end = System.currentTimeMillis();
		logger.warn("栏目取文档结束" + end + " 共耗时:" + (end - start) + "ms！");
		SearchDocumentData sd = new SearchDocumentData();
		long count = 0;
		if(json!=null && !json.equals("")){
			sd = gson.fromJson(json, SearchDocumentData.class);
			count = sd.getCount();
			getDownAndViewCount(sd.getData());
		}
		if (sd.getData() != null && sd.getData().size() > 0){
			for(Document dc : sd.getData()){
				//将更新时间由long转换成Date类型
				if (dc.getUpdateTime() != null){
					dc.setUpdateDate(DateTool.stampToDate(dc.getUpdateTime()));	
				}
			}
		}

		Map<String, Object> page = new HashMap<String, Object>();
		page.put("list", sd.getData());
		page.put("count", count);
		page.put("pageSize", pageSize);
		page.put("pageNo", pageNo);
		page.put("pageCount", ((count % pageSize == 0) ? (count / pageSize)
				: ((count / pageSize) + 1)));
		return page;
	}
	
	/**
	 * 推荐栏目下的期刊预览（页面刷新用）
	 * @param plateId
	 * @param pageNo
	 * @param pageSize
	 * @param topListStr 置顶信息
	 * @param delListStr 删除信息
	 * @return
	 */
	public Map<String, Object> columnJourForRefresh(Integer columnId, Integer pageNo,
			Integer pageSize,String topListStr, String delListStr) {
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = 12;
		}
		
		Map<String, String> map = new HashMap<String, String>();
		List<WordColumn> columnList = new ArrayList<WordColumn>();
		List<ContionParam> templist = new ArrayList<ContionParam>();
		Gson gson = new Gson();
		WordColumn column = wordColumnMapper.selectByPrimaryKey(columnId);
		if (column == null) {
			return null;
		}
		// 一级
//		if (column.getPid().intValue() == 0) {
//			WordColumnExample example = new WordColumnExample();
//			example.or().andPidEqualTo(column.getId());
//			columnList = wordColumnMapper.selectByExample(example);
//		} else {
//			columnList.add(column);
//		}
		if (columnList == null || columnList.size() == 0){
			columnList.add(column);
		}
		for (WordColumn col : columnList){
			ContionParam param = new ContionParam();
			param.setConditions(col.getConditions());
			param.setDelList(delListStr);
			param.setTopList(topListStr);
			templist.add(param);
		}

		SearchParam searchParam = getJournalParam(templist,1);
		if (pageNo == 1) {
			searchParam.setFrom(0);
		} else {
			searchParam.setFrom((pageNo - 1) * pageSize);
		}
		
		searchParam.setPageSize(pageSize);
		searchParam.setOrderByField("updateTime");
		searchParam.setOrder("DESC");	
		//searchParam.setDeleteTag("true");
		map.put("searchParamJson", gson.toJson(searchParam));
		long start = System.currentTimeMillis();
		logger.warn("栏目获取期刊开始" + System.currentTimeMillis());
		String resultJosn = getHtml(map, searchJournalByColumn);
		long end = System.currentTimeMillis();
		logger.warn("栏目获取期刊结束" + end + " 共耗时:" + (end - start) + "ms！");
		ResponseParam sd = new ResponseParam();
		List<Journal> result = new ArrayList<Journal>();
		long count = 0;
		if(resultJosn!=null && !resultJosn.equals("")){
		    sd = gson.fromJson(resultJosn, ResponseParam.class);
	        result = gson.fromJson(sd.getJsonData(), new TypeToken<List<Journal>>() {}.getType());
	        count = sd.getCount();
		}
        if (CommonUtils.isNotEmpty(result)) {
            for (Journal journal : result) {
            	journal.setUpdateDate(DateTool.stampToDate(journal.getUpdateTime()));
            }
        }
		Map<String, Object> page = new HashMap<String, Object>();
		page.put("list", result);
		page.put("count", count);
		page.put("pageSize", pageSize);
		page.put("pageNo", pageNo);
		page.put("pageCount", ((count % pageSize == 0) ? (count / pageSize)
				: ((count / pageSize) + 1)));
		return page;
	}
/**
 * 组装搜索条件
 * @param templist 条件
 * @param deleteTag 
 * @return
 */
	public  SearchParam  getJournalParam(List<ContionParam> templist,int deleteTag){
		Gson gson = new Gson();
		List<JournalSearchCondition> contionList = new ArrayList <JournalSearchCondition>();
	    List<String> topList = new ArrayList<String>();//置顶信息
		for (ContionParam wp : templist) {
			JournalSearchCondition contion = new JournalSearchCondition();
			JournalSearchCondition contion1 = new JournalSearchCondition();
			List<String> labelMust = new ArrayList<String>();//必须包含的标签
		    List<String> labelShould = new ArrayList<String>();//可以包含的标签
		    List<String> labelNot = new ArrayList<String>();//不能包含的标签
		    List<String> nameMust = new ArrayList<String>();//必须包含期刊名称（标题）
		    List<String> nameShould = new ArrayList<String>();//可以包含期刊名称（标题
		    List<String> nameNot = new ArrayList<String>();//不能包含期刊名称（标题）
		    List<String> journalIdNot = new ArrayList<String>();//不显示的
			contion.setLabelMust(labelMust);
			Condition condition = gson.fromJson(wp.getConditions(),
					Condition.class);
			if (condition == null) {
				continue;
			}
			// 关键词
			boolean flag = condition.getFlag() == 0 ? true : false;
			if (StringUtils.isNotBlank(condition.getMustTagNames())) {
				for (String s : condition.getMustTagNames().split("_")) {
					labelMust.add(s);
				}			
			}
			if (StringUtils.isNotBlank(condition.getShouldTagNames())) {
				for (String s : condition.getShouldTagNames().split("_")) {
					labelShould.add(s);
				}
			}
			if (StringUtils.isNotBlank(condition.getMustNotTagNames())) {
				for (String s : condition.getMustNotTagNames().split("_")) {
					labelNot.add(s);
				}
			}
			// and关键词
			if (StringUtils.isNotBlank(condition.getMustWordNames())) {
				String[] temp = condition.getMustWordNames().split("_");
				for (String str : temp) {
					nameMust.add(str);
				}
			}
			// OR关键词
			if (StringUtils.isNotBlank(condition.getShouldWordNames())) {
				String[] temp = condition.getShouldWordNames().split("_");
				for (String str : temp) {
					nameShould.add(str);
				}
			}
			// not关键词
			if (StringUtils.isNotBlank(condition.getMustNotWordNames())) {
				String[] temp = condition.getMustNotWordNames().split("_");
				for (String str : temp) {
					nameNot.add(str);
				}
			}
			//置顶
			if (StringUtils.isNotBlank(wp.getTopList())){
				String[] temp = wp.getTopList().split(",");
				for (String str : temp){
					topList.add(str);
				}
			}
			//不显示
			if (StringUtils.isNotBlank(wp.getDelList())){
				String[] temp = wp.getDelList().split(",");
				for (String str : temp){
					journalIdNot.add(str);
				}
			}
			if (deleteTag == 1){//
				contion.setDeleteTag("true");
				contion1.setDeleteTag("true");
			}
			contion.setLabelMust(labelMust);
			contion.setLabelShould(labelShould);
			contion.setLabelNot(labelNot);
			contion.setNameMust(nameMust);
			contion.setNameShould(nameShould);
			contion.setNameNot(nameNot);
			contion.setJournalIdNot(journalIdNot);
			contionList.add(contion);
			if (flag){//全文
				contion1.setLabelMust(labelMust);
				contion1.setLabelShould(labelShould);
				contion1.setLabelNot(labelNot);
				contion1.setKeyWordMust(nameMust);
				contion1.setKeyWordShould(nameShould);
				contion1.setKeyWordNot(nameNot);
				contion1.setJournalIdNot(journalIdNot);
				contionList.add(contion1);
				
			}
		}//for结束
		SearchParam searchParam=new SearchParam();
		searchParam.setJsonData(contionList);
		//去掉置顶条件2015-12-28
		//searchParam.setTopList(topList);
		return searchParam;
	}
	
	//设置高亮
		public void sethighlight(List<Document> data,String keyword){
			if(data!=null&&!data.isEmpty()){
				for(Document doc :data){
					String name = doc.getArticleName();
					String skip = doc.getArticleSkip();
					String key =doc.getArticleKeyWord();
					List<String> strs = Arrays.asList(keyword.trim().split(" "));
					doc.setArticleTitle(name);
					for(String str : strs){
						if(null!=name){
							str=str.trim();
							if(!"".equals(str)){
							   name = name.replaceAll(str, "<span style='color:red;'>"+str+"</span>");
							}
						}
						if(null!=skip){
							str=str.trim();
							if(!"".equals(str)){
							   skip = skip.replaceAll(str, "<span style='color:red;'>"+str+"</span>");
							}
						}
						if(null!=key){
							str=str.trim();
							if(!"".equals(str)){
							   key = key.replaceAll("  "," ");
							}
						}
					
					}
					doc.setArticleName(name);
					doc.setArticleSkip(skip);
					doc.setArticleKeyWord(key);
				}
			}
		}
	  //将title取消高亮
		public void setDocTitle(List<Document> data){
			if(data!=null&&!data.isEmpty()){
				for(Document doc :data){
					String name = doc.getArticleName();
					if(null!=name){
						name = name.replaceAll("<em>","");
						name =name.replaceAll("</em>", "");
					}				
					doc.setArticleTitle(name);
				}
			}
		}
	
	
		public void setJourHighLight(List<EsJournal> data,String keyword){
			if(data!=null&&!data.isEmpty()){
				for(EsJournal e:data){
					String name=e.getJournalName();
					e.setJournalTitle(name);
					List<String> strs = Arrays.asList(keyword.trim().split(" "));
					for(String str : strs){
						if(null!=name){
							str=str.trim();
							if(!"".equals(str)){
								name = name.replaceAll(str, "<font color=red>"+str+"</font>");
							}
						}
					}
					e.setJournalName(name);
				}
			}
		}
		 //更多页面标题高亮
		public String setMoreHighLight(String data,String keyword){
			if(data!=null&&!"".equals(data)){
				List<String> strs = Arrays.asList(keyword.trim().split(" "));
				for(String str : strs){
					if(null!=data){
						str=str.trim();
						if(!"".equals(str)){
							data = data.replaceAll(str, "<font color=red>"+str+"</font>");
						}
					}
				}
			}
			return data;
		}

	/**
	 * 前台栏目单个查询
	 * @param plateId
	 * @return
	 */
	public WordPlate getPlageById(Integer plateId) {
		// TODO Auto-generated method stub
		return wordPlateMapper.selectByPrimaryKey(plateId);
	}
	
	/**
	 * 根据userId获取用户资金信息
	 * @param userId
	 * @return WordUserMoney
	 */
	public WordUserMoney getUserMoneyByUserId(int userId){
		
		WordUserMoney wordUserMoney = null;
		WordUserMoneyExample example = new WordUserMoneyExample();
		example.or().andUserIdEqualTo(userId);
		List<WordUserMoney> list = wordUserMoneyMapper.selectByExample(example);
		if (list != null && list.size() == 1){
			wordUserMoney = list.get(0);
		}
		return wordUserMoney;
	}
	
	/**
	 * 根据文档ID获取用户资源信息
	 * @param userId
	 * @param articleId
	 * @return 
	 */
	public WordResource getWordResourceByArticleId(int userId,int articleId){
		
		WordResource wordResource = null;
		WordResourceExample example = new WordResourceExample();
		example.or().andArticleIdEqualTo(articleId).andUserIdEqualTo(userId);
		List<WordResource> list = wordResourceMapper.selectByExample(example);
		if (list != null && list.size() == 1){
			wordResource = list.get(0);
		}
		return wordResource;
		
	}
	
	/**
	 * 根据期刊ID和文档的时间获取用户资源信息
	 * @param userId
	 * @param journalId 
	 * @param articleJournalTime 文档的时间
	 * @return 
	 */
	public WordResource getWordResourceByJournalId(int userId,int journalId,Date articleJournalTime){
		
		WordResource wordResource = null;
		WordResourceExample example = new WordResourceExample();
		example.or().andUserIdEqualTo(userId).andJournalIdEqualTo(journalId).andEndTimeGreaterThan(articleJournalTime);
		List<WordResource> list = wordResourceMapper.selectByExample(example);
		if (list != null && list.size() == 1){
			wordResource = list.get(0);
		}
		return wordResource;
		
	}
	
	/**
	 * 根据文档ID、期刊ID以及文档的时间验证文档是否已购买
	 * @param articleId
	 * @param journalId 
	 * @param articleJournalTime 文档的时间
	 */
	public boolean getArticleBuyStatus(int userId,Integer articleId,Integer journalId,Date articleJournalTime){
		
		boolean buyStatus = false;
		//首先根据文档ID去用户资源表验证该文档是否已购买
		WordResource wordResource = getWordResourceByArticleId(userId,articleId);
		if (wordResource != null){
			buyStatus = true;
		}else{
			if (journalId != null && journalId != 0 && articleJournalTime != null){
				//如果该文档属于期刊文档，则根据期刊ID去用户表验证该文档所属期刊是否已购买，并且该文档在购买的期刊的有效期内
				WordResource resource = getWordResourceByJournalId(userId, journalId, articleJournalTime);
				if (resource != null){
					buyStatus = true;
				}
			}
		}
		return buyStatus;
	}
	
	/**
	 * 根据期刊ID以及当前时间验证期刊是否已购买、或是否已过期
	 * @param journalId 
	 * @param articleJournalTime 文档的时间
	 */
	public boolean getJournalBuyStatus(int userId,Integer journalId){
		
		boolean buyStatus = false;
		WordResource resource = getWordResourceByJournalId(userId, journalId, new Date());
		if (resource != null){
			buyStatus = true;
		}

		return buyStatus;
	}
	
	/**
	 * 判断文档是否已加入购物车
	 */
	public boolean getArticleCart(int userId,Integer articleId){
		
		boolean status = false;
		WordShoppingCartExample example = new WordShoppingCartExample();
		example.or().andPayFlagEqualTo(0).andUserIdEqualTo(userId).andArticleIdEqualTo(articleId);
		List<WordShoppingCart> list = wordShoppingCartMapper.selectByExample(example);
		if (list != null && list.size() > 0){
			status = true;
		}
		return status;
	}
	
	/**
	 * 判断期刊是否已加入购物车
	 */
	public boolean getJournalCart(int userId,Integer journalId){
		
		boolean status = false;
		WordShoppingCartExample example = new WordShoppingCartExample();
		example.or().andPayFlagEqualTo(0).andUserIdEqualTo(userId).andResoureTypeEqualTo("journal").andJournalIdEqualTo(journalId);
		List<WordShoppingCart> list = wordShoppingCartMapper.selectByExample(example);
		if (list != null && list.size() > 0){
			status = true;
		}
		return status;
	}
	
	/**
	 * 根据articleId以及pageId获取文档样式 类型为style
	 * @param articleId
	 * @return
	 */
	public List<WordArticleHtml> getWordArticleStyles(Integer articleId) {
		
		WordArticleHtmlExample example = new WordArticleHtmlExample();
		//类型为style的pageId始终为 0,且一篇文章只有一个 style
		example.createCriteria().andArticleIdEqualTo(articleId).andContentTypeEqualTo("style");
		List<WordArticleHtml> list = wordArticleHtmlMapper.selectByExampleWithBLOBs(example);
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	
	/**
	 * 根据articleId以及pageId获取文档内容 类型为html
	 * @param articleId
	 * @param startPageId
	 * @return
	 */
	public List<WordArticleHtml> getWordArticleContents(Integer articleId,Integer startPage, Integer endPage) {

		WordArticleHtmlExample example = new WordArticleHtmlExample();
		example.createCriteria().andArticleIdEqualTo(articleId).andContentTypeEqualTo("html").andPageIdBetween(startPage, endPage);
		List<WordArticleHtml> list = wordArticleHtmlMapper.selectByExampleWithBLOBs(example);
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	
	/**
	 * 判断文档的阅读权限
	 * 
	 */
	public boolean getArticlePower(WordUsers user,Article article){
		
		boolean power = false;
		if (article != null){
			if (article.getArticlePrice() == null || article.getArticlePrice() <= 0){
				power = true;
			}else{
				if (user != null){
					//先判断该文档属否属于当前登录人自己的
					if (user.getUserId().equals(article.getUserId())){
						power = true;
					}else{
						//根据文档ID、期刊ID以及文档的时间验证文档是否已购买
						power = getArticleBuyStatus(user.getUserId(), article.getArticleId(), article.getArticleJournalId(), article.getArticleJournalTime());
					}
				}
			}
		}		
		return power;
	}
}
