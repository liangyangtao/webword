package com.lucene.entity.doc;

public class Plugin {
	/**
	 * pluginid
	 */
	private int pluginid;
	/**
	 * pluginname
	 */
	private String pluginname;
	/**
	 * pluginintro
	 */
	private String pluginintro;
	/**
	 * userid
	 */
	private int userid;
	/**
	 * pluginindex
	 */
	private String pluginindex;
	/**
	 * pluginparams
	 */
	private String pluginparams;
	/**
	 * pluginnote
	 */
	private String pluginnote;
	/**
	 * inserttime
	 */
	private long inserttime;
	/**
	 * 数据分类  
	 */
	private int dataclass;
	
	public int getDataclass() {
		return dataclass;
	}
	public void setDataclass(int dataclass) {
		this.dataclass = dataclass;
	}
	public int getPluginid() {
		return pluginid;
	}
	public void setPluginid(int pluginid) {
		this.pluginid = pluginid;
	}
	public String getPluginname() {
		return pluginname;
	}
	public void setPluginname(String pluginname) {
		this.pluginname = pluginname;
	}
	public String getPluginintro() {
		return pluginintro;
	}
	public void setPluginintro(String pluginintro) {
		this.pluginintro = pluginintro;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getPluginindex() {
		return pluginindex;
	}
	public void setPluginindex(String pluginindex) {
		this.pluginindex = pluginindex;
	}
	public String getPluginparams() {
		return pluginparams;
	}
	public void setPluginparams(String pluginparams) {
		this.pluginparams = pluginparams;
	}
	public String getPluginnote() {
		return pluginnote;
	}
	public void setPluginnote(String pluginnote) {
		this.pluginnote = pluginnote;
	}
	public long getInserttime() {
		return inserttime;
	}
	public void setInserttime(long inserttime) {
		this.inserttime = inserttime;
	}
	
}
