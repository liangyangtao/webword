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
import com.lucene.entity.doc.Crawl;
import com.lucene.entity.doc.Crawls;
import com.lucene.entity.doc.Plugin;
import com.lucene.entity.doc.Plugins;
import com.lucene.html.Function;


/**
 * 
 * @author Quzile
 * 
 */
public class PluginWriter  extends AbstractUnbankWriter<Plugin> {
	public PluginWriter(File file, Analyzer analyzer) {
		super(file, analyzer);
//		FieldType fieldType = new FieldType();
//		fieldType.setIndexed(true);
//		fieldType.setTokenized(true);
//		fieldType.setStoreTermVectors(true);
//		fieldType.setStoreTermVectorOffsets(true);
//		text = new Field("text", "", fieldType);
	}

	@Override
	public Document newDocument(Plugin t) {
		Document doc = new Document();
		Field pluginid = new StringField("pluginid", "",Field.Store.YES);
		Field pluginname = new TextField("pluginname", "", Field.Store.YES);
		Field pluginintro = new TextField("pluginintro", "", Field.Store.YES);
		Field userid = new IntField("userid", 0, Field.Store.YES);
		Field pluginindex = new TextField("pluginindex", "", Field.Store.YES);
		Field pluginparams = new TextField("pluginparams", "", Field.Store.YES);
		Field pluginnote = new TextField("pluginnote", "", Field.Store.YES);
		Field inserttime = new LongField("inserttime", 0L, Field.Store.YES);
		Field dataclass = new IntField("dataclass", 0, Field.Store.YES);
		pluginid.setStringValue(String.valueOf(t.getPluginid()));
		pluginname.setStringValue(t.getPluginname());
		pluginintro.setStringValue(t.getPluginnote());
		userid.setIntValue(t.getUserid());
		pluginindex.setStringValue(t.getPluginindex());
		pluginparams.setStringValue(t.getPluginparams());
		pluginnote.setStringValue(t.getPluginnote());
		inserttime.setLongValue(t.getInserttime());
		dataclass.setIntValue(t.getDataclass());

		doc.add(pluginid);

		doc.add(pluginname);
		doc.add(pluginintro);
		doc.add(userid);

		doc.add(pluginindex);
		doc.add(pluginparams);

		doc.add(pluginnote);
		doc.add(inserttime);
		doc.add(dataclass);
		
		return doc;
	}

	@Override
	public Plugin conv(Query query, Document doc, int docId) throws Exception {
		Plugin o = new Plugin();
		o.setPluginid(Integer.parseInt(doc.get("pluginid")));

		o.setPluginname(doc.get("pluginname"));
		o.setPluginintro(doc.get("pluginintro"));

		o.setUserid(Integer.parseInt(doc.get("userid")));
		o.setPluginindex(doc.get("pluginindex"));

		o.setPluginparams(doc.get("pluginparams"));
		o.setPluginnote(doc.get("pluginnote"));
		o.setInserttime(Long.parseLong(doc.get("inserttime")));
        o.setDataclass(Integer.parseInt(doc.get("dataclass")));
		
		return o;
	}
	@Override
	public Plugin conv(String record,List<String> l,String[] words, Document doc) throws Exception {
		int pluginid=Integer.parseInt(doc.get("pluginid"));
		String pluginname=doc.get("pluginname");
		String pluginintro=doc.get("pluginintro");
		int userid=Integer.parseInt(doc.get("userid"));
		String pluginindex=doc.get("pluginindex");
		String pluginparams=doc.get("pluginparams");
		String pluginnote=doc.get("pluginnote");
		long inserttime=Long.parseLong(doc.get("inserttime"));
		int dataclass=Integer.parseInt(doc.get("dataclass"));
		if(!record.trim().equals("")){
			//分词多个词时加组合条件
			if(l.size()>1){
				for(int j=0;j<l.size();j++){
					String key=l.get(j)+"";
					pluginname=pluginname.replaceAll(key+"", "<span>"+key+"</span>");
					pluginintro=pluginintro.replaceAll(key+"", "<span>"+key+"</span>");
					pluginnote=pluginnote.replaceAll(key+"", "<span>"+key+"</span>");
							
					if(key.matches(".*[a-z]+.*")){
						key=key.toUpperCase();  
						pluginname=pluginname.replaceAll(key+"", "<span>"+key+"</span>");
						pluginintro=pluginintro.replaceAll(key+"", "<span>"+key+"</span>");
						pluginnote=pluginnote.replaceAll(key+"", "<span>"+key+"</span>");
					}
				}
			}else{
				if(words==null){
					pluginname=pluginname.replaceAll(record+"", "<span>"+record+"</span>");
					pluginintro=pluginintro.replaceAll(record+"", "<span>"+record+"</span>");
					pluginnote=pluginnote.replaceAll(record+"", "<span>"+record+"</span>");
					
					if(record.matches(".*[a-z]+.*")){
						record=record.toUpperCase();  
						pluginname=pluginname.replaceAll(record+"", "<span>"+record+"</span>");
						pluginintro=pluginintro.replaceAll(record+"", "<span>"+record+"</span>");
						pluginnote=pluginnote.replaceAll(record+"", "<span>"+record+"</span>");
					}
				}else{
					for(int j=0;j<words.length;j++){
						String r=words[j];
						pluginname=pluginname.replaceAll(r+"", "<span>"+r+"</span>");
						pluginintro=pluginintro.replaceAll(r+"", "<span>"+r+"</span>");
						pluginnote=pluginnote.replaceAll(record+"", "<span>"+record+"</span>");
						
						if(r.matches(".*[a-z]+.*")){
							r=r.toUpperCase();  
							pluginname=pluginname.replaceAll(r+"", "<span>"+r+"</span>");
							pluginintro=pluginintro.replaceAll(r+"", "<span>"+r+"</span>");
							pluginnote=pluginnote.replaceAll(record+"", "<span>"+record+"</span>");
						}
					}
					
				}
				
				
			}
		}

		Plugin o = new Plugin();
		o.setPluginid(pluginid);

		o.setPluginname(pluginname);
		o.setPluginintro(pluginintro);

		o.setUserid(userid);
		o.setPluginindex(pluginindex);

		o.setPluginparams(pluginparams);
		o.setPluginnote(pluginnote);
		o.setInserttime(inserttime);
		o.setDataclass(dataclass);

		return o;
	}
	@Override
	public Plugin conv(int type, String[] musts,String[] arbitrarilys,String[] nots,Document doc) throws Exception {
		return null;
	}
	@Override
	protected Term findUnique(Document doc) {
		return new Term("pluginid", doc.get("pluginid"));
	}

	@Override
	protected UnbankList<Plugin> initList(int totalHits) {
		return new Plugins(totalHits);
	}

	@Override
	protected List<Plugin> findIndexes(UnbankDAO unbankDAO, Object... args) {
		String sql = "SELECT * FROM know_plugin WHERE file_index = 2 and insert_time < ? LIMIT ?, ?";
		List<Plugin> list = unbankDAO.findBySql(sql, new RowMapper<Plugin>() {
			@Override
			public Plugin mapRow(ResultSet rs, int rowNum) throws SQLException {
				Plugin o = new Plugin();
				o.setPluginid(rs.getInt("plugin_id"));
				o.setPluginname(rs.getString("plugin_name"));
				o.setPluginintro(rs.getString("plugin_intro"));
				o.setUserid(rs.getInt("user_id"));
				o.setPluginindex(rs.getString("plugin_index"));
				o.setPluginparams(rs.getString("plugin_params"));
				o.setPluginnote(rs.getString("plugin_note"));
				o.setInserttime(rs.getTimestamp("insert_time").getTime());
				o.setDataclass(0);
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
		String sql = "UPDATE know_plugin SET file_index = ? WHERE file_index = ? AND insert_time < ?";
		return unbankDAO.update(sql, newValue, oldValue, datetime);
	}

	@Override
	protected Term findContent(int pluginid) {
		return new Term("pluginid",String.valueOf(pluginid));
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
				BooleanQuery bool1 = new BooleanQuery();
				bool1.setBoost(5);
				BooleanQuery bool2 = new BooleanQuery();
				bool2.setBoost(5);
				BooleanQuery bool3 = new BooleanQuery();
				bool3.setBoost(5);
				PhraseQuery phraseQuerypluginname =new PhraseQuery();
				phraseQuerypluginname.setBoost(10);
				phraseQuerypluginname.setSlop(25);
				PhraseQuery phraseQuerypluginintro =new PhraseQuery();
				phraseQuerypluginintro.setBoost(10);
				phraseQuerypluginintro.setSlop(25);
				PhraseQuery phraseQuerypluginnote =new PhraseQuery();
				phraseQuerypluginnote.setBoost(10);
				phraseQuerypluginnote.setSlop(25);
				for(int i=0;i<l.size();i++){
					phraseQuerypluginname.add(new Term("pluginname", l.get(i)));
					phraseQuerypluginintro.add(new Term("pluginintro", l.get(i)));
					phraseQuerypluginnote.add(new Term("pluginnote", l.get(i)));
//					if(l.size()>=5){
//						TermQuery query1 = new TermQuery(new Term("pluginname", l.get(i)));
//	        			TermQuery query2 = new TermQuery(new Term("pluginintro", l.get(i)));
//	        			TermQuery query3 = new TermQuery(new Term("pluginnote", l.get(i)));
//						bool1.add(query1, BooleanClause.Occur.MUST);
//						bool2.add(query2, BooleanClause.Occur.MUST);
//						bool3.add(query3, BooleanClause.Occur.MUST);
//					}
					
				}
				bool.add(phraseQuerypluginintro, BooleanClause.Occur.SHOULD);
				bool.add(phraseQuerypluginname, BooleanClause.Occur.SHOULD);
				bool.add(phraseQuerypluginnote, BooleanClause.Occur.SHOULD);
//				if(l.size()>=5){
//					boolQuery.add(bool1, BooleanClause.Occur.SHOULD);
//					boolQuery.add(bool2, BooleanClause.Occur.SHOULD);
//					boolQuery.add(bool3, BooleanClause.Occur.SHOULD);
//				}
				
				boolQuery.add(bool, BooleanClause.Occur.MUST);
//				String[] fieldName = { "pluginname", "pluginintro","pluginnote" }; 
//				QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_46, fieldName, analyzer);
//				Query q2 = queryParser.parse(record);
//				boolQuery.add(q2, BooleanClause.Occur.MUST);
			}else if(l.size()==1){
				String[] fieldName = { "pluginname", "pluginintro","pluginnote" }; 
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
		}else{
			Date dt = new Date();
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    		String currydate= df.format(dt);
    		Query tq  = NumericRangeQuery.newLongRange("inserttime",
        			getdate(getOneDay(currydate,-36500)).getTime(),getdate(getOneDay(currydate,1)).getTime(), true, false);
    		boolQuery.add(tq, BooleanClause.Occur.MUST);
    		sort =new Sort(new SortField("inserttime",SortField.Type.LONG,true));
		}
		return new QueryWrapper(boolQuery, sort);
	}
	
	@Override
	public QueryWrapper getCondition(int userId,String[] musts,String[] arbitrarilys,String[] nots,String startdate,String enddate,int searchsource,int sorttype,String fromsource) throws Exception {
		
		return null;
	}
	@Override
	public String getClassname() throws Exception {
		return "plugin";
	}
}
