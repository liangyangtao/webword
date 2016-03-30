package com.web.document.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.export.Function;
import com.google.gson.Gson;
import com.lucene.UnbankList;
import com.lucene.entity.doc.Crawl;
import com.lucene.entity.doc.Crawls;
import com.util.DateTool;
import com.web.document.bean.GrabContent;
import com.web.document.bean.News;
import com.web.document.bean.NewsCrawls;
import com.web.document.bean.SearchBean;
import com.web.document.bean.SearchCondition;
import com.web.document.bean.SearchConditionBean;
import com.web.utils.Fetcher;
import com.web.utils.HttpClientBuilder;

@Service
public class RiskService{
		
	public static Logger logger = Logger.getLogger(RiskService.class);
	
	@Value("${riskByJson}")
	private String riskByJson;
	
	@Value("${riskByNewsId}")
	private String riskByNewsId;
	
	@Value("${riskByMulti}")
	private String riskByMulti;
	
	@Value("${newsByJson}")
	private String newsByJson;
	
	@Value("${newsByNewsId}")
	private String newsByNewsId;
	
	@Value("${newsByMulti}")
	private String newsByMulti;
	
	@Value("${riskSearchByEsId}")
	private String riskSearchByEsId;
	
	@Value("${picUrl}")
	private String picUrl;
	
	/**
	 * 普通搜索
	 * @param userId
	 * @param record
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public UnbankList<Crawl> searchNomal(int userId,String record,int pageSize,int currentPage,String type){
		UnbankList<Crawl> list=null;
		try{
			String[] musts = Function.returnArray(record);
			//System.out.println("currentPage="+currentPage+",pageSize="+pageSize);
			SearchCondition searchcondition = new SearchCondition();
			SearchCondition newCondition = new SearchCondition();
			//根据栏目id 查询约束条件，0为全部
			//must 标签
			List<String> mustTag = new ArrayList<String>();
			List<String> highList = new ArrayList<String>();
			///必须包含
			for(String m:musts){
				mustTag.add(m);
				highList.add(m);
			}
			if(null!=mustTag&&mustTag.size()>0){
				//searchcondition.addshouldContentWords(mustTag);
				newCondition.addshouldTitleWords(mustTag);
				//newCondition.addmustTitleWords(mustTag);
				searchcondition.addmustContentWords(mustTag);
				//searchcondition.addmustTitleWords(mustTag);
			}
			List<SearchCondition> searchConditionList = new ArrayList<SearchCondition>();
			searchConditionList.add(searchcondition);
			searchConditionList.add(newCondition);
			//http返回 searchByMultiJsonStr searchByJsonStr
			//list= httpReturn(searchcondition,searchByJsonStr);
			//排序方式
			String orderByMode = "SCORE";
			int from = (currentPage-1)*pageSize;
			String startTime=DateTool.getOneDay(new Date(),-30).getTime()+"";
			String endTime = DateTool.getOneDay(new Date(),1).getTime()+"";
			boolean fullContent=false;
			//list = httpReturn(searchByJsonStr,searchcondition,from,pageSize,startTime,endTime,orderByMode,fullContent);
			String urlStr=newsByMulti;
			if("risk".equals(type)){
				urlStr=riskByMulti;
			}
			list = httpAdvancedReturn(urlStr,null,searchConditionList,from,pageSize,startTime,endTime,orderByMode,fullContent);
			//高亮显示
			highLighted(list,highList);
		}catch(Exception e){
			logger.error("搜索新闻="+e.getMessage());
		}
		return list;
	}
	/**
	 * 高级搜索-插件对外
	 * @param userId
	 * @param must 必须包含
	 * @param arbitrarily 必须包含任意一个
	 * @param not 必须不包含
	 * @param startdate 开始日期
	 * @param enddate   结束日期
	 * @param pageSize 页面大小
	 * @param currentPage 当前页
	 * @param searchsource 搜索源 1全文 2标题
	 * @param sorttype 排序1时间 2权重
	 * @param fromsource 新闻来源
	 * @param type risk:风险信息
	 * @return
	 */
	public UnbankList<Crawl> searchPlugin(int userId,String must,String arbitrarily,String not,Date startdate,Date enddate,int pageSize,int currentPage,int searchsource,int sorttype,String fromsource,String type){
		UnbankList<Crawl> list=null;
		SearchBean asBean = new SearchBean();
		asBean.setUserId(userId);
		asBean.setMust(must);
		asBean.setArbitrarily(arbitrarily);
		asBean.setNot(not);
		asBean.setStartTime(startdate);
		asBean.setEndTime(enddate);
		
		asBean.setSearchSource(searchsource);
		
		asBean.setSort(sorttype);
		asBean.setFromSource(fromsource);
		asBean.setFullContent(true);
		list=searchAdvanced(asBean,currentPage,pageSize,type);
		return list;
	}
	/**
	 * 高级搜索-对外
	 * @param asBean
	 * @param currentPage:当前页 1
	 * @param pageSize :页面 大小 10
	 * @return
	 */
	public UnbankList<Crawl> searchAdvanced(SearchBean asBean,int currentPage,int pageSize,String type){
		//asBean.setHigh(true);//高亮显示
		return search(asBean,currentPage,pageSize,type);
	}
	/**
	 * 搜索
	 * @param asBean
	 * @param currentPage:当前页 1
	 * @param pageSize :页面 大小 10
	 * @return
	 */
	public UnbankList<Crawl> search(SearchBean asBean,int currentPage,int pageSize,String type){
		
		String all = asBean.getMust();
		String any = asBean.getArbitrarily();
		String not = asBean.getNot();
		int part = asBean.getSearchSource();//新闻或者标题 1全文 2标题
		int sort = asBean.getSort();//排序方式 排序1时间 2权重
		String source = asBean.getFromSource();//新闻源,|隔开

		if(source == null) source = "";
		if(source.indexOf("|") != -1) source = StringUtils.replace(source, "|"," ");
		//必须包含
		List<String> mustList = new ArrayList<String>();
		//应该包含
		List<String> anyList =  new ArrayList<String>();
		//不包含
		List<String> notList = new ArrayList<String>();
		//新闻源
		List<String> webNameList = new ArrayList<String>();
		//分词标签
		List<String> highList = new ArrayList<String>();
		
		arrayToList(all,mustList,highList);
		arrayToList(any,anyList,highList);
		arrayToList(not,notList,null);
		arrayToList(source,webNameList,null);
		//排序方式
		SearchCondition searchcondition = new SearchCondition();
		SearchCondition newCondition = new SearchCondition();
		//必须包含
		if(mustList.size()>0){
			searchcondition.addmustTitleWords(mustList);
			if(part==1){//全文搜索
				//newCondition.addshouldContentWords(mustList);
				newCondition.addmustContentWords(mustList);
			}
		}
		//可能包含
		if(anyList.size()>0){
			searchcondition.addshouldTitleWords(anyList);
			if(part==1){//全文搜索
				newCondition.addshouldContentWords(anyList);
			}
		}
		//不包含
		if(notList.size()>0){
			searchcondition.addmustNotTitleWords(notList);
			if(part==1){//全文
				newCondition.addmustNotContentWords(notList);
			}
		}
		//新闻源
		if(webNameList.size()>0){
			//searchcondition.setMustWebNames(webNameList);
			//newCondition.setMustWebNames(webNameList);
			searchcondition.setShouldWebNames(webNameList);
			newCondition.setShouldWebNames(webNameList);
		}
		String startTime=null;
		String endTime = null;
		//开始时间
		if(asBean.getStartTime() != null){
			//System.out.println(""+asBean.getStartTime());
			    try {
					Calendar c = Calendar.getInstance(); 
					c.setTime(asBean.getStartTime());
					startTime=c.getTimeInMillis()+"";
					//System.out.println("startTime="+startTime);
				} catch (Exception e) {
					logger.error("开始时间出错="+asBean.getStartTime());
				}
		}
		//结束时间
		if(asBean.getEndTime() != null){
			//System.out.println(""+asBean.getEndTime());
			    try {
					Calendar c = Calendar.getInstance(); 
					c.setTime(asBean.getEndTime());
					endTime = (c.getTimeInMillis()+86400000)+"";
					//System.out.println("endTime="+endTime);
				} catch (Exception e) {
					logger.error("结束时间出错="+asBean.getEndTime());
				}
		}
		
		//http返回 searchByMultiJsonStr
		//排序方式
		String orderByMode="SCORE";
		if(sort==1){//时间
			orderByMode="TIME";
		}
		int from = (currentPage-1)*pageSize;
		boolean fullContent=asBean.isFullContent();
		
		List<SearchCondition> searchConditionList = new ArrayList<SearchCondition>();
		searchConditionList.add(searchcondition);
		if(part==1){//全文搜索
			searchConditionList.add(newCondition);
		}
		//UnbankList<Crawl> list= httpReturn(searchByJsonStr,searchcondition,from,pageSize,startTime,endTime,orderByMode,fullContent);
		String urlStr=newsByMulti;
		if("risk".equals(type)){
			urlStr=riskByMulti;
		}
		UnbankList<Crawl> list = httpAdvancedReturn(urlStr,null,searchConditionList,from,pageSize,startTime,endTime,orderByMode,fullContent);
		//高亮显示
		if(asBean.isHigh()){
			highLighted(list,highList);
		}
		return list;
	}
	/**
	 * 高级搜索
	 * @param userId
	 * @param must 必须包含
	 * @param arbitrarily 必须包含任意一个
	 * @param not 必须不包含
	 * @param startdate 开始日期
	 * @param enddate   结束日期
	 * @param pageSize 页面大小
	 * @param currentPage 当前页
	 * @param searchsource 搜索源 1全文 2标题
	 * @param sorttype 排序1时间 2权重
	 * @param fromsource 新闻来源
	 * @param isHigh 是否高亮 true 高亮
	 */
	public UnbankList<Crawl> search(int userId,String must,String arbitrarily,String not,String startdate,String enddate,int pageSize,int currentPage,int searchsource,int sorttype,String fromsource,boolean isHigh,String type){
		if(fromsource == null) fromsource = "";
		if(fromsource.indexOf("|") != -1) fromsource = StringUtils.replace(fromsource, "|"," ");
		//必须包含
		List<String> mustList = new ArrayList<String>();
		//应该包含
		List<String> anyList =  new ArrayList<String>();
		//不包含
		List<String> notList = new ArrayList<String>();
		//新闻源
		List<String> webNameList = new ArrayList<String>();
		//分词标签
		List<String> highList = new ArrayList<String>();
		
		arrayToList(must,mustList,highList);
		arrayToList(arbitrarily,anyList,highList);
		arrayToList(not,notList,null);
		arrayToList(fromsource,webNameList,null);
		
		SearchCondition searchcondition = new SearchCondition();
		//必须包含
		if(mustList.size()>0){
			searchcondition.addmustTitleWords(mustList);
			if(searchsource==1){//全文搜索
				searchcondition.addmustContentWords(mustList);
			}
		}
		//可能包含
		if(anyList.size()>0){
			searchcondition.addshouldTitleWords(anyList);
			if(searchsource==1){//全文搜索
				searchcondition.addshouldContentWords(anyList);
			}
		}
		//不包含
		if(notList.size()>0){
			searchcondition.addmustNotTitleWords(notList);
			if(searchsource==1){//全文
				searchcondition.addmustNotContentWords(notList);
			}
		}
		//新闻源
		if(webNameList.size()>0){
			//searchcondition.setMustWebNames(webNameList);
			searchcondition.setShouldWebNames(webNameList);
		}
		String startTime=null;
		String endTime = null;
		//开始时间
		if(startdate != null && startdate.length() > 0){
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			    try {
					Date date = sdf.parse(startdate);
					Calendar c = Calendar.getInstance(); 
					c.setTime(date);
					startTime=c.getTimeInMillis()+"";
				} catch (Exception e) {
					logger.error("转换时间出错="+startdate);
				}
		}
		//结束时间
		if(enddate != null && enddate.length() > 0){
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			    try {
					Date date = sdf.parse(enddate);
					Calendar c = Calendar.getInstance(); 
					c.setTime(date);
					endTime=(c.getTimeInMillis()+86400000)+"";
				} catch (Exception e) {
					logger.error("转换时间出错="+enddate);
				}
		}
		
		//排序方式
		String orderByMode="SCORE";
		if(sorttype==1){//时间
			orderByMode="TIME";
		}
		int from = (currentPage-1)*pageSize;
		boolean fullContent=false;
		//http返回 searchByMultiJsonStr
		String urlStr=newsByJson;
		if("risk".equals(type)){
			urlStr=riskByJson;
		}
		UnbankList<Crawl> list= httpReturn(urlStr,searchcondition,from,pageSize,startTime,endTime,orderByMode,fullContent);
		//高亮显示
		if(isHigh){
			highLighted(list,highList);
		}
		return list;
	}
	/**
	 * 返回新闻详情
	 * @param crawlId
	 * @param categoryId
	 * @return
	 */
	public GrabContent getNewsByIds(int crawlId,String categoryId,String path,String esId){
		GrabContent content = new GrabContent();
		try{
			Map<String,String> map = new HashMap<String,String>();
			map.put("crawl_id", Integer.toString(crawlId));
			map.put("categoryId", categoryId);
			map.put("esId", esId);
			String strType="";
			if(!("0".equals(esId))){
				strType="risk";
			}
			content=getHttpByMap(map,strType);
			try {
				content.setText(Function.replaceImg(content.getText(),path,picUrl));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				logger.error("替换搜索的图片出错");
			}
		}catch(Exception e){
			logger.error("获取新闻详情出错="+e.getMessage());
		}
		return content;
	}
	
	/**
	 * 字符串 空拆分转化成数组
	 * @param must
	 * @param mustList
	 * @param highList
	 */
	public void arrayToList(String must,List<String> mustList,List<String> highList){
		if(must==null||"".equals(must)) return;
		//must=RegexTest.replaceAllSpace(must);//删除多余的空格
		//String[] musts=new String[0];
		//if(!must.trim().equals("")) musts=must.trim().split(" ");//删除开始 或者结尾的空格
		String[] musts = Function.returnArray(must);
		//mustList=java.util.Arrays.asList(musts);
		for(String m:musts){
			mustList.add(m);
			if(highList!=null){
				highList.add(m);
			}
		}
	}
	/**
	 * 发送url请求
	 * @param params
	 * @param url
	 * @return 结果的字符串
	 */
	private String getHttp(Map<String, String> params ,String url) {

		PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
		BasicCookieStore cookieStore = new BasicCookieStore();
		HttpClientBuilder httpClientBuilder = new HttpClientBuilder(false,poolingHttpClientConnectionManager, cookieStore);
		CloseableHttpClient httpClient = httpClientBuilder.getHttpClient();
		Fetcher fetcher1 = new Fetcher(cookieStore, httpClient);
		String html ="";
		try {
				html = fetcher1.post(url, params, null, "utf-8");
				} catch (Exception e) {
					logger.error("http出错,url="+url);
					logger.error("e="+e.getMessage());
				}
		return html;
	}
	/**
	 * 高级搜索
	 * @param url
	 * @param searchcondition
	 * @param searchConditionList
	 * @param from 分页起始（默认为0）
	 * @param pageSize
	 * @param startTime
	 * @param endTime
	 * @param orderByColumn
	 * @param fullContent
	 * @return
	 */
	private UnbankList<Crawl> httpAdvancedReturn(String url,SearchCondition searchcondition,List<SearchCondition> searchConditionList,int from,int pageSize,String startTime,String endTime,String orderByColumn,boolean fullContent){
		
		Crawls list =  new Crawls(1);
		Gson gson = new Gson();
		Map<String,String> map = new HashMap<String,String>();
		map.put("jsonStr", gson.toJson(searchConditionList));
		if(searchcondition!=null){
			map.put("filterJsonStr", gson.toJson(searchcondition));
		}
		map.put("from", Integer.toString(from));
		map.put("pageSize",Integer.toString(pageSize));
		
		if(startTime!=null&&startTime.length()!=0){
			map.put("startTime",startTime);
		}
		if(endTime!=null&&endTime.length()>0){
			map.put("endTime",endTime);
		}
		map.put("orderByColumn",orderByColumn);//"TIME"/"SCORE
		
		if(fullContent){
			map.put("fullContent","true");
		}
		String result =  getHttp(map,url);
		//System.out.println(result);
		NewsCrawls newsCrawls = gson.fromJson(result, NewsCrawls.class);
		
		list.setTotalCount(newsCrawls.getCount());
		List<News> data = newsCrawls.getData();
		for(News news:data){
			Crawl crawl = new Crawl();
			crawl.setCrawlId(news.getCrawl_id());
			crawl.setCrawlBrief(news.getContent());
			crawl.setCrawlTitle(news.getTitle());
			crawl.setText(news.getContent());
			crawl.setNewsTime(news.getNewsDate());
			crawl.setCategoryIds(news.getCategoryId());
			crawl.setEsId(news.getEsId());
			String[] CategoryId = Function.returnArray(news.getCategoryId());
			if(CategoryId.length>0){
				crawl.setCategoryId(Integer.parseInt(CategoryId[CategoryId.length-1]));
			}
			//crawl.setCrawlTime(time);
			list.add(crawl);
		}
		return list;
	}
	/**
	 * 普通搜索
	 * @param url
	 * @param searchcondition
	 * @param from 分页起始（默认为0）
	 * @param pageSize
	 * @param startTime
	 * @param endTime
	 * @param orderByColumn 排序方式
	 * @param fullContent 
	 * @return
	 */
	private UnbankList<Crawl> httpReturn(String url,SearchCondition searchcondition,int from,int pageSize,String startTime,String endTime,String orderByColumn,boolean fullContent){
		Crawls list =  new Crawls(1);
		Gson gson = new Gson();
		Map<String,String> map = new HashMap<String,String>();
		map.put("jsonStr", gson.toJson(searchcondition));
		map.put("from", Integer.toString(from));
		map.put("pageSize",Integer.toString(pageSize));
		
		if(startTime!=null&&startTime.length()>0){
			map.put("startTime",startTime);
		}
		if(endTime!=null&&endTime.length()>0){
			map.put("endTime",endTime);
		}
		map.put("orderByColumn",orderByColumn);//"TIME"/"SCORE
		
		if(fullContent){
			map.put("fullContent","true");
		}
		String result =  getHttp(map,url);
		//System.out.println(result);
		NewsCrawls newsCrawls = gson.fromJson(result, NewsCrawls.class);
		
		list.setTotalCount(newsCrawls.getCount());
		List<News> data = newsCrawls.getData();
		for(News news:data){
			Crawl crawl = new Crawl();
			crawl.setCrawlId(news.getCrawl_id());
			crawl.setCrawlBrief(news.getContent());
			crawl.setCrawlTitle(news.getTitle());
			crawl.setText(news.getContent());
			crawl.setNewsTime(news.getNewsDate());
			crawl.setCategoryIds(news.getCategoryId());
			crawl.setEsId(news.getEsId());
			String[] CategoryId = Function.returnArray(news.getCategoryId());
			if(CategoryId.length>0){
				crawl.setCategoryId(Integer.parseInt(CategoryId[CategoryId.length-1]));
			}
			//crawl.setCrawlTime(time);
			list.add(crawl);
		}
		return list;
	}
	/**
	 * 高亮显示
	 * @param list 新闻列表
	 * @param musts 关键词
	 * @return
	 */
	public UnbankList<Crawl> highLighted(UnbankList<Crawl> list,List<String> highList){
		List<Crawl> crawlList = list.getList();
		String title;//标题
		String brief;//简介
		for(Crawl crawl:crawlList){
			title = crawl.getCrawlTitle();
			brief = crawl.getCrawlBrief();
			for(String key:highList){
				//title = title.replaceAll("(?i)"+key, "<span>"+key+"</span>");
				//brief = brief.replaceAll("(?i)"+key, "<span>"+key+"</span>");
				title = Function.replaceHeight(title, key);;
				brief = Function.replaceHeight(brief, key);;
			}
			crawl.setCrawlTitle(title);
			crawl.setCrawlBrief(brief);
		}
		return list;
	}
	/**
	 * 获取新闻详情
	 * @param map
	 * @return
	 */
	public GrabContent getHttpByMap(Map<String,String> map,String strType){
		
		String urlStr=newsByNewsId;
		if("risk".equals(strType)){
			urlStr=riskSearchByEsId;
		}
		String result =  getHttp(map,urlStr);
		
		Gson gson = new Gson();
		News news = gson.fromJson(result, News.class);
		
		GrabContent content = new GrabContent();
		content.setCrawl_id(news.getCrawl_id());
		content.setTitle(news.getTitle());
		content.setText(news.getContent());
		return content;
		
	}
}
