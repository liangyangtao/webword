package com.web.document.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.database.bean.Ascondition;
import com.database.bean.AsconditionExample;
import com.database.bean.Content;
import com.database.bean.ContentExample;
import com.database.bean.ContentExample.Criteria;
import com.database.bean.ContentWithBLOBs;
import com.database.bean.WordLabel;
import com.database.bean.WordLabelExample;
import com.database.dao.AsconditionMapper;
import com.database.dao.ContentMapper;
import com.database.dao.WordLabelMapper;
import com.google.gson.Gson;
import com.lucene.CrawlWriter;
import com.lucene.UnbankList;
import com.lucene.UnbankWriter;
import com.lucene.entity.doc.Crawl;
import com.lucene.html.Function;
import com.web.Article.service.ArticleService;
import com.web.document.bean.AdvancedSearchConditionBean;
import com.web.document.bean.GrabContent;
import com.web.document.bean.SearchBean;
import com.web.homePage.util.HomePageUtil;

@Service
public class DocumentService {
	
	@Autowired
	private CrawlWriter crawlWriter;
	
	@Autowired
	private ContentMapper contentMapper ;
		
	@Autowired
	private UnbankWriter<com.lucene.entity.doc.Content> docWriter;
	
	@Autowired
	private AsconditionMapper asconditionMapper;
	
	@Autowired
	private RiskService riskService;
	
	@Autowired
	private ArticleService articleService;
	
	private static final Logger LOGGER = Logger.getLogger(DocumentService.class);
	
	/*
	 * 搜索新闻
	 */
	public Map<String, Object> searchNews(Integer userId,SearchBean searchBean){
		UnbankList<Crawl> list=null;
		
		//list = search(userId,searchBean);
		searchBean.setHigh(true);//高亮显示
		list = riskService.searchAdvanced(searchBean,searchBean.getPageId(),searchBean.getPageSize(),searchBean.getStrType());
		Map<String, Object> maps = new HashMap<String, Object>();
		int pageId = searchBean.getPageId();
		int pageSize = searchBean.getPageSize();
		int count =list.getTotalCount();
		//计算页数
		int pageCount=0;
		pageCount=count%pageSize==0?count/pageSize:count/pageSize+1;
		
		maps.put("pageCount",pageCount);
		maps.put("count", count);
		maps.put("pageId",pageId);
		maps.put("pageSize",pageSize);
		maps.put("list", list);
		//增加关键词
		try{
			String labels = searchBean.getMust().trim()+" "+searchBean.getArbitrarily().trim();
			articleService.insertlabels(labels.trim());
		}catch(Exception e){
			e.printStackTrace();
		}
		return maps;
	}
	/*
	 * 搜索新闻-通用类
	 * @oaram userId 用户ID
	 * @param searchBean 搜索的条件
	 */
	public UnbankList<Crawl> search(Integer userId,SearchBean searchBean){
		UnbankList<Crawl> list=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startTime="";
		String endTime ="";
		if(searchBean.getStartTime()!=null){
			startTime=sdf.format(searchBean.getStartTime());
		}
		if(searchBean.getEndTime() !=null){
			endTime = sdf.format(searchBean.getEndTime());
		}
		int type=searchBean.getType();
		String must = searchBean.getMust();
		if(must == null){
			must="";
		}else{
			must=com.util.RegexTest.replaceAllSpace(must);
		}
		String arbitrarily=searchBean.getArbitrarily();
		if(arbitrarily==null){
			arbitrarily="";
		}else{
			arbitrarily=com.util.RegexTest.replaceAllSpace(arbitrarily);
		}
		//System.out.println("+arbitrarily="+arbitrarily);
		String not = searchBean.getNot();
		if(not==null){
			not="";
		}else{
			not=com.util.RegexTest.replaceAllSpace(not);
		}
		int pageId= searchBean.getPageId();
		int pageSize = searchBean.getPageSize();
		int searchsource= searchBean.getSearchSource();
		int sort=searchBean.getSort();
		String fromsource=searchBean.getFromSource();
		if(fromsource!=null) fromsource=fromsource.replaceAll("\\|",",");
		//LOGGER.info("pageId="+pageId+",pageSize="+pageSize);
		//LOGGER.info("startTime="+startTime+",endTime="+endTime+",must="+must+",arbitrarily="+arbitrarily+",not="+not+",pageSize="+pageSize);
		//LOGGER.info("searchsource="+searchsource+",sort="+sort+",fromsource="+fromsource);
		
		try {
			list = crawlWriter.search(type,userId,must,arbitrarily ,not,startTime, endTime,pageSize, pageId,searchsource,sort,fromsource);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	
	/**
	 * 根据新闻名称查找内容
	 * <p>Title: queryContentByName</p> 
	 * <p>Description: TODO</p>
	 * @param contentName
	 * @param userId
	 * @return
	 */
	public String queryContentByName(String contentName, int userId) {
		String contentText = "";
		ContentExample contentExample = new ContentExample();
		Criteria cr =contentExample.createCriteria();
		cr.andUserIdEqualTo(userId);
		cr.andContentNameEqualTo(contentName);
		List<ContentWithBLOBs> list = contentMapper.selectByExampleWithBLOBs(contentExample);
		if(list.size()>0){
			contentText = list.get(0).getContent();
			contentText = Function.removeCharacterEntity(contentText);
		}
		return contentText;
	}
	
	/**
	 * 根据新闻Id查询新闻内容
	 * <p>Title: queryContentById</p> 
	 * <p>Description: TODO</p>
	 * @param newsId
	 * @return
	 */
	public ContentWithBLOBs queryContentById(int newsId) {
		//String contentText = "";
		ContentWithBLOBs content = contentMapper.selectByPrimaryKey(newsId);
		/*if(content != null){
			contentText = content.getContent();
			contentText = Function.removeCharacterEntity(contentText);
		}*/
		return content;
	}
	
	/**
	 * 根据crawlId查询新闻内容
	 * <p>Title: queryContentByCrawlId</p> 
	 * <p>Description: TODO</p>
	 * @param crawlId
	 * @return
	 */
	public GrabContent queryContentByCrawlId(int crawlId,String categoryId,String path,String esId) {
		//String contentText = "";
		//根据新闻id抓取内容
		//GrabContent grabContent = contentMapper.queryGrabContentText(crawlId);
		GrabContent grabContent =  riskService.getNewsByIds(crawlId,categoryId,path,esId);
		/*if(grabContent != null){
			contentText = grabContent.getText();
			contentText = Function.removeCharacterEntity(contentText);
		}*/
		return grabContent;
	}
	/**
	 * 保存为我的内容
	 * <p>Title: addContent</p> 
	 * <p>Description: TODO</p>
	 * @param crawlId
	 * @param userId
	 * @param contentName
	 * @throws Exception
	 */
	public void addContent(int crawlId,int userId,String contentName,String categoryId,String path,String esId) throws Exception{
		DateFormat dd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//根据新闻id抓取内容
		//GrabContent grabContent = contentMapper.queryGrabContentText(crawlId);
		GrabContent grabContent =  riskService.getNewsByIds(crawlId,categoryId,path,esId);
		//保存为我的内容
		ContentWithBLOBs record = new ContentWithBLOBs();
		record.setContentId(crawlId);
		record.setContentName(contentName);
		record.setContent(grabContent.getText());
		record.setUserId(userId);
		record.setInsertTime(Timestamp.valueOf(dd.format(new Date())));
		int contentId = contentMapper.saveContent(record);
		
		//将与节点挂钩的内容放入lucene中
		/*
		com.lucene.entity.doc.Content content = contentMapper.selectnewContent(contentId);
		if(content != null){
			String text = Function.removeCharacterEntity(content.getText());
			int len = text.length();
			content.setContentBrief(text.substring(0,len > 80 ? 80 : len));
			content.setContentInsertTime(content.getContentInsertTimeTemp().getTime());
			docWriter.addDocument1(content);
		}*/
	}
	
	/**
	 * 查询我的内容
	 * <p>Title: searchMyNews</p> 
	 * <p>Description: TODO</p>
	 * @param userId
	 * @return
	 */
	public Map<String, Object> searchMyNews(int userId,String contentName,int pageId,int pageSize) {
		ContentExample contentExample = new ContentExample();
		Criteria cr =contentExample.createCriteria();
		cr.andUserIdEqualTo(userId);
		if(contentName != null && !"".equals(contentName)){
			cr.andContentNameLike("%"+contentName+"%");
		}
		int count =contentMapper.countByExample(contentExample);
		int start = (pageId-1)*pageSize;
		contentExample.setLimitStart(start);
		contentExample.setLimitEnd(pageSize);
		contentExample.setOrderByClause("update_time desc");
		List<Content> list = contentMapper.selectByExample(contentExample);
		Map<String, Object> maps = new HashMap<String, Object>();
		
		//计算页数
		int pageCount=0;
		pageCount=count%pageSize==0?count/pageSize:count/pageSize+1;
		
		maps.put("pageCount",pageCount);
		maps.put("count", count);
		maps.put("pageId",pageId);
		maps.put("pageSize",pageSize);
		maps.put("list", list);
		return maps;
	}
	/**
	 * 删除我的新闻
	 * @param userId
	 * @param id
	 * @return
	 */
	public Map<String,Object> newsDelById(int userId ,int id){
		Map<String, Object> maps = new HashMap<String, Object>();
		ContentWithBLOBs content = contentMapper.selectByPrimaryKey(id);
		if(content!=null){
			if(content.getUserId()==userId){
				int flag = contentMapper.deleteByPrimaryKey(id);
				maps.put("state","success");
				maps.put("info", "删除成功");
				maps.put("flag",flag);
			}else{
				maps.put("state","error");
				maps.put("info", "不是此用户的新闻");
			}
		}else{
			maps.put("state","error");
			maps.put("info", "没有此记录");
		}
		return maps;
	}
	
	/**
	 * 保存高级检索条件
	 * <p>Title: insertAdvCondition</p> 
	 * <p>Description: TODO</p>
	 * @param ascondition
	 * @param userId
	 * @return
	 */
	public int insertAdvCondition(String ascondition,Integer userId){
		Gson gson = new Gson();
		AdvancedSearchConditionBean asBean = gson.fromJson(ascondition,AdvancedSearchConditionBean.class);
		asBean.setUser_id(userId);
		AsconditionExample example = new AsconditionExample();
		AsconditionExample.Criteria cr = example.createCriteria();
		if(asBean.getAscondition_stime() == null || "".equals(asBean.getAscondition_stime())){
			asBean.setAscondition_stime(null);
		}else{
			cr.andAsconditionStimeEqualTo(HomePageUtil.stringToFormatTimeStampStart(asBean.getAscondition_stime()));
		}
		if(asBean.getAscondition_etime() == null || "".equals(asBean.getAscondition_etime())){
			asBean.setAscondition_etime(null);
		}else{
			cr.andAsconditionEtimeEqualTo(HomePageUtil.stringToFormatTimeStampEnd(asBean.getAscondition_etime()));
		}
		if(asBean.getAscondition_all() == null ){
			asBean.setAscondition_all("");
		} else{
			cr.andAsconditionAllEqualTo(asBean.getAscondition_all());
		}
		if(asBean.getAscondition_any() == null ){
			asBean.setAscondition_any("");
		}else{
			cr.andAsconditionAnyEqualTo(asBean.getAscondition_any());
		}
		if(asBean.getAscondition_nocontains() == null ){
			asBean.setAscondition_nocontains("");
		}else{
			cr.andAsconditionNocontainsEqualTo(asBean.getAscondition_nocontains());
		}
		if(asBean.getAscondition_source() == null ){
			asBean.setAscondition_source("");
		}
		List<Ascondition> list = asconditionMapper.selectByExample(example);
		int asconditionId = 0;
		if(list.size() == 0){
			asconditionId = asconditionMapper.insertAdvCondition(asBean);
		}
		return asconditionId;
	}
	
	/**
	 * 搜索高级检索条件
	 * <p>Title: searchAdvCondition</p> 
	 * <p>Description: TODO</p>
	 * @param userId
	 * @param start
	 * @param limit
	 * @param type
	 * @return
	 */
	public List<AdvancedSearchConditionBean> searchAdvCondition(Integer userId,Integer start,Integer limit,String type){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("start", start);
		map.put("end", limit);
		if(type.equals("advanced")){
			map.put("type", "advanced");
		}
		List<AdvancedSearchConditionBean> list = asconditionMapper.searchAdvCondition(map);
		return list;
	}
	
	/**
	 * 删除检索条件
	 * <p>Title: deleteAdvCondition</p> 
	 * <p>Description: TODO</p>
	 * @param asconditionIds
	 */
	public void deleteAdvCondition(String asconditionIds){
		boolean flag = true;//是否执行删除普通搜索条件
		AdvancedSearchConditionBean bean = null;
		List<AdvancedSearchConditionBean> asBeans = new ArrayList<AdvancedSearchConditionBean>();
		String[] tempStr = asconditionIds.split("`");
		for(int i=0;i<tempStr.length;i++){
			String[] tempBean = tempStr[i].split("-");
			if(tempBean.length > 0){
				bean = new AdvancedSearchConditionBean();
				bean.setAscondition_id(Integer.parseInt(tempBean[0]));
				bean.setType(tempBean[1]);
				asBeans.add(bean);
			}
		}
		for(AdvancedSearchConditionBean asBean : asBeans){
			if(asBean.getType() != null && asBean.getType().equals("ordinary")){
				if(flag){
					Map<String,Object> tempmap = new HashMap<String,Object>();
					tempmap.put("type", "ordinary");
					asconditionMapper.deleteOrdCondition(tempmap);
					flag = false;//执行过删除后，不再重复执行删除普通搜索条件
				}
			}else{
				//根据id 删除高级搜索条件
				if(asBean.getAscondition_id() != 999999999)
					asconditionMapper.deleteAdvCondition(asBean.getAscondition_id());
			}
		}
	}
	
	/**
	 * 修改高级检索条件
	 * <p>Title: updateAdvCondition</p> 
	 * <p>Description: TODO</p>
	 * @param ascondition
	 */
	public void updateAdvCondition(String ascondition){
		Gson gson = new Gson();
		AdvancedSearchConditionBean asBean = gson.fromJson(ascondition, AdvancedSearchConditionBean.class);
		asconditionMapper.updateAdvCondition(asBean);
	}
}
