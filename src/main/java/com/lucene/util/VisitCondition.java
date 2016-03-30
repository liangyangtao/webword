package com.lucene.util;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lucene.entity.UbkTable;
import com.lucene.entity.term.AbstractCondition;
import com.lucene.entity.term.Term;

public class VisitCondition extends AbstractCondition {
	
	private static final String TERM_ID = "termId";
	private static final String CONDITIONS = "conditions";

	//
	private int offset;
	private int rowCount;
	private int articleId;
	private int userId;//用户Id
	private String keyword;//关键词
	private int nodeId;//节点id
	private String form;//开始时间
	private String to;//结束时间
	private String nodeName;//节点名称
	private List keywords;//关键词集合
	
	//
	private int termId;
	private List<Term> terms;
	private Term term;

	//
	private UbkTable ubkTable;

	
	public VisitCondition(){
		
	}
	
	public VisitCondition(int nodeId, int offset, int rowCount){
		this.offset = offset;
		this.rowCount = rowCount;
		this.nodeId = nodeId;
	}
	
	public VisitCondition(String filter, int offset, int rowCount) throws JSONException {
		
		this.filter = filter;
		this.offset = offset;
		this.rowCount = rowCount;
		
		if(this.filter != null){
			JSONArray array = new JSONArray(filter);
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				String field = obj.getString(PROPERTY);
				if (TERM_ID.equals(field)) {
					this.termId = obj.getInt(VALUE);
				} else if (CONDITIONS.equals(field)) {
					JSONArray cons = new JSONArray(obj.getString(VALUE));
					System.out.println(cons.toString());
					this.terms = Term.deserialize(cons.toString());
				}
			}
		}
	}

	/**
	 * 
	 * 方法名称    :filter
	 * 功能描述    ：查询条件组装
	 * 逻辑描述    :
	 * @param   :无
	 * @return  :void
	 * @throws  :无
	 * @since   :Ver 1.00
	 */
	public void filter(String filter) throws JSONException {
		
		JSONArray array = new JSONArray(filter);
		
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = array.getJSONObject(i);
			String field = obj.getString(PROPERTY);
			if(field.equals("nodeName")){
				this.nodeName = obj.getString(VALUE);
			}else if(field.equals("keyword")){
				this.keyword = obj.getString(VALUE);
			}else if(field.equals("timetext")){
				if(!obj.getString(VALUE).equals("")){
					String[] arr = obj.getString(VALUE).split("@#%");
					this.form = arr[0];
					this.to = arr[1];
				}
			}else if(field.equals("articleId")){
				//杨振兴2015-01-28为aop增加
				this.articleId=obj.getInt(VALUE);
			}
		}
	}

	
	//--------------getter,setter--------------------
	
	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getTermId() {
		return termId;
	}

	public void setTermId(int termId) {
		this.termId = termId;
	}

	public List<Term> getTerms() {
		return terms;
	}

	public void setTerms(List<Term> terms) {
		this.terms = terms;
	}

	public UbkTable getUbkTable() {
		return ubkTable;
	}

	public void setUbkTable(UbkTable ubkTable) {
		this.ubkTable = ubkTable;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public List getKeywords() {
		return keywords;
	}

	public void setKeywords(List keywords) {
		this.keywords = keywords;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

}
