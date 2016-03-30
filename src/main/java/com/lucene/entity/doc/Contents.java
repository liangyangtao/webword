package com.lucene.entity.doc;


import java.util.ArrayList;
import java.util.List;

import com.lucene.UnbankList;

public class Contents implements UnbankList<Content> {

	private int totalCount;

	private List<Content> list = new ArrayList<Content>();

	public Contents(int totalCount) {
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
	public List<Content> getList() {
		return list;
	}

	@Override
	public void add(Content t) {
		list.add(t);
	}

	@Override
	public void setTotalCount(int total) {
		totalCount = total;
	}

	@Override
	public void setList(List<Content> l) {
		list=l;
		
	}

}
