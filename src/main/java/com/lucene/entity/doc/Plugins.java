package com.lucene.entity.doc;


import java.util.ArrayList;
import java.util.List;

import com.lucene.UnbankList;

public class Plugins implements UnbankList<Plugin> {

	private int totalCount;

	private List<Plugin> list = new ArrayList<Plugin>();

	public Plugins(int totalCount) {
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
	public List<Plugin> getList() {
		return list;
	}

	@Override
	public void add(Plugin t) {
		list.add(t);
	}

	@Override
	public void setTotalCount(int total) {
		totalCount = total;
	}

	@Override
	public void setList(List<Plugin> l) {
		list=l;
		
	}

}
