package com.web.word.service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.database.bean.Article;
import com.database.bean.ArticleExample;
import com.database.bean.ArticleProperty;
import com.database.bean.ArticlePropertyExample;
import com.database.bean.ContentPlugin;
import com.database.bean.ContentWhole;
import com.database.bean.ContentWholeExample;
import com.database.bean.ContentWithBLOBs;
import com.database.bean.Homepage;
import com.database.bean.HomepageExample;
import com.database.bean.WordJournal;
import com.database.bean.ContentWholeExample.Criteria;
import com.database.bean.KnowPlate;
import com.database.dao.ArticleMapper;
import com.database.dao.ArticlePropertyMapper;
import com.database.dao.ContentPluginMapper;
import com.database.dao.ContentWholeMapper;
import com.database.dao.HomepageMapper;
import com.database.dao.KnowPlateMapper;
import com.database.dao.WordJournalMapper;
import com.export.Builder;
import com.export.ChartReplacer;
import com.export.Function;
import com.util.DateTool;
import com.util.DocToHtml;
import com.web.Article.service.ArticleService;
import com.web.homePage.util.ArticlePageOne;
import com.web.homePage.util.Word;
import com.web.homePage.util.XmlUtils;
import com.web.word.bean.ArticleAndContent;
import com.web.word.bean.NodeBean;
import com.web.word.controller.WordController;

@Service
public class WordService {
	
	private static final Logger LOGGER = Logger.getLogger(WordService.class);
	
	@Autowired
	private ArticleMapper articleMapper;
	
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ContentWholeMapper contentWholeMapper;
	@Autowired
	private HomepageMapper homepageMapper ;
	@Autowired
	private ArticlePropertyMapper articlePropertyMapper;
	@Autowired
	private KnowPlateMapper knowPlateMapper;
	
	@Autowired
	private ContentPluginMapper contentPluginMapper;
	
	@Autowired
	private WordJournalMapper wordJournalMapper;
	
	@Value("${version}")
	private String version;
	/*
	 * 新建文档
	 * @param userId 用户ID
	 */
	public int wordNew(Integer userId ){
		DateFormat dd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Article article = new Article();
		String articleName = "新建文档_"+System.currentTimeMillis()/100;
		article.setArticleName(articleName);
		article.setUserId(userId);
		article.setArticleType("document");//默认为建立的文档
		article.setInsertTime(Timestamp.valueOf(dd.format(new Date())));
		article.setUpdateTime(Timestamp.valueOf(dd.format(new Date())));
		int articleId = articleMapper.insertSelective(article);
		//System.out.println(article.getArticleId()+"---");
		//System.out.println("articleId="+articleId);
		return article.getArticleId();
	}
	/*
	 * 获取单个文章 内容
	 * @param  articleId:文章的articleId
	 */
	public ContentWhole getArticleContentById(int articleId){
		ContentWholeExample example = new ContentWholeExample();
		example.setLimitStart(0);
		example.setLimitEnd(1);
		Criteria cr =example.createCriteria();
		cr.andArticleIdEqualTo(articleId);
		cr.andNodeIdEqualTo(0);
		List<ContentWhole> contentList =contentWholeMapper.selectByExampleWithBLOBs(example);
		//System.out.println("--"+contentList.size());
		ContentWhole content = new ContentWhole();
		if(contentList.size()>0){
			content=contentList.get(0);
			 for(int i=0;i<contentList.size();i++){
				 //System.out.println("i="+i+","+contentList.get(i).getNodeContent()+","+contentList.get(i).getWholeId());
			 }
		}else{
			//content.setNodeContent("<h1 id=\""+articleId+"\">标题1</h1><p>输入内容</p>");
		}
		//
		//content = contentWholeMapper.selectByArticleId(articleId);
		//System.out.println("--"+content.getNodeContent());
		return  content;
	}
	/*
	 * 设置文章内容
	 */
	public Map<String, Object> setArticleContentById(int articleId,String nodeContent){
		Map<String, Object>  maps= new HashMap<String, Object>();
		Article article = articleMapper.selectByPrimaryKey(articleId);
		String flag = "success";
		String info = "保存成功";
		if("".equals(nodeContent)){
			flag="error";
			info="保存的内容不能为空";
		}
		else if(article==null){
			flag="error";
			info="操作无效,该篇文档不存在";
		}else if(article.getArticleSave()!=null&&"upload".equals(article.getArticleSave())){
			//上传的文档
			flag="error";
			info="上传的文档,不能保存";
		}
		else if(getPassType(article)){
			//是私有的才能保存
			ContentWhole content = getArticleContentById(articleId);
			ContentWhole contentNew = new ContentWhole();
			if("template".equals(article.getArticleType())){//是模板,不保存内容插件子节点
				//nodeContent=clearPluginStr(nodeContent);
			}
			if(content==null||content.getArticleId()==null){//等于空,新建内容
				contentNew.setArticleId(articleId);
				contentNew.setNodeId(0);
				contentNew.setNodeContent(nodeContent);
				contentWholeMapper.insertSelective(contentNew);
				articleMapper.updateArticleTime(articleId);
			}else{
				if(nodeContent.equals(content.getNodeContent())){//相等就不用保存
					
				}else if("".equals(nodeContent)){//为空,也不用保存
					flag="error";
					info="保存的内容不能为空";
				}else{
					contentNew.setWholeId(content.getWholeId());
					contentNew.setNodeContent(nodeContent);
					contentWholeMapper.updateByPrimaryKeySelective(contentNew);
					articleMapper.updateArticleTime(articleId);
				}

			}
		}else{
			flag="error";
			info="只能保存私有文档";
		}
		maps.put("status", flag);
		maps.put("info", info);
		maps.put("article", article);
		return maps;
	}
	/*
	 * 获取节点字符串
	 */
	public List<NodeBean> getNodeList(int articleId){
		List<NodeBean> nodeList = new ArrayList<NodeBean>();
		List<NodeBean> newList = new ArrayList<NodeBean>();
		
		ContentWhole contentWhole = getArticleContentById(articleId);
		if(contentWhole.getNodeContent()==null){//没有元素
			//contentWhole.setNodeContent("<h1 id=\""+articleId+"\">标题1</h1>");
			return newList;
		}
		//String content = contentWhole.getNodeContent();
		int order =1;
		String regex="(<h[1-6][^>]*?>)([^\t]*?)(</h[1-6]>)";
		Pattern pattern = Pattern.compile(regex,Pattern.UNICODE_CASE+Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(contentWhole.getNodeContent());
		//List<NodeBean> newList = new ArrayList<NodeBean>();
		int i=0;
		/*添加封面
		NodeBean fbean = new NodeBean();
		fbean.setPid(0);
		fbean.setId(0);
		fbean.setNodeId(0);
		fbean.setOrder(1);
		fbean.setRealId(0);
		fbean.setNodeNameStatic("封面");
		fbean.setNodeType(0);
		newList.add(fbean);
		getNode(fbean,nodeList,0);
		*/
		while(matcher.find()){//遍历节点
			i++;
			String idStr=getId(matcher.group(1));
			int h = Integer.parseInt(matcher.group(1).substring(2,3));
			String title = matcher.group(2).replaceAll("<br/>", "").replaceAll("<[^>]*>", "");
			//replaceAll("\\&[a-zA-Z]{0,9};", "")
			//System.out.println("title="+title); Integer.parseInt
			NodeBean bean = new NodeBean();
			//bean.setPid(0);
			String [] idStrArry = idStr.split("-");
			if(idStrArry.length>1){
				bean.setId(Integer.parseInt(idStrArry[1]));
				//bean.setIcon("tree_plugin");
			}else{
				bean.setId(0);
			}
			//bean.setNodeId(id);
			bean.setIdStr(idStr);
			bean.setOrder(h);
			bean.setNodeNameStatic(title);
			newList.add(bean);
			//getNode(bean,nodeList,0);
		}
		return newList;
	}
	/*获取节点的内容
	 * articleId:文档的Id
	 * hId:节点的Id
	 * hName:标签名字 h1,h2
	 */
	public NodeBean getNodeContent(int articleId,String hId,String hName){
		NodeBean  node  = new NodeBean();
		ContentWhole contentWhole = getArticleContentById(articleId);
		
		if(contentWhole.getNodeContent()==null){//没有元素
			//contentWhole.setNodeContent("<h1 id=\""+articleId+"\">标题1</h1>");
			return node;
		}
		//String content = contentWhole.getNodeContent();
		String regex="(<h[1-6][^>]*?>)([^\t]*?)(</h[1-6]>)";
		Pattern pattern = Pattern.compile(regex,Pattern.UNICODE_CASE+Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(contentWhole.getNodeContent());
		//List<NodeBean> newList = new ArrayList<NodeBean>();
		int i=0;
		int order =0;
		String nodeName = "";
		String nodeNextName = "";
		while(matcher.find()){//遍历节点
			i++;
			String  id=getId(matcher.group(1));
			int h = Integer.parseInt(matcher.group(1).substring(2,3));
			if(order>0){//已经找到本节点
				if(order>=h){//找到下一个节点
					nodeNextName=matcher.group(0);
					break;//退出循环
				}
			}
			if(id.equals(hId)){//找到此节点
				order = h;
				nodeName=matcher.group(0);
			}
			//NodeBean bean = new NodeBean();
			//bean.setPid(0);
			//bean.setId(id);
			//bean.setNodeId(id);
			//bean.setOrder(h);
			//bean.setNodeNameStatic(title);
			//getNode(bean,nodeList,0);
		}
		//System.out.println("nodeName="+nodeName);
		//System.out.println("nodeNextName="+nodeNextName);
		if("".equals(nodeName)){
			return node;
		}
		String[] strarray =contentWhole.getNodeContent().split(nodeName);
		if(strarray.length>0){//存在
			String content = nodeName+strarray[1];
			if("".equals(nodeNextName)){
				node.setContent(content);
			}else{
				strarray=content.split(nodeNextName);
				if(strarray.length>0){
					node.setContent(strarray[0]);
				}
			}
		}
		return node;
	}
	/*
	 * 递归
	 * bean:新节点 newlist:树结构，level:等级 0开始
	 */
	public void getNode(NodeBean bean,List<NodeBean> newList,int level ){
		if(newList.size()==0){
			bean.setRealId(level);
			newList.add(bean);
		}else{
			NodeBean endBean=newList.get(newList.size()-1);//最后一个元素
			if(bean.getOrder()<=endBean.getOrder()){//oder 小于等于 放在当前 节点
				bean.setRealId(level);
				newList.add(bean);
			}else if(bean.getOrder()>endBean.getOrder()){
				getNode(bean,endBean.getChildren(),++level);
				//newList.get(newList.size()-1).getChildren().add(bean);
			}
		}
	}
	/*
	 * 获取id值
	 * <h1 id="123" name="456"> 
	 * 返回id的值 123
	 */
	public String getId(String str){
		//System.out.println("Str="+str);
		String reg="\\sid=['\"]{1}(.*?)['\"]{1}";
		Pattern pattern = Pattern.compile(reg,Pattern.UNICODE_CASE+Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		String r="0";
		while(matcher.find()){
			//System.out.println(matcher.group(1));
			r = matcher.group(1);
			break;
		}
		return r;
	}
	/*
	 * 获取单个文章
	 * @param  articleId:文章的articleId
	 */
	public Article selectById(int articleId){
		Article article = articleMapper.selectByPrimaryKey(articleId);
		return  article;
	}
	/*
	 * 更新文章
	 */
	public int updateArticle (Article article){
		return articleMapper.updateByPrimaryKey(article);
	}
	/**
	 * 更新文档，空的状态不更新
	 * @param article
	 * @return
	 */
	public int updateByPrimaryKeySelectiveArticle(Article article){
		return articleMapper.updateByPrimaryKeySelective(article);
	}
	/*
	 * 添加文章
	 */
	public int  insertSelective(Article article){
		return articleMapper.insertSelective(article);
	}
	/*
	 * 删除文章
	 */
	public int deleteArticle(int articleId){
		return articleMapper.deleteByPrimaryKey(articleId);
	}
	/*
	 * 获取文章列表
	 */
	public List<Article> selectByExample(){
		List<Article> listArticle = new ArrayList<Article>();
		ArticleExample example = new ArticleExample();
		example.setLimitStart(1);
		example.setLimitEnd(10);
		//Criteria cr =example.createCriteria();
		//cr.andUserIdEqualTo(431);
		listArticle = articleMapper.selectByExample(example);
		System.out.println("listArticle.length="+listArticle.size());
		return listArticle;
	}
	/**
	 * 文档搜索
	 * @param userId 用户的id
	 * @param artilceType 搜索的名字
	 * @param name 文档的名字
	 * @param pageId 页数
	 * @param pageSize 页数
	 * @return
	 */
	public Map<String, Object> searchArticle(int userId,String articleType,String passType,String name,int pageId,int pageSize){
		Map<String, Object> maps = new HashMap<String, Object>();
		int count =0;
		int pageCount=0;
		
		ArticleExample example = new ArticleExample();
		com.database.bean.ArticleExample.Criteria cr = example.createCriteria();
		//System.out.println("userId="+userId);
		cr.andArticleSaveEqualTo("write");
		if(userId!=0) {
			cr.andUserIdEqualTo(userId);
		}
		if("".equals(articleType)){
		}else{
			cr.andArticleTypeEqualTo(articleType);
		}
		if(name!=null&&!("".equals(name))) {
			String[] musts = Function.returnArray(name);
			//example.or().andUserIdEqualTo(userId).andArticleTypeEqualTo(type);
			cr.andArticleNameLike("%"+musts[0]+"%");
			//example.or().andArticleNameLike("%"+name+"%");
		}
		if(passType.length()>0){
			List<String> passTypelist = Arrays.asList(passType.split(" "));//new ArrayList<String>()
			//System.out.println(passType+","+passTypelist.size());
			//passTypelist.add("PASSED");
			//passTypelist.add("SUBMITTED");
			if(passTypelist.size()>0){
				cr.andPassTypeIn(passTypelist);
			}
		}
		cr.andPassTypeIsNotNull();
		count= articleMapper.countByExample(example);
		
		int start = (pageId-1)*pageSize;
		example.setLimitStart(start);
		example.setLimitEnd(pageSize);
		example.setOrderByClause(" update_time desc ");
		List<Article> list = articleMapper.selectByExample(example);
		//计算页数
		pageCount=count%pageSize==0?count/pageSize:count/pageSize+1;
		
		maps.put("pageCount",pageCount);
		maps.put("count", count);
		maps.put("pageId",pageId);
		maps.put("pageSize",pageSize);
		maps.put("list", list);
		return maps;
	}
	/**
	 * 自己购买和免费的文档
	 * @param userId 用户的id
	 * @param artilceType 搜索的名字
	 * @param name 文档的名字
	 * @param pageId 页数
	 * @param pageSize 页数
	 * @return
	 */
	public Map<String, Object> searchArticleByResource(int userId,String articleType,String passType,String name,int pageId,int pageSize){
		Map<String, Object> maps = new HashMap<String, Object>();
		int count =0;
		int pageCount=0;
		
		int start = (pageId-1)*pageSize;
		
		Article article = new Article();
		
		article.setPassType(passType);
		article.setArticleType(articleType);
		article.setArticleSave("write");
		
		article.setUserId(userId);
		article.setArticlePrice(0.0);
		article.setSavestatus(start);
		article.setContentStatus(pageSize);
		
		if(name!=null&&!("".equals(name))) {
			String[] musts = Function.returnArray(name);
			//cr.andArticleNameLike("%"+musts[0]+"%");
			article.setArticleName(musts[0]);
		}
		
		count= articleMapper.searchArticleByResourceCount(article);
		
		List<Article> list = articleMapper.searchArticleByResource(article);
		//计算页数
		pageCount=count%pageSize==0?count/pageSize:count/pageSize+1;
		
		maps.put("pageCount",pageCount);
		maps.put("count", count);
		maps.put("pageId",pageId);
		maps.put("pageSize",pageSize);
		maps.put("list", list);
		return maps;
	}
	/**
	 * 删除文档
	 * @param userId
	 * @param id 文档
	 * @return
	 */
	public Map<String,Object> articleDelById(int userId,int id){
		Map<String,Object> maps = new HashMap<String,Object>();
		Article article = articleMapper.selectByPrimaryKey(id);
		if(article!=null){
			if(article.getUserId()==userId){
				if("PASSED".equals(article.getPassType())){//审核通过文档
					Article newArticle = new Article();
					newArticle.setArticleId(article.getArticleId());
					newArticle.setUserId(1);
					articleMapper.updateByPrimaryKeySelective(newArticle);
				}else{
					int flag = articleMapper.deleteByPrimaryKey(id);;
				}
				maps.put("state","success");
				maps.put("info", "删除成功");
				maps.put("flag","true");

			}else{
				maps.put("state","error");
				maps.put("info", "不是此用户的内容");
			}
			
		}else{
			maps.put("state","error");
			maps.put("info", "没有此文档");
		}
		return maps;
	}
	
	/**
	 * 检查文章是否重名,并执行后续处理
	 * <p>Title: checkArticleName</p> 
	 * <p>Description: TODO</p>
	 * @param currentUserId
	 * @param articleId
	 * @param articleName
	 * @param flag:
	 * @param documentType:0:文档 1:模板
	 * @return
	 */
	public Article checkArticleName(Integer currentUserId, Integer articleId, String articleName, 
			Integer flag,Integer documentType){
		
		Article bean = new Article();
		bean.setArticleId(articleId);
		bean.setArticleName(articleName);
		bean.setUserId(currentUserId);
		if(documentType == 1){
			bean.setArticleType("template");
		}else{
			bean.setArticleType("document");
		}
		//获取老的文档
		Article oldArticle = articleMapper.selectByPrimaryKey(articleId);
		if(oldArticle==null){//不存在另存为的文档
			return null ;
		}
		//检查是否重名
		boolean isSameName = checkWordName(0,currentUserId,articleName,bean.getArticleType());
		if(isSameName){//存在相同的名字
			return null ;
		} else {
			//得到原始文章
			Article article = new Article() ;
			//保存新的文档属性
			article.setUserId(currentUserId);
			article.setArticleName(articleName);
			article.setPassType("SAVED");
			if(documentType == 1){
				article.setArticleType("template");
			}else{
				article.setArticleType("document");
			}
			article.setInsertTime(new Date(System.currentTimeMillis()));
			article.setUpdateTime(new Date(System.currentTimeMillis()));
			articleMapper.insertSelective(article);
			//检查封面信息
			Homepage hpBean = null;
			HomepageExample example = new HomepageExample();
			com.database.bean.HomepageExample.Criteria cr = example.createCriteria();
			cr.andArticleIdEqualTo(articleId);
			List<Homepage> list = homepageMapper.selectByExample(example);
			if(list.size() > 0){
				hpBean = list.get(0);
			}
			if(hpBean != null){
				//如果被拷贝的文章存在封面，则复制封面
				if(hpBean.getBasepicpath() != null && !"".equals(hpBean.getBasepicpath())){
					String basepath = hpBean.getBasepicpath().substring(0, hpBean.getBasepicpath().lastIndexOf("/"));
					String type = hpBean.getBasepicpath().substring(hpBean.getBasepicpath().lastIndexOf("."));
					String picname = System.nanoTime()+type;
					File file = new File(basepath,picname);
					try {
						File f = new File(hpBean.getBasepicpath());
						if(f.exists()){
							FileUtils.copyFile(f, file);
							hpBean.setBasepicpath(basepath+"/"+picname);
						}else{
							hpBean.setBasepicpath(null);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(hpBean.getSavepicpath() != null && !"".equals(hpBean.getSavepicpath())){
					String savepath = hpBean.getSavepicpath().substring(0, hpBean.getSavepicpath().lastIndexOf("/"));
					String picname = System.nanoTime()+".png";
					File file = new File(savepath,picname);
					try {
						File f = new File(hpBean.getSavepicpath());
						if(f.exists()){
							FileUtils.copyFile(f, file);
							hpBean.setSavepicpath(savepath+"/"+picname);
						} else{
							hpBean.setSavepicpath(null);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				hpBean.setArticleId(article.getArticleId());
				hpBean.setHomepagePropertyId(null);
				homepageMapper.insertSelective(hpBean);
			}
			
			//得到原文章内容
			ContentWhole temp = new ContentWhole();
			temp.setNodeId(0);
			temp.setArticleId(articleId);
			List<ContentWithBLOBs> list1 = contentWholeMapper.selectContentByArticleId(temp);
			String content = "";
			if(list1.size() > 0){
				content = list1.get(0).getContent();
			}
			Date updateTime = DateTool.strToDate("1970-01-01 01:01:01");
			//获取原文章的内容插件
			if(content != null && !"".equals(content)){
				String newContent = content;
				if("write".equals(oldArticle.getArticleSave())){//自己写的文档
					List<NodeBean> newList = new ArrayList<NodeBean>();//内容插件
					//String newContent = "";
					//String tempStr = content;
					String regex="(<h[1-6][^>]*?>)([^\t]*?)(</h[1-6]>)";
					Pattern pattern = Pattern.compile(regex,Pattern.UNICODE_CASE+Pattern.CASE_INSENSITIVE);
					Matcher matcher = pattern.matcher(content);
					while(matcher.find()){
						String idStr=getId(matcher.group(1));
						int h = Integer.parseInt(matcher.group(1).substring(2,3));
						String [] idStrArry = idStr.split("-");
						if(idStrArry.length>1){//这个节点是内容插件						
							Integer id = Integer.parseInt(idStrArry[1]);
							ContentPlugin contentPlugin = contentPluginMapper.selectByPrimaryKey(id);
							if(contentPlugin != null && contentPlugin.getContentpluginId() > 0){
								contentPlugin.setContentpluginId(null);
								contentPlugin.setContentpluginUpdatetime(updateTime);
								//System.out.println("updateTime="+updateTime);
								contentPluginMapper.insertSelective(contentPlugin);
								//System.out.println(contentPlugin.getContentpluginId()+"-updateTime="+contentPlugin.getContentpluginUpdatetime());
								Integer newId = contentPlugin.getContentpluginId();
								newContent = newContent.replace(idStr, idStrArry[0]+"-"+newId);
								String newHText =  matcher.group(0).replace(idStr, idStrArry[0]+"-"+newId);
								//String text = matcher.group(1);
								//int h = Integer.parseInt(matcher.group(1).substring(2,3));
				 				//newContent += tempStr.substring(0,tempStr.indexOf(text));
				 				//newContent += "<h"+h+" id='"+idStrArry[0]+"-"+newId+"'>";
				 				//tempStr = tempStr.substring(tempStr.indexOf(matcher.group(2)));
								NodeBean node = new NodeBean();
								node.setIdStr(idStrArry[0]+"-"+newId);
								node.setOrder(h);
								//node.setNodeNameStatic(title);//标题的名字
								node.setNodeNameDynamic(newHText);//标题的全称 包含h1
								newList.add(node);
							}
						}
					}
					//如果是内容插件，删除内容插件子节点。
					if(documentType == 1){//是模板
						/*
						for(NodeBean node:newList){
							newContent=clearPluginNode(node,newContent);
						}*/
					}
				}//是自己编写的
				
				//保存文章内容
				ContentWhole record = new ContentWhole();
				record.setNodeId(0);
				//record.setNodeContent(newContent+tempStr);
				record.setNodeContent(newContent);
				record.setArticleId(article.getArticleId());
				contentWholeMapper.saveContent(record);
			}//处理内容
			return article;
		}
	}
	
	/*public static void main(String[] args) {
		WordService ws = new WordService();
		String content="<h1 id='13134141-222'>nnnnnnnn</h1><p>aaa</p><h2 id='463636-55'>uuuu</h2>";
		String newContent = "";
		String tempStr = content;
		String str = content;
		String regex="(<h[1-6][^>]*?>)([^\t]*?)(</h[1-6]>)";
		Pattern pattern = Pattern.compile(regex,Pattern.UNICODE_CASE+Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(content);
		while(matcher.find()){
			String idStr=ws.getId(matcher.group(1));
			int h = Integer.parseInt(matcher.group(1).substring(2,3));
			String [] idStrArry = idStr.split("-");
			if(idStrArry.length>1){
				Integer id = Integer.parseInt(idStrArry[1]);
				Integer newId = 567;
				//newContent.replaceAll("id", "class");
				str = str.replace(idStr, idStrArry[0]+"-"+newId);
				String text = matcher.group(1);
 				newContent += tempStr.substring(0,tempStr.indexOf(text));
 				newContent += "<h"+h+" id='"+idStrArry[0]+"-"+newId+"'>";
 				tempStr = tempStr.substring(tempStr.indexOf(matcher.group(2)));
			}
		}
		System.out.println(newContent);
		System.out.println(tempStr);
		System.out.println(newContent+tempStr);
		System.out.println(str);
	}*/
	/**
	 * 根据文章Id获取全局属性
	 * <p>Title: getGlobalByArticleId</p> 
	 * <p>Description: TODO</p>
	 * @param articleId
	 * @return
	 */
	public ArticleProperty getGlobalByArticleId(Integer articleId){
		ArticleProperty prop = null;
		ArticlePropertyExample example = new ArticlePropertyExample();
		com.database.bean.ArticlePropertyExample.Criteria cr = 
				example.createCriteria();
		cr.andArticleIdEqualTo(articleId);
		List<ArticleProperty> list = articlePropertyMapper.selectByExample(example);
		if(list.size() > 0){
			prop = list.get(0);
		}
		return prop;
	}
	public ArticleProperty getGlobalSetting(Integer articleId) {
		return articlePropertyMapper.getGlobalSetting(articleId);
	}
	public ArticleProperty getOutlineSetting(Integer articleId) {
		return articlePropertyMapper.getOutlineSetting(articleId);
	}
	/**
	 * 根据文章Id获取大纲属性
	 * <p>Title: getOutlineByArticleId</p> 
	 * <p>Description: TODO</p>
	 * @param articleId
	 * @return
	 */
	public ArticleProperty getOutlineByArticleId(Integer articleId){
		return articlePropertyMapper.getOutlineByArticleId(articleId);
	}
	/**
	 * 更新全局属性
	 * <p>Title: updateGlobalProperty</p> 
	 * <p>Description: TODO</p>
	 * @param prop
	 */
	public void updateGlobalProperty(ArticleProperty prop){
		articlePropertyMapper.updateGlobalProperty(prop);
	}
	
	/**
	 * 更新大纲属性
	 * <p>Title: updateOutlineProperty</p> 
	 * <p>Description: TODO</p>
	 * @param prop
	 */
	public void updateOutlineProperty(ArticleProperty prop){
		articlePropertyMapper.updateOutlineProperty(prop);
	}
	/**
	 * 保存全局属性
	 * <p>Title: saveGlobalSetting</p> 
	 * <p>Description: TODO</p>
	 * @param prop
	 */
	public void saveGlobalSetting(ArticleProperty prop){
		articlePropertyMapper.saveGlobalSetting(prop);
	}
	
	/**
	 * 保存大纲属性
	 * <p>Title: saveOutlineSetting</p> 
	 * <p>Description: TODO</p>
	 * @param prop
	 */
	public void saveOutlineSetting(ArticleProperty prop) {
		articlePropertyMapper.saveOutlineSetting(prop);
	}
	/**
	 * 获取文章分类
	 * <p>Title: getArticlePlate</p> 
	 * <p>Description: TODO</p>
	 * @param articleId
	 * @param type
	 * @return
	 */
	public KnowPlate getArticlePlate(Integer articleId,String type){
		KnowPlate KnowPlate = new KnowPlate();
		if("document".equals(type) || "template".equals(type)){
			KnowPlate = knowPlateMapper.getArticlePlate(articleId);
		}
		return KnowPlate;
	}
	
	/**
	 * 提交审核重名验证
	 * <p>Title: submitCheckName</p> 
	 * <p>Description: TODO</p>
	 * @param type
	 * @param name
	 * @param articleId
	 * @return
	 */
	public Article submitCheckName(int userId,String type, String name,String articleId){
		Article bean = new Article();
		bean.setArticleId(Integer.parseInt(articleId));
		bean.setArticleName(name);
		bean.setArticleType(type);
		bean.setUserId(userId);
		return articleMapper.submitCheckArticleName(bean);
	}
	/**
	 * 检查期刊
	 * @param article
	 * @param userId
	 * @return  true:有重复的
	 */
	public boolean checkArticleJournalName(Article article){
		if("".equals(article.getArticleJournalName())){
			return false;
		}
		ArticleExample articleExmaple = new ArticleExample();
		articleExmaple.setLimitStart(0);
		articleExmaple.setLimitEnd(1);
		articleExmaple.or().andArticleJournalIdEqualTo(article.getArticleJournalId()).andPassTypeEqualTo("PASSED").andArticleJournalNameEqualTo(article.getArticleJournalName());
		articleExmaple.or().andArticleJournalIdEqualTo(article.getArticleJournalId()).andPassTypeEqualTo("SUBMITTED").andArticleJournalNameEqualTo(article.getArticleJournalName());
		List<Article> articlelist = articleMapper.selectByExample(articleExmaple);
		if(articlelist.size()>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断当前任务是否属于当前用户
	 * <p>Title: isBelongMe</p> 
	 * <p>Description: TODO</p>
	 * @param type
	 * @param articleId
	 * @param userId
	 * @return
	 */
	public Boolean isBelongMe(String type,Integer articleId,Integer userId){
		Boolean isBelong = false;
		if("article".equals(type)){//文章
			Article bean = new Article();
			bean.setArticleId(articleId);
			bean.setUserId(userId);
			int sum=articleMapper.articleIsBelongMe(bean);
			if(sum>0){
				isBelong=true;
			}else{
				isBelong=false;
			}
		}
		return isBelong;
	}
	
	/**
	 * 提交审核
	 * <p>Title: editSubmitApprove</p> 
	 * <p>Description: TODO</p>
	 * @param articleAndContent
	 */
	public int editSubmitApprove(ArticleAndContent articleAndContent){
		int flag=0;
		String type = articleAndContent.getType();
		if("template".equals(type) || "document".equals(type)){
			int updateArticle=articleMapper.updateArticle(articleAndContent);//更新相应的文章表
			int insertkeyword=articleMapper.insertKeyword(articleAndContent);//插入关键字
			int insertplate=articleMapper.insertUpadateArtPlate(articleAndContent);//插入分类
			if(updateArticle>0 && insertkeyword>0 && insertplate>0){
				flag=1;
			}
		}
		return flag;
	}
	
	/**
	 * 修改文章保存状态
	 * <p>Title: updateArticleOrContentSaveStatus</p> 
	 * <p>Description: TODO</p>
	 * @param type
	 * @param articleId
	 */
	public void updateArticleOrContentSaveStatus(String type,int articleId){
		if("article".equals(type)){//如果是文章
			articleMapper.updateArticleSaveStatus(articleId);
		}
	}
	
	/**
	 * 进入文档工作台进行复制
	 * <p>Title: copyDocumentOrTemplate</p> 
	 * <p>Description: TODO</p>
	 * @param userId
	 * @param articleId
	 * @param articleName
	 * @param documentType
	 * @return
	 */
	public Article copyDocumentOrTemplate(int userId, int articleId, String articleName, int documentType){
		//得到原始文章
		Article original = articleMapper.selectByPrimaryKey(articleId);
		//保存新的文档属性
		original.setUserId(userId);
		original.setArticleName(articleName);
		original.setPassType("SAVED");
		if(documentType == 1){
			original.setArticleType("template");
		}else{
			original.setArticleType("document");
		}
		original.setInsertTime(new Date(System.currentTimeMillis()));
		original.setUpdateTime(new Date(System.currentTimeMillis()));
		articleMapper.insertNewArticle(original);
		ArticleExample articleExmaple = new ArticleExample();
		com.database.bean.ArticleExample.Criteria articleCr = articleExmaple.createCriteria();
		articleCr.andArticleNameEqualTo(original.getArticleName());
		articleCr.andArticleTypeEqualTo(original.getArticleType());
		List<Article> articlelist = articleMapper.selectByExample(articleExmaple);
		for(int i=0;i<articlelist.size();i++){
			original = articlelist.get(0);
		}
		
		//检查封面信息
		Homepage hpBean = null;
		HomepageExample example = new HomepageExample();
		com.database.bean.HomepageExample.Criteria cr = example.createCriteria();
		cr.andArticleIdEqualTo(articleId);
		List<Homepage> list = homepageMapper.selectByExample(example);
		if(list.size() > 0){
			hpBean = list.get(0);
		}
		if(hpBean != null){
			//如果被拷贝的文章存在封面，则复制封面
			if(hpBean.getBasepicpath() != null && !"".equals(hpBean.getBasepicpath())){
				String basepath = hpBean.getBasepicpath().substring(0, hpBean.getBasepicpath().lastIndexOf("/"));
				String type = hpBean.getBasepicpath().substring(hpBean.getBasepicpath().lastIndexOf("."));
				String picname = System.nanoTime()+type;
				File file = new File(basepath,picname);
				try {
					File f = new File(hpBean.getBasepicpath());
					if(f.exists()){
						FileUtils.copyFile(f, file);
						hpBean.setBasepicpath(basepath+"/"+picname);
					}else{
						hpBean.setBasepicpath(null);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(hpBean.getSavepicpath() != null && !"".equals(hpBean.getSavepicpath())){
				String savepath = hpBean.getSavepicpath().substring(0, hpBean.getSavepicpath().lastIndexOf("/"));
				String picname = System.nanoTime()+".png";
				File file = new File(savepath,picname);
				try {
					File f = new File(hpBean.getSavepicpath());
					if(f.exists()){
						FileUtils.copyFile(f, file);
						hpBean.setSavepicpath(savepath+"/"+picname);
					} else{
						hpBean.setSavepicpath(null);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			hpBean.setArticleId(original.getArticleId());
			hpBean.setHomepagePropertyId(null);
			homepageMapper.insertSelective(hpBean);
		}
		
		//得到原文章内容
		ContentWhole temp = null;
		ContentWholeExample example1 = new ContentWholeExample();
		com.database.bean.ContentWholeExample.Criteria cr1 = example1.createCriteria();
		cr1.andArticleIdEqualTo(articleId);
		List<ContentWhole> list1 = contentWholeMapper.selectByExample(example1);
		if(list1.size() > 0){
			temp = list1.get(0);
		}
		if(temp != null) {//如果原文章中有内容则复制保存
			//保存文章内容
			ContentWhole record = new ContentWhole();
			record.setNodeContent(temp.getNodeContent());
			record.setArticleId(original.getArticleId());
			contentWholeMapper.saveContent(record);
		}
		return original;
	}
	
	/**
	 * 更新文章状态
	 * <p>Title: updateArticleOrContentStatus</p> 
	 * <p>Description: TODO</p>
	 * @param type
	 * @param articleId
	 * @return
	 */
	public int updateArticleOrContentStatus(String type,int articleId){
		int isSuccess=0;
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("articleId", articleId);
		map.put("status","HANDLED");
		if("article".equals(type)){//如果是文章
			isSuccess=articleMapper.updateArticleStatus(map);
		}
		return isSuccess;
	}
	
	/**
	 * 检查名字并更改名字
	 * <p>Title: replaceName</p> 
	 * <p>Description: TODO</p>
	 * @param userId
	 * @param articleId
	 * @param articleName
	 * @return
	 */
	public Map<String, Object> replaceName(Integer userId,Integer articleId,String articleName){
		Map<String, Object> maps = new HashMap<String, Object>();
		String flag = "error";
		String info ="保存失败";
		Article bean = articleMapper.selectByPrimaryKey(articleId);
		boolean isSameName = checkWordName(0,userId,articleName,bean.getArticleType());
		if(!isSameName){//没有相同的名字
			if(getPassType(bean)){
				bean.setArticleName(articleName);
				bean.setPassType("SAVED");
				int i = articleMapper.updateArticleName(bean);
				articleMapper.updateArticleTime(articleId);
				if(i > 0){
					flag = "success";
					info ="保存成功";
				}else{
					flag = "error";
					info ="只能修改私有文档";
				}
			}else{
				flag = "error";
				info ="只能修改私有文档";
			}

		}else{
			flag = "error";
			info ="已经有重名文档";
		}
		maps.put("status", flag);
		maps.put("info", info);
		return maps;
	}
	/*
	 * 执行内容插件
	 * @param userId:用户id
	 * @param path 路径
	 */
	public void doContentPlugin(Integer userId,Integer articleId,String path){
		//System.out.println("version="+version);
		List<NodeBean> newList = new ArrayList<NodeBean>();//内容插件
		ContentWhole contentWhole = getArticleContentById(articleId);
		if(contentWhole.getNodeContent()==null){//没有元素
			//contentWhole.setNodeContent("<h1 id=\""+articleId+"\">标题1</h1>");
		}else{
			String regex="(<h[1-6][^>]*?>)([^\t]*?)(</h[1-6]>)";
			Pattern pattern = Pattern.compile(regex,Pattern.UNICODE_CASE+Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(contentWhole.getNodeContent());
			//List<NodeBean> newList = new ArrayList<NodeBean>();
			int i=0;
			while(matcher.find()){//遍历节点
				i++;
				String idStr=getId(matcher.group(1));
				int h = Integer.parseInt(matcher.group(1).substring(2,3));
				String title = matcher.group(2).replaceAll("<br/>", "").replaceAll("<[^>]*>", "");
				//replaceAll("\\&[a-zA-Z]{0,9};", "")
				//System.out.println("title="+title); Integer.parseInt
				NodeBean bean = new NodeBean();
				//bean.setPid(0);
				bean.setIdStr(idStr);
				bean.setOrder(h);
				bean.setNodeNameStatic(title);//标题的名字
				bean.setNodeNameDynamic(matcher.group(0));//标题的全称 包含h1
				String [] idStrArry = idStr.split("-");
				if(idStrArry.length>1){
					bean.setId(Integer.parseInt(idStrArry[1]));
					//bean.setIcon("tree_plugin");
				}else{
					bean.setId(0);
				}
				newList.add(bean);
			}//while
		}//else
		String  contentStr = contentWhole.getNodeContent(); 
		for(int i=0 ;i<newList.size();i++) {//遍历
			NodeBean bean = newList.get(i);
			if(bean.getId()>0){//这个是内容插件
				String strHtml=articleService.updateOneNodeContent(userId,bean,path,articleId);
				if("".equals(strHtml)){//空的，不处理
				}else{//有内容
					if(i>=newList.size()-1){//是最后一个点
						contentStr+=strHtml;
					}else{
						NodeBean nextBean =  newList.get(i+1);
						String[] strarray = contentStr.split(nextBean.getNodeNameDynamic());
						if(strarray.length>1){
							contentStr=strarray[0]+strHtml+nextBean.getNodeNameDynamic()+strarray[1];
						}
					}
				}
			}
		}//for 结束
		if(contentStr!=null){//存在字符串
			if(contentStr.equals(contentWhole.getNodeContent())){
				
			}else{//文档内容不一样
				setArticleContentById(articleId,contentStr);
			}
		}
	
	}
	/**
	 * 清理内容插件
	 * @param newList 是内容插件的节点
	 * @param content 文章的内容
	 * @return
	 */
	public String clearPluginNodeList(List<NodeBean> nodeList,String content){
		String newContent = "";
		//删除内容插件的子节点
		for(NodeBean bean:nodeList){
			
		}
		return newContent;
	}
	/*
	 * 清除内容插件，并返回新的字符串
	 */
	public String clearPluginStr(String content){
		List<NodeBean> newList = new ArrayList<NodeBean>();//内容插件
		
		if(content != null && !"".equals(content)){
			String regex="(<h[1-6][^>]*?>)([^\t]*?)(</h[1-6]>)";
			Pattern pattern = Pattern.compile(regex,Pattern.UNICODE_CASE+Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(content);
			while(matcher.find()){
				String idStr=getId(matcher.group(1));
				int h = Integer.parseInt(matcher.group(1).substring(2,3));
				String [] idStrArry = idStr.split("-");
				if(idStrArry.length>1){//这个节点是内容插件						
					NodeBean node = new NodeBean();
					node.setIdStr(idStr);
					node.setOrder(h);
					node.setNodeNameDynamic(matcher.group(0));//标题的全称 包含h1
					newList.add(node);
				}
			}
		}
		String newContent = content;
		for(NodeBean node:newList){
			//System.out.println("newContent="+newContent);
			newContent=clearPluginNode(node,newContent);
		}
		return newContent;
	}
	/**
	 * 清理内容插件
	 * @param newList 是内容插件的节点
	 * @param content 文章的内容
	 * 按照node拆分,在拆分的元素下,查找到同级或者父级节点，然后拆分,保存
	 * @return
	 */
	public String clearPluginNode(NodeBean node,String content){
		String hText = node.getNodeNameDynamic();
		String parentHText = "";
		//content="123"+content;
		//System.out.println(content);
		//System.out.println(hText);
		String[] strArray = content.split(hText);
		//System.out.println(strArray.length);
		if(strArray.length<=1){//
			return content;
		}
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(strArray[0]);
		strBuffer.append(hText);
		String regex="(<h[1-6][^>]*?>)([^\t]*?)(</h[1-6]>)";
		Pattern pattern = Pattern.compile(regex,Pattern.UNICODE_CASE+Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(strArray[1]);
		while(matcher.find()){//遍历节点
			String idStr=getId(matcher.group(1));
			int h = Integer.parseInt(matcher.group(1).substring(2,3));
			String [] idStrArry = idStr.split("-");
			if(idStrArry.length>1){//这个节点是内容插件	
				//contentNode+=matcher.group(0);
				strBuffer.append(matcher.group(0));
			}else{//不是内容插件
				if(h<=node.getOrder()){//这个是它的同级和父级节点
					parentHText=matcher.group(0);
					break;
				}
			}
		}
		strBuffer.append(parentHText);
		if(!("".equals(parentHText))){//存在下一个节点
			strBuffer.append(content.split(parentHText)[1]);
		}
		return strBuffer.toString();
	}
	/**
	 * 生成html
	 * @param articleId
	 * @return
	 */
	public boolean docToHtml(int articleId){
		return DocToHtml.docToHtml("doc", "article"+articleId+".doc");
	}
	/**
	 * 判断否有写的权限 true:有写的权限
	 * 'SAVED','SUBMITTED','FAILED','HANDLED','PASSED'
	 * @param article
	 * @return
	 */
	public boolean getPassType(Article article){
		boolean flag = false;
		if(article!=null){
			if("SUBMITTED".equals(article.getPassType())||"PASSED".equals(article.getPassType())){
				flag=false;
			}else{
				flag=true;
			}
		}else{
			flag=false;
		}
		return flag;
	}
	/**
	/**
	 * 是否有重名 true:有重名
	 * @param articleId 0:代表不走
	 * @param userId
	 * @param articleName
	 * @param type:null:不检索,document,
	 * @return true:有重名，false:没有重名
	 */
	public boolean checkWordName(int articleId,int userId,String articleName,String type){
		ArticleExample example = new ArticleExample();
		ArticleExample.Criteria cr = example.createCriteria();
		if(articleId!=0){
			cr.andArticleIdNotEqualTo(articleId);
		}
		if(type!=null&&!("".equals(type))){
			cr.andArticleTypeEqualTo(type);
		}
		cr.andUserIdEqualTo(userId);
		cr.andArticleNameEqualTo(articleName);
		cr.andPassTypeIsNotNull();
		List<Article> articleList = articleMapper.selectByExample(example);
		if(articleList.size()>0){//存在
			return true;//存在文档
		}else{
			return false;//不存在文档
		}
	}
/**
 * 根据ID获取期刊
 * @param id
 * @return
 */
	public WordJournal selectByPrimaryKey(Integer id){
		return wordJournalMapper.selectByPrimaryKey(id);
	}
	
}
