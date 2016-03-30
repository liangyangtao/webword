package com.lucene;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import org.apache.lucene.index.Term;
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
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.lucene.dao.UnbankDAO;
import com.lucene.entity.Searchparam;
import com.lucene.entity.doc.Articlel;
import com.lucene.entity.doc.Articlels;
import com.lucene.html.Function;


/**
 * 
 * @author Quzile
 * 
 */
public class ArticlelWriter  extends AbstractUnbankWriter<Articlel> {
	
	public ArticlelWriter(File file, Analyzer analyzer) {
		super(file, analyzer);
	}

	@Override
	public Document newDocument(Articlel t) {
		Field articleid = new StringField("articleid", String.valueOf(""),Field.Store.YES);
		Field articlename = new TextField("articlename", "", Field.Store.YES);
		Field nodecontents = new TextField("nodecontents", "", Field.Store.YES);
		Field articleskip = new TextField("articleskip", "", Field.Store.YES);
		Field keyword = new TextField("keyword", "", Field.Store.YES);
		Field platename = new TextField("platename", "", Field.Store.YES);
		Field plateid = new IntField("plateid", 0, Field.Store.YES);
		Field articletype = new StringField("articletype", String.valueOf(""),Field.Store.YES);
		Field passtime = new LongField("passtime", 0L, Field.Store.YES);
		Document doc = new Document();
		articleid.setStringValue(String.valueOf(t.getArticleid()));
		articlename.setStringValue(t.getArticlename()==null?"":t.getArticlename());
		nodecontents.setStringValue(t.getNodecontents()==null?"":t.getNodecontents());
		articleskip.setStringValue(t.getArticleskip()==null?"":t.getArticleskip());
		keyword.setStringValue(t.getKeyword()==null?"":t.getKeyword());
		platename.setStringValue(t.getPlatename()==null?"":t.getPlatename());
		plateid.setIntValue(t.getPlateid());
		articletype.setStringValue(t.getArticletype()==null?"":t.getArticletype());
		passtime.setLongValue(t.getPasstime());
		
		doc.add(articleid);
		doc.add(articlename);
		doc.add(nodecontents);
		doc.add(articleskip);
		doc.add(keyword);
		doc.add(platename);
		doc.add(plateid);
		doc.add(articletype);
		doc.add(passtime);
		
		return doc;
	}

	@Override
	public Articlel conv(Query query, Document doc, int docId) throws Exception {
		Articlel o = new Articlel();
		o.setArticleid(Integer.parseInt(doc.get("articleid")));
		o.setArticlename(doc.get("articlename"));
		o.setNodecontents(doc.get("nodecontents"));
		o.setArticleskip(doc.get("articleskip"));
		o.setKeyword(doc.get("keyword"));
		o.setPlatename(doc.get("platename"));
		o.setArticleid(Integer.parseInt(doc.get("plateid")));
		o.setArticletype(doc.get("articletype"));
		o.setPasstime(Long.parseLong(doc.get("passtime")));
		return o;
	}
	@Override
	public Articlel conv(String record,List<String> l,String[] words, Document doc) throws Exception {
		String articleid=doc.get("articleid");
		String articlename=doc.get("articlename");
		String nodecontents=doc.get("nodecontents");
		String articleskip=doc.get("articleskip");
		String keyword=doc.get("keyword");
		String platename=doc.get("platename");
		String plateid=doc.get("plateid");
		String articletype=doc.get("articletype");
		long passtime=Long.parseLong(doc.get("passtime"));
		if(!record.trim().equals("")){
			//分词多个词时加组合条件
			if(l.size()>1){
				for(int j=0;j<l.size();j++){
					String key=l.get(j)+"";
					articlename=articlename.replaceAll(key+"", "<span>"+key+"</span>");
							
					if(key.matches(".*[a-z]+.*")){
						key=key.toUpperCase();  
						articlename=articlename.replaceAll(key+"", "<span>"+key+"</span>");
					}
				}
			}else{
				if(words==null){
					articlename=articlename.replaceAll(record+"", "<span>"+record+"</span>");
					
					if(record.matches(".*[a-z]+.*")){
						record=record.toUpperCase();  
						articlename=articlename.replaceAll(record+"", "<span>"+record+"</span>");
					}
				}else{
					for(int j=0;j<words.length;j++){
						String r=words[j];
						articlename=articlename.replaceAll(r+"", "<span>"+r+"</span>");
						
						if(r.matches(".*[a-z]+.*")){
							r=r.toUpperCase();  
							articlename=articlename.replaceAll(r+"", "<span>"+r+"</span>");
						}
					}
					
				}
				
				
			}
		}
		
		Articlel o = new Articlel();
		o.setArticleid(Integer.parseInt(articleid));
		o.setArticlename(articlename);
		o.setNodecontents(nodecontents);
		o.setArticleskip(articleskip);
		o.setKeyword(keyword);
		o.setPlatename(platename);
		o.setPlateid(Integer.parseInt(plateid));
		o.setArticletype(articletype);
		o.setPasstime(passtime);

		return o;
	}
	@Override
	public Articlel conv(int type, String[] musts,String[] arbitrarilys,String[] nots,Document doc) throws Exception {
		
		return null;
	}
	@Override
	protected Term findUnique(Document doc) {
		return new Term("articleid", doc.get("articleid"));
	}

	@Override
	protected UnbankList<Articlel> initList(int totalHits) {
		return new Articlels(totalHits);
	}

	@Override
	protected List<Articlel> findIndexes(UnbankDAO unbankDAO, Object... args) {
		
		return null;
	}

	@Override
	protected int update(UnbankDAO unbankDAO, int oldValue, int newValue,
			String datetime) {
		return 0;
	}

	@Override
	protected Term findContent(int articleid) {
		return new Term("articleid",String.valueOf(articleid));
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
			if(l.size()>1){
				BooleanQuery bool = new BooleanQuery();
//				BooleanQuery bool1 = new BooleanQuery();
//				bool1.setBoost(5);
//				BooleanQuery bool2 = new BooleanQuery();
//				bool2.setBoost(5);
//				BooleanQuery bool3 = new BooleanQuery();
//				bool3.setBoost(5);
//				BooleanQuery bool4 = new BooleanQuery();
//				bool4.setBoost(5);
//				BooleanQuery bool5 = new BooleanQuery();
//				bool5.setBoost(5);
				
//				PhraseQuery phraseQueryplatename =new PhraseQuery();
//				phraseQueryplatename.setBoost(10);
//				phraseQueryplatename.setSlop(25);
//				PhraseQuery phraseQuerynodecontents =new PhraseQuery();
//				phraseQueryplatename.setBoost(10);
//				phraseQuerynodecontents.setSlop(25);
				PhraseQuery phraseQueryarticlename =new PhraseQuery();
				phraseQueryarticlename.setBoost(10);
				phraseQueryarticlename.setSlop(25);
				PhraseQuery phraseQueryarticleskip =new PhraseQuery();
				phraseQueryarticleskip.setBoost(10);
				phraseQueryarticleskip.setSlop(25);
				PhraseQuery phraseQuerykeyword =new PhraseQuery();
				phraseQuerykeyword.setBoost(10);
				phraseQuerykeyword.setSlop(25);
				for(int i=0;i<l.size();i++){
//					phraseQueryplatename.add(new Term("platename", l.get(i)));
//					phraseQuerynodecontents.add(new Term("nodecontents", l.get(i)));
					phraseQueryarticlename.add(new Term("articlename", l.get(i)));
					phraseQueryarticleskip.add(new Term("articleskip", l.get(i)));
					phraseQuerykeyword.add(new Term("keyword", l.get(i)));
//					if(l.size()>=5){
//						TermQuery query1 = new TermQuery(new Term("platename", l.get(i)));
//	        			TermQuery query2 = new TermQuery(new Term("nodecontents", l.get(i)));
//	        			TermQuery query3 = new TermQuery(new Term("articlename", l.get(i)));
//	        			TermQuery query4 = new TermQuery(new Term("articleskip", l.get(i)));
//	        			TermQuery query5 = new TermQuery(new Term("keyword", l.get(i)));
//						bool1.add(query1, BooleanClause.Occur.MUST);
//						bool2.add(query2, BooleanClause.Occur.MUST);
//						bool3.add(query3, BooleanClause.Occur.MUST);
//						bool4.add(query4, BooleanClause.Occur.MUST);
//						bool5.add(query5, BooleanClause.Occur.MUST);
//					}
				}
//				bool.add(phraseQueryplatename, BooleanClause.Occur.SHOULD);
//				bool.add(phraseQuerynodecontents, BooleanClause.Occur.SHOULD);
				bool.add(phraseQueryarticlename, BooleanClause.Occur.SHOULD);
				bool.add(phraseQueryarticleskip, BooleanClause.Occur.SHOULD);
				bool.add(phraseQuerykeyword, BooleanClause.Occur.SHOULD);
//				if(l.size()>=5){
//					boolQuery.add(bool1, BooleanClause.Occur.SHOULD);
//					boolQuery.add(bool2, BooleanClause.Occur.SHOULD);
//					boolQuery.add(bool3, BooleanClause.Occur.SHOULD);
//					boolQuery.add(bool4, BooleanClause.Occur.SHOULD);
//					boolQuery.add(bool5, BooleanClause.Occur.SHOULD);
//				}
				
				boolQuery.add(bool, BooleanClause.Occur.MUST);
//				String[] fieldName = { "platename", "nodecontents","articlename","articleskip","keyword" }; 
//				QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_46, fieldName, analyzer);
//				Query q2 = queryParser.parse(record);
//				boolQuery.add(q2, BooleanClause.Occur.MUST);
			}else if(l.size()==1){
//				String[] fieldName = { "nodecontents", "articlename", "articleskip", "keyword", "platename" }; 
				String[] fieldName = { "articlename", "articleskip", "keyword"}; 
				if(words==null){
					QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_46, fieldName, analyzer);
					Query q2 = queryParser.parse(record);
					boolQuery.add(q2, BooleanClause.Occur.MUST);
				}else{
					for(int i=0;i<words.length;i++){
						QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_46, fieldName, analyzer);
						Query q2 = queryParser.parse(words[i]);
						BooleanQuery bool = new BooleanQuery();
						bool.add(q2, BooleanClause.Occur.SHOULD);
						boolQuery.add(bool, BooleanClause.Occur.MUST);
					}
					
				}
			}
		}
		boolQuery.add(new TermQuery(new Term("articletype",searchparam.getArticletype())), BooleanClause.Occur.MUST);
		return new QueryWrapper(boolQuery, sort);
	}
	
	@Override
	public QueryWrapper getCondition(int userId,String[] musts,String[] arbitrarilys,String[] nots,String startdate,String enddate,int searchsource,int sorttype,String fromsource) throws Exception {
		return  null;
	}
	@Override
	public String getClassname() throws Exception {
		return "articlel";
	}
}
