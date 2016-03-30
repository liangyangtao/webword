package com.lucene;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;

public class QueryWrapper {

	private Query query;

	private Sort sort;

	public QueryWrapper() {
		// TODO Auto-generated constructor stub
	}

	public QueryWrapper(Query query, Sort sort) {
		this.query = query;
		this.sort = sort;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

}
