package com.lucene;

import java.io.File;
import java.io.StringReader;
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
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.lucene.dao.UnbankDAO;
import com.lucene.entity.Searchparam;
import com.lucene.entity.doc.Content;
import com.lucene.entity.doc.Contents;
import com.lucene.entity.doc.Crawl;
import com.lucene.html.Function;


/**
 * Expert: change the value of this field. This can be used during indexing to
 * re-use a single Field instance to improve indexing speed by avoiding GC cost
 * of new'ing and reclaiming Field instances. Typically a single Document
 * instance is re-used as well. This helps most on small documents.
 * 
 * @author Quzile
 * 
 */
public class DocWriter extends
		AbstractUnbankWriter<com.lucene.entity.doc.Content> {

	
	public DocWriter(File file, Analyzer analyzer) {
		super(file, analyzer);
//		FieldType fieldType = new FieldType();
//		fieldType.setIndexed(true);
//		fieldType.setTokenized(true);
//		fieldType.setStoreTermVectors(true);
//		fieldType.setStoreTermVectorOffsets(true);
//		text = new Field("text", "", fieldType);
	}

	@Override
	public Document newDocument(com.lucene.entity.doc.Content t) {
		Document doc = new Document();
		Field contentId = new StringField("contentId", "", Field.Store.YES);
		Field text= new TextField("text", "", Field.Store.YES);
		Field contentName = new TextField("contentName", "", Field.Store.YES);
		Field contentBrief = new TextField("contentBrief", "", Field.Store.YES);
		Field userId = new IntField("userId", 0, Field.Store.YES);
		Field nodeId = new IntField("nodeId", 0, Field.Store.YES);
		Field contentInsertTime = new LongField("newsTime", 0L, Field.Store.YES);
		if (t.getText() == null) {
			return null;
		}
		System.out.println("-------------"+t.getContentInsertTime());
		contentId.setStringValue(String.valueOf(t.getContentId()));
		contentName.setStringValue(t.getContentName());
		contentBrief.setStringValue(t.getContentBrief());
		text.setStringValue(t.getText());
		userId.setIntValue(t.getUserId());
		contentInsertTime.setLongValue(t.getContentInsertTime());
		nodeId.setIntValue(t.getNodeId());

		//

		doc.add(contentId);

		doc.add(contentName);
		doc.add(contentBrief);
		doc.add(text);

		doc.add(userId);
		doc.add(contentInsertTime);
		doc.add(nodeId);

		return doc;
	}

	@Override
	public com.lucene.entity.doc.Content conv(Query query,
			Document doc, int contentId) throws Exception {
		com.lucene.entity.doc.Content o = new com.lucene.entity.doc.Content();
		o.setContentId(Integer.valueOf(doc.get("contentId")));

		if (query != null) {
			o.setContentName(highlight(query, contentId, "contentName",
					doc.get("contentName")));
			o.setContentBrief(highlight(query, contentId, "contentBrief",
					doc.get("contentBrief")));
		}
		
//		o.setCname(doc.get("cname"));
//		o.setTemplateName(doc.get("templateName"));

		o.setUserId(Integer.valueOf(doc.get("userId")));
//		o.setTemplateId(Integer.valueOf(doc.get("templateId")));
//		o.setStructureId(Integer.valueOf(doc.get("strucutureId")));
		o.setContentInsertTime(Long.valueOf(doc.get("newsTime")));
		
		o.setNodeId(Integer.valueOf(doc.get("nodeId")));

		// 正向
		keywords(contentId, "text", o.getKeywords());

		return o;
	}
	@Override
	public Content conv(String record,List<String> l,String[] words,Document doc) throws Exception {
		String contentName=doc.get("contentName");
		String text=doc.get("text");
		String contentId=doc.get("contentId");
		String contentBrief=doc.get("contentBrief");
		String userId=doc.get("userId");
		String contentInsertTime=doc.get("newsTime");
		String nodeId=doc.get("nodeId");
		if(!record.trim().equals("")){
			//分词多个词时加组合条件
			if(l.size()>1){
				for(int j=0;j<l.size();j++){
					String key=l.get(j)+"";
					contentName=contentName.replaceAll(key+"", "<span>"+key+"</span>");
					text=text.replaceAll(key+"", "<span>"+key+"</span>");
							
					if(key.matches(".*[a-z]+.*")){
						key=key.toUpperCase();  
						contentName=contentName.replaceAll(key+"", "<span>"+key+"</span>");
						text=text.replaceAll(key+"", "<span>"+key+"</span>");
					}
				}
			}else{
				if(words==null){
					contentName=contentName.replaceAll(record+"", "<span>"+record+"</span>");
					text=text.replaceAll(record+"", "<span>"+record+"</span>");
					
					if(record.matches(".*[a-z]+.*")){
						record=record.toUpperCase();  
						contentName=contentName.replaceAll(record+"", "<span>"+record+"</span>");
						text=text.replaceAll(record+"", "<span>"+record+"</span>");
					}
				}else{
					for(int j=0;j<words.length;j++){
						String r=words[j];
						contentName=contentName.replaceAll(r+"", "<span>"+r+"</span>");
						text=text.replaceAll(r+"", "<span>"+r+"</span>");
						
						if(r.matches(".*[a-z]+.*")){
							r=r.toUpperCase();  
							contentName=contentName.replaceAll(r+"", "<span>"+r+"</span>");
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
		Content o=new Content();
		o.setContentId(Integer.valueOf(contentId));

		o.setContentName(contentName);
		o.setContentBrief(contentBrief);

		o.setUserId(Integer.valueOf(userId));
		o.setContentInsertTime(Long.valueOf(contentInsertTime));
		
		o.setNodeId(Integer.valueOf(nodeId));
		o.setText(sb.toString());
		
		return o;
	}
	@Override
	public Content conv(int type,String[] musts,String[] arbitrarilys,String[] nots,Document doc) throws Exception {
		String contentName=doc.get("contentName");
		String text=doc.get("text");
		String contentId=doc.get("contentId");
		String contentBrief=doc.get("contentBrief");
		String userId=doc.get("userId");
		String contentInsertTime=doc.get("newsTime");
		String nodeId=doc.get("nodeId");
		
		for(int j=0;j<musts.length;j++){
    		String key=musts[j];
    		contentName=contentName.replaceAll(key+"", "<span>"+key+"</span>");
    		text=text.replaceAll(key+"", "<span>"+key+"</span>");
					
			if(key.matches(".*[a-z]+.*")){
				key=key.toUpperCase();  
				contentName=contentName.replaceAll(key+"", "<span>"+key+"</span>");
				text=text.replaceAll(key+"", "<span>"+key+"</span>");
			}
    	}
    	for(int j=0;j<arbitrarilys.length;j++){
    		String key=arbitrarilys[j];
    		contentName=contentName.replaceAll(key+"", "<span>"+key+"</span>");
    		text=text.replaceAll(key+"", "<span>"+key+"</span>");
					
			if(key.matches(".*[a-z]+.*")){
				key=key.toUpperCase();  
				contentName=contentName.replaceAll(key+"", "<span>"+key+"</span>");
				text=text.replaceAll(key+"", "<span>"+key+"</span>");
			}
    	}
	
	
	String[] ss=text.split("[!,.:;?！，。：；？]");//‘’“”'\"
	StringBuffer sb=new StringBuffer();
	for(int j=0;j<ss.length;j++){
		if(sb.length()+ss[j].length()>80)break;
		else sb.append(ss[j]+"/");
	}
		
		Content o=new Content();
		o.setContentId(Integer.valueOf(contentId));

		o.setContentName(contentName);
		o.setContentBrief(contentBrief);

		o.setUserId(Integer.valueOf(userId));
		o.setContentInsertTime(Long.valueOf(contentInsertTime));
		
		o.setNodeId(Integer.valueOf(nodeId));
		o.setText(sb.toString());
		
		return o;
	}
	@Override
	protected Term findUnique(Document doc) {
		return new Term("contentId", doc.get("contentId"));
	}
	
	@Override
	protected Term findContent(int contentId){
		return new Term("contentId",String.valueOf(contentId));
	}

	@Override
	protected UnbankList<com.lucene.entity.doc.Content> initList(
			int totalHits) {
		return new Contents(totalHits);
	}

	@Override
	protected List<com.lucene.entity.doc.Content> findIndexes(
			UnbankDAO unbankDAO, Object... args) {
		String sql = "SELECT a.content_id AS contentId, a.content_name AS contentName,  a.content AS text, a.user_id AS userId, a.insert_time AS contentInsertTime,b.node_id AS nodeId FROM know_content AS a LEFT JOIN know_node_content AS b ON a.content_id=b.content_id WHERE a.content_name IS NOT NULL AND  a.`file_index` = 2 AND a.insert_time < ? LIMIT ?,?";
//		String sql = "SELECT content_id AS contentId, content_name AS contentName,  content AS text, user_id AS userId, insert_time AS contentInsertTime FROM know_content WHERE  `file_index` = 2 AND insert_time < ? LIMIT ?,?";
		//LEFT(content,100) AS contentBrief,
		return unbankDAO.findBySql(sql,
				new RowMapper<com.lucene.entity.doc.Content>() {
					@Override
					public com.lucene.entity.doc.Content mapRow(
							ResultSet rs, int rowNum) throws SQLException {
						com.lucene.entity.doc.Content o = new com.lucene.entity.doc.Content();
						o.setContentId(rs.getInt("contentId"));
						o.setContentName(rs.getString("contentName"));
//						o.setContentBrief(rs.getString("contentBrief"));
						o.setContentInsertTime(rs.getTimestamp("contentInsertTime").getTime());
						o.setText(rs.getString("text"));
						o.setUserId(rs.getInt("userId"));
						o.setNodeId(rs.getInt("nodeId"));
						

						// o.setDocBrief(rs.getString("doc_brief"));

						String text = rs.getString("text");
						if (text != null) {
							String content = Function.removeCharacterEntity(text);
							content = Function.removeHTMLTag(content).trim();

							o.setText(content);

							int len = content.length();
							o.setContentBrief(content.substring(0,
									len > getLengthOfText() ? getLengthOfText()
											: len));
						}

						return o;
					}
				}, args);
	}

	@Override
	protected int update(UnbankDAO unbankDAO, int oldValue, int newValue,
			String datetime) {
//		String sql = "UPDATE `ptf_doc` SET `file_index` = ? WHERE `deny` = 0 AND `doc_type` = 'PASSED' AND `file_index` = ? AND `c_date` < ?";
		String sql = "UPDATE know_content SET file_index = ? WHERE pass_type IS NOT NULL AND pass_type !='PASSED' AND (file_index=? or file_index=1) AND insert_time<?";
		return unbankDAO.update(sql, newValue, oldValue, datetime);
	}

	
	@Override
	public QueryWrapper getCondition(Searchparam searchparam,String record, List<String> l,String[] words) throws Exception {
		//分词器
		Analyzer analyzer = new IKAnalyzer(true);
		//查询条件
		BooleanQuery boolQuery = new BooleanQuery();
		Sort sort =null;
		if(!record.equals("")){
			//分词多个词时加组合条件
			if(l.size()>1){
//				BooleanQuery bool = new BooleanQuery();
				BooleanQuery bool1 = new BooleanQuery();
				bool1.setBoost(5);
				BooleanQuery bool2 = new BooleanQuery();
				bool2.setBoost(5);
				PhraseQuery phraseQuerycontent =new PhraseQuery();
				phraseQuerycontent.setBoost(10);
				phraseQuerycontent.setSlop(25);
				PhraseQuery phraseQuerytitle =new PhraseQuery();
				phraseQuerytitle.setBoost(10);
				phraseQuerytitle.setSlop(25);
				for(int i=0;i<l.size();i++){
					phraseQuerycontent.add(new Term("text", l.get(i)));
					phraseQuerytitle.add(new Term("contentName", l.get(i)));
					if(l.size()>=5){
						TermQuery query1 = new TermQuery(new Term("text", l.get(i)));
	        			TermQuery query2 = new TermQuery(new Term("contentName", l.get(i)));
						bool1.add(query1, BooleanClause.Occur.MUST);
						bool2.add(query2, BooleanClause.Occur.MUST);
					}
				}
				boolQuery.add(phraseQuerytitle, BooleanClause.Occur.SHOULD);
				boolQuery.add(phraseQuerycontent, BooleanClause.Occur.SHOULD);
				
				if(l.size()>=5){
					boolQuery.add(bool1, BooleanClause.Occur.SHOULD);
					boolQuery.add(bool2, BooleanClause.Occur.SHOULD);
				}
				
//				boolQuery.add(bool, BooleanClause.Occur.MUST);
				String[] fieldName = { "contentName", "text" }; 
				QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_46, fieldName, analyzer);
				Query q2 = queryParser.parse(record);
				boolQuery.add(q2, BooleanClause.Occur.MUST);
				Query query = NumericRangeQuery.newIntRange("userId", searchparam.getUserId(), searchparam.getUserId(),
						true, true);
				boolQuery.add(query, BooleanClause.Occur.MUST);
//				PhraseQuery phraseQuerycontent =new PhraseQuery();
//				phraseQuerycontent.setSlop(5);
//				phraseQuerycontent.setBoost(2000);
//				PhraseQuery phraseQuerytitle =new PhraseQuery();
//				phraseQuerytitle.setSlop(5);
//				phraseQuerytitle.setBoost(1000);
//				for(int i=0;i<l.size();i++){
//					phraseQuerycontent.add(new Term("text", l.get(i)));
//					phraseQuerytitle.add(new Term("contentName", l.get(i)));
//				}
//				boolQuery.add(phraseQuerytitle, BooleanClause.Occur.SHOULD);
//				boolQuery.add(phraseQuerycontent, BooleanClause.Occur.SHOULD);
//				String[] fieldName = { "contentName", "text" }; 
//				QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_46, fieldName, analyzer);
//				Query q2 = queryParser.parse(record);
//				boolQuery.add(q2, BooleanClause.Occur.MUST);
//				
//				Query query = NumericRangeQuery.newIntRange("userId", userId, userId,
//						true, true);
//				boolQuery.add(query, BooleanClause.Occur.MUST);
			}else if(l.size()==1){
				String[] fieldName = { "contentName", "text" };
				if(words==null){
					QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_46, fieldName, analyzer);
					Query q2 = queryParser.parse(record);
					
					boolQuery.add(q2, BooleanClause.Occur.MUST);
					Query query = NumericRangeQuery.newIntRange("userId", searchparam.getUserId(), searchparam.getUserId(),
							true, true);
					boolQuery.add(query, BooleanClause.Occur.MUST);
				}else{
					for(int i=0;i<words.length;i++){
						QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_46, fieldName, analyzer);
						Query q2 = queryParser.parse(words[i]);
						BooleanQuery bool = new BooleanQuery();
						bool.add(q2, BooleanClause.Occur.SHOULD);
						boolQuery.add(bool, BooleanClause.Occur.MUST);
					}
					
					Query query = NumericRangeQuery.newIntRange("userId", searchparam.getUserId(), searchparam.getUserId(),
							true, true);
					boolQuery.add(query, BooleanClause.Occur.MUST);
				}
			}
		}else{
			Date dt = new Date();
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    		String currydate= df.format(dt);
    		Query tq  = NumericRangeQuery.newLongRange("newsTime",
        			getdate(getOneDay(currydate,-100)).getTime(),getdate(getOneDay(currydate,1)).getTime(), true, false);
    		boolQuery.add(tq, BooleanClause.Occur.MUST);
    		
    		Query query = NumericRangeQuery.newIntRange("userId", searchparam.getUserId(), searchparam.getUserId(),
					true, true);
			boolQuery.add(query, BooleanClause.Occur.MUST);
			
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
        	tq = NumericRangeQuery.newLongRange("newsTime",
        			getdate(startdate).getTime(),getdate(getOneDay(enddate,1)).getTime(), true, false);
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
        				IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(m), true);
        		        Lexeme lexeme;
        		        while ((lexeme = ikSegmenter.next()) != null) {
                			phraseQuerytitle.add(new Term("contentName", lexeme.getLexemeText()));
                			phraseQuerycontent.add(new Term("text", lexeme.getLexemeText()));
        		        }
        		        bool.add(phraseQuerytitle,BooleanClause.Occur.SHOULD);
        		        if(searchsource==1){
            				bool.add(phraseQuerycontent,BooleanClause.Occur.SHOULD);
            			}
        		        boolQuery.add(bool, BooleanClause.Occur.MUST);
        				
//            			BooleanQuery bool = new BooleanQuery();
//            			TermQuery query1 = new TermQuery(new Term("contentName", m));
//            			bool.add(query1,BooleanClause.Occur.SHOULD);
//            			if(searchsource==1){
//            				TermQuery query = new TermQuery(new Term("text", m));
//            				bool.add(query,BooleanClause.Occur.SHOULD);
//            			}
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
        				IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(n), true);
        		        Lexeme lexeme;
        		        while ((lexeme = ikSegmenter.next()) != null) {
                			phraseQuerytitle.add(new Term("contentName", lexeme.getLexemeText()));
                			phraseQuerycontent.add(new Term("text", lexeme.getLexemeText()));
        		        }
        		        bool.add(phraseQuerytitle,BooleanClause.Occur.SHOULD);
        		        if(searchsource==1){
            				bool.add(phraseQuerycontent,BooleanClause.Occur.SHOULD);
            			}
        		        boolQuery.add(bool, BooleanClause.Occur.MUST_NOT);
        		        
//            			BooleanQuery bool = new BooleanQuery();
//            			
//            			TermQuery query1 = new TermQuery(new Term("contentName", n));
//            			bool.add(query1,BooleanClause.Occur.SHOULD);
//            			if(searchsource==1){
//            				TermQuery query = new TermQuery(new Term("text", n));
//            				bool.add(query,BooleanClause.Occur.SHOULD);
//            			}
//            			boolQuery.add(bool, BooleanClause.Occur.MUST_NOT);
        			}
        			
        		}
        		if(tq!=null)boolQuery.add(tq, BooleanClause.Occur.MUST);
        		Query query = NumericRangeQuery.newIntRange("userId", userId, userId,
    					true, true);
    			boolQuery.add(query, BooleanClause.Occur.MUST);
        	}else{
        		BooleanQuery bq= new BooleanQuery();
        		for(int j=0;j<arbitrarilys.length;j++){
        			String a=arbitrarilys[j].trim();
        			if(!a.equals("")){
//        				BooleanQuery boolquery= new BooleanQuery();
//            			BooleanQuery boolq= new BooleanQuery();
//            			
//            			TermQuery q1 = new TermQuery(new Term("contentName", a));
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
        				IKSegmenter ikSegmenter1 = new IKSegmenter(new StringReader(a), true);
        		        Lexeme lexeme1;
        		        while ((lexeme1 = ikSegmenter1.next()) != null) {
                			phraseQuerytitle1.add(new Term("contentName", lexeme1.getLexemeText()));
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
//                				BooleanQuery bool = new BooleanQuery();
//        	        			TermQuery query1 = new TermQuery(new Term("contentName", m));
//        	        			
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
                				IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(m), true);
                		        Lexeme lexeme;
                		        while ((lexeme = ikSegmenter.next()) != null) {
                        			phraseQuerytitle.add(new Term("contentName", lexeme.getLexemeText()));
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
//    	        				BooleanQuery bool = new BooleanQuery();
//    	        				TermQuery query1 = new TermQuery(new Term("contentName", n));
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
    	        				IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(n), true);
    	        		        Lexeme lexeme;
    	        		        while ((lexeme = ikSegmenter.next()) != null) {
    	                			phraseQuerytitle.add(new Term("contentName", lexeme.getLexemeText()));
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
        		boolQuery.add(bq, BooleanClause.Occur.MUST);
        		Query query = NumericRangeQuery.newIntRange("userId", userId, userId,true, true);
    			boolQuery.add(query, BooleanClause.Occur.MUST);
        	}
        Sort sort =null;
        if(sorttype==1)sort =new Sort(new SortField("newsTime",SortField.Type.LONG,true));
		return new QueryWrapper(boolQuery, sort);
	}
	@Override
	public String getClassname() throws Exception {
		return "doc";
	}
}
