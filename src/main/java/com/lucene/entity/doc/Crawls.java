package com.lucene.entity.doc;


import java.util.ArrayList;
import java.util.List;

import com.lucene.UnbankList;

public class Crawls implements UnbankList<Crawl> {

	private int totalCount;

	private List<Crawl> list = new ArrayList<Crawl>();

	public Crawls(int totalCount) {
		if (totalCount > MAX_RECORD_SIZE)
			this.totalCount = MAX_RECORD_SIZE;
		else
			this.totalCount = totalCount;
	}

	@Override
	public int getTotalCount() {
		return totalCount;
	}

	@Override
	public List<Crawl> getList() {
		return list;
	}

	@Override
	public void add(Crawl t) {
		list.add(t);
	}

	@Override
	public void setTotalCount(int total) {
		totalCount = total;
	}

	@Override
	public void setList(List<Crawl> l) {
		list=l;
		
	}

}
