package com.lucene;

import java.io.File;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.mlt.MoreLikeThis;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.Version;
import org.springframework.jdbc.core.RowMapper;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.lucene.dao.UnbankDAO;
import com.lucene.entity.Searchparam;
import com.lucene.entity.doc.Crawl;
import com.lucene.entity.doc.Crawls;
import com.lucene.html.Function;


/**
 * 
 * @author Quzile
 * 
 */
public class CrawlWriter  extends AbstractUnbankWriter<Crawl> {
	
	public CrawlWriter(File file, Analyzer analyzer) {
		super(file, analyzer);
//		FieldType fieldType = new FieldType();
//		fieldType.setIndexed(true);
//		fieldType.setTokenized(true);
//		fieldType.setStoreTermVectors(true);
//		fieldType.setStoreTermVectorOffsets(true);
//		text = new Field("text", "", fieldType);
	}

	@Override
	public Document newDocument(Crawl t) {
		Document doc = new Document();
		Field crawlId = new StringField("crawlId", String.valueOf(""),Field.Store.YES);
		Field text = new TextField("text", "", Field.Store.YES);
		Field crawlTitle = new TextField("crawlTitle", "", Field.Store.YES);
		Field crawlBrief = new TextField("crawlBrief", "", Field.Store.YES);
		Field url = new TextField("url", "", Field.Store.YES);
		Field webName = new TextField("webName", "", Field.Store.YES);
		Field websiteId = new IntField("websiteId", 0, Field.Store.YES);
		Field newsTime = new LongField("newsTime", 0L, Field.Store.YES);
		Field crawlTime = new LongField("crawlTime", 0L, Field.Store.YES);
		Field dataclass = new IntField("dataclass", 0, Field.Store.YES);
		Field datafrom = new IntField("datafrom", 0, Field.Store.YES);
		if (t.getText() == null) {
			return null;
		}

		crawlId.setStringValue(String.valueOf(t.getCrawlId()));
		crawlTitle.setStringValue(t.getCrawlTitle());
		crawlBrief.setStringValue(t.getCrawlBrief());
		text.setStringValue(t.getText());
		url.setStringValue(t.getUrl());
		webName.setStringValue(t.getWebname());
		websiteId.setIntValue(t.getWebsiteId());
		newsTime.setLongValue(t.getNewsTime());
		crawlTime.setLongValue(t.getCrawlTime());
		dataclass.setIntValue(t.getDataclass());
		datafrom.setIntValue(t.getDatafrom());

		doc.add(crawlId);

		doc.add(crawlTitle);
		doc.add(crawlBrief);
		doc.add(text);

		doc.add(url);
		doc.add(webName);

		doc.add(websiteId);
		doc.add(newsTime);
		doc.add(crawlTime);
		
		doc.add(dataclass);
		doc.add(datafrom);
		return doc;
	}

	@Override
	public Crawl conv(Query query, Document doc, int docId) throws Exception {
		Crawl o = new Crawl();
		o.setCrawlId(Integer.parseInt(doc.get("crawlId")));

		o.setCrawlTitle(highlight(query, docId, "crawlTitle",
				doc.get("crawlTitle")));
		o.setCrawlBrief(highlight(query, docId, "crawlBrief",
				doc.get("crawlBrief")));

		o.setUrl(doc.get("url"));
		o.setWebname(doc.get("webName"));

		o.setWebsiteId(Integer.parseInt(doc.get("websiteId")));
		o.setNewsTime(Long.parseLong(doc.get("newsTime")));
		o.setCrawlTime(Long.parseLong(doc.get("crawlTime")));
		o.setDataclass(Integer.parseInt(doc.get("dataclass")));
		o.setDatafrom(Integer.parseInt(doc.get("datafrom")));

		// 正向
		keywords(docId, "text", o.getKeywords());

		return o;
	}
	@Override
	public Crawl conv(String record,List<String> l,String[] words, Document doc) throws Exception {
		String crawlTitle=doc.get("crawlTitle");
		String text=doc.get("text");
		String crawlId=doc.get("crawlId");
		String crawlTime=doc.get("crawlTime");
		String crawlBrief=doc.get("crawlBrief");
		String url=doc.get("url");
		String webName=doc.get("webName");
		String websiteId=doc.get("websiteId");
		String newsTime=doc.get("newsTime");
		String dataclass=doc.get("dataclass");
		String datafrom=doc.get("datafrom");
		if(!record.trim().equals("")){
			//分词多个词时加组合条件
			if(l.size()>1){
				for(int j=0;j<l.size();j++){
					String key=l.get(j)+"";
					crawlTitle=crawlTitle.replaceAll(key+"", "<span>"+key+"</span>");
					text=text.replaceAll(key+"", "<span>"+key+"</span>");
							
					if(key.matches(".*[a-z]+.*")){
						key=key.toUpperCase();  
						crawlTitle=crawlTitle.replaceAll(key+"", "<span>"+key+"</span>");
						text=text.replaceAll(key+"", "<span>"+key+"</span>");
					}
				}
			}else{
				if(words==null){
					crawlTitle=crawlTitle.replaceAll(record+"", "<span>"+record+"</span>");
					text=text.replaceAll(record+"", "<span>"+record+"</span>");
					
					if(record.matches(".*[a-z]+.*")){
						record=record.toUpperCase();  
						crawlTitle=crawlTitle.replaceAll(record+"", "<span>"+record+"</span>");
						text=text.replaceAll(record+"", "<span>"+record+"</span>");
					}
				}else{
					for(int j=0;j<words.length;j++){
						String r=words[j];
						crawlTitle=crawlTitle.replaceAll(r+"", "<span>"+r+"</span>");
						text=text.replaceAll(r+"", "<span>"+r+"</span>");
						
						if(r.matches(".*[a-z]+.*")){
							r=r.toUpperCase();  
							crawlTitle=crawlTitle.replaceAll(r+"", "<span>"+r+"</span>");
							text=text.replaceAll(r+"", "<span>"+r+"</span>");
						}
					}
					
				}
				
				
			}
		}
		
		String[] ss=text.split("[!,.:;?！，。：；？]");//‘’“”'\"
		StringBuffer sb=new StringBuffer();
		for(int j=0;j<ss.length;j++){
			if(sb.length()+ss[j].length()>80)break;
			else sb.append(ss[j]+"/");
		}
		
		Crawl o = new Crawl();
		o.setCrawlId(Integer.parseInt(crawlId));

		o.setCrawlTitle(crawlTitle);
		o.setCrawlBrief(crawlBrief);

		o.setUrl(url);
		o.setWebname(webName);

		o.setWebsiteId(Integer.parseInt(websiteId));
		o.setNewsTime(Long.parseLong(newsTime));
		o.setCrawlTime(Long.parseLong(crawlTime));
		
		o.setText(sb.toString());

		try {
			o.setDataclass(Integer.parseInt(dataclass));
			o.setDatafrom(Integer.parseInt(datafrom));
		} catch (Exception e) {
			
		}
		return o;
	}
	
	@Override
	public Crawl convbankdata(String record,List<String> l,String[] words, Document doc) throws Exception {
		String crawlTitle=doc.get("crawlTitle");
		String text=doc.get("text");
		String crawlId=doc.get("crawlId");
		String crawlTime=doc.get("crawlTime");
		String crawlBrief=doc.get("crawlBrief");
		String url=doc.get("url");
		String webName=doc.get("webName");
		String websiteId=doc.get("websiteId");
		String newsTime=doc.get("newsTime");
		String dataclass=doc.get("dataclass");
		String datafrom=doc.get("datafrom");
//		if(!record.trim().equals("")){
//			//分词多个词时加组合条件
//			if(l.size()>1){
//				for(int j=0;j<l.size();j++){
//					String key=l.get(j)+"";
//					crawlTitle=crawlTitle.replaceAll(key+"", "<span>"+key+"</span>");
//					text=text.replaceAll(key+"", "<span>"+key+"</span>");
//							
//					if(key.matches(".*[a-z]+.*")){
//						key=key.toUpperCase();  
//						crawlTitle=crawlTitle.replaceAll(key+"", "<span>"+key+"</span>");
//						text=text.replaceAll(key+"", "<span>"+key+"</span>");
//					}
//				}
//			}else{
//				if(words==null){
//					crawlTitle=crawlTitle.replaceAll(record+"", "<span>"+record+"</span>");
//					text=text.replaceAll(record+"", "<span>"+record+"</span>");
//					
//					if(record.matches(".*[a-z]+.*")){
//						record=record.toUpperCase();  
//						crawlTitle=crawlTitle.replaceAll(record+"", "<span>"+record+"</span>");
//						text=text.replaceAll(record+"", "<span>"+record+"</span>");
//					}
//				}else{
//					for(int j=0;j<words.length;j++){
//						String r=words[j];
//						crawlTitle=crawlTitle.replaceAll(r+"", "<span>"+r+"</span>");
//						text=text.replaceAll(r+"", "<span>"+r+"</span>");
//						
//						if(r.matches(".*[a-z]+.*")){
//							r=r.toUpperCase();  
//							crawlTitle=crawlTitle.replaceAll(r+"", "<span>"+r+"</span>");
//							text=text.replaceAll(r+"", "<span>"+r+"</span>");
//						}
//					}
//					
//				}
//				
//				
//			}
//		}
//		
//		String[] ss=text.split("[!,.:;?！，。：；？]");//‘’“”'\"
//		StringBuffer sb=new StringBuffer();
//		for(int j=0;j<ss.length;j++){
//			if(sb.length()+ss[j].length()>80)break;
//			else sb.append(ss[j]+"/");
//		}
		
		Crawl o = new Crawl();
		o.setCrawlId(Integer.parseInt(crawlId));

		o.setCrawlTitle(crawlTitle);
		o.setCrawlBrief(crawlBrief);

		o.setUrl(url);
		o.setWebname(webName);

		o.setWebsiteId(Integer.parseInt(websiteId));
		o.setNewsTime(Long.parseLong(newsTime));
		o.setCrawlTime(Long.parseLong(crawlTime));
		
		o.setText(text);

		try {
			o.setDataclass(Integer.parseInt(dataclass));
			o.setDatafrom(Integer.parseInt(datafrom));
		} catch (Exception e) {
			
		}
		return o;
	}
	@Override
	public Crawl conv( int type,String[] musts,String[] arbitrarilys,String[] nots,Document doc) throws Exception {
		String crawlTitle=doc.get("crawlTitle");
		String text=doc.get("text");
		String crawlId=doc.get("crawlId");
		String crawlTime=doc.get("crawlTime");
		String crawlBrief=doc.get("crawlBrief");
		String url=doc.get("url");
		String webName=doc.get("webName");
		String websiteId=doc.get("websiteId");
		String newsTime=doc.get("newsTime");
		String dataclass=doc.get("dataclass");
		String datafrom=doc.get("datafrom");
		
		
		Crawl o = new Crawl();
		if(type==1){
			for(int j=0;j<musts.length;j++){
        		String key=musts[j];
        		crawlTitle=crawlTitle.replaceAll(key+"", "<span>"+key+"</span>");
        		text=text.replaceAll(key+"", "<span>"+key+"</span>");
						
				if(key.matches(".*[a-z]+.*")){
					key=key.toUpperCase();  
					crawlTitle=crawlTitle.replaceAll(key+"", "<span>"+key+"</span>");
					text=text.replaceAll(key+"", "<span>"+key+"</span>");
				}
        	}
        	for(int j=0;j<arbitrarilys.length;j++){
        		String key=arbitrarilys[j];
        		crawlTitle=crawlTitle.replaceAll(key+"", "<span>"+key+"</span>");
        		text=text.replaceAll(key+"", "<span>"+key+"</span>");
						
				if(key.matches(".*[a-z]+.*")){
					key=key.toUpperCase();  
					crawlTitle=crawlTitle.replaceAll(key+"", "<span>"+key+"</span>");
					text=text.replaceAll(key+"", "<span>"+key+"</span>");
				}
        	}
		
		
        	String[] ss=text.split("[!,.:;?！，。：；？]");//‘’“”'\"
        	StringBuffer sb=new StringBuffer();
        	for(int j=0;j<ss.length;j++){
        		if(ss[j].matches(".*<span>.{0,30}</span>.*")){
        			if(sb.length()+ss[j].length()>80)break;
        			else sb.append(ss[j]+"/");
        		}
			
        	}
        	o.setText(sb.toString());
		}else{
			o.setText(text);
		}
        	
		
		
		o.setCrawlId(Integer.parseInt(crawlId));

		o.setCrawlTitle(crawlTitle);
		o.setCrawlBrief(crawlBrief);

		o.setUrl(url);
		o.setWebname(webName);

		o.setWebsiteId(Integer.parseInt(websiteId));
		o.setNewsTime(Long.parseLong(newsTime));
		o.setCrawlTime(Long.parseLong(crawlTime));
		
		
		o.setDataclass(Integer.parseInt(dataclass));
		o.setDatafrom(Integer.parseInt(datafrom));
		return o;
	}
	@Override
	protected Term findUnique(Document doc) {
		return new Term("crawlId", doc.get("crawlId"));
	}

	@Override
	protected UnbankList<Crawl> initList(int totalHits) {
		return new Crawls(totalHits);
	}

	@Override
	protected List<Crawl> findIndexes(UnbankDAO unbankDAO, Object... args) {
		String sql = "SELECT `crawl_id`, `website_id`, `crawl_title`, `text`, `crawl_views`, `web_name`, `url`, `file_index`, `news_time`, `crawl_time` FROM `ptf_crawl` JOIN `ptf_crawl_text` USING(`crawl_id`) WHERE `file_index` = 2 AND `task` = 2 AND `crawl_time` < ? LIMIT ?, ?";

		List<Crawl> list = unbankDAO.findBySql(sql, new RowMapper<Crawl>() {
			@Override
			public Crawl mapRow(ResultSet rs, int rowNum) throws SQLException {
				Crawl o = new Crawl();
				o.setCrawlId(rs.getInt("crawl_id"));
				o.setWebsiteId(rs.getInt("website_id"));
				o.setCrawlTitle(rs.getString("crawl_title"));

				// o.setCrawlBrief(rs.getString("crawl_brief"));

				String text = rs.getString("text");

				if (text != null) {
//					o.setOriginalText(Function.optimizeContent(text));

					String content = Function.removeCharacterEntity(text);
					content = Function.removeHTMLTag(content).trim();

					o.setText(content);

					int len = content.length();
					o.setCrawlBrief(content.substring(0,
							len > getLengthOfText() ? getLengthOfText() : len));
				}

				o.setCrawlViews(rs.getInt("crawl_views"));
				o.setWebname(rs.getString("web_name"));
				o.setUrl(rs.getString("url"));
				o.setFileIndex(rs.getBoolean("file_index"));
				
				try {
					o.setNewsTime(rs.getTimestamp("news_time").getTime());
				} catch (Exception e) {
					o.setNewsTime(rs.getTimestamp("crawl_time").getTime());
				}
				
				o.setCrawlTime(rs.getTimestamp("crawl_time").getTime());
				
				o.setDataclass(0);
				o.setDatafrom(1);
				return o;
			}
		}, args);

		/*
		 * 自动分类
		 */

		return list;
	}

	@Override
	protected int update(UnbankDAO unbankDAO, int oldValue, int newValue,
			String datetime) {
		String sql = "UPDATE `ptf_crawl` SET `file_index` = ? WHERE `file_index` = ? AND `task` = 2 AND `crawl_time` < ?";
		return unbankDAO.update(sql, newValue, oldValue, datetime);
	}

	@Override
	protected Term findContent(int contentId) {
		return null;
	}
	
	@Override
	public QueryWrapper getConditionbankdata(Searchparam searchparam,String record, List<String> l,String[] words) throws Exception  {
		//分词器
		Analyzer analyzer = new IKAnalyzer(true);
		//查询条件
		BooleanQuery boolQuery = new BooleanQuery();
		boolQuery.setMaxClauseCount(10000);//查询条件长度
		Sort sort =null;
		
		//初始化银行数据
		if("init".equals(searchparam.getArticletype())){
			String[] fieldName = { "crawlTitle", "text" }; 
			if(words==null){
				QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_46, fieldName, analyzer);
				Query q2 = queryParser.parse(record);
				boolQuery.add(q2, BooleanClause.Occur.MUST);
			}else{
				BooleanQuery bool = new BooleanQuery();
				for(int i=0;i<words.length;i++){
					QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_46, fieldName, analyzer);
					Query q2 = queryParser.parse(words[i]);
					bool.add(q2, BooleanClause.Occur.SHOULD);
				}
				boolQuery.add(bool, BooleanClause.Occur.MUST);
			}
			Date dt = new Date();
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    		String currydate= df.format(dt);
    		Query tq  = NumericRangeQuery.newLongRange("newsTime",
        			getdate(getOneDay(currydate,-31)).getTime(),getdate(getOneDay(currydate,0)).getTime(), true, false);
    		boolQuery.add(tq, BooleanClause.Occur.MUST);
			sort =new Sort(new SortField("newsTime",SortField.Type.LONG,true));
		}
		//每天计算银行数据
		else if("initYesterday".equals(searchparam.getArticletype())){
			String[] fieldName = { "crawlTitle", "text" }; 
			if(words==null){
				QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_46, fieldName, analyzer);
				Query q2 = queryParser.parse(record);
				boolQuery.add(q2, BooleanClause.Occur.MUST);
			}else{
				BooleanQuery bool = new BooleanQuery();
				for(int i=0;i<words.length;i++){
					QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_46, fieldName, analyzer);
					Query q2 = queryParser.parse(words[i]);
					bool.add(q2, BooleanClause.Occur.SHOULD);
				}
				boolQuery.add(bool, BooleanClause.Occur.MUST);
			}
			Date dt = new Date();
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    		String currydate= df.format(dt);
    		Query tq  = NumericRangeQuery.newLongRange("newsTime",
        			getdate(getOneDay(currydate,-1)).getTime(),getdate(getOneDay(currydate,0)).getTime(), true, false);
    		boolQuery.add(tq, BooleanClause.Occur.MUST);
			sort =new Sort(new SortField("newsTime",SortField.Type.LONG,true));
		}
		//查询分类相关新闻
		else if("fenlei".equals(searchparam.getArticletype())) {
			
			String[] fieldName = { "crawlTitle", "text" }; 
			if(words==null){
				QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_46, fieldName, analyzer);
				Query q2 = queryParser.parse(record);
				boolQuery.add(q2, BooleanClause.Occur.MUST);
			}else{
				BooleanQuery bool = new BooleanQuery();
				for(int i=0;i<words.length;i++){
					QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_46, fieldName, analyzer);
					Query q2 = queryParser.parse(words[i]);
					bool.add(q2, BooleanClause.Occur.SHOULD);
				}
				boolQuery.add(bool, BooleanClause.Occur.MUST);
			}
			if(!"".equals(searchparam.getBankdatarecord())){
				QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_46, fieldName, analyzer);
				Query q2 = queryParser.parse(searchparam.getBankdatarecord());
				q2.setBoost(10000);
				boolQuery.add(q2, BooleanClause.Occur.MUST);
			}
			sort =new Sort(new SortField("newsTime",SortField.Type.LONG,true));	
		}//查询银行相关新闻
		else if("onebank".equals(searchparam.getArticletype())){
			
			String[] fieldName = { "crawlTitle", "text" }; 
			if(words==null){
				QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_46, fieldName, analyzer);
				Query q2 = queryParser.parse(record);
				boolQuery.add(q2, BooleanClause.Occur.MUST);
			}else{
				BooleanQuery bool = new BooleanQuery();
				for(int i=0;i<words.length;i++){
					QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_46, fieldName, analyzer);
					Query q2 = queryParser.parse(words[i]);
					bool.add(q2, BooleanClause.Occur.SHOULD);
				}
				boolQuery.add(bool, BooleanClause.Occur.MUST);
			}
			if(!"".equals(searchparam.getBankdatarecord())){
				QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_46, fieldName, analyzer);
				Query q2 = queryParser.parse(searchparam.getBankdatarecord());
				q2.setBoost(10000);
				boolQuery.add(q2, BooleanClause.Occur.MUST);
			}
			Date dt = new Date();
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    		String currydate= df.format(dt);
    		Query tq  = NumericRangeQuery.newLongRange("newsTime",
        			getdate(getOneDay(currydate,-2)).getTime(),getdate(getOneDay(currydate,1)).getTime(), true, false);
    		
    		boolQuery.add(tq, BooleanClause.Occur.MUST);
		}
		return new QueryWrapper(boolQuery, sort);
	}
	@Override
	public QueryWrapper getCondition(Searchparam searchparam,String record, List<String> l,String[] words) throws Exception  {
		//分词器
		Analyzer analyzer = new IKAnalyzer(true);
		//查询条件
		BooleanQuery boolQuery = new BooleanQuery();
		Sort sort =null;
		
		
			if(!record.equals("")){
				//分词多个词时加组合条件
				if(l.size()>3){
					BooleanQuery bool = new BooleanQuery();
					BooleanQuery bool1 = new BooleanQuery();
					
					PhraseQuery phraseQuerycontent =new PhraseQuery();
					phraseQuerycontent.setSlop(25);
					phraseQuerycontent.setBoost(1000);
					PhraseQuery phraseQuerytitle =new PhraseQuery();
					phraseQuerytitle.setSlop(25);
					phraseQuerytitle.setBoost(1000);
					for(int i=0;i<l.size();i++){
					    //单个的字加上有时搜不到内容
					    if(l.get(i).length()>1){
					    	phraseQuerycontent.add(new Term("text", l.get(i)));
							phraseQuerytitle.add(new Term("crawlTitle", l.get(i)));
							if(record.length()>10){
								TermQuery query1 = new TermQuery(new Term("text", l.get(i)));
			        			TermQuery query2 = new TermQuery(new Term("crawlTitle", l.get(i)));
			        			BooleanQuery bool2 = new BooleanQuery();
			        			bool2.add(query1, BooleanClause.Occur.SHOULD);
			        			bool2.add(query2, BooleanClause.Occur.SHOULD);
								bool1.add(bool2, BooleanClause.Occur.MUST);
								
							}
					    }
						
					}
					bool.add(phraseQuerytitle, BooleanClause.Occur.SHOULD);
					bool.add(phraseQuerycontent, BooleanClause.Occur.SHOULD);
					if(record.length()>10){
						bool.add(bool1, BooleanClause.Occur.SHOULD);
					}
					
					boolQuery.add(bool, BooleanClause.Occur.MUST);
					
//					PhraseQuery phraseQuerycontent =new PhraseQuery();
//					phraseQuerycontent.setSlop(5);
//					phraseQuerycontent.setBoost(2000);
//					PhraseQuery phraseQuerytitle =new PhraseQuery();
//					phraseQuerytitle.setSlop(5);
//					phraseQuerytitle.setBoost(1000);
//					for(int i=0;i<l.size();i++){
//						phraseQuerycontent.add(new Term("text", l.get(i)));
//						phraseQuerytitle.add(new Term("crawlTitle", l.get(i)));
//					}
//					boolQuery.add(phraseQuerytitle, BooleanClause.Occur.SHOULD);
//					boolQuery.add(phraseQuerycontent, BooleanClause.Occur.SHOULD);
//					String[] fieldName = { "crawlTitle", "text" }; 
//					QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_46, fieldName, analyzer);
//					Query q2 = queryParser.parse(record);
//					boolQuery.add(q2, BooleanClause.Occur.MUST);
				}else if(l.size()>1){
					BooleanQuery bool = new BooleanQuery();
					PhraseQuery phraseQuerycontent =new PhraseQuery();
					phraseQuerycontent.setSlop(25);
					phraseQuerycontent.setBoost(1000);
					PhraseQuery phraseQuerytitle =new PhraseQuery();
					phraseQuerytitle.setSlop(25);
					phraseQuerytitle.setBoost(1000);
					for(int i=0;i<l.size();i++){
					    //单个的字加上有时搜不到内容
//					    if(l.get(i).length()>1){
					    	phraseQuerycontent.add(new Term("text", l.get(i)));
							phraseQuerytitle.add(new Term("crawlTitle", l.get(i)));
//					    }
						
					}
					bool.add(phraseQuerytitle, BooleanClause.Occur.SHOULD);
					bool.add(phraseQuerycontent, BooleanClause.Occur.SHOULD);
					
					boolQuery.add(bool, BooleanClause.Occur.MUST);
				}else if(l.size()==1){
					String[] fieldName = { "crawlTitle", "text" }; 
					if(words==null){
						QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_46, fieldName, analyzer);
						Query q2 = queryParser.parse(record);
						boolQuery.add(q2, BooleanClause.Occur.MUST);
					}else{
						BooleanQuery bool = new BooleanQuery();
						for(int i=0;i<words.length;i++){
							QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_46, fieldName, analyzer);
							Query q2 = queryParser.parse(words[i]);
							bool.add(q2, BooleanClause.Occur.SHOULD);
						}
						boolQuery.add(bool, BooleanClause.Occur.MUST);
					}
				}
				Date dt = new Date();
	    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	    		String currydate= df.format(dt);
	    		Query tq  = NumericRangeQuery.newLongRange("newsTime",
	        			getdate(getOneDay(currydate,-30)).getTime(),getdate(getOneDay(currydate,1)).getTime(), true, false);
	    		
	    		boolQuery.add(tq, BooleanClause.Occur.MUST);
			}else{
				Date dt = new Date();
	    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	    		String currydate= df.format(dt);
	    		Query tq  = NumericRangeQuery.newLongRange("newsTime",
	        			getdate(getOneDay(currydate,-100)).getTime(),getdate(getOneDay(currydate,1)).getTime(), true, false);
	    		
	    		boolQuery.add(tq, BooleanClause.Occur.MUST);
	    		sort =new Sort(new SortField("newsTime",SortField.Type.LONG,true));
			}
		
		return new QueryWrapper(boolQuery, sort);
	}
	
	@Override
	public QueryWrapper getCondition(int userId,String[] musts,String[] arbitrarilys,String[] nots,String startdate,String enddate,int searchsource,int sorttype,String fromsource) throws Exception {
		BooleanQuery boolQuery = new BooleanQuery();
		
//		TermRangeQuery tq = null;
		
		Query tq = null;
        if(!startdate.equals("")&&!enddate.equals("")){
//			startdate=publicTools.getOneDay(startdate,-1);
//			
//			// 时间段查询
//			Term begin = new Term("crawl_time", startdate);
//			Term end = new Term("crawl_time", enddate);
//			tq = new TermRangeQuery("crawl_time", begin.bytes(), end.bytes(), false, true);
        	
        	tq = NumericRangeQuery.newLongRange("newsTime",
        			getdate(startdate).getTime(),getdate(getOneDay(enddate,1)).getTime(), true, false);
			
        }
        BooleanQuery queryfromsourceBooleanQuery = null;
        if(fromsource!=null&&!"".equals(fromsource)){
        	queryfromsourceBooleanQuery = new BooleanQuery();
        	String[] fromsources=fromsource.split(",");
        	for(int i=0;i<fromsources.length;i++){
        		TermQuery queryfromsource= new TermQuery(new Term("webName", fromsources[i]));
        		queryfromsourceBooleanQuery.add(queryfromsource,BooleanClause.Occur.SHOULD);
        	}
        	
        }
        	
        	if(arbitrarilys.length==0){
        		for(int i=0;i<musts.length;i++){
        			String m=musts[i].trim();
        			if(!m.equals("")){
        				BooleanQuery bool = new BooleanQuery();
        				PhraseQuery phraseQuerytitle =new PhraseQuery();
        				phraseQuerytitle.setSlop(0);
    					PhraseQuery phraseQuerycontent =new PhraseQuery();
    					phraseQuerycontent.setSlop(0);
        				IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(m), false);
        		        Lexeme lexeme;
        		        while ((lexeme = ikSegmenter.next()) != null) {
                			phraseQuerytitle.add(new Term("crawlTitle", lexeme.getLexemeText()));
                			phraseQuerycontent.add(new Term("text", lexeme.getLexemeText()));
        		        }
        		        bool.add(phraseQuerytitle,BooleanClause.Occur.SHOULD);
        		        if(searchsource==1){
            				bool.add(phraseQuerycontent,BooleanClause.Occur.SHOULD);
            			}
        		        boolQuery.add(bool, BooleanClause.Occur.MUST);
//            			BooleanQuery bool = new BooleanQuery();
            			
//            			TermQuery query1 = new TermQuery(new Term("crawlTitle", m));
//            			bool.add(query1,BooleanClause.Occur.SHOULD);
//            			
//            			if(searchsource==1){
//            				TermQuery query = new TermQuery(new Term("text", m));
//            				bool.add(query,BooleanClause.Occur.SHOULD);
//            			}
//            			
//            			boolQuery.add(bool, BooleanClause.Occur.MUST);
        			}
        			
        		}
        		for(int i=0;i<nots.length;i++){
        			String n=nots[i].trim();
        			if(!n.equals("")){
        				BooleanQuery bool = new BooleanQuery();
        				PhraseQuery phraseQuerytitle =new PhraseQuery();
        				phraseQuerytitle.setSlop(0);
    					PhraseQuery phraseQuerycontent =new PhraseQuery();
    					phraseQuerycontent.setSlop(0);
        				IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(n), false);
        		        Lexeme lexeme;
        		        while ((lexeme = ikSegmenter.next()) != null) {
                			phraseQuerytitle.add(new Term("crawlTitle", lexeme.getLexemeText()));
                			phraseQuerycontent.add(new Term("text", lexeme.getLexemeText()));
        		        }
        		        bool.add(phraseQuerytitle,BooleanClause.Occur.SHOULD);
        		        if(searchsource==1){
            				bool.add(phraseQuerycontent,BooleanClause.Occur.SHOULD);
            			}
        		        boolQuery.add(bool, BooleanClause.Occur.MUST_NOT);
//            			BooleanQuery bool = new BooleanQuery();
//            			TermQuery query1 = new TermQuery(new Term("crawlTitle", n));
//            			bool.add(query1,BooleanClause.Occur.SHOULD);
//            			if(searchsource==1){
//            				TermQuery query = new TermQuery(new Term("text", n));
//            				bool.add(query,BooleanClause.Occur.SHOULD);
//            			}
//            			boolQuery.add(bool, BooleanClause.Occur.MUST_NOT);
        			}
        		}
        		if(tq!=null)boolQuery.add(tq, BooleanClause.Occur.MUST);
        		if(queryfromsourceBooleanQuery!=null)boolQuery.add(queryfromsourceBooleanQuery,BooleanClause.Occur.MUST);
        	}else{
        		BooleanQuery bq = new BooleanQuery();
        		for(int j=0;j<arbitrarilys.length;j++){
        			String a=arbitrarilys[j].trim();
        			if(!a.equals("")){
//        				BooleanQuery boolquery= new BooleanQuery();
//            			BooleanQuery boolq= new BooleanQuery();
//            			TermQuery q1 = new TermQuery(new Term("crawlTitle", a));
//            			boolq.add(q1,BooleanClause.Occur.SHOULD);
//            			if(searchsource==1){
//            				TermQuery q = new TermQuery(new Term("text", a));
//            				boolq.add(q,BooleanClause.Occur.SHOULD);
//            			}
//            			boolquery.add(boolq, BooleanClause.Occur.MUST);
        				BooleanQuery boolquery= new BooleanQuery();
        				BooleanQuery boolq = new BooleanQuery();
        				PhraseQuery phraseQuerytitle1 =new PhraseQuery();
        				phraseQuerytitle1.setSlop(0);
    					PhraseQuery phraseQuerycontent1 =new PhraseQuery();
    					phraseQuerycontent1.setSlop(0);
        				IKSegmenter ikSegmenter1 = new IKSegmenter(new StringReader(a), false);
        		        Lexeme lexeme1;
        		        while ((lexeme1 = ikSegmenter1.next()) != null) {
                			phraseQuerytitle1.add(new Term("crawlTitle", lexeme1.getLexemeText()));
                			phraseQuerycontent1.add(new Term("text", lexeme1.getLexemeText()));
        		        }
        		        boolq.add(phraseQuerytitle1,BooleanClause.Occur.SHOULD);
        		        if(searchsource==1){
        		        	boolq.add(phraseQuerycontent1,BooleanClause.Occur.SHOULD);
            			}
        		        boolquery.add(boolq, BooleanClause.Occur.MUST);
        		        
            			for(int i=0;i<musts.length;i++){
            				String m=musts[i].trim();
                			if(!m.equals("")){
//        	        			BooleanQuery bool = new BooleanQuery();
//        	        			TermQuery query1 = new TermQuery(new Term("crawlTitle", m));
//        	        			bool.add(query1,BooleanClause.Occur.SHOULD);
//        	        			if(searchsource==1){
//        	        				TermQuery query = new TermQuery(new Term("text", m));
//        	        				bool.add(query,BooleanClause.Occur.SHOULD);
//        	        			}
//        	        			boolquery.add(bool, BooleanClause.Occur.MUST);
                				BooleanQuery bool = new BooleanQuery();
                				PhraseQuery phraseQuerytitle =new PhraseQuery();
                				phraseQuerytitle.setSlop(0);
            					PhraseQuery phraseQuerycontent =new PhraseQuery();
            					phraseQuerycontent.setSlop(0);
                				IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(m), false);
                		        Lexeme lexeme;
                		        while ((lexeme = ikSegmenter.next()) != null) {
                        			phraseQuerytitle.add(new Term("crawlTitle", lexeme.getLexemeText()));
                        			phraseQuerycontent.add(new Term("text", lexeme.getLexemeText()));
                		        }
                		        bool.add(phraseQuerytitle,BooleanClause.Occur.SHOULD);
                		        if(searchsource==1){
                    				bool.add(phraseQuerycontent,BooleanClause.Occur.SHOULD);
                    			}
                		        boolquery.add(bool, BooleanClause.Occur.MUST);
                			}
    	        			
    	        		}
    	        		for(int i=0;i<nots.length;i++){
    	        			String n=nots[i].trim();
    	        			if(!n.equals("")){
//    		        			BooleanQuery bool = new BooleanQuery();
//    		        			TermQuery query1 = new TermQuery(new Term("crawlTitle", n));
//    		        			bool.add(query1,BooleanClause.Occur.SHOULD);
//    		        			if(searchsource==1){
//    		        				TermQuery query = new TermQuery(new Term("text", n));
//    		        				bool.add(query,BooleanClause.Occur.SHOULD);
//    		        			}
//    		        			boolquery.add(bool, BooleanClause.Occur.MUST_NOT);
    	        				BooleanQuery bool = new BooleanQuery();
    	        				PhraseQuery phraseQuerytitle =new PhraseQuery();
    	        				phraseQuerytitle.setSlop(0);
    	    					PhraseQuery phraseQuerycontent =new PhraseQuery();
    	    					phraseQuerycontent.setSlop(0);
    	        				IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(n), false);
    	        		        Lexeme lexeme;
    	        		        while ((lexeme = ikSegmenter.next()) != null) {
    	                			phraseQuerytitle.add(new Term("crawlTitle", lexeme.getLexemeText()));
    	                			phraseQuerycontent.add(new Term("text", lexeme.getLexemeText()));
    	        		        }
    	        		        bool.add(phraseQuerytitle,BooleanClause.Occur.SHOULD);
    	        		        if(searchsource==1){
    	            				bool.add(phraseQuerycontent,BooleanClause.Occur.SHOULD);
    	            			}
    	        		        boolquery.add(bool, BooleanClause.Occur.MUST_NOT);
    	        		        
    	        			}
    	        			
    	        		}
    	        		bq.add(boolquery, BooleanClause.Occur.SHOULD);
        			}
        			
        		}
        		if(tq!=null)boolQuery.add(tq, BooleanClause.Occur.MUST);
        		if(queryfromsourceBooleanQuery!=null)boolQuery.add(queryfromsourceBooleanQuery,BooleanClause.Occur.MUST);
        		boolQuery.add(bq, BooleanClause.Occur.MUST);
        	}
        Sort sort =null;
        if(sorttype==1)sort =new Sort(new SortField("newsTime",SortField.Type.LONG,true));
		return new QueryWrapper(boolQuery, sort);
	}
	@Override
	public QueryWrapper getCondition(MoreLikeThisConfig mltc,IndexReader ir, String text,int sort) throws Exception{
		BooleanQuery boolQuery = new BooleanQuery();
		//分词器
		Analyzer analyzer = new IKAnalyzer(true);
		MoreLikeThis mlt = new MoreLikeThis(ir);
		mlt.setAnalyzer(analyzer);
		mlt.setMinTermFreq(mltc.getMinTermFreq()); // 词频至少出现次数
		mlt.setMinDocFreq(mltc.getMinDocFreq()); // 文档中至少出现次数
		mlt.setMinWordLen(mltc.getMinWordLen()); // 最小长度为几
		mlt.setFieldNames(new String[] { "text" }); //用于计算的字段
		
		Query query = mlt.like(new StringReader(text), "text");
		Sort s =null;
		boolQuery.add(query, BooleanClause.Occur.MUST);
        if(sort==2){
        	s =new Sort(new SortField("newsTime",SortField.Type.LONG,true));
        }else{
        	Date dt = new Date();
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    		String currydate= df.format(dt);
    		Query tq  = NumericRangeQuery.newLongRange("newsTime",
        			getdate(getOneDay(currydate,-2)).getTime(),getdate(getOneDay(currydate,1)).getTime(), true, false);
    		boolQuery.add(tq, BooleanClause.Occur.MUST);
        }
		return new QueryWrapper(boolQuery, s);
	}
	@Override
	public String getClassname() throws Exception {
		return "crawl";
	}
}
