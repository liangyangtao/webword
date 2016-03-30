package com.lucene.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucene.DocWriter;
import com.lucene.service.LuceneServiceContent;

/**
 * 
 * Title   : LuceneServiceContentImpl.java
 * 类描述      ：内容建立索引
 * 作者           : 杨佳贵  
 * 创建时间 : 2014年9月2日 上午11:34:23
 * 版本           : 1.00
 * 
 * 修改记录: 
 * 版本            修改人          修改时间                  修改内容描述
 * ----------------------------------------
 * 1.00     杨佳贵          2014年9月2日 上午11:34:23
 * ----------------------------------------
 */
@Service
public class LuceneServiceContentImpl implements LuceneServiceContent {
	@Autowired
	private DocWriter docWriter;
	
	public void setDocWriter(DocWriter docWriter) {
		this.docWriter = docWriter;
	}
	
	@Override
	public void contentDoIndexes() {
		try {
			docWriter.indexes();
		} catch (Exception e) {
			LuceneServiceImpl.logger.info("自主文章建立索引出现异常!", e);
		}
	}

}
