package com.web.view.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.service.CommonController;
import com.database.bean.Article;
import com.database.bean.ContentWhole;
import com.database.bean.WordJournal;
import com.export.Builder;
import com.google.gson.Gson;
import com.util.DateTool;
import com.web.homePage.service.HomePageService;
import com.web.homePage.util.HomePageUtil;
import com.web.view.service.UserCenterService;
import com.web.word.service.WordService;

@Controller
@RequestMapping(value = "/view/")
public class ImportCilentArticle extends CommonController{
	@Autowired
	UserCenterService userCenterService;
	@Autowired
	WordService wordService;
	@Autowired
	HomePageService	homePageService;
	@Value("${nginxPic}")
	private String nginxPic;
	private static final Logger LOGGER = Logger.getLogger(CommonController.class);
	/**
	 * 同步文档
	 * 
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/importClientArticle")
	@ResponseBody
	public void importClientArticle(HttpServletResponse response,
			HttpServletRequest request, String name, String downPath,
			String userId) throws Exception {
		Map<String, Object> outmap = new HashMap<String, Object>();
		File dirFile = new File(nginxPic + "upload");
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		response.setCharacterEncoding("utf-8");
		try {
			// 下载网络文件
			downPath = downPath.replace("\\", "/");
			URL url = new URL(downPath);
			URLConnection conn = url.openConnection();
			InputStream inStream = conn.getInputStream();
			String ext = downPath.substring(downPath.lastIndexOf("."));
			Date now = new Date();
			Article article = new Article();
			article.setArticleName(name);
			article.setSavestatus(0);
			article.setArticleType("document");
			article.setPassType("SAVED");
			article.setInsertTime(now);
			article.setUpdateTime(now);
			article.setUserId(Integer.valueOf(userId));
			article.setArticleSave("upload");
			article.setArticleFormat(ext.replace(".", ""));
			long start = System.currentTimeMillis();
			File localFile = new File(nginxPic + "upload/", start + ext);
			// file.transferTo(localFile);
			FileOutputStream fos = new FileOutputStream(localFile);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = inStream.read(buff, 0, buff.length))) {
				fos.write(buff, 0, bytesRead);
			}
			fos.flush();
			inStream.close();
			fos.close();
			long end = System.currentTimeMillis();
			System.out.println("同步文档文档耗时" + (end - start) + "ms");
			int articleId = userCenterService.importWord(article, localFile
					+ "");
			Builder builder = Builder.getInstance();
			long s = System.currentTimeMillis();
			builder.copyFile(localFile + "", nginxPic + "doc/article"
					+ articleId + ext);
			long d = System.currentTimeMillis();
			System.out.println("复制文档耗时" + (d - s) + "ms");
			// file.transferTo( );
			outmap.put("success", true);
		} catch (Exception e) {
			outmap.put("success", false);
			outmap.put("msg", "系统出错");
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
	 * 提交审核-在用
	 * <p>
	 * Title: saveClientArticle
	 * </p>
	 * 
	 * @param articleName
	 *            :文档名字
	 * @param articleId
	 *            :文档Id
	 * @param radioValue
	 *            :保存类型 1模板 2文档
	 * @param keyword
	 *            :关键词
	 * @param brief
	 *            :简介
	 * @param articleLabel
	 *            :标签
	 * @param articleJournalId
	 *            :期刊Id
	 * @param articleJournalName
	 *            :期刊刊次
	 * @param articleJournalTime
	 *            :期刊刊次时间
	 * @throws IOException
	 */
	@RequestMapping(value = "saveClientArticle")
	@ResponseBody
	public void saveClientArticle(HttpSession session,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Map<String, Object> maps = new HashMap<String, Object>();
		Map<String, String> logMap = new HashMap<String, String>();
		logMap.put("keyword", "");
		logMap.put("brief", "");
		logMap.put("articleLabel", "");

		// 重名验证
		String vidioValue = request.getParameter("radioValue");// 保存类型 1模板 2文档
		String articleName = request.getParameter("articleName");
		String article_id = request.getParameter("articleId");
		Integer userId = Integer.valueOf(request.getParameter("userId"));
		logMap.put("articleName", articleName);
		Integer articleId = Integer.parseInt(request.getParameter("articleId"));// 文章id
		Article article = wordService.selectById(articleId);
		if (article == null) {
			maps.put("state", "error");
			maps.put("info", "操作无效,该篇文档不存在");
		} else {
			// Article tempbean=wordService.submitCheckName(userId,vidioValue,
			// articleName,article_id);
			boolean ArticleFlag = wordService.checkWordName(articleId, userId,
					articleName, article.getArticleType());
			if (ArticleFlag) {
				maps.put("state", "error");
				maps.put("info", "文档名称已有重复");
			} else {// 没有重名
				String articleJournalId = request
						.getParameter("articleJournalId");// 期刊Id
				if ("".equals(articleJournalId)) {
					articleJournalId = "0";
				}
				String keyword = request.getParameter("keyword");// 关键字
				String brief = request.getParameter("brief");// 简介
				String articleLabel = request.getParameter("articleLabel");// 标签
				String articlePriceStr = request.getParameter("articlePrice");
				if (articlePriceStr == null || "".equals(articlePriceStr)) {
					articlePriceStr = "0";
				}
				Double articlePrice = (double) Integer
						.parseInt(articlePriceStr);

				keyword = HomePageUtil.transfer(keyword);
				brief = HomePageUtil.transfer(brief);
				articleName = HomePageUtil.transfer(articleName);
				articleLabel = HomePageUtil.transfer(articleLabel);

				Article newArticle = new Article();
				newArticle.setArticleId(articleId);// 文档Id
				newArticle.setArticleName(articleName);
				newArticle.setArticleLabel(articleLabel);// 文档标签
				newArticle.setArticleKeyWord(keyword);// 文档的关键词
				newArticle.setArticleSkip(brief);
				newArticle.setSavestatus(1);
				newArticle.setPassTime(new Date());
				newArticle.setSubmitTime(new Date());
				newArticle.setPassType("SUBMITTED");
				newArticle.setArticlePrice(articlePrice);

				newArticle.setArticleJournalId(Integer
						.parseInt(articleJournalId));
				Double journalPrice = (double) 0;
				if (!("0".equals(articleJournalId))) {// 期刊文档
					String articleJournalName = request
							.getParameter("articleJournalName");// 关键字
					String articleJournalTime = request
							.getParameter("articleJournalTime");// 简介
					newArticle.setArticleJournalName(articleJournalName);
					newArticle.setArticleJournalTime(DateTool
							.strToDate(articleJournalTime + " 00:00:10"));
					WordJournal journal = wordService
							.selectByPrimaryKey(Integer
									.parseInt(articleJournalId));
					if (journal != null) {
						journalPrice = journal.getPrice();
					}
					if (journalPrice == (double) 0) {
						newArticle.setArticlePrice((double) 0);
					}
				}
				logMap.put("keyword", keyword);
				logMap.put("brief", brief);
				logMap.put("articleLabel", articleLabel);
				if (articlePrice < 0 || articlePrice > 9999999) {
					maps.put("state", "error");
					maps.put("info", "文档价格:请输入1-7位的整数");
				} else if (!("0".equals(articleJournalId))
						&& articlePrice > journalPrice) {
					maps.put("state", "error");
					maps.put("info", "文档价格不能大于期刊价格");
				} else if (!("0".equals(articleJournalId))
						&& wordService.checkArticleJournalName(newArticle)) {// 有重复的
					maps.put("state", "error");
					maps.put("info", "期刊刊次重复");
				} else {
					if ("write".equals(article.getArticleSave())) {// 自己编写的
						ContentWhole contentWhole = wordService
								.getArticleContentById(Integer
										.parseInt(article_id));
						if ((contentWhole != null
								&& contentWhole.getWholeId() != null
								&& contentWhole.getNodeContent() != null && contentWhole
								.getNodeContent().length() > 0)) {

							newArticle
									.setArticleType("1".equals(vidioValue) ? "template"
											: "document");// 文档的类型
							String path = request.getSession()
									.getServletContext().getRealPath("");
							boolean flag = homePageService.articleToWord(
									article, path, articleId, "article");
							if (flag) {
								wordService.updateByPrimaryKeySelectiveArticle(newArticle);
								maps.put("newArticle", newArticle);
								maps.put("state", "success");
								maps.put("info", "审核成功");
							} else {
								maps.put("state", "error");
								maps.put("info", "提示失败,生成word失败");
							}
						} else {
							maps.put("state", "error");
							maps.put("info", "文件没有内容");
						}
						// 自己编写完
					} else {// 上传的
						wordService.updateByPrimaryKeySelectiveArticle(newArticle);
						maps.put("newArticle", newArticle);
						maps.put("state", "success");
						maps.put("info", "审核成功");
					}
				}
			}
		}
		try{
			LOGGER.info("urlName=view/saveClientArticle,urlMsg=提交审核,articleId="+articleId+",userId="+userId+",state="+maps.get("state")+",info="+maps.get("info")+",articleName="+logMap.get("articleName")+",keyword="+logMap.get("keyword")+",brief="+logMap.get("brief")+",articleLabel="+logMap.get("articleLabel"));
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		responseJson(response, maps);
	}
}
