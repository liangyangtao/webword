package com.web.bean;

import java.util.Date;


public class Document {
	
	/**
	 * 文档id
	 */
	private int articleId;
	
	/**
	 * 文档所属项目（文档来源的平台）
	 */
	private String articleProject;
	
	/**
	 * 文档操作类型（upload、write）
	 */
	private String articleSave;
	
	/**
	 * 文档名称
	 */
	private String articleName;
	
	/**
	 * 文档类型（模板和文档）
	 */
	private String articleType;
	
	/**
	 * 编写文档的用户名称
	 */
	private String articleUser;
	
	/**
	 * 编写文档的用户ID
	 */
	private int userId;
	
	/**
	 * 文档简介
	 */
	private String articleBrief;
	
	/**
	 * 文档内容
	 */
	private String articleContent;
	
	/**
	 * 文档封面
	 */
	private String articleCover;
	
	/**
	 * 文档分类
	 */
	private String articleLabel;
	
	/**
	 * 文档关键字
	 */
	private String articleKeyWord;
	
	/**
	 * 审核状态
	 */
	private String passType;
	
	/**
	 * 审核通过时间
	 */
	private Long passTime;
	
	/**
	 * 审核人
	 */
	private String passUserName;
	
	/**
	 * 审核人的用户ID
	 */
	private int   passUser;
	
	/**
	 * 建立文档的时间
	 */
	private Long insertTime;
	
	/**
	 * 更新时间
	 */
	private Long updateTime;
	
	/**
	 * 生成word的时间
	 */
	private Long downTime;
	
	/**
	 * 生成的文档id
	 */
	private int docId;
	
	/**
	 * 文档保存路径
	 */
	private String docPath;
	
	/**
	 * 文档生成的HTML保存路径
	 */
	private String htmlPath;
	
	/**
	 * 文档价格
	 */
	private float articlePrice;
	
	/**
	 * 扩展字段
	 */
	private String articleInfo;
	
	/**
	 * 文档简介
	 */
	private String articleSkip;
	
	/**
	 * 文档生成的HTML文件名称
	 */
	private String htmlName;
	
	/**
	 * 文档生成的HTML页数
	 */
	private String htmlPage;
	
	/**
	 * 更新时间
	 */
	private Date updateDate;
	
	/**
	 * 下载次数 2015-10-30
	 */
	private Integer downCount;

	/**
	 * 阅读次数 2015-10-30
	 */
	private Integer viewCount;
	
	//2015-11-5新增以下字段
	/**
	 * 文档格式
	 */
	private String articleFormat;
	
	/**
	 * 文档标题
	 */
	private String articleTitle;

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getArticleFormat() {
		return articleFormat;
	}

	public void setArticleFormat(String articleFormat) {
		this.articleFormat = articleFormat;
	}

	public String getArticleJournalId() {
		return articleJournalId;
	}

	public void setArticleJournalId(String articleJournalId) {
		this.articleJournalId = articleJournalId;
	}

	public String getArticleJournalName() {
		return articleJournalName;
	}

	public void setArticleJournalName(String articleJournalName) {
		this.articleJournalName = articleJournalName;
	}

	public String getArticleJournalTime() {
		return articleJournalTime;
	}

	public void setArticleJournalTime(String articleJournalTime) {
		this.articleJournalTime = articleJournalTime;
	}

	/**
	 * 期刊ID
	 */
	private String articleJournalId;
	/**
	 * 具体期刊显示
	 */
	private String articleJournalName;
	/**
	 * 期刊期次
	 */
	private String articleJournalTime;
	
	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	

	public String getArticleSave() {
		return articleSave;
	}

	public void setArticleSave(String articleSave) {
		this.articleSave = articleSave;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getArticleType() {
		return articleType;
	}

	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}

	public String getArticleUser() {
		return articleUser;
	}

	public void setArticleUser(String articleUser) {
		this.articleUser = articleUser;
	}

	

	public String getArticleBrief() {
		return articleBrief;
	}

	public void setArticleBrief(String articleBrief) {
		this.articleBrief = articleBrief;
	}

	public String getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

	

	public String getArticleLabel() {
		return articleLabel;
	}

	public void setArticleLabel(String articleLabel) {
		this.articleLabel = articleLabel;
	}

	public String getArticleKeyWord() {
		return articleKeyWord;
	}

	public void setArticleKeyWord(String articleKeyWord) {
		this.articleKeyWord = articleKeyWord;
	}

	public String getPassType() {
		return passType;
	}

	public void setPassType(String passType) {
		this.passType = passType;
	}

	public Long getPassTime() {
		return passTime;
	}

	public void setPassTime(Long passTime) {
		this.passTime = passTime;
	}

	public Long getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Long insertTime) {
		this.insertTime = insertTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public Long getDownTime() {
		return downTime;
	}

	public void setDownTime(Long downTime) {
		this.downTime = downTime;
	}

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}

	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}

	public float getArticlePrice() {
		return articlePrice;
	}

	public void setArticlePrice(float articlePrice) {
		this.articlePrice = articlePrice;
	}

	public String getArticleInfo() {
		return articleInfo;
	}

	public void setArticleInfo(String articleInfo) {
		this.articleInfo = articleInfo;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPassUserName() {
		return passUserName;
	}

	public void setPassUserName(String passUserName) {
		this.passUserName = passUserName;
	}

	public int getPassUser() {
		return passUser;
	}

	public void setPassUser(int passUser) {
		this.passUser = passUser;
	}

	public String getArticleProject() {
		return articleProject;
	}

	public void setArticleProject(String articleProject) {
		this.articleProject = articleProject;
	}

	public String getArticleSkip() {
		return articleSkip;
	}

	public void setArticleSkip(String articleSkip) {
		this.articleSkip = articleSkip;
	}

	public String getHtmlName() {
		return htmlName;
	}

	public void setHtmlName(String htmlName) {
		this.htmlName = htmlName;
	}

	public String getHtmlPage() {
		return htmlPage;
	}

	public void setHtmlPage(String htmlPage) {
		this.htmlPage = htmlPage;
	}

	public String getArticleCover() {
		return articleCover;
	}

	public void setArticleCover(String articleCover) {
		this.articleCover = articleCover;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getDownCount() {
		return downCount;
	}

	public void setDownCount(Integer downCount) {
		this.downCount = downCount;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
}
