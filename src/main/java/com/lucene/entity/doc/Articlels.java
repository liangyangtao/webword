package com.lucene.entity.doc;


import java.util.ArrayList;
import java.util.List;

import com.lucene.UnbankList;

public class Articlels implements UnbankList<Articlel> {

	private int totalCount;

	private List<Articlel> list = new ArrayList<Articlel>();

	public Articlels(int totalCount) {
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
	public List<Articlel> getList() {
		return list;
	}

	@Override
	public void add(Articlel t) {
		list.add(t);
	}

	@Override
	public void setTotalCount(int total) {
		totalCount = total;
	}

	@Override
	public void setList(List<Articlel> t) {
		list=t;
	}

}
