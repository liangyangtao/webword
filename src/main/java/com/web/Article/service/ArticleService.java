package com.web.Article.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.database.bean.ContentPlugin;
import com.database.bean.CrawlText;
import com.database.bean.WordLabel;
import com.database.bean.WordLabelExample;
import com.database.dao.ContentPluginMapper;
import com.database.dao.CrawlTextMapper;
import com.database.dao.VerifyDocMapper;
import com.database.dao.WordLabelMapper;
import com.export.Function;
import com.lucene.CrawlWriter;
import com.lucene.UnbankList;
import com.lucene.entity.doc.Crawl;
import com.util.DateTool;
import com.util.Md5Util;
import com.web.document.controller.DocumentController;
import com.web.document.service.RiskService;
import com.web.word.bean.NodeBean;

@Service
public class ArticleService {
	
	@Autowired
	private CrawlWriter crawlWriter;
	
	@Autowired
	private ContentPluginMapper contentPluginMapper;
	
	@Autowired
	private CrawlTextMapper crawlTextMapper;
	
	@Autowired
	private RiskService riskService;
	
	@Value("${picUrl}")
	private String picUrl;
	
	@Value("${oldPicUrl}")
	private String oldPicUrl;
	
	@Autowired
	private WordLabelMapper wordLabelMapper;
	
	@Autowired
	private VerifyDocMapper verifyDocMapper;
	
	
	private static final Logger LOGGER = Logger.getLogger(ArticleService.class);
	
	/*
	 * 设置内容插件
	 * @param userId 用户ID
	 * @return 新建内容插件的Id
	 */
	public int  setContentPlugin(ContentPlugin contentPlugin){
		contentPluginMapper.insertSelective(contentPlugin);
		return contentPlugin.getContentpluginId();
		
	}
	/*
	 * 更新 内容插件
	 */
	public void  updateContentPlugin(ContentPlugin contentPlugin){
		contentPluginMapper.updateByPrimaryKeyWithBLOBs(contentPlugin);
	}
	/*
	 * 获取内容插件
	 * @paramer contentpliginId
	 */
	public ContentPlugin getContentPlugin(int contentpluginId){
		
		return contentPluginMapper.selectByPrimaryKey(contentpluginId);
	}
	/*
	 * 删除内容插件
	 * @paramer contentpliginId
	 */
	public int delConetntPlugin(int contentpluginId){
		return contentPluginMapper.deleteByPrimaryKey(contentpluginId);
	}
	/*
	 * 自动更新单个节点
	 * @param nodeBean 节点
	 * @param pluginContentBean 内容插件
	 */
	public String  updateOneNodeContent(int userId,NodeBean nodeBean,String path,int articleId) {
		String strHtml="";
		ContentPlugin contentPlugin = contentPluginMapper.selectByPrimaryKey(nodeBean.getId());//获取内容插件
		if(contentPlugin==null) return strHtml;
		Date newDate=DateTool.getDate(new Date());
		if(!DateTool.isSameDate(contentPlugin.getContentpluginUpdatetime(),newDate)){//日期不一致 更新
			//LOGGER.info("执行操作="+nodeBean.getNodeId());
			editTime(contentPlugin);//1更新修改时间
			UnbankList<Crawl> list=search(userId,contentPlugin,path,articleId);
			//这个是更新节点的内容
			strHtml =getCrawlStr(list,nodeBean.getOrder()+1);
			contentPlugin.setContentpluginUpdatetime(newDate);
			//pluginContentDao.updateTime(pluginContentBean);
			contentPluginMapper.setUpdateTimeByKey(contentPlugin);
			
		}
		return strHtml;
	}
	/*
	 * 修改执行的周期
	 */
	public void editTime(ContentPlugin contentPlugin) {
		if(contentPlugin!=null&&contentPlugin.getContentpluginRepeatflag()!=1){
			return ;//不是1的话，不自动执行
		}
		Date startTime = contentPlugin.getContentpluginStarttime();
		Date endTime = contentPlugin.getContentpluginEndtime();
		if(startTime==null&&endTime==null){
			return ;//时间为空
		}
		Date inertDate = contentPlugin.getContentpluginInsertdate();
		Date newDate=DateTool.getDate(new Date());
		if(endTime.after(newDate)){//在结束日期前不处理
			return ;
		}
		long longRepeat = DateTool.betweenDate(startTime,endTime);//执行的周期
		long longInsertNew = DateTool.betweenDate(inertDate,newDate);//插入和日期的间隔
		int longcycle = 0;
		longInsertNew = DateTool.betweenDate(startTime,newDate);//间隔
		longcycle = (int) (longInsertNew/longRepeat);
		if(longInsertNew%longRepeat==0){
			longcycle-=1;
		}
		longcycle=(int) (longcycle*longRepeat);
		//LOGGER.info("longInsertNew="+longInsertNew+",longcycle="+longcycle);
		if(longcycle==0){
			return ;
		}
		startTime=DateTool.addDate(startTime, longcycle);
		endTime=DateTool.addDate(endTime, longcycle);
		contentPlugin.setContentpluginStarttime(startTime);
		contentPlugin.setContentpluginEndtime(endTime);
	}
	/*
	 * 索引列表转换成字符串
	 */
	public String getCrawlStr(UnbankList<Crawl> list,int hOrder){
		String strHtml = "";
		for(Crawl crawl : list.getList()){
			strHtml +="<h"+hOrder+" id=\""+Md5Util.getRandom()+"\">"+crawl.getCrawlTitle()+"</h"+hOrder+">"+crawl.getText();
		}
		return strHtml;
	}
	/*
	 * 内容插件搜索-通用类
	 * @oaram userId 用户ID
	 * @param ContentPlugin 搜索的条件
	 */
	public UnbankList<Crawl> search(Integer userId,ContentPlugin contentPlugin,String path,int articleId){
		UnbankList<Crawl> list=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime, endTime;
		startTime=contentPlugin.getContentpluginStarttime();
		endTime=contentPlugin.getContentpluginEndtime();
		int type=2;//1搜索 2插件
		String must = contentPlugin.getContentpluginMust();
		if(must == null){
			must="";
		}else{
			must=com.util.RegexTest.replaceAllSpace(must);
		}
		String arbitrarily=contentPlugin.getContentpluginArbitrarily();
		if(arbitrarily==null){
			arbitrarily="";
		}else{
			arbitrarily=com.util.RegexTest.replaceAllSpace(arbitrarily);
		}
		//System.out.println("+arbitrarily="+arbitrarily);
		String not = contentPlugin.getContentpluginNot();
		if(not==null){
			not="";
		}else{
			not=com.util.RegexTest.replaceAllSpace(not);
		}
		int pageId= 1;
		int pageSize = contentPlugin.getContentpluginPagesize();
		int searchsource= contentPlugin.getContentpluginSearchsource();
		int sort=contentPlugin.getContentpluginSort();
		String fromsource=contentPlugin.getContentpluginFromsource();
		if(fromsource!=null) fromsource=fromsource.replaceAll("\\|",",");
		//LOGGER.info("pageId="+pageId+",pageSize="+pageSize);
		//LOGGER.info("startTime="+startTime+",endTime="+endTime+",must="+must+",arbitrarily="+arbitrarily+",not="+not+",pageSize="+pageSize);
		//LOGGER.info("searchsource="+searchsource+",sort="+sort+",fromsource="+fromsource);
		List<String> crawlIdList = new ArrayList<String>();//搜索出来id
		List<String> esIdList = new ArrayList<String>();//风险库的esId
		try {
			list=riskService.searchPlugin(userId,must,arbitrarily,not,startTime,endTime,pageSize,pageId,searchsource,sort, fromsource,contentPlugin.getStrType());
			toHeavy(list);
			for(Crawl crawl : list.getList()){
				int crawlId=crawl.getCrawlId();
				//LOGGER.info("crawlId="+crawlId);
				//图片处理
				String content = crawl.getText();
				if(picUrl.equals(oldPicUrl)){
				}else{//不相等替换
					content=content.replaceAll(oldPicUrl+"unbankImage", picUrl+"unbankImage");
				}
				//System.out.println(crawl.getText());
				crawl.setText(Function.replaceImg(content,path,picUrl));
				crawlIdList.add(Integer.toString(crawl.getCrawlId()));
				esIdList.add(crawl.getEsId());
			}
			String labels = must.trim()+" "+arbitrarily.trim();
			insertlabels(labels.trim());
			updateByEsIduserCount(esIdList);
			LOGGER.info("urlName=ContentPlugin,urlMsg=内容插件执行,userId="+userId+",articleId="+articleId+",strType="+contentPlugin.getStrType()+",crawlIdList="+StringUtils.join(crawlIdList.toArray(),";")+",esIdList="+StringUtils.join(esIdList.toArray(),";"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 增加关键词
	 * @param tags
	 * @return
	 */
	public int insertlabels(String tags){
		//System.out.println("tags="+tags);
		int count = 0;
		if (!"".equals(tags)) {
			for (String str : tags.split(" ")) {
				if (str != null && !"".equals(str.trim())) {
					WordLabelExample example = new WordLabelExample();
					example.or().andNameEqualTo(str.trim());
					List<WordLabel> list = wordLabelMapper.selectByExample(example);
					if (list.isEmpty()) {
						WordLabel tag = new WordLabel();
						tag.setName(str.trim());
						tag.setCount(1);
						wordLabelMapper.insert(tag);
					} else {
						WordLabel tag = list.get(0);
						tag.setCount(tag.getCount() + 1);
						wordLabelMapper.updateByPrimaryKey(tag);
					}
					count++;
				}
			}
		}
		return count;
	}
	/**
	 * 更新esId使用次数
	 * @param esIdList
	 */
	public void updateByEsIduserCount(List<String> esIdList){
		for (String esId:esIdList){
			if(esId!=null){
				verifyDocMapper.updateByEsIduserCount(esId);
			}
			
		}
	}
	/*
	 * 根据crawlId去重
	 * */
	public void toHeavy(UnbankList<Crawl> list){
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		
		List<Crawl>  newList = new ArrayList<Crawl>();
		for(Crawl crawl : list.getList()){
			map.put(Integer.toString(crawl.getCrawlId()), crawl);
		}
		for(String key:map.keySet()){
			newList.add((Crawl) map.get(key));
		}
		list.setList(newList);
		
	}
}
