package com.lucene.service.impl;

import org.apache.log4j.Logger;

import com.lucene.CrawlWriter;
import com.lucene.DocWriter;
import com.lucene.PluginWriter;
import com.lucene.service.LuceneService;

public class LuceneServiceImpl implements LuceneService {

	protected static Logger logger = Logger.getLogger(LuceneServiceImpl.class);

	private CrawlWriter crawlWriter;

//	private DocWriter docWriter;
	private PluginWriter pluginWriter;
	
	public void setCrawlWriter(CrawlWriter crawlWriter) {
		this.crawlWriter = crawlWriter;
	}

	public void setPluginWriter(PluginWriter pluginWriter) {
		this.pluginWriter = pluginWriter;
	}

	@Override
	public void doIndexes() {
//		try {
//			docWriter.indexes();
//		} catch (Exception e) {
//			logger.info("自主文章建立索引出现异常!", e);
//		}

		try {
			crawlWriter.indexes();
			//pluginWriter.indexes();
		} catch (Exception e) {
			logger.info("抓取文章建立索引出现异常!", e);
		}
	}

}
