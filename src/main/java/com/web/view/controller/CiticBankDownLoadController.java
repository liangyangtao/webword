package com.web.view.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.database.bean.Article;
import com.database.bean.WordArticleCount;
import com.web.view.service.HomeService;
import com.web.view.utils.CiticBankUtils;

@Controller
@RequestMapping(value = "/view/")
public class CiticBankDownLoadController {
	
	private static Logger LOGGER = Logger.getLogger(CiticBankDownLoadController.class);
	@Autowired
	HomeService homeService;
	@Value("${nginxPic}")
	private String nginxPic;
	@Value("${citicjournalids}")
	private String citicjournalids;
	@RequestMapping(value = "/documentgetbyid")
	public String download(HttpSession session, HttpServletRequest request,
			HttpServletResponse response, Model model, int articleId,String sign) {
	
		
			//可以下载
			Article article = homeService.getArticle(articleId);
			//查当前articleId是否属于限定的期刊中id
			String [] jids = citicjournalids.trim().split(",");
			List<String> templist = Arrays.asList(jids);
			if(!templist.contains(article.getArticleJournalId()+"")){
				return null;
			}
			String path = nginxPic+"doc"
					+ File.separator + "article" + articleId +"."+article.getArticleFormat();
			File file = new File(path);
			int userId=0;
//			if(session.getAttribute("userId")!=null){
//				userId = (Integer) session.getAttribute("userId");
//			}
//			LOGGER.info("urlName=view/download,urlMsg=下载文档,articleId="+articleId+",userId="+userId);
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			if (article != null) {
				try {
					String agent = request.getHeader("User-Agent").toUpperCase();
				System.out.println("======"+agent);
					response.setCharacterEncoding("utf-8");
					String name = article.getArticleName().replaceAll(" ", "")+"."+
							article.getArticleFormat();
					if (request.getHeader("User-Agent").toUpperCase()
							.indexOf("MSIE") > 0||agent.indexOf("RV:11.0")>0||agent.indexOf("EDGE")>0) {
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
		
		
		
		
	
		return null;
	}
}
