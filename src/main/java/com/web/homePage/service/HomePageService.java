/*
 * <p>Title: 知识自动化平台</p>
 * <p>Description: HomePageService.java</p>
 * <p>Copyright: Copyright (c) 2015 北京银联信投资顾问有限责任公司，版权所有. </p>
 * <p>Company: 北京银联信投资顾问有限责任公司</p>
 * @author knight
 * @date 2015-5-5
 * @version 1.0
 */
package com.web.homePage.service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.database.bean.Article;
import com.database.bean.ArticleExample;
import com.database.bean.ContentWhole;
import com.database.bean.ContentWithBLOBs;
import com.database.bean.Homepage;
import com.database.bean.NewsSource;
import com.database.bean.NewsSourceExample;
import com.database.dao.ArticleMapper;
import com.database.dao.ContentMapper;
import com.database.dao.ContentWholeMapper;
import com.database.dao.HomepageMapper;
import com.database.dao.NewsSourceMapper;
import com.export.Builder;
import com.export.ChartReplacer;
import com.export.Function;
import com.web.homePage.bean.KnowPlate;
import com.web.homePage.util.ArticlePageOne;
import com.web.homePage.util.GetWholeContent;
import com.web.homePage.util.NodeCompant;
import com.web.homePage.util.Word;
import com.web.homePage.util.XmlUtils;
import com.web.word.bean.NodeBean;
import com.web.word.bean.NodeContentBean;

/**
 * <p>Title: HomePageService</p>
 * <p>Description: 封面业务处理类</p>
 * @author knight
 * @date 2015-5-5
 */
@Service
public class HomePageService {

	@Autowired
	private HomepageMapper homepageMapper ;
	
	@Autowired
	private ArticleMapper articleMapper ;
	
	@Autowired
	private NodeCompant nodeCompant;
	
	@Autowired
	private ContentMapper contentMapper ;
	
	@Autowired
	private NewsSourceMapper newsSourceMapper;
	
	@Autowired
	private ContentWholeMapper contentWholeMapper;
	
	@Value("${picUrl}")
	private String picUrl;
	
	@Value("${oldPicUrl}")
	private String oldPicUrl;
	@Value("${nginxPic}")
	private String nginxPic;
	/**
	 * 根据文档Id查询封面信息
	 * <p>Title: getHomePageSetting</p> 
	 * <p>Description: TODO</p>
	 * @param articleId
	 * @return
	 */
	public Homepage getHomePageSetting(Integer articleId){
		Homepage bpBean = null;
		bpBean = homepageMapper.selectByArticleId(articleId);
		return bpBean ;
	}
	
	/**
	 * 更新封面信息
	 * <p>Title: updateHomePageSetting</p> 
	 * <p>Description: TODO</p>
	 * @param bpBean
	 */
	public int updateHomePageSetting(Homepage bpBean){
		return homepageMapper.updateByPrimaryKeySelective(bpBean);
	}
	/**
	 * 更新封面信息,空的置空
	 * @param bpBean
	 * @return
	 */
	public int updateByPrimaryKey(Homepage bpBean){
		return homepageMapper.updateByPrimaryKey(bpBean);
	}
	
	/**
	 * 保存封面信息
	 * <p>Title: saveHomePageSetting</p> 
	 * <p>Description: TODO</p>
	 * @param bpBean
	 */
	public int saveHomePageSetting(Homepage bpBean) {
		return homepageMapper.insertSelective(bpBean);
	}
	
	/**
	 * 更新文档修改时间
	 * <p>Title: updateArticleTime</p> 
	 * <p>Description: TODO</p>
	 * @param articleId
	 */
	public void updateArticleTime(Integer articleId){
		articleMapper.updateArticleTime(articleId);
	}
	
	/**
	 * 删除封面信息
	 * <p>Title: deleteHomePageSetting</p> 
	 * <p>Description: TODO</p>
	 * @param articleId
	 */
	public void deleteHomePageSetting(Integer articleId){
		homepageMapper.deleteHomePageSetting(articleId);
	}
	
	/**
	 * 根据文章id获取文章
	 * <p>Title: searchArticleById</p> 
	 * <p>Description: TODO</p>
	 * @param articleId
	 * @return
	 */
	public Article searchArticleById(Integer articleId){
		return articleMapper.selectByPrimaryKey(articleId);
	}
	
	/**
	 * 根据id获取html内容
	 * <p>Title: getArticleHtml</p> 
	 * <p>Description: TODO</p>
	 * @param articleId
	 * @return
	 */
	public String getArticleHtml(Integer articleId){
		String strHtml="";
		String style=getArticleStyle(0);
		Builder builder = Builder.getInstance();
		List<NodeContentBean> contentBeans = searchContent(articleId);
		
		if(contentBeans.size() > 0){
			try {
				NodeContentBean  bean= contentBeans.get(0);
				//替换oldPicUrl
				if(picUrl.equals(oldPicUrl)){
				}else{//不相等替换
					String content=bean.getContent().replaceAll(oldPicUrl+"unbankImage", picUrl+"unbankImage");
					//System.out.println(content);
					bean.setContent(content);
				}
				strHtml = builder.getContentHtmlStr("name", bean.getContent(), style);
			} catch (Exception e) {
				
			}
		}
		return strHtml;
	}
	
	/**
	 * 搜索文章
	 * <p>Title: searchArticle</p> 
	 * <p>Description: TODO</p>
	 * @param articleId
	 * @return
	 */
	public Map<String, Object> searchArticle(Integer articleId) {
		Map<String, Object> datas = new HashMap<String, Object>();
		// 查询文章或文档的大纲信息
		List<NodeBean> nodeBeans = searchOutline(articleId);
		datas.put("nodes", nodeBeans);
		// 查询表文章或文档的大纲的内容信息
		List<NodeContentBean> contentBeans = searchContent(articleId);
		datas.put("contents", contentBeans);
		
		return datas;
	}
	
	/**
	 * 获取文章样式
	 * <p>Title: getArticleStyle</p> 
	 * <p>Description: TODO</p>
	 * @param id
	 * @return
	 */
	public String  getArticleStyle(int id) {
		return articleMapper.getArticleStyle(id);
	}
	
	/**
	 * 查询文章或文档的大纲信息
	 * <p>Title: searchOutline</p> 
	 * <p>Description: TODO</p>
	 * @param articleId
	 * @return
	 */
	public List<NodeBean> searchOutline(Integer articleId){
		List<NodeBean> targetList = new ArrayList<NodeBean>();
		NodeBean nodeBean = new NodeBean();
		nodeBean.setArticleId(articleId);
		List<NodeBean> originalList = articleMapper.selectNodeTempByArticleId(nodeBean);
		nodeCompant.recursion(targetList, originalList, 0);
		return targetList;
	}
	
	/**
	 * 查询表文章或文档的大纲的内容信息
	 * <p>Title: searchContent</p> 
	 * <p>Description: TODO</p>
	 * @param articleId
	 * @return
	 */
	public List<NodeContentBean> searchContent(Integer articleId){
		NodeContentBean bean = new NodeContentBean();
		bean.setArticleId(articleId);
		return articleMapper.selectContentTempByArticleId(bean);
	}
	
	/**
	 * 更新文章下载时间
	 * <p>Title: updateArticleDownTime</p> 
	 * <p>Description: TODO</p>
	 * @param articleId
	 */
	public void updateArticleDownTime(Integer articleId){
		articleMapper.updateArticleDownTime(articleId);
	}
	
	/**
	 * 获取内容
	 * <p>Title: getContentById</p> 
	 * <p>Description: TODO</p>
	 * @param articleId
	 * @return
	 */
	public ContentWithBLOBs getContentById(Integer articleId){
		return contentMapper.selectByPrimaryKey(articleId);
	}
	
	/**
	 * 获取最新内容
	 * <p>Title: getCrawlByContentId</p> 
	 * <p>Description: TODO</p>
	 * @param articleId
	 * @return
	 */
	public ContentWithBLOBs getCrawlByContentId(Integer articleId){
		return contentMapper.getCrawlByContentId(articleId);
	}
	
	/**
	 * 获取内容html
	 * <p>Title: getContentHtml</p> 
	 * <p>Description: TODO</p>
	 * @param content
	 * @return
	 */
	public String getContentHtml(ContentWithBLOBs content){
		NodeBean bean= new NodeBean();
		bean.setRealId(1);
		bean.setContent(content.getContent());
		bean.setNodeNameStatic(content.getContentName());
		List<NodeBean> list = new ArrayList<NodeBean>();
		list.add(bean);
		String style="";
		Builder builder = Builder.getInstance();
		List<NodeBean> newnodes = new ArrayList<NodeBean>();
		int realId=1;//标题h2开始
		List<NodeBean> nodes=builder.getNodeList(list, newnodes,realId);
		String strHtml="";
		try {
			strHtml = builder.getHtmlStr("name", nodes, style);
		} catch (Exception e) {
			
		}
		return strHtml;
	}
	
	/**
	 * 获取所有分类
	 * <p>Title: queryAllPlate</p> 
	 * <p>Description: TODO</p>
	 * @param pid
	 * @return
	 */
	public List<KnowPlate> queryAllPlate(Integer pid) {
		return articleMapper.queryAllPlate(pid);
	}
	
	/**
	 * 获取新闻源
	 * <p>Title: searchNewsSource</p> 
	 * <p>Description: TODO</p>
	 * @param newsBean
	 * @return
	 */
	public List<NewsSource> searchNewsSource(NewsSource newsBean) {
		NewsSourceExample example = new NewsSourceExample();
		NewsSourceExample.Criteria cr = example.createCriteria();
		if(newsBean.getSourcename() != null && !"".equals(newsBean.getSourcename())){
			cr.andSourcenameLike("%" + newsBean.getSourcename() +"%");
		}
		String ids = newsBean.getNoIds();
		String[] id = ids.split(",");
		List<Integer> idList = new ArrayList<Integer>();
		for(int i=0;i<id.length;i++){
			idList.add(Integer.parseInt(id[i]));
		}
		cr.andIdNotIn(idList);
		return newsSourceMapper.selectByExample(example);
	}
	
	/**
	 * 导入文件
	 * <p>Title: importFile</p> 
	 * <p>Description: TODO</p>
	 * @param cleanImageName
	 * @param userId
	 * @param imagepath
	 * @param wordPath
	 * @return
	 */
	public int importFile(String cleanImageName,Integer userId,String imagepath,String wordPath){
		Integer articleId = 0;
		DateFormat dd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//获取ID
		ArticleExample articleExmaple = new ArticleExample();
		com.database.bean.ArticleExample.Criteria articleCr = articleExmaple.createCriteria();
		articleCr.andArticleNameEqualTo(cleanImageName);
		List<Article> articlelist = articleMapper.selectByExample(articleExmaple);
		if(articlelist.size()>0){//文件大于0
			cleanImageName=cleanImageName+"_"+System.currentTimeMillis();
		}
		for(int i=0;i<articlelist.size();i++){
			articleId = articlelist.get(0).getArticleId();
		}
		//添加Id
		Article record = new Article();
		record.setUserId(userId);
		record.setArticleName(cleanImageName);
		record.setArticleType("document");
		record.setPassType("SAVED");
		record.setInsertTime(Timestamp.valueOf(dd.format(new Date())));
		record.setUpdateTime(Timestamp.valueOf(dd.format(new Date())));
		articleMapper.insertSelective(record);
		articleId=record.getArticleId();
		
		//设置内容
		GetWholeContent getWholeContent = new GetWholeContent(wordPath,imagepath);
		String nodeContent = getWholeContent.getContent();
		ContentWhole whole = new ContentWhole();
		whole.setArticleId(articleId);
		whole.setNodeContent(nodeContent);
		whole.setNodeId(0);
		contentWholeMapper.saveContent(whole);
		return articleId;
	}
	/**
	 * 生成word文档
	 * @param article
	 * @param path
	 * @param articleId
	 * @param type
	 * @return
	 */
	public boolean articleToWord(Article article,String path,int articleId,String type){
		long strtime=System.currentTimeMillis();
		//如果配了路径走配置路径 
		if(nginxPic!=null && !nginxPic.equals("")){
			path = nginxPic;
		}
		boolean flag = true;
		String htmlPath = path+"\\doc\\"+type+articleId+strtime+".jsp";
		String docPath = path+"\\doc\\"+type+articleId+strtime+".doc";
		File docFile = new File(docPath);
		if(article.getDownTime()==null||!new File(path+"\\doc\\"+type+articleId+".doc").exists()
				|| (article.getUpdateTime()!=null && article.getUpdateTime().after(article.getDownTime()))){//文件更新
			//得到封面信息
			Homepage hpBean = getHomePageSetting(articleId);
			//获取文章的html信息
			String strHtml = getArticleHtml(articleId);
			//替换网络中http图片
			try {
				strHtml=Function.getHtml(strHtml,path);
				//给标题1 加分页符
				strHtml=Function.getPageH1Str(strHtml);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//特殊字符串替换
			XmlUtils.getInstance();
			List<Map<String,Object>> list = XmlUtils.getSpecialCharactersConfig();
			if(list.size()>0){
				for(Map<String,Object> temp : list){
					strHtml = StringUtils.replace(strHtml, temp.get("name").toString(), temp.get("value").toString());
				}
			}
			//获取图片的文件列表
			List<File> lists = new ArrayList<File>();
			//图片地址改为动态可配置（集群部署动静分离） 2015-10-21 范久滨
			strHtml=ChartReplacer.replaceIMG(strHtml,lists,path);
			//写入文件
			Builder builder = Builder.getInstance();
			builder.writeFile(htmlPath, strHtml);
			File htmlFile = new File(htmlPath);
			//保存封面参数
			Map<String,Object> map = null;	
			//向word中添加图片
			if(hpBean != null && hpBean.getBasepicpath() != null && !hpBean.getBasepicpath().equals("")){
				map = ArticlePageOne.homePageParams(hpBean);
				//将节点的所有内容都填充到HTML中
				//图片地址改为动态可配置（集群部署动静分离） 2015-10-21 范久滨
				flag = Word.htmlToWord(htmlFile, docFile,path+hpBean.getBasepicpath().
						substring(hpBean.getBasepicpath().indexOf("upload")),article.getArticleName());
				//编辑封面内容
				Word.createHomePage(docFile,map);
			}else{
				//将节点的所有内容都填充到HTML中
				flag = Word.htmlToWord(htmlFile, docFile,null,article.getArticleName());
			}			
			//将word文档中的图片都替换成图片。
			Word.insertChart(docFile, lists);

			//htmlFile.delete();//删除jsp
			builder.copyFile(htmlPath,path+"\\doc\\"+type+articleId+".jsp");
			builder.copyFile(docPath,path+"\\doc\\"+type+articleId+".doc");
			
			updateArticleDownTime(articleId);
		}
		return flag;
	}
}
