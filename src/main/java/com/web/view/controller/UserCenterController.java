package com.web.view.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.common.service.CommonController;
import com.database.bean.Article;
import com.database.bean.ContentWithBLOBs;
import com.database.bean.WordJournal;
import com.database.bean.WordUsers;
import com.database.dao.WordJournalTypeMapper;
import com.export.Builder;
import com.google.gson.Gson;
import com.util.Md5Util;
import com.web.homePage.util.HomePageUtil;
import com.web.view.service.HomeService;
import com.web.view.service.NewsUserFavoriteService;
import com.web.view.service.UserCenterService;

@Controller
@RequestMapping(value = "/user/")
public class UserCenterController extends CommonController {
	@Autowired
	HomeService homeService;
	@Autowired
	UserCenterService userCenterService;
	@Autowired
	WordJournalTypeMapper wordJournalTypeMapper;

	@Autowired
	NewsUserFavoriteService newsUserFavoriteService;
	private int movepage = 5; // 默认分页个数
	@Value("${nginxPic}")
	private String nginxPic;
	private static final Logger LOGGER = Logger
			.getLogger(UserCenterController.class);

	/**
	 * 我的期刊
	 */
	@RequestMapping(value = "/journal/index")
	public String journal(HttpServletResponse response, HttpSession session,
			Integer pageSize, Integer pageNo, Integer startPage, String status,
			Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String, Object> map = userCenterService.getMyJournal(pageSize,
				pageNo, status, user.getUserId());
		map.put("startPage", (startPage == null ? 1 : startPage));
		map.put("endPage", Math.min((startPage == null ? 1 : startPage)
				+ movepage, (Integer) map.get("pageCount")));
		map.put("movepage", movepage);
		model.addAttribute("page", map);
		model.addAttribute("map",
				userCenterService.getAllCounts(user.getUserId()));
		model.addAttribute("nav", "journal");
		model.addAttribute("typeList",
				wordJournalTypeMapper.selectByExample(null));
		try {
			LOGGER.info("urlName=user/journal/index,urlMsg=我的期刊,userId="
					+ user.getUserId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return "user/user-journal";
	}

	/**
	 * 新建期刊
	 */
	@RequestMapping(value = "/journal/add")
	public String journalAdd(HttpServletResponse response, HttpSession session,
			WordJournal wordJournal, Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<WordJournal> list = userCenterService
					.validJournalName(wordJournal.getName());
			if (list != null && !list.isEmpty()) {
				map.put("result", "0");
				map.put("msg", "期刊名称已存在");
			} else {
				wordJournal.setUserId(user.getUserId());
				userCenterService.insertOrUpdateJournal(wordJournal);
				map.put("result", "1");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			map.put("result", "0");
			map.put("msg", "提交失败");
		}
		try {
			LOGGER.info("urlName=user/journal/add,urlMsg=提交审核期刊,userId="
					+ user.getUserId() + ",result=" + map.get("result"));
			responseJson(response, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 上传图片
	 * 
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/journal/uploadCover")
	@ResponseBody
	public void uploadCover(HttpServletResponse response,
			HttpServletRequest request, MultipartFile upimg) throws Exception {
		Map<String, Object> outmap = new HashMap<String, Object>();
		File dirFile = new File(nginxPic + "upload");
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		ServletContext servletContext = request.getSession()
				.getServletContext();
		// spring的文件处理解析类，包装servlet的上下文。
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				servletContext);
		if (multipartResolver.isMultipart(request)) {
			// 把request请求进行升级，request有的，它都有，
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获得上传文件的名称
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 包装过的文件流
				MultipartFile file = multiRequest.getFile((String) iter.next());
				// 这里要进行判断，即使是空值，没有上传内容，file都是有值（空值）的，而文件流大小要大于0才是有上传的东西
				if (file.getSize() > 0) {
					// 判断文件大小
					if (file.getSize() > 1048576) {
						outmap.put("success", false);
						outmap.put("msg", "上传文件不能大于1MB！");
					} else {
						// 获得上传文件原始名
						String imagename = file.getOriginalFilename();
						// 算出后缀名
						String ext = imagename.substring(imagename
								.lastIndexOf("."));
						// 对文件类型进行判断，这个操作也可以在前台进行处理，在前台进行处理比较好，前后台都进行处理最稳妥
						List<String> fileTypes = new ArrayList<String>();
						fileTypes.add(".jpg");
						fileTypes.add(".jpeg");
						fileTypes.add(".bmp");
						fileTypes.add(".gif");
						fileTypes.add(".png");
						// 是图片再进行处理
						if (fileTypes.contains(ext.toLowerCase())) {
							String fileName = System.currentTimeMillis() + ext;
							File localFile = new File(nginxPic + "upload",
									fileName);
							try {
								// 直接写入到后台服务器，简单且快
								file.transferTo(localFile);
								// 修改图片文件尺寸
								BufferedImage image = ImageIO.read(localFile);
								BufferedImage newimg = HomePageUtil.resize(
										image, 230, 320, true);
								ImageIO.write(newimg, "png", localFile);
								outmap.put("success", true);
								outmap.put("path", "upload/" + fileName);
							} catch (Exception e) {
								outmap.put("success", false);
								outmap.put("msg", "系统出错");
							}
						} else {
							outmap.put("success", false);
							outmap.put("msg", "图片格式出错！");
						}
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
	 * 删除期刊
	 * 
	 * @param articleIds
	 */
	@RequestMapping(value = "/delJournal")
	public void delJournal(HttpServletResponse response, HttpSession session,
			String journalIds) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			userCenterService.delJournal(journalIds, user.getUserId());
			map.put("result", 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
			map.put("result", 0);
			map.put("msg", "删除失败");
		}
		try {
			LOGGER.info("urlName=user/delJournal,urlMsg=删除我的期刊,userId="
					+ user.getUserId() + ",journalIds=" + journalIds
					+ ",result=" + map.get("result"));
			responseJson(response, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * 期刊详情
	 */
	@RequestMapping(value = "/journal/detail")
	public String journalDetail(HttpServletResponse response,
			HttpSession session, String journalId, Integer startPage,
			Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		// 访问不是自己的期刊return
		WordJournal journal = userCenterService.getJournalDetail(journalId);
		if (journal.getUserId().intValue() != user.getUserId().intValue()) {
			return null;
		}
		model.addAttribute("map",
				userCenterService.getAllCounts(user.getUserId()));
		Map<String, Object> map = userCenterService.searchByJournal("",
				journalId, null, null);
		model.addAttribute("page", map);
		map.put("startPage", (startPage == null ? 1 : startPage));
		map.put("endPage", Math.min((startPage == null ? 1 : startPage)
				+ movepage, (Long) map.get("pageCount")));
		map.put("movepage", movepage);
		model.addAttribute("journal", journal);
		model.addAttribute("nav", "journal");
		model.addAttribute("journalId", journalId);
		try {
			LOGGER.info("urlName=user/journal/detail,urlMsg=期刊详情,userId="
					+ user.getUserId() + ",journalId=" + journalId);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return "user/journal-detail";
	}

	/**
	 * 搜索期刊下文档
	 */
	@RequestMapping(value = "/journal/searchByJournal")
	public String searchByJournal(HttpServletResponse response,
			HttpSession session, String journalId, Integer pageSize,
			Integer pageNo, String keyword, Integer startPage, Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String, Object> map = new HashMap<String, Object>();
		// 验证是否是自己的期刊
		map = userCenterService.searchByJournal(keyword, journalId, pageNo,
				pageSize);
		map.put("startPage", (startPage == null ? 1 : startPage));
		map.put("endPage", Math.min((startPage == null ? 1 : startPage)
				+ movepage, (Long) map.get("pageCount")));
		map.put("movepage", movepage);
		try {
			LOGGER.info("urlName=user/journal/searchByJournal,urlMsg=搜索期刊下文档,userId="
					+ user.getUserId());
			responseJson(response, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 修改期刊
	 */
	@RequestMapping(value = "/journal/edit")
	public String journalEdit(HttpServletResponse response,
			HttpSession session, WordJournal wordJournal, Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String, Object> map = new HashMap<String, Object>();
		WordJournal journal = userCenterService.getJournalDetail(wordJournal
				.getId() + "");
		// 验证是否是自己的期刊
		if (user.getUserId().intValue() == journal.getUserId().intValue()) {
			try {
				Date now = new Date();
				wordJournal.setPassType("SUBMITTED");
				wordJournal.setSubmitTime(now);
				wordJournal.setUserId(user.getUserId());
				userCenterService.editJournal(wordJournal);
				map.put("result", "1");
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				map.put("result", "0");
				map.put("msg", "提交失败");
			}
		}
		try {
			LOGGER.info("urlName=user/journal/edit,urlMsg=提交审核期刊,userId="
					+ user.getUserId() + ",result=" + map.get("result"));
			responseJson(response, map);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 我的文档
	 */
	@RequestMapping(value = "/article")
	public String article(HttpServletResponse response, HttpSession session,
			Integer pageSize, Integer pageNo, Integer startPage, String status,
			Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String, Object> map = userCenterService.getMyArticle(pageSize,
				pageNo, status, user.getUserId(), "document");
		map.put("startPage", (startPage == null ? 1 : startPage));
		map.put("endPage", Math.min((startPage == null ? 1 : startPage)
				+ movepage, (Integer) map.get("pageCount")));
		map.put("movepage", movepage);
		model.addAttribute("page", map);
		model.addAttribute("map",
				userCenterService.getAllCounts(user.getUserId()));
		model.addAttribute("nav", "article");
		try {
			LOGGER.info("urlName=user/article,urlMsg=我的文档,userId="
					+ user.getUserId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return "user/user-word";
	}

	/**
	 * 删除文档
	 * 
	 * @param articleIds
	 */
	@RequestMapping(value = "/delArticle")
	public void delArticle(HttpServletResponse response, HttpSession session,
			String articleIds) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			userCenterService.delArticle(articleIds, user.getUserId());
			map.put("result", 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
			map.put("result", 0);
			map.put("msg", "删除失败");
		}
		try {
			LOGGER.info("urlName=user/delArticle,urlMsg=删除我的文档,userId="
					+ user.getUserId() + ",articleIds=" + articleIds
					+ ",result=" + map.get("result"));
			responseJson(response, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * 我的模板
	 */
	@RequestMapping(value = "/template")
	public String template(HttpServletResponse response, HttpSession session,
			Integer pageSize, Integer pageNo, Integer startPage, String status,
			Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String, Object> map = userCenterService.getMyArticle(pageSize,
				pageNo, status, user.getUserId(), "template");
		map.put("startPage", (startPage == null ? 1 : startPage));
		map.put("endPage", Math.min((startPage == null ? 1 : startPage)
				+ movepage, (Integer) map.get("pageCount")));
		map.put("movepage", movepage);
		model.addAttribute("page", map);
		model.addAttribute("map",
				userCenterService.getAllCounts(user.getUserId()));
		model.addAttribute("nav", "template");
		try {
			LOGGER.info("urlName=user/template,urlMsg=我的模板,userId="
					+ user.getUserId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return "user/user-template";
	}

	/**
	 * 我的新闻
	 */
	@RequestMapping(value = "/content")
	public String content(HttpServletResponse response, HttpSession session,
			Integer pageSize, Integer pageNo, String status, Integer startPage,
			Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String, Object> map = userCenterService.getMyContent(pageSize,
				pageNo, status, user.getUserId());
		map.put("startPage", (startPage == null ? 1 : startPage));
		map.put("endPage", Math.min((startPage == null ? 1 : startPage)
				+ movepage, (Integer) map.get("pageCount")));
		map.put("movepage", movepage);
		model.addAttribute("page", map);
		model.addAttribute("map",
				userCenterService.getAllCounts(user.getUserId()));
		model.addAttribute("nav", "content");
		try {
			LOGGER.info("urlName=user/content,urlMsg=我的新闻,userId="
					+ user.getUserId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return "user/user-content";
	}

	/**
	 * 新闻详情
	 */
	@RequestMapping(value = "/news")
	public String news(HttpServletResponse response, HttpSession session,
			String newsId, Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		ContentWithBLOBs content = userCenterService.getContent(newsId);
		model.addAttribute("content", content);
		try {
			LOGGER.info("urlName=user/content,urlMsg=新闻详情,userId="
					+ user.getUserId() + ",contenId=" + newsId);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return "user/user-news";
	}

	/**
	 * 删除新闻
	 * 
	 * @param contentIds
	 */
	@RequestMapping(value = "/delContent")
	public void delContent(HttpServletResponse response, HttpSession session,
			String contentIds) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			userCenterService.delContent(contentIds, user.getUserId());
			map.put("result", 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
			map.put("result", 0);
			map.put("msg", "删除失败");
		}
		try {
			LOGGER.info("urlName=user/delContent,urlMsg=删除我的新闻,userId="
					+ user.getUserId() + ",contentIds=" + contentIds
					+ ",result=" + map.get("result"));
			responseJson(response, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * 我的插件
	 */
	@RequestMapping(value = "/plugin")
	public String plugin(HttpServletResponse response, HttpSession session,
			Integer pageSize, Integer pageNo, Integer startPage, String status,
			Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String, Object> map = userCenterService.getMyPlugin(pageSize,
				pageNo, user.getUserId());
		map.put("startPage", (startPage == null ? 1 : startPage));
		map.put("endPage", Math.min((startPage == null ? 1 : startPage)
				+ movepage, (Integer) map.get("pageCount")));
		map.put("movepage", movepage);
		model.addAttribute("page", map);
		model.addAttribute("map",
				userCenterService.getAllCounts(user.getUserId()));
		model.addAttribute("nav", "plugin");
		try {
			LOGGER.info("urlName=user/plugin,urlMsg=我的插件,userId="
					+ user.getUserId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return "user/user-plugin";
	}

	/**
	 * 删除插件
	 * 
	 * @param contentIds
	 */
	@RequestMapping(value = "/delPlugin")
	public void delPlugin(HttpServletResponse response, HttpSession session,
			String pluginUserIds) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			userCenterService.delPlugin(pluginUserIds, user.getUserId());
			map.put("result", 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
			map.put("result", 0);
			map.put("msg", "删除失败");
		}
		try {
			LOGGER.info("urlName=user/delPlugin,urlMsg=删除我的插件,userId="
					+ user.getUserId() + ",pluginUserIds=" + pluginUserIds
					+ ",result=" + map.get("result"));
			responseJson(response, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * 推荐模板
	 */
	@RequestMapping(value = "/recommend")
	public String recommend(HttpServletResponse response, HttpSession session,
			Integer pageSize, Integer pageNo, Integer startPage, Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String, Object> map = userCenterService.getRecommend(pageSize,
				pageNo);
		map.put("startPage", (startPage == null ? 1 : startPage));
		map.put("endPage", Math.min((startPage == null ? 1 : startPage)
				+ movepage, (Long) map.get("pageCount")));
		map.put("movepage", movepage);
		model.addAttribute("page", map);
		model.addAttribute("map",
				userCenterService.getAllCounts(user.getUserId()));
		model.addAttribute("nav", "recommend");
		try {
			LOGGER.info("urlName=user/recommend,urlMsg=推荐模板,userId="
					+ user.getUserId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return "user/user-recommend";
	}

	/**
	 * 我的上传
	 */
	@RequestMapping(value = "/upload")
	public String upload(HttpServletResponse response, HttpSession session,
			Integer pageSize, Integer pageNo, Integer startPage, String status,
			Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String, Object> map = userCenterService.getUpload(pageSize, pageNo,
				user.getUserId(), status);
		map.put("startPage", (startPage == null ? 1 : startPage));
		map.put("endPage", Math.min((startPage == null ? 1 : startPage)
				+ movepage, (Integer) map.get("pageCount")));
		map.put("movepage", movepage);
		model.addAttribute("page", map);
		model.addAttribute("map",
				userCenterService.getAllCounts(user.getUserId()));
		model.addAttribute("nav", "upload");
		try {
			LOGGER.info("urlName=user/upload,urlMsg=我的上传,userId="
					+ user.getUserId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return "user/user-upload";
	}

	/**
	 * 上传文档
	 * 
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/importFile")
	@ResponseBody
	public void importFile(HttpServletResponse response,
			HttpServletRequest request, HttpSession session,
			MultipartFile uploadfile) throws Exception {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String, Object> outmap = new HashMap<String, Object>();
		File dirFile = new File(nginxPic + "upload");
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		ServletContext servletContext = request.getSession()
				.getServletContext();
		// spring的文件处理解析类，包装servlet的上下文。
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				servletContext);
		if (multipartResolver.isMultipart(request)) {
			// 把request请求进行升级，request有的，它都有，
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获得上传文件的名称
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 包装过的文件流
				MultipartFile file = multiRequest.getFile((String) iter.next());
				// 这里要进行判断，即使是空值，没有上传内容，file都是有值（空值）的，而文件流大小要大于0才是有上传的东西
				if (file.getSize() > 0) {
					// 判断文件大小
					if (file.getSize() > (10485760 * 2)) {
						outmap.put("success", false);
						outmap.put("msg", "上传文件不能大于20MB！");
					} else {
						// 获得上传文件原始名
						String imagename = file.getOriginalFilename();
						// 算出后缀名
						String ext = imagename.substring(imagename
								.lastIndexOf("."));
						// 对文件类型进行判断，这个操作也可以在前台进行处理，在前台进行处理比较好，前后台都进行处理最稳妥
						List<String> fileTypes = new ArrayList<String>();
						fileTypes.add(".doc");
						fileTypes.add(".docx");
						fileTypes.add(".ppt");
						fileTypes.add(".pptx");
						fileTypes.add(".pdf");
						// 是图片再进行处理
						if (fileTypes.contains(ext.toLowerCase())) {
							try {
								// 直接写入到后台服务器，简单且快
								Date now = new Date();
								Article article = new Article();
								article.setArticleName(imagename.replace(ext,
										""));
								article.setSavestatus(0);
								article.setArticleType("document");
								article.setPassType("SAVED");
								article.setInsertTime(now);
								article.setUpdateTime(now);
								article.setUserId(user.getUserId());
								article.setArticleSave("upload");
								article.setArticleFormat(ext.replace(".", ""));
								long start = System.currentTimeMillis();
								File localFile = new File(nginxPic + "upload/",
										start + ext);
								file.transferTo(localFile);
								long end = System.currentTimeMillis();
								System.out.println("上传文档耗时" + (end - start)
										+ "ms");
								int articleId = userCenterService.importWord(
										article, localFile + "");
								Builder builder = Builder.getInstance();
								long s = System.currentTimeMillis();
								builder.copyFile(localFile + "", nginxPic
										+ "doc/article" + articleId + ext);
								long d = System.currentTimeMillis();
								System.out.println("复制文档耗时" + (d - s) + "ms");
								// file.transferTo( );
								outmap.put("success", true);
							} catch (Exception e) {
								outmap.put("success", false);
								outmap.put("msg", "系统出错");
								LOGGER.info("urlName=user/upload,urlMsg=我的上传,userId="
										+ user.getUserId()
										+ ",e="
										+ e.getMessage());
							}
						} else {
							outmap.put("success", false);
							outmap.put("msg", "文档格式出错！");
						}
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
	 * 批量上传文档
	 * 
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/importBatchFile")
	@ResponseBody
	public void importBatchFile(HttpServletResponse response,
			HttpServletRequest request, HttpSession session,
			MultipartFile batchuploadfile) throws Exception {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String, Object> outmap = new HashMap<String, Object>();
		File dirFile = new File(nginxPic + "upload");
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		ServletContext servletContext = request.getSession()
				.getServletContext();
		// spring的文件处理解析类，包装servlet的上下文。
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				servletContext);
		if (multipartResolver.isMultipart(request)) {
			// 把request请求进行升级，request有的，它都有，
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获得上传文件的名称
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 包装过的文件流
				MultipartFile file = multiRequest.getFile((String) iter.next());
				// 这里要进行判断，即使是空值，没有上传内容，file都是有值（空值）的，而文件流大小要大于0才是有上传的东西
				if (file.getSize() > 0) {
					// 判断文件大小
					if (file.getSize() > (10485760 * 5)) {
						outmap.put("success", false);
						outmap.put("msg", "上传文件不能大于50MB！");
					} else {
						// 获得上传文件原始名
						String imagename = file.getOriginalFilename();
						// 算出后缀名
						String ext = imagename.substring(imagename
								.lastIndexOf("."));
						if (ext.toLowerCase().equals(".zip")) {
							String zipName = UUID.randomUUID().toString()
									+ ".zip";// 构建文件名称
							String fileName = dirFile + File.separator
									+ zipName;
							File f = new File(fileName);
							batchuploadfile.transferTo(f);
							ZipFile zf = new ZipFile(f, "GBK");
							Enumeration enumeration = zf.getEntries();
							while (enumeration.hasMoreElements()) {
								ZipEntry ze = (ZipEntry) enumeration
										.nextElement();
								String name = ze.getName();
								String type = name.substring(name
										.lastIndexOf("."));
								// 对文件类型进行判断，这个操作也可以在前台进行处理，在前台进行处理比较好，前后台都进行处理最稳妥
								List<String> fileTypes = new ArrayList<String>();
								fileTypes.add(".doc");
								fileTypes.add(".docx");
								fileTypes.add(".ppt");
								fileTypes.add(".pptx");
								fileTypes.add(".pdf");
								// 是文档再进行处理
								if (fileTypes.contains(type.toLowerCase())) {
									try {
										InputStream is = zf.getInputStream(ze);
										// 直接写入到后台服务器，简单且快
										Date now = new Date();
										Article article = new Article();
										article.setArticleName(name.replace(
												type, ""));
										article.setSavestatus(0);
										article.setArticleType("document");
										article.setPassType("SAVED");
										article.setInsertTime(now);
										article.setUpdateTime(now);
										article.setUserId(user.getUserId());
										article.setArticleSave("upload");
										article.setArticleFormat(type.replace(
												".", ""));
										long start = System.currentTimeMillis();
										File localFile = new File(nginxPic
												+ "upload/", start + type);
										FileOutputStream fos = new FileOutputStream(
												localFile);
										int ch = 0;
										while ((ch = is.read()) != -1) {
											fos.write(ch);
										}
										is.close();
										fos.flush();
										fos.close();
										int articleId = userCenterService
												.importWord(article, localFile
														+ "");
										Builder builder = Builder.getInstance();
										builder.copyFile(localFile + "",
												nginxPic + "doc/article"
														+ articleId + type);
									} catch (Exception e) {
									}
								}
							}
							zf.close();
							f.delete();
							outmap.put("success", true);
						} else {
							outmap.put("success", false);
							outmap.put("msg", "文件格式出错！");
						}
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
	 * 修改资料
	 */
	@RequestMapping(value = "/edit")
	public String edit(HttpServletResponse response, HttpSession session,
			Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		model.addAttribute("nav", "edit");
		model.addAttribute("map",
				userCenterService.getAllCounts(user.getUserId()));
		try {
			LOGGER.info("urlName=user/edit,urlMsg=修改资料,userId="
					+ user.getUserId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return "user/user-edit";
	}

	/**
	 * 修改资料
	 */
	@RequestMapping(value = "/editSubmit")
	public void editSubmit(HttpServletResponse response, HttpSession session,
			WordUsers user, String checkMsg, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		WordUsers u = (WordUsers) session.getAttribute("user");
		if (u.getUserId().intValue() == user.getUserId().intValue()) {
			String str = "";
			try {
				// 昵称
				if (user.getUserName() != null
						&& !user.getUserName().equals("")) {
					u.setUserName(user.getUserName());
				}
				// 手机号
				if (user.getUserPhone() != null
						&& !user.getUserPhone().equals("")) {
					if (session.getAttribute("checkMsg") != null
							&& session.getAttribute("checkMsg")
									.equals(checkMsg)) {
						u.setUserPhone(user.getUserPhone());
						str = userCenterService.updateWordUsersInfo(u);
					} else {
						map.put("result", 0);
						str = "手机验证码错误";
					}
				} else {
					str = userCenterService.updateWordUsersInfo(u);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LOGGER.error(e.getMessage());
				map.put("result", 0);
				map.put("msg", "修改失败");
			}
			if (str.equals("1")) {
				session.setAttribute("user", u);
				map.put("result", 1);
			} else {
				map.put("result", 0);
				map.put("msg", str);
			}
			try {
				LOGGER.info("urlName=user/editSubmit,urlMsg=修改资料,userId="
						+ user.getUserId() + ",result=" + map.get("result"));
				responseJson(response, map);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LOGGER.error(e.getMessage());
			}
		}
	}

	/**
	 * 修改密码
	 */
	@RequestMapping(value = "/pass")
	public String pass(HttpServletResponse response, HttpSession session,
			Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		model.addAttribute("nav", "pass");
		model.addAttribute("map",
				userCenterService.getAllCounts(user.getUserId()));
		try {
			LOGGER.info("urlName=user/pass,urlMsg=修改密码,userId="
					+ user.getUserId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return "user/user-pass";
	}

	/**
	 * 修改密码提交
	 */
	@RequestMapping(value = "/passSubmit")
	public void pass(HttpServletResponse response, HttpSession session,
			String oldPass, String pass, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		WordUsers user = (WordUsers) session.getAttribute("user");
		String pwMd5 = Md5Util.getMD5Str(oldPass);// 密码加密
		if (!pwMd5.equals(user.getUserPassword())) {
			map.put("result", 0);
			map.put("msg", "密码输入错误!");
		} else {
			pwMd5 = Md5Util.getMD5Str(pass);// 密码加密
			user.setUserPassword(pwMd5);
			try {
				userCenterService.updateWordUsersPass(user);
				session.setAttribute("user", user);
				map.put("result", 1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LOGGER.error(e.getMessage());
				map.put("result", 0);
				map.put("msg", "修改密码失败!");
			}
		}
		try {
			LOGGER.info("urlName=user/passSubmit,urlMsg=修改密码,userId="
					+ user.getUserId() + ",result=" + map.get("result"));
			responseJson(response, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * 获取文档详情
	 */
	@RequestMapping(value = "/getArticle")
	public void getArticle(HttpServletResponse response, HttpSession session,
			Integer articleId) {
		try {
			responseJson(response, homeService.getArticle(articleId));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * 获取期刊详情
	 */
	@RequestMapping(value = "/getJournal")
	public void getJournal(HttpServletResponse response, HttpSession session,
			Integer journalId) {
		try {
			responseJson(response, homeService.getWordJournal(journalId));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * 我的资源库
	 * 
	 * @param response
	 * @param session
	 * @param pageSize
	 * @param pageNo
	 * @param startPage
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/resource")
	public String resource(HttpServletResponse response, HttpSession session,
			Integer pageSize, Integer pageNo, Integer startPage,
			String keyword, Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String, Object> map = userCenterService.getMyResource(pageSize,
				pageNo, keyword, user.getUserId());
		map.put("startPage", (startPage == null ? 1 : startPage));
		map.put("endPage", Math.min((startPage == null ? 1 : startPage)
				+ movepage, (Integer) map.get("pageCount")));
		map.put("movepage", movepage);
		model.addAttribute("page", map);
		model.addAttribute("map",
				userCenterService.getAllCounts(user.getUserId()));
		model.addAttribute("nav", "resource");
		model.addAttribute("keyword", keyword);
		/*
		 * model.addAttribute("typeList",
		 * wordJournalTypeMapper.selectByExample(null));
		 */
		try {
			LOGGER.info("urlName=user/resource,urlMsg=我的资源库,userId="
					+ user.getUserId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return "user/user-resource";
	}

	/**
	 * 购买记录
	 * 
	 * @param response
	 * @param session
	 * @param pageSize
	 * @param pageNo
	 * @param startPage
	 * @param startdate
	 * @param enddate
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/purchase")
	public String purchase(HttpServletResponse response, HttpSession session,
			Integer pageSize, Integer pageNo, Integer startPage,
			String startdate, String enddate, Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String, Object> map = userCenterService.getPurchase(startdate,
				enddate, pageSize, pageNo, user.getUserId());
		map.put("startPage", (startPage == null ? 1 : startPage));
		map.put("endPage", Math.min((startPage == null ? 1 : startPage)
				+ movepage, (Integer) map.get("pageCount")));
		map.put("movepage", movepage);
		model.addAttribute("startdate", startdate);
		model.addAttribute("enddate", enddate);
		model.addAttribute("page", map);
		model.addAttribute("map",
				userCenterService.getAllCounts(user.getUserId()));
		model.addAttribute("nav", "purchase");
		/*
		 * model.addAttribute("typeList",
		 * wordJournalTypeMapper.selectByExample(null));
		 */
		try {
			LOGGER.info("urlName=user/purchase,urlMsg=我的资源库,userId="
					+ user.getUserId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return "user/user-purchase";
	}

	/**
	 * 资金明细
	 * 
	 * @param response
	 * @param session
	 * @param pageSize
	 * @param pageNo
	 * @param startPage
	 * @param startdate
	 * @param enddate
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/capital")
	public String capital(HttpServletResponse response, HttpSession session,
			Integer pageSize, Integer pageNo, Integer startPage,
			String startdate, String enddate, Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String, Object> map = userCenterService.getCapital(startdate,
				enddate, pageSize, pageNo, user.getUserId());
		map.put("startPage", (startPage == null ? 1 : startPage));
		map.put("endPage", Math.min((startPage == null ? 1 : startPage)
				+ movepage, (Integer) map.get("pageCount")));
		map.put("movepage", movepage);
		model.addAttribute("startdate", startdate);
		model.addAttribute("enddate", enddate);
		model.addAttribute("page", map);
		model.addAttribute("map",
				userCenterService.getAllCounts(user.getUserId()));
		model.addAttribute("nav", "capital");
		/*
		 * model.addAttribute("typeList",
		 * wordJournalTypeMapper.selectByExample(null));
		 */
		try {
			LOGGER.info("urlName=user/capital,urlMsg=我的资源库,userId="
					+ user.getUserId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return "user/user-capital";
	}

	/**
	 * 充值
	 * 
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/recharge")
	public String recharge(HttpServletResponse response, HttpSession session,
			Model model) {

		WordUsers user = (WordUsers) session.getAttribute("user");
		if (user != null) {
			String userMoney = userCenterService.getUserMoneyByUserId(user
					.getUserId());
			model.addAttribute("userMoney", userMoney);
		}
		model.addAttribute("nav", "recharge");
		model.addAttribute("map",
				userCenterService.getAllCounts(user.getUserId()));
		try {
			LOGGER.info("urlName=user/recharge,urlMsg=充值,userId="
					+ user.getUserId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return "user/user-recharge";
	}

	/**
	 * 分页查询我的收藏新闻
	 * 
	 * @param pageSize
	 *            每页显示条数
	 * @param pageNo
	 *            开始页码
	 * @param userId
	 *            用户ID
	 * @return
	 */
	@RequestMapping(value = "/myfavorite")
	public String myFavorite(HttpServletResponse response, HttpSession session,
			Integer pageSize, Integer pageNo, Integer startPage, Model model) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String, Object> map = newsUserFavoriteService.getAllNews(pageSize,
				pageNo, user.getUserId());
		map.put("startPage", (startPage == null ? 1 : startPage));
		map.put("endPage", Math.min((startPage == null ? 1 : startPage)
				+ movepage, (Integer) map.get("pageCount")));
		map.put("movepage", movepage);
		model.addAttribute("page", map);
		model.addAttribute("map",
				userCenterService.getAllCounts(user.getUserId()));
		model.addAttribute("nav", "myfavorite");
		try {
			LOGGER.info("urlName=user/myfavorite,urlMsg=我的收藏,userId="
					+ user.getUserId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return "user/user-favorite";

	}

	/***
	 * 删除我的收藏
	 * 
	 * @param response
	 * @param session
	 * @param contentIds
	 *            页面传过来的id[docId]
	 */
	@RequestMapping(value = "/delAllContent")
	public void delAllContent(HttpServletResponse response,
			HttpSession session, String contentIds) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			newsUserFavoriteService.delAllContent(contentIds, user.getUserId());
			map.put("result", 1);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			map.put("result", 0);
			map.put("msg", "删除失败");
		}
		try {
			LOGGER.info("urlName=user/delmyFavorite,urlMsg=删除我的收藏,userId="
					+ user.getUserId() + ",contentIds=" + contentIds
					+ ",result=" + map.get("result"));
			responseJson(response, map);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * 添加到我的收藏
	 * 
	 * @param response
	 * @param session
	 * @param title
	 * @param docId
	 */
	@RequestMapping(value = "/addMyFavorite")
	public void addMyFavorite(HttpServletResponse response,
			HttpSession session, String title, Integer docId) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Integer userId = 0;
		if (user != null) {
			userId = user.getUserId();
		}
		try {
			newsUserFavoriteService
					.insertNewsToMyFavorite(title, docId, userId);

			LOGGER.info("urlName=user/addMyFavorite,urlMsg=添加我的收藏,userId="
					+ user.getUserId() + ",crawl_id=" + docId);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	@RequestMapping(value = "/delMyFavorite")
	public void delMyFavorite(HttpServletResponse response,
			HttpSession session, Integer docId) {
		WordUsers user = (WordUsers) session.getAttribute("user");
		Integer userId = 0;
		if (user != null) {
			userId = user.getUserId();
		}
		try {
			newsUserFavoriteService.deleteNewsToMyFavorite(docId, userId);
			LOGGER.info("urlName=user/addMyFavorite,urlMsg=添加我的收藏,userId="
					+ user.getUserId() + ",crawl_id=" + docId);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
}
