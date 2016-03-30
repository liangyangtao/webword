package com.lucene.entity.doc;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.lucene.entity.UbkTable;


public class Content extends UbkTable {

	/*public static Comparator<Content> comparator = new Comparator<Content>() {

		@Override
		public int compare(Content o1, Content o2) {
			// ASC
			if (o1.getDisplayOrder() > o2.getDisplayOrder()) {
				return 1;
			} else if (o1.getDisplayOrder() == o2.getDisplayOrder()) {
				return 0;
			} else {
				return -1;
			}
		}

	};*/

/*	public static List<Content> sort(List<Content> documents) {
		Map<DocType, List<Content>> map = new HashMap<DocType, List<Content>>();

		DocType[] types = DocType.values();
		for (DocType type : types) {
			map.put(type, new ArrayList<Content>());
		}

		for (Content doc : documents) {
			map.get(doc.getType()).add(doc);
		}

		try {
			documents = new ArrayList<Content>();
			for (DocType type : types) {
				List<Content> list = map.get(type);
				Collections.sort(list, comparator);
				documents.addAll(list);
			}

		} catch (Exception e) {
			
		}
		return documents;

	}*/

	

	/**
	 * 版面ID
	 */
//	private int forumId;

	/**
	 * 产品ID
	 */
//	private int templateId;
	
	/**
	 * 属性ID
	 */
//	private int structureId;
	
	/**
	 * 文章类型
	 */
//	private DocType type;

	/**
	 * 文章标题
	 */
//	private String docTitle;
	
	/**
	 * 预览次数
	 */
//	private int docViews;
	
	/**
	 * 排序
	 */
//	private int displayOrder;

	/**
	 * 是否阻止
	 */
//	private boolean deny;
	
	/**
	 * 模板名称(产品名称)
	 */
//	private String templateName;
	
	/**
	 * 内容ID
	 */
	private int contentId;
	
	/**
	 * 内容名称
	 */
	private String contentName;

	/**
	 * 内容摘要
	 */
	private String contentBrief;

	/**
	 * 文本内容
	 */
	private String text;
	
	/**
	 * 内容所属
	 */
	private int userId;

	/**
	 * 建立时间
	 */
	private long contentInsertTime;
	
	/**
	 * 建立时间字符串
	 */
	private Timestamp contentInsertTimeTemp;
	/**
	 * 节点Id
	 */
	private int nodeId;


	/**
	 * 关键词
	 */
	private List<Keyword> keywords = new ArrayList<Keyword>();

	public Content() {
	}

	/**
	 * 
	 * @param keyword
	 * @return
	 */
	public boolean addKeyword(Keyword keyword) {
		return keywords.add(keyword);
	}
	
	//========getter,setter===============

	public Timestamp getContentInsertTimeTemp() {
		return contentInsertTimeTemp;
	}

	public void setContentInsertTimeTemp(Timestamp contentInsertTimeTemp) {
		this.contentInsertTimeTemp = contentInsertTimeTemp;
	}

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public String getContentBrief() {
		return contentBrief;
	}

	public void setContentBrief(String contentBrief) {
		this.contentBrief = contentBrief;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public long getContentInsertTime() {
		return contentInsertTime;
	}

	public void setContentInsertTime(long contentInsertTime) {
		this.contentInsertTime = contentInsertTime;
	}

	public List<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

}
