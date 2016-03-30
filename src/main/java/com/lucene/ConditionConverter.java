package com.lucene;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.Version;

import com.lucene.entity.term.Period;
import com.lucene.entity.term.PeriodDay;
import com.lucene.entity.term.PeriodMonth;
import com.lucene.entity.term.Term;
import com.lucene.util.VisitCondition;


public class ConditionConverter {

	public static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static DateFormat dd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	/**
	 * 
	 * 方法名称    :recommanderGrab
	 * 功能描述    ：抓取文章推荐，搜索
	 * 逻辑描述    :
	 * @param   :无
	 * @return  :QueryWrapper
	 * @throws  :无
	 * @since   :Ver 1.00
	 */
	public static QueryWrapper grabContent(UnbankWriter<?> uw, VisitCondition vcon) throws ParseException, java.text.ParseException {
		
		boolean flag = false;//判断是否没有条件
		
		Sort sort = null;
		//
		BooleanQuery allbq = new BooleanQuery();
		
		if(vcon.getKeyword() != null && !"".equals(vcon.getKeyword())){
			Query q1 = new QueryParser(UnbankWriter.version, "text",
					uw.getAnalyzer()).parse(vcon.getKeyword());

			allbq.add(q1, BooleanClause.Occur.MUST);
			flag = true;
		}else{
			sort = new Sort(new SortField("newsTime",
					SortField.Type.LONG, true));
		}
		/*
		boolean ff = !keywordQuery(uw,allbq,vcon.getKeyword(),"crawlTitle","text");
		*/
		if(vcon.getForm() != null){
			Query time = NumericRangeQuery.newLongRange("newsTime",
					dd.parse(vcon.getForm()).getTime(), dd.parse(vcon.getTo()).getTime()
							, true, true);
			allbq.add(time, BooleanClause.Occur.MUST);
			flag = true;
		}
		if(vcon.getKeywords() != null){
			for(int i = 0; i<vcon.getKeywords().size();i++){
//				Query keylist = new QueryParser(UnbankWriter.version, "text",
//						uw.getAnalyzer()).parse(vcon.getKeywords().get(i).toString());
/*包含关键词*/     Query keylist = new TermQuery(new org.apache.lucene.index.Term("text", vcon.getKeywords().get(i).toString()));
				allbq.add(keylist,BooleanClause.Occur.SHOULD);
			}
			flag = true;
		}
		if(!flag){
			MatchAllDocsQuery all = new MatchAllDocsQuery();
			
			return new QueryWrapper(all,sort);
		}
		
		return new QueryWrapper(allbq,sort);
	}
	
	/**
	 * 
	 * 方法名称    :myContent
	 * 功能描述    ：我的内容搜索
	 * 逻辑描述    :
	 * @param   :无
	 * @return  :QueryWrapper
	 * @throws  :无
	 * @since   :Ver 1.00
	 */
	public static QueryWrapper myContent(UnbankWriter<?> uw,VisitCondition vcon) throws ParseException, java.text.ParseException {
		
		Sort sort = null;
		BooleanQuery allbq = new BooleanQuery();
		boolean flag = false;
//		System.out.println("-----------------106--------------"+vcon.getForm());
		if(vcon.getUserId() != 0){
			Query q1 = NumericRangeQuery.newIntRange("userId", vcon.getUserId(), vcon.getUserId(),
					true, true);
			allbq.add(q1,BooleanClause.Occur.MUST);
			flag = true;
		}
		if(vcon.getForm() != null && !vcon.getForm().equals("")){
			Query time = NumericRangeQuery.newLongRange("contentInsertTime",
					dd.parse(vcon.getForm()).getTime(), dd.parse(vcon.getTo()).getTime()
							, true, true);
			allbq.add(time, BooleanClause.Occur.MUST);
			flag = true;
		}
		if(vcon.getNodeId() != 0){
			Query node = NumericRangeQuery.newIntRange("nodeId", vcon.getNodeId(), vcon.getNodeId(), true, true);
			allbq.add(node,BooleanClause.Occur.MUST);
			flag = true;
		}
		if(vcon.getKeyword() != null && !"".equals(vcon.getKeyword())){
			Query q1 = new QueryParser(UnbankWriter.version, "text",
					uw.getAnalyzer()).parse(vcon.getKeyword());

			allbq.add(q1, BooleanClause.Occur.MUST);
			flag = true;
		}else{
			sort = new Sort(new SortField("contentInsertTime",
					SortField.Type.LONG, true));
		}
//		boolean ff = !keywordQuery(uw,allbq,vcon.getKeyword(),"contentName","text");
		
		if(vcon.getKeywords() != null){
			for(int i = 0; i<vcon.getKeywords().size();i++){
//				Query keylist = new QueryParser(UnbankWriter.version, "text",
//						uw.getAnalyzer()).parse(vcon.getKeywords().get(i).toString());
				Query keylist = new TermQuery(new org.apache.lucene.index.Term("text", vcon.getKeywords().get(i).toString()));
				allbq.add(keylist,BooleanClause.Occur.SHOULD);
			}
			flag = true;
		}
		
		if(!flag){
			MatchAllDocsQuery all = new MatchAllDocsQuery();
			
			return new QueryWrapper(all,sort);
		}
		
		return new QueryWrapper(allbq,sort);
	}
	
	//抓取文章搜索
	public static QueryWrapper crawlCondition(UnbankWriter<?> uw,
			List<Term> terms) throws ParseException, java.text.ParseException {
		Sort sort = null;
		//
		BooleanQuery allbq = new BooleanQuery();
		for (Term term : terms) {
			//
			BooleanQuery onebq = new BooleanQuery();
			if (term.isCrawl()) {
				// 关键词
				if (!keywordQuery(uw, onebq, term.getKeyword(), "crawlTitle",
						"text")) {
					// 没有关键词就按时间降序排序
					sort = new Sort(new SortField("newsTime",
							SortField.Type.LONG, true));
				}

				// 使用定制周期
				Datetime pt = new Datetime(term.getFrom(), term.getTo());
				periodQuery(term.getPeriod(), pt);

				// 新闻时间
				timeQuery(onebq, "newsTime", pt.from, pt.to);

				// 网址
				rangeQuery(onebq, term.getWebsites(), "websiteId");

			}
			allbq.add(onebq, BooleanClause.Occur.SHOULD);
		}
		return new QueryWrapper(allbq, sort);
	}

	//自主文章搜索
	public static QueryWrapper docCondition(UnbankWriter<?> uw, List<Term> terms,int userId)
			throws ParseException, java.text.ParseException {
		Sort sort = null;
		BooleanQuery allbq = new BooleanQuery();
		if(terms == null){//如果没有条件
			Query q1 = NumericRangeQuery.newIntRange("userId", userId, userId,
					true, true);
			allbq.add(q1,BooleanClause.Occur.MUST);
			sort = new Sort(new SortField("contentInsertTime",
					SortField.Type.LONG, true));
//			MatchAllDocsQuery all = new MatchAllDocsQuery();//没有参数时可以使用此对象
			return new QueryWrapper(allbq,sort);
		}else
		for (Term term : terms) {
			//
			
			
			
			BooleanQuery onebq = new BooleanQuery();
			if (term.isLocal() || term.getStructures() != null) {
				// 关键词
				if (!keywordQuery(uw, onebq, term.getKeyword(), "contentName",
						"text")) {
					// 没有关键词就按时间降序排序
					sort = new Sort(new SortField("contentInsertTime",
							SortField.Type.LONG, true));
				}

				// 使用定制周期
				Datetime pt = new Datetime(term.getFrom(), term.getTo());
				periodQuery(term.getPeriod(), pt);

				// 发表时间
				timeQuery(onebq, "contentInsertTime", pt.from, pt.to);

				// 产品属性
//				rangeQuery(onebq, term.getStructures(), "strucutureId");
			}
			allbq.add(onebq, BooleanClause.Occur.SHOULD);
		}
		return new QueryWrapper(allbq, sort);
	}

	/**
	 * 
	 * @param uw
	 * @param query
	 * @param keyword
	 * @param titlefield
	 * @param textfield
	 * @return boolean 是否使用了关键词
	 * @throws ParseException
	 */
	private static boolean keywordQuery(UnbankWriter<?> uw, BooleanQuery query,
			String keyword, String titlefield, String textfield)
			throws ParseException {
		boolean hasKey = false;
		if (keyword != null && !"".equals(keyword)) {
			// Multi Field
			// Query mq = new MultiFieldQueryParser(UnbankWriter.version,
			// new String[] { titlefield, textfield }, uw.getAnalyzer())
			// .parse(keyword);

			//

			// BooleanQuery keywordbq = new BooleanQuery();
			// 标题
			// QueryParser parser1 = new QueryParser(UnbankWriter.version,
			// titlefield, UnbankWriter.analyzer);
			// Query q1 = parser1.parse(keyword);

			// 内容
			// QueryParser parser2 = new QueryParser(UnbankWriter.version,
			// textfield, UnbankWriter.analyzer);
			// Query q2 = parser2.parse(keyword);

			// keywordbq.add(q1, BooleanClause.Occur.SHOULD);
			// keywordbq.add(q2, BooleanClause.Occur.SHOULD);

			// keywordbq.add(mq, BooleanClause.Occur.MUST);

			Query q1 = new QueryParser(UnbankWriter.version, textfield,
					uw.getAnalyzer()).parse(keyword);

			query.add(q1, BooleanClause.Occur.MUST);
			hasKey = true;
		}
		return hasKey;
	}

	/**
	 * 定制周期时间
	 * 
	 * @param period
	 * @param periodType
	 * @param pt
	 * @throws java.text.ParseException
	 */
	private static void periodQuery(Period period, Datetime pt)
			throws java.text.ParseException {
		if (period != null) {
			if (period instanceof PeriodDay) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				pt.to = sdf.format(cal.getTime());

				PeriodDay day = (PeriodDay) period;
				cal.add(Calendar.DAY_OF_MONTH, -day.getDays());
				pt.from = sdf.format(cal.getTime());
			} else if (period instanceof PeriodMonth) {
				PeriodMonth month = (PeriodMonth) period;
				Date nowDate = sdf.parse(sdf.format(new Date()));
				Date fromDate = sdf.parse(month.getFrom());
				Date toDate = sdf.parse(month.getTo());

				// 周期差值
				int days = (int) ((toDate.getTime() - fromDate.getTime()) / 86400000L);

				Calendar nowCal = Calendar.getInstance();
				Calendar fromCal = Calendar.getInstance();
				Calendar toCal = Calendar.getInstance();

				//
				nowCal.setTime(nowDate);
				toCal.setTime(toDate);

				// 日期
				int date = toCal.get(Calendar.DAY_OF_MONTH);
				toCal.setTime(nowDate);
				toCal.set(Calendar.DAY_OF_MONTH, date);
				fromCal.setTime(nowDate);
				fromCal.set(Calendar.DAY_OF_MONTH, date);
				fromCal.add(Calendar.DAY_OF_MONTH, -days);

				if (nowCal.compareTo(fromCal) < 0) {
					toCal.add(Calendar.MONTH, -1);
					fromCal.add(Calendar.MONTH, -1);
				}

				pt.to = sdf.format(toCal.getTime());
				pt.from = sdf.format(fromCal.getTime());
			}
		}
	}

	/**
	 * 搜索的开始时间至结束时间, 前台按日输入, 后台按日期getTime的long值计算, 所以结束日期加1天(86400000ms)
	 * 
	 * @param query
	 * @param timefield
	 * @param from
	 * @param to
	 * @throws java.text.ParseException
	 */
	private static void timeQuery(BooleanQuery query, String timefield,
			String from, String to) throws java.text.ParseException {

		if (from == null || to == null || "".equals(from) || "".equals(to)) {
			Date now = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			to = sdf.format(cal.getTime());
			cal.add(Calendar.DAY_OF_MONTH, -30);
			from = sdf.format(cal.getTime());
		}

		if (from != null && to != null && !"".equals(from) && !"".equals(to)) {
			Query time = NumericRangeQuery.newLongRange(timefield,
					sdf.parse(from).getTime(), sdf.parse(to).getTime()
							+ (86400000 - 1), true, true);
			query.add(time, BooleanClause.Occur.MUST);
		}
	}

	/**
	 * 等值条件, IN
	 * 
	 * @param query
	 * @param array
	 * @param arrayfield
	 */
	private static void rangeQuery(BooleanQuery query, List<Integer> array,
			String arrayfield) {
		if (array != null && array.size() != 0) {
			BooleanQuery rangebq = new BooleanQuery();
			for (Integer i : array) {
				Query range = NumericRangeQuery.newIntRange(arrayfield, i, i,
						true, true);
				rangebq.add(range, BooleanClause.Occur.SHOULD);
			}
			query.add(rangebq, BooleanClause.Occur.MUST);
		}
	}

	private static class Datetime {

		private String from;

		private String to;

		private Datetime(String from, String to) {
			this.from = from;
			this.to = to;
		}

	}
	
	/**
	 * 
	 * 方法名称    :analyzerCnStr
	 * 功能描述    ：字符串分词
	 * 逻辑描述    :
	 * @param   :无
	 * @return  :List<String>
	 * @throws  :无
	 * @since   :Ver 1.00
	 */
	public List<String> analyzerCnStr(String str){
		
		List<String> result = new ArrayList();
		Version version = Version.LUCENE_46;//lucene版本号
		Analyzer analyzer=new StandardAnalyzer(Version.LUCENE_46);
		IndexWriterConfig conf = new IndexWriterConfig(version,analyzer);
		 try {
			TokenStream tokenStream = analyzer.tokenStream("field", str);
			CharTermAttribute  term=tokenStream.addAttribute(CharTermAttribute.class);
			tokenStream.reset(); 
			while( tokenStream.incrementToken() ){
				result.add( term.toString() );
				} 
			tokenStream.end();
			tokenStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
		return result;
		
	}

	public static void main(String[] args) throws java.text.ParseException {
		Calendar a = Calendar.getInstance();
		a.setTime(sdf.parse("2014-01-26"));

		Calendar b = Calendar.getInstance();
		b.setTime(sdf.parse("2014-01-28"));

		long day = (b.getTime().getTime() - a.getTime().getTime()) / 86400000L;

		System.out.println(a.getTime().getTime());
		System.out.println(b.getTime().getTime());
	}

}
