package com.lucene.util;

public class Page {

	/**
	 * 总记录
	 */
	private int total;

	/**
	 * 页数
	 */
	private int pageIndex = 1;

	/**
	 * 总页数
	 */
	private int pageCount;

	/**
	 * 每页记录
	 */
	private int pageRow = 15;

	public Page(int total) {
		this.pageCount = (total % pageRow) == 0 ? (total / pageRow)
				: ((total / pageRow) + 1);
		this.total = total;
	}

	public Page(int total, int pageRow) {
		this.pageRow = pageRow;
		this.pageCount = (total % pageRow) == 0 ? (total / pageRow)
				: ((total / pageRow) + 1);
		this.total = total;
	}

	public int getTotal() {
		return total;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = (pageIndex < 1 ? 1 : ((pageIndex > pageCount)
				&& (pageCount != 0) ? pageCount : pageIndex));
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getPageRow() {
		return pageRow;
	}

	public int getStartIndex() {
		return (pageIndex - 1) * pageRow;
	}

	/**
	 * 获取分页
	 * 
	 * @return
	 */
	public String getLimitStr() {
		StringBuilder sb = new StringBuilder();
		sb.append(" LIMIT ");
		sb.append(getStartIndex());
		sb.append(", ");
		sb.append(getPageRow());
		return sb.toString();
	}

}
