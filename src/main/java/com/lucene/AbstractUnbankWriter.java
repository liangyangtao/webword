package com.lucene;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.queries.mlt.MoreLikeThis;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeFilter;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.RowMapper;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.lucene.bankcount.BankDataAccess;
import com.lucene.bankcount.BankDataBean;
import com.lucene.bankcount.IBankDataAccess;
import com.lucene.dao.UnbankDAO;
import com.lucene.entity.Searchparam;
import com.lucene.entity.doc.Crawl;
import com.lucene.entity.doc.Keyword;
import com.lucene.job.IndexesJob;


/**
 * 
 * @author quzile
 * 
 * @param <T>
 */
public abstract class AbstractUnbankWriter<T> implements DisposableBean,
		UnbankWriter<T> {

	private static final int INIT = 0;
	private static final int SUCCESS = 1;
	private static final int PROCESSING = 2;

	protected static Logger logger = Logger.getLogger(UnbankWriter.class);
	protected UnbankDAO unbankDAO;
	protected ComboPooledDataSource dataSource;
	protected Analyzer analyzer;
	//关联词容器
		public static Map<Integer,String> idwordsrelationmap=new HashMap<Integer,String>();
		public static Map<String,Integer> wordwordsrelationmap=new HashMap<String,Integer>();
	/**
	 * 长期打开, 对于Unbank应用来说每种文档是一个单例, 来完成持续的变更
	 */
	private IndexWriter writer;

	/**
	 * 小搜
	 */
	private IndexSearcher searcher;

	/**
	 * 创建新索引
	 */
	boolean create = false;

	/**
	 * 索引文件目录
	 */
	private File file;
	private Directory directory;
	private IndexWriterConfig conf;

	/**
	 * 创建模式
	 */
	private OpenMode mode;

	/**
	 * DEFAULT HTML Formatter
	 */
	private Formatter formatter = new SimpleHTMLFormatter(
			"<span style='color:red;'>", "</span>");

	/**
	 * 是否允许搜索结果对索引变更可见, 为ture时如果频繁更新会消耗系统性能
	 */
	private boolean createNewReader = false;

	/**
	 * true每次更新所有未索引数据, false默认只更新1000条
	 */
	private boolean all = false;

	/**
	 * 条件日志
	 */
	private boolean queryLog = false;

	/**
	 * 摘要长度
	 */
	private int lengthOfText = 140;

	/**
	 * 每次处理多少条记录, 默认2000
	 */
	private int pageSize = 2000;

	/**
	 * 时间格式化
	 */
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 推荐文章的数量
	 */
	private int recommendSize = 50;

	public AbstractUnbankWriter(File file, Analyzer analyzer) {
		try {
			this.analyzer = analyzer;
			this.file = file;
			this.directory = FSDirectory.open(file);
			this.conf = new IndexWriterConfig(UnbankWriter.version, analyzer);
			//
			// conf.setRAMBufferSizeMB(ramBufferSizeMB);
			// conf.setUseCompoundFile(useCompoundFile);

			if (create) {
				// Create a new index in the directory, removing any
				// previously indexed documents:
				conf.setOpenMode(OpenMode.CREATE);
			} else {
				// Add new documents to an existing index:
				conf.setOpenMode(OpenMode.CREATE_OR_APPEND);
			}

			writer = new IndexWriter(directory, conf);
			mode = writer.getConfig().getOpenMode();
			// IndexSearcher searcher = new IndexSearcher(
			// DirectoryReader.open(directory));
			searcher = new IndexSearcher(DirectoryReader.open(writer, false));
		} catch (IOException e) {
			logger.error("path not exist! file: " + file, e);
			throw new RuntimeException();
		}
	}

	@Override
	public void addDocument(T t) throws Exception{
		try {
			Document doc = newDocument(t);
			if (doc == null) {
				return;
			} else if (mode == OpenMode.CREATE) {
				// New index, so we just add the document (no old document can
				// be there):
				// logger.info("adding");
				writer.addDocument(doc);
			} else {
				// Existing index (an old copy of this document may have been
				// indexed) so
				// we use updateDocument instead to replace the old one matching
				// the exact
				// path, if present:
				// logger.info("updating");
				writer.updateDocument(findUnique(doc), doc);
			}
		} catch (IOException e) {
			logger.error("add document with a low-level IO error!", e);
			throw new RuntimeException();
		} catch (IllegalArgumentException e) {
			logger.error("value cannot be null, this doc is ignore!", e);
		}
	};
	@Override
	public synchronized void addDocument1(T t) throws Exception{
		try {
			Document doc = newDocument(t);
			if (doc == null) {
				return;
			} else if (mode == OpenMode.CREATE) {
				// New index, so we just add the document (no old document can
				// be there):
				// logger.info("adding");
				writer.addDocument(doc);
			} else {
				// Existing index (an old copy of this document may have been
				// indexed) so
				// we use updateDocument instead to replace the old one matching
				// the exact
				// path, if present:
				// logger.info("updating");
				writer.updateDocument(findUnique(doc), doc);
			}
			writer.commit();// 刷新buffer提交至文件
			
			// 让索引更新的记录可以被搜索到，更新reader
			if (createNewReader) {
				searcher = new IndexSearcher(DirectoryReader.open(writer, false));
			}

		} catch (Exception e) {
			logger.error("add document error!", e);
		} 
	};
	@Override
	public void deleteDocument(T t) throws Exception{
		try {
			Document doc = newDocument(t);
			if (doc == null) {
				return;
			}
			writer.deleteDocuments(findUnique(doc));
		} catch (Exception e) {
			logger.error("delete document error!", e);
			throw new Exception();
		} 
	};
	@Override
	public synchronized void deleteDocument1(T t) throws Exception{
		try {
			Document doc = newDocument(t);
			if (doc == null) {
				return;
			}
			writer.deleteDocuments(findUnique(doc));
			writer.commit();// 刷新buffer提交至文件
			// 让索引更新的记录可以被搜索到，更新reader
			if (createNewReader) {
				searcher = new IndexSearcher(DirectoryReader.open(writer, false));
			}
		} catch (Exception e) {
			logger.error("delete document error!", e);
			throw new Exception();
		} 
	};
	@Override
	public void addDocument(List<T> list) throws Exception {
		long start = System.currentTimeMillis();
		for (T t : list)
			addDocument(t);
		long end = System.currentTimeMillis();
		logger.info(getClassname()+"---batch index [" + list.size() + "] recoreds in "
				+ (end - start) + " total milliseconds");
	}

	@Override
	public void destroy() throws Exception {
		if (writer != null) {
			try {
				writer.close();
				logger.info(getClassname()+"---close UnbankWriter! filename:"
						+ file.getAbsolutePath());
			} finally {
				if (IndexWriter.isLocked(directory)) {
					IndexWriter.unlock(directory);
					logger.info(getClassname()+"---unlock directory! filename:"
							+ file.getAbsolutePath());
				}
			}
		}
	}
	@Override
	public UnbankList<T> search(Query query, int offset, int rowCount, Sort sort)
			throws Exception {
		return search(query, null, offset, rowCount, sort);
	}

	@Override
	public UnbankList<T> search(Query query, Filter filter, int offset,
			int rowCount, Sort sort) throws Exception {
		int total = offset + rowCount;

		long start = System.currentTimeMillis();
		TopDocs hits;
		if (sort == null) {
			hits = searcher.search(query, filter, total);
		} else {
			hits = searcher.search(query, filter, total, sort);
		}
		UnbankList<T> result = toList(query, searcher, hits, offset, total);
		long end = System.currentTimeMillis();

		if (queryLog) {
			logger.info(getClassname()+"---"+(end - start) + "ms, " + query.toString());
		}
		return result;
	}
	/**
	 * @param record           搜索关键词
	 * @param pageSize       每页个数
	 * @param currentPage  当期页
	 * @return
	 * @throws Exception
	 */
	public UnbankList<T> search(Searchparam searchparam) throws Exception  {
			String record=handlespecialcharact(searchparam.getRecord()).trim();
			
			IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(record), true);
	        Lexeme lexeme;
	        List<String> l=new ArrayList<String>();
	        while ((lexeme = ikSegmenter.next()) != null) {
	        	l.add(lexeme.getLexemeText());
	        }
	        
	        String[] words=null;
	        if(l.size()==1){
	        	Integer wordsid=wordwordsrelationmap.get(record);
	        	if(wordsid!=null) words=idwordsrelationmap.get(wordsid).split("/");
	        }
					
	        QueryWrapper qw=getCondition(searchparam,record, l,words);
	        
	        //查询条件
			BooleanQuery boolQuery= (BooleanQuery) qw.getQuery() ;
			
			Sort sort =qw.getSort();
			ScoreDoc[] docs = null;
			
			if(sort==null){
				if(getClassname().equals("crawl"))
					// 此处修改过jar lucene core 的
					docs=searcher.search1(boolQuery,10000).scoreDocs;
				else
					docs=searcher.search(boolQuery,10000).scoreDocs;
			}else
				docs=searcher.search(boolQuery,10000,sort).scoreDocs;
			
			int totalCount = docs.length; // 搜索结果总数量 
	          
	        //查询起始记录位置 
	        int begin = searchparam.getPageSize() * (searchparam.getCurrentPage() - 1) ; 
	        //查询终止记录位置 
	        int end = Math.min(begin + searchparam.getPageSize(), totalCount); 
	        
	        UnbankList<T> list=initList(totalCount) ;
	        //进行分页查询 
	        if(docs!=null&&docs.length>0){
        	  for(int i=begin;i<end;i++) { 
  				Document doc = searcher.doc(docs[i].doc);
  		    	list.add(conv( record, l, words, doc));
  				}
	        }
	      
	        
	        return list;

	}
	/**
	 * @param record           搜索关键词
	 * @param pageSize       每页个数
	 * @param currentPage  当期页
	 * @return
	 * @throws Exception
	 */
	public UnbankList<T> searchbankdata(Searchparam searchparam) throws Exception  {

			String record=handlespecialcharact(searchparam.getRecord()).trim();
			searchparam.setBankdatarecord(handlespecialcharact(searchparam.getBankdatarecord()==null?"":searchparam.getBankdatarecord()).trim());
			
			IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(record), true);
	        Lexeme lexeme;
	        List<String> l=new ArrayList<String>();
	        while ((lexeme = ikSegmenter.next()) != null) {
	        	l.add(lexeme.getLexemeText());
	        }
	        
	        String[] words=null;
	        if(l.size()==1){
	        	Integer wordsid=wordwordsrelationmap.get(record);
	        	if(wordsid!=null) words=idwordsrelationmap.get(wordsid).split("/");
	        }
					
	        QueryWrapper qw=getConditionbankdata(searchparam,record, l,words);
	        
	        //查询条件
			BooleanQuery boolQuery= (BooleanQuery) qw.getQuery() ;
			
			Sort sort =qw.getSort();
			ScoreDoc[] docs = null;
			
			if(sort==null){
					docs=searcher.search(boolQuery,10000).scoreDocs;
			}else
				docs=searcher.search(boolQuery,10000,sort).scoreDocs;
			
			int totalCount = docs.length; // 搜索结果总数量 
	          
	        //查询起始记录位置 
	        int begin = searchparam.getPageSize() * (searchparam.getCurrentPage() - 1) ; 
	        //查询终止记录位置 
	        int end = Math.min(begin + searchparam.getPageSize(), totalCount); 
	        
	        UnbankList<T> list=initList(totalCount) ;
	        //进行分页查询 
	        if(docs!=null&&docs.length>0){
        	  for(int i=begin;i<end;i++) { 
  				Document doc = searcher.doc(docs[i].doc);
//  				Explanation ex=searcher.explain(boolQuery, docs[i].doc);
//  				System.out.println("----------"+ex.getValue());
  		    	list.add(convbankdata( record, l, words, doc));
  				}
	        }
	      
	        
	        return list;

	}
	/**
	 * @param type             1搜索 2插件
	 * @param userId           用户id
	 * @param must             必须包含
	 * @param arbitrarily      必须包含任意一个
	 * @param not               必须不包含
	 * @param startdate       开始日期
	 * @param enddate        结束日期
	 * @param pageSize       每页个数
	 * @param currentPage  当期页
	 * @param searchsource  搜索源 1全文 2标题
	 * @param sort          排序1时间 2权重
	 * @param fromsource    新闻来源
	 * @return
	 * @throws Exception
	 */
	@Override
	public  UnbankList<T> search(int type,int userId,String must,String arbitrarily,String not,String startdate,String enddate,int pageSize,int currentPage,int searchsource,int sorttype,String fromsource)  throws Exception {
		must=must.toLowerCase();
		arbitrarily=arbitrarily.toLowerCase();
		not=not.toLowerCase();
		String[] musts=new String[0];
		  if(!must.trim().equals(""))musts=must.trim().split(" ");
		  String[] arbitrarilys=new String[0];
		  if(!arbitrarily.trim().equals(""))arbitrarilys=arbitrarily.trim().split(" ");
		  String[] nots=new String[0];
		  if(!not.trim().equals(""))nots=not.trim().split(" ");
		  
		    QueryWrapper qw=getCondition(userId,musts, arbitrarilys, nots, startdate, enddate,searchsource,sorttype,fromsource);
			//查询条件
			BooleanQuery boolQuery = (BooleanQuery) qw.getQuery();
			
			Sort sort =qw.getSort();
			ScoreDoc[] docs = null;
			
			if(sort!=null)
				docs=searcher.search(boolQuery,100000,sort).scoreDocs;
			else
				docs=searcher.search(boolQuery,100000).scoreDocs;
			
			int totalCount = docs.length; // 搜索结果总数量 
	          
	        //查询起始记录位置 
	        int begin = pageSize * (currentPage - 1) ; 
	        //查询终止记录位置 
	        int end = Math.min(begin + pageSize, totalCount); 
	        
	        UnbankList<T> list=initList(totalCount) ;
	        //进行分页查询 
	        for(int i=begin;i<end;i++) { 
				Document doc = searcher.doc(docs[i].doc);
				
				list.add(conv( type,musts, arbitrarilys, nots, doc));
			}
	        return list;
	}
	@Override
	public UnbankList<T> recommend(MoreLikeThisConfig mltc, Set<Term> termSet,
			String text, Query... queries) throws Exception {
		MoreLikeThis mlt = new MoreLikeThis(searcher.getIndexReader());
		mlt.setAnalyzer(analyzer);
		//
		mlt.setFieldNames(new String[] { "text" });
		mlt.setMinTermFreq(mltc.getMinTermFreq()); // 词频至少出现3次的
		mlt.setMinDocFreq(mltc.getMinDocFreq()); // 文档中至少出现2次的
		mlt.setMinWordLen(mltc.getMinWordLen()); // 最小长度2
		// Set<String> stopWords = new HashSet<String>();
		// mlt.setStopWords(stopWords);

		Query query = mlt.like(new StringReader(text), "text");
		query.extractTerms(termSet);

		//
		BooleanQuery onebq = new BooleanQuery();
		onebq.add(query, BooleanClause.Occur.MUST);
		for (Query q : queries) {
			onebq.add(q, BooleanClause.Occur.MUST);
		}

		//
		UnbankList<T> list = search(onebq, null, 0, recommendSize, null);
		list.setTotalCount(list.getTotalCount() < recommendSize ? list
				.getTotalCount() : recommendSize);// 推荐至多recommendSize条记录
		return list;
	}
	
	
	public UnbankList<T> recommend(Query query, MoreLikeThisConfig mltc,
			Set<Term> termSet, String text, int offset, int rowCount, Sort sort)
			throws Exception{
		
		MoreLikeThis mlt = new MoreLikeThis(searcher.getIndexReader());
		mlt.setAnalyzer(analyzer);
		//
		mlt.setFieldNames(new String[] { "text" });
		mlt.setMinTermFreq(1); // 词频至少出现3次的
		mlt.setMinDocFreq(1); // 文档中至少出现2次的
		mlt.setMinWordLen(1); // 最小长度2
		// Set<String> stopWords = new HashSet<String>();
		// mlt.setStopWords(stopWords);

		Query mquery = mlt.like(new StringReader(text), "text");
		mquery.extractTerms(termSet);

		if (query != null) {
			BooleanQuery bq = new BooleanQuery();
			bq.add(mquery, BooleanClause.Occur.MUST);
			bq.add(query, BooleanClause.Occur.MUST);

			mquery = bq;
		}

		//
		UnbankList<T> list = search(mquery, null, offset, rowCount, sort);
		
				return list;
		
	}

	@Override
	public void terms(MoreLikeThisConfig mltc, Set<Term> termSet, String text)
			throws Exception {
		
		MoreLikeThis mlt = new MoreLikeThis(searcher.getIndexReader());
		mlt.setAnalyzer(analyzer);
		//
		mlt.setFieldNames(new String[] { "text" });
		mlt.setMinTermFreq(1); // 词频至少出现3次的
		mlt.setMinDocFreq(1); // 文档中至少出现2次的
		mlt.setMinWordLen(2); // 最小长度2

		Query query = mlt.like(new StringReader(text), "text");
		query.extractTerms(termSet);
	}
	/**
	 * 
	 * @param mltc, 配置参数
	 * @param text  推荐样本内容
	 * @param s  排序方式 1权重2时间
	 * @param pageSize     每页个数
	 * @param currentPage  当期页
	 * @return
	 * @throws Exception
	 */
	public UnbankList<T> recommend(MoreLikeThisConfig mltc, String text,int s,int pageSize,int currentPage) throws Exception{
		QueryWrapper qw= getCondition(mltc,searcher.getIndexReader(), text,s);
		

	    Sort sort =qw.getSort();
		ScoreDoc[] docs = null;
		
		if(sort==null){
			docs=searcher.search(qw.getQuery(),10000).scoreDocs;
		}else
			docs=searcher.search(qw.getQuery(),10000,sort).scoreDocs;
		
		int totalCount = docs.length; // 搜索结果总数量 
          
        //查询起始记录位置 
        int begin = pageSize * (currentPage - 1) ; 
        //查询终止记录位置 
        int end = Math.min(begin + pageSize, totalCount); 
        
        UnbankList<T> list=initList(totalCount) ;
        //进行分页查询 
        if(docs!=null&&docs.length>0){
        	for(int i=begin;i<end;i++) { 
				Document doc = searcher.doc(docs[i].doc);
		    	list.add(convbankdata( null, null, null, doc));
			}
        }
        
        return list;
	}
	/**
	 * 加载词典
	 * 
	 * @throws Exception
	 */
	public synchronized void loadkeyword()throws Exception{
		if(IndexesJob.loadkeywordflag){
			
			try{
				//加载扩展词库
				Configuration cfg = DefaultConfig.getInstance();
				cfg.setUseSmart(true); 
				Dictionary.initial(cfg);
				
				Dictionary dictionary = Dictionary.getSingleton();
				
				List<String> keywordlist=findciku();
				
				dictionary.addWords(keywordlist);
				
				List<String> stopword=new ArrayList<String>();
				stopword.add("也");
				stopword.add("了");
				stopword.add("仍");
				stopword.add("从");
				stopword.add("以");
				stopword.add("使");
				stopword.add("则");
				stopword.add("却");
				stopword.add("又");
				stopword.add("及");
				stopword.add("对");
				stopword.add("就");
				stopword.add("并");
				stopword.add("很");
				stopword.add("或");
				stopword.add("把");
				stopword.add("是");
				stopword.add("的");
				stopword.add("着");
				stopword.add("给");
				stopword.add("着");
				stopword.add("而");
				stopword.add("被");
				stopword.add("让");
				stopword.add("在");
				stopword.add("让");
				stopword.add("还");
				stopword.add("比");
				stopword.add("等");
				stopword.add("当");
				stopword.add("与");
				stopword.add("但");
				stopword.add("于");
				stopword.add("阿");
				stopword.add("啊");
				stopword.add("哎");
				stopword.add("唉");
				stopword.add("多");
				stopword.add("哗");
				stopword.add("乎");
				stopword.add("嘿");
				stopword.add("多");
				stopword.add("哼");
				stopword.add("个");
				stopword.add("呵");
				dictionary.disableWords(stopword);
				
				
				List<Map<String,Object>> list=findwordsrelation();
				Map<Integer,String> idwordsrelationmap1=new HashMap<Integer,String>();
				Map<String,Integer> wordwordsrelationmap1=new HashMap<String,Integer>();
				for(int i=0;i<list.size();i++){
					Map<String,Object> map=list.get(i);
					String words=(String)map.get("words");
					int id=(Integer)map.get("id");
					idwordsrelationmap1.put(id,words);
					String[] wordss=words.split("/");
					for(int j=0;j<wordss.length;j++)
						wordwordsrelationmap1.put(wordss[j], id);
				}
				idwordsrelationmap=idwordsrelationmap1;
				wordwordsrelationmap=wordwordsrelationmap1;
				IndexesJob.loadkeywordflag=false;
			}catch(Exception e){
				logger.error("加载词典出现异常!", e);
			}
			logger.info("加载词典成功======================");
		}
	}

	/**
	 * 添加索引
	 * 
	 * @throws Exception
	 */
	@Override
	public synchronized int indexes() throws Exception {
		loadkeyword();
		int page = 0;
		int offset = 0;
		int rowCount = pageSize + 1;

		int totalCount = 0;
		String datetime = sdf.format(new Date());

		// before, 更新标志位, 初始状态更新至处理中
		update(unbankDAO, INIT, PROCESSING, datetime);

		List<T> list = null;
		boolean hasNextPage = false;
		do {
			offset = page++ * pageSize;
			list = findIndexes(unbankDAO, datetime, offset, rowCount);

			if (hasNextPage = list.size() == rowCount) {
				list.remove(list.size() - 1);
			}
			
			totalCount += list.size();
			addDocument(list);// 添加索引时buffer
			writer.commit();// 刷新buffer提交至文件
		} while (all && hasNextPage);

		if (totalCount != 0) {
			// after, 更新标志位, 处理中状态更新至成功状态
			update(unbankDAO, PROCESSING, SUCCESS, datetime);

			// 让索引更新的记录可以被搜索到，更新reader
			if (createNewReader) {
				searcher = new IndexSearcher(
						DirectoryReader.open(writer, false));
			}
		}
		logger.info(getClassname()+"--task total create [" + totalCount + "] indexes!");
		return totalCount;
	}

	/**
	 * term query
	 * 
	 * @param field
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public T findByTerm(String field, String uid) throws Exception {
		TermQuery query = new TermQuery(new Term(field, uid));
		TopDocs td = searcher.search(query, 1);
		ScoreDoc[] hits = td.scoreDocs;
		T t = null;
		for (ScoreDoc hit : hits) {
			int docId = hit.doc;
			t = conv(query, searcher.doc(docId), docId);
		}
		return t;
	}

	/**
	 * 高亮显示
	 * 
	 * @param query
	 * @param docId
	 * @param field
	 * @param text
	 * @return
	 * @throws IOException
	 * @throws InvalidTokenOffsetsException
	 */
	protected String highlight(Query query, int docId, String field, String text)
			throws IOException, InvalidTokenOffsetsException {

		QueryScorer scorer = new QueryScorer(query);
		Highlighter highlighter = new Highlighter(formatter, scorer);
		highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer));
		// 修改容量
		// highlighter.setMaxDocCharsToAnalyze(40);

		// 从原始文档中生成TokenStream
		TokenStream tokenStream = TokenSources.getAnyTokenStream(
				searcher.getIndexReader(), docId, field, analyzer);

		// TokenStream tokenStream = analyzer.tokenStream(field, new
		// StringReader(text));
		String content = highlighter.getBestFragment(tokenStream, text);
		return content == null ? text : content;
	}

	/**
	 * term vector中排名前n的, 长度越大, 排名靠前的机会越大
	 * 
	 * @param docId
	 * @param field
	 * @param keywords
	 */
	protected void keywords(int docId, String field, List<Keyword> keywords) {
		try {
			Terms vector = searcher.getIndexReader()
					.getTermVector(docId, field);
			TermsEnum termsEnum = vector.iterator(null);

			List<Keyword> list = new ArrayList<Keyword>();
			//
			BytesRef text = null;
			while ((text = termsEnum.next()) != null) {
				String term = text.utf8ToString();
				int freq = (int) termsEnum.totalTermFreq();
				// 英文字母按单词计算长度, 中文按字计算长度
				int len = term.matches("^[a-z0-9]+.*") ? 0 : term.length();
				if (len > 1) {
					Keyword keyword = new Keyword(term, freq, len * freq);
					list.add(keyword);
				}
				// System.out.printf("%s, %d \n", term, freq);
			}

			// 按评分排序
			Collections.sort(list);

			int top = 5;
			for (Keyword key : list) {
				keywords.add(key);
				top--;
				if (top == 0) {
					break;
				}
			}

		} catch (IOException e) {
		}
	}

	/**
	 * 按Term查找, 必须是唯一的, 否则行为可能会不正常
	 * 
	 * @param doc
	 * @return
	 */
	protected abstract Term findUnique(Document doc);
	
	/**
	 * 
	 * 方法名称    :deleteDocument
	 * 功能描述    ：删除索引
	 * 逻辑描述    :
	 * @param   :无
	 * @return  :Term
	 * @throws  :无
	 * @since   :Ver 1.00
	 */
	protected abstract Term findContent(int contentId);

	/**
	 * 初始化结果列表对象
	 * 
	 * @return
	 */
	protected abstract UnbankList<T> initList(int totalHits);

	/**
	 * 查找等待索引的数据
	 * 
	 * @param unbankDAO
	 * @param args
	 * @return
	 */
	protected abstract List<T> findIndexes(UnbankDAO unbankDAO, Object... args);
	/**
	 * 查找词库
	 * 
	 * @param unbankDAO
	 * @param args
	 * @return
	 * @throws Exception 
	 */
	protected List<String> findciku() throws Exception {
		String sql = "select word from ciku where state=1 or (state=0 and sum>1)";
		
		Connection conn=dataSource.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> rtlist = new ArrayList<String>();
		try {
			conn.setAutoCommit(true);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				rtlist.add(rs.getString("word"));
			}
			ps.close();

		} catch (Exception e) {
			
		} finally {
			conn.close();
		}
		return rtlist;
		
	}
	/**
	 * 查找关系词词库
	 * 
	 * @param unbankDAO
	 * @param args
	 * @return
	 */
	protected List<Map<String,Object>> findwordsrelation() throws Exception{
		String sql = "select * from wordsrelation where state=1";
		
		Connection conn=dataSource.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String,Object>> rtlist = new ArrayList<Map<String,Object>>();
		try {
			conn.setAutoCommit(true);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("words", rs.getString("words"));
				map.put("id", rs.getInt("id"));
				rtlist.add(map);
			}
			ps.close();

		} catch (Exception e) {
			
		} finally {
			conn.close();
		}
		return rtlist;
	}
	/**
	 * 更新状态, 从oldValue状态更新到newValue状态
	 * 
	 * @param unbankDAO
	 * @param oldValue
	 * @param newValue
	 * @param datetime
	 * @return
	 */
	protected abstract int update(UnbankDAO unbankDAO, int oldValue,
			int newValue, String datetime);

	/**
	 * 转换成页面中显示的列表
	 * 
	 * @param query
	 * @param searcher
	 * @param hits
	 * @param offset
	 *            起始位置, 用于分页
	 * @param total
	 *            总数
	 * @return
	 * @throws IOException
	 */
	private UnbankList<T> toList(Query query, IndexSearcher searcher,
			TopDocs hits, int offset, int total) throws Exception {
		UnbankList<T> list = initList(hits.totalHits);
		ScoreDoc[] score = hits.scoreDocs;
		for (int i = offset; i < total && i < score.length; i++) {
			int docId = score[i].doc;
			list.add(conv(query, searcher.doc(docId), docId));
			// 根据 explain 的结果调整相关性, 该操作会消耗性能
			// Explanation exp = searcher.explain(query, score[i].doc);
			// logger.info(exp.toHtml());
		}
		return list;
	}
	
	/**
	 * 
	 * 方法名称    :deleteDocument
	 * 功能描述    ：删除索引
	 * 逻辑描述    :
	 * @param   :无
	 * @return  :void
	 * @throws  :无
	 * @since   :Ver 1.00
	 */
	public synchronized void deleteDocument(List<Integer> list) {
		
			try {
				for (Integer i : list) {System.out.println("=========="+i);
				writer.deleteDocuments(findContent(i));
				}
				writer.commit();
				
//				String datetime = sdf.format(new Date());
//				update(unbankDAO, PROCESSING, SUCCESS, datetime);

				// 让索引更新的记录可以被搜索到，更新reader
				if (createNewReader) {
					searcher = new IndexSearcher(
							DirectoryReader.open(writer, false));
				}
				
			} catch (Exception e) {
				logger.error("delete error!", e);
			}
		
	}

	@Override
	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public IndexWriter getWriter() {
		return writer;
	}

	public Directory getDirectory() {
		return directory;
	}

	public UnbankDAO getUnbankDAO() {
		return unbankDAO;
	}

	public int getLengthOfText() {
		return lengthOfText;
	}


	public void setDataSource(ComboPooledDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setUnbankDAO(UnbankDAO unbankDAO) {
		this.unbankDAO = unbankDAO;
	}

	public void setCreate(boolean create) {
		this.create = create;
	}

	public void setFormatter(Formatter formatter) {
		this.formatter = formatter;
	}

	public void setCreateNewReader(boolean createNewReader) {
		this.createNewReader = createNewReader;
	}

	public void setAll(boolean all) {
		this.all = all;
	}

	public void setQueryLog(boolean queryLog) {
		this.queryLog = queryLog;
	}

	public void setLengthOfText(int lengthOfText) {
		this.lengthOfText = lengthOfText;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setRecommendSize(int recommendSize) {
		this.recommendSize = recommendSize;
	}
	@Override
	public QueryWrapper getConditionbankdata(Searchparam searchparam,String record, List<String> l,String[] words) throws Exception {
		return null;
	}
	@Override
	public T convbankdata(String record,List<String> l,String[] words, Document doc) throws Exception {
		return null;
	}
	@Override
	public QueryWrapper getCondition(MoreLikeThisConfig mltc,IndexReader ir, String text,int sort) throws Exception {
		return null;
	}
	/**
	 * 
	 * 方法名称    ：给掉Lucene特殊符号
	 * 功能描述    ：
	 * 逻辑描述    :
	 * @param   :record 要去除特殊符号的内容
	 * @return  :String
	 * @throws  :无
	 */
	public static String handlespecialcharact(String record){
		String record1=record+"";
		record1=record1.replaceAll("[+]", " ");
		record1=record1.replaceAll("[-]", " ");
		record1=record1.replaceAll("&&", " ");
		record1=record1.replaceAll("\\|", " ");
		record1=record1.replaceAll("[!]", " ");
		record1=record1.replaceAll("[(]", " ");
		record1=record1.replaceAll("[)]", " ");
		record1=record1.replaceAll("[{]", " ");
		record1=record1.replaceAll("[}]", " ");
		record1=record1.replaceAll("\\[", " ");
		record1=record1.replaceAll("\\]", " ");
		record1=record1.replaceAll("\\^", " ");
		record1=record1.replaceAll("\"", " ");
		record1=record1.replaceAll("\\~", " ");
		record1=record1.replaceAll("\\*", " ");
		record1=record1.replaceAll("\\?", " ");
		record1=record1.replaceAll("\\:", " ");
		return record1;
	}
	/**
	 * 
	 * 方法名称    ：取得传入yyyy-mm-dd日期和前、后标志向后几天、向前几天，返回该天日期
	 * 功能描述    ：
	 * 逻辑描述    :
	 * @param   :sdate 时间
	 * @param   :dnum  向前向后天数
	 * @return  :String
	 * @throws  :无
	 */
	public static String getOneDay(String sdate, int dnum) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date newdate = df.parse(sdate);
			java.util.Calendar cal = new java.util.GregorianCalendar();
			cal.setTime(newdate);
			if (dnum > 0)
				cal.add(Calendar.DATE, dnum);
			else
				cal.add(Calendar.DATE, dnum);
			Date dt = cal.getTime();
			return df.format(dt);
		} catch (Exception e) {
			
			return "";
		}
	}
	/**
	 * 
	 * 方法名称    ：格式化日期
	 * 功能描述    ：
	 * 逻辑描述    :
	 * @param   :date 日期字符串
	 * @return  :Date
	 * @throws  :Exception
	 */
	public static Date getdate(String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(date);
	}
	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
	/**
	 * 
	 * 方法名称    ：去除html标签
	 * 功能描述    ：
	 * 逻辑描述    :
	 * @param   :date 日期字符串
	 * @return  :Date
	 * @throws  :Exception
	 */
//	public String delHTMLTag(String htmlStr) throws Exception {
//		 Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
//	        Matcher m_script = p_script.matcher(htmlStr);
//	        htmlStr = m_script.replaceAll(""); // 过滤script标签
//
//	        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
//	        Matcher m_style = p_style.matcher(htmlStr);
//	        htmlStr = m_style.replaceAll(""); // 过滤style标签
//
//	        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
//	        Matcher m_html = p_html.matcher(htmlStr);
//	        htmlStr = m_html.replaceAll(""); // 过滤html标签
//
//	        return htmlStr.trim(); // 返回文本字符串
//	}

	/**
	 * 建立索引
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String[] xmlCfg = new String[] {
				"WebRoot/WEB-INF/classes/spring-context.xml",
				"WebRoot/WEB-INF/classes/activemq-context.xml",
				"WebRoot/WEB-INF/applicationContext-lucene.xml" };
		ApplicationContext ctx = new FileSystemXmlApplicationContext(xmlCfg);
		IBankDataAccess bankDataAccess = ctx.getBean(BankDataAccess.class);
		//由于不能直接运行程序测试，所以借此测试搜索功能
		Map<String,Object> returnmap=bankDataAccess.getbankcountdata(new int[]{13,2});
		Map<Integer,BankDataBean> bda=(Map<Integer,BankDataBean>)returnmap.get("bankData");
		BankDataBean bdb=bda.get(2);
		System.out.println(bdb.getOnedaycount());
		System.out.println(bdb.getOnedaychange());
		System.out.println(bdb.getFifteendaycount());
		System.out.println(bdb.getFifteendaychange());
		System.out.println(bdb.getSevendaycount());
		System.out.println(bdb.getSevendaychange());
		System.out.println("logo========"+bdb.getLogoimgname());
		List<Map<String,Object>> dataList=bdb.getDataList();
		for(int i=0;i<dataList.size();i++){
			Map<String,Object> map=dataList.get(i);
			System.out.println("sum========"+map.get("sum"));
			System.out.println("date========"+map.get("date"));
		}
		List<Crawl> dibu=(List<Crawl>)returnmap.get("bottomnews");
		
		for(int i=0;i<dibu.size();i++){
			Crawl c=dibu.get(i);
			System.out.println(c.getCrawlTitle());
		}
		List<Crawl> youbu=(List<Crawl>)returnmap.get("rightnews");
		
		for(int i=0;i<youbu.size();i++){
			Crawl c=youbu.get(i);
			System.out.println(c.getCrawlTitle());
		}
		UnbankList<Crawl> fenlei=bankDataAccess.gethongguan(3,"",10,1);
		for(int i=0;i<fenlei.getList().size();i++){
			Crawl c=fenlei.getList().get(i);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println(c.getCrawlId()+"======="+c.getCrawlTitle()+"======="+df.format(c.getNewsTime()));
		}
	}
}
