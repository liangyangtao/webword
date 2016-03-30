package com.lucene.entity;

public class UbkTable implements Cloneable {



	/**
	 * 用户ID
	 */
	private int cid = 1;

	/**
	 * 用户名称
	 */
	private String cname;

	/**
	 * 时间
	 */
	private String cdate;

	/**
	 * 用于索引
	 */
	private long longCdate;

	/**
	 * IP
	 */
	private String cip;

	public UbkTable() {
		// TODO Auto-generated constructor stub
	}

	public void copy(UbkTable table) {
		this.cid = table.cid;
		this.cip = table.cip;
		this.cname = table.cname;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

	public long getLongCdate() {
		return longCdate;
	}

	public void setLongCdate(long longCdate) {
		this.longCdate = longCdate;
	}

	public String getCip() {
		return cip;
	}

	public void setCip(String cip) {
		this.cip = cip;
	}


}
