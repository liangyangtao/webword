package com.lucene.bankcount;

import java.util.Map;

import com.lucene.UnbankList;
import com.lucene.entity.doc.Crawl;

public interface IBankDataAccess {
	public Map<String,Object> getbankcountdata(int[] bankids);
	public UnbankList<Crawl> gethongguan(int type,String record,int pagesize,int currypage);
}
