package com.lucene;

import java.util.List;

import com.lucene.entity.doc.Articlel;

public interface UnbankList<T> {

	/**
	 * 搜索前x条记录, 提高性能, 禁止查询很大的分页
	 */
	final int MAX_RECORD_SIZE = 300000;

	/**
	 * 得到总数
	 * 
	 * @return
	 */
	int getTotalCount();

	/**
	 * 
	 * @param total
	 */
	void setTotalCount(int total);

	/**
	 * 得到搜素列表
	 * 
	 * @return
	 */
	List<T> getList();
	/**
	 * 得到搜素列表
	 * 
	 * @return
	 */
	void setList(List<T> l);
	/**
	 * 添加元素
	 * 
	 * @param t
	 */
	void add(T t);

}
