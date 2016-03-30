package com.lucene;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.util.Version;

import com.lucene.entity.Searchparam;

public interface UnbankWriter<T> {

	/**
	 * LUCENE版本号
	 */
	Version version = Version.LUCENE_46;

	/**
	 * 获得分析器
	 * 
	 * @return
	 */
	Analyzer getAnalyzer();

	/**
	 * 每个索引对象的索引字段细节, 非常重要
	 * 
	 * @param t
	 * @return
	 */
	Document newDocument(T t);

	/**
	 * 将Document转换成页面中的显示实体
	 * 
	 * @param query
	 * @param doc
	 * @param docId
	 * @return
	 */
	T conv(Query query, Document doc, int docId) throws Exception;
	/**
	 * 将Document转换成页面中的显示实体
	 * 
	 * @param query
	 * @param doc
	 * @param docId
	 * @return
	 */
	T conv(String record,List<String> l, String[] words,Document doc) throws Exception;
	/**
	 * 将Document转换成页面中的显示实体
	 * 
	 * @param query
	 * @param doc
	 * @param docId
	 * @return
	 */
	T convbankdata(String record,List<String> l, String[] words,Document doc) throws Exception;
	/**
	 * 将Document转换成页面中的显示实体
	 * 
	 * @param query
	 * @param doc
	 * @param docId
	 * @return
	 */
	T conv(int type,String[] musts,String[] arbitrarilys,String[] nots,Document doc) throws Exception;
	
	/**
	 * 加入索引
	 * 
	 * @param t
	 */
	void addDocument(T t) throws Exception;
	/**
	 * 加入索引
	 * 
	 * @param t
	 */
	void addDocument1(T t) throws Exception;
	/**
	 * 加入索引
	 * 
	 * @param list
	 */
	void addDocument(List<T> list) throws Exception;
	/**
	 * 删除索引
	 * 
	 * @param t
	 */
	void deleteDocument(T t) throws Exception;
	/**
	 * 删除索引
	 * 
	 * @param t
	 */
	void deleteDocument1(T t) throws Exception;
	/**
	 * 删除索引
	 * 
	 * @param list
	 */
	public void deleteDocument(List<Integer> list);
	/**
	 * 搜索
	 * 
	 * @param query
	 * @param offset
	 * @param rowCount
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	UnbankList<T> search(Query query, int offset, int rowCount, Sort sort)
			throws Exception;

	/**
	 * 搜索
	 * 
	 * @param query
	 * @param filter
	 * @param offset
	 * @param rowCount
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	UnbankList<T> search(Query query, Filter filter, int offset, int rowCount,
			Sort sort) throws Exception;

	/**
	 * content-based, recommend, 基于内容的推荐算法
	 * 
	 * @param mltc
	 * @param termSet
	 *            记录用户行为, 用户查询热词
	 * @param text
	 * @param queries
	 *            其他查询条件
	 * @return
	 * @throws Exception
	 */
	UnbankList<T> recommend(MoreLikeThisConfig mltc, Set<Term> termSet,
			String text, Query... queries) throws Exception;
	/**
	 * 
	 * @param mltc, 配置参数
	 * @param text  推荐样本内容
	 * @param sort  排序方式 1权重2时间
	 * @param pageSize     每页个数
	 * @param currentPage  当期页
	 * @return
	 * @throws Exception
	 */
	UnbankList<T> recommend(MoreLikeThisConfig mltc, String text,int sort,int pageSize,int currentPage) throws Exception;
	/**
	 * 推荐关键词
	 * 
	 * @param mltc
	 * @param termSet
	 * @param text
	 * @throws Exception
	 */
	void terms(MoreLikeThisConfig mltc, Set<Term> termSet, String text)
			throws Exception;

	/**
	 * 
	 * @return
	 */
	IndexWriter getWriter();

	/**
	 * 添加索引, 定时任务执行这个方法
	 * 
	 * @return
	 * @throws Exception
	 */
	int indexes() throws Exception;

	/**
	 * 
	 * @param field
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	T findByTerm(String field, String uid) throws Exception;
	
	
	/**
	 * content-based, recommend, 基于内容的推荐算法
	 * 
	 * @param query
	 * @param mltc
	 * @param termSet
	 *            记录用户行为, 用户查询热词
	 * @param text
	 * @param offset
	 * @param rowCount
	 * @param sort
	 *            排序
	 * @return
	 * @throws Exception
	 */
	UnbankList<T> recommend(Query query, MoreLikeThisConfig mltc,
			Set<Term> termSet, String text, int offset, int rowCount, Sort sort)
			throws Exception;
	/**  普通搜索
	 * @param record           搜索关键词
	 * @param pageSize       每页个数
	 * @param currentPage  当期页
	 * @return
	 * @throws Exception
	 */
	UnbankList<T> search(Searchparam searchparam) throws Exception ;
	/**  普通搜索
	 * @param record           搜索关键词
	 * @param pageSize       每页个数
	 * @param currentPage  当期页
	 * @return
	 * @throws Exception
	 */
	UnbankList<T> searchbankdata(Searchparam searchparam) throws Exception ;
	/**  高级搜索
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
	UnbankList<T> search(int type,int userId,String must,String arbitrarily,String not,String startdate,String enddate,int pageSize,int currentPage,int searchsource,int sorttype,String fromsource) throws Exception ;
	/**  普通搜索条件
	 * @param userId             用户id
	 * @param record      		 搜索短语
	 * @param l                  分词列表
	 * @param words              关联词组
	 * @return
	 * @throws Exception
	 */
	QueryWrapper getCondition(Searchparam searchparam,String record,List<String> l,String[] words) throws Exception ;
	/**  普通搜索条件
	 * @param userId             用户id
	 * @param record      		 搜索短语
	 * @param l                  分词列表
	 * @param words              关联词组
	 * @return
	 * @throws Exception
	 */
	QueryWrapper getConditionbankdata(Searchparam searchparam,String record,List<String> l,String[] words) throws Exception ;
	/**  高级搜索条件
	 * @param userId             用户id
	 * @param record      		 必须包含词组
	 * @param l                  任意包含词组
	 * @param words              不包含词组
	 * @param startdate       	开始日期
	 * @param enddate        	结束日期
	 * @param searchsource  搜索源 1全文 2标题
	 * @param sort          排序1时间 2权重
	 * @param fromsource    新闻来源
	 * @return
	 * @throws Exception
	 */
	QueryWrapper getCondition(int userId,String[] must,String[] arbitrarily,String[] not,String startdate,String enddate,int searchsource,int sorttype,String fromsource) throws Exception ;
	/**
	 * 搜索条件
	 * 
	 * @param mltc              模糊搜索条件
	 * @param ir      		    
	 * @param text              对比源
	 * @param words              不包含词组
	 * @param startdate       	开始日期
	 * @param enddate        	结束日期
	 * @param searchsource  搜索源 1全文 2标题
	 * @param sort          排序1时间 2权重
	 * @param fromsource    新闻来源
	 * @return
	 */
	QueryWrapper getCondition(MoreLikeThisConfig mltc,IndexReader ir, String text,int sort) throws Exception;
	/**  类标记名称
	 * @return
	 * @throws Exception
	 */
	String getClassname() throws Exception ;
	/** 去除html标签
	 * content   要去除html标签的内容
	 * @return
	 * @throws Exception
	 */
//	String delHTMLTag(String htmlStr) throws Exception ;
}
