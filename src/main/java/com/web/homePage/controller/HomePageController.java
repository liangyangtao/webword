/*
 * <p>Title: 知识自动化平台</p>
 * <p>Description: HomePageController.java</p>
 * <p>Copyright: Copyright (c) 2015 北京银联信投资顾问有限责任公司，版权所有. </p>
 * <p>Company: 北京银联信投资顾问有限责任公司</p>
 * @author knight
 * @date 2015-5-5
 * @version 1.0
 */
package com.web.homePage.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.common.service.CommonController;
import com.database.bean.Article;
import com.database.bean.ContentWithBLOBs;
import com.database.bean.Homepage;
import com.database.bean.NewsSource;
import com.export.Builder;
import com.export.ChartReplacer;
import com.google.gson.Gson;
import com.web.homePage.bean.KnowPlate;
import com.web.homePage.service.HomePageService;
import com.web.homePage.util.GetZipDocFiles;
import com.web.homePage.util.HomePageUtil;
import com.web.homePage.util.Word;
import com.web.word.service.WordService;

/**
 * <p>Title: HomePageController</p>
 * <p>Description: 封面控制器</p>
 * @author knight
 * @date 2015-5-5
 */
@Controller
public class HomePageController extends CommonController {

	@Autowired
	private HomePageService homePageService ;
	@Autowired
	private WordService wordService;
	@Value("${nginxPic}")
	private String nginxPic;
	
	private static final Logger LOGGER = Logger.getLogger(HomePageController.class);
	
	
	/**
	 * 根据文档Id获取封面信息
	 * <p>Title: getHomePageSetting</p> 
	 * <p>Description: TODO</p>
	 * @param response
	 * @param articleId
	 * @throws Exception
	 */
	@RequestMapping(value="/homePage/getHomePageSetting")
	@ResponseBody
	public void getHomePageSetting(HttpServletResponse response, Integer articleId) throws Exception{
		Homepage hpBean = homePageService.getHomePageSetting(articleId);
		Map<String, Object> maps = new HashMap<String, Object>();
		if(hpBean == null){
			maps.put("status", "error");
			
		}else{
			maps.put("status", "success");
			maps.put("data", hpBean);
		}
		LOGGER.info("urlName=homePage/getHomePageSetting,urlMsg=获取封面,articleId="+articleId);
		responseJson(response, maps);
		
	}
	
	/**
	 * 上传图片
	 * <p>Title: upLoadPic</p> 
	 * <p>Description: TODO</p>
	 * @param response
	 * @param request
	 * @param homePage
	 * @param articleId
	 * @throws Exception
	 */
	@RequestMapping(value="/homePage/upLoadPic")
	@ResponseBody
	public void upLoadPic(HttpServletResponse response,HttpServletRequest request,
			MultipartFile homePage,Integer articleId) throws Exception{
		Map<String,Object> outmap = new HashMap<String, Object>();
		String article_id = request.getParameter("articleId");
		articleId = Integer.parseInt(article_id);
		//String imagepath = request.getServletContext().getRealPath("/upload");
	//	File dirFile = new File(imagepath);
		File dirFile = new File(nginxPic+"upload");
		if(!dirFile.exists()){
			dirFile.mkdir();
		}
		LOGGER.info("urlName=homePage/upLoadPic,urlMsg=上传封面,articleId="+articleId);
		ServletContext servletContext = request.getSession().getServletContext();
		//spring的文件处理解析类，包装servlet的上下文。
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(servletContext);
        if (multipartResolver.isMultipart(request)) {
        	//把request请求进行升级，request有的，它都有，
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //获得上传文件的名称
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()){
            	//包装过的文件流
                MultipartFile file = multiRequest.getFile((String)iter.next());
                //这里要进行判断，即使是空值，没有上传内容，file都是有值（空值）的，而文件流大小要大于0才是有上传的东西
                if (file.getSize() > 0){
                	//判断文件大小
                	if(file.getSize() >1048576){
                		outmap.put("success",false);
                        outmap.put("msg","上传文件不能大于1MB！");
                	}else{
                		//获得上传文件原始名
                        String imagename = file.getOriginalFilename();
                        //算出后缀名
                        String ext = imagename.substring(imagename.lastIndexOf(".")) ;
                        //对文件类型进行判断，这个操作也可以在前台进行处理，在前台进行处理比较好，前后台都进行处理最稳妥
                        List<String> fileTypes = new ArrayList<String>();
                        fileTypes.add(".jpg");
                        fileTypes.add(".jpeg");
                        fileTypes.add(".bmp");
                        fileTypes.add(".gif");
                        fileTypes.add(".png");
                        //是图片再进行处理
                        if (fileTypes.contains(ext.toLowerCase())){
                            String fileName = System.currentTimeMillis() + ext ;
                        //    File localFile = new File(imagepath,fileName);
                            File localFile = new File(nginxPic+"upload",fileName);
                            try {
                            	//直接写入到后台服务器，简单且快
                                file.transferTo(localFile);
                                //修改图片文件尺寸
                            	BufferedImage image = ImageIO.read(localFile);
                            	BufferedImage newimg = HomePageUtil.resize(image, 795, 1122, true);
                            	ImageIO.write(newimg, "png", localFile);
                            	Homepage tempBean = homePageService.getHomePageSetting(articleId);
                            	if(tempBean != null){
                                	StringBuffer sb = new StringBuffer(localFile.getAbsolutePath());
                            		int in = localFile.getAbsolutePath().lastIndexOf("\\");
                            		sb.replace(in, in+1, "/");
                                	tempBean.setBasepicpath(sb.toString());
                                	homePageService.updateHomePageSetting(tempBean);
                            	}else{
                            		tempBean = new Homepage();
                                	StringBuffer sb = new StringBuffer(localFile.getAbsolutePath());
                            		int in = localFile.getAbsolutePath().lastIndexOf("\\");
                            		sb.replace(in, in+1, "/");
                                	tempBean.setBasepicpath(sb.toString());
                                	tempBean.setArticleId(articleId);
                                	homePageService.saveHomePageSetting(tempBean);
                            	}
                                outmap.put("success",true);
                                outmap.put("picpath",tempBean.getBasepicpath().substring(tempBean.getBasepicpath().indexOf("upload")).replace("\\", "/"));
							} catch (Exception e) {
								outmap.put("success",false);
	                            outmap.put("msg","系统出错");
							}
                        } else{
                        	outmap.put("success",false);
                            outmap.put("msg","图片格式出错！");
                        }
                	}
                }
            }
        }
        Gson gson = new Gson();
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		//response.setContentType("application/json; charset=utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(gson.toJson(outmap));
		//System.out.println(gson.toJson(outmap));
		out.close();
	}
	
	/**
	 * 保存封面信息
	 * <p>Title: saveHomePageSetting</p> 
	 * <p>Description: TODO</p>
	 * @param response
	 * @param strhpBean
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="/homePage/saveHomePageSetting")
	@ResponseBody
	public void saveHomePageSetting(HttpSession session,HttpServletResponse response, String strhpBean,
			HttpServletRequest request) throws Exception{
		
		Map<String,Object> map = new HashMap<String,Object>();
		Gson gson = new Gson();
		Homepage hpBean = gson.fromJson(strhpBean, Homepage.class);
		Article article = homePageService.searchArticleById(hpBean.getArticleId());
		if(article!=null){
			if(wordService.getPassType(article)){
				Homepage tempBean = homePageService.getHomePageSetting(hpBean.getArticleId());
				int articleId = 0;
				if(tempBean == null){
					articleId = homePageService.saveHomePageSetting(hpBean);
				} else{
					hpBean.setBasepicpath(tempBean.getBasepicpath());
				//	String imagepath = request.getServletContext().getRealPath("/upload");
					
					String picPath = articleId+System.currentTimeMillis()+".png";
//					if(hpBean.getBasepicpath() != null && !hpBean.getBasepicpath().equals("")){
//						HomePageUtil.getPicPath(hpBean.getBasepicpath(),imagepath+"/"+picPath,hpBean);
//						hpBean.setSavepicpath(imagepath+"/"+picPath);
//					}
					if(hpBean.getBasepicpath() != null && !hpBean.getBasepicpath().equals("")){
						HomePageUtil.getPicPath(hpBean.getBasepicpath(),nginxPic+"upload/"+picPath,hpBean);
						hpBean.setSavepicpath(nginxPic+"upload/"+picPath);
					}
					hpBean.setHomepagePropertyId(tempBean.getHomepagePropertyId());
					homePageService.updateByPrimaryKey(hpBean);
					homePageService.updateArticleTime(hpBean.getArticleId());
				}
				String picpath = "";
				if(hpBean.getSavepicpath() != null){
					int index = hpBean.getSavepicpath().indexOf("upload");
					picpath = hpBean.getSavepicpath().substring(index);
				}
				map.put("state", "success");
				map.put("info", "插入成功");
				map.put("picpath", picpath);
			}else{
				map.put("state", "error");
				map.put("info", "只能编辑私有的文档");
			}
			map.put("article", article);
		}else{
			map.put("state", "error");
			map.put("info", "操作无效,该篇文档不存在");
		}
		int userId=0;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		try{
			LOGGER.info("urlName=homePage/saveHomePageSetting,urlMsg=保存封面,userId="+userId+",state="+map.get("state")+",info="+map.get("info"));
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		responseJson(response,map);
	}
	
	/**
	 * 删除封面信息
	 * <p>Title: deleteHomePageSetting</p> 
	 * <p>Description: TODO</p>
	 * @param response
	 * @param articleId
	 * @throws Exception
	 */
	@RequestMapping(value="/homePage/deleteHomePageSetting")
	@ResponseBody
	public void deleteHomePageSetting(HttpServletResponse response, Integer articleId) throws Exception{
		
		homePageService.deleteHomePageSetting(articleId);
		homePageService.updateArticleTime(articleId);
		Map<String, Object> maps = new HashMap<String, Object>();

		maps.put("status", "success");
		
		try{
			LOGGER.info("urlName=homePage/deleteHomePageSetting,urlMsg=删除封面,articleId="+articleId+",state="+maps.get("state")+",info="+maps.get("info"));
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		responseJson(response, maps);
	}
	
	/**
	 * 下载文档
	 * <p>Title: downloadArticle</p> 
	 * <p>Description: TODO</p>
	 * @param request
	 * @param response
	 * @param articleId
	 * @param type 文档类型
	 * @throws Exception
	 */
	@RequestMapping(value="/homePage/downloadArticle")
	@ResponseBody
	public void downloadArticle(HttpServletRequest request,HttpServletResponse response,
			Integer articleId,String type) throws Exception{
		//执行下载boolean变量
		boolean flag = true;
		//获取要下载的文章
		Map<String,String> maps = new HashMap<String,String>();
		Article article = homePageService.searchArticleById(articleId);
		if(article!=null){
			String path=request.getSession().getServletContext().getRealPath("");
			flag=homePageService.articleToWord(article,path,articleId,type);

			maps.put("downurl", "doc/"+article.getArticleName()+".doc");
			maps.put("fileName", article.getArticleName()+".doc");
			maps.put("articleId", Integer.toString(articleId));
			maps.put("type", type);
			if(!flag){
				maps.put("state", "error");
				maps.put("flag","false");
				maps.put("info","失败");
			}else{
				maps.put("state", "success");
				maps.put("flag", "true");
				maps.put("info","成功");
			}
		}else{
			maps.put("flag","false");
			maps.put("state", "error");
			maps.put("info","操作无效,该篇文档不存在");
		}

		
		try{
			LOGGER.info("urlName=homePage/downloadArticle,urlMsg=下载文档,articleId="+articleId+",state="+maps.get("state")+",info="+maps.get("info"));
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		responseJson(response, maps);
	}
	
	/**
	 * 下载内容
	 * <p>Title: downloadContent</p> 
	 * <p>Description: TODO</p>
	 * @param request
	 * @param response
	 * @param articleId
	 * @param type 文档类型
	 * @throws Exception
	 */
	@RequestMapping(value="/homePage/downloadContent")
	@ResponseBody
	public void downloadContent(HttpServletRequest request,HttpServletResponse response,
			Integer articleId,String type) throws Exception{
		ContentWithBLOBs content=null;
		content= homePageService.getContentById(articleId);
		if(content==null){
			content=homePageService.getCrawlByContentId(articleId);
		}
		
		String path=request.getSession().getServletContext().getRealPath("");
		long strtime=System.currentTimeMillis();
		//System.out.println("path="+path);
		String htmlPath = path+"\\doc\\"+type+articleId+strtime+".jsp";
		String docPath = path+"\\doc\\"+type+articleId+strtime+".doc";
		
		String strHtml =homePageService.getContentHtml(content);
		//获取图片的文件列表
		List<File> lists = new ArrayList<File>();
		strHtml=ChartReplacer.replaceIMG(strHtml,lists,path);
		//写入文件
		Builder builder = Builder.getInstance();
		builder.writeFile(htmlPath, strHtml);
			
		File htmlFile = new File(htmlPath);
		File docFile = new File(docPath);
		//将节点的所有内容都填充到HTML中
		Word.htmlToWord(htmlFile, docFile,null,content.getContentName());
		//将word文档中的图片都替换成图片。
		Word.insertChart(docFile, lists);
		
		builder.copyFile(htmlPath,path+"\\doc\\"+type+articleId+".jsp");
		builder.copyFile(docPath,path+"\\doc\\"+type+articleId+".doc");
		
		Map<String,String> maps = new HashMap<String,String>();
		maps.put("downurl", "doc/"+content.getContentName()+".doc");
		maps.put("fileName", content.getContentName()+".doc");
		maps.put("articleId",Integer.toString(articleId));
		maps.put("type", type);
		
		try{
			LOGGER.info("urlName=homePage/downloadContent,urlMsg=下载内容,articleId="+articleId+",fileName="+maps.get("fileName"));
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		responseJson(response, maps);
	}
	
	/**
	 * 下载
	 * <p>Title: downloadfile</p> 
	 * <p>Description: TODO</p>
	 * @param request
	 * @param response
	 * @param fileName
	 * @param articleId
	 * @param type
	 * @throws Exception
	 */
	@RequestMapping(value="homePage/downloadfile")
	@ResponseBody
	public void downloadfile(HttpServletRequest request,HttpServletResponse response, String fileName,Integer articleId,String type) throws Exception{
		response.setCharacterEncoding("utf-8");
		String agent = request.getHeader("User-Agent").toUpperCase();
		//RV:11.0是 ie11
		if (agent.indexOf("MSIE") > 0 || agent.indexOf("RV:11.0")>0) {
			fileName = URLEncoder.encode(fileName, "UTF-8");
		} else {
			fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
		}
		LOGGER.info("urlName=homePage/downloadfile,urlMsg=下载文档,articleId="+articleId);
		response.setHeader("Content-Disposition", "attachment;fileName="+fileName);
		response.setContentType("application/msword");
		//String path=request.getSession().getServletContext().getRealPath("");
		File file=new File(nginxPic+"\\doc\\"+type+articleId+".doc");
		response.setContentLength((int) file.length());
		InputStream inputStream=null;
		OutputStream os=response.getOutputStream();
		try {
			inputStream = new FileInputStream(file);
			byte[] b=new byte[1024];
			int length;
			while((length=inputStream.read(b))>0){
				os.write(b,0,length);
			}
			os.flush();
			os.close();
			inputStream.close();
			
		} catch (Exception e) {
			if(inputStream!=null){
				inputStream.close();
			}
			
		}
	}
	/**
	 * 查询所有分类
	 * <p>Title: queryAllPlate</p> 
	 * <p>Description: TODO</p>
	 * @param req
	 * @param redq
	 * @param pid
	 * @throws IOException
	 */
	@RequestMapping(value = "homePage/queryAllPlate")
	@ResponseBody
	public void queryAllPlate(HttpServletRequest req,HttpServletResponse redq, Integer pid) throws IOException{
		List<KnowPlate> kplist=homePageService.queryAllPlate(pid);
		responseJson(redq,kplist);	
		
	}
	
	/**
	 * 搜索新闻源
	 * <p>Title: searchNewsSource</p> 
	 * <p>Description: TODO</p>
	 * @param req
	 * @param redq
	 * @param newsName
	 * @param moduleName
	 * @throws IOException
	 */
	@RequestMapping(value = "homePage/searchNewsSource")
	@ResponseBody
	public void searchNewsSource(HttpServletRequest req,HttpServletResponse redq, String newsName,String moduleName) throws IOException{
		NewsSource newsBean = new NewsSource();
		String noIds = req.getParameter("noIds");
		if(noIds != null && !"".equals(noIds)){
			newsBean.setNoIds(noIds);
		}
		if(newsName != null && !"".equals(newsName)){
			newsBean.setSourcename(newsName);
		}
		if(moduleName != null && !"".equals(moduleName)){
			newsBean.setModulename(moduleName);
		}
		List<NewsSource> newsSourcelist=homePageService.searchNewsSource(newsBean);
		List<NewsSource> children = new ArrayList<NewsSource>();
		NewsSource source = new NewsSource();
		source.setId(140);
		source.setPid(4);
		//source.setSourcename("2258新闻");
		source.setState(1);
		children.add(source);
		for(int i=0;i<newsSourcelist.size();i++){
			if(newsSourcelist.get(i).getId()==4){
				newsSourcelist.get(i).setChildren(children);
				break;
			}
		}
		responseJson(redq,newsSourcelist);	
		
		
	}
	
	@RequestMapping(value="homePage/importFile")
	@ResponseBody
	public void importFile(HttpServletResponse response,HttpServletRequest request,HttpSession session,MultipartFile homePage,Integer articleId) throws IOException{
		//获取用户的id
		Integer userId=1;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		LOGGER.info("urlName=homePage/importFile,urlMsg=上传文档,userId="+userId);
		Integer wordarticleId = null;
		Map<String,Object> outmap = new HashMap<String, Object>();
			String imagepath = request.getServletContext().getRealPath("/upload");//homePage.getOriginalFilename()
			File dirFile = new File(imagepath);
			if(!dirFile.exists()){
				dirFile.mkdir();
			}
			ServletContext servletContext = request.getSession().getServletContext();
	        //spring的文件处理解析类，包装了servlet的上下文。
	        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(servletContext);
	        //如果是multipart的提交，这个判断有点多余，不过还是加上吧！
	        if (multipartResolver.isMultipart(request)) {
	            //把request请求进行升级，request有的，它都有，
	            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
	            //获得上传文件的名称
	            Iterator<String> iter = multiRequest.getFileNames();
	            //如果有的话就依次取出来
	            while (iter.hasNext()){
	                //包装过的文件流
	                MultipartFile file = multiRequest.getFile((String)iter.next());
	                //这里要进行判断，即使是空值，没有上传内容，file都是有值（空值）的，而文件流大小要大于0才是有上传的东西
	                if (file.getSize() > 0){
	                    //获得上传文件原始名
	                    String imagename = file.getOriginalFilename();
	                    String cleanImageName=null;
	                    //算出后缀名
	                    String ext = imagename.substring(imagename.lastIndexOf(".")) ;
	                    //对文件类型进行判断，这个操作也可以在前台进行处理，在前台进行处理比较好，前后台都进行处理最稳妥
	                    List<String> fileTypes = new ArrayList<String>();
	                    fileTypes.add(".doc");
	                    fileTypes.add(".docx");
	                    if (fileTypes.contains(ext.toLowerCase())){
	                    	
	                        //文件名为：唯一的工号 + avatar + 系统时间 + 后缀
	                        String fileName = System.currentTimeMillis() + ext ;
	                        File localFile = new File(imagepath,fileName);
	                        try {
	                            //直接写入到后台服务器，简单且快
	                            file.transferTo(localFile);
	                            String wordPath=localFile+"";
	                            try {
	                            if(imagename.contains(".doc")){
	                            	cleanImageName=imagename.replace(".doc", "");
	                            }
	                            if(imagename.contains(".docx")){
	                            	cleanImageName=imagename.replace(".docx", "");
	                            }
								wordarticleId=homePageService.importFile(cleanImageName,userId,imagepath,wordPath);
								} catch (Exception e) {
									
								}
	                            outmap.put("articleId", wordarticleId);
	                            outmap.put("success",true);
	                            outmap.put("msg","添加成功！");
	                        } catch (IOException e) {
	                            
	                            outmap.put("success",false);
	                            outmap.put("msg","系统出错");
	                        }
	                    } else {
	                        outmap.put("success",false);
	                        outmap.put("msg","文档格式出错！");
	                    }
	                }
	            }
	        }
	        Gson gson = new Gson();
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.write(gson.toJson(outmap));
			out.close();
		}
	/**
	 * 批量导入
	 * <p>Title: batchImportFile</p> 
	 * <p>Description: TODO</p>
	 * @param response
	 * @param request
	 * @param session
	 * @param homePage
	 * @param articleId
	 * @throws IOException
	 */
	@RequestMapping(value="homePage/batchImportFile")
	@ResponseBody
	public void batchImportFile(HttpServletResponse response,HttpServletRequest request,HttpSession session,
			MultipartFile homePage,Integer articleId) throws IOException{
		//获取用户的id
		Integer userId=1;
		if(session.getAttribute("userId")!=null){
			userId = (Integer) session.getAttribute("userId");
		}
		LOGGER.info("urlName=homePage/batchImportFile,urlMsg=批量上传文档,userId="+userId);
		Integer wordarticleId = null;
		Map<String,Object> outmap = new HashMap<String, Object>();
			String imagepath = request.getServletContext().getRealPath("/upload");//homePage.getOriginalFilename()
			File dirFile = new File(imagepath);
			if(!dirFile.exists()){
				dirFile.mkdir();
			}
			ServletContext servletContext = request.getSession().getServletContext();
	        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(servletContext);
	        if (multipartResolver.isMultipart(request)) {
	            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
	            Iterator<String> iter = multiRequest.getFileNames();
	            while (iter.hasNext()){
	                MultipartFile file = multiRequest.getFile((String)iter.next());
	                if (file.getSize() > 0){
	                    String imagename = file.getOriginalFilename();
	                    String ext = imagename.substring(imagename.lastIndexOf(".")) ;
	                    List<String> fileTypes = new ArrayList<String>();
	                    fileTypes.add(".zip");
	                    if (fileTypes.contains(ext.toLowerCase())){
	                        String fileName =  System.currentTimeMillis() + ext ;
	                        File localFile = new File(imagepath,fileName);
	                        try {
	                            file.transferTo(localFile);
	                            String zipPath=localFile+"";
	                            //对zip包进行解压，然后获取每个word文件的绝对路径
	                           List<File> files= GetZipDocFiles.getFiles(zipPath, imagepath);
	                           for(File docFile:files){
	                   			String fileAbsolutePath=docFile.getAbsolutePath();
	                   			String wordFileName=docFile.getName();
	                   			if(wordFileName.contains(".doc")){
	                   				wordFileName=wordFileName.replace(".doc", "");
	                   			}
	                   			if(wordFileName.contains(".docx")){
	                   				wordFileName=wordFileName.replace(".docx", "");
	                   			}
	                   			try {
	                   				wordarticleId=homePageService.importFile(wordFileName,userId,imagepath,fileAbsolutePath);
								} catch (Exception e) {
									
								}
	                   			
	                   		}
	                            outmap.put("articleId", wordarticleId);
	                            outmap.put("success",true);
	                            outmap.put("msg","添加成功！");
	                        } catch (IOException e) {
	                            
	                            outmap.put("success",false);
	                            outmap.put("msg","系统出错");
	                        }
	                    } else {
	                        outmap.put("success",false);
	                        outmap.put("msg","文档格式出错！");
	                    }
	                }
	            }
	        }
	        Gson gson = new Gson();
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.write(gson.toJson(outmap));
			out.close();
		}
}
