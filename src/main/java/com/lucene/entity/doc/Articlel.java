package com.lucene.entity.doc;

public class Articlel {
	/**
	 * articleid
	 */
	private int articleid;
	/**
	 * articlename
	 */
	private String articlename;
	/**
	 * nodecontents
	 */
	private String nodecontents;
	/**
	 * articleskip
	 */
	private String articleskip;
	/**
	 * keyword
	 */
	private String keyword;
	/**
	 * platename
	 */
	private String platename;
	/**
	 * plateid
	 */
	private int plateid;
	/**
	 * articletype
	 */
	private String articletype;
	
	private long passtime;
	
	
	public long getPasstime() {
		return passtime;
	}
	public void setPasstime(long passtime) {
		this.passtime = passtime;
	}
	public String getArticletype() {
		return articletype;
	}
	public void setArticletype(String articletype) {
		this.articletype = articletype;
	}
	public int getPlateid() {
		return plateid;
	}
	public void setPlateid(int plateid) {
		this.plateid = plateid;
	}
	public int getArticleid() {
		return articleid;
	}
	public void setArticleid(int articleid) {
		this.articleid = articleid;
	}
	public String getArticlename() {
		return articlename;
	}
	public void setArticlename(String articlename) {
		this.articlename = articlename;
	}
	public String getNodecontents() {
		return nodecontents;
	}
	public void setNodecontents(String nodecontents) {
		this.nodecontents = nodecontents;
	}
	public String getArticleskip() {
		return articleskip;
	}
	public void setArticleskip(String articleskip) {
		this.articleskip = articleskip;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getPlatename() {
		return platename;
	}
	public void setPlatename(String platename) {
		this.platename = platename;
	}
	
}
